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

public final class CharArray extends Array {
    public char[] data;
    public CharArray(int size) {
        this.data = new char[size];
    }
    public CharArray(char[] chars) {
        this.data = chars;
    }
    @Override
    public void ensureFree(int size) {

    }

    @Override
    public boolean tryExpand(int size) {
        return false;
    }

    @Override
    protected int newSize(int expected) {
        return 0;
    }
}
