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
 * This interface provides low-level primitives for building composite attribute structures
 * (which may contain nested attributes).
 * <br>
 * This API is unsafe; all operations must comply with the API contract.
 *
 * @author OblivRuinDev
 */
public interface IRawCompAttributeVisitor extends IRawAttributeVisitor, IRawAttributable {
    @Override
    void write(byte b);

    @Override
    void write(byte[] bytes, int off, int len);

    @Override
    void writeU2(int v);

    @Override
    void writeU4(int v);

    /**
     * Signals the start of nested attributes visitation.
     * <p>
     * <b>Must be called exactly once before visiting any nested attributes.</b>
     */
    void visitAttributes();

    @Override
    void visitAttribute(int nameIndex, int off, int len, byte[] data);

    @Override
    void visitAttribute(int nameIndex, int value);

    @Override
    IRawAttributeVisitor visitAttribute(int nameIndex);

    @Override
    void visitEmptyAttribute(int nameIndex);

    @Override
    IRawCompAttributeVisitor visitCompAttribute(int nameIndex);

    /**
     * Signals the end of composite attribute visitation.
     * <p>
     * <b>Must be called exactly once after all content and nested attributes.</b>
     */
    @Override
    void visitEnd();
}
