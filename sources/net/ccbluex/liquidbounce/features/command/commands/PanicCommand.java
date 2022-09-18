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
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

/* compiled from: PanicCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/PanicCommand.class */
public final class PanicCommand extends Command {
    public PanicCommand() {
        super("panic", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        String msg;
        Intrinsics.checkNotNullParameter(args, "args");
        Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Module it = (Module) element$iv$iv;
            if (it.getState()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<Module> modules = (List) destination$iv$iv;
        if (args.length > 1) {
            if (args[1].length() > 0) {
                String lowerCase = args[1].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual(lowerCase, "all")) {
                    msg = "all";
                } else if (Intrinsics.areEqual(lowerCase, "nonrender")) {
                    Iterable $this$filter$iv2 = modules;
                    Collection destination$iv$iv2 = new ArrayList();
                    for (Object element$iv$iv2 : $this$filter$iv2) {
                        Module it2 = (Module) element$iv$iv2;
                        if (it2.getCategory() != ModuleCategory.RENDER) {
                            destination$iv$iv2.add(element$iv$iv2);
                        }
                    }
                    modules = (List) destination$iv$iv2;
                    msg = "all non-render";
                } else {
                    ModuleCategory[] values = ModuleCategory.values();
                    Collection destination$iv$iv3 = new ArrayList();
                    int i = 0;
                    int length = values.length;
                    while (i < length) {
                        ModuleCategory moduleCategory = values[i];
                        i++;
                        if (StringsKt.equals(moduleCategory.getDisplayName(), args[1], true)) {
                            destination$iv$iv3.add(moduleCategory);
                        }
                    }
                    List categories = (List) destination$iv$iv3;
                    if (categories.isEmpty()) {
                        chat("Category " + args[1] + " not found");
                        return;
                    }
                    ModuleCategory category = (ModuleCategory) categories.get(0);
                    Iterable $this$filter$iv3 = modules;
                    Collection destination$iv$iv4 = new ArrayList();
                    for (Object element$iv$iv3 : $this$filter$iv3) {
                        Module it3 = (Module) element$iv$iv3;
                        if (it3.getCategory() == category) {
                            destination$iv$iv4.add(element$iv$iv3);
                        }
                    }
                    modules = (List) destination$iv$iv4;
                    msg = Intrinsics.stringPlus("all ", category.getDisplayName());
                }
                for (Module module : modules) {
                    module.setState(false);
                }
                chat("Disabled " + msg + " modules.");
                return;
            }
        }
        chatSyntax("panic <all/nonrender/combat/player/movement/render/world/misc/exploit/fun>");
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
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"all", "nonrender", "combat", "player", "movement", "render", "world", "misc", "exploit", "fun"});
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
