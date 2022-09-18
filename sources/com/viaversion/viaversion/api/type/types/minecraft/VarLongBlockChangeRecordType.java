package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/minecraft/VarLongBlockChangeRecordType.class */
public class VarLongBlockChangeRecordType extends Type<BlockChangeRecord> {
    public VarLongBlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public BlockChangeRecord read(ByteBuf buffer) throws Exception {
        long data = Type.VAR_LONG.readPrimitive(buffer);
        short position = (short) (data & 4095);
        return new BlockChangeRecord1_16_2((position >>> 8) & 15, position & 15, (position >>> 4) & 15, (int) (data >>> 12));
    }

    public void write(ByteBuf buffer, BlockChangeRecord object) throws Exception {
        short position = (short) ((object.getSectionX() << 8) | (object.getSectionZ() << 4) | object.getSectionY());
        Type.VAR_LONG.writePrimitive(buffer, (object.getBlockId() << 12) | position);
    }
}
