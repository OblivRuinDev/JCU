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

/**
 * A shadow class, have another version in JDK 9.
 *
 * @author OblivRuinDev
 */
@Shadow
public final class BytesUtil {
    @SameHead
    private BytesUtil() {}

    @SameHead
    public static void setShort(byte[] bytes, int off, short v) {
        bytes[off  ] = (byte) (v >>> 8);
        bytes[off+1] = (byte) v;
    }

    @SameHead
    public static short getShort(byte[] bytes, int off) {
        return (short) getUShort(bytes, off);
    }

    @SameHead
    public static boolean matchShort(byte[] bytes, int off, short v) {
        return bytes[off] == (byte) (v >>> 8) && bytes[off+1] == (byte) v;
    }

    @SameHead
    public static void setUShort(byte[] bytes, int off, int v) {
        bytes[off  ] = (byte) (v >>> 8);
        bytes[off+1] = (byte) v;
    }

    @SameHead
    public static int getUShort(byte[] bytes, int off) {
        return (bytes[off] & 0xFF) << 8 | bytes[off+1] & 0xFF;
    }

    @SameHead
    public static boolean matchUShort(byte[] bytes, int off, int v) {
        return bytes[off] == (byte) (v >>> 8) && bytes[off+1] == (byte) v;
    }

    @SameHead
    public static void setInt(byte[] bytes, int off, int v) {
        bytes[off  ] = (byte) (v >>> 24);
        bytes[off+1] = (byte) (v >>> 16);
        bytes[off+2] = (byte) (v >>> 8 );
        bytes[off+3] = (byte)  v;
    }

    @SameHead
    public static int getInt(byte[] bytes, int off) {
        return  (bytes[off  ] & 0xFF) << 24 |
                (bytes[off+1] & 0xFF) << 16 |
                (bytes[off+2] & 0xFF) << 8  |
                (bytes[off+3] & 0xFF);
    }

    @SameHead
    public static boolean matchInt(byte[] bytes, int off, int v) {
        return  bytes[off  ] == (byte) (v >>> 24) &&
                bytes[off+1] == (byte) (v >>> 16) &&
                bytes[off+2] == (byte) (v >>> 8) &&
                bytes[off+3] == (byte)  v;
    }

    @SameHead
    public static void setLong(byte[] bytes, int off, long v) {
        bytes[off  ] = (byte) (v >>> 56);
        bytes[off+1] = (byte) (v >>> 48);
        bytes[off+2] = (byte) (v >>> 40);
        bytes[off+3] = (byte) (v >>> 32);
        bytes[off+4] = (byte) (v >>> 24);
        bytes[off+5] = (byte) (v >>> 16);
        bytes[off+6] = (byte) (v >>> 8 );
        bytes[off+7] = (byte)  v;
    }

    @SameHead
    public static long getLong(byte[] bytes, int off) {
        return  ((long) (bytes[off  ] & 0xFF) << 56) |
                ((long) (bytes[off+1] & 0xFF) << 48) |
                ((long) (bytes[off+2] & 0xFF) << 40) |
                ((long) (bytes[off+3] & 0xFF) << 32) |
                ((long) (bytes[off+4] & 0xFF) << 24) |
                (       (bytes[off+5] & 0xFF) << 16) |
                (       (bytes[off+6] & 0xFF) << 8 ) |
                (       (bytes[off+7] & 0xFF));
    }

    @SameHead
    public static boolean matchLong(byte[] bytes, int off, long v) {
        return  bytes[off  ] == (byte) (v >>> 56) &&
                bytes[off+1] == (byte) (v >>> 48) &&
                bytes[off+2] == (byte) (v >>> 40) &&
                bytes[off+3] == (byte) (v >>> 32) &&
                bytes[off+4] == (byte) (v >>> 24) &&
                bytes[off+5] == (byte) (v >>> 16) &&
                bytes[off+6] == (byte) (v >>> 8) &&
                bytes[off+7] == (byte)  v;
    }
}
