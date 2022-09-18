package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/ShortByteArrayType.class */
public class ShortByteArrayType extends Type<byte[]> {
    public ShortByteArrayType() {
        super(byte[].class);
    }

    public void write(ByteBuf buffer, byte[] object) throws Exception {
        buffer.writeShort(object.length);
        buffer.writeBytes(object);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public byte[] read(ByteBuf buffer) throws Exception {
        byte[] array = new byte[buffer.readShort()];
        buffer.readBytes(array);
        return array;
    }
}
