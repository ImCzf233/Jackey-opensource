package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.exception.CancelException;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/PacketUtil.class */
public class PacketUtil {
    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
        sendToServer(packet, packetProtocol, true);
    }

    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
        sendToServer(packet, packetProtocol, skipCurrentPipeline, false);
    }

    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
        try {
            if (currentThread) {
                packet.sendToServer(packetProtocol, skipCurrentPipeline);
            } else {
                packet.scheduleSendToServer(packetProtocol, skipCurrentPipeline);
            }
        } catch (CancelException e) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
        return sendPacket(packet, packetProtocol, true);
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
        return sendPacket(packet, packetProtocol, true, false);
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
        try {
            if (currentThread) {
                packet.send(packetProtocol, skipCurrentPipeline);
                return true;
            }
            packet.scheduleSend(packetProtocol, skipCurrentPipeline);
            return true;
        } catch (CancelException e) {
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
