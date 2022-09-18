package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EndermiteReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.GuardianReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.UUID;
import kotlin.jvm.internal.ByteCompanionObject;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/SpawnPackets.class */
public class SpawnPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    UUID uuid = (UUID) packetWrapper.read(Type.UUID);
                    packetWrapper.write(Type.STRING, uuid.toString());
                    GameProfileStorage gameProfileStorage = (GameProfileStorage) packetWrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                    if (gameProfile == null) {
                        packetWrapper.write(Type.STRING, "");
                        packetWrapper.write(Type.VAR_INT, 0);
                    } else {
                        packetWrapper.write(Type.STRING, gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name);
                        packetWrapper.write(Type.VAR_INT, Integer.valueOf(gameProfile.properties.size()));
                        for (GameProfileStorage.Property property : gameProfile.properties) {
                            packetWrapper.write(Type.STRING, property.name);
                            packetWrapper.write(Type.STRING, property.value);
                            packetWrapper.write(Type.STRING, property.signature == null ? "" : property.signature);
                        }
                    }
                    if (gameProfile != null && gameProfile.gamemode == 3) {
                        int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                        PacketWrapper equipmentPacket = PacketWrapper.create(4, (ByteBuf) null, packetWrapper.user());
                        equipmentPacket.write(Type.INT, Integer.valueOf(entityId));
                        equipmentPacket.write(Type.SHORT, (short) 4);
                        equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, gameProfile.getSkull());
                        PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                        short s = 0;
                        while (true) {
                            short i = s;
                            if (i >= 4) {
                                break;
                            }
                            PacketWrapper equipmentPacket2 = PacketWrapper.create(4, (ByteBuf) null, packetWrapper.user());
                            equipmentPacket2.write(Type.INT, Integer.valueOf(entityId));
                            equipmentPacket2.write(Type.SHORT, Short.valueOf(i));
                            equipmentPacket2.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, null);
                            PacketUtil.sendPacket(equipmentPacket2, Protocol1_7_6_10TO1_8.class);
                            s = (short) (i + 1);
                        }
                    }
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.addPlayer((Integer) packetWrapper.get(Type.VAR_INT, 0), uuid);
                });
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                handler(packetWrapper2 -> {
                    List<Metadata> metadata = (List) packetWrapper2.get(Types1_7_6_10.METADATA_LIST, 0);
                    MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadata);
                });
                handler(packetWrapper3 -> {
                    int entityId = ((Integer) packetWrapper3.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper3.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PLAYER);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    byte typeId = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    int x = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    int y = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                    int z = ((Integer) packetWrapper.get(Type.INT, 2)).intValue();
                    byte pitch = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                    byte yaw = ((Byte) packetWrapper.get(Type.BYTE, 2)).byteValue();
                    if (typeId == 71) {
                        switch (yaw) {
                            case ByteCompanionObject.MIN_VALUE /* -128 */:
                                z += 32;
                                yaw = 0;
                                break;
                            case -64:
                                x -= 32;
                                yaw = -64;
                                break;
                            case 0:
                                z -= 32;
                                yaw = Byte.MIN_VALUE;
                                break;
                            case 64:
                                x += 32;
                                yaw = 64;
                                break;
                        }
                    } else if (typeId == 78) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                        ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
                        armorStand.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        armorStand.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                        armorStand.setHeadYaw((yaw * 360.0f) / 256.0f);
                        tracker.addEntityReplacement(armorStand);
                    } else if (typeId == 10) {
                        y += 12;
                    }
                    packetWrapper.set(Type.BYTE, 0, Byte.valueOf(typeId));
                    packetWrapper.set(Type.INT, 0, Integer.valueOf(x));
                    packetWrapper.set(Type.INT, 1, Integer.valueOf(y));
                    packetWrapper.set(Type.INT, 2, Integer.valueOf(z));
                    packetWrapper.set(Type.BYTE, 1, Byte.valueOf(pitch));
                    packetWrapper.set(Type.BYTE, 2, Byte.valueOf(yaw));
                    EntityTracker tracker2 = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId(typeId, true);
                    tracker2.getClientEntityTypes().put(Integer.valueOf(entityId), type);
                    tracker2.sendMetadataBuffer(entityId);
                    int data = ((Integer) packetWrapper.get(Type.INT, 3)).intValue();
                    if (type != null && type.isOrHasParent(Entity1_10Types.EntityType.FALLING_BLOCK)) {
                        int blockId = data & 4095;
                        int blockData = (data >> 12) & 15;
                        Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, blockData);
                        if (replace != null) {
                            blockId = replace.getId();
                            blockData = replace.replaceData(blockData);
                        }
                        int i = blockId | (blockData << 16);
                        data = i;
                        packetWrapper.set(Type.INT, 3, Integer.valueOf(i));
                    }
                    if (data > 0) {
                        packetWrapper.passthrough(Type.SHORT);
                        packetWrapper.passthrough(Type.SHORT);
                        packetWrapper.passthrough(Type.SHORT);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int typeId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    int x = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    int y = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                    int z = ((Integer) packetWrapper.get(Type.INT, 2)).intValue();
                    byte pitch = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                    byte yaw = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    byte headYaw = ((Byte) packetWrapper.get(Type.BYTE, 2)).byteValue();
                    if (typeId == 30) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                        ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
                        armorStand.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        armorStand.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                        armorStand.setHeadYaw((headYaw * 360.0f) / 256.0f);
                        tracker.addEntityReplacement(armorStand);
                    } else if (typeId == 68) {
                        packetWrapper.cancel();
                        EntityTracker tracker2 = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                        GuardianReplacement guardian = new GuardianReplacement(entityId, packetWrapper.user());
                        guardian.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        guardian.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                        guardian.setHeadYaw((headYaw * 360.0f) / 256.0f);
                        tracker2.addEntityReplacement(guardian);
                    } else if (typeId != 67) {
                        if (typeId == 101 || typeId == 255 || typeId == -1) {
                            packetWrapper.cancel();
                        }
                    } else {
                        packetWrapper.cancel();
                        EntityTracker tracker3 = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                        EndermiteReplacement endermite = new EndermiteReplacement(entityId, packetWrapper.user());
                        endermite.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        endermite.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                        endermite.setHeadYaw((headYaw * 360.0f) / 256.0f);
                        tracker3.addEntityReplacement(endermite);
                    }
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    int typeId = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.getTypeFromId(typeId, false));
                    tracker.sendMetadataBuffer(entityId);
                });
                handler(wrapper -> {
                    List<Metadata> metadataList = (List) wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) wrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        tracker.getEntityReplacement(entityId).updateMetadata(metadataList);
                    } else if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.STRING);
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                map(Type.UNSIGNED_BYTE, Type.INT);
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PAINTING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.SHORT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.EXPERIENCE_ORB);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.LIGHTNING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
    }
}
