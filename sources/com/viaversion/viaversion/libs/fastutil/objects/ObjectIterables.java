package com.viaversion.viaversion.libs.fastutil.objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterables.class */
public final class ObjectIterables {
    private ObjectIterables() {
    }

    public static <K> long size(Iterable<K> iterable) {
        long c = 0;
        for (K k : iterable) {
            c++;
        }
        return c;
    }
}
