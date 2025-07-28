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
package dev.oblivruin.jcu.util;

public final class IntArray extends Array {
    public int[] data;
    public IntArray() {
        this(80);
    }
    public IntArray(int size) {
        this.data = new int[size];
    }
    public IntArray(int[] array) {
        this.data = array;
    }

    public void add(int i) {
        ensureFree(1);
        data[length++] = i;
    }

    public void add0(int i) {
        data[length++] = i;
    }

    @Override
    public void ensureFree(int size) {
        int i = length + size;
        if (i >= data.length) {
            System.arraycopy(data, 0,
                    (data = new int[Math.max(i, data.length * 2)]), 0,
                    length);
        }
    }

    @Override
    public boolean tryExpand(int size) {
        int i = length + size;
        if (i >= data.length) {
            System.arraycopy(data, 0,
                    (data = new int[Math.max(i, data.length * 2)]), 0,
                    length);
            return true;
        }
        return false;
    }
}
