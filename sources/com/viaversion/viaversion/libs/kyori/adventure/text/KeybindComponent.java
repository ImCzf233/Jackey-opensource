package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/KeybindComponent.class */
public interface KeybindComponent extends BuildableComponent<KeybindComponent, Builder>, ScopedComponent<KeybindComponent> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/KeybindComponent$KeybindLike.class */
    public interface KeybindLike {
        @NotNull
        String asKeybind();
    }

    @NotNull
    String keybind();

    @Contract(pure = true)
    @NotNull
    KeybindComponent keybind(@NotNull final String keybind);

    @Contract(pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final KeybindLike keybind) {
        return keybind(((KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/KeybindComponent$Builder.class */
    public interface Builder extends ComponentBuilder<KeybindComponent, Builder> {
        @Contract("_ -> this")
        @NotNull
        Builder keybind(@NotNull final String keybind);

        @Contract(pure = true)
        @NotNull
        default Builder keybind(@NotNull final KeybindLike keybind) {
            return keybind(((KeybindLike) Objects.requireNonNull(keybind, "keybind")).asKeybind());
        }
    }
}
