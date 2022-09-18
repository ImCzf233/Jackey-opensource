package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/ByteBufWriter.class */
public interface ByteBufWriter<T> {
    void write(ByteBuf byteBuf, T t) throws Exception;
}
