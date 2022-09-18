package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packet", "Lnet/minecraft/network/Packet;", "(Lnet/minecraft/network/Packet;)V", "getPacket", "()Lnet/minecraft/network/Packet;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/PacketEvent.class */
public final class PacketEvent extends CancellableEvent {
    @NotNull
    private final Packet<?> packet;

    public PacketEvent(@NotNull Packet<?> packet) {
        Intrinsics.checkNotNullParameter(packet, "packet");
        this.packet = packet;
    }

    @NotNull
    public final Packet<?> getPacket() {
        return this.packet;
    }
}
