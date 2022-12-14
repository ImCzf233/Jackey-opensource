package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/SnowyGrassConnectionHandler.class */
public class SnowyGrassConnectionHandler extends ConnectionHandler {
    private static final Map<Pair<Integer, Boolean>, Integer> grassBlocks = new HashMap();
    private static final Set<Integer> snows = new HashSet();

    public static ConnectionData.ConnectorInitAction init() {
        Set<String> snowyGrassBlocks = new HashSet<>();
        snowyGrassBlocks.add("minecraft:grass_block");
        snowyGrassBlocks.add("minecraft:podzol");
        snowyGrassBlocks.add("minecraft:mycelium");
        SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
        return blockData -> {
            if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) handler);
                blockData.set("snowy", "true");
                grassBlocks.put(new Pair<>(Integer.valueOf(blockData.getSavedBlockStateId()), true), Integer.valueOf(blockData.getBlockStateId()));
                blockData.set("snowy", "false");
                grassBlocks.put(new Pair<>(Integer.valueOf(blockData.getSavedBlockStateId()), false), Integer.valueOf(blockData.getBlockStateId()));
            }
            if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) handler);
                snows.add(Integer.valueOf(blockData.getSavedBlockStateId()));
            }
        };
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        int blockUpId = getBlockData(user, position.getRelative(BlockFace.TOP));
        Integer newId = grassBlocks.get(new Pair(Integer.valueOf(blockState), Boolean.valueOf(snows.contains(Integer.valueOf(blockUpId)))));
        if (newId != null) {
            return newId.intValue();
        }
        return blockState;
    }
}
