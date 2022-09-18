package com.viaversion.viaversion.velocity.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.velocity.storage.VelocityStorage;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/providers/VelocityBossBarProvider.class */
public class VelocityBossBarProvider extends BossBarProvider {
    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider
    public void handleAdd(UserConnection user, UUID barUUID) {
        if (user.has(VelocityStorage.class)) {
            VelocityStorage storage = (VelocityStorage) user.get(VelocityStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().add(barUUID);
            }
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider
    public void handleRemove(UserConnection user, UUID barUUID) {
        if (user.has(VelocityStorage.class)) {
            VelocityStorage storage = (VelocityStorage) user.get(VelocityStorage.class);
            if (storage.getBossbar() != null) {
                storage.getBossbar().remove(barUUID);
            }
        }
    }
}
