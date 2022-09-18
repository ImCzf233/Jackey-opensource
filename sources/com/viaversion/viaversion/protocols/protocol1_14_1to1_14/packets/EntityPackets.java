package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.MetadataRewriter1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14_1to1_14/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol1_14_1To1_14 protocol) {
        final MetadataRewriter1_14_1To1_14 metadataRewriter = (MetadataRewriter1_14_1To1_14) protocol.get(MetadataRewriter1_14_1To1_14.class);
        protocol.registerClientbound((Protocol1_14_1To1_14) ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets.EntityPackets.1
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
                map(Types1_14.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
            }
        });
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        protocol.registerClientbound((Protocol1_14_1To1_14) ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_14.METADATA_LIST);
                handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, Entity1_14Types.PLAYER));
            }
        });
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
    }
}
