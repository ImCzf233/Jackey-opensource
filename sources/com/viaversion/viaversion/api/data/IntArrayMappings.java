package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/IntArrayMappings.class */
public class IntArrayMappings implements Mappings {
    private final int[] oldToNew;
    private final int mappedIds;

    public IntArrayMappings(int[] oldToNew, int mappedIds) {
        this.oldToNew = oldToNew;
        this.mappedIds = mappedIds;
    }

    /* renamed from: of */
    public static IntArrayMappings m234of(int[] oldToNew, int mappedIds) {
        return new IntArrayMappings(oldToNew, mappedIds);
    }

    public static Mappings.Builder<IntArrayMappings> builder() {
        return Mappings.builder(IntArrayMappings::new);
    }

    @Deprecated
    public IntArrayMappings(int[] oldToNew) {
        this(oldToNew, -1);
    }

    @Deprecated
    public IntArrayMappings(int size, JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping) {
        this.oldToNew = new int[size];
        Arrays.fill(this.oldToNew, -1);
        this.mappedIds = newMapping.size();
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping, diffMapping);
    }

    @Deprecated
    public IntArrayMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping) {
        this(oldMapping.entrySet().size(), oldMapping, newMapping, diffMapping);
    }

    @Deprecated
    public IntArrayMappings(int size, JsonObject oldMapping, JsonObject newMapping) {
        this.oldToNew = new int[size];
        Arrays.fill(this.oldToNew, -1);
        this.mappedIds = -1;
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping);
    }

    @Deprecated
    public IntArrayMappings(JsonObject oldMapping, JsonObject newMapping) {
        this(oldMapping.entrySet().size(), oldMapping, newMapping);
    }

    @Deprecated
    public IntArrayMappings(int size, JsonArray oldMapping, JsonArray newMapping, JsonObject diffMapping, boolean warnOnMissing) {
        this.oldToNew = new int[size];
        Arrays.fill(this.oldToNew, -1);
        this.mappedIds = -1;
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping, diffMapping, warnOnMissing);
    }

    @Deprecated
    public IntArrayMappings(int size, JsonArray oldMapping, JsonArray newMapping, boolean warnOnMissing) {
        this(size, oldMapping, newMapping, null, warnOnMissing);
    }

    @Deprecated
    public IntArrayMappings(JsonArray oldMapping, JsonArray newMapping, boolean warnOnMissing) {
        this(oldMapping.size(), oldMapping, newMapping, warnOnMissing);
    }

    @Deprecated
    public IntArrayMappings(int size, JsonArray oldMapping, JsonArray newMapping) {
        this(size, oldMapping, newMapping, true);
    }

    @Deprecated
    public IntArrayMappings(JsonArray oldMapping, JsonArray newMapping, JsonObject diffMapping) {
        this(oldMapping.size(), oldMapping, newMapping, diffMapping, true);
    }

    @Deprecated
    public IntArrayMappings(JsonArray oldMapping, JsonArray newMapping) {
        this(oldMapping.size(), oldMapping, newMapping, true);
    }

    @Override // com.viaversion.viaversion.api.data.Mappings
    public int getNewId(int id) {
        if (id < 0 || id >= this.oldToNew.length) {
            return -1;
        }
        return this.oldToNew[id];
    }

    @Override // com.viaversion.viaversion.api.data.Mappings
    public void setNewId(int id, int newId) {
        this.oldToNew[id] = newId;
    }

    @Override // com.viaversion.viaversion.api.data.Mappings
    public int size() {
        return this.oldToNew.length;
    }

    @Override // com.viaversion.viaversion.api.data.Mappings
    public int mappedSize() {
        return this.mappedIds;
    }

    public int[] getOldToNew() {
        return this.oldToNew;
    }
}
