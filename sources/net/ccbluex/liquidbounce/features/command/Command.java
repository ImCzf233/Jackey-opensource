package net.ccbluex.liquidbounce.features.command;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

/* compiled from: Command.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018��2\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0004J\u001b\u0010\u000f\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0004¢\u0006\u0002\u0010\u0011J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0003H\u0004J\b\u0010\u0013\u001a\u00020\rH\u0004J\u001b\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H&¢\u0006\u0002\u0010\u0011J\b\u0010\u0016\u001a\u00020\rH\u0004J!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00182\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016¢\u0006\u0002\u0010\u0019R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "command", "", "alias", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "getAlias", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getCommand", "()Ljava/lang/String;", "chat", "", "msg", "chatSyntax", "syntaxes", "([Ljava/lang/String;)V", "syntax", "chatSyntaxError", "execute", "args", "playEdit", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/Command.class */
public abstract class Command extends MinecraftInstance {
    @NotNull
    private final String command;
    @NotNull
    private final String[] alias;

    public abstract void execute(@NotNull String[] strArr);

    public Command(@NotNull String command, @NotNull String[] alias) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(alias, "alias");
        this.command = command;
        this.alias = alias;
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    @NotNull
    public final String[] getAlias() {
        return this.alias;
    }

    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return CollectionsKt.emptyList();
    }

    public final void chat(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ClientUtils.displayChatMessage(Intrinsics.stringPlus("§8[§9§lJackey§8] §3", msg));
    }

    public final void chatSyntax(@NotNull String syntax) {
        Intrinsics.checkNotNullParameter(syntax, "syntax");
        ClientUtils.displayChatMessage("§8[§9§lJackey§8] §3Syntax: §7" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + syntax);
    }

    public final void chatSyntax(@NotNull String[] syntaxes) {
        Intrinsics.checkNotNullParameter(syntaxes, "syntaxes");
        ClientUtils.displayChatMessage("§8[§9§lJackey§8] §3Syntax:");
        int i = 0;
        int length = syntaxes.length;
        while (i < length) {
            String syntax = syntaxes[i];
            i++;
            StringBuilder append = new StringBuilder().append("§8> §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(this.command).append(' ');
            String lowerCase = syntax.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            ClientUtils.displayChatMessage(append.append(lowerCase).toString());
        }
    }

    public final void chatSyntaxError() {
        ClientUtils.displayChatMessage("§8[§9§lJackey§8] §3Syntax error");
    }

    public final void playEdit() {
    }
}
