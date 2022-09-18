package com.viaversion.viaversion.api.minecraft.chunks;

import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/NibbleArray.class */
public class NibbleArray {
    private final byte[] handle;

    public NibbleArray(int length) {
        if (length == 0 || length % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = new byte[length / 2];
    }

    public NibbleArray(byte[] handle) {
        if (handle.length == 0 || handle.length % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = handle;
    }

    public byte get(int x, int y, int z) {
        return get(ChunkSection.index(x, y, z));
    }

    public byte get(int index) {
        byte value = this.handle[index / 2];
        if (index % 2 == 0) {
            return (byte) (value & 15);
        }
        return (byte) ((value >> 4) & 15);
    }

    public void set(int x, int y, int z, int value) {
        set(ChunkSection.index(x, y, z), value);
    }

    public void set(int index, int value) {
        if (index % 2 == 0) {
            int index2 = index / 2;
            this.handle[index2] = (byte) ((this.handle[index2] & 240) | (value & 15));
            return;
        }
        int index3 = index / 2;
        this.handle[index3] = (byte) ((this.handle[index3] & 15) | ((value & 15) << 4));
    }

    public int size() {
        return this.handle.length * 2;
    }

    public int actualSize() {
        return this.handle.length;
    }

    public void fill(byte value) {
        byte value2 = (byte) (value & 15);
        Arrays.fill(this.handle, (byte) ((value2 << 4) | value2));
    }

    public byte[] getHandle() {
        return this.handle;
    }

    public void setHandle(byte[] handle) {
        if (handle.length != this.handle.length) {
            throw new IllegalArgumentException("Length of handle must equal to size of nibble array!");
        }
        System.arraycopy(handle, 0, this.handle, 0, handle.length);
    }
}
