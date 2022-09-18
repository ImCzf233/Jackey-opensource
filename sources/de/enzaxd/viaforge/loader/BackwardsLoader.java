package de.enzaxd.viaforge.loader;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import de.enzaxd.viaforge.ViaForge;
import java.io.File;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/loader/BackwardsLoader.class */
public class BackwardsLoader implements ViaBackwardsPlatform {
    private final File file;

    public BackwardsLoader(File file) {
        File file2 = new File(file, "ViaBackwards");
        this.file = file2;
        init(file2);
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public Logger getLogger() {
        return ViaForge.getInstance().getjLogger();
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public void disable() {
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public boolean isOutdated() {
        return false;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public File getDataFolder() {
        return new File(this.file, "config.yml");
    }
}
