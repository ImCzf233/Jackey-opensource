package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Fucker.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\bÇ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010-\u001a\u0004\u0018\u00010\u00142\u0006\u0010.\u001a\u00020\u0006H\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0014H\u0002J\b\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u0002032\u0006\u00105\u001a\u000206H\u0007J\u0010\u00107\u001a\u0002032\u0006\u00105\u001a\u000208H\u0007J\u0010\u00109\u001a\u0002032\u0006\u00105\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u0002032\u0006\u00105\u001a\u00020<H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010'\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��¨\u0006="}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Fucker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "actionValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockHitDelay", "", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "coolDownTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "coolDownValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "firstPos", "Lnet/minecraft/util/BlockPos;", "firstPosBed", "ignoreFirstBlockValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "instantValue", "lastWorld", "Lnet/minecraft/client/multiplayer/WorldClient;", "noHitValue", "oldPos", "pos", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "renderValue", "resetOnWorldValue", "rotationsValue", "surroundingsValue", "swingValue", "switchTimer", "switchValue", "tag", "", "getTag", "()Ljava/lang/String;", "throughWallsValue", "toggleResetCDValue", "find", "targetID", "isHitable", "", "blockPos", "onEnable", "", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "Fucker", description = "Destroys selected blocks around you. (aka. IDNuker)", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Fucker.class */
public final class Fucker extends Module {
    @Nullable
    private static BlockPos firstPos;
    @Nullable
    private static BlockPos firstPosBed;
    @Nullable
    private static BlockPos pos;
    @Nullable
    private static BlockPos oldPos;
    private static int blockHitDelay;
    private static float currentDamage;
    @Nullable
    private static WorldClient lastWorld;
    @NotNull
    public static final Fucker INSTANCE = new Fucker();
    @NotNull
    private static final BlockValue blockValue = new BlockValue("Block", 26);
    @NotNull
    private static final BoolValue ignoreFirstBlockValue = new BoolValue("IgnoreFirstDetection", false);
    @NotNull
    private static final BoolValue resetOnWorldValue = new BoolValue("ResetOnWorldChange", false, Fucker$resetOnWorldValue$1.INSTANCE);
    @NotNull
    private static final ListValue renderValue = new ListValue("Render-Mode", new String[]{"Box", "Outline", "2D", "None"}, "Box");
    @NotNull
    private static final ListValue throughWallsValue = new ListValue("ThroughWalls", new String[]{"None", "Raycast", "Around"}, "None");
    @NotNull
    private static final FloatValue rangeValue = new FloatValue("Range", 5.0f, 1.0f, 7.0f, "m");
    @NotNull
    private static final ListValue actionValue = new ListValue("Action", new String[]{"Destroy", "Use"}, "Destroy");
    @NotNull
    private static final BoolValue instantValue = new BoolValue("Instant", false);
    @NotNull
    private static final IntegerValue switchValue = new IntegerValue("SwitchDelay", (int) LinkerCallSite.ARGLIMIT, 0, 1000, "ms");
    @NotNull
    private static final IntegerValue coolDownValue = new IntegerValue("Cooldown-Seconds", 15, 0, 60);
    @NotNull
    private static final BoolValue swingValue = new BoolValue("Swing", true);
    @NotNull
    private static final BoolValue rotationsValue = new BoolValue("Rotations", true);
    @NotNull
    private static final BoolValue surroundingsValue = new BoolValue("Surroundings", true);
    @NotNull
    private static final BoolValue noHitValue = new BoolValue("NoAura", false);
    @NotNull
    private static final BoolValue toggleResetCDValue = new BoolValue("ResetCoolDownWhenToggled", false);
    @NotNull
    private static final MSTimer switchTimer = new MSTimer();
    @NotNull
    private static final MSTimer coolDownTimer = new MSTimer();

    private Fucker() {
    }

    public final float getCurrentDamage() {
        return currentDamage;
    }

