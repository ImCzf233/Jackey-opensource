package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/ModernMetaType.class */
public abstract class ModernMetaType extends MetaTypeTemplate {
    private static final int END = 255;

    protected abstract MetaType getType(int i);

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Metadata read(ByteBuf buffer) throws Exception {
        short index = buffer.readUnsignedByte();
        if (index == 255) {
            return null;
        }
        MetaType type = getType(Type.VAR_INT.readPrimitive(buffer));
        return new Metadata(index, type, type.type().read(buffer));
    }

    public void write(ByteBuf buffer, Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(255);
            return;
        }
        buffer.writeByte(object.m222id());
        MetaType type = object.metaType();
        Type.VAR_INT.writePrimitive(buffer, type.typeId());
        type.type().write(buffer, object.getValue());
    }
}
