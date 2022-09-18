package us.myles.ViaVersion.api;

import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.UUID;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

@Deprecated
/* loaded from: Jackey Client b2.jar:us/myles/ViaVersion/api/Via.class */
public class Via<T> implements ViaAPI<T> {
    private static final ViaAPI INSTANCE = new Via();

    private Via() {
    }

    @Deprecated
    public static ViaAPI getAPI() {
        return INSTANCE;
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public int getPlayerVersion(T player) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion((com.viaversion.viaversion.api.ViaAPI) player);
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public int getPlayerVersion(UUID uuid) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(uuid);
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public boolean isInjected(UUID playerUUID) {
        return com.viaversion.viaversion.api.Via.getAPI().isInjected(playerUUID);
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public String getVersion() {
        return com.viaversion.viaversion.api.Via.getAPI().getVersion();
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public void sendRawPacket(T player, ByteBuf packet) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket((com.viaversion.viaversion.api.ViaAPI) player, packet);
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public void sendRawPacket(UUID uuid, ByteBuf packet) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(uuid, packet);
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public BossBar createBossBar(String title, BossColor color, BossStyle style) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(title, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[color.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[style.ordinal()]));
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public BossBar createBossBar(String title, float health, BossColor color, BossStyle style) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(title, health, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[color.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[style.ordinal()]));
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public SortedSet<Integer> getSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getSupportedVersions();
    }

    @Override // us.myles.ViaVersion.api.ViaAPI
    public SortedSet<Integer> getFullSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getFullSupportedVersions();
    }
}
