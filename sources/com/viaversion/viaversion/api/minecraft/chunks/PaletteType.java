package com.viaversion.viaversion.api.minecraft.chunks;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/PaletteType.class */
public enum PaletteType {
    BLOCKS(4096, 8),
    BIOMES(64, 3);
    
    private final int size;
    private final int highestBitsPerValue;

    PaletteType(int size, int highestBitsPerValue) {
        this.size = size;
        this.highestBitsPerValue = highestBitsPerValue;
    }

    public int size() {
        return this.size;
    }

    public int highestBitsPerValue() {
        return this.highestBitsPerValue;
    }
}
