package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/PPSSubCmd.class */
public class PPSSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "pps";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Shows the packets per second of online players";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String usage() {
        return "pps";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        ViaCommandSender[] onlinePlayers;
        Map<Integer, Set<String>> playerVersions = new HashMap<>();
        int totalPackets = 0;
        int clients = 0;
        long max = 0;
        for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
            int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
            if (!playerVersions.containsKey(Integer.valueOf(playerVersion))) {
                playerVersions.put(Integer.valueOf(playerVersion), new HashSet<>());
            }
            UserConnection uc = Via.getManager().getConnectionManager().getConnectedClient(p.getUUID());
            if (uc != null && uc.getPacketTracker().getPacketsPerSecond() > -1) {
                playerVersions.get(Integer.valueOf(playerVersion)).add(p.getName() + " (" + uc.getPacketTracker().getPacketsPerSecond() + " PPS)");
                totalPackets = (int) (totalPackets + uc.getPacketTracker().getPacketsPerSecond());
                if (uc.getPacketTracker().getPacketsPerSecond() > max) {
                    max = uc.getPacketTracker().getPacketsPerSecond();
                }
                clients++;
            }
        }
        Map<Integer, Set<String>> sorted = new TreeMap<>(playerVersions);
        sendMessage(sender, "&4Live Packets Per Second", new Object[0]);
        if (clients > 1) {
            sendMessage(sender, "&cAverage: &f" + (totalPackets / clients), new Object[0]);
            sendMessage(sender, "&cHighest: &f" + max, new Object[0]);
        }
        if (clients == 0) {
            sendMessage(sender, "&cNo clients to display.", new Object[0]);
        }
        for (Map.Entry<Integer, Set<String>> entry : sorted.entrySet()) {
            sendMessage(sender, "&8[&6%s&8]: &b%s", ProtocolVersion.getProtocol(entry.getKey().intValue()).getName(), entry.getValue());
        }
        sorted.clear();
        return true;
    }
}
