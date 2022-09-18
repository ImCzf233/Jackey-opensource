package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoWeb.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0014"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoWeb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "horizonSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "NoWeb", spacedName = "No Web", description = "Prevents you from getting slowed down in webs.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoWeb.class */
public final class NoWeb extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"None", "AAC", "LAAC", "Rewi", "AACv4", "Cardinal", "Horizon", "Spartan", "Negativity"}, "None");
    @NotNull
    private final FloatValue horizonSpeed = new FloatValue("HorizonSpeed", 0.1f, 0.01f, 0.8f);
    private boolean usedTimer;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.usedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
        if (!MinecraftInstance.f362mc.field_71439_g.field_70134_J) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2011701869:
                if (lowerCase.equals("spartan")) {
                    MovementUtils.strafe(0.27f);
                    MinecraftInstance.f362mc.field_71428_T.field_74278_d = 3.7f;
                    if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.7f;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 40 == 0) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 3.0f;
                    }
                    this.usedTimer = true;
                    return;
                }
                return;
            case -7612640:
                if (lowerCase.equals("cardinal")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        MovementUtils.strafe(0.262f);
                        return;
                    } else {
                        MovementUtils.strafe(0.366f);
                        return;
                    }
                }
                return;
            case 96323:
                if (lowerCase.equals("aac")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.59f;
                    if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                        return;
                    }
                    return;
                }
                return;
            case 3313751:
                if (lowerCase.equals("laac")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70747_aH = !((MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78902_a > 0.0f ? 1 : (MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78902_a == 0.0f ? 0 : -1)) == 0) ? 1.0f : 1.21f;
                    if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                        return;
                    }
                    return;
                }
                return;
            case 3387192:
                if (lowerCase.equals("none")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70134_J = false;
                    return;
                }
                return;
            case 3497029:
                if (lowerCase.equals("rewi")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.42f;
                    if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                        return;
                    }
                    return;
                }
                return;
            case 92570113:
                if (lowerCase.equals("aacv4")) {
                    MinecraftInstance.f362mc.field_71474_y.field_74366_z.field_74513_e = false;
                    MinecraftInstance.f362mc.field_71474_y.field_74368_y.field_74513_e = false;
                    MinecraftInstance.f362mc.field_71474_y.field_74370_x.field_74513_e = false;
                    if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        MovementUtils.strafe(0.25f);
                        return;
                    }
                    MovementUtils.strafe(0.12f);
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                    return;
                }
                return;
            case 424996990:
                if (lowerCase.equals("negativity")) {
                    MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.4f;
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0) {
                        MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.53f;
                    }
                    if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                        return;
                    }
                    return;
                }
                return;
            case 1097468315:
                if (lowerCase.equals("horizon") && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    MovementUtils.strafe(this.horizonSpeed.get().floatValue());
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.modeValue.get(), "AACv4", true) || StringsKt.equals(this.modeValue.get(), "Negativity", true) || StringsKt.equals(this.modeValue.get(), "Intave", true)) {
            event.cancelEvent();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }
}
