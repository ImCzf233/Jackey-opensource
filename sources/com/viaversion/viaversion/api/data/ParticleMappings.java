package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonArray;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/ParticleMappings.class */
public class ParticleMappings {
    private final Object2IntMap<String> stringToId;
    private final Object2IntMap<String> mappedStringToId;
    private final Mappings mappings;
    private final IntList itemParticleIds = new IntArrayList(2);
    private final IntList blockParticleIds = new IntArrayList(4);

    public ParticleMappings(JsonArray oldMappings, JsonArray newMappings, Mappings mappings) {
        this.mappings = mappings;
        this.stringToId = MappingDataLoader.arrayToMap(oldMappings);
        this.mappedStringToId = MappingDataLoader.arrayToMap(newMappings);
        this.stringToId.defaultReturnValue(-1);
        this.mappedStringToId.defaultReturnValue(-1);
        addBlockParticle("block");
        addBlockParticle("falling_dust");
        addBlockParticle("block_marker");
        addItemParticle("item");
    }

    /* renamed from: id */
    public int m233id(String identifier) {
        return this.stringToId.getInt(identifier);
    }

    public int mappedId(String mappedIdentifier) {
        return this.mappedStringToId.getInt(mappedIdentifier);
    }

    public Mappings getMappings() {
        return this.mappings;
    }

    public boolean addItemParticle(String identifier) {
        int id = m233id(identifier);
        return id != -1 && this.itemParticleIds.add(id);
    }

    public boolean addBlockParticle(String identifier) {
        int id = m233id(identifier);
        return id != -1 && this.blockParticleIds.add(id);
    }

    public boolean isBlockParticle(int id) {
        return this.blockParticleIds.contains(id);
    }

    public boolean isItemParticle(int id) {
        return this.itemParticleIds.contains(id);
    }

    @Deprecated
    public int getBlockId() {
        return m233id("block");
    }

    @Deprecated
    public int getFallingDustId() {
        return m233id("falling_dust");
    }

    @Deprecated
    public int getItemId() {
        return m233id("item");
    }
}
