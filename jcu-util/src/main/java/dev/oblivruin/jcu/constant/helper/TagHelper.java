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

package dev.oblivruin.jcu.constant.helper;

import dev.oblivruin.jcu.ValidateException;
import dev.oblivruin.jcu.VersionException;
import dev.oblivruin.jcu.constant.Java;
import dev.oblivruin.jcu.constant.Tag;

import static dev.oblivruin.jcu.constant.Tag.*;
import static dev.oblivruin.jcu.constant.Tag.Kind.*;

/**
 * Constant helper for Tag.
 *
 * @see Tag
 * @author OblivRuinDev
 */
public final class TagHelper {
    private TagHelper() {}

    /**
     * Match the name by given value of the tag.
     *
     * @param tag the value of the tag
     * @return the name, or {@code "WRONG"} if couldn't find
     */
    public static String toString(int tag) {
        switch (tag) {
            case        Utf8:
                return "Utf8";
            case        Integer:
                return "Integer";
            case        Float:
                return "Float";
            case        Long:
                return "Long";
            case        Double:
                return "Double";
            case        Class:
                return "Class";
            case        String:
                return "String";
            case        Fieldref:
                return "Fieldref";
            case        Methodref:
                return "Methodref";
            case        InterfaceMethodref:
                return "InterfaceMethodref";
            case        NameAndType:
                return "NameAndType";
            case        MethodHandle:
                return "MethodHandle";
            case        MethodType:
                return "MethodType";
            case        Dynamic:
                return "Dynamic";
            case        InvokeDynamic:
                return "InvokeDynamic";
            case        Module:
                return "Module";
            case        Package:
                return "Package";
            default:
                return "UNKNOWN=" + tag;
        }
    }

    /**
     * Try to match the value of the tag using the given name
     * @param tagName the name of tag
     * @return the value of the tag corresponding to the name, or -1 if couldn't find
     * @throws NullPointerException if tagName is null
     */
    public static int valueOf(String tagName) {
        switch (tagName) {
            case "Utf8":
                return 1;
            case "Integer":
                return 3;
            case "Float":
                return 4;
            case "Long":
                return 5;
            case "Double":
                return 6;
            case "Class":
                return 7;
            case "String":
                return 8;
            case "Fieldref":
                return 9;
            case "Methodref":
                return 10;
            case "InterfaceMethodref":
                return 11;
            case "NameAndType":
                return 12;
            case "MethodHandle":
                return 15;
            case "MethodType":
                return 16;
            case "Dynamic":
                return 17;
            case "InvokeDynamic":
                return 18;
            case "Module":
                return 19;
            case "Package":
                return 20;
            default:
                return -1;
        }
    }

    public static String[] names() {
        return new String[] {
                "Utf8",
                "Integer",
                "Float",
                "Long",
                "Double",
                "Class",
                "String",
                "Fieldref",
                "Methodref",
                "InterfaceMethodref",
                "NameAndType",
                "MethodHandle",
                "MethodType",
                "Dynamic",
                "InvokeDynamic",
                "Module",
                "Package"
        };
    }

    /**
     * Validates the given value.
     *
     * @param tag value to be validated
     * @throws ValidateException if given value is an invalid tag
     */
    public static void validate(int tag) throws ValidateException {
        if (tag < 1 || tag > 20 || tag == 2 || tag == 13 || tag == 14) {
            throw new ValidateException(tag, "tag");
        }
    }

    /**
     * Validates the given value under given major version.
     *
     * @param tag value to be validated
     * @param major class file version
     * @throws ValidateException if given value is an invalid tag
     */
    public static void validate(int tag, int major) throws ValidateException {
        switch (tag) {
            case Utf8:
            case Integer:
            case Float:
            case Long:
            case Double:
            case Class:
            case String:
            case Fieldref:
            case Methodref:
            case InterfaceMethodref:
            case NameAndType:
                return;
            case MethodHandle:
            case MethodType:
            case InvokeDynamic:
                if (major < Java.V1_7) {
                    throw new VersionException(VersionException.CP7);
                }
                return;
            case Module:
            case Package:
                if (major < Java.V9) {
                    throw new VersionException(VersionException.CP9);
                }
                return;
            case Dynamic:
                if (major < Java.V11) {
                    throw new VersionException(VersionException.CP11);
                }
                return;
            default:
                throw new ValidateException(tag, "tag");
        }
    }

