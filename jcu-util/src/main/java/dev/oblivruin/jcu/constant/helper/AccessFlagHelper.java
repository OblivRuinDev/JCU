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

import dev.oblivruin.jcu.constant.Java;
import dev.oblivruin.jcu.misc.UtilX;
import dev.oblivruin.jcu.ValidateException;
import dev.oblivruin.jcu.VersionException;

import java.util.ArrayList;

import static dev.oblivruin.jcu.constant.AccessFlag.*;

public final class AccessFlagHelper {
    private AccessFlagHelper() {}
    public static final String UNKNOWN = "ACC_UNKNOWN";
    private static final int[] classFlags = {
            ACC_PUBLIC  , ACC_FINAL    , ACC_SUPER     , ACC_INTERFACE,
            ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM, ACC_MODULE};
    private static final int[] fieldFlags = {
            ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC,
            ACC_FINAL, ACC_VOLATILE, ACC_TRANSIENT, ACC_SYNTHETIC, ACC_ENUM};
    private static final int[] methodFlags = {
            ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC,
            ACC_FINAL, ACC_SYNCHRONIZED, ACC_BRIDGE, ACC_VARARGS,
            ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT, ACC_SYNTHETIC};
    private static final int[] innerFlags = {
            ACC_PUBLIC, ACC_PRIVATE    , ACC_PROTECTED, ACC_STATIC,
            ACC_FINAL , ACC_SUPER      , ACC_INTERFACE, ACC_ABSTRACT,
            ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM};
    public static int[] classFlags() {
        return classFlags.clone();
    }
    public static String[] classFlagNames() {
        return new String[]{"ACC_PUBLIC", "ACC_FINAL", "ACC_SUPER", "ACC_INTERFACE",
                "ACC_ABSTRACT", "ACC_SYNTHETIC", "ACC_ANNOTATION", "ACC_ENUM", "ACC_MODULE"};
    }
    public static int[] fieldFlags() {
        return fieldFlags.clone();
    }
    public static String[] fieldFlagNames() {
        return new String[]{"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC",
                "ACC_FINAL", "ACC_VOLATILE", "ACC_TRANSIENT", "ACC_SYNTHETIC", "ACC_ENUM"};
    }
    public static int[] methodFlags() {
        return methodFlags.clone();
    }
    public static String[] methodFlagNames() {
        return new String[]{"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC",
                "ACC_FINAL", "ACC_SYNCHRONIZED", "ACC_BRIDGE", "ACC_VARARGS",
                "ACC_NATIVE", "ACC_ABSTRACT", "ACC_STRICT", "ACC_SYNTHETIC"};
    }
    public static int[] innerClassFlags() {
        return innerFlags.clone();
    }
    public static String[] innerClassFlagNames() {
        return new String[]{"ACC_PUBLIC", "ACC_PRIVATE", "ACC_PROTECTED", "ACC_STATIC",
                "ACC_FINAL" , "ACC_SUPER", "ACC_INTERFACE", "ACC_ABSTRACT",
                "ACC_SYNTHETIC", "ACC_ANNOTATION", "ACC_ENUM"};
    }

    public static ArrayList<String> toStrings_Class(int flags) {
        return toStrings(flags, classFlags, 1);
    }

    public static ArrayList<String> toStrings_Method(int flags) {
        return toStrings(flags, methodFlags, 0);
    }

    public static ArrayList<String> toStrings_Field(int flags) {
        return toStrings(flags, fieldFlags, 3);
    }

    public static ArrayList<String> toStrings_InnerClass(int flags) {
        return toStrings(flags, innerFlags, 2);
    }

    // Arg Contract for target:  1 = class  2 = inner class  3 = field  0 = method
    private static ArrayList<String> toStrings(int flags, int[] array, int target) {
        if (flags == 0) {
            return new ArrayList<>(0);
        }
        ArrayList<String> ret = new ArrayList<>(array.length);
        for (int v : array) {
            if ((flags & v) != 0) {
                ret.add(toString(v, target));
                flags = flags & ~v;
            }
        }
        while (flags != 0) {
            int bit = Integer.highestOneBit(flags);
            ret.add(UtilX.toHexStr(bit));
            flags = flags & ~bit;
        }
        return ret;
    }

