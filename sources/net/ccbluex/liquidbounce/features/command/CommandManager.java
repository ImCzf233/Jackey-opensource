package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.commands.AutoDisableCommand;
import net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindsCommand;
import net.ccbluex.liquidbounce.features.command.commands.ConnectCommand;
import net.ccbluex.liquidbounce.features.command.commands.FriendCommand;
import net.ccbluex.liquidbounce.features.command.commands.HClipCommand;
import net.ccbluex.liquidbounce.features.command.commands.HelpCommand;
import net.ccbluex.liquidbounce.features.command.commands.HideCommand;
import net.ccbluex.liquidbounce.features.command.commands.LocalAutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.LocalThemeCommand;
import net.ccbluex.liquidbounce.features.command.commands.MacroCommand;
import net.ccbluex.liquidbounce.features.command.commands.PanicCommand;
import net.ccbluex.liquidbounce.features.command.commands.PathfindingTeleportCommand;
import net.ccbluex.liquidbounce.features.command.commands.PingCommand;
import net.ccbluex.liquidbounce.features.command.commands.PrefixCommand;
import net.ccbluex.liquidbounce.features.command.commands.ReloadCommand;
import net.ccbluex.liquidbounce.features.command.commands.SayCommand;
import net.ccbluex.liquidbounce.features.command.commands.ScriptManagerCommand;
import net.ccbluex.liquidbounce.features.command.commands.ServerInfoCommand;
import net.ccbluex.liquidbounce.features.command.commands.TacoCommand;
import net.ccbluex.liquidbounce.features.command.commands.TargetCommand;
import net.ccbluex.liquidbounce.features.command.commands.TeleportCommand;
import net.ccbluex.liquidbounce.features.command.commands.ThemeCommand;
import net.ccbluex.liquidbounce.features.command.commands.ToggleCommand;
import net.ccbluex.liquidbounce.features.command.commands.UUIDCommand;
import net.ccbluex.liquidbounce.features.command.commands.VClipCommand;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CommandManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\nJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001c\u001a\u00020\nJ\u001d\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\nH\u0002¢\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u001aJ\u0010\u0010\"\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n��\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006#"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "getCommands", "()Ljava/util/List;", "latestAutoComplete", "", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "command", "registerCommands", "unregisterCommand", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/CommandManager.class */
public final class CommandManager {
    @NotNull
    private final List<Command> commands = new ArrayList();
    @NotNull
    private String[] latestAutoComplete = new String[0];
    private char prefix = '.';

    @NotNull
    public final List<Command> getCommands() {
        return this.commands;
    }

    @NotNull
    public final String[] getLatestAutoComplete() {
        return this.latestAutoComplete;
    }

    public final void setLatestAutoComplete(@NotNull String[] strArr) {
        Intrinsics.checkNotNullParameter(strArr, "<set-?>");
        this.latestAutoComplete = strArr;
    }

    public final char getPrefix() {
        return this.prefix;
    }

    public final void setPrefix(char c) {
        this.prefix = c;
    }

    public final void registerCommands() {
        registerCommand(new BindCommand());
        registerCommand(new VClipCommand());
        registerCommand(new HClipCommand());
        registerCommand(new HelpCommand());
        registerCommand(new SayCommand());
        registerCommand(new MacroCommand());
        registerCommand(new FriendCommand());
        registerCommand(new AutoSettingsCommand());
        registerCommand(new LocalAutoSettingsCommand());
        registerCommand(new ServerInfoCommand());
        registerCommand(new ToggleCommand());
        registerCommand(new TargetCommand());
        registerCommand(new TacoCommand());
        registerCommand(new BindsCommand());
        registerCommand(new PanicCommand());
        registerCommand(new PingCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new ScriptManagerCommand());
        registerCommand(new PrefixCommand());
        registerCommand(new HideCommand());
        registerCommand(new AutoDisableCommand());
        registerCommand(new TeleportCommand());
        registerCommand(new PathfindingTeleportCommand());
        registerCommand(new ThemeCommand());
        registerCommand(new LocalThemeCommand());
        registerCommand(new ConnectCommand());
        registerCommand(new UUIDCommand());
    }

