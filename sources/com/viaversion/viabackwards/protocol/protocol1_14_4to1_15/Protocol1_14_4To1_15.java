package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_4to1_15/Protocol1_14_4To1_15.class */
public class Protocol1_14_4To1_15 extends BackwardsProtocol<ClientboundPackets1_15, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.15", "1.14", Protocol1_15To1_14_4.class, true);
    private final EntityRewriter entityRewriter = new EntityPackets1_15(this);
    private final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
    private BlockItemPackets1_15 blockItemPackets;

    public Protocol1_14_4To1_15() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_15To1_14_4.class, this::load);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_15.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_15.DISCONNECT);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_15.OPEN_WINDOW);
        this.translatableRewriter.registerTabList(ClientboundPackets1_15.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_15.TITLE);
        this.translatableRewriter.registerPing();
        BlockItemPackets1_15 blockItemPackets1_15 = new BlockItemPackets1_15(this);
        this.blockItemPackets = blockItemPackets1_15;
        blockItemPackets1_15.register();
        this.entityRewriter.register();
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_15.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_15.STOP_SOUND);
        registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.EXPLOSION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(wrapper -> {
                    PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_14.SOUND);
                    soundPacket.write(Type.VAR_INT, 243);
                    soundPacket.write(Type.VAR_INT, 4);
                    soundPacket.write(Type.INT, Integer.valueOf(toEffectCoordinate(((Float) wrapper.get(Type.FLOAT, 0)).floatValue())));
                    soundPacket.write(Type.INT, Integer.valueOf(toEffectCoordinate(((Float) wrapper.get(Type.FLOAT, 1)).floatValue())));
                    soundPacket.write(Type.INT, Integer.valueOf(toEffectCoordinate(((Float) wrapper.get(Type.FLOAT, 2)).floatValue())));
                    soundPacket.write(Type.FLOAT, Float.valueOf(4.0f));
                    soundPacket.write(Type.FLOAT, Float.valueOf(1.0f));
                    soundPacket.send(Protocol1_14_4To1_15.class);
                });
            }

            private int toEffectCoordinate(float coordinate) {
                return (int) (coordinate * 8.0f);
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter(this).register(ClientboundPackets1_15.STATISTICS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        user.put(new ImmediateRespawn());
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_15Types.PLAYER));
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
    public BlockItemPackets1_15 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
}
