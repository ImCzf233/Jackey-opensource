package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/VoidType.class */
public class VoidType extends Type<Void> implements TypeConverter<Void> {
    public VoidType() {
        super(Void.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Void read(ByteBuf buffer) {
        return null;
    }

    public void write(ByteBuf buffer, Void object) {
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Void from(Object o) {
        return null;
    }
}
