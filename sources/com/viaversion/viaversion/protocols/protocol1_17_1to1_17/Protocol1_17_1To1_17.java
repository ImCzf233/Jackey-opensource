package com.viaversion.viaversion.protocols.protocol1_17_1to1_17;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_17_1to1_17/Protocol1_17_1To1_17.class */
public final class Protocol1_17_1To1_17 extends AbstractProtocol<ClientboundPackets1_17, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final StringType PAGE_STRING_TYPE = new StringType(8192);
    private static final StringType TITLE_STRING_TYPE = new StringType(128);

    public Protocol1_17_1To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_17_1To1_17) ClientboundPackets1_17.REMOVE_ENTITY, (ClientboundPackets1_17) ClientboundPackets1_17_1.REMOVE_ENTITIES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int entityId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{entityId});
                });
            }
        });
        registerClientbound((Protocol1_17_1To1_17) ClientboundPackets1_17.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                create(Type.VAR_INT, 0);
            }
        });
        registerClientbound((Protocol1_17_1To1_17) ClientboundPackets1_17.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                create(Type.VAR_INT, 0);
                handler(wrapper -> {
                    wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY));
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, null);
                });
            }
        });
        registerServerbound((Protocol1_17_1To1_17) ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                read(Type.VAR_INT);
            }
        });
        registerServerbound((Protocol1_17_1To1_17) ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    CompoundTag tag = new CompoundTag();
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, new DataItem(942, (byte) 1, (short) 0, tag));
                    int slot = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    int pages = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    ListTag pagesTag = new ListTag(StringTag.class);
                    for (int i = 0; i < pages; i++) {
                        String page = (String) wrapper.read(Protocol1_17_1To1_17.PAGE_STRING_TYPE);
                        pagesTag.add(new StringTag(page));
                    }
                    if (pagesTag.size() == 0) {
                        pagesTag.add(new StringTag(""));
                    }
                    tag.put("pages", pagesTag);
                    if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                        String title = (String) wrapper.read(Protocol1_17_1To1_17.TITLE_STRING_TYPE);
                        tag.put("title", new StringTag(title));
                        tag.put("author", new StringTag(wrapper.user().getProtocolInfo().getUsername()));
                        wrapper.write(Type.BOOLEAN, true);
                    } else {
                        wrapper.write(Type.BOOLEAN, false);
                    }
                    wrapper.write(Type.VAR_INT, Integer.valueOf(slot));
                });
            }
        });
    }
}
