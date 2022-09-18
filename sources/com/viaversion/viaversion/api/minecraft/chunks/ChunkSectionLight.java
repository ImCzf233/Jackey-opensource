package com.viaversion.viaversion.api.minecraft.chunks;

import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionLight.class */
public interface ChunkSectionLight {
    public static final int LIGHT_LENGTH = 2048;

    boolean hasSkyLight();

    boolean hasBlockLight();

    byte[] getSkyLight();

    byte[] getBlockLight();

    void setSkyLight(byte[] bArr);

    void setBlockLight(byte[] bArr);

    NibbleArray getSkyLightNibbleArray();

    NibbleArray getBlockLightNibbleArray();

    void readSkyLight(ByteBuf byteBuf);

    void readBlockLight(ByteBuf byteBuf);

    void writeSkyLight(ByteBuf byteBuf);

    void writeBlockLight(ByteBuf byteBuf);
}
