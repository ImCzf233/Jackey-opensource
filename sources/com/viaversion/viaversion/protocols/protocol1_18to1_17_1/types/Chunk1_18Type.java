package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.ChunkSectionType1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/types/Chunk1_18Type.class */
public final class Chunk1_18Type extends Type<Chunk> {
    private final ChunkSectionType1_18 sectionType;
    private final int ySectionCount;

    public Chunk1_18Type(int ySectionCount, int globalPaletteBlockBits, int globalPaletteBiomeBits) {
        super(Chunk.class);
        Preconditions.checkArgument(ySectionCount > 0);
        this.sectionType = new ChunkSectionType1_18(globalPaletteBlockBits, globalPaletteBiomeBits);
        this.ySectionCount = ySectionCount;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Chunk read(ByteBuf buffer) throws Exception {
        int chunkX = buffer.readInt();
        int chunkZ = buffer.readInt();
        CompoundTag heightMap = Type.NBT.read(buffer);
        ByteBuf sectionsBuf = buffer.readBytes(Type.VAR_INT.readPrimitive(buffer));
        ChunkSection[] sections = new ChunkSection[this.ySectionCount];
        for (int i = 0; i < this.ySectionCount; i++) {
            try {
                sections[i] = this.sectionType.read(sectionsBuf);
            } finally {
                if (sectionsBuf.readableBytes() > 0 && Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("Found " + sectionsBuf.readableBytes() + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
                }
                sectionsBuf.release();
            }
        }
        int blockEntitiesLength = Type.VAR_INT.readPrimitive(buffer);
        List<BlockEntity> blockEntities = new ArrayList<>(blockEntitiesLength);
        for (int i2 = 0; i2 < blockEntitiesLength; i2++) {
            blockEntities.add(Types1_18.BLOCK_ENTITY.read(buffer));
        }
        return new Chunk1_18(chunkX, chunkZ, sections, heightMap, blockEntities);
    }

    public void write(ByteBuf buffer, Chunk chunk) throws Exception {
        ChunkSection[] sections;
        buffer.writeInt(chunk.getX());
        buffer.writeInt(chunk.getZ());
        Type.NBT.write(buffer, chunk.getHeightMap());
        ByteBuf sectionBuffer = buffer.alloc().buffer();
        try {
            for (ChunkSection section : chunk.getSections()) {
                this.sectionType.write(sectionBuffer, section);
            }
            sectionBuffer.readerIndex(0);
            Type.VAR_INT.writePrimitive(buffer, sectionBuffer.readableBytes());
            buffer.writeBytes(sectionBuffer);
            sectionBuffer.release();
            Type.VAR_INT.writePrimitive(buffer, chunk.blockEntities().size());
            for (BlockEntity blockEntity : chunk.blockEntities()) {
                Types1_18.BLOCK_ENTITY.write(buffer, blockEntity);
            }
        } catch (Throwable th) {
            sectionBuffer.release();
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
