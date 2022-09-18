package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/MetadataType.class */
public class MetadataType extends MetaTypeTemplate {
    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Metadata read(ByteBuf buffer) throws Exception {
        byte item = buffer.readByte();
        if (item == Byte.MAX_VALUE) {
            return null;
        }
        int typeID = (item & 224) >> 5;
        MetaType1_7_6_10 type = MetaType1_7_6_10.byId(typeID);
        int id = item & 31;
        return new Metadata(id, type, type.type().read(buffer));
    }

    public void write(ByteBuf buffer, Metadata meta) throws Exception {
        int item = ((meta.metaType().typeId() << 5) | (meta.m222id() & 31)) & 255;
        buffer.writeByte(item);
        meta.metaType().type().write(buffer, meta.getValue());
    }
}
