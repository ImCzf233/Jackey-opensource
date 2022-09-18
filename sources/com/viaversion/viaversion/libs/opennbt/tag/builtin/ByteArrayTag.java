package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/ByteArrayTag.class */
public class ByteArrayTag extends Tag {

    /* renamed from: ID */
    public static final int f190ID = 7;
    private byte[] value;

    public ByteArrayTag() {
        this(new byte[0]);
    }

    public ByteArrayTag(byte[] value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        if (value == null) {
            return;
        }
        this.value = value;
    }

    public byte getValue(int index) {
        return this.value[index];
    }

    public void setValue(int index, byte value) {
        this.value[index] = value;
    }

    public int length() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = new byte[in.readInt()];
        in.readFully(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        out.write(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteArrayTag that = (ByteArrayTag) o;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final ByteArrayTag clone() {
        return new ByteArrayTag(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 7;
    }
}
