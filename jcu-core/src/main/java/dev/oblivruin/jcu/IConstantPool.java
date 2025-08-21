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

/**
 * Provides low-level primitives for interacting and building
 * constant pool structure.
 * <br>
 * This API is unsafe and requires strict contract compliance.
 *
 * @author OblivRuinDev
 */
public interface IConstantPool {
    /**
     * Returns the {@code constant_pool_count} value as defined in JVMS.
     * <p>
     * This value equals the number of entries in the constant pool table plus one.
     * Valid constant pool indices range from 1 to {@code count()-1}, with special
     * handling for {@code CONSTANT_Long_info} and {@code CONSTANT_Double_info} entries.
     *
     * @return the value of {@code constant_pool_count}
     */
    int count();

    /**
     * Returns the tag byte of the constant pool entry at the specified index.
     * <br>
     * May return a negative value to indicate an invalid constant slot (depending on implementation).
     *
     * @param index constant pool index (1 to {@code count()-1})
     * @return the tag value
     * @throws IndexOutOfBoundsException if index is outside valid range
     * @throws IllegalArgumentException if index point to an invalid position (depending on implementation)
     */
    int tag(int index);

    /**
     * Try to find tag on given offset index
     * @param tag the constant tag
     * @param off the constant index
     * @return the constant index, or -1 if not find
     */
    int findTag(int tag, int off);

    /**
     * Try to find a {@code CONSTANT_Utf8_info} entry for the given string.
     * <br>
     * Always return 0 if value is {@code null}
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param value the string value (maybe null)
     * @return index of the constant pool entry, or 0 if value is {@code null}
     */
    int findUtf8(String value);

    /**
     * Try to find a {@code CONSTANT_Integer_info} entry for the given value.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param value the integer value
     * @return index of the constant pool entry
     */
    default int findInt(int value) {
        return findC5(Tag.Integer, value);
    }

    /**
     * Try to find a {@code CONSTANT_Float_info} entry for the given value.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param value the float value
     * @return index of the constant pool entry
     */
    default int findFloat(float value) {
        return findC5(Tag.Float, Float.floatToIntBits(value));
    }

    /**
     * Try to find a 5-byte constant entry (e.g. {@code CONSTANT_Integer_info},
     * {@code CONSTANT_Methodref_info}) for given value.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param tag the constant tag (see {@link Tag})
     * @param data the payload data
     * @return index of the constant pool entry
     */
    int findC5(int tag, int data);

    /**
     * Try to find a {@code CONSTANT_Long_info} entry for the given value.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param value the long value
     * @return index of the constant pool entry
     */
    default int findLong(long value) {
        return findC9(Tag.Long, value);
    }

    /**
     * Try to find a {@code CONSTANT_Double_info} entry for the given value.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param value the double value
     * @return index of the constant pool entry
     */
    default int findDouble(double value) {
        return findC9(Tag.Double, Double.doubleToLongBits(value));
    }

    /**
     * Try to find a 9-byte constant pool entry (e.g. {@code CONSTANT_Long_info}).
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param tag the constant tag (see {@link Tag})
     * @param data the payload data
     * @return index of the constant pool entry
     */
    int findC9(int tag, long data);

    /**
     * Try to find a single-reference constant entry (e.g. {@code CONSTANT_Class_info}).
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param tag the constant tag (see {@link Tag})
     * @param refIndex index of the referenced constant pool entry
     * @return index of the constant pool entry
     */
    int findRef1(int tag, int refIndex);

    /**
     * Try to find a double-reference constant entry (e.g. {@code CONSTANT_Fieldref}).
     * <br>
     * The return value of this method is the same as {@code findU5(tag, (refIndex1 << 16) | refIndex2)}.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param tag       the constant tag (see {@link Tag})
     * @param refIndex1 index of the 1st referenced constant
     * @param refIndex2 index of the 2nd referenced constant
     * @return index of the constant pool entry
     */
    default int findRef2(int tag, int refIndex1, int refIndex2) {
        return findC5(tag, (refIndex1 << 8) | (refIndex2 & 0xFFFF));
    }

    /**
     * Try to find a {@code CONSTANT_MethodHandle_info} entry.
     * <br>
     * May return -1 if not found, depending on implementation.
     *
     * @param kind the method handle kind (1-9, see {@link dev.oblivruin.jcu.constant.Tag.Kind})
     * @param refIndex index of the referenced constant
     * @return index of the constant pool entry
     */
    int findMethodHandle(int kind, int refIndex);

