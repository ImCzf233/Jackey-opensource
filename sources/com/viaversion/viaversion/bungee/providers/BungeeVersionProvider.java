package com.viaversion.viaversion.bungee.providers;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/providers/BungeeVersionProvider.class */
public class BungeeVersionProvider extends BaseVersionProvider {
    private static Class<?> ref;

    static {
        try {
            ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
        } catch (Exception e) {
            Via.getPlatform().getLogger().severe("Could not detect the ProtocolConstants class");
            e.printStackTrace();
        }
    }

    @Override // com.viaversion.viaversion.protocols.base.BaseVersionProvider, com.viaversion.viaversion.api.protocol.version.VersionProvider
    public int getClosestServerProtocol(UserConnection user) throws Exception {
        if (ref == null) {
            return super.getClosestServerProtocol(user);
        }
        List<Integer> list = (List) ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
        List<Integer> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        ProtocolInfo info = user.getProtocolInfo();
        if (sorted.contains(Integer.valueOf(info.getProtocolVersion()))) {
            return info.getProtocolVersion();
        }
        if (info.getProtocolVersion() < sorted.get(0).intValue()) {
            return getLowestSupportedVersion();
        }
        for (Integer protocol : Lists.reverse(sorted)) {
            if (info.getProtocolVersion() > protocol.intValue() && ProtocolVersion.isRegistered(protocol.intValue())) {
                return protocol.intValue();
            }
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + info.getProtocolVersion());
        return info.getProtocolVersion();
    }

    public static int getLowestSupportedVersion() {
        try {
            List<Integer> list = (List) ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
            return list.get(0).intValue();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ProxyServer.getInstance().getProtocolVersion();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return ProxyServer.getInstance().getProtocolVersion();
        }
    }
}
