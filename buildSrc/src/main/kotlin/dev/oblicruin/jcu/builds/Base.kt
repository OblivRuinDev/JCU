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

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.AbstractCopyTask
import org.gradle.api.tasks.TaskProvider
import java.io.BufferedWriter

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

fun BufferedWriter.writeln(str: String) {
    write(str)
    newLine()
}