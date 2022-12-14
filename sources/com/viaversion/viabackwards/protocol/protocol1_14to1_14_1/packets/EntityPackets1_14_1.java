package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14to1_14_1/packets/EntityPackets1_14_1.class */
public class EntityPackets1_14_1 extends LegacyEntityRewriter<Protocol1_14To1_14_1> {
    public EntityPackets1_14_1(Protocol1_14To1_14_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        registerTracker(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, Entity1_14Types.EXPERIENCE_ORB);
        registerTracker(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, Entity1_14Types.LIGHTNING_BOLT);
        registerTracker(ClientboundPackets1_14.SPAWN_PAINTING, Entity1_14Types.PAINTING);
        registerTracker(ClientboundPackets1_14.SPAWN_PLAYER, Entity1_14Types.PLAYER);
        registerTracker(ClientboundPackets1_14.JOIN_GAME, Entity1_14Types.PLAYER, Type.INT);
        registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        ((Protocol1_14To1_14_1) this.protocol).registerClientbound((Protocol1_14To1_14_1) ClientboundPackets1_14.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(EntityPackets1_14_1.this.getTrackerHandler());
            }
        });
        ((Protocol1_14To1_14_1) this.protocol).registerClientbound((Protocol1_14To1_14_1) ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1.2
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
                map(Types1_14.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        EntityPackets1_14_1.this.tracker(wrapper.user()).addEntity(entityId, Entity1_14Types.getTypeFromId(type));
                        List<Metadata> metadata = (List) wrapper.get(Types1_14.METADATA_LIST, 0);
                        EntityPackets1_14_1.this.handleMetadata(entityId, metadata, wrapper.user());
                    }
                });
            }
        });
        registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        filter().type(Entity1_14Types.VILLAGER).cancel(15);
        filter().type(Entity1_14Types.VILLAGER).index(16).toIndex(15);
        filter().type(Entity1_14Types.WANDERING_TRADER).cancel(15);
    }

    @Override // com.viaversion.viaversion.api.rewriter.EntityRewriter
    public EntityType typeFromId(int typeId) {
        return Entity1_14Types.getTypeFromId(typeId);
    }
}
