package com.github.benmanes.caffeine.cache;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/StripedBuffer.class */
abstract class StripedBuffer<E> implements Buffer<E> {
    static final long TABLE_BUSY = UnsafeAccess.objectFieldOffset(StripedBuffer.class, "tableBusy");
    static final long PROBE = UnsafeAccess.objectFieldOffset(Thread.class, "threadLocalRandomProbe");
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final int MAXIMUM_TABLE_SIZE = 4 * Caffeine.ceilingPowerOfTwo(NCPU);
    static final int ATTEMPTS = 3;
    volatile transient Buffer<E>[] table;
    volatile transient int tableBusy;

    protected abstract Buffer<E> create(E e);

    final boolean casTableBusy() {
        return UnsafeAccess.UNSAFE.compareAndSwapInt(this, TABLE_BUSY, 0, 1);
    }

    static final int getProbe() {
        return UnsafeAccess.UNSAFE.getInt(Thread.currentThread(), PROBE);
    }

    static final int advanceProbe(int probe) {
        int probe2 = probe ^ (probe << 13);
        int probe3 = probe2 ^ (probe2 >>> 17);
        int probe4 = probe3 ^ (probe3 << 5);
        UnsafeAccess.UNSAFE.putInt(Thread.currentThread(), PROBE, probe4);
        return probe4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003e, code lost:
        if (r0 == false) goto L14;
     */
    @Override // com.github.benmanes.caffeine.cache.Buffer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int offer(E r5) {
        /*
            r4 = this;
            r0 = 0
            r7 = r0
            r0 = 1
            r9 = r0
            r0 = r4
            com.github.benmanes.caffeine.cache.Buffer<E>[] r0 = r0.table
            r10 = r0
            r0 = r10
            if (r0 == 0) goto L41
            r0 = r10
            int r0 = r0.length
            r1 = 1
            int r0 = r0 - r1
            r1 = r0
            r6 = r1
            if (r0 < 0) goto L41
            r0 = r10
            int r1 = getProbe()
            r2 = r6
            r1 = r1 & r2
            r0 = r0[r1]
            r1 = r0
            r8 = r1
            if (r0 == 0) goto L41
            r0 = r8
            r1 = r5
            int r0 = r0.offer(r1)
            r1 = r0
            r7 = r1
            r1 = -1
            if (r0 == r1) goto L3a
            r0 = 1
            goto L3b
        L3a:
            r0 = 0
        L3b:
            r1 = r0
            r9 = r1
            if (r0 != 0) goto L48
        L41:
            r0 = r4
            r1 = r5
            r2 = r9
            r0.expandOrRetry(r1, r2)
        L48:
            r0 = r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.StripedBuffer.offer(java.lang.Object):int");
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public void drainTo(Consumer<E> consumer) {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return;
        }
        for (Buffer<E> buffer : buffers) {
            if (buffer != null) {
                buffer.drainTo(consumer);
            }
        }
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int reads() {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return 0;
        }
        int reads = 0;
        for (Buffer<E> buffer : buffers) {
            if (buffer != null) {
                reads += buffer.reads();
            }
        }
        return reads;
    }

    @Override // com.github.benmanes.caffeine.cache.Buffer
    public int writes() {
        Buffer<E>[] buffers = this.table;
        if (buffers == null) {
            return 0;
        }
        int writes = 0;
        for (Buffer<E> buffer : buffers) {
            if (buffer != null) {
                writes += buffer.writes();
            }
        }
        return writes;
    }

    final void expandOrRetry(E e, boolean wasUncontended) {
        int n;
        int mask;
        int probe = getProbe();
        int h = probe;
        if (probe == 0) {
            ThreadLocalRandom.current();
            h = getProbe();
            wasUncontended = true;
        }
        boolean collide = false;
        for (int attempt = 0; attempt < 3; attempt++) {
            Buffer<E>[] buffers = this.table;
            if (buffers != null && (n = buffers.length) > 0) {
                Buffer<E> buffer = buffers[(n - 1) & h];
                if (buffer == null) {
                    if (this.tableBusy == 0 && casTableBusy()) {
                        boolean created = false;
                        try {
                            Buffer<E>[] rs = this.table;
                            if (rs != null && (mask = rs.length) > 0) {
                                int j = (mask - 1) & h;
                                if (rs[j] == null) {
                                    rs[j] = create(e);
                                    created = true;
                                }
                            }
                            this.tableBusy = 0;
                            if (created) {
                                return;
                            }
                        } finally {
                        }
                    } else {
                        collide = false;
                        h = advanceProbe(h);
                    }
                } else {
                    if (!wasUncontended) {
                        wasUncontended = true;
                    } else if (buffer.offer(e) == -1) {
                        if (n >= MAXIMUM_TABLE_SIZE || this.table != buffers) {
                            collide = false;
                        } else if (!collide) {
                            collide = true;
                        } else if (this.tableBusy == 0 && casTableBusy()) {
                            try {
                                if (this.table == buffers) {
                                    this.table = (Buffer[]) Arrays.copyOf(buffers, n << 1);
                                }
                                this.tableBusy = 0;
                                collide = false;
                            } finally {
                            }
                        }
                    } else {
                        return;
                    }
                    h = advanceProbe(h);
                }
            } else if (this.tableBusy == 0 && this.table == buffers && casTableBusy()) {
                boolean init = false;
                try {
                    if (this.table == buffers) {
                        this.table = new Buffer[]{create(e)};
                        init = true;
                    }
                    this.tableBusy = 0;
                    if (init) {
                        return;
                    }
                } finally {
                    this.tableBusy = 0;
                }
            }
        }
    }
}
