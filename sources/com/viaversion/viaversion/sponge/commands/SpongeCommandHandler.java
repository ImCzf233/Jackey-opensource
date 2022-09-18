package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.ArgumentReader;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/commands/SpongeCommandHandler.class */
public class SpongeCommandHandler extends ViaCommandHandler implements Command.Raw {
    public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) {
        String[] args = arguments.input().length() > 0 ? arguments.input().split(" ") : new String[0];
        onCommand(new SpongeCommandSender(cause), args);
        return CommandResult.success();
    }

    public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) {
        String[] args = arguments.input().split(" ", -1);
        return (List) onTabComplete(new SpongeCommandSender(cause), args).stream().map(CommandCompletion::of).collect(Collectors.toList());
    }

    public boolean canExecute(CommandCause cause) {
        return cause.hasPermission("viaversion.admin");
    }

    public Optional<Component> shortDescription(CommandCause cause) {
        return Optional.of(Component.text("Shows ViaVersion Version and more."));
    }

    public Optional<Component> extendedDescription(CommandCause cause) {
        return shortDescription(cause);
    }

    public Optional<Component> help(@NotNull CommandCause cause) {
        return Optional.empty();
    }

    public Component usage(CommandCause cause) {
        return Component.text("Usage /viaversion");
    }
}
