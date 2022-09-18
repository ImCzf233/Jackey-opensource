package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/block_entity_handlers/PistonHandler.class */
public class PistonHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private final Map<String, Integer> pistonIds = new HashMap();

    public PistonHandler() {
        if (Via.getConfig().isServersideBlockConnections()) {
            try {
                Field field = ConnectionData.class.getDeclaredField("keyToId");
                field.setAccessible(true);
                Map<String, Integer> keyToId = (Map) field.get(null);
                for (Map.Entry<String, Integer> entry : keyToId.entrySet()) {
                    if (entry.getKey().contains("piston")) {
                        addEntries(entry.getKey(), entry.getValue().intValue());
                    }
                }
                return;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return;
            }
        }
        JsonObject mappings = MappingDataLoader.getMappingsCache().get("mapping-1.13.json").getAsJsonObject("blockstates");
        for (Map.Entry<String, JsonElement> blockState : mappings.entrySet()) {
            String key = blockState.getValue().getAsString();
            if (key.contains("piston")) {
                addEntries(key, Integer.parseInt(blockState.getKey()));
            }
        }
    }

    private void addEntries(String data, int id) {
        int id2 = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(id);
        this.pistonIds.put(data, Integer.valueOf(id2));
        String substring = data.substring(10);
        if (substring.startsWith("piston") || substring.startsWith("sticky_piston")) {
            String[] split = data.substring(0, data.length() - 1).split("\\[");
            String[] properties = split[1].split(",");
            this.pistonIds.put(split[0] + "[" + properties[1] + "," + properties[0] + "]", Integer.valueOf(id2));
        }
    }

    @Override // com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        String dataFromTag;
        CompoundTag blockState = (CompoundTag) tag.get("blockState");
        if (blockState != null && (dataFromTag = getDataFromTag(blockState)) != null) {
            Integer id = this.pistonIds.get(dataFromTag);
            if (id == null) {
                return tag;
            }
            tag.put("blockId", new IntTag(id.intValue() >> 4));
            tag.put("blockData", new IntTag(id.intValue() & 15));
            return tag;
        }
        return tag;
    }

    private String getDataFromTag(CompoundTag tag) {
        StringTag name = (StringTag) tag.get("Name");
        if (name == null) {
            return null;
        }
        CompoundTag properties = (CompoundTag) tag.get("Properties");
        if (properties == null) {
            return name.getValue();
        }
        StringJoiner joiner = new StringJoiner(",", name.getValue() + "[", "]");
        Iterator<Map.Entry<String, Tag>> it = properties.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Tag> entry = it.next();
            if (entry.getValue() instanceof StringTag) {
                joiner.add(entry.getKey() + "=" + ((StringTag) entry.getValue()).getValue());
            }
        }
        return joiner.toString();
    }
}
