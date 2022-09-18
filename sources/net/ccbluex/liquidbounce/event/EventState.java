package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: Event.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/EventState;", "", "stateName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getStateName", "()Ljava/lang/String;", "PRE", "POST", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/EventState.class */
public enum EventState {
    PRE("PRE"),
    POST("POST");
    
    @NotNull
    private final String stateName;

    EventState(String stateName) {
        this.stateName = stateName;
    }

    @NotNull
    public final String getStateName() {
        return this.stateName;
    }
}
