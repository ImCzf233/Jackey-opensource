package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/ServerboundPackets1_9.class */
public enum ServerboundPackets1_9 implements ServerboundPacketType {
    TELEPORT_CONFIRM,
    TAB_COMPLETE,
    CHAT_MESSAGE,
    CLIENT_STATUS,
    CLIENT_SETTINGS,
    WINDOW_CONFIRMATION,
    CLICK_WINDOW_BUTTON,
    CLICK_WINDOW,
    CLOSE_WINDOW,
    PLUGIN_MESSAGE,
    INTERACT_ENTITY,
    KEEP_ALIVE,
    PLAYER_POSITION,
    PLAYER_POSITION_AND_ROTATION,
    PLAYER_ROTATION,
    PLAYER_MOVEMENT,
    VEHICLE_MOVE,
    STEER_BOAT,
    PLAYER_ABILITIES,
    PLAYER_DIGGING,
    ENTITY_ACTION,
    STEER_VEHICLE,
    RESOURCE_PACK_STATUS,
    HELD_ITEM_CHANGE,
    CREATIVE_INVENTORY_ACTION,
    UPDATE_SIGN,
    ANIMATION,
    SPECTATE,
    PLAYER_BLOCK_PLACEMENT,
    USE_ITEM;

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public int getId() {
        return ordinal();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public String getName() {
        return name();
    }
}
