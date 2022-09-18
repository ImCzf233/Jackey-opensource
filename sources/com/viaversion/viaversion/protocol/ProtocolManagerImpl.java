package com.viaversion.viaversion.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocol.packet.VersionedPacketTransformerImpl;
import com.viaversion.viaversion.protocols.base.BaseProtocol;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_16;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_7;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.Protocol1_11_1To1_11;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.Protocol1_12_1To1_12;
import com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1.Protocol1_12_2To1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14_2to1_14_1.Protocol1_14_2To1_14_1;
import com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2.Protocol1_14_3To1_14_2;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.Protocol1_14_4To1_14_3;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_15_1to1_15.Protocol1_15_1To1_15;
import com.viaversion.viaversion.protocols.protocol1_15_2to1_15_1.Protocol1_15_2To1_15_1;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_16_1to1_16.Protocol1_16_1To1_16;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_3to1_16_2.Protocol1_16_3To1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3.Protocol1_16_4To1_16_3;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_9_1.Protocol1_9To1_9_1;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ProtocolManagerImpl.class */
public class ProtocolManagerImpl implements ProtocolManager {
    private static final Protocol BASE_PROTOCOL = new BaseProtocol();
    private ThreadPoolExecutor mappingLoaderExecutor;
    private boolean mappingsLoaded;
    private final Int2ObjectMap<Int2ObjectMap<Protocol>> registryMap = new Int2ObjectOpenHashMap(32);
    private final Map<Class<? extends Protocol>, Protocol> protocols = new HashMap();
    private final Map<ProtocolPathKey, List<ProtocolPathEntry>> pathCache = new ConcurrentHashMap();
    private final Set<Integer> supportedVersions = new HashSet();
    private final List<Pair<Range<Integer>, Protocol>> baseProtocols = Lists.newCopyOnWriteArrayList();
    private final List<Protocol> registerList = new ArrayList();
    private final ReadWriteLock mappingLoaderLock = new ReentrantReadWriteLock();
    private Map<Class<? extends Protocol>, CompletableFuture<Void>> mappingLoaderFutures = new HashMap();
    private ServerProtocolVersion serverProtocolVersion = new ServerProtocolVersionSingleton(-1);
    private boolean onlyCheckLoweringPathEntries = true;
    private int maxProtocolPathSize = 50;