    // Skip 3 because use other check
    private static void v0(int flag, int[] array) {
        int flag0 = flag;
        int visCount = 0;
        if ((flag0 & ACC_PUBLIC) != 0) {
            ++visCount;
            flag0 = flag0 & ~ACC_PUBLIC;
        }
        if ((flag0 & ACC_PRIVATE) != 0) {
            ++visCount;
            flag0 = flag0 & ~ACC_PRIVATE;
        }
        if ((flag0 & ACC_PROTECTED) != 0) {
            ++visCount;
            flag0 = flag0 & ~ACC_PROTECTED;
        }
        if (visCount > 1) {
            throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because it has more than one visible flags");
        }

        for (visCount = 2; visCount < array.length; ++visCount) {
            int v = array[visCount];
            if ((flag0 & v) != 0) {
                flag0 = flag0 & ~v;
            }
        }

        if (flag0 != 0) {
            throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because it ");
        }
    }

    private static String toString(int flag, int target) {
        switch (flag) {
            case ACC_PUBLIC:
                return "ACC_PUBLIC";
            case ACC_PRIVATE:
                return "ACC_PRIVATE";
            case ACC_PROTECTED:
                return "ACC_PROTECTED";
            case ACC_STATIC:
                return "ACC_STATIC";
            case ACC_FINAL:
                return "ACC_FINAL";
            case 0x0020:
                return (target == 0 ? "ACC_SYNCHRONIZED" : "ACC_SUPER");
            case 0x0040:
                return (target == 0 ? "ACC_BRIDGE" : "ACC_VOLATILE");
            case 0x0080:
                return (target == 0 ? "ACC_VARARGS" : "ACC_TRANSIENT");
            case ACC_NATIVE:
                return "ACC_NATIVE";
            case ACC_INTERFACE:
                return "ACC_INTERFACE";
            case ACC_ABSTRACT:
                return "ACC_ABSTRACT";
            case ACC_STRICT:
                return "ACC_STRICT";
            case ACC_SYNTHETIC:
                return "ACC_SYNTHETIC";
            case ACC_ANNOTATION:
                return "ACC_ANNOTATION";
            case ACC_ENUM:
                return "ACC_ENUM";
            case 0x8000:
                return (target == 1 ? "ACC_MODULE" : "ACC_MANDATED");
            default:
                return null;
        }
    }

    public static void validate_class(int flag, int major) {
        int flag0 = flag;
        if ((flag0 & ACC_MODULE) != 0) {
            if (major < Java.V9) {
                throw new VersionException(VersionException.AF9);
            }
            if (flag0 != ACC_MODULE && flag0 != (ACC_MODULE & ACC_SYNTHETIC)) {
                throw new ValidateException("0x" + Integer.toHexString(flag) +
                        " is an invalid AccessFlags because a module-info cannot has more AccessFlag than ACC_MODULE or ACC_SYNTHETIC");
            }
        }
        if ((flag0 & ACC_PRIVATE) != 0) {
        }
        if ((flag0 & ACC_PROTECTED) != 0) {
        }
        if ((flag0 & ACC_INTERFACE) != 0) {
            if ((flag0 & ACC_ABSTRACT) == 0) {
                throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because ACC_ABSTRACT must set if ACC_INTERFACE is set");
            }
            if ((flag0 & ACC_FINAL) != 0) {
                throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because ACC_FINAL must not set if ACC_INTERFACE is set");
            }
            if ((flag0 & ACC_SUPER) != 0) {
                throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because ACC_SUPER must not set if ACC_INTERFACE is set");
            }
            if ((flag0 & ACC_ENUM) != 0) {
                throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because ACC_ENUM must not set if ACC_INTERFACE is set");
            }
            //  ACC_MODULE already checked before
            flag0 = flag0 & ~(ACC_ABSTRACT & ACC_INTERFACE);
        }
    }

    // reserve for future use
    public static void validate_field(int flag, int major) {
        if ((flag & ACC_ENUM) != 0) {
            validate_constField(flag);
            return;
        }
        if ((flag & (ACC_FINAL | ACC_VOLATILE))
                 == (ACC_FINAL | ACC_VOLATILE)) {
            throw new ValidateException("A Field AccessFlag must not have both ACC_FINAL and ACC_VOLATILE set");
        }
        vVisF(flag);
    }

    public static void validate_method(int flag, int major) {
        vVisF(flag);
        if ((flag & ACC_ABSTRACT) != 0) {
            if ((flag & (ACC_PRIVATE | ACC_STATIC | ACC_FINAL | ACC_SYNCHRONIZED | ACC_NATIVE)) != 0) {
                throw new ValidateException("If a method of a class or interface has its ACC_ABSTRACT flag set, it must not have any of its ACC_PRIVATE, ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, or ACC_NATIVE flags set");
            }
            if (major >= 46 && major <= 60 &&
                    (flag & ACC_STRICT) != 0) {
                throw new ValidateException("If a method of a class or interface has its ACC_ABSTRACT flag set, it must not have have its ACC_STRICT flag set (in a class file whose major version number is at least 46 and at most 60).");
            }
        }
    }

