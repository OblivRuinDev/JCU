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
plugins {
    `java-platform`
}

description = "JCU, a Java Classfile Util which provide access with raw class bytes"
version = "1.0-SNAPSHOT"

javaPlatform { allowDependencies() }

repositories {
    mavenCentral()
}

allprojects {
    group = "dev.oblivruin.jcu"
    version = rootProject.version
}

subprojects {
    repositories { mavenCentral() }

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

    dependencies {
        api(buildApi)
    }

    init1()
}

val helper = project(":jcu-helper") {
    description = "Core API for classfile"

    dependencies {
        api(buildApi)
        api(core)
    }

    init1()
}

val asm = project(":jcu-asm") {
    description = "Core API for classfile"

    dependencies {
        api(buildApi)
        api(core)
        api(helper)
    }

    init1()
}

val buildTool = project(":build-tool") {
    description = "A tool for build"

    dependencies {
        api(core)
    }
}

val unsafe = project(":unsafe") {
    description = "Deep access to JDK"

    dependencies {
        api(buildApi)
        api(core)
    }
}

fun Project.init1() {
    val genModuleInfo by tasks.register<JavaExec>("genModuleInfo") {}

    val recompile by tasks.register<JavaExec>("recompile") {
//    classpath = project(":build-tool").configurations["runtimeClasspath"]
//    classpath += project(":build-tool").b
    }

    tasks.named<Jar>("jar") {
        dependsOn("genModuleInfo")
        from(tasks.named("genModuleInfo").get().outputs.files)
    }
}

//dependencies {
//    testImplementation(platform("org.junit:junit-bom:5.10.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
//}