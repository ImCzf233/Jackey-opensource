package com.viaversion.viaversion.api.legacy;

import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/legacy/LegacyViaAPI.class */
public interface LegacyViaAPI<T> {
    BossBar createLegacyBossBar(String str, float f, BossColor bossColor, BossStyle bossStyle);

    default BossBar createLegacyBossBar(String title, BossColor color, BossStyle style) {
        return createLegacyBossBar(title, 1.0f, color, style);
    }
}
