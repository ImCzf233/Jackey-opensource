package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/Pointer.class */
public interface Pointer<V> extends Examinable {
    @NotNull
    Class<V> type();

    @NotNull
    Key key();

    @NotNull
    static <V> Pointer<V> pointer(@NotNull final Class<V> type, @NotNull final Key key) {
        return new PointerImpl(type, key);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("type", type()), ExaminableProperty.m91of(LocalCacheFactory.KEY, key())});
    }
}
