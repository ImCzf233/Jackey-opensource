package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
import com.viaversion.viaversion.rewriter.EntityRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/packets/EntityPackets.class */
public final class EntityPackets extends EntityRewriter<Protocol1_18To1_17_1> {
    public EntityPackets(Protocol1_18To1_17_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerMetadataRewriter(ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
        ((Protocol1_18To1_17_1) this.protocol).registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.EntityPackets.1
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
                handler(wrapper -> {
                    int chunkRadius = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    wrapper.write(Type.VAR_INT, Integer.valueOf(chunkRadius));
                });
                handler(EntityPackets.this.worldDataTrackerHandler(1));
                handler(EntityPackets.this.biomeSizeTracker());
            }
        });
        ((Protocol1_18To1_17_1) this.protocol).registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.NBT);
                map(Type.STRING);
                handler(wrapper -> {
                    String world = (String) wrapper.get(Type.STRING, 0);
                    EntityTracker tracker = EntityPackets.this.tracker(wrapper.user());
                    if (!world.equals(tracker.currentWorld())) {
                        ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).clear();
                    }
                });
                handler(EntityPackets.this.worldDataTrackerHandler(0));
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().handler(event, meta -> {
            meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
            if (meta.metaType() == Types1_18.META_TYPES.particleType) {
                Particle particle = (Particle) meta.getValue();
                if (particle.getId() == 2) {
                    particle.setId(3);
                    particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7754));
                } else if (particle.getId() == 3) {
                    particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, 7786));
                } else {
                    rewriteParticle(particle);
                }
            }
        });
        registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int type) {
        return Entity1_17Types.getTypeFromId(type);
    }
}
