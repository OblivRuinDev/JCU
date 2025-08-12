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
 * Provides low-level primitives for building composite attribute structures
 * (attributes that may contain attributes).
 * <br>
 * This API is unsafe and requires strict contract compliance.
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
     * Signals the start visiting attributes section.
     * <p>
     * <b>Contract:</b>
     * Optional if no attributes will be written.
     * If called, must be invoked exactly once before visiting any attributes.
     */
    void visitAttributes();

    @Override
    void visitAttribute(int nameIndex, int off, int len, byte[] data);

    @Override
    void visitAttribute(int nameIndex, int value);

    @Override
    void visitEmptyAttribute(int nameIndex);

    @Override
    IRawAttributeVisitor visitAttribute(int nameIndex);

    @Override
    IRawCompAttributeVisitor visitCompAttribute(int nameIndex);

    /**
     * Signals the end of composite attribute visitation.
     * <p>
     * <b>Contract:</b> Must be called exactly once after all content and attributes.
     */
    @Override
    void visitEnd();
}
