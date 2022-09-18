package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoBreak.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/AutoBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoBreak", spacedName = "Auto Break", description = "Automatically breaks the block you are looking at.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/AutoBreak.class */
public final class AutoBreak extends Module {
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71476_x == null || MinecraftInstance.f362mc.field_71476_x.func_178782_a() == null) {
            return;
        }
        MinecraftInstance.f362mc.field_71474_y.field_74312_F.field_74513_e = !Intrinsics.areEqual(MinecraftInstance.f362mc.field_71441_e.func_180495_p(MinecraftInstance.f362mc.field_71476_x.func_178782_a()).func_177230_c(), Blocks.field_150350_a);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74312_F)) {
            MinecraftInstance.f362mc.field_71474_y.field_74312_F.field_74513_e = false;
        }
    }
}
