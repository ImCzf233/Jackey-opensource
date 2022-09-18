package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/SoundStopImpl.class */
public abstract class SoundStopImpl implements SoundStop {
    static final SoundStop ALL = new SoundStopImpl(null) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStopImpl.1
        @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
        @Nullable
        public Key sound() {
            return null;
        }
    };
    private final Sound.Source source;

    public SoundStopImpl(final Sound.Source source) {
        this.source = source;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
    public Sound.Source source() {
        return this.source;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundStopImpl)) {
            return false;
        }
        SoundStopImpl that = (SoundStopImpl) other;
        return Objects.equals(sound(), that.sound()) && Objects.equals(this.source, that.source);
    }

    public int hashCode() {
        int result = Objects.hashCode(sound());
        return (31 * result) + Objects.hashCode(this.source);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("name", sound()), ExaminableProperty.m91of("source", this.source)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
