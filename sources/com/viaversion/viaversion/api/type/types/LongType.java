package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/LongType.class */
public class LongType extends Type<Long> implements TypeConverter<Long> {
    public LongType() {
        super(Long.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Long read(ByteBuf buffer) {
        return Long.valueOf(buffer.readLong());
    }

    @Deprecated
    public void write(ByteBuf buffer, Long object) {
        buffer.writeLong(object.longValue());
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

    public long readPrimitive(ByteBuf buffer) {
        return buffer.readLong();
    }

    public void writePrimitive(ByteBuf buffer, long object) {
        buffer.writeLong(object);
    }
}
