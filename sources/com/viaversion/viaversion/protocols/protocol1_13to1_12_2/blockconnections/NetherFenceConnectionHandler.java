package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/NetherFenceConnectionHandler.class */
public class NetherFenceConnectionHandler extends AbstractFenceConnectionHandler {
    public static ConnectionData.ConnectorInitAction init() {
        return new NetherFenceConnectionHandler("netherFenceConnections").getInitAction("minecraft:nether_brick_fence");
    }

    public NetherFenceConnectionHandler(String blockConnections) {
        super(blockConnections);
    }
}
