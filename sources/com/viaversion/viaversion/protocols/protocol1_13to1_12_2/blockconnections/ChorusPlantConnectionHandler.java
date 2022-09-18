package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ChorusPlantConnectionHandler.class */
public class ChorusPlantConnectionHandler extends AbstractFenceConnectionHandler {
    private final int endstone = ConnectionData.getId("minecraft:end_stone");

    public static List<ConnectionData.ConnectorInitAction> init() {
        List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(2);
        ChorusPlantConnectionHandler handler = new ChorusPlantConnectionHandler();
        actions.add(handler.getInitAction("minecraft:chorus_plant"));
        actions.add(handler.getExtraAction());
        return actions;
    }

    public ChorusPlantConnectionHandler() {
        super(null);
    }

    public ConnectionData.ConnectorInitAction getExtraAction() {
        return blockData -> {
            if (blockData.getMinecraftKey().equals("minecraft:chorus_flower")) {
                getBlockStates().add(Integer.valueOf(blockData.getSavedBlockStateId()));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler
    public byte getStates(WrappedBlockData blockData) {
        byte states = super.getStates(blockData);
        if (blockData.getValue("up").equals("true")) {
            states = (byte) (states | 16);
        }
        if (blockData.getValue("down").equals("true")) {
            states = (byte) (states | 32);
        }
        return states;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler
    public byte getStates(UserConnection user, Position position, int blockState) {
        byte states = super.getStates(user, position, blockState);
        if (connects(BlockFace.TOP, getBlockData(user, position.getRelative(BlockFace.TOP)), false)) {
            states = (byte) (states | 16);
        }
        if (connects(BlockFace.BOTTOM, getBlockData(user, position.getRelative(BlockFace.BOTTOM)), false)) {
            states = (byte) (states | 32);
        }
        return states;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler
    protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
        return getBlockStates().contains(Integer.valueOf(blockState)) || (side == BlockFace.BOTTOM && blockState == this.endstone);
    }
}
