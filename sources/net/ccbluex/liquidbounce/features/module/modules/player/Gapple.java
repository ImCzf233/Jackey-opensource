package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Gapple.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Gapple;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "noAbsorption", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "doEat", "", "warn", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Gapple", description = "Eat Gapples.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Gapple.class */
public final class Gapple extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Auto", "Once", "Head"}, "Once");
    @NotNull
    private final FloatValue healthValue = new FloatValue("Health", 10.0f, 1.0f, 20.0f);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 1000, "ms");
    @NotNull
    private final BoolValue noAbsorption = new BoolValue("NoAbsorption", true);
    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        int headInHotbar;
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 3005871:
                if (lowerCase.equals("auto") && this.timer.hasTimePassed(this.delayValue.get().intValue()) && MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() <= this.healthValue.get().floatValue()) {
                    doEat(false);
                    this.timer.reset();
                    return;
                }
                return;
            case 3198432:
                if (lowerCase.equals("head") && this.timer.hasTimePassed(this.delayValue.get().intValue()) && MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() <= this.healthValue.get().floatValue() && (headInHotbar = InventoryUtils.findItem(36, 45, Items.field_151144_bL)) != -1) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(headInHotbar - 36));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.func_70694_bm()));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                    this.timer.reset();
                    return;
                }
                return;
            case 3415681:
                if (lowerCase.equals("once")) {
                    doEat(true);
                    setState(false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private final void doEat(boolean warn) {
        if (this.noAbsorption.get().booleanValue() && !warn) {
            float abAmount = MinecraftInstance.f362mc.field_71439_g.func_110139_bj();
            if (abAmount > 0.0f) {
                return;
            }
        }
        int gappleInHotbar = InventoryUtils.findItem(36, 45, Items.field_151153_ao);
        if (gappleInHotbar != -1) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(gappleInHotbar - 36));
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.func_70694_bm()));
            int i = 0;
            while (i < 35) {
                i++;
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
        } else if (warn) {
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("No Gapple were found in hotbar", Notification.Type.ERROR));
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }
}
