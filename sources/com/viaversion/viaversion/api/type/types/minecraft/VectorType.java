package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/VectorType.class */
public class VectorType extends Type<Vector> {
    public VectorType() {
        super(Vector.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Vector read(ByteBuf buffer) throws Exception {
        int x = Type.INT.read(buffer).intValue();
        int y = Type.INT.read(buffer).intValue();
        int z = Type.INT.read(buffer).intValue();
        return new Vector(x, y, z);
    }

    public void write(ByteBuf buffer, Vector object) throws Exception {
        Type.INT.write(buffer, Integer.valueOf(object.blockX()));
        Type.INT.write(buffer, Integer.valueOf(object.blockY()));
        Type.INT.write(buffer, Integer.valueOf(object.blockZ()));
    }
}
