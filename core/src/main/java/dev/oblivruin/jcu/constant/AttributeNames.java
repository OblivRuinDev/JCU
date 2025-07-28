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
 * Enumerate names of attribute.
 *
 * @author OblivRuinDev
 */
public final class AttributeNames {
    private AttributeNames() {}
    /**
     * @since 1.0.2
     */
    public static final String ConstantValue
                            = "ConstantValue";
    /**
     * @since 1.0.2
     */
    public static final String Code
                            = "Code";
    /**
     * @since 1.6
     */
    public static final String StackMapTable
                            = "StackMapTable";
    /**
     * @since 1.7
     */
    public static final String BootstrapMethods
                            = "BootstrapMethods";
    /**
     * @since 11
     */
    public static final String NestHost
                            = "NestHost";
    /**
     * @since 11
     */
    public static final String NestMembers
                            = "NestMembers";
    /**
     * @since 17
     */
    public static final String PermittedSubclasses
                            = "PermittedSubclasses";

    /**
     * @since 1.0.2
     */
    public static final String Exceptions
                            = "Exceptions";
    /**
     * @since 1.1
     */
    public static final String InnerClasses
                            = "InnerClasses";
    /**
     * @since 1.5
     */
    public static final String EnclosingMethod
                            = "EnclosingMethod";
    /**
     * @since 1.1
     */
    public static final String Synthetic
                            = "Synthetic";
    /**
     * @since 1.5
     */
    public static final String Signature
                            = "Signature";
    /**
     * @since 16
     */
    public static final String Record
                            = "Record";
    /**
     * @since 1.0.2
     */
    public static final String SourceFile
                            = "SourceFile";
    /**
     * @since 1.0.2
     */
    public static final String LineNumberTable
                            = "LineNumberTable";
    /**
     * @since 1.0.2
     */
    public static final String LocalVariableTable
                            = "LocalVariableTable";
    /**
     * @since 1.5
     */
    public static final String LocalVariableTypeTable
                            = "LocalVariableTypeTable";

    /**
     * @since 1.5
     */
    public static final String SourceDebugExtension
                            = "SourceDebugExtension";
    /**
     * @since 1.1
     */
    public static final String Deprecated
                            = "Deprecated";
    /**
     * @since 1.5
     */
    public static final String RuntimeVisibleAnnotations
                            = "RuntimeVisibleAnnotations";
    /**
     * @since 1.5
     */
    public static final String RuntimeInvisibleAnnotations
                            = "RuntimeInvisibleAnnotations";
    /**
     * @since 1.5
     */
    public static final String RuntimeVisibleParameterAnnotations
                            = "RuntimeVisibleParameterAnnotations";
    /**
     * @since 1.5
     */
    public static final String RuntimeInvisibleParameterAnnotations
                            = "RuntimeInvisibleParameterAnnotations";
    /**
     * @since 1.8
     */
    public static final String RuntimeVisibleTypeAnnotations
                            = "RuntimeVisibleTypeAnnotations";
    /**
     * @since 1.8
     */
    public static final String RuntimeInvisibleTypeAnnotations
                            = "RuntimeInvisibleTypeAnnotations";
    /**
     * @since 1.5
     */
    public static final String AnnotationDefault
                            = "AnnotationDefault";
    /**
     * @since 1.8
     */
    public static final String MethodParameters
                            = "MethodParameters";
    /**
     * @since 9
     */
    public static final String Module
                            = "Module";
    /**
     * @since 9
     */
    public static final String ModulePackages
                            = "ModulePackages";
    /**
     * @since 9
     */
    public static final String ModuleMainClass
                            = "ModuleMainClass";

    /**
     * Seem not publish in jvms, but I found in javap.
     * <br>
     * It points to an utf8 index.
     * <br>
     * Utf8 possible values: {@code "windows-amd64"}
     * @since 9
     */
    public static final String ModuleTarget
                            = "ModuleTarget";
}
