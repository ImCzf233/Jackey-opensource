package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/OldMetaType.class */
public abstract class OldMetaType extends MetaTypeTemplate {
    private static final int END = 127;

    protected abstract MetaType getType(int i);

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Metadata read(ByteBuf buffer) throws Exception {
        byte index = buffer.readByte();
        if (index == Byte.MAX_VALUE) {
            return null;
        }
        MetaType type = getType((index & 224) >> 5);
        return new Metadata(index & 31, type, type.type().read(buffer));
    }

    public void write(ByteBuf buffer, Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(127);
            return;
        }
        int index = ((object.metaType().typeId() << 5) | (object.m222id() & 31)) & 255;
        buffer.writeByte(index);
        object.metaType().type().write(buffer, object.getValue());
    }
}