    public final void setCurrentDamage(float f) {
        currentDamage = f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (toggleResetCDValue.get().booleanValue()) {
            coolDownTimer.reset();
        }
        firstPos = null;
        firstPosBed = null;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!Intrinsics.areEqual(event.getWorldClient(), lastWorld) && resetOnWorldValue.get().booleanValue()) {
            firstPos = null;
            firstPosBed = null;
        }
        lastWorld = event.getWorldClient();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x007a, code lost:
        if (net.ccbluex.liquidbounce.utils.block.BlockUtils.getCenterDistance(r0) > net.ccbluex.liquidbounce.features.module.modules.world.Fucker.rangeValue.get().floatValue()) goto L19;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpdate(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.UpdateEvent r16) {
        /*
            Method dump skipped, instructions count: 1088
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.world.Fucker.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void");
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String lowerCase = renderValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1106245566:
                if (lowerCase.equals("outline")) {
                    BlockPos blockPos = pos;
                    if (blockPos == null) {
                        return;
                    }
                    RenderUtils.drawBlockBox(blockPos, !coolDownTimer.hasTimePassed(((long) coolDownValue.get().intValue()) * 1000) ? Color.DARK_GRAY : Color.RED, true);
                    return;
                }
                return;
            case 1650:
                if (lowerCase.equals("2d")) {
                    BlockPos blockPos2 = pos;
                    if (blockPos2 == null) {
                        return;
                    }
                    RenderUtils.draw2D(blockPos2, !coolDownTimer.hasTimePassed(((long) coolDownValue.get().intValue()) * 1000) ? Color.DARK_GRAY.getRGB() : Color.RED.getRGB(), Color.BLACK.getRGB());
                    return;
                }
                return;
            case 97739:
                if (lowerCase.equals("box")) {
                    BlockPos blockPos3 = pos;
                    if (blockPos3 == null) {
                        return;
                    }
                    RenderUtils.drawBlockBox(blockPos3, !coolDownTimer.hasTimePassed(((long) coolDownValue.get().intValue()) * 1000) ? Color.DARK_GRAY : Color.RED, false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ScaledResolution sc = new ScaledResolution(MinecraftInstance.f362mc);
        if (coolDownValue.get().intValue() > 0 && !coolDownTimer.hasTimePassed(coolDownValue.get().intValue() * 1000)) {
            String timeLeft = "Cooldown: " + ((int) (coolDownTimer.hasTimeLeft(coolDownValue.get().intValue() * 1000) / 1000)) + 's';
            int strWidth = Fonts.minecraftFont.func_78256_a(timeLeft);
            Fonts.minecraftFont.func_78276_b(timeLeft, ((sc.func_78326_a() / 2) - (strWidth / 2)) - 1, (sc.func_78328_b() / 2) - 70, 0);
            Fonts.minecraftFont.func_78276_b(timeLeft, ((sc.func_78326_a() / 2) - (strWidth / 2)) + 1, (sc.func_78328_b() / 2) - 70, 0);
            Fonts.minecraftFont.func_78276_b(timeLeft, (sc.func_78326_a() / 2) - (strWidth / 2), (sc.func_78328_b() / 2) - 69, 0);
            Fonts.minecraftFont.func_78276_b(timeLeft, (sc.func_78326_a() / 2) - (strWidth / 2), (sc.func_78328_b() / 2) - 71, 0);
            Fonts.minecraftFont.func_78276_b(timeLeft, (sc.func_78326_a() / 2) - (strWidth / 2), (sc.func_78328_b() / 2) - 70, -1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0295  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final net.minecraft.util.BlockPos find(int r8) {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.world.Fucker.find(int):net.minecraft.util.BlockPos");
    }

    private final boolean isHitable(BlockPos blockPos) {
        String lowerCase = throughWallsValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (!Intrinsics.areEqual(lowerCase, "raycast")) {
            return !Intrinsics.areEqual(lowerCase, "around") || !BlockUtils.isFullBlock(blockPos.func_177977_b()) || !BlockUtils.isFullBlock(blockPos.func_177984_a()) || !BlockUtils.isFullBlock(blockPos.func_177978_c()) || !BlockUtils.isFullBlock(blockPos.func_177974_f()) || !BlockUtils.isFullBlock(blockPos.func_177968_d()) || !BlockUtils.isFullBlock(blockPos.func_177976_e());
        }
        Vec3 eyesPos = new Vec3(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b + MinecraftInstance.f362mc.field_71439_g.func_70047_e(), MinecraftInstance.f362mc.field_71439_g.field_70161_v);
        MovingObjectPosition movingObjectPosition = MinecraftInstance.f362mc.field_71441_e.func_147447_a(eyesPos, new Vec3(blockPos.func_177958_n() + 0.5d, blockPos.func_177956_o() + 0.5d, blockPos.func_177952_p() + 0.5d), false, true, false);
        return movingObjectPosition != null && Intrinsics.areEqual(movingObjectPosition.func_178782_a(), blockPos);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return BlockUtils.getBlockName(blockValue.get().intValue());
    }
}
