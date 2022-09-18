package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/entity/StoredEntityData.class */
public interface StoredEntityData {
    EntityType type();

    boolean has(Class<?> cls);

    <T> T get(Class<T> cls);

    void put(Object obj);
}
