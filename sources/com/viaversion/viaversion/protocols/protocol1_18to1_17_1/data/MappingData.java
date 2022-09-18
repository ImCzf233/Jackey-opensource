package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data;

import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/data/MappingData.class */
public final class MappingData extends MappingDataBase {
    private final Object2IntMap<String> blockEntityIds = new Object2IntOpenHashMap();

    public MappingData() {
        super("1.17", "1.18", true);
        this.blockEntityIds.defaultReturnValue(-1);
    }

    @Override // com.viaversion.viaversion.api.data.MappingDataBase
    protected void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
        int i = 0;
        Iterator<JsonElement> it = newMappings.getAsJsonArray("blockentities").iterator();
        while (it.hasNext()) {
            JsonElement element = it.next();
            String id = element.getAsString();
            int i2 = i;
            i++;
            this.blockEntityIds.put((Object2IntMap<String>) id, i2);
        }
    }

    public Object2IntMap<String> blockEntityIds() {
        return this.blockEntityIds;
    }
}
