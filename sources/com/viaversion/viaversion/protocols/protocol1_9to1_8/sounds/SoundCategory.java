package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/sounds/SoundCategory.class */
public enum SoundCategory {
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
    private final int f208id;

    SoundCategory(String name, int id) {
        this.name = name;
        this.f208id = id;
    }

    public int getId() {
        return this.f208id;
    }

    public String getName() {
        return this.name;
    }
}
