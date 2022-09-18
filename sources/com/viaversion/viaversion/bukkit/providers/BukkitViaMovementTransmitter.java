package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/providers/BukkitViaMovementTransmitter.class */
public class BukkitViaMovementTransmitter extends MovementTransmitterProvider {
    private static boolean USE_NMS = true;
    private Object idlePacket;
    private Object idlePacket2;
    private Method getHandle;
    private Field connection;
    private Method handleFlying;

    public BukkitViaMovementTransmitter() {
        USE_NMS = Via.getConfig().isNMSPlayerTicking();
        try {
            Class<?> idlePacketClass = NMSUtil.nms("PacketPlayInFlying");
            try {
                this.idlePacket = idlePacketClass.newInstance();
                this.idlePacket2 = idlePacketClass.newInstance();
                Field flying = idlePacketClass.getDeclaredField("f");
                flying.setAccessible(true);
                flying.set(this.idlePacket2, true);
                if (USE_NMS) {
                    try {
                        this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
                        try {
                            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
                            try {
                                this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", idlePacketClass);
                            } catch (ClassNotFoundException | NoSuchMethodException e) {
                                throw new RuntimeException("Couldn't find CraftPlayer", e);
                            }
                        } catch (ClassNotFoundException | NoSuchFieldException e2) {
                            throw new RuntimeException("Couldn't find Player Connection", e2);
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException e3) {
                        throw new RuntimeException("Couldn't find CraftPlayer", e3);
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException e4) {
                throw new RuntimeException("Couldn't make player idle packet, help!", e4);
            }
        } catch (ClassNotFoundException e5) {
        }
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public Object getFlyingPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket2;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public Object getGroundPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket;
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider
    public void sendPlayer(UserConnection info) {
        if (USE_NMS) {
            Player player = Bukkit.getPlayer(info.getProtocolInfo().getUuid());
            if (player != null) {
                try {
                    Object entityPlayer = this.getHandle.invoke(player, new Object[0]);
                    Object pc = this.connection.get(entityPlayer);
                    if (pc != null) {
                        Method method = this.handleFlying;
                        Object[] objArr = new Object[1];
                        objArr[0] = ((MovementTracker) info.get(MovementTracker.class)).isGround() ? this.idlePacket2 : this.idlePacket;
                        method.invoke(pc, objArr);
                        ((MovementTracker) info.get(MovementTracker.class)).incrementIdlePacket();
                    }
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        super.sendPlayer(info);
    }
}
