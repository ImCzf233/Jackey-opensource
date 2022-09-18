package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.Int2IntBiHashMap;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/MappingDataBase.class */
public class MappingDataBase implements MappingData {
    protected final String oldVersion;
    protected final String newVersion;
    protected final boolean hasDiffFile;
    protected Int2IntBiMap itemMappings;
    protected ParticleMappings particleMappings;
    protected Mappings blockMappings;
    protected Mappings blockStateMappings;
    protected Mappings blockEntityMappings;
    protected Mappings soundMappings;
    protected Mappings statisticsMappings;
    protected Map<RegistryType, List<TagData>> tags;
    protected boolean loadItems;

    public MappingDataBase(String oldVersion, String newVersion) {
        this(oldVersion, newVersion, false);
    }

    public MappingDataBase(String oldVersion, String newVersion, boolean hasDiffFile) {
        this.loadItems = true;
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.hasDiffFile = hasDiffFile;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public void load() {
        getLogger().info("Loading " + this.oldVersion + " -> " + this.newVersion + " mappings...");
        JsonObject diffmapping = this.hasDiffFile ? loadDiffFile() : null;
        JsonObject oldMappings = MappingDataLoader.loadData("mapping-" + this.oldVersion + ".json", true);
        JsonObject newMappings = MappingDataLoader.loadData("mapping-" + this.newVersion + ".json", true);
        this.blockMappings = loadFromObject(oldMappings, newMappings, diffmapping, "blocks");
        this.blockStateMappings = loadFromObject(oldMappings, newMappings, diffmapping, "blockstates");
        this.blockEntityMappings = loadFromArray(oldMappings, newMappings, diffmapping, "blockentities");
        this.soundMappings = loadFromArray(oldMappings, newMappings, diffmapping, "sounds");
        this.statisticsMappings = loadFromArray(oldMappings, newMappings, diffmapping, "statistics");
        Mappings particles = loadFromArray(oldMappings, newMappings, diffmapping, "particles");
        if (particles != null) {
            this.particleMappings = new ParticleMappings(oldMappings.getAsJsonArray("particles"), newMappings.getAsJsonArray("particles"), particles);
        }
        if (this.loadItems && newMappings.has("items")) {
            this.itemMappings = new Int2IntBiHashMap();
            this.itemMappings.defaultReturnValue(-1);
            MappingDataLoader.mapIdentifiers(this.itemMappings, oldMappings.getAsJsonObject("items"), newMappings.getAsJsonObject("items"), diffmapping != null ? diffmapping.getAsJsonObject("items") : null, true);
        }
        if (diffmapping != null && diffmapping.has("tags")) {
            this.tags = new EnumMap(RegistryType.class);
            JsonObject tags = diffmapping.getAsJsonObject("tags");
            if (tags.has(RegistryType.ITEM.resourceLocation())) {
                loadTags(RegistryType.ITEM, tags, MappingDataLoader.indexedObjectToMap(newMappings.getAsJsonObject("items")));
            }
            if (tags.has(RegistryType.BLOCK.resourceLocation())) {
                loadTags(RegistryType.BLOCK, tags, MappingDataLoader.indexedObjectToMap(newMappings.getAsJsonObject("blocks")));
            }
        }
        loadExtras(oldMappings, newMappings, diffmapping);
    }

    private void loadTags(RegistryType type, JsonObject object, Object2IntMap<String> typeMapping) {
        JsonObject tags = object.getAsJsonObject(type.resourceLocation());
        List<TagData> tagsList = new ArrayList<>(tags.size());
        for (Map.Entry<String, JsonElement> entry : tags.entrySet()) {
            JsonArray array = entry.getValue().getAsJsonArray();
            int[] entries = new int[array.size()];
            int i = 0;
            Iterator<JsonElement> it = array.iterator();
            while (it.hasNext()) {
                JsonElement element = it.next();
                String stringId = element.getAsString();
                if (!typeMapping.containsKey(stringId)) {
                    String replace = stringId.replace("minecraft:", "");
                    stringId = replace;
                    if (!typeMapping.containsKey(replace)) {
                        getLogger().warning(type + " Tags contains invalid type identifier " + stringId + " in tag " + entry.getKey());
                    }
                }
                int i2 = i;
                i++;
                entries[i2] = typeMapping.getInt(stringId);
            }
            tagsList.add(new TagData(entry.getKey(), entries));
        }
        this.tags.put(type, tagsList);
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public int getNewBlockStateId(int id) {
        return checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public int getNewBlockId(int id) {
        return checkValidity(id, this.blockMappings.getNewId(id), "block");
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public int getNewItemId(int id) {
        return checkValidity(id, this.itemMappings.get(id), "item");
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public int getOldItemId(int id) {
        int oldId = this.itemMappings.inverse().get(id);
        if (oldId != -1) {
            return oldId;
        }
        return 1;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public int getNewParticleId(int id) {
        return checkValidity(id, this.particleMappings.getMappings().getNewId(id), "particles");
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public List<TagData> getTags(RegistryType type) {
        if (this.tags != null) {
            return this.tags.get(type);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Int2IntBiMap getItemMappings() {
        return this.itemMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public ParticleMappings getParticleMappings() {
        return this.particleMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Mappings getBlockMappings() {
        return this.blockMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Mappings getBlockEntityMappings() {
        return this.blockEntityMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Mappings getBlockStateMappings() {
        return this.blockStateMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Mappings getSoundMappings() {
        return this.soundMappings;
    }

    @Override // com.viaversion.viaversion.api.data.MappingData
    public Mappings getStatisticsMappings() {
        return this.statisticsMappings;
    }

    public Mappings loadFromArray(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (!oldMappings.has(key) || !newMappings.has(key)) {
            return null;
        }
        JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
        return IntArrayMappings.builder().unmapped(oldMappings.getAsJsonArray(key)).mapped(newMappings.getAsJsonArray(key)).diffMappings(diff).build();
    }

    public Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (!oldMappings.has(key) || !newMappings.has(key)) {
            return null;
        }
        JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
        return IntArrayMappings.builder().unmapped(oldMappings.getAsJsonObject(key)).mapped(newMappings.getAsJsonObject(key)).diffMappings(diff).build();
    }

    protected JsonObject loadDiffFile() {
        return MappingDataLoader.loadData("mappingdiff-" + this.oldVersion + "to" + this.newVersion + ".json");
    }

    protected Logger getLogger() {
        return Via.getPlatform().getLogger();
    }

    public int checkValidity(int id, int mappedId, String type) {
        if (mappedId == -1) {
            getLogger().warning(String.format("Missing %s %s for %s %s %d", this.newVersion, type, this.oldVersion, type, Integer.valueOf(id)));
            return 0;
        }
        return mappedId;
    }

    protected void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
    }
}
