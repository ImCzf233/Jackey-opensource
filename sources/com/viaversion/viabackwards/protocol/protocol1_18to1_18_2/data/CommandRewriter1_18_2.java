package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_18to1_18_2/data/CommandRewriter1_18_2.class */
public final class CommandRewriter1_18_2 extends CommandRewriter {
    public CommandRewriter1_18_2(Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:resource", wrapper -> {
            wrapper.read(Type.STRING);
            wrapper.write(Type.VAR_INT, 1);
        });
        this.parserHandlers.put("minecraft:resource_or_tag", wrapper2 -> {
            wrapper2.read(Type.STRING);
            wrapper2.write(Type.VAR_INT, 1);
        });
    }

    @Override // com.viaversion.viaversion.rewriter.CommandRewriter
    public String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:resource") || argumentType.equals("minecraft:resource_or_tag")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}
