package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/Sound.class */
public interface Sound extends Examinable {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/Sound$Type.class */
    public interface Type extends Keyed {
        @Override // com.viaversion.viaversion.libs.kyori.adventure.key.Keyed
        @NotNull
        Key key();
    }

    @NotNull
    Key name();

    @NotNull
    Source source();

    float volume();

    float pitch();

    @NotNull
    SoundStop asStop();

    @NotNull
    static Sound sound(@NotNull final Key name, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(source, volume, pitch) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.Sound.1
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
            @NotNull
            public Key name() {
                return name;
            }
        };
    }

    @NotNull
    static Sound sound(@NotNull final Type type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        return sound(type.key(), source, volume, pitch);
    }

    @NotNull
    static Sound sound(@NotNull final Supplier<? extends Type> type, @NotNull final Source source, final float volume, final float pitch) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(source, "source");
        return new SoundImpl(source, volume, pitch) { // from class: com.viaversion.viaversion.libs.kyori.adventure.sound.Sound.2
            @Override // com.viaversion.viaversion.libs.kyori.adventure.sound.Sound
            @NotNull
            public Key name() {
                return ((Type) type.get()).key();
            }
        };
    }

    @NotNull
    static Sound sound(@NotNull final Key name, final Source.Provider source, final float volume, final float pitch) {
        return sound(name, source.soundSource(), volume, pitch);
    }

    @NotNull
    static Sound sound(@NotNull final Type type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }

    @NotNull
    static Sound sound(@NotNull final Supplier<? extends Type> type, final Source.Provider source, final float volume, final float pitch) {
        return sound(type, source.soundSource(), volume, pitch);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/Sound$Source.class */
    public enum Source {
        MASTER("master"),
        MUSIC("music"),
        RECORD("record"),
        WEATHER("weather"),
        BLOCK("block"),
        HOSTILE("hostile"),
        NEUTRAL("neutral"),
        PLAYER("player"),
        AMBIENT("ambient"),
        VOICE("voice");
        
        public static final Index<String, Source> NAMES = Index.create(Source.class, source -> {
            return source.name;
        });
        private final String name;

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/Sound$Source$Provider.class */
        public interface Provider {
            @NotNull
            Source soundSource();
        }

        Source(final String name) {
            this.name = name;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/sound/Sound$Emitter.class */
    public interface Emitter {
        @NotNull
        static Emitter self() {
            return SoundImpl.EMITTER_SELF;
        }
    }
}
