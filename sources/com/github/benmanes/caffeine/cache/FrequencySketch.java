package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FrequencySketch.class */
final class FrequencySketch<E> {
    static final long[] SEED = {-4348849565147123417L, -5435081209227447693L, -7286425919675154353L, -3750763034362895579L};
    static final long RESET_MASK = 8608480567731124087L;
    static final long ONE_MASK = 1229782938247303441L;
    int sampleSize;
    int tableMask;
    long[] table;
    int size;

    public void ensureCapacity(long maximumSize) {
        Caffeine.requireArgument(maximumSize >= 0);
        int maximum = (int) Math.min(maximumSize, 1073741823L);
        if (this.table != null && this.table.length >= maximum) {
            return;
        }
        this.table = new long[maximum == 0 ? 1 : Caffeine.ceilingPowerOfTwo(maximum)];
        this.tableMask = Math.max(0, this.table.length - 1);
        this.sampleSize = maximumSize == 0 ? 10 : 10 * maximum;
        if (this.sampleSize <= 0) {
            this.sampleSize = Integer.MAX_VALUE;
        }
        this.size = 0;
    }

    public boolean isNotInitialized() {
        return this.table == null;
    }

    public int frequency(E e) {
        if (isNotInitialized()) {
            return 0;
        }
        int hash = spread(e.hashCode());
        int start = (hash & 3) << 2;
        int frequency = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            int index = indexOf(hash, i);
            int count = (int) ((this.table[index] >>> ((start + i) << 2)) & 15);
            frequency = Math.min(frequency, count);
        }
        return frequency;
    }

    public void increment(E e) {
        if (isNotInitialized()) {
            return;
        }
        int hash = spread(e.hashCode());
        int start = (hash & 3) << 2;
        int index0 = indexOf(hash, 0);
        int index1 = indexOf(hash, 1);
        int index2 = indexOf(hash, 2);
        int index3 = indexOf(hash, 3);
        boolean added = incrementAt(index0, start);
        if (!(added | incrementAt(index1, start + 1) | incrementAt(index2, start + 2)) && !incrementAt(index3, start + 3)) {
            return;
        }
        int i = this.size + 1;
        this.size = i;
        if (i == this.sampleSize) {
            reset();
        }
    }

    boolean incrementAt(int i, int j) {
        int offset = j << 2;
        long mask = 15 << offset;
        if ((this.table[i] & mask) != mask) {
            long[] jArr = this.table;
            jArr[i] = jArr[i] + (1 << offset);
            return true;
        }
        return false;
    }

    void reset() {
        int count = 0;
        for (int i = 0; i < this.table.length; i++) {
            count += Long.bitCount(this.table[i] & ONE_MASK);
            this.table[i] = (this.table[i] >>> 1) & RESET_MASK;
        }
        this.size = (this.size >>> 1) - (count >>> 2);
    }

    int indexOf(int item, int i) {
        long hash = (item + SEED[i]) * SEED[i];
        return ((int) (hash + (hash >>> 32))) & this.tableMask;
    }

    int spread(int x) {
        int x2 = ((x >>> 16) ^ x) * 73244475;
        int x3 = ((x2 >>> 16) ^ x2) * 73244475;
        return (x3 >>> 16) ^ x3;
    }
}
