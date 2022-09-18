package com.viaversion.viaversion.protocols.protocol1_9_1to1_9;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9_1to1_9/Protocol1_9_1To1_9.class */
public class Protocol1_9_1To1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9> {
    public Protocol1_9_1To1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_9_1To1_9) ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE, Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.BOOLEAN);
            }
        });
        registerClientbound((Protocol1_9_1To1_9) ClientboundPackets1_9.SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int sound = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (sound >= 415) {
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(sound + 1));
                        }
                    }
                });
            }
        });
    }
}
