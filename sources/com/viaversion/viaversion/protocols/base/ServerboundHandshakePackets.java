package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/base/ServerboundHandshakePackets.class */
public enum ServerboundHandshakePackets implements ServerboundPacketType {
    CLIENT_INTENTION;

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
        return State.HANDSHAKE;
    }
}
