package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/data/RecipeRewriter1_16.class */
public class RecipeRewriter1_16 extends RecipeRewriter1_14 {
    public RecipeRewriter1_16(Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("smithing", this::handleSmithing);
    }

    public void handleSmithing(PacketWrapper wrapper) throws Exception {
        Item[] baseIngredients = (Item[]) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        for (Item item : baseIngredients) {
            rewrite(item);
        }
        Item[] ingredients = (Item[]) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        for (Item item2 : ingredients) {
            rewrite(item2);
        }
        rewrite((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}
