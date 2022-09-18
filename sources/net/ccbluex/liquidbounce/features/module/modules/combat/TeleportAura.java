package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: TeleportAura.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020*H\u0016J\b\u0010,\u001a\u00020*H\u0016J\u0010\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020*2\u0006\u0010.\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020*2\u0006\u0010.\u001a\u000203H\u0007J\b\u00104\u001a\u00020*H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n��R\u001e\u0010%\u001a\u0012\u0012\u0004\u0012\u00020'0&j\b\u0012\u0004\u0012\u00020'`(X\u0082\u000e¢\u0006\u0002\n��¨\u00065"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/TeleportAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "apsValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "attackDelay", "", "getAttackDelay", "()J", "auraMod", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "fovValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "lastTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getLastTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setLastTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "maxMoveDistValue", "maxTargetsValue", "noKillAuraValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noPureC03Value", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rangeValue", "renderValue", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "thread", "Ljava/lang/Thread;", "tpVectors", "Ljava/util/ArrayList;", "Lnet/minecraft/util/Vec3;", "Lkotlin/collections/ArrayList;", "onDisable", "", "onEnable", "onInitialize", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "LiquidBounce"})
@ModuleInfo(name = "TeleportAura", spacedName = "Teleport Aura", description = "Automatically attacks targets around you. (by tp to them and back)", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/TeleportAura.class */
public final class TeleportAura extends Module {
    @NotNull
    private final IntegerValue apsValue = new IntegerValue("APS", 1, 1, 10);
    @NotNull
    private final IntegerValue maxTargetsValue = new IntegerValue("MaxTargets", 2, 1, 8);
    @NotNull
    private final IntegerValue rangeValue = new IntegerValue("Range", 80, 10, 200, "m");
    @NotNull
    private final FloatValue fovValue = new FloatValue("FOV", 180.0f, 0.0f, 180.0f, "°");
    @NotNull
    private final FloatValue maxMoveDistValue = new FloatValue("MaxMoveSpeed", 8.0f, 2.0f, 15.0f, "m");
    @NotNull
    private final ListValue swingValue = new ListValue("Swing", new String[]{"Normal", "Packet", "None"}, "Normal");
    @NotNull
    private final BoolValue noPureC03Value = new BoolValue("NoStandingPackets", true);
    @NotNull
    private final BoolValue noKillAuraValue = new BoolValue("NoKillAura", true);
    @NotNull
    private final ListValue renderValue = new ListValue("Render", new String[]{"Box", "Lines", "None"}, "Box");
    @NotNull
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "LivingTime"}, "Distance");
    @NotNull
    private final MSTimer clickTimer = new MSTimer();
    @NotNull
    private ArrayList<Vec3> tpVectors = new ArrayList<>();
    @Nullable
    private Thread thread;
    @Nullable
    private EntityLivingBase lastTarget;
    private KillAura auraMod;

    @Nullable
    public final EntityLivingBase getLastTarget() {
        return this.lastTarget;
    }

    public final void setLastTarget(@Nullable EntityLivingBase entityLivingBase) {
        this.lastTarget = entityLivingBase;
    }

    private final long getAttackDelay() {
        return 1000 / this.apsValue.get().intValue();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return "APS " + this.apsValue.get().intValue() + ", Range " + this.rangeValue.get().intValue();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.clickTimer.reset();
        this.tpVectors.clear();
        this.lastTarget = null;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.clickTimer.reset();
        this.tpVectors.clear();
        this.lastTarget = null;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onInitialize() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        Intrinsics.checkNotNull(module);
        this.auraMod = (KillAura) module;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.noKillAuraValue.get().booleanValue()) {
            KillAura killAura = this.auraMod;
            if (killAura == null) {
                Intrinsics.throwUninitializedPropertyAccessException("auraMod");
                killAura = null;
            }
            if (killAura.getTarget() != null) {
                return;
            }
        }
        if (!this.clickTimer.hasTimePassed(getAttackDelay())) {
            return;
        }
        if (this.thread != null) {
            Thread thread = this.thread;
            Intrinsics.checkNotNull(thread);
            if (thread.isAlive()) {
                this.clickTimer.reset();
                return;
            }
        }
        this.tpVectors.clear();
        this.clickTimer.reset();
        this.thread = new Thread(() -> {
            m2770onUpdate$lambda0(r3);
        });
        Thread thread2 = this.thread;
        Intrinsics.checkNotNull(thread2);
        thread2.start();
    }

    /* renamed from: onUpdate$lambda-0 */
    private static final void m2770onUpdate$lambda0(TeleportAura this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.runAttack();
    }

    private final void runAttack() {
        if (this.noKillAuraValue.get().booleanValue()) {
            KillAura killAura = this.auraMod;
            if (killAura == null) {
                Intrinsics.throwUninitializedPropertyAccessException("auraMod");
                killAura = null;
            }
            if (killAura.getTarget() != null) {
                return;
            }
        }
        if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71441_e == null) {
            return;
        }
        ArrayList targets = new ArrayList();
        int entityCount = 0;
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if ((entity instanceof EntityLivingBase) && EntityUtils.isSelected(entity, true) && MinecraftInstance.f362mc.field_71439_g.func_70032_d(entity) <= this.rangeValue.get().intValue() && (this.fovValue.get().floatValue() >= 180.0f || RotationUtils.getRotationDifference(entity) <= this.fovValue.get().floatValue())) {
                if (entityCount >= this.maxTargetsValue.get().intValue()) {
                    break;
                }
                targets.add(entity);
                entityCount++;
            }
        }
        if (targets.isEmpty()) {
            this.lastTarget = null;
            return;
        }
        String lowerCase = this.priorityValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1221262756:
                if (lowerCase.equals("health")) {
                    ArrayList $this$sortBy$iv = targets;
                    if ($this$sortBy$iv.size() > 1) {
                        CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura$runAttack$$inlined$sortBy$2
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                EntityLivingBase it = (EntityLivingBase) t;
                                EntityLivingBase it2 = (EntityLivingBase) t2;
                                return ComparisonsKt.compareValues(Float.valueOf(it.func_110143_aJ()), Float.valueOf(it2.func_110143_aJ()));
                            }
                        });
                        break;
                    }
                }
                break;
            case 288459765:
                if (lowerCase.equals("distance")) {
                    ArrayList $this$sortBy$iv2 = targets;
                    if ($this$sortBy$iv2.size() > 1) {
                        CollectionsKt.sortWith($this$sortBy$iv2, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura$runAttack$$inlined$sortBy$1
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                Minecraft minecraft;
                                Minecraft minecraft2;
                                minecraft = MinecraftInstance.f362mc;
                                Float valueOf = Float.valueOf(minecraft.field_71439_g.func_70032_d((EntityLivingBase) t));
                                minecraft2 = MinecraftInstance.f362mc;
                                return ComparisonsKt.compareValues(valueOf, Float.valueOf(minecraft2.field_71439_g.func_70032_d((EntityLivingBase) t2)));
                            }
                        });
                        break;
                    }
                }
                break;
            case 886905078:
                if (lowerCase.equals("livingtime")) {
                    ArrayList $this$sortBy$iv3 = targets;
                    if ($this$sortBy$iv3.size() > 1) {
                        CollectionsKt.sortWith($this$sortBy$iv3, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura$runAttack$$inlined$sortBy$3
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                EntityLivingBase it = (EntityLivingBase) t;
                                EntityLivingBase it2 = (EntityLivingBase) t2;
                                return ComparisonsKt.compareValues(Integer.valueOf(-it.field_70173_aa), Integer.valueOf(-it2.field_70173_aa));
                            }
                        });
                        break;
                    }
                }
                break;
        }
        ArrayList $this$forEach$iv = targets;
        for (Object element$iv : $this$forEach$iv) {
            Entity entity2 = (EntityLivingBase) element$iv;
            if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71441_e == null) {
                return;
            }
            Iterable path = PathUtils.findTeleportPath(MinecraftInstance.f362mc.field_71439_g, entity2, this.maxMoveDistValue.get().floatValue());
            if (this.noKillAuraValue.get().booleanValue()) {
                KillAura killAura2 = this.auraMod;
                if (killAura2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("auraMod");
                    killAura2 = null;
                }
                if (killAura2.getTarget() != null) {
                    return;
                }
            }
            Intrinsics.checkNotNullExpressionValue(path, "path");
            Iterable $this$forEach$iv2 = path;
            for (Object element$iv2 : $this$forEach$iv2) {
                Vec3 point = (Vec3) element$iv2;
                this.tpVectors.add(point);
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(point.field_72450_a, point.field_72448_b, point.field_72449_c, true));
            }
            setLastTarget(entity2);
            String lowerCase2 = this.swingValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase2, "normal")) {
                MinecraftInstance.f362mc.field_71439_g.func_71038_i();
            } else if (Intrinsics.areEqual(lowerCase2, "packet")) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity(entity2, C02PacketUseEntity.Action.ATTACK));
            Iterable $this$forEach$iv3 = CollectionsKt.reversed(path);
            for (Object element$iv3 : $this$forEach$iv3) {
                Vec3 point2 = (Vec3) element$iv3;
                if (StringsKt.equals(this.renderValue.get(), "lines", true)) {
                    this.tpVectors.add(point2);
                }
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(point2.field_72450_a, point2.field_72448_b, point2.field_72449_c, true));
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.clickTimer.reset();
        }
        if (this.noPureC03Value.get().booleanValue() && (packet instanceof C03PacketPlayer) && !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        synchronized (this.tpVectors) {
            if (StringsKt.equals(this.renderValue.get(), "none", true) || this.tpVectors.isEmpty()) {
                return;
            }
            double renderPosX = MinecraftInstance.f362mc.func_175598_ae().field_78730_l;
            double renderPosY = MinecraftInstance.f362mc.func_175598_ae().field_78731_m;
            double renderPosZ = MinecraftInstance.f362mc.func_175598_ae().field_78728_n;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glHint(3154, 4354);
            GL11.glLoadIdentity();
            MinecraftInstance.f362mc.field_71460_t.func_78479_a(MinecraftInstance.f362mc.field_71428_T.field_74281_c, 2);
            RenderUtils.glColor(Color.WHITE);
            GL11.glLineWidth(1.0f);
            if (StringsKt.equals(this.renderValue.get(), "lines", true)) {
                GL11.glBegin(3);
            }
            try {
                Iterator<Vec3> it = this.tpVectors.iterator();
                while (it.hasNext()) {
                    Vec3 vec = it.next();
                    double x = vec.field_72450_a - renderPosX;
                    double y = vec.field_72448_b - renderPosY;
                    double z = vec.field_72449_c - renderPosZ;
                    double height = MinecraftInstance.f362mc.field_71439_g.func_70047_e();
                    String lowerCase = this.renderValue.get().toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                    if (Intrinsics.areEqual(lowerCase, "box")) {
                        GL11.glBegin(3);
                        GL11.glVertex3d(x - 0.3d, y, z - 0.3d);
                        GL11.glVertex3d(x - 0.3d, y, z - 0.3d);
                        GL11.glVertex3d(x - 0.3d, y + height, z - 0.3d);
                        GL11.glVertex3d(x + 0.3d, y + height, z - 0.3d);
                        GL11.glVertex3d(x + 0.3d, y, z - 0.3d);
                        GL11.glVertex3d(x - 0.3d, y, z - 0.3d);
                        GL11.glVertex3d(x - 0.3d, y, z + 0.3d);
                        GL11.glEnd();
                        GL11.glBegin(3);
                        GL11.glVertex3d(x + 0.3d, y, z + 0.3d);
                        GL11.glVertex3d(x + 0.3d, y + height, z + 0.3d);
                        GL11.glVertex3d(x - 0.3d, y + height, z + 0.3d);
                        GL11.glVertex3d(x - 0.3d, y, z + 0.3d);
                        GL11.glVertex3d(x + 0.3d, y, z + 0.3d);
                        GL11.glVertex3d(x + 0.3d, y, z - 0.3d);
                        GL11.glEnd();
                        GL11.glBegin(3);
                        GL11.glVertex3d(x + 0.3d, y + height, z + 0.3d);
                        GL11.glVertex3d(x + 0.3d, y + height, z - 0.3d);
                        GL11.glEnd();
                        GL11.glBegin(3);
                        GL11.glVertex3d(x - 0.3d, y + height, z + 0.3d);
                        GL11.glVertex3d(x - 0.3d, y + height, z - 0.3d);
                        GL11.glEnd();
                    } else if (Intrinsics.areEqual(lowerCase, "lines")) {
                        GL11.glVertex3d(x, y, z);
                    }
                }
            } catch (Exception e) {
            }
            if (StringsKt.equals(this.renderValue.get(), "lines", true)) {
                GL11.glEnd();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Unit unit = Unit.INSTANCE;
        }
    }
}
