package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.TranslatableRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.CommandRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.PlayerSneakStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.Objects;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/Protocol1_15_2To1_16.class */
public class Protocol1_15_2To1_16 extends BackwardsProtocol<ClientboundPackets1_16, ClientboundPackets1_15, ServerboundPackets1_16, ServerboundPackets1_14> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityRewriter entityRewriter = new EntityPackets1_16(this);
    private final TranslatableRewriter translatableRewriter = new TranslatableRewriter1_16(this);
    private BlockItemPackets1_16 blockItemPackets;

    public Protocol1_15_2To1_16() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_16To1_15_2.class, this::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16(this).registerDeclareCommands(ClientboundPackets1_16.DECLARE_COMMANDS);
        BlockItemPackets1_16 blockItemPackets1_16 = new BlockItemPackets1_16(this);
        this.blockItemPackets = blockItemPackets1_16;
        blockItemPackets1_16.register();
        this.entityRewriter.register();
        registerClientbound(State.STATUS, 0, 0, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String original = (String) wrapper.passthrough(Type.STRING);
                    JsonObject object = (JsonObject) GsonUtil.getGson().fromJson(original, (Class<Object>) JsonObject.class);
                    JsonElement description = object.get("description");
                    if (description == null) {
                        return;
                    }
                    Protocol1_15_2To1_16.this.translatableRewriter.processText(description);
                    wrapper.set(Type.STRING, 0, object.toString());
                });
            }
        });
        registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.CHAT_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Protocol1_15_2To1_16.this.translatableRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
                map(Type.BYTE);
                map(Type.UUID, Type.NOTHING);
            }
        });
        registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    Protocol1_15_2To1_16.this.translatableRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
                handler(wrapper2 -> {
                    int windowType = ((Integer) wrapper2.get(Type.VAR_INT, 1)).intValue();
                    if (windowType == 20) {
                        wrapper2.set(Type.VAR_INT, 1, 7);
                    } else if (windowType > 20) {
                        wrapper2.set(Type.VAR_INT, 1, Integer.valueOf(windowType - 1));
                    }
                });
            }
        });
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16.STOP_SOUND);
        registerClientbound(State.LOGIN, 2, 2, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    UUID uuid = (UUID) wrapper.read(Type.UUID_INT_ARRAY);
                    wrapper.write(Type.STRING, uuid.toString());
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_16.STATISTICS);
        registerServerbound((Protocol1_15_2To1_16) ServerboundPackets1_14.ENTITY_ACTION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int action = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (action == 0) {
                        ((PlayerSneakStorage) wrapper.user().get(PlayerSneakStorage.class)).setSneaking(true);
                    } else if (action == 1) {
                        ((PlayerSneakStorage) wrapper.user().get(PlayerSneakStorage.class)).setSneaking(false);
                    }
                });
            }
        });
        registerServerbound((Protocol1_15_2To1_16) ServerboundPackets1_14.INTERACT_ENTITY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int action = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (action == 0 || action == 2) {
                        if (action == 2) {
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        wrapper.passthrough(Type.VAR_INT);
                    }
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(((PlayerSneakStorage) wrapper.user().get(PlayerSneakStorage.class)).isSneaking()));
                });
            }
        });
        registerServerbound((Protocol1_15_2To1_16) ServerboundPackets1_14.PLAYER_ABILITIES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    byte flags = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                    wrapper.write(Type.BYTE, Byte.valueOf((byte) (flags & 2)));
                    wrapper.read(Type.FLOAT);
                    wrapper.read(Type.FLOAT);
                });
            }
        });
        cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.put(new PlayerSneakStorage());
        user.put(new WorldNameTracker());
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_16Types.PLAYER, true));
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
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
    public BlockItemPackets1_16 getItemRewriter() {
        return this.blockItemPackets;
    }
}
