package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/Protocol1_16_4To1_17.class */
public final class Protocol1_16_4To1_17 extends BackwardsProtocol<ClientboundPackets1_17, ClientboundPackets1_16_2, ServerboundPackets1_17, ServerboundPackets1_16_2> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.17", "1.16.2", Protocol1_17To1_16_4.class, true);
    private static final int[] EMPTY_ARRAY = new int[0];
    private final EntityRewriter entityRewriter = new EntityPackets1_17(this);
    private final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
    private BlockItemPackets1_17 blockItemPackets;

    public Protocol1_16_4To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_16_2.class, ServerboundPackets1_17.class, ServerboundPackets1_16_2.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_17To1_16_4.class, this::load);
        this.translatableRewriter.registerChatMessage(ClientboundPackets1_17.CHAT_MESSAGE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_17.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_17.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_17.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_17.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        this.blockItemPackets = new BlockItemPackets1_17(this);
        this.blockItemPackets.register();
        this.entityRewriter.register();
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_17.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_17.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_17.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_17.STOP_SOUND);
        final TagRewriter tagRewriter = new TagRewriter(this);
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.TAGS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                TagRewriter tagRewriter2 = tagRewriter;
                handler(wrapper -> {
                    RegistryType[] values;
                    Map<String, List<TagData>> tags = new HashMap<>();
                    int length = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    for (int i = 0; i < length; i++) {
                        String resourceKey = (String) wrapper.read(Type.STRING);
                        if (resourceKey.startsWith("minecraft:")) {
                            resourceKey = resourceKey.substring(10);
                        }
                        List<TagData> tagList = new ArrayList<>();
                        tags.put(resourceKey, tagList);
                        int tagLength = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        for (int j = 0; j < tagLength; j++) {
                            String identifier = (String) wrapper.read(Type.STRING);
                            tagList.add(new TagData(identifier, (int[]) wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE)));
                        }
                    }
                    for (RegistryType type : RegistryType.getValues()) {
                        List<TagData> tagList2 = tags.get(type.getResourceLocation());
                        IdRewriteFunction rewriter = tagRewriter2.getRewriter(type);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(tagList2.size()));
                        for (TagData tagData : tagList2) {
                            int[] entries = tagData.entries();
                            if (rewriter != null) {
                                IntList idList = new IntArrayList(entries.length);
                                for (int id : entries) {
                                    int mappedId = rewriter.rewrite(id);
                                    if (mappedId != -1) {
                                        idList.add(mappedId);
                                    }
                                }
                                entries = idList.toArray(Protocol1_16_4To1_17.EMPTY_ARRAY);
                            }
                            wrapper.write(Type.STRING, tagData.identifier());
                            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, entries);
                        }
                        if (type == RegistryType.ENTITY) {
                            return;
                        }
                    }
                });
            }
        });
        new StatisticsRewriter(this).register(ClientboundPackets1_17.STATISTICS);
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.RESOURCE_PACK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.STRING);
                    wrapper.passthrough(Type.STRING);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.OPTIONAL_COMPONENT);
                });
            }
        });
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.EXPLOSION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(wrapper -> {
                    wrapper.write(Type.INT, (Integer) wrapper.read(Type.VAR_INT));
                });
            }
        });
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.SPAWN_POSITION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                handler(wrapper -> {
                    wrapper.read(Type.FLOAT);
                });
            }
        });
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.PING, (ClientboundPackets1_17) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.cancel();
                    int id = ((Integer) wrapper.read(Type.INT)).intValue();
                    short shortId = (short) id;
                    if (id == shortId && ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                        ((PingRequests) wrapper.user().get(PingRequests.class)).addId(shortId);
                        PacketWrapper acknowledgementPacket = wrapper.create(ClientboundPackets1_16_2.WINDOW_CONFIRMATION);
                        acknowledgementPacket.write(Type.UNSIGNED_BYTE, (short) 0);
                        acknowledgementPacket.write(Type.SHORT, Short.valueOf(shortId));
                        acknowledgementPacket.write(Type.BOOLEAN, false);
                        acknowledgementPacket.send(Protocol1_16_4To1_17.class);
                        return;
                    }
                    PacketWrapper pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
                    pongPacket.write(Type.INT, Integer.valueOf(id));
                    pongPacket.sendToServer(Protocol1_16_4To1_17.class);
                });
            }
        });
        registerServerbound((Protocol1_16_4To1_17) ServerboundPackets1_16_2.CLIENT_SETTINGS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    wrapper.write(Type.BOOLEAN, false);
                });
            }
        });
        mergePacket(ClientboundPackets1_17.TITLE_TEXT, ClientboundPackets1_16_2.TITLE, 0);
        mergePacket(ClientboundPackets1_17.TITLE_SUBTITLE, ClientboundPackets1_16_2.TITLE, 1);
        mergePacket(ClientboundPackets1_17.ACTIONBAR, ClientboundPackets1_16_2.TITLE, 2);
        mergePacket(ClientboundPackets1_17.TITLE_TIMES, ClientboundPackets1_16_2.TITLE, 3);
        registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.CLEAR_TITLES, (ClientboundPackets1_17) ClientboundPackets1_16_2.TITLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                        wrapper.write(Type.VAR_INT, 5);
                    } else {
                        wrapper.write(Type.VAR_INT, 4);
                    }
                });
            }
        });
        cancelClientbound(ClientboundPackets1_17.ADD_VIBRATION_SIGNAL);
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        addEntityTracker(user, new EntityTrackerBase(user, Entity1_17Types.PLAYER));
        user.put(new PingRequests());
        user.put(new PlayerLastCursorItem());
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override // com.viaversion.viabackwards.api.BackwardsProtocol
    public TranslatableRewriter getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    public void mergePacket(ClientboundPackets1_17 newPacketType, ClientboundPackets1_16_2 oldPacketType, final int type) {
        registerClientbound((Protocol1_16_4To1_17) newPacketType, (ClientboundPackets1_17) oldPacketType, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                int i = type;
                handler(wrapper -> {
                    wrapper.write(Type.VAR_INT, Integer.valueOf(i));
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.blockItemPackets;
    }
}
