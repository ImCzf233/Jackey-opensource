package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/platform/VelocityViaConfig.class */
public class VelocityViaConfig extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "anti-xray-patch", "quick-move-action-fix", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    private int velocityPingInterval;
    private boolean velocityPingSave;
    private Map<String, Integer> velocityServerProtocols;

    public VelocityViaConfig(File configFile) {
        super(new File(configFile, "config.yml"));
        reloadConfig();
    }

    @Override // com.viaversion.viaversion.configuration.AbstractViaConfig
    public void loadFields() {
        super.loadFields();
        this.velocityPingInterval = getInt("velocity-ping-interval", 60);
        this.velocityPingSave = getBoolean("velocity-ping-save", true);
        this.velocityServerProtocols = (Map) get("velocity-servers", Map.class, new HashMap());
    }

    @Override // com.viaversion.viaversion.util.Config
    protected void handleConfig(Map<String, Object> config) {
        Map<String, Object> servers;
        if (!(config.get("velocity-servers") instanceof Map)) {
            servers = new HashMap<>();
        } else {
            servers = (Map) config.get("velocity-servers");
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
            try {
                servers.put("default", Integer.valueOf(VelocityViaInjector.getLowestSupportedProtocolVersion()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        config.put("velocity-servers", servers);
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

    public int getVelocityPingInterval() {
        return this.velocityPingInterval;
    }

    public boolean isVelocityPingSave() {
        return this.velocityPingSave;
    }

    public Map<String, Integer> getVelocityServerProtocols() {
        return this.velocityServerProtocols;
    }
}
