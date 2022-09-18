package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
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
import com.viaversion.viaversion.util.Pair;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_STATUS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(packetWrapper -> {
                    byte status = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    if (status > 23) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.write(Type.BYTE, Byte.valueOf(status));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_POSITION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int relX = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    int relY = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    int relZ = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        replacement.relMove(relX / 4096.0d, relY / 4096.0d, relZ / 4096.0d);
                        return;
                    }
                    Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockX()));
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockY()));
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockZ()));
                    boolean onGround = ((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue();
                    if (moves.length > 1) {
                        PacketWrapper secondPacket = PacketWrapper.create(21, (ByteBuf) null, packetWrapper.user());
                        secondPacket.write(Type.VAR_INT, (Integer) packetWrapper.get(Type.VAR_INT, 0));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockX()));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockY()));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockZ()));
                        secondPacket.write(Type.BOOLEAN, Boolean.valueOf(onGround));
                        PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int relX = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    int relY = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    int relZ = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        replacement.relMove(relX / 4096.0d, relY / 4096.0d, relZ / 4096.0d);
                        replacement.setYawPitch((((Byte) packetWrapper.read(Type.BYTE)).byteValue() * 360.0f) / 256.0f, (((Byte) packetWrapper.read(Type.BYTE)).byteValue() * 360.0f) / 256.0f);
                        return;
                    }
                    Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockX()));
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockY()));
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) moves[0].getBlockZ()));
                    byte yaw = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                    byte pitch = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                    boolean onGround = ((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue();
                    Entity1_10Types.EntityType type = ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        yaw = (byte) (yaw - 64);
                        packetWrapper.set(Type.BYTE, 3, Byte.valueOf(yaw));
                    }
                    if (moves.length > 1) {
                        PacketWrapper secondPacket = PacketWrapper.create(23, (ByteBuf) null, packetWrapper.user());
                        secondPacket.write(Type.VAR_INT, (Integer) packetWrapper.get(Type.VAR_INT, 0));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockX()));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockY()));
                        secondPacket.write(Type.BYTE, Byte.valueOf((byte) moves[1].getBlockZ()));
                        secondPacket.write(Type.BYTE, Byte.valueOf(yaw));
                        secondPacket.write(Type.BYTE, Byte.valueOf(pitch));
                        secondPacket.write(Type.BOOLEAN, Boolean.valueOf(onGround));
                        PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_ROTATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int yaw = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                        int pitch = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                        replacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    }
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    Entity1_10Types.EntityType type = ((EntityTracker) packetWrapper2.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = ((Byte) packetWrapper2.get(Type.BYTE, 0)).byteValue();
                        packetWrapper2.set(Type.BYTE, 0, Byte.valueOf((byte) (yaw - 64)));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.VEHICLE_MOVE, (ClientboundPackets1_9) ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    int vehicle = tracker.getVehicle(tracker.getPlayerId());
                    if (vehicle == -1) {
                        packetWrapper.cancel();
                    }
                    packetWrapper.write(Type.VAR_INT, Integer.valueOf(vehicle));
                });
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                handler(packetWrapper2 -> {
                    if (packetWrapper2.isCancelled()) {
                        return;
                    }
                    PlayerPosition position = (PlayerPosition) packetWrapper2.user().get(PlayerPosition.class);
                    double x = ((Integer) packetWrapper2.get(Type.INT, 0)).intValue() / 32.0d;
                    double y = ((Integer) packetWrapper2.get(Type.INT, 1)).intValue() / 32.0d;
                    double z = ((Integer) packetWrapper2.get(Type.INT, 2)).intValue() / 32.0d;
                    position.setPos(x, y, z);
                });
                create(Type.BOOLEAN, true);
                handler(packetWrapper3 -> {
                    int entityId = ((Integer) packetWrapper3.get(Type.VAR_INT, 0)).intValue();
                    Entity1_10Types.EntityType type = ((EntityTracker) packetWrapper3.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = ((Byte) packetWrapper3.get(Type.BYTE, 1)).byteValue();
                        packetWrapper3.set(Type.BYTE, 0, Byte.valueOf((byte) (yaw - 64)));
                        int y = ((Integer) packetWrapper3.get(Type.INT, 1)).intValue();
                        packetWrapper3.set(Type.INT, 1, Integer.valueOf(y + 10));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.DESTROY_ENTITIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT_ARRAY_PRIMITIVE);
                handler(packetWrapper -> {
                    int[] iArr;
                    EntityTracker tracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    for (int entityId : (int[]) packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                        tracker.removeEntity(entityId);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    int id = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    if (id > 23) {
                        packetWrapper.cancel();
                    }
                    if (id != 25 || ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue() != ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                        return;
                    }
                    Levitation levitation = (Levitation) packetWrapper.user().get(Levitation.class);
                    levitation.setActive(false);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_HEAD_LOOK, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
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
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_METADATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                handler(wrapper -> {
                    List<Metadata> metadataList = (List) wrapper.get(Types1_8.METADATA_LIST, 0);
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) wrapper.user().get(EntityTracker.class);
                    if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
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
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ATTACH_ENTITY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                create(Type.BOOLEAN, true);
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int slot = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    if (slot == 1) {
                        packetWrapper.cancel();
                    } else if (slot > 1) {
                        slot--;
                    }
                    packetWrapper.write(Type.SHORT, Short.valueOf((short) slot));
                });
                map(Type.ITEM);
                handler(packetWrapper2 -> {
                    packetWrapper2.set(Type.ITEM, 0, ItemRewriter.toClient((Item) packetWrapper2.get(Type.ITEM, 0)));
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SET_PASSENGERS, (ClientboundPackets1_9) null, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    EntityTracker entityTracker = (EntityTracker) packetWrapper.user().get(EntityTracker.class);
                    int vehicle = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    int count = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    ArrayList<Integer> passengers = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        passengers.add((Integer) packetWrapper.read(Type.VAR_INT));
                    }
                    List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
                    entityTracker.setPassengers(vehicle, passengers);
                    if (!oldPassengers.isEmpty()) {
                        for (Integer passenger : oldPassengers) {
                            PacketWrapper detach = PacketWrapper.create(27, (ByteBuf) null, packetWrapper.user());
                            detach.write(Type.INT, passenger);
                            detach.write(Type.INT, -1);
                            detach.write(Type.BOOLEAN, false);
                            PacketUtil.sendPacket(detach, Protocol1_8TO1_9.class);
                        }
                    }
                    int i2 = 0;
                    while (i2 < count) {
                        int v = i2 == 0 ? vehicle : passengers.get(i2 - 1).intValue();
                        int p = passengers.get(i2).intValue();
                        PacketWrapper attach = PacketWrapper.create(27, (ByteBuf) null, packetWrapper.user());
                        attach.write(Type.INT, Integer.valueOf(p));
                        attach.write(Type.INT, Integer.valueOf(v));
                        attach.write(Type.BOOLEAN, false);
                        PacketUtil.sendPacket(attach, Protocol1_8TO1_9.class);
                        i2++;
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_TELEPORT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    int entityId = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    Entity1_10Types.EntityType type = ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = ((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue();
                        packetWrapper.set(Type.BYTE, 0, Byte.valueOf((byte) (yaw - 64)));
                        int y = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                        packetWrapper.set(Type.INT, 1, Integer.valueOf(y + 10));
                    }
                });
                handler(packetWrapper2 -> {
                    int entityId = ((Integer) packetWrapper2.get(Type.VAR_INT, 0)).intValue();
                    ((EntityTracker) packetWrapper2.user().get(EntityTracker.class)).resetEntityOffset(entityId);
                });
                handler(packetWrapper3 -> {
                    int entityId = ((Integer) packetWrapper3.get(Type.VAR_INT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker) packetWrapper3.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper3.cancel();
                        int x = ((Integer) packetWrapper3.get(Type.INT, 0)).intValue();
                        int y = ((Integer) packetWrapper3.get(Type.INT, 1)).intValue();
                        int z = ((Integer) packetWrapper3.get(Type.INT, 2)).intValue();
                        int yaw = ((Byte) packetWrapper3.get(Type.BYTE, 0)).byteValue();
                        int pitch = ((Byte) packetWrapper3.get(Type.BYTE, 1)).byteValue();
                        replacement.setLocation(x / 32.0d, y / 32.0d, z / 32.0d);
                        replacement.setYawPitch((yaw * 360.0f) / 256.0f, (pitch * 360.0f) / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_PROPERTIES, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT);
                handler(packetWrapper -> {
                    boolean player = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue() == ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getPlayerId();
                    int size = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    int removed = 0;
                    for (int i = 0; i < size; i++) {
                        String key = (String) packetWrapper.read(Type.STRING);
                        boolean skip = !Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(key);
                        double value = ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue();
                        int modifiersize = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                        if (!skip) {
                            packetWrapper.write(Type.STRING, key);
                            packetWrapper.write(Type.DOUBLE, Double.valueOf(value));
                            packetWrapper.write(Type.VAR_INT, Integer.valueOf(modifiersize));
                        } else {
                            removed++;
                        }
                        ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<>();
                        for (int j = 0; j < modifiersize; j++) {
                            UUID uuid = (UUID) packetWrapper.read(Type.UUID);
                            double amount = ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue();
                            byte operation = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                            modifiers.add(new Pair<>(Byte.valueOf(operation), Double.valueOf(amount)));
                            if (!skip) {
                                packetWrapper.write(Type.UUID, uuid);
                                packetWrapper.write(Type.DOUBLE, Double.valueOf(amount));
                                packetWrapper.write(Type.BYTE, Byte.valueOf(operation));
                            }
                        }
                        if (player && key.equals("generic.attackSpeed")) {
                            ((Cooldown) packetWrapper.user().get(Cooldown.class)).setAttackSpeed(value, modifiers);
                        }
                    }
                    packetWrapper.set(Type.INT, 0, Integer.valueOf(size - removed));
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.ENTITY_EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.EntityPackets.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(packetWrapper -> {
                    int id = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    if (id > 23) {
                        packetWrapper.cancel();
                    }
                    if (id != 25 || ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue() != ((EntityTracker) packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                        return;
                    }
                    Levitation levitation = (Levitation) packetWrapper.user().get(Levitation.class);
                    levitation.setActive(true);
                    levitation.setAmplifier(((Byte) packetWrapper.get(Type.BYTE, 1)).byteValue());
                });
            }
        });
    }
}
