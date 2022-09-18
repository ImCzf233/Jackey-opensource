package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionHandler.class */
public abstract class ConnectionHandler {
    public abstract int connect(UserConnection userConnection, Position position, int i);

    public int getBlockData(UserConnection user, Position position) {
        return ((BlockConnectionProvider) Via.getManager().getProviders().get(BlockConnectionProvider.class)).getBlockData(user, position.m228x(), position.m227y(), position.m226z());
    }
}
