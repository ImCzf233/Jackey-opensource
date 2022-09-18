package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/NBTComponent.class */
public interface NBTComponent<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends BuildableComponent<C, B> {
    @NotNull
    String nbtPath();

    @Contract(pure = true)
    @NotNull
    C nbtPath(@NotNull final String nbtPath);

    boolean interpret();

    @Contract(pure = true)
    @NotNull
    C interpret(final boolean interpret);

    @Nullable
    Component separator();

    @NotNull
    C separator(@Nullable final ComponentLike separator);
}
