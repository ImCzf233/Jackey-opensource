package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityPlayer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityPlayer.class */
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {
    @Shadow
    protected int field_71101_bC;
    @Shadow
    public PlayerCapabilities field_71075_bZ;

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntityLivingBase
    @Shadow
    public abstract ItemStack func_70694_bm();

    @Shadow
    public abstract GameProfile func_146103_bH();

    @Shadow
    public abstract boolean func_70041_e_();

    @Shadow
    public abstract String func_145776_H();

    @Shadow
    public abstract FoodStats func_71024_bL();

    @Shadow
    public abstract int func_71057_bx();

    @Shadow
    public abstract ItemStack func_71011_bu();

    @Shadow
    public abstract boolean func_71039_bw();

    @Shadow
    public abstract boolean func_70608_bn();
}
