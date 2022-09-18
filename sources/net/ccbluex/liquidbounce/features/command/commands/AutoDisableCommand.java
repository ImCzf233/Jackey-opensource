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
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoDisable;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoDisableCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/AutoDisableCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/AutoDisableCommand.class */
public final class AutoDisableCommand extends Command {

    /* compiled from: AutoDisableCommand.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/AutoDisableCommand$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AutoDisable.DisableEvent.values().length];
            iArr[AutoDisable.DisableEvent.FLAG.ordinal()] = 1;
            iArr[AutoDisable.DisableEvent.WORLD_CHANGE.ordinal()] = 2;
            iArr[AutoDisable.DisableEvent.GAME_END.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AutoDisableCommand() {
        super("autodisable", new String[]{"ad"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        String str;
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 2) {
            String lowerCase = args[1].toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase, "list")) {
                chat("§c§lAutoDisable modules:");
                Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Module it = (Module) element$iv$iv;
                    if (it.getAutoDisables().size() > 0) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterable $this$forEach$iv = (List) destination$iv$iv;
                for (Object element$iv : $this$forEach$iv) {
                    Module it2 = (Module) element$iv;
                    StringBuilder append = new StringBuilder().append("§6> §c").append(it2.getName()).append(" §7| §a");
                    Iterable $this$map$iv = it2.getAutoDisables();
                    Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        AutoDisable.DisableEvent d = (AutoDisable.DisableEvent) item$iv$iv;
                        String lowerCase2 = d.name().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                        destination$iv$iv2.add(lowerCase2);
                    }
                    ClientUtils.displayChatMessage(append.append(CollectionsKt.joinToString$default((List) destination$iv$iv2, null, null, null, 0, null, null, 63, null)).toString());
                }
                return;
            } else if (Intrinsics.areEqual(lowerCase, "clear")) {
                Iterable $this$filter$iv2 = LiquidBounce.INSTANCE.getModuleManager().getModules();
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    Module it3 = (Module) element$iv$iv2;
                    if (it3.getAutoDisables().size() > 0) {
                        destination$iv$iv3.add(element$iv$iv2);
                    }
                }
                Iterable $this$forEach$iv2 = (List) destination$iv$iv3;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Module it4 = (Module) element$iv2;
                    it4.getAutoDisables().clear();
                }
                chat("Successfully cleared the AutoDisable list.");
                return;
            }
        } else if (args.length > 2) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(args[1]);
            if (module == null) {
                chat("Module §a§l" + args[1] + "§3 not found.");
                return;
            } else if (StringsKt.equals(args[2], "clear", true)) {
                module.getAutoDisables().clear();
                chat("Module §a§l" + module.getName() + "§3 has been removed from AutoDisable trigger list.");
                playEdit();
                return;
            } else {
                try {
                    String upperCase = args[2].toUpperCase();
                    Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
                    AutoDisable.DisableEvent disableWhen = AutoDisable.DisableEvent.valueOf(upperCase);
                    String added = "will now";
                    if (module.getAutoDisables().contains(disableWhen)) {
                        if (module.getAutoDisables().remove(disableWhen)) {
                            added = "will no longer";
                        }
                    } else {
                        module.getAutoDisables().add(disableWhen);
                    }
                    switch (WhenMappings.$EnumSwitchMapping$0[disableWhen.ordinal()]) {
                        case 1:
                            str = "when you get flagged.";
                            break;
                        case 2:
                            str = "when you change the world.";
                            break;
                        case 3:
                            str = "when the game end.";
                            break;
                        default:
                            str = null;
                            break;
                    }
                    String disableType = str;
                    chat("Module §a§l" + module.getName() + "§3 " + added + " be disabled " + ((Object) disableType));
                    playEdit();
                    return;
                } catch (IllegalArgumentException e) {
                    chat("§c§lWrong auto disable type!");
                    chatSyntax("autodisable <module> <clear/flag/world_change/game_end>");
                    return;
                }
            }
        }
        chatSyntax("autodisable <module/list> <clear/flag/world_change/game_end>");
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
                return CollectionsKt.toList((List) destination$iv$iv2);
            case 2:
                Iterable $this$filter$iv2 = CollectionsKt.listOf((Object[]) new String[]{"clear", "flag", "world_change", "game_end"});
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv2) {
                    String it3 = (String) element$iv$iv2;
                    if (StringsKt.startsWith(it3, args[1], true)) {
                        destination$iv$iv3.add(element$iv$iv2);
                    }
                }
                return (List) destination$iv$iv3;
            default:
                return CollectionsKt.emptyList();
        }
    }
}
