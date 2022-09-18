package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag.class */
public class LongArrayTag extends Tag {

    /* renamed from: ID */
    public static final int f198ID = 12;
    private long[] value;

    public LongArrayTag() {
        this(new long[0]);
    }

    public LongArrayTag(long[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public long[] getValue() {
        return this.value;
    }

    public void setValue(long[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    public long getValue(int index) {
        return this.value[index];
    }

    public void setValue(int index, long value) {
        this.value[index] = value;
    }

    public int length() {
        return this.value.length;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = new long[in.readInt()];
        for (int index = 0; index < this.value.length; index++) {
            this.value[index] = in.readLong();
        }
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        long[] jArr;
        out.writeInt(this.value.length);
        for (long l : this.value) {
            out.writeLong(l);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongArrayTag that = (LongArrayTag) o;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final LongArrayTag clone() {
        return new LongArrayTag((long[]) this.value.clone());
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 12;
    }
}
