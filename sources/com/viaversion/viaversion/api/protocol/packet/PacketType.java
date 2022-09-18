package com.viaversion.viaversion.api.protocol.packet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/packet/PacketType.class */
public interface PacketType {
    int getId();

    String getName();

    Direction direction();

    default State state() {
        return State.PLAY;
    }
}
