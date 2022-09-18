package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/PlayerPackets.class */
public class PlayerPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.BOSSBAR, (ClientboundPackets1_9) null, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    UUID uuid = (UUID) packetWrapper.read(Type.UUID);
                    int action = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    BossBarStorage bossBarStorage = (BossBarStorage) packetWrapper.user().get(BossBarStorage.class);
                    if (action == 0) {
                        bossBarStorage.add(uuid, ChatUtil.jsonToLegacy((JsonElement) packetWrapper.read(Type.COMPONENT)), ((Float) packetWrapper.read(Type.FLOAT)).floatValue());
                        packetWrapper.read(Type.VAR_INT);
                        packetWrapper.read(Type.VAR_INT);
                        packetWrapper.read(Type.UNSIGNED_BYTE);
                    } else if (action == 1) {
                        bossBarStorage.remove(uuid);
                    } else if (action == 2) {
                        bossBarStorage.updateHealth(uuid, ((Float) packetWrapper.read(Type.FLOAT)).floatValue());
                    } else if (action == 3) {
                        String title = ChatUtil.jsonToLegacy((JsonElement) packetWrapper.read(Type.COMPONENT));
                        bossBarStorage.updateTitle(uuid, title);
                    }
                });
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_9.COOLDOWN);
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    int size;
                    String channel = (String) packetWrapper.get(Type.STRING, 0);
                    if (!channel.equalsIgnoreCase("MC|TrList")) {
                        if (channel.equalsIgnoreCase("MC|BOpen")) {
                            packetWrapper.read(Type.VAR_INT);
                            return;
                        }
                        return;
                    }
                    packetWrapper.passthrough(Type.INT);
                    if (packetWrapper.isReadable(Type.BYTE, 0)) {
                        size = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                    } else {
                        size = ((Short) packetWrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    }
                    for (int i = 0; i < size; i++) {
                        packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM)));
                        boolean has3Items = ((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue();
                        if (has3Items) {
                            packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM)));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.GAME_EVENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    int reason = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (reason == 3) {
                        ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Float) packetWrapper.get(Type.FLOAT, 0)).intValue());
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.setPlayerId(((Integer) packetWrapper.get(Type.INT, 0)).intValue());
                    tracker.setPlayerGamemode(((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue());
                    tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
                });
                handler(packetWrapper2 -> {
                    ClientWorld world = (ClientWorld) packetWrapper2.user().get(ClientWorld.class);
                    world.setEnvironment(((Byte) packetWrapper2.get(Type.BYTE, 0)).byteValue());
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.PLAYER_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    int teleportId = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    pos.setConfirmId(teleportId);
                    byte flags = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    double x = ((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue();
                    double y = ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue();
                    double z = ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue();
                    float yaw = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                    packetWrapper.set(Type.BYTE, 0, (byte) 0);
                    if (flags != 0) {
                        if ((flags & 1) != 0) {
                            x += pos.getPosX();
                            packetWrapper.set(Type.DOUBLE, 0, Double.valueOf(x));
                        }
                        if ((flags & 2) != 0) {
                            y += pos.getPosY();
                            packetWrapper.set(Type.DOUBLE, 1, Double.valueOf(y));
                        }
                        if ((flags & 4) != 0) {
                            z += pos.getPosZ();
                            packetWrapper.set(Type.DOUBLE, 2, Double.valueOf(z));
                        }
                        if ((flags & 8) != 0) {
                            yaw += pos.getYaw();
                            packetWrapper.set(Type.FLOAT, 0, Float.valueOf(yaw));
                        }
                        if ((flags & 16) != 0) {
                            pitch += pos.getPitch();
                            packetWrapper.set(Type.FLOAT, 1, Float.valueOf(pitch));
                        }
                    }
                    pos.setPos(x, y, z);
                    pos.setYaw(yaw);
                    pos.setPitch(pitch);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.RESPAWN, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                handler(packetWrapper -> {
                    ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 1)).shortValue());
                });
                handler(packetWrapper2 -> {
                    ((BossBarStorage) packetWrapper2.user().get(BossBarStorage.class)).updateLocation();
                    ((BossBarStorage) packetWrapper2.user().get(BossBarStorage.class)).changeWorld();
                });
                handler(packetWrapper3 -> {
                    ClientWorld world = (ClientWorld) packetWrapper3.user().get(ClientWorld.class);
                    world.setEnvironment(((Integer) packetWrapper3.get(Type.INT, 0)).intValue());
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    String msg = (String) packetWrapper.get(Type.STRING, 0);
                    if (msg.toLowerCase().startsWith("/offhand")) {
                        packetWrapper.cancel();
                        PacketWrapper swapItems = PacketWrapper.create(19, (ByteBuf) null, packetWrapper.user());
                        swapItems.write(Type.VAR_INT, 6);
                        swapItems.write(Type.POSITION, new Position(0, (short) 0, 0));
                        swapItems.write(Type.BYTE, (byte) -1);
                        PacketUtil.sendToServer(swapItems, Protocol1_8TO1_9.class, true, true);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.INTERACT_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int type = ((Integer) packetWrapper.get(Type.VAR_INT, 1)).intValue();
                    if (type == 2) {
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.FLOAT);
                    }
                    if (type == 2 || type == 0) {
                        packetWrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_MOVEMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    int playerId = tracker.getPlayerId();
                    if (tracker.isInsideVehicle(playerId)) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        return;
                    }
                    pos.setPos(((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue(), ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue(), ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue());
                    pos.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                });
                handler(packetWrapper2 -> {
                    ((BossBarStorage) packetWrapper2.user().get(BossBarStorage.class)).updateLocation();
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        return;
                    }
                    pos.setYaw(((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue());
                    pos.setPitch(((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue());
                    pos.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                });
                handler(packetWrapper2 -> {
                    ((BossBarStorage) packetWrapper2.user().get(BossBarStorage.class)).updateLocation();
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    double x = ((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue();
                    double y = ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue();
                    double z = ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue();
                    float yaw = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                    boolean onGround = ((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue();
                    PlayerPosition pos = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        if (pos.getPosX() == x && pos.getPosY() == y && pos.getPosZ() == z && pos.getYaw() == yaw && pos.getPitch() == pitch) {
                            PacketWrapper confirmTeleport = packetWrapper.create(0);
                            confirmTeleport.write(Type.VAR_INT, Integer.valueOf(pos.getConfirmId()));
                            PacketUtil.sendToServer(confirmTeleport, Protocol1_8TO1_9.class, true, true);
                            pos.setConfirmId(-1);
                            return;
                        }
                        return;
                    }
                    pos.setPos(x, y, z);
                    pos.setYaw(yaw);
                    pos.setPitch(pitch);
                    pos.setOnGround(onGround);
                    ((BossBarStorage) packetWrapper.user().get(BossBarStorage.class)).updateLocation();
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_DIGGING, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION);
                handler(packetWrapper -> {
                    int state = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    if (state == 0) {
                        ((BlockPlaceDestroyTracker) packetWrapper.user().get(BlockPlaceDestroyTracker.class)).setMining(true);
                    } else if (state == 2) {
                        BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker) packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                        tracker.setMining(false);
                        tracker.setLastMining(System.currentTimeMillis() + 100);
                        ((Cooldown) packetWrapper.user().get(Cooldown.class)).setLastHit(0L);
                    } else if (state == 1) {
                        BlockPlaceDestroyTracker tracker2 = (BlockPlaceDestroyTracker) packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                        tracker2.setMining(false);
                        tracker2.setLastMining(0L);
                        ((Cooldown) packetWrapper.user().get(Cooldown.class)).hit();
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.BYTE, Type.VAR_INT);
                map(Type.ITEM, Type.NOTHING);
                create(Type.VAR_INT, 0);
                map(Type.BYTE, Type.UNSIGNED_BYTE);
                map(Type.BYTE, Type.UNSIGNED_BYTE);
                map(Type.BYTE, Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    if (((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue() == -1) {
                        packetWrapper.cancel();
                        PacketWrapper useItem = PacketWrapper.create(29, (ByteBuf) null, packetWrapper.user());
                        useItem.write(Type.VAR_INT, 0);
                        PacketUtil.sendToServer(useItem, Protocol1_8TO1_9.class, true, true);
                    }
                });
                handler(packetWrapper2 -> {
                    if (((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue() != -1) {
                        ((BlockPlaceDestroyTracker) packetWrapper2.user().get(BlockPlaceDestroyTracker.class)).place();
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.HELD_ITEM_CHANGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    ((Cooldown) packetWrapper.user().get(Cooldown.class)).hit();
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.ANIMATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    final PacketWrapper delayedPacket = PacketWrapper.create(26, (ByteBuf) null, packetWrapper.user());
                    delayedPacket.write(Type.VAR_INT, 0);
                    Protocol1_8TO1_9.TIMER.schedule(new TimerTask() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.16.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            PacketUtil.sendToServer(delayedPacket, Protocol1_8TO1_9.class);
                        }
                    }, 5L);
                });
                handler(packetWrapper2 -> {
                    ((BlockPlaceDestroyTracker) packetWrapper2.user().get(BlockPlaceDestroyTracker.class)).updateMining();
                    ((Cooldown) packetWrapper2.user().get(Cooldown.class)).hit();
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.ENTITY_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.17
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int action = ((Integer) packetWrapper.get(Type.VAR_INT, 1)).intValue();
                    if (action == 6) {
                        packetWrapper.set(Type.VAR_INT, 1, 7);
                    } else if (action == 0) {
                        PlayerPosition pos = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                        if (!pos.isOnGround()) {
                            PacketWrapper elytra = PacketWrapper.create(20, (ByteBuf) null, packetWrapper.user());
                            elytra.write(Type.VAR_INT, (Integer) packetWrapper.get(Type.VAR_INT, 0));
                            elytra.write(Type.VAR_INT, 8);
                            elytra.write(Type.VAR_INT, 0);
                            PacketUtil.sendToServer(elytra, Protocol1_8TO1_9.class, true, false);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.STEER_VEHICLE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.18
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    int playerId = tracker.getPlayerId();
                    int vehicle = tracker.getVehicle(playerId);
                    if (vehicle != -1 && tracker.getClientEntityTypes().get(Integer.valueOf(vehicle)) == Entity1_10Types.EntityType.BOAT) {
                        PacketWrapper steerBoat = PacketWrapper.create(17, (ByteBuf) null, packetWrapper.user());
                        float left = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                        float forward = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                        steerBoat.write(Type.BOOLEAN, Boolean.valueOf(forward != 0.0f || left < 0.0f));
                        steerBoat.write(Type.BOOLEAN, Boolean.valueOf(forward != 0.0f || left > 0.0f));
                        PacketUtil.sendToServer(steerBoat, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.UPDATE_SIGN, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.19
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                handler(packetWrapper -> {
                    for (int i = 0; i < 4; i++) {
                        packetWrapper.write(Type.STRING, ChatUtil.jsonToLegacy((JsonElement) packetWrapper.read(Type.COMPONENT)));
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.TAB_COMPLETE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.20
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    packetWrapper.write(Type.BOOLEAN, false);
                });
                map(Type.OPTIONAL_POSITION);
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.CLIENT_SETTINGS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.21
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.BYTE, Type.VAR_INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                create(Type.VAR_INT, 1);
                handler(packetWrapper -> {
                    short flags = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    PacketWrapper updateSkin = PacketWrapper.create(28, (ByteBuf) null, packetWrapper.user());
                    updateSkin.write(Type.VAR_INT, Integer.valueOf(((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getPlayerId()));
                    ArrayList<Metadata> metadata = new ArrayList<>();
                    metadata.add(new Metadata(10, MetaType1_8.Byte, Byte.valueOf((byte) flags)));
                    updateSkin.write(Types1_8.METADATA_LIST, metadata);
                    PacketUtil.sendPacket(updateSkin, Protocol1_8TO1_9.class);
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets.22
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    String channel = (String) packetWrapper.get(Type.STRING, 0);
                    if (!channel.equalsIgnoreCase("MC|BEdit") && !channel.equalsIgnoreCase("MC|BSign")) {
                        if (channel.equalsIgnoreCase("MC|AdvCdm")) {
                            packetWrapper.set(Type.STRING, 0, "MC|AdvCmd");
                            return;
                        }
                        return;
                    }
                    Item book = (Item) packetWrapper.passthrough(Type.ITEM);
                    book.setIdentifier(386);
                    CompoundTag tag = book.tag();
                    if (tag.contains("pages")) {
                        ListTag pages = (ListTag) tag.get("pages");
                        if (pages.size() > ViaRewind.getConfig().getMaxBookPages()) {
                            packetWrapper.user().disconnect("Too many book pages");
                            return;
                        }
                        for (int i = 0; i < pages.size(); i++) {
                            StringTag page = (StringTag) pages.get(i);
                            String value = page.getValue();
                            if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
                                packetWrapper.user().disconnect("Book page too large");
                                return;
                            }
                            page.setValue(ChatUtil.jsonToLegacy(value));
                        }
                    }
                });
            }
        });
    }
}
