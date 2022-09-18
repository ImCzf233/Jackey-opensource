package com.viaversion.viaversion.connection;

import com.google.common.cache.CacheBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/connection/UserConnectionImpl.class */
public class UserConnectionImpl implements UserConnection {
    private static final AtomicLong IDS = new AtomicLong();

    /* renamed from: id */
    private final long f59id;
    private final Map<Class<?>, StorableObject> storedObjects;
    private final Map<Class<? extends Protocol>, EntityTracker> entityTrackers;
    private final PacketTracker packetTracker;
    private final Set<UUID> passthroughTokens;
    private final ProtocolInfo protocolInfo;
    private final Channel channel;
    private final boolean clientSide;
    private boolean active;
    private boolean pendingDisconnect;
    private boolean packetLimiterEnabled;

    public UserConnectionImpl(Channel channel, boolean clientSide) {
        this.f59id = IDS.incrementAndGet();
        this.storedObjects = new ConcurrentHashMap();
        this.entityTrackers = new HashMap();
        this.packetTracker = new PacketTracker(this);
        this.passthroughTokens = Collections.newSetFromMap(CacheBuilder.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build().asMap());
        this.protocolInfo = new ProtocolInfoImpl(this);
        this.active = true;
        this.packetLimiterEnabled = true;
        this.channel = channel;
        this.clientSide = clientSide;
    }

