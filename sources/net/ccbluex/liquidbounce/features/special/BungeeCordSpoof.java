package net.ccbluex.liquidbounce.features.special;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import org.jetbrains.annotations.NotNull;

/* compiled from: BungeeCordSpoof.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018�� \f2\u00020\u00012\u00020\u0002:\u0001\fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "getRandomIpPart", "", "handleEvents", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Companion", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/BungeeCordSpoof.class */
public final class BungeeCordSpoof extends MinecraftInstance implements Listenable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Random RANDOM = new Random();
    @JvmPlatformAnnotations
    public static boolean enabled;

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C00Handshake packet = event.getPacket();
        if ((packet instanceof C00Handshake) && enabled && packet.func_149594_c() == EnumConnectionState.LOGIN) {
            packet.field_149598_b = ((Object) packet.field_149598_b) + (char) 0 + getRandomIpPart() + '.' + getRandomIpPart() + '.' + getRandomIpPart() + '.' + getRandomIpPart() + (char) 0 + UUIDSpoofer.getUUID();
        }
    }

    private final String getRandomIpPart() {
        return String.valueOf(RANDOM.nextInt(256));
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }

    /* compiled from: BungeeCordSpoof.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n��¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof$Companion;", "", "()V", "RANDOM", "Ljava/util/Random;", "enabled", "", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/BungeeCordSpoof$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }
}
