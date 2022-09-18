package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/providers/EntityIdProvider.class */
public class EntityIdProvider implements Provider {
    public int getEntityId(UserConnection user) throws Exception {
        return user.getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
    }
}
