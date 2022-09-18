package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/AbstractFenceConnectionHandler.class */
public abstract class AbstractFenceConnectionHandler extends ConnectionHandler {
    private static final StairConnectionHandler STAIR_CONNECTION_HANDLER = new StairConnectionHandler();
    private final String blockConnections;
    private final Set<Integer> blockStates = new HashSet();
    private final Map<Byte, Integer> connectedBlockStates = new HashMap();

    public AbstractFenceConnectionHandler(String blockConnections) {
        this.blockConnections = blockConnections;
    }

    public ConnectionData.ConnectorInitAction getInitAction(String key) {
        return blockData -> {
            if (key.equals(this.getMinecraftKey())) {
                if (this.hasData("waterlogged") && this.getValue("waterlogged").equals("true")) {
                    return;
                }
                this.blockStates.add(Integer.valueOf(this.getSavedBlockStateId()));
                ConnectionData.connectionHandlerMap.put(this.getSavedBlockStateId(), (int) key);
                this.connectedBlockStates.put(Byte.valueOf(getStates(this)), Integer.valueOf(this.getSavedBlockStateId()));
            }
        };
    }

    public byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        if (blockData.getValue("east").equals("true")) {
            states = (byte) (0 | 1);
        }
        if (blockData.getValue("north").equals("true")) {
            states = (byte) (states | 2);
        }
        if (blockData.getValue("south").equals("true")) {
            states = (byte) (states | 4);
        }
        if (blockData.getValue("west").equals("true")) {
            states = (byte) (states | 8);
        }
        return states;
    }

    public byte getStates(UserConnection user, Position position, int blockState) {
        byte states = 0;
        boolean pre1_12 = user.getProtocolInfo().getServerProtocolVersion() < ProtocolVersion.v1_12.getVersion();
        if (connects(BlockFace.EAST, getBlockData(user, position.getRelative(BlockFace.EAST)), pre1_12)) {
            states = (byte) (0 | 1);
        }
        if (connects(BlockFace.NORTH, getBlockData(user, position.getRelative(BlockFace.NORTH)), pre1_12)) {
            states = (byte) (states | 2);
        }
        if (connects(BlockFace.SOUTH, getBlockData(user, position.getRelative(BlockFace.SOUTH)), pre1_12)) {
            states = (byte) (states | 4);
        }
        if (connects(BlockFace.WEST, getBlockData(user, position.getRelative(BlockFace.WEST)), pre1_12)) {
            states = (byte) (states | 8);
        }
        return states;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int getBlockData(UserConnection user, Position position) {
        return STAIR_CONNECTION_HANDLER.connect(user, position, super.getBlockData(user, position));
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        Integer newBlockState = this.connectedBlockStates.get(Byte.valueOf(getStates(user, position, blockState)));
        return newBlockState == null ? blockState : newBlockState.intValue();
    }

    protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
        BlockData blockData;
        if (this.blockStates.contains(Integer.valueOf(blockState))) {
            return true;
        }
        return (this.blockConnections == null || (blockData = ConnectionData.blockConnectionData.get(blockState)) == null || !blockData.connectsTo(this.blockConnections, side.opposite(), pre1_12)) ? false : true;
    }

    public Set<Integer> getBlockStates() {
        return this.blockStates;
    }
}
