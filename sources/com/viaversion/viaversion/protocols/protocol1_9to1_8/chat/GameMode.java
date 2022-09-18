package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/chat/GameMode.class */
public enum GameMode {
    SURVIVAL(0, "Survival Mode"),
    CREATIVE(1, "Creative Mode"),
    ADVENTURE(2, "Adventure Mode"),
    SPECTATOR(3, "Spectator Mode");
    

    /* renamed from: id */
    private final int f206id;
    private final String text;

    GameMode(int id, String text) {
        this.f206id = id;
        this.text = text;
    }

    public int getId() {
        return this.f206id;
    }

    public String getText() {
        return this.text;
    }

    public static GameMode getById(int id) {
        GameMode[] values;
        for (GameMode gm : values()) {
            if (gm.getId() == id) {
                return gm;
            }
        }
        return null;
    }
}
