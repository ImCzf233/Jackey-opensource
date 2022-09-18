package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.Effect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.SoundEffect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.ChunkBulk1_8Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        wrapper.set(Type.INT, 0, Integer.valueOf(Effect.getNewId(id)));
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.2.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        if (id == 2002) {
                            int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                            int newData = ItemRewriter.getNewEffectID(data);
                            wrapper.set(Type.INT, 1, Integer.valueOf(newData));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String name = (String) wrapper.get(Type.STRING, 0);
                        SoundEffect effect = SoundEffect.getByName(name);
                        int catid = 0;
                        String newname = name;
                        if (effect != null) {
                            catid = effect.getCategory().getId();
                            newname = effect.getNewName();
                        }
                        wrapper.set(Type.STRING, 0, newname);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(catid));
                        if (effect != null && effect.isBreaksound()) {
                            EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            int x = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                            int y = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                            int z = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                            if (tracker.interactedBlockRecently((int) Math.floor(x / 8.0d), (int) Math.floor(y / 8.0d), (int) Math.floor(z / 8.0d))) {
                                wrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BlockFace[] blockFaceArr;
                        BlockFace[] blockFaceArr2;
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        ClientChunks clientChunks = (ClientChunks) wrapper.user().get(ClientChunks.class);
                        Chunk chunk = (Chunk) wrapper.read(new Chunk1_8Type(clientWorld));
                        long chunkHash = ClientChunks.toLong(chunk.getX(), chunk.getZ());
                        if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                            wrapper.setPacketType(ClientboundPackets1_9.UNLOAD_CHUNK);
                            wrapper.write(Type.INT, Integer.valueOf(chunk.getX()));
                            wrapper.write(Type.INT, Integer.valueOf(chunk.getZ()));
                            CommandBlockProvider provider = (CommandBlockProvider) Via.getManager().getProviders().get(CommandBlockProvider.class);
                            provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
                            clientChunks.getLoadedChunks().remove(Long.valueOf(chunkHash));
                            if (Via.getConfig().isChunkBorderFix()) {
                                for (BlockFace face : BlockFace.HORIZONTAL) {
                                    int chunkX = chunk.getX() + face.modX();
                                    int chunkZ = chunk.getZ() + face.modZ();
                                    if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX, chunkZ)))) {
                                        PacketWrapper unloadChunk = wrapper.create(ClientboundPackets1_9.UNLOAD_CHUNK);
                                        unloadChunk.write(Type.INT, Integer.valueOf(chunkX));
                                        unloadChunk.write(Type.INT, Integer.valueOf(chunkZ));
                                        unloadChunk.send(Protocol1_9To1_8.class);
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
                        wrapper.write(chunk1_9_1_2Type, chunk);
                        clientChunks.getLoadedChunks().add(Long.valueOf(chunkHash));
                        if (Via.getConfig().isChunkBorderFix()) {
                            for (BlockFace face2 : BlockFace.HORIZONTAL) {
                                int chunkX2 = chunk.getX() + face2.modX();
                                int chunkZ2 = chunk.getZ() + face2.modZ();
                                if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX2, chunkZ2)))) {
                                    PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                                    emptyChunk.write(chunk1_9_1_2Type, new BaseChunk(chunkX2, chunkZ2, true, false, 0, new ChunkSection[16], new int[256], new ArrayList()));
                                    emptyChunk.send(Protocol1_9To1_8.class);
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.MAP_BULK_CHUNK, (ClientboundPackets1_8) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockFace[] blockFaceArr;
                    wrapper.cancel();
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    ClientChunks clientChunks = (ClientChunks) wrapper.user().get(ClientChunks.class);
                    Chunk[] chunks = (Chunk[]) wrapper.read(new ChunkBulk1_8Type(clientWorld));
                    Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
                    for (Chunk chunk : chunks) {
                        PacketWrapper chunkData = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                        chunkData.write(chunk1_9_1_2Type, chunk);
                        chunkData.send(Protocol1_9To1_8.class);
                        clientChunks.getLoadedChunks().add(Long.valueOf(ClientChunks.toLong(chunk.getX(), chunk.getZ())));
                        if (Via.getConfig().isChunkBorderFix()) {
                            for (BlockFace face : BlockFace.HORIZONTAL) {
                                int chunkX = chunk.getX() + face.modX();
                                int chunkZ = chunk.getZ() + face.modZ();
                                if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX, chunkZ)))) {
                                    PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                                    emptyChunk.write(chunk1_9_1_2Type, new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList()));
                                    emptyChunk.send(Protocol1_9To1_8.class);
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CompoundTag tag;
                        int action = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        if (action == 1 && (tag = (CompoundTag) wrapper.get(Type.NBT, 0)) != null) {
                            if (tag.contains("EntityId")) {
                                String entity = (String) tag.get("EntityId").getValue();
                                CompoundTag spawn = new CompoundTag();
                                spawn.put("id", new StringTag(entity));
                                tag.put("SpawnData", spawn);
                            } else {
                                CompoundTag spawn2 = new CompoundTag();
                                spawn2.put("id", new StringTag("AreaEffectCloud"));
                                tag.put("SpawnData", spawn2);
                            }
                        }
                        if (action == 2) {
                            CommandBlockProvider provider = (CommandBlockProvider) Via.getManager().getProviders().get(CommandBlockProvider.class);
                            provider.addOrUpdateBlock(wrapper.user(), (Position) wrapper.get(Type.POSITION, 0), (CompoundTag) wrapper.get(Type.NBT, 0));
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.UPDATE_SIGN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.PLAYER_DIGGING, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int status = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (status == 6) {
                            wrapper.cancel();
                        }
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.8.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int status = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (status == 5 || status == 4 || status == 3) {
                            EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            if (entityTracker.isBlocking()) {
                                entityTracker.setBlocking(false);
                                if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                                    entityTracker.setSecondHand(null);
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.USE_ITEM, (ServerboundPackets1_9) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        boolean z;
                        int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.clearInputBuffer();
                        wrapper.setId(8);
                        wrapper.write(Type.POSITION, new Position(-1, (short) -1, -1));
                        wrapper.write(Type.UNSIGNED_BYTE, (short) 255);
                        Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                        if (Via.getConfig().isShieldBlocking()) {
                            EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
                            if (showShieldWhenSwordInHand) {
                                z = tracker.hasSwordInHand();
                            } else {
                                z = item != null && Protocol1_9To1_8.isSword(item.identifier());
                            }
                            boolean isSword = z;
                            if (isSword) {
                                if (hand == 0 && !tracker.isBlocking()) {
                                    tracker.setBlocking(true);
                                    if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
                                        Item shield = new DataItem(442, (byte) 1, (short) 0, null);
                                        tracker.setSecondHand(shield);
                                    }
                                }
                                boolean blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
                                if ((blockUsingMainHand && hand == 1) || (!blockUsingMainHand && hand == 0)) {
                                    wrapper.cancel();
                                }
                            } else {
                                if (!showShieldWhenSwordInHand) {
                                    tracker.setSecondHand(null);
                                }
                                tracker.setBlocking(false);
                            }
                        }
                        wrapper.write(Type.ITEM, item);
                        wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
                        wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
                        wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT, Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        if (hand != 0) {
                            wrapper.cancel();
                        }
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.10.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                        wrapper.write(Type.ITEM, item);
                    }
                });
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.10.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int face = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        if (face == 255) {
                            return;
                        }
                        Position p = (Position) wrapper.get(Type.POSITION, 0);
                        int x = p.m228x();
                        int y = p.m227y();
                        int z = p.m226z();
                        switch (face) {
                            case 0:
                                y--;
                                break;
                            case 1:
                                y++;
                                break;
                            case 2:
                                z--;
                                break;
                            case 3:
                                z++;
                                break;
                            case 4:
                                x--;
                                break;
                            case 5:
                                x++;
                                break;
                        }
                        EntityTracker1_9 tracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        tracker.addBlockInteraction(new Position(x, y, z));
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets.10.4
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CommandBlockProvider provider = (CommandBlockProvider) Via.getManager().getProviders().get(CommandBlockProvider.class);
                        Position pos = (Position) wrapper.get(Type.POSITION, 0);
                        Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
                        if (tag.isPresent()) {
                            PacketWrapper updateBlockEntity = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, (ByteBuf) null, wrapper.user());
                            updateBlockEntity.write(Type.POSITION, pos);
                            updateBlockEntity.write(Type.UNSIGNED_BYTE, (short) 2);
                            updateBlockEntity.write(Type.NBT, tag.get());
                            updateBlockEntity.scheduleSend(Protocol1_9To1_8.class);
                        }
                    }
                });
            }
        });
    }
}
