package com.viaversion.viaversion.api.legacy.bossbar;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/legacy/bossbar/BossBar.class */
public interface BossBar {
    String getTitle();

    BossBar setTitle(String str);

    float getHealth();

    BossBar setHealth(float f);

    BossColor getColor();

    BossBar setColor(BossColor bossColor);

    BossStyle getStyle();

    BossBar setStyle(BossStyle bossStyle);

    BossBar addPlayer(UUID uuid);

    BossBar addConnection(UserConnection userConnection);

    BossBar removePlayer(UUID uuid);

    BossBar removeConnection(UserConnection userConnection);

    BossBar addFlag(BossFlag bossFlag);

    BossBar removeFlag(BossFlag bossFlag);

    boolean hasFlag(BossFlag bossFlag);

    Set<UUID> getPlayers();

    Set<UserConnection> getConnections();

    BossBar show();

    BossBar hide();

    boolean isVisible();

    UUID getId();
}
