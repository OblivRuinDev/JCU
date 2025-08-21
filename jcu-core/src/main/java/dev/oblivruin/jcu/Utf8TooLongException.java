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

/**
 * Thrown to indicate that the total number of bytes of the string
 * represented by {@code CONSTANT_Utf8_info} is greater than {@code 65535}.
 *
 * @author OblivRuinDev
 */
public class Utf8TooLongException extends ConstantException {
    /**
     * The string value of the {@code CONSTANT_Utf8_info}
     */
    public final String utf8V;
    /**
     * The actual number of bytes.
     */
    public final int length;

    public Utf8TooLongException(int index, String utf8V, int length) {
        super(index, null);

        this.utf8V = utf8V;
        this.length = length;
    }

    @Override
    public String getMessage() {
        int len = utf8V.length();
        return "Utf8 (" + utf8V.substring(0, 8) + "..." + utf8V.substring(len - 8, len) + ") too long: " +
                length + " bytes";
    }
}
