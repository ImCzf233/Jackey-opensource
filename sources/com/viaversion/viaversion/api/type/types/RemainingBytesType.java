package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/RemainingBytesType.class */
public class RemainingBytesType extends Type<byte[]> {
    public RemainingBytesType() {
        super(byte[].class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public byte[] read(ByteBuf buffer) {
        byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        return array;
    }

    public void write(ByteBuf buffer, byte[] object) {
        buffer.writeBytes(object);
    }
}
