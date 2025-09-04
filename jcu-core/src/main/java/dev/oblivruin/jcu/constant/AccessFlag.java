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
package dev.oblivruin.jcu.constant;

/**
 * If you want to check flags is set, the code should follow below:
 * <blockquote><pre>
 *     // Single flag check:
 *     if ((checkedFlags & ACC_YOUR_EXCEPTED) != 0) {
 *         // checkedFlags contain flag we excepted
 *     }
 *
 *     // Composed flags check:
 *     int flag1 = ACC_YOUR_EXCEPTED1 | ACC_YOUR_EXCEPTED2...;
 *     // flag1 can be inlined because ACC_<i>XXX</i> it referred is constant value and flag1 will be computed and replaced in compile.
 *     // In other words, compiler will execute constant fold.
 *     if ((checkedFlags & flag1) == flag1) {
 *         // checkedFlags contain flags we excepted
 *         // execute your code
 *     }
 *     // So your code may be like this:
 *     if ((checkedFlags & (ACC_YOUR_EXCEPTED1 | ACC_YOUR_EXCEPTED2...))
 *                      == (ACC_YOUR_EXCEPTED1 | ACC_YOUR_EXCEPTED2...) ) {
 *         ...
 *     }
 * </pre></blockquote>
 *
 * <table class="striped" style="text-align:left">
 * <caption>Constant Member Usage</caption>
 * <thead><tr>
 * <th scope="col">Name</th>
 * <th scope="col">Class</th>
 * <th scope="col">Field</th>
 * <th scope="col">Method</th>
 * <th scope="col">InnerClasses Attribute</th>
 * <th scope="col">Method Parameter</th>
 * <th scope="col">Module</th>
 * <th scope="col">Module Requires</th>
 * <th scope="col">Module Exports</th>
 * <th scope="col">Module Opens</th>
 * </tr></thead>
 * <tbody>
 *     <tr><th scope="row">{@link #ACC_PUBLIC}</th>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_PRIVATE}, {@link #ACC_PROTECTED}, {@link #ACC_STATIC}</th>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_FINAL}</th>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_SUPER}, {@link #ACC_MODULE}</th>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_OPEN}</th>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_SYNCHRONIZED}, {@link #ACC_BRIDGE}, {@link #ACC_VARARGS}, {@link #ACC_NATIVE}, {@link #ACC_STRICT}</th>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_VOLATILE}, {@link #ACC_TRANSIENT}</th>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_INTERFACE}, {@link #ACC_ANNOTATION}</th>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_ABSTRACT}</th>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_ENUM}</th>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_TRANSITIVE}, {@link #ACC_STATIC_PHASE}</th>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>×</td>
 *         <td>×</td></tr>
 *     <tr><th scope="row">{@link #ACC_SYNTHETIC}</th>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td></tr>
 *     <tr><th scope="row">{@link #ACC_MANDATED}</th>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>×</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td>
 *         <td>√</td></tr>
 * </tbody></table>
 *
 * <p>
 * This documentation includes content derived from the Java Virtual Machine Specification (JVMS)
 * for accuracy and completeness. Original JVMS content is copyrighted by Oracle and/or its affiliates.
 * </p>
 *
 * @author OblivRuinDev
 */
