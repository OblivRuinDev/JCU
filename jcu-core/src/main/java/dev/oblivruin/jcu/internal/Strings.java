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
package dev.oblivruin.jcu.internal;

import dev.oblivruin.jcu.builds.api.shadow.SameHead;
import dev.oblivruin.jcu.builds.api.shadow.Shadow;
import dev.oblivruin.jcu.misc.ByteArray;

/**
 * Common implementation which using standard Java API, there are more efficient implementations
 * specifically tailored for JDK internal.
 *
 * @author OblivRuinDev
 */
@Shadow
public final class Strings {
    private Strings() {}

    /**
     * Write Modified UTF-8 at {@code array.length}.
     *
     * @param value the string value to be written
     * @param array the target byte array
     * @return the length of written bytes
     */
    @SameHead
    public static int write(String value, ByteArray array) {
        int pointer = array.length - 1;
        int len = value.length();
        array.ensureFree(len);
        byte[] data = array.data;
        for (int index = 0; index < len; ++index) {
            char c = value.charAt(index);
            if (c != 0 && c < 0x80) {
                data[++pointer] = (byte) c;
            } else {
                int v = 3*(len - index) + pointer + 1;
                if (v > data.length) {
                    byte[] temp = new byte[array.newSize(v)];
                    System.arraycopy(data, 0, temp, 0, pointer + 1);
                    array.data = temp;
                    data = temp;
                }
                for (; index < len; ++index) {
                    c = value.charAt(index);
                    if (c >= 0x800) {
                        data[++pointer] = (byte) (0b1110_0000 | c >> 12 & 0b0000_1111);
                        data[++pointer] = (byte) (0b1000_0000 | c >> 6  & 0b0011_1111);
                        data[++pointer] = (byte) (0b1000_0000 | c       & 0b0011_1111);
                    } else if (c >= 0x80 || c == 0) {
                        data[++pointer] = (byte) (0b1100_0000 | c >> 6 & 0b0001_1111);
                        data[++pointer] = (byte) (0b1000_0000 | c      & 0b0011_1111);
                    } else {
                        data[++pointer] = (byte) c;
                    }
                }
                len = ++pointer - array.length;
                array.length = pointer;
                return len;
            }
        }
        array.length = 1 + pointer;
        return len;
    }
}
