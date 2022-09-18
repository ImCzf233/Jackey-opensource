package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.Locale;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/EntityData.class */
public class EntityData {
    private final BackwardsProtocol<?, ?, ?, ?> protocol;

    /* renamed from: id */
    private final int f9id;
    private final int replacementId;
    private final String key;
    private NameVisibility nameVisibility;
    private MetaCreator defaultMeta;

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/EntityData$MetaCreator.class */
    public interface MetaCreator {
        void createMeta(WrappedMetadata wrappedMetadata);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/EntityData$NameVisibility.class */
    public enum NameVisibility {
        PLAIN,
        JSON,
        NONE
    }

    public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, EntityType type, int replacementId) {
        this(protocol, type.name(), type.getId(), replacementId);
    }

    public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId) {
        this.nameVisibility = NameVisibility.NONE;
        this.protocol = protocol;
        this.f9id = id;
        this.replacementId = replacementId;
        this.key = key.toLowerCase(Locale.ROOT);
    }

    public EntityData jsonName() {
        this.nameVisibility = NameVisibility.JSON;
        return this;
    }

    public EntityData plainName() {
        this.nameVisibility = NameVisibility.PLAIN;
        return this;
    }

    public EntityData spawnMetadata(MetaCreator handler) {
        this.defaultMeta = handler;
        return this;
    }

    public boolean hasBaseMeta() {
        return this.defaultMeta != null;
    }

    public int typeId() {
        return this.f9id;
    }

    public Object mobName() {
        if (this.nameVisibility == NameVisibility.NONE) {
            return null;
        }
        String name = this.protocol.getMappingData().mappedEntityName(this.key);
        if (name == null) {
            ViaBackwards.getPlatform().getLogger().warning("Entity name for " + this.key + " not found in protocol " + this.protocol.getClass().getSimpleName());
            name = this.key;
        }
        return this.nameVisibility == NameVisibility.JSON ? ChatRewriter.legacyTextToJson(name) : name;
    }

    public int replacementId() {
        return this.replacementId;
    }

    public MetaCreator defaultMeta() {
        return this.defaultMeta;
    }

    public boolean isObjectType() {
        return false;
    }

    public int objectData() {
        return -1;
    }

    public String toString() {
        return "EntityData{id=" + this.f9id + ", mobName='" + this.key + "', replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
    }
}
