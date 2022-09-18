package de.gerrygames.viarewind.api;

import com.viaversion.viaversion.util.Config;
import de.gerrygames.viarewind.api.ViaRewindConfig;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/api/ViaRewindConfigImpl.class */
public class ViaRewindConfigImpl extends Config implements ViaRewindConfig {
    public ViaRewindConfigImpl(File configFile) {
        super(configFile);
        reloadConfig();
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindConfig
    public ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
        return ViaRewindConfig.CooldownIndicator.valueOf(getString("cooldown-indicator", "TITLE").toUpperCase());
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindConfig
    public boolean isReplaceAdventureMode() {
        return getBoolean("replace-adventure", false);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindConfig
    public boolean isReplaceParticles() {
        return getBoolean("replace-particles", false);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindConfig
    public int getMaxBookPages() {
        return getInt("max-book-pages", 100);
    }

    @Override // de.gerrygames.viarewind.api.ViaRewindConfig
    public int getMaxBookPageSize() {
        return getInt("max-book-page-length", Level.TRACE_INT);
    }

    @Override // com.viaversion.viaversion.util.Config
    public URL getDefaultConfigURL() {
        return getClass().getClassLoader().getResource("assets/viarewind/config.yml");
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> map) {
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
