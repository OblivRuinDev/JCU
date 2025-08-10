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

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
// Ignore this import error as it will be handled by the compiler
import jdk.internal.vm.annotation.ForceInline;

/**
 * More efficient implementation in JDK9
 */
public final class BytesUtil {
    private BytesUtil() {}
    public static final VarHandle v2 = MethodHandles.byteArrayViewVarHandle(short[].class, ByteOrder.BIG_ENDIAN);
    public static final VarHandle v4 = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
    public static final VarHandle v8 = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);
    @ForceInline
    public static void putShort(byte[] bytes, int off, int v) {
        v2.set(bytes, off, (short) v);
    }
    @ForceInline
    public static void putShort(byte[] bytes, int off, short v) {
        v2.set(bytes, off, v);
    }
    @ForceInline
    public static int getShort(byte[] bytes, int off) {
        return (short) v2.get(bytes, off);
    }
    @ForceInline
    public static int getU2(byte[] bytes, int off) {
        return getShort(bytes, off) & 0xFFFF;
    }
    @ForceInline
    public static void putInt(byte[] bytes, int off, int v) {
        v4.set(bytes, off, v);
    }
    @ForceInline
    public static int getInt(byte[] bytes, int off) {
        return (int) v4.get(bytes, off);
    }
    @ForceInline
    public static void putLong(byte[] bytes, int off, long v) {
        v8.set(bytes, off, v);
    }
    @ForceInline
    public static long getLong(byte[] bytes, int off) {
        return (long) v8.get(bytes, off);
    }
}
