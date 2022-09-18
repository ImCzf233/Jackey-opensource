package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/StorageNBTComponent.class */
public interface StorageNBTComponent extends NBTComponent<StorageNBTComponent, Builder>, ScopedComponent<StorageNBTComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/StorageNBTComponent$Builder.class */
    public interface Builder extends NBTComponentBuilder<StorageNBTComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder storage(@NotNull final Key storage);
    }

    @NotNull
    Key storage();

    @Contract(pure = true)
    @NotNull
    StorageNBTComponent storage(@NotNull final Key storage);
}
