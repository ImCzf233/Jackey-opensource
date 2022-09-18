package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/BlockEntityType1_18.class */
public class BlockEntityType1_18 extends Type<BlockEntity> {
    public BlockEntityType1_18() {
        super(BlockEntity.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public BlockEntity read(ByteBuf buffer) throws Exception {
        byte xz = buffer.readByte();
        short y = buffer.readShort();
        int typeId = Type.VAR_INT.readPrimitive(buffer);
        CompoundTag tag = Type.NBT.read(buffer);
        return new BlockEntityImpl(xz, y, typeId, tag);
    }

    public void write(ByteBuf buffer, BlockEntity entity) throws Exception {
        buffer.writeByte(entity.packedXZ());
        buffer.writeShort(entity.mo225y());
        Type.VAR_INT.writePrimitive(buffer, entity.typeId());
        Type.NBT.write(buffer, entity.tag());
    }
}
