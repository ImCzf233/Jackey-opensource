package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/FloatTagConverter.class */
public class FloatTagConverter implements TagConverter<FloatTag, Float> {
    public Float convert(FloatTag tag) {
        return tag.getValue();
    }

    public FloatTag convert(Float value) {
        return new FloatTag(value.floatValue());
    }
}
