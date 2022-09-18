package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Gui.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGui.class */
public abstract class MixinGui {
    @Shadow
    protected float field_73735_i;

    @Shadow
    public abstract void func_175174_a(float f, float f2, int i, int i2, int i3, int i4);
}
