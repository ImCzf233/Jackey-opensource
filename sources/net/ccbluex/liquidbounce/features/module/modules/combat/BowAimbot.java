package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BowAimbot.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "markValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "predictSizeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "predictValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "silentValue", "target", "Lnet/minecraft/entity/Entity;", "throughWallsValue", "getTarget", "throughWalls", "", "priorityMode", "", "hasTarget", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "BowAimbot", spacedName = "Bow Aimbot", description = "Automatically aims at players when using a bow.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot.class */
public final class BowAimbot extends Module {
    @NotNull
    private final BoolValue silentValue = new BoolValue("Silent", true);
    @NotNull
    private final BoolValue predictValue = new BoolValue("Predict", true);
    @NotNull
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    @NotNull
    private final FloatValue predictSizeValue = new FloatValue("PredictSize", 2.0f, 0.1f, 5.0f, "m");
    @NotNull
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction"}, "Direction");
    @NotNull
    private final BoolValue markValue = new BoolValue("Mark", true);
    @Nullable
    private Entity target;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.target = null;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Entity entity;
        Intrinsics.checkNotNullParameter(event, "event");
        this.target = null;
        ItemStack func_71011_bu = MinecraftInstance.f362mc.field_71439_g.func_71011_bu();
        if (!((func_71011_bu == null ? null : func_71011_bu.func_77973_b()) instanceof ItemBow) || (entity = getTarget(this.throughWallsValue.get().booleanValue(), this.priorityValue.get())) == null) {
            return;
        }
        this.target = entity;
        RotationUtils.faceBow(this.target, this.silentValue.get().booleanValue(), this.predictValue.get().booleanValue(), this.predictSizeValue.get().floatValue());
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.target != null && !StringsKt.equals(this.priorityValue.get(), "Multi", true) && this.markValue.get().booleanValue()) {
            RenderUtils.drawPlatform(this.target, new Color(37, 126, 255, 70));
        }
    }

    private final Entity getTarget(boolean throughWalls, String priorityMode) {
        Object obj;
        Object obj2;
        Object obj3;
        Iterable iterable = MinecraftInstance.f362mc.field_71441_e.field_72996_f;
        Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.loadedEntityList");
        Iterable $this$filter$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Entity it = (Entity) element$iv$iv;
            if ((it instanceof EntityLivingBase) && EntityUtils.isSelected(it, true) && (throughWalls || MinecraftInstance.f362mc.field_71439_g.func_70685_l(it))) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable targets = (List) destination$iv$iv;
        String upperCase = priorityMode.toUpperCase();
        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
        switch (upperCase.hashCode()) {
            case 1071086581:
                if (upperCase.equals("DISTANCE")) {
                    Iterable $this$minByOrNull$iv = targets;
                    Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                    if (!iterator$iv.hasNext()) {
                        obj3 = null;
                    } else {
                        Object minElem$iv = iterator$iv.next();
                        if (!iterator$iv.hasNext()) {
                            obj3 = minElem$iv;
                        } else {
                            Entity it2 = (Entity) minElem$iv;
                            float minValue$iv = MinecraftInstance.f362mc.field_71439_g.func_70032_d(it2);
                            do {
                                Object e$iv = iterator$iv.next();
                                Entity it3 = (Entity) e$iv;
                                float v$iv = MinecraftInstance.f362mc.field_71439_g.func_70032_d(it3);
                                if (Float.compare(minValue$iv, v$iv) > 0) {
                                    minElem$iv = e$iv;
                                    minValue$iv = v$iv;
                                }
                            } while (iterator$iv.hasNext());
                            obj3 = minElem$iv;
                        }
                    }
                    return (Entity) obj3;
                }
                break;
            case 1824003935:
                if (upperCase.equals("DIRECTION")) {
                    Iterable $this$minByOrNull$iv2 = targets;
                    Iterator iterator$iv2 = $this$minByOrNull$iv2.iterator();
                    if (!iterator$iv2.hasNext()) {
                        obj2 = null;
                    } else {
                        Object minElem$iv2 = iterator$iv2.next();
                        if (!iterator$iv2.hasNext()) {
                            obj2 = minElem$iv2;
                        } else {
                            Entity it4 = (Entity) minElem$iv2;
                            double minValue$iv2 = RotationUtils.getRotationDifference(it4);
                            do {
                                Object e$iv2 = iterator$iv2.next();
                                Entity it5 = (Entity) e$iv2;
                                double v$iv2 = RotationUtils.getRotationDifference(it5);
                                if (Double.compare(minValue$iv2, v$iv2) > 0) {
                                    minElem$iv2 = e$iv2;
                                    minValue$iv2 = v$iv2;
                                }
                            } while (iterator$iv2.hasNext());
                            obj2 = minElem$iv2;
                        }
                    }
                    return (Entity) obj2;
                }
                break;
            case 2127033948:
                if (upperCase.equals("HEALTH")) {
                    Iterable $this$minByOrNull$iv3 = targets;
                    Iterator iterator$iv3 = $this$minByOrNull$iv3.iterator();
                    if (!iterator$iv3.hasNext()) {
                        obj = null;
                    } else {
                        Object minElem$iv3 = iterator$iv3.next();
                        if (!iterator$iv3.hasNext()) {
                            obj = minElem$iv3;
                        } else {
                            EntityLivingBase entityLivingBase = (Entity) minElem$iv3;
                            if (entityLivingBase == null) {
                                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                            }
                            float minValue$iv3 = entityLivingBase.func_110143_aJ();
                            do {
                                Object e$iv3 = iterator$iv3.next();
                                EntityLivingBase entityLivingBase2 = (Entity) e$iv3;
                                if (entityLivingBase2 == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                                }
                                float v$iv3 = entityLivingBase2.func_110143_aJ();
                                if (Float.compare(minValue$iv3, v$iv3) > 0) {
                                    minElem$iv3 = e$iv3;
                                    minValue$iv3 = v$iv3;
                                }
                            } while (iterator$iv3.hasNext());
                            obj = minElem$iv3;
                        }
                    }
                    return (Entity) obj;
                }
                break;
        }
        return null;
    }

    public final boolean hasTarget() {
        return this.target != null && MinecraftInstance.f362mc.field_71439_g.func_70685_l(this.target);
    }
}
