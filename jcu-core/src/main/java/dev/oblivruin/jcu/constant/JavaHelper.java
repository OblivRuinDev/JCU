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

package dev.oblivruin.jcu.constant;

public final class JavaHelper {
    public static int compute(int minor, int major) {
        return (minor << 16) | major;
    }

    public static int minor(int ver) {
        return ver >>> 16;
    }

    public static int major(int ver) {
        return ver & 0xFFFF;
    }

    public static void validate(int ver) {
        int major = ver & 0xFFFF;
        int minor = ver >>> 16;
    }
}
