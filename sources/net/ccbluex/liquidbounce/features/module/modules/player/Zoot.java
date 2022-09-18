package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

/* compiled from: Zoot.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Zoot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "badEffectsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fireValue", "noAirValue", "hasBadEffect", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Zoot", description = "Removes all bad potion effects/fire.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Zoot.class */
public final class Zoot extends Module {
    @NotNull
    private final BoolValue badEffectsValue = new BoolValue("BadEffects", true);
    @NotNull
    private final BoolValue fireValue = new BoolValue("Fire", true);
    @NotNull
    private final BoolValue noAirValue = new BoolValue("NoAir", false);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Object obj;
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.noAirValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
            if (this.badEffectsValue.get().booleanValue()) {
                Iterable func_70651_bq = MinecraftInstance.f362mc.field_71439_g.func_70651_bq();
                Intrinsics.checkNotNullExpressionValue(func_70651_bq, "mc.thePlayer.activePotionEffects");
                Iterable $this$maxByOrNull$iv = func_70651_bq;
                Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
                if (!iterator$iv.hasNext()) {
                    obj = null;
                } else {
                    Object maxElem$iv = iterator$iv.next();
                    if (!iterator$iv.hasNext()) {
                        obj = maxElem$iv;
                    } else {
                        PotionEffect it = (PotionEffect) maxElem$iv;
                        int maxValue$iv = it.func_76459_b();
                        do {
                            Object e$iv = iterator$iv.next();
                            PotionEffect it2 = (PotionEffect) e$iv;
                            int v$iv = it2.func_76459_b();
                            if (maxValue$iv < v$iv) {
                                maxElem$iv = e$iv;
                                maxValue$iv = v$iv;
                            }
                        } while (iterator$iv.hasNext());
                        obj = maxElem$iv;
                    }
                }
                PotionEffect effect = (PotionEffect) obj;
                if (effect != null) {
                    int func_76459_b = effect.func_76459_b() / 20;
                    int i = 0;
                    while (i < func_76459_b) {
                        i++;
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                    }
                }
            }
            if (!this.fireValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75098_d || !MinecraftInstance.f362mc.field_71439_g.func_70027_ad()) {
                return;
            }
            int i2 = 0;
            while (i2 < 9) {
                i2++;
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
            }
        }
    }

    private final boolean hasBadEffect() {
        return MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76438_s) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76421_d) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76419_f) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76433_i) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76431_k) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76440_q) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76437_t) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_82731_v) || MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76436_u);
    }
}
