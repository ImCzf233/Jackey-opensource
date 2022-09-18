package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.special.MacroManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: MacroCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/MacroCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/MacroCommand.class */
public final class MacroCommand extends Command {
    public MacroCommand() {
        super("macro", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length > 2) {
            String upperCase = args[2].toUpperCase();
            Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
            int key = Keyboard.getKeyIndex(upperCase);
            if (key == 0) {
                chat("§c§lKeybind doesn't exist, or not allowed.");
                chatSyntax("macro <list/clear/add/remove>");
                return;
            }
            String lowerCase = args[1].toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase, "add")) {
                if (args.length < 4) {
                    chatSyntax("macro add <key name> <message>");
                    return;
                }
                String message = StringUtils.toCompleteString(args, 3);
                boolean existed = MacroManager.INSTANCE.getMacroMapping().containsKey(Integer.valueOf(key));
                MacroManager macroManager = MacroManager.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(message, "message");
                macroManager.addMacro(key, message);
                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                if (existed) {
                    chat("§a§lSuccessfully changed macro in key §7" + ((Object) Keyboard.getKeyName(key)) + " to §r" + ((Object) message) + '.');
                } else {
                    chat("§a§lSuccessfully added §r" + ((Object) message) + " §a§lto key §7" + ((Object) Keyboard.getKeyName(key)) + '.');
                }
                playEdit();
                return;
            } else if (Intrinsics.areEqual(lowerCase, "remove")) {
                if (MacroManager.INSTANCE.getMacroMapping().containsKey(Integer.valueOf(key))) {
                    String lastMessage = MacroManager.INSTANCE.getMacroMapping().get(Integer.valueOf(key));
                    MacroManager.INSTANCE.removeMacro(key);
                    LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                    chat("§a§lSuccessfully removed the macro §r" + ((Object) lastMessage) + " §a§lfrom §7" + ((Object) Keyboard.getKeyName(key)) + '.');
                    playEdit();
                    return;
                }
                chat("§c§lThere's no macro bound to this key.");
                chatSyntax("macro remove <key name>");
                return;
            }
        }
        if (args.length == 2) {
            String lowerCase2 = args[1].toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
            switch (lowerCase2.hashCode()) {
                case -934610812:
                    if (lowerCase2.equals("remove")) {
                        chatSyntax("macro remove <key name>");
                        return;
                    }
                    break;
                case 96417:
                    if (lowerCase2.equals("add")) {
                        chatSyntax("macro add <key name> <message>");
                        return;
                    }
                    break;
                case 3322014:
                    if (lowerCase2.equals("list")) {
                        chat("§6§lMacros:");
                        Map $this$forEach$iv = MacroManager.INSTANCE.getMacroMapping();
                        for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                            ClientUtils.displayChatMessage("§6> §c" + ((Object) Keyboard.getKeyName(element$iv.getKey().intValue())) + ": §r" + element$iv.getValue());
                        }
                        return;
                    }
                    break;
                case 94746189:
                    if (lowerCase2.equals("clear")) {
                        MacroManager.INSTANCE.getMacroMapping().clear();
                        playEdit();
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                        chat("§a§lSuccessfully cleared macro list.");
                        return;
                    }
                    break;
            }
        }
        chatSyntax("macro <list/clear/add/remove>");
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
        Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"add", "remove", "list", "clear"});
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
