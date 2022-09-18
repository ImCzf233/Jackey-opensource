package com.viaversion.viaversion.api.minecraft.chunks;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/DataPalette.class */
public interface DataPalette {
    int index(int i, int i2, int i3);

    int idAt(int i);

    void setIdAt(int i, int i2);

    int idByIndex(int i);

    void setIdByIndex(int i, int i2);

    int paletteIndexAt(int i);

    void setPaletteIndexAt(int i, int i2);

    void addId(int i);

    void replaceId(int i, int i2);

    int size();

    void clear();

    default int idAt(int sectionX, int sectionY, int sectionZ) {
        return idAt(index(sectionX, sectionY, sectionZ));
    }

    default void setIdAt(int sectionX, int sectionY, int sectionZ, int id) {
        setIdAt(index(sectionX, sectionY, sectionZ), id);
    }
}
