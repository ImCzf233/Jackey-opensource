package com.viaversion.viaversion.api.minecraft.chunks;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.class */
public interface ChunkSection {
    public static final int SIZE = 4096;
    public static final int BIOME_SIZE = 64;

    int getNonAirBlocksCount();

    void setNonAirBlocksCount(int i);

    ChunkSectionLight getLight();

    void setLight(ChunkSectionLight chunkSectionLight);

    DataPalette palette(PaletteType paletteType);

    void addPalette(PaletteType paletteType, DataPalette dataPalette);

    void removePalette(PaletteType paletteType);

    static int index(int x, int y, int z) {
        return (y << 8) | (z << 4) | x;
    }

    @Deprecated
    default int getFlatBlock(int idx) {
        return palette(PaletteType.BLOCKS).idAt(idx);
    }

    @Deprecated
    default int getFlatBlock(int x, int y, int z) {
        return getFlatBlock(index(x, y, z));
    }

    @Deprecated
    default void setFlatBlock(int idx, int id) {
        palette(PaletteType.BLOCKS).setIdAt(idx, id);
    }

    @Deprecated
    default void setFlatBlock(int x, int y, int z, int id) {
        setFlatBlock(index(x, y, z), id);
    }

    @Deprecated
    default int getBlockWithoutData(int x, int y, int z) {
        return getFlatBlock(x, y, z) >> 4;
    }

    @Deprecated
    default int getBlockData(int x, int y, int z) {
        return getFlatBlock(x, y, z) & 15;
    }

    @Deprecated
    default void setBlockWithData(int x, int y, int z, int type, int data) {
        setFlatBlock(index(x, y, z), (type << 4) | (data & 15));
    }

    @Deprecated
    default void setBlockWithData(int idx, int type, int data) {
        setFlatBlock(idx, (type << 4) | (data & 15));
    }

    @Deprecated
    default void setPaletteIndex(int idx, int index) {
        palette(PaletteType.BLOCKS).setPaletteIndexAt(idx, index);
    }

    @Deprecated
    default int getPaletteIndex(int idx) {
        return palette(PaletteType.BLOCKS).paletteIndexAt(idx);
    }

    @Deprecated
    default int getPaletteSize() {
        return palette(PaletteType.BLOCKS).size();
    }

    @Deprecated
    default int getPaletteEntry(int index) {
        return palette(PaletteType.BLOCKS).idByIndex(index);
    }

    @Deprecated
    default void setPaletteEntry(int index, int id) {
        palette(PaletteType.BLOCKS).setIdByIndex(index, id);
    }

    @Deprecated
    default void replacePaletteEntry(int oldId, int newId) {
        palette(PaletteType.BLOCKS).replaceId(oldId, newId);
    }

    @Deprecated
    default void addPaletteEntry(int id) {
        palette(PaletteType.BLOCKS).addId(id);
    }

    @Deprecated
    default void clearPalette() {
        palette(PaletteType.BLOCKS).clear();
    }

    default boolean hasLight() {
        return getLight() != null;
    }
}
