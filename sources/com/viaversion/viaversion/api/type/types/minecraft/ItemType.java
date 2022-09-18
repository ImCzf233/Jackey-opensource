package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ItemType.class */
public class ItemType extends BaseItemType {
    public ItemType() {
        super("Item");
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Item read(ByteBuf buffer) throws Exception {
        int id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        Item item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setData(buffer.readShort());
        item.setTag(NBT.read(buffer));
        return item;
    }

    public void write(ByteBuf buffer, Item object) throws Exception {
        if (object == null) {
            buffer.writeShort(-1);
            return;
        }
        buffer.writeShort(object.identifier());
        buffer.writeByte(object.amount());
        buffer.writeShort(object.data());
        NBT.write(buffer, object.tag());
    }
}
