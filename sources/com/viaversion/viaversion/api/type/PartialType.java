package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/PartialType.class */
public abstract class PartialType<T, X> extends Type<T> {
    private final X param;

    public abstract T read(ByteBuf byteBuf, X x) throws Exception;

    public abstract void write(ByteBuf byteBuf, X x, T t) throws Exception;

    public PartialType(X param, Class<T> type) {
        super(type);
        this.param = param;
    }

    protected PartialType(X param, String name, Class<T> type) {
        super(name, type);
        this.param = param;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public final T read(ByteBuf buffer) throws Exception {
        return read(buffer, this.param);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufWriter
    public final void write(ByteBuf buffer, T object) throws Exception {
        write(buffer, this.param, object);
    }
}
