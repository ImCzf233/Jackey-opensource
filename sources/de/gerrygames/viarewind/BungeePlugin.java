package de.gerrygames.viarewind;

import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.io.File;
import net.md_5.bungee.api.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/BungeePlugin.class */
public class BungeePlugin extends Plugin implements ViaRewindPlatform {
    public void onEnable() {
        ViaRewindConfigImpl conf = new ViaRewindConfigImpl(new File(getDataFolder(), "config.yml"));
        conf.reloadConfig();
        init(conf);
    }
}
