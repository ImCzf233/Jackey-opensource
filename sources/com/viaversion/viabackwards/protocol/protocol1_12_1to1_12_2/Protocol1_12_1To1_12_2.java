package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_1to1_12_2/Protocol1_12_1To1_12_2.class */
public class Protocol1_12_1To1_12_2 extends BackwardsProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1> {
    public Protocol1_12_1To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_12_1To1_12_2) ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.Protocol1_12_1To1_12_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.Protocol1_12_1To1_12_2.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Long keepAlive = (Long) packetWrapper.read(Type.LONG);
                        ((KeepAliveTracker) packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(keepAlive.longValue());
                        packetWrapper.write(Type.VAR_INT, Integer.valueOf(keepAlive.hashCode()));
                    }
                });
            }
        });
        registerServerbound((Protocol1_12_1To1_12_2) ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.Protocol1_12_1To1_12_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.Protocol1_12_1To1_12_2.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int keepAlive = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                        long realKeepAlive = ((KeepAliveTracker) packetWrapper.user().get(KeepAliveTracker.class)).getKeepAlive();
                        if (keepAlive != Long.hashCode(realKeepAlive)) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.LONG, Long.valueOf(realKeepAlive));
                        ((KeepAliveTracker) packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(2147483647L);
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.put(new KeepAliveTracker());
    }
}
