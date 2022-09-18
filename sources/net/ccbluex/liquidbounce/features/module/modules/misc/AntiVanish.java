package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiVanish.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\b\u0010\u000b\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiVanish;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "lastNotify", "", "notifyLast", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "vanish", "LiquidBounce"})
@ModuleInfo(name = "AntiVanish", spacedName = "Anti Vanish", description = "Anti player vanish", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiVanish.class */
public final class AntiVanish extends Module {
    private long lastNotify = -1;
    @NotNull
    private final IntegerValue notifyLast = new IntegerValue("Notification-Seconds", 2, 1, 30);

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71441_e == null || MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        if (event.getPacket() instanceof S1DPacketEntityEffect) {
            if (MinecraftInstance.f362mc.field_71441_e.func_73045_a(event.getPacket().func_149426_d()) == null) {
                vanish();
            }
        } else if ((event.getPacket() instanceof S14PacketEntity) && event.getPacket().func_149065_a(MinecraftInstance.f362mc.field_71441_e) == null) {
            vanish();
        }
    }

    private final void vanish() {
        if (System.currentTimeMillis() - this.lastNotify > 5000) {
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Found a vanished entity!", Notification.Type.WARNING, this.notifyLast.get().intValue() * 1000));
        }
        this.lastNotify = System.currentTimeMillis();
    }
}
