package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/IntType.class */
public class IntType extends Type<Integer> implements TypeConverter<Integer> {
    public IntType() {
        super(Integer.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Integer read(ByteBuf buffer) {
        return Integer.valueOf(buffer.readInt());
    }

    public void write(ByteBuf buffer, Integer object) {
        buffer.writeInt(object.intValue());
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Integer from(Object o) {
        if (o instanceof Number) {
            return Integer.valueOf(((Number) o).intValue());
        }
        if (!(o instanceof Boolean)) {
            return (Integer) o;
        }
        return Integer.valueOf(((Boolean) o).booleanValue() ? 1 : 0);
    }
}
