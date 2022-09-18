package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import java.io.DataInputStream;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({AnvilChunkLoader.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinAnvilChunkLoader.class */
public class MixinAnvilChunkLoader {
    @Redirect(method = {"loadChunk__Async"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompressedStreamTools;read(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NBTTagCompound;"))
    private NBTTagCompound closeStream(DataInputStream stream) throws IOException {
        NBTTagCompound result = CompressedStreamTools.func_74794_a(stream);
        stream.close();
        return result;
    }
}
