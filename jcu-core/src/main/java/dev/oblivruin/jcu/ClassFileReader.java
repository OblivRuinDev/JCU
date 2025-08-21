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

import dev.oblivruin.jcu.constant.Tag;
import dev.oblivruin.jcu.misc.BytesUtil;
import jdk.internal.vm.annotation.Stable;

import java.nio.charset.StandardCharsets;
import java.util.function.IntFunction;

import static dev.oblivruin.jcu.constant.Tag.*;

/**
 * A low-level class file reader.
 *
 * @author OblivRuinDev
 */
public class ClassFileReader implements IConstantPool, IntFunction<char[]> {
    // Stable Member Declare Start
    /**
     * Point to each constant entry tag <b>plus 1</b>.
     * <br>
     * <b>DO NOT CHANGE ELEMENTS IN THIS ARRAY OR UNEXPECTED BEHAVIOR MAY OCCUR!!!</b>
     */
    @Stable
    protected final int[] cpInfo;
    /**
     * Class file bytes.
     * <br>
     * <b>DO NOT CHANGE ELEMENTS IN THIS ARRAY OR UNEXPECTED BEHAVIOR MAY OCCUR!!!</b>
     */
    @Stable
    protected final byte[] bytes;
    public final int maxStrLen;
    public final int header;
    public final int interfaceCount;
    protected char[] buffer = null;
    protected final String[] utf8Cache;
    /**
     * Position of first method.
     */
    protected int mPos = 0;
    /**
     * Position of first attribute.
     */
    protected int aPos = 0;

    /**
     * Construct a new ClassFileReader
     * @param bytes trusted class file bytes,
     *             <b>DO NOT CHANGE ELEMENTS IN THIS ARRAY OR UNEXPECTED BEHAVIOR MAY OCCUR!!!</b>
     */
    public ClassFileReader(byte[] bytes) {
        this.bytes = bytes;
        int cCount = readU2(8);
        int maxStrLen = 20;
        int[] array = new int[cCount];
        array[0] = 0;
        int off = 10;
        for (int index = 1; index < cCount; ++index) {
            array[index] = off + 1;
            switch (bytes[off]) {
                case Utf8:
                    int len = readU2(++off);
                    if (len > maxStrLen) {
                        maxStrLen = len;
                    }
                    off+=(len + 2);
                    break;
                case Long:
                case Double:
                    off+=9;
                    array[++index] = 1;// not available
                    break;
                case Integer:
                case Float:
                case Fieldref:
                case Methodref:
                case InterfaceMethodref:
                case NameAndType:
                case Dynamic:
                case InvokeDynamic:
                    off+=5;
                    break;
                case MethodHandle:
                    off+=4;
                    break;
                case Class:
                case String:
                case MethodType:
                case Module:
                case Package:
                    off+=3;
                    break;
            }
        }
        this.header = off;
        this.interfaceCount = readU2(off + 6);
        this.maxStrLen = maxStrLen;
        this.cpInfo = array;
        int s = 0;
        for (int index = cCount - 1; index > 0; --index) {
            if (bytes[array[index] - 1] == Utf8) {
                s = ++index;
                break;
            }
        }
        utf8Cache = new String[s];
    }

