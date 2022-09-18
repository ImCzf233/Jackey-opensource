package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/storage/ClientChunks.class */
public class ClientChunks extends StoredObject {
    private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();

    public ClientChunks(UserConnection connection) {
        super(connection);
    }

    public static long toLong(int msw, int lsw) {
        return (msw << 32) + lsw + 2147483648L;
    }

    public Set<Long> getLoadedChunks() {
        return this.loadedChunks;
    }
}
