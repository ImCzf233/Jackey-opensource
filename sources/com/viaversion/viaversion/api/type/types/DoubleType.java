package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/DoubleType.class */
public class DoubleType extends Type<Double> implements TypeConverter<Double> {
    public DoubleType() {
        super(Double.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    @Deprecated
    public Double read(ByteBuf buffer) {
        return Double.valueOf(buffer.readDouble());
    }

    public double readPrimitive(ByteBuf buffer) {
        return buffer.readDouble();
    }

    @Deprecated
    public void write(ByteBuf buffer, Double object) {
        buffer.writeDouble(object.doubleValue());
    }

    public void writePrimitive(ByteBuf buffer, double object) {
        buffer.writeDouble(object);
    }

    @Override // com.viaversion.viaversion.api.type.TypeConverter
    public Double from(Object o) {
        if (o instanceof Number) {
            return Double.valueOf(((Number) o).doubleValue());
        }
        if (!(o instanceof Boolean)) {
            return (Double) o;
        }
        return Double.valueOf(((Boolean) o).booleanValue() ? 1.0d : 0.0d);
    }
}
