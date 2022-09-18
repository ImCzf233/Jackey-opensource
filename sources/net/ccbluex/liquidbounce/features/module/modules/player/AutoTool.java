package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoTool.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoTool;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "switchSlot", "blockPos", "Lnet/minecraft/util/BlockPos;", "LiquidBounce"})
@ModuleInfo(name = "AutoTool", spacedName = "Auto Tool", description = "Automatically selects the best tool in your inventory to mine a block.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoTool.class */
public final class AutoTool extends Module {
    @EventTarget
    public final void onClick(@NotNull ClickBlockEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BlockPos clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        switchSlot(clickedBlock);
    }

    public final void switchSlot(@NotNull BlockPos blockPos) {
        Intrinsics.checkNotNullParameter(blockPos, "blockPos");
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        Block block = MinecraftInstance.f362mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
        int i = 0;
        while (i < 9) {
            int i2 = i;
            i++;
            ItemStack item = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(i2);
            if (item != null) {
                float speed = item.func_150997_a(block);
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i2;
                }
            }
        }
        if (bestSlot != -1) {
            MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c = bestSlot;
        }
    }
}
