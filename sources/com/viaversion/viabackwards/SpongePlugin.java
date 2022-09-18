package com.viaversion.viabackwards;

import com.google.inject.Inject;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("viabackwards")
/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/SpongePlugin.class */
public class SpongePlugin implements ViaBackwardsPlatform {
    private final Logger logger;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;

    @Inject
    SpongePlugin(org.apache.logging.log4j.Logger logger) {
        this.logger = new LoggerWrapper(logger);
    }

    @Listener
    public void constructPlugin(ConstructPluginEvent event) {
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
