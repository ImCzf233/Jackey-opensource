package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/ByteArrayTagConverter.class */
public class ByteArrayTagConverter implements TagConverter<ByteArrayTag, byte[]> {
    public byte[] convert(ByteArrayTag tag) {
        return tag.getValue();
    }

    public ByteArrayTag convert(byte[] value) {
        return new ByteArrayTag(value);
    }
}
