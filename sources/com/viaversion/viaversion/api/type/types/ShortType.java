package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/ShortType.class */
public class ShortType extends Type<Short> implements TypeConverter<Short> {
    public ShortType() {
        super(Short.class);
    }

    public short readPrimitive(ByteBuf buffer) {
        return buffer.readShort();
    }

    public void writePrimitive(ByteBuf buffer, short object) {
        buffer.writeShort(object);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Short read(ByteBuf buffer) {
        return Short.valueOf(buffer.readShort());
    }

    @Deprecated
    public void write(ByteBuf buffer, Short object) {
        buffer.writeShort(object.shortValue());
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Short from(Object o) {
        if (o instanceof Number) {
            return Short.valueOf(((Number) o).shortValue());
        }
        if (!(o instanceof Boolean)) {
            return (Short) o;
        }
        return Short.valueOf(((Boolean) o).booleanValue() ? (short) 1 : (short) 0);
    }
}
