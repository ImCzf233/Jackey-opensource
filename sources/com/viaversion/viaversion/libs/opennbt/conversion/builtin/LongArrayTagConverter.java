package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/LongArrayTagConverter.class */
public class LongArrayTagConverter implements TagConverter<LongArrayTag, long[]> {
    public long[] convert(LongArrayTag tag) {
        return tag.getValue();
    }

    public LongArrayTag convert(long[] value) {
        return new LongArrayTag(value);
    }
}
