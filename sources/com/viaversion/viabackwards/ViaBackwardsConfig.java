package com.viaversion.viabackwards;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/ViaBackwardsConfig.class */
public class ViaBackwardsConfig extends Config implements com.viaversion.viabackwards.api.ViaBackwardsConfig {
    private boolean addCustomEnchantsToLore;
    private boolean addTeamColorToPrefix;
    private boolean fix1_13FacePlayer;
    private boolean alwaysShowOriginalMobName;
    private boolean fix1_13FormattedInventoryTitles;
    private boolean handlePingsAsInvAcknowledgements;

    public ViaBackwardsConfig(File configFile) {
        super(configFile);
    }

    @Override // com.viaversion.viaversion.util.Config, com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public void reloadConfig() {
        super.reloadConfig();
        loadFields();
    }

    private void loadFields() {
        this.addCustomEnchantsToLore = getBoolean("add-custom-enchants-into-lore", true);
        this.addTeamColorToPrefix = getBoolean("add-teamcolor-to-prefix", true);
        this.fix1_13FacePlayer = getBoolean("fix-1_13-face-player", false);
        this.fix1_13FormattedInventoryTitles = getBoolean("fix-formatted-inventory-titles", true);
        this.alwaysShowOriginalMobName = getBoolean("always-show-original-mob-name", true);
        this.handlePingsAsInvAcknowledgements = getBoolean("handle-pings-as-inv-acknowledgements", false);
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean addCustomEnchantsToLore() {
        return this.addCustomEnchantsToLore;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean addTeamColorTo1_13Prefix() {
        return this.addTeamColorToPrefix;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean isFix1_13FacePlayer() {
        return this.fix1_13FacePlayer;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean fix1_13FormattedInventoryTitle() {
        return this.fix1_13FormattedInventoryTitles;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean alwaysShowOriginalMobName() {
        return this.alwaysShowOriginalMobName;
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsConfig
    public boolean handlePingsAsInvAcknowledgements() {
        return this.handlePingsAsInvAcknowledgements || Boolean.getBoolean("com.viaversion.handlePingsAsInvAcknowledgements");
    }

    @Override // com.viaversion.viaversion.util.Config
    public URL getDefaultConfigURL() {
        return getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> map) {
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
