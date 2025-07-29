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
 * Enumerate tags of each CONSTANT in Constant Pool,
 * or the possible values that may occur in Constant Pool (like kinds for MethodHandle).<br>
 * If a constant field's Javadoc contains a {@code @see} reference to a class, it indicates a loadable constant
 * (see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4-310">JVMS 4.4-C</a>).
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4">JVMS
 *     4.4</a>
 * @author OblivRuinDev
 */
public final class Tag {
    /**
     * The tag value of {@code CONSTANT_Utf8_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.7">
     *     JVMS-4.4.7</a>
     */
    public static final int Utf8 = 1;
    /**
     * The tag value of {@code CONSTANT_Integer_info} JVMS structures.
     * @see Integer
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.4">
     *     JVMS-4.4.4</a>
     */
    public static final int Integer = 3;
    /**
     * The tag value of {@code CONSTANT_Float_info} JVMS structures.
     * @see Float
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.4">
     *     JVMS-4.4.4</a>
     */
    public static final int Float = 4;
    /**
     * The tag value of {@code CONSTANT_Long_info} JVMS structures
     * @see Long
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.5">
     *     JVMS-4.4.5</a>
     */
    public static final int Long = 5;
    /**
     * The tag value of {@code CONSTANT_Double_info} JVMS structures.
     * @see Double
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.5">
     *     JVMS-4.4.5</a>
     */
    public static final int Double = 6;
    /**
     * The tag value of {@code CONSTANT_Class_info} JVMS structures.
     * @see Class
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.1">
     *     JVMS-4.4.1</a>
     */
    public static final int Class = 7;
    /**
     * The tag value of {@code CONSTANT_String_info} JVMS structures.
     * @see String
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.3">
     *     JVMS-4.4.3</a>
     */
    public static final int String = 8;
    /**
     * The tag value of {@code CONSTANT_Fieldref_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.2">
     *     JVMS-4.4.2</a>
     */
    public static final int Fieldref = 9;
    /**
     * The tag value of {@code CONSTANT_Methodref_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.2">
     *     JVMS-4.4.2</a>
     */
    public static final int Methodref = 10;
    /**
     * The tag value of {@code CONSTANT_InterfaceMethodref_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.2">
     *     JVMS-4.4.2</a>
     */
    public static final int InterfaceMethodref = 11;
    /**
     * The tag value of {@code CONSTANT_NameAndType_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.6">
     *     JVMS-4.4.6</a>
     */
    public static final int NameAndType = 12;
    /**
     * The tag value of {@code CONSTANT_MethodHandle_info} JVMS structures.
     * @see java.lang.invoke.MethodHandle
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.8">
     *     JVMS-4.4.8</a>
     * @since 1.7
     */
    public static final int MethodHandle = 15;
    /**
     * The tag value of {@code CONSTANT_MethodType_info} JVMS structures.
     * @see java.lang.invoke.MethodType
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.9">
     *     JVMS-4.4.9</a>
     * @since 1.7
     */
    public static final int MethodType = 16;
    /**
     * The tag value of {@code CONSTANT_Dynamic_info} JVMS structures.
     * @see java.lang.invoke.CallSite
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.10">
     *     JVMS-4.4.10</a>
     * @since 11
     */
    public static final int Dynamic = 17;
    /**
     * The tag value of {@code CONSTANT_InvokeDynamic_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.10">
     *     JVMS-4.4.10</a>
     * @since 1.7
     */
    public static final int InvokeDynamic = 18;
    /**
     * The tag value of {@code CONSTANT_Module_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.11">
     *     JVMS-4.4.11</a>
     * @since 9
     */
    public static final int Module = 19;
    /**
     * The tag value of {@code CONSTANT_Package_info} JVMS structures.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.4.12">
     *     JVMS-4.4.12</a>
     * @since 9
     */
    public static final int Package = 20;

    /**
     * Enumerate kinds for {@link #MethodHandle}
     *
     * @author OblivRuinDev
     * @since 1.7
     */
    public static final class Kind {
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_getField         = 1;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_getStatic        = 2;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_putField         = 3;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_putStatic        = 4;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_invokeVirtual    = 5;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_invokeStatic     = 6;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_invokeSpecial    = 7;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_newInvokeSpecial = 8;
        /** Kind for {@link #MethodHandle}. */
        public static final int REF_invokeInterface  = 9;

        private Kind() {}
    }

    private Tag() {}
}