    /**
     * Returns the string value of a {@code CONSTANT_Utf8_info} entry.
     *
     * @param index constant pool index of a {@code CONSTANT_Utf8_info} entry
     * @return the decoded string value
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    String utf8V(int index);

    /**
     * Returns the integer value by interpreting the constant pool entry at the given index
     * as a {@code CONSTANT_Integer_info} structure.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as an integer constant regardless of its actual tag.
     *
     * @param index constant pool index
     * @return the integer value stored at the entry
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int intV(int index);

    /**
     * Returns the float value of a {@code CONSTANT_Float_info} entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a float constant regardless of its actual tag.
     *
     * @param index constant pool index of a {@code CONSTANT_Float_info} entry
     * @return the float value
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    default float floatV(int index) {
        return Float.intBitsToFloat(intV(index));
    }

    /**
     * Returns the long value by interpreting the constant pool entry at the given index
     * as a {@code CONSTANT_Long_info} structure.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a long constant regardless of its actual tag.
     *
     * @param index constant pool index
     * @return the long value stored at the entry
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    long longV(int index);

    /**
     * Returns the double value of a {@code CONSTANT_Double_info} entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a double constant regardless of its actual tag.
     *
     * @param index constant pool index of a {@code CONSTANT_Double_info} entry
     * @return the double value
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    default double doubleV(int index) {
        return Double.longBitsToDouble(longV(index));
    }

    /**
     * Returns the reference index of a single-reference constant entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a single_reference constant regardless of its actual tag.
     *
     * @param index constant pool index of a reference entry
     * @return referenced constant pool index
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int ref1Index(int index);

    /**
     * Returns the first reference index of a double-reference constant entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a double-reference constant regardless of its actual tag.
     *
     * @param index constant pool index of a double-reference entry
     * @return first referenced constant pool index
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int ref2Index1(int index);

    /**
     * Returns the second reference index of a double-reference constant entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a double-reference constant regardless of its actual tag.
     *
     * @param index constant pool index of a double-reference entry
     * @return second referenced constant pool index
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int ref2Index2(int index);

    /**
     * Returns the combined reference indexes of a double-reference entry as a combined value.
     * The return value of this method is the same as {@link #intV(int)}.
     * <p>
     * The format is: {@code (ref2Index1 << 16) | ref2Index2}
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a double-reference constant regardless of its actual tag.
     *
     * @param index constant pool index of a double-reference entry
     * @return combined reference indexes
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    default int ref2Indexes(int index) {
        return intV(index);
    }

    /**
     * Returns the reference index of a {@code CONSTANT_MethodHandle_info} entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a double-reference constant regardless of its actual tag.
     *
     * @param index constant pool index of a {@code CONSTANT_MethodHandle_info} entry
     * @return referenced constant pool index
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int methodHandleIndex(int index);

    /**
     * Returns the kind value of a {@code CONSTANT_MethodHandle_info} entry.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a MethodHandle constant regardless of its actual tag.
     *
     * @param index constant pool index of a {@code CONSTANT_MethodHandle_info} entry
     * @return method handle kind (1-9)
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int methodHandleKind(int index);

    /**
     * Returns the kind and reference index of a {@code CONSTANT_MethodHandle_info} entry as a packed value.
     * <p>
     * This method assumes the entry is of the correct type and will attempt to read it
     * as a MethodHandle constant regardless of its actual tag.
     * <p>
     * The format is: {@code (kind << 16) | refIndex}
     *
     * @param index constant pool index of a {@code CONSTANT_MethodHandle_info} entry
     * @return packed kind and reference index
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    int methodHandleKindAndRef(int index);

    /**
     * Returns a structured view of a single-reference constant entry.
     *
     * @param index constant pool index of a reference entry
     * @return structured reference data
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    IRef1 ref1V(int index);

    /**
     * Returns a structured view of a double-reference constant entry.
     *
     * @param index constant pool index of a double-reference entry
     * @return structured reference data
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    IRef2 ref2V(int index);

    /**
     * Returns a structured view of a {@code CONSTANT_MethodHandle_info} entry.
     *
     * @param index constant pool index of a MethodHandle entry
     * @return structured method handle data
     * @throws IndexOutOfBoundsException if index is outside valid range
     */
    IMethodHandle methodHandleV(int index);

    /**
     * Base interface for structured constant pool entry views.
     */
    interface IConstant {
        /**
         * Returns the tag value of the constant pool entry.
         * @return tag value as defined in {@link Tag}
         */
        int tag();
    }

    /**
     * Structured view of single-reference constant entries.
     */
    interface IRef1 extends IConstant {
        /**
         * Returns the referenced constant pool index.
         * @return referenced index
         */
        int refIndex();
    }

    /**
     * Structured view of double-reference constant entries.
     */
    interface IRef2 extends IConstant {
        /**
         * Returns the first referenced constant pool index.
         * @return first referenced index
         */
        int refIndex1();

        /**
         * Returns the second referenced constant pool index.
         * @return second referenced index
         */
        int refIndex2();
    }

    abstract class IUtf8 implements IConstant {
        @Override
        public final int tag() {
            return Tag.Utf8;
        }

        abstract String value();
    }

    /**
     * Structured view of {@code CONSTANT_MethodHandle_info} entries.
     */
    abstract class IMethodHandle implements IConstant {
        @Override
        public final int tag() {
            return Tag.MethodHandle;
        }

        /**
         * Returns the method handle kind (1-9).
         * @return kind value
         */
        abstract int kind();

        /**
         * Returns the referenced constant pool index.
         * @return referenced index
         */
        abstract int refIndex();
    }
}
