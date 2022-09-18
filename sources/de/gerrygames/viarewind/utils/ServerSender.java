package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/ServerSender.class */
public interface ServerSender {
    void sendToServer(PacketWrapper packetWrapper, Class<? extends Protocol> cls, boolean z, boolean z2) throws Exception;
}
