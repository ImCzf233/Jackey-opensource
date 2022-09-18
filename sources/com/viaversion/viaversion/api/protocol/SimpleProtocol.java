package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/SimpleProtocol.class */
public interface SimpleProtocol extends Protocol<DummyPacketTypes, DummyPacketTypes, DummyPacketTypes, DummyPacketTypes> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/SimpleProtocol$DummyPacketTypes.class */
    public enum DummyPacketTypes implements ClientboundPacketType, ServerboundPacketType {
        ;

        @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
        public int getId() {
            return ordinal();
        }

        @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
        public String getName() {
            return name();
        }

        @Override // com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType, com.viaversion.viaversion.api.protocol.packet.PacketType, com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType
        public Direction direction() {
            throw new UnsupportedOperationException();
        }
    }
}
