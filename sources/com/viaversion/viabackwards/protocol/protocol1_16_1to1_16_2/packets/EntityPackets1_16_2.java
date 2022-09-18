package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import java.util.Iterator;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_1to1_16_2/packets/EntityPackets1_16_2.class */
public class EntityPackets1_16_2 extends EntityRewriter<Protocol1_16_1To1_16_2> {
    private final Set<String> oldDimensions = Sets.newHashSet(new String[]{WorldIdentifiers.OVERWORLD_DEFAULT, WorldIdentifiers.NETHER_DEFAULT, WorldIdentifiers.END_DEFAULT});
    private boolean warned;

    public EntityPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        registerSpawnTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        registerTracker(ClientboundPackets1_16_2.SPAWN_EXPERIENCE_ORB, Entity1_16_2Types.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_16_2.SPAWN_PAINTING, Entity1_16_2Types.PAINTING);
        registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        registerRemoveEntities(ClientboundPackets1_16_2.DESTROY_ENTITIES);
        registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST);
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.EntityPackets1_16_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    boolean hardcore = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    short gamemode = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    if (hardcore) {
                        gamemode = (short) (gamemode | 8);
                    }
                    wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(gamemode));
                });
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                handler(wrapper2 -> {
                    CompoundTag registry = (CompoundTag) wrapper2.read(Type.NBT);
                    if (wrapper2.user().getProtocolInfo().getProtocolVersion() > ProtocolVersion.v1_15_2.getVersion()) {
                        if (!EntityPackets1_16_2.this.warned) {
                            EntityPackets1_16_2.this.warned = true;
                            ViaBackwards.getPlatform().getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
                        }
                    } else {
                        CompoundTag biomeRegistry = (CompoundTag) registry.get("minecraft:worldgen/biome");
                        ListTag biomes = (ListTag) biomeRegistry.get("value");
                        BiomeStorage biomeStorage = (BiomeStorage) wrapper2.user().get(BiomeStorage.class);
                        biomeStorage.clear();
                        Iterator<Tag> it = biomes.iterator();
                        while (it.hasNext()) {
                            Tag biome = it.next();
                            CompoundTag biomeCompound = (CompoundTag) biome;
                            StringTag name = (StringTag) biomeCompound.get("name");
                            NumberTag id = (NumberTag) biomeCompound.get("id");
                            biomeStorage.addBiome(name.getValue(), id.asInt());
                        }
                    }
                    wrapper2.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
                    CompoundTag dimensionData = (CompoundTag) wrapper2.read(Type.NBT);
                    wrapper2.write(Type.STRING, EntityPackets1_16_2.this.getDimensionFromData(dimensionData));
                });
                map(Type.STRING);
                map(Type.LONG);
                handler(wrapper3 -> {
                    int maxPlayers = ((Integer) wrapper3.read(Type.VAR_INT)).intValue();
                    wrapper3.write(Type.UNSIGNED_BYTE, Short.valueOf((short) Math.max(maxPlayers, 255)));
                });
                handler(EntityPackets1_16_2.this.getTrackerHandler(Entity1_16_2Types.PLAYER, Type.INT));
            }
        });
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.EntityPackets1_16_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    CompoundTag dimensionData = (CompoundTag) wrapper.read(Type.NBT);
                    wrapper.write(Type.STRING, EntityPackets1_16_2.this.getDimensionFromData(dimensionData));
                });
            }
        });
    }

    public String getDimensionFromData(CompoundTag dimensionData) {
        StringTag effectsLocation = (StringTag) dimensionData.get("effects");
        return (effectsLocation == null || !this.oldDimensions.contains(effectsLocation.getValue())) ? WorldIdentifiers.OVERWORLD_DEFAULT : effectsLocation.getValue();
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, Types1_16.META_TYPES.particleType, Types1_16.META_TYPES.optionalComponentType);
        mapTypes(Entity1_16_2Types.values(), Entity1_16Types.class);
        mapEntityTypeWithData(Entity1_16_2Types.PIGLIN_BRUTE, Entity1_16_2Types.PIGLIN).jsonName();
        filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(15).toIndex(16);
        filter().filterFamily(Entity1_16_2Types.ABSTRACT_PIGLIN).index(16).toIndex(15);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_16_2Types.getTypeFromId(typeId);
    }
}
