package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/platform/ViaPlatform.class */
public interface ViaPlatform<T> {
    Logger getLogger();

    String getPlatformName();

    String getPlatformVersion();

    String getPluginVersion();

    PlatformTask runAsync(Runnable runnable);

    PlatformTask runSync(Runnable runnable);

    PlatformTask runSync(Runnable runnable, long j);

    PlatformTask runRepeatingSync(Runnable runnable, long j);

    ViaCommandSender[] getOnlinePlayers();

    void sendMessage(UUID uuid, String str);

    boolean kickPlayer(UUID uuid, String str);

    boolean isPluginEnabled();

    ViaAPI<T> getApi();

    ViaVersionConfig getConf();

    ConfigurationProvider getConfigurationProvider();

    File getDataFolder();

    void onReload();

    JsonObject getDump();

    boolean isOldClientsAllowed();

    default boolean isProxy() {
        return false;
    }

    default boolean disconnect(UserConnection connection, String message) {
        UUID uuid;
        if (!connection.isClientSide() && (uuid = connection.getProtocolInfo().getUuid()) != null) {
            return kickPlayer(uuid, message);
        }
        return false;
    }

    default Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        return Collections.emptyList();
    }
}
