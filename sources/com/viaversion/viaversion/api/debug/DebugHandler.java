package com.viaversion.viaversion.api.debug;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/debug/DebugHandler.class */
public interface DebugHandler {
    boolean enabled();

    void setEnabled(boolean z);

    void addPacketTypeNameToLog(String str);

    boolean removePacketTypeNameToLog(String str);

    void clearPacketTypesToLog();

    boolean logPostPacketTransform();

    void setLogPostPacketTransform(boolean z);

    boolean shouldLog(PacketWrapper packetWrapper);
}
