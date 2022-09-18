package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/OptionalVarIntType.class */
public class OptionalVarIntType extends Type<Integer> {
    public OptionalVarIntType() {
        super(Integer.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Integer read(ByteBuf buffer) throws Exception {
        int read = Type.VAR_INT.readPrimitive(buffer);
        if (read == 0) {
            return null;
        }
        return Integer.valueOf(read - 1);
    }

    public void write(ByteBuf buffer, Integer object) throws Exception {
        if (object != null) {
            Type.VAR_INT.writePrimitive(buffer, object.intValue() + 1);
        } else {
            Type.VAR_INT.writePrimitive(buffer, 0);
        }
    }
}
