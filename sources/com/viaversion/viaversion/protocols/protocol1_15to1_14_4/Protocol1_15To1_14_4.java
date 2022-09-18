package com.viaversion.viaversion.protocols.protocol1_15to1_14_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/Protocol1_15To1_14_4.class */
public class Protocol1_15To1_14_4 extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_15, ServerboundPackets1_14, ServerboundPackets1_14> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_15To1_14_4(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);
    private TagRewriter tagRewriter;

    public Protocol1_15To1_14_4() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        PlayerPackets.register(this);
        WorldPackets.register(this);
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_14.ENTITY_SOUND);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        new StatisticsRewriter(this).register(ClientboundPackets1_14.STATISTICS);
        registerServerbound((Protocol1_15To1_14_4) ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Protocol1_15To1_14_4.this.itemRewriter.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        this.tagRewriter = new TagRewriter(this);
        this.tagRewriter.register(ClientboundPackets1_14.TAGS, RegistryType.ENTITY);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void onMappingDataLoaded() {
        int[] shulkerBoxes = new int[17];
        for (int i = 0; i < 17; i++) {
            shulkerBoxes[i] = 501 + i;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:shulker_boxes", shulkerBoxes);
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection connection) {
        addEntityTracker(connection, new EntityTrackerBase(connection, Entity1_15Types.PLAYER));
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
