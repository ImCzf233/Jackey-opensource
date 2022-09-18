package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiBlind.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/AntiBlind;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bossHealth", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBossHealth", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "confusionEffect", "getConfusionEffect", "fireEffect", "getFireEffect", "pumpkinEffect", "getPumpkinEffect", "scoreBoard", "getScoreBoard", "LiquidBounce"})
@ModuleInfo(name = "AntiBlind", spacedName = "Anti Blind", description = "Cancels blindness effects.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/AntiBlind.class */
public final class AntiBlind extends Module {
    @NotNull
    private final BoolValue confusionEffect = new BoolValue("Confusion", true);
    @NotNull
    private final BoolValue pumpkinEffect = new BoolValue("Pumpkin", true);
    @NotNull
    private final BoolValue fireEffect = new BoolValue("Fire", false);
    @NotNull
    private final BoolValue scoreBoard = new BoolValue("Scoreboard", false);
    @NotNull
    private final BoolValue bossHealth = new BoolValue("Boss-Health", true);

    @NotNull
    public final BoolValue getConfusionEffect() {
        return this.confusionEffect;
    }

    @NotNull
    public final BoolValue getPumpkinEffect() {
        return this.pumpkinEffect;
    }

    @NotNull
    public final BoolValue getFireEffect() {
        return this.fireEffect;
    }

    @NotNull
    public final BoolValue getScoreBoard() {
        return this.scoreBoard;
    }

    @NotNull
    public final BoolValue getBossHealth() {
        return this.bossHealth;
    }
}
