package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import java.lang.reflect.Method;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/providers/BungeeEntityIdProvider.class */
public class BungeeEntityIdProvider extends EntityIdProvider {
    private static Method getClientEntityId;

    static {
        try {
            getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider
    public int getEntityId(UserConnection user) throws Exception {
        BungeeStorage storage = (BungeeStorage) user.get(BungeeStorage.class);
        ProxiedPlayer player = storage.getPlayer();
        return ((Integer) getClientEntityId.invoke(player, new Object[0])).intValue();
    }
}
