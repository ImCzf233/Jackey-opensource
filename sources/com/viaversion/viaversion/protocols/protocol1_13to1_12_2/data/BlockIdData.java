package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/BlockIdData.class */
public class BlockIdData {
    public static final String[] PREVIOUS = new String[0];
    public static Map<String, String[]> blockIdMapping;
    public static Map<String, String[]> fallbackReverseMapping;
    public static Int2ObjectMap<String> numberIdToString;

    /* JADX WARN: Multi-variable type inference failed */
    public static void init() {
        String[] value;
        InputStream stream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");
        try {
            InputStreamReader reader = new InputStreamReader(stream);
            Map<String, String[]> map = (Map) GsonUtil.getGson().fromJson(reader, new TypeToken<Map<String, String[]>>() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData.1
            }.getType());
            blockIdMapping = new HashMap(map);
            fallbackReverseMapping = new HashMap();
            for (Map.Entry<String, String[]> entry : blockIdMapping.entrySet()) {
                for (String val : entry.getValue()) {
                    String[] previous = fallbackReverseMapping.get(val);
                    if (previous == null) {
                        previous = PREVIOUS;
                    }
                    fallbackReverseMapping.put(val, ObjectArrays.concat(previous, entry.getKey()));
                }
            }
            if (reader != null) {
                if (0 != 0) {
                    reader.close();
                } else {
                    reader.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream blockS = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");
        try {
            InputStreamReader blockR = new InputStreamReader(blockS);
            Map<Integer, String> map2 = (Map) GsonUtil.getGson().fromJson(blockR, new TypeToken<Map<Integer, String>>() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData.2
            }.getType());
            numberIdToString = new Int2ObjectOpenHashMap(map2);
            if (blockR != null) {
                if (0 != 0) {
                    blockR.close();
                } else {
                    blockR.close();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
