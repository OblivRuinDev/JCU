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

import dev.oblivruin.jcu.constant.Tag;

public interface IConstantPool {
    /**
     * The value of the {@code constant_pool_count} item is equal to
     * the number of entries in the {@code constant_pool} table plus one.
     * A {@code constant_pool} index is considered valid if it is
     * greater than zero and less than constant_pool_count,
     * with the exception for constants of type long and double.
     *
     * @return standard {@code constant_pool_count} in jvms
     */
    int count();
    /**
     * @param index range should from 1 to {@link #count}-1
     * @return
     */
    int tag(int index);
    int findUtf8(String value);
    int findInt(int value);
    int findFloat(float value);
    int findU5(int tag, int data);
    int findLong(long value);
    int findDouble(double value);
    int findU9(int tag, long data);
    int findRef1(int tag, int refIndex);
    int findRef2(int tag, int refIndex1, int refIndex2);
    int findMethodHandle(int kind, int refIndex);
    String utf8V(int index);
    int intV(int index);
    float floatV(int index);
    long longV(int index);
    double doubleV(int index);
    int ref1Index(int index);
    int ref2Index1(int index);
    int ref2Index2(int index);
    int ref2Indexes(int index);
    int methodHandleIndex(int index);
    int methodHandleKind(int index);
    int methodHandleKindAndRef(int index);
    IRef1 ref1V(int index);
    IRef2 ref2V(int index);
    IMethodHandle methodHandleV(int index);
    interface IConstant {
        int tag();
    }
    interface IRef1 extends IConstant {
        int refIndex();
    }
    interface IRef2 extends IConstant {
        int refIndex1();
        int refIndex2();
    }
    abstract class IMethodHandle implements IConstant {
        @Override
        public final int tag() {
            return Tag.MethodHandle;
        }

        abstract int kind();
        abstract int refIndex();
    }
}
