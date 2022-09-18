package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(packetWrapper -> {
                    CompoundTag tag = (CompoundTag) packetWrapper.get(Type.NBT, 0);
                    if (tag != null && tag.contains("SpawnData")) {
                        String entity = (String) ((CompoundTag) tag.get("SpawnData")).get("id").getValue();
                        tag.remove("SpawnData");
                        tag.put("entityId", new StringTag(entity));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.BLOCK_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int block = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    if (block >= 219 && block <= 234) {
                        packetWrapper.set(Type.VAR_INT, 0, 130);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.BLOCK_CHANGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    int combined = ((Integer) packetWrapper.get(Type.VAR_INT, 0)).intValue();
                    int replacedCombined = ReplacementRegistry1_8to1_9.replace(combined);
                    packetWrapper.set(Type.VAR_INT, 0, Integer.valueOf(replacedCombined));
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                handler(packetWrapper -> {
                    BlockChangeRecord[] blockChangeRecordArr;
                    for (BlockChangeRecord record : (BlockChangeRecord[]) packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                        int replacedCombined = ReplacementRegistry1_8to1_9.replace(record.getBlockId());
                        record.setBlockId(replacedCombined);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.NAMED_SOUND, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    String name = SoundRemapper.getOldName((String) packetWrapper.get(Type.STRING, 0));
                    if (name == null) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.set(Type.STRING, 0, name);
                    }
                });
                map(Type.VAR_INT, Type.NOTHING);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.UNSIGNED_BYTE);
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.EXPLOSION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(packetWrapper -> {
                    int count = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    packetWrapper.write(Type.INT, Integer.valueOf(count));
                    for (int i = 0; i < count; i++) {
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    }
                });
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.UNLOAD_CHUNK, (ClientboundPackets1_9) ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int chunkX = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    int chunkZ = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    ClientWorld world = (ClientWorld) packetWrapper.user().get(ClientWorld.class);
                    packetWrapper.write(new Chunk1_8Type(world), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList()));
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    ChunkSection[] sections;
                    ClientWorld world = (ClientWorld) packetWrapper.user().get(ClientWorld.class);
                    Chunk chunk = (Chunk) packetWrapper.read(new Chunk1_9_1_2Type(world));
                    for (ChunkSection section : chunk.getSections()) {
                        if (section != null) {
                            for (int i = 0; i < section.getPaletteSize(); i++) {
                                int block = section.getPaletteEntry(i);
                                int replacedBlock = ReplacementRegistry1_8to1_9.replace(block);
                                section.setPaletteEntry(i, replacedBlock);
                            }
                        }
                    }
                    if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                        boolean skylight = world.getEnvironment() == Environment.NORMAL;
                        ChunkSection[] sections2 = new ChunkSection[16];
                        ChunkSection section2 = new ChunkSectionImpl(true);
                        sections2[0] = section2;
                        section2.addPaletteEntry(0);
                        if (skylight) {
                            section2.getLight().setSkyLight(new byte[2048]);
                        }
                        chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections2, chunk.getBiomeData(), chunk.getBlockEntities());
                    }
                    packetWrapper.write(new Chunk1_8Type(world), chunk);
                    UserConnection user = packetWrapper.user();
                    chunk.getBlockEntities().forEach(nbt -> {
                        short action;
                        if (!nbt.contains("x") || !nbt.contains("y") || !nbt.contains("z") || !nbt.contains("id")) {
                            return;
                        }
                        Position position = new Position(((Integer) nbt.get("x").getValue()).intValue(), (short) ((Integer) nbt.get("y").getValue()).intValue(), ((Integer) nbt.get("z").getValue()).intValue());
                        String id = (String) nbt.get("id").getValue();
                        boolean z = true;
                        switch (id.hashCode()) {
                            case -1883218338:
                                if (id.equals("minecraft:flower_pot")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1296947815:
                                if (id.equals("minecraft:banner")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1293651279:
                                if (id.equals("minecraft:beacon")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1134211248:
                                if (id.equals("minecraft:skull")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -199249700:
                                if (id.equals("minecraft:mob_spawner")) {
                                    z = false;
                                    break;
                                }
                                break;
                            case 339138444:
                                if (id.equals("minecraft:command_block")) {
                                    z = true;
                                    break;
                                }
                                break;
                        }
                        switch (z) {
                            case false:
                                action = 1;
                                break;
                            case true:
                                action = 2;
                                break;
                            case true:
                                action = 3;
                                break;
                            case true:
                                action = 4;
                                break;
                            case true:
                                action = 5;
                                break;
                            case true:
                                action = 6;
                                break;
                            default:
                                return;
                        }
                        PacketWrapper updateTileEntity = PacketWrapper.create(9, (ByteBuf) null, user);
                        updateTileEntity.write(Type.POSITION, position);
                        updateTileEntity.write(Type.UNSIGNED_BYTE, Short.valueOf(action));
                        updateTileEntity.write(Type.NBT, nbt);
                        PacketUtil.sendPacket(updateTileEntity, Protocol1_8TO1_9.class, false, false);
                    });
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    int id = Effect.getOldId(((Integer) packetWrapper.get(Type.INT, 0)).intValue());
                    if (id == -1) {
                        packetWrapper.cancel();
                        return;
                    }
                    packetWrapper.set(Type.INT, 0, Integer.valueOf(id));
                    if (id == 2001) {
                        int replacedBlock = ReplacementRegistry1_8to1_9.replace(((Integer) packetWrapper.get(Type.INT, 1)).intValue());
                        packetWrapper.set(Type.INT, 1, Integer.valueOf(replacedBlock));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SPAWN_PARTICLE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(packetWrapper -> {
                    int type = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                        packetWrapper.cancel();
                    } else if (type == 42) {
                        packetWrapper.set(Type.INT, 0, 24);
                    } else if (type == 43) {
                        packetWrapper.set(Type.INT, 0, 3);
                    } else if (type == 44) {
                        packetWrapper.set(Type.INT, 0, 34);
                    } else if (type == 45) {
                        packetWrapper.set(Type.INT, 0, 1);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.MAP_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SOUND, (ClientboundPackets1_9) ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.WorldPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int soundId = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    String sound = SoundRemapper.oldNameFromId(soundId);
                    if (sound == null) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.write(Type.STRING, sound);
                    }
                });
                handler(packetWrapper2 -> {
                    packetWrapper2.read(Type.VAR_INT);
                });
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.UNSIGNED_BYTE);
            }
        });
    }
}
