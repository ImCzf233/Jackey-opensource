package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.BackwardsProtocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/EntityObjectData.class */
public class EntityObjectData extends EntityData {
    private final int objectData;

    public EntityObjectData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId, int objectData) {
        super(protocol, key, id, replacementId);
        this.objectData = objectData;
    }

    @Override // com.viaversion.viabackwards.api.entities.storage.EntityData
    public boolean isObjectType() {
        return true;
    }

    @Override // com.viaversion.viabackwards.api.entities.storage.EntityData
    public int objectData() {
        return this.objectData;
    }
}
