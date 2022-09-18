package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/platform/SpongeViaConfig.class */
public class SpongeViaConfig extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method");

    public SpongeViaConfig(File configFile) {
        super(new File(configFile, "config.yml"));
        reloadConfig();
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }
}