    public ProtocolManagerImpl() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Via-Mappingloader-%d").build();
        this.mappingLoaderExecutor = new ThreadPoolExecutor(5, 16, 45L, TimeUnit.SECONDS, new SynchronousQueue(), threadFactory);
        this.mappingLoaderExecutor.allowCoreThreadTimeOut(true);
    }

    public void registerProtocols() {
        registerBaseProtocol(BASE_PROTOCOL, Range.lessThan(Integer.MIN_VALUE));
        registerBaseProtocol(new BaseProtocol1_7(), Range.lessThan(Integer.valueOf(ProtocolVersion.v1_16.getVersion())));
        registerBaseProtocol(new BaseProtocol1_16(), Range.atLeast(Integer.valueOf(ProtocolVersion.v1_16.getVersion())));
        registerProtocol(new Protocol1_9To1_8(), ProtocolVersion.v1_9, ProtocolVersion.v1_8);
        registerProtocol(new Protocol1_9_1To1_9(), Arrays.asList(Integer.valueOf(ProtocolVersion.v1_9_1.getVersion()), Integer.valueOf(ProtocolVersion.v1_9_2.getVersion())), ProtocolVersion.v1_9.getVersion());
        registerProtocol(new Protocol1_9_3To1_9_1_2(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
        registerProtocol(new Protocol1_9To1_9_1(), ProtocolVersion.v1_9, ProtocolVersion.v1_9_1);
        registerProtocol(new Protocol1_9_1_2To1_9_3_4(), Arrays.asList(Integer.valueOf(ProtocolVersion.v1_9_1.getVersion()), Integer.valueOf(ProtocolVersion.v1_9_2.getVersion())), ProtocolVersion.v1_9_3.getVersion());
        registerProtocol(new Protocol1_10To1_9_3_4(), ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
        registerProtocol(new Protocol1_11To1_10(), ProtocolVersion.v1_11, ProtocolVersion.v1_10);
        registerProtocol(new Protocol1_11_1To1_11(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
        registerProtocol(new Protocol1_12To1_11_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
        registerProtocol(new Protocol1_12_1To1_12(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
        registerProtocol(new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
        registerProtocol(new Protocol1_13To1_12_2(), ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
        registerProtocol(new Protocol1_13_1To1_13(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
        registerProtocol(new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
        registerProtocol(new Protocol1_14To1_13_2(), ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
        registerProtocol(new Protocol1_14_1To1_14(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
        registerProtocol(new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
        registerProtocol(new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
        registerProtocol(new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
        registerProtocol(new Protocol1_15To1_14_4(), ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
        registerProtocol(new Protocol1_15_1To1_15(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
        registerProtocol(new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
        registerProtocol(new Protocol1_16To1_15_2(), ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
        registerProtocol(new Protocol1_16_1To1_16(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
        registerProtocol(new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
        registerProtocol(new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
        registerProtocol(new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
        registerProtocol(new Protocol1_17To1_16_4(), ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
        registerProtocol(new Protocol1_17_1To1_17(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_17);
        registerProtocol(new Protocol1_18To1_17_1(), ProtocolVersion.v1_18, ProtocolVersion.v1_17_1);
        registerProtocol(new Protocol1_18_2To1_18(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_18);
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void registerProtocol(Protocol protocol, ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
        registerProtocol(protocol, Collections.singletonList(Integer.valueOf(clientVersion.getVersion())), serverVersion.getVersion());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void registerProtocol(Protocol protocol, List<Integer> supportedClientVersion, int serverVersion) {
        protocol.initialize();
        if (!this.pathCache.isEmpty()) {
            this.pathCache.clear();
        }
        this.protocols.put(protocol.getClass(), protocol);
        for (Integer num : supportedClientVersion) {
            int clientVersion = num.intValue();
            Preconditions.checkArgument(clientVersion != serverVersion);
            Int2ObjectMap<Protocol> protocolMap = this.registryMap.computeIfAbsent(clientVersion, s -> {
                return new Int2ObjectOpenHashMap(2);
            });
            protocolMap.put(serverVersion, (int) protocol);
        }
        if (Via.getPlatform().isPluginEnabled()) {
            protocol.register(Via.getManager().getProviders());
            refreshVersions();
        } else {
            this.registerList.add(protocol);
        }
        if (protocol.hasMappingDataToLoad()) {
            if (this.mappingLoaderExecutor != null) {
                Class<?> cls = protocol.getClass();
                protocol.getClass();
                addMappingLoaderFuture(cls, this::loadMappingData);
                return;
            }
            protocol.loadMappingData();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void registerBaseProtocol(Protocol baseProtocol, Range<Integer> supportedProtocols) {
        Preconditions.checkArgument(baseProtocol.isBaseProtocol(), "Protocol is not a base protocol");
        baseProtocol.initialize();
        this.baseProtocols.add(new Pair<>(supportedProtocols, baseProtocol));
        if (Via.getPlatform().isPluginEnabled()) {
            baseProtocol.register(Via.getManager().getProviders());
            refreshVersions();
            return;
        }
        this.registerList.add(baseProtocol);
    }

    public void refreshVersions() {
        this.supportedVersions.clear();
        this.supportedVersions.add(Integer.valueOf(this.serverProtocolVersion.lowestSupportedVersion()));
        for (ProtocolVersion version : ProtocolVersion.getProtocols()) {
            List<ProtocolPathEntry> protocolPath = getProtocolPath(version.getVersion(), this.serverProtocolVersion.lowestSupportedVersion());
            if (protocolPath != null) {
                this.supportedVersions.add(Integer.valueOf(version.getVersion()));
                for (ProtocolPathEntry pathEntry : protocolPath) {
                    this.supportedVersions.add(Integer.valueOf(pathEntry.outputProtocolVersion()));
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public List<ProtocolPathEntry> getProtocolPath(int clientVersion, int serverVersion) {
        if (clientVersion == serverVersion) {
            return null;
        }
        ProtocolPathKey protocolKey = new ProtocolPathKeyImpl(clientVersion, serverVersion);
        List<ProtocolPathEntry> protocolList = this.pathCache.get(protocolKey);
        if (protocolList != null) {
            return protocolList;
        }
        Int2ObjectSortedMap<Protocol> outputPath = getProtocolPath(new Int2ObjectLinkedOpenHashMap(), clientVersion, serverVersion);
        if (outputPath == null) {
            return null;
        }
        List<ProtocolPathEntry> path = new ArrayList<>(outputPath.size());
        ObjectBidirectionalIterator<Int2ObjectMap.Entry<Protocol>> it = outputPath.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<Protocol> entry = it.next();
            path.add(new ProtocolPathEntryImpl(entry.getIntKey(), entry.getValue()));
        }
        this.pathCache.put(protocolKey, path);
        return path;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(ProtocolVersion inputVersion, Class<C> clientboundPacketsClass, Class<S> serverboundPacketsClass) {
        Preconditions.checkArgument((clientboundPacketsClass == ClientboundPacketType.class || serverboundPacketsClass == ServerboundPacketType.class) ? false : true);
        return new VersionedPacketTransformerImpl(inputVersion, clientboundPacketsClass, serverboundPacketsClass);
    }

    private Int2ObjectSortedMap<Protocol> getProtocolPath(Int2ObjectSortedMap<Protocol> current, int clientVersion, int serverVersion) {
        Int2ObjectMap<Protocol> toServerProtocolMap;
        if (current.size() > this.maxProtocolPathSize || (toServerProtocolMap = this.registryMap.get(clientVersion)) == null) {
            return null;
        }
        Protocol protocol = toServerProtocolMap.get(serverVersion);
        if (protocol != null) {
            current.put(serverVersion, (int) protocol);
            return current;
        }
        Int2ObjectSortedMap<Protocol> shortest = null;
        ObjectIterator<Int2ObjectMap.Entry<Protocol>> it = toServerProtocolMap.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<Protocol> entry = it.next();
            int translatedToVersion = entry.getIntKey();
            if (!current.containsKey(translatedToVersion) && (!this.onlyCheckLoweringPathEntries || Math.abs(serverVersion - translatedToVersion) <= Math.abs(serverVersion - clientVersion))) {
                Int2ObjectSortedMap<Protocol> newCurrent = new Int2ObjectLinkedOpenHashMap<>((Int2ObjectMap<Protocol>) current);
                newCurrent.put(translatedToVersion, (int) entry.getValue());
                Int2ObjectSortedMap<Protocol> newCurrent2 = getProtocolPath(newCurrent, translatedToVersion, serverVersion);
                if (newCurrent2 != null && (shortest == null || newCurrent2.size() < shortest.size())) {
                    shortest = newCurrent2;
                }
            }
        }
        return shortest;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public <T extends Protocol> T getProtocol(Class<T> protocolClass) {
        return (T) this.protocols.get(protocolClass);
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public Protocol getProtocol(int clientVersion, int serverVersion) {
        Int2ObjectMap<Protocol> map = this.registryMap.get(clientVersion);
        if (map != null) {
            return map.get(serverVersion);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public Protocol getBaseProtocol(int serverVersion) {
        for (Pair<Range<Integer>, Protocol> rangeProtocol : Lists.reverse(this.baseProtocols)) {
            if (rangeProtocol.key().contains(Integer.valueOf(serverVersion))) {
                return rangeProtocol.value();
            }
        }
        throw new IllegalStateException("No Base Protocol for " + serverVersion);
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public ServerProtocolVersion getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public void setServerProtocol(ServerProtocolVersion serverProtocolVersion) {
        this.serverProtocolVersion = serverProtocolVersion;
        ProtocolRegistry.SERVER_PROTOCOL = serverProtocolVersion.lowestSupportedVersion();
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public boolean isWorkingPipe() {
        ObjectIterator<Int2ObjectMap<Protocol>> it = this.registryMap.values().iterator();
        while (it.hasNext()) {
            Int2ObjectMap<Protocol> map = it.next();
            Iterator<Integer> it2 = this.serverProtocolVersion.supportedVersions().iterator();
            while (it2.hasNext()) {
                int protocolVersion = it2.next().intValue();
                if (map.containsKey(protocolVersion)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public SortedSet<Integer> getSupportedVersions() {
        return Collections.unmodifiableSortedSet(new TreeSet(this.supportedVersions));
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void setOnlyCheckLoweringPathEntries(boolean onlyCheckLoweringPathEntries) {
        this.onlyCheckLoweringPathEntries = onlyCheckLoweringPathEntries;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public boolean onlyCheckLoweringPathEntries() {
        return this.onlyCheckLoweringPathEntries;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public int getMaxProtocolPathSize() {
        return this.maxProtocolPathSize;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void setMaxProtocolPathSize(int maxProtocolPathSize) {
        this.maxProtocolPathSize = maxProtocolPathSize;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public Protocol getBaseProtocol() {
        return BASE_PROTOCOL;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void completeMappingDataLoading(Class<? extends Protocol> protocolClass) throws Exception {
        CompletableFuture<Void> future;
        if (!this.mappingsLoaded && (future = getMappingLoaderFuture(protocolClass)) != null) {
            future.get();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public boolean checkForMappingCompletion() {
        this.mappingLoaderLock.readLock().lock();
        try {
            if (this.mappingsLoaded) {
                return false;
            }
            for (CompletableFuture<Void> future : this.mappingLoaderFutures.values()) {
                if (!future.isDone()) {
                    this.mappingLoaderLock.readLock().unlock();
                    return false;
                }
            }
            shutdownLoaderExecutor();
            this.mappingLoaderLock.readLock().unlock();
            return true;
        } finally {
            this.mappingLoaderLock.readLock().unlock();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Runnable runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, this.mappingLoaderExecutor).exceptionally((Function<Throwable, ? extends Void>) mappingLoaderThrowable(protocolClass));
        this.mappingLoaderLock.writeLock().lock();
        try {
            this.mappingLoaderFutures.put(protocolClass, future);
            this.mappingLoaderLock.writeLock().unlock();
        } catch (Throwable th) {
            this.mappingLoaderLock.writeLock().unlock();
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Class<? extends Protocol> dependsOn, Runnable runnable) {
        CompletableFuture<Void> future = getMappingLoaderFuture(dependsOn).whenCompleteAsync(v, throwable -> {
            runnable.run();
        }, (Executor) this.mappingLoaderExecutor).exceptionally((Function<Throwable, ? extends Void>) mappingLoaderThrowable(protocolClass));
        this.mappingLoaderLock.writeLock().lock();
        try {
            this.mappingLoaderFutures.put(protocolClass, future);
            this.mappingLoaderLock.writeLock().unlock();
        } catch (Throwable th) {
            this.mappingLoaderLock.writeLock().unlock();
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> protocolClass) {
        this.mappingLoaderLock.readLock().lock();
        try {
            return this.mappingsLoaded ? null : this.mappingLoaderFutures.get(protocolClass);
        } finally {
            this.mappingLoaderLock.readLock().unlock();
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    public PacketWrapper createPacketWrapper(PacketType packetType, ByteBuf buf, UserConnection connection) {
        return new PacketWrapperImpl(packetType, buf, connection);
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolManager
    @Deprecated
    public PacketWrapper createPacketWrapper(int packetId, ByteBuf buf, UserConnection connection) {
        return new PacketWrapperImpl(packetId, buf, connection);
    }

    public void onServerLoaded() {
        for (Protocol protocol : this.registerList) {
            protocol.register(Via.getManager().getProviders());
        }
        this.registerList.clear();
    }

    private void shutdownLoaderExecutor() {
        Preconditions.checkArgument(!this.mappingsLoaded);
        Via.getPlatform().getLogger().info("Finished mapping loading, shutting down loader executor!");
        this.mappingsLoaded = true;
        this.mappingLoaderExecutor.shutdown();
        this.mappingLoaderExecutor = null;
        this.mappingLoaderFutures.clear();
        this.mappingLoaderFutures = null;
        if (MappingDataLoader.isCacheJsonMappings()) {
            MappingDataLoader.getMappingsCache().clear();
        }
    }

    private Function<Throwable, Void> mappingLoaderThrowable(Class<? extends Protocol> protocolClass) {
        return throwable -> {
            Via.getPlatform().getLogger().severe("Error during mapping loading of " + protocolClass.getSimpleName());
            throwable.printStackTrace();
            return null;
        };
    }
}
