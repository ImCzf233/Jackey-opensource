package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WSLSMW.class */
public class WSLSMW<K, V> extends WSLS<K, V> {
    long maximum;
    long weightedSize;
    long windowMaximum;
    long windowWeightedSize;
    long mainProtectedMaximum;
    long mainProtectedWeightedSize;
    double stepSize;
    long adjustment;
    int hitsInSample;
    int missesInSample;
    double previousSampleHitRate;
    final FrequencySketch<K> sketch = new FrequencySketch<>();
    final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque;
    final AccessOrderDeque<Node<K, V>> accessOrderProbationDeque;
    final AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque;
    final MpscGrowableArrayQueue<Runnable> writeBuffer;

    public WSLSMW(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        AccessOrderDeque<Node<K, V>> accessOrderDeque;
        if (builder.hasInitialCapacity()) {
            long capacity = Math.min(builder.getMaximum(), builder.getInitialCapacity());
            this.sketch.ensureCapacity(capacity);
        }
        if (builder.evicts() || builder.expiresAfterAccess()) {
            accessOrderDeque = new AccessOrderDeque<>();
        } else {
            accessOrderDeque = null;
        }
        this.accessOrderWindowDeque = accessOrderDeque;
        this.accessOrderProbationDeque = new AccessOrderDeque<>();
        this.accessOrderProtectedDeque = new AccessOrderDeque<>();
        this.writeBuffer = new MpscGrowableArrayQueue<>(4, WRITE_BUFFER_MAX);
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean evicts() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long maximum() {
        return this.maximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long weightedSize() {
        return this.weightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setWeightedSize(long weightedSize) {
        this.weightedSize = weightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long windowMaximum() {
        return this.windowMaximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setWindowMaximum(long windowMaximum) {
        this.windowMaximum = windowMaximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long windowWeightedSize() {
        return this.windowWeightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setWindowWeightedSize(long windowWeightedSize) {
        this.windowWeightedSize = windowWeightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long mainProtectedMaximum() {
        return this.mainProtectedMaximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setMainProtectedMaximum(long mainProtectedMaximum) {
        this.mainProtectedMaximum = mainProtectedMaximum;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long mainProtectedWeightedSize() {
        return this.mainProtectedWeightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setMainProtectedWeightedSize(long mainProtectedWeightedSize) {
        this.mainProtectedWeightedSize = mainProtectedWeightedSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final double stepSize() {
        return this.stepSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long adjustment() {
        return this.adjustment;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setAdjustment(long adjustment) {
        this.adjustment = adjustment;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final int hitsInSample() {
        return this.hitsInSample;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setHitsInSample(int hitsInSample) {
        this.hitsInSample = hitsInSample;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final int missesInSample() {
        return this.missesInSample;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setMissesInSample(int missesInSample) {
        this.missesInSample = missesInSample;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final double previousSampleHitRate() {
        return this.previousSampleHitRate;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setPreviousSampleHitRate(double previousSampleHitRate) {
        this.previousSampleHitRate = previousSampleHitRate;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final FrequencySketch<K> frequencySketch() {
        return this.sketch;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque() {
        return this.accessOrderWindowDeque;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final AccessOrderDeque<Node<K, V>> accessOrderProbationDeque() {
        return this.accessOrderProbationDeque;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque() {
        return this.accessOrderProtectedDeque;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final MpscGrowableArrayQueue<Runnable> writeBuffer() {
        return this.writeBuffer;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean buffersWrites() {
        return true;
    }
}
