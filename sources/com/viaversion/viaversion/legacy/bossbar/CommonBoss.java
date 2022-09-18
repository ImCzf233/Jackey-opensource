package com.viaversion.viaversion.legacy.bossbar;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/legacy/bossbar/CommonBoss.class */
public class CommonBoss implements BossBar {
    private final UUID uuid;
    private final Map<UUID, UserConnection> connections;
    private final Set<BossFlag> flags;
    private String title;
    private float health;
    private BossColor color;
    private BossStyle style;
    private boolean visible;

    public CommonBoss(String title, float health, BossColor color, BossStyle style) {
        Preconditions.checkNotNull(title, "Title cannot be null");
        Preconditions.checkArgument(health >= 0.0f && health <= 1.0f, "Health must be between 0 and 1. Input: " + health);
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.health = health;
        this.color = color == null ? BossColor.PURPLE : color;
        this.style = style == null ? BossStyle.SOLID : style;
        this.connections = new MapMaker().weakValues().makeMap();
        this.flags = new HashSet();
        this.visible = true;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setTitle(String title) {
        Preconditions.checkNotNull(title);
        this.title = title;
        sendPacket(UpdateAction.UPDATE_TITLE);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setHealth(float health) {
        Preconditions.checkArgument(health >= 0.0f && health <= 1.0f, "Health must be between 0 and 1. Input: " + health);
        this.health = health;
        sendPacket(UpdateAction.UPDATE_HEALTH);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossColor getColor() {
        return this.color;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setColor(BossColor color) {
        Preconditions.checkNotNull(color);
        this.color = color;
        sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar setStyle(BossStyle style) {
        Preconditions.checkNotNull(style);
        this.style = style;
        sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addPlayer(UUID player) {
        UserConnection client = Via.getManager().getConnectionManager().getConnectedClient(player);
        if (client != null) {
            addConnection(client);
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addConnection(UserConnection conn) {
        if (this.connections.put(conn.getProtocolInfo().getUuid(), conn) == null && this.visible) {
            sendPacketConnection(conn, getPacket(UpdateAction.ADD, conn));
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removePlayer(UUID uuid) {
        UserConnection client = this.connections.remove(uuid);
        if (client != null) {
            sendPacketConnection(client, getPacket(UpdateAction.REMOVE, client));
        }
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removeConnection(UserConnection conn) {
        removePlayer(conn.getProtocolInfo().getUuid());
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar addFlag(BossFlag flag) {
        Preconditions.checkNotNull(flag);
        if (!hasFlag(flag)) {
            this.flags.add(flag);
        }
        sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar removeFlag(BossFlag flag) {
        Preconditions.checkNotNull(flag);
        if (hasFlag(flag)) {
            this.flags.remove(flag);
        }
        sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public boolean hasFlag(BossFlag flag) {
        Preconditions.checkNotNull(flag);
        return this.flags.contains(flag);
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public Set<UUID> getPlayers() {
        return Collections.unmodifiableSet(this.connections.keySet());
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(new HashSet(this.connections.values()));
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar show() {
        setVisible(true);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossBar hide() {
        setVisible(false);
        return this;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public boolean isVisible() {
        return this.visible;
    }

    private void setVisible(boolean value) {
        if (this.visible != value) {
            this.visible = value;
            sendPacket(value ? UpdateAction.ADD : UpdateAction.REMOVE);
        }
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public UUID getId() {
        return this.uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public String getTitle() {
        return this.title;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public float getHealth() {
        return this.health;
    }

    @Override // com.viaversion.viaversion.api.legacy.bossbar.BossBar
    public BossStyle getStyle() {
        return this.style;
    }

    public Set<BossFlag> getFlags() {
        return this.flags;
    }

    private void sendPacket(UpdateAction action) {
        Iterator it = new ArrayList(this.connections.values()).iterator();
        while (it.hasNext()) {
            UserConnection conn = (UserConnection) it.next();
            PacketWrapper wrapper = getPacket(action, conn);
            sendPacketConnection(conn, wrapper);
        }
    }

    private void sendPacketConnection(UserConnection conn, PacketWrapper wrapper) {
        if (conn.getProtocolInfo() == null || !conn.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
            this.connections.remove(conn.getProtocolInfo().getUuid());
            return;
        }
        try {
            wrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PacketWrapper getPacket(UpdateAction action, UserConnection connection) {
        try {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, (ByteBuf) null, connection);
            wrapper.write(Type.UUID, this.uuid);
            wrapper.write(Type.VAR_INT, Integer.valueOf(action.getId()));
            switch (action) {
                case ADD:
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
                    wrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    wrapper.write(Type.VAR_INT, Integer.valueOf(this.color.getId()));
                    wrapper.write(Type.VAR_INT, Integer.valueOf(this.style.getId()));
                    wrapper.write(Type.BYTE, Byte.valueOf((byte) flagToBytes()));
                    break;
                case UPDATE_HEALTH:
                    wrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    break;
                case UPDATE_TITLE:
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
                    break;
                case UPDATE_STYLE:
                    wrapper.write(Type.VAR_INT, Integer.valueOf(this.color.getId()));
                    wrapper.write(Type.VAR_INT, Integer.valueOf(this.style.getId()));
                    break;
                case UPDATE_FLAGS:
                    wrapper.write(Type.BYTE, Byte.valueOf((byte) flagToBytes()));
                    break;
            }
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int flagToBytes() {
        int bitmask = 0;
        for (BossFlag flag : this.flags) {
            bitmask |= flag.getId();
        }
        return bitmask;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/legacy/bossbar/CommonBoss$UpdateAction.class */
    public enum UpdateAction {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);
        

        /* renamed from: id */
        private final int f61id;

        UpdateAction(int id) {
            this.f61id = id;
        }

        public int getId() {
            return this.f61id;
        }
    }
}
