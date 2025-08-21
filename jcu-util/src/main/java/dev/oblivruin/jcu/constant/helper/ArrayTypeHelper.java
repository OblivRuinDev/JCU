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
package dev.oblivruin.jcu.constant.helper;

import dev.oblivruin.jcu.ValidateException;

import static dev.oblivruin.jcu.constant.ArrayType.*;

public final class ArrayTypeHelper {
    private ArrayTypeHelper() {}

    public static String[] names() {
        return new String[]{"T_BOOLEAN", "T_CHAR", "T_FLOAT", "T_DOUBLE", "T_BYTE", "T_SHORT", "T_INT", "T_LONG"};
    }

    public static void validate(int value) throws ValidateException {
        if (value < T_BOOLEAN || value > T_LONG) {
            throw new ValidateException(value, "ArrayType");
        }
    }

    public static String toString(int value) {
        switch (value) {
            case        T_BOOLEAN:
                return "T_BOOLEAN";
            case        T_CHAR:
                return "T_CHAR";
            case        T_FLOAT:
                return "T_FLOAT";
            case        T_DOUBLE:
                return "T_DOUBLE";
            case        T_BYTE:
                return "T_BYTE";
            case        T_SHORT:
                return "T_SHORT";
            case        T_INT:
                return "T_INT";
            case        T_LONG:
                return "T_LONG";
            default:
                return "T_UNKNOWN=" + value;
        }
    }

    public static int valueOf(String name) {
        switch (name) {
            case      "T_BOOLEAN":
                return T_BOOLEAN;
            case      "T_CHAR":
                return T_CHAR;
            case      "T_FLOAT":
                return T_FLOAT;
            case      "T_DOUBLE":
                return T_DOUBLE;
            case      "T_BYTE":
                return T_BYTE;
            case      "T_SHORT":
                return T_SHORT;
            case      "T_INT":
                return T_INT;
            case      "T_LONG":
                return T_LONG;
            default:
                return -1;
        }
    }
}
