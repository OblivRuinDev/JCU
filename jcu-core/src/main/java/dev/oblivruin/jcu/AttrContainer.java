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
 * A low-level writer for building the content of attributes structure.
 * <br>
 * Automatically manages the {@code attribute_length} by reserving
 * space during construction and updating it during {@link #visitEnd()}.
 * Provides methods for writing primitive values and {@link #array} for complex writing.
 *
 * <p>Attributes structure:</p>
 * <pre class="screen">
 * {
 *     u2 attributes_count;
 *     attribute_info attributes[attributes_count];
 * }</pre>
 *
 * @author OblivRuinDev
 */
public class AttrContainer implements IRawAttributable {
    /** The underlying byte array where the structure's content is stored. */
    public final ByteArray array;
    /** The start offset of {@code attribute_length} in {@link #array}. */
    public final int off;
    /** The number of attributes ({@code attributes_count}) in this structure. */
    public int count = 0;

    /**
     * Constructs an attributes structure writer,
     * reserving 2 bytes for {@code attributes_count}.
     *
     * @param array the byte array used for storage
     */
    protected AttrContainer(ByteArray array) {
        this.array = array;
        array.ensureFree(2);
        this.off = array.length;
        array.length+=2;
    }

    @Override
    public void visitAttribute(int nameIndex, int off, int len, byte[] data) {
        ++count;
        array.put24(nameIndex, len);
        array.add(data, off, len);
    }

    @Override
    public void visitAttribute(int nameIndex, int value) {
        ++count;
        array.put242(nameIndex, 2, value);
    }

    @Override
    public void visitEmptyAttribute(int nameIndex) {
        ++count;
        array.put24(nameIndex, 0);
    }

    @Override
    public AttributeWriter visitAttribute(int nameIndex) {
        ++count;
        array.put2(nameIndex);
        return new AttributeWriter(array);
    }

    @Override
    public CompAttributeWriter visitCompAttribute(int nameIndex) {
        ++count;
        array.put2(nameIndex);
        return new CompAttributeWriter(array);
    }

    /**
     * Finalizes the attribute by writing the {@code attributes_count} at offset {@link #off}.
     * <p>
     * {@inheritDoc}
     */
    public void visitEnd() {
        BytesUtil.setUShort(array.data, off, count);
    }
}
