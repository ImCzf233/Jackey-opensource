package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtension;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChestAura.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\bÇ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "clickedBlocks", "", "Lnet/minecraft/util/BlockPos;", "getClickedBlocks", "()Ljava/util/List;", "currentBlock", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "throughWallsValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "visualSwing", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"})
@ModuleInfo(name = "ChestAura", spacedName = "Chest Aura", description = "Automatically opens chests around you.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestAura.class */
public final class ChestAura extends Module {
    @Nullable
    private static BlockPos currentBlock;
    @NotNull
    public static final ChestAura INSTANCE = new ChestAura();
    @NotNull
    private static final FloatValue rangeValue = new FloatValue("Range", 5.0f, 1.0f, 6.0f, "m");
    @NotNull
    private static final IntegerValue delayValue = new IntegerValue("Delay", 100, 50, 200, "ms");
    @NotNull
    private static final BoolValue throughWallsValue = new BoolValue("ThroughWalls", true);
    @NotNull
    private static final BoolValue visualSwing = new BoolValue("VisualSwing", true);
    @NotNull
    private static final BlockValue chestValue = new BlockValue("Chest", Block.func_149682_b(Blocks.field_150486_ae));
    @NotNull
    private static final BoolValue rotationsValue = new BoolValue("Rotations", true);
    @NotNull
    private static final MSTimer timer = new MSTimer();
    @NotNull
    private static final List<BlockPos> clickedBlocks = new ArrayList();

    /* compiled from: ChestAura.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestAura$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EventState.values().length];
            iArr[EventState.PRE.ordinal()] = 1;
            iArr[EventState.POST.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ChestAura() {
    }

    @NotNull
    public final List<BlockPos> getClickedBlocks() {
        return clickedBlocks;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Object obj;
        VecRotation faceBlock;
        boolean z;
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Blink.class);
        Intrinsics.checkNotNull(module);
        if (module.getState()) {
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
            case 1:
                if (MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) {
                    timer.reset();
                }
                float radius = rangeValue.get().floatValue() + 1;
                Vec3 eyesPos = new Vec3(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b + MinecraftInstance.f362mc.field_71439_g.func_70047_e(), MinecraftInstance.f362mc.field_71439_g.field_70161_v);
                Map $this$filter$iv = BlockUtils.searchBlocks((int) radius);
                Map destination$iv$iv = new LinkedHashMap();
                for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
                    if (Block.func_149682_b(element$iv$iv.getValue()) == chestValue.get().intValue() && !INSTANCE.getClickedBlocks().contains(element$iv$iv.getKey()) && BlockUtils.getCenterDistance(element$iv$iv.getKey()) < ((double) rangeValue.get().floatValue())) {
                        destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                    }
                }
                Map destination$iv$iv2 = new LinkedHashMap();
                for (Map.Entry element$iv$iv2 : destination$iv$iv.entrySet()) {
                    if (throughWallsValue.get().booleanValue()) {
                        z = true;
                    } else {
                        BlockPos blockPos = (BlockPos) element$iv$iv2.getKey();
                        MovingObjectPosition movingObjectPosition = MinecraftInstance.f362mc.field_71441_e.func_147447_a(eyesPos, BlockExtension.getVec(blockPos), false, true, false);
                        z = movingObjectPosition != null && Intrinsics.areEqual(movingObjectPosition.func_178782_a(), blockPos);
                    }
                    if (z) {
                        destination$iv$iv2.put(element$iv$iv2.getKey(), element$iv$iv2.getValue());
                    }
                }
                Iterator it = destination$iv$iv2.entrySet().iterator();
                if (!it.hasNext()) {
                    obj = null;
                } else {
                    Object next = it.next();
                    if (!it.hasNext()) {
                        obj = next;
                    } else {
                        Map.Entry it2 = (Map.Entry) next;
                        double centerDistance = BlockUtils.getCenterDistance((BlockPos) it2.getKey());
                        do {
                            Object next2 = it.next();
                            Map.Entry it3 = (Map.Entry) next2;
                            double centerDistance2 = BlockUtils.getCenterDistance((BlockPos) it3.getKey());
                            if (Double.compare(centerDistance, centerDistance2) > 0) {
                                next = next2;
                                centerDistance = centerDistance2;
                            }
                        } while (it.hasNext());
                        obj = next;
                    }
                }
                Map.Entry entry = (Map.Entry) obj;
                currentBlock = entry == null ? null : (BlockPos) entry.getKey();
                if (!rotationsValue.get().booleanValue()) {
                    return;
                }
                BlockPos blockPos2 = currentBlock;
                if (blockPos2 == null || (faceBlock = RotationUtils.faceBlock(blockPos2)) == null) {
                    return;
                }
                RotationUtils.setTargetRotation(faceBlock.getRotation());
                return;
            case 2:
                if (currentBlock == null || !timer.hasTimePassed(delayValue.get().intValue())) {
                    return;
                }
                PlayerControllerMP playerControllerMP = MinecraftInstance.f362mc.field_71442_b;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
                WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
                ItemStack func_70694_bm = MinecraftInstance.f362mc.field_71439_g.func_70694_bm();
                BlockPos blockPos3 = currentBlock;
                EnumFacing enumFacing = EnumFacing.DOWN;
                BlockPos blockPos4 = currentBlock;
                Intrinsics.checkNotNull(blockPos4);
                if (!playerControllerMP.func_178890_a(entityPlayerSP, worldClient, func_70694_bm, blockPos3, enumFacing, BlockExtension.getVec(blockPos4))) {
                    return;
                }
                if (visualSwing.get().booleanValue()) {
                    MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                } else {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
                }
                List<BlockPos> list = clickedBlocks;
                BlockPos blockPos5 = currentBlock;
                Intrinsics.checkNotNull(blockPos5);
                list.add(blockPos5);
                currentBlock = null;
                timer.reset();
                return;
            default:
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        clickedBlocks.clear();
    }
}
