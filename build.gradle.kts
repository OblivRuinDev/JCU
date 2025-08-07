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
import java.io.DataOutputStream
import java.nio.file.Files

plugins {
    `java-platform`
}

description = "JCU, a Java Classfile Util which provide access to raw class bytes"
version = "1.0-SNAPSHOT"
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

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}

val buildApi = project(":build-api") {
    description = "Api for custom build"
}

val core = project(":jcu-core") {
    description = "Core API for classfile"
    group = "dev.oblivruin.jcu.core"

    dependencies.api(buildApi)

    set(arrayOf("dev/oblivruin/jcu", "dev/oblivruin/jcu/constant", "dev/oblivruin/jcu/util"), emptyArray(), 9)

    tasks.named<JavaCompile>("compileV9Java") {
        options.compilerArgs.add("--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED")
    }
}

val helper = project(":jcu-helper") {
    description = "Provide extra help for check, (de)serialize"
    group = "dev.oblivruin.jcu.helper"

    dependencies {
        api(buildApi)
        api(core)
    }

    set(arrayOf("dev/oblivruin/jcu/attribute", "dev/oblivruin/jcu/constant/helper"), arrayOf(core.group.toString()))
}

val common = project(":jcu-common") {
    group = "dev.oblivruin.jcu.common"
    dependencies {
        api(buildApi)
        api(core)
    }

    set(arrayOf("dev/oblivruin/jcu/attribute", "dev/oblivruin/jcu/constant/helper"), arrayOf(core.group.toString()))
}

val asm = project(":jcu-asm") {
    description = "Asm-style API"
    group = "dev.oblivruin.jcu.asm"

    dependencies {
        api(buildApi)
        api(core)
        api(helper)
    }

    set(arrayOf("dev/oblivruin/jcu/asm"), arrayOf(core.group.toString(), helper.group.toString()))
}

val buildTool = project(":build-tool") {
    description = "A implementation for recompile"

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        api(core)
    }
}

fun Project.set(packages: Array<String>, requires1: Array<String>, vararg vers: Int) {
    val gr = this.group.toString()
    val gen = tasks.register<ModuleInfoTask>("genModuleInfo") {
        moduleName = gr
        moduleVer = version.toString()
        exports = packages
        requires = requires1
    }
    val taskJar = tasks.named<Jar>("jar") {
        dependsOn(gen)

        from(gen.get().outputs.files.singleFile)
        manifest {
            attributes["Implementation-Version"] = version
            if (vers.isNotEmpty()) {
                attributes["Multi-Release"] = "true"
            }
        }
    }

    // shadow class source set for multiple version
    for (ver in vers) {
        sourceSets.create("v$ver") {
            java.srcDir("shadow/v$ver")
            val path1 = layout.buildDirectory.dir("classes/v$ver")
            output.dir(path1)
            val compTask = tasks.named<JavaCompile>("compileV${ver}Java") {
                inputs.files(java)
                outputs.dir(path1)
                val v = JavaVersion.toVersion(ver).toString()
                sourceCompatibility = v
                targetCompatibility = v
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

fun JavaExec.setCP() {
    dependsOn(buildTool.tasks.named("compileJava"))
    classpath += buildTool.sourceSets["main"].output
    classpath += core.sourceSets["main"].output
}

abstract class ModuleInfoTask : DefaultTask() {
    @get:Input
    abstract val moduleName: Property<String>

    @get:Input
    abstract val moduleVer: Property<String>

    @get:Input
    abstract val exports: Property<Array<String>>

    @get:Input
    abstract val requires: Property<Array<String>>

    init {
        if (outputs.files.isEmpty) {
            outputs.files(project.layout.buildDirectory.dir("generated/module").get().file("module-info.class"))
        }
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
            if (exports.size + requires.size > 123) {
                throw IndexOutOfBoundsException("This script only support constant count <= 255")
            }
            dos.writeShort(9 + (exports.size + requires.size) * 2)
            // #1 Utf8    module-info
            dos.write(1); dos.writeUTF("module-info")
            // #2 Class   #1       | #3 Utf8    Module
            dos.writeInt(0x07000101) ;dos.writeUTF("Module")
            // #4 Utf8    $moduleName
            dos.write(1)
            dos.writeUTF(moduleName.get())
            // #5 Module  #4
            buffer[0] = 19// tag package
            buffer[2] = 4
            dos.write(buffer)
            // #6 Utf8    $moduleVer
            dos.write(1); dos.writeUTF(moduleVer.get())
            // #7 Utf8    java.base
            dos.write(1); dos.writeUTF("java.base")
            // #8 Module  #7
            buffer[2] = 7
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
            dos.writeInt(0x0001_0003)// attribute count and 1st attr name index
            dos.writeInt(22 + (exports.size + requires.size) * 6)// length
            dos.writeShort(5)// name index
            dos.writeInt(6)// flag, version
            dos.writeShort(requires.size + 1)// require count
            dos.writeInt(0x0008_8000); dos.writeShort(0)// "java.base" ACC_MANDATED
            var pos = 10
            for (index in 1..requires.size) {
                dos.writeShort(pos)
                dos.writeInt(0)
                pos+=2
            }
            dos.writeShort(exports.size)// export count
            for (index in 1..exports.size) {
                dos.writeShort(pos)
                dos.writeInt(0)
                pos+=2
            }
            dos.writeShort(0)// open count
            dos.writeInt(0)// use, provide  count
        }
    }

    final override fun getGroup(): String {
        return "build"
    }
}