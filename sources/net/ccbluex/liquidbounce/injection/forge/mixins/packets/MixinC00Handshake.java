package net.ccbluex.liquidbounce.injection.forge.mixins.packets;

import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({C00Handshake.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/packets/MixinC00Handshake.class */
public class MixinC00Handshake {
    @Shadow
    private int field_149600_a;
    @Shadow
    public int field_149599_c;
    @Shadow
    private EnumConnectionState field_149597_d;
    @Shadow
    public String field_149598_b;

    @ModifyConstant(method = {"writePacketData"}, constant = {@Constant(stringValue = "��FML��")})
    private String injectAntiForge(String constant) {
        return (!AntiForge.enabled || !AntiForge.blockFML || Minecraft.func_71410_x().func_71387_A()) ? "��FML��" : "";
    }
}
