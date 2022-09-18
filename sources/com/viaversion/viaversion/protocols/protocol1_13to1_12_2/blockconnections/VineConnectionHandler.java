package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashSet;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/VineConnectionHandler.class */
public class VineConnectionHandler extends ConnectionHandler {
    private static final Set<Integer> vines = new HashSet();

    VineConnectionHandler() {
    }

    public static ConnectionData.ConnectorInitAction init() {
        VineConnectionHandler connectionHandler = new VineConnectionHandler();
        return blockData -> {
            if (!blockData.getMinecraftKey().equals("minecraft:vine")) {
                return;
            }
            vines.add(Integer.valueOf(blockData.getSavedBlockStateId()));
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (int) connectionHandler);
        };
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        if (isAttachedToBlock(user, position)) {
            return blockState;
        }
        Position upperPos = position.getRelative(BlockFace.TOP);
        int upperBlock = getBlockData(user, upperPos);
        if (vines.contains(Integer.valueOf(upperBlock)) && isAttachedToBlock(user, upperPos)) {
            return blockState;
        }
        return 0;
    }

    private boolean isAttachedToBlock(UserConnection user, Position position) {
        return isAttachedToBlock(user, position, BlockFace.EAST) || isAttachedToBlock(user, position, BlockFace.WEST) || isAttachedToBlock(user, position, BlockFace.NORTH) || isAttachedToBlock(user, position, BlockFace.SOUTH);
    }

    private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
        return ConnectionData.occludingStates.contains(getBlockData(user, position.getRelative(blockFace)));
    }
}