    public UserConnectionImpl(Channel channel) {
        this(channel, false);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public <T extends StorableObject> T get(Class<T> objectClass) {
        return (T) this.storedObjects.get(objectClass);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public boolean has(Class<? extends StorableObject> objectClass) {
        return this.storedObjects.containsKey(objectClass);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public void put(StorableObject object) {
        this.storedObjects.put(object.getClass(), object);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public Collection<EntityTracker> getEntityTrackers() {
        return this.entityTrackers.values();
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public <T extends EntityTracker> T getEntityTracker(Class<? extends Protocol> protocolClass) {
        return (T) this.entityTrackers.get(protocolClass);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public void addEntityTracker(Class<? extends Protocol> protocolClass, EntityTracker tracker) {
        this.entityTrackers.put(protocolClass, tracker);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public void clearStoredObjects() {
        this.storedObjects.clear();
        this.entityTrackers.clear();
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public void sendRawPacket(ByteBuf packet) {
        sendRawPacket(packet, true);
    }

    @Override // com.viaversion.viaversion.api.connection.UserConnection
    public void scheduleSendRawPacket(ByteBuf packet) {
        sendRawPacket(packet, false);
    }

    private void sendRawPacket(ByteBuf packet, boolean currentThread) {
        Runnable act;
        if (this.clientSide) {
            act = ()
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000e: INVOKE_CUSTOM (r6v2 'act' java.lang.Runnable) = 
                  (r3v0 'this' com.viaversion.viaversion.connection.UserConnectionImpl A[D('this' com.viaversion.viaversion.connection.UserConnectionImpl), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                  (r4v0 'packet' io.netty.buffer.ByteBuf A[D('packet' io.netty.buffer.ByteBuf), DONT_INLINE])
                
                 handle type: INVOKE_DIRECT
                 lambda: java.lang.Runnable.run():void
                 call insn: ?: INVOKE  (r0 I:com.viaversion.viaversion.connection.UserConnectionImpl), (r1 I:io.netty.buffer.ByteBuf) type: DIRECT call: com.viaversion.viaversion.connection.UserConnectionImpl.lambda$sendRawPacket$0(io.netty.buffer.ByteBuf):void in method: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacket(io.netty.buffer.ByteBuf, boolean):void, file: Jackey Client b2.jar:com/viaversion/viaversion/connection/UserConnectionImpl.class
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                	at java.base/java.util.Objects.checkIndex(Unknown Source)
                	at java.base/java.util.ArrayList.get(Unknown Source)
                	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                	... 21 more
                */
            /*
                this = this;
                r0 = r3
                boolean r0 = r0.clientSide
                if (r0 == 0) goto L12
                r0 = r3
                r1 = r4
                void r0 = () -> { // java.lang.Runnable.run():void
                    r0.lambda$sendRawPacket$0(r1);
                }
                r6 = r0
                goto L1a
            L12:
                r0 = r3
                r1 = r4
                void r0 = () -> { // java.lang.Runnable.run():void
                    r0.lambda$sendRawPacket$1(r1);
                }
                r6 = r0
            L1a:
                r0 = r5
                if (r0 == 0) goto L27
                r0 = r6
                r0.run()
                goto L46
            L27:
                r0 = r3
                io.netty.channel.Channel r0 = r0.channel     // Catch: java.lang.Throwable -> L3a
                io.netty.channel.EventLoop r0 = r0.eventLoop()     // Catch: java.lang.Throwable -> L3a
                r1 = r6
                io.netty.util.concurrent.Future r0 = r0.submit(r1)     // Catch: java.lang.Throwable -> L3a
                goto L46
            L3a:
                r7 = move-exception
                r0 = r4
                boolean r0 = r0.release()
                r0 = r7
                r0.printStackTrace()
            L46:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacket(io.netty.buffer.ByteBuf, boolean):void");
        }

        @Override // com.viaversion.viaversion.api.connection.UserConnection
        public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
            if (this.clientSide) {
                getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(packet);
                return getChannel().newSucceededFuture();
            }
            return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(packet);
        }

        @Override // com.viaversion.viaversion.api.connection.UserConnection
        public PacketTracker getPacketTracker() {
            return this.packetTracker;
        }

        @Override // com.viaversion.viaversion.api.connection.UserConnection
        public void disconnect(String reason) {
            if (!this.channel.isOpen() || this.pendingDisconnect) {
                return;
            }
            this.pendingDisconnect = true;
            Via.getPlatform().runSync(()
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0023: INVOKE  
                  (wrap: com.viaversion.viaversion.api.platform.ViaPlatform : 0x0019: INVOKE  (r0v6 com.viaversion.viaversion.api.platform.ViaPlatform A[REMOVE]) =  type: STATIC call: com.viaversion.viaversion.api.Via.getPlatform():com.viaversion.viaversion.api.platform.ViaPlatform)
                  (wrap: java.lang.Runnable : 0x001e: INVOKE_CUSTOM (r1v2 java.lang.Runnable A[REMOVE]) = 
                  (r4v0 'this' com.viaversion.viaversion.connection.UserConnectionImpl A[D('this' com.viaversion.viaversion.connection.UserConnectionImpl), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                  (r5v0 'reason' java.lang.String A[D('reason' java.lang.String), DONT_INLINE])
                
                 handle type: INVOKE_DIRECT
                 lambda: java.lang.Runnable.run():void
                 call insn: ?: INVOKE  (r1 I:com.viaversion.viaversion.connection.UserConnectionImpl), (r2 I:java.lang.String) type: DIRECT call: com.viaversion.viaversion.connection.UserConnectionImpl.lambda$disconnect$2(java.lang.String):void)
                 type: INTERFACE call: com.viaversion.viaversion.api.platform.ViaPlatform.runSync(java.lang.Runnable):com.viaversion.viaversion.api.platform.PlatformTask in method: com.viaversion.viaversion.connection.UserConnectionImpl.disconnect(java.lang.String):void, file: Jackey Client b2.jar:com/viaversion/viaversion/connection/UserConnectionImpl.class
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                	at java.base/java.util.Objects.checkIndex(Unknown Source)
                	at java.base/java.util.ArrayList.get(Unknown Source)
                	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                	... 19 more
                */
            /*
                this = this;
                r0 = r4
                io.netty.channel.Channel r0 = r0.channel
                boolean r0 = r0.isOpen()
                if (r0 == 0) goto L13
                r0 = r4
                boolean r0 = r0.pendingDisconnect
                if (r0 == 0) goto L14
            L13:
                return
            L14:
                r0 = r4
                r1 = 1
                r0.pendingDisconnect = r1
                com.viaversion.viaversion.api.platform.ViaPlatform r0 = com.viaversion.viaversion.api.Via.getPlatform()
                r1 = r4
                r2 = r5
                void r1 = () -> { // java.lang.Runnable.run():void
                    r1.lambda$disconnect$2(r2);
                }
                com.viaversion.viaversion.api.platform.PlatformTask r0 = r0.runSync(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.connection.UserConnectionImpl.disconnect(java.lang.String):void");
        }

        @Override // com.viaversion.viaversion.api.connection.UserConnection
        public void sendRawPacketToServer(ByteBuf packet) {
            if (this.clientSide) {
                sendRawPacketToServerClientSide(packet, true);
            } else {
                sendRawPacketToServerServerSide(packet, true);
            }
        }

        @Override // com.viaversion.viaversion.api.connection.UserConnection
        public void scheduleSendRawPacketToServer(ByteBuf packet) {
            if (this.clientSide) {
                sendRawPacketToServerClientSide(packet, false);
            } else {
                sendRawPacketToServerServerSide(packet, false);
            }
        }

        private void sendRawPacketToServerServerSide(ByteBuf packet, boolean currentThread) {
            ByteBuf buf = packet.alloc().buffer();
            try {
                ChannelHandlerContext context = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
                if (shouldTransformPacket()) {
                    try {
                        Type.VAR_INT.writePrimitive(buf, 1000);
                        Type.UUID.write(buf, generatePassthroughToken());
                    } catch (Exception shouldNotHappen) {
                        throw new RuntimeException(shouldNotHappen);
                    }
                }
                buf.writeBytes(packet);
                Runnable act = ()
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x005a: INVOKE_CUSTOM (r0v18 'act' java.lang.Runnable) = 
                      (r4v0 'this' com.viaversion.viaversion.connection.UserConnectionImpl A[D('this' com.viaversion.viaversion.connection.UserConnectionImpl), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                      (r0v9 'context' io.netty.channel.ChannelHandlerContext A[D('context' io.netty.channel.ChannelHandlerContext), DONT_INLINE])
                      (r0v2 'buf' io.netty.buffer.ByteBuf A[D('buf' io.netty.buffer.ByteBuf), DONT_INLINE])
                    
                     handle type: INVOKE_DIRECT
                     lambda: java.lang.Runnable.run():void
                     call insn: ?: INVOKE  
                      (r0 I:com.viaversion.viaversion.connection.UserConnectionImpl)
                      (r1 I:io.netty.channel.ChannelHandlerContext)
                      (r2 I:io.netty.buffer.ByteBuf)
                     type: DIRECT call: com.viaversion.viaversion.connection.UserConnectionImpl.lambda$sendRawPacketToServerServerSide$3(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf):void in method: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacketToServerServerSide(io.netty.buffer.ByteBuf, boolean):void, file: Jackey Client b2.jar:com/viaversion/viaversion/connection/UserConnectionImpl.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:302)
                    	at jadx.core.dex.regions.TryCatchRegion.generate(TryCatchRegion.java:85)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                    	at java.base/java.util.ArrayList.forEach(Unknown Source)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                    Caused by: java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 2
                    	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                    	at java.base/java.util.Objects.checkIndex(Unknown Source)
                    	at java.base/java.util.ArrayList.get(Unknown Source)
                    	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                    	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 21 more
                    */
                /*
                    this = this;
                    r0 = r5
                    io.netty.buffer.ByteBufAllocator r0 = r0.alloc()
                    io.netty.buffer.ByteBuf r0 = r0.buffer()
                    r7 = r0
                    com.viaversion.viaversion.api.ViaManager r0 = com.viaversion.viaversion.api.Via.getManager()     // Catch: java.lang.Throwable -> L95
                    com.viaversion.viaversion.api.platform.ViaInjector r0 = r0.getInjector()     // Catch: java.lang.Throwable -> L95
                    java.lang.String r0 = r0.getDecoderName()     // Catch: java.lang.Throwable -> L95
                    r1 = r4
                    io.netty.channel.Channel r1 = r1.channel     // Catch: java.lang.Throwable -> L95
                    io.netty.channel.ChannelPipeline r1 = r1.pipeline()     // Catch: java.lang.Throwable -> L95
                    io.netty.channel.ChannelHandlerContext r0 = com.viaversion.viaversion.util.PipelineUtil.getPreviousContext(r0, r1)     // Catch: java.lang.Throwable -> L95
                    r8 = r0
                    r0 = r4
                    boolean r0 = r0.shouldTransformPacket()     // Catch: java.lang.Throwable -> L95
                    if (r0 == 0) goto L50
                    com.viaversion.viaversion.api.type.types.VarIntType r0 = com.viaversion.viaversion.api.type.Type.VAR_INT     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L95
                    r1 = r7
                    r2 = 1000(0x3e8, float:1.401E-42)
                    r0.writePrimitive(r1, r2)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L95
                    com.viaversion.viaversion.api.type.Type<java.util.UUID> r0 = com.viaversion.viaversion.api.type.Type.UUID     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L95
                    r1 = r7
                    r2 = r4
                    java.util.UUID r2 = r2.generatePassthroughToken()     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L95
                    r0.write(r1, r2)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L95
                    goto L50
                L44:
                    r9 = move-exception
                    java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L95
                    r1 = r0
                    r2 = r9
                    r1.<init>(r2)     // Catch: java.lang.Throwable -> L95
                    throw r0     // Catch: java.lang.Throwable -> L95
                L50:
                    r0 = r7
                    r1 = r5
                    io.netty.buffer.ByteBuf r0 = r0.writeBytes(r1)     // Catch: java.lang.Throwable -> L95
                    r0 = r4
                    r1 = r8
                    r2 = r7
                    void r0 = () -> { // java.lang.Runnable.run():void
                        r0.lambda$sendRawPacketToServerServerSide$3(r1, r2);
                    }     // Catch: java.lang.Throwable -> L95
                    r9 = r0
                    r0 = r6
                    if (r0 == 0) goto L6f
                    r0 = r9
                    r0.run()     // Catch: java.lang.Throwable -> L95
                    goto L8d
                L6f:
                    r0 = r4
                    io.netty.channel.Channel r0 = r0.channel     // Catch: java.lang.Throwable -> L83 java.lang.Throwable -> L95
                    io.netty.channel.EventLoop r0 = r0.eventLoop()     // Catch: java.lang.Throwable -> L83 java.lang.Throwable -> L95
                    r1 = r9
                    io.netty.util.concurrent.Future r0 = r0.submit(r1)     // Catch: java.lang.Throwable -> L83 java.lang.Throwable -> L95
                    goto L8d
                L83:
                    r10 = move-exception
                    r0 = r7
                    boolean r0 = r0.release()     // Catch: java.lang.Throwable -> L95
                    r0 = r10
                    throw r0     // Catch: java.lang.Throwable -> L95
                L8d:
                    r0 = r5
                    boolean r0 = r0.release()
                    goto L9f
                L95:
                    r11 = move-exception
                    r0 = r5
                    boolean r0 = r0.release()
                    r0 = r11
                    throw r0
                L9f:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacketToServerServerSide(io.netty.buffer.ByteBuf, boolean):void");
            }

            private void sendRawPacketToServerClientSide(ByteBuf packet, boolean currentThread) {
                Runnable act = ()
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: INVOKE_CUSTOM (r0v1 'act' java.lang.Runnable) = 
                      (r3v0 'this' com.viaversion.viaversion.connection.UserConnectionImpl A[D('this' com.viaversion.viaversion.connection.UserConnectionImpl), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                      (r4v0 'packet' io.netty.buffer.ByteBuf A[D('packet' io.netty.buffer.ByteBuf), DONT_INLINE])
                    
                     handle type: INVOKE_DIRECT
                     lambda: java.lang.Runnable.run():void
                     call insn: ?: INVOKE  (r0 I:com.viaversion.viaversion.connection.UserConnectionImpl), (r1 I:io.netty.buffer.ByteBuf) type: DIRECT call: com.viaversion.viaversion.connection.UserConnectionImpl.lambda$sendRawPacketToServerClientSide$4(io.netty.buffer.ByteBuf):void in method: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacketToServerClientSide(io.netty.buffer.ByteBuf, boolean):void, file: Jackey Client b2.jar:com/viaversion/viaversion/connection/UserConnectionImpl.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                    	at java.base/java.util.ArrayList.forEach(Unknown Source)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                    Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                    	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                    	at java.base/java.util.Objects.checkIndex(Unknown Source)
                    	at java.base/java.util.ArrayList.get(Unknown Source)
                    	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                    	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 15 more
                    */
                /*
                    this = this;
                    r0 = r3
                    r1 = r4
                    void r0 = () -> { // java.lang.Runnable.run():void
                        r0.lambda$sendRawPacketToServerClientSide$4(r1);
                    }
                    r6 = r0
                    r0 = r5
                    if (r0 == 0) goto L15
                    r0 = r6
                    r0.run()
                    goto L34
                L15:
                    r0 = r3
                    io.netty.channel.Channel r0 = r0.getChannel()     // Catch: java.lang.Throwable -> L28
                    io.netty.channel.EventLoop r0 = r0.eventLoop()     // Catch: java.lang.Throwable -> L28
                    r1 = r6
                    io.netty.util.concurrent.Future r0 = r0.submit(r1)     // Catch: java.lang.Throwable -> L28
                    goto L34
                L28:
                    r7 = move-exception
                    r0 = r7
                    r0.printStackTrace()
                    r0 = r4
                    boolean r0 = r0.release()
                L34:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.connection.UserConnectionImpl.sendRawPacketToServerClientSide(io.netty.buffer.ByteBuf, boolean):void");
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean checkServerboundPacket() {
                if (this.pendingDisconnect) {
                    return false;
                }
                return !this.packetLimiterEnabled || !this.packetTracker.incrementReceived() || !this.packetTracker.exceedsMaxPPS();
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean checkClientboundPacket() {
                this.packetTracker.incrementSent();
                return true;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean shouldTransformPacket() {
                return this.active;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public void transformClientbound(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
                transform(buf, Direction.CLIENTBOUND, cancelSupplier);
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public void transformServerbound(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
                transform(buf, Direction.SERVERBOUND, cancelSupplier);
            }

            private void transform(ByteBuf buf, Direction direction, Function<Throwable, Exception> cancelSupplier) throws Exception {
                if (!buf.isReadable()) {
                    return;
                }
                int id = Type.VAR_INT.readPrimitive(buf);
                if (id == 1000) {
                    if (!this.passthroughTokens.remove(Type.UUID.read(buf))) {
                        throw new IllegalArgumentException("Invalid token");
                    }
                    return;
                }
                PacketWrapper wrapper = new PacketWrapperImpl(id, buf, this);
                try {
                    this.protocolInfo.getPipeline().transform(direction, this.protocolInfo.getState(), wrapper);
                    ByteBuf transformed = buf.alloc().buffer();
                    try {
                        wrapper.writeToBuffer(transformed);
                        buf.clear().writeBytes(transformed);
                        transformed.release();
                    } catch (Throwable th) {
                        transformed.release();
                        throw th;
                    }
                } catch (CancelException ex) {
                    throw cancelSupplier.apply(ex);
                }
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public long getId() {
                return this.f59id;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public Channel getChannel() {
                return this.channel;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public ProtocolInfo getProtocolInfo() {
                return this.protocolInfo;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public Map<Class<?>, StorableObject> getStoredObjects() {
                return this.storedObjects;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean isActive() {
                return this.active;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public void setActive(boolean active) {
                this.active = active;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean isPendingDisconnect() {
                return this.pendingDisconnect;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public void setPendingDisconnect(boolean pendingDisconnect) {
                this.pendingDisconnect = pendingDisconnect;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean isClientSide() {
                return this.clientSide;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean shouldApplyBlockProtocol() {
                return !this.clientSide;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public boolean isPacketLimiterEnabled() {
                return this.packetLimiterEnabled;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public void setPacketLimiterEnabled(boolean packetLimiterEnabled) {
                this.packetLimiterEnabled = packetLimiterEnabled;
            }

            @Override // com.viaversion.viaversion.api.connection.UserConnection
            public UUID generatePassthroughToken() {
                UUID token = UUID.randomUUID();
                this.passthroughTokens.add(token);
                return token;
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                UserConnectionImpl that = (UserConnectionImpl) o;
                return this.f59id == that.f59id;
            }

            public int hashCode() {
                return Long.hashCode(this.f59id);
            }
        }
