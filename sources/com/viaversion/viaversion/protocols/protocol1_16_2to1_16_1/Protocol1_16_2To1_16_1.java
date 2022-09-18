package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.MetadataRewriter1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/Protocol1_16_2To1_16_1.class */
public class Protocol1_16_2To1_16_1 extends AbstractProtocol<ClientboundPackets1_16, ClientboundPackets1_16_2, ServerboundPackets1_16, ServerboundPackets1_16_2> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_16_2To1_16_1(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);
    private TagRewriter tagRewriter;

    public Protocol1_16_2To1_16_1() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16.class, ServerboundPackets1_16_2.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.tagRewriter = new TagRewriter(this);
        this.tagRewriter.register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_16.STATISTICS);
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        registerServerbound((Protocol1_16_2To1_16_1) ServerboundPackets1_16_2.RECIPE_BOOK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int recipeType = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    boolean open = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    boolean filter = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    wrapper.write(Type.VAR_INT, 1);
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(recipeType == 0 && open));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(filter));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(recipeType == 1 && open));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(filter));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(recipeType == 2 && open));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(filter));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(recipeType == 3 && open));
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(filter));
                });
            }
        });
        registerServerbound((Protocol1_16_2To1_16_1) ServerboundPackets1_16_2.SEEN_RECIPE, (ServerboundPackets1_16_2) ServerboundPackets1_16.RECIPE_BOOK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String recipe = (String) wrapper.read(Type.STRING);
                    wrapper.write(Type.VAR_INT, 0);
                    wrapper.write(Type.STRING, recipe);
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void onMappingDataLoaded() {
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:stone_crafting_materials", 14, 962);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:mushroom_grow_block");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:soul_fire_base_blocks", "minecraft:furnace_materials", "minecraft:crimson_stems", "minecraft:gold_ores", "minecraft:piglin_loved", "minecraft:piglin_repellents", "minecraft:creeper_drop_music_discs", "minecraft:logs_that_burn", "minecraft:stone_tool_materials", "minecraft:warped_stems");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:infiniburn_nether", "minecraft:crimson_stems", "minecraft:wither_summon_base_blocks", "minecraft:infiniburn_overworld", "minecraft:piglin_repellents", "minecraft:hoglin_repellents", "minecraft:prevent_mob_spawning_inside", "minecraft:wart_blocks", "minecraft:stone_pressure_plates", "minecraft:nylium", "minecraft:gold_ores", "minecraft:pressure_plates", "minecraft:logs_that_burn", "minecraft:strider_warm_blocks", "minecraft:warped_stems", "minecraft:infiniburn_end", "minecraft:base_stone_nether", "minecraft:base_stone_overworld");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTrackerBase(userConnection, Entity1_16_2Types.PLAYER));
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
