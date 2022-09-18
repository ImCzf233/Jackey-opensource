package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/base/ClientboundStatusPackets.class */
public enum ClientboundStatusPackets implements ClientboundPacketType {
    STATUS_RESPONSE,
    PONG_RESPONSE;

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public final int getId() {
        return ordinal();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public final String getName() {
        return name();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public final State state() {
        return State.STATUS;
    }
}
