package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.base.Joiner;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/PlayerPacket1_13.class */
public class PlayerPacket1_13 extends RewriterBase<Protocol1_12_2To1_13> {
    private final CommandRewriter commandRewriter = new CommandRewriter(this.protocol) { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.1
    };

    public PlayerPacket1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound(State.LOGIN, 4, -1, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        packetWrapper.create(2, new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.2.1.1
                            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                            public void handle(PacketWrapper newWrapper) throws Exception {
                                newWrapper.write(Type.VAR_INT, (Integer) packetWrapper.read(Type.VAR_INT));
                                newWrapper.write(Type.BOOLEAN, false);
                            }
                        }).sendToServer(Protocol1_12_2To1_13.class);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = (String) wrapper.read(Type.STRING);
                        if (channel.equals("minecraft:trader_list")) {
                            wrapper.write(Type.STRING, "MC|TrList");
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                Item input = (Item) wrapper.read(Type.FLAT_ITEM);
                                wrapper.write(Type.ITEM, ((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol).getItemRewriter().handleItemToClient(input));
                                Item output = (Item) wrapper.read(Type.FLAT_ITEM);
                                wrapper.write(Type.ITEM, ((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol).getItemRewriter().handleItemToClient(output));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    Item second = (Item) wrapper.read(Type.FLAT_ITEM);
                                    wrapper.write(Type.ITEM, ((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol).getItemRewriter().handleItemToClient(second));
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                            return;
                        }
                        String oldChannel = InventoryPackets.getOldPluginChannelId(channel);
                        if (oldChannel == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                ViaBackwards.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + channel);
                            }
                            wrapper.cancel();
                            return;
                        }
                        wrapper.write(Type.STRING, oldChannel);
                        if (oldChannel.equals("REGISTER") || oldChannel.equals("UNREGISTER")) {
                            String[] channels = new String((byte[]) wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("��");
                            List<String> rewrittenChannels = new ArrayList<>();
                            for (String s : channels) {
                                String rewritten = InventoryPackets.getOldPluginChannelId(s);
                                if (rewritten != null) {
                                    rewrittenChannels.add(rewritten);
                                } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + s);
                                }
                            }
                            wrapper.write(Type.REMAINING_BYTES, Joiner.on((char) 0).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ParticleMapping.ParticleData old = ParticleMapping.getMapping(((Integer) wrapper.get(Type.INT, 0)).intValue());
                        wrapper.set(Type.INT, 0, Integer.valueOf(old.getHistoryId()));
                        int[] data = old.rewriteData((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol, wrapper);
                        if (data != null) {
                            if (old.getHandler().isBlockHandler() && data[0] == 0) {
                                wrapper.cancel();
                                return;
                            }
                            for (int i : data) {
                                wrapper.write(Type.VAR_INT, Integer.valueOf(i));
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.PLAYER_INFO, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        TabCompleteStorage storage = (TabCompleteStorage) packetWrapper.user().get(TabCompleteStorage.class);
                        int action = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                        int nPlayers = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < nPlayers; i++) {
                            UUID uuid = (UUID) packetWrapper.passthrough(Type.UUID);
                            if (action == 0) {
                                String name = (String) packetWrapper.passthrough(Type.STRING);
                                storage.usernames().put(uuid, name);
                                int nProperties = ((Integer) packetWrapper.passthrough(Type.VAR_INT)).intValue();
                                for (int j = 0; j < nProperties; j++) {
                                    packetWrapper.passthrough(Type.STRING);
                                    packetWrapper.passthrough(Type.STRING);
                                    if (((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                        packetWrapper.passthrough(Type.STRING);
                                    }
                                }
                                packetWrapper.passthrough(Type.VAR_INT);
                                packetWrapper.passthrough(Type.VAR_INT);
                                if (((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                    packetWrapper.passthrough(Type.COMPONENT);
                                }
                            } else if (action == 1) {
                                packetWrapper.passthrough(Type.VAR_INT);
                            } else if (action == 2) {
                                packetWrapper.passthrough(Type.VAR_INT);
                            } else if (action == 3) {
                                if (((Boolean) packetWrapper.passthrough(Type.BOOLEAN)).booleanValue()) {
                                    packetWrapper.passthrough(Type.COMPONENT);
                                }
                            } else if (action == 4) {
                                storage.usernames().remove(uuid);
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SCOREBOARD_OBJECTIVE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte mode = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (mode == 0 || mode == 2) {
                            String value = ChatRewriter.jsonToLegacyText(((JsonElement) wrapper.read(Type.COMPONENT)).toString());
                            if (value.length() > 32) {
                                value = value.substring(0, 32);
                            }
                            wrapper.write(Type.STRING, value);
                            int type = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            wrapper.write(Type.STRING, type == 1 ? "hearts" : "integer");
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.TEAMS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        byte action = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        if (action == 0 || action == 2) {
                            String displayName = ChatUtil.removeUnusedColor(ChatRewriter.jsonToLegacyText((String) wrapper.read(Type.STRING)), 'f');
                            if (displayName.length() > 32) {
                                displayName = displayName.substring(0, 32);
                            }
                            wrapper.write(Type.STRING, displayName);
                            byte flags = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            String nameTagVisibility = (String) wrapper.read(Type.STRING);
                            String collisionRule = (String) wrapper.read(Type.STRING);
                            int colour = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            if (colour == 21) {
                                colour = -1;
                            }
                            JsonElement prefixComponent = (JsonElement) wrapper.read(Type.COMPONENT);
                            JsonElement suffixComponent = (JsonElement) wrapper.read(Type.COMPONENT);
                            String prefix = (prefixComponent == null || prefixComponent.isJsonNull()) ? "" : ChatRewriter.jsonToLegacyText(prefixComponent.toString());
                            if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                                prefix = prefix + "§" + ((colour <= -1 || colour > 15) ? "r" : Integer.toHexString(colour));
                            }
                            String prefix2 = ChatUtil.removeUnusedColor(prefix, 'f', true);
                            if (prefix2.length() > 16) {
                                prefix2 = prefix2.substring(0, 16);
                            }
                            if (prefix2.endsWith("§")) {
                                prefix2 = prefix2.substring(0, prefix2.length() - 1);
                            }
                            String suffix = ChatUtil.removeUnusedColor((suffixComponent == null || suffixComponent.isJsonNull()) ? "" : ChatRewriter.jsonToLegacyText(suffixComponent.toString()), (char) 0);
                            if (suffix.length() > 16) {
                                suffix = suffix.substring(0, 16);
                            }
                            if (suffix.endsWith("§")) {
                                suffix = suffix.substring(0, suffix.length() - 1);
                            }
                            wrapper.write(Type.STRING, prefix2);
                            wrapper.write(Type.STRING, suffix);
                            wrapper.write(Type.BYTE, Byte.valueOf(flags));
                            wrapper.write(Type.STRING, nameTagVisibility);
                            wrapper.write(Type.STRING, collisionRule);
                            wrapper.write(Type.BYTE, Byte.valueOf((byte) colour));
                        }
                        if (action == 0 || action == 3 || action == 4) {
                            wrapper.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.DECLARE_COMMANDS, (ClientboundPackets1_13) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.cancel();
                    TabCompleteStorage storage = (TabCompleteStorage) wrapper.user().get(TabCompleteStorage.class);
                    if (!storage.commands().isEmpty()) {
                        storage.commands().clear();
                    }
                    int size = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    boolean initialNodes = true;
                    for (int i = 0; i < size; i++) {
                        byte flags = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                        wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                        if ((flags & 8) != 0) {
                            wrapper.read(Type.VAR_INT);
                        }
                        byte nodeType = (byte) (flags & 3);
                        if (initialNodes && nodeType == 2) {
                            initialNodes = false;
                        }
                        if (nodeType == 1 || nodeType == 2) {
                            String name = (String) wrapper.read(Type.STRING);
                            if (nodeType == 1 && initialNodes) {
                                storage.commands().add('/' + name);
                            }
                        }
                        if (nodeType == 2) {
                            PlayerPacket1_13.this.commandRewriter.handleArgument(wrapper, (String) wrapper.read(Type.STRING));
                        }
                        if ((flags & 16) != 0) {
                            wrapper.read(Type.STRING);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        TabCompleteStorage storage = (TabCompleteStorage) wrapper.user().get(TabCompleteStorage.class);
                        if (storage.lastRequest() == null) {
                            wrapper.cancel();
                            return;
                        }
                        if (storage.lastId() != ((Integer) wrapper.read(Type.VAR_INT)).intValue()) {
                            wrapper.cancel();
                        }
                        int start = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int length = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int lastRequestPartIndex = storage.lastRequest().lastIndexOf(32) + 1;
                        if (lastRequestPartIndex != start) {
                            wrapper.cancel();
                        }
                        if (length != storage.lastRequest().length() - lastRequestPartIndex) {
                            wrapper.cancel();
                        }
                        int count = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < count; i++) {
                            String match = (String) wrapper.read(Type.STRING);
                            wrapper.write(Type.STRING, ((start != 0 || storage.isLastAssumeCommand()) ? "" : "/") + match);
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                wrapper.read(Type.STRING);
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    TabCompleteStorage storage = (TabCompleteStorage) wrapper.user().get(TabCompleteStorage.class);
                    List<String> suggestions = new ArrayList<>();
                    String command = (String) wrapper.read(Type.STRING);
                    boolean assumeCommand = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    wrapper.read(Type.OPTIONAL_POSITION);
                    if (!assumeCommand && !command.startsWith("/")) {
                        String buffer = command.substring(command.lastIndexOf(32) + 1);
                        for (String value : storage.usernames().values()) {
                            if (PlayerPacket1_13.startsWithIgnoreCase(value, buffer)) {
                                suggestions.add(value);
                            }
                        }
                    } else if (!storage.commands().isEmpty() && !command.contains(" ")) {
                        for (String value2 : storage.commands()) {
                            if (PlayerPacket1_13.startsWithIgnoreCase(value2, command)) {
                                suggestions.add(value2);
                            }
                        }
                    }
                    if (!suggestions.isEmpty()) {
                        wrapper.cancel();
                        PacketWrapper response = wrapper.create(ClientboundPackets1_12_1.TAB_COMPLETE);
                        response.write(Type.VAR_INT, Integer.valueOf(suggestions.size()));
                        for (String value3 : suggestions) {
                            response.write(Type.STRING, value3);
                        }
                        response.scheduleSend(Protocol1_12_2To1_13.class);
                        storage.setLastRequest(null);
                        return;
                    }
                    if (!assumeCommand && command.startsWith("/")) {
                        command = command.substring(1);
                    }
                    int id = ThreadLocalRandom.current().nextInt();
                    wrapper.write(Type.VAR_INT, Integer.valueOf(id));
                    wrapper.write(Type.STRING, command);
                    storage.setLastId(id);
                    storage.setLastAssumeCommand(assumeCommand);
                    storage.setLastRequest(command);
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    String channel = (String) wrapper.read(Type.STRING);
                    boolean z = true;
                    switch (channel.hashCode()) {
                        case -751882236:
                            if (channel.equals("MC|ItemName")) {
                                z = true;
                                break;
                            }
                            break;
                        case -708575099:
                            if (channel.equals("MC|AutoCmd")) {
                                z = true;
                                break;
                            }
                            break;
                        case -592727859:
                            if (channel.equals("MC|AdvCmd")) {
                                z = true;
                                break;
                            }
                            break;
                        case -563769974:
                            if (channel.equals("MC|Beacon")) {
                                z = true;
                                break;
                            }
                            break;
                        case -296231034:
                            if (channel.equals("MC|BEdit")) {
                                z = true;
                                break;
                            }
                            break;
                        case -295809223:
                            if (channel.equals("MC|BSign")) {
                                z = false;
                                break;
                            }
                            break;
                        case -278283530:
                            if (channel.equals("MC|TrSel")) {
                                z = true;
                                break;
                            }
                            break;
                        case -62698213:
                            if (channel.equals("MC|Struct")) {
                                z = true;
                                break;
                            }
                            break;
                        case 1626013338:
                            if (channel.equals("MC|PickItem")) {
                                z = true;
                                break;
                            }
                            break;
                    }
                    switch (z) {
                        case false:
                        case true:
                            wrapper.setId(11);
                            Item book = (Item) wrapper.read(Type.ITEM);
                            wrapper.write(Type.FLAT_ITEM, ((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol).getItemRewriter().handleItemToServer(book));
                            boolean signing = channel.equals("MC|BSign");
                            wrapper.write(Type.BOOLEAN, Boolean.valueOf(signing));
                            return;
                        case true:
                            wrapper.setId(28);
                            return;
                        case true:
                            byte type = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            if (type == 0) {
                                wrapper.setId(34);
                                wrapper.cancel();
                                ViaBackwards.getPlatform().getLogger().warning("Client send MC|AdvCmd custom payload to update command block, weird!");
                                return;
                            } else if (type == 1) {
                                wrapper.setId(35);
                                wrapper.write(Type.VAR_INT, (Integer) wrapper.read(Type.INT));
                                wrapper.passthrough(Type.STRING);
                                wrapper.passthrough(Type.BOOLEAN);
                                return;
                            } else {
                                wrapper.cancel();
                                return;
                            }
                        case true:
                            wrapper.setId(34);
                            int x = ((Integer) wrapper.read(Type.INT)).intValue();
                            int y = ((Integer) wrapper.read(Type.INT)).intValue();
                            int z2 = ((Integer) wrapper.read(Type.INT)).intValue();
                            wrapper.write(Type.POSITION, new Position(x, (short) y, z2));
                            wrapper.passthrough(Type.STRING);
                            byte flags = 0;
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags = (byte) (0 | 1);
                            }
                            String mode = (String) wrapper.read(Type.STRING);
                            int modeId = mode.equals("SEQUENCE") ? 0 : mode.equals("AUTO") ? 1 : 2;
                            wrapper.write(Type.VAR_INT, Integer.valueOf(modeId));
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags = (byte) (flags | 2);
                            }
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags = (byte) (flags | 4);
                            }
                            wrapper.write(Type.BYTE, Byte.valueOf(flags));
                            return;
                        case true:
                            wrapper.setId(37);
                            int x2 = ((Integer) wrapper.read(Type.INT)).intValue();
                            int y2 = ((Integer) wrapper.read(Type.INT)).intValue();
                            int z3 = ((Integer) wrapper.read(Type.INT)).intValue();
                            wrapper.write(Type.POSITION, new Position(x2, (short) y2, z3));
                            wrapper.write(Type.VAR_INT, Integer.valueOf(((Byte) wrapper.read(Type.BYTE)).byteValue() - 1));
                            String mode2 = (String) wrapper.read(Type.STRING);
                            int modeId2 = mode2.equals("SAVE") ? 0 : mode2.equals("LOAD") ? 1 : mode2.equals("CORNER") ? 2 : 3;
                            wrapper.write(Type.VAR_INT, Integer.valueOf(modeId2));
                            wrapper.passthrough(Type.STRING);
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            wrapper.write(Type.BYTE, Byte.valueOf(((Integer) wrapper.read(Type.INT)).byteValue()));
                            String str = (String) wrapper.read(Type.STRING);
                            char c = mode2.equals("NONE") ? (char) 0 : mode2.equals("LEFT_RIGHT") ? (char) 1 : (char) 2;
                            String str2 = (String) wrapper.read(Type.STRING);
                            char c2 = mode2.equals("NONE") ? (char) 0 : mode2.equals("CLOCKWISE_90") ? (char) 1 : mode2.equals("CLOCKWISE_180") ? (char) 2 : (char) 3;
                            wrapper.passthrough(Type.STRING);
                            byte flags2 = 0;
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags2 = (byte) (0 | 1);
                            }
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags2 = (byte) (flags2 | 2);
                            }
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                flags2 = (byte) (flags2 | 4);
                            }
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.VAR_LONG);
                            wrapper.write(Type.BYTE, Byte.valueOf(flags2));
                            return;
                        case true:
                            wrapper.setId(32);
                            wrapper.write(Type.VAR_INT, (Integer) wrapper.read(Type.INT));
                            wrapper.write(Type.VAR_INT, (Integer) wrapper.read(Type.INT));
                            return;
                        case true:
                            wrapper.setId(31);
                            wrapper.write(Type.VAR_INT, (Integer) wrapper.read(Type.INT));
                            return;
                        case true:
                            wrapper.setId(21);
                            return;
                        default:
                            String newChannel = InventoryPackets.getNewPluginChannelId(channel);
                            if (newChannel == null) {
                                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    ViaBackwards.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + channel);
                                }
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Type.STRING, newChannel);
                            if (newChannel.equals("minecraft:register") || newChannel.equals("minecraft:unregister")) {
                                String[] channels = new String((byte[]) wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("��");
                                List<String> rewrittenChannels = new ArrayList<>();
                                for (String s : channels) {
                                    String rewritten = InventoryPackets.getNewPluginChannelId(s);
                                    if (rewritten != null) {
                                        rewrittenChannels.add(rewritten);
                                    } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        ViaBackwards.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + s);
                                    }
                                }
                                if (!rewrittenChannels.isEmpty()) {
                                    wrapper.write(Type.REMAINING_BYTES, Joiner.on((char) 0).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                                    return;
                                } else {
                                    wrapper.cancel();
                                    return;
                                }
                            }
                            return;
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.STATISTICS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13.12.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int size = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int newSize = size;
                        for (int i = 0; i < size; i++) {
                            int categoryId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            int statisticId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            String name = "";
                            switch (categoryId) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                    wrapper.read(Type.VAR_INT);
                                    newSize--;
                                    continue;
                                case 8:
                                    name = ((Protocol1_12_2To1_13) PlayerPacket1_13.this.protocol).getMappingData().getStatisticMappings().get(statisticId);
                                    if (name == null) {
                                        wrapper.read(Type.VAR_INT);
                                        newSize--;
                                        continue;
                                    }
                                    break;
                            }
                            wrapper.write(Type.STRING, name);
                            wrapper.passthrough(Type.VAR_INT);
                        }
                        if (newSize != size) {
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(newSize));
                        }
                    }
                });
            }
        });
    }

    public static boolean startsWithIgnoreCase(String string, String prefix) {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
