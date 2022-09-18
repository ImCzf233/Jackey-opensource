package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import com.github.benmanes.caffeine.cache.Async;
import com.github.benmanes.caffeine.cache.BLCHeader;
import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.References;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import kotlin.jvm.internal.LongCompanionObject;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class */
public abstract class BoundedLocalCache<K, V> extends BLCHeader.DrainStatusRef<K, V> implements LocalCache<K, V> {
    static final int WRITE_BUFFER_MIN = 4;
    static final int WRITE_BUFFER_RETRIES = 100;
    static final long MAXIMUM_CAPACITY = 9223372034707292160L;
    static final double PERCENT_MAIN = 0.99d;
    static final double PERCENT_MAIN_PROTECTED = 0.8d;
    static final double HILL_CLIMBER_RESTART_THRESHOLD = 0.05d;
    static final double HILL_CLIMBER_STEP_PERCENT = 0.0625d;
    static final double HILL_CLIMBER_STEP_DECAY_RATE = 0.98d;
    static final int QUEUE_TRANSFER_THRESHOLD = 1000;
    static final long MAXIMUM_EXPIRY = 4611686018427387903L;
    final ConcurrentHashMap<Object, Node<K, V>> data;
    final CacheLoader<K, V> cacheLoader;
    final Consumer<Node<K, V>> accessPolicy;
    final Buffer<Node<K, V>> readBuffer;
    final NodeFactory<K, V> nodeFactory;
    final CacheWriter<K, V> writer;
    final Weigher<K, V> weigher;
    final Executor executor;
    final boolean isAsync;
    transient Set<K> keySet;
    transient Collection<V> values;
    transient Set<Map.Entry<K, V>> entrySet;
    static final Logger logger = Logger.getLogger(BoundedLocalCache.class.getName());
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final int WRITE_BUFFER_MAX = 128 * Caffeine.ceilingPowerOfTwo(NCPU);
    static final long EXPIRE_WRITE_TOLERANCE = TimeUnit.SECONDS.toNanos(1);
    final ReentrantLock evictionLock = new ReentrantLock();
    final PerformCleanupTask drainBuffersTask = new PerformCleanupTask(this);

    public BoundedLocalCache(Caffeine<K, V> builder, CacheLoader<K, V> cacheLoader, boolean isAsync) {
        Buffer<Node<K, V>> buffer;
        this.isAsync = isAsync;
        this.cacheLoader = cacheLoader;
        this.executor = builder.getExecutor();
        this.weigher = (Weigher<K, V>) builder.getWeigher(isAsync);
        this.writer = (CacheWriter<K, V>) builder.getCacheWriter(isAsync);
        this.nodeFactory = NodeFactory.newFactory(builder, isAsync);
        this.data = new ConcurrentHashMap<>(builder.getInitialCapacity());
        if (evicts() || collectKeys() || collectValues() || expiresAfterAccess()) {
            buffer = new BoundedBuffer<>();
        } else {
            buffer = Buffer.disabled();
        }
        this.readBuffer = buffer;
        this.accessPolicy = (evicts() || expiresAfterAccess()) ? this::onAccess : e -> {
        };
        if (evicts()) {
            setMaximumSize(builder.getMaximum());
        }
    }

    final boolean isComputingAsync(Node<?, ?> node) {
        return this.isAsync && !Async.isReady((CompletableFuture) node.getValue());
    }

    @GuardedBy("evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderWindowDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderProbationDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected WriteOrderDeque<Node<K, V>> writeOrderDeque() {
        throw new UnsupportedOperationException();
    }

    protected boolean buffersWrites() {
        return false;
    }

