package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/IntTagConverter.class */
public class IntTagConverter implements TagConverter<IntTag, Integer> {
    public Integer convert(IntTag tag) {
        return tag.getValue();
    }

    public IntTag convert(Integer value) {
        return new IntTag(value.intValue());
    }
}
