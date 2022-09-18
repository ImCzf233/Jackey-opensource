package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/ShortTag.class */
public class ShortTag extends NumberTag {

    /* renamed from: ID */
    public static final int f200ID = 2;
    private short value;

    public ShortTag() {
        this((short) 0);
    }

    public ShortTag(short value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    @Deprecated
    public Short getValue() {
        return Short.valueOf(this.value);
    }

    public void setValue(short value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readShort();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeShort(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortTag shortTag = (ShortTag) o;
        return this.value == shortTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final ShortTag clone() {
        return new ShortTag(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag
    public byte asByte() {
        return (byte) this.value;
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
        return 2;
    }
}
