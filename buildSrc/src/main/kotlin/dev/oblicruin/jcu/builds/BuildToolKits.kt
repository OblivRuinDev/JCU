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
package dev.oblicruin.jcu.builds

import org.gradle.api.file.Directory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.net.URLClassLoader
import java.util.function.BiConsumer
import java.util.function.Function

object BuildTool: URLClassLoader(arrayOf(), BuildTool::class.java.classLoader) {
    @Suppress("UNCHECKED_CAST")
    private val rename by lazy {
        instance("dev.oblivruin.jcu.builds.Rename") as BiConsumer<Function<String, FileOutputStream>, FileInputStream>
    }

    fun renameImpl(dir: Directory): (File) -> Unit = { file ->
        rename.accept(
            Function { string -> dir.file(string).asFile.outputStream() },
            file.inputStream())
    }

    public override fun addURL(url: URL) = super.addURL(url)

    fun instance(className: String): Any = findClass(className).getConstructor().newInstance()
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
    fun exec() =
        inputs.files.forEach(BuildTool.renameImpl(project.layout.buildDirectory.dir("generated/classres").get()))
}