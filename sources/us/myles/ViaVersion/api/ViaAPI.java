package us.myles.ViaVersion.api;

import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.UUID;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

@Deprecated
/* loaded from: Jackey Client b2.jar:us/myles/ViaVersion/api/ViaAPI.class */
public interface ViaAPI<T> {
    int getPlayerVersion(T t);

    int getPlayerVersion(UUID uuid);

    boolean isInjected(UUID uuid);

    String getVersion();

    void sendRawPacket(T t, ByteBuf byteBuf);

    void sendRawPacket(UUID uuid, ByteBuf byteBuf);

    BossBar createBossBar(String str, BossColor bossColor, BossStyle bossStyle);

    BossBar createBossBar(String str, float f, BossColor bossColor, BossStyle bossStyle);

    SortedSet<Integer> getSupportedVersions();

    SortedSet<Integer> getFullSupportedVersions();

    default boolean isPorted(UUID playerUUID) {
        return isInjected(playerUUID);
    }
}
