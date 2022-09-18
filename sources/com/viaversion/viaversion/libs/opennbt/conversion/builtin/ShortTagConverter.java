package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/ShortTagConverter.class */
public class ShortTagConverter implements TagConverter<ShortTag, Short> {
    public Short convert(ShortTag tag) {
        return tag.getValue();
    }

    public ShortTag convert(Short value) {
        return new ShortTag(value.shortValue());
    }
}
