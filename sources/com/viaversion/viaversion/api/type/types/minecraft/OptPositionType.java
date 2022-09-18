package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/OptPositionType.class */
public class OptPositionType extends Type<Position> {
    public OptPositionType() {
        super(Position.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Position read(ByteBuf buffer) throws Exception {
        boolean present = buffer.readBoolean();
        if (!present) {
            return null;
        }
        return Type.POSITION.read(buffer);
    }

    public void write(ByteBuf buffer, Position object) throws Exception {
        buffer.writeBoolean(object != null);
        if (object != null) {
            Type.POSITION.write(buffer, object);
        }
    }
}
