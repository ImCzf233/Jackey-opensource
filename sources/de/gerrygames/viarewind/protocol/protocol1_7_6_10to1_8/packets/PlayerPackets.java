package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Utils;
import de.gerrygames.viarewind.utils.math.AABB;
import de.gerrygames.viarewind.utils.math.Ray3d;
import de.gerrygames.viarewind.utils.math.RayTracing;
import de.gerrygames.viarewind.utils.math.Vector3d;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/PlayerPackets.class */
public class PlayerPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.JOIN_GAME, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(packetWrapper -> {
                    if (ViaRewind.getConfig().isReplaceAdventureMode() && ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 2) {
                        packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short) 0);
                    }
                });
                handler(packetWrapper2 -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.setGamemode(((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue());
                    tracker.setPlayerId(((Integer) packetWrapper2.get(Type.INT, 0)).intValue());
                    tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
                    tracker.setDimension(((Byte) packetWrapper2.get(Type.BYTE, 0)).byteValue());
                });
                handler(packetWrapper3 -> {
                    ClientWorld world = (ClientWorld) packetWrapper3.user().get(ClientWorld.class);
                    world.setEnvironment(((Byte) packetWrapper3.get(Type.BYTE, 0)).byteValue());
                });
                handler(wrapper -> {
                    wrapper.user().put(new Scoreboard(wrapper.user()));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.COMPONENT);
                handler(packetWrapper -> {
                    int position = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    if (position == 2) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.UPDATE_HEALTH, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.VAR_INT, Type.SHORT);
                map(Type.FLOAT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.RESPAWN, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                handler(packetWrapper -> {
                    if (ViaRewind.getConfig().isReplaceAdventureMode() && ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 1)).shortValue() == 2) {
                        packetWrapper.set(Type.UNSIGNED_BYTE, 1, (short) 0);
                    }
                });
                handler(packetWrapper2 -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.setGamemode(((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 1)).shortValue());
                    if (tracker.getDimension() != ((Integer) packetWrapper2.get(Type.INT, 0)).intValue()) {
                        tracker.setDimension(((Integer) packetWrapper2.get(Type.INT, 0)).intValue());
                        tracker.clearEntities();
                        tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
                    }
                });
                handler(packetWrapper3 -> {
                    ClientWorld world = (ClientWorld) packetWrapper3.user().get(ClientWorld.class);
                    world.setEnvironment(((Integer) packetWrapper3.get(Type.INT, 0)).intValue());
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setPositionPacketReceived(true);
                    int flags = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    if ((flags & 1) == 1) {
                        double x = ((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue();
                        packetWrapper.set(Type.DOUBLE, 0, Double.valueOf(x + playerPosition.getPosX()));
                    }
                    double y = ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue();
                    if ((flags & 2) == 2) {
                        y += playerPosition.getPosY();
                    }
                    playerPosition.setReceivedPosY(y);
                    packetWrapper.set(Type.DOUBLE, 1, Double.valueOf(y + 1.6200000047683716d));
                    if ((flags & 4) == 4) {
                        double z = ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue();
                        packetWrapper.set(Type.DOUBLE, 2, Double.valueOf(z + playerPosition.getPosZ()));
                    }
                    if ((flags & 8) == 8) {
                        float yaw = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                        packetWrapper.set(Type.FLOAT, 0, Float.valueOf(yaw + playerPosition.getYaw()));
                    }
                    if ((flags & 16) == 16) {
                        float pitch = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                        packetWrapper.set(Type.FLOAT, 1, Float.valueOf(pitch + playerPosition.getPitch()));
                    }
                });
                handler(packetWrapper2 -> {
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper2.user().get(PlayerPosition.class);
                    packetWrapper2.write(Type.BOOLEAN, Boolean.valueOf(playerPosition.isOnGround()));
                });
                handler(packetWrapper3 -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper3.user().get(EntityTracker.class);
                    if (tracker.getSpectating() != tracker.getPlayerId()) {
                        packetWrapper3.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SET_EXPERIENCE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.VAR_INT, Type.SHORT);
                map(Type.VAR_INT, Type.SHORT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.GAME_EVENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    Item[] equipment;
                    int mode = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (mode != 3) {
                        return;
                    }
                    int gamemode = ((Float) packetWrapper.get(Type.FLOAT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    if (gamemode == 3 || tracker.getGamemode() == 3) {
                        UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
                        if (gamemode == 3) {
                            GameProfileStorage.GameProfile profile = ((GameProfileStorage) packetWrapper.user().get(GameProfileStorage.class)).get(uuid);
                            equipment = new Item[5];
                            equipment[4] = profile.getSkull();
                        } else {
                            equipment = tracker.getPlayerEquipment(uuid);
                            if (equipment == null) {
                                equipment = new Item[5];
                            }
                        }
                        for (int i = 1; i < 5; i++) {
                            PacketWrapper setSlot = PacketWrapper.create(47, (ByteBuf) null, packetWrapper.user());
                            setSlot.write(Type.BYTE, (byte) 0);
                            setSlot.write(Type.SHORT, Short.valueOf((short) (9 - i)));
                            setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[i]);
                            PacketUtil.sendPacket(setSlot, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
                handler(packetWrapper2 -> {
                    int mode = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (mode == 3) {
                        int gamemode = ((Float) packetWrapper2.get(Type.FLOAT, 0)).intValue();
                        if (gamemode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                            gamemode = 0;
                            packetWrapper2.set(Type.FLOAT, 0, Float.valueOf(0.0f));
                        }
                        ((EntityTracker) packetWrapper2.user().get(EntityTracker.class)).setGamemode(gamemode);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    GameProfileStorage.GameProfile gameProfile;
                    EntityTracker tracker;
                    int entityId;
                    Item[] equipment;
                    packetWrapper.cancel();
                    int action = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    int count = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    GameProfileStorage gameProfileStorage = (GameProfileStorage) packetWrapper.user().get(GameProfileStorage.class);
                    for (int i = 0; i < count; i++) {
                        UUID uuid = (UUID) packetWrapper.read(Type.UUID);
                        if (action == 0) {
                            String name = (String) packetWrapper.read(Type.STRING);
                            GameProfileStorage.GameProfile gameProfile2 = gameProfileStorage.get(uuid);
                            if (gameProfile2 == null) {
                                gameProfile2 = gameProfileStorage.put(uuid, name);
                            }
                            int propertyCount = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                            while (true) {
                                int i2 = propertyCount;
                                propertyCount--;
                                if (i2 <= 0) {
                                    break;
                                }
                                gameProfile2.properties.add(new GameProfileStorage.Property((String) packetWrapper.read(Type.STRING), (String) packetWrapper.read(Type.STRING), ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue() ? (String) packetWrapper.read(Type.STRING) : null));
                            }
                            int gamemode = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                            int ping = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                            gameProfile2.ping = ping;
                            gameProfile2.gamemode = gamemode;
                            if (((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue()) {
                                gameProfile2.setDisplayName(ChatUtil.jsonToLegacy((JsonElement) packetWrapper.read(Type.COMPONENT)));
                            }
                            PacketWrapper packet = PacketWrapper.create(56, (ByteBuf) null, packetWrapper.user());
                            packet.write(Type.STRING, gameProfile2.name);
                            packet.write(Type.BOOLEAN, true);
                            packet.write(Type.SHORT, Short.valueOf((short) ping));
                            PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                        } else if (action == 1) {
                            int gamemode2 = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                            GameProfileStorage.GameProfile gameProfile3 = gameProfileStorage.get(uuid);
                            if (gameProfile3 != null && gameProfile3.gamemode != gamemode2) {
                                if ((gamemode2 == 3 || gameProfile3.gamemode == 3) && (entityId = (tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class)).getPlayerEntityId(uuid)) != -1) {
                                    if (gamemode2 == 3) {
                                        equipment = new Item[5];
                                        equipment[4] = gameProfile3.getSkull();
                                    } else {
                                        equipment = tracker.getPlayerEquipment(uuid);
                                        if (equipment == null) {
                                            equipment = new Item[5];
                                        }
                                    }
                                    short s = 0;
                                    while (true) {
                                        short slot = s;
                                        if (slot >= 5) {
                                            break;
                                        }
                                        PacketWrapper equipmentPacket = PacketWrapper.create(4, (ByteBuf) null, packetWrapper.user());
                                        equipmentPacket.write(Type.INT, Integer.valueOf(entityId));
                                        equipmentPacket.write(Type.SHORT, Short.valueOf(slot));
                                        equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[slot]);
                                        PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                                        s = (short) (slot + 1);
                                    }
                                }
                                gameProfile3.gamemode = gamemode2;
                            }
                        } else if (action == 2) {
                            int ping2 = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                            GameProfileStorage.GameProfile gameProfile4 = gameProfileStorage.get(uuid);
                            if (gameProfile4 != null) {
                                gameProfile4.ping = ping2;
                                PacketWrapper packet2 = PacketWrapper.create(56, (ByteBuf) null, packetWrapper.user());
                                packet2.write(Type.STRING, gameProfile4.name);
                                packet2.write(Type.BOOLEAN, true);
                                packet2.write(Type.SHORT, Short.valueOf((short) ping2));
                                PacketUtil.sendPacket(packet2, Protocol1_7_6_10TO1_8.class);
                            }
                        } else if (action == 3) {
                            String displayName = ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue() ? ChatUtil.jsonToLegacy((JsonElement) packetWrapper.read(Type.COMPONENT)) : null;
                            GameProfileStorage.GameProfile gameProfile5 = gameProfileStorage.get(uuid);
                            if (gameProfile5 != null && ((gameProfile5.displayName != null || displayName != null) && ((gameProfile5.displayName == null && displayName != null) || ((gameProfile5.displayName != null && displayName == null) || !gameProfile5.displayName.equals(displayName))))) {
                                gameProfile5.setDisplayName(displayName);
                            }
                        } else if (action == 4 && (gameProfile = gameProfileStorage.remove(uuid)) != null) {
                            PacketWrapper packet3 = PacketWrapper.create(56, (ByteBuf) null, packetWrapper.user());
                            packet3.write(Type.STRING, gameProfile.name);
                            packet3.write(Type.BOOLEAN, false);
                            packet3.write(Type.SHORT, Short.valueOf((short) gameProfile.ping));
                            PacketUtil.sendPacket(packet3, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.PLAYER_ABILITIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    byte flags = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    float flySpeed = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                    float walkSpeed = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                    PlayerAbilities abilities = (PlayerAbilities) packetWrapper.user().get(PlayerAbilities.class);
                    abilities.setInvincible((flags & 8) == 8);
                    abilities.setAllowFly((flags & 4) == 4);
                    abilities.setFlying((flags & 2) == 2);
                    abilities.setCreative((flags & 1) == 1);
                    abilities.setFlySpeed(flySpeed);
                    abilities.setWalkSpeed(walkSpeed);
                    if (abilities.isSprinting() && abilities.isFlying()) {
                        packetWrapper.set(Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed() * 2.0f));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    int size;
                    String channel = (String) packetWrapper.get(Type.STRING, 0);
                    if (channel.equalsIgnoreCase("MC|TrList")) {
                        packetWrapper.passthrough(Type.INT);
                        if (packetWrapper.isReadable(Type.BYTE, 0)) {
                            size = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                        } else {
                            size = ((Short) packetWrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        }
                        for (int i = 0; i < size; i++) {
                            Item item = ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM));
                            packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                            Item item2 = ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM));
                            packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item2);
                            boolean has3Items = ((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (has3Items) {
                                Item item3 = ItemRewriter.toClient((Item) packetWrapper.read(Type.ITEM));
                                packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item3);
                            }
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.read(Type.INT);
                            packetWrapper.read(Type.INT);
                        }
                    } else if (channel.equalsIgnoreCase("MC|Brand")) {
                        packetWrapper.write(Type.REMAINING_BYTES, ((String) packetWrapper.read(Type.STRING)).getBytes(StandardCharsets.UTF_8));
                    }
                    packetWrapper.cancel();
                    packetWrapper.setId(-1);
                    ByteBuf newPacketBuf = Unpooled.buffer();
                    packetWrapper.writeToBuffer(newPacketBuf);
                    PacketWrapper newWrapper = PacketWrapper.create(63, newPacketBuf, packetWrapper.user());
                    newWrapper.passthrough(Type.STRING);
                    if (newPacketBuf.readableBytes() <= 32767) {
                        newWrapper.write(Type.SHORT, Short.valueOf((short) newPacketBuf.readableBytes()));
                        newWrapper.send(Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.CAMERA, (ClientboundPackets1_8) null, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    int entityId = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    int spectating = tracker.getSpectating();
                    if (spectating != entityId) {
                        tracker.setSpectating(entityId);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.TITLE, (ClientboundPackets1_8) null, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    TitleRenderProvider titleRenderProvider = (TitleRenderProvider) Via.getManager().getProviders().get(TitleRenderProvider.class);
                    if (titleRenderProvider == null) {
                        return;
                    }
                    int action = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    UUID uuid = Utils.getUUID(packetWrapper.user());
                    switch (action) {
                        case 0:
                            titleRenderProvider.setTitle(uuid, (String) packetWrapper.read(Type.STRING));
                            return;
                        case 1:
                            titleRenderProvider.setSubTitle(uuid, (String) packetWrapper.read(Type.STRING));
                            return;
                        case 2:
                            titleRenderProvider.setTimings(uuid, ((Integer) packetWrapper.read(Type.INT)).intValue(), ((Integer) packetWrapper.read(Type.INT)).intValue(), ((Integer) packetWrapper.read(Type.INT)).intValue());
                            return;
                        case 3:
                            titleRenderProvider.clear(uuid);
                            return;
                        case 4:
                            titleRenderProvider.reset(uuid);
                            return;
                        default:
                            return;
                    }
                });
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_8.TAB_LIST);
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.RESOURCE_PACK, (ClientboundPackets1_8) ClientboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                create(Type.STRING, "MC|RPack");
                handler(packetWrapper -> {
                    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                    try {
                        Type.STRING.write(buf, (String) packetWrapper.read(Type.STRING));
                        packetWrapper.write(Type.SHORT_BYTE_ARRAY, Type.REMAINING_BYTES.read(buf));
                    } finally {
                        buf.release();
                    }
                });
                map(Type.STRING, Type.NOTHING);
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.CHAT_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    String msg = (String) packetWrapper.get(Type.STRING, 0);
                    int gamemode = ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getGamemode();
                    if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
                        String username = msg.split(" ")[1];
                        GameProfileStorage storage = (GameProfileStorage) packetWrapper.user().get(GameProfileStorage.class);
                        GameProfileStorage.GameProfile profile = storage.get(username, true);
                        if (profile != null && profile.uuid != null) {
                            packetWrapper.cancel();
                            PacketWrapper teleportPacket = PacketWrapper.create(24, (ByteBuf) null, packetWrapper.user());
                            teleportPacket.write(Type.UUID, profile.uuid);
                            PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10TO1_8.class, true, true);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.INTERACT_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.17
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT, Type.VAR_INT);
                map(Type.BYTE, Type.VAR_INT);
                handler(packetWrapper -> {
                    int mode = ((Integer) packetWrapper.get(Type.VAR_INT, 1)).intValue();
                    if (mode != 0) {
                        return;
                    }
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (!(replacement instanceof ArmorStandReplacement)) {
                        return;
                    }
                    ArmorStandReplacement armorStand = (ArmorStandReplacement) replacement;
                    AABB boundingBox = armorStand.getBoundingBox();
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    Vector3d pos = new Vector3d(playerPosition.getPosX(), playerPosition.getPosY() + 1.8d, playerPosition.getPosZ());
                    double yaw = Math.toRadians(playerPosition.getYaw());
                    double pitch = Math.toRadians(playerPosition.getPitch());
                    Vector3d dir = new Vector3d((-Math.cos(pitch)) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                    Ray3d ray = new Ray3d(pos, dir);
                    Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0d);
                    if (intersection == null) {
                        return;
                    }
                    intersection.substract(boundingBox.getMin());
                    packetWrapper.set(Type.VAR_INT, 1, 2);
                    packetWrapper.write(Type.FLOAT, Float.valueOf((float) intersection.getX()));
                    packetWrapper.write(Type.FLOAT, Float.valueOf((float) intersection.getY()));
                    packetWrapper.write(Type.FLOAT, Float.valueOf((float) intersection.getZ()));
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_MOVEMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.18
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.19
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE, Type.NOTHING);
                map(Type.DOUBLE);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    double x = ((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue();
                    double feetY = ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue();
                    double z = ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue();
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    if (playerPosition.isPositionPacketReceived()) {
                        playerPosition.setPositionPacketReceived(false);
                        feetY -= 0.01d;
                        packetWrapper.set(Type.DOUBLE, 1, Double.valueOf(feetY));
                    }
                    playerPosition.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                    playerPosition.setPos(x, feetY, z);
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.20
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setYaw(((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue());
                    playerPosition.setPitch(((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue());
                    playerPosition.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.21
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE, Type.NOTHING);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    double x = ((Double) packetWrapper.get(Type.DOUBLE, 0)).doubleValue();
                    double feetY = ((Double) packetWrapper.get(Type.DOUBLE, 1)).doubleValue();
                    double z = ((Double) packetWrapper.get(Type.DOUBLE, 2)).doubleValue();
                    float yaw = ((Float) packetWrapper.get(Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float) packetWrapper.get(Type.FLOAT, 1)).floatValue();
                    PlayerPosition playerPosition = (PlayerPosition) packetWrapper.user().get(PlayerPosition.class);
                    if (playerPosition.isPositionPacketReceived()) {
                        playerPosition.setPositionPacketReceived(false);
                        feetY = playerPosition.getReceivedPosY();
                        packetWrapper.set(Type.DOUBLE, 1, Double.valueOf(feetY));
                    }
                    playerPosition.setOnGround(((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue());
                    playerPosition.setPos(x, feetY, z);
                    playerPosition.setYaw(yaw);
                    playerPosition.setPitch(pitch);
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_DIGGING, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.22
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int x = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    short y = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    int z = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    packetWrapper.write(Type.POSITION, new Position(x, y, z));
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.23
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int x = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    short y = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    int z = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    packetWrapper.write(Type.POSITION, new Position(x, y, z));
                    packetWrapper.passthrough(Type.BYTE);
                    Item item = (Item) packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                    packetWrapper.write(Type.ITEM, ItemRewriter.toServer(item));
                    for (int i = 0; i < 3; i++) {
                        packetWrapper.passthrough(Type.BYTE);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.ANIMATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.24
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int animation;
                    int entityId = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    int animation2 = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    if (animation2 == 1) {
                        return;
                    }
                    packetWrapper.cancel();
                    switch (animation2) {
                        case 3:
                            animation = 2;
                            break;
                        case 104:
                            animation = 0;
                            break;
                        case 105:
                            animation = 1;
                            break;
                        default:
                            return;
                    }
                    PacketWrapper entityAction = PacketWrapper.create(11, (ByteBuf) null, packetWrapper.user());
                    entityAction.write(Type.VAR_INT, Integer.valueOf(entityId));
                    entityAction.write(Type.VAR_INT, Integer.valueOf(animation));
                    entityAction.write(Type.VAR_INT, 0);
                    PacketUtil.sendPacket(entityAction, Protocol1_7_6_10TO1_8.class, true, true);
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.ENTITY_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.25
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT, Type.VAR_INT);
                handler(packetWrapper -> {
                    packetWrapper.write(Type.VAR_INT, Integer.valueOf(((Byte) packetWrapper.read(Type.BYTE)).byteValue() - 1));
                });
                map(Type.INT, Type.VAR_INT);
                handler(packetWrapper2 -> {
                    int action = ((Integer) packetWrapper2.get(Type.VAR_INT, 1)).intValue();
                    if (action == 3 || action == 4) {
                        PlayerAbilities abilities = (PlayerAbilities) packetWrapper2.user().get(PlayerAbilities.class);
                        abilities.setSprinting(action == 3);
                        PacketWrapper abilitiesPacket = PacketWrapper.create(57, (ByteBuf) null, packetWrapper2.user());
                        abilitiesPacket.write(Type.BYTE, Byte.valueOf(abilities.getFlags()));
                        abilitiesPacket.write(Type.FLOAT, Float.valueOf(abilities.isSprinting() ? abilities.getFlySpeed() * 2.0f : abilities.getFlySpeed()));
                        abilitiesPacket.write(Type.FLOAT, Float.valueOf(abilities.getWalkSpeed()));
                        PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.STEER_VEHICLE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.26
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    boolean jump = ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue();
                    boolean unmount = ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue();
                    short flags = 0;
                    if (jump) {
                        flags = (short) (0 + 1);
                    }
                    if (unmount) {
                        flags = (short) (flags + 2);
                    }
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(flags));
                    if (unmount) {
                        EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                        if (tracker.getSpectating() != tracker.getPlayerId()) {
                            PacketWrapper sneakPacket = PacketWrapper.create(11, (ByteBuf) null, packetWrapper.user());
                            sneakPacket.write(Type.VAR_INT, Integer.valueOf(tracker.getPlayerId()));
                            sneakPacket.write(Type.VAR_INT, 0);
                            sneakPacket.write(Type.VAR_INT, 0);
                            PacketWrapper unsneakPacket = PacketWrapper.create(11, (ByteBuf) null, packetWrapper.user());
                            unsneakPacket.write(Type.VAR_INT, Integer.valueOf(tracker.getPlayerId()));
                            unsneakPacket.write(Type.VAR_INT, 1);
                            unsneakPacket.write(Type.VAR_INT, 0);
                            PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.UPDATE_SIGN, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.27
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int x = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    short y = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    int z = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    packetWrapper.write(Type.POSITION, new Position(x, y, z));
                    for (int i = 0; i < 4; i++) {
                        String line = (String) packetWrapper.read(Type.STRING);
                        packetWrapper.write(Type.COMPONENT, JsonParser.parseString(ChatUtil.legacyToJson(line)));
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLAYER_ABILITIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.28
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    PlayerAbilities abilities = (PlayerAbilities) packetWrapper.user().get(PlayerAbilities.class);
                    if (abilities.isAllowFly()) {
                        byte flags = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        abilities.setFlying((flags & 2) == 2);
                    }
                    packetWrapper.set(Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed()));
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.TAB_COMPLETE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.29
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                create(Type.OPTIONAL_POSITION, null);
                handler(packetWrapper -> {
                    String msg = (String) packetWrapper.get(Type.STRING, 0);
                    if (msg.toLowerCase().startsWith("/stp ")) {
                        packetWrapper.cancel();
                        String[] args = msg.split(" ");
                        if (args.length <= 2) {
                            String prefix = args.length == 1 ? "" : args[1];
                            GameProfileStorage storage = (GameProfileStorage) packetWrapper.user().get(GameProfileStorage.class);
                            List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                            PacketWrapper tabComplete = PacketWrapper.create(58, (ByteBuf) null, packetWrapper.user());
                            tabComplete.write(Type.VAR_INT, Integer.valueOf(profiles.size()));
                            for (GameProfileStorage.GameProfile profile : profiles) {
                                tabComplete.write(Type.STRING, profile.name);
                            }
                            PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.CLIENT_SETTINGS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.30
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                map(Type.BYTE, Type.NOTHING);
                handler(packetWrapper -> {
                    boolean cape = ((Boolean) packetWrapper.read(Type.BOOLEAN)).booleanValue();
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) (cape ? 127 : 126)));
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets.31
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.SHORT, Type.NOTHING);
                handler(packetWrapper -> {
                    String channel = (String) packetWrapper.get(Type.STRING, 0);
                    boolean z = true;
                    switch (channel.hashCode()) {
                        case -751882236:
                            if (channel.equals("MC|ItemName")) {
                                z = true;
                                break;
                            }
                            break;
                        case -296231034:
                            if (channel.equals("MC|BEdit")) {
                                z = true;
                                break;
                            }
                            break;
                        case -295809223:
                            if (channel.equals("MC|BSign")) {
                                z = true;
                                break;
                            }
                            break;
                        case -294893183:
                            if (channel.equals("MC|Brand")) {
                                z = true;
                                break;
                            }
                            break;
                        case -278283530:
                            if (channel.equals("MC|TrSel")) {
                                z = false;
                                break;
                            }
                            break;
                    }
                    switch (z) {
                        case false:
                            packetWrapper.passthrough(Type.INT);
                            packetWrapper.read(Type.REMAINING_BYTES);
                            return;
                        case true:
                            byte[] data = (byte[]) packetWrapper.read(Type.REMAINING_BYTES);
                            String name = new String(data, StandardCharsets.UTF_8);
                            packetWrapper.write(Type.STRING, name);
                            Windows windows = (Windows) packetWrapper.user().get(Windows.class);
                            PacketWrapper updateCost = PacketWrapper.create(49, (ByteBuf) null, packetWrapper.user());
                            updateCost.write(Type.UNSIGNED_BYTE, Short.valueOf(windows.anvilId));
                            updateCost.write(Type.SHORT, (short) 0);
                            updateCost.write(Type.SHORT, Short.valueOf(windows.levelCost));
                            PacketUtil.sendPacket(updateCost, Protocol1_7_6_10TO1_8.class, true, true);
                            return;
                        case true:
                        case true:
                            Item book = (Item) packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                            CompoundTag tag = book.tag();
                            if (tag != null && tag.contains("pages")) {
                                ListTag pages = (ListTag) tag.get("pages");
                                for (int i = 0; i < pages.size(); i++) {
                                    StringTag page = (StringTag) pages.get(i);
                                    String value = page.getValue();
                                    page.setValue(ChatUtil.legacyToJson(value));
                                }
                            }
                            packetWrapper.write(Type.ITEM, book);
                            return;
                        case true:
                            packetWrapper.write(Type.STRING, new String((byte[]) packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
                            return;
                        default:
                            return;
                    }
                });
            }
        });
    }
}
