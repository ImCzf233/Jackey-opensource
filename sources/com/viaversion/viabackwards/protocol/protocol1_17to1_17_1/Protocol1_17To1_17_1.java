package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage.InventoryStateIds;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_17to1_17_1/Protocol1_17To1_17_1.class */
public final class Protocol1_17To1_17_1 extends BackwardsProtocol<ClientboundPackets1_17_1, ClientboundPackets1_17, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final int MAX_PAGE_LENGTH = 8192;
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_PAGES = 200;

    public Protocol1_17To1_17_1() {
        super(ClientboundPackets1_17_1.class, ClientboundPackets1_17.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_17To1_17_1) ClientboundPackets1_17_1.REMOVE_ENTITIES, (ClientboundPackets1_17_1) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int[] entityIds = (int[]) wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    wrapper.cancel();
                    for (int entityId : entityIds) {
                        PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                        newPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                        newPacket.send(Protocol1_17To1_17_1.class);
                    }
                });
            }
        });
        registerClientbound((Protocol1_17To1_17_1) ClientboundPackets1_17_1.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short containerId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    ((InventoryStateIds) wrapper.user().get(InventoryStateIds.class)).removeStateId(containerId);
                });
            }
        });
        registerClientbound((Protocol1_17To1_17_1) ClientboundPackets1_17_1.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short containerId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    int stateId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    ((InventoryStateIds) wrapper.user().get(InventoryStateIds.class)).setStateId(containerId, stateId);
                });
            }
        });
        registerClientbound((Protocol1_17To1_17_1) ClientboundPackets1_17_1.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short containerId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    int stateId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    ((InventoryStateIds) wrapper.user().get(InventoryStateIds.class)).setStateId(containerId, stateId);
                    wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY, (Item[]) wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
                    Item carried = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    PlayerLastCursorItem lastCursorItem = (PlayerLastCursorItem) wrapper.user().get(PlayerLastCursorItem.class);
                    if (lastCursorItem != null) {
                        lastCursorItem.setLastCursorItem(carried);
                    }
                });
            }
        });
        registerServerbound((Protocol1_17To1_17_1) ServerboundPackets1_17.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short containerId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    ((InventoryStateIds) wrapper.user().get(InventoryStateIds.class)).removeStateId(containerId);
                });
            }
        });
        registerServerbound((Protocol1_17To1_17_1) ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short containerId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    int stateId = ((InventoryStateIds) wrapper.user().get(InventoryStateIds.class)).removeStateId(containerId);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(stateId == Integer.MAX_VALUE ? 0 : stateId));
                });
            }
        });
        registerServerbound((Protocol1_17To1_17_1) ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.Protocol1_17To1_17_1.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Item item = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    boolean signing = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    wrapper.passthrough(Type.VAR_INT);
                    CompoundTag tag = item.tag();
                    StringTag titleTag = null;
                    if (tag != null) {
                        ListTag listTag = (ListTag) tag.get("pages");
                        ListTag pagesTag = listTag;
                        if (listTag != null) {
                            if (signing) {
                                StringTag stringTag = (StringTag) tag.get("title");
                                titleTag = stringTag;
                            }
                            if (pagesTag.size() > 200) {
                                pagesTag = new ListTag(pagesTag.getValue().subList(0, 200));
                            }
                            wrapper.write(Type.VAR_INT, Integer.valueOf(pagesTag.size()));
                            Iterator<Tag> it = pagesTag.iterator();
                            while (it.hasNext()) {
                                Tag pageTag = it.next();
                                String page = ((StringTag) pageTag).getValue();
                                if (page.length() > 8192) {
                                    page = page.substring(0, 8192);
                                }
                                wrapper.write(Type.STRING, page);
                            }
                            wrapper.write(Type.BOOLEAN, Boolean.valueOf(signing));
                            if (signing) {
                                if (titleTag == null) {
                                    titleTag = (StringTag) tag.get("title");
                                }
                                String title = titleTag.getValue();
                                if (title.length() > 128) {
                                    title = title.substring(0, 128);
                                }
                                wrapper.write(Type.STRING, title);
                                return;
                            }
                            return;
                        }
                    }
                    wrapper.write(Type.VAR_INT, 0);
                    wrapper.write(Type.BOOLEAN, false);
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection connection) {
        connection.put(new InventoryStateIds());
    }
}
