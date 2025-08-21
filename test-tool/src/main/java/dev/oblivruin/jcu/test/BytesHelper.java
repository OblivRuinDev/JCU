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
package dev.oblivruin.jcu.test;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

@SuppressWarnings("RedundantCast")
public final class BytesHelper {
    public static final byte[] j8ObjBytes = read("/8/Object.class");
    public static final byte[] jshModuleBytes = read("/9/jshModule.class");

    public static final class ALL implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) {
            return Stream.of(
                    Arguments.of((Object) j8ObjBytes),
                    Arguments.of((Object) jshModuleBytes));
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private static byte[] read(String path) {
        try (InputStream input = BytesHelper.class.getResourceAsStream(path)) {
            int len = input.available();
            byte[] bytes = new byte[len];
            if (input.read(bytes) != len) {
                throw new EOFException("Unexpected file cut off at " + path);
            }
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
