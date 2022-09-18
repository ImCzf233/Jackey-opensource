package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/FloatTag.class */
public class FloatTag extends NumberTag {

    /* renamed from: ID */
    public static final int f194ID = 5;
    private float value;

    public FloatTag() {
        this(0.0f);
    }

    public FloatTag(float value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    @Deprecated
    public Float getValue() {
        return Float.valueOf(this.value);
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readFloat();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeFloat(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FloatTag floatTag = (FloatTag) o;
        return this.value == floatTag.value;
    }

    public int hashCode() {
        return Float.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final FloatTag clone() {
        return new FloatTag(this.value);
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
        return (int) this.value;
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
        return 5;
    }
}
