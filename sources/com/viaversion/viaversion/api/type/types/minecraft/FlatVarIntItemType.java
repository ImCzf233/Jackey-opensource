package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/FlatVarIntItemType.class */
public class FlatVarIntItemType extends BaseItemType {
    public FlatVarIntItemType() {
        super("FlatVarIntItem");
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Item read(ByteBuf buffer) throws Exception {
        boolean present = buffer.readBoolean();
        if (!present) {
            return null;
        }
        Item item = new DataItem();
        item.setIdentifier(VAR_INT.readPrimitive(buffer));
        item.setAmount(buffer.readByte());
        item.setTag(NBT.read(buffer));
        return item;
    }

    public void write(ByteBuf buffer, Item object) throws Exception {
        if (object == null) {
            buffer.writeBoolean(false);
            return;
        }
        buffer.writeBoolean(true);
        VAR_INT.writePrimitive(buffer, object.identifier());
        buffer.writeByte(object.amount());
        NBT.write(buffer, object.tag());
    }
}
