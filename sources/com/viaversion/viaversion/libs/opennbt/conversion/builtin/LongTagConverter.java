package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/LongTagConverter.class */
public class LongTagConverter implements TagConverter<LongTag, Long> {
    public Long convert(LongTag tag) {
        return tag.getValue();
    }

    public LongTag convert(Long value) {
        return new LongTag(value.longValue());
    }
}
