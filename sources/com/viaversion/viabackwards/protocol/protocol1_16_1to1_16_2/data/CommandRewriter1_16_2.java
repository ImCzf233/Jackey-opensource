package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_1to1_16_2/data/CommandRewriter1_16_2.class */
public class CommandRewriter1_16_2 extends CommandRewriter {
    public CommandRewriter1_16_2(Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:angle", wrapper -> {
            wrapper.write(Type.VAR_INT, 0);
        });
    }

    @Override // com.viaversion.viaversion.rewriter.CommandRewriter
    public String handleArgumentType(String argumentType) {
        if (argumentType.equals("minecraft:angle")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}
