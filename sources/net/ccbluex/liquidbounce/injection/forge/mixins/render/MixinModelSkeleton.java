package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ModelSkeleton.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinModelSkeleton.class */
public class MixinModelSkeleton extends ModelBiped {
    public void func_178718_a(float scale) {
        this.field_178723_h.field_78800_c += 1.0f;
        this.field_178723_h.func_78794_c(scale);
        this.field_178723_h.field_78800_c -= 1.0f;
    }
}
