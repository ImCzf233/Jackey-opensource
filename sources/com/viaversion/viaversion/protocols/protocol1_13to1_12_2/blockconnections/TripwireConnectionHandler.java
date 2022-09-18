package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/TripwireConnectionHandler.class */
public class TripwireConnectionHandler extends ConnectionHandler {
    private static final Map<Integer, TripwireData> tripwireDataMap = new HashMap();
    private static final Map<Byte, Integer> connectedBlocks = new HashMap();
    private static final Map<Integer, BlockFace> tripwireHooks = new HashMap();

    public static ConnectionData.ConnectorInitAction init() {
        TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
        return blockData -> {
            if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
                tripwireHooks.put(Integer.valueOf(blockData.getSavedBlockStateId()), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
            } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
                TripwireData tripwireData = new TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"));
                tripwireDataMap.put(Integer.valueOf(blockData.getSavedBlockStateId()), tripwireData);
                connectedBlocks.put(Byte.valueOf(getStates(blockData)), Integer.valueOf(blockData.getSavedBlockStateId()));
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) connectionHandler);
            }
        };
    }

    private static byte getStates(WrappedBlockData blockData) {
        byte b = 0;
        if (blockData.getValue("attached").equals("true")) {
            b = (byte) (0 | 1);
        }
        if (blockData.getValue("disarmed").equals("true")) {
            b = (byte) (b | 2);
        }
        if (blockData.getValue("powered").equals("true")) {
            b = (byte) (b | 4);
        }
        if (blockData.getValue("east").equals("true")) {
            b = (byte) (b | 8);
        }
        if (blockData.getValue("north").equals("true")) {
            b = (byte) (b | 16);
        }
        if (blockData.getValue("south").equals("true")) {
            b = (byte) (b | 32);
        }
        if (blockData.getValue("west").equals("true")) {
            b = (byte) (b | 64);
        }
        return b;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        TripwireData tripwireData = tripwireDataMap.get(Integer.valueOf(blockState));
        if (tripwireData == null) {
            return blockState;
        }
        byte b = 0;
        if (tripwireData.isAttached()) {
            b = (byte) (0 | 1);
        }
        if (tripwireData.isDisarmed()) {
            b = (byte) (b | 2);
        }
        if (tripwireData.isPowered()) {
            b = (byte) (b | 4);
        }
        int east = getBlockData(user, position.getRelative(BlockFace.EAST));
        int north = getBlockData(user, position.getRelative(BlockFace.NORTH));
        int south = getBlockData(user, position.getRelative(BlockFace.SOUTH));
        int west = getBlockData(user, position.getRelative(BlockFace.WEST));
        if (tripwireDataMap.containsKey(Integer.valueOf(east)) || tripwireHooks.get(Integer.valueOf(east)) == BlockFace.WEST) {
            b = (byte) (b | 8);
        }
        if (tripwireDataMap.containsKey(Integer.valueOf(north)) || tripwireHooks.get(Integer.valueOf(north)) == BlockFace.SOUTH) {
            b = (byte) (b | 16);
        }
        if (tripwireDataMap.containsKey(Integer.valueOf(south)) || tripwireHooks.get(Integer.valueOf(south)) == BlockFace.NORTH) {
            b = (byte) (b | 32);
        }
        if (tripwireDataMap.containsKey(Integer.valueOf(west)) || tripwireHooks.get(Integer.valueOf(west)) == BlockFace.EAST) {
            b = (byte) (b | 64);
        }
        Integer newBlockState = connectedBlocks.get(Byte.valueOf(b));
        return newBlockState == null ? blockState : newBlockState.intValue();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/TripwireConnectionHandler$TripwireData.class */
    public static final class TripwireData {
        private final boolean attached;
        private final boolean disarmed;
        private final boolean powered;

        private TripwireData(boolean attached, boolean disarmed, boolean powered) {
            this.attached = attached;
            this.disarmed = disarmed;
            this.powered = powered;
        }

        public boolean isAttached() {
            return this.attached;
        }

        public boolean isDisarmed() {
            return this.disarmed;
        }

        public boolean isPowered() {
            return this.powered;
        }
    }
}
