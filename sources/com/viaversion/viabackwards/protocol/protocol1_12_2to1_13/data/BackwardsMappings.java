package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.data.VBMappings;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.IntArrayMappings;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings.class */
public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
    private final Int2ObjectMap<String> statisticMappings = new Int2ObjectOpenHashMap();
    private final Map<String, String> translateMappings = new HashMap();
    private Mappings enchantmentMappings;

    public BackwardsMappings() {
        super("1.13", "1.12", Protocol1_13To1_12_2.class, true);
    }

    @Override // com.viaversion.viabackwards.api.data.BackwardsMappings
    public void loadVBExtras(JsonObject oldMappings, JsonObject newMappings) {
        this.enchantmentMappings = VBMappings.vbBuilder().warnOnMissing(false).unmapped(oldMappings.getAsJsonObject("enchantments")).mapped(newMappings.getAsJsonObject("enchantments")).build();
        for (Map.Entry<String, Integer> entry : StatisticMappings.CUSTOM_STATS.entrySet()) {
            this.statisticMappings.put(entry.getValue().intValue(), (int) entry.getKey());
        }
        for (Map.Entry<String, String> entry2 : Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet()) {
            this.translateMappings.put(entry2.getValue(), entry2.getKey());
        }
    }

    private static void mapIdentifiers(int[] output, JsonObject newIdentifiers, JsonObject oldIdentifiers, JsonObject mapping) {
        int propertyIndex;
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(oldIdentifiers);
        for (Map.Entry<String, JsonElement> entry : newIdentifiers.entrySet()) {
            String key = entry.getValue().getAsString();
            int value = newIdentifierMap.getInt(key);
            short hardId = -1;
            if (value == -1) {
                JsonPrimitive replacement = mapping.getAsJsonPrimitive(key);
                if (replacement == null && (propertyIndex = key.indexOf(91)) != -1) {
                    replacement = mapping.getAsJsonPrimitive(key.substring(0, propertyIndex));
                }
                if (replacement != null) {
                    if (replacement.getAsString().startsWith("id:")) {
                        String id = replacement.getAsString().replace("id:", "");
                        hardId = Short.parseShort(id);
                        value = newIdentifierMap.getInt(oldIdentifiers.getAsJsonPrimitive(id).getAsString());
                    } else {
                        value = newIdentifierMap.getInt(replacement.getAsString());
                    }
                }
                if (value == -1) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        if (replacement != null) {
                            ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + "/" + replacement.getAsString() + " :( ");
                        } else {
                            ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                        }
                    }
                }
            }
            output[Integer.parseInt(entry.getKey())] = hardId != -1 ? hardId : (short) value;
        }
    }

    @Override // com.viaversion.viabackwards.api.data.BackwardsMappings, com.viaversion.viaversion.api.data.MappingDataBase
    public Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (key.equals("blockstates")) {
            int[] oldToNew = new int[8582];
            Arrays.fill(oldToNew, -1);
            mapIdentifiers(oldToNew, oldMappings.getAsJsonObject("blockstates"), newMappings.getAsJsonObject("blocks"), diffMappings.getAsJsonObject("blockstates"));
            return IntArrayMappings.m234of(oldToNew, -1);
        }
        return super.loadFromObject(oldMappings, newMappings, diffMappings, key);
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase, com.viaversion.viaversion.api.data.MappingData
    public int getNewBlockStateId(int id) {
        int mappedId = super.getNewBlockStateId(id);
        switch (mappedId) {
            case 1595:
            case 1596:
            case 1597:
                return 1584;
            case 1598:
            case 1599:
            case 1600:
            case 1601:
            case 1602:
            case 1603:
            case 1604:
            case 1605:
            case 1606:
            case 1607:
            case 1608:
            case 1609:
            case 1610:
            default:
                return mappedId;
            case 1611:
            case 1612:
            case 1613:
                return 1600;
        }
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public int checkValidity(int id, int mappedId, String type) {
        return mappedId;
    }

    @Override // com.viaversion.viabackwards.api.data.BackwardsMappings
    public boolean shouldWarnOnMissing(String key) {
        return super.shouldWarnOnMissing(key) && !key.equals("items");
    }

    public Int2ObjectMap<String> getStatisticMappings() {
        return this.statisticMappings;
    }

    public Map<String, String> getTranslateMappings() {
        return this.translateMappings;
    }

    public Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }
}
