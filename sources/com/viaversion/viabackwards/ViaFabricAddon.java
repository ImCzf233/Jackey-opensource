package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viabackwards.fabric.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/ViaFabricAddon.class */
public class ViaFabricAddon implements ViaBackwardsPlatform, Runnable {
    private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaBackwards"));
    private File configDir;

    @Override // java.lang.Runnable
    public void run() {
        Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaBackwards");
        this.configDir = configDirPath.toFile();
        init(getDataFolder());
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public void disable() {
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public File getDataFolder() {
        return this.configDir;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public Logger getLogger() {
        return this.logger;
    }
}
