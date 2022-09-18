package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/packet/PacketWrapper.class */
public interface PacketWrapper {
    public static final int PASSTHROUGH_ID = 1000;

    <T> T get(Type<T> type, int i) throws Exception;

    /* renamed from: is */
    boolean mo79is(Type type, int i);

    boolean isReadable(Type type, int i);

    <T> void set(Type<T> type, int i, T t) throws Exception;

    <T> T read(Type<T> type) throws Exception;

    <T> void write(Type<T> type, T t);

    <T> T passthrough(Type<T> type) throws Exception;

    void passthroughAll() throws Exception;

    void writeToBuffer(ByteBuf byteBuf) throws Exception;

    void clearInputBuffer();

    void clearPacket();

    void send(Class<? extends Protocol> cls, boolean z) throws Exception;

    void scheduleSend(Class<? extends Protocol> cls, boolean z) throws Exception;

    ChannelFuture sendFuture(Class<? extends Protocol> cls) throws Exception;

    void sendRaw() throws Exception;

    void scheduleSendRaw() throws Exception;

    PacketWrapper create(int i);

    PacketWrapper create(int i, PacketHandler packetHandler) throws Exception;

    PacketWrapper apply(Direction direction, State state, int i, List<Protocol> list, boolean z) throws Exception;

    PacketWrapper apply(Direction direction, State state, int i, List<Protocol> list) throws Exception;

    void cancel();

    boolean isCancelled();

    UserConnection user();

    void resetReader();

    void sendToServerRaw() throws Exception;

    void scheduleSendToServerRaw() throws Exception;

    void sendToServer(Class<? extends Protocol> cls, boolean z) throws Exception;

    void scheduleSendToServer(Class<? extends Protocol> cls, boolean z) throws Exception;

    PacketType getPacketType();

    void setPacketType(PacketType packetType);

    int getId();

    @Deprecated
    void setId(int i);

    static PacketWrapper create(PacketType packetType, UserConnection connection) {
        return create(packetType, (ByteBuf) null, connection);
    }

    static PacketWrapper create(PacketType packetType, ByteBuf inputBuffer, UserConnection connection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(packetType, inputBuffer, connection);
    }

    @Deprecated
    static PacketWrapper create(int packetId, ByteBuf inputBuffer, UserConnection connection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(packetId, inputBuffer, connection);
    }

    default void send(Class<? extends Protocol> protocol) throws Exception {
        send(protocol, true);
    }

    default void scheduleSend(Class<? extends Protocol> protocol) throws Exception {
        scheduleSend(protocol, true);
    }

    @Deprecated
    default void send() throws Exception {
        sendRaw();
    }

    default PacketWrapper create(PacketType packetType) {
        return create(packetType.getId());
    }

    default PacketWrapper create(PacketType packetType, PacketHandler handler) throws Exception {
        return create(packetType.getId(), handler);
    }

    @Deprecated
    default void sendToServer() throws Exception {
        sendToServerRaw();
    }

    default void sendToServer(Class<? extends Protocol> protocol) throws Exception {
        sendToServer(protocol, true);
    }

    default void scheduleSendToServer(Class<? extends Protocol> protocol) throws Exception {
        scheduleSendToServer(protocol, true);
    }

    @Deprecated
    default void setId(PacketType packetType) {
        setPacketType(packetType);
    }
}
