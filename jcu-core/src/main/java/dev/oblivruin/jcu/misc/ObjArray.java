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
package dev.oblivruin.jcu.misc;

public abstract class ObjArray<T> extends Array {
    public T[] data;

    public ObjArray(T[] data) {
        this.data = data;
    }

    public ObjArray(int size) {
        this.data = newArray(size);
    }

    public ObjArray() {
        this.data = newArray(20);
    }

    public final void add(T value) {
        ensureFree(1);
        data[length++] = value;
    }

    @Override
    public final void ensureFree(int size) {
        int i = length + size;
        if (i >= data.length) {
            System.arraycopy(data, 0,
                    (data = newArray(newSize(i))), 0, length);
        }
    }

    @Override
    public final boolean tryExpand(int size) {
        int i = length + size;
        if (i >= data.length) {
            System.arraycopy(data, 0,
                    (data = newArray(newSize(i))), 0,
                    length);
            return true;
        }
        return false;
    }

    @Override
    protected int newSize(int expected) {
        return Math.max(expected, data.length * 2);
    }

    public abstract T[] newArray(int length);
}
