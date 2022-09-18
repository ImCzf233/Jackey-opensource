package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/MelonConnectionHandler.class */
public class MelonConnectionHandler extends AbstractStempConnectionHandler {
    public MelonConnectionHandler(String baseStateId) {
        super(baseStateId);
    }

    public static ConnectionData.ConnectorInitAction init() {
        return new MelonConnectionHandler("minecraft:melon_stem[age=7]").getInitAction("minecraft:melon", "minecraft:attached_melon_stem");
    }
}
