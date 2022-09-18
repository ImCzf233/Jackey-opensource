package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/platform/BungeeViaConfig.class */
public class BungeeViaConfig extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "anti-xray-patch", "quick-move-action-fix", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    private int bungeePingInterval;
    private boolean bungeePingSave;
    private Map<String, Integer> bungeeServerProtocols;

    public BungeeViaConfig(File configFile) {
        super(new File(configFile, "config.yml"));
        reloadConfig();
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig
    public void loadFields() {
        super.loadFields();
        this.bungeePingInterval = getInt("bungee-ping-interval", 60);
        this.bungeePingSave = getBoolean("bungee-ping-save", true);
        this.bungeeServerProtocols = (Map) get("bungee-servers", Map.class, new HashMap());
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> config) {
        Map<String, Object> servers;
        if (!(config.get("bungee-servers") instanceof Map)) {
            servers = new HashMap<>();
        } else {
            servers = (Map) config.get("bungee-servers");
        }
        Iterator it = new HashSet(servers.entrySet()).iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) it.next();
            if (!(entry.getValue() instanceof Integer)) {
                if (entry.getValue() instanceof String) {
                    ProtocolVersion found = ProtocolVersion.getClosest((String) entry.getValue());
                    if (found != null) {
                        servers.put(entry.getKey(), Integer.valueOf(found.getVersion()));
                    } else {
                        servers.remove(entry.getKey());
                    }
                } else {
                    servers.remove(entry.getKey());
                }
            }
        }
        if (!servers.containsKey("default")) {
            servers.put("default", Integer.valueOf(BungeeVersionProvider.getLowestSupportedVersion()));
        }
        config.put("bungee-servers", servers);
    }

    @Override // com.viaversion.viaversion.util.Config
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isItemCache() {
        return false;
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig, com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isNMSPlayerTicking() {
        return false;
    }

    public int getBungeePingInterval() {
        return this.bungeePingInterval;
    }

    public boolean isBungeePingSave() {
        return this.bungeePingSave;
    }

    public Map<String, Integer> getBungeeServerProtocols() {
        return this.bungeeServerProtocols;
    }
}
