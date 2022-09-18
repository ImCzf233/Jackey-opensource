package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_17to1_16_4/packets/EntityPackets.class */
public final class EntityPackets extends EntityRewriter<Protocol1_17To1_16_4> {
    public EntityPackets(Protocol1_17To1_16_4 protocol) {
        super(protocol);
        mapTypes(Entity1_16_2Types.values(), Entity1_17Types.class);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        registerTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17To1_16_4) this.protocol).registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.DESTROY_ENTITIES, (ClientboundPackets1_16_2) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int[] entityIds = (int[]) wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    wrapper.cancel();
                    EntityTracker entityTracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
                    for (int entityId : entityIds) {
                        entityTracker.removeEntity(entityId);
                        PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                        newPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                        newPacket.send(Protocol1_17To1_16_4.class);
                    }
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.write(Type.VAR_INT, wrapper.read(Type.INT));
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.PLAYER_POSITION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BYTE);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.write(Type.BOOLEAN, false);
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.COMBAT_EVENT, (ClientboundPackets1_16_2) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    ClientboundPacketType packetType;
                    int type = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    switch (type) {
                        case 0:
                            packetType = ClientboundPackets1_17.COMBAT_ENTER;
                            break;
                        case 1:
                            packetType = ClientboundPackets1_17.COMBAT_END;
                            break;
                        case 2:
                            packetType = ClientboundPackets1_17.COMBAT_KILL;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid combat type received: " + type);
                    }
                    wrapper.setId(packetType.getId());
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).cancelClientbound(ClientboundPackets1_16_2.ENTITY_MOVEMENT);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            int pose;
            meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
            if (meta.metaType() == Types1_17.META_TYPES.poseType && (pose = ((Integer) meta.value()).intValue()) > 5) {
                meta.setValue(Integer.valueOf(pose + 1));
            }
        });
        registerMetaTypeHandler(Types1_17.META_TYPES.itemType, Types1_17.META_TYPES.blockStateType, Types1_17.META_TYPES.particleType);
        filter().filterFamily(Entity1_17Types.ENTITY).addIndex(7);
        filter().filterFamily(Entity1_17Types.MINECART_ABSTRACT).index(11).handler(event2, meta2 -> {
            int data = ((Integer) meta2.getValue()).intValue();
            meta2.setValue(Integer.valueOf(((Protocol1_17To1_16_4) this.protocol).getMappingData().getNewBlockStateId(data)));
        });
        filter().type(Entity1_17Types.SHULKER).removeIndex(17);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_17Types.getTypeFromId(type);
    }
}
