package com.viaversion.viaversion.legacy;

import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.legacy.bossbar.CommonBoss;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/legacy/LegacyAPI.class */
public final class LegacyAPI<T> implements LegacyViaAPI<T> {
    @Override // com.viaversion.viaversion.api.legacy.LegacyViaAPI
    public BossBar createLegacyBossBar(String title, float health, BossColor color, BossStyle style) {
        return new CommonBoss(title, health, color, style);
    }
}
