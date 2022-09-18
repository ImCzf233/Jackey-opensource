package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/builtin/IntArrayTagConverter.class */
public class IntArrayTagConverter implements TagConverter<IntArrayTag, int[]> {
    public int[] convert(IntArrayTag tag) {
        return tag.getValue();
    }

    public IntArrayTag convert(int[] value) {
        return new IntArrayTag(value);
    }
}
