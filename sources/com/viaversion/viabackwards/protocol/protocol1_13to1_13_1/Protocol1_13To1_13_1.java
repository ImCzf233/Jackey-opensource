package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data.CommandRewriter1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.InventoryPackets1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13to1_13_1/Protocol1_13To1_13_1.class */
public class Protocol1_13To1_13_1 extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.13.2", "1.13", Protocol1_13_1To1_13.class, true);
    private final EntityRewriter entityRewriter = new EntityPackets1_13_1(this);
    private final ItemRewriter itemRewriter = new InventoryPackets1_13_1(this);

    public Protocol1_13To1_13_1() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        BackwardsMappings backwardsMappings = MAPPINGS;
        Objects.requireNonNull(backwardsMappings);
        executeAsyncAfterLoaded(Protocol1_13_1To1_13.class, this::load);
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets1_13_1.register(this);
        final TranslatableRewriter translatableRewriter = new TranslatableRewriter(this);
        translatableRewriter.registerChatMessage(ClientboundPackets1_13.CHAT_MESSAGE);
        translatableRewriter.registerCombatEvent(ClientboundPackets1_13.COMBAT_EVENT);
        translatableRewriter.registerDisconnect(ClientboundPackets1_13.DISCONNECT);
        translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
        translatableRewriter.registerTitle(ClientboundPackets1_13.TITLE);
        translatableRewriter.registerPing();
        new CommandRewriter1_13_1(this).registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        registerServerbound((Protocol1_13To1_13_1) ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.STRING, new ValueTransformer<String, String>(Type.STRING) { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.1.1
                    public String transform(PacketWrapper wrapper, String inputValue) {
                        return !inputValue.startsWith("/") ? "/" + inputValue : inputValue;
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_13_1) ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLAT_ITEM);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Protocol1_13To1_13_1.this.itemRewriter.handleItemToServer((Item) wrapper.get(Type.FLAT_ITEM, 0));
                        wrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                TranslatableRewriter translatableRewriter2 = translatableRewriter;
                handler(wrapper -> {
                    JsonElement title = (JsonElement) wrapper.passthrough(Type.COMPONENT);
                    translatableRewriter2.processText(title);
                    if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                        if (title.isJsonObject() && title.getAsJsonObject().size() == 1 && title.getAsJsonObject().has("translate")) {
                            return;
                        }
                        JsonObject legacyComponent = new JsonObject();
                        legacyComponent.addProperty("text", ChatRewriter.jsonToLegacyText(title.toString()));
                        wrapper.set(Type.COMPONENT, 0, legacyComponent);
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int start = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        wrapper.set(Type.VAR_INT, 1, Integer.valueOf(start - 1));
                        int count = ((Integer) wrapper.get(Type.VAR_INT, 3)).intValue();
                        for (int i = 0; i < count; i++) {
                            wrapper.passthrough(Type.STRING);
                            boolean hasTooltip = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (hasTooltip) {
                                wrapper.passthrough(Type.STRING);
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.BOSSBAR, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (action == 0 || action == 3) {
                            translatableRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                            if (action == 0) {
                                wrapper.passthrough(Type.FLOAT);
                                wrapper.passthrough(Type.VAR_INT);
                                wrapper.passthrough(Type.VAR_INT);
                                short flags = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                                if ((flags & 4) != 0) {
                                    flags = (short) (flags | 2);
                                }
                                wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(flags));
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_13_1) ClientboundPackets1_13.ADVANCEMENTS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.BOOLEAN);
                        int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < size; i++) {
                            wrapper.passthrough(Type.STRING);
                            if (((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                wrapper.passthrough(Type.STRING);
                            }
                            if (((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                wrapper.passthrough(Type.COMPONENT);
                                wrapper.passthrough(Type.COMPONENT);
                                Item icon = (Item) wrapper.passthrough(Type.FLAT_ITEM);
                                Protocol1_13To1_13_1.this.itemRewriter.handleItemToClient(icon);
                                wrapper.passthrough(Type.VAR_INT);
                                int flags = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                                if ((flags & 1) != 0) {
                                    wrapper.passthrough(Type.STRING);
                                }
                                wrapper.passthrough(Type.FLOAT);
                                wrapper.passthrough(Type.FLOAT);
                            }
                            wrapper.passthrough(Type.STRING_ARRAY);
                            int arrayLength = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            for (int array = 0; array < arrayLength; array++) {
                                wrapper.passthrough(Type.STRING_ARRAY);
                            }
                        }
                    }
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        user.addEntityTracker(getClass(), new EntityTrackerBase(user, Entity1_13Types.EntityType.PLAYER));
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
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
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
