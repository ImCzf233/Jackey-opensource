package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/audience/Audience.class */
public interface Audience extends Pointered {
    @NotNull
    static Audience empty() {
        return EmptyAudience.INSTANCE;
    }

    @NotNull
    static Audience audience(@NotNull final Audience... audiences) {
        int length = audiences.length;
        if (length == 0) {
            return empty();
        }
        if (length == 1) {
            return audiences[0];
        }
        return audience(Arrays.asList(audiences));
    }

    @NotNull
    static ForwardingAudience audience(@NotNull final Iterable<? extends Audience> audiences) {
        return () -> {
            return audiences;
        };
    }

    @NotNull
    static Collector<? super Audience, ?, ForwardingAudience> toAudience() {
        return Audiences.COLLECTOR;
    }

    @NotNull
    default Audience filterAudience(@NotNull final Predicate<? super Audience> filter) {
        if (filter.test(this)) {
            return this;
        }
        return empty();
    }

    default void forEachAudience(@NotNull final Consumer<? super Audience> action) {
        action.accept(this);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final ComponentLike message) {
        sendMessage(Identity.nil(), message);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message) {
        sendMessage(source, message.asComponent());
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message) {
        sendMessage(source, message.asComponent());
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Component message) {
        sendMessage(Identity.nil(), message);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final Component message) {
        sendMessage(source, message, MessageType.SYSTEM);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final Component message) {
        sendMessage(source, message, MessageType.SYSTEM);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final ComponentLike message, @NotNull final MessageType type) {
        sendMessage(Identity.nil(), message, type);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
        sendMessage(source, message.asComponent(), type);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
        sendMessage(source, message.asComponent(), type);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendMessage(@NotNull final Component message, @NotNull final MessageType type) {
        sendMessage(Identity.nil(), message, type);
    }

    default void sendMessage(@NotNull final Identified source, @NotNull final Component message, @NotNull final MessageType type) {
        sendMessage(source.identity(), message, type);
    }

    default void sendMessage(@NotNull final Identity source, @NotNull final Component message, @NotNull final MessageType type) {
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendActionBar(@NotNull final ComponentLike message) {
        sendActionBar(message.asComponent());
    }

    default void sendActionBar(@NotNull final Component message) {
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeader(@NotNull final ComponentLike header) {
        sendPlayerListHeader(header.asComponent());
    }

    default void sendPlayerListHeader(@NotNull final Component header) {
        sendPlayerListHeaderAndFooter(header, (Component) Component.empty());
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListFooter(@NotNull final ComponentLike footer) {
        sendPlayerListFooter(footer.asComponent());
    }

    default void sendPlayerListFooter(@NotNull final Component footer) {
        sendPlayerListHeaderAndFooter((Component) Component.empty(), footer);
    }

    @ForwardingAudienceOverrideNotRequired
    default void sendPlayerListHeaderAndFooter(@NotNull final ComponentLike header, @NotNull final ComponentLike footer) {
        sendPlayerListHeaderAndFooter(header.asComponent(), footer.asComponent());
    }

    default void sendPlayerListHeaderAndFooter(@NotNull final Component header, @NotNull final Component footer) {
    }

    @ForwardingAudienceOverrideNotRequired
    default void showTitle(@NotNull final Title title) {
        Title.Times times = title.times();
        if (times != null) {
            sendTitlePart(TitlePart.TIMES, times);
        }
        sendTitlePart(TitlePart.SUBTITLE, title.subtitle());
        sendTitlePart(TitlePart.TITLE, title.title());
    }

    default <T> void sendTitlePart(@NotNull final TitlePart<T> part, @NotNull final T value) {
    }

    default void clearTitle() {
    }

    default void resetTitle() {
    }

    default void showBossBar(@NotNull final BossBar bar) {
    }

    default void hideBossBar(@NotNull final BossBar bar) {
    }

    default void playSound(@NotNull final Sound sound) {
    }

    default void playSound(@NotNull final Sound sound, final double x, final double y, final double z) {
    }

    @ForwardingAudienceOverrideNotRequired
    default void stopSound(@NotNull final Sound sound) {
        stopSound(((Sound) Objects.requireNonNull(sound, "sound")).asStop());
    }

    default void playSound(@NotNull final Sound sound, final Sound.Emitter emitter) {
    }

    default void stopSound(@NotNull final SoundStop stop) {
    }

    @ForwardingAudienceOverrideNotRequired
    default void openBook(final Book.Builder book) {
        openBook(book.build());
    }

    default void openBook(@NotNull final Book book) {
    }
}
