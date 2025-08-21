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

import dev.oblicruin.jcu.builds.ModuleInfoTask
import dev.oblicruin.jcu.builds.JarIndexTask

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
    }

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
// not in use at now but reserve it
//fun JavaExec.setCP() {
//    dependsOn(buildTool.tasks.named("compileJava"))
//    classpath += buildTool.sourceSets["main"].output
//    classpath += core.sourceSets["main"].output
//}