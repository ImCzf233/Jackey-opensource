package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/platform/BukkitViaConfig.class */
public class BukkitViaConfig extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers");
    private boolean antiXRay;
    private boolean quickMoveActionFix;
    private boolean hitboxFix1_9;
    private boolean hitboxFix1_14;
    private String blockConnectionMethod;

    public BukkitViaConfig() {
        super(new File(Via.getPlatform().getDataFolder(), "config.yml"));
        reloadConfig();
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig
    public void loadFields() {
        super.loadFields();
        this.antiXRay = getBoolean("anti-xray-patch", true);
        this.quickMoveActionFix = getBoolean("quick-move-action-fix", false);
        this.hitboxFix1_9 = getBoolean("change-1_9-hitbox", false);
        this.hitboxFix1_14 = getBoolean("change-1_14-hitbox", false);
        this.blockConnectionMethod = getString("blockconnection-method", "packet");
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isAntiXRay() {
        return this.antiXRay;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_12QuickMoveActionFix() {
        return this.quickMoveActionFix;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_9HitboxFix() {
        return this.hitboxFix1_9;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_14HitboxFix() {
        return this.hitboxFix1_14;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getBlockConnectionMethod() {
        return this.blockConnectionMethod;
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }
}
