package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/WallConnectionHandler.class */
public class WallConnectionHandler extends AbstractFenceConnectionHandler {
    private static final BlockFace[] BLOCK_FACES = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private static final int[] OPPOSITES = {3, 2, 1, 0};

    public static List<ConnectionData.ConnectorInitAction> init() {
        List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(2);
        actions.add(new WallConnectionHandler("cobbleWallConnections").getInitAction("minecraft:cobblestone_wall"));
        actions.add(new WallConnectionHandler("cobbleWallConnections").getInitAction("minecraft:mossy_cobblestone_wall"));
        return actions;
    }

    public WallConnectionHandler(String blockConnections) {
        super(blockConnections);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler
    public byte getStates(WrappedBlockData blockData) {
        byte states = super.getStates(blockData);
        if (blockData.getValue("up").equals("true")) {
            states = (byte) (states | 16);
        }
        return states;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler
    public byte getStates(UserConnection user, Position position, int blockState) {
        byte states = super.getStates(user, position, blockState);
        if (m78up(user, position)) {
            states = (byte) (states | 16);
        }
        return states;
    }

    /* renamed from: up */
    public boolean m78up(UserConnection user, Position position) {
        int blockFaces;
        if (isWall(getBlockData(user, position.getRelative(BlockFace.BOTTOM))) || isWall(getBlockData(user, position.getRelative(BlockFace.TOP))) || (blockFaces = getBlockFaces(user, position)) == 0 || blockFaces == 15) {
            return true;
        }
        for (int i = 0; i < BLOCK_FACES.length; i++) {
            if ((blockFaces & (1 << i)) != 0 && (blockFaces & (1 << OPPOSITES[i])) == 0) {
                return true;
            }
        }
        return false;
    }

    private int getBlockFaces(UserConnection user, Position position) {
        int blockFaces = 0;
        for (int i = 0; i < BLOCK_FACES.length; i++) {
            if (isWall(getBlockData(user, position.getRelative(BLOCK_FACES[i])))) {
                blockFaces |= 1 << i;
            }
        }
        return blockFaces;
    }

    private boolean isWall(int id) {
        return getBlockStates().contains(Integer.valueOf(id));
    }
}
