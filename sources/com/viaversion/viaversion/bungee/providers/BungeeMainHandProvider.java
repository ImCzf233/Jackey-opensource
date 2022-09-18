package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/providers/BungeeMainHandProvider.class */
public class BungeeMainHandProvider extends MainHandProvider {
    private static Method getSettings;
    private static Method setMainHand;

    static {
        getSettings = null;
        setMainHand = null;
        try {
            getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", new Class[0]);
            setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", Integer.TYPE);
        } catch (Exception e) {
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider
    public void setMainHand(UserConnection user, int hand) {
        ProxiedPlayer player;
        ProtocolInfo info = user.getProtocolInfo();
        if (info == null || info.getUuid() == null || (player = ProxyServer.getInstance().getPlayer(info.getUuid())) == null) {
            return;
        }
        try {
            Object settings = getSettings.invoke(player, new Object[0]);
            if (settings != null) {
                setMainHand.invoke(settings, Integer.valueOf(hand));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
