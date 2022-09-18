package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.special.UUIDSpoofer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: UUIDCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/UUIDCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/UUIDCommand.class */
public final class UUIDCommand extends Command {
    public UUIDCommand() {
        super("uuid", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 2) {
            String theName = args[1];
            if (StringsKt.equals(theName, "reset", true)) {
                UUIDSpoofer.INSTANCE.setSpoofId(null);
                chat("§aSuccessfully resetted your UUID.");
                return;
            }
            Iterable iterable = MinecraftInstance.f362mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.playerEntities");
            Iterable $this$filter$iv = iterable;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                EntityLivingBase entityLivingBase = (EntityPlayer) element$iv$iv;
                if (!AntiBot.isBot(entityLivingBase) && StringsKt.equals(entityLivingBase.func_70005_c_(), theName, true)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            EntityPlayer targetPlayer = (EntityPlayer) CollectionsKt.firstOrNull((List<? extends Object>) destination$iv$iv);
            if (targetPlayer != null) {
                UUIDSpoofer.INSTANCE.setSpoofId(targetPlayer.func_146103_bH().getId().toString());
            } else {
                UUIDSpoofer.INSTANCE.setSpoofId(theName);
            }
            StringBuilder append = new StringBuilder().append("§aSuccessfully changed your UUID to §6");
            String spoofId = UUIDSpoofer.INSTANCE.getSpoofId();
            Intrinsics.checkNotNull(spoofId);
            chat(append.append(spoofId).append("§a. Make sure to turn on BungeeCordSpoof in server selection.").toString());
            return;
        }
        if (args.length == 1) {
            chat("§6Session's UUID is §7" + ((Object) MinecraftInstance.f362mc.field_71449_j.func_148255_b()) + "§6.");
            chat("§6Player's UUID is §7" + MinecraftInstance.f362mc.field_71439_g.func_110124_au() + "§6.");
        }
        chatSyntax("uuid <player's name in current world/uuid/reset>");
    }
}
