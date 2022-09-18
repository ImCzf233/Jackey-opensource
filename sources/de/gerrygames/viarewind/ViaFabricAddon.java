package de.gerrygames.viarewind;

import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import de.gerrygames.viarewind.fabric.util.LoggerWrapper;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/ViaFabricAddon.class */
public class ViaFabricAddon implements ViaRewindPlatform, Runnable {
    private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));

    @Override // java.lang.Runnable
    public void run() {
        ViaRewindConfigImpl conf = new ViaRewindConfigImpl(FabricLoader.getInstance().getConfigDirectory().toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        init(conf);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindPlatform
    public Logger getLogger() {
        return this.logger;
    }
}
