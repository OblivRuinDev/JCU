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

public class TestException extends RuntimeException {
    public TestException(String message) {
        super(message);
    }

    public final TestException suppress(Throwable ex) {
        if (ex != null) {
            addSuppressed(ex);
        }
        return this;
    }

    public static class LengthNotEqualEx extends TestException {
        public final int actual;
        public final int expected;

        public LengthNotEqualEx(int actual, int expected) {
            super("Wanted length=" + actual + "but actual=" + actual);
            this.actual = actual;
            this.expected = expected;
        }
    }

    public static class MismatchException extends TestException {
        public final int offset;

        public MismatchException(int offset) {
            super("");
            this.offset = offset;
        }

        public MismatchException(int offset, String msg) {
            super(msg);
            this.offset = offset;
        }

        public String msg = null;

        @Override
        public String getMessage() {
            return this.msg == null ? super.getMessage() : this.msg;
        }
    }
}
