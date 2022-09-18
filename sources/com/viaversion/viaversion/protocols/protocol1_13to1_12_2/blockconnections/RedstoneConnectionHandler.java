package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashSet;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/RedstoneConnectionHandler.class */
public class RedstoneConnectionHandler extends ConnectionHandler {
    private static final Set<Integer> redstone = new HashSet();
    private static final Int2IntMap connectedBlockStates = new Int2IntOpenHashMap(1296);
    private static final Int2IntMap powerMappings = new Int2IntOpenHashMap(1296);

    public static ConnectionData.ConnectorInitAction init() {
        RedstoneConnectionHandler connectionHandler = new RedstoneConnectionHandler();
        return blockData -> {
            if (!"minecraft:redstone_wire".equals(blockData.getMinecraftKey())) {
                return;
            }
            redstone.add(Integer.valueOf(blockData.getSavedBlockStateId()));
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) connectionHandler);
            connectedBlockStates.put(getStates(blockData), blockData.getSavedBlockStateId());
            powerMappings.put(blockData.getSavedBlockStateId(), Integer.parseInt(blockData.getValue("power")));
        };
    }

    private static short getStates(WrappedBlockData data) {
        short b = (short) (0 | getState(data.getValue("east")));
        return (short) (((short) (((short) (((short) (b | (getState(data.getValue("north")) << 2))) | (getState(data.getValue("south")) << 4))) | (getState(data.getValue("west")) << 6))) | (Integer.parseInt(data.getValue("power")) << 8));
    }

    private static int getState(String value) {
        boolean z = true;
        switch (value.hashCode()) {
            case 3739:
                if (value.equals("up")) {
                    z = true;
                    break;
                }
                break;
            case 3387192:
                if (value.equals("none")) {
                    z = false;
                    break;
                }
                break;
            case 3530071:
                if (value.equals("side")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return 0;
            case true:
                return 1;
            case true:
                return 2;
            default:
                return 0;
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        short b = (short) (0 | connects(user, position, BlockFace.EAST));
        return connectedBlockStates.getOrDefault((short) (((short) (((short) (((short) (b | (connects(user, position, BlockFace.NORTH) << 2))) | (connects(user, position, BlockFace.SOUTH) << 4))) | (connects(user, position, BlockFace.WEST) << 6))) | (powerMappings.get(blockState) << 8)), blockState);
    }

    private int connects(UserConnection user, Position position, BlockFace side) {
        Position relative = position.getRelative(side);
        int blockState = getBlockData(user, relative);
        if (connects(side, blockState)) {
            return 1;
        }
        int up = getBlockData(user, relative.getRelative(BlockFace.TOP));
        if (redstone.contains(Integer.valueOf(up)) && !ConnectionData.occludingStates.contains(getBlockData(user, position.getRelative(BlockFace.TOP)))) {
            return 2;
        }
        int down = getBlockData(user, relative.getRelative(BlockFace.BOTTOM));
        if (redstone.contains(Integer.valueOf(down)) && !ConnectionData.occludingStates.contains(getBlockData(user, relative))) {
            return 1;
        }
        return 0;
    }

    private boolean connects(BlockFace side, int blockState) {
        BlockData blockData = ConnectionData.blockConnectionData.get(blockState);
        return blockData != null && blockData.connectsTo("redstoneConnections", side.opposite(), false);
    }
}
