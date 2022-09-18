package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import net.minecraft.util.LazyLoadBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LazyLoadBase.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinLazyLoadBase.class */
public abstract class MixinLazyLoadBase<T> {
    @Shadow
    private boolean field_179282_b;
    @Shadow
    private T field_179283_a;

    @Shadow
    protected abstract T func_179280_b();

    @Overwrite
    public T func_179281_c() {
        if (!this.field_179282_b) {
            synchronized (this) {
                if (!this.field_179282_b) {
                    this.field_179283_a = func_179280_b();
                    this.field_179282_b = true;
                }
            }
        }
        return this.field_179283_a;
    }
}
