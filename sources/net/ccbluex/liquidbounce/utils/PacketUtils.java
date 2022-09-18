package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/PacketUtils.class */
public class PacketUtils extends MinecraftInstance implements Listenable {
    public static int inBound;
    public static int avgInBound;
    public static int outBound = 0;
    public static int avgOutBound = 0;
    private static ArrayList<Packet<INetHandlerPlayServer>> packets = new ArrayList<>();
    private static MSTimer packetTimer = new MSTimer();
    private static MSTimer wdTimer = new MSTimer();
    private static int transCount = 0;
    private static int wdVL = 0;

    private static boolean isInventoryAction(short action) {
        return action > 0 && action < 100;
    }

    public static boolean isWatchdogActive() {
        return wdVL >= 8;
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        handlePacket(event.getPacket());
    }

    private static void handlePacket(Packet<?> packet) {
        if (packet.getClass().getSimpleName().startsWith("C")) {
            outBound++;
        } else if (packet.getClass().getSimpleName().startsWith("S")) {
            inBound++;
        }
        if ((packet instanceof S32PacketConfirmTransaction) && !isInventoryAction(((S32PacketConfirmTransaction) packet).func_148890_d())) {
            transCount++;
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (packetTimer.hasTimePassed(1000L)) {
            avgInBound = inBound;
            avgOutBound = outBound;
            outBound = 0;
            inBound = 0;
            packetTimer.reset();
        }
        if (f362mc.field_71439_g == null || f362mc.field_71441_e == null) {
            wdVL = 0;
            transCount = 0;
            wdTimer.reset();
        } else if (wdTimer.hasTimePassed(100L)) {
            wdVL += transCount > 0 ? 1 : -1;
            transCount = 0;
            if (wdVL > 10) {
                wdVL = 10;
            }
            if (wdVL < 0) {
                wdVL = 0;
            }
            wdTimer.reset();
        }
    }

    public static void sendPacketNoEvent(Packet<INetHandlerPlayServer> packet) {
        packets.add(packet);
        f362mc.func_147114_u().func_147297_a(packet);
    }

    public static boolean handleSendPacket(Packet<?> packet) {
        if (packets.contains(packet)) {
            packets.remove(packet);
            handlePacket(packet);
            return true;
        }
        return false;
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
