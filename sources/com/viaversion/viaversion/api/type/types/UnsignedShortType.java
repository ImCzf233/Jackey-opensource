package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/UnsignedShortType.class */
public class UnsignedShortType extends Type<Integer> implements TypeConverter<Integer> {
    public UnsignedShortType() {
        super(Integer.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Integer read(ByteBuf buffer) {
        return Integer.valueOf(buffer.readUnsignedShort());
    }

    public void write(ByteBuf buffer, Integer object) {
        buffer.writeShort(object.intValue());
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
