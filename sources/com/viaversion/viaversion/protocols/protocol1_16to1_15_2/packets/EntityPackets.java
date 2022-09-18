package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/packets/EntityPackets.class */
public class EntityPackets {
    private static final PacketHandler DIMENSION_HANDLER = wrapper -> {
        String outputName;
        String dimensionName;
        WorldIdentifiers map = Via.getConfig().get1_16WorldNamesMap();
        WorldIdentifiers userMap = (WorldIdentifiers) wrapper.user().get(WorldIdentifiers.class);
        if (userMap != null) {
            map = userMap;
        }
        int dimension = ((Integer) wrapper.read(Type.INT)).intValue();
        switch (dimension) {
            case -1:
                dimensionName = WorldIdentifiers.NETHER_DEFAULT;
                outputName = map.nether();
                break;
            case 0:
                dimensionName = WorldIdentifiers.OVERWORLD_DEFAULT;
                outputName = map.overworld();
                break;
            case 1:
                dimensionName = WorldIdentifiers.END_DEFAULT;
                outputName = map.end();
                break;
            default:
                Via.getPlatform().getLogger().warning("Invalid dimension id: " + dimension);
                dimensionName = WorldIdentifiers.OVERWORLD_DEFAULT;
                outputName = map.overworld();
                break;
        }
        wrapper.write(Type.STRING, dimensionName);
        wrapper.write(Type.STRING, outputName);
    };
    public static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
    private static final String[] WORLD_NAMES = {WorldIdentifiers.OVERWORLD_DEFAULT, WorldIdentifiers.NETHER_DEFAULT, WorldIdentifiers.END_DEFAULT};

    static {
        ListTag list = new ListTag(CompoundTag.class);
        list.add(createOverworldEntry());
        list.add(createOverworldCavesEntry());
        list.add(createNetherEntry());
        list.add(createEndEntry());
        DIMENSIONS_TAG.put("dimension", list);
    }

