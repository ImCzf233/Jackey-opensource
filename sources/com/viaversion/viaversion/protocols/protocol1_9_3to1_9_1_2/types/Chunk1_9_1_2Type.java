package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/types/Chunk1_9_1_2Type.class */
public class Chunk1_9_1_2Type extends PartialType<Chunk, ClientWorld> {
    public Chunk1_9_1_2Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }

    public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean groundUp = input.readBoolean();
        int primaryBitmask = Type.VAR_INT.readPrimitive(input);
        Type.VAR_INT.readPrimitive(input);
        BitSet usedSections = new BitSet(16);
        ChunkSection[] sections = new ChunkSection[16];
        for (int i = 0; i < 16; i++) {
            if ((primaryBitmask & (1 << i)) != 0) {
                usedSections.set(i);
            }
        }
        for (int i2 = 0; i2 < 16; i2++) {
            if (usedSections.get(i2)) {
                ChunkSection section = Types1_9.CHUNK_SECTION.read(input);
                sections[i2] = section;
                section.getLight().readBlockLight(input);
                if (world.getEnvironment() == Environment.NORMAL) {
                    section.getLight().readSkyLight(input);
                }
            }
        }
        int[] biomeData = groundUp ? new int[256] : null;
        if (groundUp) {
            for (int i3 = 0; i3 < 256; i3++) {
                biomeData[i3] = input.readByte() & 255;
            }
        }
        return new BaseChunk(chunkX, chunkZ, groundUp, false, primaryBitmask, sections, biomeData, new ArrayList());
    }

    public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
        int[] biomeData;
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
        ByteBuf buf = output.alloc().buffer();
        for (int i = 0; i < 16; i++) {
            try {
                ChunkSection section = chunk.getSections()[i];
                if (section != null) {
                    Types1_9.CHUNK_SECTION.write(buf, section);
                    section.getLight().writeBlockLight(buf);
                    if (section.getLight().hasSkyLight()) {
                        section.getLight().writeSkyLight(buf);
                    }
                }
            } catch (Throwable th) {
                buf.release();
                throw th;
            }
        }
        buf.readerIndex(0);
        Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
        output.writeBytes(buf);
        buf.release();
        if (chunk.isBiomeData()) {
            for (int biome : chunk.getBiomeData()) {
                output.writeByte((byte) biome);
            }
        }
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
