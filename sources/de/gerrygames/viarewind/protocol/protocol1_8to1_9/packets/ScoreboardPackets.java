package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/ScoreboardPackets.class */
public class ScoreboardPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.TEAMS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.ScoreboardPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    byte mode = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    if (mode == 0 || mode == 2) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.BYTE);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.read(Type.STRING);
                        packetWrapper.passthrough(Type.BYTE);
                    }
                    if (mode == 0 || mode == 3 || mode == 4) {
                        packetWrapper.passthrough(Type.STRING_ARRAY);
                    }
                });
            }
        });
    }
}
