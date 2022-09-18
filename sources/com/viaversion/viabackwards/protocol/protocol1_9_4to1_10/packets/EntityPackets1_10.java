package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.List;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_9_4to1_10/packets/EntityPackets1_10.class */
public class EntityPackets1_10 extends LegacyEntityRewriter<Protocol1_9_4To1_10> {
    public EntityPackets1_10(Protocol1_9_4To1_10 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_9_4To1_10) this.protocol).registerClientbound((Protocol1_9_4To1_10) ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(EntityPackets1_10.this.getObjectTrackerHandler());
                handler(EntityPackets1_10.this.getObjectRewriter(id -> {
                    return Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null);
                }));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(((Byte) wrapper.get(Type.BYTE, 0)).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int objType = objectData & 4095;
                            int data = (objectData >> 12) & 15;
                            Block block = ((Protocol1_9_4To1_10) EntityPackets1_10.this.protocol).getItemRewriter().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, Integer.valueOf(block.getId() | (block.getData() << 12)));
                        }
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_10Types.EntityType.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_10Types.EntityType.WEATHER);
        ((Protocol1_9_4To1_10) this.protocol).registerClientbound((Protocol1_9_4To1_10) ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.UNSIGNED_BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_9.METADATA_LIST);
                handler(EntityPackets1_10.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityType type = EntityPackets1_10.this.tracker(wrapper.user()).entityType(entityId);
                        List<Metadata> metadata = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        EntityPackets1_10.this.handleMetadata(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), metadata, wrapper.user());
                        EntityData entityData = EntityPackets1_10.this.entityDataForType(type);
                        if (entityData != null) {
                            WrappedMetadata storage = new WrappedMetadata(metadata);
                            wrapper.set(Type.UNSIGNED_BYTE, 0, Short.valueOf((short) entityData.replacementId()));
                            if (entityData.hasBaseMeta()) {
                                entityData.defaultMeta().createMeta(storage);
                            }
                        }
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_10Types.EntityType.PAINTING);
        registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_10Types.EntityType.PLAYER);
        registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_9_4To1_10) this.protocol).registerClientbound((Protocol1_9_4To1_10) ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_9.METADATA_LIST);
                handler(EntityPackets1_10.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
            }
        });
        registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        mapEntityTypeWithData(Entity1_10Types.EntityType.POLAR_BEAR, Entity1_10Types.EntityType.SHEEP).plainName();
        filter().type(Entity1_10Types.EntityType.POLAR_BEAR).index(13).handler(event, meta -> {
            boolean b = ((Boolean) meta.getValue()).booleanValue();
            meta.setTypeAndValue(MetaType1_9.Byte, Byte.valueOf(b ? (byte) 14 : (byte) 0));
        });
        filter().type(Entity1_10Types.EntityType.ZOMBIE).index(13).handler(event2, meta2 -> {
            if (((Integer) meta2.getValue()).intValue() == 6) {
                meta2.setValue(0);
            }
        });
        filter().type(Entity1_10Types.EntityType.SKELETON).index(12).handler(event3, meta3 -> {
            if (((Integer) meta3.getValue()).intValue() == 2) {
                meta3.setValue(0);
            }
        });
        filter().removeIndex(5);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_10Types.getTypeFromId(typeId, false);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_10Types.getTypeFromId(typeId, true);
    }
}
