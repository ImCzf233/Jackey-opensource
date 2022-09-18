package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ListTagSetter.class */
public interface ListTagSetter<R, T extends BinaryTag> {
    @NotNull
    R add(final T tag);

    @NotNull
    R add(final Iterable<? extends T> tags);
}
