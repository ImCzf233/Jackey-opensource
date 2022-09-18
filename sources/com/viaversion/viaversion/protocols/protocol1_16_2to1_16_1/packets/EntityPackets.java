package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.MetadataRewriter1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/packets/EntityPackets.class */
public class EntityPackets {
    public static void register(final Protocol1_16_2To1_16_1 protocol) {
        MetadataRewriter1_16_2To1_16_1 metadataRewriter = (MetadataRewriter1_16_2To1_16_1) protocol.get(MetadataRewriter1_16_2To1_16_1.class);
        metadataRewriter.registerTrackerWithData(ClientboundPackets1_16.SPAWN_ENTITY, Entity1_16_2Types.FALLING_BLOCK);
        metadataRewriter.registerTracker(ClientboundPackets1_16.SPAWN_MOB);
        metadataRewriter.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16_2Types.PLAYER);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST);
        metadataRewriter.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        protocol.registerClientbound((Protocol1_16_2To1_16_1) ClientboundPackets1_16.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.EntityPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    short gamemode = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf((gamemode & 8) != 0));
                    wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) (gamemode & (-9))));
                });
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1 = protocol;
                handler(wrapper2 -> {
                    wrapper2.read(Type.NBT);
                    wrapper2.write(Type.NBT, protocol1_16_2To1_16_1.getMappingData().getDimensionRegistry());
                    String dimensionType = (String) wrapper2.read(Type.STRING);
                    wrapper2.write(Type.NBT, EntityPackets.getDimensionData(dimensionType));
                });
                map(Type.STRING);
                map(Type.LONG);
                map(Type.UNSIGNED_BYTE, Type.VAR_INT);
                handler(wrapper3 -> {
                    wrapper3.user().getEntityTracker(Protocol1_16_2To1_16_1.class).addEntity(((Integer) wrapper3.get(Type.INT, 0)).intValue(), Entity1_16_2Types.PLAYER);
                });
            }
        });
        protocol.registerClientbound((Protocol1_16_2To1_16_1) ClientboundPackets1_16.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.EntityPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String dimensionType = (String) wrapper.read(Type.STRING);
                    wrapper.write(Type.NBT, EntityPackets.getDimensionData(dimensionType));
                });
            }
        });
    }

    public static CompoundTag getDimensionData(String dimensionType) {
        CompoundTag tag = Protocol1_16_2To1_16_1.MAPPINGS.getDimensionDataMap().get(dimensionType);
        if (tag == null) {
            Via.getPlatform().getLogger().severe("Could not get dimension data of " + dimensionType);
            throw new NullPointerException("Dimension data for " + dimensionType + " is null!");
        }
        return tag.clone();
    }
}
