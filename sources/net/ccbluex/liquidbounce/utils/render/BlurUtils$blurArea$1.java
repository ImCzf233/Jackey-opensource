package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.minecraft.client.renderer.GlStateManager;

/* compiled from: BlurUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/BlurUtils$blurArea$1.class */
public final class BlurUtils$blurArea$1 extends Lambda implements Functions<Unit> {

    /* renamed from: $x */
    final /* synthetic */ float f375$x;

    /* renamed from: $y */
    final /* synthetic */ float f376$y;
    final /* synthetic */ float $x2;
    final /* synthetic */ float $y2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlurUtils$blurArea$1(float $x, float $y, float $x2, float $y2) {
        super(0);
        this.f375$x = $x;
        this.f376$y = $y;
        this.$x2 = $x2;
        this.$y2 = $y2;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.quickDrawRect(this.f375$x, this.f376$y, this.$x2, this.$y2);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
}
