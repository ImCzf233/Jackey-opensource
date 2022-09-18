package com.viaversion.viaversion.protocol;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ProtocolPipelineImpl.class */
public class ProtocolPipelineImpl extends AbstractSimpleProtocol implements ProtocolPipeline {
    private final UserConnection userConnection;
    private List<Protocol> protocolList;
    private Set<Class<? extends Protocol>> protocolSet;

    public ProtocolPipelineImpl(UserConnection userConnection) {
        this.userConnection = userConnection;
        userConnection.getProtocolInfo().setPipeline(this);
        registerPackets();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.protocolList = new CopyOnWriteArrayList();
        this.protocolSet = Sets.newSetFromMap(new ConcurrentHashMap());
        Protocol baseProtocol = Via.getManager().getProtocolManager().getBaseProtocol();
        this.protocolList.add(baseProtocol);
        this.protocolSet.add(baseProtocol.getClass());
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public void add(Protocol protocol) {
        this.protocolList.add(protocol);
        this.protocolSet.add(protocol.getClass());
        protocol.init(this.userConnection);
        if (!protocol.isBaseProtocol()) {
            moveBaseProtocolsToTail();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public void add(Collection<Protocol> protocols) {
        this.protocolList.addAll(protocols);
        for (Protocol protocol : protocols) {
            protocol.init(this.userConnection);
            this.protocolSet.add(protocol.getClass());
        }
        moveBaseProtocolsToTail();
    }

    private void moveBaseProtocolsToTail() {
        List<Protocol> baseProtocols = null;
        for (Protocol protocol : this.protocolList) {
            if (protocol.isBaseProtocol()) {
                if (baseProtocols == null) {
                    baseProtocols = new ArrayList<>();
                }
                baseProtocols.add(protocol);
            }
        }
        if (baseProtocols != null) {
            this.protocolList.removeAll(baseProtocols);
            this.protocolList.addAll(baseProtocols);
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol, com.viaversion.viaversion.api.protocol.Protocol
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int originalID = packetWrapper.getId();
        packetWrapper.apply(direction, state, 0, this.protocolList, direction == Direction.CLIENTBOUND);
        super.transform(direction, state, packetWrapper);
        DebugHandler debugHandler = Via.getManager().debugHandler();
        if (debugHandler.enabled() && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper)) {
            logPacket(direction, state, packetWrapper, originalID);
        }
    }

    private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
        int clientProtocol = this.userConnection.getProtocolInfo().getProtocolVersion();
        ViaPlatform platform = Via.getPlatform();
        String actualUsername = packetWrapper.user().getProtocolInfo().getUsername();
        String username = actualUsername != null ? actualUsername + " " : "";
        platform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} (0x{4}) -> {5} (0x{6}) [{7}] {8}", new Object[]{username, direction, state, Integer.valueOf(originalID), Integer.toHexString(originalID), Integer.valueOf(packetWrapper.getId()), Integer.toHexString(packetWrapper.getId()), Integer.toString(clientProtocol), packetWrapper});
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public boolean contains(Class<? extends Protocol> protocolClass) {
        return this.protocolSet.contains(protocolClass);
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public <P extends Protocol> P getProtocol(Class<P> pipeClass) {
        Iterator<Protocol> it = this.protocolList.iterator();
        while (it.hasNext()) {
            P p = (P) it.next();
            if (p.getClass() == pipeClass) {
                return p;
            }
        }
        return null;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public List<Protocol> pipes() {
        return this.protocolList;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public boolean hasNonBaseProtocols() {
        for (Protocol protocol : this.protocolList) {
            if (!protocol.isBaseProtocol()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPipeline
    public void cleanPipes() {
        registerPackets();
    }
}
