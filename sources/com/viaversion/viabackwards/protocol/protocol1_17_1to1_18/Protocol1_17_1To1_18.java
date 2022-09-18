package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_17_1to1_18/Protocol1_17_1To1_18.class */
public final class Protocol1_17_1To1_18 extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityPackets1_18 entityRewriter = new EntityPackets1_18(this);
    private final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
    private BlockItemPackets1_18 itemRewriter;

    public Protocol1_17_1To1_18() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_18To1_17_1.class, this::load);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.CHAT_MESSAGE);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.ACTIONBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_SUBTITLE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_18.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_18.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_18.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_18.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_18.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        this.itemRewriter = new BlockItemPackets1_18(this);
        this.entityRewriter.register();
        this.itemRewriter.register();
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_18.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_18.ENTITY_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_18.STOP_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_18.NAMED_SOUND);
        TagRewriter tagRewriter = new TagRewriter(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        registerServerbound((Protocol1_17_1To1_18) ServerboundPackets1_17.CLIENT_SETTINGS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                create(Type.BOOLEAN, true);
            }
        });
        registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(Protocol1_17_1To1_18.this.cutName(0, 16));
            }
        });
        registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.DISPLAY_SCOREBOARD, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                map(Type.STRING);
                handler(Protocol1_17_1To1_18.this.cutName(0, 16));
            }
        });
        registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.TEAMS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(Protocol1_17_1To1_18.this.cutName(0, 16));
            }
        });
        registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.UPDATE_SCORE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.STRING);
                handler(Protocol1_17_1To1_18.this.cutName(0, 40));
                handler(Protocol1_17_1To1_18.this.cutName(1, 16));
            }
        });
    }

    public PacketHandler cutName(int index, int maxLength) {
        return wrapper -> {
            String s = (String) wrapper.get(Type.STRING, index);
            if (s.length() > maxLength) {
                wrapper.set(Type.STRING, index, s.substring(0, maxLength));
            }
        };
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection connection) {
        addEntityTracker(connection, new EntityTrackerBase(connection, Entity1_17Types.PLAYER));
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityPackets1_18 getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public BlockItemPackets1_18 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }
}
