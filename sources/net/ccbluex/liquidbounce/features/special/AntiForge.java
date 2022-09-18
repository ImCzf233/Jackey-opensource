package net.ccbluex.liquidbounce.features.special;

import io.netty.buffer.Unpooled;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/AntiForge.class */
public class AntiForge extends MinecraftInstance implements Listenable {
    public static boolean enabled = true;
    public static boolean blockFML = true;
    public static boolean blockProxyPacket = true;
    public static boolean blockPayloadPackets = true;

    @EventTarget
    public void onPacket(PacketEvent event) {
        C17PacketCustomPayload packet = event.getPacket();
        if (enabled && !f362mc.func_71387_A()) {
            try {
                if (blockProxyPacket && packet.getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                    event.cancelEvent();
                }
                if (blockPayloadPackets && (packet instanceof C17PacketCustomPayload)) {
                    C17PacketCustomPayload customPayload = packet;
                    if (!customPayload.func_149559_c().startsWith("MC|")) {
                        event.cancelEvent();
                    } else if (customPayload.func_149559_c().equalsIgnoreCase("MC|Brand")) {
                        customPayload.field_149561_c = new PacketBuffer(Unpooled.buffer()).func_180714_a("vanilla");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
