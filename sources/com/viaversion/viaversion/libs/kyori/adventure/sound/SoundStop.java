package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/SoundStop.class */
public interface SoundStop extends Examinable {
    @Nullable
    Key sound();

    Sound.Source source();

    @NotNull
    static SoundStop all() {
        return SoundStopImpl.ALL;
    }

    @NotNull
    static SoundStop named(@NotNull final Key sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.1
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @NotNull
            public Key sound() {
                return sound;
            }
        };
    }

    @NotNull
    static SoundStop named(final Sound.Type sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.2
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @NotNull
            public Key sound() {
                return sound.key();
            }
        };
    }

    @NotNull
    static SoundStop named(@NotNull final Supplier<? extends Sound.Type> sound) {
        Objects.requireNonNull(sound, "sound");
        return new SoundStopImpl(null) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.3
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @NotNull
            public Key sound() {
                return ((Sound.Type) sound.get()).key();
            }
        };
    }

    @NotNull
    static SoundStop source(final Sound.Source source) {
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.4
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @Nullable
            public Key sound() {
                return null;
            }
        };
    }

    @NotNull
    static SoundStop namedOnSource(@NotNull final Key sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.5
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @NotNull
            public Key sound() {
                return sound;
            }
        };
    }

    @NotNull
    static SoundStop namedOnSource(final Sound.Type sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        return namedOnSource(sound.key(), source);
    }

    @NotNull
    static SoundStop namedOnSource(@NotNull final Supplier<? extends Sound.Type> sound, final Sound.Source source) {
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop.6
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop
            @NotNull
            public Key sound() {
                return ((Sound.Type) sound.get()).key();
            }
        };
    }
}
