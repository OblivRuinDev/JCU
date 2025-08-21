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

import java.util.Arrays;

public class ByteArray extends Array {
    /**
     * Core data.
     * Shouldn't be {@literal null}
     */
    public byte[] data;
    public ByteArray() {
        this(200);
    }
    public ByteArray(byte[] bytes, int freeOff) {
        this.data = bytes;
        this.length = freeOff;
    }
    public ByteArray(int size) {
        if (size > 0) {
            this.data = new byte[size];
        } else if (size == 0) {
            this.data = E;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    private static final byte[] E = new byte[0];

    /**
     * Write Modified UTF-8 at current {@link #length},
     * don't write length.
     * @param value the string value to be written
     * @return the length of written bytes
     */
    public final int writeUtf8(String value) {
        char[] chars = value.toCharArray();
        int pointer = this.length - 1;
        int len = chars.length;
        ensureFree(len);
        byte[] data = this.data;
        for (int index = 0; index < len; ++index) {
            char c = chars[index];
            if (c != 0 && c < 0x80) {
                data[++pointer] = (byte) c;
            } else {
                int v = 3*(len - index) + pointer + 1;
                if (v > data.length) {
                    System.arraycopy(data, 0, (data = new byte[newSize(v)]), 0, pointer + 1);
                    this.data = data;
                }
                for (; index < len; ++index) {
                    c = chars[index];
                    if (c >= 0x800) {
                        data[++pointer] = (byte) (0xE0 | c >> 12 & 0x0F);
                        data[++pointer] = (byte) (0x80 | c >> 6  & 0x3F);
                        data[++pointer] = (byte) (0x80 | c       & 0x3F);
                    } else if (c >= 0x80 || c == 0) {
                        data[++pointer] = (byte) (0xC0 | c >> 6 & 0x1F);
                        data[++pointer] = (byte) (0x80 | c      & 0x3F);
                    } else {
                        data[++pointer] = (byte) c;
                    }
                }
                len = 1 + pointer - this.length;
                break;
            }
        }
        this.length = 1 + pointer;
        return len;
    }

    public final void add(byte b) {
        ensureFree(1);
        data[length++] = b;
    }

    /**
     * Copy the source ByteArray.
     * @param byteArray source ByteArray
     */
    public final void add(ByteArray byteArray) {
        add(byteArray.data, 0, byteArray.length);
    }

    /**
     * Copy the source array.
     * @param bytes source array, must not be {@code null}
     */
    public final void add(byte[] bytes) {
        add(bytes, 0, bytes.length);
    }

    /**
     * Copy the source array within given range.
     * @param bytes source array, must not be {@code null}
     * @param off starting position in {@code bytes}
     * @param len the number of array elements to be copied
     */
    public final void add(byte[] bytes, int off, int len) {
        this.ensureFree(len);
        System.arraycopy(bytes, off, data, length, len);
        length+=len;
    }

    /** {@inheritDoc} */
    @Override
    public final void ensureFree(int size) {
        int i = length + size;
        int o = data.length;
        if (i >= o) {
            System.arraycopy(data, 0,
                    (data = new byte[newSize(i)]), 0,
                    o);
        }
    }

    @Override
    public final boolean tryExpand(int size) {
        int i = length + size;
        int o = data.length;
        if (i >= o) {
            System.arraycopy(data, 0,
                    (data = new byte[newSize(i)]), 0,
                    o);
            return true;
        }
        return false;
    }

    @Override
    protected int newSize(int expected) {
        return Math.max(expected, data.length * 2);
    }

    public final void put2(short value) {
        ensureFree(2);
        BytesUtil.setShort(this.data, this.length, value);
        this.length+=2;
    }

    public final void put2(int value) {
        ensureFree(2);
        BytesUtil.setUShort(this.data, this.length, value);
        this.length+=2;
    }

    public final void put4(int value) {
        ensureFree(4);
        BytesUtil.setInt(this.data, this.length, value);
        this.length+=4;
    }

    public final void put8(long value) {
        ensureFree(8);
        BytesUtil.setLong(this.data, this.length, value);
        this.length+=8;
    }

    public final void put4(float value) {
        put4(Float.floatToIntBits(value));
    }

    public final void put8(double value) {
        put8(Double.doubleToLongBits(value));
    }

    public final void put222(int v1, int v2, int v3) {
        ensureFree(6);
        int pointer = this.length;
        byte[] array = this.data;
        BytesUtil.setUShort(array, pointer, v1);
        BytesUtil.setUShort(array, pointer + 2, v2);
        BytesUtil.setUShort(array, pointer + 4, v3);
        this.length = pointer + 6;
    }

    public final void put24(int v1, int v2) {
        ensureFree(6);
        int pointer = this.length;
        byte[] array = this.data;
        BytesUtil.setUShort(array, pointer, v1);
        BytesUtil.setInt(array, pointer + 2, v2);
        this.length = pointer + 6;
    }

    public final void put242(int v1, int v2, int v3) {
        ensureFree(8);
        int pointer = this.length;
        byte[] array = this.data;
        BytesUtil.setUShort(array, pointer, v1);
        BytesUtil.setInt(array, pointer + 2, v2);
        BytesUtil.setUShort(array, pointer + 6, v3);
        this.length = pointer + 8;
    }

    public final void put122_(int v1, int v2, int v3) {
        int pointer = this.length;
        byte[] array = this.data;
        array[pointer] = (byte) v1;
        BytesUtil.setUShort(array, pointer + 1, v2);
        BytesUtil.setUShort(array, pointer + 3, v3);
        this.length = pointer + 5;
    }

    public final void put12_(int v1, int v2) {
        int pointer = this.length;
        byte[] array = this.data;
        array[  pointer] = (byte) v1;
        BytesUtil.setUShort(array, pointer + 1, v2);
        this.length = pointer + 3;
    }

    public final byte[] toArray() {
        return data.length == length ? data : Arrays.copyOf(data, length);
    }

    public static ByteArray of(byte... bytes) {
        return new ByteArray(bytes, bytes.length);
    }
}