    public static void validate_method_init(int flag, int major) {
        vVisF(flag);
        if ((flag & (ACC_STATIC | ACC_FINAL | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_NATIVE | ACC_ABSTRACT)) != 0) {
            throw new ValidateException("An instance initialization method (§2.9.1) may have at most one of its ACC_PUBLIC, ACC_PRIVATE, and ACC_PROTECTED flags set, and may also have its ACC_VARARGS and ACC_SYNTHETIC flags set, and may also (in a class file whose major version number is at least 46 and at most 60) have its ACC_STRICT flag set, but must not have any of the other flags in Table 4.6-A set.");
        }
    }

    public static void validate_method_cinit(int flag, int major) {
        if (major >= Java.V1_7 && (flag & ACC_STATIC) == 0) {
            throw new ValidateException("In a class file whose version number is 51.0 or above, a method whose name is <clinit> must have its ACC_STATIC flag set.");
        }
    }

    public static void validate_constField(int flag) {
        if ((flag & (ACC_PUBLIC | ACC_STATIC | ACC_FINAL))
                 != (ACC_PUBLIC | ACC_STATIC | ACC_FINAL)) {
            throw new ValidateException("A constant field must have ACC_PUBLIC, ACC_STATIC, ACC_FINAL flag set.");
        }
        if ((flag & (ACC_PRIVATE | ACC_PROTECTED | ACC_VOLATILE | ACC_TRANSIENT)) != 0) {
            throw new ValidateException("A constant field must not have ACC_PRIVATE, ACC_PROTECTED, ACC_VOLATILE, ACC_TRANSIENT flag set.");
        }
    }

    private static void vVisF(int flag) {
        if (Integer.bitCount(flag & (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED)) > 1) {
            throw new ValidateException(UtilX.toHexStr(flag) +" is an invalid AccessFlags because it has more than one visible flags");
        }
    }

    //todo: inline
    private static int interfaceHandleModifier(int flags) {
        return (flags & ACC_INTERFACE) != 0 ? flags & ~ACC_ABSTRACT : flags;
    }

    private static String flagToModifier(int flag, int target) {
        switch (flag) {
            case ACC_PUBLIC:
                return "public";
            case ACC_PRIVATE:
                return "private";
            case ACC_PROTECTED:
                return "protected";
            case ACC_STATIC:
                return "static";
            case ACC_FINAL:
                return "final";
            case ACC_SYNCHRONIZED:
                return "synchronized";
            case ACC_VOLATILE:
                return "volatile";
            case 0x0080:
                return (target == 3 ? "transient" : null);
            case ACC_NATIVE:
                return "native";
            case ACC_INTERFACE:
                return "interface";
            case ACC_ABSTRACT:
                return "abstract";
            case ACC_STRICT:
                return "strictfp";
            case 0x8000:
                return target == 1 ? "module" : "mandated";
            default:
                return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public int valueOf(String name) {
        switch (name) {
            case "ACC_PUBLIC":
                return ACC_PUBLIC;
            case "ACC_PRIVATE":
                return ACC_PRIVATE;
            case "ACC_PROTECTED":
                return ACC_PROTECTED;
            case "ACC_STATIC":
                return ACC_STATIC;
            case "ACC_FINAL":
                return ACC_FINAL;
            case "ACC_SUPER":
            case "ACC_OPEN":
            case "ACC_TRANSITIVE":
            case "ACC_SYNCHRONIZED":
                return 0x0020;
            case "ACC_STATIC_PHASE":
            case "ACC_VOLATILE":
            case "ACC_BRIDGE":
                return 0x0040;
            case "ACC_TRANSIENT":
            case "ACC_VARARGS":
                return 0x0080;
            case "ACC_NATIVE":
                return ACC_NATIVE;
            case "ACC_INTERFACE":
                return ACC_INTERFACE;
            case "ACC_ABSTRACT":
                return ACC_ABSTRACT;
            case "ACC_STRICT":
                return ACC_STRICT;
            case "ACC_SYNTHETIC":
                return ACC_SYNTHETIC;
            case "ACC_ANNOTATION":
                return ACC_ANNOTATION;
            case "ACC_ENUM":
                return ACC_ENUM;
            case "ACC_MANDATED":
            case "ACC_MODULE":
                return 0x8000;
            default:
                return -1;
        }
    }
}
