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
import dev.oblivruin.jcu.misc.ByteArray;
import dev.oblivruin.jcu.misc.BytesUtil;
import dev.oblivruin.jcu.misc.IntArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides low-level primitives for interacting with class bytecodes.
 * <br>
 * This class only ensures that correct API calls are executed successfully.
 * For uncorrected API callings, exception and data corruption may occur.
 * <br>
 * About unsafe APIs, all of them have API Contract in their javadoc.
 * <p><b>Performance suggestion:</b>
 * <ul>
 *     <li> Cache the return value which call {@code find<i>XXX</i>} and declared
 *     in {@link IConstantPool} due to linear-time iterative searches.</li>
 *     <li>Some attribute names(e.g., "Code", "RuntimeVisibleAnnotation") have their
 *     utf8 indexes cached in {@link dev.oblivruin.jcu.util.AttrStrMap}, just call {@code attrMap.<i>AttributeName</i>()} to get value.</li>
 * </ul>
 * </p>
 * @author OblivRuinDev
 */
@SuppressWarnings("JavadocReference")
public class ClassFileWriter implements IRawClassVisitor, IConstantPool {//todo: hash all constant
    /**
     * Point to every constant tag position in {@link #head}.
     * @apiNote for unusable slot, just pus {@code 0}, which the tag value is -54 in {@code head.data}.
     */
    protected final IntArray cpInfo = new IntArray();

    /**
     * The data structure is:
     * <pre class="screen">
     * u4       magic;
     * u2       minor_version;
     * u2       major_version;
     * u2       constant_pool_count;
     * cp_info  constant_pool[constant_pool_count-1];</pre>
     */
    protected final ByteArray head = new ByteArray() {
        @Override
        protected int newSize(int expected) {
            return Math.max(expected, this.length > 10000 ? this.data.length + 4800 : this.data.length * 2);// avoid data inflation
        }
    };
    /**
     * The data structure is:
     * <pre class="screen">
     * u2           access_flags;
     * u2           this_class;
     * u2           super_class;
     * u2           interfaces_count;
     * u2           interfaces[interfaces_count];
     * u2           fields_count;
     * field_info   fields[fields_count];</pre>
     */
    protected final ByteArray body = new ByteArray(75);
    /** The value of {@code fields_count}. */
    protected int countF = 0;
    /** The value of {@code methods_count}. */
    protected int countM = 0;
    /**
     * The data structure is:
     * <pre class="screen">
     * method_info   methods[methods_count];</pre>
     */
    protected final ByteArray meth = new ByteArray(0) {
        @Override
        protected int newSize(int expected) {
            return this.length == 0 ? 100 : super.newSize(expected);
        }
    };
    /** The value of {@code attributes_count}. */
    protected int countA = 0;
    /**
     * The data structure is:
     * <pre class="screen">
     * attribute_info  attributes[attribute_count];</pre>
     */
    protected final ByteArray attr = new ByteArray(100);

    protected final HashMap<String, Integer> utf8Map = new HashMap<>();

    {
        // magic number
        BytesUtil.setInt(head.data, 0, 0xCAFEBABE);
        // skip  minor, major, and constant pool count
        head.length = 10;

        cpInfo.data[0] = -1;
        cpInfo.length = 1;
    }

    @Override
    public final int count() {
        return cpInfo.length;
    }

    /**
     * Returns the tag byte of the constant pool entry at the specified index,
     * or a negative value to indicate an invalid constant slot.
     *
     * @param index {@inheritDoc}
     * @return {@inheritDoc}, or 0 if index is between {@code head.length}(inclusive) and {@code head.data.length}(exclusive),
     * or -54 if it is an unusable slot.
     * @throws IndexOutOfBoundsException if index is outside or equal to {@code head.data.length}
     */
    @Override
    public final int tag(int index) {
        return head.data[cpInfo.data[index]];
    }

    @Override
    public int findTag(int tag, int off) {
        byte[] cp = head.data;
        int[] cpInf = cpInfo.data;
        for (int len = cpInfo.length; off < len; ++off) {
            if (cp[cpInf[off]] == tag) {
                return len;
            }
        }
        return -1;
    }

