package com.viaversion.viaversion.velocity.storage;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/storage/VelocityStorage.class */
public class VelocityStorage implements StorableObject {
    private final Player player;
    private String currentServer = "";
    private List<UUID> cachedBossbar;
    private static Method getServerBossBars;
    private static Class<?> clientPlaySessionHandler;
    private static Method getMinecraftConnection;

    static {
        try {
            clientPlaySessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
            getServerBossBars = clientPlaySessionHandler.getDeclaredMethod("getServerBossBars", new Class[0]);
            getMinecraftConnection = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer").getDeclaredMethod("getMinecraftConnection", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public VelocityStorage(Player player) {
        this.player = player;
    }

    public List<UUID> getBossbar() {
        if (this.cachedBossbar == null) {
            if (clientPlaySessionHandler == null || getServerBossBars == null || getMinecraftConnection == null) {
                return null;
            }
            try {
                Object connection = getMinecraftConnection.invoke(this.player, new Object[0]);
                Object sessionHandler = ReflectionUtil.invoke(connection, "getSessionHandler");
                if (clientPlaySessionHandler.isInstance(sessionHandler)) {
                    this.cachedBossbar = (List) getServerBossBars.invoke(sessionHandler, new Object[0]);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return this.cachedBossbar;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getCurrentServer() {
        return this.currentServer;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    public List<UUID> getCachedBossbar() {
        return this.cachedBossbar;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VelocityStorage that = (VelocityStorage) o;
        if (!Objects.equals(this.player, that.player) || !Objects.equals(this.currentServer, that.currentServer)) {
            return false;
        }
        return Objects.equals(this.cachedBossbar, that.cachedBossbar);
    }

    public int hashCode() {
        int result = this.player != null ? this.player.hashCode() : 0;
        return (31 * ((31 * result) + (this.currentServer != null ? this.currentServer.hashCode() : 0))) + (this.cachedBossbar != null ? this.cachedBossbar.hashCode() : 0);
    }
}
