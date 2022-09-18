package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data.CommandRewriter1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.SoundPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/Protocol1_13_2To1_14.class */
public class Protocol1_13_2To1_14 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_13, ServerboundPackets1_14, ServerboundPackets1_13> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.14", "1.13.2", Protocol1_14To1_13_2.class, true);
    private final EntityRewriter entityRewriter = new EntityPackets1_14(this);
    private final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
    private BlockItemPackets1_14 blockItemPackets;

    public Protocol1_13_2To1_14() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_14To1_13_2.class, this::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_14.BOSSBAR);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_14.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_14.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_14.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_14.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_14.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_14.DECLARE_COMMANDS);
        this.blockItemPackets = new BlockItemPackets1_14(this);
        this.blockItemPackets.register();
        this.entityRewriter.register();
        new PlayerPackets1_14(this).register();
        new SoundPackets1_14(this).register();
        new StatisticsRewriter(this).register(ClientboundPackets1_14.STATISTICS);
        cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
        cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE);
        cancelClientbound(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
        registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.TAGS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockTagsSize = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < blockTagsSize; i++) {
                            wrapper.passthrough(Type.STRING);
                            int[] blockIds = (int[]) wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j = 0; j < blockIds.length; j++) {
                                int id = blockIds[j];
                                int blockId = Protocol1_13_2To1_14.this.getMappingData().getNewBlockId(id);
                                blockIds[j] = blockId;
                            }
                        }
                        int itemTagsSize = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i2 = 0; i2 < itemTagsSize; i2++) {
                            wrapper.passthrough(Type.STRING);
                            int[] itemIds = (int[]) wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j2 = 0; j2 < itemIds.length; j2++) {
                                int itemId = itemIds[j2];
                                int oldId = Protocol1_13_2To1_14.this.getMappingData().getItemMappings().get(itemId);
                                itemIds[j2] = oldId;
                            }
                        }
                        int fluidTagsSize = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i3 = 0; i3 < fluidTagsSize; i3++) {
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                        int entityTagsSize = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        for (int i4 = 0; i4 < entityTagsSize; i4++) {
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.UPDATE_LIGHT, (ClientboundPackets1_14) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14.2.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v25, types: [byte[], byte[][]] */
                    /* JADX WARN: Type inference failed for: r0v33, types: [byte[], byte[][]] */
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int x = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int z = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int skyLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int blockLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int emptySkyLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int emptyBlockLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        ?? r0 = new byte[16];
                        if (isSet(skyLightMask, 0)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        for (int i = 0; i < 16; i++) {
                            if (isSet(skyLightMask, i + 1)) {
                                r0[i] = (byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            } else if (isSet(emptySkyLightMask, i + 1)) {
                                r0[i] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                        }
                        if (isSet(skyLightMask, 17)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        ?? r02 = new byte[16];
                        if (isSet(blockLightMask, 0)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        for (int i2 = 0; i2 < 16; i2++) {
                            if (isSet(blockLightMask, i2 + 1)) {
                                r02[i2] = (byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                            } else if (isSet(emptyBlockLightMask, i2 + 1)) {
                                r02[i2] = ChunkLightStorage.EMPTY_LIGHT;
                            }
                        }
                        if (isSet(blockLightMask, 17)) {
                            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        }
                        ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).setStoredLight(r0, r02, x, z);
                        wrapper.cancel();
                    }

                    private boolean isSet(int mask, int i) {
                        return (mask & (1 << i)) != 0;
                    }
                });
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_14Types.PLAYER, true));
        if (!user.has(ChunkLightStorage.class)) {
            user.put(new ChunkLightStorage(user));
        }
        user.put(new DifficultyStorage(user));
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public BlockItemPackets1_14 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
}
