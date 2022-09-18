package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/VarIntType.class */
public class VarIntType extends Type<Integer> implements TypeConverter<Integer> {
    public VarIntType() {
        super("VarInt", Integer.class);
    }

    public int readPrimitive(ByteBuf buffer) {
        byte in;
        int out = 0;
        int bytes = 0;
        do {
            in = buffer.readByte();
            int i = bytes;
            bytes++;
            out |= (in & Byte.MAX_VALUE) << (i * 7);
            if (bytes > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((in & 128) == 128);
        return out;
    }

    public void writePrimitive(ByteBuf buffer, int object) {
        do {
            int part = object & 127;
            object >>>= 7;
            if (object != 0) {
                part |= 128;
            }
            buffer.writeByte(part);
        } while (object != 0);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Integer read(ByteBuf buffer) {
        return Integer.valueOf(readPrimitive(buffer));
    }

    @Deprecated
    public void write(ByteBuf buffer, Integer object) {
        writePrimitive(buffer, object.intValue());
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
