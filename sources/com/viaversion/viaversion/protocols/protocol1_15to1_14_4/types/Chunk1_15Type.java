package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/types/Chunk1_15Type.class */
public class Chunk1_15Type extends Type<Chunk> {
    private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];

    public Chunk1_15Type() {
        super(Chunk.class);
    }

    @Override // com.viaversion.viaversion.api.type.ByteBufReader
    public Chunk read(ByteBuf input) throws Exception {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean fullChunk = input.readBoolean();
        int primaryBitmask = Type.VAR_INT.readPrimitive(input);
        CompoundTag heightMap = Type.NBT.read(input);
        int[] biomeData = fullChunk ? new int[1024] : null;
        if (fullChunk) {
            for (int i = 0; i < 1024; i++) {
                biomeData[i] = input.readInt();
            }
        }
        Type.VAR_INT.readPrimitive(input);
        ChunkSection[] sections = new ChunkSection[16];
        for (int i2 = 0; i2 < 16; i2++) {
            if ((primaryBitmask & (1 << i2)) != 0) {
                short nonAirBlocksCount = input.readShort();
                ChunkSection section = Types1_13.CHUNK_SECTION.read(input);
                section.setNonAirBlocksCount(nonAirBlocksCount);
                sections[i2] = section;
            }
        }
        List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NBT_ARRAY.read(input)));
        if (input.readableBytes() > 0) {
            byte[] array = Type.REMAINING_BYTES.read(input);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
            }
        }
        return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
    }

    public void write(ByteBuf output, Chunk chunk) throws Exception {
        int[] biomeData;
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
        Type.NBT.write(output, chunk.getHeightMap());
        if (chunk.isBiomeData()) {
            for (int value : chunk.getBiomeData()) {
                output.writeInt(value);
            }
        }
        ByteBuf buf = output.alloc().buffer();
        for (int i = 0; i < 16; i++) {
            try {
                ChunkSection section = chunk.getSections()[i];
                if (section != null) {
                    buf.writeShort(section.getNonAirBlocksCount());
                    Types1_13.CHUNK_SECTION.write(buf, section);
                }
            } catch (Throwable th) {
                buf.release();
                throw th;
            }
        }
        buf.readerIndex(0);
        Type.VAR_INT.writePrimitive(output, buf.readableBytes());
        output.writeBytes(buf);
        buf.release();
        Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray(EMPTY_COMPOUNDS));
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