    /**
     * Validate
     * @param tag
     * @throws ValidateException
     */
    public static void validateModule(int tag) throws ValidateException {
        // consider the possibility of Annotation
        if (tag < 1) {
            throw new ValidateException(tag, "tag for module-info");
        }
        // MethodHandle MethodType Dynamic InvokeDynamic  is unavailable for module-info
        if (tag > 12 && (tag != Module && tag != Package)) {//todo: reduce range: 12 -> 8
            throw new ValidateException(tag, "tag for module-info");
        }
    }

    public static void validateClass(int tag) throws ValidateException {
        if (tag < 1 || tag > 18 || tag == 2 || tag == 13 || tag == 14) {
            throw new ValidateException(tag, "is a invalid tag for class");
        }
    }

    public static void validateClass(int tag, int major) throws ValidateException {
        switch (tag) {
            case Utf8:
            case Integer:
            case Float:
            case Long:
            case Double:
            case Class:
            case String:
            case Fieldref:
            case Methodref:
            case InterfaceMethodref:
            case NameAndType:
                return;
            case MethodHandle:
            case MethodType:
            case InvokeDynamic:
                if (major < Java.V1_7) {
                    throw new VersionException(VersionException.CP7);
                }
                return;
            case Module:
                throw new ValidateException("CONSTANT_Module is an invalid tag for class");
            case Package:
                throw new ValidateException("CONSTANT_Package is an invalid tag for class");
            case Dynamic:
                if (major < Java.V11) {
                    throw new VersionException(VersionException.CP11);
                }
                return;
            default:
                throw new ValidateException(tag, "tag for class");
        }
    }

    /**
     * Constant helper for Kind.
     *
     * @see Kind
     * @author OblivRuinDev
     */
    public static final class KindHelper {
        private KindHelper() {}

        /**
         * Validates the given value.
         *
         * @param kind value to be validated
         * @throws ValidateException if given value is an invalid kind for MethodHandle
         */
        public static void validate(int kind) throws ValidateException {
            if (kind < 1 || kind > 9) {
                throw new ValidateException(kind + " is a invalid kind");
            }
        }

        /**
         * Match the name by given value of the kind.
         *
         * @param kind the value of the kind
         * @return the name, or {@code "REF_UNKNOWN"} if couldn't find
         */
        public static String toString(int kind) {
            switch (kind) {
                case REF_getField:
                    return "REF_getField";
                case REF_getStatic:
                    return "REF_getStatic";
                case REF_putField:
                    return "REF_putField";
                case REF_putStatic:
                    return "REF_putStatic";
                case REF_invokeVirtual:
                    return "REF_invokeVirtual";
                case REF_invokeStatic:
                    return "REF_invokeStatic";
                case REF_invokeSpecial:
                    return "REF_invokeSpecial";
                case REF_newInvokeSpecial:
                    return "REF_newInvokeSpecial";
                case REF_invokeInterface:
                    return "REF_invokeInterface";
                default:
                    return "REF_UNKNOWN";
            }
        }

        /**
         * Returns an array which contains names of all kinds
         * @return an array
         */
        public static String[] names() {
            return new String[]{"REF_getField", "REF_getStatic",
                                "REF_putField", "REF_putStatic",
                    "REF_invokeVirtual", "REF_invokeStatic",
                    "REF_invokeSpecial", "REF_newInvokeSpecial",
                    "REF_invokeInterface"};
        }

        /**
         * Try to match the value of the kind using the given name
         * @param name the name of kind
         * @return the value of the kind corresponding to the name, or -1 if couldn't find
         * @throws NullPointerException if name is null
         */
        public static int valueOf(String name) {
            switch (name) {
                case "REF_getField":
                    return REF_getField;
                case "REF_getStatic":
                    return REF_getStatic;
                case "REF_putField":
                    return REF_putField;
                case "REF_putStatic":
                    return REF_putStatic;
                case "REF_invokeVirtual":
                    return REF_invokeVirtual;
                case "REF_invokeStatic":
                    return REF_invokeStatic;
                case "REF_invokeSpecial":
                    return REF_invokeSpecial;
                case "REF_newInvokeSpecial":
                    return REF_newInvokeSpecial;
                case "REF_invokeInterface":
                    return REF_invokeInterface;
                default:
                    return -1;
            }
        }
    }
}
