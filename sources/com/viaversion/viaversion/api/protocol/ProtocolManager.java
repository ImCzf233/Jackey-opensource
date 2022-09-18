package com.viaversion.viaversion.api.protocol;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/ProtocolManager.class */
public interface ProtocolManager {
    ServerProtocolVersion getServerProtocolVersion();

    <T extends Protocol> T getProtocol(Class<T> cls);

    Protocol getProtocol(int i, int i2);

    Protocol getBaseProtocol();

    Protocol getBaseProtocol(int i);

    void registerProtocol(Protocol protocol, ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2);

    void registerProtocol(Protocol protocol, List<Integer> list, int i);

    void registerBaseProtocol(Protocol protocol, Range<Integer> range);

    List<ProtocolPathEntry> getProtocolPath(int i, int i2);

    <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(ProtocolVersion protocolVersion, Class<C> cls, Class<S> cls2);

    boolean onlyCheckLoweringPathEntries();

    void setOnlyCheckLoweringPathEntries(boolean z);

    int getMaxProtocolPathSize();

    void setMaxProtocolPathSize(int i);

    SortedSet<Integer> getSupportedVersions();

    boolean isWorkingPipe();

    void completeMappingDataLoading(Class<? extends Protocol> cls) throws Exception;

    boolean checkForMappingCompletion();

    void addMappingLoaderFuture(Class<? extends Protocol> cls, Runnable runnable);

    void addMappingLoaderFuture(Class<? extends Protocol> cls, Class<? extends Protocol> cls2, Runnable runnable);

    CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> cls);

    PacketWrapper createPacketWrapper(PacketType packetType, ByteBuf byteBuf, UserConnection userConnection);

    @Deprecated
    PacketWrapper createPacketWrapper(int i, ByteBuf byteBuf, UserConnection userConnection);

    default Protocol getProtocol(ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
        return getProtocol(clientVersion.getVersion(), serverVersion.getVersion());
    }

    default boolean isBaseProtocol(Protocol protocol) {
        return protocol.isBaseProtocol();
    }
}
