package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.command.ViaVersionCommand;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/ViaManager.class */
public interface ViaManager {
    ProtocolManager getProtocolManager();

    ViaPlatform<?> getPlatform();

    ConnectionManager getConnectionManager();

    ViaProviders getProviders();

    ViaInjector getInjector();

    ViaVersionCommand getCommandHandler();

    ViaPlatformLoader getLoader();

    DebugHandler debugHandler();

    Set<String> getSubPlatforms();

    void addEnableListener(Runnable runnable);

    @Deprecated
    default boolean isDebug() {
        return debugHandler().enabled();
    }

    @Deprecated
    default void setDebug(boolean debug) {
        debugHandler().setEnabled(debug);
    }
}
