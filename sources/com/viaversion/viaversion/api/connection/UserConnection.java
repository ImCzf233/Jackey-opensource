package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/connection/UserConnection.class */
public interface UserConnection {
    <T extends StorableObject> T get(Class<T> cls);

    boolean has(Class<? extends StorableObject> cls);

    void put(StorableObject storableObject);

    Collection<EntityTracker> getEntityTrackers();

    <T extends EntityTracker> T getEntityTracker(Class<? extends Protocol> cls);

    void addEntityTracker(Class<? extends Protocol> cls, EntityTracker entityTracker);

    void clearStoredObjects();

    void sendRawPacket(ByteBuf byteBuf);

    void scheduleSendRawPacket(ByteBuf byteBuf);

    ChannelFuture sendRawPacketFuture(ByteBuf byteBuf);

    PacketTracker getPacketTracker();

    void disconnect(String str);

    void sendRawPacketToServer(ByteBuf byteBuf);

    void scheduleSendRawPacketToServer(ByteBuf byteBuf);

    boolean checkServerboundPacket();

    boolean checkClientboundPacket();

    boolean shouldTransformPacket();

    void transformClientbound(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception;

    void transformServerbound(ByteBuf byteBuf, Function<Throwable, Exception> function) throws Exception;

    long getId();

    Channel getChannel();

    ProtocolInfo getProtocolInfo();

    Map<Class<?>, StorableObject> getStoredObjects();

    boolean isActive();

    void setActive(boolean z);

    boolean isPendingDisconnect();

    void setPendingDisconnect(boolean z);

    boolean isClientSide();

    boolean shouldApplyBlockProtocol();

    boolean isPacketLimiterEnabled();

    void setPacketLimiterEnabled(boolean z);

    UUID generatePassthroughToken();

    default boolean checkIncomingPacket() {
        return isClientSide() ? checkClientboundPacket() : checkServerboundPacket();
    }

    default boolean checkOutgoingPacket() {
        return isClientSide() ? checkServerboundPacket() : checkClientboundPacket();
    }

    default void transformOutgoing(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (isClientSide()) {
            transformServerbound(buf, cancelSupplier);
        } else {
            transformClientbound(buf, cancelSupplier);
        }
    }

    default void transformIncoming(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
        if (isClientSide()) {
            transformClientbound(buf, cancelSupplier);
        } else {
            transformServerbound(buf, cancelSupplier);
        }
    }
}
