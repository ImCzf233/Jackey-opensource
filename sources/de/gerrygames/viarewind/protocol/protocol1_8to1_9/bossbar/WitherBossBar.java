package de.gerrygames.viarewind.protocol.protocol1_8to1_9.bossbar;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/bossbar/WitherBossBar.class */
public class WitherBossBar implements BossBar {
    private static int highestId = 2147473647;
    private final UUID uuid;
    private String title;
    private float health;
    private boolean visible = false;
    private UserConnection connection;
    private final int entityId;
    private double locX;
    private double locY;
    private double locZ;

    public WitherBossBar(UserConnection connection, UUID uuid, String title, float health) {
        int i = highestId;
        highestId = i + 1;
        this.entityId = i;
        this.connection = connection;
        this.uuid = uuid;
        this.title = title;
        this.health = health;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public String getTitle() {
        return this.title;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setTitle(String title) {
        this.title = title;
        if (this.visible) {
            updateMetadata();
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public float getHealth() {
        return this.health;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setHealth(float health) {
        this.health = health;
        if (this.health <= 0.0f) {
            this.health = 1.0E-4f;
        }
        if (this.visible) {
            updateMetadata();
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossColor getColor() {
        return null;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setColor(BossColor bossColor) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support color");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossStyle getStyle() {
        return null;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setStyle(BossStyle bossStyle) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support styles");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addPlayer(UUID uuid) {
        throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addConnection(UserConnection userConnection) {
        throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removePlayer(UUID uuid) {
        throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removeConnection(UserConnection userConnection) {
        throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addFlag(BossFlag bossFlag) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support flags");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removeFlag(BossFlag bossFlag) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support flags");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public boolean hasFlag(BossFlag bossFlag) {
        return false;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public Set<UUID> getPlayers() {
        return Collections.singleton(this.connection.getProtocolInfo().getUuid());
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public Set<UserConnection> getConnections() {
        throw new UnsupportedOperationException(getClass().getName() + " is only for one UserConnection!");
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar show() {
        if (!this.visible) {
            this.visible = true;
            spawnWither();
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar hide() {
        if (this.visible) {
            this.visible = false;
            despawnWither();
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public boolean isVisible() {
        return this.visible;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public UUID getId() {
        return this.uuid;
    }

    public void setLocation(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        updateLocation();
    }

    private void spawnWither() {
        PacketWrapper packetWrapper = PacketWrapper.create(15, (ByteBuf) null, this.connection);
        packetWrapper.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        packetWrapper.write(Type.UNSIGNED_BYTE, (short) 64);
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
        packetWrapper.write(Type.BYTE, (byte) 0);
        packetWrapper.write(Type.BYTE, (byte) 0);
        packetWrapper.write(Type.BYTE, (byte) 0);
        packetWrapper.write(Type.SHORT, (short) 0);
        packetWrapper.write(Type.SHORT, (short) 0);
        packetWrapper.write(Type.SHORT, (short) 0);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Metadata(0, MetaType1_8.Byte, (byte) 32));
        arrayList.add(new Metadata(2, MetaType1_8.String, this.title));
        arrayList.add(new Metadata(3, MetaType1_8.Byte, (byte) 1));
        arrayList.add(new Metadata(6, MetaType1_8.Float, Float.valueOf(this.health * 300.0f)));
        packetWrapper.write(Types1_8.METADATA_LIST, arrayList);
        PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
    }

    private void updateLocation() {
        PacketWrapper packetWrapper = PacketWrapper.create(24, (ByteBuf) null, this.connection);
        packetWrapper.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locX * 32.0d)));
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locY * 32.0d)));
        packetWrapper.write(Type.INT, Integer.valueOf((int) (this.locZ * 32.0d)));
        packetWrapper.write(Type.BYTE, (byte) 0);
        packetWrapper.write(Type.BYTE, (byte) 0);
        packetWrapper.write(Type.BOOLEAN, false);
        PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
    }

    private void updateMetadata() {
        PacketWrapper packetWrapper = PacketWrapper.create(28, (ByteBuf) null, this.connection);
        packetWrapper.write(Type.VAR_INT, Integer.valueOf(this.entityId));
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Metadata(2, MetaType1_8.String, this.title));
        arrayList.add(new Metadata(6, MetaType1_8.Float, Float.valueOf(this.health * 300.0f)));
        packetWrapper.write(Types1_8.METADATA_LIST, arrayList);
        PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
    }

    private void despawnWither() {
        PacketWrapper packetWrapper = PacketWrapper.create(19, (ByteBuf) null, this.connection);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId});
        PacketUtil.sendPacket(packetWrapper, Protocol1_8TO1_9.class, true, false);
    }

    public void setPlayerLocation(double posX, double posY, double posZ, float yaw, float pitch) {
        double yawR = Math.toRadians(yaw);
        double pitchR = Math.toRadians(pitch);
        setLocation(posX - ((Math.cos(pitchR) * Math.sin(yawR)) * 48.0d), posY - (Math.sin(pitchR) * 48.0d), posZ + (Math.cos(pitchR) * Math.cos(yawR) * 48.0d));
    }
}
