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

/**
 * Provides low-level primitives for building
 * bytecodes structures that may contain attributes.
 * <br>
 * This API is unsafe and requires strict contract compliance.
 *
 * @author OblivRuinDev
 */
public interface IRawAttributable {
    /**
     * Visits an attribute with custom binary data.
     * <br>
     * The resulting attribute structure is:
     * <pre class="screen">
     * attribute_info {
     *     u2 attribute_name_index;  // = nameIndex
     *     u4 attribute_length;      // = len
     *     u1 info[attribute_length];// = Arrays.copyOfRange(data, off, off + len)
     * }</pre>
     *
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} entry which represents the attribute name
     * @param off starting offset of the attribute data within the {@code data} array(inclusive).
     * @param len length of the attribute data in bytes.
     * @param data byte array containing the attribute's payload.
     */
    void visitAttribute(int nameIndex, int off, int len, byte[] data);

    /**
     * Visits a fixed-length attribute storing a 16-bit value (e.g. {@code ConstantValue}).
     * <br>
     * The resulting attribute structure is:
     * <pre class="screen">
     * attribute_info {
     *     u2 attribute_name_index;// = nameIndex
     *     u4 attribute_length;    // = 2
     *     u2 value;               // = (value & 0xFFFF)
     * }</pre>
     *
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} entry which represents the attribute name
     * @param value the attribute value (only the low 16 bits are stored)
     */
    void visitAttribute(int nameIndex, int value);

    /**
     * Visits an empty attribute with no payload (e.g. {@code Deprecated}).
     * <br>
     * The resulting attribute structure is:
     * <pre class="screen">
     * attribute_info {
     *     u2 attribute_name_index;// = nameIndex
     *     u4 attribute_length;    // = 0
     * }</pre>
     * 
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} entry which represents the attribute name
     */
    void visitEmptyAttribute(int nameIndex);

    /**
     * Visits a custom attribute, returning a visitor for its contents.
     *
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} entry which represents the attribute name
     * @return visitor for building the attribute's internal structure
     */
    IRawAttributeVisitor visitAttribute(int nameIndex);

    /**
     * Visits a composite attribute that may contain some attributes. (e.g. {@code Code} attribute)
     *
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} entry which represents the attribute name
     * @return visitor for building the composite attribute structure
     */
    IRawCompAttributeVisitor visitCompAttribute(int nameIndex);

    /**
     * Signals the end of this structure's visitation.
     * <br>
     * <b>Contract</b>: Must be called exactly once after all other visits even don't visit.
     */
    void visitEnd();
}
