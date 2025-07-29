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
package dev.oblivruin.jcu.util;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public final class UtilX {
    private UtilX() {}

    public static IllegalArgumentException forInputEx(String s) {
        return new IllegalArgumentException("For input string: \"" + s + "\"");
    }

    /**
     * Compares two lists for content equality regardless of element order.
     * @param list1 first list
     * @param list2 second list
     * @return true if both lists contain the same elements (order-agnostic)
     * @param <T> the class of the objects in the list
     */
    public static <T> boolean equalE(List<T> list1, List<T> list2) {
        if (fastEqual(list1, list2)) {
            return true;
        }
        if (list1 == null || list2 == null) {//only one list is null and another list is not empty
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        // Convert first list to array for destructive matching
        final Object[] array = list1.toArray();
        final int len = array.length;
        lab:for (T another : list2) {
            for (int index = 0; index < len; ++index) {
                if (another.equals(array[index])) {
                    // Mark element as matched to prevent reuse
                    array[index] = null;
                    // Continue with next element in list2
                    continue lab;
                }
            }
            // Current element(in list2) has no match in list1
            return false;
        }
        // All elements matched successfully
        return true;
    }

    /**
     * Checks if both collections are either null, empty or equal.<br>
     * Null collection means empty.
     * @param c1 first collection
     * @param c2 second collection
     * @return true if both collections are null/empty
     * @param <T> the class of the objects in the collection
     */
    public static <T> boolean fastEqual(Collection<T> c1, Collection<T> c2) {
        return c1 == c2 ||
                ((c1 == null || c1.isEmpty()) &&
                        (c2 == null || c2.isEmpty()));
    }

    public static String toHexStr(int value) {
        final byte[] bytes;
        if ((value & 0xFFFF0000) == 0) {
            bytes = new byte[6];
            int val = value & 0xFFFF;
            bytes[5] = toHexDigit(val & 0xF);
            bytes[4] = toHexDigit((val >>> 4) & 0xF);
            bytes[3] = toHexDigit((val >>> 8) & 0xF);
            bytes[2] = toHexDigit((val >>> 12) & 0xF);
        } else {
            bytes = new byte[10];
            bytes[9] = toHexDigit(value & 0xF);
            bytes[8] = toHexDigit((value >>> 4) & 0xF);
            bytes[7] = toHexDigit((value >>> 8) & 0xF);
            bytes[6] = toHexDigit((value >>> 12) & 0xF);
            bytes[5] = toHexDigit((value >>> 16) & 0xF);
            bytes[4] = toHexDigit((value >>> 20) & 0xF);
            bytes[3] = toHexDigit((value >>> 24) & 0xF);
            bytes[2] = toHexDigit((value >>> 28) & 0xF);
        }
        bytes[0] = '0';
        bytes[1] = 'x';

        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    public static String decodeUtf8(byte[] bytes, int off, int len) {
        return new String(bytes, off, len, StandardCharsets.UTF_8);
    }

    private static byte toHexDigit(int digit) {
        return (byte) (digit < 10
                ? '0' + digit
                : 'A' - 10 + digit);
    }
}
