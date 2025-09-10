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
 * Enumerate possible {@code target_type} values for TypeAnnotation as defined in JVMS.
 * <p>
 * This documentation includes content derived from the Java Virtual Machine Specification (JVMS)
 * for accuracy and completeness. Original JVMS content is copyrighted by Oracle and/or its affiliates.
 * </p>
 *
 * @author OblivRuinDev
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.7.20-400">Target Type Table-A (jvms)</a>
 */
public final class TargetType {
    private TargetType() {}
    /**
     * For type parameter on a generic class.
     *
     * @targetInfo type_parameter_target
     * @location ClassFile
     */
    public static final int CLASS_TYPE_PARA = 0x00;
    /**
     * For type parameter on a generic method or constructor.
     *
     * @targetInfo type_parameter_target
     * @location method_info
     */
    public static final int METHOD_TYPE_PARA = 0x01;
    /**
     * For type in an {@code extends} or {@code implements} clause of class declaration,
     * or in the {@code extends} clause of an interface declaration.
     * Including the direct superclass or direct superinterface of an anonymous class declaration.
     *
     * @targetInfo supertype_target
     * @location ClassFile
     */
    public static final int SUPER_TYPE = 0x10;//todo: may rename
    /**
     * For bound of a type parameter on a generic class.
     *
     * @targetInfo type_parameter_bound_target
     * @location ClassFile
     */
    public static final int CLASS_TYPE_PARA_BOUND = 0x11;
    /**
     * For bound of a type parameter on a generic method or constructor.
     *
     * @targetInfo type_parameter_bound_target
     * @location method_info
     */
    public static final int METHOD_TYPE_PARA_BOUND = 0x12;
    /**
     * For field or record component.
     *
     * @targetInfo empty_target
     * @location field_info, record_component_info
     */
    public static final int FIELD = 0x13;
    /**
     * For the return type on a method or newly constructed object.
     *
     * @targetInfo empty_target
     * @location method_info
     */
    public static final int RET_TYPE = 0x14;
    /**
     * For the receiver type of method or constructor.
     *
     * @targetInfo empty_target
     * @location method_info
     */
    public static final int RECV_TYPE = 0x15;
    /**
     * For type in a formal parameter declaration of method, constructor, or lambda expression.
     *
     * @targetInfo formal_parameter_target
     * @location method_info
     */
    public static final int FORMAL_PARA = 0x16;
    /**
     * For {@code throws} clause in a method or constructor declaration.
     *
     * @targetInfo throws_target
     * @location method_info
     */
    public static final int THROWS = 0x17;
    /**
     * For local variable.
     *
     * @targetInfo localvar_target
     * @location Code
     */
    public static final int LOCAL_VAR = 0x40;
    /**
     * For resource variable declaration of a try-with-resources statement.
     *
     * @targetInfo localvar_target
     * @location Code
     */
    public static final int RES_VAR = 0x41;
    /**
     * For exception parameter.
     *
     * @targetInfo catch_target
     * @location Code
     */
    public static final int EXCEPTION_PARA = 0x42;
    /**
     * For {@code instanceof} expression.
     *
     * @targetInfo offset_target
     * @location Code
     */
    public static final int INSTANCEOF = 0x43;
    /**
     * For {@code new} expression.
     *
     * @targetInfo offset_target
     * @location Code
     */
    public static final int NEW = 0x44;
    /**
     * For constructor reference expression like {@code Object::new}.
     *
     * @targetInfo offset_target
     * @location Code
     */
    public static final int CONSTRUCTOR_REF = 0x45;
    /**
     * For method reference expression like {@code Foo::method}.
     *
     * @targetInfo offset_target
     * @location Code
     */
    public static final int METHOD_REF = 0x46;
    /**
     * For typecast expression
     *
     * @targetInfo type_argument_target
     * @location Code
     */
    public static final int CAST = 0x47;
    /**
     * For type argument in a generic constructor invocation expression.
     *
     * @targetInfo type_argument_target
     * @location Code
     */
    public static final int CONSTRUCTOR_INVOKE_TYPE_ARG = 0x48;
    /**
     * For type argument in a generic method invocation expression.
     *
     * @targetInfo type_argument_target
     * @location Code
     */
    public static final int METHOD_INVOKE_TYPE_ARG = 0x49;
    /**
     * For type argument on a generic constructor reference expression.
     *
     * @targetInfo type_argument_target
     * @location Code
     */
    public static final int CONSTRUCTOR_REF_TYPE_ARG = 0x4A;
    /**
     * For type argument on a generic method reference expression.
     *
     * @targetInfo type_argument_target
     * @location Code
     */
    public static final int METHOD_REF_TYPE_ARG = 0x4B;
}
