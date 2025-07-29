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

import java.nio.charset.StandardCharsets;

import static dev.oblivruin.jcu.constant.Tag.*;

public class ClassFileReader implements IConstantPool {
    // Stable Member Declare Start
    /**
     *
     */
    protected final int[] cpInfo;
    protected final byte[] bytes;
    public final int maxStrLen;
    protected char[] buffer = null;
    protected final String[] utf8Cache;

    public ClassFileReader(byte[] bytes) {
        this.bytes = bytes;
        int cCount = ((bytes[8] << 8) | bytes[9]);
        int maxStrLen = 20;
        int[] array = new int[cCount];
        array[0] = 0;
        int off = 10;
        for (int index = 1; index < cCount; ++index) {
            array[index] = ++off;
            switch (bytes[off]) {
                case Utf8:
                    int len = readU2(++off);
                    if (len > maxStrLen) {
                        maxStrLen = len;
                    }
                    off+=len;
                    ++off;
                    break;
                case Long:
                case Double:
                    off+=8;
                    array[++index] = 0;// not available
                    break;
                case Integer:
                case Float:
                case Fieldref:
                case Methodref:
                case InterfaceMethodref:
                case NameAndType:
                case Dynamic:
                case InvokeDynamic:
                    off+=4;
                    break;
                case MethodHandle:
                    off+=3;
                    break;
                case Class:
                case String:
                case MethodType:
                case Module:
                case Package:
                    off+=2;
                    break;
            }
        }
        this.maxStrLen = maxStrLen;
        this.cpInfo = array;
        int s = 0;
        for (int index = cCount - 1; index > 0; --index) {
            if (bytes[array[index]] == String) {
                s = ++index;
                break;
            }
        }
        utf8Cache = new String[s];
    }

    @Override
    public final int count() {
        return cpInfo.length;
    }

    /**
     * {@inheritDoc}
     * @param index {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException
     */
    @Override
    public final int tag(int index) {
        return bytes[cpInfo[index] - 1];
    }

    @Override
    public int findInt(int value) {
        return findU5(Integer, value);
    }

    @Override
    public int findFloat(float value) {
        return findU5(Float, java.lang.Float.floatToIntBits(value));
    }

    @Override
    public int findU5(int tag, int data) {
        return 0;
    }

    @Override
    public String utf8V(int index) {
        java.lang.String str = utf8Cache[index];
        if (str != null) {
            return str;
        } else {
        }
    }

    protected final String readUtf8(int off, int len) {
        for (int index = off, end = off + len; index < end; ++index) {
            byte b = bytes[index];
            if ((b & 0x40) != 0) {// 7th bit is 1, which means that it isn't one-byte-char
                if (buffer == null) {
                    buffer = new char[maxStrLen];
                }
            }
        }
        return new String(bytes, off, len, StandardCharsets.ISO_8859_1);
    }

    @Override
    public final int intV(int index) {
        return readU4(cpInfo[index]);
    }

    @Override
    public final float floatV(int index) {
        return java.lang.Float.intBitsToFloat(intV(index));
    }

    @Override
    public final long longV(int index) {
        return readU8(cpInfo[index]);
    }

    @Override
    public final double doubleV(int index) {
        return java.lang.Double.longBitsToDouble(longV(index));
    }

    @Override
    public final int ref1Index(int index) {
        return readU2(cpInfo[index]);
    }

    @Override
    public final int ref2Index1(int index) {
        return ref1Index(index);
    }

    @Override
    public final int ref2Index2(int index) {
        return readU2(cpInfo[index] + 2);
    }

    @Override
    public final int ref2Indexes(int index) {
        return intV(index);// can be treated as a CONSTANT_Integer
    }

    @Override
    public final int methodHandleIndex(int index) {
        return readU2(cpInfo[index] + 1);
    }

    @Override
    public final int methodHandleKind(int index) {
        return bytes[cpInfo[index]];
    }

    @Override
    public final int methodHandleKindAndRef(int index) {
        return (methodHandleKind(index) >>> 16) | methodHandleIndex(index);
    }

    public final int readU2(int index) {
        return (bytes[index] << 8) | bytes[++index];
    }

    public final int readU4(int index) {
        return  (bytes[  index] << 24) |
                (bytes[++index] << 16) |
                (bytes[++index] << 8 ) |
                 bytes[++index];
    }

    public final long readU8(int index) {
        return ((long) readU4(index) << 32) | readU4(index + 4);
    }
}
