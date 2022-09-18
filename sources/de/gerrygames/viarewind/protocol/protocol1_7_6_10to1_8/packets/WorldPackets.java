package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.CustomByteType;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import com.viaversion.viaversion.util.ChatColorUtil;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks.ChunkPacketTransformer;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorder;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Chunk1_7_10Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.types.VarLongType;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    ChunkSection[] sections;
                    ClientWorld world = (ClientWorld) packetWrapper.user().get(ClientWorld.class);
                    Chunk chunk = (Chunk) packetWrapper.read(new Chunk1_8Type(world));
                    packetWrapper.write(new Chunk1_7_10Type(world), chunk);
                    for (ChunkSection section : chunk.getSections()) {
                        if (section != null) {
                            for (int i = 0; i < section.getPaletteSize(); i++) {
                                int block = section.getPaletteEntry(i);
                                int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(block);
                                section.setPaletteEntry(i, replacedBlock);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                handler(packetWrapper -> {
                    BlockChangeRecord[] records = (BlockChangeRecord[]) packetWrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
                    packetWrapper.write(Type.SHORT, Short.valueOf((short) records.length));
                    packetWrapper.write(Type.INT, Integer.valueOf(records.length * 4));
                    for (BlockChangeRecord record : records) {
                        short data = (short) ((record.getSectionX() << 12) | (record.getSectionZ() << 8) | record.getY());
                        packetWrapper.write(Type.SHORT, Short.valueOf(data));
                        int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(record.getBlockId());
                        packetWrapper.write(Type.SHORT, Short.valueOf((short) replacedBlock));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.BLOCK_CHANGE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                handler(packetWrapper2 -> {
                    int data = ((Integer) packetWrapper2.read(Type.VAR_INT)).intValue();
                    int blockId = data >> 4;
                    int meta = data & 15;
                    Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, meta);
                    if (replace != null) {
                        blockId = replace.getId();
                        meta = replace.replaceData(meta);
                    }
                    packetWrapper2.write(Type.VAR_INT, Integer.valueOf(blockId));
                    packetWrapper2.write(Type.UNSIGNED_BYTE, Short.valueOf((short) meta));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.BLOCK_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.SHORT, Short.valueOf((short) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.BLOCK_BREAK_ANIMATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                map(Type.BYTE);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.MAP_BULK_CHUNK, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(ChunkPacketTransformer::transformChunkBulk);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.EFFECT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.BYTE, Byte.valueOf((byte) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                map(Type.INT);
                map(Type.BOOLEAN);
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SPAWN_PARTICLE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int particleId = ((Integer) packetWrapper.read(Type.INT)).intValue();
                    Particle particle = Particle.find(particleId);
                    if (particle == null) {
                        particle = Particle.CRIT;
                    }
                    packetWrapper.write(Type.STRING, particle.name);
                    packetWrapper.read(Type.BOOLEAN);
                });
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(packetWrapper2 -> {
                    String name = (String) packetWrapper2.get(Type.STRING, 0);
                    Particle particle = Particle.find(name);
                    if (particle == Particle.ICON_CRACK || particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST) {
                        int id = ((Integer) packetWrapper2.read(Type.VAR_INT)).intValue();
                        int data = particle == Particle.ICON_CRACK ? ((Integer) packetWrapper2.read(Type.VAR_INT)).intValue() : 0;
                        if ((id >= 256 && id <= 422) || (id >= 2256 && id <= 2267)) {
                            particle = Particle.ICON_CRACK;
                        } else if ((id >= 0 && id <= 164) || (id >= 170 && id <= 175)) {
                            if (particle == Particle.ICON_CRACK) {
                                particle = Particle.BLOCK_CRACK;
                            }
                        } else {
                            packetWrapper2.cancel();
                            return;
                        }
                        name = particle.name + "_" + id + "_" + data;
                    }
                    packetWrapper2.set(Type.STRING, 0, name);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.SHORT, Short.valueOf((short) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                handler(packetWrapper2 -> {
                    for (int i = 0; i < 4; i++) {
                        String line = ChatUtil.removeUnusedColor(ChatUtil.jsonToLegacy((String) packetWrapper2.read(Type.STRING)), '0');
                        if (line.length() > 15) {
                            line = ChatColorUtil.stripColor(line);
                            if (line.length() > 15) {
                                line = line.substring(0, 15);
                            }
                        }
                        packetWrapper2.write(Type.STRING, line);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.MAP_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    packetWrapper.cancel();
                    int id = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    byte scale = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    int count = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    byte[] icons = new byte[count * 4];
                    for (int i = 0; i < count; i++) {
                        int j = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                        icons[i * 4] = (byte) ((j >> 4) & 15);
                        icons[(i * 4) + 1] = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                        icons[(i * 4) + 2] = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                        icons[(i * 4) + 3] = (byte) (j & 15);
                    }
                    short columns = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    if (columns > 0) {
                        short rows = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                        short x = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                        short z = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                        byte[] data = (byte[]) packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        for (int column = 0; column < columns; column++) {
                            byte[] columnData = new byte[rows + 3];
                            columnData[0] = 0;
                            columnData[1] = (byte) (x + column);
                            columnData[2] = (byte) z;
                            for (int i2 = 0; i2 < rows; i2++) {
                                columnData[i2 + 3] = data[column + (i2 * columns)];
                            }
                            PacketWrapper columnUpdate = PacketWrapper.create(52, (ByteBuf) null, packetWrapper.user());
                            columnUpdate.write(Type.VAR_INT, Integer.valueOf(id));
                            columnUpdate.write(Type.SHORT, Short.valueOf((short) columnData.length));
                            columnUpdate.write(new CustomByteType(Integer.valueOf(columnData.length)), columnData);
                            PacketUtil.sendPacket(columnUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                        }
                    }
                    if (count > 0) {
                        byte[] iconData = new byte[(count * 3) + 1];
                        iconData[0] = 1;
                        for (int i3 = 0; i3 < count; i3++) {
                            iconData[(i3 * 3) + 1] = (byte) ((icons[i3 * 4] << 4) | (icons[(i3 * 4) + 3] & 15));
                            iconData[(i3 * 3) + 2] = icons[(i3 * 4) + 1];
                            iconData[(i3 * 3) + 3] = icons[(i3 * 4) + 2];
                        }
                        PacketWrapper iconUpdate = PacketWrapper.create(52, (ByteBuf) null, packetWrapper.user());
                        iconUpdate.write(Type.VAR_INT, Integer.valueOf(id));
                        iconUpdate.write(Type.SHORT, Short.valueOf((short) iconData.length));
                        CustomByteType customByteType = new CustomByteType(Integer.valueOf(iconData.length));
                        iconUpdate.write(customByteType, iconData);
                        PacketUtil.sendPacket(iconUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                    }
                    PacketWrapper scaleUpdate = PacketWrapper.create(52, (ByteBuf) null, packetWrapper.user());
                    scaleUpdate.write(Type.VAR_INT, Integer.valueOf(id));
                    scaleUpdate.write(Type.SHORT, (short) 2);
                    scaleUpdate.write(new CustomByteType(2), new byte[]{2, scale});
                    PacketUtil.sendPacket(scaleUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    Position position = (Position) packetWrapper.read(Type.POSITION);
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getX()));
                    packetWrapper.write(Type.SHORT, Short.valueOf((short) position.getY()));
                    packetWrapper.write(Type.INT, Integer.valueOf(position.getZ()));
                });
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT, Types1_7_6_10.COMPRESSED_NBT);
            }
        });
        protocol.cancelClientbound(ClientboundPackets1_8.SERVER_DIFFICULTY);
        protocol.cancelClientbound(ClientboundPackets1_8.COMBAT_EVENT);
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.WORLD_BORDER, (ClientboundPackets1_8) null, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    int action = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                    WorldBorder worldBorder = (WorldBorder) packetWrapper.user().get(WorldBorder.class);
                    if (action == 0) {
                        worldBorder.setSize(((Double) packetWrapper.read(Type.DOUBLE)).doubleValue());
                    } else if (action == 1) {
                        worldBorder.lerpSize(((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Long) packetWrapper.read(VarLongType.VAR_LONG)).longValue());
                    } else if (action == 2) {
                        worldBorder.setCenter(((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue());
                    } else if (action == 3) {
                        worldBorder.init(((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Double) packetWrapper.read(Type.DOUBLE)).doubleValue(), ((Long) packetWrapper.read(VarLongType.VAR_LONG)).longValue(), ((Integer) packetWrapper.read(Type.VAR_INT)).intValue(), ((Integer) packetWrapper.read(Type.VAR_INT)).intValue(), ((Integer) packetWrapper.read(Type.VAR_INT)).intValue());
                    } else if (action == 4) {
                        worldBorder.setWarningTime(((Integer) packetWrapper.read(Type.VAR_INT)).intValue());
                    } else if (action == 5) {
                        worldBorder.setWarningBlocks(((Integer) packetWrapper.read(Type.VAR_INT)).intValue());
                    }
                    packetWrapper.cancel();
                });
            }
        });
    }
}
