package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/PositionType.class */
public class PositionType extends Type<Position> {
    public PositionType() {
        super(Position.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Position read(ByteBuf buffer) {
        long val = buffer.readLong();
        long x = val >> 38;
        long y = (val >> 26) & 4095;
        long z = (val << 38) >> 38;
        return new Position((int) x, (short) y, (int) z);
    }

    public void write(ByteBuf buffer, Position object) {
        buffer.writeLong(((object.m228x() & 67108863) << 38) | ((object.m227y() & 4095) << 26) | (object.m226z() & 67108863));
    }
}
