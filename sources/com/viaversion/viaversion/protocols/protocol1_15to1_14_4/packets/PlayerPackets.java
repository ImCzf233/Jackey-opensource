package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/PlayerPackets.class */
public class PlayerPackets {
    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_14.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.PlayerPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    wrapper.write(Type.LONG, 0L);
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_14.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.PlayerPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_15To1_14_4.class);
                    int entityId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    tracker.addEntity(entityId, Entity1_15Types.PLAYER);
                });
                handler(wrapper2 -> {
                    wrapper2.write(Type.LONG, 0L);
                });
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                handler(wrapper3 -> {
                    wrapper3.write(Type.BOOLEAN, Boolean.valueOf(!Via.getConfig().is1_15InstantRespawn()));
                });
            }
        });
    }
}
