package com.viaversion.viaversion.data.entity;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/data/entity/EntityTrackerBase.class */
public class EntityTrackerBase implements EntityTracker, ClientEntityIdChangeListener {
    private final Int2ObjectMap<EntityType> entityTypes;
    private final Int2ObjectMap<StoredEntityData> entityData;
    private final UserConnection connection;
    private final EntityType playerType;
    private int clientEntityId;
    private int currentWorldSectionHeight;
    private int currentMinY;
    private String currentWorld;
    private int biomesSent;

    public EntityTrackerBase(UserConnection connection, EntityType playerType) {
        this(connection, playerType, false);
    }

    public EntityTrackerBase(UserConnection connection, EntityType playerType, boolean storesEntityData) {
        this.entityTypes = Int2ObjectSyncMap.hashmap();
        this.clientEntityId = -1;
        this.currentWorldSectionHeight = 16;
        this.biomesSent = -1;
        this.connection = connection;
        this.playerType = playerType;
        this.entityData = storesEntityData ? Int2ObjectSyncMap.hashmap() : null;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public UserConnection user() {
        return this.connection;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void addEntity(int id, EntityType type) {
        this.entityTypes.put(id, (int) type);
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public boolean hasEntity(int id) {
        return this.entityTypes.containsKey(id);
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public EntityType entityType(int id) {
        return this.entityTypes.get(id);
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public StoredEntityData entityData(int id) {
        Preconditions.checkArgument(this.entityData != null, "Entity data storage has to be explicitly enabled via the constructor");
        EntityType type = entityType(id);
        if (type != null) {
            return this.entityData.computeIfAbsent(id, s -> {
                return new StoredEntityImpl(type);
            });
        }
        return null;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public StoredEntityData entityDataIfPresent(int id) {
        Preconditions.checkArgument(this.entityData != null, "Entity data storage has to be explicitly enabled via the constructor");
        return this.entityData.get(id);
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void removeEntity(int id) {
        this.entityTypes.remove(id);
        if (this.entityData != null) {
            this.entityData.remove(id);
        }
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void clearEntities() {
        this.entityTypes.clear();
        if (this.entityData != null) {
            this.entityData.clear();
        }
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public int clientEntityId() {
        return this.clientEntityId;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker, com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener
    public void setClientEntityId(int clientEntityId) {
        StoredEntityData data;
        Preconditions.checkNotNull(this.playerType);
        this.entityTypes.put(clientEntityId, (int) this.playerType);
        if (this.clientEntityId != -1 && this.entityData != null && (data = this.entityData.remove(this.clientEntityId)) != null) {
            this.entityData.put(clientEntityId, (int) data);
        }
        this.clientEntityId = clientEntityId;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public int currentWorldSectionHeight() {
        return this.currentWorldSectionHeight;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void setCurrentWorldSectionHeight(int currentWorldSectionHeight) {
        this.currentWorldSectionHeight = currentWorldSectionHeight;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public int currentMinY() {
        return this.currentMinY;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void setCurrentMinY(int currentMinY) {
        this.currentMinY = currentMinY;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public String currentWorld() {
        return this.currentWorld;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void setCurrentWorld(String currentWorld) {
        this.currentWorld = currentWorld;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public int biomesSent() {
        return this.biomesSent;
    }

    @Override // com.viaversion.viaversion.api.data.entity.EntityTracker
    public void setBiomesSent(int biomesSent) {
        this.biomesSent = biomesSent;
    }
}
