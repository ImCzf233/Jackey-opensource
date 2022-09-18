package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/EntityPackets1_13.class */
public class EntityPackets1_13 extends LegacyEntityRewriter<Protocol1_12_2To1_13> {
    public EntityPackets1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.PLAYER_POSITION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                            return;
                        }
                        PlayerPositionStorage1_13 playerStorage = (PlayerPositionStorage1_13) wrapper.user().get(PlayerPositionStorage1_13.class);
                        byte bitField = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        playerStorage.setX(toSet(bitField, 0, playerStorage.getX(), ((Double) wrapper.get(Type.DOUBLE, 0)).doubleValue()));
                        playerStorage.setY(toSet(bitField, 1, playerStorage.getY(), ((Double) wrapper.get(Type.DOUBLE, 1)).doubleValue()));
                        playerStorage.setZ(toSet(bitField, 2, playerStorage.getZ(), ((Double) wrapper.get(Type.DOUBLE, 2)).doubleValue()));
                    }

                    private double toSet(int field, int bitIndex, double origin, double packetValue) {
                        return (field & (1 << bitIndex)) != 0 ? origin + packetValue : packetValue;
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.2
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
                handler(EntityPackets1_13.this.getObjectTrackerHandler());
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Optional<Entity1_13Types.ObjectType> optionalType = Entity1_13Types.ObjectType.findById(((Byte) wrapper.get(Type.BYTE, 0)).byteValue());
                        if (!optionalType.isPresent()) {
                            return;
                        }
                        Entity1_13Types.ObjectType type = optionalType.get();
                        if (type == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                            int blockState = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int combined = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState);
                            wrapper.set(Type.INT, 0, Integer.valueOf(((combined >> 4) & 4095) | ((combined & 15) << 12)));
                        } else if (type != Entity1_13Types.ObjectType.ITEM_FRAME) {
                            if (type == Entity1_13Types.ObjectType.TRIDENT) {
                                wrapper.set(Type.BYTE, 0, Byte.valueOf((byte) Entity1_13Types.ObjectType.TIPPED_ARROW.getId()));
                            }
                        } else {
                            int data = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            switch (data) {
                                case 3:
                                    data = 0;
                                    break;
                                case 4:
                                    data = 1;
                                    break;
                                case 5:
                                    data = 3;
                                    break;
                            }
                            wrapper.set(Type.INT, 0, Integer.valueOf(data));
                        }
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.3
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
                map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        EntityType entityType = Entity1_13Types.getTypeFromId(type, false);
                        EntityPackets1_13.this.tracker(wrapper.user()).addEntity(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue(), entityType);
                        int oldId = EntityTypeMapping.getOldId(type);
                        if (oldId == -1) {
                            if (!EntityPackets1_13.this.hasData(entityType)) {
                                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + type + "/" + entityType);
                                return;
                            }
                            return;
                        }
                        wrapper.set(Type.VAR_INT, 1, Integer.valueOf(oldId));
                    }
                });
                handler(EntityPackets1_13.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                handler(EntityPackets1_13.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SPAWN_PAINTING, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                handler(EntityPackets1_13.this.getTrackerHandler(Entity1_13Types.EntityType.PAINTING, Type.VAR_INT));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int motive = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        String title = PaintingMapping.getStringId(motive);
                        wrapper.write(Type.STRING, title);
                    }
                });
            }
        });
        registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(EntityPackets1_13.this.getDimensionHandler(0));
                handler(wrapper -> {
                    ((BackwardsBlockStorage) wrapper.user().get(BackwardsBlockStorage.class)).clear();
                });
            }
        });
        registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.FACE_PLAYER, (ClientboundPackets1_13) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.cancel();
                        if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                            return;
                        }
                        int anchor = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        double x = ((Double) wrapper.read(Type.DOUBLE)).doubleValue();
                        double y = ((Double) wrapper.read(Type.DOUBLE)).doubleValue();
                        double z = ((Double) wrapper.read(Type.DOUBLE)).doubleValue();
                        PlayerPositionStorage1_13 positionStorage = (PlayerPositionStorage1_13) wrapper.user().get(PlayerPositionStorage1_13.class);
                        PacketWrapper positionAndLook = wrapper.create(ClientboundPackets1_12_1.PLAYER_POSITION);
                        positionAndLook.write(Type.DOUBLE, Double.valueOf(0.0d));
                        positionAndLook.write(Type.DOUBLE, Double.valueOf(0.0d));
                        positionAndLook.write(Type.DOUBLE, Double.valueOf(0.0d));
                        EntityPositionHandler.writeFacingDegrees(positionAndLook, positionStorage.getX(), anchor == 1 ? positionStorage.getY() + 1.62d : positionStorage.getY(), positionStorage.getZ(), x, y, z);
                        positionAndLook.write(Type.BYTE, (byte) 7);
                        positionAndLook.write(Type.VAR_INT, -1);
                        positionAndLook.send(Protocol1_12_2To1_13.class);
                    }
                });
            }
        });
        if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
            PacketRemapper movementRemapper = new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13.8
                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
                public void registerMap() {
                    map(Type.DOUBLE);
                    map(Type.DOUBLE);
                    map(Type.DOUBLE);
                    handler(wrapper -> {
                        ((PlayerPositionStorage1_13) wrapper.user().get(PlayerPositionStorage1_13.class)).setCoordinates(wrapper, false);
                    });
                }
            };
            ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.PLAYER_POSITION, movementRemapper);
            ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.PLAYER_POSITION_AND_ROTATION, movementRemapper);
            ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.VEHICLE_MOVE, movementRemapper);
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        mapEntityTypeWithData(Entity1_13Types.EntityType.DROWNED, Entity1_13Types.EntityType.ZOMBIE_VILLAGER).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.COD, Entity1_13Types.EntityType.SQUID).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.SALMON, Entity1_13Types.EntityType.SQUID).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.PUFFERFISH, Entity1_13Types.EntityType.SQUID).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.TROPICAL_FISH, Entity1_13Types.EntityType.SQUID).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.PHANTOM, Entity1_13Types.EntityType.PARROT).plainName().spawnMetadata(storage -> {
            storage.add(new Metadata(15, MetaType1_12.VarInt, 3));
        });
        mapEntityTypeWithData(Entity1_13Types.EntityType.DOLPHIN, Entity1_13Types.EntityType.SQUID).plainName();
        mapEntityTypeWithData(Entity1_13Types.EntityType.TURTLE, Entity1_13Types.EntityType.OCELOT).plainName();
        filter().handler(event, meta -> {
            int typeId = meta.metaType().typeId();
            if (typeId == 5) {
                meta.setTypeAndValue(MetaType1_12.String, meta.getValue() != null ? meta.getValue().toString() : "");
            } else if (typeId == 6) {
                Item item = (Item) meta.getValue();
                meta.setTypeAndValue(MetaType1_12.Slot, ((Protocol1_12_2To1_13) this.protocol).getItemRewriter().handleItemToClient(item));
            } else if (typeId == 15) {
                event.cancel();
            } else if (typeId > 5) {
                meta.setMetaType(MetaType1_12.byId(typeId - 1));
            }
        });
        filter().filterFamily(Entity1_13Types.EntityType.ENTITY).index(2).handler(event2, meta2 -> {
            String value = meta2.getValue().toString();
            if (!value.isEmpty()) {
                meta2.setValue(ChatRewriter.jsonToLegacyText(value));
            }
        });
        filter().filterFamily(Entity1_13Types.EntityType.ZOMBIE).removeIndex(15);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(13);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(14);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(15);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(16);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(17);
        filter().type(Entity1_13Types.EntityType.TURTLE).cancel(18);
        filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(12);
        filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(13);
        filter().type(Entity1_13Types.EntityType.PHANTOM).cancel(12);
        filter().type(Entity1_13Types.EntityType.BOAT).cancel(12);
        filter().type(Entity1_13Types.EntityType.TRIDENT).cancel(7);
        filter().type(Entity1_13Types.EntityType.WOLF).index(17).handler(event3, meta3 -> {
            meta3.setValue(Integer.valueOf(15 - ((Integer) meta3.getValue()).intValue()));
        });
        filter().type(Entity1_13Types.EntityType.AREA_EFFECT_CLOUD).index(9).handler(event4, meta4 -> {
            Particle particle = (Particle) meta4.getValue();
            ParticleMapping.ParticleData data = ParticleMapping.getMapping(particle.getId());
            int firstArg = 0;
            int secondArg = 0;
            int[] particleArgs = data.rewriteMeta((Protocol1_12_2To1_13) this.protocol, particle.getArguments());
            if (particleArgs != null && particleArgs.length != 0) {
                if (data.getHandler().isBlockHandler() && particleArgs[0] == 0) {
                    particleArgs[0] = 102;
                }
                firstArg = particleArgs[0];
                secondArg = particleArgs.length == 2 ? particleArgs[1] : 0;
            }
            event4.createExtraMeta(new Metadata(9, MetaType1_12.VarInt, Integer.valueOf(data.getHistoryId())));
            event4.createExtraMeta(new Metadata(10, MetaType1_12.VarInt, Integer.valueOf(firstArg)));
            event4.createExtraMeta(new Metadata(11, MetaType1_12.VarInt, Integer.valueOf(secondArg)));
            event4.cancel();
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, false);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter
    protected EntityType getObjectTypeFromId(int typeId) {
        return Entity1_13Types.getTypeFromId(typeId, true);
    }

    @Override // com.viaversion.viaversion.rewriter.EntityRewriter, com.viaversion.viaversion.api.rewriter.EntityRewriter
    public int newEntityId(int newId) {
        return EntityTypeMapping.getOldId(newId);
    }
}
