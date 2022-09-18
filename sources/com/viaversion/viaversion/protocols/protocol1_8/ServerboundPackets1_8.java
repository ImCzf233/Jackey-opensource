package com.viaversion.viaversion.protocols.protocol1_8;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_8/ServerboundPackets1_8.class */
public enum ServerboundPackets1_8 implements ServerboundPacketType {
    KEEP_ALIVE,
    CHAT_MESSAGE,
    INTERACT_ENTITY,
    PLAYER_MOVEMENT,
    PLAYER_POSITION,
    PLAYER_ROTATION,
    PLAYER_POSITION_AND_ROTATION,
    PLAYER_DIGGING,
    PLAYER_BLOCK_PLACEMENT,
    HELD_ITEM_CHANGE,
    ANIMATION,
    ENTITY_ACTION,
    STEER_VEHICLE,
    CLOSE_WINDOW,
    CLICK_WINDOW,
    WINDOW_CONFIRMATION,
    CREATIVE_INVENTORY_ACTION,
    CLICK_WINDOW_BUTTON,
    UPDATE_SIGN,
    PLAYER_ABILITIES,
    TAB_COMPLETE,
    CLIENT_SETTINGS,
    CLIENT_STATUS,
    PLUGIN_MESSAGE,
    SPECTATE,
    RESOURCE_PACK_STATUS;

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public int getId() {
        return ordinal();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public String getName() {
        return name();
    }
}
