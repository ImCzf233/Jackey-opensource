package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.PartialType;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/CustomByteType.class */
public class CustomByteType extends PartialType<byte[], Integer> {
    public CustomByteType(Integer param) {
        super(param, byte[].class);
    }

    public byte[] read(ByteBuf byteBuf, Integer integer) throws Exception {
        if (byteBuf.readableBytes() < integer.intValue()) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        byte[] byteArray = new byte[integer.intValue()];
        byteBuf.readBytes(byteArray);
        return byteArray;
    }

    public void write(ByteBuf byteBuf, Integer integer, byte[] bytes) throws Exception {
        byteBuf.writeBytes(bytes);
    }
}
