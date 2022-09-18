package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoReconnect.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/special/AutoReconnect;", "", "()V", "MAX", "", "MIN", "value", "delay", "getDelay", "()I", "setDelay", "(I)V", "<set-?>", "", "isEnabled", "()Z", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/AutoReconnect.class */
public final class AutoReconnect {
    public static final int MAX = 60000;
    public static final int MIN = 1000;
    @NotNull
    public static final AutoReconnect INSTANCE = new AutoReconnect();
    private static boolean isEnabled = true;
    private static int delay = Level.TRACE_INT;

    private AutoReconnect() {
    }

    public final boolean isEnabled() {
        return isEnabled;
    }

    public final int getDelay() {
        return delay;
    }

    public final void setDelay(int value) {
        isEnabled = value < 60000;
        delay = value;
    }
}
