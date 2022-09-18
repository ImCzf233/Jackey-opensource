package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ShortType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Arrays;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_10to1_11/packets/BlockItemPackets1_11.class */
public class BlockItemPackets1_11 extends LegacyBlockItemRewriter<Protocol1_10To1_11> {
    private LegacyEnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_11(Protocol1_10To1_11 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.ITEM);
                handler(BlockItemPackets1_11.this.itemToClientHandler(Type.ITEM));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                            Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                            if (!horse.isPresent()) {
                                return;
                            }
                            ChestedHorseStorage storage = horse.get();
                            int currentSlot = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            ShortType shortType = Type.SHORT;
                            int currentSlot2 = BlockItemPackets1_11.this.getNewSlotId(storage, currentSlot);
                            wrapper.set(shortType, 0, Short.valueOf(Integer.valueOf(currentSlot2).shortValue()));
                            wrapper.set(Type.ITEM, 0, BlockItemPackets1_11.this.getNewItem(storage, currentSlot2, (Item) wrapper.get(Type.ITEM, 0)));
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.ITEM_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item[] stacks = (Item[]) wrapper.get(Type.ITEM_ARRAY, 0);
                        for (int i = 0; i < stacks.length; i++) {
                            stacks[i] = BlockItemPackets1_11.this.handleItemToClient(stacks[i]);
                        }
                        if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                            Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                            if (!horse.isPresent()) {
                                return;
                            }
                            ChestedHorseStorage storage = horse.get();
                            Item[] stacks2 = (Item[]) Arrays.copyOf(stacks, !storage.isChested() ? 38 : 53);
                            for (int i2 = stacks2.length - 1; i2 >= 0; i2--) {
                                stacks2[BlockItemPackets1_11.this.getNewSlotId(storage, i2)] = stacks2[i2];
                                stacks2[i2] = BlockItemPackets1_11.this.getNewItem(storage, i2, stacks2[i2]);
                            }
                            wrapper.set(Type.ITEM_ARRAY, 0, stacks2);
                        }
                    }
                });
            }
        });
        registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((String) wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
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
        ((Protocol1_10To1_11) this.protocol).registerServerbound((Protocol1_10To1_11) ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.ITEM);
                handler(BlockItemPackets1_11.this.itemToServerHandler(Type.ITEM));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                            Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                            if (!horse.isPresent()) {
                                return;
                            }
                            ChestedHorseStorage storage = horse.get();
                            int clickSlot = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            int correctSlot = BlockItemPackets1_11.this.getOldSlotId(storage, clickSlot);
                            wrapper.set(Type.SHORT, 0, Short.valueOf(Integer.valueOf(correctSlot).shortValue()));
                        }
                    }
                });
            }
        });
        registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        Chunk chunk = (Chunk) wrapper.passthrough(type);
                        BlockItemPackets1_11.this.handleChunk(chunk);
                        for (CompoundTag tag : chunk.getBlockEntities()) {
                            Tag idTag = tag.get("id");
                            if (idTag instanceof StringTag) {
                                String id = (String) idTag.getValue();
                                if (id.equals("minecraft:sign")) {
                                    ((StringTag) idTag).setValue("Sign");
                                }
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int idx = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(BlockItemPackets1_11.this.handleBlockID(idx)));
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BlockChangeRecord[] blockChangeRecordArr;
                        for (BlockChangeRecord record : (BlockChangeRecord[]) wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            record.setBlockId(BlockItemPackets1_11.this.handleBlockID(record.getBlockId()));
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 10) {
                            wrapper.cancel();
                        }
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 1) {
                            CompoundTag tag = (CompoundTag) wrapper.get(Type.NBT, 0);
                            EntityIdRewriter.toClientSpawner(tag, true);
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.COMPONENT);
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = -1;
                        if (((String) wrapper.get(Type.STRING, 0)).equals("EntityHorse")) {
                            entityId = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                        }
                        String inventory = (String) wrapper.get(Type.STRING, 0);
                        WindowTracker windowTracker = (WindowTracker) wrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(inventory);
                        windowTracker.setEntityId(entityId);
                        if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                            wrapper.set(Type.UNSIGNED_BYTE, 1, (short) 17);
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerClientbound((Protocol1_10To1_11) ClientboundPackets1_9_3.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        WindowTracker windowTracker = (WindowTracker) wrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(null);
                        windowTracker.setEntityId(-1);
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).registerServerbound((Protocol1_10To1_11) ServerboundPackets1_9_3.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        WindowTracker windowTracker = (WindowTracker) wrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(null);
                        windowTracker.setEntityId(-1);
                    }
                });
            }
        });
        ((Protocol1_10To1_11) this.protocol).getEntityRewriter().filter().handler(event, meta -> {
            if (meta.metaType().type().equals(Type.ITEM)) {
                meta.setValue(handleItemToClient((Item) meta.getValue()));
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        MappedLegacyBlockItem data = this.replacementData.computeIfAbsent(52, s -> {
            return new MappedLegacyBlockItem(52, (short) -1, null, false);
        });
        data.setBlockEntityHandler(b, tag -> {
            EntityIdRewriter.toClientSpawner(tag, true);
            return tag;
        });
        this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
        this.enchantmentRewriter.registerEnchantment(71, "§cCurse of Vanishing");
        this.enchantmentRewriter.registerEnchantment(10, "§cCurse of Binding");
        this.enchantmentRewriter.setHideLevelForEnchants(71, 10);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        EntityIdRewriter.toClientItem(item, true);
        if (tag.get("ench") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.get("StoredEnchantments") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
        }
        return item;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        EntityIdRewriter.toServerItem(item, true);
        if (tag.contains(this.nbtTagName + "|ench")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
        }
        return item;
    }

    public boolean isLlama(UserConnection user) {
        WindowTracker tracker = (WindowTracker) user.get(WindowTracker.class);
        if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse")) {
            EntityTracker entTracker = user.getEntityTracker(Protocol1_10To1_11.class);
            StoredEntityData entityData = entTracker.entityData(tracker.getEntityId());
            return entityData != null && entityData.type().m224is(Entity1_11Types.EntityType.LIAMA);
        }
        return false;
    }

    public Optional<ChestedHorseStorage> getChestedHorse(UserConnection user) {
        WindowTracker tracker = (WindowTracker) user.get(WindowTracker.class);
        if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse")) {
            EntityTracker entTracker = user.getEntityTracker(Protocol1_10To1_11.class);
            StoredEntityData entityData = entTracker.entityData(tracker.getEntityId());
            if (entityData != null) {
                return Optional.of((ChestedHorseStorage) entityData.get(ChestedHorseStorage.class));
            }
        }
        return Optional.empty();
    }

    public int getNewSlotId(ChestedHorseStorage storage, int slotId) {
        int totalSlots = !storage.isChested() ? 38 : 53;
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + (3 * strength);
        int offsetForm = 15 - (3 * strength);
        if (slotId >= startNonExistingFormula && totalSlots > slotId + offsetForm) {
            return offsetForm + slotId;
        }
        if (slotId == 1) {
            return 0;
        }
        return slotId;
    }

    public int getOldSlotId(ChestedHorseStorage storage, int slotId) {
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + (3 * strength);
        int endNonExistingFormula = 2 + (3 * (storage.isChested() ? 5 : 0));
        int offsetForm = endNonExistingFormula - startNonExistingFormula;
        if (slotId != 1) {
            if (slotId >= startNonExistingFormula && slotId < endNonExistingFormula) {
                return 0;
            }
            if (slotId >= endNonExistingFormula) {
                return slotId - offsetForm;
            }
            if (slotId == 0) {
                return 1;
            }
            return slotId;
        }
        return 0;
    }

    public Item getNewItem(ChestedHorseStorage storage, int slotId, Item current) {
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + (3 * strength);
        int endNonExistingFormula = 2 + (3 * (storage.isChested() ? 5 : 0));
        if (slotId >= startNonExistingFormula && slotId < endNonExistingFormula) {
            return new DataItem(166, (byte) 1, (short) 0, getNamedTag("§4SLOT DISABLED"));
        }
        if (slotId == 1) {
            return null;
        }
        return current;
    }
}
