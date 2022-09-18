package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.network.ProtocolVersion;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.velocity.handlers.VelocityChannelInitializer;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/platform/VelocityViaInjector.class */
public class VelocityViaInjector implements ViaInjector {
    public static Method getPlayerInfoForwardingMode;

    static {
        try {
            getPlayerInfoForwardingMode = Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private ChannelInitializer getInitializer() throws Exception {
        Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
        return (ChannelInitializer) ReflectionUtil.invoke(channelInitializerHolder, PropertyDescriptor.GET);
    }

    private ChannelInitializer getBackendInitializer() throws Exception {
        Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
        return (ChannelInitializer) ReflectionUtil.invoke(channelInitializerHolder, PropertyDescriptor.GET);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void inject() throws Exception {
        Via.getPlatform().getLogger().info("Replacing channel initializers; you can safely ignore the following two warnings.");
        Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
        ChannelInitializer originalInitializer = getInitializer();
        channelInitializerHolder.getClass().getMethod(PropertyDescriptor.SET, ChannelInitializer.class).invoke(channelInitializerHolder, new VelocityChannelInitializer(originalInitializer, false));
        Object backendInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
        ChannelInitializer backendInitializer = getBackendInitializer();
        backendInitializerHolder.getClass().getMethod(PropertyDescriptor.SET, ChannelInitializer.class).invoke(backendInitializerHolder, new VelocityChannelInitializer(backendInitializer, true));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public int getServerProtocolVersion() throws Exception {
        return getLowestSupportedProtocolVersion();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public IntSortedSet getServerProtocolVersions() throws Exception {
        int lowestSupportedProtocolVersion = getLowestSupportedProtocolVersion();
        IntSortedSet set = new IntLinkedOpenHashSet();
        for (ProtocolVersion version : ProtocolVersion.SUPPORTED_VERSIONS) {
            if (version.getProtocol() >= lowestSupportedProtocolVersion) {
                set.add(version.getProtocol());
            }
        }
        return set;
    }

    public static int getLowestSupportedProtocolVersion() {
        try {
            if (getPlayerInfoForwardingMode != null && ((Enum) getPlayerInfoForwardingMode.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
                return com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return ProtocolVersion.MINIMUM_VERSION.getProtocol();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public JsonObject getDump() {
        JsonObject data = new JsonObject();
        try {
            data.addProperty("currentInitializer", getInitializer().getClass().getName());
        } catch (Exception e) {
        }
        return data;
    }
}
