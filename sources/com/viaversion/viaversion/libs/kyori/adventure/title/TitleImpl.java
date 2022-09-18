package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/title/TitleImpl.class */
public final class TitleImpl implements Title {
    private final Component title;
    private final Component subtitle;
    @Nullable
    private final Title.Times times;

    public TitleImpl(@NotNull final Component title, @NotNull final Component subtitle, @Nullable final Title.Times times) {
        this.title = title;
        this.subtitle = subtitle;
        this.times = times;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title
    @NotNull
    public Component title() {
        return this.title;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title
    @NotNull
    public Component subtitle() {
        return this.subtitle;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title
    @Nullable
    public Title.Times times() {
        return this.times;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title
    public <T> T part(@NotNull final TitlePart<T> part) {
        if (part == TitlePart.TITLE) {
            return (T) this.title;
        }
        if (part == TitlePart.SUBTITLE) {
            return (T) this.subtitle;
        }
        if (part == TitlePart.TIMES) {
            return (T) this.times;
        }
        throw new IllegalArgumentException("Don't know what " + part + " is.");
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TitleImpl that = (TitleImpl) other;
        return this.title.equals(that.title) && this.subtitle.equals(that.subtitle) && Objects.equals(this.times, that.times);
    }

    public int hashCode() {
        int result = this.title.hashCode();
        return (31 * ((31 * result) + this.subtitle.hashCode())) + Objects.hashCode(this.times);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("title", this.title), ExaminableProperty.m91of("subtitle", this.subtitle), ExaminableProperty.m91of("times", this.times)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/title/TitleImpl$TimesImpl.class */
    public static class TimesImpl implements Title.Times {
        private final Duration fadeIn;
        private final Duration stay;
        private final Duration fadeOut;

        public TimesImpl(final Duration fadeIn, final Duration stay, final Duration fadeOut) {
            this.fadeIn = fadeIn;
            this.stay = stay;
            this.fadeOut = fadeOut;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title.Times
        @NotNull
        public Duration fadeIn() {
            return this.fadeIn;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title.Times
        @NotNull
        public Duration stay() {
            return this.stay;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.title.Title.Times
        @NotNull
        public Duration fadeOut() {
            return this.fadeOut;
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            TimesImpl that = (TimesImpl) other;
            return this.fadeIn.equals(that.fadeIn) && this.stay.equals(that.stay) && this.fadeOut.equals(that.fadeOut);
        }

        public int hashCode() {
            int result = this.fadeIn.hashCode();
            return (31 * ((31 * result) + this.stay.hashCode())) + this.fadeOut.hashCode();
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("fadeIn", this.fadeIn), ExaminableProperty.m91of("stay", this.stay), ExaminableProperty.m91of("fadeOut", this.fadeOut)});
        }

        public String toString() {
            return (String) examine(StringExaminer.simpleEscaping());
        }
    }
}
