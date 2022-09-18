package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

/* compiled from: Target.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Target$drawElement$2 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Target$drawElement$2.class */
final class Target$drawElement$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Target this$0;
    final /* synthetic */ float $calcTranslateX;
    final /* synthetic */ float $calcTranslateY;
    final /* synthetic */ float $calcScaleX;
    final /* synthetic */ float $calcScaleY;
    final /* synthetic */ TargetStyle $mainStyle;
    final /* synthetic */ EntityPlayer $convertTarget;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Target$drawElement$2(Target $receiver, float $calcTranslateX, float $calcTranslateY, float $calcScaleX, float $calcScaleY, TargetStyle $mainStyle, EntityPlayer $convertTarget) {
        super(0);
        this.this$0 = $receiver;
        this.$calcTranslateX = $calcTranslateX;
        this.$calcTranslateY = $calcTranslateY;
        this.$calcScaleX = $calcScaleX;
        this.$calcScaleY = $calcScaleY;
        this.$mainStyle = $mainStyle;
        this.$convertTarget = $convertTarget;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        GL11.glPushMatrix();
        GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0d);
        if (this.this$0.getFadeValue().get().booleanValue()) {
            GL11.glTranslatef(this.$calcTranslateX, this.$calcTranslateY, 0.0f);
            GL11.glScalef(1.0f - this.$calcScaleX, 1.0f - this.$calcScaleY, 1.0f - this.$calcScaleX);
        }
        this.$mainStyle.handleShadowCut(this.$convertTarget);
        GL11.glPopMatrix();
    }
}
