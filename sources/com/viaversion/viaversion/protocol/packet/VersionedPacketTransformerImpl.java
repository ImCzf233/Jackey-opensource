package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/packet/VersionedPacketTransformerImpl.class */
public class VersionedPacketTransformerImpl<C extends ClientboundPacketType, S extends ServerboundPacketType> implements VersionedPacketTransformer<C, S> {
    private final int inputProtocolVersion;
    private final Class<C> clientboundPacketsClass;
    private final Class<S> serverboundPacketsClass;

    public VersionedPacketTransformerImpl(ProtocolVersion inputVersion, Class<C> clientboundPacketsClass, Class<S> serverboundPacketsClass) {
        Preconditions.checkNotNull(inputVersion);
        Preconditions.checkArgument((clientboundPacketsClass == null && serverboundPacketsClass == null) ? false : true, "Either the clientbound or serverbound packets class has to be non-null");
        this.inputProtocolVersion = inputVersion.getVersion();
        this.clientboundPacketsClass = clientboundPacketsClass;
        this.serverboundPacketsClass = serverboundPacketsClass;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean send(PacketWrapper packet) throws Exception {
        validatePacket(packet);
        return transformAndSendPacket(packet, true);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean send(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return createAndSend(connection, packetType, packetWriter);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean send(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return createAndSend(connection, packetType, packetWriter);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean scheduleSend(PacketWrapper packet) throws Exception {
        validatePacket(packet);
        return transformAndSendPacket(packet, false);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean scheduleSend(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return scheduleCreateAndSend(connection, packetType, packetWriter);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public boolean scheduleSend(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return scheduleCreateAndSend(connection, packetType, packetWriter);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public PacketWrapper transform(PacketWrapper packet) throws Exception {
        validatePacket(packet);
        transformPacket(packet);
        if (packet.isCancelled()) {
            return null;
        }
        return packet;
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public PacketWrapper transform(UserConnection connection, C packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return createAndTransform(connection, packetType, packetWriter);
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer
    public PacketWrapper transform(UserConnection connection, S packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        return createAndTransform(connection, packetType, packetWriter);
    }

    private void validatePacket(PacketWrapper packet) {
        if (packet.user() == null) {
            throw new IllegalArgumentException("PacketWrapper does not have a targetted UserConnection");
        }
        if (packet.getPacketType() == null) {
            throw new IllegalArgumentException("PacketWrapper does not have a valid packet type");
        }
        Class<? extends PacketType> expectedPacketClass = packet.getPacketType().direction() == Direction.CLIENTBOUND ? (Class<C>) this.clientboundPacketsClass : (Class<S>) this.serverboundPacketsClass;
        if (packet.getPacketType().getClass() != expectedPacketClass) {
            throw new IllegalArgumentException("PacketWrapper packet type is of the wrong packet class");
        }
    }

    private boolean transformAndSendPacket(PacketWrapper packet, boolean currentThread) throws Exception {
        transformPacket(packet);
        if (packet.isCancelled()) {
            return false;
        }
        if (currentThread) {
            if (packet.getPacketType().direction() == Direction.CLIENTBOUND) {
                packet.sendRaw();
                return true;
            }
            packet.sendToServerRaw();
            return true;
        } else if (packet.getPacketType().direction() == Direction.CLIENTBOUND) {
            packet.scheduleSendRaw();
            return true;
        } else {
            packet.scheduleSendToServerRaw();
            return true;
        }
    }

    private void transformPacket(PacketWrapper packet) throws Exception {
        PacketType packetType = packet.getPacketType();
        UserConnection connection = packet.user();
        boolean clientbound = packetType.direction() == Direction.CLIENTBOUND;
        int serverProtocolVersion = clientbound ? this.inputProtocolVersion : connection.getProtocolInfo().getServerProtocolVersion();
        int clientProtocolVersion = clientbound ? connection.getProtocolInfo().getProtocolVersion() : this.inputProtocolVersion;
        List<ProtocolPathEntry> path = Via.getManager().getProtocolManager().getProtocolPath(clientProtocolVersion, serverProtocolVersion);
        List<Protocol> protocolList = null;
        if (path != null) {
            protocolList = new ArrayList<>(path.size());
            for (ProtocolPathEntry entry : path) {
                protocolList.add(entry.protocol());
            }
        } else if (serverProtocolVersion != clientProtocolVersion) {
            throw new RuntimeException("No protocol path between client version " + clientProtocolVersion + " and server version " + serverProtocolVersion);
        }
        if (protocolList != null) {
            packet.resetReader();
            try {
                packet.apply(packetType.direction(), State.PLAY, 0, protocolList, clientbound);
            } catch (Exception e) {
                throw new Exception("Exception trying to transform packet between client version " + clientProtocolVersion + " and server version " + serverProtocolVersion + ". Are you sure you used the correct input version and packet write types?", e);
            }
        }
    }

    private boolean createAndSend(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        PacketWrapper packet = PacketWrapper.create(packetType, connection);
        packetWriter.accept(packet);
        return send(packet);
    }

    private boolean scheduleCreateAndSend(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        PacketWrapper packet = PacketWrapper.create(packetType, connection);
        packetWriter.accept(packet);
        return scheduleSend(packet);
    }

    private PacketWrapper createAndTransform(UserConnection connection, PacketType packetType, Consumer<PacketWrapper> packetWriter) throws Exception {
        PacketWrapper packet = PacketWrapper.create(packetType, connection);
        packetWriter.accept(packet);
        return transform(packet);
    }
}
