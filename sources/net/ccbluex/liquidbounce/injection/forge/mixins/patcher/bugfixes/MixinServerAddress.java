package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import java.net.IDN;
import net.minecraft.client.multiplayer.ServerAddress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ServerAddress.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinServerAddress.class */
public class MixinServerAddress {
    @Shadow
    @Final
    private String field_78866_a;

    @Overwrite
    public String func_78861_a() {
        try {
            return IDN.toASCII(this.field_78866_a);
        } catch (Exception e) {
            return "";
        }
    }
}
