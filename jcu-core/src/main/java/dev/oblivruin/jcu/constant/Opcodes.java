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
 * @author OblivRuinDev
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-6.html">jvms-6</a>
 */
public final class Opcodes {//todo
    public static final int IINC = 132; // visitIincInsn
    public static final int I2L = 133; // visitInsn
    public static final int I2F = 134; // -
    public static final int I2D = 135; // -
    public static final int L2I = 136; // -
    public static final int L2F = 137; // -
    public static final int L2D = 138; // -
    public static final int F2I = 139; // -
    public static final int F2L = 140; // -
    public static final int F2D = 141; // -
    public static final int D2I = 142; // -
    public static final int D2L = 143; // -
    public static final int D2F = 144; // -
    public static final int I2B = 145; // -
    public static final int I2C = 146; // -
    public static final int I2S = 147; // -
    public static final int LCMP = 148; // -
    public static final int FCMPL = 149; // -
    public static final int FCMPG = 150; // -
    public static final int DCMPL = 151; // -
    public static final int DCMPG = 152; // -

    private Opcodes() {}
    public static final int NOP = 0;
    public static final int aconst_null = 1;
    public static final int iconst_m1 = 2;
    public static final int iconst_0 = 3;
    public static final int iconst_1 = 4; // -
    public static final int iconst_2 = 5; // -
    public static final int ICONST_3 = 6; // -
    public static final int ICONST_4 = 7; // -
    public static final int ICONST_5 = 8; // -
    public static final int LCONST_0 = 9; // -
    public static final int LCONST_1 = 10; // -
    public static final int FCONST_0 = 11; // -
    public static final int FCONST_1 = 12; // -
    public static final int FCONST_2 = 13; // -
    public static final int DCONST_0 = 14; // -
    public static final int DCONST_1 = 15; // -
    public static final int IALOAD = 46; // visitInsn
    public static final int LALOAD = 47; // -
    public static final int FALOAD = 48; // -
    public static final int DALOAD = 49; // -
    public static final int AALOAD = 50; // -
    public static final int BALOAD = 51; // -
    public static final int CALOAD = 52; // -
    public static final int SALOAD = 53; // -
    public static final int IASTORE = 79; // visitInsn
    public static final int LASTORE = 80; // -
    public static final int FASTORE = 81; // -
    public static final int DASTORE = 82; // -
    public static final int AASTORE = 83; // -
    public static final int BASTORE = 84; // -
    public static final int CASTORE = 85; // -
    public static final int SASTORE = 86; // -
    public static final int POP = 87; // -
    public static final int POP2 = 88; // -
    public static final int DUP = 89; // -
    public static final int DUP_X1 = 90; // -
    public static final int DUP_X2 = 91; // -
    public static final int DUP2 = 92; // -
    public static final int DUP2_X1 = 93; // -
    public static final int DUP2_X2 = 94; // -
    public static final int SWAP = 95; // -
    public static final int IADD = 96; // -
    public static final int LADD = 97; // -
    public static final int FADD = 98; // -
    public static final int DADD = 99; // -
    public static final int ISUB = 100; // -
    public static final int LSUB = 101; // -
    public static final int FSUB = 102; // -
    public static final int DSUB = 103; // -
    public static final int IMUL = 104; // -
    public static final int LMUL = 105; // -
    public static final int FMUL = 106; // -
    public static final int DMUL = 107; // -
    public static final int IDIV = 108; // -
    public static final int LDIV = 109; // -
    public static final int FDIV = 110; // -
    public static final int DDIV = 111; // -
    public static final int IREM = 112; // -
    public static final int LREM = 113; // -
    public static final int FREM = 114; // -
    public static final int DREM = 115; // -
    public static final int INEG = 116; // -
    public static final int LNEG = 117; // -
    public static final int FNEG = 118; // -
    public static final int DNEG = 119; // -
    public static final int ISHL = 120; // -
    public static final int LSHL = 121; // -
    public static final int ISHR = 122; // -
    public static final int LSHR = 123; // -
    public static final int IUSHR = 124; // -
    public static final int LUSHR = 125; // -
    public static final int IAND = 126; // -
    public static final int LAND = 127; // -
    public static final int IOR = 128; // -
    public static final int LOR = 129; // -
    public static final int IXOR = 130; // -
    public static final int LXOR = 131; // -

    public static final class Int { private Int() {}
        public static final int BIPUSH = 16;
        public static final int SIPUSH = 17; // -
    }

    public static final int LDC = 18;

    public static final class Var { private Var() {}
        public static final int ILOAD = 21; // visitVarInsn
        public static final int LLOAD = 22; // -
        public static final int FLOAD = 23; // -
        public static final int DLOAD = 24; // -
        public static final int ALOAD = 25; // -
        public static final int ISTORE = 54; // visitVarInsn
        public static final int LSTORE = 55; // -
        public static final int FSTORE = 56; // -
        public static final int DSTORE = 57; // -
        public static final int ASTORE = 58; // -

        public static final int RET = 169;
    }

    public static final class Field { private Field() {}
        public static final int GETSTATIC = 178; // visitFieldInsn
        public static final int PUTSTATIC = 179; // -
        public static final int GETFIELD = 180; // -
        public static final int PUTFIELD = 181; // -
    }

    public static final class Method { private Method() {}
        public static final int INVOKEVIRTUAL = 182; // visitMethodInsn
        public static final int INVOKESPECIAL = 183; // -
        public static final int INVOKESTATIC = 184; // -
        public static final int INVOKEINTERFACE = 185; // -
    }
}
