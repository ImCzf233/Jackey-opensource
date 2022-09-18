package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.PlayerMovementMapper;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/packets/PlayerPackets.class */
public class PlayerPackets {
    public static void register(Protocol1_9To1_8 protocol) {
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.BYTE);
                handler(wrapper -> {
                    try {
                        JsonObject obj = (JsonObject) wrapper.get(Type.COMPONENT, 0);
                        ChatRewriter.toClient(obj, wrapper.user());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.TAB_LIST, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.DISCONNECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.TITLE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    if (action == 0 || action == 1) {
                        Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.VAR_INT, 0);
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.TEAMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(wrapper -> {
                    byte mode = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                    if (mode == 0 || mode == 2) {
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.BYTE);
                        wrapper.passthrough(Type.STRING);
                        wrapper.write(Type.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                        wrapper.passthrough(Type.BYTE);
                    }
                    if (mode == 0 || mode == 3 || mode == 4) {
                        String[] players = (String[]) wrapper.passthrough(Type.STRING_ARRAY);
                        EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        String myName = wrapper.user().getProtocolInfo().getUsername();
                        String teamName = (String) wrapper.get(Type.STRING, 0);
                        for (String player : players) {
                            if (entityTracker.isAutoTeam() && player.equalsIgnoreCase(myName)) {
                                if (mode == 4) {
                                    wrapper.send(Protocol1_9To1_8.class);
                                    wrapper.cancel();
                                    entityTracker.sendTeamPacket(true, true);
                                    entityTracker.setCurrentTeam("viaversion");
                                } else {
                                    entityTracker.sendTeamPacket(false, true);
                                    entityTracker.setCurrentTeam(teamName);
                                }
                            }
                        }
                    }
                    if (mode == 1) {
                        EntityTracker1_9 entityTracker2 = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        String teamName2 = (String) wrapper.get(Type.STRING, 0);
                        if (entityTracker2.isAutoTeam() && teamName2.equals(entityTracker2.getCurrentTeam())) {
                            wrapper.send(Protocol1_9To1_8.class);
                            wrapper.cancel();
                            entityTracker2.sendTeamPacket(true, true);
                            entityTracker2.setCurrentTeam("viaversion");
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    tracker.addEntity(entityId, Entity1_10Types.EntityType.PLAYER);
                    tracker.setClientEntityId(entityId);
                });
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.BOOLEAN);
                handler(wrapper2 -> {
                    EntityTracker1_9 tracker = (EntityTracker1_9) wrapper2.user().getEntityTracker(Protocol1_9To1_8.class);
                    tracker.setGameMode(GameMode.getById(((Short) wrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue()));
                });
                handler(wrapper3 -> {
                    ClientWorld clientWorld = (ClientWorld) wrapper3.user().get(ClientWorld.class);
                    int dimensionId = ((Byte) wrapper3.get(Type.BYTE, 0)).byteValue();
                    clientWorld.setEnvironment(dimensionId);
                });
                handler(wrapper4 -> {
                    CommandBlockProvider provider = (CommandBlockProvider) Via.getManager().getProviders().get(CommandBlockProvider.class);
                    provider.sendPermission(wrapper4.user());
                });
                handler(wrapper5 -> {
                    EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper5.user().getEntityTracker(Protocol1_9To1_8.class);
                    if (Via.getConfig().isAutoTeam()) {
                        entityTracker.setAutoTeam(true);
                        wrapper5.send(Protocol1_9To1_8.class);
                        wrapper5.cancel();
                        entityTracker.sendTeamPacket(true, true);
                        entityTracker.setCurrentTeam("viaversion");
                        return;
                    }
                    entityTracker.setAutoTeam(false);
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    int count = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                    for (int i = 0; i < count; i++) {
                        wrapper.passthrough(Type.UUID);
                        if (action == 0) {
                            wrapper.passthrough(Type.STRING);
                            int properties = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            for (int j = 0; j < properties; j++) {
                                wrapper.passthrough(Type.STRING);
                                wrapper.passthrough(Type.STRING);
                                boolean isSigned = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (isSigned) {
                                    wrapper.passthrough(Type.STRING);
                                }
                            }
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.passthrough(Type.VAR_INT);
                            boolean hasDisplayName = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (hasDisplayName) {
                                Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
                            }
                        } else if (action == 1 || action == 2) {
                            wrapper.passthrough(Type.VAR_INT);
                        } else if (action == 3) {
                            boolean hasDisplayName2 = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (hasDisplayName2) {
                                Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
                            }
                        } else if (action == 4) {
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(wrapper -> {
                    String name = (String) wrapper.get(Type.STRING, 0);
                    if (name.equalsIgnoreCase("MC|BOpen")) {
                        wrapper.read(Type.REMAINING_BYTES);
                        wrapper.write(Type.VAR_INT, 0);
                    }
                    if (name.equalsIgnoreCase("MC|TrList")) {
                        wrapper.passthrough(Type.INT);
                        Short size = (Short) wrapper.passthrough(Type.UNSIGNED_BYTE);
                        for (int i = 0; i < size.shortValue(); i++) {
                            Item item1 = (Item) wrapper.passthrough(Type.ITEM);
                            ItemRewriter.toClient(item1);
                            Item item2 = (Item) wrapper.passthrough(Type.ITEM);
                            ItemRewriter.toClient(item2);
                            boolean present = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (present) {
                                Item item3 = (Item) wrapper.passthrough(Type.ITEM);
                                ItemRewriter.toClient(item3);
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
                handler(wrapper -> {
                    ((ClientChunks) wrapper.user().get(ClientChunks.class)).getLoadedChunks().clear();
                    int gamemode = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    tracker.setGameMode(GameMode.getById(gamemode));
                });
                handler(wrapper2 -> {
                    CommandBlockProvider provider = (CommandBlockProvider) Via.getManager().getProviders().get(CommandBlockProvider.class);
                    provider.sendPermission(wrapper2.user());
                    provider.unloadChunks(wrapper2.user());
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.GAME_EVENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLOAT);
                handler(wrapper -> {
                    short reason = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (reason == 3) {
                        int gamemode = ((Float) wrapper.get(Type.FLOAT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.setGameMode(GameMode.getById(gamemode));
                    } else if (reason == 4) {
                        wrapper.set(Type.FLOAT, 0, Float.valueOf(1.0f));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SET_COMPRESSION, (ClientboundPackets1_8) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.cancel();
                    CompressionProvider provider = (CompressionProvider) Via.getManager().getProviders().get(CompressionProvider.class);
                    provider.handlePlayCompression(wrapper.user(), ((Integer) wrapper.read(Type.VAR_INT)).intValue());
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.CLIENT_SETTINGS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.VAR_INT, Type.BYTE);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper -> {
                    int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    if (Via.getConfig().isLeftHandedHandling() && hand == 0) {
                        wrapper.set(Type.UNSIGNED_BYTE, 0, Short.valueOf((short) (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).intValue() | 128)));
                    }
                    wrapper.sendToServer(Protocol1_9To1_8.class);
                    wrapper.cancel();
                    ((MainHandProvider) Via.getManager().getProviders().get(MainHandProvider.class)).setMainHand(wrapper.user(), hand);
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.ANIMATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.NOTHING);
            }
        });
        protocol.cancelServerbound(ServerboundPackets1_9.TELEPORT_CONFIRM);
        protocol.cancelServerbound(ServerboundPackets1_9.VEHICLE_MOVE);
        protocol.cancelServerbound(ServerboundPackets1_9.STEER_BOAT);
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(wrapper -> {
                    Item item;
                    String name = (String) wrapper.get(Type.STRING, 0);
                    if (name.equalsIgnoreCase("MC|BSign") && (item = (Item) wrapper.passthrough(Type.ITEM)) != null) {
                        item.setIdentifier(387);
                        ItemRewriter.rewriteBookToServer(item);
                    }
                    if (name.equalsIgnoreCase("MC|AutoCmd")) {
                        wrapper.set(Type.STRING, 0, "MC|AdvCdm");
                        wrapper.write(Type.BYTE, (byte) 0);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.STRING);
                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.clearInputBuffer();
                    }
                    if (name.equalsIgnoreCase("MC|AdvCmd")) {
                        wrapper.set(Type.STRING, 0, "MC|AdvCdm");
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.CLIENT_STATUS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.17
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    if (action == 2) {
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (tracker.isBlocking()) {
                            if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                                tracker.setSecondHand(null);
                            }
                            tracker.setBlocking(false);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.PLAYER_POSITION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.18
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BOOLEAN);
                handler(new PlayerMovementMapper());
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.19
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(new PlayerMovementMapper());
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.PLAYER_ROTATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.20
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(new PlayerMovementMapper());
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.PLAYER_MOVEMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets.21
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BOOLEAN);
                handler(new PlayerMovementMapper());
            }
        });
    }
}
