package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/VarLongType.class */
public class VarLongType extends Type<Long> implements TypeConverter<Long> {
    public VarLongType() {
        super("VarLong", Long.class);
    }

    public long readPrimitive(ByteBuf buffer) {
        byte in;
        long out = 0;
        int bytes = 0;
        do {
            in = buffer.readByte();
            int i = bytes;
            bytes++;
            out |= (in & Byte.MAX_VALUE) << (i * 7);
            if (bytes > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((in & 128) == 128);
        return out;
    }

    public void writePrimitive(ByteBuf buffer, long object) {
        do {
            int part = (int) (object & 127);
            object >>>= 7;
            if (object != 0) {
                part |= 128;
            }
            buffer.writeByte(part);
        } while (object != 0);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Long read(ByteBuf buffer) {
        return Long.valueOf(readPrimitive(buffer));
    }

    @Deprecated
    public void write(ByteBuf buffer, Long object) {
        writePrimitive(buffer, object.longValue());
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Long from(Object o) {
        if (o instanceof Number) {
            return Long.valueOf(((Number) o).longValue());
        }
        if (!(o instanceof Boolean)) {
            return (Long) o;
        }
        return Long.valueOf(((Boolean) o).booleanValue() ? 1L : 0L);
    }
}
