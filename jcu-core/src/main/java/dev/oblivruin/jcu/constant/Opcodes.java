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
 * Contains constants for all Java bytecode opcodes as defined in the JVMS.
 * Each constant represents an opcode with its corresponding hexadecimal value.
 *
 * <h2>Opcode Mnemonics</h2>
 * <table border="0">
 * <colgroup><col><col><col></colgroup>
 * <thead>
 *     <tr><th align="center">Constants</th>
 *     <th align="center">Loads</th>
 *     <th align="center">Stores</th>
 * </tr></thead>
 * <tbody><tr>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">00&nbsp;(0x00)</code>&nbsp;{@link #NOP}<br>
 *         <code class="literal">01&nbsp;(0x01)</code>&nbsp;{@link #ACONST_NULL}<br>
 *         <code class="literal">02&nbsp;(0x02)</code>&nbsp;{@link #ICONST_M1}<br>
 *         <code class="literal">03&nbsp;(0x03)</code>&nbsp;{@link #ICONST_0}<br>
 *         <code class="literal">04&nbsp;(0x04)</code>&nbsp;{@link #ICONST_1}<br>
 *         <code class="literal">05&nbsp;(0x05)</code>&nbsp;{@link #ICONST_2}<br>
 *         <code class="literal">06&nbsp;(0x06)</code>&nbsp;{@link #ICONST_3}<br>
 *         <code class="literal">07&nbsp;(0x07)</code>&nbsp;{@link #ICONST_4}<br>
 *         <code class="literal">08&nbsp;(0x08)</code>&nbsp;{@link #ICONST_5}<br>
 *         <code class="literal">09&nbsp;(0x09)</code>&nbsp;{@link #LCONST_0}<br>
 *         <code class="literal">10&nbsp;(0x0a)</code>&nbsp;{@link #LCONST_1}<br>
 *         <code class="literal">11&nbsp;(0x0b)</code>&nbsp;{@link #FCONST_0}<br>
 *         <code class="literal">12&nbsp;(0x0c)</code>&nbsp;{@link #FCONST_1}<br>
 *         <code class="literal">13&nbsp;(0x0d)</code>&nbsp;{@link #FCONST_2}<br>
 *         <code class="literal">14&nbsp;(0x0e)</code>&nbsp;{@link #DCONST_0}<br>
 *         <code class="literal">15&nbsp;(0x0f)</code>&nbsp;{@link #DCONST_1}<br>
 *         <code class="literal">16&nbsp;(0x10)</code>&nbsp;{@link #BIPUSH}<br>
 *         <code class="literal">17&nbsp;(0x11)</code>&nbsp;{@link #SIPUSH}<br>
 *         <code class="literal">18&nbsp;(0x12)</code>&nbsp;{@link #LDC}<br>
 *         <code class="literal">19&nbsp;(0x13)</code>&nbsp;{@link #LDC_W}<br>
 *         <code class="literal">20&nbsp;(0x14)</code>&nbsp;{@link #LDC2_W}<br>
 *     </p></div></td>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">21&nbsp;(0x15)</code>&nbsp;{@link #ILOAD}<br>
 *         <code class="literal">22&nbsp;(0x16)</code>&nbsp;{@link #LLOAD}<br>
 *         <code class="literal">23&nbsp;(0x17)</code>&nbsp;{@link #FLOAD}<br>
 *         <code class="literal">24&nbsp;(0x18)</code>&nbsp;{@link #DLOAD}<br>
 *         <code class="literal">25&nbsp;(0x19)</code>&nbsp;{@link #ALOAD}<br>
 *         <code class="literal">26&nbsp;(0x1a)</code>&nbsp;{@link #ILOAD_0}<br>
 *         <code class="literal">27&nbsp;(0x1b)</code>&nbsp;{@link #ILOAD_1}<br>
 *         <code class="literal">28&nbsp;(0x1c)</code>&nbsp;{@link #ILOAD_2}<br>
 *         <code class="literal">29&nbsp;(0x1d)</code>&nbsp;{@link #ILOAD_3}<br>
 *         <code class="literal">30&nbsp;(0x1e)</code>&nbsp;{@link #LLOAD_0}<br>
 *         <code class="literal">31&nbsp;(0x1f)</code>&nbsp;{@link #LLOAD_1}<br>
 *         <code class="literal">32&nbsp;(0x20)</code>&nbsp;{@link #LLOAD_2}<br>
 *         <code class="literal">33&nbsp;(0x21)</code>&nbsp;{@link #LLOAD_3}<br>
 *         <code class="literal">34&nbsp;(0x22)</code>&nbsp;{@link #FLOAD_0}<br>
 *         <code class="literal">35&nbsp;(0x23)</code>&nbsp;{@link #FLOAD_1}<br>
 *         <code class="literal">36&nbsp;(0x24)</code>&nbsp;{@link #FLOAD_2}<br>
 *         <code class="literal">37&nbsp;(0x25)</code>&nbsp;{@link #FLOAD_3}<br>
 *         <code class="literal">38&nbsp;(0x26)</code>&nbsp;{@link #DLOAD_0}<br>
 *         <code class="literal">39&nbsp;(0x27)</code>&nbsp;{@link #DLOAD_1}<br>
 *         <code class="literal">40&nbsp;(0x28)</code>&nbsp;{@link #DLOAD_2}<br>
 *         <code class="literal">41&nbsp;(0x29)</code>&nbsp;{@link #DLOAD_3}<br>
 *         <code class="literal">42&nbsp;(0x2a)</code>&nbsp;{@link #ALOAD_0}<br>
 *         <code class="literal">43&nbsp;(0x2b)</code>&nbsp;{@link #ALOAD_1}<br>
 *         <code class="literal">44&nbsp;(0x2c)</code>&nbsp;{@link #ALOAD_2}<br>
 *         <code class="literal">45&nbsp;(0x2d)</code>&nbsp;{@link #ALOAD_3}<br>
 *         <code class="literal">46&nbsp;(0x2e)</code>&nbsp;{@link #IALOAD}<br>
 *         <code class="literal">47&nbsp;(0x2f)</code>&nbsp;{@link #LALOAD}<br>
 *         <code class="literal">48&nbsp;(0x30)</code>&nbsp;{@link #FALOAD}<br>
 *         <code class="literal">49&nbsp;(0x31)</code>&nbsp;{@link #DALOAD}<br>
 *         <code class="literal">50&nbsp;(0x32)</code>&nbsp;{@link #AALOAD}<br>
 *         <code class="literal">51&nbsp;(0x33)</code>&nbsp;{@link #BALOAD}<br>
 *         <code class="literal">52&nbsp;(0x34)</code>&nbsp;{@link #CALOAD}<br>
 *         <code class="literal">53&nbsp;(0x35)</code>&nbsp;{@link #SALOAD}<br>
 *     </p></div></td>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">54&nbsp;(0x36)</code>&nbsp;{@link #ISTORE}<br>
 *         <code class="literal">55&nbsp;(0x37)</code>&nbsp;{@link #LSTORE}<br>
 *         <code class="literal">56&nbsp;(0x38)</code>&nbsp;{@link #FSTORE}<br>
 *         <code class="literal">57&nbsp;(0x39)</code>&nbsp;{@link #DSTORE}<br>
 *         <code class="literal">58&nbsp;(0x3a)</code>&nbsp;{@link #ASTORE}<br>
 *         <code class="literal">59&nbsp;(0x3b)</code>&nbsp;{@link #ISTORE_0}<br>
 *         <code class="literal">60&nbsp;(0x3c)</code>&nbsp;{@link #ISTORE_1}<br>
 *         <code class="literal">61&nbsp;(0x3d)</code>&nbsp;{@link #ISTORE_2}<br>
 *         <code class="literal">62&nbsp;(0x3e)</code>&nbsp;{@link #ISTORE_3}<br>
 *         <code class="literal">63&nbsp;(0x3f)</code>&nbsp;{@link #LSTORE_0}<br>
 *         <code class="literal">64&nbsp;(0x40)</code>&nbsp;{@link #LSTORE_1}<br>
 *         <code class="literal">65&nbsp;(0x41)</code>&nbsp;{@link #LSTORE_2}<br>
 *         <code class="literal">66&nbsp;(0x42)</code>&nbsp;{@link #LSTORE_3}<br>
 *         <code class="literal">67&nbsp;(0x43)</code>&nbsp;{@link #FSTORE_0}<br>
 *         <code class="literal">68&nbsp;(0x44)</code>&nbsp;{@link #FSTORE_1}<br>
 *         <code class="literal">69&nbsp;(0x45)</code>&nbsp;{@link #FSTORE_2}<br>
 *         <code class="literal">70&nbsp;(0x46)</code>&nbsp;{@link #FSTORE_3}<br>
 *         <code class="literal">71&nbsp;(0x47)</code>&nbsp;{@link #DSTORE_0}<br>
 *         <code class="literal">72&nbsp;(0x48)</code>&nbsp;{@link #DSTORE_1}<br>
 *         <code class="literal">73&nbsp;(0x49)</code>&nbsp;{@link #DSTORE_2}<br>
 *         <code class="literal">74&nbsp;(0x4a)</code>&nbsp;{@link #DSTORE_3}<br>
 *         <code class="literal">75&nbsp;(0x4b)</code>&nbsp;{@link #ASTORE_0}<br>
 *         <code class="literal">76&nbsp;(0x4c)</code>&nbsp;{@link #ASTORE_1}<br>
 *         <code class="literal">77&nbsp;(0x4d)</code>&nbsp;{@link #ASTORE_2}<br>
 *         <code class="literal">78&nbsp;(0x4e)</code>&nbsp;{@link #ASTORE_3}<br>
 *         <code class="literal">79&nbsp;(0x4f)</code>&nbsp;{@link #IASTORE}<br>
 *         <code class="literal">80&nbsp;(0x50)</code>&nbsp;{@link #LASTORE}<br>
 *         <code class="literal">81&nbsp;(0x51)</code>&nbsp;{@link #FASTORE}<br>
 *         <code class="literal">82&nbsp;(0x52)</code>&nbsp;{@link #DASTORE}<br>
 *         <code class="literal">83&nbsp;(0x53)</code>&nbsp;{@link #AASTORE}<br>
 *         <code class="literal">84&nbsp;(0x54)</code>&nbsp;{@link #BASTORE}<br>
 *         <code class="literal">85&nbsp;(0x55)</code>&nbsp;{@link #CASTORE}<br>
 *         <code class="literal">86&nbsp;(0x56)</code>&nbsp;{@link #SASTORE}<br>
 *     </p></div></td>
 * </tr></tbody>
 * </table>
 *
 * <table border="0">
 * <colgroup><col><col><col></colgroup>
 * <thead><tr>
 *     <th align="center">Stack</th>
 *     <th align="center">Math</th>
 *     <th align="center">Conversions</th>
 * </tr></thead>
 * <tbody><tr>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">87&nbsp;(0x57)</code>&nbsp;{@link #POP}<br>
 *         <code class="literal">88&nbsp;(0x58)</code>&nbsp;{@link #POP2}<br>
 *         <code class="literal">89&nbsp;(0x59)</code>&nbsp;{@link #DUP}<br>
 *         <code class="literal">90&nbsp;(0x5a)</code>&nbsp;{@link #DUP_X1}<br>
 *         <code class="literal">91&nbsp;(0x5b)</code>&nbsp;{@link #DUP_X2}<br>
 *         <code class="literal">92&nbsp;(0x5c)</code>&nbsp;{@link #DUP2}<br>
 *         <code class="literal">93&nbsp;(0x5d)</code>&nbsp;{@link #DUP2_X1}<br>
 *         <code class="literal">94&nbsp;(0x5e)</code>&nbsp;{@link #DUP2_X2}<br>
 *         <code class="literal">95&nbsp;(0x5f)</code>&nbsp;{@link #SWAP}<br>
 *     </p></div></td>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">&nbsp;96&nbsp;(0x60)</code>&nbsp;{@link #IADD}<br>
 *         <code class="literal">&nbsp;97&nbsp;(0x61)</code>&nbsp;{@link #LADD}<br>
 *         <code class="literal">&nbsp;98&nbsp;(0x62)</code>&nbsp;{@link #FADD}<br>
 *         <code class="literal">&nbsp;99&nbsp;(0x63)</code>&nbsp;{@link #DADD}<br>
 *         <code class="literal">100&nbsp;(0x64)</code>&nbsp;{@link #ISUB}<br>
 *         <code class="literal">101&nbsp;(0x65)</code>&nbsp;{@link #LSUB}<br>
 *         <code class="literal">102&nbsp;(0x66)</code>&nbsp;{@link #FSUB}<br>
 *         <code class="literal">103&nbsp;(0x67)</code>&nbsp;{@link #DSUB}<br>
 *         <code class="literal">104&nbsp;(0x68)</code>&nbsp;{@link #IMUL}<br>
 *         <code class="literal">105&nbsp;(0x69)</code>&nbsp;{@link #LMUL}<br>
 *         <code class="literal">106&nbsp;(0x6a)</code>&nbsp;{@link #FMUL}<br>
 *         <code class="literal">107&nbsp;(0x6b)</code>&nbsp;{@link #DMUL}<br>
 *         <code class="literal">108&nbsp;(0x6c)</code>&nbsp;{@link #IDIV}<br>
 *         <code class="literal">109&nbsp;(0x6d)</code>&nbsp;{@link #LDIV}<br>
 *         <code class="literal">110&nbsp;(0x6e)</code>&nbsp;{@link #FDIV}<br>
 *         <code class="literal">111&nbsp;(0x6f)</code>&nbsp;{@link #DDIV}<br>
 *         <code class="literal">112&nbsp;(0x70)</code>&nbsp;{@link #IREM}<br>
 *         <code class="literal">113&nbsp;(0x71)</code>&nbsp;{@link #LREM}<br>
 *         <code class="literal">114&nbsp;(0x72)</code>&nbsp;{@link #FREM}<br>
 *         <code class="literal">115&nbsp;(0x73)</code>&nbsp;{@link #DREM}<br>
 *         <code class="literal">116&nbsp;(0x74)</code>&nbsp;{@link #INEG}<br>
 *         <code class="literal">117&nbsp;(0x75)</code>&nbsp;{@link #LNEG}<br>
 *         <code class="literal">118&nbsp;(0x76)</code>&nbsp;{@link #FNEG}<br>
 *         <code class="literal">119&nbsp;(0x77)</code>&nbsp;{@link #DNEG}<br>
 *         <code class="literal">120&nbsp;(0x78)</code>&nbsp;{@link #ISHL}<br>
 *         <code class="literal">121&nbsp;(0x79)</code>&nbsp;{@link #LSHL}<br>
 *         <code class="literal">122&nbsp;(0x7a)</code>&nbsp;{@link #ISHR}<br>
 *         <code class="literal">123&nbsp;(0x7b)</code>&nbsp;{@link #LSHR}<br>
 *         <code class="literal">124&nbsp;(0x7c)</code>&nbsp;{@link #IUSHR}<br>
 *         <code class="literal">125&nbsp;(0x7d)</code>&nbsp;{@link #LUSHR}<br>
 *         <code class="literal">126&nbsp;(0x7e)</code>&nbsp;{@link #IAND}<br>
 *         <code class="literal">127&nbsp;(0x7f)</code>&nbsp;{@link #LAND}<br>
 *         <code class="literal">128&nbsp;(0x80)</code>&nbsp;{@link #IOR}<br>
 *         <code class="literal">129&nbsp;(0x81)</code>&nbsp;{@link #LOR}<br>
 *         <code class="literal">130&nbsp;(0x82)</code>&nbsp;{@link #IXOR}<br>
 *         <code class="literal">131&nbsp;(0x83)</code>&nbsp;{@link #LXOR}<br>
 *         <code class="literal">132&nbsp;(0x84)</code>&nbsp;{@link #IINC}<br>
 *     </p></div></td>
 *     <td><div class="literallayout"><p><br>
 *         <code class="literal">133&nbsp;(0x85)</code>&nbsp;{@link #I2L}<br>
 *         <code class="literal">134&nbsp;(0x86)</code>&nbsp;{@link #I2F}<br>
 *         <code class="literal">135&nbsp;(0x87)</code>&nbsp;{@link #I2D}<br>
 *         <code class="literal">136&nbsp;(0x88)</code>&nbsp;{@link #L2I}<br>
 *         <code class="literal">137&nbsp;(0x89)</code>&nbsp;{@link #L2F}<br>
 *         <code class="literal">138&nbsp;(0x8a)</code>&nbsp;{@link #L2D}<br>
 *         <code class="literal">139&nbsp;(0x8b)</code>&nbsp;{@link #F2I}<br>
 *         <code class="literal">140&nbsp;(0x8c)</code>&nbsp;{@link #F2L}<br>
 *         <code class="literal">141&nbsp;(0x8d)</code>&nbsp;{@link #F2D}<br>
 *         <code class="literal">142&nbsp;(0x8e)</code>&nbsp;{@link #D2I}<br>
 *         <code class="literal">143&nbsp;(0x8f)</code>&nbsp;{@link #D2L}<br>
 *         <code class="literal">144&nbsp;(0x90)</code>&nbsp;{@link #D2F}<br>
 *         <code class="literal">145&nbsp;(0x91)</code>&nbsp;{@link #I2B}<br>
 *         <code class="literal">146&nbsp;(0x92)</code>&nbsp;{@link #I2C}<br>
 *         <code class="literal">147&nbsp;(0x93)</code>&nbsp;{@link #I2S}<br>
 *     </p></div></td>
 * </tr></tbody>
 * </table>
 *
 * <table border="0">
 * <colgroup><col><col></colgroup>
 * <tbody><tr>
 *     <td><div class="table">
 *         <div class="table-contents">
 *             <table border="0">
 *                 <colgroup><col></colgroup>
 *                 <thead><tr><th align="center">Comparisons</th></tr></thead>
 *                 <tbody><tr><td><div class="literallayout"><p><br>
 *                     <code class="literal">148&nbsp;(0x94)</code>&nbsp;{@link #LCMP}<br>
 *                         <code class="literal">149&nbsp;(0x95)</code>&nbsp;{@link #FCMPL}<br>
 *                         <code class="literal">150&nbsp;(0x96)</code>&nbsp;{@link #FCMPG}<br>
 *                         <code class="literal">151&nbsp;(0x97)</code>&nbsp;{@link #DCMPL}<br>
 *                         <code class="literal">152&nbsp;(0x98)</code>&nbsp;{@link #DCMPG}<br>
 *                         <code class="literal">153&nbsp;(0x99)</code>&nbsp;{@link #IFEQ}<br>
 *                         <code class="literal">154&nbsp;(0x9a)</code>&nbsp;{@link #IFNE}<br>
 *                         <code class="literal">155&nbsp;(0x9b)</code>&nbsp;{@link #IFLT}<br>
 *                         <code class="literal">156&nbsp;(0x9c)</code>&nbsp;{@link #IFGE}<br>
 *                         <code class="literal">157&nbsp;(0x9d)</code>&nbsp;{@link #IFGT}<br>
 *                         <code class="literal">158&nbsp;(0x9e)</code>&nbsp;{@link #IFLE}<br>
 *                         <code class="literal">159&nbsp;(0x9f)</code>&nbsp;{@link #IF_ICMPEQ}<br>
 *                         <code class="literal">160&nbsp;(0xa0)</code>&nbsp;{@link #IF_ICMPNE}<br>
 *                         <code class="literal">161&nbsp;(0xa1)</code>&nbsp;{@link #IF_ICMPLT}<br>
 *                         <code class="literal">162&nbsp;(0xa2)</code>&nbsp;{@link #IF_ICMPGE}<br>
 *                         <code class="literal">163&nbsp;(0xa3)</code>&nbsp;{@link #IF_ICMPGT}<br>
 *                         <code class="literal">164&nbsp;(0xa4)</code>&nbsp;{@link #IF_ICMPLE}<br>
 *                         <code class="literal">165&nbsp;(0xa5)</code>&nbsp;{@link #IF_ACMPEQ}<br>
 *                         <code class="literal">166&nbsp;(0xa6)</code>&nbsp;{@link #IF_ACMPNE}<br>
 *             </p></div></td></tr></tbody></table>
 *             <table border="0">
 *                 <colgroup><col></colgroup>
 *                 <thead><tr><th align="center">Control</th></tr></thead>
 *                 <tbody><tr><td><div class="literallayout"><p><br>
 *                         <code class="literal">167&nbsp;(0xa7)</code>&nbsp;{@link #GOTO}<br>
 *                         <code class="literal">168&nbsp;(0xa8)</code>&nbsp;{@link #JSR}<br>
 *                         <code class="literal">169&nbsp;(0xa9)</code>&nbsp;{@link #RET}<br>
 *                         <code class="literal">170&nbsp;(0xaa)</code>&nbsp;{@link #TABLESWITCH}<br>
 *                         <code class="literal">171&nbsp;(0xab)</code>&nbsp;{@link #LOOKUPSWITCH}<br>
 *                         <code class="literal">172&nbsp;(0xac)</code>&nbsp;{@link #IRETURN}<br>
 *                         <code class="literal">173&nbsp;(0xad)</code>&nbsp;{@link #LRETURN}<br>
 *                         <code class="literal">174&nbsp;(0xae)</code>&nbsp;{@link #FRETURN}<br>
 *                         <code class="literal">175&nbsp;(0xaf)</code>&nbsp;{@link #DRETURN}<br>
 *                         <code class="literal">176&nbsp;(0xb0)</code>&nbsp;{@link #ARETURN}<br>
 *                         <code class="literal">177&nbsp;(0xb1)</code>&nbsp;{@link #RETURN}<br>
 *             </p></div></td></tr></tbody></table>
 *         </div>
 *     </div><br class="table-break"></td>
 *     <td><div class="table">
 *         <div class="table-contents">
 *             <table border="0">
 *                 <colgroup><col></colgroup>
 *                 <thead><tr><th align="center">References</th></tr></thead>
 *                 <tbody><tr><td><div class="literallayout"><p><br>
 *                         <code class="literal">178&nbsp;(0xb2)</code>&nbsp;{@link #GETSTATIC}<br>
 *                         <code class="literal">179&nbsp;(0xb3)</code>&nbsp;{@link #PUTSTATIC}<br>
 *                         <code class="literal">180&nbsp;(0xb4)</code>&nbsp;{@link #GETFIELD}<br>
 *                         <code class="literal">181&nbsp;(0xb5)</code>&nbsp;{@link #PUTFIELD}<br>
 *                         <code class="literal">182&nbsp;(0xb6)</code>&nbsp;{@link #INVOKEVIRTUAL}<br>
 *                         <code class="literal">183&nbsp;(0xb7)</code>&nbsp;{@link #INVOKESPECIAL}<br>
 *                         <code class="literal">184&nbsp;(0xb8)</code>&nbsp;{@link #INVOKESTATIC}<br>
 *                         <code class="literal">185&nbsp;(0xb9)</code>&nbsp;{@link #INVOKEINTERFACE}<br>
 *                         <code class="literal">186&nbsp;(0xba)</code>&nbsp;{@link #INVOKEDYNAMIC}<br>
 *                         <code class="literal">187&nbsp;(0xbb)</code>&nbsp;{@link #NEW}<br>
 *                         <code class="literal">188&nbsp;(0xbc)</code>&nbsp;{@link #NEWARRAY}<br>
 *                         <code class="literal">189&nbsp;(0xbd)</code>&nbsp;{@link #ANEWARRAY}<br>
 *                         <code class="literal">190&nbsp;(0xbe)</code>&nbsp;{@link #ARRAYLENGTH}<br>
 *                         <code class="literal">191&nbsp;(0xbf)</code>&nbsp;{@link #ATHROW}<br>
 *                         <code class="literal">192&nbsp;(0xc0)</code>&nbsp;{@link #CHECKCAST}<br>
 *                         <code class="literal">193&nbsp;(0xc1)</code>&nbsp;{@link #INSTANCEOF}<br>
 *                         <code class="literal">194&nbsp;(0xc2)</code>&nbsp;{@link #MONITORENTER}<br>
 *                         <code class="literal">195&nbsp;(0xc3)</code>&nbsp;{@link #MONITOREXIT}<br>
 *             </p></div></td></tr></tbody></table>
 *             <table border="0">
 *                 <colgroup><col></colgroup>
 *                 <thead><tr><th align="center">Extended</th></tr></thead>
 *                 <tbody><tr><td><div class="literallayout"><p><br>
 *                         <code class="literal">196&nbsp;(0xc4)</code>&nbsp;{@link #WIDE}<br>
 *                         <code class="literal">197&nbsp;(0xc5)</code>&nbsp;{@link #MULTIANEWARRAY}<br>
 *                         <code class="literal">198&nbsp;(0xc6)</code>&nbsp;{@link #IFNULL}<br>
 *                         <code class="literal">199&nbsp;(0xc7)</code>&nbsp;{@link #IFNONNULL}<br>
 *                         <code class="literal">200&nbsp;(0xc8)</code>&nbsp;{@link #GOTO_W}<br>
 *                         <code class="literal">201&nbsp;(0xc9)</code>&nbsp;{@link #JSR_W}<br>
 *                 </p></div></td></tr></tbody></table>
 *             <table border="0">
 *                 <colgroup><col></colgroup>
 *                 <thead><tr><th align="center">Reserved</th></tr></thead>
 *                 <tbody><tr><td><div class="literallayout"><p><br>
 *                         <code class="literal">202&nbsp;(0xca)</code>&nbsp;{@code BREAKPOINT}<br>
 *                         <code class="literal">254&nbsp;(0xfe)</code>&nbsp;{@code IMPDEP1}<br>
 *                         <code class="literal">255&nbsp;(0xff)</code>&nbsp;{@code IMPDEP2}<br>
 *                 </p></div></td></tr></tbody></table>
 * </div></div><br class="table-break"></td></tr></tbody></table>
 *
 * <p>
 * This documentation includes content derived from the Java Virtual Machine Specification (JVMS)
 * for accuracy and completeness. Original JVMS content is copyrighted by Oracle and/or its affiliates.
 * </p>
 *
 * @author OblivRuinDev
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-6.html">jvms-6: Instruction Set</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-7.html">jvms-7: Opcode Mnemonics</a>
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class Opcodes { private Opcodes() {}
    /**
     * Do nothing
     */
    public static final int NOP = 0;
    /**
     * Push {@code null}
     */
    public static final int ACONST_NULL = 1;
    /**
     * Push {@code int -1}
     */
    public static final int ICONST_M1 = 2;
    /**
     * Push {@code int 0}
     */
    public static final int ICONST_0 = 3;
    /**
     * Push {@code int 1}
     */
    public static final int ICONST_1 = 4;
    /**
     * Push {@code int 2}
     */
    public static final int ICONST_2 = 5;
    /**
     * Push {@code int 3}
     */
    public static final int ICONST_3 = 6;
    /**
     * Push {@code int 4}
     */
    public static final int ICONST_4 = 7;
    /**
     * Push {@code int 5}
     */
    public static final int ICONST_5 = 8;
    /**
     * Push {@code long 0}
     */
    public static final int LCONST_0 = 9;
    /**
     * Push {@code long 1}
     */
    public static final int LCONST_1 = 10;
    /**
     * Push {@code double 0.0}
     */
    public static final int FCONST_0 = 11;
    /**
     * Push {@code double 1.0}
     */
    public static final int FCONST_1 = 12;
    /**
     * Push {@code double 2.0}
     */
    public static final int FCONST_2 = 13;
    /**
     * Push {@code double 0.0}
     */
    public static final int DCONST_0 = 14;
    /**
     * Push {@code double 1.0}
     */
    public static final int DCONST_1 = 15;
    /**
     * Push {@code byte}
     */
    public static final int BIPUSH = 16;
    /**
     * Push {@code short}
     */
    public static final int SIPUSH = 17;
    /**
     * Push item from run-time constant pool
     */
    public static final int LDC = 18;
    /**
     * Push item from run-time constant pool (wide index)
     */
    public static final int LDC_W = 19;
    /**
     * Push {@code long} or {@code double} from run-time constant pool (wide index)
     */
    public static final int LDC2_W = 20;

    /**
     * Load {@code int} from local variable
     */
    public static final int ILOAD = 21;
    /**
     * Load {@code long} from local variable
     */
    public static final int LLOAD = 22;
    /**
     * Load {@code float} from local variable
     */
    public static final int FLOAD = 23;
    /**
     * Load {@code double} from local variable
     */
    public static final int DLOAD = 24;
    /**
     * Load {@code reference} from local variable
     */
    public static final int ALOAD = 25;
    /** @see #ILOAD */
    public static final int ILOAD_0 = 26;
    /** @see #ILOAD */
    public static final int ILOAD_1 = 27;
    /** @see #ILOAD */
    public static final int ILOAD_2 = 28;
    /** @see #ILOAD */
    public static final int ILOAD_3 = 29;
    /** @see #LLOAD */
    public static final int LLOAD_0 = 30;
    /** @see #LLOAD */
    public static final int LLOAD_1 = 31;
    /** @see #LLOAD */
    public static final int LLOAD_2 = 32;
    /** @see #LLOAD */
    public static final int LLOAD_3 = 33;
    /** @see #FLOAD */
    public static final int FLOAD_0 = 34;
    /** @see #FLOAD */
    public static final int FLOAD_1 = 35;
    /** @see #FLOAD */
    public static final int FLOAD_2 = 36;
    /** @see #FLOAD */
    public static final int FLOAD_3 = 37;
    /** @see #DLOAD */
    public static final int DLOAD_0 = 38;
    /** @see #DLOAD */
    public static final int DLOAD_1 = 39;
    /** @see #DLOAD */
    public static final int DLOAD_2 = 40;
    /** @see #DLOAD */
    public static final int DLOAD_3 = 41;
    /** @see #ALOAD */
    public static final int ALOAD_0 = 42;
    /** @see #ALOAD */
    public static final int ALOAD_1 = 43;
    /** @see #ALOAD */
    public static final int ALOAD_2 = 44;
    /** @see #ALOAD */
    public static final int ALOAD_3 = 45;

    /**
     * Load {@code int} from array
     */
    public static final int IALOAD = 46;
    /**
     * Load {@code long} from array
     */
    public static final int LALOAD = 47;
    /**
     * Load {@code float} from array
     */
    public static final int FALOAD = 48;
    /**
     * Load {@code double} from array
     */
    public static final int DALOAD = 49;
    /**
     * Load {@code reference} from array
     */
    public static final int AALOAD = 50;
    /**
     * Load {@code byte} or {@code boolean} from array
     */
    public static final int BALOAD = 51;
    /**
     * Load {@code char} from array
     */
    public static final int CALOAD = 52;
    /**
     * Load {@code short} from array
     */
    public static final int SALOAD = 53;

    /**
     * Store {@code int} into local variable
     */
    public static final int ISTORE = 54;
    /**
     * Store {@code long} into local variable
     */
    public static final int LSTORE = 55;
    /**
     * Store {@code float} into local variable
     */
    public static final int FSTORE = 56;
    /**
     * Store {@code double} into local variable
     */
    public static final int DSTORE = 57;
    /**
     * Store {@code reference} into local variable
     */
    public static final int ASTORE = 58;
    /** @see #ISTORE */
    public static final int ISTORE_0 = 59;
    /** @see #ISTORE */
    public static final int ISTORE_1 = 60;
    /** @see #ISTORE */
    public static final int ISTORE_2 = 61;
    /** @see #ISTORE */
    public static final int ISTORE_3 = 62;
    /** @see #LSTORE */
    public static final int LSTORE_0 = 63;
    /** @see #LSTORE */
    public static final int LSTORE_1 = 64;
    /** @see #LSTORE */
    public static final int LSTORE_2 = 65;
    /** @see #LSTORE */
    public static final int LSTORE_3 = 66;
    /** @see #FSTORE */
    public static final int FSTORE_0 = 67;
    /** @see #FSTORE */
    public static final int FSTORE_1 = 68;
    /** @see #FSTORE */
    public static final int FSTORE_2 = 69;
    /** @see #FSTORE */
    public static final int FSTORE_3 = 70;
    /** @see #DSTORE */
    public static final int DSTORE_0 = 71;
    /** @see #DSTORE */
    public static final int DSTORE_1 = 72;
    /** @see #DSTORE */
    public static final int DSTORE_2 = 73;
    /** @see #DSTORE */
    public static final int DSTORE_3 = 74;
    /** @see #ASTORE */
    public static final int ASTORE_0 = 75;
    /** @see #ASTORE */
    public static final int ASTORE_1 = 76;
    /** @see #ASTORE */
    public static final int ASTORE_2 = 77;
    /** @see #ASTORE */
    public static final int ASTORE_3 = 78;

    /**
     * Store into {@code int} array
     */
    public static final int IASTORE = 79;
    /**
     * Store into {@code long} array
     */
    public static final int LASTORE = 80;
    /**
     * Store into {@code float} array
     */
    public static final int FASTORE = 81;
    /**
     * Store into {@code double} array
     */
    public static final int DASTORE = 82;
    /**
     * Store into {@code reference} array
     */
    public static final int AASTORE = 83;
    /**
     * Store into {@code byte} or {@code boolean} array
     */
    public static final int BASTORE = 84;
    /**
     * Store into {@code char} array
     */
    public static final int CASTORE = 85;
    /**
     * Store into {@code short} array
     */
    public static final int SASTORE = 86;

    /**
     * Pop the top operand stack value
     */
    public static final int POP = 87;
    /**
     * Pop the top one or two operand stack values
     */
    public static final int POP2 = 88;
    /**
     * Duplicate the top operand stack value
     */
    public static final int DUP = 89;
    /**
     * Duplicate the top operand stack value and insert two values down
     */
    public static final int DUP_X1 = 90;
    /**
     * Duplicate the top operand stack value and insert two or three values down
     */
    public static final int DUP_X2 = 91;
    /**
     * Duplicate the top one or two operand stack values
     */
    public static final int DUP2 = 92;
    /**
     * Duplicate the top one or two operand stack values and insert two or three values down
     */
    public static final int DUP2_X1 = 93;
    /**
     * Duplicate the top one or two operand stack values and insert two, three, or four values down
     */
    public static final int DUP2_X2 = 94;
    /**
     * Swap the top two operand stack values
     */
    public static final int SWAP = 95;

    /**
     * Add {@code int}
     */
    public static final int IADD = 96;
    /**
     * Add {@code long}
     */
    public static final int LADD = 97;
    /**
     * Add {@code float}
     */
    public static final int FADD = 98;
    /**
     * Add {@code double}
     */
    public static final int DADD = 99;

    /**
     * Subtract {@code int}
     */
    public static final int ISUB = 100;
    /**
     * Subtract {@code long}
     */
    public static final int LSUB = 101;
    /**
     * Subtract {@code float}
     */
    public static final int FSUB = 102;
    /**
     * Subtract {@code double}
     */
    public static final int DSUB = 103;

    /**
     * Multiply {@code int}
     */
    public static final int IMUL = 104;
    /**
     * Multiply {@code long}
     */
    public static final int LMUL = 105;
    /**
     * Multiply {@code float}
     */
    public static final int FMUL = 106;
    /**
     * Multiply {@code double}
     */
    public static final int DMUL = 107;

    /**
     * Divide {@code int}
     */
    public static final int IDIV = 108;
    /**
     * Divide {@code long}
     */
    public static final int LDIV = 109;
    /**
     * Divide {@code float}
     */
    public static final int FDIV = 110;
    /**
     * Divide {@code double}
     */
    public static final int DDIV = 111;

    /**
     * Remainder {@code int}
     */
    public static final int IREM = 112;
    /**
     * Remainder {@code long}
     */
    public static final int LREM = 113;
    /**
     * Remainder {@code float}
     */
    public static final int FREM = 114;
    /**
     * Remainder {@code double}
     */
    public static final int DREM = 115;

    /**
     * Negate {@code int}
     */
    public static final int INEG = 116;
    /**
     * Negate {@code long}
     */
    public static final int LNEG = 117;
    /**
     * Negate {@code float}
     */
    public static final int FNEG = 118;
    /**
     * Negate {@code double}
     */
    public static final int DNEG = 119;

    /**
     * Shift left {@code int}
     */
    public static final int ISHL = 120;
    /**
     * Shift left {@code long}
     */
    public static final int LSHL = 121;

    /**
     * Arithmetic shift right {@code int}
     */
    public static final int ISHR = 122;
    /**
     * Arithmetic shift right {@code long}
     */
    public static final int LSHR = 122;

    /**
     * Logical shift right {@code int}
     */
    public static final int IUSHR = 124;
    /**
     * Logical shift right {@code long}
     */
    public static final int LUSHR = 125;

    /**
     * Boolean AND {@code int}
     */
    public static final int IAND = 126;
    /**
     * Boolean AND {@code long}
     */
    public static final int LAND = 127;

    /**
     * Boolean OR {@code int}
     */
    public static final int IOR = 128;
    /**
     * Boolean OR {@code long}
     */
    public static final int LOR = 129;

    /**
     * Boolean XOR {@code int}
     */
    public static final int IXOR = 130;
    /**
     * Boolean XOR {@code long}
     */
    public static final int LXOR = 131;

    /**
     * Increment local variable by constant
     */
    public static final int IINC = 132;

    /**
     * Convert {@code int} to {@code long}
     */
    public static final int I2L = 133;
    /**
     * Convert {@code int} to {@code float}
     */
    public static final int I2F = 134;
    /**
     * Convert {@code int} to {@code double}
     */
    public static final int I2D = 135;
    /**
     * Convert {@code long} to {@code int}
     */
    public static final int L2I = 136;
    /**
     * Convert {@code long} to {@code float}
     */
    public static final int L2F = 137;
    /**
     * Convert {@code long} to {@code double}
     */
    public static final int L2D = 138;
    /**
     * Convert {@code float} to {@code int}
     */
    public static final int F2I = 139;
    /**
     * Convert {@code float} to {@code long}
     */
    public static final int F2L = 140;
    /**
     * Convert {@code float} to {@code double}
     */
    public static final int F2D = 141;
    /**
     * Convert {@code double} to {@code int}
     */
    public static final int D2I = 142;
    /**
     * Convert {@code double} to {@code long}
     */
    public static final int D2L = 143;
    /**
     * Convert {@code double} to {@code float}
     */
    public static final int D2F = 144;
    /**
     * Convert {@code int} to {@code byte}
     */
    public static final int I2B = 145;
    /**
     * Convert {@code int} to {@code char}
     */
    public static final int I2C = 146;
    /**
     * Convert {@code int} to {@code short}
     */
    public static final int I2S = 147;

    /**
     * Compare {@code long}
     */
    public static final int LCMP = 148;
    /**
     * Compare {@code float}
     */
    public static final int FCMPL = 149;
    /**
     * Compare {@code float}
     */
    public static final int FCMPG = 150;
    /**
     * Compare {@code double}
     */
    public static final int DCMPL = 151;//todo: float number doc
    /**
     * Compare {@code double}
     */
    public static final int DCMPG = 152;

    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value = 0
     */
    public static final int IFEQ = 153;
    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value ≠ 0
     */
    public static final int IFNE = 154;
    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value < 0
     */
    public static final int IFLT = 155;
    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value ≥ 0
     */
    public static final int IFGE = 156;
    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value > 0
     */
    public static final int IFGT = 157;
    /**
     * Branch if {@code int} comparison with zero succeeds if and only if value ≤ 0
     */
    public static final int IFLE = 158;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 = value2
     */
    public static final int IF_ICMPEQ = 159;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 ≠ value2
     */
    public static final int IF_ICMPNE = 160;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 < value2
     */
    public static final int IF_ICMPLT = 161;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 ≥ value2
     */
    public static final int IF_ICMPGE = 162;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 > value2
     */
    public static final int IF_ICMPGT = 163;
    /**
     * Branch if {@code int} comparsion succeeds if and only if value1 ≤ value2
     */
    public static final int IF_ICMPLE = 164;
    /**
     * Branch if {@code reference} comparsion succeeds if and only if value1 = value2
     */
    public static final int IF_ACMPEQ = 165;
    /**
     * Branch if {@code reference} comparsion succeeds if and only if value1 ≠ value2
     */
    public static final int IF_ACMPNE = 166;
    /**
     * Branch always
     */
    public static final int GOTO = 167;
    /**
     * Jump subroutine
     */
    public static final int JSR = 168;
    /**
     * Return from subroutine
     */
    public static final int RET = 169;

    /**
     * Access jump table by index and jump
     */
    public static final int TABLESWITCH = 170;
    /**
     * Access jump table by key match and jump
     */
    public static final int LOOKUPSWITCH = 171;

    /**
     * Return {@code int} from method
     */
    public static final int IRETURN = 172;
    /**
     * Return {@code long} from method
     */
    public static final int LRETURN = 173;
    /**
     * Return {@code float} from method
     */
    public static final int FRETURN = 174;
    /**
     * Return {@code double} from method
     */
    public static final int DRETURN = 175;
    /**
     * Return {@code reference} from method
     */
    public static final int ARETURN = 176;
    /**
     * Return {@code void} from method
     */
    public static final int RETURN = 177;

    /**
     * Get {@code static} field from class
     */
    public static final int GETSTATIC = 178;
    /**
     * Set {@code static} field in class
     */
    public static final int PUTSTATIC = 179;
    /**
     * Fetch field from object
     */
    public static final int GETFIELD = 180;
    /**
     * Set field in object
     */
    public static final int PUTFIELD = 181;

    /**
     * Invoke instance method; dispatch based on class
     */
    public static final int INVOKEVIRTUAL = 182;
    /**
     * Invoke instance method; special handling for superclass, private, and instance initialization method invocations
     */
    public static final int INVOKESPECIAL = 183;
    /**
     * Invoke a class (static) method
     */
    public static final int INVOKESTATIC = 184;
    /**
     * Invoke interface method
     */
    public static final int INVOKEINTERFACE = 185;
    /**
     * Invoke dynamic method
     * @since 1.7
     */
    public static final int INVOKEDYNAMIC = 186;
    /**
     * Create new object
     */
    public static final int NEW = 187;
    /**
     * Create new array
     *
     * @see ArrayType
     */
    public static final int NEWARRAY = 188;
    /**
     * Create new array of {@code reference}
     */
    public static final int ANEWARRAY = 189;
    /**
     * Get length of array
     */
    public static final int ARRAYLENGTH = 190;
    /**
     * Throw exception or error
     */
    public static final int ATHROW = 191;

    /**
     * Check whether object is of given type
     */
    public static final int CHECKCAST = 192;
    /**
     * Determine if object is of given type
     */
    public static final int INSTANCEOF = 193;

    /**
     * Enter monitor for object
     */
    public static final int MONITORENTER = 194;
    /**
     * Exit monitor for object
     */
    public static final int MONITOREXIT = 195;

    /**
     * Extend local variable index by additional bytes
     */
    public static final int WIDE = 196;

    /**
     * Create new multidimensional array
     */
    public static final int MULTIANEWARRAY = 197;

    /**
     * Branch if {@code reference} is {@code null}
     */
    public static final int IFNULL = 198;
    /**
     * Branch if {@code reference} not {@code null}
     */
    public static final int IFNONNULL = 199;
    /**
     * Branch always (wide index)
     */
    public static final int GOTO_W = 200;
    /**
     * Jump subroutine (wide index)
     */
    public static final int JSR_W = 201;
}
