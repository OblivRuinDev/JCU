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

import dev.oblivruin.jcu.misc.ByteArray;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteOrder;

import static java.lang.invoke.MethodType.*;

/**
 * The extremely radical strategy for JDK 9 String.
 * <br>
 * Need module {@code java.base} open package {@code java.lang} to this.
 *
 * @author OblivRuinDev
 */
public final class Strings$$$$9 {
    private static final MethodHandle isLatin1;
    private static final MethodHandle getBytes;
    private static final MethodHandle toChars;
    public static final int HI;
    public static final int LO;
    static {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            HI = 8;
            LO = 0;
        } else {
            HI = 0;
            LO = 8;
        }
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(String.class, MethodHandles.lookup());
            isLatin1 = lookup.findVirtual(String.class, "isLatin1", methodType(boolean.class));
            getBytes = lookup.findGetter(String.class, "value", byte[].class);
            toChars = lookup.findStatic(Class.forName("java.lang.StringLatin1"),
                    "inflate",
                    methodType(void.class,
                            new Class[]{byte[].class, int.class, char[].class, int.class, int.class}));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static int write(String value, ByteArray array) throws Throwable {
        byte[] bytes = (byte[]) getBytes.invokeExact(value);
        if ((boolean) isLatin1.invokeExact(value)) {
            int b2 = 0;
            for (byte b : bytes) {
                if ((b & 0b1000_0000) != 0 || b == 0) {
                    ++b2;
                }
            }
            if (b2 == 0) {
                array.add(bytes);
                return bytes.length;
            } else {
                array.ensureFree(bytes.length + b2);
                byte[] dst = array.data;
                int pointer = array.length - 1;
                for (byte b : bytes) {
                    if ((b & 0b1000_0000) != 0) {
                        dst[++pointer] = (byte) (b >>> 6 | 0b1100_0000);
                        dst[++pointer] = (byte) (b       & 0b1011_1111);// 8th bit is 1
                    } else if (b == 0) {
                        dst[++pointer] = (byte) 0xC0;
                        dst[++pointer] = (byte) 0x80;
                    } else {
                        dst[++pointer] = b;
                    }
                }
                array.length = pointer + 1;//update length
                return bytes.length + b2;
            }
        } else {
            array.ensureFree(3*bytes.length);// pessimistic expansion strategy
            byte[] dst = array.data;
            int pointer = array.length - 1;
            int ch;
            for (int index = -1, len = bytes.length - 1; index < len;  ) {
                ch = ((bytes[++index] & 0xFF) << HI) | ((bytes[++index] & 0xFF) << LO);
                if (ch == 0) {
                    dst[++pointer] = (byte) 0xC0;
                    dst[++pointer] = (byte) 0x80;
                } else if (ch < 0x80) {
                    dst[++pointer] = (byte) ch;
                } else if (ch < 0x800) {
                    dst[++pointer] = (byte) (0xC0 | (ch >> 6));
                    dst[++pointer] = (byte) (0x80 | (ch & 0x3F));
                } else {
                    dst[++pointer] = (byte) (0xE0 | ( ch >> 12));
                    dst[++pointer] = (byte) (0x80 | ((ch >> 6) & 0x3F));
                    dst[++pointer] = (byte) (0x80 | ( ch       & 0x3F));
                }
            }
            int len = ++pointer - array.length;
            array.length = pointer;
            return len;
        }
    }
}
