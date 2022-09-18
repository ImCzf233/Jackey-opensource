package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.List;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_10to1_11/packets/EntityPackets1_11.class */
public class EntityPackets1_11 extends LegacyEntityRewriter<Protocol1_10To1_11> {
    public EntityPackets1_11(Protocol1_10To1_11 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                handler(wrapper -> {
                    int type = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    if (type == 2002 || type == 2007) {
                        if (type == 2007) {
                            wrapper.set(Type.INT, 0, 2002);
                        }
                        int mappedData = PotionSplashHandler.getOldData(((Integer) wrapper.get(Type.INT, 1)).intValue());
                        if (mappedData != -1) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(mappedData));
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.2
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
                handler(EntityPackets1_11.this.getObjectTrackerHandler());
                handler(EntityPackets1_11.this.getObjectRewriter(id -> {
                    return Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null);
                }));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(((Byte) wrapper.get(Type.BYTE, 0)).byteValue());
                        if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            int objectData = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int objType = objectData & 4095;
                            int data = (objectData >> 12) & 15;
                            Block block = ((Protocol1_10To1_11) EntityPackets1_11.this.protocol).getItemRewriter().handleBlock(objType, data);
                            if (block == null) {
                                return;
                            }
                            wrapper.set(Type.INT, 0, Integer.valueOf(block.getId() | (block.getData() << 12)));
                        }
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT, Type.UNSIGNED_BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_9.METADATA_LIST);
                handler(EntityPackets1_11.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                    EntityType type = EntityPackets1_11.this.tracker(wrapper.user()).entityType(entityId);
                    List<Metadata> list = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                    EntityPackets1_11.this.handleMetadata(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), list, wrapper.user());
                    EntityData entityData = EntityPackets1_11.this.entityDataForType(type);
                    if (entityData != null) {
                        wrapper.set(Type.UNSIGNED_BYTE, 0, Short.valueOf((short) entityData.replacementId()));
                        if (entityData.hasBaseMeta()) {
                            entityData.defaultMeta().createMeta(new WrappedMetadata(list));
                        }
                    }
                    if (list.isEmpty()) {
                        list.add(new Metadata(0, MetaType1_9.Byte, (byte) 0));
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_9.METADATA_LIST);
                handler(EntityPackets1_11.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
                handler(wrapper -> {
                    List<Metadata> metadata = (List) wrapper.get(Types1_9.METADATA_LIST, 0);
                    if (metadata.isEmpty()) {
                        metadata.add(new Metadata(0, MetaType1_9.Byte, (byte) 0));
                    }
                });
            }
        });
        registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.ENTITY_STATUS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte b = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (b == 35) {
                            wrapper.clearPacket();
                            wrapper.setId(30);
                            wrapper.write(Type.UNSIGNED_BYTE, (short) 10);
                            wrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                        }
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        mapEntityTypeWithData(Entity1_11Types.EntityType.ELDER_GUARDIAN, Entity1_11Types.EntityType.GUARDIAN);
        mapEntityTypeWithData(Entity1_11Types.EntityType.WITHER_SKELETON, Entity1_11Types.EntityType.SKELETON).spawnMetadata(storage -> {
            storage.add(getSkeletonTypeMeta(1));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.STRAY, Entity1_11Types.EntityType.SKELETON).plainName().spawnMetadata(storage2 -> {
            storage2.add(getSkeletonTypeMeta(2));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.HUSK, Entity1_11Types.EntityType.ZOMBIE).plainName().spawnMetadata(storage3 -> {
            handleZombieType(storage3, 6);
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_VILLAGER, Entity1_11Types.EntityType.ZOMBIE).spawnMetadata(storage4 -> {
            handleZombieType(storage4, 1);
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage5 -> {
            storage5.add(getHorseMetaType(0));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage6 -> {
            storage6.add(getHorseMetaType(1));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.MULE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage7 -> {
            storage7.add(getHorseMetaType(2));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.SKELETON_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage8 -> {
            storage8.add(getHorseMetaType(4));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(storage9 -> {
            storage9.add(getHorseMetaType(3));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_FANGS, Entity1_11Types.EntityType.SHULKER);
        mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName();
        mapEntityTypeWithData(Entity1_11Types.EntityType.VEX, Entity1_11Types.EntityType.BAT).plainName();
        mapEntityTypeWithData(Entity1_11Types.EntityType.VINDICATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName().spawnMetadata(storage10 -> {
            storage10.add(new Metadata(13, MetaType1_9.VarInt, 4));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA, Entity1_11Types.EntityType.HORSE).plainName().spawnMetadata(storage11 -> {
            storage11.add(getHorseMetaType(1));
        });
        mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA_SPIT, Entity1_11Types.EntityType.SNOWBALL);
        mapObjectType(Entity1_11Types.ObjectType.LIAMA_SPIT, Entity1_11Types.ObjectType.SNOWBALL, -1);
        mapObjectType(Entity1_11Types.ObjectType.EVOCATION_FANGS, Entity1_11Types.ObjectType.FALLING_BLOCK, 4294);
        filter().filterFamily(Entity1_11Types.EntityType.GUARDIAN).index(12).handler(event, meta -> {
            boolean b = ((Boolean) meta.getValue()).booleanValue();
            int bitmask = b ? 2 : 0;
            if (event.entityType() == Entity1_11Types.EntityType.ELDER_GUARDIAN) {
                bitmask |= 4;
            }
            meta.setTypeAndValue(MetaType1_9.Byte, Byte.valueOf((byte) bitmask));
        });
        filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
        filter().filterFamily(Entity1_11Types.EntityType.ZOMBIE).handler(event2, meta2 -> {
            switch (meta2.m222id()) {
                case 13:
                    event2.cancel();
                    return;
                case 14:
                    event2.setIndex(15);
                    return;
                case 15:
                    event2.setIndex(14);
                    return;
                case 16:
                    event2.setIndex(13);
                    meta2.setValue(Integer.valueOf(1 + ((Integer) meta2.getValue()).intValue()));
                    return;
                default:
                    return;
            }
        });
        filter().type(Entity1_11Types.EntityType.EVOCATION_ILLAGER).index(12).handler(event3, meta3 -> {
            event3.setIndex(13);
            meta3.setTypeAndValue(MetaType1_9.VarInt, Integer.valueOf(((Byte) meta3.getValue()).intValue()));
        });
        filter().type(Entity1_11Types.EntityType.VEX).index(12).handler(event4, meta4 -> {
            meta4.setValue((byte) 0);
        });
        filter().type(Entity1_11Types.EntityType.VINDICATION_ILLAGER).index(12).handler(event5, meta5 -> {
            event5.setIndex(13);
            meta5.setTypeAndValue(MetaType1_9.VarInt, Integer.valueOf(((Number) meta5.getValue()).intValue() == 1 ? 2 : 4));
        });
        filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(13).handler(event6, meta6 -> {
            StoredEntityData data = storedEntityData(event6);
            byte b = ((Byte) meta6.getValue()).byteValue();
            if (data.has(ChestedHorseStorage.class) && ((ChestedHorseStorage) data.get(ChestedHorseStorage.class)).isChested()) {
                meta6.setValue(Byte.valueOf((byte) (b | 8)));
            }
        });
        filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).handler(event7, meta7 -> {
            StoredEntityData data = storedEntityData(event7);
            if (!data.has(ChestedHorseStorage.class)) {
                data.put(new ChestedHorseStorage());
            }
        });
        filter().type(Entity1_11Types.EntityType.HORSE).index(16).toIndex(17);
        filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).index(15).handler(event8, meta8 -> {
            StoredEntityData data = storedEntityData(event8);
            ChestedHorseStorage storage12 = (ChestedHorseStorage) data.get(ChestedHorseStorage.class);
            boolean b = ((Boolean) meta8.getValue()).booleanValue();
            storage12.setChested(b);
            event8.cancel();
        });
        filter().type(Entity1_11Types.EntityType.LIAMA).handler(event9, meta9 -> {
            StoredEntityData data = storedEntityData(event9);
            ChestedHorseStorage storage12 = (ChestedHorseStorage) data.get(ChestedHorseStorage.class);
            int index = event9.index();
            switch (index) {
                case 16:
                    storage12.setLiamaStrength(((Integer) meta9.getValue()).intValue());
                    event9.cancel();
                    return;
                case 17:
                    storage12.setLiamaCarpetColor(((Integer) meta9.getValue()).intValue());
                    event9.cancel();
                    return;
                case 18:
                    storage12.setLiamaVariant(((Integer) meta9.getValue()).intValue());
                    event9.cancel();
                    return;
                default:
                    return;
            }
        });
        filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
        filter().type(Entity1_11Types.EntityType.VILLAGER).index(13).handler(event10, meta10 -> {
            if (((Integer) meta10.getValue()).intValue() == 5) {
                meta10.setValue(0);
            }
        });
        filter().type(Entity1_11Types.EntityType.SHULKER).cancel(15);
    }

    private Metadata getSkeletonTypeMeta(int type) {
        return new Metadata(12, MetaType1_9.VarInt, Integer.valueOf(type));
    }

    private Metadata getZombieTypeMeta(int type) {
        return new Metadata(13, MetaType1_9.VarInt, Integer.valueOf(type));
    }

    private void handleZombieType(WrappedMetadata storage, int type) {
        Metadata meta = storage.get(13);
        if (meta == null) {
            storage.add(getZombieTypeMeta(type));
        }
    }

    private Metadata getHorseMetaType(int type) {
        return new Metadata(14, MetaType1_9.VarInt, Integer.valueOf(type));
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_11Types.getTypeFromId(typeId, false);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_11Types.getTypeFromId(typeId, true);
    }
}
