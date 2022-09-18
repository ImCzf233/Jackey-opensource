package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/StringTagConverter.class */
public class StringTagConverter implements TagConverter<StringTag, String> {
    public String convert(StringTag tag) {
        return tag.getValue();
    }

    public StringTag convert(String value) {
        return new StringTag(value);
    }
}
