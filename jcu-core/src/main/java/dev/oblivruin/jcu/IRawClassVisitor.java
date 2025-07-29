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
 * This interface provides low-level primitives for interacting with class bytecodes.
 * <br>
 * This API is unsafe; all operations must comply with the API contract.
 *
 * @author OblivRuinDev
 */
public interface IRawClassVisitor extends IRawAttributable {
    /**
     * Visits the class header.
     * <br>
     * <b>Contract:</b> Must be the first method called, and only once.
     *
     * @param version the class version. The minor version is stored in the 16 most significant bits,
     *     and the major version in the 16 least significant bits.
     * @param access the class's access flags, see {@link dev.oblivruin.jcu.constant.AccessFlag} for more.
     * @param thisCIndex index of a {@code CONSTANT_Class_info} entry which representing the class/interface defined by this class file.
     * @param superCIndex index of a {@code CONSTANT_Class_info} entry for superclass.
     *                   For interface, this must point to {@code java/lang/Object}.
     *                   The only exception is {@link Object}, where this index must be 0.
     * @param interfaceCIndexes array of indexes to {@code CONSTANT_Class_info} entries representing an
     *                         direct superinterfaces in source code order.
     */
    void visit(int version, int access, int thisCIndex, int superCIndex, int[] interfaceCIndexes);

    /**
     * Visits a field of the class.
     * <br>
     * Can be called multiple times or don't call.
     * 
     * @param access the field's access flags, see {@link dev.oblivruin.jcu.constant.AccessFlag} for more.
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} which represents a valid unqualified name denoting a field.
     * @param descIndex index of a {@code CONSTANT_Utf8_info} which represents a valid field descriptor.
     * @return a field visitor for adding attributes to the field.
     */
    IRawFieldVisitor visitField(int access, int nameIndex, int descIndex);
    /**
     * Visits a method of the class.
     * <br>
     * Can be called multiple times or don't call.
     *
     * @param access the method's access flags, see {@link dev.oblivruin.jcu.constant.AccessFlag} for more.
     * @param nameIndex index of a {@code CONSTANT_Utf8_info} which represents a valid unqualified name denoting a method.
     * @param descIndex index of a {@code CONSTANT_Utf8_info} which represents a valid method descriptor.
     * @return a method visitor for adding attributes to the method.
     */
    IRawMethodVisitor visitMethod(int access, int nameIndex, int descIndex);

    /**
     * Signals the end of class visitation.
     * <br>
     * <b>Must be called exactly once after all other visits.</b>
     */
    @Override
    void visitEnd();
}
