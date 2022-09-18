package net.ccbluex.liquidbounce.utils.misc.sound;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: TipSoundManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "", "()V", "disableSound", "Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;", "getDisableSound", "()Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;", "setDisableSound", "(Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundPlayer;)V", "enableSound", "getEnableSound", "setEnableSound", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager.class */
public final class TipSoundManager {
    @NotNull
    private TipSoundPlayer enableSound;
    @NotNull
    private TipSoundPlayer disableSound;

    public TipSoundManager() {
        File enableSoundFile = new File(LiquidBounce.INSTANCE.getFileManager().soundsDir, "enable.wav");
        File disableSoundFile = new File(LiquidBounce.INSTANCE.getFileManager().soundsDir, "disable.wav");
        if (!enableSoundFile.exists()) {
            FileUtils.unpackFile(enableSoundFile, "assets/minecraft/liquidbounce+/sound/enable.wav");
        }
        if (!disableSoundFile.exists()) {
            FileUtils.unpackFile(disableSoundFile, "assets/minecraft/liquidbounce+/sound/disable.wav");
        }
        this.enableSound = new TipSoundPlayer(enableSoundFile);
        this.disableSound = new TipSoundPlayer(disableSoundFile);
    }

    @NotNull
    public final TipSoundPlayer getEnableSound() {
        return this.enableSound;
    }

    public final void setEnableSound(@NotNull TipSoundPlayer tipSoundPlayer) {
        Intrinsics.checkNotNullParameter(tipSoundPlayer, "<set-?>");
        this.enableSound = tipSoundPlayer;
    }

    @NotNull
    public final TipSoundPlayer getDisableSound() {
        return this.disableSound;
    }

    public final void setDisableSound(@NotNull TipSoundPlayer tipSoundPlayer) {
        Intrinsics.checkNotNullParameter(tipSoundPlayer, "<set-?>");
        this.disableSound = tipSoundPlayer;
    }
}
