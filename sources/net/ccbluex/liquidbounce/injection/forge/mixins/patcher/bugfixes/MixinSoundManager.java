package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

@Mixin({SoundManager.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinSoundManager.class */
public abstract class MixinSoundManager {
    @Shadow
    @Final
    private Map<String, ISound> field_148629_h;
    private final List<String> p_pausedSounds = new ArrayList();

    @Shadow
    public abstract boolean func_148597_a(ISound iSound);

    @Redirect(method = {"pauseAllSounds"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager$SoundSystemStarterThread;pause(Ljava/lang/String;)V", remap = false))
    private void p_onlyPauseSoundIfNecessary(@Coerce SoundSystem soundSystem, String sound) {
        if (func_148597_a(this.field_148629_h.get(sound))) {
            soundSystem.pause(sound);
            this.p_pausedSounds.add(sound);
        }
    }

    @Redirect(method = {"resumeAllSounds"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;", remap = false))
    private Iterator<String> p_iterateOverPausedSounds(Set<String> keySet) {
        return this.p_pausedSounds.iterator();
    }

    @Redirect(method = {"playSound"}, slice = @Slice(from = @AbstractC1790At(value = "CONSTANT", args = {"stringValue=Unable to play unknown soundEvent: {}"}, ordinal = 0)), m17at = @AbstractC1790At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Lorg/apache/logging/log4j/Marker;Ljava/lang/String;[Ljava/lang/Object;)V", ordinal = 0, remap = false))
    private void p_silenceWarning(Logger instance, Marker marker, String s, Object[] objects) {
    }

    @Inject(method = {"resumeAllSounds"}, m23at = {@AbstractC1790At("TAIL")})
    private void p_clearPausedSounds(CallbackInfo ci) {
        this.p_pausedSounds.clear();
    }
}
