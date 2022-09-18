package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/conversion/TagConverter.class */
public interface TagConverter<T extends Tag, V> {
    V convert(T t);

    T convert(V v);
}
