package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoRespawn.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoRespawn", spacedName = "Auto Respawn", description = "Automatically respawns you after dying.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn.class */
public final class AutoRespawn extends Module {
    @NotNull
    private final BoolValue instantValue = new BoolValue("Instant", true);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        boolean z;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.instantValue.get().booleanValue()) {
            z = ((MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() > 0.0f ? 1 : (MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() == 0.0f ? 0 : -1)) == 0) || MinecraftInstance.f362mc.field_71439_g.field_70128_L;
        } else {
            if (MinecraftInstance.f362mc.field_71462_r instanceof GuiGameOver) {
                GuiGameOver guiGameOver = MinecraftInstance.f362mc.field_71462_r;
                if (guiGameOver == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.client.gui.GuiGameOver");
                }
                if (guiGameOver.field_146347_a >= 20) {
                    z = true;
                }
            }
            z = false;
        }
        if (z) {
            MinecraftInstance.f362mc.field_71439_g.func_71004_bE();
            MinecraftInstance.f362mc.func_147108_a((GuiScreen) null);
        }
    }
}
