package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: TargetCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/TargetCommand.class */
public final class TargetCommand extends Command {
    public TargetCommand() {
        super("target", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "players", true)) {
                EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                chat("§7Target player toggled " + (EntityUtils.targetPlayer ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "mobs", true)) {
                EntityUtils.targetMobs = !EntityUtils.targetMobs;
                chat("§7Target mobs toggled " + (EntityUtils.targetMobs ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "animals", true)) {
                EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                chat("§7Target animals toggled " + (EntityUtils.targetAnimals ? "on" : "off") + '.');
                playEdit();
                return;
            } else if (StringsKt.equals(args[1], "invisible", true)) {
                EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                chat("§7Target Invisible toggled " + (EntityUtils.targetInvisible ? "on" : "off") + '.');
                playEdit();
                return;
            }
        }
        chatSyntax("target <players/mobs/animals/invisible>");
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        if (args.length != 1) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"players", "mobs", "animals", "invisible"});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (StringsKt.startsWith(it, args[0], true)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }
}
