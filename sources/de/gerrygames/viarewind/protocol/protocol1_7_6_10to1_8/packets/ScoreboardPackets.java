package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.util.ChatColorUtil;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/ScoreboardPackets.class */
public class ScoreboardPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SCOREBOARD_OBJECTIVE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    String name = (String) packetWrapper.passthrough(Type.STRING);
                    if (name.length() > 16) {
                        Type<String> type = Type.STRING;
                        String substring = name.substring(0, 16);
                        name = substring;
                        packetWrapper.set(type, 0, substring);
                    }
                    byte mode = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    Scoreboard scoreboard = (Scoreboard) packetWrapper.user().get(Scoreboard.class);
                    if (mode == 0) {
                        if (scoreboard.objectiveExists(name)) {
                            packetWrapper.cancel();
                            return;
                        }
                        scoreboard.addObjective(name);
                    } else if (mode == 1) {
                        if (!scoreboard.objectiveExists(name)) {
                            packetWrapper.cancel();
                            return;
                        }
                        if (scoreboard.getColorIndependentSidebar() != null) {
                            String username = packetWrapper.user().getProtocolInfo().getUsername();
                            Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                            if (color.isPresent()) {
                                String sidebar = scoreboard.getColorDependentSidebar().get(color.get());
                                if (name.equals(sidebar)) {
                                    PacketWrapper sidebarPacket = PacketWrapper.create(61, (ByteBuf) null, packetWrapper.user());
                                    sidebarPacket.write(Type.BYTE, (byte) 1);
                                    sidebarPacket.write(Type.STRING, scoreboard.getColorIndependentSidebar());
                                    PacketUtil.sendPacket(sidebarPacket, Protocol1_7_6_10TO1_8.class);
                                }
                            }
                        }
                        scoreboard.removeObjective(name);
                    } else if (mode == 2 && !scoreboard.objectiveExists(name)) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (mode == 0 || mode == 2) {
                        String displayName = (String) packetWrapper.passthrough(Type.STRING);
                        if (displayName.length() > 32) {
                            packetWrapper.set(Type.STRING, 1, displayName.substring(0, 32));
                        }
                        packetWrapper.read(Type.STRING);
                    } else {
                        packetWrapper.write(Type.STRING, "");
                    }
                    packetWrapper.write(Type.BYTE, Byte.valueOf(mode));
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.UPDATE_SCORE, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    String name;
                    Scoreboard scoreboard = (Scoreboard) packetWrapper.user().get(Scoreboard.class);
                    String name2 = (String) packetWrapper.passthrough(Type.STRING);
                    byte mode = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                    if (mode == 1) {
                        name = scoreboard.removeTeamForScore(name2);
                    } else {
                        name = scoreboard.sendTeamForScore(name2);
                    }
                    if (name.length() > 16) {
                        name = ChatColorUtil.stripColor(name);
                        if (name.length() > 16) {
                            name = name.substring(0, 16);
                        }
                    }
                    packetWrapper.set(Type.STRING, 0, name);
                    String objective = (String) packetWrapper.read(Type.STRING);
                    if (objective.length() > 16) {
                        objective = objective.substring(0, 16);
                    }
                    if (mode != 1) {
                        int score = ((Integer) packetWrapper.read(Type.VAR_INT)).intValue();
                        packetWrapper.write(Type.STRING, objective);
                        packetWrapper.write(Type.INT, Integer.valueOf(score));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.DISPLAY_SCOREBOARD, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                map(Type.STRING);
                handler(packetWrapper -> {
                    byte position = ((Byte) packetWrapper.get(Type.BYTE, 0)).byteValue();
                    String name = (String) packetWrapper.get(Type.STRING, 0);
                    Scoreboard scoreboard = (Scoreboard) packetWrapper.user().get(Scoreboard.class);
                    if (position > 2) {
                        byte receiverTeamColor = (byte) (position - 3);
                        scoreboard.getColorDependentSidebar().put(Byte.valueOf(receiverTeamColor), name);
                        String username = packetWrapper.user().getProtocolInfo().getUsername();
                        Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                        position = (!color.isPresent() || color.get().byteValue() != receiverTeamColor) ? (byte) -1 : (byte) 1;
                    } else if (position == 1) {
                        scoreboard.setColorIndependentSidebar(name);
                        String username2 = packetWrapper.user().getProtocolInfo().getUsername();
                        Optional<Byte> color2 = scoreboard.getPlayerTeamColor(username2);
                        if (color2.isPresent() && scoreboard.getColorDependentSidebar().containsKey(color2.get())) {
                            position = -1;
                        }
                    }
                    if (position == -1) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.set(Type.BYTE, 0, Byte.valueOf(position));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.TEAMS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(packetWrapper -> {
                    String team = (String) packetWrapper.get(Type.STRING, 0);
                    if (team == null) {
                        packetWrapper.cancel();
                        return;
                    }
                    byte mode = ((Byte) packetWrapper.passthrough(Type.BYTE)).byteValue();
                    Scoreboard scoreboard = (Scoreboard) packetWrapper.user().get(Scoreboard.class);
                    if (mode != 0 && !scoreboard.teamExists(team)) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (mode == 0 && scoreboard.teamExists(team)) {
                        scoreboard.removeTeam(team);
                        PacketWrapper remove = PacketWrapper.create(62, (ByteBuf) null, packetWrapper.user());
                        remove.write(Type.STRING, team);
                        remove.write(Type.BYTE, (byte) 1);
                        PacketUtil.sendPacket(remove, Protocol1_7_6_10TO1_8.class, true, true);
                    }
                    if (mode == 0) {
                        scoreboard.addTeam(team);
                    } else if (mode == 1) {
                        scoreboard.removeTeam(team);
                    }
                    if (mode == 0 || mode == 2) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.BYTE);
                        packetWrapper.read(Type.STRING);
                        byte color = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                        if (mode == 2 && scoreboard.getTeamColor(team).get().byteValue() != color) {
                            packetWrapper.user().getProtocolInfo().getUsername();
                            String sidebar = scoreboard.getColorDependentSidebar().get(Byte.valueOf(color));
                            PacketWrapper sidebarPacket = packetWrapper.create(61);
                            sidebarPacket.write(Type.BYTE, (byte) 1);
                            sidebarPacket.write(Type.STRING, sidebar == null ? "" : sidebar);
                            PacketUtil.sendPacket(sidebarPacket, Protocol1_7_6_10TO1_8.class);
                        }
                        scoreboard.setTeamColor(team, Byte.valueOf(color));
                    }
                    if (mode == 0 || mode == 3 || mode == 4) {
                        byte color2 = scoreboard.getTeamColor(team).get().byteValue();
                        String[] entries = (String[]) packetWrapper.read(Type.STRING_ARRAY);
                        List<String> entryList = new ArrayList<>();
                        for (String entry : entries) {
                            String username = packetWrapper.user().getProtocolInfo().getUsername();
                            if (mode == 4) {
                                if (scoreboard.isPlayerInTeam(entry, team)) {
                                    scoreboard.removePlayerFromTeam(entry, team);
                                    if (entry.equals(username)) {
                                        PacketWrapper sidebarPacket2 = packetWrapper.create(61);
                                        sidebarPacket2.write(Type.BYTE, (byte) 1);
                                        sidebarPacket2.write(Type.STRING, scoreboard.getColorIndependentSidebar() == null ? "" : scoreboard.getColorIndependentSidebar());
                                        PacketUtil.sendPacket(sidebarPacket2, Protocol1_7_6_10TO1_8.class);
                                    }
                                }
                            } else {
                                scoreboard.addPlayerToTeam(entry, team);
                                if (entry.equals(username) && scoreboard.getColorDependentSidebar().containsKey(Byte.valueOf(color2))) {
                                    PacketWrapper displayObjective = packetWrapper.create(61);
                                    displayObjective.write(Type.BYTE, (byte) 1);
                                    displayObjective.write(Type.STRING, scoreboard.getColorDependentSidebar().get(Byte.valueOf(color2)));
                                    PacketUtil.sendPacket(displayObjective, Protocol1_7_6_10TO1_8.class);
                                }
                            }
                            entryList.add(entry);
                        }
                        packetWrapper.write(Type.SHORT, Short.valueOf((short) entryList.size()));
                        for (String entry2 : entryList) {
                            packetWrapper.write(Type.STRING, entry2);
                        }
                    }
                });
            }
        });
    }
}
