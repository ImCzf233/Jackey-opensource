package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler.class */
public class StairConnectionHandler extends ConnectionHandler {
    private static final Map<Integer, StairData> stairDataMap = new HashMap();
    private static final Map<Short, Integer> connectedBlocks = new HashMap();

    public static ConnectionData.ConnectorInitAction init() {
        List<String> baseStairs = new LinkedList<>();
        baseStairs.add("minecraft:oak_stairs");
        baseStairs.add("minecraft:cobblestone_stairs");
        baseStairs.add("minecraft:brick_stairs");
        baseStairs.add("minecraft:stone_brick_stairs");
        baseStairs.add("minecraft:nether_brick_stairs");
        baseStairs.add("minecraft:sandstone_stairs");
        baseStairs.add("minecraft:spruce_stairs");
        baseStairs.add("minecraft:birch_stairs");
        baseStairs.add("minecraft:jungle_stairs");
        baseStairs.add("minecraft:quartz_stairs");
        baseStairs.add("minecraft:acacia_stairs");
        baseStairs.add("minecraft:dark_oak_stairs");
        baseStairs.add("minecraft:red_sandstone_stairs");
        baseStairs.add("minecraft:purpur_stairs");
        baseStairs.add("minecraft:prismarine_stairs");
        baseStairs.add("minecraft:prismarine_brick_stairs");
        baseStairs.add("minecraft:dark_prismarine_stairs");
        StairConnectionHandler connectionHandler = new StairConnectionHandler();
        return blockData -> {
            byte shape;
            int type = baseStairs.indexOf(blockData.getMinecraftKey());
            if (type != -1 && !blockData.getValue("waterlogged").equals("true")) {
                String value = blockData.getValue("shape");
                boolean z = true;
                switch (value.hashCode()) {
                    case -1766998696:
                        if (value.equals("outer_right")) {
                            z = true;
                            break;
                        }
                        break;
                    case -239805709:
                        if (value.equals("inner_right")) {
                            z = true;
                            break;
                        }
                        break;
                    case 823365712:
                        if (value.equals("inner_left")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1743932747:
                        if (value.equals("outer_left")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1787472634:
                        if (value.equals("straight")) {
                            z = false;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        shape = 0;
                        break;
                    case true:
                        shape = 1;
                        break;
                    case true:
                        shape = 2;
                        break;
                    case true:
                        shape = 3;
                        break;
                    case true:
                        shape = 4;
                        break;
                    default:
                        return;
                }
                StairData stairData = new StairData(blockData.getValue("half").equals("bottom"), shape, (byte) type, BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
                stairDataMap.put(Integer.valueOf(blockData.getSavedBlockStateId()), stairData);
                connectedBlocks.put(Short.valueOf(getStates(stairData)), Integer.valueOf(blockData.getSavedBlockStateId()));
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) connectionHandler);
            }
        };
    }

    private static short getStates(StairData stairData) {
        short s = 0;
        if (stairData.isBottom()) {
            s = (short) (0 | 1);
        }
        return (short) (((short) (((short) (s | (stairData.getShape() << 1))) | (stairData.getType() << 4))) | (stairData.getFacing().ordinal() << 9));
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        StairData stairData = stairDataMap.get(Integer.valueOf(blockState));
        if (stairData == null) {
            return blockState;
        }
        short s = 0;
        if (stairData.isBottom()) {
            s = (short) (0 | 1);
        }
        Integer newBlockState = connectedBlocks.get(Short.valueOf((short) (((short) (((short) (s | (getShape(user, position, stairData) << 1))) | (stairData.getType() << 4))) | (stairData.getFacing().ordinal() << 9))));
        return newBlockState == null ? blockState : newBlockState.intValue();
    }

    private int getShape(UserConnection user, Position position, StairData stair) {
        BlockFace facing = stair.getFacing();
        StairData relativeStair = stairDataMap.get(Integer.valueOf(getBlockData(user, position.getRelative(facing))));
        if (relativeStair != null && relativeStair.isBottom() == stair.isBottom()) {
            BlockFace facing2 = relativeStair.getFacing();
            if (facing.axis() != facing2.axis() && checkOpposite(user, stair, position, facing2.opposite())) {
                return facing2 == rotateAntiClockwise(facing) ? 3 : 4;
            }
        }
        StairData relativeStair2 = stairDataMap.get(Integer.valueOf(getBlockData(user, position.getRelative(facing.opposite()))));
        if (relativeStair2 != null && relativeStair2.isBottom() == stair.isBottom()) {
            BlockFace facing22 = relativeStair2.getFacing();
            if (facing.axis() == facing22.axis() || !checkOpposite(user, stair, position, facing22)) {
                return 0;
            }
            return facing22 == rotateAntiClockwise(facing) ? 1 : 2;
        }
        return 0;
    }

    private boolean checkOpposite(UserConnection user, StairData stair, Position position, BlockFace face) {
        StairData relativeStair = stairDataMap.get(Integer.valueOf(getBlockData(user, position.getRelative(face))));
        return (relativeStair != null && relativeStair.getFacing() == stair.getFacing() && relativeStair.isBottom() == stair.isBottom()) ? false : true;
    }

    private BlockFace rotateAntiClockwise(BlockFace face) {
        switch (face) {
            case NORTH:
                return BlockFace.WEST;
            case SOUTH:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.NORTH;
            case WEST:
                return BlockFace.SOUTH;
            default:
                return face;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/StairConnectionHandler$StairData.class */
    public static final class StairData {
        private final boolean bottom;
        private final byte shape;
        private final byte type;
        private final BlockFace facing;

        private StairData(boolean bottom, byte shape, byte type, BlockFace facing) {
            this.bottom = bottom;
            this.shape = shape;
            this.type = type;
            this.facing = facing;
        }

        public boolean isBottom() {
            return this.bottom;
        }

        public byte getShape() {
            return this.shape;
        }

        public byte getType() {
            return this.type;
        }

        public BlockFace getFacing() {
            return this.facing;
        }
    }
}
