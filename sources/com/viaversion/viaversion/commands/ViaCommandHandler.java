package com.viaversion.viaversion.commands;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.command.ViaVersionCommand;
import com.viaversion.viaversion.commands.defaultsubs.AutoTeamSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DebugSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DisplayLeaksSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DontBugMeSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DumpSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.HelpSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.ListSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.PPSSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.ReloadSubCmd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/ViaCommandHandler.class */
public abstract class ViaCommandHandler implements ViaVersionCommand {
    private final Map<String, ViaSubCommand> commandMap = new HashMap();

    public ViaCommandHandler() {
        registerDefaults();
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public void registerSubCommand(ViaSubCommand command) {
        Preconditions.checkArgument(command.name().matches("^[a-z0-9_-]{3,15}$"), command.name() + " is not a valid sub-command name.");
        Preconditions.checkArgument(!hasSubCommand(command.name()), "ViaSubCommand " + command.name() + " does already exists!");
        this.commandMap.put(command.name().toLowerCase(Locale.ROOT), command);
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public boolean hasSubCommand(String name) {
        return this.commandMap.containsKey(name.toLowerCase(Locale.ROOT));
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public ViaSubCommand getSubCommand(String name) {
        return this.commandMap.get(name.toLowerCase(Locale.ROOT));
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public boolean onCommand(ViaCommandSender sender, String[] args) {
        if (args.length == 0) {
            showHelp(sender);
            return false;
        } else if (!hasSubCommand(args[0])) {
            sender.sendMessage(ViaSubCommand.color("&cThis command does not exist."));
            showHelp(sender);
            return false;
        } else {
            ViaSubCommand handler = getSubCommand(args[0]);
            if (!hasPermission(sender, handler.permission())) {
                sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
                return false;
            }
            String[] subArgs = (String[]) Arrays.copyOfRange(args, 1, args.length);
            boolean result = handler.execute(sender, subArgs);
            if (!result) {
                sender.sendMessage("Usage: /viaversion " + handler.usage());
            }
            return result;
        }
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
        Set<ViaSubCommand> allowed = calculateAllowedCommands(sender);
        List<String> output = new ArrayList<>();
        if (args.length == 1) {
            if (!args[0].isEmpty()) {
                for (ViaSubCommand sub : allowed) {
                    if (sub.name().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT))) {
                        output.add(sub.name());
                    }
                }
            } else {
                for (ViaSubCommand sub2 : allowed) {
                    output.add(sub2.name());
                }
            }
        } else if (args.length >= 2 && getSubCommand(args[0]) != null) {
            ViaSubCommand sub3 = getSubCommand(args[0]);
            if (!allowed.contains(sub3)) {
                return output;
            }
            String[] subArgs = (String[]) Arrays.copyOfRange(args, 1, args.length);
            List<String> tab = sub3.onTabComplete(sender, subArgs);
            Collections.sort(tab);
            return tab;
        }
        return output;
    }

    @Override // com.viaversion.viaversion.api.command.ViaVersionCommand
    public void showHelp(ViaCommandSender sender) {
        Set<ViaSubCommand> allowed = calculateAllowedCommands(sender);
        if (allowed.isEmpty()) {
            sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use these commands!"));
            return;
        }
        sender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
        sender.sendMessage(ViaSubCommand.color("&6Commands:"));
        for (ViaSubCommand cmd : allowed) {
            sender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", cmd.usage(), cmd.description())));
        }
        allowed.clear();
    }

    private Set<ViaSubCommand> calculateAllowedCommands(ViaCommandSender sender) {
        Set<ViaSubCommand> cmds = new HashSet<>();
        for (ViaSubCommand sub : this.commandMap.values()) {
            if (hasPermission(sender, sub.permission())) {
                cmds.add(sub);
            }
        }
        return cmds;
    }

    private boolean hasPermission(ViaCommandSender sender, String permission) {
        return permission == null || sender.hasPermission(permission);
    }

    private void registerDefaults() {
        registerSubCommand(new ListSubCmd());
        registerSubCommand(new PPSSubCmd());
        registerSubCommand(new DebugSubCmd());
        registerSubCommand(new DumpSubCmd());
        registerSubCommand(new DisplayLeaksSubCmd());
        registerSubCommand(new DontBugMeSubCmd());
        registerSubCommand(new AutoTeamSubCmd());
        registerSubCommand(new HelpSubCmd());
        registerSubCommand(new ReloadSubCmd());
    }
}
