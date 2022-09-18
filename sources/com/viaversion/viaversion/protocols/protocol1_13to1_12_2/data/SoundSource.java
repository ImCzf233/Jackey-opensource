package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/SoundSource.class */
public enum SoundSource {
    MASTER("master", 0),
    MUSIC("music", 1),
    RECORD("record", 2),
    WEATHER("weather", 3),
    BLOCK("block", 4),
    HOSTILE("hostile", 5),
    NEUTRAL("neutral", 6),
    PLAYER("player", 7),
    AMBIENT("ambient", 8),
    VOICE("voice", 9);
    
    private final String name;

    /* renamed from: id */
    private final int f204id;

    SoundSource(String name, int id) {
        this.name = name;
        this.f204id = id;
    }

    public static Optional<SoundSource> findBySource(String source) {
        SoundSource[] values;
        for (SoundSource item : values()) {
            if (item.name.equalsIgnoreCase(source)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.f204id;
    }
}
