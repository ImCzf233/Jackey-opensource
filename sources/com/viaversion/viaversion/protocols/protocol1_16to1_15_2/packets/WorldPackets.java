package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Map;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(final Protocol1_16To1_15_2 protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.UPDATE_LIGHT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.write(Type.BOOLEAN, true);
                });
            }
        });
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol1_16To1_15_2 protocol1_16To1_15_2 = protocol;
                handler(wrapper -> {
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_15Type());
                    wrapper.write(new Chunk1_16Type(), chunk);
                    chunk.setIgnoreOldLightData(chunk.isFullChunk());
                    for (int s = 0; s < chunk.getSections().length; s++) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section != null) {
                            for (int i = 0; i < section.getPaletteSize(); i++) {
                                int old = section.getPaletteEntry(i);
                                section.setPaletteEntry(i, protocol1_16To1_15_2.getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Tag heightMapTag : heightMaps.values()) {
                        LongArrayTag heightMap = (LongArrayTag) heightMapTag;
                        int[] heightMapData = new int[256];
                        CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), i2, v -> {
                            heightMapData[i2] = v;
                        });
                        heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i3 -> {
                            return heightMapData[i3];
                        }));
                    }
                    if (chunk.getBlockEntities() == null) {
                        return;
                    }
                    for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                        WorldPackets.handleBlockEntity(blockEntity);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Position position = (Position) wrapper.passthrough(Type.POSITION1_14);
                    ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    CompoundTag tag = (CompoundTag) wrapper.passthrough(Type.NBT);
                    WorldPackets.handleBlockEntity(tag);
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
    }

    public static void handleBlockEntity(CompoundTag compoundTag) {
        StringTag idTag = (StringTag) compoundTag.get("id");
        if (idTag == null) {
            return;
        }
        String id = idTag.getValue();
        if (id.equals("minecraft:conduit")) {
            Tag targetUuidTag = compoundTag.remove("target_uuid");
            if (!(targetUuidTag instanceof StringTag)) {
                return;
            }
            UUID targetUuid = UUID.fromString((String) targetUuidTag.getValue());
            compoundTag.put("Target", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(targetUuid)));
        } else if (id.equals("minecraft:skull") && (compoundTag.get("Owner") instanceof CompoundTag)) {
            CompoundTag ownerTag = (CompoundTag) compoundTag.remove("Owner");
            StringTag ownerUuidTag = (StringTag) ownerTag.remove("Id");
            if (ownerUuidTag != null) {
                UUID ownerUuid = UUID.fromString(ownerUuidTag.getValue());
                ownerTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
            }
            CompoundTag skullOwnerTag = new CompoundTag();
            for (Map.Entry<String, Tag> entry : ownerTag.entrySet()) {
                skullOwnerTag.put(entry.getKey(), entry.getValue());
            }
            compoundTag.put("SkullOwner", skullOwnerTag);
        }
    }
}
