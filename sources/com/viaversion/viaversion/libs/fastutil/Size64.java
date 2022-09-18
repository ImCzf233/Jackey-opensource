package com.viaversion.viaversion.libs.fastutil;

import java.util.Collection;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Size64.class */
public interface Size64 {
    long size64();

    @Deprecated
    default int size() {
        return (int) Math.min(2147483647L, size64());
    }

    static long sizeOf(Collection<?> c) {
        return c instanceof Size64 ? ((Size64) c).size64() : c.size();
    }

    static long sizeOf(Map<?, ?> m) {
        return m instanceof Size64 ? ((Size64) m).size64() : m.size();
    }
}
