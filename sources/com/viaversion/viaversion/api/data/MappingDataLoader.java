package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/data/MappingDataLoader.class */
public class MappingDataLoader {
    private static final Map<String, JsonObject> MAPPINGS_CACHE = new ConcurrentHashMap();
    private static boolean cacheJsonMappings;

    public static boolean isCacheJsonMappings() {
        return cacheJsonMappings;
    }

    public static void enableMappingsCache() {
        cacheJsonMappings = true;
    }

    public static Map<String, JsonObject> getMappingsCache() {
        return MAPPINGS_CACHE;
    }

    public static JsonObject loadFromDataDir(String name) {
        File file = new File(Via.getPlatform().getDataFolder(), name);
        if (!file.exists()) {
            return loadData(name);
        }
        try {
            try {
                FileReader reader = new FileReader(file);
                Throwable th = null;
                try {
                    JsonObject jsonObject = (JsonObject) GsonUtil.getGson().fromJson((Reader) reader, (Class<Object>) JsonObject.class);
                    if (reader != null) {
                        if (0 != 0) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            reader.close();
                        }
                    }
                    return jsonObject;
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        if (reader != null) {
                            if (th3 != null) {
                                try {
                                    reader.close();
                                } catch (Throwable th5) {
                                    th3.addSuppressed(th5);
                                }
                            } else {
                                reader.close();
                            }
                        }
                        throw th4;
                    }
                }
            } catch (JsonIOException | IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (JsonSyntaxException e2) {
            Via.getPlatform().getLogger().warning(name + " is badly formatted!");
            e2.printStackTrace();
            return null;
        }
    }

    public static JsonObject loadData(String name) {
        return loadData(name, false);
    }

    public static JsonObject loadData(String name, boolean cacheIfEnabled) {
        JsonObject cached;
        if (cacheJsonMappings && (cached = MAPPINGS_CACHE.get(name)) != null) {
            return cached;
        }
        InputStream stream = getResource(name);
        if (stream == null) {
            return null;
        }
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            JsonObject object = (JsonObject) GsonUtil.getGson().fromJson((Reader) reader, (Class<Object>) JsonObject.class);
            if (cacheIfEnabled && cacheJsonMappings) {
                MAPPINGS_CACHE.put(name, object);
            }
            return object;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }

    public static void mapIdentifiers(Int2IntBiMap output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
            int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers, warnOnMissing);
            if (value != -1) {
                output.put(Integer.parseInt(entry.getKey()), value);
            }
        }
    }

    @Deprecated
    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers) {
        mapIdentifiers(output, oldIdentifiers, newIdentifiers, (JsonObject) null);
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        Object2IntMap<String> newIdentifierMap = indexedObjectToMap(newIdentifiers);
        for (Map.Entry<String, JsonElement> entry : oldIdentifiers.entrySet()) {
            int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers, warnOnMissing);
            if (value != -1) {
                output[Integer.parseInt(entry.getKey())] = value;
            }
        }
    }

    public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers) {
        mapIdentifiers(output, oldIdentifiers, newIdentifiers, diffIdentifiers, true);
    }

    private static int mapIdentifierEntry(Map.Entry<String, JsonElement> entry, Object2IntMap newIdentifierMap, JsonObject diffIdentifiers, boolean warnOnMissing) {
        JsonElement diffElement;
        int value = newIdentifierMap.getInt(entry.getValue().getAsString());
        if (value == -1) {
            if (diffIdentifiers != null && (diffElement = diffIdentifiers.get(entry.getKey())) != null) {
                value = newIdentifierMap.getInt(diffElement.getAsString());
            }
            if (value == -1) {
                if ((warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
                    return -1;
                }
                return -1;
            }
        }
        return value;
    }

    @Deprecated
    public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, boolean warnOnMissing) {
        mapIdentifiers(output, oldIdentifiers, newIdentifiers, (JsonObject) null, warnOnMissing);
    }

    public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
        JsonElement diffElement;
        Object2IntMap<String> newIdentifierMap = arrayToMap(newIdentifiers);
        for (int i = 0; i < oldIdentifiers.size(); i++) {
            JsonElement oldIdentifier = oldIdentifiers.get(i);
            int mappedId = newIdentifierMap.getInt(oldIdentifier.getAsString());
            if (mappedId == -1) {
                if (diffIdentifiers != null && (diffElement = diffIdentifiers.get(oldIdentifier.getAsString())) != null) {
                    String mappedName = diffElement.getAsString();
                    if (!mappedName.isEmpty()) {
                        mappedId = newIdentifierMap.getInt(mappedName);
                    }
                }
                if (mappedId == -1) {
                    if ((warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug()) {
                        Via.getPlatform().getLogger().warning("No key for " + oldIdentifier + " :( ");
                    }
                }
            }
            output[i] = mappedId;
        }
    }

    public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
        Object2IntMap<String> map = new Object2IntOpenHashMap<>(object.size(), 0.99f);
        map.defaultReturnValue(-1);
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            map.put((Object2IntMap<String>) entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
        }
        return map;
    }

    public static Object2IntMap<String> arrayToMap(JsonArray array) {
        Object2IntMap<String> map = new Object2IntOpenHashMap<>(array.size(), 0.99f);
        map.defaultReturnValue(-1);
        for (int i = 0; i < array.size(); i++) {
            map.put((Object2IntMap<String>) array.get(i).getAsString(), i);
        }
        return map;
    }

    public static InputStream getResource(String name) {
        return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
    }
}
