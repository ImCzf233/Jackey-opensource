package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoFOV.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoFOV;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fovValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getFovValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "LiquidBounce"})
@ModuleInfo(name = "NoFOV", spacedName = "No FOV", description = "Disables FOV changes caused by speed effect, etc.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/NoFOV.class */
public final class NoFOV extends Module {
    @NotNull
    private final FloatValue fovValue = new FloatValue("FOV", 1.0f, 0.0f, 1.5f, "x");

    @NotNull
    public final FloatValue getFovValue() {
        return this.fovValue;
    }
}
