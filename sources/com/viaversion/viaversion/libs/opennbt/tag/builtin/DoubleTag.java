package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/DoubleTag.class */
public class DoubleTag extends NumberTag {

    /* renamed from: ID */
    public static final int f193ID = 6;
    private double value;

    public DoubleTag() {
        this(0.0d);
    }

    public DoubleTag(double value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    @Deprecated
    public Double getValue() {
        return Double.valueOf(this.value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readDouble();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeDouble(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoubleTag doubleTag = (DoubleTag) o;
        return this.value == doubleTag.value;
    }

    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final DoubleTag clone() {
        return new DoubleTag(this.value);
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
        return (long) this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public float asFloat() {
        return (float) this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public double asDouble() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 6;
    }
}
