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
package dev.oblivruin.jcu.builds;

import dev.oblivruin.jcu.*;

import java.io.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public final class Rename extends DataOutputStream implements Consumer<File> {
    final File root;
    private FileOutputStream output;

    public Rename(File root) {
        super(nullOutputStream());
        this.root = root;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        output.write(b, off, len);
    }

    @Override
    public void accept(File inputF) {
        try (FileInputStream input = new FileInputStream(inputF)) {
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            ClassFileReader reader = new ClassFileReader(bytes);
            int thisUtf8Index = reader.ref1Index(reader.readU2(reader.header + 2));
            String origin = reader.utf8V(thisUtf8Index);
            String newName = origin.substring(0, origin.indexOf("$$$$"));
            try (FileOutputStream output = new FileOutputStream(new File(root, newName.substring(newName.lastIndexOf('/') + 1)))) {
                this.output = output;
                output.write(bytes, 0, reader.offset(thisUtf8Index) + 1);
                super.writeUTF(newName);
                int offset = reader.offset(thisUtf8Index + 1);
                output.write(bytes, offset, bytes.length - offset);
            } finally {
                this.output = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
