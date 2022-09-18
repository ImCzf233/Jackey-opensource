package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/packet/VersionedPacketTransformer.class */
public interface VersionedPacketTransformer<C extends ClientboundPacketType, S extends ServerboundPacketType> {
    boolean send(PacketWrapper packetWrapper) throws Exception;

    boolean send(UserConnection userConnection, C c, Consumer<PacketWrapper> consumer) throws Exception;

    boolean send(UserConnection userConnection, S s, Consumer<PacketWrapper> consumer) throws Exception;

    boolean scheduleSend(PacketWrapper packetWrapper) throws Exception;

    boolean scheduleSend(UserConnection userConnection, C c, Consumer<PacketWrapper> consumer) throws Exception;

    boolean scheduleSend(UserConnection userConnection, S s, Consumer<PacketWrapper> consumer) throws Exception;

    PacketWrapper transform(PacketWrapper packetWrapper) throws Exception;

    PacketWrapper transform(UserConnection userConnection, C c, Consumer<PacketWrapper> consumer) throws Exception;

    PacketWrapper transform(UserConnection userConnection, S s, Consumer<PacketWrapper> consumer) throws Exception;
}
