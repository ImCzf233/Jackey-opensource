package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/ByteType.class */
public class ByteType extends Type<Byte> implements TypeConverter<Byte> {
    public ByteType() {
        super(Byte.class);
    }

    public byte readPrimitive(ByteBuf buffer) {
        return buffer.readByte();
    }

    public void writePrimitive(ByteBuf buffer, byte object) {
        buffer.writeByte(object);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Byte read(ByteBuf buffer) {
        return Byte.valueOf(buffer.readByte());
    }

    @Deprecated
    public void write(ByteBuf buffer, Byte object) {
        buffer.writeByte(object.byteValue());
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Byte from(Object o) {
        if (o instanceof Number) {
            return Byte.valueOf(((Number) o).byteValue());
        }
        if (!(o instanceof Boolean)) {
            return (Byte) o;
        }
        return Byte.valueOf(((Boolean) o).booleanValue() ? (byte) 1 : (byte) 0);
    }
}
