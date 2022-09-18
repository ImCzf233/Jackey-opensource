package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkBulkType;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/types/ChunkBulk1_8Type.class */
public class ChunkBulk1_8Type extends PartialType<Chunk[], ClientWorld> {
    private static final int BLOCKS_PER_SECTION = 4096;
    private static final int BLOCKS_BYTES = 8192;
    private static final int LIGHT_BYTES = 2048;
    private static final int BIOME_BYTES = 256;

    public ChunkBulk1_8Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk[].class);
    }

    @Override // com.viaversion.viaversion.api.type.Type
    public Class<? extends Type> getBaseClass() {
        return BaseChunkBulkType.class;
    }

    public Chunk[] read(ByteBuf input, ClientWorld world) throws Exception {
        boolean skyLight = input.readBoolean();
        int count = Type.VAR_INT.readPrimitive(input);
        Chunk[] chunks = new Chunk[count];
        ChunkBulkSection[] chunkInfo = new ChunkBulkSection[count];
        for (int i = 0; i < chunkInfo.length; i++) {
            chunkInfo[i] = new ChunkBulkSection(input, skyLight);
        }
        for (int i2 = 0; i2 < chunks.length; i2++) {
            ChunkBulkSection chunkBulkSection = chunkInfo[i2];
            chunkBulkSection.readData(input);
            chunks[i2] = Chunk1_8Type.deserialize(chunkBulkSection.chunkX, chunkBulkSection.chunkZ, true, skyLight, chunkBulkSection.bitmask, chunkBulkSection.getData());
        }
        return chunks;
    }

    public void write(ByteBuf output, ClientWorld world, Chunk[] chunks) throws Exception {
        ChunkSection[] sections;
        boolean skyLight = false;
        int length = chunks.length;
        int i = 0;
        loop0: while (true) {
            if (i >= length) {
                break;
            }
            for (ChunkSection section : chunks[i].getSections()) {
                if (section != null && section.getLight().hasSkyLight()) {
                    skyLight = true;
                    break loop0;
                }
            }
            i++;
        }
        output.writeBoolean(skyLight);
        Type.VAR_INT.writePrimitive(output, chunks.length);
        for (Chunk c : chunks) {
            output.writeInt(c.getX());
            output.writeInt(c.getZ());
            output.writeShort(c.getBitmask());
        }
        for (Chunk c2 : chunks) {
            output.writeBytes(Chunk1_8Type.serialize(c2));
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/types/ChunkBulk1_8Type$ChunkBulkSection.class */
    public static final class ChunkBulkSection {
        private final int chunkX;
        private final int chunkZ;
        private final int bitmask;
        private final byte[] data;

        public ChunkBulkSection(ByteBuf input, boolean skyLight) {
            this.chunkX = input.readInt();
            this.chunkZ = input.readInt();
            this.bitmask = input.readUnsignedShort();
            int setSections = Integer.bitCount(this.bitmask);
            this.data = new byte[(setSections * (8192 + (skyLight ? 4096 : 2048))) + 256];
        }

        public void readData(ByteBuf input) {
            input.readBytes(this.data);
        }

        public int getChunkX() {
            return this.chunkX;
        }

        public int getChunkZ() {
            return this.chunkZ;
        }

        public int getBitmask() {
            return this.bitmask;
        }

        public byte[] getData() {
            return this.data;
        }
    }
}
