package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoClip.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoClip;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "NoClip", spacedName = "No Clip", description = "Allows you to freely move through walls (A sandblock has to fall on your head).", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoClip.class */
public final class NoClip extends Module {
    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        if (entityPlayerSP == null) {
            return;
        }
        entityPlayerSP.field_70145_X = false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.f362mc.field_71439_g.field_70145_X = false;
        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
        MinecraftInstance.f362mc.field_71439_g.field_70122_E = false;
        MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
        MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
        MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.32f;
        if (MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            MinecraftInstance.f362mc.field_71439_g.field_70181_x += 0.32f;
        }
        if (MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
            MinecraftInstance.f362mc.field_71439_g.field_70181_x -= 0.32f;
        }
    }
}
