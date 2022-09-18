package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import io.netty.buffer.ByteBuf;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/packets/EntityPackets1_12.class */
public class EntityPackets1_12 extends LegacyEntityRewriter<Protocol1_11_1To1_12> {
    public EntityPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                handler(EntityPackets1_12.this.getObjectTrackerHandler());
                handler(EntityPackets1_12.this.getObjectRewriter(id -> {
                    return Entity1_12Types.ObjectType.findById(id.byteValue()).orElse(null);
                }));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(((Byte) wrapper.get(Type.BYTE, 0)).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int objType = objectData & 4095;
                            int data = (objectData >> 12) & 15;
                            Block block = ((Protocol1_11_1To1_12) EntityPackets1_12.this.protocol).getItemRewriter().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, Integer.valueOf(block.getId() | (block.getData() << 12)));
                        }
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, Entity1_12Types.EntityType.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, Entity1_12Types.EntityType.WEATHER);
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_12.METADATA_LIST);
                handler(EntityPackets1_12.this.getTrackerHandler());
                handler(EntityPackets1_12.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
            }
        });
        registerTracker(ClientboundPackets1_12.SPAWN_PAINTING, Entity1_12Types.EntityType.PAINTING);
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_12.METADATA_LIST);
                handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, Entity1_12Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(EntityPackets1_12.this.getTrackerHandler(Entity1_12Types.EntityType.PLAYER, Type.INT));
                handler(EntityPackets1_12.this.getDimensionHandler(1));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ShoulderTracker tracker = (ShoulderTracker) wrapper.user().get(ShoulderTracker.class);
                        tracker.setEntityId(((Integer) wrapper.get(Type.INT, 0)).intValue());
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.4.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        PacketWrapper wrapper = PacketWrapper.create(7, (ByteBuf) null, packetWrapper.user());
                        wrapper.write(Type.VAR_INT, 1);
                        wrapper.write(Type.STRING, "achievement.openInventory");
                        wrapper.write(Type.VAR_INT, 1);
                        wrapper.scheduleSend(Protocol1_11_1To1_12.class);
                    }
                });
            }
        });
        registerRespawn(ClientboundPackets1_12.RESPAWN);
        registerRemoveEntities(ClientboundPackets1_12.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.INT);
                handler(wrapper -> {
                    int size = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    int newSize = size;
                    for (int i = 0; i < size; i++) {
                        String key = (String) wrapper.read(Type.STRING);
                        if (key.equals("generic.flyingSpeed")) {
                            newSize--;
                            wrapper.read(Type.DOUBLE);
                            int modSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            for (int j = 0; j < modSize; j++) {
                                wrapper.read(Type.UUID);
                                wrapper.read(Type.DOUBLE);
                                wrapper.read(Type.BYTE);
                            }
                        } else {
                            wrapper.write(Type.STRING, key);
                            wrapper.passthrough(Type.DOUBLE);
                            int modSize2 = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            for (int j2 = 0; j2 < modSize2; j2++) {
                                wrapper.passthrough(Type.UUID);
                                wrapper.passthrough(Type.DOUBLE);
                                wrapper.passthrough(Type.BYTE);
                            }
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Type.INT, 0, Integer.valueOf(newSize));
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        mapEntityTypeWithData(Entity1_12Types.EntityType.PARROT, Entity1_12Types.EntityType.BAT).plainName().spawnMetadata(storage -> {
            storage.add(new Metadata(12, MetaType1_12.Byte, (byte) 0));
        });
        mapEntityTypeWithData(Entity1_12Types.EntityType.ILLUSION_ILLAGER, Entity1_12Types.EntityType.EVOCATION_ILLAGER).plainName();
        filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).cancel(12);
        filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
        filter().type(Entity1_12Types.EntityType.ILLUSION_ILLAGER).index(0).handler(event, meta -> {
            byte mask = ((Byte) meta.getValue()).byteValue();
            if ((mask & 32) == 32) {
                mask = (byte) (mask & (-33));
            }
            meta.setValue(Byte.valueOf(mask));
        });
        filter().filterFamily(Entity1_12Types.EntityType.PARROT).handler(event2, meta2 -> {
            StoredEntityData data = storedEntityData(event2);
            if (!data.has(ParrotStorage.class)) {
                data.put(new ParrotStorage());
            }
        });
        filter().type(Entity1_12Types.EntityType.PARROT).cancel(12);
        filter().type(Entity1_12Types.EntityType.PARROT).index(13).handler(event3, meta3 -> {
            StoredEntityData data = storedEntityData(event3);
            ParrotStorage storage2 = (ParrotStorage) data.get(ParrotStorage.class);
            boolean isSitting = (((Byte) meta3.getValue()).byteValue() & 1) == 1;
            boolean isTamed = (((Byte) meta3.getValue()).byteValue() & 4) == 4;
            if (storage2.isTamed() || isTamed) {
            }
            storage2.setTamed(isTamed);
            if (isSitting) {
                event3.setIndex(12);
                meta3.setValue((byte) 1);
                storage2.setSitting(true);
            } else if (storage2.isSitting()) {
                event3.setIndex(12);
                meta3.setValue((byte) 0);
                storage2.setSitting(false);
            } else {
                event3.cancel();
            }
        });
        filter().type(Entity1_12Types.EntityType.PARROT).cancel(14);
        filter().type(Entity1_12Types.EntityType.PARROT).cancel(15);
        filter().type(Entity1_12Types.EntityType.PLAYER).index(15).handler(event4, meta4 -> {
            CompoundTag tag = (CompoundTag) meta4.getValue();
            ShoulderTracker tracker = (ShoulderTracker) event4.user().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
                tracker.setLeftShoulder(null);
                tracker.update();
            } else if (tag.contains("id") && event4.entityId() == tracker.getEntityId()) {
                String id = (String) tag.get("id").getValue();
                if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
                    tracker.setLeftShoulder(id);
                    tracker.update();
                }
            }
            event4.cancel();
        });
        filter().type(Entity1_12Types.EntityType.PLAYER).index(16).handler(event5, meta5 -> {
            CompoundTag tag = (CompoundTag) event5.meta().getValue();
            ShoulderTracker tracker = (ShoulderTracker) event5.user().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getRightShoulder() != null) {
                tracker.setRightShoulder(null);
                tracker.update();
            } else if (tag.contains("id") && event5.entityId() == tracker.getEntityId()) {
                String id = (String) tag.get("id").getValue();
                if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
                    tracker.setRightShoulder(id);
                    tracker.update();
                }
            }
            event5.cancel();
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_12Types.getTypeFromId(typeId, false);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_12Types.getTypeFromId(typeId, true);
    }
}
