package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl.class */
public final class DataPaletteImpl implements DataPalette {
    private final IntList palette;
    private final Int2IntMap inversePalette;
    private final int[] values;
    private final int sizeBits;

    public DataPaletteImpl(int valuesLength) {
        this(valuesLength, 8);
    }

    public DataPaletteImpl(int valuesLength, int expectedPaletteLength) {
        this.values = new int[valuesLength];
        this.sizeBits = Integer.numberOfTrailingZeros(valuesLength) / 3;
        this.palette = new IntArrayList(expectedPaletteLength);
        this.inversePalette = new Int2IntOpenHashMap(expectedPaletteLength);
        this.inversePalette.defaultReturnValue(-1);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public int index(int x, int y, int z) {
        return (((y << this.sizeBits) | z) << this.sizeBits) | x;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public int idAt(int sectionCoordinate) {
        int index = this.values[sectionCoordinate];
        return this.palette.getInt(index);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void setIdAt(int sectionCoordinate, int id) {
        int index = this.inversePalette.get(id);
        if (index == -1) {
            index = this.palette.size();
            this.palette.add(id);
            this.inversePalette.put(id, index);
        }
        this.values[sectionCoordinate] = index;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public int paletteIndexAt(int packedCoordinate) {
        return this.values[packedCoordinate];
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void setPaletteIndexAt(int sectionCoordinate, int index) {
        this.values[sectionCoordinate] = index;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public int size() {
        return this.palette.size();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public int idByIndex(int index) {
        return this.palette.getInt(index);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void setIdByIndex(int index, int id) {
        int oldId = this.palette.set(index, id);
        if (oldId == id) {
            return;
        }
        this.inversePalette.put(id, index);
        if (this.inversePalette.get(oldId) == index) {
            this.inversePalette.remove(oldId);
            for (int i = 0; i < this.palette.size(); i++) {
                if (this.palette.getInt(i) == oldId) {
                    this.inversePalette.put(oldId, i);
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void replaceId(int oldId, int newId) {
        int index = this.inversePalette.remove(oldId);
        if (index == -1) {
            return;
        }
        this.inversePalette.put(newId, index);
        for (int i = 0; i < this.palette.size(); i++) {
            if (this.palette.getInt(i) == oldId) {
                this.palette.set(i, newId);
            }
        }
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void addId(int id) {
        this.inversePalette.put(id, this.palette.size());
        this.palette.add(id);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.DataPalette
    public void clear() {
        this.palette.clear();
        this.inversePalette.clear();
    }
}
