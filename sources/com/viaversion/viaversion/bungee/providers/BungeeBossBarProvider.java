package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/providers/BungeeBossBarProvider.class */
public class BungeeBossBarProvider extends BossBarProvider {
    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider
    public void handleAdd(UserConnection user, UUID barUUID) {
        if (user.has(BungeeStorage.class)) {
            BungeeStorage storage = (BungeeStorage) user.get(BungeeStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().add(barUUID);
            }
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider
    public void handleRemove(UserConnection user, UUID barUUID) {
        if (user.has(BungeeStorage.class)) {
            BungeeStorage storage = (BungeeStorage) user.get(BungeeStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().remove(barUUID);
            }
        }
    }
}
