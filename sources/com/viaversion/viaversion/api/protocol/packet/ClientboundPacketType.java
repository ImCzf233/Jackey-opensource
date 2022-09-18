package com.viaversion.viaversion.api.protocol.packet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/packet/ClientboundPacketType.class */
public interface ClientboundPacketType extends PacketType {
    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType, com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType
    default Direction direction() {
        return Direction.CLIENTBOUND;
    }
}
