package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/data/CommandRewriter1_14.class */
public class CommandRewriter1_14 extends CommandRewriter {
    public CommandRewriter1_14(Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:nbt_tag", wrapper -> {
            wrapper.write(Type.VAR_INT, 2);
        });
        this.parserHandlers.put("minecraft:time", wrapper2 -> {
            wrapper2.write(Type.BYTE, (byte) 1);
            wrapper2.write(Type.INT, 0);
        });
    }

    @Override // com.viaversion.viaversion.rewriter.CommandRewriter
    public String handleArgumentType(String argumentType) {
        boolean z = true;
        switch (argumentType.hashCode()) {
            case -1006391174:
                if (argumentType.equals("minecraft:time")) {
                    z = true;
                    break;
                }
                break;
            case -783223342:
                if (argumentType.equals("minecraft:nbt_compound_tag")) {
                    z = false;
                    break;
                }
                break;
            case 543170382:
                if (argumentType.equals("minecraft:nbt_tag")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return "minecraft:nbt";
            case true:
                return "brigadier:string";
            case true:
                return "brigadier:integer";
            default:
                return super.handleArgumentType(argumentType);
        }
    }
}
