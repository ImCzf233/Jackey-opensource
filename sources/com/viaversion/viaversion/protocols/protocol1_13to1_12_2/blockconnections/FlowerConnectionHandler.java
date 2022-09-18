package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashSet;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/FlowerConnectionHandler.class */
public class FlowerConnectionHandler extends ConnectionHandler {
    private static final Int2IntMap flowers = new Int2IntOpenHashMap();

    public static ConnectionData.ConnectorInitAction init() {
        Set<String> baseFlower = new HashSet<>();
        baseFlower.add("minecraft:rose_bush");
        baseFlower.add("minecraft:sunflower");
        baseFlower.add("minecraft:peony");
        baseFlower.add("minecraft:tall_grass");
        baseFlower.add("minecraft:large_fern");
        baseFlower.add("minecraft:lilac");
        FlowerConnectionHandler handler = new FlowerConnectionHandler();
        return blockData -> {
            if (baseFlower.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) handler);
                if (blockData.getValue("half").equals("lower")) {
                    blockData.set("half", "upper");
                    flowers.put(blockData.getSavedBlockStateId(), blockData.getBlockStateId());
                }
            }
        };
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        int blockBelowId = getBlockData(user, position.getRelative(BlockFace.BOTTOM));
        int connectBelow = flowers.get(blockBelowId);
        if (connectBelow != 0) {
            int blockAboveId = getBlockData(user, position.getRelative(BlockFace.TOP));
            if (Via.getConfig().isStemWhenBlockAbove()) {
                if (blockAboveId == 0) {
                    return connectBelow;
                }
            } else if (!flowers.containsKey(blockAboveId)) {
                return connectBelow;
            }
        }
        return blockState;
    }
}
