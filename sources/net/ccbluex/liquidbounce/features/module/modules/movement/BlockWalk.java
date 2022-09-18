package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

/* compiled from: BlockWalk.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\n"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/BlockWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cobwebValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "snowValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "LiquidBounce"})
@ModuleInfo(name = "BlockWalk", spacedName = "Block Walk", description = "Allows you to walk on non-fullblock blocks.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/BlockWalk.class */
public final class BlockWalk extends Module {
    @NotNull
    private final BoolValue cobwebValue = new BoolValue("Cobweb", true);
    @NotNull
    private final BoolValue snowValue = new BoolValue("Snow", true);

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((this.cobwebValue.get().booleanValue() && Intrinsics.areEqual(event.getBlock(), Blocks.field_150321_G)) || (this.snowValue.get().booleanValue() && Intrinsics.areEqual(event.getBlock(), Blocks.field_150431_aC))) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0d, event.getY() + 1.0d, event.getZ() + 1.0d));
        }
    }
}
