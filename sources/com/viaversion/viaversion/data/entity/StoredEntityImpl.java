package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/data/entity/StoredEntityImpl.class */
public final class StoredEntityImpl implements StoredEntityData {
    private final Map<Class<?>, Object> storedObjects = new ConcurrentHashMap();
    private final EntityType type;

    public StoredEntityImpl(EntityType type) {
        this.type = type;
    }

    @Override // com.viaversion.viaversion.api.data.entity.StoredEntityData
    public EntityType type() {
        return this.type;
    }

    @Override // com.viaversion.viaversion.api.data.entity.StoredEntityData
    public <T> T get(Class<T> objectClass) {
        return (T) this.storedObjects.get(objectClass);
    }

    @Override // com.viaversion.viaversion.api.data.entity.StoredEntityData
    public boolean has(Class<?> objectClass) {
        return this.storedObjects.containsKey(objectClass);
    }

    @Override // com.viaversion.viaversion.api.data.entity.StoredEntityData
    public void put(Object object) {
        this.storedObjects.put(object.getClass(), object);
    }
}
