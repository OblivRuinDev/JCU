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

import dev.oblivruin.jcu.constant.AttributeNames;
import dev.oblivruin.jcu.constant.Tag;
import dev.oblivruin.jcu.util.ByteArray;
import dev.oblivruin.jcu.util.IntArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides low-level primitives for interacting with class bytecodes.
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
 *     utf8 indexes cached in {@link #attrMap}, just call {@code attrMap.<i>AttributeName</i>()} to get value.</li>
 * </ul>
 * </p>
 * @author OblivRuinDev
 */
public class ClassFileWriter implements IRawClassVisitor, IConstantPool {//todo: hash all constant
    /**
     * Point to every constant tag position in {@link #head}.
     * @apiNote for unfree slot, just use {@code 0}, which the tag value is -54.
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
            return Math.max(expected, this.length > 10000 ? this.data.length + 3200 : this.data.length * 2);// avoid data inflation
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

    /**
     * The data structure is:
     * <pre class="screen">
     * method_info   methods[methods_count];</pre>
     */
    protected final ByteArray meth = new ByteArray(0);

    /**
     * The data structure is:
     * <pre class="screen">
     * attribute_info  attributes[attribute_count];</pre>
     */
    protected final ByteArray attr = new ByteArray(100);

    /**
     * Cache for frequently used attribute name indexes in the constant pool.
     */
    public final AttrStrMap attrMap = new AttrStrMap();
    protected final HashMap<String, Integer> utf8Map = new HashMap<>();

    {
        // magic number
        head.set4(0, 0xCAFEBABE);
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
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public final int tag(int index) {
        return head.data[cpInfo.data[index]];
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
        int pos = head.length;
        // reserve for tag and length
        head.length+=3;
        int len = head.writeUtf8(value);
        if (len > 65535) {
            throw new IndexOutOfBoundsException("Utf8 cannot long than 65535!");
        }
        byte[] bytes = head.data;
        bytes[pos] = Tag.Utf8;
        bytes[pos + 1] = (byte) (len >>> 8);
        bytes[pos + 2] = (byte)  len;
        // reuse variable
        len = cpInfo.length;
        cpInfo.add(pos);
        utf8Map.put(value, len);
        return len;
    }

    @Override
    public final int findInt(int value) {
        return findU5(Tag.Integer, value);
    }

    @Override
    public final int findFloat(float value) {
        return findU5(Tag.Float, Float.floatToIntBits(value));
    }

    @Override
    public final int findU5(int tag, int value) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] c = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = c[index];
            if (h[pos] == tag) {
                if (head.match4(pos + 1, value)) {
                    return index;
                }
            }
        }
        if (head.tryExpand(5)) {
            h = head.data;
        }
        index = cpInfo.length;
        pos = head.length++;
        cpInfo.add(pos);
        h[pos] = (byte) tag;
        head.put4_(value);
        return index;
    }

    @Override
    public final int findLong(long value) {
        return findU9(Tag.Long, value);
    }

    @Override
    public final int findDouble(double value) {
        return findU9(Tag.Double, Double.doubleToLongBits(value));
    }

    @Override
    public final int findU9(int tag, long value) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] c = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = c[index];
            if (h[pos] == tag && head.match8(pos + 1, value)) {
                return index;
            }
        }
        if (head.tryExpand(9)) {
            h = head.data;
        }
        pos = head.length++;
        cpInfo.add(pos);
        cpInfo.add(0);// unavailable
        h[pos] = (byte) tag;
        head.put8_(value);
        return cpInfo.length - 1;
    }

    @Override
    public final int findRef1(int tag, int refIndex) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] c = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = c[index];
            if (h[pos] == tag &&
                    h[pos + 1] == (byte) (refIndex >>> 8) &&
                    h[pos + 2] == (byte) refIndex) {
                return index;
            }
        }
        if (head.tryExpand(3)) {
            h = head.data;
        }
        pos = head.length;
        cpInfo.add(pos);
        h[pos] = (byte) tag;
        h[++pos] = (byte) (refIndex >>> 8);
        h[++pos] = (byte) refIndex;
        head.length = ++pos;
        return cpInfo.length - 1;
    }

    @Override
    public final int findRef2(int tag, int refIndex1, int refIndex2) {
        return findU5(tag, (refIndex1 << 16) | (refIndex2 & 0xFFFF));
    }

    @Override
    public final int findMethodHandle(int kind, int refIndex) {
        int index = cpInfo.length;
        byte[] h = head.data;
        int[] c = cpInfo.data;
        int pos;
        while (--index > 0) {
            pos = c[index];
            if (h[pos] == Tag.MethodHandle &&
                    h[++pos] == kind &&
                    h[++pos] == (byte) (refIndex >>> 8) &&
                    h[++pos] == (byte) refIndex) {
                return index;
            }
        }
        if (head.tryExpand(4)) {
            h = head.data;
        }
        index = cpInfo.length;
        pos = head.length;
        cpInfo.add(pos);
        h[  pos] = (byte) Tag.MethodHandle;
        h[++pos] = (byte) kind;
        h[++pos] = (byte) (refIndex >>> 8);
        h[++pos] = (byte) refIndex;
        head.length = pos + 1;
        return index;
    }

    @Override
    public final String utf8V(int index) {
        for (Map.Entry<String, Integer> entry : utf8Map.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public final int intV(int index) {
        return head.u4(cpInfo.data[index] + 1);
    }

    @Override
    public final float floatV(int index) {
        return Float.intBitsToFloat(intV(index));
    }

    @Override
    public final long longV(int index) {
        return head.u8(cpInfo.data[index] + 1);
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
        return head.u2(cpInfo.data[index] + 1);
    }

    @Override
    public final int ref2Index1(int index) {
        return head.u2(cpInfo.data[index] + 1);
    }

    @Override
    public final int ref2Index2(int index) {
        return head.u2(cpInfo.data[index] + 3);
    }

    @Override
    public final int ref2Indexes(int index) {
        return intV(index);
    }

    @Override
    public final int methodHandleIndex(int index) {
        return head.u2(cpInfo.data[index] + 2);
    }

    @Override
    public final int methodHandleKind(int index) {
        return head.data[cpInfo.data[index]];
    }

    @Override
    public final int methodHandleKindAndRef(int index) {
        return (methodHandleKind(index) << 16) | methodHandleIndex(index);
    }

    @Override
    public final void visit(int version, int access, int thisCIndex, int superCIndex, int[] interfaceCIndexes) {
        // body.length is 0
        if (interfaceCIndexes == null || interfaceCIndexes.length == 0) {
            body.set2(6, 0);
            posF =
                    (body.length = 6);
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
            posF =
                    (body.length = ++pointer);
        }
        h_(version, access, thisCIndex, superCIndex);
    }

    protected final void h_(int ver, int access, int thisCIndex, int superCIndex) {
        head.set4(4, ver);
        body.set2(0, access);
        body.set2(2, thisCIndex);
        body.set2(4, superCIndex);
    }

    //@Stable
    protected int posF;
    // Internal counter
    protected int countF = 0;
    protected int countM = 0;
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

    protected int countA = 0;

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
        head.set2(8, cpInfo.length);//  Constant Count
        body.set2(posF, countF);// Field Count
    }

    public final byte[] toByteArray() {
        int l1 = head.length;
        int l2 = body.length;
        int l3 = meth.length;
        int l4 = attr.length;
        byte[] ret = new byte[l1 + l2 + l3 + 4/*for methods_count and attribute count*/ + l4];
        System.arraycopy(head.data, 0, ret, 0, l1);
        System.arraycopy(body.data, 0, ret, l1, l2);
        l1+=l2;
        if (l3 == 0) {
            ret[l1] = 0;
            ret[++l1] = 0;
            ++l1;
        } else {
            ret[l1] = (byte) (countM >>> 8);
            ret[++l1] = (byte) countM;
            System.arraycopy(meth.data, 0, ret, ++l1, l3);
            l1+=l3;
        }
        if (l4 == 0) {
            ret[l1] = 0;
            ret[++l1] = 0;
        } else {
            ret[l1] = (byte) (countA >>> 8);
            ret[++l1] = (byte)  countA;
            System.arraycopy(attr.data, 0, ret, ++l1, l4);
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

    public final class AttrStrMap {
        // Special naming to reuse CONSTANT_Utf8 to reduce the size of ConstantPool
        int ConstantValue = 0;
        public int ConstantValue() {return ConstantValue == 0 ? ConstantValue = findUtf8(AttributeNames.ConstantValue) : ConstantValue;}
        int Code = 0;
        public int Code() {return Code == 0 ? Code = findUtf8(AttributeNames.Code) : Code;}
        int StackMapTable = 0;
        public int StackMapTable() {return StackMapTable == 0 ? StackMapTable = findUtf8(AttributeNames.StackMapTable) : StackMapTable;}
        int Exceptions = 0;
        public int Exceptions() {return Exceptions == 0 ? Exceptions = findUtf8(AttributeNames.Exceptions) : Exceptions;}
        int Synthetic = 0;
        public int Synthetic() {return Synthetic == 0 ? Synthetic = findUtf8(AttributeNames.Synthetic) : Synthetic;}
        int Signature = 0;
        public int Signature() {return Signature == 0 ? Signature = findUtf8(AttributeNames.Signature) : Signature;}
        int LineNumberTable = 0;
        public int LineNumberTable() {return LineNumberTable == 0 ? LineNumberTable = findUtf8(AttributeNames.LineNumberTable) : LineNumberTable;}
        int LocalVariableTable = 0;
        public int LocalVariableTable() {return LocalVariableTable == 0 ? LocalVariableTable = findUtf8(AttributeNames.LocalVariableTable) : LocalVariableTable;}
        int Deprecated = 0;
        public int Deprecated() {return Deprecated == 0 ? Deprecated = findUtf8(AttributeNames.Deprecated) : Deprecated;}
        int RuntimeVisibleAnnotations = 0;
        public int RuntimeVisibleAnnotations() {return RuntimeVisibleAnnotations == 0 ? RuntimeVisibleAnnotations = findUtf8(AttributeNames.RuntimeVisibleAnnotations) : RuntimeVisibleAnnotations;}
        int RuntimeInvisibleAnnotations = 0;
        public int RuntimeInvisibleAnnotations() {return RuntimeInvisibleAnnotations == 0 ? RuntimeInvisibleAnnotations = findUtf8(AttributeNames.RuntimeInvisibleAnnotations) : RuntimeInvisibleAnnotations;}
        int RuntimeVisibleTypeAnnotations = 0;
        public int RuntimeVisibleTypeAnnotations() {return RuntimeVisibleTypeAnnotations == 0 ? RuntimeVisibleTypeAnnotations = findUtf8(AttributeNames.RuntimeVisibleTypeAnnotations) : RuntimeVisibleTypeAnnotations;}
        int RuntimeInvisibleTypeAnnotations = 0;
        public int RuntimeInvisibleTypeAnnotations() {return RuntimeInvisibleTypeAnnotations == 0 ? RuntimeInvisibleTypeAnnotations = findUtf8(AttributeNames.RuntimeInvisibleTypeAnnotations) : RuntimeInvisibleTypeAnnotations;}
        int RuntimeVisibleParameterAnnotations = 0;
        public int RuntimeVisibleParameterAnnotations() {return RuntimeVisibleParameterAnnotations == 0 ? RuntimeVisibleParameterAnnotations = findUtf8(AttributeNames.RuntimeVisibleParameterAnnotations) : RuntimeVisibleParameterAnnotations;}
        int RuntimeInvisibleParameterAnnotations = 0;
        public int RuntimeInvisibleParameterAnnotations() {return RuntimeInvisibleParameterAnnotations == 0 ? RuntimeInvisibleParameterAnnotations = findUtf8(AttributeNames.RuntimeInvisibleParameterAnnotations) : RuntimeInvisibleParameterAnnotations;}
        int AnnotationDefault = 0;
        public int AnnotationDefault() {return AnnotationDefault == 0 ? AnnotationDefault = findUtf8(AttributeNames.AnnotationDefault) : AnnotationDefault;}
        int MethodParameters = 0;
        public int MethodParameters() {return MethodParameters == 0 ? MethodParameters = findUtf8(AttributeNames.MethodParameters) : MethodParameters;}
    }
}
