package com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_2to1_14_3/Protocol1_14_2To1_14_3.class */
public class Protocol1_14_2To1_14_3 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_2To1_14_3() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_14_2To1_14_3) ClientboundPackets1_14.TRADE_LIST, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3.Protocol1_14_2To1_14_3.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3.Protocol1_14_2To1_14_3.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.VAR_INT);
                        int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; i++) {
                            wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            if (((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.read(Type.BOOLEAN);
                    }
                });
            }
        });
        final RecipeRewriter recipeHandler = new RecipeRewriter1_14(this);
        registerClientbound((Protocol1_14_2To1_14_3) ClientboundPackets1_14.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_2to1_14_3.Protocol1_14_2To1_14_3.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                RecipeRewriter recipeRewriter = recipeHandler;
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    int deleted = 0;
                    for (int i = 0; i < size; i++) {
                        String fullType = (String) wrapper.read(Type.STRING);
                        String type = fullType.replace("minecraft:", "");
                        String id = (String) wrapper.read(Type.STRING);
                        if (type.equals("crafting_special_repairitem")) {
                            deleted++;
                        } else {
                            wrapper.write(Type.STRING, fullType);
                            wrapper.write(Type.STRING, id);
                            recipeRewriter.handle(wrapper, type);
                        }
                    }
                    wrapper.set(Type.VAR_INT, 0, Integer.valueOf(size - deleted));
                });
            }
        });
    }
}
