package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EntityDamageEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

/* compiled from: SuperheroFX.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0010"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/SuperheroFX;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "generateTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "textParticles", "", "Lnet/ccbluex/liquidbounce/features/module/modules/render/FXParticle;", "onEntityDamage", "", "event", "Lnet/ccbluex/liquidbounce/event/EntityDamageEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "SuperheroFX", spacedName = "Superhero FX", description = "Creates comic-like words as flying particles.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/SuperheroFX.class */
public final class SuperheroFX extends Module {
    @NotNull
    private final List<FXParticle> textParticles = new ArrayList();
    @NotNull
    private final MSTimer generateTimer = new MSTimer();

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.textParticles.clear();
    }

    @EventTarget
    public final void onEntityDamage(@NotNull EntityDamageEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Entity entity = event.getDamagedEntity();
        if (MinecraftInstance.f362mc.field_71441_e.field_72996_f.contains(entity) && this.generateTimer.hasTimePassed(500L)) {
            double dirX = RandomUtils.nextDouble(-0.5d, 0.5d);
            double dirZ = RandomUtils.nextDouble(-0.5d, 0.5d);
            this.generateTimer.reset();
            this.textParticles.add(new FXParticle(entity.field_70165_t + dirX, entity.func_174813_aQ().field_72338_b + ((entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b) / 2.0d), entity.field_70161_v + dirZ, dirX, dirZ));
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        List removeList = new ArrayList();
        for (FXParticle particle : this.textParticles) {
            if (particle.getCanRemove()) {
                removeList.add(particle);
            } else {
                particle.draw();
            }
        }
        this.textParticles.removeAll(removeList);
    }
}
