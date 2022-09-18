package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/providers/BungeeMovementTransmitter.class */
public class BungeeMovementTransmitter extends MovementTransmitterProvider {
    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public Object getFlyingPacket() {
        return null;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public Object getGroundPacket() {
        return null;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public void sendPlayer(UserConnection userConnection) {
        if (userConnection.getProtocolInfo().getState() == State.PLAY) {
            PacketWrapper wrapper = PacketWrapper.create(ServerboundPackets1_8.PLAYER_MOVEMENT, (ByteBuf) null, userConnection);
            MovementTracker tracker = (MovementTracker) userConnection.get(MovementTracker.class);
            wrapper.write(Type.BOOLEAN, Boolean.valueOf(tracker.isGround()));
            try {
                wrapper.scheduleSendToServer(Protocol1_9To1_8.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tracker.incrementIdlePacket();
        }
    }
}
