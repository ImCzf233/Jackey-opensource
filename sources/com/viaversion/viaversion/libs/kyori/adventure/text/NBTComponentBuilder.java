package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/NBTComponentBuilder.class */
public interface NBTComponentBuilder<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends ComponentBuilder<C, B> {
    @Contract("_ -> this")
    @NotNull
    B nbtPath(@NotNull final String nbtPath);

    @Contract("_ -> this")
    @NotNull
    B interpret(final boolean interpret);

    @Contract("_ -> this")
    @NotNull
    B separator(@Nullable final ComponentLike separator);
}
