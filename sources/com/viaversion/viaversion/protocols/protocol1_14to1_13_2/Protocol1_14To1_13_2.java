package com.viaversion.viaversion.protocols.protocol1_14to1_13_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.CommandRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.ComponentRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/Protocol1_14To1_13_2.class */
public class Protocol1_14To1_13_2 extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_14, ServerboundPackets1_13, ServerboundPackets1_14> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);

    public Protocol1_14To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        PlayerPackets.register(this);
        new SoundRewriter(this).registerSound(ClientboundPackets1_13.SOUND);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
        ComponentRewriter componentRewriter = new ComponentRewriter1_14(this);
        componentRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
        CommandRewriter1_14 commandRewriter = new CommandRewriter1_14(this);
        commandRewriter.registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.TAGS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockTagsSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.write(Type.VAR_INT, Integer.valueOf(blockTagsSize + 6));
                        for (int i = 0; i < blockTagsSize; i++) {
                            wrapper.passthrough(Type.STRING);
                            int[] blockIds = (int[]) wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j = 0; j < blockIds.length; j++) {
                                blockIds[j] = Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(blockIds[j]);
                            }
                        }
                        wrapper.write(Type.STRING, "minecraft:signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150), Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                        wrapper.write(Type.STRING, "minecraft:wall_signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                        wrapper.write(Type.STRING, "minecraft:standing_signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150)});
                        wrapper.write(Type.STRING, "minecraft:fences");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 248, 472, 473, 474, 475});
                        wrapper.write(Type.STRING, "minecraft:walls");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{271, 272});
                        wrapper.write(Type.STRING, "minecraft:wooden_fences");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 472, 473, 474, 475});
                        int itemTagsSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.write(Type.VAR_INT, Integer.valueOf(itemTagsSize + 2));
                        for (int i2 = 0; i2 < itemTagsSize; i2++) {
                            wrapper.passthrough(Type.STRING);
                            int[] itemIds = (int[]) wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j2 = 0; j2 < itemIds.length; j2++) {
                                itemIds[j2] = Protocol1_14To1_13_2.this.getMappingData().getNewItemId(itemIds[j2]);
                            }
                        }
                        wrapper.write(Type.STRING, "minecraft:signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewItemId(541)});
                        wrapper.write(Type.STRING, "minecraft:arrows");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{526, 825, 826});
                        int fluidTagsSize = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i3 = 0; i3 < fluidTagsSize; i3++) {
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                        wrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        cancelServerbound(ServerboundPackets1_14.SET_DIFFICULTY);
        cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
        cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void onMappingDataLoaded() {
        WorldPackets.air = getMappingData().getBlockStateMappings().getNewId(0);
        WorldPackets.voidAir = getMappingData().getBlockStateMappings().getNewId(8591);
        WorldPackets.caveAir = getMappingData().getBlockStateMappings().getNewId(8592);
        Types1_13_2.PARTICLE.filler(this, false).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
        Types1_14.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTracker1_14(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
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
