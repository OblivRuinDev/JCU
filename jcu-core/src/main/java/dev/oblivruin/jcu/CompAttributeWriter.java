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

import dev.oblivruin.jcu.misc.ByteArray;
import jdk.internal.vm.annotation.Stable;

/**
 * A low-level writer for building composite attributes (attributes that may contain attributes).
 *
 * @see dev.oblivruin.jcu.AttributeWriter
 * @author OblivRuinDev
 */
public final class CompAttributeWriter extends AttributeWriter implements IRawCompAttributeVisitor {
    @Stable
    private AttrContainer nest;

    /**
     * Constructs a composite attribute writer, reserving 4 bytes for the {@code attribute_length}.
     *
     * @see AttributeWriter#AttributeWriter(ByteArray)
     * @param array the byte array used for storage
     */
    public CompAttributeWriter(ByteArray array) {
        super(array);
    }

    /**
     * Delegating to {@code new AttrContainer(this.array)},
     * the current offset is stored in {@link AttrContainer#off} and
     * reserve 2 bytes for the {@code attributes_count} before writing attributes.
     * <p>
     * <b>Contract:</b> Optional if no attributes will be written.
     * If called, must be invoked exactly once before visiting any attributes.
     *
     * @see AttrContainer#AttrContainer(ByteArray)
     */
    @Override
    public void visitAttributes() {
        nest = new AttrContainer(array);
    }

    /** @return the attributes container */
    public AttrContainer getAttrContainer() {
        return nest;
    }

    @Override
    public void visitAttribute(int nameIndex, int off, int len, byte[] data) {
        nest.visitAttribute(nameIndex, off, len, data);
    }

    @Override
    public void visitAttribute(int nameIndex, int value) {
        nest.visitAttribute(nameIndex, value);
    }

    @Override
    public void visitEmptyAttribute(int nameIndex) {
        nest.visitEmptyAttribute(nameIndex);
    }

    @Override
    public AttributeWriter visitAttribute(int nameIndex) {
        return nest.visitAttribute(nameIndex);
    }

    @Override
    public CompAttributeWriter visitCompAttribute(int nameIndex) {
        return nest.visitCompAttribute(nameIndex);
    }

    /**
     * Finalizes the composite attribute by:
     * <ol>
     *   <li>Writing {@code attributes_count} via {@code nest.}{@link AttrContainer#visitEnd() visitEnd()}</li>
     *   <li>Writing {@code attribute_length} via {@code super.}{@link AttributeWriter#visitEnd() visitEnd()}</li>
     * </ol>
     * <p>
     * <b>Contract:</b> Must be called exactly once after all content and attributes.
     */
    @Override
    public void visitEnd() {
        if (nest == null) {
            array.put2(0);
        } else {
            nest.visitEnd();
        }
        super.visitEnd();
    }
}
