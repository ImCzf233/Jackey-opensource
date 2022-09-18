package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SuperKnockback.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hurtTimeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "LiquidBounce"})
@ModuleInfo(name = "SuperKnockback", spacedName = "Super Knockback", description = "Increases knockback dealt to other entities.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback.class */
public final class SuperKnockback extends Module {
    @NotNull
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"ExtraPacket", "WTap", "Packet"}, "ExtraPacket");
    @NotNull
    private final IntegerValue delay = new IntegerValue("Delay", 0, 0, 500, "ms");
    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!(event.getTargetEntity() instanceof EntityLivingBase) || event.getTargetEntity().field_70737_aN > this.hurtTimeValue.get().intValue() || !this.timer.hasTimePassed(this.delay.get().intValue())) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2117036904:
                if (lowerCase.equals("extrapacket")) {
                    if (MinecraftInstance.f362mc.field_71439_g.func_70051_ag()) {
                        MinecraftInstance.f362mc.field_71439_g.func_70031_b(true);
                    }
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    MinecraftInstance.f362mc.field_71439_g.field_175171_bO = true;
                    break;
                }
                break;
            case -995865464:
                if (lowerCase.equals("packet")) {
                    if (MinecraftInstance.f362mc.field_71439_g.func_70051_ag()) {
                        MinecraftInstance.f362mc.field_71439_g.func_70031_b(true);
                    }
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    MinecraftInstance.f362mc.field_71439_g.field_175171_bO = true;
                    break;
                }
                break;
            case 3659724:
                if (lowerCase.equals("wtap")) {
                    if (MinecraftInstance.f362mc.field_71439_g.func_70051_ag()) {
                        MinecraftInstance.f362mc.field_71439_g.func_70031_b(false);
                    }
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(MinecraftInstance.f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    MinecraftInstance.f362mc.field_71439_g.field_175171_bO = true;
                    break;
                }
                break;
        }
        this.timer.reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }
}
