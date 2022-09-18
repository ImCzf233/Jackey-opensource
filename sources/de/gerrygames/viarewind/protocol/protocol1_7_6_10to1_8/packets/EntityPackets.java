package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.SHORT);
                map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                handler(packetWrapper -> {
                    Item item = (Item) packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    ItemRewriter.toClient(item);
                    packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
                });
                handler(packetWrapper2 -> {
                    if (((Short) packetWrapper2.get(Type.SHORT, 0)).shortValue() > 4) {
                        packetWrapper2.cancel();
                    }
                });
                handler(packetWrapper3 -> {
                    EntityTracker tracker;
                    UUID uuid;
                    if (!packetWrapper3.isCancelled() && (uuid = (tracker = (EntityTracker) packetWrapper3.user().get(EntityTracker.class)).getPlayerUUID(((Integer) packetWrapper3.get(Type.INT, 0)).intValue())) != null) {
                        Item[] equipment = tracker.getPlayerEquipment(uuid);
                        if (equipment == null) {
                            Item[] itemArr = new Item[5];
                            equipment = itemArr;
                            tracker.setPlayerEquipment(uuid, itemArr);
                        }
                        equipment[((Short) packetWrapper3.get(Type.SHORT, 0)).shortValue()] = (Item) packetWrapper3.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                        GameProfileStorage storage = (GameProfileStorage) packetWrapper3.user().get(GameProfileStorage.class);
                        GameProfileStorage.GameProfile profile = storage.get(uuid);
                        if (profile == null || profile.gamemode != 3) {
                            return;
                        }
                        packetWrapper3.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.USE_BED, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.COLLECT_ITEM, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_VELOCITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int[] entityIds = (int[]) packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    for (int entityId : entityIds) {
                        tracker.removeEntity(entityId);
                    }
                    while (entityIds.length > 127) {
                        int[] entityIds2 = new int[127];
                        System.arraycopy(entityIds, 0, entityIds2, 0, 127);
                        int[] temp = new int[entityIds.length - 127];
                        System.arraycopy(entityIds, 127, temp, 0, temp.length);
                        entityIds = temp;
                        PacketWrapper destroy = PacketWrapper.create(19, (ByteBuf) null, packetWrapper.user());
                        destroy.write(Types1_7_6_10.INT_ARRAY, entityIds2);
                        PacketUtil.sendPacket(destroy, Protocol1_7_6_10TO1_8.class);
                    }
                    packetWrapper.write(Types1_7_6_10.INT_ARRAY, entityIds);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_MOVEMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int x = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        int y = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                        int z = ((Byte) packetWrapper.get(Type.BYTE, 2)).byteValue();
                        replacement.relMove(x / 32.0d, y / 32.0d, z / 32.0d);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int yaw = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        int pitch = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                        replacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int x = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        int y = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                        int z = ((Byte) packetWrapper.get(Type.BYTE, 2)).byteValue();
                        int yaw = ((Byte) packetWrapper.get(Type.BYTE, 3)).byteValue();
                        int pitch = ((Byte) packetWrapper.get(Type.BYTE, 4)).byteValue();
                        replacement.relMove(x / 32.0d, y / 32.0d, z / 32.0d);
                        replacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = tracker.getClientEntityTypes().get(Integer.valueOf(entityId));
                    if (type == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
                        int y = ((Integer) packetWrapper.get(Type.INT, 2)).intValue();
                        packetWrapper.set(Type.INT, 2, Integer.valueOf(y + 12));
                    }
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper2.cancel();
                        int x = ((Integer) packetWrapper2.get(Type.INT, 1)).intValue();
                        int y = ((Integer) packetWrapper2.get(Type.INT, 2)).intValue();
                        int z = ((Integer) packetWrapper2.get(Type.INT, 3)).intValue();
                        int yaw = ((Byte) packetWrapper2.get(Type.BYTE, 0)).byteValue();
                        int pitch = ((Byte) packetWrapper2.get(Type.BYTE, 1)).byteValue();
                        replacement.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        replacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_HEAD_LOOK, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int yaw = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        replacement.setHeadYaw((yaw * 360.0f) / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    boolean leash = ((Boolean) packetWrapper.get(Type.BOOLEAN, 0)).booleanValue();
                    if (leash) {
                        return;
                    }
                    int passenger = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    int vehicle = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    tracker.setPassenger(vehicle, passenger);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                handler(wrapper -> {
                    List<Metadata> metadataList = (List) wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) wrapper.user().get(EntityTracker.class);
                    if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            wrapper.cancel();
                            replacement.updateMetadata(metadataList);
                            return;
                        }
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
                        if (metadataList.isEmpty()) {
                            wrapper.cancel();
                            return;
                        }
                        return;
                    }
                    tracker.addMetadataToBuffer(entityId, metadataList);
                    wrapper.cancel();
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.VAR_INT, Type.SHORT);
                map(Type.BYTE, Type.NOTHING);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.REMOVE_ENTITY_EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                map(Type.BYTE);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT, Type.INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        packetWrapper.cancel();
                        return;
                    }
                    int amount = ((Integer) packetWrapper.passthrough(Type.INT)).intValue();
                    for (int i = 0; i < amount; i++) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.DOUBLE);
                        int modifierlength = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                        packetWrapper.write(Type.SHORT, Short.valueOf((short) modifierlength));
                        for (int j = 0; j < modifierlength; j++) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough(Type.DOUBLE);
                            packetWrapper.passthrough(Type.BYTE);
                        }
                    }
                });
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
    }
}
