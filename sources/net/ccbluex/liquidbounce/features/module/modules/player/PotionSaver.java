package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: PotionSaver.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/PotionSaver;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "e", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
@ModuleInfo(name = "PotionSaver", spacedName = "Potion Saver", description = "Freezes all potion effects while you are standing still.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/PotionSaver.class */
public final class PotionSaver extends Module {
    @EventTarget
    public final void onPacket(@NotNull PacketEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Packet packet = e.getPacket();
        if ((packet instanceof C03PacketPlayer) && !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) && !(packet instanceof C03PacketPlayer.C05PacketPlayerLook) && MinecraftInstance.f362mc.field_71439_g != null && !MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
            e.cancelEvent();
        }
    }
}
