package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/WorldPackets.class */
public class WorldPackets {
    private static final IntSet VALID_BIOMES = new IntOpenHashSet(70, 0.99f);

    static {
        for (int i = 0; i < 50; i++) {
            VALID_BIOMES.add(i);
        }
        VALID_BIOMES.add(127);
        for (int i2 = 129; i2 <= 134; i2++) {
            VALID_BIOMES.add(i2);
        }
        VALID_BIOMES.add(140);
        VALID_BIOMES.add(149);
        VALID_BIOMES.add(151);
        for (int i3 = 155; i3 <= 158; i3++) {
            VALID_BIOMES.add(i3);
        }
        for (int i4 = 160; i4 <= 167; i4++) {
            VALID_BIOMES.add(i4);
        }
    }

    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.SPAWN_PAINTING, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        PaintingProvider provider = (PaintingProvider) Via.getManager().getProviders().get(PaintingProvider.class);
                        String motive = (String) wrapper.read(Type.STRING);
                        Optional<Integer> id = provider.getIntByIdentifier(motive);
                        if (!id.isPresent() && (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())) {
                            Via.getPlatform().getLogger().warning("Could not find painting motive: " + motive + " falling back to default (0)");
                        }
                        wrapper.write(Type.VAR_INT, id.orElse(0));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = (Position) wrapper.get(Type.POSITION, 0);
                        short action = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        CompoundTag tag = (CompoundTag) wrapper.get(Type.NBT, 0);
                        BlockEntityProvider provider = (BlockEntityProvider) Via.getManager().getProviders().get(BlockEntityProvider.class);
                        int newId = provider.transform(wrapper.user(), position, tag, true);
                        if (newId != -1) {
                            BlockStorage storage = (BlockStorage) wrapper.user().get(BlockStorage.class);
                            BlockStorage.ReplacementData replacementData = storage.get(position);
                            if (replacementData != null) {
                                replacementData.setReplacement(newId);
                            }
                        }
                        if (action == 5) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.BLOCK_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position pos = (Position) wrapper.get(Type.POSITION, 0);
                        short action = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        short param = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 1)).shortValue();
                        int blockId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (blockId == 25) {
                            blockId = 73;
                        } else if (blockId == 33) {
                            blockId = 99;
                        } else if (blockId == 29) {
                            blockId = 92;
                        } else if (blockId == 54) {
                            blockId = 142;
                        } else if (blockId == 146) {
                            blockId = 305;
                        } else if (blockId == 130) {
                            blockId = 249;
                        } else if (blockId == 138) {
                            blockId = 257;
                        } else if (blockId == 52) {
                            blockId = 140;
                        } else if (blockId == 209) {
                            blockId = 472;
                        } else if (blockId >= 219 && blockId <= 234) {
                            blockId = (blockId - 219) + 483;
                        }
                        if (blockId == 73) {
                            PacketWrapper blockChange = wrapper.create(11);
                            blockChange.write(Type.POSITION, pos);
                            blockChange.write(Type.VAR_INT, Integer.valueOf(249 + (action * 24 * 2) + (param * 2)));
                            blockChange.send(Protocol1_13To1_12_2.class);
                        }
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(blockId));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = (Position) wrapper.get(Type.POSITION, 0);
                        int newId = WorldPackets.toNewId(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue());
                        UserConnection userConnection = wrapper.user();
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.updateBlockStorage(userConnection, position.m228x(), position.m227y(), position.m226z(), newId);
                            newId = ConnectionData.connect(userConnection, position, newId);
                        }
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(WorldPackets.checkStorage(wrapper.user(), position, newId)));
                        if (Via.getConfig().isServersideBlockConnections()) {
                            wrapper.send(Protocol1_13To1_12_2.class);
                            wrapper.cancel();
                            ConnectionData.update(userConnection, position);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int chunkX = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int chunkZ = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        UserConnection userConnection = wrapper.user();
                        BlockChangeRecord[] records = (BlockChangeRecord[]) wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        for (BlockChangeRecord record : records) {
                            int newBlock = WorldPackets.toNewId(record.getBlockId());
                            Position position = new Position(record.getSectionX() + (chunkX * 16), record.getY(), record.getSectionZ() + (chunkZ * 16));
                            if (Via.getConfig().isServersideBlockConnections()) {
                                ConnectionData.updateBlockStorage(userConnection, position.m228x(), position.m227y(), position.m226z(), newBlock);
                            }
                            record.setBlockId(WorldPackets.checkStorage(wrapper.user(), position, newBlock));
                        }
                        if (Via.getConfig().isServersideBlockConnections()) {
                            for (BlockChangeRecord record2 : records) {
                                int blockState = record2.getBlockId();
                                Position position2 = new Position(record2.getSectionX() + (chunkX * 16), record2.getY(), record2.getSectionZ() + (chunkZ * 16));
                                ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
                                if (handler != null) {
                                    record2.setBlockId(handler.connect(userConnection, position2, blockState));
                                }
                            }
                            wrapper.send(Protocol1_13To1_12_2.class);
                            wrapper.cancel();
                            for (BlockChangeRecord record3 : records) {
                                Position position3 = new Position(record3.getSectionX() + (chunkX * 16), record3.getY(), record3.getSectionZ() + (chunkZ * 16));
                                ConnectionData.update(userConnection, position3);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.EXPLOSION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                if (!Via.getConfig().isServersideBlockConnections()) {
                    return;
                }
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        UserConnection userConnection = wrapper.user();
                        int x = (int) Math.floor(((Float) wrapper.get(Type.FLOAT, 0)).floatValue());
                        int y = (int) Math.floor(((Float) wrapper.get(Type.FLOAT, 1)).floatValue());
                        int z = (int) Math.floor(((Float) wrapper.get(Type.FLOAT, 2)).floatValue());
                        int recordCount = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        Position[] records = new Position[recordCount];
                        for (int i = 0; i < recordCount; i++) {
                            Position position = new Position(x + ((Byte) wrapper.passthrough(Type.BYTE)).byteValue(), (short) (y + ((Byte) wrapper.passthrough(Type.BYTE)).byteValue()), z + ((Byte) wrapper.passthrough(Type.BYTE)).byteValue());
                            records[i] = position;
                            ConnectionData.updateBlockStorage(userConnection, position.m228x(), position.m227y(), position.m226z(), 0);
                        }
                        wrapper.send(Protocol1_13To1_12_2.class);
                        wrapper.cancel();
                        for (int i2 = 0; i2 < recordCount; i2++) {
                            ConnectionData.update(userConnection, records[i2]);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.UNLOAD_CHUNK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                if (Via.getConfig().isServersideBlockConnections()) {
                    handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.7.1
                        @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                        public void handle(PacketWrapper wrapper) throws Exception {
                            int x = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                            int z = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                            ConnectionData.blockConnectionProvider.unloadChunk(wrapper.user(), x, z);
                        }
                    });
                }
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String sound = ((String) wrapper.get(Type.STRING, 0)).replace("minecraft:", "");
                        String newSoundId = NamedSoundRewriter.getNewId(sound);
                        wrapper.set(Type.STRING, 0, newSoundId);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        BlockStorage storage = (BlockStorage) wrapper.user().get(BlockStorage.class);
                        Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        Chunk1_13Type type1_13 = new Chunk1_13Type(clientWorld);
                        Chunk chunk = (Chunk) wrapper.read(type);
                        wrapper.write(type1_13, chunk);
                        for (int i = 0; i < chunk.getSections().length; i++) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section != null) {
                                for (int p = 0; p < section.getPaletteSize(); p++) {
                                    int old = section.getPaletteEntry(p);
                                    section.setPaletteEntry(p, WorldPackets.toNewId(old));
                                }
                                boolean willSaveToStorage = false;
                                int p2 = 0;
                                while (true) {
                                    if (p2 < section.getPaletteSize()) {
                                        if (!storage.isWelcome(section.getPaletteEntry(p2))) {
                                            p2++;
                                        } else {
                                            willSaveToStorage = true;
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                boolean willSaveConnection = false;
                                if (Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
                                    int p3 = 0;
                                    while (true) {
                                        if (p3 < section.getPaletteSize()) {
                                            if (!ConnectionData.isWelcome(section.getPaletteEntry(p3))) {
                                                p3++;
                                            } else {
                                                willSaveConnection = true;
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                                if (willSaveToStorage) {
                                    for (int y = 0; y < 16; y++) {
                                        for (int z = 0; z < 16; z++) {
                                            for (int x = 0; x < 16; x++) {
                                                int block = section.getFlatBlock(x, y, z);
                                                if (storage.isWelcome(block)) {
                                                    storage.store(new Position(x + (chunk.getX() << 4), (short) (y + (i << 4)), z + (chunk.getZ() << 4)), block);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (willSaveConnection) {
                                    for (int y2 = 0; y2 < 16; y2++) {
                                        for (int z2 = 0; z2 < 16; z2++) {
                                            for (int x2 = 0; x2 < 16; x2++) {
                                                int block2 = section.getFlatBlock(x2, y2, z2);
                                                if (ConnectionData.isWelcome(block2)) {
                                                    ConnectionData.blockConnectionProvider.storeBlock(wrapper.user(), x2 + (chunk.getX() << 4), y2 + (i << 4), z2 + (chunk.getZ() << 4), block2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (chunk.isBiomeData()) {
                            int latestBiomeWarn = Integer.MIN_VALUE;
                            for (int i2 = 0; i2 < 256; i2++) {
                                int biome = chunk.getBiomeData()[i2];
                                if (!WorldPackets.VALID_BIOMES.contains(biome)) {
                                    if (biome != 255 && latestBiomeWarn != biome) {
                                        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                            Via.getPlatform().getLogger().warning("Received invalid biome id " + biome);
                                        }
                                        latestBiomeWarn = biome;
                                    }
                                    chunk.getBiomeData()[i2] = 1;
                                }
                            }
                        }
                        BlockEntityProvider provider = (BlockEntityProvider) Via.getManager().getProviders().get(BlockEntityProvider.class);
                        Iterator<CompoundTag> iterator = chunk.getBlockEntities().iterator();
                        while (iterator.hasNext()) {
                            CompoundTag tag = iterator.next();
                            int newId = provider.transform(wrapper.user(), null, tag, false);
                            if (newId != -1) {
                                int x3 = ((NumberTag) tag.get("x")).asInt();
                                int y3 = ((NumberTag) tag.get("y")).asInt();
                                int z3 = ((NumberTag) tag.get("z")).asInt();
                                Position position = new Position(x3, (short) y3, z3);
                                BlockStorage.ReplacementData replacementData = storage.get(position);
                                if (replacementData != null) {
                                    replacementData.setReplacement(newId);
                                }
                                chunk.getSections()[y3 >> 4].setFlatBlock(x3 & 15, y3 & 15, z3 & 15, newId);
                            }
                            Tag idTag = tag.get("id");
                            if (idTag instanceof StringTag) {
                                String id = ((StringTag) idTag).getValue();
                                if (id.equals("minecraft:noteblock") || id.equals("minecraft:flower_pot")) {
                                    iterator.remove();
                                }
                            }
                        }
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.connectBlocks(wrapper.user(), chunk);
                            wrapper.send(Protocol1_13To1_12_2.class);
                            wrapper.cancel();
                            for (int i3 = 0; i3 < chunk.getSections().length; i3++) {
                                if (chunk.getSections()[i3] != null) {
                                    ConnectionData.updateChunkSectionNeighbours(wrapper.user(), chunk.getX(), chunk.getZ(), i3);
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_12_1.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int particleId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int dataCount = 0;
                        if (particleId == 37 || particleId == 38 || particleId == 46) {
                            dataCount = 1;
                        } else if (particleId == 36) {
                            dataCount = 2;
                        }
                        Integer[] data = new Integer[dataCount];
                        for (int i = 0; i < data.length; i++) {
                            data[i] = (Integer) wrapper.read(Type.VAR_INT);
                        }
                        Particle particle = ParticleRewriter.rewriteParticle(particleId, data);
                        if (particle == null || particle.getId() == -1) {
                            wrapper.cancel();
                            return;
                        }
                        if (particle.getId() == 11) {
                            int count = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                            float speed = ((Float) wrapper.get(Type.FLOAT, 6)).floatValue();
                            if (count == 0) {
                                wrapper.set(Type.INT, 1, 1);
                                wrapper.set(Type.FLOAT, 6, Float.valueOf(0.0f));
                                List<Particle.ParticleData> arguments = particle.getArguments();
                                for (int i2 = 0; i2 < 3; i2++) {
                                    float colorValue = ((Float) wrapper.get(Type.FLOAT, i2 + 3)).floatValue() * speed;
                                    if (colorValue == 0.0f && i2 == 0) {
                                        colorValue = 1.0f;
                                    }
                                    arguments.get(i2).setValue(Float.valueOf(colorValue));
                                    wrapper.set(Type.FLOAT, i2 + 3, Float.valueOf(0.0f));
                                }
                            }
                        }
                        wrapper.set(Type.INT, 0, Integer.valueOf(particle.getId()));
                        for (Particle.ParticleData particleData : particle.getArguments()) {
                            wrapper.write(particleData.getType(), particleData.getValue());
                        }
                    }
                });
            }
        });
    }

    public static int toNewId(int oldId) {
        if (oldId < 0) {
            oldId = 0;
        }
        int newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId);
        if (newId != -1) {
            return newId;
        }
        int newId2 = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId & (-16));
        if (newId2 != -1) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Missing block " + oldId);
            }
            return newId2;
        } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Missing block completely " + oldId);
            return 1;
        } else {
            return 1;
        }
    }

    public static int checkStorage(UserConnection user, Position position, int newId) {
        BlockStorage storage = (BlockStorage) user.get(BlockStorage.class);
        if (storage.contains(position)) {
            BlockStorage.ReplacementData data = storage.get(position);
            if (data.getOriginal() == newId) {
                if (data.getReplacement() != -1) {
                    return data.getReplacement();
                }
            } else {
                storage.remove(position);
                if (storage.isWelcome(newId)) {
                    storage.store(position, newId);
                }
            }
        } else if (storage.isWelcome(newId)) {
            storage.store(position, newId);
        }
        return newId;
    }
}
