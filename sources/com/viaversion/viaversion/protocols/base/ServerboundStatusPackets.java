package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/base/ServerboundStatusPackets.class */
public enum ServerboundStatusPackets implements ServerboundPacketType {
    STATUS_REQUEST,
    PING_REQUEST;

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
