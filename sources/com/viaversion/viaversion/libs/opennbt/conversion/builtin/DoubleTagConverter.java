package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/DoubleTagConverter.class */
public class DoubleTagConverter implements TagConverter<DoubleTag, Double> {
    public Double convert(DoubleTag tag) {
        return tag.getValue();
    }

    public DoubleTag convert(Double value) {
        return new DoubleTag(value.doubleValue());
    }
}
