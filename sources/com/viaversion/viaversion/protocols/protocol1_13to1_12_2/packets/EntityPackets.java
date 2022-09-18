package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol1_13To1_12_2 protocol) {
        final MetadataRewriter1_13To1_12_2 metadataRewriter = (MetadataRewriter1_13To1_12_2) protocol.get(MetadataRewriter1_13To1_12_2.class);
        protocol.registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets.1
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
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        byte type = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
                        if (entType == null) {
                            return;
                        }
                        wrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(entityId, entType);
                        if (entType.m224is(Entity1_13Types.EntityType.FALLING_BLOCK)) {
                            int oldId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int combined = ((oldId & 4095) << 4) | ((oldId >> 12) & 15);
                            wrapper.set(Type.INT, 0, Integer.valueOf(WorldPackets.toNewId(combined)));
                        }
                        if (entType.m224is(Entity1_13Types.EntityType.ITEM_FRAME)) {
                            int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            switch (data) {
                                case 0:
                                    data = 3;
                                    break;
                                case 1:
                                    data = 4;
                                    break;
                                case 3:
                                    data = 5;
                                    break;
                            }
                            wrapper.set(Type.INT, 0, Integer.valueOf(data));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets.2
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
                map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
            }
        });
        protocol.registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_12_1.DESTROY_ENTITIES);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_12_1.ENTITY_METADATA, Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
    }
}
