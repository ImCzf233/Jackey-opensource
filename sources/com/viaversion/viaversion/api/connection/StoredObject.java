package com.viaversion.viaversion.api.connection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/connection/StoredObject.class */
public abstract class StoredObject implements StorableObject {
    private final UserConnection user;

    public StoredObject(UserConnection user) {
        this.user = user;
    }

    public UserConnection getUser() {
        return this.user;
    }
}
