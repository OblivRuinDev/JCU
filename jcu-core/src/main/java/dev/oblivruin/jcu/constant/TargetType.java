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
 * <p>
 * This documentation includes content derived from the Java Virtual Machine Specification (JVMS)
 * for accuracy and completeness. Original JVMS content is copyrighted by Oracle and/or its affiliates.
 * </p>
 *
 * @author OblivRuinDev
 */
public final class TargetType {//todo: rename for its bad name
    private TargetType() {}
    /**
     * type parameter declaration of generic class or interface
     * <br>
     * Target info: type_parameter_target
     * <br>
     * Location: ClassFile
     * <br>
     * info structure:
     * <pre class="screen">type_parameter_target {
     *     u1 type_parameter_index;
     * }</pre>
     */
    public static final int CLASS_TYPE_PARA = 0x00;
    /**
     * type parameter declaration of generic method or constructor
     * <br>
     * Target info: type_parameter_target
     * <br>
     * Location: method_info
     */
    public static final int METHOD_TYPE_PARA = 0x01;
    /**
     * type in extends or implements clause of class declaration (including the direct superclass or direct superinterface of an anonymous class declaration), or in extends clause of interface declaration
     * <br>
     * Target info: supertype_target
     * <br>
     * Location: ClassFile
     */
    public static final int SUPER_TYPE = 0x10;
    /**
     * type in bound of type parameter declaration of generic class or interface
     * <br>
     * Target info: type_parameter_bound_target
     * <br>
     * Location: ClassFile
     */
    public static final int CLASS_TYPE_PARA_BOUND = 0x11;
    /**
     * type in bound of type parameter declaration of generic method or constructor
     * <br>
     * Target info: type_parameter_bound_target
     * <br>
     * Location: method_info
     */
    public static final int METHOD_TYPE_PARA_BOUND = 0x12;
    /**
     * type in field or record component declaration
     * <br>
     * Target info: empty_target
     * <br>
     * Location: field_info, record_component_info
     */
    public static final int FIELD = 0x13;
    /**
     * return type of method, or type of newly constructed object
     * <br>
     * Target info: empty_target
     * <br>
     * Location: method_info
     */
    public static final int RET_TYPE = 0x14;
    /**
     * receiver type of method or constructor
     * <br>
     * Target info: empty_target
     * <br>
     * Location: method_info
     */
    public static final int RECV_TYPE = 0x15;
    /**
     * type in formal parameter declaration of method, constructor, or lambda expression
     * <br>
     * Target info: formal_parameter_target
     * <br>
     * Location: method_info
     */
    public static final int FORMAL_PARA = 0x16;
    /**
     * type in {@code throws} clause of method or constructor
     * <br>
     * Target info: throws_target
     * <br>
     * Location: method_info
     */
    public static final int THROWS = 0x17;
    /**
     * type in local variable declaration
     * <br>
     * Target info: localvar_target
     * <br>
     * Location: Code
     */
    public static final int LOCAL_VAR = 0x40;
    /**
     * type in resource variable declaration
     * <br>
     * Target info: localvar_target
     * <br>
     * Location: Code
     */
    public static final int RES_VAR = 0x41;
    /**
     * type in exception parameter declaration
     * <br>
     * Target info: catch_target
     * <br>
     * Location: Code
     */
    public static final int EXCEPTION_PARA = 0x42;
    /**
     * type in {@code instanceof} expression
     * <br>
     * Target info: offset_target
     * <br>
     * Location: Code
     */
    public static final int INSTANCEOF = 0x43;
    /**
     * type in {@code new} expression
     * <br>
     * Target info: offset_target
     * <br>
     * Location: Code
     */
    public static final int NEW = 0x44;
    /**
     * type in method reference expression using {@code ::new}
     * <br>
     * Target info: offset_target
     * <br>
     * Location: Code
     */
    public static final int CONSTRUCTOR_REF = 0x45;
    /**
     * type in method reference expression using {@code ::Identifier}
     * <br>
     * Target info: offset_target
     * <br>
     * Location: Code
     */
    public static final int METHOD_REF = 0x46;
    /**
     * type in cast expression
     * <br>
     * Target info: type_argument_target
     * <br>
     * Location: Code
     */
    public static final int CAST = 0x47;
    /**
     * type argument for generic constructor in {@code new} expression or explicit constructor invocation statement
     * <br>
     * Target info: type_argument_target
     * <br>
     * Location: Code
     */
    public static final int CONSTRUCTOR_INVOKE_TYPE_ARG = 0x48;
    /**
     * type argument for generic method in method invocation expression
     * <br>
     * Target info: type_argument_target
     * <br>
     * Location: Code
     */
    public static final int METHOD_INVOKE_TYPE_ARG = 0x49;
    /**
     * type argument for generic constructor in method reference expression using {@code ::new}
     * <br>
     * Target info: type_argument_target
     * <br>
     * Location: Code
     */
    public static final int CONSTRUCTOR_REF_TYPE_ARG = 0x4A;
    /**
     * type argument for generic method in method reference expression using {@code ::Identifier}
     * <br>
     * Target info: type_argument_target
     * <br>
     * Location: Code
     */
    public static final int METHOD_REF_TYPE_ARG = 0x4B;
}
