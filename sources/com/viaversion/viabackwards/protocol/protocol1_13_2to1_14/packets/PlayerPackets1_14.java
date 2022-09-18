package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/PlayerPackets1_14.class */
public class PlayerPackets1_14 extends RewriterBase<Protocol1_13_2To1_14> {
    public PlayerPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SERVER_DIFFICULTY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.BOOLEAN, Type.NOTHING);
                handler(wrapper -> {
                    byte difficulty = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue();
                    ((DifficultyStorage) wrapper.user().get(DifficultyStorage.class)).setDifficulty(difficulty);
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.OPEN_SIGN_EDITOR, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.QUERY_BLOCK_NBT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.PLAYER_DIGGING, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (type == 0) {
                            wrapper.passthrough(Type.STRING);
                        } else if (type == 1) {
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.write(Type.BOOLEAN, false);
                            wrapper.write(Type.BOOLEAN, false);
                            wrapper.write(Type.BOOLEAN, false);
                            wrapper.write(Type.BOOLEAN, false);
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.UPDATE_SIGN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = (Position) wrapper.read(Type.POSITION);
                        int face = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        float x = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        float y = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        float z = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        wrapper.write(Type.VAR_INT, Integer.valueOf(hand));
                        wrapper.write(Type.POSITION1_14, position);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(face));
                        wrapper.write(Type.FLOAT, Float.valueOf(x));
                        wrapper.write(Type.FLOAT, Float.valueOf(y));
                        wrapper.write(Type.FLOAT, Float.valueOf(z));
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
    }
}
