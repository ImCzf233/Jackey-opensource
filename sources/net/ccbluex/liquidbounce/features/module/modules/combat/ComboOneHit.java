package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.jetbrains.annotations.NotNull;

/* compiled from: ComboOneHit.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/ComboOneHit;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "amountValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onlyAuraValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "swingItemValue", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "LiquidBounce"})
@ModuleInfo(name = "ComboOneHit", spacedName = "Combo One Hit", description = "Automatically deals hits within one click. Only works if no attack delay is present.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/ComboOneHit.class */
public final class ComboOneHit extends Module {
    @NotNull
    private final IntegerValue amountValue = new IntegerValue("Packets", 200, 0, 500, "x");
    @NotNull
    private final BoolValue swingItemValue = new BoolValue("SwingPacket", false);
    @NotNull
    private final BoolValue onlyAuraValue = new BoolValue("OnlyAura", false);

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getTargetEntity() == null) {
            return;
        }
        if (this.onlyAuraValue.get().booleanValue()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
            Intrinsics.checkNotNull(module);
            if (!module.getState()) {
                Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(TeleportAura.class);
                Intrinsics.checkNotNull(module2);
                if (!module2.getState()) {
                    return;
                }
            }
        }
        int intValue = this.amountValue.get().intValue();
        int i = 0;
        while (i < intValue) {
            i++;
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity(event.getTargetEntity(), C02PacketUseEntity.Action.ATTACK));
        }
    }
}
