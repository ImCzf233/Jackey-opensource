package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/remapper/ValueReader.class */
public interface ValueReader<T> {
    T read(PacketWrapper packetWrapper) throws Exception;
}
