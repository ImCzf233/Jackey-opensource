package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;

/* compiled from: Regen.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Regen;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "foodValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noAirValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potionEffectValue", "resetTimer", "", "speedValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Regen", description = "Regenerates your health much faster.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Regen.class */
public final class Regen extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Spartan"}, "Vanilla");
    @NotNull
    private final IntegerValue healthValue = new IntegerValue("Health", 18, 0, 20);
    @NotNull
    private final IntegerValue foodValue = new IntegerValue("Food", 18, 0, 20);
    @NotNull
    private final IntegerValue speedValue = new IntegerValue("Speed", 100, 1, 100, "x");
    @NotNull
    private final BoolValue noAirValue = new BoolValue("NoAir", false);
    @NotNull
    private final BoolValue potionEffectValue = new BoolValue("PotionEffect", false);
    private boolean resetTimer;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.resetTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
        }
        this.resetTimer = false;
        if ((!this.noAirValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_70122_E) && !MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75098_d && MinecraftInstance.f362mc.field_71439_g.func_71024_bL().func_75116_a() > this.foodValue.get().intValue() && MinecraftInstance.f362mc.field_71439_g.func_70089_S() && MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() < this.healthValue.get().intValue()) {
            if (this.potionEffectValue.get().booleanValue() && !MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76428_l)) {
                return;
            }
            String lowerCase = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase, "vanilla")) {
                int intValue = this.speedValue.get().intValue();
                int i = 0;
                while (i < intValue) {
                    i++;
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                }
            } else if (!Intrinsics.areEqual(lowerCase, "spartan") || MovementUtils.isMoving() || !MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
            } else {
                int i2 = 0;
                while (i2 < 9) {
                    i2++;
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                }
                MinecraftInstance.f362mc.field_71428_T.field_74278_d = 0.45f;
                this.resetTimer = true;
            }
        }
    }
}
