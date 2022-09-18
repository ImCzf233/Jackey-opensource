package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/RecipeRewriter.class */
public abstract class RecipeRewriter {
    protected final Protocol protocol;
    protected final Map<String, RecipeConsumer> recipeHandlers = new HashMap();

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/RecipeRewriter$RecipeConsumer.class */
    public interface RecipeConsumer {
        void accept(PacketWrapper packetWrapper) throws Exception;
    }

    public RecipeRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public void handle(PacketWrapper wrapper, String type) throws Exception {
        RecipeConsumer handler = this.recipeHandlers.get(type);
        if (handler != null) {
            handler.accept(wrapper);
        }
    }

    public void registerDefaultHandler(ClientboundPacketType packetType) {
        this.protocol.registerClientbound((Protocol) packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.RecipeRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        String type = ((String) wrapper.passthrough(Type.STRING)).replace("minecraft:", "");
                        String str = (String) wrapper.passthrough(Type.STRING);
                        RecipeRewriter.this.handle(wrapper, type);
                    }
                });
            }
        });
    }

    public void rewrite(Item item) {
        if (this.protocol.getItemRewriter() != null) {
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
    }
}
