package de.gerrygames.viarewind;

import com.google.inject.Inject;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "viarewind", name = "ViaRewind", version = "2.0.3-SNAPSHOT", authors = {"Gerrygames"}, dependencies = {@Dependency(id = "viaversion"), @Dependency(id = "viabackwards", optional = true)}, url = "https://viaversion.com/rewind")
/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/SpongePlugin.class */
public class SpongePlugin implements ViaRewindPlatform {
    private Logger logger;
    @Inject
    private org.slf4j.Logger loggerSlf4j;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Listener(order = Order.LATE)
    public void onGameStart(GameInitializationEvent e) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        ViaRewindConfigImpl conf = new ViaRewindConfigImpl(this.configDir.resolve("config.yml").toFile());
        conf.reloadConfig();
        init(conf);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindPlatform
    public Logger getLogger() {
        return this.logger;
    }
}
