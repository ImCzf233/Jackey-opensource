package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/Protocol.class */
public interface Protocol<C1 extends ClientboundPacketType, C2 extends ClientboundPacketType, S1 extends ServerboundPacketType, S2 extends ServerboundPacketType> {
    void registerServerbound(State state, int i, int i2, PacketRemapper packetRemapper, boolean z);

    @Deprecated
    void cancelServerbound(State state, int i, int i2);

    void cancelServerbound(State state, int i);

    @Deprecated
    void cancelClientbound(State state, int i, int i2);

    void cancelClientbound(State state, int i);

    void registerClientbound(State state, int i, int i2, PacketRemapper packetRemapper, boolean z);

    void registerClientbound(C1 c1, PacketRemapper packetRemapper);

    void registerClientbound(C1 c1, C2 c2, PacketRemapper packetRemapper, boolean z);

    void cancelClientbound(C1 c1);

    void registerServerbound(S2 s2, PacketRemapper packetRemapper);

    void registerServerbound(S2 s2, S1 s1, PacketRemapper packetRemapper, boolean z);

    void cancelServerbound(S2 s2);

    boolean hasRegisteredClientbound(C1 c1);

    boolean hasRegisteredServerbound(S2 s2);

    boolean hasRegisteredClientbound(State state, int i);

    boolean hasRegisteredServerbound(State state, int i);

    void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception;

    <T> T get(Class<T> cls);

    void put(Object obj);

    void initialize();

    boolean hasMappingDataToLoad();

    void loadMappingData();

    default void registerServerbound(State state, int oldPacketID, int newPacketID) {
        registerServerbound(state, oldPacketID, newPacketID, (PacketRemapper) null);
    }

    default void registerServerbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
        registerServerbound(state, oldPacketID, newPacketID, packetRemapper, false);
    }

    default void registerClientbound(State state, int oldPacketID, int newPacketID) {
        registerClientbound(state, oldPacketID, newPacketID, (PacketRemapper) null);
    }

    default void registerClientbound(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
        registerClientbound(state, oldPacketID, newPacketID, packetRemapper, false);
    }

    default void registerClientbound(C1 packetType, C2 mappedPacketType) {
        registerClientbound((Protocol<C1, C2, S1, S2>) packetType, (C1) mappedPacketType, (PacketRemapper) null);
    }

    default void registerClientbound(C1 packetType, C2 mappedPacketType, PacketRemapper packetRemapper) {
        registerClientbound((Protocol<C1, C2, S1, S2>) packetType, (C1) mappedPacketType, packetRemapper, false);
    }

    default void registerServerbound(S2 packetType, S1 mappedPacketType) {
        registerServerbound((Protocol<C1, C2, S1, S2>) packetType, (S2) mappedPacketType, (PacketRemapper) null);
    }

    default void registerServerbound(S2 packetType, S1 mappedPacketType, PacketRemapper packetRemapper) {
        registerServerbound((Protocol<C1, C2, S1, S2>) packetType, (S2) mappedPacketType, packetRemapper, false);
    }

    default void register(ViaProviders providers) {
    }

    default void init(UserConnection userConnection) {
    }

    default MappingData getMappingData() {
        return null;
    }

    default EntityRewriter getEntityRewriter() {
        return null;
    }

    default ItemRewriter getItemRewriter() {
        return null;
    }

    default boolean isBaseProtocol() {
        return false;
    }
}
