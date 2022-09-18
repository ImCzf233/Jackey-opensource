package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/EntityNBTComponent.class */
public interface EntityNBTComponent extends NBTComponent<EntityNBTComponent, Builder>, ScopedComponent<EntityNBTComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/EntityNBTComponent$Builder.class */
    public interface Builder extends NBTComponentBuilder<EntityNBTComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder selector(@NotNull final String selector);
    }

    @NotNull
    String selector();

    @Contract(pure = true)
    @NotNull
    EntityNBTComponent selector(@NotNull final String selector);
}
