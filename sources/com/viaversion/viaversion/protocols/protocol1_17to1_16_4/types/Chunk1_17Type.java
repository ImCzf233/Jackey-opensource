package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_17to1_16_4/types/Chunk1_17Type.class */
public final class Chunk1_17Type extends Type<Chunk> {
    private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
    private final int ySectionCount;

    public Chunk1_17Type(int ySectionCount) {
        super(Chunk.class);
        Preconditions.checkArgument(ySectionCount > 0);
        this.ySectionCount = ySectionCount;
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Chunk read(ByteBuf input) throws Exception {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        BitSet sectionsMask = BitSet.valueOf(Type.LONG_ARRAY_PRIMITIVE.read(input));
        CompoundTag heightMap = Type.NBT.read(input);
        int[] biomeData = Type.VAR_INT_ARRAY_PRIMITIVE.read(input);
        Type.VAR_INT.readPrimitive(input);
        ChunkSection[] sections = new ChunkSection[this.ySectionCount];
        for (int i = 0; i < this.ySectionCount; i++) {
            if (sectionsMask.get(i)) {
                short nonAirBlocksCount = input.readShort();
                ChunkSection section = Types1_16.CHUNK_SECTION.read(input);
                section.setNonAirBlocksCount(nonAirBlocksCount);
                sections[i] = section;
            }
        }
        List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NBT_ARRAY.read(input)));
        if (input.readableBytes() > 0) {
            byte[] array = Type.REMAINING_BYTES.read(input);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
            }
        }
        return new BaseChunk(chunkX, chunkZ, true, false, sectionsMask, sections, biomeData, heightMap, nbtData);
    }

    public void write(ByteBuf output, Chunk chunk) throws Exception {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        Type.LONG_ARRAY_PRIMITIVE.write(output, chunk.getChunkMask().toLongArray());
        Type.NBT.write(output, chunk.getHeightMap());
        Type.VAR_INT_ARRAY_PRIMITIVE.write(output, chunk.getBiomeData());
        ByteBuf buf = output.alloc().buffer();
        try {
            ChunkSection[] sections = chunk.getSections();
            for (ChunkSection section : sections) {
                if (section != null) {
                    buf.writeShort(section.getNonAirBlocksCount());
                    Types1_16.CHUNK_SECTION.write(buf, section);
                }
            }
            buf.readerIndex(0);
            Type.VAR_INT.writePrimitive(output, buf.readableBytes());
            output.writeBytes(buf);
            buf.release();
            Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray(EMPTY_COMPOUNDS));
        } catch (Throwable th) {
            buf.release();
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
