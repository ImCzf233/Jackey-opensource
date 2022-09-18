package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import org.jetbrains.annotations.NotNull;

/* compiled from: ToggleCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ToggleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ToggleCommand.class */
public final class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", new String[]{"t"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length > 1) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(args[1]);
            if (module == null) {
                chat("Module '" + args[1] + "' not found.");
                return;
            }
            if (args.length > 2) {
                String newState = args[2].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(newState, "this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual(newState, "on") || Intrinsics.areEqual(newState, "off")) {
                    module.setState(Intrinsics.areEqual(newState, "on"));
                } else {
                    chatSyntax("toggle <module> [on/off]");
                    return;
                }
            } else {
                module.toggle();
            }
            chat((module.getState() ? "Enabled" : "Disabled") + " module §8" + module.getName() + "§3.");
            return;
        }
        chatSyntax("toggle <module> [on/off]");
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        String moduleName = args[0];
        if (args.length != 1) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Module it = (Module) item$iv$iv;
            destination$iv$iv.add(it.getName());
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it2 = (String) element$iv$iv;
            if (StringsKt.startsWith(it2, moduleName, true)) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        return CollectionsKt.toList((List) destination$iv$iv2);
    }
}
