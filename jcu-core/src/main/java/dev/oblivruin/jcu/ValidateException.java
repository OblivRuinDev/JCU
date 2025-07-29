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

public class ValidateException extends RuntimeException {
    public ValidateException() {
        super();
    }

    public ValidateException(String msg) {
        super(msg);
    }

    public ValidateException(String msg, boolean track) {
        super(msg, null, true, track);
    }

    public ValidateException(int value, String name) {
        this(value + " is an invalid " + name);
    }

    public ValidateException(int value, String name, boolean track) {
        super(value + " is an invalid " + name, null, true, track);
    }

    public ValidateException(String head, int value, String name) {
        this(head + value + " is an invalid " + name);
    }

    public ValidateException(String head, int value, String name, boolean track) {
        super(head + value + " is an invalid " + name, null, true, track);
    }
}
