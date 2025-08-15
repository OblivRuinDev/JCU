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

import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.util.*

@CacheableTask
abstract class JarIndexTask : SingleFileBuildTask() {
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
            writer.writeln("JarIndex-Version: 1.0")
            writer.newLine()

            writer.writeln(jarName.get())

            val packages = this.packages.get()
            Arrays.sort(packages)
            val set: HashSet<String> = HashSet()
            var index: Int
            for (pkg in packages) {
                index = pkg.indexOf('/')
                while (index != -1) {
                    val str = pkg.substring(0, index)
                    if (set.add(str)) {
                        writer.writeln(str)
                    }
                    index = pkg.indexOf('/', index + 1)
                }
                if (set.add(pkg)) {
                    writer.writeln(pkg)
                }
            }

            writer.writeln("module-info.class")
        }
    }
}

fun BufferedWriter.writeln(str: String) {
    write(str)
    newLine()
}