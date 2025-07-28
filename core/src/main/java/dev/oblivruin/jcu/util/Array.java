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

public abstract class Array {
    public int length = 0;

    /**
     * Ensure that the remaining space of the array is bigger
     * than or equal to the given value, or cause an array expansion.
     * @param size required free size
     */
    public abstract void ensureFree(int size);
    /**
     * Ensure that the remaining space of the array is bigger
     * than or equal to the given value, or cause an array expansion.
     * @param size required free size
     * @return whether the array expansion operation was performed
     */
    public abstract boolean tryExpand(int size);

    protected abstract int newSize(int expected);
    public final void removeLast() {
        length--;
    }
    public final void clear() {
        length = 0;
    }
}
