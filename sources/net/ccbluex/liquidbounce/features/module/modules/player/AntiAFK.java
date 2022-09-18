package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiAFK.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001d"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiAFK;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "moveValue", "randomTimerDelay", "", "rotateValue", "rotationAngleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "shouldMove", "", "swingDelayTimer", "swingDelayValue", "swingValue", "getRandomMoveKeyBind", "", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AntiAFK", spacedName = "Anti AFK", description = "Prevents you from getting kicked for being AFK.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AntiAFK.class */
public final class AntiAFK extends Module {
    private boolean shouldMove;
    @NotNull
    private final MSTimer swingDelayTimer = new MSTimer();
    @NotNull
    private final MSTimer delayTimer = new MSTimer();
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Old", "Random", "Custom"}, "Random");
    @NotNull
    private final IntegerValue swingDelayValue = new IntegerValue("SwingDelay", 100, 0, 1000);
    @NotNull
    private final IntegerValue rotationDelayValue = new IntegerValue("RotationDelay", 100, 0, 1000);
    @NotNull
    private final FloatValue rotationAngleValue = new FloatValue("RotationAngle", 1.0f, -180.0f, 180.0f);
    @NotNull
    private final BoolValue jumpValue = new BoolValue("Jump", true);
    @NotNull
    private final BoolValue moveValue = new BoolValue("Move", true);
    @NotNull
    private final BoolValue rotateValue = new BoolValue("Rotate", true);
    @NotNull
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private long randomTimerDelay = 500;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    if (this.moveValue.get().booleanValue()) {
                        MinecraftInstance.f362mc.field_71474_y.field_74351_w.field_74513_e = true;
                    }
                    if (this.jumpValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                        MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                    }
                    if (this.rotateValue.get().booleanValue() && this.delayTimer.hasTimePassed(this.rotationDelayValue.get().intValue())) {
                        MinecraftInstance.f362mc.field_71439_g.field_70177_z += this.rotationAngleValue.get().floatValue();
                        if (MinecraftInstance.f362mc.field_71439_g.field_70125_A <= -90.0f || MinecraftInstance.f362mc.field_71439_g.field_70125_A >= 90.0f) {
                            MinecraftInstance.f362mc.field_71439_g.field_70125_A = 0.0f;
                        }
                        MinecraftInstance.f362mc.field_71439_g.field_70125_A += (RandomUtils.nextFloat(0.0f, 1.0f) * 2) - 1;
                        this.delayTimer.reset();
                    }
                    if (this.swingValue.get().booleanValue() && !MinecraftInstance.f362mc.field_71439_g.field_82175_bq && this.swingDelayTimer.hasTimePassed(this.swingDelayValue.get().intValue())) {
                        MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                        this.swingDelayTimer.reset();
                        return;
                    }
                    return;
                }
                return;
            case -938285885:
                if (lowerCase.equals("random")) {
                    KeyBinding.func_74510_a(getRandomMoveKeyBind(), this.shouldMove);
                    if (!this.delayTimer.hasTimePassed(this.randomTimerDelay)) {
                        return;
                    }
                    this.shouldMove = false;
                    this.randomTimerDelay = 500L;
                    switch (RandomUtils.nextInt(0, 6)) {
                        case 0:
                            if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                            }
                            this.delayTimer.reset();
                            return;
                        case 1:
                            if (!MinecraftInstance.f362mc.field_71439_g.field_82175_bq) {
                                MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                            }
                            this.delayTimer.reset();
                            return;
                        case 2:
                            this.randomTimerDelay = RandomUtils.nextInt(0, 1000);
                            this.shouldMove = true;
                            this.delayTimer.reset();
                            return;
                        case 3:
                            MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c = RandomUtils.nextInt(0, 9);
                            MinecraftInstance.f362mc.field_71442_b.func_78765_e();
                            this.delayTimer.reset();
                            return;
                        case 4:
                            MinecraftInstance.f362mc.field_71439_g.field_70177_z += RandomUtils.nextFloat(-180.0f, 180.0f);
                            this.delayTimer.reset();
                            return;
                        case 5:
                            if (MinecraftInstance.f362mc.field_71439_g.field_70125_A <= -90.0f || MinecraftInstance.f362mc.field_71439_g.field_70125_A >= 90.0f) {
                                MinecraftInstance.f362mc.field_71439_g.field_70125_A = 0.0f;
                            }
                            MinecraftInstance.f362mc.field_71439_g.field_70125_A += RandomUtils.nextFloat(-10.0f, 10.0f);
                            this.delayTimer.reset();
                            return;
                        default:
                            return;
                    }
                }
                return;
            case 110119:
                if (lowerCase.equals("old")) {
                    MinecraftInstance.f362mc.field_71474_y.field_74351_w.field_74513_e = true;
                    if (this.delayTimer.hasTimePassed(500L)) {
                        MinecraftInstance.f362mc.field_71439_g.field_70177_z += 180.0f;
                        this.delayTimer.reset();
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    private final int getRandomMoveKeyBind() {
        switch (RandomUtils.nextInt(0, 4)) {
            case 0:
                return MinecraftInstance.f362mc.field_71474_y.field_74366_z.func_151463_i();
            case 1:
                return MinecraftInstance.f362mc.field_71474_y.field_74370_x.func_151463_i();
            case 2:
                return MinecraftInstance.f362mc.field_71474_y.field_74368_y.func_151463_i();
            case 3:
                return MinecraftInstance.f362mc.field_71474_y.field_74351_w.func_151463_i();
            default:
                return 0;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74351_w)) {
            MinecraftInstance.f362mc.field_71474_y.field_74351_w.field_74513_e = false;
        }
    }
}
