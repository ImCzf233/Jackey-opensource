package com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_4to1_16_3/Protocol1_16_4To1_16_3.class */
public class Protocol1_16_4To1_16_3 extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2> {
    public Protocol1_16_4To1_16_3() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerServerbound((Protocol1_16_4To1_16_3) ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3.Protocol1_16_4To1_16_3.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLAT_VAR_INT_ITEM);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    int slot = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    wrapper.write(Type.VAR_INT, Integer.valueOf(slot == 40 ? 1 : 0));
                });
            }
        });
    }
}
