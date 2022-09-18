package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitleImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Ticks;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.time.Duration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/title/Title.class */
public interface Title extends Examinable {
    public static final Times DEFAULT_TIMES = Times.m103of(Ticks.duration(10), Ticks.duration(70), Ticks.duration(20));

    @NotNull
    Component title();

    @NotNull
    Component subtitle();

    @Nullable
    Times times();

    <T> T part(@NotNull final TitlePart<T> part);

    @NotNull
    static Title title(@NotNull final Component title, @NotNull final Component subtitle) {
        return title(title, subtitle, DEFAULT_TIMES);
    }

    @NotNull
    static Title title(@NotNull final Component title, @NotNull final Component subtitle, @Nullable final Times times) {
        return new TitleImpl(title, subtitle, times);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/title/Title$Times.class */
    public interface Times extends Examinable {
        @NotNull
        Duration fadeIn();

        @NotNull
        Duration stay();

        @NotNull
        Duration fadeOut();

        @NotNull
        /* renamed from: of */
        static Times m103of(@NotNull final Duration fadeIn, @NotNull final Duration stay, @NotNull final Duration fadeOut) {
            return new TitleImpl.TimesImpl(fadeIn, stay, fadeOut);
        }
    }
}
