package com.viaversion.viaversion.api.minecraft;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/BlockChangeRecord1_8.class */
public class BlockChangeRecord1_8 implements BlockChangeRecord {
    private final byte sectionX;

    /* renamed from: y */
    private final short f28y;
    private final byte sectionZ;
    private int blockId;

    public BlockChangeRecord1_8(byte sectionX, short y, byte sectionZ, int blockId) {
        this.sectionX = sectionX;
        this.f28y = y;
        this.sectionZ = sectionZ;
        this.blockId = blockId;
    }

    public BlockChangeRecord1_8(int sectionX, int y, int sectionZ, int blockId) {
        this((byte) sectionX, (short) y, (byte) sectionZ, blockId);
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public byte getSectionX() {
        return this.sectionX;
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public byte getSectionY() {
        return (byte) (this.f28y & 15);
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public short getY(int chunkSectionY) {
        return this.f28y;
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public byte getSectionZ() {
        return this.sectionZ;
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public int getBlockId() {
        return this.blockId;
    }

    @Override // com.viaversion.viaversion.api.minecraft.BlockChangeRecord
    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }
}
