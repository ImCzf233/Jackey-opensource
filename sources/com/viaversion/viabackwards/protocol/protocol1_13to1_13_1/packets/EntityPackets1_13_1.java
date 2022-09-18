package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13to1_13_1/packets/EntityPackets1_13_1.class */
public class EntityPackets1_13_1 extends LegacyEntityRewriter<Protocol1_13To1_13_1> {
    public EntityPackets1_13_1(Protocol1_13To1_13_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_13To1_13_1) this.protocol).registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1.1
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
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        byte type = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
                        if (entType == null) {
                            ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + ((int) type));
                            return;
                        }
                        if (entType.m224is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            wrapper.set(Type.INT, 0, Integer.valueOf(((Protocol1_13To1_13_1) EntityPackets1_13_1.this.protocol).getMappingData().getNewBlockStateId(data)));
                        }
                        EntityPackets1_13_1.this.tracker(wrapper.user()).addEntity(entityId, entType);
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_13To1_13_1) this.protocol).registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1.2
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
                map(Types1_13.METADATA_LIST);
                handler(EntityPackets1_13_1.this.getTrackerHandler());
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadata = (List) wrapper.get(Types1_13.METADATA_LIST, 0);
                        EntityPackets1_13_1.this.handleMetadata(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), metadata, wrapper.user());
                    }
                });
            }
        });
        ((Protocol1_13To1_13_1) this.protocol).registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_13.METADATA_LIST);
                handler(EntityPackets1_13_1.this.getTrackerAndMetaHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        registerTracker(ClientboundPackets1_13.SPAWN_PAINTING, Entity1_13Types.EntityType.PAINTING);
        registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        registerRespawn(ClientboundPackets1_13.RESPAWN);
        registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            if (meta.metaType() == Types1_13.META_TYPES.itemType) {
                ((Protocol1_13To1_13_1) this.protocol).getItemRewriter().handleItemToClient((Item) meta.getValue());
            } else if (meta.metaType() == Types1_13.META_TYPES.blockStateType) {
                int data = ((Integer) meta.getValue()).intValue();
                meta.setValue(Integer.valueOf(((Protocol1_13To1_13_1) this.protocol).getMappingData().getNewBlockStateId(data)));
            } else if (meta.metaType() == Types1_13.META_TYPES.particleType) {
                rewriteParticle((Particle) meta.getValue());
            }
        });
        filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_ARROW).cancel(7);
        filter().type(Entity1_13Types.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
        filter().type(Entity1_13Types.EntityType.TRIDENT).index(8).toIndex(7);
        filter().filterFamily(Entity1_13Types.EntityType.MINECART_ABSTRACT).index(9).handler(event2, meta2 -> {
            int data = ((Integer) meta2.getValue()).intValue();
            meta2.setValue(Integer.valueOf(((Protocol1_13To1_13_1) this.protocol).getMappingData().getNewBlockStateId(data)));
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, false);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, true);
    }
}
