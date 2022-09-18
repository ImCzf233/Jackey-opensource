package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/ListSubCmd.class */
public class ListSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "list";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Shows lists of the versions from logged in players";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String usage() {
        return "list";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        ViaCommandSender[] onlinePlayers;
        Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap<>(o1, o2 -> {
            return ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1);
        });
        for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
            int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
            ProtocolVersion key = ProtocolVersion.getProtocol(playerVersion);
            playerVersions.computeIfAbsent(key, s -> {
                return new HashSet();
            }).add(p.getName());
        }
        for (Map.Entry<ProtocolVersion, Set<String>> entry : playerVersions.entrySet()) {
            sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", entry.getKey().getName(), Integer.valueOf(entry.getValue().size()), entry.getValue());
        }
        playerVersions.clear();
        return true;
    }
}
