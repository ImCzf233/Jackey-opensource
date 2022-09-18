package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/ItemType.class */
public class ItemType extends Type<Item> {
    private final boolean compressed;

    public ItemType(boolean compressed) {
        super(Item.class);
        this.compressed = compressed;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Item read(ByteBuf buffer) throws Exception {
        buffer.readerIndex();
        int id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        Item item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setData(buffer.readShort());
        item.setTag((this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).read(buffer));
        return item;
    }

    public void write(ByteBuf buffer, Item item) throws Exception {
        if (item == null) {
            buffer.writeShort(-1);
            return;
        }
        buffer.writeShort(item.identifier());
        buffer.writeByte(item.amount());
        buffer.writeShort(item.data());
        (this.compressed ? Types1_7_6_10.COMPRESSED_NBT : Types1_7_6_10.NBT).write(buffer, item.tag());
    }
}
