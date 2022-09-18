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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/AbstractStempConnectionHandler.class */
public abstract class AbstractStempConnectionHandler extends ConnectionHandler {
    private static final BlockFace[] BLOCK_FACES = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private final int baseStateId;
    private final Set<Integer> blockId = new HashSet();
    private final Map<BlockFace, Integer> stemps = new HashMap();

    public AbstractStempConnectionHandler(String baseStateId) {
        this.baseStateId = ConnectionData.getId(baseStateId);
    }

    public ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
        return blockData -> {
            if (toKey.getSavedBlockStateId() == this.baseStateId || blockId.equals(toKey.getMinecraftKey())) {
                if (toKey.getSavedBlockStateId() != this.baseStateId) {
                    blockId.blockId.add(Integer.valueOf(toKey.getSavedBlockStateId()));
                }
                ConnectionData.connectionHandlerMap.put(toKey.getSavedBlockStateId(), (int) blockId);
            }
            if (toKey.getMinecraftKey().equals(this)) {
                String facing = toKey.getValue("facing").toUpperCase(Locale.ROOT);
                this.stemps.put(BlockFace.valueOf(facing), Integer.valueOf(toKey.getSavedBlockStateId()));
            }
        };
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler
    public int connect(UserConnection user, Position position, int blockState) {
        BlockFace[] blockFaceArr;
        if (blockState != this.baseStateId) {
            return blockState;
        }
        for (BlockFace blockFace : BLOCK_FACES) {
            if (this.blockId.contains(Integer.valueOf(getBlockData(user, position.getRelative(blockFace))))) {
                return this.stemps.get(blockFace).intValue();
            }
        }
        return this.baseStateId;
    }
}
