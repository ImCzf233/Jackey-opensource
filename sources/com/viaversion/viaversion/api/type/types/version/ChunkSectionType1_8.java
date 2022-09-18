package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/type/types/version/ChunkSectionType1_8.class */
public class ChunkSectionType1_8 extends Type<ChunkSection> {
    public ChunkSectionType1_8() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public ChunkSection read(ByteBuf buffer) throws Exception {
        ChunkSection chunkSection = new ChunkSectionImpl(true);
        chunkSection.addPaletteEntry(0);
        ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 4096; i++) {
            int mask = littleEndianView.readShort();
            int type = mask >> 4;
            int data = mask & 15;
            chunkSection.setBlockWithData(i, type, data);
        }
        return chunkSection;
    }

    public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
        for (int y = 0; y < 16; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    int block = chunkSection.getFlatBlock(x, y, z);
                    buffer.writeByte(block);
                    buffer.writeByte(block >> 8);
                }
            }
        }
    }
}
