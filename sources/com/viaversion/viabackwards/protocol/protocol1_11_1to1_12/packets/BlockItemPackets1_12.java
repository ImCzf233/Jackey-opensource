package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Iterator;
import java.util.Map;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11_1to1_12/packets/BlockItemPackets1_12.class */
public class BlockItemPackets1_12 extends LegacyBlockItemRewriter<Protocol1_11_1To1_12> {
    public BlockItemPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int count = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < count * 3; i++) {
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.1.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short columns = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        if (columns <= 0) {
                            return;
                        }
                        ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        byte[] data = (byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        for (int i = 0; i < data.length; i++) {
                            short color = (short) (data[i] & 255);
                            if (color > 143) {
                                data[i] = (byte) MapColorMapping.getNearestOldColor(color);
                            }
                        }
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, data);
                    }
                });
            }
        });
        registerSetSlot(ClientboundPackets1_12.SET_SLOT, Type.ITEM);
        registerWindowItems(ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
        registerEntityEquipment(ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((String) wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerServerbound((Protocol1_11_1To1_12) ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Integer) wrapper.get(Type.VAR_INT, 0)).intValue() == 1) {
                            wrapper.set(Type.ITEM, 0, null);
                            PacketWrapper confirm = wrapper.create(ServerboundPackets1_12.WINDOW_CONFIRMATION);
                            confirm.write(Type.UNSIGNED_BYTE, (Short) wrapper.get(Type.UNSIGNED_BYTE, 0));
                            confirm.write(Type.SHORT, (Short) wrapper.get(Type.SHORT, 1));
                            confirm.write(Type.BOOLEAN, false);
                            wrapper.sendToServer(Protocol1_11_1To1_12.class);
                            wrapper.cancel();
                            confirm.sendToServer(Protocol1_11_1To1_12.class);
                            return;
                        }
                        Item item = (Item) wrapper.get(Type.ITEM, 0);
                        BlockItemPackets1_12.this.handleItemToServer(item);
                    }
                });
            }
        });
        registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        Chunk chunk = (Chunk) wrapper.passthrough(type);
                        BlockItemPackets1_12.this.handleChunk(chunk);
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int idx = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(BlockItemPackets1_12.this.handleBlockID(idx)));
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BlockChangeRecord[] blockChangeRecordArr;
                        for (BlockChangeRecord record : (BlockChangeRecord[]) wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            record.setBlockId(BlockItemPackets1_12.this.handleBlockID(record.getBlockId()));
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerClientbound((Protocol1_11_1To1_12) ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).getEntityRewriter().filter().handler(event, meta -> {
            if (meta.metaType().type().equals(Type.ITEM)) {
                meta.setValue(handleItemToClient((Item) meta.getValue()));
            }
        });
        ((Protocol1_11_1To1_12) this.protocol).registerServerbound((Protocol1_11_1To1_12) ServerboundPackets1_9_3.CLIENT_STATUS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Integer) wrapper.get(Type.VAR_INT, 0)).intValue() == 2) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        if (item.tag() != null) {
            CompoundTag backupTag = new CompoundTag();
            if (handleNbtToClient(item.tag(), backupTag)) {
                item.tag().put("Via|LongArrayTags", backupTag);
            }
        }
        return item;
    }

    private boolean handleNbtToClient(CompoundTag compoundTag, CompoundTag backupTag) {
        Iterator<Map.Entry<String, Tag>> iterator = compoundTag.iterator();
        boolean hasLongArrayTag = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Tag> entry = iterator.next();
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag nestedBackupTag = new CompoundTag();
                backupTag.put(entry.getKey(), nestedBackupTag);
                hasLongArrayTag |= handleNbtToClient((CompoundTag) entry.getValue(), nestedBackupTag);
            } else if (entry.getValue() instanceof LongArrayTag) {
                backupTag.put(entry.getKey(), fromLongArrayTag((LongArrayTag) entry.getValue()));
                iterator.remove();
                hasLongArrayTag = true;
            }
        }
        return hasLongArrayTag;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null) {
            Tag tag = item.tag().remove("Via|LongArrayTags");
            if (tag instanceof CompoundTag) {
                handleNbtToServer(item.tag(), (CompoundTag) tag);
            }
        }
        return item;
    }

    private void handleNbtToServer(CompoundTag compoundTag, CompoundTag backupTag) {
        Iterator<Map.Entry<String, Tag>> it = backupTag.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Tag> entry = it.next();
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag nestedTag = (CompoundTag) compoundTag.get(entry.getKey());
                handleNbtToServer(nestedTag, (CompoundTag) entry.getValue());
            } else {
                compoundTag.put(entry.getKey(), fromIntArrayTag((IntArrayTag) entry.getValue()));
            }
        }
    }

    private IntArrayTag fromLongArrayTag(LongArrayTag tag) {
        int[] intArray = new int[tag.length() * 2];
        long[] longArray = tag.getValue();
        int i = 0;
        for (long l : longArray) {
            int i2 = i;
            int i3 = i + 1;
            intArray[i2] = (int) (l >> 32);
            i = i3 + 1;
            intArray[i3] = (int) l;
        }
        return new IntArrayTag(intArray);
    }

    private LongArrayTag fromIntArrayTag(IntArrayTag tag) {
        long[] longArray = new long[tag.length() / 2];
        int[] intArray = tag.getValue();
        int i = 0;
        int j = 0;
        while (i < intArray.length) {
            longArray[j] = (intArray[i] << 32) | (intArray[i + 1] & JSType.MAX_UINT);
            i += 2;
            j++;
        }
        return new LongArrayTag(longArray);
    }
}
