package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "guiScreen", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/minecraft/client/gui/GuiScreen;)V", "getGuiScreen", "()Lnet/minecraft/client/gui/GuiScreen;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/ScreenEvent.class */
public final class ScreenEvent extends Event {
    @Nullable
    private final GuiScreen guiScreen;

    public ScreenEvent(@Nullable GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    @Nullable
    public final GuiScreen getGuiScreen() {
        return this.guiScreen;
    }
}