    @Override
    public final int findUtf8(String value) {
        if (value == null) {
            return 0;
        }
        Integer v = utf8Map.get(value);
        if (v != null) {
            return v;
        }
        return createUtf8(value);
    }

    /**
     * Create a {@code CONSTANT_Utf8_info} at the tail of the constant pool.
     * @param value utf8 value (must not null)
     * @return the index where the utf8 constant is placed
     */
    protected final int createUtf8(String value) {
        int pos = head.length;
        // reserve for tag and length
        head.length+=3;
        int len = head.writeUtf8(value);
        if (len > 65535) {
            throw new Utf8TooLongException(cpInfo.length, value, len);
        }
        byte[] bytes = head.data;
        bytes[pos] = Tag.Utf8;
        BytesUtil.setUShort(bytes, pos + 1, len);
        // reuse variable
        len = cpInfo.length;
        cpInfo.add(pos);
        utf8Map.put(value, len);
        return len;
    }

    @Override
    public final int findInt(int value) {
        return findC5(Tag.Integer, value);
    }

    @Override
    public final int findFloat(float value) {
        return findC5(Tag.Float, Float.floatToIntBits(value));
    }

    @Override
    public final int findC5(int tag, int value) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] cpInf = cpInfo.data;
        int pos;
        while (--index > 0) {
            if (h[pos = cpInf[index]] == tag) {
                if (BytesUtil.matchInt(h, pos + 1, value)) {
                    return index;
                }
            }
        }
        return createC5(tag, value);
    }

    /**
     * Create a constant with a fixed-length of 5 (include the space of {@code tag}) at the tail of the constant pool.
     * @param value 4-bytes value
     * @return the index where the constant is placed
     */
    protected final int createC5(int tag, int value) {
        head.ensureFree(5);
        int pos = head.length;
        cpInfo.add(pos);
        head.data[pos] = (byte) tag;
        BytesUtil.setInt(head.data, pos + 1, value);
        head.length+=5;
        return cpInfo.length - 1;
    }

    @Override
    public final int findLong(long value) {
        return findC9(Tag.Long, value);
    }

    @Override
    public final int findDouble(double value) {
        return findC9(Tag.Double, Double.doubleToLongBits(value));
    }

    @Override
    public final int findC9(int tag, long value) {
        int index = cpInfo.length;
        byte[] data = head.data;
        int[] cpInf = cpInfo.data;
        int pos;
        while (--index > 0) {
            if (data[pos = cpInf[index]] == tag && BytesUtil.matchLong(data, pos + 1, value)) {
                return index;
            }
        }
        return createC9(tag, value);
    }

    /**
     * Create a constant with a fixed-length of 9 (include the space of {@code tag}) at the tail of the constant pool.
     * @param value 8-bytes value
     * @return the index where the constant is placed
     */
    protected final int createC9(int tag, long value) {
        head.ensureFree(9);
        int pos = head.length;
        cpInfo.add(pos);
        cpInfo.add(0);// unusable slot
        byte[] data = head.data;
        data[pos] = (byte) tag;
        BytesUtil.setLong(data, pos + 1, value);
        head.length+=9;
        return cpInfo.length - 2;
    }

    @Override
    public final int findRef1(int tag, int refIndex) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] cpInf = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = cpInf[index];
            if (h[pos] == tag &&
                    BytesUtil.getShort(h, pos + 1) == refIndex) {
                return index;
            }
        }
        return createRef1(tag, refIndex);
    }

    protected final int createRef1(int tag, int refIndex) {
        head.ensureFree(3);
        int pos = head.length;
        cpInfo.add(pos);
        byte[] data = head.data;
        data[pos] = (byte) tag;
        BytesUtil.setUShort(data, pos + 1, refIndex);
        head.length+=3;
        return cpInfo.length - 1;
    }

    @Override
    public final int findRef2(int tag, int refIndex1, int refIndex2) {
        return findC5(tag, (refIndex1 << 16) | (refIndex2 & 0xFFFF));
    }

    @Override
    public final int findMethodHandle(int kind, int refIndex) {
        int index = cpInfo.length;
        byte[] data = head.data;
        int[] cpInf = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = cpInf[index];
            if (data[pos] == Tag.MethodHandle &&
                    data[pos+1] == kind &&
                    BytesUtil.matchUShort(data, pos + 2, refIndex)) {
                return index;
            }
        }
        return createMethodHandle(kind, refIndex);
    }

    protected final int createMethodHandle(int kind, int refIndex) {
        head.ensureFree(4);
        int pos = head.length;
        cpInfo.add(pos);
        byte[] data = head.data;
        data[pos] = (byte) Tag.MethodHandle;
        data[pos + 1] = (byte) kind;
        BytesUtil.setUShort(data, pos + 2, refIndex);
        head.length+=4;
        return cpInfo.length - 1;
    }

    @Override
    public final String utf8V(int index) {
        Integer v = index;
        for (Map.Entry<String, Integer> entry : utf8Map.entrySet()) {
            if (v.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public final int intV(int index) {
        return BytesUtil.getInt(head.data, cpInfo.data[index] + 1);
    }

    @Override
    public final float floatV(int index) {
        return Float.intBitsToFloat(intV(index));
    }

    @Override
    public final long longV(int index) {
        return BytesUtil.getLong(head.data, cpInfo.data[index] + 1);
    }

    @Override
    public final double doubleV(int index) {
        return Double.longBitsToDouble(longV(index));
    }

    @Override
    public final IRef1 ref1V(int index) {
        return new Ref1(cpInfo.data[index]);
    }

    abstract class Constant implements IConstant {
        final int off;

        Constant(int off) {
            this.off = off;
        }

        @Override
        public final int tag() {
            return head.data[off];
        }
    }

    final class Ref1 extends Constant implements IRef1 {
        Ref1(int off) {
            super(off);
        }

        @Override
        public int refIndex() {
            return (head.data[off + 1] << 8) | (head.data[off + 2]);
        }
    }

    final class Ref2 extends Constant implements IRef2 {
        Ref2(int off) {
            super(off);
        }

        @Override
        public int refIndex1() {
            return (head.data[off + 1] << 8) | (head.data[off + 2]);
        }

        @Override
        public int refIndex2() {
            return (head.data[off + 3] << 8) | (head.data[off + 4]);
        }
    }

    @Override
    public final IRef2 ref2V(int index) {
        return new Ref2(index);
    }

    @Override
    public final IMethodHandle methodHandleV(int index) {
        return new IMethodHandle() {
            final int off = cpInfo.data[index];
            @Override
            int kind() {
                return head.data[off + 1];
            }

            @Override
            int refIndex() {
                return (head.data[off + 2] << 8) | (head.data[off + 3]);
            }
        };
    }

    @Override
    public final int ref1Index(int index) {
        return BytesUtil.getUShort(head.data, cpInfo.data[index] + 1);
    }

    @Override
    public final int ref2Index1(int index) {
        return ref1Index(index);
    }

    @Override
    public final int ref2Index2(int index) {
        return BytesUtil.getUShort(head.data, cpInfo.data[index] + 3);
    }

    @Override
    public final int ref2Indexes(int index) {
        return intV(index);
    }

    @Override
    public final int methodHandleIndex(int index) {
        return BytesUtil.getUShort(head.data, cpInfo.data[index] + 2);
    }

    @Override
    public final int methodHandleKind(int index) {
        return head.data[cpInfo.data[index] + 1];
    }

    @Override
    public final int methodHandleKindAndRef(int index) {
        return (methodHandleKind(index) << 16) | methodHandleIndex(index);
    }

    /**
     * @implSpec Must execute {@code posF = $var; body.length = 2 + $var;}.
     */
    @Override
    public final void visit(int version, int access, int thisCIndex, int superCIndex, int[] interfaceCIndexes) {
        // body.length is 0
        if (interfaceCIndexes == null || interfaceCIndexes.length == 0) {
            //BytesUtil.setUShort(body.data, 6, 0);
            body.length = 10;
        } else {
            int inteCount = interfaceCIndexes.length;
            body.ensureFree(8 + 2*inteCount);
            byte[] d = body.data;
            d[6] = (byte) (inteCount >>> 8);
            d[7] = (byte) inteCount;
            int pointer = 7;
            for (int v : interfaceCIndexes) {
                d[++pointer] = (byte) (v >>> 8);
                d[++pointer] = (byte) v;
            }
            body.length = pointer + 3;
        }
        visitHead(version, access, thisCIndex, superCIndex);
    }

    protected final void visitHead(int ver, int access, int thisCIndex, int superCIndex) {
        BytesUtil.setInt(head.data, 4, ver);
        byte[] data = body.data;
        BytesUtil.setUShort(data, 0, access);
        BytesUtil.setUShort(data, 2, thisCIndex);
        BytesUtil.setUShort(data, 4, superCIndex);
    }

    @Override
    public final FieldWriter visitField(int access, int nameIndex, int descIndex) {
        ++countF;
        body.put222(access, nameIndex, descIndex);
        return new FieldWriter(body);
    }

    @Override
    public final MethodWriter visitMethod(int access, int nameIndex, int descIndex) {
        ++countM;
        meth.put222(access, nameIndex, descIndex);
        return new MethodWriter(meth);
    }

    @Override
    public final void visitAttribute(int nameIndex, int off, int len, byte[] data) {
        ++countA;
        attr.put24(nameIndex, len);
        attr.add(data, off, len);
    }

    @Override
    public final void visitAttribute(int nameIndex, int value) {
        ++countA;
        attr.put242(nameIndex, 2, value);
    }

    @Override
    public final void visitEmptyAttribute(int nameIndex) {
        ++countA;
        attr.put24(nameIndex, 0);
    }

    @Override
    public final AttributeWriter visitAttribute(int nameIndex) {
        ++countA;
        attr.put2(nameIndex);
        return new AttributeWriter(attr);
    }

    @Override
    public final CompAttributeWriter visitCompAttribute(int nameIndex) {
        ++countA;
        attr.put2(nameIndex);
        return new CompAttributeWriter(attr);
    }

    @Override
    public final void visitEnd() {
        //  Constant Count
        if (cpInfo.length > 65535) {
            throw new ConstantPoolOverflowException();
        }
        BytesUtil.setUShort(head.data, 8, cpInfo.length);
        // Field Count
        BytesUtil.setUShort(body.data, BytesUtil.getUShort(body.data, 6) * 2 + 8, countF);
    }

    public final byte[] toByteArray() {
        int off = head.length;
        int l2 = body.length;
        int l3 = meth.length;
        int l4 = attr.length;
        byte[] ret = new byte[off + l2 + l3 + 4/*for methods_count and attribute count*/ + l4];
        System.arraycopy(head.data, 0, ret, 0, off);
        System.arraycopy(body.data, 0, ret, off, l2);
        off+=l2;
        if (l3 == 0) {
            ret[off] = 0;
            ret[++off] = 0;
            ++off;
        } else {
            ret[off] = (byte) (countM >>> 8);
            ret[++off] = (byte) countM;
            System.arraycopy(meth.data, 0, ret, ++off, l3);// off = head.length + l2 + 2
            off+=l3;
        }
        if (l4 == 0) {
            ret[off] = 0;
            ret[++off] = 0;
        } else {
            ret[off] = (byte) (countA >>> 8);
            ret[++off] = (byte)  countA;
            System.arraycopy(attr.data, 0, ret, ++off, l4);// off = head.length + l2 + l3 + 4
        }
        return ret;
    }

    public final void writeTo(OutputStream output) throws IOException {
        output.write(head.data, 0, head.length);
        output.write(body.data, 0, body.length);
        if (countM == 0) {
            output.write(0);
            output.write(0);
        } else {
            output.write(countM >>> 8);
            output.write(countM);
            output.write(meth.data, 0, meth.length);
        }
        if (countA == 0) {
            output.write(0);
            output.write(0);
        } else {
            output.write(countA >>> 8);
            output.write(countA);
            output.write(attr.data, 0, attr.length);
        }
    }
}
