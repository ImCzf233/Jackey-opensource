package net.ccbluex.liquidbounce.injection.forge.mixins.resources;

import com.google.common.collect.ImmutableSet;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.util.Set;
import net.minecraft.client.resources.DefaultResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({DefaultResourcePack.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/resources/MixinDefaultResourcePack.class */
public class MixinDefaultResourcePack {
    @Shadow
    public static final Set<String> field_110608_a = ImmutableSet.of(Key.MINECRAFT_NAMESPACE, "realms");
}
