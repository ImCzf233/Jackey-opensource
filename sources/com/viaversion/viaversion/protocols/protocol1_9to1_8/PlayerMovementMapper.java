package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/PlayerMovementMapper.class */
public class PlayerMovementMapper implements PacketHandler {
    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
    public void handle(PacketWrapper wrapper) throws Exception {
        MovementTracker tracker = (MovementTracker) wrapper.user().get(MovementTracker.class);
        tracker.incrementIdlePacket();
        if (wrapper.mo79is(Type.BOOLEAN, 0)) {
            tracker.setGround(((Boolean) wrapper.get(Type.BOOLEAN, 0)).booleanValue());
        }
    }
}
