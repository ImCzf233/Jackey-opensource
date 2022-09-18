package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ComponentRewriter1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2.class */
public class Protocol1_13To1_12_2 extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_13, ServerboundPackets1_12_1, ServerboundPackets1_13> {
    private final EntityRewriter entityRewriter = new MetadataRewriter1_13To1_12_2(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);
    private final ComponentRewriter componentRewriter = new ComponentRewriter1_13(this);
    public static final MappingData MAPPINGS = new MappingData();
    private static final Map<Character, Character> SCOREBOARD_TEAM_NAME_REWRITE = new HashMap();
    private static final Set<Character> FORMATTING_CODES = Sets.newHashSet(new Character[]{'k', 'l', 'm', 'n', 'o', 'r'});
    public static final PacketHandler POS_TO_3_INT = wrapper -> {
        Position position = (Position) wrapper.read(Type.POSITION);
        wrapper.write(Type.INT, Integer.valueOf(position.m228x()));
        wrapper.write(Type.INT, Integer.valueOf(position.m227y()));
        wrapper.write(Type.INT, Integer.valueOf(position.m226z()));
    };
    private static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS = w -> {
        w.create(ClientboundPackets1_13.DECLARE_COMMANDS, wrapper -> {
            wrapper.write(Type.VAR_INT, 2);
            wrapper.write(Type.BYTE, (byte) 0);
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{1});
            wrapper.write(Type.BYTE, (byte) 22);
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
            wrapper.write(Type.STRING, "args");
            wrapper.write(Type.STRING, "brigadier:string");
            wrapper.write(Type.VAR_INT, 2);
            wrapper.write(Type.STRING, "minecraft:ask_server");
            wrapper.write(Type.VAR_INT, 0);
        }).scheduleSend(Protocol1_13To1_12_2.class);
        w.create(ClientboundPackets1_13.TAGS, wrapper2 -> {
            wrapper2.write(Type.VAR_INT, Integer.valueOf(MAPPINGS.getBlockTags().size()));
            for (Map.Entry<String, Integer[]> tag : MAPPINGS.getBlockTags().entrySet()) {
                wrapper2.write(Type.STRING, tag.getKey());
                wrapper2.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive(tag.getValue()));
            }
            wrapper2.write(Type.VAR_INT, Integer.valueOf(MAPPINGS.getItemTags().size()));
            for (Map.Entry<String, Integer[]> tag2 : MAPPINGS.getItemTags().entrySet()) {
                wrapper2.write(Type.STRING, tag2.getKey());
                wrapper2.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive(tag2.getValue()));
            }
            wrapper2.write(Type.VAR_INT, Integer.valueOf(MAPPINGS.getFluidTags().size()));
            for (Map.Entry<String, Integer[]> tag3 : MAPPINGS.getFluidTags().entrySet()) {
                wrapper2.write(Type.STRING, tag3.getKey());
                wrapper2.write(Type.VAR_INT_ARRAY_PRIMITIVE, toPrimitive(tag3.getValue()));
            }
        }).scheduleSend(Protocol1_13To1_12_2.class);
    };

    static {
        SCOREBOARD_TEAM_NAME_REWRITE.put('0', 'g');
        SCOREBOARD_TEAM_NAME_REWRITE.put('1', 'h');
        SCOREBOARD_TEAM_NAME_REWRITE.put('2', 'i');
        SCOREBOARD_TEAM_NAME_REWRITE.put('3', 'j');
        SCOREBOARD_TEAM_NAME_REWRITE.put('4', 'p');
        SCOREBOARD_TEAM_NAME_REWRITE.put('5', 'q');
        SCOREBOARD_TEAM_NAME_REWRITE.put('6', 's');
        SCOREBOARD_TEAM_NAME_REWRITE.put('7', 't');
        SCOREBOARD_TEAM_NAME_REWRITE.put('8', 'u');
        SCOREBOARD_TEAM_NAME_REWRITE.put('9', 'v');
        SCOREBOARD_TEAM_NAME_REWRITE.put('a', 'w');
        SCOREBOARD_TEAM_NAME_REWRITE.put('b', 'x');
        SCOREBOARD_TEAM_NAME_REWRITE.put('c', 'y');
        SCOREBOARD_TEAM_NAME_REWRITE.put('d', 'z');
        SCOREBOARD_TEAM_NAME_REWRITE.put('e', '!');
        SCOREBOARD_TEAM_NAME_REWRITE.put('f', '?');
        SCOREBOARD_TEAM_NAME_REWRITE.put('k', '#');
        SCOREBOARD_TEAM_NAME_REWRITE.put('l', '(');
        SCOREBOARD_TEAM_NAME_REWRITE.put('m', ')');
        SCOREBOARD_TEAM_NAME_REWRITE.put('n', ':');
        SCOREBOARD_TEAM_NAME_REWRITE.put('o', ';');
        SCOREBOARD_TEAM_NAME_REWRITE.put('r', '/');
    }

    public Protocol1_13To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        registerClientbound(State.LOGIN, 0, 0, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
        registerClientbound(State.STATUS, 0, 0, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String response = (String) wrapper.get(Type.STRING, 0);
                        try {
                            JsonObject json = (JsonObject) GsonUtil.getGson().fromJson(response, (Class<Object>) JsonObject.class);
                            if (json.has("favicon")) {
                                json.addProperty("favicon", json.get("favicon").getAsString().replace("\n", ""));
                            }
                            wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson((JsonElement) json));
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.STATISTICS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int size = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        List<StatisticData> remappedStats = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            String name = (String) wrapper.read(Type.STRING);
                            String[] split = name.split("\\.");
                            int categoryId = 0;
                            int newId = -1;
                            int value = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            if (split.length == 2) {
                                categoryId = 8;
                                Integer newIdRaw = StatisticMappings.CUSTOM_STATS.get(name);
                                if (newIdRaw != null) {
                                    newId = newIdRaw.intValue();
                                } else {
                                    Via.getPlatform().getLogger().warning("Could not find 1.13 -> 1.12.2 statistic mapping for " + name);
                                }
                            } else if (split.length > 2) {
                                String category = split[1];
                                boolean z = true;
                                switch (category.hashCode()) {
                                    case -1964602143:
                                        if (category.equals("killEntity")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -1898270542:
                                        if (category.equals("breakItem")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -988476804:
                                        if (category.equals("pickup")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -863208777:
                                        if (category.equals("entityKilledBy")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -148334278:
                                        if (category.equals("useItem")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case 3092207:
                                        if (category.equals("drop")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case 664431610:
                                        if (category.equals("mineBlock")) {
                                            z = false;
                                            break;
                                        }
                                        break;
                                    case 1485652307:
                                        if (category.equals("craftItem")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                }
                                switch (z) {
                                    case false:
                                        categoryId = 0;
                                        break;
                                    case true:
                                        categoryId = 1;
                                        break;
                                    case true:
                                        categoryId = 2;
                                        break;
                                    case true:
                                        categoryId = 3;
                                        break;
                                    case true:
                                        categoryId = 4;
                                        break;
                                    case true:
                                        categoryId = 5;
                                        break;
                                    case true:
                                        categoryId = 6;
                                        break;
                                    case true:
                                        categoryId = 7;
                                        break;
                                }
                            }
                            if (newId != -1) {
                                remappedStats.add(new StatisticData(categoryId, newId, value));
                            }
                        }
                        wrapper.write(Type.VAR_INT, Integer.valueOf(remappedStats.size()));
                        for (StatisticData stat : remappedStats) {
                            wrapper.write(Type.VAR_INT, Integer.valueOf(stat.getCategoryId()));
                            wrapper.write(Type.VAR_INT, Integer.valueOf(stat.getNewId()));
                            wrapper.write(Type.VAR_INT, Integer.valueOf(stat.getValue()));
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerBossBar(ClientboundPackets1_12_1.BOSSBAR);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.CHAT_MESSAGE);
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int length;
                        int index;
                        wrapper.write(Type.VAR_INT, Integer.valueOf(((TabCompleteTracker) wrapper.user().get(TabCompleteTracker.class)).getTransactionId()));
                        String input = ((TabCompleteTracker) wrapper.user().get(TabCompleteTracker.class)).getInput();
                        if (input.endsWith(" ") || input.isEmpty()) {
                            index = input.length();
                            length = 0;
                        } else {
                            int lastSpace = input.lastIndexOf(32) + 1;
                            index = lastSpace;
                            length = input.length() - lastSpace;
                        }
                        wrapper.write(Type.VAR_INT, Integer.valueOf(index));
                        wrapper.write(Type.VAR_INT, Integer.valueOf(length));
                        int count = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < count; i++) {
                            String suggestion = (String) wrapper.read(Type.STRING);
                            if (suggestion.startsWith("/") && index == 0) {
                                suggestion = suggestion.substring(1);
                            }
                            wrapper.write(Type.STRING, suggestion);
                            wrapper.write(Type.BOOLEAN, false);
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                handler(wrapper -> {
                    Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.COOLDOWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int newItem;
                        Integer newItem2;
                        int item = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int ticks = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.cancel();
                        if (item == 383) {
                            for (int i = 0; i < 44 && (newItem2 = Integer.valueOf(Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get((item << 16) | i))) != null; i++) {
                                PacketWrapper packet = wrapper.create(ClientboundPackets1_13.COOLDOWN);
                                packet.write(Type.VAR_INT, newItem2);
                                packet.write(Type.VAR_INT, Integer.valueOf(ticks));
                                packet.send(Protocol1_13To1_12_2.class);
                            }
                            return;
                        }
                        for (int i2 = 0; i2 < 16 && (newItem = Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get((item << 4) | i2)) != -1; i2++) {
                            PacketWrapper packet2 = wrapper.create(ClientboundPackets1_13.COOLDOWN);
                            packet2.write(Type.VAR_INT, Integer.valueOf(newItem));
                            packet2.write(Type.VAR_INT, Integer.valueOf(ticks));
                            packet2.send(Protocol1_13To1_12_2.class);
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_12_1.DISCONNECT);
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(Protocol1_13To1_12_2.this.getMappingData().getItemMappings().get(data << 4)));
                        } else if (id == 2001) {
                            int blockId = data & 4095;
                            int blockData = data >> 12;
                            wrapper.set(Type.INT, 1, Integer.valueOf(WorldPackets.toNewId((blockId << 4) | blockData)));
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        wrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(entityId, Entity1_13Types.EntityType.PLAYER);
                        ClientWorld clientChunks = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
                handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "viaversion:legacy/" + wrapper.read(Type.VAR_INT));
                });
            }
        });
        this.componentRewriter.registerCombatEvent(ClientboundPackets1_12_1.COMBAT_EVENT);
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int iconCount = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < iconCount; i++) {
                            byte directionAndType = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            int type = (directionAndType & 240) >> 4;
                            wrapper.write(Type.VAR_INT, Integer.valueOf(type));
                            wrapper.passthrough(Type.BYTE);
                            wrapper.passthrough(Type.BYTE);
                            byte direction = (byte) (directionAndType & 15);
                            wrapper.write(Type.BYTE, Byte.valueOf(direction));
                            wrapper.write(Type.OPTIONAL_COMPONENT, null);
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.UNLOCK_RECIPES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.11.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int i = 0;
                        while (true) {
                            if (i >= (action == 0 ? 2 : 1)) {
                                break;
                            }
                            int[] ids = (int[]) wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                            String[] stringIds = new String[ids.length];
                            for (int j = 0; j < ids.length; j++) {
                                stringIds[j] = "viaversion:legacy/" + ids[j];
                            }
                            wrapper.write(Type.STRING_ARRAY, stringIds);
                            i++;
                        }
                        if (action == 0) {
                            wrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.11.2.1
                                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                                public void handle(PacketWrapper wrapper2) throws Exception {
                                    Item[][] ingredients;
                                    Item[][] ingredients2;
                                    wrapper2.write(Type.VAR_INT, Integer.valueOf(RecipeData.recipes.size()));
                                    for (Map.Entry<String, RecipeData.Recipe> entry : RecipeData.recipes.entrySet()) {
                                        wrapper2.write(Type.STRING, entry.getKey());
                                        wrapper2.write(Type.STRING, entry.getValue().getType());
                                        String type = entry.getValue().getType();
                                        boolean z = true;
                                        switch (type.hashCode()) {
                                            case -571676035:
                                                if (type.equals("crafting_shapeless")) {
                                                    z = false;
                                                    break;
                                                }
                                                break;
                                            case -491776273:
                                                if (type.equals("smelting")) {
                                                    z = true;
                                                    break;
                                                }
                                                break;
                                            case 1533084160:
                                                if (type.equals("crafting_shaped")) {
                                                    z = true;
                                                    break;
                                                }
                                                break;
                                        }
                                        switch (z) {
                                            case false:
                                                wrapper2.write(Type.STRING, entry.getValue().getGroup());
                                                wrapper2.write(Type.VAR_INT, Integer.valueOf(entry.getValue().getIngredients().length));
                                                for (Item[] ingredient : entry.getValue().getIngredients()) {
                                                    Item[] clone = (Item[]) ingredient.clone();
                                                    for (int i2 = 0; i2 < clone.length; i2++) {
                                                        if (clone[i2] != null) {
                                                            clone[i2] = new DataItem(clone[i2]);
                                                        }
                                                    }
                                                    wrapper2.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone);
                                                }
                                                wrapper2.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                                                break;
                                            case true:
                                                wrapper2.write(Type.VAR_INT, Integer.valueOf(entry.getValue().getWidth()));
                                                wrapper2.write(Type.VAR_INT, Integer.valueOf(entry.getValue().getHeight()));
                                                wrapper2.write(Type.STRING, entry.getValue().getGroup());
                                                for (Item[] ingredient2 : entry.getValue().getIngredients()) {
                                                    Item[] clone2 = (Item[]) ingredient2.clone();
                                                    for (int i3 = 0; i3 < clone2.length; i3++) {
                                                        if (clone2[i3] != null) {
                                                            clone2[i3] = new DataItem(clone2[i3]);
                                                        }
                                                    }
                                                    wrapper2.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone2);
                                                }
                                                wrapper2.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                                                break;
                                            case true:
                                                wrapper2.write(Type.STRING, entry.getValue().getGroup());
                                                Item[] clone3 = (Item[]) entry.getValue().getIngredient().clone();
                                                for (int i4 = 0; i4 < clone3.length; i4++) {
                                                    if (clone3[i4] != null) {
                                                        clone3[i4] = new DataItem(clone3[i4]);
                                                    }
                                                }
                                                wrapper2.write(Type.FLAT_ITEM_ARRAY_VAR_INT, clone3);
                                                wrapper2.write(Type.FLAT_ITEM, new DataItem(entry.getValue().getResult()));
                                                wrapper2.write(Type.FLOAT, Float.valueOf(entry.getValue().getExperience()));
                                                wrapper2.write(Type.VAR_INT, Integer.valueOf(entry.getValue().getCookingTime()));
                                                break;
                                        }
                                    }
                                }
                            }).send(Protocol1_13To1_12_2.class);
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.12.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.clearBlockStorage(wrapper.user());
                        }
                    }
                });
                handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.13.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte mode = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (mode == 0 || mode == 2) {
                            String value = (String) wrapper.read(Type.STRING);
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(value));
                            String type = (String) wrapper.read(Type.STRING);
                            wrapper.write(Type.VAR_INT, Integer.valueOf(type.equals("integer") ? 0 : 1));
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.TEAMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.14.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte action = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (action == 0 || action == 2) {
                            String displayName = (String) wrapper.read(Type.STRING);
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(displayName));
                            String prefix = (String) wrapper.read(Type.STRING);
                            String suffix = (String) wrapper.read(Type.STRING);
                            wrapper.passthrough(Type.BYTE);
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.STRING);
                            int colour = ((Byte) wrapper.read(Type.BYTE)).intValue();
                            if (colour == -1) {
                                colour = 21;
                            }
                            if (Via.getConfig().is1_13TeamColourFix()) {
                                char lastColorChar = Protocol1_13To1_12_2.this.getLastColorChar(prefix);
                                colour = ChatColorUtil.getColorOrdinal(lastColorChar);
                                suffix = (char) 167 + Character.toString(lastColorChar) + suffix;
                            }
                            wrapper.write(Type.VAR_INT, Integer.valueOf(colour));
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(prefix));
                            wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(suffix));
                        }
                        if (action == 0 || action == 3 || action == 4) {
                            String[] names = (String[]) wrapper.read(Type.STRING_ARRAY);
                            for (int i = 0; i < names.length; i++) {
                                names[i] = Protocol1_13To1_12_2.this.rewriteTeamMemberName(names[i]);
                            }
                            wrapper.write(Type.STRING_ARRAY, names);
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.UPDATE_SCORE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.15.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String displayName = (String) wrapper.read(Type.STRING);
                        wrapper.write(Type.STRING, Protocol1_13To1_12_2.this.rewriteTeamMemberName(displayName));
                        byte action = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                        wrapper.write(Type.BYTE, Byte.valueOf(action));
                        wrapper.passthrough(Type.STRING);
                        if (action != 1) {
                            wrapper.passthrough(Type.VAR_INT);
                        }
                    }
                });
            }
        });
        this.componentRewriter.registerTitle(ClientboundPackets1_12_1.TITLE);
        new SoundRewriter(this).registerSound(ClientboundPackets1_12_1.SOUND);
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.TAB_LIST, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.16.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                        Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                    }
                });
            }
        });
        registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.ADVANCEMENTS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.17
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.17.1
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
                                Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                                Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement) wrapper.passthrough(Type.COMPONENT));
                                Item icon = (Item) wrapper.read(Type.ITEM);
                                Protocol1_13To1_12_2.this.itemRewriter.handleItemToClient(icon);
                                wrapper.write(Type.FLAT_ITEM, icon);
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
        cancelServerbound(State.LOGIN, 2);
        cancelServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT);
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.18
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.18.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (Via.getConfig().isDisable1_13AutoComplete()) {
                            wrapper.cancel();
                        }
                        int tid = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        ((TabCompleteTracker) wrapper.user().get(TabCompleteTracker.class)).setTransactionId(tid);
                    }
                });
                map(Type.STRING, new ValueTransformer<String, String>(Type.STRING) { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.18.2
                    public String transform(PacketWrapper wrapper, String inputValue) {
                        ((TabCompleteTracker) wrapper.user().get(TabCompleteTracker.class)).setInput(inputValue);
                        return "/" + inputValue;
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.18.3
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.BOOLEAN, false);
                        wrapper.write(Type.OPTIONAL_POSITION, null);
                        if (!wrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
                            TabCompleteTracker tracker = (TabCompleteTracker) wrapper.user().get(TabCompleteTracker.class);
                            wrapper.cancel();
                            tracker.setTimeToSend(System.currentTimeMillis() + (Via.getConfig().get1_13TabCompleteDelay() * 50));
                            tracker.setLastTabComplete((String) wrapper.get(Type.STRING, 0));
                        }
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.EDIT_BOOK, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.19
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.19.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = (Item) wrapper.read(Type.FLAT_ITEM);
                        boolean isSigning = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                        Protocol1_13To1_12_2.this.itemRewriter.handleItemToServer(item);
                        wrapper.write(Type.STRING, isSigning ? "MC|BSign" : "MC|BEdit");
                        wrapper.write(Type.ITEM, item);
                    }
                });
            }
        });
        cancelServerbound(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.PICK_ITEM, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.20
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.20.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "MC|PickItem");
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.21
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                handler(wrapper -> {
                    Integer id;
                    String s = (String) wrapper.read(Type.STRING);
                    if (s.length() < 19 || (id = Ints.tryParse(s.substring(18))) == null) {
                        wrapper.cancel();
                    } else {
                        wrapper.write(Type.VAR_INT, id);
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.22
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.22.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Integer id;
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (type == 0) {
                            String s = (String) wrapper.read(Type.STRING);
                            if (s.length() < 19 || (id = Ints.tryParse(s.substring(18))) == null) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Type.INT, id);
                        }
                        if (type == 1) {
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                            wrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.RENAME_ITEM, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.23
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "MC|ItemName");
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.SELECT_TRADE, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.24
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "MC|TrSel");
                });
                map(Type.VAR_INT, Type.INT);
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.SET_BEACON_EFFECT, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.25
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "MC|Beacon");
                });
                map(Type.VAR_INT, Type.INT);
                map(Type.VAR_INT, Type.INT);
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.26
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "MC|AutoCmd");
                });
                handler(Protocol1_13To1_12_2.POS_TO_3_INT);
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.26.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper2) throws Exception {
                        int mode = ((Integer) wrapper2.read(Type.VAR_INT)).intValue();
                        byte flags = ((Byte) wrapper2.read(Type.BYTE)).byteValue();
                        String stringMode = mode == 0 ? "SEQUENCE" : mode == 1 ? "AUTO" : "REDSTONE";
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 1) != 0));
                        wrapper2.write(Type.STRING, stringMode);
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 2) != 0));
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 4) != 0));
                    }
                });
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.27
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.27.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "MC|AdvCmd");
                        wrapper.write(Type.BYTE, (byte) 1);
                    }
                });
                map(Type.VAR_INT, Type.INT);
            }
        });
        registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, (ServerboundPackets1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.write(Type.STRING, "MC|Struct");
                });
                handler(Protocol1_13To1_12_2.POS_TO_3_INT);
                map(Type.VAR_INT, new ValueTransformer<Integer, Byte>(Type.BYTE) { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28.1
                    public Byte transform(PacketWrapper wrapper2, Integer action) throws Exception {
                        return Byte.valueOf((byte) (action.intValue() + 1));
                    }
                });
                map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28.2
                    public String transform(PacketWrapper wrapper2, Integer mode) throws Exception {
                        return mode.intValue() == 0 ? "SAVE" : mode.intValue() == 1 ? "LOAD" : mode.intValue() == 2 ? "CORNER" : "DATA";
                    }
                });
                map(Type.STRING);
                map(Type.BYTE, Type.INT);
                map(Type.BYTE, Type.INT);
                map(Type.BYTE, Type.INT);
                map(Type.BYTE, Type.INT);
                map(Type.BYTE, Type.INT);
                map(Type.BYTE, Type.INT);
                map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28.3
                    public String transform(PacketWrapper wrapper2, Integer mirror) throws Exception {
                        return mirror.intValue() == 0 ? "NONE" : mirror.intValue() == 1 ? "LEFT_RIGHT" : "FRONT_BACK";
                    }
                });
                map(Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING) { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28.4
                    public String transform(PacketWrapper wrapper2, Integer rotation) throws Exception {
                        return rotation.intValue() == 0 ? "NONE" : rotation.intValue() == 1 ? "CLOCKWISE_90" : rotation.intValue() == 2 ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90";
                    }
                });
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2.28.5
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper2) throws Exception {
                        float integrity = ((Float) wrapper2.read(Type.FLOAT)).floatValue();
                        long seed = ((Long) wrapper2.read(Type.VAR_LONG)).longValue();
                        byte flags = ((Byte) wrapper2.read(Type.BYTE)).byteValue();
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 1) != 0));
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 2) != 0));
                        wrapper2.write(Type.BOOLEAN, Boolean.valueOf((flags & 4) != 0));
                        wrapper2.write(Type.FLOAT, Float.valueOf(integrity));
                        wrapper2.write(Type.VAR_LONG, Long.valueOf(seed));
                    }
                });
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void onMappingDataLoaded() {
        ConnectionData.init();
        RecipeData.init();
        BlockIdData.init();
        Types1_13.PARTICLE.filler(this).reader(3, ParticleType.Readers.BLOCK).reader(20, ParticleType.Readers.DUST).reader(11, ParticleType.Readers.DUST).reader(27, ParticleType.Readers.ITEM);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        userConnection.put(new TabCompleteTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new BlockStorage());
        if (Via.getConfig().isServersideBlockConnections() && (Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof PacketBlockConnectionProvider)) {
            userConnection.put(new BlockConnectionStorage());
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void register(ViaProviders providers) {
        providers.register(BlockEntityProvider.class, new BlockEntityProvider());
        providers.register(PaintingProvider.class, new PaintingProvider());
    }

    public char getLastColorChar(String input) {
        int length = input.length();
        for (int index = length - 1; index > -1; index--) {
            char section = input.charAt(index);
            if (section == 167 && index < length - 1) {
                char c = input.charAt(index + 1);
                if (ChatColorUtil.isColorCode(c) && !FORMATTING_CODES.contains(Character.valueOf(c))) {
                    return c;
                }
            }
        }
        return 'r';
    }

    protected String rewriteTeamMemberName(String name) {
        if (ChatColorUtil.stripColor(name).isEmpty()) {
            StringBuilder newName = new StringBuilder();
            for (int i = 1; i < name.length(); i += 2) {
                char colorChar = name.charAt(i);
                Character rewrite = SCOREBOARD_TEAM_NAME_REWRITE.get(Character.valueOf(colorChar));
                if (rewrite == null) {
                    rewrite = Character.valueOf(colorChar);
                }
                newName.append((char) 167).append(rewrite);
            }
            name = newName.toString();
        }
        return name;
    }

    public static int[] toPrimitive(Integer[] array) {
        int[] prim = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            prim[i] = array[i].intValue();
        }
        return prim;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public MappingData getMappingData() {
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

    public ComponentRewriter getComponentRewriter() {
        return this.componentRewriter;
    }
}
