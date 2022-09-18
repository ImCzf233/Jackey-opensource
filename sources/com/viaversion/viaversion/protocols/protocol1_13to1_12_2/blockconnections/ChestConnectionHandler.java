package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ChestConnectionHandler.class */
public class ChestConnectionHandler extends ConnectionHandler {
    private static final Map<Integer, BlockFace> chestFacings = new HashMap();
    private static final Map<Byte, Integer> connectedStates = new HashMap();
    private static final Set<Integer> trappedChests = new HashSet();

    ChestConnectionHandler() {
    }

    public static ConnectionData.ConnectorInitAction init() {
        ChestConnectionHandler connectionHandler = new ChestConnectionHandler();
        return blockData -> {
            if ((blockData.getMinecraftKey().equals("minecraft:chest") || blockData.getMinecraftKey().equals("minecraft:trapped_chest")) && !blockData.getValue("waterlogged").equals("true")) {
                chestFacings.put(Integer.valueOf(blockData.getSavedBlockStateId()), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
                if (blockData.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
                    trappedChests.add(Integer.valueOf(blockData.getSavedBlockStateId()));
                }
                connectedStates.put(getStates(blockData), Integer.valueOf(blockData.getSavedBlockStateId()));
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) connectionHandler);
            }
        };
    }

    private static Byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        String type = blockData.getValue("type");
        if (type.equals("left")) {
            states = (byte) (0 | 1);
        }
        if (type.equals("right")) {
            states = (byte) (states | 2);
        }
        byte states2 = (byte) (states | (BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2));
        if (blockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
            states2 = (byte) (states2 | 16);
        }
        return Byte.valueOf(states2);
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        BlockFace facing = chestFacings.get(Integer.valueOf(blockState));
        byte states = (byte) (0 | (facing.ordinal() << 2));
        boolean trapped = trappedChests.contains(Integer.valueOf(blockState));
        if (trapped) {
            states = (byte) (states | 16);
        }
        Map<Integer, BlockFace> map = chestFacings;
        int relative = getBlockData(user, position.getRelative(BlockFace.NORTH));
        if (!map.containsKey(Integer.valueOf(relative)) || trapped != trappedChests.contains(Integer.valueOf(relative))) {
            Map<Integer, BlockFace> map2 = chestFacings;
            int relative2 = getBlockData(user, position.getRelative(BlockFace.SOUTH));
            if (!map2.containsKey(Integer.valueOf(relative2)) || trapped != trappedChests.contains(Integer.valueOf(relative2))) {
                Map<Integer, BlockFace> map3 = chestFacings;
                int relative3 = getBlockData(user, position.getRelative(BlockFace.WEST));
                if (!map3.containsKey(Integer.valueOf(relative3)) || trapped != trappedChests.contains(Integer.valueOf(relative3))) {
                    Map<Integer, BlockFace> map4 = chestFacings;
                    int relative4 = getBlockData(user, position.getRelative(BlockFace.EAST));
                    if (map4.containsKey(Integer.valueOf(relative4)) && trapped == trappedChests.contains(Integer.valueOf(relative4))) {
                        states = (byte) (states | (facing == BlockFace.SOUTH ? (byte) 2 : (byte) 1));
                    }
                } else {
                    states = (byte) (states | (facing == BlockFace.NORTH ? (byte) 2 : (byte) 1));
                }
            } else {
                states = (byte) (states | (facing == BlockFace.EAST ? (byte) 1 : (byte) 2));
            }
        } else {
            states = (byte) (states | (facing == BlockFace.WEST ? (byte) 1 : (byte) 2));
        }
        Integer newBlockState = connectedStates.get(Byte.valueOf(states));
        return newBlockState == null ? blockState : newBlockState.intValue();
    }
}
