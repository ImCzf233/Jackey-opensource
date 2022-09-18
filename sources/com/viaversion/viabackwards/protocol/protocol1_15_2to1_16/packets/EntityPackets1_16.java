package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.WolfDataMaskStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/EntityPackets1_16.class */
public class EntityPackets1_16 extends EntityRewriter<Protocol1_15_2To1_16> {
    private final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(Type.STRING, Type.INT) { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.1
        public Integer transform(PacketWrapper wrapper, String input) {
            boolean z = true;
            switch (input.hashCode()) {
                case -1526768685:
                    if (input.equals(WorldIdentifiers.NETHER_DEFAULT)) {
                        z = false;
                        break;
                    }
                    break;
                case 1104210353:
                    if (input.equals(WorldIdentifiers.OVERWORLD_DEFAULT)) {
                        z = true;
                        break;
                    }
                    break;
                case 1731133248:
                    if (input.equals(WorldIdentifiers.END_DEFAULT)) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    return -1;
                case true:
                case true:
                default:
                    return 0;
                case true:
                    return 1;
            }
        }
    };

    public EntityPackets1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.2
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
                map(Type.INT);
                handler(wrapper -> {
                    EntityType entityType = EntityPackets1_16.this.typeFromId(((Integer) wrapper.get(Type.VAR_INT, 1)).intValue());
                    if (entityType == Entity1_16Types.LIGHTNING_BOLT) {
                        wrapper.cancel();
                        PacketWrapper spawnLightningPacket = wrapper.create(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
                        spawnLightningPacket.write(Type.VAR_INT, (Integer) wrapper.get(Type.VAR_INT, 0));
                        spawnLightningPacket.write(Type.BYTE, (byte) 1);
                        spawnLightningPacket.write(Type.DOUBLE, (Double) wrapper.get(Type.DOUBLE, 0));
                        spawnLightningPacket.write(Type.DOUBLE, (Double) wrapper.get(Type.DOUBLE, 1));
                        spawnLightningPacket.write(Type.DOUBLE, (Double) wrapper.get(Type.DOUBLE, 2));
                        spawnLightningPacket.send(Protocol1_15_2To1_16.class);
                    }
                });
                handler(EntityPackets1_16.this.getSpawnTrackerWithDataHandler(Entity1_16Types.FALLING_BLOCK));
            }
        });
        registerSpawnTracker(ClientboundPackets1_16.SPAWN_MOB);
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(EntityPackets1_16.this.dimensionTransformer);
                handler(wrapper -> {
                    WorldNameTracker worldNameTracker = (WorldNameTracker) wrapper.user().get(WorldNameTracker.class);
                    String nextWorldName = (String) wrapper.read(Type.STRING);
                    wrapper.passthrough(Type.LONG);
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    wrapper.read(Type.BYTE);
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    int dimension = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    if (clientWorld.getEnvironment() != null && dimension == clientWorld.getEnvironment().getId() && (wrapper.user().isClientSide() || Via.getPlatform().isProxy() || wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion() || !nextWorldName.equals(worldNameTracker.getWorldName()))) {
                        PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN);
                        packet.write(Type.INT, Integer.valueOf(dimension == 0 ? -1 : 0));
                        packet.write(Type.LONG, 0L);
                        packet.write(Type.UNSIGNED_BYTE, (short) 0);
                        packet.write(Type.STRING, "default");
                        packet.send(Protocol1_15_2To1_16.class);
                    }
                    clientWorld.setEnvironment(dimension);
                    wrapper.write(Type.STRING, "default");
                    wrapper.read(Type.BOOLEAN);
                    if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                        wrapper.set(Type.STRING, 0, "flat");
                    }
                    wrapper.read(Type.BOOLEAN);
                    worldNameTracker.setWorldName(nextWorldName);
                });
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE, Type.NOTHING);
                map(Type.STRING_ARRAY, Type.NOTHING);
                map(Type.NBT, Type.NOTHING);
                map(EntityPackets1_16.this.dimensionTransformer);
                handler(wrapper -> {
                    WorldNameTracker worldNameTracker = (WorldNameTracker) wrapper.user().get(WorldNameTracker.class);
                    worldNameTracker.setWorldName((String) wrapper.read(Type.STRING));
                });
                map(Type.LONG);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper2 -> {
                    ClientWorld clientChunks = (ClientWorld) wrapper2.user().get(ClientWorld.class);
                    clientChunks.setEnvironment(((Integer) wrapper2.get(Type.INT, 1)).intValue());
                    EntityPackets1_16.this.tracker(wrapper2.user()).addEntity(((Integer) wrapper2.get(Type.INT, 0)).intValue(), Entity1_16Types.PLAYER);
                    wrapper2.write(Type.STRING, "default");
                    wrapper2.passthrough(Type.VAR_INT);
                    wrapper2.passthrough(Type.BOOLEAN);
                    wrapper2.passthrough(Type.BOOLEAN);
                    wrapper2.read(Type.BOOLEAN);
                    if (((Boolean) wrapper2.read(Type.BOOLEAN)).booleanValue()) {
                        wrapper2.set(Type.STRING, 0, "flat");
                    }
                });
            }
        });
        registerTracker(ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, Entity1_16Types.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_16.SPAWN_PAINTING, Entity1_16Types.PAINTING);
        registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_14.METADATA_LIST);
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int size = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        String attributeIdentifier = (String) wrapper.read(Type.STRING);
                        String oldKey = ((Protocol1_15_2To1_16) EntityPackets1_16.this.protocol).getMappingData().getAttributeMappings().get(attributeIdentifier);
                        wrapper.write(Type.STRING, oldKey != null ? oldKey : attributeIdentifier.replace("minecraft:", ""));
                        wrapper.passthrough(Type.DOUBLE);
                        int modifierSize = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int j = 0; j < modifierSize; j++) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.PLAYER_INFO, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int action = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                    int playerCount = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < playerCount; i++) {
                        packetWrapper.passthrough(Type.UUID);
                        if (action == 0) {
                            packetWrapper.passthrough(Type.STRING);
                            int properties = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                            for (int j = 0; j < properties; j++) {
                                packetWrapper.passthrough(Type.STRING);
                                packetWrapper.passthrough(Type.STRING);
                                if (((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                    packetWrapper.passthrough(Type.STRING);
                                }
                            }
                            packetWrapper.passthrough(Type.VAR_INT);
                            packetWrapper.passthrough(Type.VAR_INT);
                            if (((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                ((Protocol1_15_2To1_16) EntityPackets1_16.this.protocol).getTranslatableRewriter().processText((JsonElement) packetWrapper.passthrough(Type.COMPONENT));
                            }
                        } else if (action == 1) {
                            packetWrapper.passthrough(Type.VAR_INT);
                        } else if (action == 2) {
                            packetWrapper.passthrough(Type.VAR_INT);
                        } else if (action == 3 && ((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                            ((Protocol1_15_2To1_16) EntityPackets1_16.this.protocol).getTranslatableRewriter().processText((JsonElement) packetWrapper.passthrough(Type.COMPONENT));
                        }
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            JsonElement text;
            meta.setMetaType(Types1_14.META_TYPES.byId(meta.metaType().typeId()));
            MetaType type = meta.metaType();
            if (type == Types1_14.META_TYPES.itemType) {
                meta.setValue(((Protocol1_15_2To1_16) this.protocol).getItemRewriter().handleItemToClient((Item) meta.getValue()));
            } else if (type == Types1_14.META_TYPES.blockStateType) {
                meta.setValue(Integer.valueOf(((Protocol1_15_2To1_16) this.protocol).getMappingData().getNewBlockStateId(((Integer) meta.getValue()).intValue())));
            } else if (type == Types1_14.META_TYPES.particleType) {
                rewriteParticle((Particle) meta.getValue());
            } else if (type == Types1_14.META_TYPES.optionalComponentType && (text = (JsonElement) meta.value()) != null) {
                ((Protocol1_15_2To1_16) this.protocol).getTranslatableRewriter().processText(text);
            }
        });
        mapEntityType(Entity1_16Types.ZOMBIFIED_PIGLIN, Entity1_15Types.ZOMBIE_PIGMAN);
        mapTypes(Entity1_16Types.values(), Entity1_15Types.class);
        mapEntityTypeWithData(Entity1_16Types.HOGLIN, Entity1_16Types.COW).jsonName();
        mapEntityTypeWithData(Entity1_16Types.ZOGLIN, Entity1_16Types.COW).jsonName();
        mapEntityTypeWithData(Entity1_16Types.PIGLIN, Entity1_16Types.ZOMBIFIED_PIGLIN).jsonName();
        mapEntityTypeWithData(Entity1_16Types.STRIDER, Entity1_16Types.MAGMA_CUBE).jsonName();
        filter().type(Entity1_16Types.ZOGLIN).cancel(16);
        filter().type(Entity1_16Types.HOGLIN).cancel(15);
        filter().type(Entity1_16Types.PIGLIN).cancel(16);
        filter().type(Entity1_16Types.PIGLIN).cancel(17);
        filter().type(Entity1_16Types.PIGLIN).cancel(18);
        filter().type(Entity1_16Types.STRIDER).index(15).handler(event2, meta2 -> {
            boolean baby = ((Boolean) meta2.value()).booleanValue();
            meta2.setTypeAndValue(Types1_14.META_TYPES.varIntType, Integer.valueOf(baby ? 1 : 3));
        });
        filter().type(Entity1_16Types.STRIDER).cancel(16);
        filter().type(Entity1_16Types.STRIDER).cancel(17);
        filter().type(Entity1_16Types.STRIDER).cancel(18);
        filter().type(Entity1_16Types.FISHING_BOBBER).cancel(8);
        filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).cancel(8);
        filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).handler(event3, meta3 -> {
            if (event3.index() >= 8) {
                event3.setIndex(event3.index() + 1);
            }
        });
        filter().type(Entity1_16Types.WOLF).index(16).handler(event4, meta4 -> {
            byte mask = ((Byte) meta4.value()).byteValue();
            StoredEntityData data = tracker(event4.user()).entityData(event4.entityId());
            data.put(new WolfDataMaskStorage(mask));
        });
        filter().type(Entity1_16Types.WOLF).index(20).handler(event5, meta5 -> {
            WolfDataMaskStorage wolfData;
            StoredEntityData data = tracker(event5.user()).entityDataIfPresent(event5.entityId());
            byte previousMask = 0;
            if (data != null && (wolfData = (WolfDataMaskStorage) data.get(WolfDataMaskStorage.class)) != null) {
                previousMask = wolfData.tameableMask();
            }
            int angerTime = ((Integer) meta5.value()).intValue();
            byte tameableMask = (byte) (angerTime > 0 ? previousMask | 2 : previousMask & (-3));
            event5.createExtraMeta(new Metadata(16, Types1_14.META_TYPES.byteType, Byte.valueOf(tameableMask)));
            event5.cancel();
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_16Types.getTypeFromId(typeId);
    }
}
