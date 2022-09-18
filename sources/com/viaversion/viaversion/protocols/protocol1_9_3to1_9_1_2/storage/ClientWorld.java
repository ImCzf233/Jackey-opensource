package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Environment;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld.class */
public class ClientWorld extends StoredObject {
    private Environment environment;

    public ClientWorld(UserConnection connection) {
        super(connection);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(int environmentId) {
        this.environment = Environment.getEnvironmentById(environmentId);
    }
}
