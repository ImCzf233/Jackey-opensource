package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/blockentities/FlowerPotHandler.class */
public class FlowerPotHandler implements BlockEntityProvider.BlockEntityHandler {
    private static final Map<Pair<?, Byte>, Integer> flowers = new ConcurrentHashMap();

    static {
        register("air", (byte) 0, (byte) 0, 5265);
        register("sapling", (byte) 6, (byte) 0, 5266);
        register("sapling", (byte) 6, (byte) 1, 5267);
        register("sapling", (byte) 6, (byte) 2, 5268);
        register("sapling", (byte) 6, (byte) 3, 5269);
        register("sapling", (byte) 6, (byte) 4, 5270);
        register("sapling", (byte) 6, (byte) 5, 5271);
        register("tallgrass", (byte) 31, (byte) 2, 5272);
        register("yellow_flower", (byte) 37, (byte) 0, 5273);
        register("red_flower", (byte) 38, (byte) 0, 5274);
        register("red_flower", (byte) 38, (byte) 1, 5275);
        register("red_flower", (byte) 38, (byte) 2, 5276);
        register("red_flower", (byte) 38, (byte) 3, 5277);
        register("red_flower", (byte) 38, (byte) 4, 5278);
        register("red_flower", (byte) 38, (byte) 5, 5279);
        register("red_flower", (byte) 38, (byte) 6, 5280);
        register("red_flower", (byte) 38, (byte) 7, 5281);
        register("red_flower", (byte) 38, (byte) 8, 5282);
        register("red_mushroom", (byte) 40, (byte) 0, 5283);
        register("brown_mushroom", (byte) 39, (byte) 0, 5284);
        register("deadbush", (byte) 32, (byte) 0, 5285);
        register("cactus", (byte) 81, (byte) 0, 5286);
    }

    public static void register(String identifier, byte numbericBlockId, byte blockData, int newId) {
        flowers.put(new Pair<>(identifier, Byte.valueOf(blockData)), Integer.valueOf(newId));
        flowers.put(new Pair<>(Byte.valueOf(numbericBlockId), Byte.valueOf(blockData)), Integer.valueOf(newId));
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider.BlockEntityHandler
    public int transform(UserConnection user, CompoundTag tag) {
        Object item;
        Object data;
        Object item2 = tag.contains("Item") ? tag.get("Item").getValue() : null;
        Object data2 = tag.contains("Data") ? tag.get("Data").getValue() : null;
        if (item2 instanceof String) {
            item = ((String) item2).replace("minecraft:", "");
        } else if (item2 instanceof Number) {
            item = Byte.valueOf(((Number) item2).byteValue());
        } else {
            item = (byte) 0;
        }
        if (data2 instanceof Number) {
            data = Byte.valueOf(((Number) data2).byteValue());
        } else {
            data = (byte) 0;
        }
        Integer flower = flowers.get(new Pair(item, Byte.valueOf(((Byte) data).byteValue())));
        if (flower != null) {
            return flower.intValue();
        }
        Integer flower2 = flowers.get(new Pair(item, (byte) 0));
        if (flower2 == null) {
            return 5265;
        }
        return flower2.intValue();
    }
}
