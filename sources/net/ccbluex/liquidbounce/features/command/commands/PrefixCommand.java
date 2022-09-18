package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;

/* compiled from: PrefixCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PrefixCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/PrefixCommand.class */
public final class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length <= 1) {
            chatSyntax("prefix <character>");
            return;
        }
        String prefix = args[1];
        if (prefix.length() > 1) {
            chat("§cPrefix can only be one character long!");
            return;
        }
        LiquidBounce.INSTANCE.getCommandManager().setPrefix(StringsKt.single(prefix));
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        chat("Successfully changed command prefix to '§8" + prefix + "§3'");
    }
}
