package de.enzaxd.viaforge.platform;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/platform/ViaConfig.class */
public class ViaConfig extends AbstractViaConfig {
    private static List<String> UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");

    public ViaConfig(File configFile) {
        super(configFile);
        reloadConfig();
    }

    @Override // com.viaversion.viaversion.util.Config
    public URL getDefaultConfigURL() {
        return getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isAntiXRay() {
        return false;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isNMSPlayerTicking() {
        return false;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_12QuickMoveActionFix() {
        return false;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getBlockConnectionMethod() {
        return "packet";
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_9HitboxFix() {
        return false;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_14HitboxFix() {
        return false;
    }
}
