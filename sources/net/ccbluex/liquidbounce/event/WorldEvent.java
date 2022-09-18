package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.client.multiplayer.WorldClient;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "worldClient", "Lnet/minecraft/client/multiplayer/WorldClient;", "(Lnet/minecraft/client/multiplayer/WorldClient;)V", "getWorldClient", "()Lnet/minecraft/client/multiplayer/WorldClient;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/WorldEvent.class */
public final class WorldEvent extends Event {
    @Nullable
    private final WorldClient worldClient;

    public WorldEvent(@Nullable WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    @Nullable
    public final WorldClient getWorldClient() {
        return this.worldClient;
    }
}