    public final void executeCommands(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        for (Command command : this.commands) {
            Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence) input, new String[]{" "}, false, 0, 6, (Object) null);
            Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            String[] args = (String[]) array;
            if (StringsKt.equals(args[0], this.prefix + command.getCommand(), true)) {
                command.execute(args);
                return;
            }
            String[] alias = command.getAlias();
            int i = 0;
            int length = alias.length;
            while (i < length) {
                String alias2 = alias[i];
                i++;
                if (StringsKt.equals(args[0], this.prefix + alias2, true)) {
                    command.execute(args);
                    return;
                }
            }
        }
        ClientUtils.displayChatMessage("§cCommand not found. Type " + this.prefix + "help to view all commands.");
    }

    public final boolean autoComplete(@NotNull String input) {
        String[] strArr;
        Intrinsics.checkNotNullParameter(input, "input");
        String[] completions = getCompletions(input);
        if (completions != null) {
            strArr = completions;
        } else {
            strArr = new String[0];
        }
        this.latestAutoComplete = strArr;
        if (StringsKt.startsWith$default((CharSequence) input, this.prefix, false, 2, (Object) null)) {
            if (!(this.latestAutoComplete.length == 0)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0190 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x011d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String[] getCompletions(java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 681
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.CommandManager.getCompletions(java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0083 A[SYNTHETIC] */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final net.ccbluex.liquidbounce.features.command.Command getCommand(@org.jetbrains.annotations.NotNull java.lang.String r5) {
        /*
            r4 = this;
            r0 = r5
            java.lang.String r1 = "name"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r4
            java.util.List<net.ccbluex.liquidbounce.features.command.Command> r0 = r0.commands
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
            r6 = r0
        L14:
            r0 = r6
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L87
            r0 = r6
            java.lang.Object r0 = r0.next()
            r7 = r0
            r0 = r7
            net.ccbluex.liquidbounce.features.command.Command r0 = (net.ccbluex.liquidbounce.features.command.Command) r0
            r8 = r0
            r0 = 0
            r9 = r0
            r0 = r8
            java.lang.String r0 = r0.getCommand()
            r1 = r5
            r2 = 1
            boolean r0 = kotlin.text.StringsKt.equals(r0, r1, r2)
            if (r0 != 0) goto L7a
            r0 = r8
            java.lang.String[] r0 = r0.getAlias()
            r10 = r0
            r0 = 0
            r11 = r0
            r0 = r10
            r12 = r0
            r0 = 0
            r13 = r0
            r0 = r12
            int r0 = r0.length
            r14 = r0
        L50:
            r0 = r13
            r1 = r14
            if (r0 >= r1) goto L76
            r0 = r12
            r1 = r13
            r0 = r0[r1]
            r15 = r0
            int r13 = r13 + 1
            r0 = r15
            r16 = r0
            r0 = 0
            r17 = r0
            r0 = r16
            r1 = r5
            r2 = 1
            boolean r0 = kotlin.text.StringsKt.equals(r0, r1, r2)
            if (r0 == 0) goto L50
            r0 = 1
            goto L77
        L76:
            r0 = 0
        L77:
            if (r0 == 0) goto L7e
        L7a:
            r0 = 1
            goto L7f
        L7e:
            r0 = 0
        L7f:
            if (r0 == 0) goto L14
            r0 = r7
            goto L88
        L87:
            r0 = 0
        L88:
            net.ccbluex.liquidbounce.features.command.Command r0 = (net.ccbluex.liquidbounce.features.command.Command) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.CommandManager.getCommand(java.lang.String):net.ccbluex.liquidbounce.features.command.Command");
    }

    public final boolean registerCommand(@NotNull Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        return this.commands.add(command);
    }

    public final boolean unregisterCommand(@Nullable Command command) {
        return this.commands.remove(command);
    }
}
