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

import jdk.internal.misc.Unsafe;

/**
 * Implement by {@link jdk.internal.misc.Unsafe}
 */
public class BytesUtil$$$$Unsafe9 {
    private static final Unsafe u = Unsafe.getUnsafe();

    public static void setShort(byte[] bytes, int off, short v) {
        u.putShortUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, v, true);
    }

    public static short getShort(byte[] bytes, int off) {
        return u.getShortUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, true);
    }

    public static boolean matchShort(byte[] bytes, int off, short v) {
        return bytes[off] == (byte) (v >>> 8) && bytes[off+1] == (byte) v;
    }

    public static void setUShort(byte[] bytes, int off, int v) {
        setShort(bytes, off, (short) v);
    }

    public static int getUShort(byte[] bytes, int off) {
        return getShort(bytes, off) & 0xFFFF;
    }

    public static boolean matchUShort(byte[] bytes, int off, int v) {
        return bytes[off] == (byte) (v >>> 8) && bytes[off+1] == (byte) v;
    }

    public static void setInt(byte[] bytes, int off, int v) {
        u.putIntUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, v, true);
    }

    public static int getInt(byte[] bytes, int off) {
        return u.getIntUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, true);
    }

    public static boolean matchInt(byte[] bytes, int off, int v) {
        return getInt(bytes, off) == v;
    }

    public static void setLong(byte[] bytes, int off, long v) {
        u.putLongUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, v, true);
    }

    public static long getLong(byte[] bytes, int off) {
        return u.getLongUnaligned(bytes, Unsafe.ARRAY_BYTE_BASE_OFFSET + off, true);
    }

    public static boolean matchLong(byte[] bytes, int off, long v) {
        return getLong(bytes, off) == v;
    }
}
