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

public class Domain {
    /** Always bigger than or equal to 0 */
    public final int start;
    /** Always bigger than or equal to {@link #start} */
    public final int end;

    /**
     * Construct a new domain.
     *
     * @param start the start of the domain (inclusive)
     * @param end the end of the domain (exclusive)
     * @throws IllegalArgumentException if arguments is illegal
     */
    public Domain(int start, int end) {
        if (start > end || start < 0) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
    }
}
