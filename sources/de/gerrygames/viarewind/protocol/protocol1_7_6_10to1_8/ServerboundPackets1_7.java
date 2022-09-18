package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/ServerboundPackets1_7.class */
public enum ServerboundPackets1_7 implements ServerboundPacketType {
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
    PLUGIN_MESSAGE;

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public int getId() {
        return ordinal();
    }

    @Override // com.viaversion.viaversion.api.protocol.packet.PacketType
    public String getName() {
        return name();
    }
}
