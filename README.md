# JCU, designed for performance

![License](https://img.shields.io/badge/License-Apache_2.0--Clause-blue.svg)
[![Project Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)](https://github.com/OblivRuinDev/AsmX)
![Java Requirement](https://img.shields.io/badge/Java_Requirement-1.8+-red.svg)

Java Classfile Util, which provides low-level primitives for interacting with class bytecodes and unsafe but fast APIs

> [!NOTE]
> Apart from jcu-core, all other APIs are still in the early development stage.

~~JCU, is it the brother of ICU?~~

---

## Project features:
- Advanced Bytecode Manipulation:
  - Low-level primitives for classfile parsing and generation
  - Direct access to constant pool and method bytecode


- JDK-specific Optimizations:
  - Extremely radical strategies tailored for specific JDK internals (must be manually enabled, see below for details)
  - Version-specific optimizations that leverage newer APIs


- Ultimate performance:
  - jcu-core is designed from the ground up for maximum performance
  - Fast Class: We provide some extremely radical strategies specifically for JDK internal of the particular version, to enable those, you should add some arguments to your JVM:
    ```
    --add-exports=java.base/jdk.internal.misc=dev.oblivruin.jcu.core
    --add-opens=java.base/java.lang=dev.oblivruin.jcu.core
    ```
    And initialize the optimizations before using other APIs:
    `dev.oblivruin.jcu.misc.Internal.loadFastC()`

    | Java    | Support |
    |---------|---------|
    | JDK 1.8 | ×       |
    | JDK 9   | √       |
    | JDK 10  | √       |
    | JDK 11  | √       |
    | JDK 12  | √       |
    | JDK 13  | √       |
    | JDK 14  | √       |
    | JDK 15  | √       |
    | JDK 16  | √       |
    | JDK 17  | √       |
    | JDK 18  | √       |
    | JDK 19  | √       |
    | JDK 20  | √       |
    | JDK 21  | √       |
    | JDK 22  | √       |
    | JDK 23  | √       |
    | JDK 24  | √       |
    | JDK 25  | √       |