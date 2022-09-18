package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/ByteTag.class */
public class ByteTag extends NumberTag {

    /* renamed from: ID */
    public static final int f191ID = 1;
    private byte value;

    public ByteTag() {
        this((byte) 0);
    }

    public ByteTag(byte value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    @Deprecated
    public Byte getValue() {
        return Byte.valueOf(this.value);
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readByte();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeByte(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteTag byteTag = (ByteTag) o;
        return this.value == byteTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final ByteTag clone() {
        return new ByteTag(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public byte asByte() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public short asShort() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public int asInt() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public long asLong() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public float asFloat() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public double asDouble() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 1;
    }
}
