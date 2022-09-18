package com.viaversion.viaversion.debug;

import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.HashSet;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/debug/DebugHandlerImpl.class */
public final class DebugHandlerImpl implements DebugHandler {
    private final Set<String> packetTypesToLog = new HashSet();
    private boolean logPostPacketTransform = true;
    private boolean enabled;

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public boolean enabled() {
        return this.enabled;
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public void addPacketTypeNameToLog(String packetTypeName) {
        this.packetTypesToLog.add(packetTypeName);
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public boolean removePacketTypeNameToLog(String packetTypeName) {
        return this.packetTypesToLog.remove(packetTypeName);
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public void clearPacketTypesToLog() {
        this.packetTypesToLog.clear();
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public boolean logPostPacketTransform() {
        return this.logPostPacketTransform;
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public void setLogPostPacketTransform(boolean logPostPacketTransform) {
        this.logPostPacketTransform = logPostPacketTransform;
    }

    @Override // com.viaversion.viaversion.api.debug.DebugHandler
    public boolean shouldLog(PacketWrapper wrapper) {
        return this.packetTypesToLog.isEmpty() || (wrapper.getPacketType() != null && this.packetTypesToLog.contains(wrapper.getPacketType().getName()));
    }
}
