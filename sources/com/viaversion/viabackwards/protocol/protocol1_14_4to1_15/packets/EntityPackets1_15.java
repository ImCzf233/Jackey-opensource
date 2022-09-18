package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import java.util.ArrayList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_4to1_15/packets/EntityPackets1_15.class */
public class EntityPackets1_15 extends EntityRewriter<Protocol1_14_4To1_15> {
    public EntityPackets1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.UPDATE_HEALTH, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    float health = ((Float) wrapper.passthrough(Type.FLOAT)).floatValue();
                    if (health <= 0.0f && ((ImmediateRespawn) wrapper.user().get(ImmediateRespawn.class)).isImmediateRespawn()) {
                        PacketWrapper statusPacket = wrapper.create(ServerboundPackets1_14.CLIENT_STATUS);
                        statusPacket.write(Type.VAR_INT, 0);
                        statusPacket.sendToServer(Protocol1_14_4To1_15.class);
                    }
                });
            }
        });
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.GAME_EVENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLOAT);
                handler(wrapper -> {
                    if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
                        ((ImmediateRespawn) wrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn(((Float) wrapper.get(Type.FLOAT, 0)).floatValue() == 1.0f);
                    }
                });
            }
        });
        registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.3
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
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(wrapper -> {
                    wrapper.write(Types1_14.METADATA_LIST, new ArrayList());
                });
                handler(wrapper2 -> {
                    int type = ((Integer) wrapper2.get(Type.VAR_INT, 1)).intValue();
                    EntityType entityType = Entity1_15Types.getTypeFromId(type);
                    EntityPackets1_15.this.tracker(wrapper2.user()).addEntity(((Integer) wrapper2.get(Type.VAR_INT, 0)).intValue(), entityType);
                    wrapper2.set(Type.VAR_INT, 1, Integer.valueOf(EntityTypeMapping.getOldEntityId(type)));
                });
            }
        });
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.LONG, Type.NOTHING);
            }
        });
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                map(Type.LONG, Type.NOTHING);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                handler(EntityPackets1_15.this.getTrackerHandler(Entity1_15Types.PLAYER, Type.INT));
                handler(wrapper -> {
                    boolean immediateRespawn = !((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    ((ImmediateRespawn) wrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn(immediateRespawn);
                });
            }
        });
        registerTracker(ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, Entity1_15Types.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, Entity1_15Types.LIGHTNING_BOLT);
        registerTracker(ClientboundPackets1_15.SPAWN_PAINTING, Entity1_15Types.PAINTING);
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Types1_14.METADATA_LIST, new ArrayList());
                });
                handler(EntityPackets1_15.this.getTrackerHandler(Entity1_15Types.PLAYER, Type.VAR_INT));
            }
        });
        registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT);
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityType entityType = EntityPackets1_15.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType != Entity1_15Types.BEE) {
                        return;
                    }
                    int size = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    int newSize = size;
                    for (int i = 0; i < size; i++) {
                        String key = (String) wrapper.read(Type.STRING);
                        if (key.equals("generic.flyingSpeed")) {
                            newSize--;
                            wrapper.read(Type.DOUBLE);
                            int modSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            for (int j = 0; j < modSize; j++) {
                                wrapper.read(Type.UUID);
                                wrapper.read(Type.DOUBLE);
                                wrapper.read(Type.BYTE);
                            }
                        } else {
                            wrapper.write(Type.STRING, key);
                            wrapper.passthrough(Type.DOUBLE);
                            int modSize2 = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            for (int j2 = 0; j2 < modSize2; j2++) {
                                wrapper.passthrough(Type.UUID);
                                wrapper.passthrough(Type.DOUBLE);
                                wrapper.passthrough(Type.BYTE);
                            }
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Type.INT, 0, Integer.valueOf(newSize));
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        registerMetaTypeHandler(Types1_14.META_TYPES.itemType, Types1_14.META_TYPES.blockStateType, Types1_14.META_TYPES.particleType, null);
        filter().filterFamily(Entity1_15Types.LIVINGENTITY).removeIndex(12);
        filter().type(Entity1_15Types.BEE).cancel(15);
        filter().type(Entity1_15Types.BEE).cancel(16);
        mapEntityTypeWithData(Entity1_15Types.BEE, Entity1_15Types.PUFFERFISH).jsonName().spawnMetadata(storage -> {
            storage.add(new Metadata(14, Types1_14.META_TYPES.booleanType, false));
            storage.add(new Metadata(15, Types1_14.META_TYPES.varIntType, 2));
        });
        filter().type(Entity1_15Types.ENDERMAN).cancel(16);
        filter().type(Entity1_15Types.TRIDENT).cancel(10);
        filter().type(Entity1_15Types.WOLF).addIndex(17);
        filter().type(Entity1_15Types.WOLF).index(8).handler(event, meta -> {
            event.createExtraMeta(new Metadata(17, Types1_14.META_TYPES.floatType, event.meta().value()));
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_15Types.getTypeFromId(typeId);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter, com.viaversion.viaversion.api.rewriter.EntityRewriter
    public int newEntityId(int newId) {
        return EntityTypeMapping.getOldEntityId(newId);
    }
}
