package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.IntArrayMappings;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/MappingData.class */
public class MappingData extends MappingDataBase {
    private final Map<String, Integer[]> blockTags = new HashMap();
    private final Map<String, Integer[]> itemTags = new HashMap();
    private final Map<String, Integer[]> fluidTags = new HashMap();
    private final BiMap<Short, String> oldEnchantmentsIds = HashBiMap.create();
    private final Map<String, String> translateMapping = new HashMap();
    private final Map<String, String> mojangTranslation = new HashMap();
    private final BiMap<String, String> channelMappings = HashBiMap.create();
    private Mappings enchantmentMappings;

    public MappingData() {
        super("1.12", "1.13");
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
        loadTags(this.blockTags, newMappings.getAsJsonObject("block_tags"));
        loadTags(this.itemTags, newMappings.getAsJsonObject("item_tags"));
        loadTags(this.fluidTags, newMappings.getAsJsonObject("fluid_tags"));
        loadEnchantments(this.oldEnchantmentsIds, oldMappings.getAsJsonObject("enchantments"));
        this.enchantmentMappings = IntArrayMappings.builder().customEntrySize(72).unmapped(oldMappings.getAsJsonObject("enchantments")).mapped(newMappings.getAsJsonObject("enchantments")).build();
        if (Via.getConfig().isSnowCollisionFix()) {
            this.blockMappings.setNewId(1248, 3416);
        }
        if (Via.getConfig().isInfestedBlocksFix()) {
            this.blockMappings.setNewId(1552, 1);
            this.blockMappings.setNewId(1553, 14);
            this.blockMappings.setNewId(1554, 3983);
            this.blockMappings.setNewId(1555, 3984);
            this.blockMappings.setNewId(1556, 3985);
            this.blockMappings.setNewId(1557, 3986);
        }
        JsonObject object = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json");
        if (object != null) {
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                String oldChannel = entry.getKey();
                String newChannel = entry.getValue().getAsString();
                if (!isValid1_13Channel(newChannel)) {
                    Via.getPlatform().getLogger().warning("Channel '" + newChannel + "' is not a valid 1.13 plugin channel, please check your configuration!");
                } else {
                    this.channelMappings.put(oldChannel, newChannel);
                }
            }
        }
        Map<String, String> translateData = (Map) GsonUtil.getGson().fromJson(new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")), new TypeToken<Map<String, String>>() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData.1
        }.getType());
        try {
            Reader reader = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8);
            String[] lines = CharStreams.toString(reader).split("\n");
            if (reader != null) {
                if (0 != 0) {
                    reader.close();
                } else {
                    reader.close();
                }
            }
            for (String line : lines) {
                if (!line.isEmpty()) {
                    String[] keyAndTranslation = line.split("=", 2);
                    if (keyAndTranslation.length == 2) {
                        String key = keyAndTranslation[0];
                        if (!translateData.containsKey(key)) {
                            String translation = keyAndTranslation[1].replaceAll("%(\\d\\$)?d", "%$1s");
                            this.mojangTranslation.put(key, translation);
                        } else {
                            String dataValue = translateData.get(key);
                            if (dataValue != null) {
                                this.translateMapping.put(key, dataValue);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    public Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings, String key) {
        if (key.equals("blocks")) {
            return IntArrayMappings.builder().customEntrySize(4084).unmapped(oldMappings.getAsJsonObject("blocks")).mapped(newMappings.getAsJsonObject("blockstates")).build();
        }
        return super.loadFromObject(oldMappings, newMappings, diffMappings, key);
    }

    public static String validateNewChannel(String newId) {
        if (!isValid1_13Channel(newId)) {
            return null;
        }
        int separatorIndex = newId.indexOf(58);
        if (separatorIndex == -1) {
            return "minecraft:" + newId;
        }
        if (separatorIndex == 0) {
            return Key.MINECRAFT_NAMESPACE + newId;
        }
        return newId;
    }

    public static boolean isValid1_13Channel(String channelId) {
        return channelId.matches("([0-9a-z_.-]+:)?[0-9a-z_/.-]+");
    }

    private void loadTags(Map<String, Integer[]> output, JsonObject newTags) {
        for (Map.Entry<String, JsonElement> entry : newTags.entrySet()) {
            JsonArray ids = entry.getValue().getAsJsonArray();
            Integer[] idsArray = new Integer[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                idsArray[i] = Integer.valueOf(ids.get(i).getAsInt());
            }
            output.put(entry.getKey(), idsArray);
        }
    }

    private void loadEnchantments(Map<Short, String> output, JsonObject enchantments) {
        for (Map.Entry<String, JsonElement> enchantment : enchantments.entrySet()) {
            output.put(Short.valueOf(Short.parseShort(enchantment.getKey())), enchantment.getValue().getAsString());
        }
    }

    public Map<String, Integer[]> getBlockTags() {
        return this.blockTags;
    }

    public Map<String, Integer[]> getItemTags() {
        return this.itemTags;
    }

    public Map<String, Integer[]> getFluidTags() {
        return this.fluidTags;
    }

    public BiMap<Short, String> getOldEnchantmentsIds() {
        return this.oldEnchantmentsIds;
    }

    public Map<String, String> getTranslateMapping() {
        return this.translateMapping;
    }

    public Map<String, String> getMojangTranslation() {
        return this.mojangTranslation;
    }

    public BiMap<String, String> getChannelMappings() {
        return this.channelMappings;
    }

    public Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }
}
