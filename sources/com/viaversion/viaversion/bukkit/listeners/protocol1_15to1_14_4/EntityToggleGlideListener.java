package com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.potion.PotionEffectType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/protocol1_15to1_14_4/EntityToggleGlideListener.class */
public class EntityToggleGlideListener extends ViaBukkitListener {
    private boolean swimmingMethodExists;

    public EntityToggleGlideListener(ViaVersionPlugin plugin) {
        super(plugin, Protocol1_15To1_14_4.class);
        try {
            Player.class.getMethod("isSwimming", new Class[0]);
            this.swimmingMethodExists = true;
        } catch (NoSuchMethodException e) {
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void entityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (isOnPipe(player) && event.isGliding() && event.isCancelled()) {
            PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, (ByteBuf) null, getUserConnection(player));
            try {
                packet.write(Type.VAR_INT, Integer.valueOf(player.getEntityId()));
                byte bitmask = 0;
                if (player.getFireTicks() > 0) {
                    bitmask = (byte) (0 | 1);
                }
                if (player.isSneaking()) {
                    bitmask = (byte) (bitmask | 2);
                }
                if (player.isSprinting()) {
                    bitmask = (byte) (bitmask | 8);
                }
                if (this.swimmingMethodExists && player.isSwimming()) {
                    bitmask = (byte) (bitmask | 16);
                }
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    bitmask = (byte) (bitmask | 32);
                }
                if (player.isGlowing()) {
                    bitmask = (byte) (bitmask | 64);
                }
                packet.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata(0, Types1_14.META_TYPES.byteType, Byte.valueOf(bitmask))));
                packet.scheduleSend(Protocol1_15To1_14_4.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
