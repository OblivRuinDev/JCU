// JCU: Java Classfile Util, which provides low-level primitives for
//  interacting with class bytecodes and unsafe but fast APIs
// Copyright (c) 2025 OblivRuinDev.
// All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
@file:Suppress("Since15", "UnstableApiUsage")

import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.Constructor
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.*
import java.util.function.Consumer

plugins {
    `java-platform`
    idea
}

description = "JCU, a Java Classfile Util which provide access to raw class bytes"
version = "1.0.0"
group = "dev.oblivruin.jcu"

javaPlatform { allowDependencies() }

repositories {
    mavenCentral()
}

subprojects {
    version = rootProject.version
    repositories.mavenCentral()

    apply(plugin = "java-library")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

project(":build-api") {
    description = "Api for custom build"
}

val core = project(":jcu-core") {
    description = "Core API for classfile"
    group = "dev.oblivruin.jcu.core"


    set(arrayOf("dev/oblivruin/jcu", "dev/oblivruin/jcu/constant", "dev/oblivruin/jcu/internal", "dev/oblivruin/jcu/misc"), vers = intArrayOf(9, 12))
    ToolLoader.instance.addURL(layout.buildDirectory.dir("classes/java/main").get().asFile.toURI().toURL())
}

val util = project(":jcu-util") {
    description = "Provide extra help for check, (de)serialize"
    group = "dev.oblivruin.jcu.util"

    dependencies {
        api(core)
    }

    set(arrayOf("dev/oblivruin/jcu/attribute", "dev/oblivruin/jcu/constant/helper"), arrayOf(core.group.toString()))
}

val common = project(":jcu-common") {
    group = "dev.oblivruin.jcu.common"
    dependencies {
        api(core)
    }

    set(arrayOf("dev/oblivruin/jcu/attribute", "dev/oblivruin/jcu/constant/helper"), arrayOf(core.group.toString()))
}

val asm = project(":jcu-asm") {
    description = "Asm-style API"
    group = "dev.oblivruin.jcu.asm"

    dependencies {
        api(core)
        api(util)
    }

    set(arrayOf("dev/oblivruin/jcu/asm"), arrayOf(core.group.toString(), util.group.toString()))
}

project(":test-tool") {
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dependencies {
        api(core)
        api(common)
        val implementation by configurations
        implementation(platform("org.junit:junit-bom:5.13.4"))
        implementation("org.junit.jupiter:junit-jupiter")
    }
}

project(":build-tool") {
    description = "A implementation for recompile"

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        api(core)
    }
    ToolLoader.instance.addURL(layout.buildDirectory.dir("classes/java/main").get().asFile.toURI().toURL())
}

