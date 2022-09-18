package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import java.util.LinkedList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(final Protocol1_14To1_13_2 protocol) {
        final MetadataRewriter1_14To1_13_2 metadataRewriter = (MetadataRewriter1_14To1_13_2) protocol.get(MetadataRewriter1_14To1_13_2.class);
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.BYTE, Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        Entity1_13Types.EntityType type1_13 = Entity1_13Types.getTypeFromId(((Integer) wrapper.get(Type.VAR_INT, 1)).intValue(), true);
                        int typeId = metadataRewriter.newEntityId(type1_13.getId());
                        EntityType type1_14 = Entity1_14Types.getTypeFromId(typeId);
                        if (type1_14 != null) {
                            int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            if (type1_14.m224is(Entity1_14Types.FALLING_BLOCK)) {
                                wrapper.set(Type.INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
                            } else if (type1_14.m224is(Entity1_14Types.MINECART)) {
                                switch (data) {
                                    case 1:
                                        typeId = Entity1_14Types.CHEST_MINECART.getId();
                                        break;
                                    case 2:
                                        typeId = Entity1_14Types.FURNACE_MINECART.getId();
                                        break;
                                    case 3:
                                        typeId = Entity1_14Types.TNT_MINECART.getId();
                                        break;
                                    case 4:
                                        typeId = Entity1_14Types.SPAWNER_MINECART.getId();
                                        break;
                                    case 5:
                                        typeId = Entity1_14Types.HOPPER_MINECART.getId();
                                        break;
                                    case 6:
                                        typeId = Entity1_14Types.COMMAND_BLOCK_MINECART.getId();
                                        break;
                                }
                            } else if ((type1_14.m224is(Entity1_14Types.ITEM) && data > 0) || type1_14.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                                if (type1_14.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                                    wrapper.set(Type.INT, 0, Integer.valueOf(data - 1));
                                }
                                PacketWrapper velocity = wrapper.create(69);
                                velocity.write(Type.VAR_INT, Integer.valueOf(entityId));
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 0));
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                                velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 2));
                                velocity.scheduleSend(Protocol1_14To1_13_2.class);
                            }
                            wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class).addEntity(entityId, type1_14);
                        }
                        wrapper.set(Type.VAR_INT, 1, Integer.valueOf(typeId));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.2
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
                map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SPAWN_PAINTING, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.POSITION, Type.POSITION1_14);
                map(Type.BYTE);
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, Entity1_14Types.PLAYER));
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.ENTITY_ANIMATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short animation = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        if (animation == 2) {
                            EntityTracker1_14 tracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                            int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                            tracker.setSleeping(entityId, false);
                            PacketWrapper metadataPacket = wrapper.create(ClientboundPackets1_14.ENTITY_METADATA);
                            metadataPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                            LinkedList linkedList = new LinkedList();
                            if (tracker.clientEntityId() != entityId) {
                                linkedList.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker))));
                            }
                            linkedList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, null));
                            metadataPacket.write(Types1_14.METADATA_LIST, linkedList);
                            metadataPacket.scheduleSend(Protocol1_14To1_13_2.class);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.USE_BED, (ClientboundPackets1_13) ClientboundPackets1_14.ENTITY_METADATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_14 tracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        tracker.setSleeping(entityId, true);
                        Position position = (Position) wrapper.read(Type.POSITION);
                        LinkedList linkedList = new LinkedList();
                        linkedList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, position));
                        if (tracker.clientEntityId() != entityId) {
                            linkedList.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker))));
                        }
                        wrapper.write(Types1_14.METADATA_LIST, linkedList);
                    }
                });
            }
        });
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
    }
}
