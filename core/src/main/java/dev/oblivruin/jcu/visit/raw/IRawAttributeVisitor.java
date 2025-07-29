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
package dev.oblivruin.jcu.visit.raw;

/**
 * This interface provides low-level primitives for interacting with attribute structure.
 * <br>
 * This API is unsafe; all operations must comply with the API contract.
 *
 * @author OblivRuinDev
 */
public interface IRawAttributeVisitor {
    /**
     * Writes a single byte to the attribute's {@code info} array.
     *
     * @param b the byte value to write.
     */
    void write(byte b);
    /**
     * Writes a sequence of bytes to the attribute's {@code info} array.
     *
     * @param off starting offset in the array (inclusive).
     * @param len number of bytes to write.
     * @param bytes byte array containing the data to write.
     */
    void write(byte[] bytes, int off, int len);

    /**
     * Write a 16-bit value to the attribute's {@code info} array.
     * <p>
     * Only the low 16 bits of the value are stored.
     *
     * @param v the value to write
     */
    void writeU2(int v);

    /**
     * Write a 32-bit to the attribute's {@code info} array.
     *
     * @param v the value to write
     */
    void writeU4(int v);
    /**
     * Signals the end of attribute content visitation.
     * <p>
     * <b>Must be called exactly once after all write operations.</b>
     */
    void visitEnd();
}
