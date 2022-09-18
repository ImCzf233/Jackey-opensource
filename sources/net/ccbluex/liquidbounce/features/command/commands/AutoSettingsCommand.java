package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.Thread;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: AutoSettingsCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\fJ;\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0018\u0010\u0012\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0014\u0012\u0004\u0012\u00020\t0\u0013H\u0002¢\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\u0017R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0018"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "autoSettingFiles", "", "", "loadingLock", Constants.OBJECT, "execute", "", "args", "", "([Ljava/lang/String;)V", "loadSettings", "useCached", "", "join", "", "callback", "Lkotlin/Function1;", "", "(ZLjava/lang/Long;Lkotlin/jvm/functions/Function1;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand.class */
public final class AutoSettingsCommand extends Command {
    @NotNull
    private final Object loadingLock = new Object();
    @Nullable
    private List<String> autoSettingFiles;

    public AutoSettingsCommand() {
        super("autosettings", new String[]{"setting", "settings", "config", "autosetting"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        String str;
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length <= 1) {
            chatSyntax("settings <load/list>");
        } else if (StringsKt.equals(args[1], "load", true)) {
            if (args.length < 3) {
                chatSyntax("settings load <name/url>");
                return;
            }
            if (StringsKt.startsWith$default(args[2], "http", false, 2, (Object) null)) {
                str = args[2];
            } else {
                String lowerCase = args[2].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                str = Intrinsics.stringPlus("https://wysi-foundation.github.io/LiquidCloud/LiquidBounce/settings/", lowerCase);
            }
            String url = str;
            chat("Loading settings...");
            Thread.thread$default(false, false, null, null, 0, new AutoSettingsCommand$execute$1(url, this), 31, null);
        } else if (StringsKt.equals(args[1], "list", true)) {
            chat("Loading settings...");
            loadSettings$default(this, false, null, new AutoSettingsCommand$execute$2(this), 2, null);
        }
    }

    static /* synthetic */ void loadSettings$default(AutoSettingsCommand autoSettingsCommand, boolean z, Long l, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            l = null;
        }
        autoSettingsCommand.loadSettings(z, l, function1);
    }

    private final void loadSettings(boolean useCached, Long join, Function1<? super List<String>, Unit> function1) {
        Thread thread = Thread.thread$default(false, false, null, null, 0, new AutoSettingsCommand$loadSettings$thread$1(this, useCached, function1), 31, null);
        if (join != null) {
            thread.join(join.longValue());
        }
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"list", "load"});
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    String it = (String) element$iv$iv;
                    if (StringsKt.startsWith(it, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                return (List) destination$iv$iv;
            case 2:
                if (StringsKt.equals(args[0], "load", true)) {
                    if (this.autoSettingFiles == null) {
                        loadSettings(true, 500L, AutoSettingsCommand$tabComplete$2.INSTANCE);
                    }
                    if (this.autoSettingFiles != null) {
                        Iterable iterable = this.autoSettingFiles;
                        Intrinsics.checkNotNull(iterable);
                        Iterable $this$filter$iv2 = iterable;
                        Collection destination$iv$iv2 = new ArrayList();
                        for (Object element$iv$iv2 : $this$filter$iv2) {
                            String it2 = (String) element$iv$iv2;
                            if (StringsKt.startsWith(it2, args[1], true)) {
                                destination$iv$iv2.add(element$iv$iv2);
                            }
                        }
                        return (List) destination$iv$iv2;
                    }
                }
                return CollectionsKt.emptyList();
            default:
                return CollectionsKt.emptyList();
        }
    }
}
