package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Reach.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Reach;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "buildReachValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBuildReachValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "combatReachValue", "getCombatReachValue", "maxRange", "", "getMaxRange", "()F", "LiquidBounce"})
@ModuleInfo(name = "Reach", description = "Increases your reach.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Reach.class */
public final class Reach extends Module {
    @NotNull
    private final FloatValue combatReachValue = new FloatValue("CombatReach", 3.5f, 3.0f, 7.0f, "m");
    @NotNull
    private final FloatValue buildReachValue = new FloatValue("BuildReach", 5.0f, 4.5f, 7.0f, "m");

    @NotNull
    public final FloatValue getCombatReachValue() {
        return this.combatReachValue;
    }

    @NotNull
    public final FloatValue getBuildReachValue() {
        return this.buildReachValue;
    }

    public final float getMaxRange() {
        float combatRange = this.combatReachValue.get().floatValue();
        float buildRange = this.buildReachValue.get().floatValue();
        return combatRange > buildRange ? combatRange : buildRange;
    }
}
