package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/FloatType.class */
public class FloatType extends Type<Float> implements TypeConverter<Float> {
    public FloatType() {
        super(Float.class);
    }

    public float readPrimitive(ByteBuf buffer) {
        return buffer.readFloat();
    }

    public void writePrimitive(ByteBuf buffer, float object) {
        buffer.writeFloat(object);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Float read(ByteBuf buffer) {
        return Float.valueOf(buffer.readFloat());
    }

    @Deprecated
    public void write(ByteBuf buffer, Float object) {
        buffer.writeFloat(object.floatValue());
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Float from(Object o) {
        if (o instanceof Number) {
            return Float.valueOf(((Number) o).floatValue());
        }
        if (!(o instanceof Boolean)) {
            return (Float) o;
        }
        return Float.valueOf(((Boolean) o).booleanValue() ? 1.0f : 0.0f);
    }
}
