package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.slf4j.Marker;

/* compiled from: DamageParticle.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/DamageParticle;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aliveTicks", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blue", "customColor", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "green", "healthData", "Ljava/util/HashMap;", "", "", "particles", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/module/modules/render/SingleParticle;", "red", "sizeValue", "onRender3d", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "DamageParticle", spacedName = "Damage Particle", description = "Allows you to see targets damage.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/DamageParticle.class */
public final class DamageParticle extends Module {
    @NotNull
    private final HashMap<Integer, Float> healthData = new HashMap<>();
    @NotNull
    private final ArrayList<SingleParticle> particles = new ArrayList<>();
    @NotNull
    private final IntegerValue aliveTicks = new IntegerValue("AliveTicks", 20, 10, 50);
    @NotNull
    private final IntegerValue sizeValue = new IntegerValue("Size", 3, 1, 7);
    @NotNull
    private final BoolValue customColor = new BoolValue("CustomColor", false);
    @NotNull
    private final IntegerValue red = new IntegerValue("Red", 255, 0, 255, new DamageParticle$red$1(this));
    @NotNull
    private final IntegerValue green = new IntegerValue("Green", 255, 0, 255, new DamageParticle$green$1(this));
    @NotNull
    private final IntegerValue blue = new IntegerValue("Blue", 255, 0, 255, new DamageParticle$blue$1(this));

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        synchronized (this.particles) {
            for (EntityLivingBase entityLivingBase : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
                if ((entityLivingBase instanceof EntityLivingBase) && EntityUtils.isSelected(entityLivingBase, true)) {
                    Float orDefault = this.healthData.getOrDefault(Integer.valueOf(entityLivingBase.func_145782_y()), Float.valueOf(entityLivingBase.func_110138_aP()));
                    Intrinsics.checkNotNullExpressionValue(orDefault, "healthData.getOrDefault(…tityId, entity.maxHealth)");
                    float lastHealth = orDefault.floatValue();
                    this.healthData.put(Integer.valueOf(entityLivingBase.func_145782_y()), Float.valueOf(entityLivingBase.func_110143_aJ()));
                    if (!(lastHealth == entityLivingBase.func_110143_aJ())) {
                        String prefix = !this.customColor.get().booleanValue() ? lastHealth > entityLivingBase.func_110143_aJ() ? "§c" : "§a" : lastHealth > entityLivingBase.func_110143_aJ() ? "-" : Marker.ANY_NON_NULL_MARKER;
                        this.particles.add(new SingleParticle(Intrinsics.stringPlus(prefix, Double.valueOf(new BigDecimal(Math.abs(lastHealth - entityLivingBase.func_110143_aJ())).setScale(1, 4).doubleValue())), (((Entity) entityLivingBase).field_70165_t - 0.5d) + (new Random(System.currentTimeMillis()).nextInt(5) * 0.1d), entityLivingBase.func_174813_aQ().field_72338_b + ((entityLivingBase.func_174813_aQ().field_72337_e - entityLivingBase.func_174813_aQ().field_72338_b) / 2.0d), (((Entity) entityLivingBase).field_70161_v - 0.5d) + (new Random(System.currentTimeMillis() + 1).nextInt(5) * 0.1d)));
                    }
                }
            }
            ArrayList needRemove = new ArrayList();
            Iterator<SingleParticle> it = this.particles.iterator();
            while (it.hasNext()) {
                SingleParticle particle = it.next();
                particle.setTicks(particle.getTicks() + 1);
                if (particle.getTicks() > this.aliveTicks.get().intValue()) {
                    needRemove.add(particle);
                }
            }
            Iterator it2 = needRemove.iterator();
            while (it2.hasNext()) {
                this.particles.remove((SingleParticle) it2.next());
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @EventTarget
    public final void onRender3d(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        synchronized (this.particles) {
            RenderManager renderManager = MinecraftInstance.f362mc.func_175598_ae();
            double size = this.sizeValue.get().doubleValue() * 0.01d;
            Iterator<SingleParticle> it = this.particles.iterator();
            while (it.hasNext()) {
                SingleParticle particle = it.next();
                double n = particle.getPosX() - renderManager.field_78725_b;
                double n2 = particle.getPosY() - renderManager.field_78726_c;
                double n3 = particle.getPosZ() - renderManager.field_78723_d;
                GlStateManager.func_179094_E();
                GlStateManager.func_179088_q();
                GlStateManager.func_179136_a(1.0f, -1500000.0f);
                GlStateManager.func_179109_b((float) n, (float) n2, (float) n3);
                GlStateManager.func_179114_b(-renderManager.field_78735_i, 0.0f, 1.0f, 0.0f);
                float textY = MinecraftInstance.f362mc.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f;
                GlStateManager.func_179114_b(renderManager.field_78732_j, textY, 0.0f, 0.0f);
                GlStateManager.func_179139_a(-size, -size, size);
                GL11.glDepthMask(false);
                MinecraftInstance.f362mc.field_71466_p.func_175063_a(particle.getStr(), -(MinecraftInstance.f362mc.field_71466_p.func_78256_a(particle.getStr()) / 2), -(MinecraftInstance.f362mc.field_71466_p.field_78288_b - 1), this.customColor.get().booleanValue() ? new Color(this.red.get().intValue(), this.green.get().intValue(), this.blue.get().intValue()).getRGB() : 0);
                GL11.glColor4f(187.0f, 255.0f, 255.0f, 1.0f);
                GL11.glDepthMask(true);
                GlStateManager.func_179136_a(1.0f, 1500000.0f);
                GlStateManager.func_179113_r();
                GlStateManager.func_179121_F();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.particles.clear();
        this.healthData.clear();
    }
}
