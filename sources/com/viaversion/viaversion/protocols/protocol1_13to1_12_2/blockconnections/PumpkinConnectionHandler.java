package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/PumpkinConnectionHandler.class */
public class PumpkinConnectionHandler extends AbstractStempConnectionHandler {
    public static ConnectionData.ConnectorInitAction init() {
        return new PumpkinConnectionHandler("minecraft:pumpkin_stem[age=7]").getInitAction("minecraft:carved_pumpkin", "minecraft:attached_pumpkin_stem");
    }

    public PumpkinConnectionHandler(String baseStateId) {
        super(baseStateId);
    }
}
