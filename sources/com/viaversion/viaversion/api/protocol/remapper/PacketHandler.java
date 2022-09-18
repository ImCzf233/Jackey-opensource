package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/remapper/PacketHandler.class */
public interface PacketHandler {
    void handle(PacketWrapper packetWrapper) throws Exception;
}
