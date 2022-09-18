package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/data/VBMappingDataLoader.class */
public final class VBMappingDataLoader {
    public static JsonObject loadFromDataDir(String name) {
        File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return loadData(name);
        }
        try {
            try {
                FileReader reader = new FileReader(file);
                try {
                    JsonObject jsonObject = (JsonObject) GsonUtil.getGson().fromJson((Reader) reader, (Class<Object>) JsonObject.class);
                    reader.close();
                    return jsonObject;
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (JsonIOException | IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (JsonSyntaxException e2) {
            ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
            e2.printStackTrace();
            ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
            return loadData(name);
        }
    }

    public static JsonObject loadData(String name) {
        InputStream stream = VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);
        try {
            InputStreamReader reader = new InputStreamReader(stream);
            JsonObject jsonObject = (JsonObject) GsonUtil.getGson().fromJson((Reader) reader, (Class<Object>) JsonObject.class);
            reader.close();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        int dataIndex;
        JsonPrimitive diffValueJson;
        Object2IntMap<String> newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
            String key = entry.getValue().getAsString();
            int mappedId = newIdentifierMap.getInt(key);
            if (mappedId == -1) {
                if (diffIdentifiers != null) {
                    JsonPrimitive diffValueJson2 = diffIdentifiers.getAsJsonPrimitive(key);
                    String diffValue = diffValueJson2 != null ? diffValueJson2.getAsString() : null;
                    if (diffValue == null && (dataIndex = key.indexOf(91)) != -1 && (diffValueJson = diffIdentifiers.getAsJsonPrimitive(key.substring(0, dataIndex))) != null) {
                        diffValue = diffValueJson.getAsString();
                        if (diffValue.endsWith("[")) {
                            diffValue = diffValue + key.substring(dataIndex + 1);
                        }
                    }
                    if (diffValue != null) {
                        mappedId = newIdentifierMap.getInt(diffValue);
                    }
                }
                if (mappedId == -1) {
                    if ((warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                    }
                }
            }
            output[Integer.parseInt(entry.getKey())] = mappedId;
        }
    }

    public static Map<String, String> objectToNamespacedMap(JsonObject object) {
        Map<String, String> mappings = new HashMap<>(object.size());
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String key = entry.getKey();
            if (key.indexOf(58) == -1) {
                key = "minecraft:" + key;
            }
            String value = entry.getValue().getAsString();
            if (value.indexOf(58) == -1) {
                value = "minecraft:" + value;
            }
            mappings.put(key, value);
        }
        return mappings;
    }

    public static Map<String, String> objectToMap(JsonObject object) {
        Map<String, String> mappings = new HashMap<>(object.size());
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            mappings.put(entry.getKey(), entry.getValue().getAsString());
        }
        return mappings;
    }

    public static Int2ObjectMap<MappedItem> loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping, boolean warnOnMissing) {
        Int2ObjectMap<MappedItem> itemMapping = new Int2ObjectOpenHashMap<>(diffMapping.size(), 0.99f);
        Object2IntMap<String> newIdenfierMap = MappingDataLoader.indexedObjectToMap(newMapping);
        Object2IntMap<String> oldIdenfierMap = MappingDataLoader.indexedObjectToMap(oldMapping);
        for (Map.Entry<String, JsonElement> entry : diffMapping.entrySet()) {
            JsonObject object = entry.getValue().getAsJsonObject();
            String mappedIdName = object.getAsJsonPrimitive("id").getAsString();
            int mappedId = newIdenfierMap.getInt(mappedIdName);
            if (mappedId == -1) {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    ViaBackwards.getPlatform().getLogger().warning("No key for " + mappedIdName + " :( ");
                }
            } else {
                int oldId = oldIdenfierMap.getInt(entry.getKey());
                if (oldId == -1) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("No old entry for " + mappedIdName + " :( ");
                    }
                } else {
                    String name = object.getAsJsonPrimitive("name").getAsString();
                    itemMapping.put(oldId, (int) new MappedItem(mappedId, name));
                }
            }
        }
        if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) {
            ObjectIterator<Object2IntMap.Entry<String>> it = oldIdenfierMap.object2IntEntrySet().iterator();
            while (it.hasNext()) {
                Object2IntMap.Entry<String> entry2 = it.next();
                if (!newIdenfierMap.containsKey(entry2.getKey()) && !itemMapping.containsKey(entry2.getIntValue())) {
                    ViaBackwards.getPlatform().getLogger().warning("No item mapping for " + entry2.getKey() + " :( ");
                }
            }
        }
        return itemMapping;
    }
}