    protected MpscGrowableArrayQueue<Runnable> writeBuffer() {
        throw new UnsupportedOperationException();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public final Executor executor() {
        return this.executor;
    }

    protected boolean hasWriter() {
        return this.writer != CacheWriter.disabledWriter();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean isRecordingStats() {
        return false;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public StatsCounter statsCounter() {
        return StatsCounter.disabledStatsCounter();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Ticker statsTicker() {
        return Ticker.disabledTicker();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public RemovalListener<K, V> removalListener() {
        return null;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean hasRemovalListener() {
        return false;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public void notifyRemoval(K key, V value, RemovalCause cause) {
        Caffeine.requireState(hasRemovalListener(), "Notification should be guarded with a check", new Object[0]);
        Runnable task = ()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0012: INVOKE_CUSTOM (r0v3 'task' java.lang.Runnable) = 
              (r5v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r6v0 'key' K A[D('key' K), DONT_INLINE])
              (r7v0 'value' V A[D('value' V), DONT_INLINE])
              (r8v0 'cause' com.github.benmanes.caffeine.cache.RemovalCause A[D('cause' com.github.benmanes.caffeine.cache.RemovalCause), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.lang.Runnable.run():void
             call insn: ?: INVOKE  
              (r0 I:com.github.benmanes.caffeine.cache.BoundedLocalCache)
              (r1 I:java.lang.Object)
              (r2 I:java.lang.Object)
              (r3 I:com.github.benmanes.caffeine.cache.RemovalCause)
             type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$notifyRemoval$1(java.lang.Object, java.lang.Object, com.github.benmanes.caffeine.cache.RemovalCause):void in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.notifyRemoval(K, V, com.github.benmanes.caffeine.cache.RemovalCause):void, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
            Caused by: java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
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
            r0 = r5
            boolean r0 = r0.hasRemovalListener()
            java.lang.String r1 = "Notification should be guarded with a check"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.github.benmanes.caffeine.cache.Caffeine.requireState(r0, r1, r2)
            r0 = r5
            r1 = r6
            r2 = r7
            r3 = r8
            void r0 = () -> { // java.lang.Runnable.run():void
                r0.lambda$notifyRemoval$1(r1, r2, r3);
            }
            r9 = r0
            r0 = r5
            java.util.concurrent.Executor r0 = r0.executor     // Catch: java.lang.Throwable -> L27
            r1 = r9
            r0.execute(r1)     // Catch: java.lang.Throwable -> L27
            goto L3e
        L27:
            r10 = move-exception
            java.util.logging.Logger r0 = com.github.benmanes.caffeine.cache.BoundedLocalCache.logger
            java.util.logging.Level r1 = java.util.logging.Level.SEVERE
            java.lang.String r2 = "Exception thrown when submitting removal listener"
            r3 = r10
            r0.log(r1, r2, r3)
            r0 = r9
            r0.run()
        L3e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.notifyRemoval(java.lang.Object, java.lang.Object, com.github.benmanes.caffeine.cache.RemovalCause):void");
    }

    protected boolean collectKeys() {
        return false;
    }

    protected boolean collectValues() {
        return false;
    }

    protected ReferenceQueue<K> keyReferenceQueue() {
        return null;
    }

    protected ReferenceQueue<V> valueReferenceQueue() {
        return null;
    }

    protected Pacer pacer() {
        return null;
    }

    protected boolean expiresVariable() {
        return false;
    }

    protected boolean expiresAfterAccess() {
        return false;
    }

    protected long expiresAfterAccessNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setExpiresAfterAccessNanos(long expireAfterAccessNanos) {
        throw new UnsupportedOperationException();
    }

    protected boolean expiresAfterWrite() {
        return false;
    }

    protected long expiresAfterWriteNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setExpiresAfterWriteNanos(long expireAfterWriteNanos) {
        throw new UnsupportedOperationException();
    }

    protected boolean refreshAfterWrite() {
        return false;
    }

    protected long refreshAfterWriteNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setRefreshAfterWriteNanos(long refreshAfterWriteNanos) {
        throw new UnsupportedOperationException();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public boolean hasWriteTime() {
        return expiresAfterWrite() || refreshAfterWrite();
    }

    protected Expiry<K, V> expiry() {
        return null;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Ticker expirationTicker() {
        return Ticker.disabledTicker();
    }

    protected TimerWheel<K, V> timerWheel() {
        throw new UnsupportedOperationException();
    }

    protected boolean evicts() {
        return false;
    }

    protected boolean isWeighted() {
        return this.weigher != Weigher.singletonWeigher();
    }

    protected FrequencySketch<K> frequencySketch() {
        throw new UnsupportedOperationException();
    }

    protected boolean fastpath() {
        return false;
    }

    protected long maximum() {
        throw new UnsupportedOperationException();
    }

    protected long windowMaximum() {
        throw new UnsupportedOperationException();
    }

    protected long mainProtectedMaximum() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setWindowMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setMainProtectedMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    protected long weightedSize() {
        throw new UnsupportedOperationException();
    }

    protected long windowWeightedSize() {
        throw new UnsupportedOperationException();
    }

    protected long mainProtectedWeightedSize() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setWindowWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setMainProtectedWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    protected int hitsInSample() {
        throw new UnsupportedOperationException();
    }

    protected int missesInSample() {
        throw new UnsupportedOperationException();
    }

    protected int sampleCount() {
        throw new UnsupportedOperationException();
    }

    protected double stepSize() {
        throw new UnsupportedOperationException();
    }

    protected double previousSampleHitRate() {
        throw new UnsupportedOperationException();
    }

    protected long adjustment() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setHitsInSample(int hitCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setMissesInSample(int missCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setSampleCount(int sampleCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setStepSize(double stepSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setPreviousSampleHitRate(double hitRate) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    protected void setAdjustment(long amount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    void setMaximumSize(long maximum) {
        Caffeine.requireArgument(maximum >= 0, "maximum must not be negative", new Object[0]);
        if (maximum == maximum()) {
            return;
        }
        long max = Math.min(maximum, (long) MAXIMUM_CAPACITY);
        long window = max - ((long) (PERCENT_MAIN * max));
        long mainProtected = (long) (PERCENT_MAIN_PROTECTED * (max - window));
        setMaximum(max);
        setWindowMaximum(window);
        setMainProtectedMaximum(mainProtected);
        setHitsInSample(0);
        setMissesInSample(0);
        setStepSize((-0.0625d) * max);
        if (frequencySketch() != null && !isWeighted() && weightedSize() >= (max >>> 1)) {
            frequencySketch().ensureCapacity(max);
        }
    }

    @GuardedBy("evictionLock")
    void evictEntries() {
        if (!evicts()) {
            return;
        }
        int candidates = evictFromWindow();
        evictFromMain(candidates);
    }

    @GuardedBy("evictionLock")
    int evictFromWindow() {
        int candidates = 0;
        Node<K, V> node = (Node) accessOrderWindowDeque().peek();
        while (true) {
            Node<K, V> node2 = node;
            if (windowWeightedSize() <= windowMaximum() || node2 == null) {
                break;
            }
            Node<K, V> next = node2.getNextInAccessOrder();
            if (node2.getPolicyWeight() != 0) {
                node2.makeMainProbation();
                accessOrderWindowDeque().remove((AccessOrderDeque<Node<K, V>>) node2);
                accessOrderProbationDeque().add(node2);
                candidates++;
                setWindowWeightedSize(windowWeightedSize() - node2.getPolicyWeight());
            }
            node = next;
        }
        return candidates;
    }

    @GuardedBy("evictionLock")
    void evictFromMain(int candidates) {
        Node<K, V> node;
        Node<K, V> node2;
        Node<K, V> node3;
        Node<K, V> node4;
        int victimQueue = 1;
        Node<K, V> victim = (Node) accessOrderProbationDeque().peekFirst();
        Node<K, V> candidate = (Node) accessOrderProbationDeque().peekLast();
        while (weightedSize() > maximum()) {
            if (candidates == 0) {
                candidate = (Node) accessOrderWindowDeque().peekLast();
            }
            if (candidate == null && victim == null) {
                if (victimQueue == 1) {
                    victim = (Node) accessOrderProtectedDeque().peekFirst();
                    victimQueue = 2;
                } else if (victimQueue == 2) {
                    victim = (Node) accessOrderWindowDeque().peekFirst();
                    victimQueue = 0;
                } else {
                    return;
                }
            } else if (victim != null && victim.getPolicyWeight() == 0) {
                victim = victim.getNextInAccessOrder();
            } else if (candidate != null && candidate.getPolicyWeight() == 0) {
                if (candidates > 0) {
                    node4 = candidate.getPreviousInAccessOrder();
                } else {
                    node4 = candidate.getNextInAccessOrder();
                }
                candidate = node4;
                candidates--;
            } else if (victim == null) {
                Node<K, V> previous = candidate.getPreviousInAccessOrder();
                Node<K, V> evict = candidate;
                candidate = previous;
                candidates--;
                evictEntry(evict, RemovalCause.SIZE, 0L);
            } else if (candidate == null) {
                Node<K, V> evict2 = victim;
                victim = victim.getNextInAccessOrder();
                evictEntry(evict2, RemovalCause.SIZE, 0L);
            } else {
                K victimKey = victim.getKey();
                K candidateKey = candidate.getKey();
                if (victimKey == null) {
                    Node<K, V> evict3 = victim;
                    victim = victim.getNextInAccessOrder();
                    evictEntry(evict3, RemovalCause.COLLECTED, 0L);
                } else if (candidateKey == null) {
                    Node<K, V> evict4 = candidate;
                    if (candidates > 0) {
                        node = candidate.getPreviousInAccessOrder();
                    } else {
                        node = candidate.getNextInAccessOrder();
                    }
                    candidate = node;
                    candidates--;
                    evictEntry(evict4, RemovalCause.COLLECTED, 0L);
                } else if (candidate.getPolicyWeight() > maximum()) {
                    Node<K, V> evict5 = candidate;
                    if (candidates > 0) {
                        node2 = candidate.getPreviousInAccessOrder();
                    } else {
                        node2 = candidate.getNextInAccessOrder();
                    }
                    candidate = node2;
                    candidates--;
                    evictEntry(evict5, RemovalCause.SIZE, 0L);
                } else {
                    candidates--;
                    if (admit(candidateKey, victimKey)) {
                        Node<K, V> evict6 = victim;
                        victim = victim.getNextInAccessOrder();
                        evictEntry(evict6, RemovalCause.SIZE, 0L);
                        candidate = candidate.getPreviousInAccessOrder();
                    } else {
                        Node<K, V> evict7 = candidate;
                        if (candidates > 0) {
                            node3 = candidate.getPreviousInAccessOrder();
                        } else {
                            node3 = candidate.getNextInAccessOrder();
                        }
                        candidate = node3;
                        evictEntry(evict7, RemovalCause.SIZE, 0L);
                    }
                }
            }
        }
    }

    @GuardedBy("evictionLock")
    boolean admit(K candidateKey, K victimKey) {
        int victimFreq = frequencySketch().frequency(victimKey);
        int candidateFreq = frequencySketch().frequency(candidateKey);
        if (candidateFreq > victimFreq) {
            return true;
        }
        if (candidateFreq <= 5) {
            return false;
        }
        int random = ThreadLocalRandom.current().nextInt();
        return (random & 127) == 0;
    }

    @GuardedBy("evictionLock")
    void expireEntries() {
        long now = expirationTicker().read();
        expireAfterAccessEntries(now);
        expireAfterWriteEntries(now);
        expireVariableEntries(now);
        Pacer pacer = pacer();
        if (pacer != null) {
            long delay = getExpirationDelay(now);
            if (delay != LongCompanionObject.MAX_VALUE) {
                pacer.schedule(this.executor, this.drainBuffersTask, now, delay);
            }
        }
    }

    @GuardedBy("evictionLock")
    void expireAfterAccessEntries(long now) {
        if (!expiresAfterAccess()) {
            return;
        }
        expireAfterAccessEntries(accessOrderWindowDeque(), now);
        if (evicts()) {
            expireAfterAccessEntries(accessOrderProbationDeque(), now);
            expireAfterAccessEntries(accessOrderProtectedDeque(), now);
        }
    }

    @GuardedBy("evictionLock")
    void expireAfterAccessEntries(AccessOrderDeque<Node<K, V>> accessOrderDeque, long now) {
        long duration = expiresAfterAccessNanos();
        while (true) {
            Node<K, V> node = (Node) accessOrderDeque.peekFirst();
            if (node == null || now - node.getAccessTime() < duration) {
                return;
            }
            evictEntry(node, RemovalCause.EXPIRED, now);
        }
    }

    @GuardedBy("evictionLock")
    void expireAfterWriteEntries(long now) {
        if (!expiresAfterWrite()) {
            return;
        }
        long duration = expiresAfterWriteNanos();
        while (true) {
            Node<K, V> node = (Node) writeOrderDeque().peekFirst();
            if (node != null && now - node.getWriteTime() >= duration) {
                evictEntry(node, RemovalCause.EXPIRED, now);
            } else {
                return;
            }
        }
    }

    @GuardedBy("evictionLock")
    void expireVariableEntries(long now) {
        if (expiresVariable()) {
            timerWheel().advance(now);
        }
    }

    @GuardedBy("evictionLock")
    private long getExpirationDelay(long now) {
        Node<K, V> node;
        long delay = Long.MAX_VALUE;
        if (expiresAfterAccess()) {
            Node<K, V> node2 = (Node) accessOrderWindowDeque().peekFirst();
            if (node2 != null) {
                delay = Math.min((long) LongCompanionObject.MAX_VALUE, expiresAfterAccessNanos() - (now - node2.getAccessTime()));
            }
            if (evicts()) {
                Node<K, V> node3 = (Node) accessOrderProbationDeque().peekFirst();
                if (node3 != null) {
                    delay = Math.min(delay, expiresAfterAccessNanos() - (now - node3.getAccessTime()));
                }
                Node<K, V> node4 = (Node) accessOrderProtectedDeque().peekFirst();
                if (node4 != null) {
                    delay = Math.min(delay, expiresAfterAccessNanos() - (now - node4.getAccessTime()));
                }
            }
        }
        if (expiresAfterWrite() && (node = (Node) writeOrderDeque().peekFirst()) != null) {
            delay = Math.min(delay, expiresAfterWriteNanos() - (now - node.getWriteTime()));
        }
        if (expiresVariable()) {
            delay = Math.min(delay, timerWheel().getExpirationDelay());
        }
        return delay;
    }

    boolean hasExpired(Node<K, V> node, long now) {
        return (expiresAfterAccess() && now - node.getAccessTime() >= expiresAfterAccessNanos()) | (expiresAfterWrite() && now - node.getWriteTime() >= expiresAfterWriteNanos()) | (expiresVariable() && now - node.getVariableTime() >= 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GuardedBy("evictionLock")
    public boolean evictEntry(Node<K, V> node, RemovalCause cause, long now) {
        K key = node.getKey();
        Object[] objArr = new Object[1];
        boolean[] removed = new boolean[1];
        boolean[] resurrect = new boolean[1];
        RemovalCause[] actualCause = new RemovalCause[1];
        this.data.computeIfPresent(node.getKeyReference(), k, n -> {
            if (n != node) {
                return n;
            }
            synchronized (n) {
                node[0] = n.getValue();
                objArr[0] = (actualCause == null || node[0] == null) ? RemovalCause.COLLECTED : key;
                if (objArr[0] == RemovalCause.EXPIRED) {
                    boolean expired = false;
                    if (expiresAfterAccess()) {
                        expired = false | (cause - n.getAccessTime() >= expiresAfterAccessNanos());
                    }
                    if (expiresAfterWrite()) {
                        expired |= cause - n.getWriteTime() >= expiresAfterWriteNanos();
                    }
                    if (expiresVariable()) {
                        expired |= n.getVariableTime() <= cause;
                    }
                    if (!expired) {
                        now[0] = true;
                        return n;
                    }
                } else if (objArr[0] == RemovalCause.SIZE) {
                    int weight = node.getWeight();
                    if (weight == 0) {
                        now[0] = true;
                        return n;
                    }
                }
                this.writer.delete(actualCause, node[0], objArr[0]);
                makeDead(n);
                resurrect[0] = true;
                return null;
            }
        });
        if (resurrect[0]) {
            return false;
        }
        if (node.inWindow() && (evicts() || expiresAfterAccess())) {
            accessOrderWindowDeque().remove((AccessOrderDeque) node);
        } else if (evicts()) {
            if (node.inMainProbation()) {
                accessOrderProbationDeque().remove((AccessOrderDeque) node);
            } else {
                accessOrderProtectedDeque().remove((AccessOrderDeque) node);
            }
        }
        if (expiresAfterWrite()) {
            writeOrderDeque().remove((WriteOrderDeque) node);
        } else if (expiresVariable()) {
            timerWheel().deschedule(node);
        }
        if (removed[0]) {
            statsCounter().recordEviction(node.getWeight(), actualCause[0]);
            if (hasRemovalListener()) {
                notifyRemoval(key, objArr[0], actualCause[0]);
                return true;
            }
            return true;
        }
        makeDead(node);
        return true;
    }

    @GuardedBy("evictionLock")
    void climb() {
        if (!evicts()) {
            return;
        }
        determineAdjustment();
        demoteFromMainProtected();
        long amount = adjustment();
        if (amount == 0) {
            return;
        }
        if (amount > 0) {
            increaseWindow();
        } else {
            decreaseWindow();
        }
    }

    @GuardedBy("evictionLock")
    void determineAdjustment() {
        double d;
        if (frequencySketch().isNotInitialized()) {
            setPreviousSampleHitRate(0.0d);
            setMissesInSample(0);
            setHitsInSample(0);
            return;
        }
        int requestCount = hitsInSample() + missesInSample();
        if (requestCount < frequencySketch().sampleSize) {
            return;
        }
        double hitRate = hitsInSample() / requestCount;
        double hitRateChange = hitRate - previousSampleHitRate();
        double amount = hitRateChange >= 0.0d ? stepSize() : -stepSize();
        if (Math.abs(hitRateChange) >= HILL_CLIMBER_RESTART_THRESHOLD) {
            d = HILL_CLIMBER_STEP_PERCENT * maximum() * (amount >= 0.0d ? 1 : -1);
        } else {
            d = HILL_CLIMBER_STEP_DECAY_RATE * amount;
        }
        double nextStepSize = d;
        setPreviousSampleHitRate(hitRate);
        setAdjustment((long) amount);
        setStepSize(nextStepSize);
        setMissesInSample(0);
        setHitsInSample(0);
    }

    @GuardedBy("evictionLock")
    void increaseWindow() {
        if (mainProtectedMaximum() == 0) {
            return;
        }
        long quota = Math.min(adjustment(), mainProtectedMaximum());
        setMainProtectedMaximum(mainProtectedMaximum() - quota);
        setWindowMaximum(windowMaximum() + quota);
        demoteFromMainProtected();
        for (int i = 0; i < 1000; i++) {
            Node<K, V> candidate = (Node) accessOrderProbationDeque().peek();
            boolean probation = true;
            if (candidate == null || quota < candidate.getPolicyWeight()) {
                candidate = (Node) accessOrderProtectedDeque().peek();
                probation = false;
            }
            if (candidate == null) {
                break;
            }
            int weight = candidate.getPolicyWeight();
            if (quota < weight) {
                break;
            }
            quota -= weight;
            if (probation) {
                accessOrderProbationDeque().remove((AccessOrderDeque<Node<K, V>>) candidate);
            } else {
                setMainProtectedWeightedSize(mainProtectedWeightedSize() - weight);
                accessOrderProtectedDeque().remove((AccessOrderDeque<Node<K, V>>) candidate);
            }
            setWindowWeightedSize(windowWeightedSize() + weight);
            accessOrderWindowDeque().add(candidate);
            candidate.makeWindow();
        }
        setMainProtectedMaximum(mainProtectedMaximum() + quota);
        setWindowMaximum(windowMaximum() - quota);
        setAdjustment(quota);
    }

    @GuardedBy("evictionLock")
    void decreaseWindow() {
        Node<K, V> candidate;
        if (windowMaximum() <= 1) {
            return;
        }
        long quota = Math.min(-adjustment(), Math.max(0L, windowMaximum() - 1));
        setMainProtectedMaximum(mainProtectedMaximum() + quota);
        setWindowMaximum(windowMaximum() - quota);
        for (int i = 0; i < 1000 && (candidate = (Node) accessOrderWindowDeque().peek()) != null; i++) {
            int weight = candidate.getPolicyWeight();
            if (quota < weight) {
                break;
            }
            quota -= weight;
            setWindowWeightedSize(windowWeightedSize() - weight);
            accessOrderWindowDeque().remove((AccessOrderDeque<Node<K, V>>) candidate);
            accessOrderProbationDeque().add(candidate);
            candidate.makeMainProbation();
        }
        setMainProtectedMaximum(mainProtectedMaximum() - quota);
        setWindowMaximum(windowMaximum() + quota);
        setAdjustment(-quota);
    }

    @GuardedBy("evictionLock")
    void demoteFromMainProtected() {
        Node<K, V> demoted;
        long mainProtectedMaximum = mainProtectedMaximum();
        long mainProtectedWeightedSize = mainProtectedWeightedSize();
        if (mainProtectedWeightedSize <= mainProtectedMaximum) {
            return;
        }
        for (int i = 0; i < 1000 && mainProtectedWeightedSize > mainProtectedMaximum && (demoted = (Node) accessOrderProtectedDeque().poll()) != null; i++) {
            demoted.makeMainProbation();
            accessOrderProbationDeque().add(demoted);
            mainProtectedWeightedSize -= demoted.getPolicyWeight();
        }
        setMainProtectedWeightedSize(mainProtectedWeightedSize);
    }

    void afterRead(Node<K, V> node, long now, boolean recordHit) {
        if (recordHit) {
            statsCounter().recordHits(1);
        }
        boolean delayable = skipReadBuffer() || this.readBuffer.offer(node) != 1;
        if (shouldDrainBuffers(delayable)) {
            scheduleDrainBuffers();
        }
        refreshIfNeeded(node, now);
    }

    boolean skipReadBuffer() {
        return fastpath() && frequencySketch().isNotInitialized();
    }

    void refreshIfNeeded(Node<K, V> node, long now) {
        K key;
        V oldValue;
        CompletableFuture<V> refreshFuture;
        if (!refreshAfterWrite()) {
            return;
        }
        long oldWriteTime = node.getWriteTime();
        long refreshWriteTime = now + 6917529027641081854L;
        if (now - oldWriteTime > refreshAfterWriteNanos() && (key = node.getKey()) != null && (oldValue = node.getValue()) != null && node.casWriteTime(oldWriteTime, refreshWriteTime)) {
            try {
                long startTime = statsTicker().read();
                if (this.isAsync) {
                    CompletableFuture<V> future = (CompletableFuture) oldValue;
                    if (Async.isReady(future)) {
                        CompletableFuture<V> refresh = future.thenCompose((Function<? super V, ? extends CompletionStage<U>>) value -> {
                            return this.cacheLoader.asyncReload(key, key, this.executor);
                        });
                        refreshFuture = refresh;
                    } else {
                        node.casWriteTime(refreshWriteTime, oldWriteTime);
                        return;
                    }
                } else {
                    CompletableFuture<V> refresh2 = this.cacheLoader.asyncReload(key, oldValue, this.executor);
                    refreshFuture = refresh2;
                }
                CompletableFuture<V> completableFuture = refreshFuture;
                refreshFuture.whenComplete(newValue, error -> {
                    long loadTime = statsTicker().read() - startTime;
                    if (error != null) {
                        logger.log(Level.WARNING, "Exception thrown during refresh", error);
                        startTime.casWriteTime(node, refreshWriteTime);
                        statsCounter().recordLoadFailure(loadTime);
                        return;
                    }
                    V value2 = (!this.isAsync || oldValue == 0) ? oldValue : oldWriteTime;
                    boolean[] discard = new boolean[1];
                    compute(completableFuture, k, currentValue -> {
                        if (currentValue == null) {
                            return value2;
                        }
                        if (currentValue == key && startTime.getWriteTime() == node) {
                            return value2;
                        }
                        discard[0] = true;
                        return currentValue;
                    }, false, false, true);
                    if (discard[0] && hasRemovalListener()) {
                        notifyRemoval(completableFuture, value2, RemovalCause.REPLACED);
                    }
                    if (oldValue == 0) {
                        statsCounter().recordLoadFailure(loadTime);
                    } else {
                        statsCounter().recordLoadSuccess(loadTime);
                    }
                });
            } catch (Throwable t) {
                node.casWriteTime(refreshWriteTime, oldWriteTime);
                logger.log(Level.SEVERE, "Exception thrown when submitting refresh task", t);
            }
        }
    }

    long expireAfterCreate(K key, V value, Expiry<K, V> expiry, long now) {
        if (expiresVariable() && key != null && value != null) {
            long duration = expiry.expireAfterCreate(key, value, now);
            return this.isAsync ? now + duration : now + Math.min(duration, 4611686018427387903L);
        }
        return 0L;
    }

    long expireAfterUpdate(Node<K, V> node, K key, V value, Expiry<K, V> expiry, long now) {
        if (expiresVariable() && key != null && value != null) {
            long currentDuration = Math.max(1L, node.getVariableTime() - now);
            long duration = expiry.expireAfterUpdate(key, value, now, currentDuration);
            return this.isAsync ? now + duration : now + Math.min(duration, 4611686018427387903L);
        }
        return 0L;
    }

    long expireAfterRead(Node<K, V> node, K key, V value, Expiry<K, V> expiry, long now) {
        if (expiresVariable() && key != null && value != null) {
            long currentDuration = Math.max(1L, node.getVariableTime() - now);
            long duration = expiry.expireAfterRead(key, value, now, currentDuration);
            return this.isAsync ? now + duration : now + Math.min(duration, 4611686018427387903L);
        }
        return 0L;
    }

    void tryExpireAfterRead(Node<K, V> node, K key, V value, Expiry<K, V> expiry, long now) {
        if (!expiresVariable() || key == null || value == null) {
            return;
        }
        long variableTime = node.getVariableTime();
        long currentDuration = Math.max(1L, variableTime - now);
        if (this.isAsync && currentDuration > 4611686018427387903L) {
            return;
        }
        long duration = expiry.expireAfterRead(key, value, now, currentDuration);
        if (duration != currentDuration) {
            long expirationTime = this.isAsync ? now + duration : now + Math.min(duration, 4611686018427387903L);
            node.casVariableTime(variableTime, expirationTime);
        }
    }

    void setVariableTime(Node<K, V> node, long expirationTime) {
        if (expiresVariable()) {
            node.setVariableTime(expirationTime);
        }
    }

    void setWriteTime(Node<K, V> node, long now) {
        if (expiresAfterWrite() || refreshAfterWrite()) {
            node.setWriteTime(now);
        }
    }

    void setAccessTime(Node<K, V> node, long now) {
        if (expiresAfterAccess()) {
            node.setAccessTime(now);
        }
    }

    void afterWrite(Runnable task) {
        if (buffersWrites()) {
            for (int i = 0; i < 100; i++) {
                if (writeBuffer().offer(task)) {
                    scheduleAfterWrite();
                    return;
                }
                scheduleDrainBuffers();
            }
            try {
                performCleanUp(task);
                return;
            } catch (RuntimeException e) {
                logger.log(Level.SEVERE, "Exception thrown when performing the maintenance task", (Throwable) e);
                return;
            }
        }
        scheduleAfterWrite();
    }

    void scheduleAfterWrite() {
        do {
            switch (drainStatus()) {
                case 0:
                    casDrainStatus(0, 1);
                    scheduleDrainBuffers();
                    return;
                case 1:
                    scheduleDrainBuffers();
                    return;
                case 2:
                    break;
                case 3:
                    return;
                default:
                    throw new IllegalStateException();
            }
        } while (!casDrainStatus(2, 3));
    }

    void scheduleDrainBuffers() {
        if (drainStatus() < 2 && this.evictionLock.tryLock()) {
            try {
                int drainStatus = drainStatus();
                if (drainStatus >= 2) {
                    return;
                }
                lazySetDrainStatus(2);
                this.executor.execute(this.drainBuffersTask);
            } catch (Throwable t) {
                try {
                    logger.log(Level.WARNING, "Exception thrown when submitting maintenance task", t);
                    maintenance(null);
                } finally {
                    this.evictionLock.unlock();
                }
            }
        }
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public void cleanUp() {
        try {
            performCleanUp(null);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Exception thrown when performing the maintenance task", (Throwable) e);
        }
    }

    void performCleanUp(Runnable task) {
        this.evictionLock.lock();
        try {
            maintenance(task);
            if (drainStatus() == 1 && this.executor == ForkJoinPool.commonPool()) {
                scheduleDrainBuffers();
            }
        } finally {
            this.evictionLock.unlock();
        }
    }

    @GuardedBy("evictionLock")
    void maintenance(Runnable task) {
        lazySetDrainStatus(2);
        try {
            drainReadBuffer();
            drainWriteBuffer();
            if (task != null) {
                task.run();
            }
            drainKeyReferences();
            drainValueReferences();
            expireEntries();
            evictEntries();
            climb();
        } finally {
            if (drainStatus() != 2 || !casDrainStatus(2, 0)) {
                lazySetDrainStatus(1);
            }
        }
    }

    @GuardedBy("evictionLock")
    void drainKeyReferences() {
        if (!collectKeys()) {
            return;
        }
        while (true) {
            Reference<? extends K> keyRef = keyReferenceQueue().poll();
            if (keyRef != null) {
                Node<K, V> node = this.data.get(keyRef);
                if (node != null) {
                    evictEntry(node, RemovalCause.COLLECTED, 0L);
                }
            } else {
                return;
            }
        }
    }

    @GuardedBy("evictionLock")
    void drainValueReferences() {
        if (!collectValues()) {
            return;
        }
        while (true) {
            Reference<? extends V> valueRef = valueReferenceQueue().poll();
            if (valueRef != null) {
                References.InternalReference<V> ref = (References.InternalReference) valueRef;
                Node<K, V> node = this.data.get(ref.getKeyReference());
                if (node != null && valueRef == node.getValueReference()) {
                    evictEntry(node, RemovalCause.COLLECTED, 0L);
                }
            } else {
                return;
            }
        }
    }

    @GuardedBy("evictionLock")
    void drainReadBuffer() {
        if (!skipReadBuffer()) {
            this.readBuffer.drainTo(this.accessPolicy);
        }
    }

    @GuardedBy("evictionLock")
    void onAccess(Node<K, V> node) {
        if (evicts()) {
            K key = node.getKey();
            if (key == null) {
                return;
            }
            frequencySketch().increment(key);
            if (node.inWindow()) {
                reorder(accessOrderWindowDeque(), node);
            } else if (node.inMainProbation()) {
                reorderProbation(node);
            } else {
                reorder(accessOrderProtectedDeque(), node);
            }
            setHitsInSample(hitsInSample() + 1);
        } else if (expiresAfterAccess()) {
            reorder(accessOrderWindowDeque(), node);
        }
        if (expiresVariable()) {
            timerWheel().reschedule(node);
        }
    }

    @GuardedBy("evictionLock")
    void reorderProbation(Node<K, V> node) {
        if (!accessOrderProbationDeque().contains((AccessOrderDeque.AccessOrder<?>) node) || node.getPolicyWeight() > mainProtectedMaximum()) {
            return;
        }
        setMainProtectedWeightedSize(mainProtectedWeightedSize() + node.getPolicyWeight());
        accessOrderProbationDeque().remove((AccessOrderDeque<Node<K, V>>) node);
        accessOrderProtectedDeque().add(node);
        node.makeMainProtected();
    }

    static <K, V> void reorder(LinkedDeque<Node<K, V>> deque, Node<K, V> node) {
        if (deque.contains(node)) {
            deque.moveToBack(node);
        }
    }

    @GuardedBy("evictionLock")
    void drainWriteBuffer() {
        if (!buffersWrites()) {
            return;
        }
        for (int i = 0; i < WRITE_BUFFER_MAX; i++) {
            Runnable task = writeBuffer().poll();
            if (task == null) {
                return;
            }
            task.run();
        }
        lazySetDrainStatus(3);
    }

    @GuardedBy("evictionLock")
    void makeDead(Node<K, V> node) {
        synchronized (node) {
            if (node.isDead()) {
                return;
            }
            if (evicts()) {
                if (node.inWindow()) {
                    setWindowWeightedSize(windowWeightedSize() - node.getWeight());
                } else if (node.inMainProtected()) {
                    setMainProtectedWeightedSize(mainProtectedWeightedSize() - node.getWeight());
                }
                setWeightedSize(weightedSize() - node.getWeight());
            }
            node.die();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$AddTask.class */
    public final class AddTask implements Runnable {
        final Node<K, V> node;
        final int weight;

        AddTask(Node<K, V> node, int weight) {
            BoundedLocalCache.this = this$0;
            this.weight = weight;
            this.node = node;
        }

        @Override // java.lang.Runnable
        @GuardedBy("evictionLock")
        public void run() {
            boolean isAlive;
            if (BoundedLocalCache.this.evicts()) {
                long weightedSize = BoundedLocalCache.this.weightedSize();
                BoundedLocalCache.this.setWeightedSize(weightedSize + this.weight);
                BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + this.weight);
                this.node.setPolicyWeight(this.node.getPolicyWeight() + this.weight);
                long maximum = BoundedLocalCache.this.maximum();
                if (weightedSize >= (maximum >>> 1)) {
                    long capacity = BoundedLocalCache.this.isWeighted() ? BoundedLocalCache.this.data.mappingCount() : maximum;
                    BoundedLocalCache.this.frequencySketch().ensureCapacity(capacity);
                }
                K key = this.node.getKey();
                if (key != null) {
                    BoundedLocalCache.this.frequencySketch().increment(key);
                }
                BoundedLocalCache.this.setMissesInSample(BoundedLocalCache.this.missesInSample() + 1);
            }
            synchronized (this.node) {
                isAlive = this.node.isAlive();
            }
            if (isAlive) {
                if (BoundedLocalCache.this.expiresAfterWrite()) {
                    BoundedLocalCache.this.writeOrderDeque().add(this.node);
                }
                if (BoundedLocalCache.this.evicts() && this.weight > BoundedLocalCache.this.windowMaximum()) {
                    BoundedLocalCache.this.accessOrderWindowDeque().offerFirst(this.node);
                } else if (BoundedLocalCache.this.evicts() || BoundedLocalCache.this.expiresAfterAccess()) {
                    BoundedLocalCache.this.accessOrderWindowDeque().offerLast(this.node);
                }
                if (BoundedLocalCache.this.expiresVariable()) {
                    BoundedLocalCache.this.timerWheel().schedule(this.node);
                }
            }
            if (BoundedLocalCache.this.isComputingAsync(this.node)) {
                synchronized (this.node) {
                    if (!Async.isReady((CompletableFuture) this.node.getValue())) {
                        long expirationTime = BoundedLocalCache.this.expirationTicker().read() + 6917529027641081854L;
                        BoundedLocalCache.this.setVariableTime(this.node, expirationTime);
                        BoundedLocalCache.this.setAccessTime(this.node, expirationTime);
                        BoundedLocalCache.this.setWriteTime(this.node, expirationTime);
                    }
                }
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$RemovalTask.class */
    public final class RemovalTask implements Runnable {
        final Node<K, V> node;

        RemovalTask(Node<K, V> node) {
            BoundedLocalCache.this = this$0;
            this.node = node;
        }

        @Override // java.lang.Runnable
        @GuardedBy("evictionLock")
        public void run() {
            if (this.node.inWindow() && (BoundedLocalCache.this.evicts() || BoundedLocalCache.this.expiresAfterAccess())) {
                BoundedLocalCache.this.accessOrderWindowDeque().remove((AccessOrderDeque<Node<K, V>>) this.node);
            } else if (BoundedLocalCache.this.evicts()) {
                if (this.node.inMainProbation()) {
                    BoundedLocalCache.this.accessOrderProbationDeque().remove((AccessOrderDeque<Node<K, V>>) this.node);
                } else {
                    BoundedLocalCache.this.accessOrderProtectedDeque().remove((AccessOrderDeque<Node<K, V>>) this.node);
                }
            }
            if (BoundedLocalCache.this.expiresAfterWrite()) {
                BoundedLocalCache.this.writeOrderDeque().remove((WriteOrderDeque<Node<K, V>>) this.node);
            } else if (BoundedLocalCache.this.expiresVariable()) {
                BoundedLocalCache.this.timerWheel().deschedule(this.node);
            }
            BoundedLocalCache.this.makeDead(this.node);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$UpdateTask.class */
    public final class UpdateTask implements Runnable {
        final int weightDifference;
        final Node<K, V> node;

        public UpdateTask(Node<K, V> node, int weightDifference) {
            BoundedLocalCache.this = this$0;
            this.weightDifference = weightDifference;
            this.node = node;
        }

        @Override // java.lang.Runnable
        @GuardedBy("evictionLock")
        public void run() {
            if (BoundedLocalCache.this.evicts()) {
                int oldWeightedSize = this.node.getPolicyWeight();
                this.node.setPolicyWeight(oldWeightedSize + this.weightDifference);
                if (this.node.inWindow()) {
                    if (this.node.getPolicyWeight() <= BoundedLocalCache.this.windowMaximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                    } else if (BoundedLocalCache.this.accessOrderWindowDeque().contains((AccessOrderDeque.AccessOrder<?>) this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().moveToFront(this.node);
                    }
                    BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + this.weightDifference);
                } else if (this.node.inMainProbation()) {
                    if (this.node.getPolicyWeight() <= BoundedLocalCache.this.maximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                    } else if (BoundedLocalCache.this.accessOrderProbationDeque().remove((AccessOrderDeque<Node<K, V>>) this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().addFirst(this.node);
                        BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + this.node.getPolicyWeight());
                    }
                } else if (this.node.inMainProtected()) {
                    if (this.node.getPolicyWeight() <= BoundedLocalCache.this.maximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() + this.weightDifference);
                    } else if (BoundedLocalCache.this.accessOrderProtectedDeque().remove((AccessOrderDeque<Node<K, V>>) this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().addFirst(this.node);
                        BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + this.node.getPolicyWeight());
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() - oldWeightedSize);
                    } else {
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() - oldWeightedSize);
                    }
                }
                BoundedLocalCache.this.setWeightedSize(BoundedLocalCache.this.weightedSize() + this.weightDifference);
            } else if (BoundedLocalCache.this.expiresAfterAccess()) {
                BoundedLocalCache.this.onAccess(this.node);
            }
            if (BoundedLocalCache.this.expiresAfterWrite()) {
                BoundedLocalCache.reorder(BoundedLocalCache.this.writeOrderDeque(), this.node);
            } else if (BoundedLocalCache.this.expiresVariable()) {
                BoundedLocalCache.this.timerWheel().reschedule(this.node);
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.data.size();
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public long estimatedSize() {
        return this.data.mappingCount();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Runnable task;
        this.evictionLock.lock();
        try {
            long now = expirationTicker().read();
            while (buffersWrites() && (task = writeBuffer().poll()) != null) {
                task.run();
            }
            for (Node<K, V> node : this.data.values()) {
                removeNode(node, now);
            }
            this.readBuffer.drainTo(e -> {
            });
            this.evictionLock.unlock();
        } catch (Throwable th) {
            this.evictionLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GuardedBy("evictionLock")
    void removeNode(Node<K, V> node, long now) {
        K key = node.getKey();
        Object[] objArr = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        this.data.computeIfPresent(node.getKeyReference(), k, n -> {
            if (n != node) {
                return n;
            }
            synchronized (n) {
                node[0] = n.getValue();
                if (objArr == null || node[0] == null) {
                    key[0] = RemovalCause.COLLECTED;
                } else if (hasExpired(n, cause)) {
                    key[0] = RemovalCause.EXPIRED;
                } else {
                    key[0] = RemovalCause.EXPLICIT;
                }
                if (objArr != null) {
                    this.writer.delete(objArr, node[0], key[0]);
                }
                makeDead(n);
            }
            return null;
        });
        if (node.inWindow() && (evicts() || expiresAfterAccess())) {
            accessOrderWindowDeque().remove((AccessOrderDeque) node);
        } else if (evicts()) {
            if (node.inMainProbation()) {
                accessOrderProbationDeque().remove((AccessOrderDeque) node);
            } else {
                accessOrderProtectedDeque().remove((AccessOrderDeque) node);
            }
        }
        if (expiresAfterWrite()) {
            writeOrderDeque().remove((WriteOrderDeque) node);
        } else if (expiresVariable()) {
            timerWheel().deschedule(node);
        }
        if (cause[0] != null && hasRemovalListener()) {
            notifyRemoval(key, objArr[0], cause[0]);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        return (node == null || node.getValue() == null || hasExpired(node, expirationTicker().read())) ? false : true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(Object value) {
        Objects.requireNonNull(value);
        long now = expirationTicker().read();
        for (Node<K, V> node : this.data.values()) {
            if (node.containsValue(value) && !hasExpired(node, now) && node.getKey() != null) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        return getIfPresent(key, false);
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V getIfPresent(Object key, boolean recordStats) {
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null) {
            if (recordStats) {
                statsCounter().recordMisses(1);
            }
            if (drainStatus() == 1) {
                scheduleDrainBuffers();
                return null;
            }
            return null;
        }
        V value = node.getValue();
        long now = expirationTicker().read();
        if (hasExpired(node, now) || (collectValues() && value == null)) {
            if (recordStats) {
                statsCounter().recordMisses(1);
            }
            scheduleDrainBuffers();
            return null;
        }
        if (!isComputingAsync(node)) {
            setAccessTime(node, now);
            tryExpireAfterRead(node, key, value, expiry(), now);
        }
        afterRead(node, now, recordStats);
        return value;
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V getIfPresentQuietly(Object key, long[] writeTime) {
        V value;
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null || (value = node.getValue()) == null || hasExpired(node, expirationTicker().read())) {
            return null;
        }
        writeTime[0] = node.getWriteTime();
        return value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public Map<K, V> getAllPresent(Iterable<?> keys) {
        V value;
        Set<Object> uniqueKeys = new LinkedHashSet<>();
        Iterator<?> it = keys.iterator();
        while (it.hasNext()) {
            uniqueKeys.add(it.next());
        }
        int misses = 0;
        long now = expirationTicker().read();
        Map<Object, Object> result = new LinkedHashMap<>(uniqueKeys.size());
        for (Object key : uniqueKeys) {
            Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
            if (node == null || (value = node.getValue()) == null || hasExpired(node, now)) {
                misses++;
            } else {
                result.put(key, value);
                if (!isComputingAsync(node)) {
                    tryExpireAfterRead(node, key, value, expiry(), now);
                    setAccessTime(node, now);
                }
                afterRead(node, now, false);
            }
        }
        statsCounter().recordMisses(misses);
        statsCounter().recordHits(result.size());
        return Collections.unmodifiableMap(result);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        return put(key, value, expiry(), true, false);
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V put(K key, V value, boolean notifyWriter) {
        return put(key, value, expiry(), notifyWriter, false);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V putIfAbsent(K key, V value) {
        return put(key, value, expiry(), true, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0143, code lost:
        r0 = r19.getValue();
        r0 = r19.getWeight();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0153, code lost:
        if (r0 != null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0156, code lost:
        r21 = expireAfterCreate(r10, r11, r12, r0);
        r9.writer.delete(r10, null, com.github.benmanes.caffeine.cache.RemovalCause.COLLECTED);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x017a, code lost:
        if (hasExpired(r19, r0) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x017d, code lost:
        r24 = true;
        r21 = expireAfterCreate(r10, r11, r12, r0);
        r9.writer.delete(r10, r0, com.github.benmanes.caffeine.cache.RemovalCause.EXPIRED);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x019f, code lost:
        if (r14 == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01a2, code lost:
        r25 = false;
        r21 = expireAfterRead(r19, r10, r11, r12, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01b5, code lost:
        r21 = expireAfterUpdate(r19, r10, r11, r12, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01c4, code lost:
        if (r13 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01c9, code lost:
        if (r24 != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01ce, code lost:
        if (r25 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01d4, code lost:
        if (r11 == r0) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01d7, code lost:
        r9.writer.write(r10, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01e4, code lost:
        if (r25 == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01eb, code lost:
        if (expiresAfterWrite() == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01fa, code lost:
        if ((r0 - r19.getWriteTime()) > com.github.benmanes.caffeine.cache.BoundedLocalCache.EXPIRE_WRITE_TOLERANCE) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0201, code lost:
        if (expiresVariable() == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0213, code lost:
        if (java.lang.Math.abs(r21 - r19.getVariableTime()) <= com.github.benmanes.caffeine.cache.BoundedLocalCache.EXPIRE_WRITE_TOLERANCE) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0216, code lost:
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x021a, code lost:
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x021b, code lost:
        r26 = r0;
        setWriteTime(r19, r0);
        r19.setWeight(r0);
        r19.setValue(r11, valueReferenceQueue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0236, code lost:
        setVariableTime(r19, r21);
        setAccessTime(r19, r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    V put(K r10, V r11, com.github.benmanes.caffeine.cache.Expiry<K, V> r12, boolean r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 772
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.put(java.lang.Object, java.lang.Object, com.github.benmanes.caffeine.cache.Expiry, boolean, boolean):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        Node<K, V>[] node = new Node[1];
        Object[] objArr = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), k, n -> {
            synchronized (n) {
                oldValue[0] = n.getValue();
                if (oldValue[0] == null) {
                    objArr[0] = RemovalCause.COLLECTED;
                } else if (hasExpired(n, expirationTicker().read())) {
                    objArr[0] = RemovalCause.EXPIRED;
                } else {
                    objArr[0] = RemovalCause.EXPLICIT;
                }
                this.writer.delete(cause, oldValue[0], objArr[0]);
                n.retire();
            }
            key[0] = n;
            return null;
        });
        if (cause[0] != null) {
            afterWrite(new RemovalTask(node[0]));
            if (hasRemovalListener()) {
                notifyRemoval(key, objArr[0], cause[0]);
            }
        }
        if (cause[0] == RemovalCause.EXPLICIT) {
            return (V) objArr[0];
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean remove(Object key, Object value) {
        Objects.requireNonNull(key);
        if (value == null) {
            return false;
        }
        Node<K, V>[] removed = new Node[1];
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), kR, node -> {
            synchronized (node) {
                oldKey[0] = node.getKey();
                objArr[0] = node.getValue();
                if (oldKey[0] == null) {
                    objArr2[0] = RemovalCause.COLLECTED;
                } else if (hasExpired(node, expirationTicker().read())) {
                    objArr2[0] = RemovalCause.EXPIRED;
                } else if (node.containsValue(cause)) {
                    objArr2[0] = RemovalCause.EXPLICIT;
                } else {
                    return node;
                }
                this.writer.delete(oldKey[0], objArr[0], objArr2[0]);
                value[0] = node;
                node.retire();
                return null;
            }
        });
        if (removed[0] == null) {
            return false;
        }
        if (hasRemovalListener()) {
            notifyRemoval(objArr[0], objArr2[0], cause[0]);
        }
        afterWrite(new RemovalTask(removed[0]));
        return cause[0] == RemovalCause.EXPLICIT;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V replace(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        int[] oldWeight = new int[1];
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        long[] now = new long[1];
        int weight = this.weigher.weigh(key, value);
        Node<K, V> node = this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), 
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x004e: INVOKE  (r0v18 'node' com.github.benmanes.caffeine.cache.Node<K, V>) = 
              (wrap: java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> : 0x002d: IGET  (r0v16 java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> A[REMOVE]) = 
              (r11v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), IMMUTABLE_TYPE, THIS])
             com.github.benmanes.caffeine.cache.BoundedLocalCache.data java.util.concurrent.ConcurrentHashMap)
              (wrap: java.lang.Object : 0x0035: INVOKE  (r1v3 java.lang.Object A[REMOVE]) = 
              (wrap: com.github.benmanes.caffeine.cache.NodeFactory<K, V> : 0x0031: IGET  (r1v2 com.github.benmanes.caffeine.cache.NodeFactory<K, V> A[REMOVE]) = 
              (r11v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), IMMUTABLE_TYPE, THIS])
             com.github.benmanes.caffeine.cache.BoundedLocalCache.nodeFactory com.github.benmanes.caffeine.cache.NodeFactory)
              (r12v0 'key' K A[D('key' K)])
             type: INTERFACE call: com.github.benmanes.caffeine.cache.NodeFactory.newLookupKey(java.lang.Object):java.lang.Object)
              (wrap: java.util.function.BiFunction<? super java.lang.Object, ? super com.github.benmanes.caffeine.cache.Node<K, V>, ? extends com.github.benmanes.caffeine.cache.Node<K, V>> : 0x0046: INVOKE_CUSTOM (r2v3 java.util.function.BiFunction<? super java.lang.Object, ? super com.github.benmanes.caffeine.cache.Node<K, V>, ? extends com.github.benmanes.caffeine.cache.Node<K, V>> A[REMOVE]) = 
              (r11v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r0v7 'objArr' java.lang.Object[] A[D('nodeKey' K[]), DONT_INLINE])
              (r0v9 'objArr2' java.lang.Object[] A[D('oldValue' V[]), DONT_INLINE])
              (r0v5 'oldWeight' int[] A[D('oldWeight' int[]), DONT_INLINE])
              (r0v11 'now' long[] A[D('now' long[]), DONT_INLINE])
              (r12v0 'key' K A[D('key' K), DONT_INLINE])
              (r13v0 'value' V A[D('value' V), DONT_INLINE])
              (r0v14 'weight' int A[D('weight' int), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.BiFunction.apply(java.lang.Object, java.lang.Object):java.lang.Object
             call insn: ?: INVOKE  
              (r2 I:com.github.benmanes.caffeine.cache.BoundedLocalCache)
              (r3 I:java.lang.Object[])
              (r4 I:java.lang.Object[])
              (r5 I:int[])
              (r6 I:long[])
              (r7 I:java.lang.Object)
              (r8 I:java.lang.Object)
              (r9 I:int)
              (v8 java.lang.Object)
              (v9 com.github.benmanes.caffeine.cache.Node)
             type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$replace$11(java.lang.Object[], java.lang.Object[], int[], long[], java.lang.Object, java.lang.Object, int, java.lang.Object, com.github.benmanes.caffeine.cache.Node):com.github.benmanes.caffeine.cache.Node)
             type: VIRTUAL call: java.util.concurrent.ConcurrentHashMap.computeIfPresent(java.lang.Object, java.util.function.BiFunction):java.lang.Object in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.replace(K, V):V, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
            Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getCodeVar()" because the return value of "jadx.core.dex.instructions.args.RegisterArg.getSVar()" is null
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:943)
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
            	... 15 more
            */
        /*
            this = this;
            r0 = r12
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r13
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = 1
            int[] r0 = new int[r0]
            r14 = r0
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r15 = r0
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r0 = 1
            long[] r0 = new long[r0]
            r17 = r0
            r0 = r11
            com.github.benmanes.caffeine.cache.Weigher<K, V> r0 = r0.weigher
            r1 = r12
            r2 = r13
            int r0 = r0.weigh(r1, r2)
            r18 = r0
            r0 = r11
            java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> r0 = r0.data
            r1 = r11
            com.github.benmanes.caffeine.cache.NodeFactory<K, V> r1 = r1.nodeFactory
            r2 = r12
            java.lang.Object r1 = r1.newLookupKey(r2)
            r2 = r11
            r3 = r15
            r4 = r16
            r5 = r14
            r6 = r17
            r7 = r12
            r8 = r13
            r9 = r18
            V r2 = (v8, v9) -> { // java.util.function.BiFunction.apply(java.lang.Object, java.lang.Object):java.lang.Object
                return r2.lambda$replace$11(r3, r4, r5, r6, r7, r8, r9, v8, v9);
            }
            java.lang.Object r0 = r0.computeIfPresent(r1, r2)
            com.github.benmanes.caffeine.cache.Node r0 = (com.github.benmanes.caffeine.cache.Node) r0
            r19 = r0
            r0 = r16
            r1 = 0
            r0 = r0[r1]
            if (r0 != 0) goto L5c
            r0 = 0
            return r0
        L5c:
            r0 = r18
            r1 = r14
            r2 = 0
            r1 = r1[r2]
            int r0 = r0 - r1
            r20 = r0
            r0 = r11
            boolean r0 = r0.expiresAfterWrite()
            if (r0 != 0) goto L70
            r0 = r20
            if (r0 == 0) goto L83
        L70:
            r0 = r11
            com.github.benmanes.caffeine.cache.BoundedLocalCache$UpdateTask r1 = new com.github.benmanes.caffeine.cache.BoundedLocalCache$UpdateTask
            r2 = r1
            r3 = r11
            r4 = r19
            r5 = r20
            r2.<init>(r4, r5)
            r0.afterWrite(r1)
            goto L8e
        L83:
            r0 = r11
            r1 = r19
            r2 = r17
            r3 = 0
            r2 = r2[r3]
            r3 = 0
            r0.afterRead(r1, r2, r3)
        L8e:
            r0 = r11
            boolean r0 = r0.hasRemovalListener()
            if (r0 == 0) goto Lac
            r0 = r13
            r1 = r16
            r2 = 0
            r1 = r1[r2]
            if (r0 == r1) goto Lac
            r0 = r11
            r1 = r15
            r2 = 0
            r1 = r1[r2]
            r2 = r16
            r3 = 0
            r2 = r2[r3]
            com.github.benmanes.caffeine.cache.RemovalCause r3 = com.github.benmanes.caffeine.cache.RemovalCause.REPLACED
            r0.notifyRemoval(r1, r2, r3)
        Lac:
            r0 = r16
            r1 = 0
            r0 = r0[r1]
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.replace(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(K key, V oldValue, V newValue) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(oldValue);
        Objects.requireNonNull(newValue);
        int weight = this.weigher.weigh(key, newValue);
        boolean[] replaced = new boolean[1];
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        int[] oldWeight = new int[1];
        long[] now = new long[1];
        Node<K, V> node = this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), 
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x005d: INVOKE  (r0v22 'node' com.github.benmanes.caffeine.cache.Node<K, V>) = 
              (wrap: java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> : 0x0038: IGET  (r0v20 java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> A[REMOVE]) = 
              (r13v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), IMMUTABLE_TYPE, THIS])
             com.github.benmanes.caffeine.cache.BoundedLocalCache.data java.util.concurrent.ConcurrentHashMap)
              (wrap: java.lang.Object : 0x0040: INVOKE  (r1v3 java.lang.Object A[REMOVE]) = 
              (wrap: com.github.benmanes.caffeine.cache.NodeFactory<K, V> : 0x003c: IGET  (r1v2 com.github.benmanes.caffeine.cache.NodeFactory<K, V> A[REMOVE]) = 
              (r13v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), IMMUTABLE_TYPE, THIS])
             com.github.benmanes.caffeine.cache.BoundedLocalCache.nodeFactory com.github.benmanes.caffeine.cache.NodeFactory)
              (r14v0 'key' K A[D('key' K)])
             type: INTERFACE call: com.github.benmanes.caffeine.cache.NodeFactory.newLookupKey(java.lang.Object):java.lang.Object)
              (wrap: java.util.function.BiFunction<? super java.lang.Object, ? super com.github.benmanes.caffeine.cache.Node<K, V>, ? extends com.github.benmanes.caffeine.cache.Node<K, V>> : 0x0055: INVOKE_CUSTOM (r2v3 java.util.function.BiFunction<? super java.lang.Object, ? super com.github.benmanes.caffeine.cache.Node<K, V>, ? extends com.github.benmanes.caffeine.cache.Node<K, V>> A[REMOVE]) = 
              (r13v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r0v12 'objArr' java.lang.Object[] A[D('nodeKey' K[]), DONT_INLINE])
              (r0v14 'objArr2' java.lang.Object[] A[D('prevValue' V[]), DONT_INLINE])
              (r0v16 'oldWeight' int[] A[D('oldWeight' int[]), DONT_INLINE])
              (r15v0 'oldValue' V A[D('oldValue' V), DONT_INLINE])
              (r0v18 'now' long[] A[D('now' long[]), DONT_INLINE])
              (r14v0 'key' K A[D('key' K), DONT_INLINE])
              (r16v0 'newValue' V A[D('newValue' V), DONT_INLINE])
              (r0v8 'weight' int A[D('weight' int), DONT_INLINE])
              (r0v10 'replaced' boolean[] A[D('replaced' boolean[]), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.BiFunction.apply(java.lang.Object, java.lang.Object):java.lang.Object
             call insn: ?: INVOKE  
              (r2 I:com.github.benmanes.caffeine.cache.BoundedLocalCache)
              (r3 I:java.lang.Object[])
              (r4 I:java.lang.Object[])
              (r5 I:int[])
              (r6 I:java.lang.Object)
              (r7 I:long[])
              (r8 I:java.lang.Object)
              (r9 I:java.lang.Object)
              (r10 I:int)
              (r11 I:boolean[])
              (v10 java.lang.Object)
              (v11 com.github.benmanes.caffeine.cache.Node)
             type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$replace$12(java.lang.Object[], java.lang.Object[], int[], java.lang.Object, long[], java.lang.Object, java.lang.Object, int, boolean[], java.lang.Object, com.github.benmanes.caffeine.cache.Node):com.github.benmanes.caffeine.cache.Node)
             type: VIRTUAL call: java.util.concurrent.ConcurrentHashMap.computeIfPresent(java.lang.Object, java.util.function.BiFunction):java.lang.Object in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.replace(K, V, V):boolean, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
            Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getCodeVar()" because the return value of "jadx.core.dex.instructions.args.RegisterArg.getSVar()" is null
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:943)
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
            	... 15 more
            */
        /*
            this = this;
            r0 = r14
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r15
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r16
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r13
            com.github.benmanes.caffeine.cache.Weigher<K, V> r0 = r0.weigher
            r1 = r14
            r2 = r16
            int r0 = r0.weigh(r1, r2)
            r17 = r0
            r0 = 1
            boolean[] r0 = new boolean[r0]
            r18 = r0
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r19 = r0
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r20 = r0
            r0 = 1
            int[] r0 = new int[r0]
            r21 = r0
            r0 = 1
            long[] r0 = new long[r0]
            r22 = r0
            r0 = r13
            java.util.concurrent.ConcurrentHashMap<java.lang.Object, com.github.benmanes.caffeine.cache.Node<K, V>> r0 = r0.data
            r1 = r13
            com.github.benmanes.caffeine.cache.NodeFactory<K, V> r1 = r1.nodeFactory
            r2 = r14
            java.lang.Object r1 = r1.newLookupKey(r2)
            r2 = r13
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r15
            r7 = r22
            r8 = r14
            r9 = r16
            r10 = r17
            r11 = r18
            boolean r2 = (v10, v11) -> { // java.util.function.BiFunction.apply(java.lang.Object, java.lang.Object):java.lang.Object
                return r2.lambda$replace$12(r3, r4, r5, r6, r7, r8, r9, r10, r11, v10, v11);
            }
            java.lang.Object r0 = r0.computeIfPresent(r1, r2)
            com.github.benmanes.caffeine.cache.Node r0 = (com.github.benmanes.caffeine.cache.Node) r0
            r23 = r0
            r0 = r18
            r1 = 0
            r0 = r0[r1]
            if (r0 != 0) goto L6b
            r0 = 0
            return r0
        L6b:
            r0 = r17
            r1 = r21
            r2 = 0
            r1 = r1[r2]
            int r0 = r0 - r1
            r24 = r0
            r0 = r13
            boolean r0 = r0.expiresAfterWrite()
            if (r0 != 0) goto L80
            r0 = r24
            if (r0 == 0) goto L93
        L80:
            r0 = r13
            com.github.benmanes.caffeine.cache.BoundedLocalCache$UpdateTask r1 = new com.github.benmanes.caffeine.cache.BoundedLocalCache$UpdateTask
            r2 = r1
            r3 = r13
            r4 = r23
            r5 = r24
            r2.<init>(r4, r5)
            r0.afterWrite(r1)
            goto L9e
        L93:
            r0 = r13
            r1 = r23
            r2 = r22
            r3 = 0
            r2 = r2[r3]
            r3 = 0
            r0.afterRead(r1, r2, r3)
        L9e:
            r0 = r13
            boolean r0 = r0.hasRemovalListener()
            if (r0 == 0) goto Lb9
            r0 = r15
            r1 = r16
            if (r0 == r1) goto Lb9
            r0 = r13
            r1 = r19
            r2 = 0
            r1 = r1[r2]
            r2 = r20
            r3 = 0
            r2 = r2[r3]
            com.github.benmanes.caffeine.cache.RemovalCause r3 = com.github.benmanes.caffeine.cache.RemovalCause.REPLACED
            r0.notifyRemoval(r1, r2, r3)
        Lb9:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.replace(java.lang.Object, java.lang.Object, java.lang.Object):boolean");
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        BiFunction<? super K, ? super V, ? extends V> biFunction = key, oldValue -> {
            Object requireNonNull = Objects.requireNonNull(function.apply(function, oldValue));
            if (oldValue != requireNonNull) {
                this.writer.write(function, requireNonNull);
            }
            return requireNonNull;
        };
        for (K key2 : keySet()) {
            long[] now = {expirationTicker().read()};
            Object lookupKey = this.nodeFactory.newLookupKey(key2);
            remap(key2, lookupKey, biFunction, now, false);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, boolean recordStats, boolean recordLoad) {
        V value;
        Objects.requireNonNull(key);
        Objects.requireNonNull(mappingFunction);
        long now = expirationTicker().read();
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node != null && (value = node.getValue()) != null && !hasExpired(node, now)) {
            if (!isComputingAsync(node)) {
                tryExpireAfterRead(node, key, value, expiry(), now);
                setAccessTime(node, now);
            }
            afterRead(node, now, recordStats);
            return value;
        }
        if (recordStats) {
            mappingFunction = statsAware(mappingFunction, recordLoad);
        }
        Object keyRef = this.nodeFactory.newReferenceKey(key, keyReferenceQueue());
        return doComputeIfAbsent(key, keyRef, mappingFunction, new long[]{now}, recordStats);
    }

    /* JADX WARN: Multi-variable type inference failed */
    V doComputeIfAbsent(K key, Object keyRef, Function<? super K, ? extends V> mappingFunction, long[] now, boolean recordStats) {
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        Object[] objArr3 = new Object[1];
        Node<K, V>[] removed = new Node[1];
        int[] weight = new int[2];
        RemovalCause[] cause = new RemovalCause[1];
        Node<K, V> node = this.data.compute(keyRef, k, n -> {
            if (n == 0) {
                newValue[0] = objArr2.apply(mappingFunction);
                if (newValue[0] == null) {
                    return null;
                }
                key[0] = expirationTicker().read();
                now[1] = this.weigher.weigh(mappingFunction, newValue[0]);
                Node n = this.nodeFactory.newNode(mappingFunction, keyReferenceQueue(), newValue[0], valueReferenceQueue(), now[1], key[0]);
                setVariableTime(n, expireAfterCreate(mappingFunction, newValue[0], expiry(), key[0]));
                return n;
            }
            synchronized (n) {
                weight[0] = n.getKey();
                now[0] = n.getWeight();
                objArr3[0] = n.getValue();
                if (weight[0] == null || objArr3[0] == null) {
                    objArr[0] = RemovalCause.COLLECTED;
                } else if (hasExpired(n, key[0])) {
                    objArr[0] = RemovalCause.EXPIRED;
                } else {
                    return n;
                }
                this.writer.delete(weight[0], objArr3[0], objArr[0]);
                newValue[0] = objArr2.apply(mappingFunction);
                if (newValue[0] == null) {
                    cause[0] = n;
                    n.retire();
                    return null;
                }
                now[1] = this.weigher.weigh(mappingFunction, newValue[0]);
                n.setValue(newValue[0], valueReferenceQueue());
                n.setWeight(now[1]);
                key[0] = expirationTicker().read();
                setVariableTime(n, expireAfterCreate(mappingFunction, newValue[0], expiry(), key[0]));
                setAccessTime(n, key[0]);
                setWriteTime(n, key[0]);
                return n;
            }
        });
        if (node == null) {
            if (removed[0] != null) {
                afterWrite(new RemovalTask(removed[0]));
                return null;
            }
            return null;
        }
        if (cause[0] != null) {
            if (hasRemovalListener()) {
                notifyRemoval(objArr3[0], objArr[0], cause[0]);
            }
            statsCounter().recordEviction(weight[0], cause[0]);
        }
        if (objArr2[0] == null) {
            if (!isComputingAsync(node)) {
                tryExpireAfterRead(node, key, objArr[0], expiry(), now[0]);
                setAccessTime(node, now[0]);
            }
            afterRead(node, now[0], recordStats);
            return (V) objArr[0];
        }
        if (objArr[0] == null && cause[0] == null) {
            afterWrite(new AddTask(node, weight[1]));
        } else {
            int weightedDifference = weight[1] - weight[0];
            afterWrite(new UpdateTask(node, weightedDifference));
        }
        return (V) objArr2[0];
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(remappingFunction);
        Object lookupKey = this.nodeFactory.newLookupKey(key);
        Node<K, V> node = this.data.get(lookupKey);
        if (node == null) {
            return null;
        }
        if (node.getValue() != null) {
            long now = expirationTicker().read();
            if (!hasExpired(node, now)) {
                return (V) remap(key, lookupKey, statsAware(remappingFunction, false, true, true), new long[]{now}, false);
            }
        }
        scheduleDrainBuffers();
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LocalCache
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, boolean recordMiss, boolean recordLoad, boolean recordLoadFailure) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(remappingFunction);
        long[] now = {expirationTicker().read()};
        Object keyRef = this.nodeFactory.newReferenceKey(key, keyReferenceQueue());
        return (V) remap(key, keyRef, statsAware(remappingFunction, recordMiss, recordLoad, recordLoadFailure), now, true);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Objects.requireNonNull(remappingFunction);
        long[] now = {expirationTicker().read()};
        Object keyRef = this.nodeFactory.newReferenceKey(key, keyReferenceQueue());
        BiFunction<? super K, ? super V, ? extends V> mergeFunction = k, oldValue -> {
            return oldValue == null ? value : statsAware(value).apply(oldValue, value);
        };
        return remap(key, keyRef, mergeFunction, now, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    V remap(K key, Object keyRef, BiFunction<? super K, ? super V, ? extends V> remappingFunction, long[] now, boolean computeIfAbsent) {
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[1];
        Object[] objArr3 = new Object[1];
        Node<K, V>[] removed = new Node[1];
        int[] weight = new int[2];
        RemovalCause[] cause = new RemovalCause[1];
        Node<K, V> node = this.data.compute(keyRef, kr, n -> {
            if (n == 0) {
                if (!computeIfAbsent) {
                    return null;
                }
                computeIfAbsent[0] = objArr3.apply(remappingFunction, null);
                if (computeIfAbsent[0] == null) {
                    return null;
                }
                key[0] = expirationTicker().read();
                now[1] = this.weigher.weigh(remappingFunction, computeIfAbsent[0]);
                Node n = this.nodeFactory.newNode(weight, computeIfAbsent[0], valueReferenceQueue(), now[1], key[0]);
                setVariableTime(n, expireAfterCreate(remappingFunction, computeIfAbsent[0], expiry(), key[0]));
                return n;
            }
            synchronized (n) {
                keyRef[0] = n.getKey();
                objArr[0] = n.getValue();
                if (keyRef[0] == null || objArr[0] == null) {
                    objArr2[0] = RemovalCause.COLLECTED;
                } else if (hasExpired(n, key[0])) {
                    objArr2[0] = RemovalCause.EXPIRED;
                }
                if (objArr2[0] != null) {
                    this.writer.delete(keyRef[0], objArr[0], objArr2[0]);
                    if (!computeIfAbsent) {
                        cause[0] = n;
                        n.retire();
                        return null;
                    }
                }
                computeIfAbsent[0] = objArr3.apply(keyRef[0], objArr2[0] == null ? objArr[0] : null);
                if (computeIfAbsent[0] == null) {
                    if (objArr2[0] == null) {
                        objArr2[0] = RemovalCause.EXPLICIT;
                    }
                    cause[0] = n;
                    n.retire();
                    return null;
                }
                now[0] = n.getWeight();
                now[1] = this.weigher.weigh(remappingFunction, computeIfAbsent[0]);
                key[0] = expirationTicker().read();
                if (objArr2[0] == null) {
                    if (computeIfAbsent[0] != objArr[0]) {
                        objArr2[0] = RemovalCause.REPLACED;
                    }
                    setVariableTime(n, expireAfterUpdate(n, remappingFunction, computeIfAbsent[0], expiry(), key[0]));
                } else {
                    setVariableTime(n, expireAfterCreate(remappingFunction, computeIfAbsent[0], expiry(), key[0]));
                }
                n.setValue(computeIfAbsent[0], valueReferenceQueue());
                n.setWeight(now[1]);
                setAccessTime(n, key[0]);
                setWriteTime(n, key[0]);
                return n;
            }
        });
        if (cause[0] != null) {
            if (cause[0].wasEvicted()) {
                statsCounter().recordEviction(weight[0], cause[0]);
            }
            if (hasRemovalListener()) {
                notifyRemoval(objArr[0], objArr2[0], cause[0]);
            }
        }
        if (removed[0] != null) {
            afterWrite(new RemovalTask(removed[0]));
        } else if (node != null) {
            if (objArr2[0] == null && cause[0] == null) {
                afterWrite(new AddTask(node, weight[1]));
            } else {
                int weightedDifference = weight[1] - weight[0];
                if (expiresAfterWrite() || weightedDifference != 0) {
                    afterWrite(new UpdateTask(node, weightedDifference));
                } else {
                    if (cause[0] == null) {
                        if (!isComputingAsync(node)) {
                            tryExpireAfterRead(node, key, objArr3[0], expiry(), now[0]);
                            setAccessTime(node, now[0]);
                        }
                    } else if (cause[0] == RemovalCause.COLLECTED) {
                        scheduleDrainBuffers();
                    }
                    afterRead(node, now[0], false);
                }
            }
        }
        return (V) objArr3[0];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks == null) {
            KeySetView keySetView = new KeySetView(this);
            this.keySet = keySetView;
            return keySetView;
        }
        return ks;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs == null) {
            ValuesView valuesView = new ValuesView(this);
            this.values = valuesView;
            return valuesView;
        }
        return vs;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        if (es == null) {
            EntrySetView entrySetView = new EntrySetView(this);
            this.entrySet = entrySetView;
            return entrySetView;
        }
        return es;
    }

    Map<K, V> evictionOrder(int limit, Function<V, V> transformer, boolean hottest) {
        Supplier<Iterator<Node<K, V>>> iteratorSupplier = ()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: INVOKE_CUSTOM (r0v1 'iteratorSupplier' java.util.function.Supplier<java.util.Iterator<com.github.benmanes.caffeine.cache.Node<K, V>>>) = 
              (r5v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r8v0 'hottest' boolean A[D('hottest' boolean), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.BoundedLocalCache), (r1 I:boolean) type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$evictionOrder$18(boolean):java.util.Iterator in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.evictionOrder(int, java.util.function.Function<V, V>, boolean):java.util.Map<K, V>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
            r0 = r5
            r1 = r8
            java.util.Map<K, V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r0.lambda$evictionOrder$18(r1);
            }
            r9 = r0
            r0 = r5
            r1 = r9
            r2 = r6
            r3 = r7
            java.util.Map r0 = r0.fixedSnapshot(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.evictionOrder(int, java.util.function.Function, boolean):java.util.Map");
    }

    Map<K, V> expireAfterAccessOrder(int limit, Function<V, V> transformer, boolean oldest) {
        if (!evicts()) {
            Supplier<Iterator<Node<K, V>>> iteratorSupplier = ()
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0009: INVOKE_CUSTOM (r0v7 'iteratorSupplier' java.util.function.Supplier<java.util.Iterator<com.github.benmanes.caffeine.cache.Node<K, V>>>) = 
                  (r5v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                  (r8v0 'oldest' boolean A[D('oldest' boolean), DONT_INLINE])
                
                 handle type: INVOKE_DIRECT
                 lambda: java.util.function.Supplier.get():java.lang.Object
                 call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.BoundedLocalCache), (r1 I:boolean) type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$expireAfterAccessOrder$19(boolean):java.util.Iterator in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.expireAfterAccessOrder(int, java.util.function.Function<V, V>, boolean):java.util.Map<K, V>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
                	... 23 more
                */
            /*
                this = this;
                r0 = r5
                boolean r0 = r0.evicts()
                if (r0 != 0) goto L19
                r0 = r5
                r1 = r8
                java.util.Map<K, V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                    return r0.lambda$expireAfterAccessOrder$19(r1);
                }
                r9 = r0
                r0 = r5
                r1 = r9
                r2 = r6
                r3 = r7
                java.util.Map r0 = r0.fixedSnapshot(r1, r2, r3)
                return r0
            L19:
                r0 = r5
                r1 = r8
                java.util.Map<K, V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                    return r0.lambda$expireAfterAccessOrder$20(r1);
                }
                r9 = r0
                r0 = r5
                r1 = r9
                r2 = r6
                r3 = r7
                java.util.Map r0 = r0.fixedSnapshot(r1, r2, r3)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.expireAfterAccessOrder(int, java.util.function.Function, boolean):java.util.Map");
        }

        Map<K, V> expireAfterWriteOrder(int limit, Function<V, V> transformer, boolean oldest) {
            Supplier<Iterator<Node<K, V>>> iteratorSupplier = ()
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: INVOKE_CUSTOM (r0v1 'iteratorSupplier' java.util.function.Supplier<java.util.Iterator<com.github.benmanes.caffeine.cache.Node<K, V>>>) = 
                  (r5v0 'this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V> A[D('this' com.github.benmanes.caffeine.cache.BoundedLocalCache<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                  (r8v0 'oldest' boolean A[D('oldest' boolean), DONT_INLINE])
                
                 handle type: INVOKE_DIRECT
                 lambda: java.util.function.Supplier.get():java.lang.Object
                 call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.BoundedLocalCache), (r1 I:boolean) type: DIRECT call: com.github.benmanes.caffeine.cache.BoundedLocalCache.lambda$expireAfterWriteOrder$21(boolean):java.util.Iterator in method: com.github.benmanes.caffeine.cache.BoundedLocalCache.expireAfterWriteOrder(int, java.util.function.Function<V, V>, boolean):java.util.Map<K, V>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache.class
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
                r0 = r5
                r1 = r8
                java.util.Map<K, V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                    return r0.lambda$expireAfterWriteOrder$21(r1);
                }
                r9 = r0
                r0 = r5
                r1 = r9
                r2 = r6
                r3 = r7
                java.util.Map r0 = r0.fixedSnapshot(r1, r2, r3)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BoundedLocalCache.expireAfterWriteOrder(int, java.util.function.Function, boolean):java.util.Map");
        }

        Map<K, V> fixedSnapshot(Supplier<Iterator<Node<K, V>>> iteratorSupplier, int limit, Function<V, V> transformer) {
            Caffeine.requireArgument(limit >= 0);
            this.evictionLock.lock();
            try {
                maintenance(null);
                int initialCapacity = Math.min(limit, size());
                Iterator<Node<K, V>> iterator = iteratorSupplier.get();
                Map<K, V> map = new LinkedHashMap<>(initialCapacity);
                while (map.size() < limit && iterator.hasNext()) {
                    Node<K, V> node = iterator.next();
                    K key = node.getKey();
                    V value = transformer.apply(node.getValue());
                    if (key != null && value != null && node.isAlive()) {
                        map.put(key, value);
                    }
                }
                Map<K, V> unmodifiableMap = Collections.unmodifiableMap(map);
                this.evictionLock.unlock();
                return unmodifiableMap;
            } catch (Throwable th) {
                this.evictionLock.unlock();
                throw th;
            }
        }

        Map<K, V> variableSnapshot(boolean ascending, int limit, Function<V, V> transformer) {
            this.evictionLock.lock();
            try {
                maintenance(null);
                Map<K, V> snapshot = timerWheel().snapshot(ascending, limit, transformer);
                this.evictionLock.unlock();
                return snapshot;
            } catch (Throwable th) {
                this.evictionLock.unlock();
                throw th;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$KeySetView.class */
        public static final class KeySetView<K, V> extends AbstractSet<K> {
            final BoundedLocalCache<K, V> cache;

            KeySetView(BoundedLocalCache<K, V> cache) {
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.cache.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                this.cache.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return this.cache.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                return this.cache.remove(obj) != null;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<K> iterator() {
                return new KeyIterator(this.cache);
            }

            @Override // java.util.Collection, java.lang.Iterable, java.util.Set
            public Spliterator<K> spliterator() {
                return new KeySpliterator(this.cache);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public Object[] toArray() {
                List<Object> keys = new ArrayList<>(size());
                Iterator<K> it = iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    keys.add(key);
                }
                return keys.toArray();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public <T> T[] toArray(T[] array) {
                List<Object> keys = new ArrayList<>(size());
                Iterator<K> it = iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    keys.add(key);
                }
                return (T[]) keys.toArray(array);
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$KeyIterator.class */
        public static final class KeyIterator<K, V> implements Iterator<K> {
            final EntryIterator<K, V> iterator;

            KeyIterator(BoundedLocalCache<K, V> cache) {
                this.iterator = new EntryIterator<>(cache);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            @Override // java.util.Iterator
            public K next() {
                return this.iterator.nextKey();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.iterator.remove();
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$KeySpliterator.class */
        static final class KeySpliterator<K, V> implements Spliterator<K> {
            final Spliterator<Node<K, V>> spliterator;
            final BoundedLocalCache<K, V> cache;

            KeySpliterator(BoundedLocalCache<K, V> cache) {
                this(cache, cache.data.values().spliterator());
            }

            KeySpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
                this.spliterator = (Spliterator) Objects.requireNonNull(spliterator);
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.Spliterator
            public void forEachRemaining(Consumer<? super K> action) {
                Objects.requireNonNull(action);
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = action.getKey();
                    Object value = action.getValue();
                    long now = this.cache.expirationTicker().read();
                    if (key != null && value != null && action.isAlive() && !this.cache.hasExpired(action, now)) {
                        action.accept(key);
                    }
                };
                this.spliterator.forEachRemaining(consumer);
            }

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super K> action) {
                Objects.requireNonNull(action);
                boolean[] advanced = {false};
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = advanced.getKey();
                    Object value = advanced.getValue();
                    long now = this.cache.expirationTicker().read();
                    if (key != null && value != null && advanced.isAlive() && !this.cache.hasExpired(advanced, now)) {
                        action.accept(key);
                        action[0] = true;
                    }
                };
                while (this.spliterator.tryAdvance(consumer)) {
                    if (advanced[0]) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Spliterator
            public Spliterator<K> trySplit() {
                Spliterator<Node<K, V>> split = this.spliterator.trySplit();
                if (split == null) {
                    return null;
                }
                return new KeySpliterator(this.cache, split);
            }

            @Override // java.util.Spliterator
            public long estimateSize() {
                return this.spliterator.estimateSize();
            }

            @Override // java.util.Spliterator
            public int characteristics() {
                return 4353;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$ValuesView.class */
        static final class ValuesView<K, V> extends AbstractCollection<V> {
            final BoundedLocalCache<K, V> cache;

            ValuesView(BoundedLocalCache<K, V> cache) {
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return this.cache.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                this.cache.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object o) {
                return this.cache.containsValue(o);
            }

            @Override // java.util.Collection
            public boolean removeIf(Predicate<? super V> filter) {
                Objects.requireNonNull(filter);
                boolean removed = false;
                for (Map.Entry<K, V> entry : this.cache.entrySet()) {
                    if (filter.test((V) entry.getValue())) {
                        removed |= this.cache.remove(entry.getKey(), entry.getValue());
                    }
                }
                return removed;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<V> iterator() {
                return new ValueIterator(this.cache);
            }

            @Override // java.util.Collection, java.lang.Iterable
            public Spliterator<V> spliterator() {
                return new ValueSpliterator(this.cache);
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$ValueIterator.class */
        static final class ValueIterator<K, V> implements Iterator<V> {
            final EntryIterator<K, V> iterator;

            ValueIterator(BoundedLocalCache<K, V> cache) {
                this.iterator = new EntryIterator<>(cache);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            @Override // java.util.Iterator
            public V next() {
                return this.iterator.nextValue();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.iterator.remove();
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$ValueSpliterator.class */
        static final class ValueSpliterator<K, V> implements Spliterator<V> {
            final Spliterator<Node<K, V>> spliterator;
            final BoundedLocalCache<K, V> cache;

            ValueSpliterator(BoundedLocalCache<K, V> cache) {
                this(cache, cache.data.values().spliterator());
            }

            ValueSpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
                this.spliterator = (Spliterator) Objects.requireNonNull(spliterator);
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.Spliterator
            public void forEachRemaining(Consumer<? super V> action) {
                Objects.requireNonNull(action);
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = action.getKey();
                    Object value = action.getValue();
                    long now = this.cache.expirationTicker().read();
                    if (key != null && value != null && action.isAlive() && !this.cache.hasExpired(action, now)) {
                        action.accept(value);
                    }
                };
                this.spliterator.forEachRemaining(consumer);
            }

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super V> action) {
                Objects.requireNonNull(action);
                boolean[] advanced = {false};
                long now = this.cache.expirationTicker().read();
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = advanced.getKey();
                    Object value = advanced.getValue();
                    if (key != null && value != null && !this.cache.hasExpired(advanced, now) && advanced.isAlive()) {
                        now.accept(value);
                        action[0] = true;
                    }
                };
                while (this.spliterator.tryAdvance(consumer)) {
                    if (advanced[0]) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Spliterator
            public Spliterator<V> trySplit() {
                Spliterator<Node<K, V>> split = this.spliterator.trySplit();
                if (split == null) {
                    return null;
                }
                return new ValueSpliterator(this.cache, split);
            }

            @Override // java.util.Spliterator
            public long estimateSize() {
                return this.spliterator.estimateSize();
            }

            @Override // java.util.Spliterator
            public int characteristics() {
                return 4352;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$EntrySetView.class */
        public static final class EntrySetView<K, V> extends AbstractSet<Map.Entry<K, V>> {
            final BoundedLocalCache<K, V> cache;

            EntrySetView(BoundedLocalCache<K, V> cache) {
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.cache.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                this.cache.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                Node<K, V> node = this.cache.data.get(this.cache.nodeFactory.newLookupKey(entry.getKey()));
                return node != null && Objects.equals(node.getValue(), entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) obj;
                return this.cache.remove(entry.getKey(), entry.getValue());
            }

            @Override // java.util.Collection
            public boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
                Objects.requireNonNull(filter);
                boolean removed = false;
                Iterator<Map.Entry<K, V>> it = iterator();
                while (it.hasNext()) {
                    Map.Entry<K, V> entry = it.next();
                    if (filter.test(entry)) {
                        removed |= this.cache.remove(entry.getKey(), entry.getValue());
                    }
                }
                return removed;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new EntryIterator(this.cache);
            }

            @Override // java.util.Collection, java.lang.Iterable, java.util.Set
            public Spliterator<Map.Entry<K, V>> spliterator() {
                return new EntrySpliterator(this.cache);
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$EntryIterator.class */
        public static final class EntryIterator<K, V> implements Iterator<Map.Entry<K, V>> {
            final BoundedLocalCache<K, V> cache;
            final Iterator<Node<K, V>> iterator;
            final long now;
            K key;
            V value;
            K removalKey;
            Node<K, V> next;

            EntryIterator(BoundedLocalCache<K, V> cache) {
                this.iterator = cache.data.values().iterator();
                this.now = cache.expirationTicker().read();
                this.cache = cache;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.next != null) {
                    return true;
                }
                while (this.iterator.hasNext()) {
                    this.next = this.iterator.next();
                    this.value = this.next.getValue();
                    this.key = this.next.getKey();
                    boolean evictable = this.cache.hasExpired(this.next, this.now) || this.key == null || this.value == null;
                    if (evictable || !this.next.isAlive()) {
                        if (evictable) {
                            this.cache.scheduleDrainBuffers();
                        }
                        this.value = null;
                        this.next = null;
                        this.key = null;
                    } else {
                        return true;
                    }
                }
                return false;
            }

            K nextKey() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removalKey = this.key;
                this.value = null;
                this.next = null;
                this.key = null;
                return this.removalKey;
            }

            V nextValue() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removalKey = this.key;
                V val = this.value;
                this.value = null;
                this.next = null;
                this.key = null;
                return val;
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Map.Entry<K, V> entry = new WriteThroughEntry<>(this.cache, this.key, this.value);
                this.removalKey = this.key;
                this.value = null;
                this.next = null;
                this.key = null;
                return entry;
            }

            @Override // java.util.Iterator
            public void remove() {
                if (this.removalKey == null) {
                    throw new IllegalStateException();
                }
                this.cache.remove(this.removalKey);
                this.removalKey = null;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$EntrySpliterator.class */
        static final class EntrySpliterator<K, V> implements Spliterator<Map.Entry<K, V>> {
            final Spliterator<Node<K, V>> spliterator;
            final BoundedLocalCache<K, V> cache;

            EntrySpliterator(BoundedLocalCache<K, V> cache) {
                this(cache, cache.data.values().spliterator());
            }

            EntrySpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
                this.spliterator = (Spliterator) Objects.requireNonNull(spliterator);
                this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
            }

            @Override // java.util.Spliterator
            public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
                Objects.requireNonNull(action);
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = action.getKey();
                    Object value = action.getValue();
                    long now = this.cache.expirationTicker().read();
                    if (key != null && value != null && action.isAlive() && !this.cache.hasExpired(action, now)) {
                        action.accept(new WriteThroughEntry(this.cache, key, value));
                    }
                };
                this.spliterator.forEachRemaining(consumer);
            }

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> action) {
                Objects.requireNonNull(action);
                boolean[] advanced = {false};
                Consumer<Node<K, V>> consumer = node -> {
                    Object key = advanced.getKey();
                    Object value = advanced.getValue();
                    long now = this.cache.expirationTicker().read();
                    if (key != null && value != null && advanced.isAlive() && !this.cache.hasExpired(advanced, now)) {
                        action.accept(new WriteThroughEntry(this.cache, key, value));
                        action[0] = true;
                    }
                };
                while (this.spliterator.tryAdvance(consumer)) {
                    if (advanced[0]) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Spliterator
            public Spliterator<Map.Entry<K, V>> trySplit() {
                Spliterator<Node<K, V>> split = this.spliterator.trySplit();
                if (split == null) {
                    return null;
                }
                return new EntrySpliterator(this.cache, split);
            }

            @Override // java.util.Spliterator
            public long estimateSize() {
                return this.spliterator.estimateSize();
            }

            @Override // java.util.Spliterator
            public int characteristics() {
                return 4353;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$PerformCleanupTask.class */
        public static final class PerformCleanupTask extends ForkJoinTask<Void> implements Runnable {
            private static final long serialVersionUID = 1;
            final WeakReference<BoundedLocalCache<?, ?>> reference;

            PerformCleanupTask(BoundedLocalCache<?, ?> cache) {
                this.reference = new WeakReference<>(cache);
            }

            @Override // java.util.concurrent.ForkJoinTask
            public boolean exec() {
                try {
                    run();
                    return false;
                } catch (Throwable t) {
                    BoundedLocalCache.logger.log(Level.SEVERE, "Exception thrown when performing the maintenance task", t);
                    return false;
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                BoundedLocalCache<?, ?> cache = this.reference.get();
                if (cache != null) {
                    cache.performCleanUp(null);
                }
            }

            @Override // java.util.concurrent.ForkJoinTask
            public Void getRawResult() {
                return null;
            }

            public void setRawResult(Void v) {
            }

            public void complete(Void value) {
            }

            @Override // java.util.concurrent.ForkJoinTask
            public void completeExceptionally(Throwable ex) {
            }

            @Override // java.util.concurrent.ForkJoinTask, java.util.concurrent.Future
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }
        }

        static <K, V> SerializationProxy<K, V> makeSerializationProxy(BoundedLocalCache<?, ?> cache, boolean isWeighted) {
            SerializationProxy<K, V> proxy = new SerializationProxy<>();
            proxy.weakKeys = cache.collectKeys();
            proxy.weakValues = cache.nodeFactory.weakValues();
            proxy.softValues = cache.nodeFactory.softValues();
            proxy.isRecordingStats = cache.isRecordingStats();
            proxy.removalListener = cache.removalListener();
            proxy.ticker = cache.expirationTicker();
            proxy.writer = cache.writer;
            if (cache.expiresAfterAccess()) {
                proxy.expiresAfterAccessNanos = cache.expiresAfterAccessNanos();
            }
            if (cache.expiresAfterWrite()) {
                proxy.expiresAfterWriteNanos = cache.expiresAfterWriteNanos();
            }
            if (cache.expiresVariable()) {
                proxy.expiry = cache.expiry();
            }
            if (cache.evicts()) {
                if (isWeighted) {
                    proxy.weigher = cache.weigher;
                    proxy.maximumWeight = cache.maximum();
                } else {
                    proxy.maximumSize = cache.maximum();
                }
            }
            return proxy;
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedLocalManualCache.class */
        public static class BoundedLocalManualCache<K, V> implements LocalManualCache<K, V>, Serializable {
            private static final long serialVersionUID = 1;
            final BoundedLocalCache<K, V> cache;
            final boolean isWeighted;
            Policy<K, V> policy;

            public BoundedLocalManualCache(Caffeine<K, V> builder) {
                this(builder, null);
            }

            BoundedLocalManualCache(Caffeine<K, V> builder, CacheLoader<? super K, V> loader) {
                this.cache = LocalCacheFactory.newBoundedLocalCache(builder, loader, false);
                this.isWeighted = builder.isWeighted();
            }

            @Override // com.github.benmanes.caffeine.cache.LocalManualCache
            public BoundedLocalCache<K, V> cache() {
                return this.cache;
            }

            @Override // com.github.benmanes.caffeine.cache.Cache
            public Policy<K, V> policy() {
                if (this.policy == null) {
                    BoundedPolicy boundedPolicy = new BoundedPolicy(this.cache, Function.identity(), this.isWeighted);
                    this.policy = boundedPolicy;
                    return boundedPolicy;
                }
                return this.policy;
            }

            private void readObject(ObjectInputStream stream) throws InvalidObjectException {
                throw new InvalidObjectException("Proxy required");
            }

            Object writeReplace() {
                return BoundedLocalCache.makeSerializationProxy(this.cache, this.isWeighted);
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy.class */
        public static final class BoundedPolicy<K, V> implements Policy<K, V> {
            final BoundedLocalCache<K, V> cache;
            final Function<V, V> transformer;
            final boolean isWeighted;
            Optional<Policy.Eviction<K, V>> eviction;
            Optional<Policy.Expiration<K, V>> refreshes;
            Optional<Policy.Expiration<K, V>> afterWrite;
            Optional<Policy.Expiration<K, V>> afterAccess;
            Optional<Policy.VarExpiration<K, V>> variable;

            BoundedPolicy(BoundedLocalCache<K, V> cache, Function<V, V> transformer, boolean isWeighted) {
                this.transformer = transformer;
                this.isWeighted = isWeighted;
                this.cache = cache;
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public boolean isRecordingStats() {
                return this.cache.isRecordingStats();
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public V getIfPresentQuietly(Object key) {
                Node<K, V> node = this.cache.data.get(this.cache.nodeFactory.newLookupKey(key));
                if (node == null || this.cache.hasExpired(node, this.cache.expirationTicker().read())) {
                    return null;
                }
                return this.transformer.apply(node.getValue());
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public Optional<Policy.Eviction<K, V>> eviction() {
                if (this.cache.evicts()) {
                    if (this.eviction != null) {
                        return this.eviction;
                    }
                    Optional<Policy.Eviction<K, V>> of = Optional.of(new BoundedEviction());
                    this.eviction = of;
                    return of;
                }
                return Optional.empty();
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public Optional<Policy.Expiration<K, V>> expireAfterAccess() {
                if (!this.cache.expiresAfterAccess()) {
                    return Optional.empty();
                }
                if (this.afterAccess == null) {
                    Optional<Policy.Expiration<K, V>> of = Optional.of(new BoundedExpireAfterAccess());
                    this.afterAccess = of;
                    return of;
                }
                return this.afterAccess;
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public Optional<Policy.Expiration<K, V>> expireAfterWrite() {
                if (!this.cache.expiresAfterWrite()) {
                    return Optional.empty();
                }
                if (this.afterWrite == null) {
                    Optional<Policy.Expiration<K, V>> of = Optional.of(new BoundedExpireAfterWrite());
                    this.afterWrite = of;
                    return of;
                }
                return this.afterWrite;
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public Optional<Policy.VarExpiration<K, V>> expireVariably() {
                if (!this.cache.expiresVariable()) {
                    return Optional.empty();
                }
                if (this.variable == null) {
                    Optional<Policy.VarExpiration<K, V>> of = Optional.of(new BoundedVarExpiration());
                    this.variable = of;
                    return of;
                }
                return this.variable;
            }

            @Override // com.github.benmanes.caffeine.cache.Policy
            public Optional<Policy.Expiration<K, V>> refreshAfterWrite() {
                if (!this.cache.refreshAfterWrite()) {
                    return Optional.empty();
                }
                if (this.refreshes == null) {
                    Optional<Policy.Expiration<K, V>> of = Optional.of(new BoundedRefreshAfterWrite());
                    this.refreshes = of;
                    return of;
                }
                return this.refreshes;
            }

            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy$BoundedEviction.class */
            final class BoundedEviction implements Policy.Eviction<K, V> {
                BoundedEviction() {
                    BoundedPolicy.this = this$0;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public boolean isWeighted() {
                    return BoundedPolicy.this.isWeighted;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public OptionalInt weightOf(K key) {
                    OptionalInt of;
                    Objects.requireNonNull(key);
                    if (!BoundedPolicy.this.isWeighted) {
                        return OptionalInt.empty();
                    }
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(BoundedPolicy.this.cache.nodeFactory.newLookupKey(key));
                    if (node == null) {
                        return OptionalInt.empty();
                    }
                    synchronized (node) {
                        of = OptionalInt.of(node.getWeight());
                    }
                    return of;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public OptionalLong weightedSize() {
                    if (BoundedPolicy.this.cache.evicts() && isWeighted()) {
                        BoundedPolicy.this.cache.evictionLock.lock();
                        try {
                            return OptionalLong.of(Math.max(0L, BoundedPolicy.this.cache.weightedSize()));
                        } finally {
                            BoundedPolicy.this.cache.evictionLock.unlock();
                        }
                    }
                    return OptionalLong.empty();
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public long getMaximum() {
                    BoundedPolicy.this.cache.evictionLock.lock();
                    try {
                        return BoundedPolicy.this.cache.maximum();
                    } finally {
                        BoundedPolicy.this.cache.evictionLock.unlock();
                    }
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public void setMaximum(long maximum) {
                    BoundedPolicy.this.cache.evictionLock.lock();
                    try {
                        BoundedPolicy.this.cache.setMaximumSize(maximum);
                        BoundedPolicy.this.cache.maintenance(null);
                    } finally {
                        BoundedPolicy.this.cache.evictionLock.unlock();
                    }
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public Map<K, V> coldest(int limit) {
                    return BoundedPolicy.this.cache.evictionOrder(limit, BoundedPolicy.this.transformer, false);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Eviction
                public Map<K, V> hottest(int limit) {
                    return BoundedPolicy.this.cache.evictionOrder(limit, BoundedPolicy.this.transformer, true);
                }
            }

            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy$BoundedExpireAfterAccess.class */
            final class BoundedExpireAfterAccess implements Policy.Expiration<K, V> {
                BoundedExpireAfterAccess() {
                    BoundedPolicy.this = this$0;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public OptionalLong ageOf(K key, TimeUnit unit) {
                    Objects.requireNonNull(key);
                    Objects.requireNonNull(unit);
                    Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(lookupKey);
                    if (node == null) {
                        return OptionalLong.empty();
                    }
                    long age = BoundedPolicy.this.cache.expirationTicker().read() - node.getAccessTime();
                    if (age > BoundedPolicy.this.cache.expiresAfterAccessNanos()) {
                        return OptionalLong.empty();
                    }
                    return OptionalLong.of(unit.convert(age, TimeUnit.NANOSECONDS));
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public long getExpiresAfter(TimeUnit unit) {
                    return unit.convert(BoundedPolicy.this.cache.expiresAfterAccessNanos(), TimeUnit.NANOSECONDS);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public void setExpiresAfter(long duration, TimeUnit unit) {
                    Caffeine.requireArgument(duration >= 0);
                    BoundedPolicy.this.cache.setExpiresAfterAccessNanos(unit.toNanos(duration));
                    BoundedPolicy.this.cache.scheduleAfterWrite();
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> oldest(int limit) {
                    return BoundedPolicy.this.cache.expireAfterAccessOrder(limit, BoundedPolicy.this.transformer, true);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> youngest(int limit) {
                    return BoundedPolicy.this.cache.expireAfterAccessOrder(limit, BoundedPolicy.this.transformer, false);
                }
            }

            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy$BoundedExpireAfterWrite.class */
            public final class BoundedExpireAfterWrite implements Policy.Expiration<K, V> {
                BoundedExpireAfterWrite() {
                    BoundedPolicy.this = this$0;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public OptionalLong ageOf(K key, TimeUnit unit) {
                    Objects.requireNonNull(key);
                    Objects.requireNonNull(unit);
                    Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(lookupKey);
                    if (node == null) {
                        return OptionalLong.empty();
                    }
                    long age = BoundedPolicy.this.cache.expirationTicker().read() - node.getWriteTime();
                    if (age > BoundedPolicy.this.cache.expiresAfterWriteNanos()) {
                        return OptionalLong.empty();
                    }
                    return OptionalLong.of(unit.convert(age, TimeUnit.NANOSECONDS));
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public long getExpiresAfter(TimeUnit unit) {
                    return unit.convert(BoundedPolicy.this.cache.expiresAfterWriteNanos(), TimeUnit.NANOSECONDS);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public void setExpiresAfter(long duration, TimeUnit unit) {
                    Caffeine.requireArgument(duration >= 0);
                    BoundedPolicy.this.cache.setExpiresAfterWriteNanos(unit.toNanos(duration));
                    BoundedPolicy.this.cache.scheduleAfterWrite();
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> oldest(int limit) {
                    return BoundedPolicy.this.cache.expireAfterWriteOrder(limit, BoundedPolicy.this.transformer, true);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> youngest(int limit) {
                    return BoundedPolicy.this.cache.expireAfterWriteOrder(limit, BoundedPolicy.this.transformer, false);
                }
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy$BoundedVarExpiration.class */
            public final class BoundedVarExpiration implements Policy.VarExpiration<K, V> {
                BoundedVarExpiration() {
                    BoundedPolicy.this = this$0;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public OptionalLong getExpiresAfter(K key, TimeUnit unit) {
                    Objects.requireNonNull(key);
                    Objects.requireNonNull(unit);
                    Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(lookupKey);
                    if (node == null) {
                        return OptionalLong.empty();
                    }
                    long duration = node.getVariableTime() - BoundedPolicy.this.cache.expirationTicker().read();
                    if (duration <= 0) {
                        return OptionalLong.empty();
                    }
                    return OptionalLong.of(unit.convert(duration, TimeUnit.NANOSECONDS));
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public void setExpiresAfter(K key, long duration, TimeUnit unit) {
                    long now;
                    Objects.requireNonNull(key);
                    Objects.requireNonNull(unit);
                    Caffeine.requireArgument(duration >= 0);
                    Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(lookupKey);
                    if (node != null) {
                        long durationNanos = TimeUnit.NANOSECONDS.convert(duration, unit);
                        synchronized (node) {
                            now = BoundedPolicy.this.cache.expirationTicker().read();
                            node.setVariableTime(now + Math.min(durationNanos, 4611686018427387903L));
                        }
                        BoundedPolicy.this.cache.afterRead(node, now, false);
                    }
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public void put(K key, V value, long duration, TimeUnit unit) {
                    put(key, value, duration, unit, false);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public boolean putIfAbsent(K key, V value, long duration, TimeUnit unit) {
                    return put(key, value, duration, unit, true) == null;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v19, types: [java.util.concurrent.CompletableFuture] */
                V put(K key, V value, final long duration, final TimeUnit unit, boolean onlyIfAbsent) {
                    Objects.requireNonNull(unit);
                    Objects.requireNonNull(value);
                    Caffeine.requireArgument(duration >= 0);
                    Expiry<K, V> expiry = new Expiry<K, V>() { // from class: com.github.benmanes.caffeine.cache.BoundedLocalCache.BoundedPolicy.BoundedVarExpiration.1
                        @Override // com.github.benmanes.caffeine.cache.Expiry
                        public long expireAfterCreate(K key2, V value2, long currentTime) {
                            return unit.toNanos(duration);
                        }

                        @Override // com.github.benmanes.caffeine.cache.Expiry
                        public long expireAfterUpdate(K key2, V value2, long currentTime, long currentDuration) {
                            return unit.toNanos(duration);
                        }

                        @Override // com.github.benmanes.caffeine.cache.Expiry
                        public long expireAfterRead(K key2, V value2, long currentTime, long currentDuration) {
                            return currentDuration;
                        }
                    };
                    Expiry<K, V> expiry2 = expiry;
                    if (BoundedPolicy.this.cache.isAsync) {
                        expiry2 = new Async.AsyncExpiry<>(expiry);
                        value = CompletableFuture.completedFuture(value);
                    }
                    return BoundedPolicy.this.cache.put(key, value, expiry2, true, onlyIfAbsent);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public Map<K, V> oldest(int limit) {
                    return BoundedPolicy.this.cache.variableSnapshot(true, limit, BoundedPolicy.this.transformer);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.VarExpiration
                public Map<K, V> youngest(int limit) {
                    return BoundedPolicy.this.cache.variableSnapshot(false, limit, BoundedPolicy.this.transformer);
                }
            }

            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedPolicy$BoundedRefreshAfterWrite.class */
            final class BoundedRefreshAfterWrite implements Policy.Expiration<K, V> {
                BoundedRefreshAfterWrite() {
                    BoundedPolicy.this = this$0;
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public OptionalLong ageOf(K key, TimeUnit unit) {
                    Objects.requireNonNull(key);
                    Objects.requireNonNull(unit);
                    Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                    Node<K, V> node = BoundedPolicy.this.cache.data.get(lookupKey);
                    if (node == null) {
                        return OptionalLong.empty();
                    }
                    long age = BoundedPolicy.this.cache.expirationTicker().read() - node.getWriteTime();
                    if (age > BoundedPolicy.this.cache.refreshAfterWriteNanos()) {
                        return OptionalLong.empty();
                    }
                    return OptionalLong.of(unit.convert(age, TimeUnit.NANOSECONDS));
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public long getExpiresAfter(TimeUnit unit) {
                    return unit.convert(BoundedPolicy.this.cache.refreshAfterWriteNanos(), TimeUnit.NANOSECONDS);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public void setExpiresAfter(long duration, TimeUnit unit) {
                    Caffeine.requireArgument(duration >= 0);
                    BoundedPolicy.this.cache.setRefreshAfterWriteNanos(unit.toNanos(duration));
                    BoundedPolicy.this.cache.scheduleAfterWrite();
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> oldest(int limit) {
                    if (BoundedPolicy.this.cache.expiresAfterWrite()) {
                        return BoundedPolicy.this.expireAfterWrite().get().oldest(limit);
                    }
                    return sortedByWriteTime(limit, true);
                }

                @Override // com.github.benmanes.caffeine.cache.Policy.Expiration
                public Map<K, V> youngest(int limit) {
                    if (BoundedPolicy.this.cache.expiresAfterWrite()) {
                        return BoundedPolicy.this.expireAfterWrite().get().youngest(limit);
                    }
                    return sortedByWriteTime(limit, false);
                }

                Map<K, V> sortedByWriteTime(int limit, boolean ascending) {
                    Comparator<Node<K, V>> comparator = Comparator.comparingLong((v0) -> {
                        return v0.getWriteTime();
                    });
                    Iterator<Node<K, V>> iterator = ((Stream) BoundedPolicy.this.cache.data.values().stream().parallel()).sorted(ascending ? comparator : comparator.reversed()).limit(limit).iterator();
                    return BoundedPolicy.this.cache.fixedSnapshot(() -> {
                        return iterator;
                    }, limit, BoundedPolicy.this.transformer);
                }
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedLocalLoadingCache.class */
        public static final class BoundedLocalLoadingCache<K, V> extends BoundedLocalManualCache<K, V> implements LocalLoadingCache<K, V> {
            private static final long serialVersionUID = 1;
            final Function<K, V> mappingFunction;
            final Function<Iterable<? extends K>, Map<K, V>> bulkMappingFunction;

            public BoundedLocalLoadingCache(Caffeine<K, V> builder, CacheLoader<? super K, V> loader) {
                super(builder, loader);
                Objects.requireNonNull(loader);
                this.mappingFunction = LocalLoadingCache.newMappingFunction(loader);
                this.bulkMappingFunction = LocalLoadingCache.newBulkMappingFunction(loader);
            }

            @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
            public CacheLoader<? super K, V> cacheLoader() {
                return (CacheLoader<K, V>) this.cache.cacheLoader;
            }

            @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
            public Function<K, V> mappingFunction() {
                return this.mappingFunction;
            }

            @Override // com.github.benmanes.caffeine.cache.LocalLoadingCache
            public Function<Iterable<? extends K>, Map<K, V>> bulkMappingFunction() {
                return this.bulkMappingFunction;
            }

            private void readObject(ObjectInputStream stream) throws InvalidObjectException {
                throw new InvalidObjectException("Proxy required");
            }

            @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache.BoundedLocalManualCache
            Object writeReplace() {
                SerializationProxy<K, V> proxy = (SerializationProxy) super.writeReplace();
                if (this.cache.refreshAfterWrite()) {
                    proxy.refreshAfterWriteNanos = this.cache.refreshAfterWriteNanos();
                }
                proxy.loader = this.cache.cacheLoader;
                return proxy;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedLocalAsyncCache.class */
        public static final class BoundedLocalAsyncCache<K, V> implements LocalAsyncCache<K, V>, Serializable {
            private static final long serialVersionUID = 1;
            final BoundedLocalCache<K, CompletableFuture<V>> cache;
            final boolean isWeighted;
            ConcurrentMap<K, CompletableFuture<V>> mapView;
            LocalAsyncCache.CacheView<K, V> cacheView;
            Policy<K, V> policy;

            public BoundedLocalAsyncCache(Caffeine<K, V> builder) {
                this.cache = LocalCacheFactory.newBoundedLocalCache(builder, null, true);
                this.isWeighted = builder.isWeighted();
            }

            @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
            public BoundedLocalCache<K, CompletableFuture<V>> cache() {
                return this.cache;
            }

            @Override // com.github.benmanes.caffeine.cache.AsyncCache
            public ConcurrentMap<K, CompletableFuture<V>> asMap() {
                if (this.mapView == null) {
                    LocalAsyncCache.AsyncAsMapView asyncAsMapView = new LocalAsyncCache.AsyncAsMapView(this);
                    this.mapView = asyncAsMapView;
                    return asyncAsMapView;
                }
                return this.mapView;
            }

            @Override // com.github.benmanes.caffeine.cache.AsyncCache
            public Cache<K, V> synchronous() {
                if (this.cacheView == null) {
                    LocalAsyncCache.CacheView<K, V> cacheView = new LocalAsyncCache.CacheView<>(this);
                    this.cacheView = cacheView;
                    return cacheView;
                }
                return this.cacheView;
            }

            @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
            public Policy<K, V> policy() {
                if (this.policy == null) {
                    BoundedLocalCache<K, CompletableFuture<V>> boundedLocalCache = this.cache;
                    Function<CompletableFuture<V>, V> transformer = Async::getIfReady;
                    this.policy = new BoundedPolicy(boundedLocalCache, transformer, this.isWeighted);
                }
                return this.policy;
            }

            private void readObject(ObjectInputStream stream) throws InvalidObjectException {
                throw new InvalidObjectException("Proxy required");
            }

            Object writeReplace() {
                SerializationProxy<K, V> proxy = BoundedLocalCache.makeSerializationProxy(this.cache, this.isWeighted);
                if (this.cache.refreshAfterWrite()) {
                    proxy.refreshAfterWriteNanos = this.cache.refreshAfterWriteNanos();
                }
                proxy.async = true;
                return proxy;
            }
        }

        /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedLocalAsyncLoadingCache.class */
        public static final class BoundedLocalAsyncLoadingCache<K, V> extends LocalAsyncLoadingCache<K, V> implements Serializable {
            private static final long serialVersionUID = 1;
            final BoundedLocalCache<K, CompletableFuture<V>> cache;
            final boolean isWeighted;
            ConcurrentMap<K, CompletableFuture<V>> mapView;
            Policy<K, V> policy;

            public BoundedLocalAsyncLoadingCache(Caffeine<K, V> builder, AsyncCacheLoader<? super K, V> loader) {
                super(loader);
                this.isWeighted = builder.isWeighted();
                this.cache = LocalCacheFactory.newBoundedLocalCache(builder, new AsyncLoader(loader, builder), true);
            }

            @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
            public BoundedLocalCache<K, CompletableFuture<V>> cache() {
                return this.cache;
            }

            @Override // com.github.benmanes.caffeine.cache.AsyncCache
            public ConcurrentMap<K, CompletableFuture<V>> asMap() {
                if (this.mapView == null) {
                    LocalAsyncCache.AsyncAsMapView asyncAsMapView = new LocalAsyncCache.AsyncAsMapView(this);
                    this.mapView = asyncAsMapView;
                    return asyncAsMapView;
                }
                return this.mapView;
            }

            @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache
            public Policy<K, V> policy() {
                if (this.policy == null) {
                    BoundedLocalCache<K, CompletableFuture<V>> boundedLocalCache = this.cache;
                    Function<CompletableFuture<V>, V> transformer = Async::getIfReady;
                    this.policy = new BoundedPolicy(boundedLocalCache, transformer, this.isWeighted);
                }
                return this.policy;
            }

            private void readObject(ObjectInputStream stream) throws InvalidObjectException {
                throw new InvalidObjectException("Proxy required");
            }

            Object writeReplace() {
                SerializationProxy<K, V> proxy = BoundedLocalCache.makeSerializationProxy(this.cache, this.isWeighted);
                if (this.cache.refreshAfterWrite()) {
                    proxy.refreshAfterWriteNanos = this.cache.refreshAfterWriteNanos();
                }
                proxy.loader = (AsyncCacheLoader<K, V>) this.loader;
                proxy.async = true;
                return proxy;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedLocalCache$BoundedLocalAsyncLoadingCache$AsyncLoader.class */
            public static final class AsyncLoader<K, V> implements CacheLoader<K, V> {
                final AsyncCacheLoader<? super K, V> loader;
                final Executor executor;

                AsyncLoader(AsyncCacheLoader<? super K, V> loader, Caffeine<?, ?> builder) {
                    this.executor = (Executor) Objects.requireNonNull(builder.getExecutor());
                    this.loader = (AsyncCacheLoader) Objects.requireNonNull(loader);
                }

                /* JADX WARN: Type inference failed for: r0v2, types: [V, java.util.concurrent.CompletableFuture] */
                @Override // com.github.benmanes.caffeine.cache.CacheLoader
                public V load(K key) {
                    return this.loader.asyncLoad(key, this.executor);
                }

                /* JADX WARN: Type inference failed for: r0v2, types: [V, java.util.concurrent.CompletableFuture] */
                @Override // com.github.benmanes.caffeine.cache.CacheLoader
                public V reload(K key, V oldValue) {
                    return this.loader.asyncReload(key, oldValue, this.executor);
                }

                @Override // com.github.benmanes.caffeine.cache.CacheLoader, com.github.benmanes.caffeine.cache.AsyncCacheLoader
                public CompletableFuture<V> asyncReload(K key, V oldValue, Executor executor) {
                    return this.loader.asyncReload(key, oldValue, executor);
                }
            }
        }
    }
