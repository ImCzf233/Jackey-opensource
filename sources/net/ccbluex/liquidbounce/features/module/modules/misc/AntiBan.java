package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;

/* compiled from: AntiBan.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n��R\u0014\u0010\n\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001a"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiBan;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "detected", "", "finishedCheck", "obStaffs", "", "staff_fallback", "staff_main", "tag", "getTag", "()Ljava/lang/String;", "totalCount", "", "updater", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onEnable", "", "onInitialize", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "e", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "AntiBan", spacedName = "Anti Ban", description = "Anti staff on BlocksMC. Automatically leaves a map if detected known staffs.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiBan.class */
public final class AntiBan extends Module {
    private boolean detected;
    private int totalCount;
    private boolean finishedCheck;
    @NotNull
    private String obStaffs = "_";
    @NotNull
    private MSTimer updater = new MSTimer();
    private String staff_main = "https://add-my-brain.exit-scammed.repl.co/staff/";
    private String staff_fallback = "https://wysi-foundation.github.io/LiquidCloud/LiquidBounce/staffs.txt";

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onInitialize() {
        Thread.thread$default(false, false, null, null, 0, new AntiBan$onInitialize$1(this), 31, null);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.detected = false;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        this.detected = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0080, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0119, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01b2, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x024b, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x02e4, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x037d, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0416, code lost:
        if (kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6.obStaffs, (java.lang.CharSequence) r1, false, 2, (java.lang.Object) null) != false) goto L81;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPacket(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.PacketEvent r7) {
        /*
            Method dump skipped, instructions count: 1256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.misc.AntiBan.onPacket(net.ccbluex.liquidbounce.event.PacketEvent):void");
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return String.valueOf(this.totalCount);
    }
}
