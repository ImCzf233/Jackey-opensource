package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/ItemArrayType.class */
public class ItemArrayType extends Type<Item[]> {
    private final boolean compressed;

    public ItemArrayType(boolean compressed) {
        super(Item[].class);
        this.compressed = compressed;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Item[] read(ByteBuf buffer) throws Exception {
        int amount = Type.SHORT.read(buffer).shortValue();
        Item[] items = new Item[amount];
        for (int i = 0; i < amount; i++) {
            items[i] = (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(buffer);
        }
        return items;
    }

    public void write(ByteBuf buffer, Item[] items) throws Exception {
        Type.SHORT.write(buffer, Short.valueOf((short) items.length));
        for (Item item : items) {
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(buffer, item);
        }
    }
}
