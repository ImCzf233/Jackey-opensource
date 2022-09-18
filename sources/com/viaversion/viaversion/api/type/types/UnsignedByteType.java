package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/UnsignedByteType.class */
public class UnsignedByteType extends Type<Short> implements TypeConverter<Short> {
    public UnsignedByteType() {
        super("Unsigned Byte", Short.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Short read(ByteBuf buffer) {
        return Short.valueOf(buffer.readUnsignedByte());
    }

    public void write(ByteBuf buffer, Short object) {
        buffer.writeByte(object.shortValue());
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
