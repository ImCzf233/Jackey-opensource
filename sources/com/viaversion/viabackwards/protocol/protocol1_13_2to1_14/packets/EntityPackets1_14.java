package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandler;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/EntityPackets1_14.class */
public class EntityPackets1_14 extends LegacyEntityRewriter<Protocol1_13_2To1_14> {
    private EntityPositionHandler positionHandler;

    public EntityPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol, Types1_13_2.META_TYPES.optionalComponentType, Types1_13_2.META_TYPES.booleanType);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    public void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
        super.addTrackedEntity(wrapper, entityId, type);
        if (type == Entity1_14Types.PAINTING) {
            Position position = (Position) wrapper.get(Type.POSITION, 0);
            this.positionHandler.cacheEntityPosition(wrapper, position.getX(), position.getY(), position.getZ(), true, false);
        } else if (wrapper.getId() != ClientboundPackets1_14.JOIN_GAME.getId()) {
            this.positionHandler.cacheEntityPosition(wrapper, true, false);
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        this.positionHandler = new EntityPositionHandler(this, EntityPositionStorage1_14.class, EntityPositionStorage1_14::new);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.ENTITY_STATUS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                    byte status = ((Byte) wrapper.passthrough(Type.BYTE)).byteValue();
                    if (status != 3) {
                        return;
                    }
                    EntityTracker tracker = EntityPackets1_14.this.tracker(wrapper.user());
                    EntityType entityType = tracker.entityType(entityId);
                    if (entityType != Entity1_14Types.PLAYER) {
                        return;
                    }
                    for (int i = 0; i <= 5; i++) {
                        PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_13.ENTITY_EQUIPMENT);
                        equipmentPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                        equipmentPacket.write(Type.VAR_INT, Integer.valueOf(i));
                        equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, null);
                        equipmentPacket.send(Protocol1_13_2To1_14.class);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.ENTITY_TELEPORT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                handler(wrapper -> {
                    EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, false, false);
                });
            }
        });
        PacketRemapper relativeMoveHandler = new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        double x = ((Short) wrapper.get(Type.SHORT, 0)).shortValue() / 4096.0d;
                        double y = ((Short) wrapper.get(Type.SHORT, 1)).shortValue() / 4096.0d;
                        double z = ((Short) wrapper.get(Type.SHORT, 2)).shortValue() / 4096.0d;
                        EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, x, y, z, false, true);
                    }
                });
            }
        };
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.ENTITY_POSITION, relativeMoveHandler);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.ENTITY_POSITION_AND_ROTATION, relativeMoveHandler);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT, Type.BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.INT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(EntityPackets1_14.this.getObjectTrackerHandler());
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Entity1_13Types.ObjectType objectType;
                        int id = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        int mappedId = EntityPackets1_14.this.newEntityId(id);
                        Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(mappedId, false);
                        if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT)) {
                            objectType = Entity1_13Types.ObjectType.MINECART;
                            int data = 0;
                            switch (entityType) {
                                case CHEST_MINECART:
                                    data = 1;
                                    break;
                                case FURNACE_MINECART:
                                    data = 2;
                                    break;
                                case TNT_MINECART:
                                    data = 3;
                                    break;
                                case SPAWNER_MINECART:
                                    data = 4;
                                    break;
                                case HOPPER_MINECART:
                                    data = 5;
                                    break;
                                case COMMAND_BLOCK_MINECART:
                                    data = 6;
                                    break;
                            }
                            if (data != 0) {
                                wrapper.set(Type.INT, 0, Integer.valueOf(data));
                            }
                        } else {
                            objectType = Entity1_13Types.ObjectType.fromEntityType(entityType).orElse(null);
                        }
                        if (objectType == null) {
                            return;
                        }
                        wrapper.set(Type.BYTE, 0, Byte.valueOf((byte) objectType.getId()));
                        int data2 = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        if (objectType == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                            int blockState = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int combined = ((Protocol1_13_2To1_14) EntityPackets1_14.this.protocol).getMappingData().getNewBlockStateId(blockState);
                            wrapper.set(Type.INT, 0, Integer.valueOf(combined));
                        } else if (entityType.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW)) {
                            wrapper.set(Type.INT, 0, Integer.valueOf(data2 + 1));
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.5
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
                map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        EntityType entityType = Entity1_14Types.getTypeFromId(type);
                        EntityPackets1_14.this.addTrackedEntity(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), entityType);
                        int oldId = EntityPackets1_14.this.newEntityId(type);
                        if (oldId == -1) {
                            EntityData entityData = EntityPackets1_14.this.entityDataForType(entityType);
                            if (entityData == null) {
                                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + type + "/" + entityType);
                                wrapper.cancel();
                                return;
                            }
                            wrapper.set(Type.VAR_INT, 1, Integer.valueOf(entityData.replacementId()));
                            return;
                        }
                        wrapper.set(Type.VAR_INT, 1, Integer.valueOf(oldId));
                    }
                });
                handler(EntityPackets1_14.this.getMobSpawnRewriter(Types1_13_2.METADATA_LIST));
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                handler(wrapper -> {
                    EntityPackets1_14.this.addTrackedEntity(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), Entity1_14Types.EXPERIENCE_ORB);
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                handler(wrapper -> {
                    EntityPackets1_14.this.addTrackedEntity(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), Entity1_14Types.LIGHTNING_BOLT);
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_PAINTING, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.POSITION1_14, Type.POSITION);
                map(Type.BYTE);
                handler(wrapper -> {
                    EntityPackets1_14.this.addTrackedEntity(wrapper, ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), Entity1_14Types.PAINTING);
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                handler(EntityPackets1_14.this.getTrackerAndMetaHandler(Types1_13_2.METADATA_LIST, Entity1_14Types.PLAYER));
                handler(wrapper -> {
                    EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, true, false);
                });
            }
        });
        registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(EntityPackets1_14.this.getTrackerHandler(Entity1_14Types.PLAYER, Type.INT));
                handler(EntityPackets1_14.this.getDimensionHandler(1));
                handler(wrapper -> {
                    short difficulty = ((DifficultyStorage) wrapper.user().get(DifficultyStorage.class)).getDifficulty();
                    wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    wrapper.passthrough(Type.STRING);
                    wrapper.read(Type.VAR_INT);
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    clientWorld.setEnvironment(dimensionId);
                    short difficulty = ((DifficultyStorage) wrapper.user().get(DifficultyStorage.class)).getDifficulty();
                    wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
                    ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).clear();
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        mapTypes(Entity1_14Types.values(), Entity1_13Types.EntityType.class);
        mapEntityTypeWithData(Entity1_14Types.CAT, Entity1_14Types.OCELOT).jsonName();
        mapEntityTypeWithData(Entity1_14Types.TRADER_LLAMA, Entity1_14Types.LLAMA).jsonName();
        mapEntityTypeWithData(Entity1_14Types.FOX, Entity1_14Types.WOLF).jsonName();
        mapEntityTypeWithData(Entity1_14Types.PANDA, Entity1_14Types.POLAR_BEAR).jsonName();
        mapEntityTypeWithData(Entity1_14Types.PILLAGER, Entity1_14Types.VILLAGER).jsonName();
        mapEntityTypeWithData(Entity1_14Types.WANDERING_TRADER, Entity1_14Types.VILLAGER).jsonName();
        mapEntityTypeWithData(Entity1_14Types.RAVAGER, Entity1_14Types.COW).jsonName();
        filter().handler(event, meta -> {
            int typeId = meta.metaType().typeId();
            if (typeId <= 15) {
                meta.setMetaType(Types1_13_2.META_TYPES.byId(typeId));
            }
            MetaType type = meta.metaType();
            if (type == Types1_13_2.META_TYPES.itemType) {
                Item item = (Item) meta.getValue();
                meta.setValue(((Protocol1_13_2To1_14) this.protocol).getItemRewriter().handleItemToClient(item));
            } else if (type == Types1_13_2.META_TYPES.blockStateType) {
                int blockstate = ((Integer) meta.value()).intValue();
                meta.setValue(Integer.valueOf(((Protocol1_13_2To1_14) this.protocol).getMappingData().getNewBlockStateId(blockstate)));
            }
        });
        filter().type(Entity1_14Types.PILLAGER).cancel(15);
        filter().type(Entity1_14Types.FOX).cancel(15);
        filter().type(Entity1_14Types.FOX).cancel(16);
        filter().type(Entity1_14Types.FOX).cancel(17);
        filter().type(Entity1_14Types.FOX).cancel(18);
        filter().type(Entity1_14Types.PANDA).cancel(15);
        filter().type(Entity1_14Types.PANDA).cancel(16);
        filter().type(Entity1_14Types.PANDA).cancel(17);
        filter().type(Entity1_14Types.PANDA).cancel(18);
        filter().type(Entity1_14Types.PANDA).cancel(19);
        filter().type(Entity1_14Types.PANDA).cancel(20);
        filter().type(Entity1_14Types.CAT).cancel(18);
        filter().type(Entity1_14Types.CAT).cancel(19);
        filter().type(Entity1_14Types.CAT).cancel(20);
        filter().handler(event2, meta2 -> {
            EntityType type = event2.entityType();
            if (type == null) {
                return;
            }
            if (type.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) || type == Entity1_14Types.RAVAGER || type == Entity1_14Types.WITCH) {
                int index = event2.index();
                if (index == 14) {
                    event2.cancel();
                } else if (index > 14) {
                    event2.setIndex(index - 1);
                }
            }
        });
        filter().type(Entity1_14Types.AREA_EFFECT_CLOUD).index(10).handler(event3, meta3 -> {
            rewriteParticle((Particle) meta3.getValue());
        });
        filter().type(Entity1_14Types.FIREWORK_ROCKET).index(8).handler(event4, meta4 -> {
            meta4.setMetaType(Types1_13_2.META_TYPES.varIntType);
            Integer value = (Integer) meta4.getValue();
            if (value == null) {
                meta4.setValue(0);
            }
        });
        filter().filterFamily(Entity1_14Types.ABSTRACT_ARROW).removeIndex(9);
        filter().type(Entity1_14Types.VILLAGER).cancel(15);
        MetaHandler villagerDataHandler = event5, meta5 -> {
            VillagerData villagerData = (VillagerData) meta5.getValue();
            meta5.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, Integer.valueOf(villagerDataToProfession(villagerData)));
            if (meta5.m222id() == 16) {
                event5.setIndex(15);
            }
        };
        filter().type(Entity1_14Types.ZOMBIE_VILLAGER).index(18).handler(villagerDataHandler);
        filter().type(Entity1_14Types.VILLAGER).index(16).handler(villagerDataHandler);
        filter().filterFamily(Entity1_14Types.ABSTRACT_SKELETON).index(13).handler(event6, meta6 -> {
            byte value = ((Byte) meta6.getValue()).byteValue();
            if ((value & 4) != 0) {
                event6.createExtraMeta(new Metadata(14, Types1_13_2.META_TYPES.booleanType, true));
            }
        });
        filter().filterFamily(Entity1_14Types.ZOMBIE).index(13).handler(event7, meta7 -> {
            byte value = ((Byte) meta7.getValue()).byteValue();
            if ((value & 4) != 0) {
                event7.createExtraMeta(new Metadata(16, Types1_13_2.META_TYPES.booleanType, true));
            }
        });
        filter().filterFamily(Entity1_14Types.ZOMBIE).addIndex(16);
        filter().filterFamily(Entity1_14Types.LIVINGENTITY).handler(event8, meta8 -> {
            int index = event8.index();
            if (index != 12) {
                if (index > 12) {
                    event8.setIndex(index - 1);
                    return;
                }
                return;
            }
            Position position = (Position) meta8.getValue();
            if (position != null) {
                PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_13.USE_BED, (ByteBuf) null, event8.user());
                wrapper.write(Type.VAR_INT, Integer.valueOf(event8.entityId()));
                wrapper.write(Type.POSITION, position);
                try {
                    wrapper.scheduleSend(Protocol1_13_2To1_14.class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            event8.cancel();
        });
        filter().removeIndex(6);
        filter().type(Entity1_14Types.OCELOT).index(13).handler(event9, meta9 -> {
            event9.setIndex(15);
            meta9.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, 0);
        });
        filter().type(Entity1_14Types.CAT).handler(event10, meta10 -> {
            if (event10.index() == 15) {
                meta10.setValue(1);
            } else if (event10.index() == 13) {
                meta10.setValue(Byte.valueOf((byte) (((Byte) meta10.getValue()).byteValue() & 4)));
            }
        });
        filter().handler(event11, meta11 -> {
            if (meta11.metaType().typeId() > 15) {
                throw new IllegalArgumentException("Unhandled metadata: " + meta11);
            }
        });
    }

    public int villagerDataToProfession(VillagerData data) {
        switch (data.getProfession()) {
            case 0:
            case 11:
            default:
                return 5;
            case 1:
            case 10:
            case 13:
            case 14:
                return 3;
            case 2:
            case 8:
                return 4;
            case 3:
            case 9:
                return 1;
            case 4:
                return 2;
            case 5:
            case 6:
            case 7:
            case 12:
                return 0;
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_14Types.getTypeFromId(typeId);
    }
}
