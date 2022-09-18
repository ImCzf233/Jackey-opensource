package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/remapper/ValueWriter.class */
public interface ValueWriter<T> {
    void write(PacketWrapper packetWrapper, T t) throws Exception;
}
