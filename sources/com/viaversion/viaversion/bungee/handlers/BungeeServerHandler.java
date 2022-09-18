package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.score.Team;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.PluginMessage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/handlers/BungeeServerHandler.class */
public class BungeeServerHandler implements Listener {
    private static Method getHandshake;
    private static Method getRegisteredChannels;
    private static Method getBrandMessage;
    private static Method setProtocol;
    private static Method getEntityMap;
    private static Method setVersion;
    private static Field entityRewrite;
    private static Field channelWrapper;

    static {
        getEntityMap = null;
        setVersion = null;
        entityRewrite = null;
        channelWrapper = null;
        try {
            getHandshake = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getHandshake", new Class[0]);
            getRegisteredChannels = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRegisteredChannels", new Class[0]);
            getBrandMessage = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getBrandMessage", new Class[0]);
            setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", Integer.TYPE);
            getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", Integer.TYPE);
            setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", Integer.TYPE);
            channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch");
            channelWrapper.setAccessible(true);
            entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite");
            entityRewrite.setAccessible(true);
        } catch (Exception e) {
            Via.getPlatform().getLogger().severe("Error initializing BungeeServerHandler, try updating BungeeCord or ViaVersion!");
            e.printStackTrace();
        }
    }

    @EventHandler(priority = 120)
    public void onServerConnect(ServerConnectEvent e) {
        UserConnection user;
        if (!e.isCancelled() && (user = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId())) != null) {
            if (!user.has(BungeeStorage.class)) {
                user.put(new BungeeStorage(e.getPlayer()));
            }
            int protocolId = ProtocolDetectorService.getProtocolId(e.getTarget().getName()).intValue();
            List<ProtocolPathEntry> protocols = Via.getManager().getProtocolManager().getProtocolPath(user.getProtocolInfo().getProtocolVersion(), protocolId);
            try {
                Object handshake = getHandshake.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
                Method method = setProtocol;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(protocols == null ? user.getProtocolInfo().getProtocolVersion() : protocolId);
                method.invoke(handshake, objArr);
            } catch (IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    @EventHandler(priority = -120)
    public void onServerConnected(ServerConnectedEvent e) {
        try {
            checkServerChange(e, Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @EventHandler(priority = -120)
    public void onServerSwitch(ServerSwitchEvent e) {
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(e.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        try {
            int playerId = ((EntityIdProvider) Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(userConnection);
            for (EntityTracker tracker : userConnection.getEntityTrackers()) {
                tracker.setClientEntityId(playerId);
            }
            for (StorableObject object : userConnection.getStoredObjects().values()) {
                if (object instanceof ClientEntityIdChangeListener) {
                    ((ClientEntityIdChangeListener) object).setClientEntityId(playerId);
                }
            }
        } catch (Exception e2) {
        }
    }

    public void checkServerChange(ServerConnectedEvent e, UserConnection user) throws Exception {
        String channel;
        String channel2;
        if (user != null && user.has(BungeeStorage.class)) {
            BungeeStorage storage = (BungeeStorage) user.get(BungeeStorage.class);
            ProxiedPlayer player = storage.getPlayer();
            if (e.getServer() != null && !e.getServer().getInfo().getName().equals(storage.getCurrentServer())) {
                EntityTracker1_9 oldEntityTracker = (EntityTracker1_9) user.getEntityTracker(Protocol1_9To1_8.class);
                if (oldEntityTracker != null && oldEntityTracker.isAutoTeam() && oldEntityTracker.isTeamExists()) {
                    oldEntityTracker.sendTeamPacket(false, true);
                }
                String serverName = e.getServer().getInfo().getName();
                storage.setCurrentServer(serverName);
                int protocolId = ProtocolDetectorService.getProtocolId(serverName).intValue();
                if (protocolId <= ProtocolVersion.v1_8.getVersion() && storage.getBossbar() != null) {
                    if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                        for (UUID uuid : storage.getBossbar()) {
                            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, (ByteBuf) null, user);
                            wrapper.write(Type.UUID, uuid);
                            wrapper.write(Type.VAR_INT, 1);
                            wrapper.send(Protocol1_9To1_8.class);
                        }
                    }
                    storage.getBossbar().clear();
                }
                ProtocolInfo info = user.getProtocolInfo();
                int previousServerProtocol = info.getServerProtocolVersion();
                List<ProtocolPathEntry> protocolPath = Via.getManager().getProtocolManager().getProtocolPath(info.getProtocolVersion(), protocolId);
                ProtocolPipeline pipeline = user.getProtocolInfo().getPipeline();
                user.clearStoredObjects();
                pipeline.cleanPipes();
                if (protocolPath == null) {
                    protocolId = info.getProtocolVersion();
                } else {
                    List<Protocol> protocols = new ArrayList<>(protocolPath.size());
                    for (ProtocolPathEntry entry : protocolPath) {
                        protocols.add(entry.protocol());
                    }
                    pipeline.add(protocols);
                }
                info.setServerProtocolVersion(protocolId);
                pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(protocolId));
                int id1_13 = ProtocolVersion.v1_13.getVersion();
                boolean toNewId = previousServerProtocol < id1_13 && protocolId >= id1_13;
                boolean toOldId = previousServerProtocol >= id1_13 && protocolId < id1_13;
                if (previousServerProtocol != -1 && (toNewId || toOldId)) {
                    Collection<String> registeredChannels = (Collection) getRegisteredChannels.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
                    if (!registeredChannels.isEmpty()) {
                        Collection<String> newChannels = new HashSet<>();
                        Iterator<String> iterator = registeredChannels.iterator();
                        while (iterator.hasNext()) {
                            String channel3 = iterator.next();
                            if (toNewId) {
                                channel2 = InventoryPackets.getNewPluginChannelId(channel3);
                            } else {
                                channel2 = InventoryPackets.getOldPluginChannelId(channel3);
                            }
                            if (channel2 == null) {
                                iterator.remove();
                            } else if (!channel3.equals(channel2)) {
                                iterator.remove();
                                newChannels.add(channel2);
                            }
                        }
                        registeredChannels.addAll(newChannels);
                    }
                    PluginMessage brandMessage = (PluginMessage) getBrandMessage.invoke(e.getPlayer().getPendingConnection(), new Object[0]);
                    if (brandMessage != null) {
                        String channel4 = brandMessage.getTag();
                        if (toNewId) {
                            channel = InventoryPackets.getNewPluginChannelId(channel4);
                        } else {
                            channel = InventoryPackets.getOldPluginChannelId(channel4);
                        }
                        if (channel != null) {
                            brandMessage.setTag(channel);
                        }
                    }
                }
                user.put(storage);
                user.setActive(protocolPath != null);
                for (Protocol protocol : pipeline.pipes()) {
                    protocol.init(user);
                }
                EntityTracker1_9 newTracker = (EntityTracker1_9) user.getEntityTracker(Protocol1_9To1_8.class);
                if (newTracker != null && Via.getConfig().isAutoTeam()) {
                    String currentTeam = null;
                    for (Team team : player.getScoreboard().getTeams()) {
                        if (team.getPlayers().contains(info.getUsername())) {
                            currentTeam = team.getName();
                        }
                    }
                    newTracker.setAutoTeam(true);
                    if (currentTeam == null) {
                        newTracker.sendTeamPacket(true, true);
                        newTracker.setCurrentTeam("viaversion");
                    } else {
                        newTracker.setAutoTeam(Via.getConfig().isAutoTeam());
                        newTracker.setCurrentTeam(currentTeam);
                    }
                }
                setVersion.invoke(channelWrapper.get(player), Integer.valueOf(protocolId));
                Object entityMap = getEntityMap.invoke(null, Integer.valueOf(protocolId));
                entityRewrite.set(player, entityMap);
            }
        }
    }
}
