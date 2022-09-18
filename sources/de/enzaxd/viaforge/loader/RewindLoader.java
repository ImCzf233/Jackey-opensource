package de.enzaxd.viaforge.loader;

import com.viaversion.viaversion.api.Via;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.io.File;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/loader/RewindLoader.class */
public class RewindLoader implements ViaRewindPlatform {
    public RewindLoader(File file) {
        ViaRewindConfigImpl conf = new ViaRewindConfigImpl(file.toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        init(conf);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindPlatform
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
}
