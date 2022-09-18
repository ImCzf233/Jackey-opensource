package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

/* compiled from: FastClimb.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0019"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastClimb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "downSpeedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "spartanTimerBoostValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timerValue", "upSpeedValue", "usedTimer", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "LiquidBounce"})
@ModuleInfo(name = "FastClimb", spacedName = "Fast Climb", description = "Allows you to climb up ladders and vines faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/FastClimb.class */
public final class FastClimb extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Clip", "AAC3.0.0", "AAC3.0.5", "SAAC3.1.2", "AAC3.1.2", "Spartan", "Negativity", "Horizon1.4.6", "HiveMC"}, "Vanilla");
    @NotNull
    private final FloatValue upSpeedValue = new FloatValue("UpSpeed", 0.2872f, 0.01f, 10.0f);
    @NotNull
    private final FloatValue downSpeedValue = new FloatValue("DownSpeed", 0.2872f, 0.01f, 10.0f);
    @NotNull
    private final FloatValue timerValue = new FloatValue("Timer", 2.0f, 0.1f, 10.0f, "x");
    @NotNull
    private final BoolValue spartanTimerBoostValue = new BoolValue("SpartanTimerBoost", false);
    private boolean usedTimer;

    /* compiled from: FastClimb.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/FastClimb$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EnumFacing.values().length];
            iArr[EnumFacing.NORTH.ordinal()] = 1;
            iArr[EnumFacing.EAST.ordinal()] = 2;
            iArr[EnumFacing.SOUTH.ordinal()] = 3;
            iArr[EnumFacing.WEST.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        int i;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.usedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
        String mode = this.modeValue.get();
        if (StringsKt.equals(mode, "Vanilla", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                event.setY(this.upSpeedValue.get().floatValue());
            } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                event.setY(-this.downSpeedValue.get().floatValue());
            }
            MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = this.timerValue.get().floatValue();
            this.usedTimer = true;
        } else if (StringsKt.equals(mode, "AAC3.0.0", true) && MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
            double x = 0.0d;
            double z = 0.0d;
            EnumFacing func_174811_aO = MinecraftInstance.f362mc.field_71439_g.func_174811_aO();
            switch (func_174811_aO == null ? -1 : WhenMappings.$EnumSwitchMapping$0[func_174811_aO.ordinal()]) {
                case 1:
                    z = -0.99d;
                    break;
                case 2:
                    x = 0.99d;
                    break;
                case 3:
                    z = 0.99d;
                    break;
                case 4:
                    x = -0.99d;
                    break;
            }
            Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t + x, MinecraftInstance.f362mc.field_71439_g.field_70163_u, MinecraftInstance.f362mc.field_71439_g.field_70161_v + z));
            if ((block instanceof BlockLadder) || (block instanceof BlockVine)) {
                event.setY(0.5d);
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            }
        } else {
            if (StringsKt.equals(mode, "AAC3.0.5", true) && MinecraftInstance.f362mc.field_71474_y.field_74351_w.func_151470_d()) {
                AxisAlignedBB func_174813_aQ = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ();
                Intrinsics.checkNotNullExpressionValue(func_174813_aQ, "mc.thePlayer.entityBoundingBox");
                if (BlockUtils.collideBlockIntersects(func_174813_aQ, FastClimb$onMove$1.INSTANCE)) {
                    event.setX(0.0d);
                    event.setY(0.5d);
                    event.setZ(0.0d);
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                    return;
                }
            }
            if (StringsKt.equals(mode, "SAAC3.1.2", true) && MinecraftInstance.f362mc.field_71439_g.field_70123_F && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                event.setY(0.1649d);
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "AAC3.1.2", true) && MinecraftInstance.f362mc.field_71439_g.field_70123_F && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                event.setY(0.1699d);
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "Spartan", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                    event.setY(0.199d);
                } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                    event.setY(-1.3489d);
                }
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                if (this.spartanTimerBoostValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 2.5f;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 30 == 0) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 3.0f;
                    }
                    this.usedTimer = true;
                }
            } else if (StringsKt.equals(mode, "Negativity", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                    event.setY(0.2299d);
                } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                    event.setY(-0.226d);
                }
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "Twillight", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                    event.setY(0.16d);
                } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                    event.setY(-7.99d);
                }
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "Horizon1.4.6", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                    event.setY(0.125d);
                } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                    event.setY(-0.16d);
                }
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "HiveMC", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70123_F) {
                    event.setY(0.179d);
                } else if (!MinecraftInstance.f362mc.field_71474_y.field_74311_E.field_74513_e) {
                    event.setY(-0.225d);
                }
                MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            } else if (StringsKt.equals(mode, "Clip", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && MinecraftInstance.f362mc.field_71474_y.field_74351_w.func_151470_d()) {
                int i2 = (int) MinecraftInstance.f362mc.field_71439_g.field_70163_u;
                int i3 = ((int) MinecraftInstance.f362mc.field_71439_g.field_70163_u) + 8;
                if (i2 <= i3) {
                    do {
                        i = i2;
                        i2++;
                        if (!(BlockUtils.getBlock(new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, i, MinecraftInstance.f362mc.field_71439_g.field_70161_v)) instanceof BlockLadder)) {
                            double x2 = 0.0d;
                            double z2 = 0.0d;
                            EnumFacing func_174811_aO2 = MinecraftInstance.f362mc.field_71439_g.func_174811_aO();
                            switch (func_174811_aO2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[func_174811_aO2.ordinal()]) {
                                case 1:
                                    z2 = -1.0d;
                                    break;
                                case 2:
                                    x2 = 1.0d;
                                    break;
                                case 3:
                                    z2 = 1.0d;
                                    break;
                                case 4:
                                    x2 = -1.0d;
                                    break;
                            }
                            MinecraftInstance.f362mc.field_71439_g.func_70107_b(MinecraftInstance.f362mc.field_71439_g.field_70165_t + x2, i, MinecraftInstance.f362mc.field_71439_g.field_70161_v + z2);
                            return;
                        }
                        MinecraftInstance.f362mc.field_71439_g.func_70107_b(MinecraftInstance.f362mc.field_71439_g.field_70165_t, i, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
                    } while (i != i3);
                }
            }
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g != null) {
            if (((event.getBlock() instanceof BlockLadder) || (event.getBlock() instanceof BlockVine)) && StringsKt.equals(this.modeValue.get(), "AAC3.0.5", true) && MinecraftInstance.f362mc.field_71439_g.func_70617_f_()) {
                event.setBoundingBox(null);
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }
}
