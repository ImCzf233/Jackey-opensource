package com.viaversion.viabackwards.api.data;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/data/BackwardsMappings.class */
public class BackwardsMappings extends MappingDataBase {
    private final Class<? extends Protocol> vvProtocolClass;
    private Int2ObjectMap<MappedItem> backwardsItemMappings;
    private Map<String, String> backwardsSoundMappings;
    private Map<String, String> entityNames;

    public BackwardsMappings(String oldVersion, String newVersion, Class<? extends Protocol> vvProtocolClass) {
        this(oldVersion, newVersion, vvProtocolClass, false);
    }

    public BackwardsMappings(String oldVersion, String newVersion, Class<? extends Protocol> vvProtocolClass, boolean hasDiffFile) {
        super(oldVersion, newVersion, hasDiffFile);
        Preconditions.checkArgument(vvProtocolClass == null || !vvProtocolClass.isAssignableFrom(BackwardsProtocol.class));
        this.vvProtocolClass = vvProtocolClass;
        this.loadItems = false;
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    protected void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
        if (diffMappings != null) {
            JsonObject diffItems = diffMappings.getAsJsonObject("items");
            if (diffItems != null) {
                this.backwardsItemMappings = VBMappingDataLoader.loadItemMappings(oldMappings.getAsJsonObject("items"), newMappings.getAsJsonObject("items"), diffItems, shouldWarnOnMissing("items"));
            }
            JsonObject diffSounds = diffMappings.getAsJsonObject("sounds");
            if (diffSounds != null) {
                this.backwardsSoundMappings = VBMappingDataLoader.objectToNamespacedMap(diffSounds);
            }
            JsonObject diffEntityNames = diffMappings.getAsJsonObject("entitynames");
            if (diffEntityNames != null) {
                this.entityNames = VBMappingDataLoader.objectToMap(diffEntityNames);
            }
        }
        if (this.vvProtocolClass != null) {
            this.itemMappings = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData().getItemMappings().inverse();
        }
        loadVBExtras(oldMappings, newMappings);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public Mappings loadFromArray(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (!oldMappings.has(key) || !newMappings.has(key)) {
            return null;
        }
        JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
        return VBMappings.vbBuilder().unmapped(oldMappings.getAsJsonArray(key)).mapped(newMappings.getAsJsonArray(key)).diffMappings(diff).warnOnMissing(shouldWarnOnMissing(key)).build();
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (!oldMappings.has(key) || !newMappings.has(key)) {
            return null;
        }
        JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
        return VBMappings.vbBuilder().unmapped(oldMappings.getAsJsonObject(key)).mapped(newMappings.getAsJsonObject(key)).diffMappings(diff).warnOnMissing(shouldWarnOnMissing(key)).build();
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    protected JsonObject loadDiffFile() {
        return VBMappingDataLoader.loadFromDataDir("mapping-" + this.newVersion + "to" + this.oldVersion + ".json");
    }

    protected void loadVBExtras(JsonObject oldMappings, JsonObject newMappings) {
    }

    public boolean shouldWarnOnMissing(String key) {
        return !key.equals("blocks") && !key.equals("statistics");
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    protected Logger getLogger() {
        return ViaBackwards.getPlatform().getLogger();
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase, com.viaversion.viaversion.api.data.MappingData
    public int getNewItemId(int id) {
        return this.itemMappings.get(id);
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase, com.viaversion.viaversion.api.data.MappingData
    public int getNewBlockId(int id) {
        return this.blockMappings.getNewId(id);
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase, com.viaversion.viaversion.api.data.MappingData
    public int getOldItemId(int id) {
        return checkValidity(id, this.itemMappings.inverse().get(id), "item");
    }

    public MappedItem getMappedItem(int id) {
        if (this.backwardsItemMappings != null) {
            return this.backwardsItemMappings.get(id);
        }
        return null;
    }

    public String getMappedNamedSound(String id) {
        if (this.backwardsSoundMappings == null) {
            return null;
        }
        if (id.indexOf(58) == -1) {
            id = "minecraft:" + id;
        }
        return this.backwardsSoundMappings.get(id);
    }

    public String mappedEntityName(String entityName) {
        return this.entityNames.get(entityName);
    }

    public Int2ObjectMap<MappedItem> getBackwardsItemMappings() {
        return this.backwardsItemMappings;
    }

    public Map<String, String> getBackwardsSoundMappings() {
        return this.backwardsSoundMappings;
    }
}
