package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacyEntityRewriter.class */
public abstract class LegacyEntityRewriter<T extends BackwardsProtocol> extends EntityRewriterBase<T> {
    private final Map<ObjectType, EntityData> objectTypes;

    public LegacyEntityRewriter(T protocol) {
        this(protocol, MetaType1_9.String, MetaType1_9.Boolean);
    }

    public LegacyEntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
        super(protocol, displayType, 2, displayVisibilityType, 3);
        this.objectTypes = new HashMap();
    }

    public EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
        EntityObjectData entData = new EntityObjectData((BackwardsProtocol) this.protocol, oldObjectType.getType().name(), oldObjectType.getId(), replacement.getId(), data);
        this.objectTypes.put(oldObjectType, entData);
        return entData;
    }

    protected EntityData getObjectData(ObjectType type) {
        return this.objectTypes.get(type);
    }

    public void registerRespawn(ClientboundPacketType packetType) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    clientWorld.setEnvironment(((Integer) wrapper.get(Type.INT, 0)).intValue());
                });
            }
        });
    }

    public void registerJoinGame(ClientboundPacketType packetType, final EntityType playerType) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                EntityType entityType = playerType;
                handler(wrapper -> {
                    ClientWorld clientChunks = (ClientWorld) entityType.user().get(ClientWorld.class);
                    clientChunks.setEnvironment(((Integer) entityType.get(Type.INT, 1)).intValue());
                    LegacyEntityRewriter.this.addTrackedEntity(entityType, ((Integer) entityType.get(Type.INT, 0)).intValue(), playerType);
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void registerMetadataRewriter(ClientboundPacketType packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                if (oldMetaType != null) {
                    map(oldMetaType, newMetaType);
                } else {
                    map(newMetaType);
                }
                Type type = newMetaType;
                handler(wrapper -> {
                    List<Metadata> metadata = (List) type.get(newMetaType, 0);
                    LegacyEntityRewriter.this.handleMetadata(((Integer) type.get(Type.VAR_INT, 0)).intValue(), metadata, type.user());
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void registerMetadataRewriter(ClientboundPacketType packetType, Type<List<Metadata>> metaType) {
        registerMetadataRewriter(packetType, null, metaType);
    }

    public PacketHandler getMobSpawnRewriter(Type<List<Metadata>> metaType) {
        return wrapper -> {
            int entityId = ((Integer) metaType.get(Type.VAR_INT, 0)).intValue();
            EntityType type = tracker(metaType.user()).entityType(entityId);
            List<Metadata> metadata = (List) metaType.get(metaType, 0);
            handleMetadata(entityId, metadata, metaType.user());
            EntityData entityData = entityDataForType(type);
            if (entityData != null) {
                metaType.set(Type.VAR_INT, 1, Integer.valueOf(entityData.replacementId()));
                if (entityData.hasBaseMeta()) {
                    entityData.defaultMeta().createMeta(new WrappedMetadata(metadata));
                }
            }
        };
    }

    public PacketHandler getObjectTrackerHandler() {
        return wrapper -> {
            addTrackedEntity(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), getObjectTypeFromId(((Byte) wrapper.get(Type.BYTE, 0)).byteValue()));
        };
    }

    public PacketHandler getTrackerAndMetaHandler(Type<List<Metadata>> metaType, EntityType entityType) {
        return wrapper -> {
            addTrackedEntity(metaType, ((Integer) metaType.get(Type.VAR_INT, 0)).intValue(), entityType);
            List<Metadata> metadata = (List) metaType.get(entityType, 0);
            handleMetadata(((Integer) metaType.get(Type.VAR_INT, 0)).intValue(), metadata, metaType.user());
        };
    }

    public PacketHandler getObjectRewriter(Function<Byte, ObjectType> objectGetter) {
        return wrapper -> {
            ObjectType type = (ObjectType) objectGetter.apply((Byte) objectGetter.get(Type.BYTE, 0));
            if (type == null) {
                ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + objectGetter.get(Type.BYTE, 0));
                return;
            }
            EntityData data = getObjectData(type);
            if (data != null) {
                objectGetter.set(Type.BYTE, 0, Byte.valueOf((byte) data.replacementId()));
                if (data.objectData() != -1) {
                    objectGetter.set(Type.INT, 0, Integer.valueOf(data.objectData()));
                }
            }
        };
    }

    protected EntityType getObjectTypeFromId(int typeId) {
        return typeFromId(typeId);
    }

    @Deprecated
    public void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
        tracker(wrapper.user()).addEntity(entityId, type);
    }
}