    @Override
    public int findUtf8(String value) {
        if (value == null) {
            return 0;
        }
        int index = 0;
        while ((index = findTag(String, ++index)) != -1) {
            java.lang.String str = utf8Cache[index];
            if (value.equals(str != null ? str : (utf8Cache[index] = utf8V(index)))) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public final int findLong(long value) {
        return this.findC9(Tag.Long, value);
    }

    @Override
    public final int findDouble(double value) {
        return this.findC9(Tag.Double, java.lang.Double.doubleToLongBits(value));
    }

    @Override
    public int findC9(int tag, long data) {
        int index = 0;
        while ((index = findTag(tag, ++index)) != -1) {
            if (data == BytesUtil.getLong(bytes, cpInfo[index])) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int findRef1(int tag, int refIndex) {
        int index = 0;
        while ((index = findTag(tag, ++index)) != -1) {
            if (refIndex == readU2(cpInfo[index])) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int findRef2(int tag, int refIndex1, int refIndex2) {
        return findC5(tag, (refIndex1 << 8) | refIndex2);
    }

    @Override
    public int findMethodHandle(int kind, int refIndex) {
        int index = 0;
        while ((index = findTag(MethodHandle, ++index)) != -1) {
            if (bytes[cpInfo[index]] == kind && refIndex == readU2(cpInfo[index] + 1)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public IRef1 ref1V(int index) {
        throw new UnsupportedOperationException();//todo
    }

    @Override
    public IRef2 ref2V(int index) {
        throw new UnsupportedOperationException();//todo
    }

    @Override
    public IMethodHandle methodHandleV(int index) {
        throw new UnsupportedOperationException();//todo
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
    public int findTag(int tag, int off) {
        for (int len = cpInfo.length; off < len; ++off) {
            if (bytes[cpInfo[off] - 1] == tag) {
                return len;
            }
        }
        return -1;
    }

    @Override
    public int findInt(int value) {
        return findC5(Integer, value);
    }

    @Override
    public int findFloat(float value) {
        return findC5(Float, java.lang.Float.floatToIntBits(value));
    }

    /**
     * May return -1 if don't find.
     * <br>
     * {@inheritDoc}
     * @param tag {@inheritDoc}
     * @param data {@inheritDoc}
     * @return {@inheritDoc}, may return -1 if don't find.
     */
    @Override
    public int findC5(int tag, int data) {
        int index = 0;
        while ((index = findTag(tag, ++index)) != -1) {
            if (data == readInt(cpInfo[index])) {
                return index;
            }
        }
        return -1;
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public String utf8V(int index) {
        java.lang.String str = utf8Cache[index];
        if (str != null) {
            return str;
        } else {
            return utf8Cache[index] = readUtf8((index = cpInfo[index]) + 2, readU2(index));
        }
    }

    protected final String readUtf8(int off, int len) {
        byte[] bytes = this.bytes;
        for (int index = off, end = off + len; index < end; ++index) {
            if ((bytes[index] & 0b1000_0000) != 0) {
                char[] buffer = this.buffer == null ?
                        this.buffer = new char[maxStrLen] :
                        this.buffer;
                int pointer = -1;
                for (; off < index; ++off) {
                    buffer[++pointer] = (char) bytes[off];
                }
                byte b;
                for (; off < end; ++off) {
                    b = bytes[off];
                    buffer[++pointer] =
                            ((b & 0b1000_0000) != 0) ?
                                    (((b & 0b0010_0000) != 0) ?
                                            (char)    (((b & 0b0000_1111) << 12) |
                                            ((bytes[++off] & 0b0011_1111) << 6 ) |
                                             (bytes[++off] & 0b0011_1111))
                                            :  (char) (((b & 0b0001_1111) << 6) |
                                             (bytes[++off] & 0b0011_1111)))
                                    : (char) b;
                }
                return new String(buffer, 0, ++pointer);
            }
        }// one-byte char
        return new String(bytes, off, len, StandardCharsets.ISO_8859_1);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int intV(int index) {
        return readInt(cpInfo[index]);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final float floatV(int index) {
        return java.lang.Float.intBitsToFloat(intV(index));
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final long longV(int index) {
        return BytesUtil.getLong(bytes, cpInfo[index]);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final double doubleV(int index) {
        return java.lang.Double.longBitsToDouble(longV(index));
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int ref1Index(int index) {
        return readU2(cpInfo[index]);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int ref2Index1(int index) {
        return ref1Index(index);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int ref2Index2(int index) {
        return readU2(cpInfo[index] + 2);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int ref2Indexes(int index) {
        return intV(index);// can be treated as a CONSTANT_Integer
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int methodHandleIndex(int index) {
        return readU2(cpInfo[index] + 1);
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int methodHandleKind(int index) {
        return bytes[cpInfo[index]];
    }

    /** @throws IndexOutOfBoundsException {@inheritDoc}*/
    @Override
    public final int methodHandleKindAndRef(int index) {
        return readInt(cpInfo[index] - 1) & 0xFF_FFFF;
    }

    public void copyCPTo(IConstantPool cp) {
        for (int index = 1, len = cpInfo.length; index < len; ++index) {
            int tag;
            int off;
            switch (tag = bytes[(off = cpInfo[index]) - 1]) {
                case Utf8:
                    cp.findUtf8(utf8V(index));
                    break;
                case Long:
                case Double:
                    cp.findC9(tag, BytesUtil.getLong(bytes, off));
                    ++index;
                    break;
                case Integer:
                case Float:
                case Fieldref:
                case Methodref:
                case InterfaceMethodref:
                case NameAndType:
                case Dynamic:
                case InvokeDynamic:
                    cp.findC5(tag, readInt(off));
                    break;
                case MethodHandle:
                    cp.findMethodHandle(bytes[off], readU2(off + 1));
                    break;
                case Class:
                case String:
                case MethodType:
                case Module:
                case Package:
                    cp.findRef1(tag, readU2(off));
                    break;
            }
        }
    }

    public void accept(IRawClassVisitor rawClassVisitor) {
        int[] array;
        int off = header + 7;
        if (interfaceCount > 0) {
            array = new int[interfaceCount];
            for (int index = 0; index < interfaceCount; ++index) {
                array[index] = (bytes[++off] << 8) | bytes[++off];
            }
        } else {
            array = null;
        }
        ++off;
        rawClassVisitor.visit(readInt(4), readU2(header), readU2(header + 2), readU2(header + 4), array);
        int count0 = readU2(off);//field count
        off+=2;
        while (count0 > 0) {
            off = acceptAttributes(rawClassVisitor.visitField(readU2(off), readU2(off + 2), readU2(off + 4)), off + 6);
            --count0;
        }
        count0 = readU2(off);//method
        off+=2;
        while (count0 > 0) {
            off = acceptAttributes(rawClassVisitor.visitMethod(readU2(off), readU2(off + 2), readU2(off + 4)), off + 6);
            --count0;
        }
        acceptAttributes(rawClassVisitor, off);// implicit  visitEnd()
    }

    /**
     * Visit attributes structure on given offset.
     *
     * @param attributable visitor
     * @param off point to {@code attributes_count}
     */
    protected int acceptAttributes(IRawAttributable attributable, int off) {
        int count = readU2(off);
        off+=2;
        for (; count > 0; --count) {
            int len;
            attributable.visitAttribute(readU2(off), off + 6, (len = readInt(off + 2)), bytes);
            off = off + len + 6;
        }
        attributable.visitEnd();
        return off;
    }

    public final int fieldPos() {
        return header + 8 + interfaceCount*2;
    }

    // bytes reader
    public final int readU2(int index) {
        return BytesUtil.getUShort(bytes, index);
    }

    public final int readInt(int index) {
        return BytesUtil.getInt(bytes, index);
    }

    @Override
    public char[] apply(int value) {
        if (buffer == null) {
            return buffer = new char[Math.max(maxStrLen, value)];
        } else if (buffer.length >= value) {
            return buffer;
        } else {
            return buffer = new char[value];
        }
    }
}
