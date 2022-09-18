package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/VarIntArrayType.class */
public class VarIntArrayType extends Type<int[]> {
    public VarIntArrayType() {
        super(int[].class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public int[] read(ByteBuf buffer) throws Exception {
        int length = Type.VAR_INT.readPrimitive(buffer);
        Preconditions.checkArgument(buffer.isReadable(length));
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Type.VAR_INT.readPrimitive(buffer);
        }
        return array;
    }

    public void write(ByteBuf buffer, int[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (int i : object) {
            Type.VAR_INT.writePrimitive(buffer, i);
        }
    }
}
