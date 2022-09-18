package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerBulletReplacement;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerReplacement;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/SpawnPackets.class */
public class SpawnPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID, Type.NOTHING);
                map(Type.BYTE);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int typeId = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId(typeId, true);
                    if (typeId == 3 || typeId == 91 || typeId == 92 || typeId == 93) {
                        packetWrapper.cancel();
                    } else if (type == null) {
                        ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + typeId);
                        packetWrapper.cancel();
                    } else {
                        int x = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                        int y = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                        int z = ((Integer) packetWrapper.get(Type.INT, 2)).intValue();
                        if (type.m224is(Entity1_10Types.EntityType.BOAT)) {
                            byte yaw = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                            packetWrapper.set(Type.BYTE, 1, Byte.valueOf((byte) (yaw - 64)));
                            packetWrapper.set(Type.INT, 1, Integer.valueOf(y + 10));
                        } else if (type.m224is(Entity1_10Types.EntityType.SHULKER_BULLET)) {
                            packetWrapper.cancel();
                            ShulkerBulletReplacement shulkerBulletReplacement = new ShulkerBulletReplacement(entityId, packetWrapper.user());
                            shulkerBulletReplacement.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                            tracker.addEntityReplacement(shulkerBulletReplacement);
                            return;
                        }
                        int data = ((Integer) packetWrapper.get(Type.INT, 3)).intValue();
                        if (type.isOrHasParent(Entity1_10Types.EntityType.ARROW) && data != 0) {
                            data--;
                            packetWrapper.set(Type.INT, 3, Integer.valueOf(data));
                        }
                        if (type.m224is(Entity1_10Types.EntityType.FALLING_BLOCK)) {
                            int blockId = data & 4095;
                            int blockData = (data >> 12) & 15;
                            Replacement replace = ReplacementRegistry1_8to1_9.getReplacement(blockId, blockData);
                            if (replace != null) {
                                packetWrapper.set(Type.INT, 3, Integer.valueOf(replace.getId() | (replace.replaceData(data) << 12)));
                            }
                        }
                        if (data > 0) {
                            packetWrapper.passthrough(Type.SHORT);
                            packetWrapper.passthrough(Type.SHORT);
                            packetWrapper.passthrough(Type.SHORT);
                        } else {
                            short vX = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                            short vY = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                            short vZ = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                            PacketWrapper velocityPacket = PacketWrapper.create(18, (ByteBuf) null, packetWrapper.user());
                            velocityPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                            velocityPacket.write(Type.SHORT, Short.valueOf(vX));
                            velocityPacket.write(Type.SHORT, Short.valueOf(vY));
                            velocityPacket.write(Type.SHORT, Short.valueOf(vZ));
                            PacketUtil.sendPacket(velocityPacket, Protocol1_8TO1_9.class);
                        }
                        tracker.getClientEntityTypes().put(Integer.valueOf(entityId), type);
                        tracker.sendMetadataBuffer(entityId);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.SHORT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.EXPERIENCE_ORB);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.LIGHTNING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_MOB, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID, Type.NOTHING);
                map(Type.UNSIGNED_BYTE);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int typeId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    int x = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    int y = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                    int z = ((Integer) packetWrapper.get(Type.INT, 2)).intValue();
                    byte pitch = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                    byte yaw = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    byte headYaw = ((Byte) packetWrapper.get(Type.BYTE, 2)).byteValue();
                    if (typeId != 69) {
                        if (typeId == -1 || typeId == 255) {
                            packetWrapper.cancel();
                            return;
                        }
                        return;
                    }
                    packetWrapper.cancel();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    ShulkerReplacement shulkerReplacement = new ShulkerReplacement(entityId, packetWrapper.user());
                    shulkerReplacement.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                    shulkerReplacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    shulkerReplacement.setHeadYaw((headYaw * 360.0f) / 256.0f);
                    tracker.addEntityReplacement(shulkerReplacement);
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    int typeId = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.getTypeFromId(typeId, false));
                    tracker.sendMetadataBuffer(entityId);
                });
                handler(wrapper -> {
                    List<Metadata> metadataList = (List) wrapper.get(Types1_8.METADATA_LIST, 0);
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) wrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        replacement.updateMetadata(metadataList);
                    } else if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_PAINTING, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID, Type.NOTHING);
                map(Type.STRING);
                map(Type.POSITION);
                map(Type.BYTE, Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PAINTING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_PLAYER, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.SpawnPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    packetWrapper.write(Type.SHORT, (short) 0);
                });
                map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                handler(wrapper -> {
                    List<Metadata> metadataList = (List) wrapper.get(Types1_8.METADATA_LIST, 0);
                    MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadataList);
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PLAYER);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
    }
}
