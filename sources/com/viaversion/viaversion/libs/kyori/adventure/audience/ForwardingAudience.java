package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/audience/ForwardingAudience.class */
public interface ForwardingAudience extends Audience {
    @ApiStatus.OverrideOnly
    @NotNull
    Iterable<? extends Audience> audiences();

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    @NotNull
    default Audience filterAudience(@NotNull final Predicate<? super Audience> filter) {
        Audience filtered;
        List<Audience> audiences = null;
        for (Audience audience : audiences()) {
            if (filter.test(audience) && (filtered = audience.filterAudience(filter)) != Audience.empty()) {
                if (audiences == null) {
                    audiences = new ArrayList<>();
                }
                audiences.add(filtered);
            }
        }
        if (audiences != null) {
            return Audience.audience(audiences);
        }
        return Audience.empty();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void forEachAudience(@NotNull final Consumer<? super Audience> action) {
        for (Audience audience : audiences()) {
            audience.forEachAudience(action);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
        for (Audience audience : audiences()) {
            audience.sendMessage(source, message, type);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
        for (Audience audience : audiences()) {
            audience.sendMessage(source, message, type);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendActionBar(@NotNull final Component message) {
        for (Audience audience : audiences()) {
            audience.sendActionBar(message);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendPlayerListHeader(@NotNull final Component header) {
        for (Audience audience : audiences()) {
            audience.sendPlayerListHeader(header);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendPlayerListFooter(@NotNull final Component footer) {
        for (Audience audience : audiences()) {
            audience.sendPlayerListFooter(footer);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
        for (Audience audience : audiences()) {
            audience.sendPlayerListHeaderAndFooter(header, footer);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default <T> void sendTitlePart(@NotNull final TitlePart<T> part, @NotNull final T value) {
        for (Audience audience : audiences()) {
            audience.sendTitlePart(part, value);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void clearTitle() {
        for (Audience audience : audiences()) {
            audience.clearTitle();
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void resetTitle() {
        for (Audience audience : audiences()) {
            audience.resetTitle();
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void showBossBar(@NotNull final BossBar bar) {
        for (Audience audience : audiences()) {
            audience.showBossBar(bar);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void hideBossBar(@NotNull final BossBar bar) {
        for (Audience audience : audiences()) {
            audience.hideBossBar(bar);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void playSound(@NotNull final Sound sound) {
        for (Audience audience : audiences()) {
            audience.playSound(sound);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
        for (Audience audience : audiences()) {
            audience.playSound(sound, x, y, z);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
        for (Audience audience : audiences()) {
            audience.playSound(sound, emitter);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void stopSound(@NotNull final SoundStop stop) {
        for (Audience audience : audiences()) {
            audience.stopSound(stop);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
    default void openBook(@NotNull final Book book) {
        for (Audience audience : audiences()) {
            audience.openBook(book);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/audience/ForwardingAudience$Single.class */
    public interface Single extends ForwardingAudience {
        @ApiStatus.OverrideOnly
        @NotNull
        Audience audience();

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience
        @Deprecated
        @NotNull
        default Iterable<? extends Audience> audiences() {
            return Collections.singleton(audience());
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
        @NotNull
        default <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
            return audience().get(pointer);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
        @Contract("_, null -> null; _, !null -> !null")
        @Nullable
        default <T> T getOrDefault(@NotNull final Pointer<T> pointer, @Nullable final T defaultValue) {
            return (T) audience().getOrDefault(pointer, defaultValue);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
        default <T> T getOrDefaultFrom(@NotNull final Pointer<T> pointer, @NotNull final Supplier<? extends T> defaultValue) {
            return (T) audience().getOrDefaultFrom(pointer, defaultValue);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        @NotNull
        default Audience filterAudience(@NotNull final Predicate<? super Audience> filter) {
            Audience audience = audience();
            if (filter.test(audience)) {
                return this;
            }
            return Audience.empty();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void forEachAudience(@NotNull final Consumer<? super Audience> action) {
            audience().forEachAudience(action);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered
        @NotNull
        default Pointers pointers() {
            return audience().pointers();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
            audience().sendMessage(source, message, type);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
            audience().sendMessage(source, message, type);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendActionBar(@NotNull final Component message) {
            audience().sendActionBar(message);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendPlayerListHeader(@NotNull final Component header) {
            audience().sendPlayerListHeader(header);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendPlayerListFooter(@NotNull final Component footer) {
            audience().sendPlayerListFooter(footer);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
            audience().sendPlayerListHeaderAndFooter(header, footer);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default <T> void sendTitlePart(@NotNull final TitlePart<T> part, @NotNull final T value) {
            audience().sendTitlePart(part, value);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void clearTitle() {
            audience().clearTitle();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void resetTitle() {
            audience().resetTitle();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void showBossBar(@NotNull final BossBar bar) {
            audience().showBossBar(bar);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void hideBossBar(@NotNull final BossBar bar) {
            audience().hideBossBar(bar);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void playSound(@NotNull final Sound sound) {
            audience().playSound(sound);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
            audience().playSound(sound, x, y, z);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
            audience().playSound(sound, emitter);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void stopSound(@NotNull final SoundStop stop) {
            audience().stopSound(stop);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience, com.viaversion.viaversion.libs.kyori.adventure.audience.Audience
        default void openBook(@NotNull final Book book) {
            audience().openBook(book);
        }
    }
}
