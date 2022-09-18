package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Tickable;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/Cooldown.class */
public class Cooldown extends StoredObject implements Tickable {
    private double attackSpeed = 4.0d;
    private long lastHit = 0;
    private final ViaRewindConfig.CooldownIndicator cooldownIndicator;
    private UUID bossUUID;
    private boolean lastSend;
    private static final int max = 10;

    public Cooldown(UserConnection user) {
        super(user);
        ViaRewindConfig.CooldownIndicator indicator;
        try {
            indicator = ViaRewind.getConfig().getCooldownIndicator();
        } catch (IllegalArgumentException e) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            indicator = ViaRewindConfig.CooldownIndicator.DISABLED;
        }
        this.cooldownIndicator = indicator;
    }

    @Override // de.gerrygames.viarewind.utils.Tickable
    public void tick() {
        if (!hasCooldown()) {
            if (this.lastSend) {
                hide();
                this.lastSend = false;
                return;
            }
            return;
        }
        BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker) getUser().get(BlockPlaceDestroyTracker.class);
        if (tracker.isMining()) {
            this.lastHit = 0L;
            if (this.lastSend) {
                hide();
                this.lastSend = false;
                return;
            }
            return;
        }
        showCooldown();
        this.lastSend = true;
    }

    private void showCooldown() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            sendTitle("", getTitle(), 0, 2, 5);
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            sendActionBar(getTitle());
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            sendBossBar((float) getCooldown());
        }
    }

    private void hide() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            sendActionBar("§r");
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            hideTitle();
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            hideBossBar();
        }
    }

    private void hideBossBar() {
        if (this.bossUUID == null) {
            return;
        }
        PacketWrapper wrapper = PacketWrapper.create(12, (ByteBuf) null, getUser());
        wrapper.write(Type.UUID, this.bossUUID);
        wrapper.write(Type.VAR_INT, 1);
        PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
        this.bossUUID = null;
    }

    private void sendBossBar(float cooldown) {
        PacketWrapper wrapper = PacketWrapper.create(12, (ByteBuf) null, getUser());
        if (this.bossUUID == null) {
            this.bossUUID = UUID.randomUUID();
            wrapper.write(Type.UUID, this.bossUUID);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.STRING, "{\"text\":\"  \"}");
            wrapper.write(Type.FLOAT, Float.valueOf(cooldown));
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.UNSIGNED_BYTE, (short) 0);
        } else {
            wrapper.write(Type.UUID, this.bossUUID);
            wrapper.write(Type.VAR_INT, 2);
            wrapper.write(Type.FLOAT, Float.valueOf(cooldown));
        }
        PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
    }

    private void hideTitle() {
        PacketWrapper hide = PacketWrapper.create(69, (ByteBuf) null, getUser());
        hide.write(Type.VAR_INT, 3);
        PacketUtil.sendPacket(hide, Protocol1_8TO1_9.class);
    }

    private void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        PacketWrapper timePacket = PacketWrapper.create(69, (ByteBuf) null, getUser());
        timePacket.write(Type.VAR_INT, 2);
        timePacket.write(Type.INT, Integer.valueOf(fadeIn));
        timePacket.write(Type.INT, Integer.valueOf(stay));
        timePacket.write(Type.INT, Integer.valueOf(fadeOut));
        PacketWrapper titlePacket = PacketWrapper.create(69, (ByteBuf) null, getUser());
        titlePacket.write(Type.VAR_INT, 0);
        titlePacket.write(Type.STRING, title);
        PacketWrapper subtitlePacket = PacketWrapper.create(69, (ByteBuf) null, getUser());
        subtitlePacket.write(Type.VAR_INT, 1);
        subtitlePacket.write(Type.STRING, subTitle);
        PacketUtil.sendPacket(titlePacket, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(subtitlePacket, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(timePacket, Protocol1_8TO1_9.class);
    }

    private void sendActionBar(String bar) {
        PacketWrapper actionBarPacket = PacketWrapper.create(2, (ByteBuf) null, getUser());
        actionBarPacket.write(Type.STRING, bar);
        actionBarPacket.write(Type.BYTE, (byte) 2);
        PacketUtil.sendPacket(actionBarPacket, Protocol1_8TO1_9.class);
    }

    public boolean hasCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        double cooldown = restrain((time * this.attackSpeed) / 1000.0d, 0.0d, 1.5d);
        return cooldown > 0.1d && cooldown < 1.1d;
    }

    public double getCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        return restrain((time * this.attackSpeed) / 1000.0d, 0.0d, 1.0d);
    }

    private double restrain(double x, double a, double b) {
        return x < a ? a : x > b ? b : x;
    }

    private String getTitle() {
        String symbol = this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR ? "■" : "˙";
        double cooldown = getCooldown();
        int green = (int) Math.floor(10.0d * cooldown);
        int grey = 10 - green;
        StringBuilder builder = new StringBuilder("§8");
        while (true) {
            int i = green;
            green--;
            if (i <= 0) {
                break;
            }
            builder.append(symbol);
        }
        builder.append("§7");
        while (true) {
            int i2 = grey;
            grey--;
            if (i2 > 0) {
                builder.append(symbol);
            } else {
                return builder.toString();
            }
        }
    }

    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setAttackSpeed(double base, ArrayList<Pair<Byte, Double>> modifiers) {
        this.attackSpeed = base;
        int j = 0;
        while (j < modifiers.size()) {
            if (modifiers.get(j).getKey().byteValue() == 0) {
                this.attackSpeed += modifiers.get(j).getValue().doubleValue();
                int i = j;
                j--;
                modifiers.remove(i);
            }
            j++;
        }
        int j2 = 0;
        while (j2 < modifiers.size()) {
            if (modifiers.get(j2).getKey().byteValue() == 1) {
                this.attackSpeed += base * modifiers.get(j2).getValue().doubleValue();
                int i2 = j2;
                j2--;
                modifiers.remove(i2);
            }
            j2++;
        }
        int j3 = 0;
        while (j3 < modifiers.size()) {
            if (modifiers.get(j3).getKey().byteValue() == 2) {
                this.attackSpeed *= 1.0d + modifiers.get(j3).getValue().doubleValue();
                int i3 = j3;
                j3--;
                modifiers.remove(i3);
            }
            j3++;
        }
    }

    public void hit() {
        this.lastHit = System.currentTimeMillis();
    }

    public void setLastHit(long lastHit) {
        this.lastHit = lastHit;
    }
}
