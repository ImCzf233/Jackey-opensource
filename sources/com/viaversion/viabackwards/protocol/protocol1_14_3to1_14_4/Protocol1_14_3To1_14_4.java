package com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_3to1_14_4/Protocol1_14_3To1_14_4.class */
public class Protocol1_14_3To1_14_4 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_3To1_14_4() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_14_3To1_14_4) ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING, ClientboundPackets1_14.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4.Protocol1_14_3To1_14_4.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int status = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    boolean allGood = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    if (allGood && status == 0) {
                        wrapper.cancel();
                    }
                });
            }
        });
        registerClientbound((Protocol1_14_3To1_14_4) ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4.Protocol1_14_3To1_14_4.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4.Protocol1_14_3To1_14_4.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.VAR_INT);
                        int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; i++) {
                            wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            if (((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.read(Type.INT);
                        }
                    }
                });
            }
        });
    }
}