@SuppressWarnings("JavadocDeclaration")
public final class AccessFlag {
    /**
     * @Target: Class, Field, Method, Inner Class
     */
    public static final int ACC_PUBLIC       = 0x0001;
    /**
     * @Target:        Field, Method, Inner Class
     */
    public static final int ACC_PRIVATE      = 0x0002;
    /**
     * @Target:        Field, Method, Inner Class
     */
    public static final int ACC_PROTECTED    = 0x0004;
    /**
     * @Target: Class, Field, Method, Inner Class
     */
    public static final int ACC_STATIC       = 0x0008;
    /**
     * @Target: Class, Field, Method, Inner Class, Method Parameter
     */
    public static final int ACC_FINAL        = 0x0010;
    /**
     * Treat superclass methods specially when invoked by the <em>invokespecial</em> instruction.
     * <p>
     * The {@code ACC_SUPER} flag indicates which of two alternative semantics is to be expressed by the
     * <em>invokespecial</em> instruction if it appears in this class or interface.
     * Compilers to the instruction set of JVM should set the {@code ACC_SUPER} flag.
     * In Java SE 8 and above, JVM considers the {@code ACC_SUPER} flag to be set in every classfile,
     * regardless of the actual value of the flag in the classfile and the version of the classfile.
     * </p>
     * <p class="note">
     * The {@code ACC_SUPER} flag exists for backward compatibility with code compiled
     * by older compilers for the Java programming language. Prior to JDK 1.0.2,
     * the compiler generated {@code access_flags} in which the flag now representing {@code ACC_SUPER}
     * had no assigned meaning, and Oracle's Java Virtual Machine implementation ignored the flag if it was set.
     * </p>
     * <p>
     * @Target: Class
     * </p>
     */
    public static final int ACC_SUPER        = 0x0020;
    /**
     * Indicates that this module is open.
     * <br>
     * @Target: Module
     */
    public static final int ACC_OPEN         = 0x0020;
    /**
     * Indicates that any module which depends on the current module,
     * implicitly declares a dependence on the module indicated by this entry.
     * <br>
     * @Target: Module Requires
     */
    public static final int ACC_TRANSITIVE   = 0x0020;
    /**
     * Declared {@code synchronized}; invocation is wrapped by a monitor use.
     * <br>
     * @Target: Method
     */
    public static final int ACC_SYNCHRONIZED = 0x0020;
    /**
     * Indicates that this dependence is mandatory in the static phase,
     * i.e., at compile time, but is optional in the dynamic phase, i.e., at run time.
     * <br>
     * @Target: Module Requires
     */
    public static final int ACC_STATIC_PHASE = 0x0040;
    /**
     * Declared {@code volatile}; cannot be cached.
     * <br>
     * @Target: Field
     */
    public static final int ACC_VOLATILE     = 0x0040;
    /**
     * A bridge method, generated by the compiler.
     * <br>
     * @Target: Method
     */
    public static final int ACC_BRIDGE       = 0x0040;
    /**
     * Declared {@code transient}; not written or read by a persistent object manager.
     * <br>
     * @Target: Field
     */
    public static final int ACC_TRANSIENT    = 0x0080;
    /**
     * Declared with variable number of arguments.
     * <br>
     * @Target: Method
     */
    public static final int ACC_VARARGS      = 0x0080;
    /**
     * Declared {@code native}; implemented in a language other than the Java programming language.
     * <br>
     * @Target: Method
     */
    public static final int ACC_NATIVE       = 0x0100;
    /**
     * @Target: Class, Inner Class
     */
    public static final int ACC_INTERFACE    = 0x0200;
    /**
     * @Target: Class, Inner Class, Method
     */
    public static final int ACC_ABSTRACT     = 0x0400;
    /**
     * In a class file whose major version number is at least 46 and at most 60: Declared {@code strictfp}.
     * <br>
     * @Target: Method
     */
    public static final int ACC_STRICT       = 0x0800;
    /**
     * Indicates that thing was not explicitly or implicitly declared in the source declaration.
     * <br>
     * @Target: All
     */
    public static final int ACC_SYNTHETIC    = 0x1000;
    /**
     * Declared as an annotation interface.
     * <br>
     * @Target: Class, Inner Class
     */
    public static final int ACC_ANNOTATION   = 0x2000;
    /**
     * For class, it declared as an {@code enum} class.<br>
     * For field, it declared as an element of an {@code enum} class.
     * <br>
     * @Target: Class, Inner Class, Field
     */
    public static final int ACC_ENUM         = 0x4000;
    /**
     * Indicates that thing was implicitly declared.
     * <br>
     * @Target: Method Parameter, Module, Module Requires, Module Exports, Module Opens
     */
    public static final int ACC_MANDATED     = 0x8000;
    /**
     * Declared as a module, not a class or interface.
     * <br>
     * @Target: Class
     * @since 9
     */
    public static final int ACC_MODULE       = 0x8000;

    private AccessFlag() {}
}
