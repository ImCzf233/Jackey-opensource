package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/packets/WorldPackets.class */
public class WorldPackets {
    public static final int SERVERSIDE_VIEW_DISTANCE = 64;
    private static final byte[] FULL_LIGHT = new byte[2048];
    public static int air;
    public static int voidAir;
    public static int caveAir;

    static {
        Arrays.fill(FULL_LIGHT, (byte) -1);
    }

    public static void register(final Protocol1_14To1_13_2 protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, null);
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.BLOCK_BREAK_ANIMATION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION, Type.POSITION1_14);
                map(Type.BYTE);
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockId(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue())));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(id)));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SERVER_DIFFICULTY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.EXPLOSION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        for (int i = 0; i < 3; i++) {
                            float coord = ((Float) wrapper.get(Type.FLOAT, i)).floatValue();
                            if (coord < 0.0f) {
                                wrapper.set(Type.FLOAT, i, Float.valueOf((int) coord));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ChunkSection[] sections;
                        ChunkSection[] sections2;
                        ChunkSection[] sections3;
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.read(new Chunk1_13Type(clientWorld));
                        wrapper.write(new Chunk1_14Type(), chunk);
                        int[] motionBlocking = new int[256];
                        int[] worldSurface = new int[256];
                        for (int s = 0; s < chunk.getSections().length; s++) {
                            ChunkSection section = chunk.getSections()[s];
                            if (section != null) {
                                boolean hasBlock = false;
                                for (int i = 0; i < section.getPaletteSize(); i++) {
                                    int old = section.getPaletteEntry(i);
                                    int newId = protocol.getMappingData().getNewBlockStateId(old);
                                    if (!hasBlock && newId != WorldPackets.air && newId != WorldPackets.voidAir && newId != WorldPackets.caveAir) {
                                        hasBlock = true;
                                    }
                                    section.setPaletteEntry(i, newId);
                                }
                                if (!hasBlock) {
                                    section.setNonAirBlocksCount(0);
                                } else {
                                    int nonAirBlockCount = 0;
                                    for (int x = 0; x < 16; x++) {
                                        for (int y = 0; y < 16; y++) {
                                            for (int z = 0; z < 16; z++) {
                                                int id = section.getFlatBlock(x, y, z);
                                                if (id != WorldPackets.air && id != WorldPackets.voidAir && id != WorldPackets.caveAir) {
                                                    nonAirBlockCount++;
                                                    worldSurface[x + (z * 16)] = y + (s * 16) + 1;
                                                }
                                                if (protocol.getMappingData().getMotionBlocking().contains(id)) {
                                                    motionBlocking[x + (z * 16)] = y + (s * 16) + 1;
                                                }
                                                if (Via.getConfig().isNonFullBlockLightFix() && protocol.getMappingData().getNonFullBlocks().contains(id)) {
                                                    WorldPackets.setNonFullLight(chunk, section, s, x, y, z);
                                                }
                                            }
                                        }
                                    }
                                    section.setNonAirBlocksCount(nonAirBlockCount);
                                }
                            }
                        }
                        CompoundTag heightMap = new CompoundTag();
                        heightMap.put("MOTION_BLOCKING", new LongArrayTag(WorldPackets.encodeHeightMap(motionBlocking)));
                        heightMap.put("WORLD_SURFACE", new LongArrayTag(WorldPackets.encodeHeightMap(worldSurface)));
                        chunk.setHeightMap(heightMap);
                        PacketWrapper lightPacket = wrapper.create(ClientboundPackets1_14.UPDATE_LIGHT);
                        lightPacket.write(Type.VAR_INT, Integer.valueOf(chunk.getX()));
                        lightPacket.write(Type.VAR_INT, Integer.valueOf(chunk.getZ()));
                        int skyLightMask = chunk.isFullChunk() ? 262143 : 0;
                        int blockLightMask = 0;
                        for (int i2 = 0; i2 < chunk.getSections().length; i2++) {
                            ChunkSection sec = chunk.getSections()[i2];
                            if (sec != null) {
                                if (!chunk.isFullChunk() && sec.getLight().hasSkyLight()) {
                                    skyLightMask |= 1 << (i2 + 1);
                                }
                                blockLightMask |= 1 << (i2 + 1);
                            }
                        }
                        lightPacket.write(Type.VAR_INT, Integer.valueOf(skyLightMask));
                        lightPacket.write(Type.VAR_INT, Integer.valueOf(blockLightMask));
                        lightPacket.write(Type.VAR_INT, 0);
                        lightPacket.write(Type.VAR_INT, 0);
                        if (chunk.isFullChunk()) {
                            lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                        }
                        for (ChunkSection section2 : chunk.getSections()) {
                            if (section2 == null || !section2.getLight().hasSkyLight()) {
                                if (chunk.isFullChunk()) {
                                    lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                                }
                            } else {
                                lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section2.getLight().getSkyLight());
                            }
                        }
                        if (chunk.isFullChunk()) {
                            lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.FULL_LIGHT);
                        }
                        for (ChunkSection section3 : chunk.getSections()) {
                            if (section3 != null) {
                                lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section3.getLight().getBlockLight());
                            }
                        }
                        EntityTracker1_14 entityTracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        int diffX = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
                        int diffZ = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());
                        if (entityTracker.isForceSendCenterChunk() || diffX >= 64 || diffZ >= 64) {
                            PacketWrapper fakePosLook = wrapper.create(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
                            fakePosLook.write(Type.VAR_INT, Integer.valueOf(chunk.getX()));
                            fakePosLook.write(Type.VAR_INT, Integer.valueOf(chunk.getZ()));
                            fakePosLook.send(Protocol1_14To1_13_2.class);
                            entityTracker.setChunkCenterX(chunk.getX());
                            entityTracker.setChunkCenterZ(chunk.getZ());
                        }
                        lightPacket.send(Protocol1_14To1_13_2.class);
                        for (ChunkSection section4 : chunk.getSections()) {
                            if (section4 != null) {
                                section4.setLight(null);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION, Type.POSITION1_14);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewItemId(data)));
                        } else if (id == 2001) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientChunks = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientChunks.setEnvironment(dimensionId);
                        int entityId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        Entity1_14Types entType = Entity1_14Types.PLAYER;
                        EntityTracker1_14 tracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        tracker.addEntity(entityId, entType);
                        tracker.setClientEntityId(entityId);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.9.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short difficulty = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                        PacketWrapper difficultyPacket = wrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                        difficultyPacket.write(Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
                        difficultyPacket.write(Type.BOOLEAN, false);
                        difficultyPacket.scheduleSend(protocol.getClass());
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.passthrough(Type.STRING);
                        wrapper.write(Type.VAR_INT, 64);
                    }
                });
                handler(wrapper -> {
                    wrapper.send(Protocol1_14To1_13_2.class);
                    wrapper.cancel();
                    WorldPackets.sendViewDistancePacket(wrapper.user());
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                        EntityTracker1_14 entityTracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        entityTracker.setForceSendCenterChunk(true);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.11.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short difficulty = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                        PacketWrapper difficultyPacket = wrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                        difficultyPacket.write(Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
                        difficultyPacket.write(Type.BOOLEAN, false);
                        difficultyPacket.scheduleSend(protocol.getClass());
                    }
                });
                handler(wrapper -> {
                    wrapper.send(Protocol1_14To1_13_2.class);
                    wrapper.cancel();
                    WorldPackets.sendViewDistancePacket(wrapper.user());
                });
            }
        });
        protocol.registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.SPAWN_POSITION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
    }

    public static void sendViewDistancePacket(UserConnection connection) throws Exception {
        PacketWrapper setViewDistance = PacketWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE, (ByteBuf) null, connection);
        setViewDistance.write(Type.VAR_INT, 64);
        setViewDistance.send(Protocol1_14To1_13_2.class);
    }

    public static long[] encodeHeightMap(int[] heightMap) {
        return CompactArrayUtil.createCompactArray(9, heightMap.length, i -> {
            return heightMap[i];
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x016b A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setNonFullLight(com.viaversion.viaversion.api.minecraft.chunks.Chunk r6, com.viaversion.viaversion.api.minecraft.chunks.ChunkSection r7, int r8, int r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 456
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets.setNonFullLight(com.viaversion.viaversion.api.minecraft.chunks.Chunk, com.viaversion.viaversion.api.minecraft.chunks.ChunkSection, int, int, int, int):void");
    }

    private static long getChunkIndex(int x, int z) {
        return ((x & 67108863) << 38) | (z & 67108863);
    }
}
