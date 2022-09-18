package com.viaversion.viaversion.velocity.providers;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.ServerConnection;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;
import io.netty.channel.ChannelHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/providers/VelocityVersionProvider.class */
public class VelocityVersionProvider extends BaseVersionProvider {
    private static Method getAssociation;

    static {
        try {
            getAssociation = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection").getMethod("getAssociation", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override // com.viaversion.viaversion.protocols.base.BaseVersionProvider, com.viaversion.viaversion.api.protocol.version.VersionProvider
    public int getClosestServerProtocol(UserConnection user) throws Exception {
        return user.isClientSide() ? getBackProtocol(user) : getFrontProtocol(user);
    }

    private int getBackProtocol(UserConnection user) throws Exception {
        ChannelHandler mcHandler = user.getChannel().pipeline().get("handler");
        return ProtocolDetectorService.getProtocolId(((ServerConnection) getAssociation.invoke(mcHandler, new Object[0])).getServerInfo().getName()).intValue();
    }

    private int getFrontProtocol(UserConnection user) throws Exception {
        int playerVersion = user.getProtocolInfo().getProtocolVersion();
        IntStream versions = ProtocolVersion.SUPPORTED_VERSIONS.stream().mapToInt((v0) -> {
            return v0.getProtocol();
        });
        if (VelocityViaInjector.getPlayerInfoForwardingMode != null && ((Enum) VelocityViaInjector.getPlayerInfoForwardingMode.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
            versions = versions.filter(ver -> {
                return ver >= com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion();
            });
        }
        int[] compatibleProtocols = versions.toArray();
        if (Arrays.binarySearch(compatibleProtocols, playerVersion) >= 0) {
            return playerVersion;
        }
        if (playerVersion < compatibleProtocols[0]) {
            return compatibleProtocols[0];
        }
        for (int i = compatibleProtocols.length - 1; i >= 0; i--) {
            int protocol = compatibleProtocols[i];
            if (playerVersion > protocol && com.viaversion.viaversion.api.protocol.version.ProtocolVersion.isRegistered(protocol)) {
                return protocol;
            }
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + playerVersion);
        return playerVersion;
    }
}
