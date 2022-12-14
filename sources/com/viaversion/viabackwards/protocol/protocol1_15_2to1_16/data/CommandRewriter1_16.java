package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.CommandRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/data/CommandRewriter1_16.class */
public class CommandRewriter1_16 extends CommandRewriter {
    public CommandRewriter1_16(Protocol protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.CommandRewriter
    public String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:uuid")) {
            return "minecraft:game_profile";
        }
        return super.handleArgumentType(argumentType);
    }
}
