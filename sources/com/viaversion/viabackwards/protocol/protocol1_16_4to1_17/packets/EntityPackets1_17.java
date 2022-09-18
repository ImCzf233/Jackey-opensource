package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/EntityPackets1_17.class */
public final class EntityPackets1_17 extends EntityRewriter<Protocol1_16_4To1_17> {
    private boolean warned;

    public EntityPackets1_17(Protocol1_16_4To1_17 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        registerTrackerWithData(ClientboundPackets1_17.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        registerSpawnTracker(ClientboundPackets1_17.SPAWN_MOB);
        registerTracker(ClientboundPackets1_17.SPAWN_EXPERIENCE_ORB, Entity1_17Types.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_17.SPAWN_PAINTING, Entity1_17Types.PAINTING);
        registerTracker(ClientboundPackets1_17.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        registerMetadataRewriter(ClientboundPackets1_17.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_16.METADATA_LIST);
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.REMOVE_ENTITY, (ClientboundPackets1_17) ClientboundPackets1_16_2.DESTROY_ENTITIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    EntityPackets1_17.this.tracker(wrapper.user()).removeEntity(entityId);
                    int[] array = {entityId};
                    wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, array);
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                map(Type.NBT);
                map(Type.NBT);
                map(Type.STRING);
                handler(wrapper -> {
                    byte previousGamemode = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                    if (previousGamemode == -1) {
                        wrapper.set(Type.BYTE, 0, (byte) 0);
                    }
                });
                handler(EntityPackets1_17.this.getTrackerHandler(Entity1_17Types.PLAYER, Type.INT));
                handler(EntityPackets1_17.this.worldDataTrackerHandler(1));
                handler(wrapper2 -> {
                    CompoundTag registry = (CompoundTag) wrapper2.get(Type.NBT, 0);
                    CompoundTag biomeRegistry = (CompoundTag) registry.get("minecraft:worldgen/biome");
                    ListTag biomes = (ListTag) biomeRegistry.get("value");
                    Iterator<Tag> it = biomes.iterator();
                    while (it.hasNext()) {
                        Tag biome = it.next();
                        CompoundTag biomeCompound = (CompoundTag) ((CompoundTag) biome).get("element");
                        StringTag category = (StringTag) biomeCompound.get("category");
                        if (category.getValue().equalsIgnoreCase("underground")) {
                            category.setValue("none");
                        }
                    }
                    CompoundTag dimensionRegistry = (CompoundTag) registry.get("minecraft:dimension_type");
                    ListTag dimensions = (ListTag) dimensionRegistry.get("value");
                    Iterator<Tag> it2 = dimensions.iterator();
                    while (it2.hasNext()) {
                        Tag dimension = it2.next();
                        CompoundTag dimensionCompound = (CompoundTag) ((CompoundTag) dimension).get("element");
                        EntityPackets1_17.this.reduceExtendedHeight(dimensionCompound, false);
                    }
                    EntityPackets1_17.this.reduceExtendedHeight((CompoundTag) wrapper2.get(Type.NBT, 1), true);
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.NBT);
                map(Type.STRING);
                handler(EntityPackets1_17.this.worldDataTrackerHandler(0));
                handler(wrapper -> {
                    EntityPackets1_17.this.reduceExtendedHeight((CompoundTag) wrapper.get(Type.NBT, 0), true);
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.PLAYER_POSITION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.BYTE);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.ENTITY_PROPERTIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.write(Type.INT, (Integer) wrapper.read(Type.VAR_INT));
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_ENTER, ClientboundPackets1_16_2.COMBAT_EVENT, 0);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_END, ClientboundPackets1_16_2.COMBAT_EVENT, 1);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_KILL, ClientboundPackets1_16_2.COMBAT_EVENT, 2);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            meta.setMetaType(Types1_16.META_TYPES.byId(meta.metaType().typeId()));
            MetaType type = meta.metaType();
            if (type == Types1_16.META_TYPES.particleType) {
                Particle particle = (Particle) meta.getValue();
                if (particle.getId() == 16) {
                    particle.getArguments().subList(4, 7).clear();
                } else if (particle.getId() == 37) {
                    particle.setId(0);
                    particle.getArguments().clear();
                    return;
                }
                rewriteParticle(particle);
            } else if (type == Types1_16.META_TYPES.poseType) {
                int pose = ((Integer) meta.value()).intValue();
                if (pose == 6) {
                    meta.setValue(1);
                } else if (pose > 6) {
                    meta.setValue(Integer.valueOf(pose - 1));
                }
            }
        });
        registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, Types1_16.META_TYPES.optionalComponentType);
        mapTypes(Entity1_17Types.values(), Entity1_16_2Types.class);
        filter().type(Entity1_17Types.AXOLOTL).cancel(17);
        filter().type(Entity1_17Types.AXOLOTL).cancel(18);
        filter().type(Entity1_17Types.AXOLOTL).cancel(19);
        filter().type(Entity1_17Types.GLOW_SQUID).cancel(16);
        filter().type(Entity1_17Types.GOAT).cancel(17);
        mapEntityTypeWithData(Entity1_17Types.AXOLOTL, Entity1_17Types.TROPICAL_FISH).jsonName();
        mapEntityTypeWithData(Entity1_17Types.GOAT, Entity1_17Types.SHEEP).jsonName();
        mapEntityTypeWithData(Entity1_17Types.GLOW_SQUID, Entity1_17Types.SQUID).jsonName();
        mapEntityTypeWithData(Entity1_17Types.GLOW_ITEM_FRAME, Entity1_17Types.ITEM_FRAME);
        filter().type(Entity1_17Types.SHULKER).addIndex(17);
        filter().removeIndex(7);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_17Types.getTypeFromId(typeId);
    }

    public void reduceExtendedHeight(CompoundTag tag, boolean warn) {
        IntTag minY = (IntTag) tag.get("min_y");
        IntTag height = (IntTag) tag.get("height");
        IntTag logicalHeight = (IntTag) tag.get("logical_height");
        if (minY.asInt() != 0 || height.asInt() > 256 || logicalHeight.asInt() > 256) {
            if (warn && !this.warned) {
                ViaBackwards.getPlatform().getLogger().warning("Custom worlds heights are NOT SUPPORTED for 1.16 players and older and may lead to errors!");
                ViaBackwards.getPlatform().getLogger().warning("You have min/max set to " + minY.asInt() + "/" + height.asInt());
                this.warned = true;
            }
            height.setValue(Math.min(256, height.asInt()));
            logicalHeight.setValue(Math.min(256, logicalHeight.asInt()));
        }
    }
}
