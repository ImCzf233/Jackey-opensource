package com.viaversion.viabackwards.api.rewriters;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/EntityRewriterBase.class */
public abstract class EntityRewriterBase<T extends BackwardsProtocol> extends com.viaversion.viaversion.rewriter.EntityRewriter<T> {
    private final Int2ObjectMap<EntityData> entityDataMappings = new Int2ObjectOpenHashMap();
    private final MetaType displayNameMetaType;
    private final MetaType displayVisibilityMetaType;
    private final int displayNameIndex;
    private final int displayVisibilityIndex;

    public EntityRewriterBase(T protocol, MetaType displayNameMetaType, int displayNameIndex, MetaType displayVisibilityMetaType, int displayVisibilityIndex) {
        super(protocol, false);
        this.displayNameMetaType = displayNameMetaType;
        this.displayNameIndex = displayNameIndex;
        this.displayVisibilityMetaType = displayVisibilityMetaType;
        this.displayVisibilityIndex = displayVisibilityIndex;
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter, com.viaversion.viaversion.api.rewriter.EntityRewriter
    public void handleMetadata(int entityId, List<Metadata> metadataList, UserConnection connection) {
        super.handleMetadata(entityId, metadataList, connection);
        EntityType type = tracker(connection).entityType(entityId);
        if (type == null) {
            return;
        }
        EntityData entityData = entityDataForType(type);
        Metadata meta = getMeta(this.displayNameIndex, metadataList);
        if (meta != null && entityData != null && entityData.mobName() != null && ((meta.getValue() == null || meta.getValue().toString().isEmpty()) && meta.metaType().typeId() == this.displayNameMetaType.typeId())) {
            meta.setValue(entityData.mobName());
            if (ViaBackwards.getConfig().alwaysShowOriginalMobName()) {
                removeMeta(this.displayVisibilityIndex, metadataList);
                metadataList.add(new Metadata(this.displayVisibilityIndex, this.displayVisibilityMetaType, true));
            }
        }
        if (entityData != null && entityData.hasBaseMeta()) {
            entityData.defaultMeta().createMeta(new WrappedMetadata(metadataList));
        }
    }

    protected Metadata getMeta(int metaIndex, List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            if (metadata.m222id() == metaIndex) {
                return metadata;
            }
        }
        return null;
    }

    protected void removeMeta(int metaIndex, List<Metadata> metadataList) {
        metadataList.removeIf(meta -> {
            return meta.m222id() == metaIndex;
        });
    }

    public boolean hasData(EntityType type) {
        return this.entityDataMappings.containsKey(type.getId());
    }

    public EntityData entityDataForType(EntityType type) {
        return this.entityDataMappings.get(type.getId());
    }

    public StoredEntityData storedEntityData(MetaHandlerEvent event) {
        return tracker(event.user()).entityData(event.entityId());
    }

    public EntityData mapEntityTypeWithData(EntityType type, EntityType mappedType) {
        Preconditions.checkArgument(type.getClass() == mappedType.getClass());
        int mappedReplacementId = newEntityId(mappedType.getId());
        EntityData data = new EntityData((BackwardsProtocol) this.protocol, type, mappedReplacementId);
        mapEntityType(type.getId(), mappedReplacementId);
        this.entityDataMappings.put(type.getId(), (int) data);
        return data;
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public <E extends Enum<E> & EntityType> void mapTypes(EntityType[] oldTypes, Class<E> newTypeClass) {
        if (this.typeMappings == null) {
            this.typeMappings = new Int2IntOpenHashMap(oldTypes.length, 0.99f);
            this.typeMappings.defaultReturnValue(-1);
        }
        for (EntityType oldType : oldTypes) {
            try {
                this.typeMappings.put(oldType.getId(), ((EntityType) Enum.valueOf(newTypeClass, oldType.name())).getId());
            } catch (IllegalArgumentException e) {
            }
        }
    }

    public void registerMetaTypeHandler(MetaType itemType, MetaType blockType, MetaType particleType, MetaType optChatType) {
        filter().handler(event, meta -> {
            JsonElement text;
            MetaType type = meta.metaType();
            if (itemType != null && type == itemType) {
                ((BackwardsProtocol) this.protocol).getItemRewriter().handleItemToClient((Item) meta.value());
            } else if (itemType != null && type == itemType) {
                int data = ((Integer) meta.value()).intValue();
                meta.setValue(Integer.valueOf(((BackwardsProtocol) this.protocol).getMappingData().getNewBlockStateId(data)));
            } else if (blockType != null && type == blockType) {
                rewriteParticle((Particle) meta.value());
            } else if (particleType != null && type == particleType && (text = (JsonElement) meta.value()) != null) {
                ((BackwardsProtocol) this.protocol).getTranslatableRewriter().processText(text);
            }
        });
    }

    public PacketHandler getTrackerHandler(Type<? extends Number> intType, int typeIndex) {
        return wrapper -> {
            Number id = (Number) typeIndex.get(intType, intType);
            tracker(typeIndex.user()).addEntity(((Integer) typeIndex.get(Type.VAR_INT, 0)).intValue(), typeFromId(id.intValue()));
        };
    }

    public PacketHandler getTrackerHandler() {
        return getTrackerHandler(Type.VAR_INT, 1);
    }

    public PacketHandler getTrackerHandler(EntityType entityType, Type<? extends Number> intType) {
        return wrapper -> {
            tracker(entityType.user()).addEntity(((Integer) ((Number) entityType.get(intType, 0))).intValue(), intType);
        };
    }

    public PacketHandler getDimensionHandler(int index) {
        return wrapper -> {
            ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
            int dimensionId = ((Integer) wrapper.get(Type.INT, index)).intValue();
            clientWorld.setEnvironment(dimensionId);
        };
    }
}
