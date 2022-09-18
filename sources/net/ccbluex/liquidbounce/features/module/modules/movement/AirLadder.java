package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

/* compiled from: AirLadder.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/AirLadder;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AirLadder", spacedName = "Air Ladder", description = "Allows you to climb up ladders/vines without touching them.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/AirLadder.class */
public final class AirLadder extends Module {
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 0.2872f, 0.01f, 5.0f);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((BlockUtils.getBlock(new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 1, MinecraftInstance.f362mc.field_71439_g.field_70161_v)) instanceof BlockLadder) && MinecraftInstance.f362mc.field_71439_g.field_70123_F) || (BlockUtils.getBlock(new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u, MinecraftInstance.f362mc.field_71439_g.field_70161_v)) instanceof BlockVine) || (BlockUtils.getBlock(new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 1, MinecraftInstance.f362mc.field_71439_g.field_70161_v)) instanceof BlockVine)) {
            MinecraftInstance.f362mc.field_71439_g.field_70181_x = this.speedValue.get().floatValue();
            MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
            MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
        }
    }
}
