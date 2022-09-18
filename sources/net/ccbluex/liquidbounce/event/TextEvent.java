package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018��2\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/TextEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "setText", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/TextEvent.class */
public final class TextEvent extends Event {
    @Nullable
    private String text;

    public TextEvent(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public final String getText() {
        return this.text;
    }

    public final void setText(@Nullable String str) {
        this.text = str;
    }
}
