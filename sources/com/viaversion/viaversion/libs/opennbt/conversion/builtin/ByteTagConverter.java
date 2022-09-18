package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/ByteTagConverter.class */
public class ByteTagConverter implements TagConverter<ByteTag, Byte> {
    public Byte convert(ByteTag tag) {
        return tag.getValue();
    }

    public ByteTag convert(Byte value) {
        return new ByteTag(value.byteValue());
    }
}
