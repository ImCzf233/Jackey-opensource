package com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_12_2to1_12_1/Protocol1_12_2To1_12_1.class */
public class Protocol1_12_2To1_12_1 extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1> {
    public Protocol1_12_2To1_12_1() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_12_2To1_12_1) ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1.Protocol1_12_2To1_12_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.LONG);
            }
        });
        registerServerbound((Protocol1_12_2To1_12_1) ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1.Protocol1_12_2To1_12_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.LONG, Type.VAR_INT);
            }
        });
    }
}
