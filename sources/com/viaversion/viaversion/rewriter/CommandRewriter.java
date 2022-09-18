package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/CommandRewriter.class */
public abstract class CommandRewriter {
    protected final Protocol protocol;
    protected final Map<String, CommandArgumentConsumer> parserHandlers = new HashMap();

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/CommandRewriter$CommandArgumentConsumer.class */
    public interface CommandArgumentConsumer {
        void accept(PacketWrapper packetWrapper) throws Exception;
    }

    public CommandRewriter(Protocol protocol) {
        this.protocol = protocol;
        this.parserHandlers.put("brigadier:double", wrapper -> {
            byte propertyFlags = ((Byte) wrapper.passthrough(Type.BYTE)).byteValue();
            if ((propertyFlags & 1) != 0) {
                wrapper.passthrough(Type.DOUBLE);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper.passthrough(Type.DOUBLE);
            }
        });
        this.parserHandlers.put("brigadier:float", wrapper2 -> {
            byte propertyFlags = ((Byte) wrapper2.passthrough(Type.BYTE)).byteValue();
            if ((propertyFlags & 1) != 0) {
                wrapper2.passthrough(Type.FLOAT);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper2.passthrough(Type.FLOAT);
            }
        });
        this.parserHandlers.put("brigadier:integer", wrapper3 -> {
            byte propertyFlags = ((Byte) wrapper3.passthrough(Type.BYTE)).byteValue();
            if ((propertyFlags & 1) != 0) {
                wrapper3.passthrough(Type.INT);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper3.passthrough(Type.INT);
            }
        });
        this.parserHandlers.put("brigadier:long", wrapper4 -> {
            byte propertyFlags = ((Byte) wrapper4.passthrough(Type.BYTE)).byteValue();
            if ((propertyFlags & 1) != 0) {
                wrapper4.passthrough(Type.LONG);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper4.passthrough(Type.LONG);
            }
        });
        this.parserHandlers.put("brigadier:string", wrapper5 -> {
            wrapper5.passthrough(Type.VAR_INT);
        });
        this.parserHandlers.put("minecraft:entity", wrapper6 -> {
            wrapper6.passthrough(Type.BYTE);
        });
        this.parserHandlers.put("minecraft:score_holder", wrapper7 -> {
            wrapper7.passthrough(Type.BYTE);
        });
        this.parserHandlers.put("minecraft:resource", wrapper8 -> {
            wrapper8.passthrough(Type.STRING);
        });
        this.parserHandlers.put("minecraft:resource_or_tag", wrapper9 -> {
            wrapper9.passthrough(Type.STRING);
        });
    }

    public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
        CommandArgumentConsumer handler = this.parserHandlers.get(argumentType);
        if (handler != null) {
            handler.accept(wrapper);
        }
    }

    public void registerDeclareCommands(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.CommandRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        byte flags = ((Byte) wrapper.passthrough(Type.BYTE)).byteValue();
                        wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                        if ((flags & 8) != 0) {
                            wrapper.passthrough(Type.VAR_INT);
                        }
                        byte nodeType = (byte) (flags & 3);
                        if (nodeType == 1 || nodeType == 2) {
                            wrapper.passthrough(Type.STRING);
                        }
                        if (nodeType == 2) {
                            String argumentType = (String) wrapper.read(Type.STRING);
                            String newArgumentType = CommandRewriter.this.handleArgumentType(argumentType);
                            if (newArgumentType != null) {
                                wrapper.write(Type.STRING, newArgumentType);
                            }
                            CommandRewriter.this.handleArgument(wrapper, argumentType);
                        }
                        if ((flags & 16) != 0) {
                            wrapper.passthrough(Type.STRING);
                        }
                    }
                    wrapper.passthrough(Type.VAR_INT);
                });
            }
        });
    }

    public String handleArgumentType(String argumentType) {
        return argumentType;
    }
}
