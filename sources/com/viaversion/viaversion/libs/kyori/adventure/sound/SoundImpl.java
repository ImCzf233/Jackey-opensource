package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.util.ShadyPines;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/SoundImpl.class */
public abstract class SoundImpl implements Sound {
    static final Sound.Emitter EMITTER_SELF = new Sound.Emitter() { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.SoundImpl.1
        public String toString() {
            return "SelfSoundEmitter";
        }
    };
    private final Sound.Source source;
    private final float volume;
    private final float pitch;
    private SoundStop stop;

    public SoundImpl(@NotNull final Sound.Source source, final float volume, final float pitch) {
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
    @NotNull
    public Sound.Source source() {
        return this.source;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
    public float volume() {
        return this.volume;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
    public float pitch() {
        return this.pitch;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
    @NotNull
    public SoundStop asStop() {
        if (this.stop == null) {
            this.stop = SoundStop.namedOnSource(name(), source());
        }
        return this.stop;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundImpl)) {
            return false;
        }
        SoundImpl that = (SoundImpl) other;
        return name().equals(that.name()) && this.source == that.source && ShadyPines.equals(this.volume, that.volume) && ShadyPines.equals(this.pitch, that.pitch);
    }

    public int hashCode() {
        int result = name().hashCode();
        return (31 * ((31 * ((31 * result) + this.source.hashCode())) + Float.hashCode(this.volume))) + Float.hashCode(this.pitch);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("name", name()), ExaminableProperty.m91of("source", this.source), ExaminableProperty.m94of("volume", this.volume), ExaminableProperty.m94of("pitch", this.pitch)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
