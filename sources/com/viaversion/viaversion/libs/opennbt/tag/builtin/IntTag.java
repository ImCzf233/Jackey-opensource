package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.class */
public class IntTag extends NumberTag {

    /* renamed from: ID */
    public static final int f196ID = 3;
    private int value;

    public IntTag() {
        this(0);
    }

    public IntTag(int value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    @Deprecated
    public Integer getValue() {
        return Integer.valueOf(this.value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readInt();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntTag intTag = (IntTag) o;
        return this.value == intTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final IntTag clone() {
        return new IntTag(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public byte asByte() {
        return (byte) this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public short asShort() {
        return (short) this.value;
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
        return 3;
    }
}
