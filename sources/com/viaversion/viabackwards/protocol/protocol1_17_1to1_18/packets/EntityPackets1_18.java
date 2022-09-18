package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_17_1to1_18/packets/EntityPackets1_18.class */
public final class EntityPackets1_18 extends EntityRewriter<Protocol1_17_1To1_18> {
    public EntityPackets1_18(Protocol1_17_1To1_18 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        registerMetadataRewriter(ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18.1
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
                map(Type.LONG);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                read(Type.VAR_INT);
                handler(EntityPackets1_18.this.worldDataTrackerHandler(1));
                handler(wrapper -> {
                    CompoundTag registry = (CompoundTag) wrapper.get(Type.NBT, 0);
                    CompoundTag biomeRegistry = (CompoundTag) registry.get("minecraft:worldgen/biome");
                    ListTag biomes = (ListTag) biomeRegistry.get("value");
                    for (Tag biome : biomes.getValue()) {
                        CompoundTag biomeCompound = (CompoundTag) ((CompoundTag) biome).get("element");
                        StringTag category = (StringTag) biomeCompound.get("category");
                        if (category.getValue().equals("mountain")) {
                            category.setValue("extreme_hills");
                        }
                        biomeCompound.put("depth", new FloatTag(0.125f));
                        biomeCompound.put("scale", new FloatTag(0.05f));
                    }
                    EntityPackets1_18.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
                });
            }
        });
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.NBT);
                map(Type.STRING);
                handler(EntityPackets1_18.this.worldDataTrackerHandler(0));
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
            MetaType type = meta.metaType();
            if (type == Types1_17.META_TYPES.particleType) {
                Particle particle = (Particle) meta.getValue();
                if (particle.getId() == 3) {
                    Particle.ParticleData data = particle.getArguments().remove(0);
                    int blockState = ((Integer) data.getValue()).intValue();
                    if (blockState == 7786) {
                        particle.setId(3);
                        return;
                    } else {
                        particle.setId(2);
                        return;
                    }
                }
                rewriteParticle(particle);
            }
        });
        registerMetaTypeHandler(Types1_17.META_TYPES.itemType, null, null, Types1_17.META_TYPES.optionalComponentType);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_17Types.getTypeFromId(typeId);
    }
}
