package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/ByteArrayType.class */
public class ByteArrayType extends Type<byte[]> {
    public ByteArrayType() {
        super(byte[].class);
    }

    public void write(ByteBuf buffer, byte[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        buffer.writeBytes(object);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public byte[] read(ByteBuf buffer) throws Exception {
        int length = Type.VAR_INT.readPrimitive(buffer);
        Preconditions.checkArgument(buffer.isReadable(length), "Length is fewer than readable bytes");
        byte[] array = new byte[length];
        buffer.readBytes(array);
        return array;
    }
}
