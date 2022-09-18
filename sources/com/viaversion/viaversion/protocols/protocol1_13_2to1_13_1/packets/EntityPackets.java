package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_2to1_13_1/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol protocol) {
        final PacketHandler metaTypeHandler = wrapper -> {
            for (Metadata metadata : (List) wrapper.get(Types1_13_2.METADATA_LIST, 0)) {
                metadata.setMetaType(Types1_13_2.META_TYPES.byId(metadata.metaType().typeId()));
            }
        };
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.EntityPackets.1
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
                map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                handler(metaTypeHandler);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                handler(metaTypeHandler);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.ENTITY_METADATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
                handler(metaTypeHandler);
            }
        });
    }
}
