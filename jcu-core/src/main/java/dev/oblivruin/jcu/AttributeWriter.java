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
import dev.oblivruin.jcu.internal.BytesUtil;

/**
 * A low-level writer for building the content of the attribute.
 * <br>
 * Automatically manages the {@code attribute_length} by reserving space during construction
 * and updating it during {@link #visitEnd()}.
 * Provides methods for writing primitive values and {@link #array} for complex writing.
 *
 * @author OblivRuinDev
 */
public class AttributeWriter implements IRawAttributeVisitor {
    /**
     * The underlying byte array where the attribute's content is stored.
     * <br>
     * Direct access allows optimized writing via {@code putX} methods.
     */
    public final ByteArray array;
    /** The start offset of {@code attribute_length} in {@link #array}. */
    public final int off;

    /**
     * Constructs an attribute writer, reserving 4 bytes for the {@code attribute_length}.
     * <br>
     * The current {@code array.length} is stored as {@link #off} before advancing the length.
     *
     * @param array the byte array used for storage
     */
    public AttributeWriter(ByteArray array) {
        array.ensureFree(4);
        this.off = array.length;
        array.length+=4;
        this.array = array;
    }

    @Override
    public final void write(byte b) {
        array.add(b);
    }

    @Override
    public final void write(byte[] bytes, int off, int len) {
        array.add(bytes, off, len);
    }

    @Override
    public final void writeU2(int v) {
        array.put2(v);
    }

    @Override
    public final void writeU4(int v) {
        array.put4(v);
    }

    /**
     * Finalizes the attribute by writing the {@code attribute_length} at offset {@link #off}.
     * <p>
     * <b>Contract:</b> Must be called exactly once after all write operations.
     */
    @Override
    public void visitEnd() {
        BytesUtil.setInt(array.data, off, array.length - off - 4);
    }
}
