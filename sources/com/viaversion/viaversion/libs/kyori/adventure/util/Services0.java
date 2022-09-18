package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.ServiceLoader;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Services0.class */
final class Services0 {
    private Services0() {
    }

    public static <S> ServiceLoader<S> loader(final Class<S> type) {
        return ServiceLoader.load(type, type.getClassLoader());
    }
}
