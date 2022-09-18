package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CivBreak.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/CivBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airResetValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blockPos", "Lnet/minecraft/util/BlockPos;", "enumFacing", "Lnet/minecraft/util/EnumFacing;", AsmConstants.CODERANGE, "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rangeResetValue", "rotationsValue", "visualSwingValue", "onBlockClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"})
@ModuleInfo(name = "CivBreak", spacedName = "Civ Break", description = "Allows you to break blocks instantly.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/CivBreak.class */
public final class CivBreak extends Module {
    @Nullable
    private BlockPos blockPos;
    @Nullable
    private EnumFacing enumFacing;
    @NotNull
    private final FloatValue range = new FloatValue("Range", 5.0f, 1.0f, 6.0f);
    @NotNull
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    @NotNull
    private final BoolValue visualSwingValue = new BoolValue("VisualSwing", true);
    @NotNull
    private final BoolValue airResetValue = new BoolValue("Air-Reset", true);
    @NotNull
    private final BoolValue rangeResetValue = new BoolValue("Range-Reset", true);

    /* compiled from: CivBreak.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/CivBreak$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EventState.values().length];
            iArr[EventState.PRE.ordinal()] = 1;
            iArr[EventState.POST.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @EventTarget
    public final void onBlockClick(@NotNull ClickBlockEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (Intrinsics.areEqual(BlockUtils.getBlock(event.getClickedBlock()), Blocks.field_150357_h)) {
            return;
        }
        this.blockPos = event.getClickedBlock();
        this.enumFacing = event.getEnumFacing();
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, this.enumFacing));
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.enumFacing));
    }

    @EventTarget
    public final void onUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BlockPos pos = this.blockPos;
        if (pos == null) {
            return;
        }
        if ((this.airResetValue.get().booleanValue() && (BlockUtils.getBlock(pos) instanceof BlockAir)) || (this.rangeResetValue.get().booleanValue() && BlockUtils.getCenterDistance(pos) > this.range.get().floatValue())) {
            this.blockPos = null;
        } else if ((BlockUtils.getBlock(pos) instanceof BlockAir) || BlockUtils.getCenterDistance(pos) > this.range.get().floatValue()) {
        } else {
            switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                case 1:
                    if (this.rotationsValue.get().booleanValue()) {
                        VecRotation faceBlock = RotationUtils.faceBlock(pos);
                        if (faceBlock == null) {
                            return;
                        }
                        RotationUtils.setTargetRotation(faceBlock.getRotation());
                        return;
                    }
                    return;
                case 2:
                    if (this.visualSwingValue.get().booleanValue()) {
                        MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                    } else {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
                    }
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, this.enumFacing));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.enumFacing));
                    MinecraftInstance.f362mc.field_71442_b.func_180511_b(this.blockPos, this.enumFacing);
                    return;
                default:
                    return;
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BlockPos blockPos = this.blockPos;
        if (blockPos == null) {
            return;
        }
        RenderUtils.drawBlockBox(blockPos, Color.RED, true);
    }
}
