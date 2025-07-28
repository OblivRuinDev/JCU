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

package dev.oblivruin.jcu.visit.raw;

import dev.oblivruin.jcu.IConstantPool;

/**
 *
 */
public interface IRawClassVisitor extends IAttributable {
    /**
     * Visit the header of class.
     *
     * @param version the class version. The minor version is stored in the 16 most significant bits,
     *     and the major version in the 16 least significant bits.
     * @param access the class's access flags, see {@link dev.oblivruin.jcu.constant.AccessFlag} for more.
     * @param thisCIndex point to a CONSTANT_Class
     * @param superCIndex point to a CONSTANT_Class, if this is an interface, the CONSTANT_Class should be java/lang/Object
     * @param interfaceCIndexes an array which contains
     */
    void visit(int version, int access, int thisCIndex, int superCIndex, int[] interfaceCIndexes);
    IRawFieldVisitor visitField(int access, int nameIndex, int descIndex);

    /**
     * Called before calling {@link #visitMethod}
     */
    void visitMethods();
    IRawMethodVisitor visitMethod(int access, int nameIndex, int descIndex);
    @Override
    void visitEnd();
}
