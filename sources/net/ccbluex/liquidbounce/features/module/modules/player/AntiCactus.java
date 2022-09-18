package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiCactus.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiCactus;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "LiquidBounce"})
@ModuleInfo(name = "AntiCactus", spacedName = "Anti Cactus", description = "Prevents cactuses from damaging you.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AntiCactus.class */
public final class AntiCactus extends Module {
    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockCactus) {
            event.setBoundingBox(new AxisAlignedBB(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0d, event.getY() + 1.0d, event.getZ() + 1.0d));
        }
    }
}
