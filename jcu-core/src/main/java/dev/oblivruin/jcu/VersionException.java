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
package dev.oblivruin.jcu;

import dev.oblivruin.jcu.constant.Java;

public class VersionException extends ValidateException {
    public VersionException(String msg) {
        super(msg);
    }

    public VersionException(String msg, boolean track) {
        super(msg, track);
    }

    public static void throw$(int cfVer) {
        throw new VersionException("Unsupported class file major version : " + cfVer);
    }

    public static final String CP7 = concat("Constant MethodHandle/MethodType/InvokeDynamic", Java.V1_7);
    public static final String CP9 = concat("Constant Module/Package", Java.V9);
    public static final String CP11 = concat("Constant Dynamic", Java.V11);
    public static final String AF9 = concat("ACC_MODULE", Java.V9);

    private static String concat(String feature, int cfVer) {
        return feature + " require ClassFile V" + cfVer;
    }
}
