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
import dev.oblivruin.jcu.visit.raw.IRawCompAttributeVisitor;

public class CompAttributeWriter extends AttributeWriter implements IRawCompAttributeVisitor {
    protected short count = 0;
    protected int off1;
    public CompAttributeWriter(ByteArray array) {
        super(array);
    }

    @Override
    public void visitAttributes() {
        this.off1 = array.length;
        array.ensureFree(2);
        array.length+=2;
    }

    @Override
    public void visitAttribute(int nameIndex, int off, int len, byte[] data) {
        ++count;
        array.put24(nameIndex, len);
        array.add(data, off, len);
    }

    @Override
    public void visitAttribute(int nameIndex, int valueIndex) {
        ++count;
        array.put242(nameIndex, 2, valueIndex);
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

    @Override
    public void visitEnd() {
        super.visitEnd();
        array.set2(off1, count);
    }
}
