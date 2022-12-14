package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/LongArrayType.class */
public class LongArrayType extends Type<long[]> {
    public LongArrayType() {
        super(long[].class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public long[] read(ByteBuf buffer) throws Exception {
        int length = Type.VAR_INT.readPrimitive(buffer);
        long[] array = new long[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Type.LONG.readPrimitive(buffer);
        }
        return array;
    }

    public void write(ByteBuf buffer, long[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (long l : object) {
            Type.LONG.writePrimitive(buffer, l);
        }
    }
}
