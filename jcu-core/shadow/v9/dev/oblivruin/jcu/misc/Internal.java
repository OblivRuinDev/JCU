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
package dev.oblivruin.jcu.misc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.security.*;

public final class Internal {
    private Internal() {}

    public static void loadFastC() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Object.class, MethodHandles.lookup());
            Module java = Object.class.getModule();
            Module self = Internal.class.getModule();
            ClassLoader loader = Internal.class.getClassLoader();
            PermissionCollection perm = Internal.class.getProtectionDomain().getPermissions();
            MethodHandle handle = lookup.findVirtual(ClassLoader.class, "defineClass",
                    MethodType.methodType(Class.class, new Class[]{String.class, byte[].class, int.class, int.class, ProtectionDomain.class}));
            if (java.isExported("jdk/internal/misc", self)) {
                doLoad(loader, handle, perm, "dev.oblivruin.jcu.internal.BytesUtil", "/fastC/BytesUtil$$$$9");
            }
            doLoad(loader, handle, perm, "dev.oblivruin.jcu.internal.Strings", "/fastC/Strings$$$$9");

        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private static void doLoad(ClassLoader loader, MethodHandle handle, PermissionCollection perm , String className, String resName) {
        URL url = loader.getResource(resName);
        try (InputStream in = url.openStream()) {
            ByteArrayOutputStream reader = new ByteArrayOutputStream(in.available());
            in.transferTo(reader);
            byte[] bytes = reader.toByteArray();
            Class<?> ignored = (Class<?>) handle.invokeExact(loader, className, bytes, 0, bytes.length,
                    new ProtectionDomain(new CodeSource(url, (CodeSigner[]) null), perm, loader, null));
        } catch (VirtualMachineError vmError) {
            throw vmError;
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }
}
