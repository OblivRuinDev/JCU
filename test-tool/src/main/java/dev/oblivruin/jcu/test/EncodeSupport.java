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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public final class EncodeSupport extends ByteArrayOutputStream {
    public final DataOutputStream handle;
    public EncodeSupport() {
        this(200);
    }

    public EncodeSupport(int size) {
        super(size);
        handle = new DataOutputStream(this);
    }

    public void test(byte[] bytes, int off, int len) {
        byte[] origin = buf;
        TestException ex = null;
        if (len != count) {
            ex = new TestException.LengthNotEqualEx(len, count);
            len = Math.min(len, count);
        }
        for (int pointer = 0; pointer < len; ++pointer, ++off) {
            if (origin[pointer] != bytes[off]) {
                throw new TestException.MismatchException(off).suppress(ex);
            }
        }
        if (ex != null) {
            throw ex;
        }
    }

    public String ofArrayStr(int off) {
        return Arrays.toString(Arrays.copyOfRange(buf, off, count));
    }
}
