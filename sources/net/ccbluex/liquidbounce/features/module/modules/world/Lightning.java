package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import org.jetbrains.annotations.NotNull;

/* compiled from: Lightning.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Lightning;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chatValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getChatValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "notifValue", "getNotifValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
@ModuleInfo(name = "Lightning", description = "Checks for lightning spawn and notify you.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Lightning.class */
public final class Lightning extends Module {
    @NotNull
    private final BoolValue chatValue = new BoolValue("Chat", true);
    @NotNull
    private final BoolValue notifValue = new BoolValue("Notification", false);

    @NotNull
    public final BoolValue getChatValue() {
        return this.chatValue;
    }

    @NotNull
    public final BoolValue getNotifValue() {
        return this.notifValue;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        S2CPacketSpawnGlobalEntity packet = event.getPacket();
        if ((packet instanceof S2CPacketSpawnGlobalEntity) && packet.func_149053_g() == 1) {
            double x = packet.func_149051_d() / 32.0d;
            double y = packet.func_149050_e() / 32.0d;
            double z = packet.func_149049_f() / 32.0d;
            int dist = MathKt.roundToInt(MinecraftInstance.f362mc.field_71439_g.func_70011_f(x, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b, z));
            if (this.chatValue.get().booleanValue()) {
                ClientUtils.displayChatMessage("§7[§6§lLightning§7] §fDetected lightning at §a" + x + ' ' + y + ' ' + z + " §7(" + dist + " blocks away)");
            }
            if (this.notifValue.get().booleanValue()) {
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Lightning [" + x + ' ' + y + ' ' + z + "] (" + dist + " blocks away)", Notification.Type.WARNING, 3000L));
            }
        }
    }
}
