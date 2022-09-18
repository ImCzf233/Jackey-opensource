package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.google.common.collect.ImmutableList;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.Triple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/packets/EntityPackets.class */
public class EntityPackets {
    public static final ValueTransformer<Byte, Short> toNewShort = new ValueTransformer<Byte, Short>(Type.SHORT) { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.1
        public Short transform(PacketWrapper wrapper, Byte inputValue) {
            return Short.valueOf((short) (inputValue.byteValue() * 128));
        }
    };

    public static void register(final Protocol1_9To1_8 protocol) {
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BOOLEAN, new ValueTransformer<Boolean, Void>(Type.NOTHING) { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.2.1
                    public Void transform(PacketWrapper wrapper, Boolean inputValue) throws Exception {
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (!inputValue.booleanValue()) {
                            int passenger = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int vehicle = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                            wrapper.cancel();
                            PacketWrapper passengerPacket = wrapper.create(ClientboundPackets1_9.SET_PASSENGERS);
                            if (vehicle == -1) {
                                if (!tracker.getVehicleMap().containsKey(Integer.valueOf(passenger))) {
                                    return null;
                                }
                                passengerPacket.write(Type.VAR_INT, tracker.getVehicleMap().remove(Integer.valueOf(passenger)));
                                passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                            } else {
                                passengerPacket.write(Type.VAR_INT, Integer.valueOf(vehicle));
                                passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{passenger});
                                tracker.getVehicleMap().put(Integer.valueOf(passenger), Integer.valueOf(vehicle));
                            }
                            passengerPacket.send(Protocol1_9To1_8.class);
                            return null;
                        }
                        return null;
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (Via.getConfig().isHologramPatch()) {
                            EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            if (tracker.getKnownHolograms().contains(Integer.valueOf(entityID))) {
                                Double newValue = (Double) wrapper.get(Type.DOUBLE, 1);
                                wrapper.set(Type.DOUBLE, 1, Double.valueOf(newValue.doubleValue() + Via.getConfig().getHologramYOffset()));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BYTE, EntityPackets.toNewShort);
                map(Type.BOOLEAN);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.SHORT, new ValueTransformer<Short, Integer>(Type.VAR_INT) { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.6.1
                    public Integer transform(PacketWrapper wrapper, Short slot) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int receiverId = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
                        if (entityId == receiverId) {
                            return Integer.valueOf(slot.intValue() + 2);
                        }
                        return Integer.valueOf(slot.shortValue() > 0 ? slot.intValue() + 1 : slot.intValue());
                    }
                });
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.6.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item stack = (Item) wrapper.get(Type.ITEM, 0);
                        ItemRewriter.toClient(stack);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.6.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        Item stack = (Item) wrapper.get(Type.ITEM, 0);
                        if (stack != null && Protocol1_9To1_8.isSword(stack.identifier())) {
                            entityTracker.getValidBlocking().add(Integer.valueOf(entityID));
                        } else {
                            entityTracker.getValidBlocking().remove(Integer.valueOf(entityID));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (tracker.hasEntity(entityId)) {
                            ((MetadataRewriter1_9To1_8) protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                            return;
                        }
                        tracker.addMetadataToBuffer(entityId, metadataList);
                        wrapper.cancel();
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.7.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.handleMetadata(entityID, metadataList);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.7.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        if (metadataList.isEmpty()) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        boolean showParticles = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                        boolean newEffect = Via.getConfig().isNewEffectIndicator();
                        wrapper.write(Type.BYTE, Byte.valueOf((byte) (showParticles ? newEffect ? 2 : 1 : 0)));
                    }
                });
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.COMBAT_EVENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Integer) wrapper.get(Type.VAR_INT, 0)).intValue() == 2) {
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.passthrough(Type.INT);
                            Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (!Via.getConfig().isMinimizeCooldown()) {
                            return;
                        }
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (((Integer) wrapper.get(Type.VAR_INT, 0)).intValue() != tracker.getProvidedEntityId()) {
                            return;
                        }
                        int propertiesToRead = ((Integer) wrapper.read(Type.INT)).intValue();
                        Map<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> properties = new HashMap<>(propertiesToRead);
                        for (int i = 0; i < propertiesToRead; i++) {
                            String key = (String) wrapper.read(Type.STRING);
                            Double value = (Double) wrapper.read(Type.DOUBLE);
                            int modifiersToRead = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            List<Triple<UUID, Double, Byte>> modifiers = new ArrayList<>(modifiersToRead);
                            for (int j = 0; j < modifiersToRead; j++) {
                                modifiers.add(new Triple<>(wrapper.read(Type.UUID), wrapper.read(Type.DOUBLE), wrapper.read(Type.BYTE)));
                            }
                            properties.put(key, new Pair<>(value, modifiers));
                        }
                        properties.put("generic.attackSpeed", new Pair<>(Double.valueOf(15.9d), ImmutableList.of(new Triple(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), Double.valueOf(0.0d), (byte) 0), new Triple(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), Double.valueOf(0.0d), (byte) 2), new Triple(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), Double.valueOf(0.0d), (byte) 2))));
                        wrapper.write(Type.INT, Integer.valueOf(properties.size()));
                        for (Map.Entry<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> entry : properties.entrySet()) {
                            wrapper.write(Type.STRING, entry.getKey());
                            wrapper.write(Type.DOUBLE, entry.getValue().key());
                            wrapper.write(Type.VAR_INT, Integer.valueOf(entry.getValue().value().size()));
                            for (Triple<UUID, Double, Byte> modifier : entry.getValue().value()) {
                                wrapper.write(Type.UUID, modifier.first());
                                wrapper.write(Type.DOUBLE, modifier.second());
                                wrapper.write(Type.BYTE, modifier.third());
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.ENTITY_ANIMATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 3) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.ENTITY_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.12.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        if (action == 6 || action == 8) {
                            wrapper.cancel();
                        }
                        if (action == 7) {
                            wrapper.set(Type.VAR_INT, 1, 6);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_9To1_8) ServerboundPackets1_9.INTERACT_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets.13.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        if (type == 2) {
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        if (type == 0 || type == 2) {
                            int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            if (hand == 1) {
                                wrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
    }
}
