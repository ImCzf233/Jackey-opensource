package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/data/MappingData.class */
public class MappingData extends MappingDataBase {
    private IntSet motionBlocking;
    private IntSet nonFullBlocks;

    public MappingData() {
        super("1.13.2", "1.14");
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
        JsonObject blockStates = newMappings.getAsJsonObject("blockstates");
        Map<String, Integer> blockStateMap = new HashMap<>(blockStates.entrySet().size());
        for (Map.Entry<String, JsonElement> entry : blockStates.entrySet()) {
            blockStateMap.put(entry.getValue().getAsString(), Integer.valueOf(Integer.parseInt(entry.getKey())));
        }
        JsonObject heightMapData = MappingDataLoader.loadData("heightMapData-1.14.json");
        JsonArray motionBlocking = heightMapData.getAsJsonArray("MOTION_BLOCKING");
        this.motionBlocking = new IntOpenHashSet(motionBlocking.size(), 0.99f);
        Iterator<JsonElement> it = motionBlocking.iterator();
        while (it.hasNext()) {
            JsonElement blockState = it.next();
            String key = blockState.getAsString();
            Integer id = blockStateMap.get(key);
            if (id == null) {
                Via.getPlatform().getLogger().warning("Unknown blockstate " + key + " :(");
            } else {
                this.motionBlocking.add(id.intValue());
            }
        }
        if (Via.getConfig().isNonFullBlockLightFix()) {
            this.nonFullBlocks = new IntOpenHashSet(1611, 0.99f);
            for (Map.Entry<String, JsonElement> blockstates : oldMappings.getAsJsonObject("blockstates").entrySet()) {
                String state = blockstates.getValue().getAsString();
                if (state.contains("_slab") || state.contains("_stairs") || state.contains("_wall[")) {
                    this.nonFullBlocks.add(this.blockStateMappings.getNewId(Integer.parseInt(blockstates.getKey())));
                }
            }
            this.nonFullBlocks.add(this.blockStateMappings.getNewId(8163));
            for (int i = 3060; i <= 3067; i++) {
                this.nonFullBlocks.add(this.blockStateMappings.getNewId(i));
            }
        }
    }

    public IntSet getMotionBlocking() {
        return this.motionBlocking;
    }

    public IntSet getNonFullBlocks() {
        return this.nonFullBlocks;
    }
}
