package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
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
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/packets/SpawnPackets.class */
public class SpawnPackets {
    public static final ValueTransformer<Integer, Double> toNewDouble = new ValueTransformer<Integer, Double>(Type.DOUBLE) { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.1
        public Double transform(PacketWrapper wrapper, Integer inputValue) {
            return Double.valueOf(inputValue.intValue() / 32.0d);
        }
    };

    public static void register(final Protocol1_9To1_8 protocol) {
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int typeID = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, true));
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        short vX = 0;
                        short vY = 0;
                        short vZ = 0;
                        if (data > 0) {
                            vX = ((Short) wrapper.read(Type.SHORT)).shortValue();
                            vY = ((Short) wrapper.read(Type.SHORT)).shortValue();
                            vZ = ((Short) wrapper.read(Type.SHORT)).shortValue();
                        }
                        wrapper.write(Type.SHORT, Short.valueOf(vX));
                        wrapper.write(Type.SHORT, Short.valueOf(vY));
                        wrapper.write(Type.SHORT, Short.valueOf(vZ));
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2.4
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        final int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        final int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int typeID = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (Entity1_10Types.getTypeFromId(typeID, true) == Entity1_10Types.EntityType.SPLASH_POTION) {
                            PacketWrapper metaPacket = wrapper.create(ClientboundPackets1_9.ENTITY_METADATA, new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.2.4.1
                                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                                public void handle(PacketWrapper wrapper2) throws Exception {
                                    wrapper2.write(Type.VAR_INT, Integer.valueOf(entityID));
                                    ArrayList arrayList = new ArrayList();
                                    Item item = new DataItem(373, (byte) 1, (short) data, null);
                                    ItemRewriter.toClient(item);
                                    Metadata potion = new Metadata(5, MetaType1_9.Slot, item);
                                    arrayList.add(potion);
                                    wrapper2.write(Types1_9.METADATA_LIST, arrayList);
                                }
                            });
                            wrapper.send(Protocol1_9To1_8.class);
                            metaPacket.send(Protocol1_9To1_8.class);
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.SHORT);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.LIGHTNING);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.5.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int typeID = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.getTypeFromId(typeID, false));
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.5.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (tracker.hasEntity(entityId)) {
                            ((MetadataRewriter1_9To1_8) protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                            return;
                        }
                        Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                        metadataList.clear();
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.5.4
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.handleMetadata(entityID, metadataList);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.PAINTING);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.6.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
                    }
                });
                map(Type.STRING);
                map(Type.POSITION);
                map(Type.BYTE);
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addEntity(entityID, Entity1_10Types.EntityType.PLAYER);
                        tracker.sendMetadataBuffer(entityID);
                    }
                });
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.INT, SpawnPackets.toNewDouble);
                map(Type.BYTE);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.7.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short item = ((Short) wrapper.read(Type.SHORT)).shortValue();
                        if (item != 0) {
                            PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, (ByteBuf) null, wrapper.user());
                            packet.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                            packet.write(Type.VAR_INT, 0);
                            packet.write(Type.ITEM, new DataItem(item, (byte) 1, (short) 0, null));
                            try {
                                packet.send(Protocol1_9To1_8.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.7.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (tracker.hasEntity(entityId)) {
                            ((MetadataRewriter1_9To1_8) protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
                            return;
                        }
                        Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                        metadataList.clear();
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.7.4
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        List<Metadata> metadataList = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.handleMetadata(entityID, metadataList);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_9To1_8) ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT_ARRAY_PRIMITIVE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int[] entities = (int[]) wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
                        EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        for (int entity : entities) {
                            tracker.removeEntity(entity);
                        }
                    }
                });
            }
        });
    }
}
