package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.CommandRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/CommandRewriter1_14.class */
public class CommandRewriter1_14 extends CommandRewriter {
    public CommandRewriter1_14(Protocol protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.rewriter.CommandRewriter
    public String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:nbt")) {
            return "minecraft:nbt_compound_tag";
        }
        return super.handleArgumentType(argumentType);
    }
}
