package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/ByteBufReader.class */
public interface ByteBufReader<T> {
    T read(ByteBuf byteBuf) throws Exception;
}
