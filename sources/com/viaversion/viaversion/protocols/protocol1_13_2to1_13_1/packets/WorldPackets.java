package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_2to1_13_1/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.WorldPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        if (id == 27) {
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                        }
                    }
                });
            }
        });
    }
}
