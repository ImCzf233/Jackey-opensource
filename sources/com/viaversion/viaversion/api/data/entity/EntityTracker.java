package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/entity/EntityTracker.class */
public interface EntityTracker {
    UserConnection user();

    void addEntity(int i, EntityType entityType);

    boolean hasEntity(int i);

    EntityType entityType(int i);

    void removeEntity(int i);

    void clearEntities();

    StoredEntityData entityData(int i);

    StoredEntityData entityDataIfPresent(int i);

    int clientEntityId();

    void setClientEntityId(int i);

    int currentWorldSectionHeight();

    void setCurrentWorldSectionHeight(int i);

    int currentMinY();

    void setCurrentMinY(int i);

    String currentWorld();

    void setCurrentWorld(String str);

    int biomesSent();

    void setBiomesSent(int i);
}
