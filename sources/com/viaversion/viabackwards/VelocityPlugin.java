package com.viaversion.viabackwards;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "viabackwards", name = "ViaBackwards", version = "4.2.1", authors = {"Matsv", "kennytv", "Gerrygames", "creeper123123321", "ForceUpdate1"}, description = "Allow older Minecraft versions to connect to a newer server version.", dependencies = {@Dependency(id = "viaversion")})
/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/VelocityPlugin.class */
public class VelocityPlugin implements ViaBackwardsPlatform {
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @DataDirectory
    private Path configPath;

    @Subscribe(order = PostOrder.LATE)
    public void onProxyStart(ProxyInitializeEvent e) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        Via.getManager().addEnableListener(() -> {
            init(getDataFolder());
        });
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public void disable() {
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public File getDataFolder() {
        return this.configPath.toFile();
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public Logger getLogger() {
        return this.logger;
    }
}
