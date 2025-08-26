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

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.security.*;

public final class Internal {
    private Internal() {}

    @SuppressWarnings("DataFlowIssue")
    public static void loadFastC() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Object.class, MethodHandles.lookup());
            ClassLoader loader = Internal.class.getClassLoader();
            MethodHandle handle = lookup.findVirtual(ClassLoader.class, "defineClass",
                    MethodType.methodType(Class.class, new Class[]{String.class, byte[].class, int.class, int.class, ProtectionDomain.class}));
            PermissionCollection perm = Internal.class.getProtectionDomain().getPermissions();
            URL url = loader.getResource("/fastC/BytesUtil$$$$Unsafe9");
            byte[] bytes;
            int len;
            try (InputStream in = url.openConnection().getInputStream()) {
                bytes = new byte[len = in.available()];
                in.read(bytes);
                Class<?> ignored = (Class<?>) handle.invokeExact(loader, "dev.oblivruin.jcu.internal.BytesUtil", bytes, 0, len,
                        new ProtectionDomain(new CodeSource(url, (CodeSigner[]) null), perm, loader, null));
            } catch (VirtualMachineError vmError) {
                throw vmError;
            } catch (Throwable ignored) {
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
