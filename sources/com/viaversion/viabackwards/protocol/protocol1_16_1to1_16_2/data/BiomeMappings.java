package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_1to1_16_2/data/BiomeMappings.class */
public final class BiomeMappings {
    private static final Object2IntMap<String> MODERN_TO_LEGACY_ID = new Object2IntOpenHashMap();
    private static final Object2IntMap<String> LEGACY_BIOMES = new Object2IntOpenHashMap();

    static {
        Object2IntMap.Entry<String> entry;
        LEGACY_BIOMES.defaultReturnValue(-1);
        MODERN_TO_LEGACY_ID.defaultReturnValue(-1);
        add(0, "ocean");
        add(1, "plains");
        add(2, "desert");
        add(3, "mountains");
        add(4, "forest");
        add(5, "taiga");
        add(6, "swamp");
        add(7, "river");
        add(8, "nether");
        add(9, "the_end");
        add(10, "frozen_ocean");
        add(11, "frozen_river");
        add(12, "snowy_tundra");
        add(13, "snowy_mountains");
        add(14, "mushroom_fields");
        add(15, "mushroom_field_shore");
        add(16, "beach");
        add(17, "desert_hills");
        add(18, "wooded_hills");
        add(19, "taiga_hills");
        add(20, "mountain_edge");
        add(21, "jungle");
        add(22, "jungle_hills");
        add(23, "jungle_edge");
        add(24, "deep_ocean");
        add(25, "stone_shore");
        add(26, "snowy_beach");
        add(27, "birch_forest");
        add(28, "birch_forest_hills");
        add(29, "dark_forest");
        add(30, "snowy_taiga");
        add(31, "snowy_taiga_hills");
        add(32, "giant_tree_taiga");
        add(33, "giant_tree_taiga_hills");
        add(34, "wooded_mountains");
        add(35, "savanna");
        add(36, "savanna_plateau");
        add(37, "badlands");
        add(38, "wooded_badlands_plateau");
        add(39, "badlands_plateau");
        add(40, "small_end_islands");
        add(41, "end_midlands");
        add(42, "end_highlands");
        add(43, "end_barrens");
        add(44, "warm_ocean");
        add(45, "lukewarm_ocean");
        add(46, "cold_ocean");
        add(47, "deep_warm_ocean");
        add(48, "deep_lukewarm_ocean");
        add(49, "deep_cold_ocean");
        add(50, "deep_frozen_ocean");
        add(127, "the_void");
        add(129, "sunflower_plains");
        add(130, "desert_lakes");
        add(131, "gravelly_mountains");
        add(132, "flower_forest");
        add(133, "taiga_mountains");
        add(134, "swamp_hills");
        add(140, "ice_spikes");
        add(149, "modified_jungle");
        add(151, "modified_jungle_edge");
        add(155, "tall_birch_forest");
        add(156, "tall_birch_hills");
        add(157, "dark_forest_hills");
        add(158, "snowy_taiga_mountains");
        add(160, "giant_spruce_taiga");
        add(161, "giant_spruce_taiga_hills");
        add(162, "modified_gravelly_mountains");
        add(163, "shattered_savanna");
        add(164, "shattered_savanna_plateau");
        add(165, "eroded_badlands");
        add(166, "modified_wooded_badlands_plateau");
        add(167, "modified_badlands_plateau");
        add(168, "bamboo_jungle");
        add(169, "bamboo_jungle_hills");
        ObjectIterator<Object2IntMap.Entry<String>> it = LEGACY_BIOMES.object2IntEntrySet().iterator();
        while (it.hasNext()) {
            MODERN_TO_LEGACY_ID.put((Object2IntMap<String>) ("minecraft:" + entry.getKey()), it.next().getIntValue());
        }
        JsonObject mappings = VBMappingDataLoader.loadFromDataDir("biome-mappings.json");
        for (Map.Entry<String, JsonElement> entry2 : mappings.entrySet()) {
            int legacyBiome = LEGACY_BIOMES.getInt(entry2.getValue().getAsString());
            if (legacyBiome == -1) {
                ViaBackwards.getPlatform().getLogger().warning("Unknown legacy biome: " + entry2.getValue().getAsString());
            } else {
                MODERN_TO_LEGACY_ID.put((Object2IntMap<String>) entry2.getKey(), legacyBiome);
            }
        }
    }

    private static void add(int id, String biome) {
        LEGACY_BIOMES.put((Object2IntMap<String>) biome, id);
    }

    public static int toLegacyBiome(String biome) {
        if (biome.indexOf(58) == -1) {
            biome = "minecraft:" + biome;
        }
        int legacyBiome = MODERN_TO_LEGACY_ID.getInt(biome);
        if (legacyBiome == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                ViaBackwards.getPlatform().getLogger().warning("Biome with id " + biome + " has no legacy biome mapping (custom datapack?)");
                return 1;
            }
            return 1;
        }
        return legacyBiome;
    }
}
