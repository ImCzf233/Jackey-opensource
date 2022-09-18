package com.viaversion.viaversion.api.legacy.bossbar;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/legacy/bossbar/BossFlag.class */
public enum BossFlag {
    DARKEN_SKY(1),
    PLAY_BOSS_MUSIC(2),
    CREATE_FOG(4);
    

    /* renamed from: id */
    private final int f26id;

    BossFlag(int id) {
        this.f26id = id;
    }

    public int getId() {
        return this.f26id;
    }
}