    private static CompoundTag createOverworldEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("name", new StringTag(WorldIdentifiers.OVERWORLD_DEFAULT));
        tag.put("has_ceiling", new ByteTag((byte) 0));
        addSharedOverwaldEntries(tag);
        return tag;
    }

    private static CompoundTag createOverworldCavesEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("name", new StringTag("minecraft:overworld_caves"));
        tag.put("has_ceiling", new ByteTag((byte) 1));
        addSharedOverwaldEntries(tag);
        return tag;
    }

    private static void addSharedOverwaldEntries(CompoundTag tag) {
        tag.put("piglin_safe", new ByteTag((byte) 0));
        tag.put("natural", new ByteTag((byte) 1));
        tag.put("ambient_light", new FloatTag(0.0f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
        tag.put("respawn_anchor_works", new ByteTag((byte) 0));
        tag.put("has_skylight", new ByteTag((byte) 1));
        tag.put("bed_works", new ByteTag((byte) 1));
        tag.put("has_raids", new ByteTag((byte) 1));
        tag.put("logical_height", new IntTag(256));
        tag.put("shrunk", new ByteTag((byte) 0));
        tag.put("ultrawarm", new ByteTag((byte) 0));
    }

    private static CompoundTag createNetherEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("piglin_safe", new ByteTag((byte) 1));
        tag.put("natural", new ByteTag((byte) 0));
        tag.put("ambient_light", new FloatTag(0.1f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
        tag.put("respawn_anchor_works", new ByteTag((byte) 1));
        tag.put("has_skylight", new ByteTag((byte) 0));
        tag.put("bed_works", new ByteTag((byte) 0));
        tag.put("fixed_time", new LongTag(18000L));
        tag.put("has_raids", new ByteTag((byte) 0));
        tag.put("name", new StringTag(WorldIdentifiers.NETHER_DEFAULT));
        tag.put("logical_height", new IntTag(128));
        tag.put("shrunk", new ByteTag((byte) 1));
        tag.put("ultrawarm", new ByteTag((byte) 1));
        tag.put("has_ceiling", new ByteTag((byte) 1));
        return tag;
    }

    private static CompoundTag createEndEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("piglin_safe", new ByteTag((byte) 0));
        tag.put("natural", new ByteTag((byte) 0));
        tag.put("ambient_light", new FloatTag(0.0f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
        tag.put("respawn_anchor_works", new ByteTag((byte) 0));
        tag.put("has_skylight", new ByteTag((byte) 0));
        tag.put("bed_works", new ByteTag((byte) 0));
        tag.put("fixed_time", new LongTag(6000L));
        tag.put("has_raids", new ByteTag((byte) 1));
        tag.put("name", new StringTag(WorldIdentifiers.END_DEFAULT));
        tag.put("logical_height", new IntTag(256));
        tag.put("shrunk", new ByteTag((byte) 0));
        tag.put("ultrawarm", new ByteTag((byte) 0));
        tag.put("has_ceiling", new ByteTag((byte) 0));
        return tag;
    }

    public static void register(final Protocol1_16To1_15_2 protocol) {
        MetadataRewriter1_16To1_15_2 metadataRewriter = (MetadataRewriter1_16To1_15_2) protocol.get(MetadataRewriter1_16To1_15_2.class);
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, (ClientboundPackets1_15) ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    byte type = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                    if (type != 1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(entityId, Entity1_16Types.LIGHTNING_BOLT);
                    wrapper.write(Type.UUID, UUID.randomUUID());
                    wrapper.write(Type.VAR_INT, Integer.valueOf(Entity1_16Types.LIGHTNING_BOLT.getId()));
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.write(Type.BYTE, (byte) 0);
                    wrapper.write(Type.BYTE, (byte) 0);
                    wrapper.write(Type.INT, 0);
                    wrapper.write(Type.SHORT, (short) 0);
                    wrapper.write(Type.SHORT, (short) 0);
                    wrapper.write(Type.SHORT, (short) 0);
                });
            }
        });
        metadataRewriter.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_16Types.FALLING_BLOCK);
        metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
        metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(EntityPackets.DIMENSION_HANDLER);
                map(Type.LONG);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte) -1);
                    String levelType = (String) wrapper.read(Type.STRING);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(levelType.equals("flat")));
                    wrapper.write(Type.BOOLEAN, true);
                });
            }
        });
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte) -1);
                    wrapper.write(Type.STRING_ARRAY, Arrays.copyOf(EntityPackets.WORLD_NAMES, EntityPackets.WORLD_NAMES.length));
                    wrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG.clone());
                });
                handler(EntityPackets.DIMENSION_HANDLER);
                map(Type.LONG);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper2 -> {
                    wrapper2.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(((Integer) wrapper2.get(Type.INT, 0)).intValue(), Entity1_16Types.PLAYER);
                    String type = (String) wrapper2.read(Type.STRING);
                    wrapper2.passthrough(Type.VAR_INT);
                    wrapper2.passthrough(Type.BOOLEAN);
                    wrapper2.passthrough(Type.BOOLEAN);
                    wrapper2.write(Type.BOOLEAN, false);
                    wrapper2.write(Type.BOOLEAN, Boolean.valueOf(type.equals("flat")));
                });
            }
        });
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol1_16To1_15_2 protocol1_16To1_15_2 = protocol;
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int size = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                    int actualSize = size;
                    for (int i = 0; i < size; i++) {
                        String key = (String) wrapper.read(Type.STRING);
                        String attributeIdentifier = (String) protocol1_16To1_15_2.getMappingData().getAttributeMappings().get(key);
                        if (attributeIdentifier == null) {
                            attributeIdentifier = "minecraft:" + key;
                            if (!MappingData.isValid1_13Channel(attributeIdentifier)) {
                                if (!Via.getConfig().isSuppressConversionWarnings()) {
                                    Via.getPlatform().getLogger().warning("Invalid attribute: " + key);
                                }
                                actualSize--;
                                wrapper.read(Type.DOUBLE);
                                int modifierSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                                for (int j = 0; j < modifierSize; j++) {
                                    wrapper.read(Type.UUID);
                                    wrapper.read(Type.DOUBLE);
                                    wrapper.read(Type.BYTE);
                                }
                            }
                        }
                        wrapper.write(Type.STRING, attributeIdentifier);
                        wrapper.passthrough(Type.DOUBLE);
                        int modifierSize2 = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int j2 = 0; j2 < modifierSize2; j2++) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                    if (size != actualSize) {
                        wrapper.set(Type.INT, 0, Integer.valueOf(actualSize));
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.ANIMATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16) wrapper.user().get(InventoryTracker1_16.class);
                    if (inventoryTracker.getInventory() != -1) {
                        wrapper.cancel();
                    }
                });
            }
        });
    }
}