fun Project.set(packages: Array<String>, requires: Array<String> = emptyArray(), vararg vers: Int) {
    dependencies {
        val testRuntimeOnly by configurations
        val testImplementation by configurations

        testImplementation(project(":test-tool"))//fuck this line because I waste at least 2 days to configure it
        testImplementation(platform("org.junit:junit-bom:5.13.4"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        api(project(":build-api"))
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
        maxParallelForks = 8
    }

    val taskJar = tasks.named<Jar>("jar") {
        isPreserveFileTimestamps = false

        manifest {
            attributes["Implementation-Version"] = version
            attributes["Implementation-Title"] = this@set.name.uppercase()
            if (vers.isNotEmpty()) {
                attributes["Multi-Release"] = "true"
            }
        }

        exclude("**/*\$\$\$\$*.class")
    }

    tasks.register<Recompile>("recompile") {}

    val taskModInf = tasks.register<ModuleInfoTask>("genModuleInfo") {
        moduleName = this@set.group.toString()
        moduleVer = version.toString()
        exports = packages
        this.requires = requires
        defOutput()
        appendOutput(taskJar)
    }

    val taskJarIndex = tasks.register<JarIndexTask>("genJarIndex") {
        this.packages = packages
        jarName = tasks.named<Jar>("jar").get().archiveFileName
        defOutput()
        appendOutput(taskJar, "META-INF/")
    }

    taskJar.configure {
        dependsOn(taskModInf, taskJarIndex)
    }

    // shadow class source set for multiple version
    for (ver in vers) {
        sourceSets.create("v$ver") {
            java.srcDir("shadow/v$ver")
            val lib = rootProject.file("lib/jdk${ver}.jar")
            if (lib.exists()) {
                dependencies.add("v${ver}Implementation", files(lib))
            }
            val compTask = tasks.named<JavaCompile>("compileV${ver}Java") {
                val v = JavaVersion.toVersion(ver).toString()
                sourceCompatibility = v
                targetCompatibility = v
                options.compilerArgs.add("--add-exports=java.base/jdk.internal.access=ALL-UNNAMED")
                options.compilerArgs.add("--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED")
                options.compilerArgs.add("--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED")
            }
            taskJar.configure {
                dependsOn(compTask)
                from(output) {
                    into("META-INF/versions/$ver")
                }
            }
        }
    }
}

abstract class BuildTask: DefaultTask() {
    final override fun getGroup() = "build"
}

abstract class SingleFileBuildTask: BuildTask() {
    fun appendOutput(task: TaskProvider<out AbstractCopyTask>, into: String? = null) {
        if (into == null) {
            task.get().from(this.outputs.files.singleFile)
        } else {
            task.get().from(this.outputs.files.singleFile) {
                into(into)
            }
        }
    }
}

@CacheableTask
abstract class JarIndexTask: SingleFileBuildTask() {
    @get:Input
    abstract val packages: Property<Array<String>>

    @get:Input
    abstract val jarName: Property<String>

    fun defOutput() {
        outputs.files(project.layout.buildDirectory.dir("generated/jarindex").get().file("INDEX.LIST"))
    }

    @TaskAction
    fun exec() {
        val outputFile = outputs.files.singleFile.toPath()
        Files.createDirectories(outputFile.parent)
        BufferedWriter(OutputStreamWriter(Files.newOutputStream(outputFile))).use { writer ->
            writer.write("JarIndex-Version: 1.0")
            writer.newLine()
            writer.newLine()

            writer.write(jarName.get())
            writer.newLine()

            val packages = this.packages.get()
            Arrays.sort(packages)
            val set: HashSet<String> = HashSet()
            var index: Int
            for (pkg in packages) {
                index = pkg.indexOf('/')
                while (index != -1) {
                    val str = pkg.substring(0, index)
                    if (set.add(str)) {
                        writer.write(str)
                        writer.newLine()
                    }
                    index = pkg.indexOf('/', index + 1)
                }
                if (set.add(pkg)) {
                    writer.write(pkg)
                    writer.newLine()
                }
            }

            writer.write("module-info.class")
            writer.newLine()
            writer.newLine()
        }
    }
}

@CacheableTask
abstract class ModuleInfoTask: SingleFileBuildTask() {
    @get:Input
    abstract val moduleName: Property<String>

    @get:Input
    abstract val moduleVer: Property<String>

    @get:Input
    abstract val exports: Property<Array<String>>

    @get:Input
    abstract val requires: Property<Array<String>>

    fun defOutput() {
        outputs.files(project.layout.buildDirectory.dir("generated/module").get().file("module-info.class"))
    }

    @TaskAction
    fun exec() {
        val outputFile = outputs.files.singleFile.toPath()
        Files.createDirectories(outputFile.parent)
        DataOutputStream(Files.newOutputStream(outputFile)).use { dos ->
            dos.writeInt(0xCAFEBABE.toInt())//magic
            dos.writeInt(53)//version
            val buffer = ByteArray(3)
            buffer[1] = 0
            val exports = this.exports.get()
            val requires = this.requires.get()
            val exportSize = exports.size
            if (exports.size + requires.size > 123) {
                throw IndexOutOfBoundsException("This script only support constant count <= 255")
            }
            dos.writeShort(10 + (exports.size + requires.size) * 2)
            // #1 Utf8    module-info
            dos.write(1); dos.writeUTF("module-info")
            // #2 Class   #1       | #3 Utf8    Module
            dos.writeInt(0x07000101); dos.writeUTF("Module")
            // #4 Utf8    ModulePackages
            dos.write(1); dos.writeUTF("ModulePackages")
            // #5 Utf8    $moduleName
            dos.write(1)
            dos.writeUTF(moduleName.get())
            // #6 Module  #4
            buffer[0] = 19// tag package
            buffer[2] = 5
            dos.write(buffer)
            // #7 Utf8    $moduleVer
            dos.write(1); dos.writeUTF(moduleVer.get())
            // #8 Utf8    java.base
            dos.write(1); dos.writeUTF("java.base")
            // #9 Module  #8
            buffer[2] = 8
            dos.write(buffer)
            for (str in requires) {
                // Utf8
                dos.write(1); dos.writeUTF(str)
                // Module
                buffer[2] = (buffer[2] + 2).toByte()
                dos.write(buffer)
            }
            buffer[0] = 20//package
            for (str in exports) {
                // Utf8
                dos.write(1); dos.writeUTF(str)
                // Package
                buffer[2] = (buffer[2] + 2).toByte()
                dos.write(buffer)
            }
            dos.writeShort(0x8000)// access
            dos.writeShort(2)// this
            dos.writeLong(0)// super, interface, field, method
            dos.writeInt(0x0002_0003)// attribute count and 1st attr name index
            dos.writeInt(22 + (exportSize + requires.size) * 6)// length
            dos.writeShort(6)// name index
            dos.writeInt(7)// flag, version
            dos.writeShort(requires.size + 1)// require count
            dos.writeInt(0x0008_9000); dos.writeShort(0)// "java.base" ACC_MANDATED
            var pos = 11
            for (index in 1..requires.size) {
                dos.writeShort(pos)
                dos.writeInt(0)
                pos+=2
            }
            dos.writeShort(exportSize)// export count
            val array = ByteArray(exportSize*2)
            var pointer = -1
            for (index in 1..exportSize) {
                dos.writeShort(pos)
                array[++pointer] = 0
                array[++pointer] = pos.toByte()
                dos.writeInt(0)
                pos+=2
            }
            dos.writeInt(0)// open, use  count
            dos.writeInt(4)// provide count and 2nd attr name index
            dos.writeInt(2 + exportSize*2)
            dos.writeShort(exportSize)
            dos.write(array)
        }
    }
}

class ToolLoader private constructor(): URLClassLoader(emptyArray(), ToolLoader::class.java.classLoader) {
    companion object {
        init {
            ClassLoader.registerAsParallelCapable()
        }

        @JvmStatic
        val instance = ToolLoader()

        @Suppress("UNCHECKED_CAST")
        val renameC by lazy {
            instance.findClass("dev.oblivruin.jcu.builds.Rename").getConstructor(File::class.java) as Constructor<Consumer<File>>
        }
    }

    public override fun addURL(url: URL) = super.addURL(url)
}

@Suppress("LeakingThis")
abstract class BuildToolTask: BuildTask() {
    init {
        dependsOn(project.rootProject.project("jcu-core").tasks.findByName("compileJava"), project.rootProject.project("build-tool").tasks.findByName("compileJava"))
    }
}

@CacheableTask
abstract class Recompile: BuildToolTask() {
    init {
        outputs.dir(project.layout.buildDirectory.dir("generated/classres").get())
    }

    @TaskAction
    fun exec() {
        inputs.files.forEach(ToolLoader.renameC.newInstance(project.layout.buildDirectory.dir("generated/classres").get().asFile))
    }
}