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

import dev.oblivruin.jcu.util.ByteArray;

public class AttributeWriter implements IRawAttributeVisitor {
    final ByteArray array;
    final int off;

    /**
     * Will auto reserve 4bits for length, also check if need expand array.
     */
    public AttributeWriter(ByteArray array) {
        this.array = array;
        this.off = array.length;
        array.ensureFree(4);
        array.length+=4;
    }

    @Override
    public void write(byte b) {
        array.add(b);
    }

    @Override
    public void write(byte[] bytes, int off, int len) {
        array.add(bytes, off, len);
    }

    @Override
    public void writeU2(int v) {
        array.put2(v);
    }

    @Override
    public void writeU4(int v) {
        array.put4(v);
    }

    @Override
    public void visitEnd() {
        array.set4(off, array.length - off - 4);
    }
}
