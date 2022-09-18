package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/audience/EmptyAudience.class */
public final class EmptyAudience implements Audience {
    static final EmptyAudience INSTANCE = new EmptyAudience();

    EmptyAudience() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
    @NotNull
    public <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
        return Optional.empty();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
    @Contract("_, null -> null; _, !null -> !null")
    @Nullable
    public <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
    public <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
        return defaultValue.get();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    @NotNull
    public Audience filterAudience(@NotNull final Predicate<? super Audience> filter) {
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void forEachAudience(@NotNull final Consumer<? super Audience> action) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final ComponentLike message) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final ComponentLike message, @NotNull final MessageType type) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendActionBar(@NotNull final ComponentLike message) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendPlayerListHeader(@NotNull final ComponentLike header) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendPlayerListFooter(@NotNull final ComponentLike footer) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void sendPlayerListHeaderAndFooter(@NotNull final ComponentLike header, @NotNull final ComponentLike footer) {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    public void openBook(final Book.Builder book) {
    }

    public boolean equals(final Object that) {
        return this == that;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "EmptyAudience";
    }
}
