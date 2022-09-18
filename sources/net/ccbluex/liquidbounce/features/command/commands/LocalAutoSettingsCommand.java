package net.ccbluex.liquidbounce.features.command.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;

/* compiled from: LocalAutoSettingsCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "getLocalSettings", "Ljava/io/File;", "()[Ljava/io/File;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand.class */
public final class LocalAutoSettingsCommand extends Command {
    public LocalAutoSettingsCommand() {
        super("localautosettings", new String[]{"localsetting", "localsettings", "localconfig"});
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:10:0x003a
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@org.jetbrains.annotations.NotNull java.lang.String[] r7) {
        /*
            Method dump skipped, instructions count: 629
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.commands.LocalAutoSettingsCommand.execute(java.lang.String[]):void");
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
                Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"delete", "list", "load", "save"});
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    if (StringsKt.startsWith((String) element$iv$iv, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                return (List) destination$iv$iv;
            case 2:
                String lowerCase = args[0].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual(lowerCase, "delete") ? true : Intrinsics.areEqual(lowerCase, "load")) {
                    File[] settings = getLocalSettings();
                    if (settings == null) {
                        return CollectionsKt.emptyList();
                    }
                    Collection destination$iv$iv2 = new ArrayList(settings.length);
                    int i = 0;
                    int length = settings.length;
                    while (i < length) {
                        i++;
                        destination$iv$iv2.add(settings[i].getName());
                    }
                    Iterable $this$filter$iv2 = (List) destination$iv$iv2;
                    Collection destination$iv$iv3 = new ArrayList();
                    for (Object element$iv$iv2 : $this$filter$iv2) {
                        String it = (String) element$iv$iv2;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        if (StringsKt.startsWith(it, args[1], true)) {
                            destination$iv$iv3.add(element$iv$iv2);
                        }
                    }
                    return (List) destination$iv$iv3;
                }
                return CollectionsKt.emptyList();
            case 3:
                if (!StringsKt.equals(args[0], "save", true)) {
                    return CollectionsKt.emptyList();
                }
                Iterable $this$filter$iv3 = CollectionsKt.listOf((Object[]) new String[]{"all", "states", "binds", "values"});
                Collection destination$iv$iv4 = new ArrayList();
                for (Object element$iv$iv3 : $this$filter$iv3) {
                    if (StringsKt.startsWith((String) element$iv$iv3, args[2], true)) {
                        destination$iv$iv4.add(element$iv$iv3);
                    }
                }
                return (List) destination$iv$iv4;
            default:
                return CollectionsKt.emptyList();
        }
    }

    private final File[] getLocalSettings() {
        return LiquidBounce.INSTANCE.getFileManager().settingsDir.listFiles();
    }
}
