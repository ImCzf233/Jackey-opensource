package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.platform.LegacyViaInjector;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.util.ReflectionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.log4j.spi.LocationInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/platform/BukkitViaInjector.class */
public class BukkitViaInjector extends LegacyViaInjector {
    private boolean protocolLib;

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector, com.viaversion.viaversion.api.platform.ViaInjector
    public void inject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.setPaperChannelInitializeListener();
        } else {
            super.inject();
        }
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector, com.viaversion.viaversion.api.platform.ViaInjector
    public void uninject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.removePaperChannelInitializeListener();
        } else {
            super.uninject();
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        Field[] declaredFields;
        if (PaperViaInjector.PAPER_PROTOCOL_METHOD) {
            return Bukkit.getUnsafe().getProtocolVersion();
        }
        Class<?> serverClazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        Object server = ReflectionUtil.invokeStatic(serverClazz, "getServer");
        Class<?> pingClazz = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
        Object ping = null;
        Field[] declaredFields2 = serverClazz.getDeclaredFields();
        int length = declaredFields2.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Field field = declaredFields2[i];
            if (field.getType() != pingClazz) {
                i++;
            } else {
                field.setAccessible(true);
                ping = field.get(server);
                break;
            }
        }
        Class<?> serverDataClass = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
        Object serverData = null;
        Field[] declaredFields3 = pingClazz.getDeclaredFields();
        int length2 = declaredFields3.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length2) {
                break;
            }
            Field field2 = declaredFields3[i2];
            if (field2.getType() != serverDataClass) {
                i2++;
            } else {
                field2.setAccessible(true);
                serverData = field2.get(ping);
                break;
            }
        }
        for (Field field3 : serverDataClass.getDeclaredFields()) {
            if (field3.getType() == Integer.TYPE) {
                field3.setAccessible(true);
                int protocolVersion = ((Integer) field3.get(serverData)).intValue();
                if (protocolVersion != -1) {
                    return protocolVersion;
                }
            }
        }
        throw new RuntimeException("Failed to get server");
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector, com.viaversion.viaversion.api.platform.ViaInjector
    public String getDecoderName() {
        return this.protocolLib ? "protocol_lib_decoder" : "decoder";
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected Object getServerConnection() throws ReflectiveOperationException {
        Method[] declaredMethods;
        Object connection;
        Class<?> serverClass = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        Class<?> connectionClass = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
        Object server = ReflectionUtil.invokeStatic(serverClass, "getServer");
        for (Method method : serverClass.getDeclaredMethods()) {
            if (method.getReturnType() == connectionClass && method.getParameterTypes().length == 0 && (connection = method.invoke(server, new Object[0])) != null) {
                return connection;
            }
        }
        return null;
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> oldInitializer) {
        return new BukkitChannelInitializer(oldInitializer);
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected void blame(ChannelHandler bootstrapAcceptor) throws ReflectiveOperationException {
        ClassLoader classLoader = bootstrapAcceptor.getClass().getClassLoader();
        if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
            PluginDescriptionFile description = (PluginDescriptionFile) ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class);
            throw new RuntimeException("Unable to inject, due to " + bootstrapAcceptor.getClass().getName() + ", try without the plugin " + description.getName() + LocationInfo.f402NA);
        }
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
    }

    public boolean isBinded() {
        Field[] declaredFields;
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return true;
        }
        try {
            Object connection = getServerConnection();
            if (connection == null) {
                return false;
            }
            for (Field field : connection.getClass().getDeclaredFields()) {
                if (List.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    List<?> value = (List) field.get(connection);
                    synchronized (value) {
                        if (!value.isEmpty() && (value.get(0) instanceof ChannelFuture)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setProtocolLib(boolean protocolLib) {
        this.protocolLib = protocolLib;
    }
}
