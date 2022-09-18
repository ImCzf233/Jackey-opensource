package com.viaversion.viaversion.bungee.service;

import com.viaversion.viaversion.BungeePlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/service/ProtocolDetectorService.class */
public class ProtocolDetectorService implements Runnable {
    private static final Map<String, Integer> detectedProtocolIds = new ConcurrentHashMap();
    private static ProtocolDetectorService instance;
    private final BungeePlugin plugin;

    public ProtocolDetectorService(BungeePlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static Integer getProtocolId(String serverName) {
        Map<String, Integer> servers = ((BungeeViaConfig) Via.getConfig()).getBungeeServerProtocols();
        Integer protocol = servers.get(serverName);
        if (protocol != null) {
            return protocol;
        }
        Integer detectedProtocol = detectedProtocolIds.get(serverName);
        if (detectedProtocol != null) {
            return detectedProtocol;
        }
        Integer defaultProtocol = servers.get("default");
        if (defaultProtocol != null) {
            return defaultProtocol;
        }
        return Integer.valueOf(BungeeVersionProvider.getLowestSupportedVersion());
    }

    @Override // java.lang.Runnable
    public void run() {
        for (Map.Entry<String, ServerInfo> lists : this.plugin.getProxy().getServers().entrySet()) {
            probeServer(lists.getValue());
        }
    }

    public static void probeServer(ServerInfo serverInfo) {
        final String key = serverInfo.getName();
        serverInfo.ping(new Callback<ServerPing>() { // from class: com.viaversion.viaversion.bungee.service.ProtocolDetectorService.1
            public void done(ServerPing serverPing, Throwable throwable) {
                if (throwable == null && serverPing != null && serverPing.getVersion() != null && serverPing.getVersion().getProtocol() > 0) {
                    ProtocolDetectorService.detectedProtocolIds.put(key, Integer.valueOf(serverPing.getVersion().getProtocol()));
                    if (((BungeeViaConfig) Via.getConfig()).isBungeePingSave()) {
                        Map<String, Integer> servers = ((BungeeViaConfig) Via.getConfig()).getBungeeServerProtocols();
                        Integer protocol = servers.get(key);
                        if (protocol != null && protocol.intValue() == serverPing.getVersion().getProtocol()) {
                            return;
                        }
                        synchronized (Via.getPlatform().getConfigurationProvider()) {
                            servers.put(key, Integer.valueOf(serverPing.getVersion().getProtocol()));
                        }
                        Via.getPlatform().getConfigurationProvider().saveConfig();
                    }
                }
            }
        });
    }

    public static Map<String, Integer> getDetectedIds() {
        return new HashMap(detectedProtocolIds);
    }

    public static ProtocolDetectorService getInstance() {
        return instance;
    }

    public BungeePlugin getPlugin() {
        return this.plugin;
    }
}
