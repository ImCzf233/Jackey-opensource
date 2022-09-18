package com.viaversion.viabackwards.utils;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/utils/Block.class */
public class Block {

    /* renamed from: id */
    private final int f24id;
    private final short data;

    public Block(int id, int data) {
        this.f24id = id;
        this.data = (short) data;
    }

    public Block(int id) {
        this.f24id = id;
        this.data = (short) 0;
    }

    public int getId() {
        return this.f24id;
    }

    public int getData() {
        return this.data;
    }

    public Block withData(int data) {
        return new Block(this.f24id, data);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        return this.f24id == block.f24id && this.data == block.data;
    }

    public int hashCode() {
        int result = this.f24id;
        return (31 * result) + this.data;
    }
}
