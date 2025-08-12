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
/**
 * Provides low-level primitives for direct Java bytecode manipulation and generation.
 * <p>
 * This package contains APIs for raw interaction with class file structures including:
 * <ul>
 *   <li>Class/Field/Method bytecode visitors</li>
 *   <li>Attribute manipulation primitives and visitors</li>
 *   <li>Constant pool management utilities</li>
 * </ul>
 *
 * <h3>Performance and Safety Considerations</h3>
 * Types in this package are optimized for high-performance bytecode generation with minimal overhead.
 * However, these low-level APIs:
 * <ul>
 *   <li>Require strict adherence to method call sequences and structural constraints</li>
 *   <li>Perform minimal runtime validation for performance reasons</li>
 *   <li>May produce invalid class files or JVM crashes if used incorrectly</li>
 * </ul>
 *
 * <b>Critical requirement:</b> Callers must follow the documented API contract precisely,
 * including method invocation order, value ranges, and lifecycle constraints. Violations may
 * result in undefined behavior including corrupted bytecode or exception throw.
 *
 * @author OblivRuinDev
 */
package dev.oblivruin.jcu;