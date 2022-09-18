package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/IntArrayTag.class */
public class IntArrayTag extends Tag {

    /* renamed from: ID */
    public static final int f195ID = 11;
    private int[] value;

    public IntArrayTag() {
        this(new int[0]);
    }

    public IntArrayTag(int[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int[] getValue() {
        return this.value;
    }

    public void setValue(int[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    public int getValue(int index) {
        return this.value[index];
    }

    public void setValue(int index, int value) {
        this.value[index] = value;
    }

    public int length() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = new int[in.readInt()];
        for (int index = 0; index < this.value.length; index++) {
            this.value[index] = in.readInt();
        }
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        int[] iArr;
        out.writeInt(this.value.length);
        for (int i : this.value) {
            out.writeInt(i);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntArrayTag that = (IntArrayTag) o;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final IntArrayTag clone() {
        return new IntArrayTag((int[]) this.value.clone());
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 11;
    }
}
