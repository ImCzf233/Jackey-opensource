package com.viaversion.viaversion.api.minecraft.chunks;

import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLightImpl.class */
public class ChunkSectionLightImpl implements ChunkSectionLight {
    private NibbleArray blockLight = new NibbleArray(4096);
    private NibbleArray skyLight;

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void setBlockLight(byte[] data) {
        if (data.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(data);
        } else {
            this.blockLight.setHandle(data);
        }
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void setSkyLight(byte[] data) {
        if (data.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(data);
        } else {
            this.skyLight.setHandle(data);
        }
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public byte[] getBlockLight() {
        if (this.blockLight == null) {
            return null;
        }
        return this.blockLight.getHandle();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public NibbleArray getBlockLightNibbleArray() {
        return this.blockLight;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public byte[] getSkyLight() {
        if (this.skyLight == null) {
            return null;
        }
        return this.skyLight.getHandle();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public NibbleArray getSkyLightNibbleArray() {
        return this.skyLight;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void readBlockLight(ByteBuf input) {
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(4096);
        }
        input.readBytes(this.blockLight.getHandle());
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void readSkyLight(ByteBuf input) {
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(4096);
        }
        input.readBytes(this.skyLight.getHandle());
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void writeBlockLight(ByteBuf output) {
        output.writeBytes(this.blockLight.getHandle());
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public void writeSkyLight(ByteBuf output) {
        output.writeBytes(this.skyLight.getHandle());
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public boolean hasSkyLight() {
        return this.skyLight != null;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight
    public boolean hasBlockLight() {
        return this.blockLight != null;
    }
}
