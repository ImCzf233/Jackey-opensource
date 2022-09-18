package com.viaversion.viaversion.libs.kyori.adventure.key;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/key/KeyedValue.class */
public interface KeyedValue<T> extends Keyed {
    @NotNull
    T value();

    @NotNull
    /* renamed from: of */
    static <T> KeyedValue<T> m140of(@NotNull final Key key, @NotNull final T value) {
        return new KeyedValueImpl(key, Objects.requireNonNull(value, "value"));
    }
}
