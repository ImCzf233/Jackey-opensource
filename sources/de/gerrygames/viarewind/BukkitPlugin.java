package de.gerrygames.viarewind;

import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/BukkitPlugin.class */
public class BukkitPlugin extends JavaPlugin implements ViaRewindPlatform {
    public void onEnable() {
        ViaRewindConfigImpl conf = new ViaRewindConfigImpl(new File(getDataFolder(), "config.yml"));
        conf.reloadConfig();
        init(conf);
    }
}
