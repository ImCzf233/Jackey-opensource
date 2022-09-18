package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/TimerWheel.class */
public final class TimerWheel<K, V> {
    static final int[] BUCKETS = {64, 64, 32, 4, 1};
    static final long[] SPANS = {Caffeine.ceilingPowerOfTwo(TimeUnit.SECONDS.toNanos(1)), Caffeine.ceilingPowerOfTwo(TimeUnit.MINUTES.toNanos(1)), Caffeine.ceilingPowerOfTwo(TimeUnit.HOURS.toNanos(1)), Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1)), BUCKETS[3] * Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1)), BUCKETS[3] * Caffeine.ceilingPowerOfTwo(TimeUnit.DAYS.toNanos(1))};
    static final long[] SHIFT = {Long.numberOfTrailingZeros(SPANS[0]), Long.numberOfTrailingZeros(SPANS[1]), Long.numberOfTrailingZeros(SPANS[2]), Long.numberOfTrailingZeros(SPANS[3]), Long.numberOfTrailingZeros(SPANS[4])};
    final BoundedLocalCache<K, V> cache;
    final Node<K, V>[][] wheel = new Node[BUCKETS.length][1];
    long nanos;

    public TimerWheel(BoundedLocalCache<K, V> cache) {
        this.cache = (BoundedLocalCache) Objects.requireNonNull(cache);
        for (int i = 0; i < this.wheel.length; i++) {
            this.wheel[i] = new Node[BUCKETS[i]];
            for (int j = 0; j < this.wheel[i].length; j++) {
                this.wheel[i][j] = new Sentinel();
            }
        }
    }

    public void advance(long currentTimeNanos) {
        long previousTimeNanos = this.nanos;
        try {
            this.nanos = currentTimeNanos;
            for (int i = 0; i < SHIFT.length; i++) {
                long previousTicks = previousTimeNanos >>> ((int) SHIFT[i]);
                long currentTicks = currentTimeNanos >>> ((int) SHIFT[i]);
                if (currentTicks - previousTicks <= 0) {
                    break;
                }
                expire(i, previousTicks, currentTicks);
            }
        } catch (Throwable t) {
            this.nanos = previousTimeNanos;
            throw t;
        }
    }

    void expire(int index, long previousTicks, long currentTicks) {
        int end;
        int start;
        Node<K, V>[] timerWheel = this.wheel[index];
        if (currentTicks - previousTicks >= timerWheel.length) {
            end = timerWheel.length;
            start = 0;
        } else {
            long mask = SPANS[index] - 1;
            start = (int) (previousTicks & mask);
            end = 1 + ((int) (currentTicks & mask));
        }
        int mask2 = timerWheel.length - 1;
        for (int i = start; i < end; i++) {
            Node<K, V> sentinel = timerWheel[i & mask2];
            Node<K, V> prev = sentinel.getPreviousInVariableOrder();
            Node<K, V> node = sentinel.getNextInVariableOrder();
            sentinel.setPreviousInVariableOrder(sentinel);
            sentinel.setNextInVariableOrder(sentinel);
            while (node != sentinel) {
                Node<K, V> next = node.getNextInVariableOrder();
                node.setPreviousInVariableOrder(null);
                node.setNextInVariableOrder(null);
                try {
                    if (node.getVariableTime() - this.nanos > 0 || !this.cache.evictEntry(node, RemovalCause.EXPIRED, this.nanos)) {
                        schedule(node);
                    }
                    node = next;
                } catch (Throwable t) {
                    node.setPreviousInVariableOrder(sentinel.getPreviousInVariableOrder());
                    node.setNextInVariableOrder(next);
                    sentinel.getPreviousInVariableOrder().setNextInVariableOrder(node);
                    sentinel.setPreviousInVariableOrder(prev);
                    throw t;
                }
            }
        }
    }

    public void schedule(Node<K, V> node) {
        Node<K, V> sentinel = findBucket(node.getVariableTime());
        link(sentinel, node);
    }

    public void reschedule(Node<K, V> node) {
        if (node.getNextInVariableOrder() != null) {
            unlink(node);
            schedule(node);
        }
    }

    public void deschedule(Node<K, V> node) {
        unlink(node);
        node.setNextInVariableOrder(null);
        node.setPreviousInVariableOrder(null);
    }

    Node<K, V> findBucket(long time) {
        long duration = time - this.nanos;
        int length = this.wheel.length - 1;
        for (int i = 0; i < length; i++) {
            if (duration < SPANS[i + 1]) {
                long ticks = time >>> ((int) SHIFT[i]);
                int index = (int) (ticks & (this.wheel[i].length - 1));
                return this.wheel[i][index];
            }
        }
        return this.wheel[length][0];
    }

    void link(Node<K, V> sentinel, Node<K, V> node) {
        node.setPreviousInVariableOrder(sentinel.getPreviousInVariableOrder());
        node.setNextInVariableOrder(sentinel);
        sentinel.getPreviousInVariableOrder().setNextInVariableOrder(node);
        sentinel.setPreviousInVariableOrder(node);
    }

    void unlink(Node<K, V> node) {
        Node<K, V> next = node.getNextInVariableOrder();
        if (next != null) {
            Node<K, V> prev = node.getPreviousInVariableOrder();
            next.setPreviousInVariableOrder(prev);
            prev.setNextInVariableOrder(next);
        }
    }

    public long getExpirationDelay() {
        for (int i = 0; i < SHIFT.length; i++) {
            Node<K, V>[] timerWheel = this.wheel[i];
            long ticks = this.nanos >>> ((int) SHIFT[i]);
            long spanMask = SPANS[i] - 1;
            int start = (int) (ticks & spanMask);
            int end = start + timerWheel.length;
            int mask = timerWheel.length - 1;
            for (int j = start; j < end; j++) {
                Node<K, V> sentinel = timerWheel[j & mask];
                Node<K, V> next = sentinel.getNextInVariableOrder();
                if (next != sentinel) {
                    long buckets = j - start;
                    long delay = (buckets << ((int) SHIFT[i])) - (this.nanos & spanMask);
                    long delay2 = delay > 0 ? delay : SPANS[i];
                    for (int k = i + 1; k < SHIFT.length; k++) {
                        long nextDelay = peekAhead(k);
                        delay2 = Math.min(delay2, nextDelay);
                    }
                    return delay2;
                }
            }
        }
        return LongCompanionObject.MAX_VALUE;
    }

    long peekAhead(int i) {
        long ticks = this.nanos >>> ((int) SHIFT[i]);
        Node<K, V>[] timerWheel = this.wheel[i];
        long spanMask = SPANS[i] - 1;
        int mask = timerWheel.length - 1;
        int probe = (int) ((ticks + 1) & mask);
        Node<K, V> sentinel = timerWheel[probe];
        Node<K, V> next = sentinel.getNextInVariableOrder();
        return next == sentinel ? LongCompanionObject.MAX_VALUE : SPANS[i] - (this.nanos & spanMask);
    }

    public Map<K, V> snapshot(boolean ascending, int limit, Function<V, V> transformer) {
        Caffeine.requireArgument(limit >= 0);
        Map<K, V> map = new LinkedHashMap<>(Math.min(limit, this.cache.size()));
        int startLevel = ascending ? 0 : this.wheel.length - 1;
        for (int i = 0; i < this.wheel.length; i++) {
            int indexOffset = ascending ? i : -i;
            int index = startLevel + indexOffset;
            int ticks = (int) (this.nanos >>> ((int) SHIFT[index]));
            int bucketMask = this.wheel[index].length - 1;
            int startBucket = (ticks & bucketMask) + (ascending ? 1 : 0);
            for (int j = 0; j < this.wheel[index].length; j++) {
                int bucketOffset = ascending ? j : -j;
                Node<K, V> sentinel = this.wheel[index][(startBucket + bucketOffset) & bucketMask];
                Node<K, V> traverse = traverse(ascending, sentinel);
                while (true) {
                    Node<K, V> node = traverse;
                    if (node != sentinel && map.size() < limit) {
                        K key = node.getKey();
                        V value = transformer.apply(node.getValue());
                        if (key != null && value != null && node.isAlive()) {
                            map.put(key, value);
                        }
                        traverse = traverse(ascending, node);
                    }
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }

    static <K, V> Node<K, V> traverse(boolean ascending, Node<K, V> node) {
        return ascending ? node.getNextInVariableOrder() : node.getPreviousInVariableOrder();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.wheel.length; i++) {
            Map<Integer, List<K>> buckets = new TreeMap<>();
            for (int j = 0; j < this.wheel[i].length; j++) {
                List<K> events = new ArrayList<>();
                Node<K, V> nextInVariableOrder = this.wheel[i][j].getNextInVariableOrder();
                while (true) {
                    Node<K, V> node = nextInVariableOrder;
                    if (node == this.wheel[i][j]) {
                        break;
                    }
                    events.add(node.getKey());
                    nextInVariableOrder = node.getNextInVariableOrder();
                }
                if (!events.isEmpty()) {
                    buckets.put(Integer.valueOf(j), events);
                }
            }
            builder.append("Wheel #").append(i + 1).append(": ").append(buckets).append('\n');
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/TimerWheel$Sentinel.class */
    public static final class Sentinel<K, V> extends Node<K, V> {
        Node<K, V> next = this;
        Node<K, V> prev = this;

        Sentinel() {
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public Node<K, V> getPreviousInVariableOrder() {
            return this.prev;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public void setPreviousInVariableOrder(Node<K, V> prev) {
            this.prev = prev;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public Node<K, V> getNextInVariableOrder() {
            return this.next;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public void setNextInVariableOrder(Node<K, V> next) {
            this.next = next;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public K getKey() {
            return null;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public Object getKeyReference() {
            throw new UnsupportedOperationException();
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public V getValue() {
            return null;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public Object getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public void setValue(V value, ReferenceQueue<V> referenceQueue) {
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public boolean containsValue(Object value) {
            return false;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public boolean isAlive() {
            return false;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public boolean isRetired() {
            return false;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public boolean isDead() {
            return false;
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public void retire() {
        }

        @Override // com.github.benmanes.caffeine.cache.Node
        public void die() {
        }
    }
}
