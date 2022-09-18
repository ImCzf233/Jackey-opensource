package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/Int2IntBiMap.class */
public interface Int2IntBiMap extends Int2IntMap {
    Int2IntBiMap inverse();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    int put(int i, int i2);

    @Override // java.util.Map
    @Deprecated
    default void putAll(Map<? extends Integer, ? extends Integer> m) {
        throw new UnsupportedOperationException();
    }
}
