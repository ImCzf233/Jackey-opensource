package com.viaversion.viaversion.api.protocol.packet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/packet/ServerboundPacketType.class */
public interface ServerboundPacketType extends PacketType {
    @Override // 
    default Direction direction() {
        return Direction.SERVERBOUND;
    }
}
