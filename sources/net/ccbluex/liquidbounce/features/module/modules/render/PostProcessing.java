package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: PostProcessing.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u000f\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\nR\u000e\u0010\u0013\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/PostProcessing;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blur", "", "getBlur", "()Z", "blurStrength", "", "getBlurStrength", "()F", "blurStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "shadow", "getShadow", "shadowStrength", "getShadowStrength", "shadowStrengthValue", "shadowValue", "LiquidBounce"})
@ModuleInfo(name = "PostProcessing", spacedName = "Post Processing", description = "Adds visual effects (shadow, blur) to HUD elements.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/PostProcessing.class */
public final class PostProcessing extends Module {
    @NotNull
    private final BoolValue blurValue = new BoolValue("Blur", true);
    @NotNull
    private final FloatValue blurStrengthValue = new FloatValue("Blur-Strength", 0.01f, 0.01f, 40.0f, new PostProcessing$blurStrengthValue$1(this));
    @NotNull
    private final BoolValue shadowValue = new BoolValue("Shadow", true);
    @NotNull
    private final FloatValue shadowStrengthValue = new FloatValue("Shadow-Strength", 0.01f, 0.01f, 40.0f, new PostProcessing$shadowStrengthValue$1(this));

    public final boolean getBlur() {
        return this.blurValue.get().booleanValue();
    }

    public final boolean getShadow() {
        return this.shadowValue.get().booleanValue();
    }

    public final float getBlurStrength() {
        return this.blurStrengthValue.get().floatValue();
    }

    public final float getShadowStrength() {
        return this.shadowStrengthValue.get().floatValue();
    }
}
