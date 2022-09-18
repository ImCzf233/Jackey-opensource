package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ResourcePackRepository.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinResourcePackRepository.class */
public class MixinResourcePackRepository {
    @Shadow
    @Final
    private File field_148534_e;
    @Shadow
    @Final
    private static Logger field_177320_c;

    @Inject(method = {"deleteOldServerResourcesPacks"}, m23at = {@AbstractC1790At("HEAD")})
    private void createDirectory(CallbackInfo ci) {
        if (!this.field_148534_e.exists()) {
            this.field_148534_e.mkdirs();
        }
    }

    @Overwrite
    private void func_183028_i() {
        try {
            List<File> lvt_1_1_ = Lists.newArrayList(FileUtils.listFiles(this.field_148534_e, TrueFileFilter.TRUE, (IOFileFilter) null));
            Collections.sort(lvt_1_1_, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int lvt_2_1_ = 0;
            for (File lvt_4_1_ : lvt_1_1_) {
                int i = lvt_2_1_;
                lvt_2_1_++;
                if (i >= 10) {
                    field_177320_c.info("Deleting old server resource pack " + lvt_4_1_.getName());
                    FileUtils.deleteQuietly(lvt_4_1_);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
