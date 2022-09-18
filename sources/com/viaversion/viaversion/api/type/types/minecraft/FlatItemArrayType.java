package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/FlatItemArrayType.class */
public class FlatItemArrayType extends BaseItemArrayType {
    public FlatItemArrayType() {
        super("Flat Item Array");
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Item[] read(ByteBuf buffer) throws Exception {
        int amount = Type.SHORT.readPrimitive(buffer);
        Item[] array = new Item[amount];
        for (int i = 0; i < amount; i++) {
            array[i] = Type.FLAT_ITEM.read(buffer);
        }
        return array;
    }

    public void write(ByteBuf buffer, Item[] object) throws Exception {
        Type.SHORT.writePrimitive(buffer, (short) object.length);
        for (Item o : object) {
            Type.FLAT_ITEM.write(buffer, o);
        }
    }
}
