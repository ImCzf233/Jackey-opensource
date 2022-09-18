package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol1_15To1_14_4 protocol) {
        final MetadataRewriter1_15To1_14_4 metadataRewriter = (MetadataRewriter1_15To1_14_4) protocol.get(MetadataRewriter1_15To1_14_4.class);
        metadataRewriter.registerTrackerWithData(ClientboundPackets1_14.SPAWN_ENTITY, Entity1_15Types.FALLING_BLOCK);
        protocol.registerClientbound((Protocol1_15To1_14_4) ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets.1
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
                handler(metadataRewriter.trackerHandler());
                MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4 = metadataRewriter;
                handler(wrapper -> {
                    EntityPackets.sendMetadataPacket(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), metadataRewriter1_15To1_14_4);
                });
            }
        });
        protocol.registerClientbound((Protocol1_15To1_14_4) ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                MetadataRewriter1_15To1_14_4 metadataRewriter1_15To1_14_4 = metadataRewriter;
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    wrapper.user().getEntityTracker(Protocol1_15To1_14_4.class).addEntity(entityId, Entity1_15Types.PLAYER);
                    EntityPackets.sendMetadataPacket(wrapper, entityId, metadataRewriter1_15To1_14_4);
                });
            }
        });
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
    }

    public static void sendMetadataPacket(PacketWrapper wrapper, int entityId, EntityRewriter rewriter) throws Exception {
        List<Metadata> metadata = (List) wrapper.read(Types1_14.METADATA_LIST);
        if (metadata.isEmpty()) {
            return;
        }
        wrapper.send(Protocol1_15To1_14_4.class);
        wrapper.cancel();
        rewriter.handleMetadata(entityId, metadata, wrapper.user());
        PacketWrapper metadataPacket = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, wrapper.user());
        metadataPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
        metadataPacket.write(Types1_14.METADATA_LIST, metadata);
        metadataPacket.send(Protocol1_15To1_14_4.class);
    }

    public static int getNewEntityId(int oldId) {
        return oldId >= 4 ? oldId + 1 : oldId;
    }
}
