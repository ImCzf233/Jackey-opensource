package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/EntityRewriter.class */
public abstract class EntityRewriter<T extends BackwardsProtocol> extends EntityRewriterBase<T> {
    public EntityRewriter(T protocol) {
        this(protocol, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
    }

    protected EntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
        super(protocol, displayType, 2, displayVisibilityType, 3);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter
    public void registerTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.EntityRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(EntityRewriter.this.getSpawnTrackerWithDataHandler(fallingBlockType));
            }
        });
    }

    public PacketHandler getSpawnTrackerWithDataHandler(EntityType fallingBlockType) {
        return wrapper -> {
            EntityType entityType = setOldEntityId(fallingBlockType);
            if (entityType == fallingBlockType) {
                int blockState = ((Integer) fallingBlockType.get(Type.INT, 0)).intValue();
                fallingBlockType.set(Type.INT, 0, Integer.valueOf(((BackwardsProtocol) this.protocol).getMappingData().getNewBlockStateId(blockState)));
            }
        };
    }

    public void registerSpawnTracker(ClientboundPacketType packetType) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.EntityRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    EntityRewriter.this.setOldEntityId(wrapper);
                });
            }
        });
    }

    public EntityType setOldEntityId(PacketWrapper wrapper) throws Exception {
        int typeId = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
        EntityType entityType = typeFromId(typeId);
        tracker(wrapper.user()).addEntity(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), entityType);
        int mappedTypeId = newEntityId(entityType.getId());
        if (typeId != mappedTypeId) {
            wrapper.set(Type.VAR_INT, 1, Integer.valueOf(mappedTypeId));
        }
        return entityType;
    }
}
