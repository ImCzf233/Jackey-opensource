package com.viaversion.viaversion.api.minecraft.chunks;

import java.util.EnumMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionImpl.class */
public class ChunkSectionImpl implements ChunkSection {
    private final EnumMap<PaletteType, DataPalette> palettes = new EnumMap<>(PaletteType.class);
    private ChunkSectionLight light;
    private int nonAirBlocksCount;

    public ChunkSectionImpl() {
    }

    public ChunkSectionImpl(boolean holdsLight) {
        addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096));
        if (holdsLight) {
            this.light = new ChunkSectionLightImpl();
        }
    }

    public ChunkSectionImpl(boolean holdsLight, int expectedPaletteLength) {
        addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096, expectedPaletteLength));
        if (holdsLight) {
            this.light = new ChunkSectionLightImpl();
        }
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public int getNonAirBlocksCount() {
        return this.nonAirBlocksCount;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public void setNonAirBlocksCount(int nonAirBlocksCount) {
        this.nonAirBlocksCount = nonAirBlocksCount;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public ChunkSectionLight getLight() {
        return this.light;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public void setLight(ChunkSectionLight light) {
        this.light = light;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public DataPalette palette(PaletteType type) {
        return this.palettes.get(type);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public void addPalette(PaletteType type, DataPalette palette) {
        this.palettes.put((EnumMap<PaletteType, DataPalette>) type, (PaletteType) palette);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
    public void removePalette(PaletteType type) {
        this.palettes.remove(type);
    }
}
