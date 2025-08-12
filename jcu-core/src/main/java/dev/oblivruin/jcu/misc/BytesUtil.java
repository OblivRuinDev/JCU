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
package dev.oblivruin.jcu.misc;

import dev.oblivruin.jcu.builds.api.shadow.SameHead;
import dev.oblivruin.jcu.builds.api.shadow.Shadow;

/**
 * A shadow class, have another version in JDK 9.
 */
@Shadow
public final class BytesUtil {
    @SameHead
    private BytesUtil() {}

    @SameHead
    public static void setShort(byte[] bytes, int off, int v) {
        bytes[off] = (byte) (v >>> 8);
        bytes[++off] = (byte) v;
    }

    @SameHead
    public static void setShort(byte[] bytes, int off, short v) {
        bytes[off] = (byte) (v >>> 8);
        bytes[++off] = (byte) v;
    }

    @SameHead
    public static int getShort(byte[] bytes, int off) {
        return (bytes[off] << 8) | bytes[++off];
    }

    @SameHead
    public static int getU2(byte[] bytes, int off) {
        return getShort(bytes, off) & 0xFFFF;
    }

    @SameHead
    public static void setInt(byte[] bytes, int off, int v) {
        bytes[  off] = (byte) (v >>> 24);
        bytes[++off] = (byte) (v >>> 16);
        bytes[++off] = (byte) (v >>> 8 );
        bytes[++off] = (byte)  v;
    }

    @SameHead
    public static int getInt(byte[] bytes, int off) {
        return  (bytes[  off] << 24) |
                (bytes[++off] << 16) |
                (bytes[++off] << 8 ) |
                (bytes[++off]);
    }

    @SameHead
    public static void setLong(byte[] bytes, int off, long v) {
        bytes[  off] = (byte) (v >>> 56);
        bytes[++off] = (byte) (v >>> 48);
        bytes[++off] = (byte) (v >>> 40);
        bytes[++off] = (byte) (v >>> 32);
        bytes[++off] = (byte) (v >>> 24);
        bytes[++off] = (byte) (v >>> 16);
        bytes[++off] = (byte) (v >>> 8 );
        bytes[++off] = (byte)  v;
    }

    @SameHead
    public static long getLong(byte[] bytes, int off) {
        return  ((long) bytes[  off] << 56) |
                ((long) bytes[++off] << 48) |
                ((long) bytes[++off] << 40) |
                ((long) bytes[++off] << 32) |
                (       bytes[++off] << 24) |
                (       bytes[++off] << 16) |
                (       bytes[++off] << 8 ) |
                (       bytes[++off]);
    }
}
