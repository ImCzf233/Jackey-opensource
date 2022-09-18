package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_10to1_11/packets/PlayerPackets1_11.class */
public class PlayerPackets1_11 {
    private static final ValueTransformer<Short, Float> TO_NEW_FLOAT = new ValueTransformer<Short, Float>(Type.FLOAT) { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11.1
        public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return Float.valueOf(inputValue.shortValue() / 15.0f);
        }
    };

    public void register(Protocol1_10To1_11 protocol) {
        protocol.registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.TITLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    if (action != 2) {
                        if (action > 2) {
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(action - 1));
                            return;
                        }
                        return;
                    }
                    JsonElement message = (JsonElement) wrapper.read(Type.COMPONENT);
                    wrapper.clearPacket();
                    wrapper.setId(ClientboundPackets1_9_3.CHAT_MESSAGE.ordinal());
                    String legacy = LegacyComponentSerializer.legacySection().serialize(GsonComponentSerializer.gson().deserialize(message.toString()));
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.getAsJsonObject().addProperty("text", legacy);
                    wrapper.write(Type.COMPONENT, jsonObject);
                    wrapper.write(Type.BYTE, (byte) 2);
                });
            }
        });
        protocol.registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.read(Type.VAR_INT);
                });
            }
        });
        protocol.registerServerbound((Protocol1_10To1_11) ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
                map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
                map(Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
            }
        });
    }
}
