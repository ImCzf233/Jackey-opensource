package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: HideCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HideCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/HideCommand.class */
public final class HideCommand extends Command {
    public HideCommand() {
        super("hide", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Object obj;
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length <= 1) {
            chatSyntax("hide <module/list/clear/reset/category>");
        } else if (StringsKt.equals(args[1], "list", true)) {
            chat("§c§lHidden");
            Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Module it = (Module) element$iv$iv;
                if (!it.getArray()) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $this$forEach$iv = (List) destination$iv$iv;
            for (Object element$iv : $this$forEach$iv) {
                Module it2 = (Module) element$iv;
                ClientUtils.displayChatMessage(Intrinsics.stringPlus("§6> §c", it2.getName()));
            }
        } else if (StringsKt.equals(args[1], "clear", true)) {
            Iterator<Module> it3 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
            while (it3.hasNext()) {
                it3.next().setArray(true);
            }
            chat("Cleared hidden modules.");
        } else if (StringsKt.equals(args[1], "reset", true)) {
            Iterator<Module> it4 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
            while (it4.hasNext()) {
                Module module = it4.next();
                module.setArray(((ModuleInfo) module.getClass().getAnnotation(ModuleInfo.class)).array());
            }
            chat("Reset hidden modules.");
        } else if (!StringsKt.equals(args[1], "category", true)) {
            Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(args[1]);
            if (module2 == null) {
                chat("Module §a§l" + args[1] + "§3 not found.");
                return;
            }
            module2.setArray(!module2.getArray());
            chat("Module §a§l" + module2.getName() + "§3 is now §a§l" + (module2.getArray() ? "visible" : "invisible") + "§3 on the array list.");
            playEdit();
        } else if (args.length < 3) {
            chatSyntax("hide category <name>");
        } else {
            Iterator<T> it5 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
            while (true) {
                if (!it5.hasNext()) {
                    obj = null;
                    break;
                }
                Object next = it5.next();
                Module it6 = (Module) next;
                if (StringsKt.equals(it6.getCategory().getDisplayName(), args[2], true)) {
                    obj = next;
                    break;
                }
            }
            if (obj == null) {
                chat("Couldn't find any category named §7" + args[2] + "§3!");
                return;
            }
            Iterable $this$filter$iv2 = LiquidBounce.INSTANCE.getModuleManager().getModules();
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                Module it7 = (Module) element$iv$iv2;
                if (StringsKt.equals(it7.getCategory().getDisplayName(), args[2], true)) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
            }
            Iterable $this$forEach$iv2 = (List) destination$iv$iv2;
            for (Object element$iv2 : $this$forEach$iv2) {
                Module it8 = (Module) element$iv2;
                it8.setArray(false);
            }
            chat("All modules in category §7" + args[2] + "§3 is now §a§lhidden.");
        }
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        String moduleName = args[0];
        switch (args.length) {
            case 1:
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
                List moduleList = CollectionsKt.toMutableList((Collection) ((List) destination$iv$iv2));
                Iterable $this$filter$iv2 = CollectionsKt.listOf((Object[]) new String[]{"category", "list", "clear", "reset"});
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    String it3 = (String) element$iv$iv2;
                    if (StringsKt.startsWith(it3, moduleName, true)) {
                        destination$iv$iv3.add(element$iv$iv2);
                    }
                }
                moduleList.addAll((List) destination$iv$iv3);
                return moduleList;
            case 2:
                if (StringsKt.equals(moduleName, "category", true)) {
                    ModuleCategory[] values = ModuleCategory.values();
                    Collection destination$iv$iv4 = new ArrayList(values.length);
                    int i = 0;
                    int length = values.length;
                    while (i < length) {
                        i++;
                        destination$iv$iv4.add(values[i].getDisplayName());
                    }
                    Iterable $this$filter$iv3 = (List) destination$iv$iv4;
                    Collection destination$iv$iv5 = new ArrayList();
                    for (Object element$iv$iv3 : $this$filter$iv3) {
                        String it4 = (String) element$iv$iv3;
                        if (StringsKt.startsWith(it4, args[1], true)) {
                            destination$iv$iv5.add(element$iv$iv3);
                        }
                    }
                    return CollectionsKt.toList((List) destination$iv$iv5);
                }
                break;
        }
        return CollectionsKt.emptyList();
    }
}
