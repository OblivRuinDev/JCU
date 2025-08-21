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
package dev.oblivruin.jcu.test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class RandomUtf {
    public static final java.util.Random random = new java.util.Random();
    public static final RandomUtf INSTANCE = new RandomUtf(new ArrayList<Object>() {
        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public void add(int index, Object element) {
        }

        @Override
        public boolean addAll(Collection c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection c) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    });
    public final ArrayList<Object> snapshot;
    public RandomUtf() {
        this(20);
    }
    public RandomUtf(int size) {
        this(new ArrayList<>(size));
    }
    public RandomUtf(ArrayList<Object> list) {
        this.snapshot = list;
    }

    public String str1byte(int maxSize) {
        int len = random.nextInt(1, maxSize);
        byte[] data = new byte[len];
        for (int index = 0; index < len; ++index) {
            data[index] = (byte) random.nextInt(1, 0x80);
        }
        snapshot.add(data);
        return new String(data, StandardCharsets.ISO_8859_1);
    }

    public String str2byte(int maxSize) {
        return charsStr(maxSize, 0x800);
    }

    public String str3byte(int maxSize) {
        return charsStr(maxSize, 0x8000);
    }

    public String randomStr(int maxSize) {
        switch (random.nextInt(0, 3)) {
            case 0:
                return str1byte(maxSize);
            case 1:
                return str2byte(maxSize);
            default:
                return str3byte(maxSize);
        }
    }

    public String charsStr(int maxSize, int bound) {
        int len = random.nextInt(1, maxSize);
        char[] data = new char[len];
        for (int index = 0; index < len; ++index) {
            data[index] = (char) random.nextInt(1, bound);
        }
        snapshot.add(data);
        return new String(data);
    }
}
