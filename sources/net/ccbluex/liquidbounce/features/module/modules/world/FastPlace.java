package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: FastPlace.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastPlace;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getSpeedValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "LiquidBounce"})
@ModuleInfo(name = "FastPlace", spacedName = "Fast Place", description = "Allows you to place blocks faster.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/FastPlace.class */
public final class FastPlace extends Module {
    @NotNull
    private final IntegerValue speedValue = new IntegerValue("Speed", 0, 0, 4);

    @NotNull
    public final IntegerValue getSpeedValue() {
        return this.speedValue;
    }
}
