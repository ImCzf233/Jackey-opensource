package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.class */
public class StringTag extends Tag {

    /* renamed from: ID */
    public static final int f201ID = 8;
    private String value;

    public StringTag() {
        this("");
    }

    public StringTag(String value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.value = in.readUTF();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringTag stringTag = (StringTag) o;
        return this.value.equals(stringTag.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final StringTag clone() {
        return new StringTag(this.value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 8;
    }
}
