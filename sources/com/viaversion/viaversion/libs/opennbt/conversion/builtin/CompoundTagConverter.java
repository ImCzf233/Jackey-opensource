package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/CompoundTagConverter.class */
public class CompoundTagConverter implements TagConverter<CompoundTag, Map> {
    public Map convert(CompoundTag tag) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Tag> tags = tag.getValue();
        for (Map.Entry<String, Tag> entry : tags.entrySet()) {
            ret.put(entry.getKey(), ConverterRegistry.convertToValue(entry.getValue()));
        }
        return ret;
    }

    public CompoundTag convert(Map value) {
        Map<String, Tag> tags = new HashMap<>();
        for (Object na : value.keySet()) {
            String n = (String) na;
            tags.put(n, ConverterRegistry.convertToTag(value.get(n)));
        }
        return new CompoundTag(tags);
    }
}
