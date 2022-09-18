package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import java.util.Collections;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/packets/PlayerPackets.class */
public class PlayerPackets {
    public static void register(final Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.QUERY_BLOCK_NBT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CompoundTag tag;
                        Item item = (Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                        protocol.getItemRewriter().handleItemToServer(item);
                        if (item == null || (tag = item.tag()) == null) {
                            return;
                        }
                        Tag pages = tag.get("pages");
                        if (pages == null) {
                            tag.put("pages", new ListTag(Collections.singletonList(new StringTag())));
                        }
                        if (Via.getConfig().isTruncate1_14Books() && (pages instanceof ListTag)) {
                            ListTag listTag = (ListTag) pages;
                            if (listTag.size() > 50) {
                                listTag.setValue(listTag.getValue().subList(0, 50));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.PLAYER_DIGGING, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.RECIPE_BOOK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.5.1
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
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.UPDATE_SIGN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        Position position = (Position) wrapper.read(Type.POSITION1_14);
                        int face = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        float x = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        float y = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        float z = ((Float) wrapper.read(Type.FLOAT)).floatValue();
                        wrapper.read(Type.BOOLEAN);
                        wrapper.write(Type.POSITION, position);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(face));
                        wrapper.write(Type.VAR_INT, Integer.valueOf(hand));
                        wrapper.write(Type.FLOAT, Float.valueOf(x));
                        wrapper.write(Type.FLOAT, Float.valueOf(y));
                        wrapper.write(Type.FLOAT, Float.valueOf(z));
                    }
                });
            }
        });
    }
}
