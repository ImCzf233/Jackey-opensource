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
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: FriendCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/FriendCommand.class */
public final class FriendCommand extends Command {
    public FriendCommand() {
        super("friend", new String[]{"friends"});
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x014d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x00f9 A[SYNTHETIC] */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void execute(@org.jetbrains.annotations.NotNull java.lang.String[] r6) {
        /*
            Method dump skipped, instructions count: 1144
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.commands.FriendCommand.execute(java.lang.String[]):void");
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
                Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{"add", "addall", "remove", "removeall", "list", "clear"});
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
                if (Intrinsics.areEqual(lowerCase, "add")) {
                    Iterable iterable = MinecraftInstance.f362mc.field_71441_e.field_73010_i;
                    Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.playerEntities");
                    Iterable $this$map$iv = iterable;
                    Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        destination$iv$iv2.add(((EntityPlayer) item$iv$iv).func_70005_c_());
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
                } else if (Intrinsics.areEqual(lowerCase, "remove")) {
                    Iterable friends = LiquidBounce.INSTANCE.getFileManager().friendsConfig.getFriends();
                    Intrinsics.checkNotNullExpressionValue(friends, "LiquidBounce.fileManager.friendsConfig.friends");
                    Iterable $this$map$iv2 = friends;
                    Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                    for (Object item$iv$iv2 : $this$map$iv2) {
                        destination$iv$iv4.add(((FriendsConfig.Friend) item$iv$iv2).getPlayerName());
                    }
                    Iterable $this$filter$iv3 = (List) destination$iv$iv4;
                    Collection destination$iv$iv5 = new ArrayList();
                    for (Object element$iv$iv3 : $this$filter$iv3) {
                        String it2 = (String) element$iv$iv3;
                        Intrinsics.checkNotNullExpressionValue(it2, "it");
                        if (StringsKt.startsWith(it2, args[1], true)) {
                            destination$iv$iv5.add(element$iv$iv3);
                        }
                    }
                    return (List) destination$iv$iv5;
                } else {
                    return CollectionsKt.emptyList();
                }
            default:
                return CollectionsKt.emptyList();
        }
    }
}
