package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/storage/DifficultyStorage.class */
public class DifficultyStorage extends StoredObject {
    private byte difficulty;

    public DifficultyStorage(UserConnection user) {
        super(user);
    }

    public byte getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }
}
