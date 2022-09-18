package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import com.github.benmanes.caffeine.cache.WriteOrderDeque;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Node.class */
public abstract class Node<K, V> implements AccessOrderDeque.AccessOrder<Node<K, V>>, WriteOrderDeque.WriteOrder<Node<K, V>> {
    public static final int WINDOW = 0;
    public static final int PROBATION = 1;
    public static final int PROTECTED = 2;

    public abstract K getKey();

    public abstract Object getKeyReference();

    public abstract V getValue();

    public abstract Object getValueReference();

    @GuardedBy("this")
    public abstract void setValue(V v, ReferenceQueue<V> referenceQueue);

    public abstract boolean containsValue(Object obj);

    public abstract boolean isAlive();

    @GuardedBy("this")
    public abstract boolean isRetired();

    @GuardedBy("this")
    public abstract boolean isDead();

    @GuardedBy("this")
    public abstract void retire();

    @GuardedBy("this")
    public abstract void die();

    @Override // com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    @GuardedBy("evictionLock")
    public /* bridge */ /* synthetic */ void setNextInAccessOrder(AccessOrderDeque.AccessOrder accessOrder) {
        setNextInAccessOrder((Node) ((Node) accessOrder));
    }

    @Override // com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    @GuardedBy("evictionLock")
    public /* bridge */ /* synthetic */ void setPreviousInAccessOrder(AccessOrderDeque.AccessOrder accessOrder) {
        setPreviousInAccessOrder((Node) ((Node) accessOrder));
    }

    @GuardedBy("evictionLock")
    public /* bridge */ /* synthetic */ void setNextInWriteOrder(WriteOrderDeque.WriteOrder writeOrder) {
        setNextInWriteOrder((Node) ((Node) writeOrder));
    }

    @GuardedBy("evictionLock")
    public /* bridge */ /* synthetic */ void setPreviousInWriteOrder(WriteOrderDeque.WriteOrder writeOrder) {
        setPreviousInWriteOrder((Node) ((Node) writeOrder));
    }

    @GuardedBy("this")
    public int getWeight() {
        return 1;
    }

    @GuardedBy("this")
    public void setWeight(int weight) {
    }

    @GuardedBy("evictionLock")
    public int getPolicyWeight() {
        return 1;
    }

    @GuardedBy("evictionLock")
    public void setPolicyWeight(int weight) {
    }

    public long getVariableTime() {
        return 0L;
    }

    public void setVariableTime(long time) {
    }

    public boolean casVariableTime(long expect, long update) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public Node<K, V> getPreviousInVariableOrder() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public void setPreviousInVariableOrder(Node<K, V> prev) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public Node<K, V> getNextInVariableOrder() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public void setNextInVariableOrder(Node<K, V> prev) {
        throw new UnsupportedOperationException();
    }

    public boolean inWindow() {
        return getQueueType() == 0;
    }

    public boolean inMainProbation() {
        return getQueueType() == 1;
    }

    public boolean inMainProtected() {
        return getQueueType() == 2;
    }

    public void makeWindow() {
        setQueueType(0);
    }

    public void makeMainProbation() {
        setQueueType(1);
    }

    public void makeMainProtected() {
        setQueueType(2);
    }

    public int getQueueType() {
        return 0;
    }

    public void setQueueType(int queueType) {
        throw new UnsupportedOperationException();
    }

    public long getAccessTime() {
        return 0L;
    }

    public void setAccessTime(long time) {
    }

    @Override // com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    @GuardedBy("evictionLock")
    public Node<K, V> getPreviousInAccessOrder() {
        return null;
    }

    @GuardedBy("evictionLock")
    public void setPreviousInAccessOrder(Node<K, V> prev) {
        throw new UnsupportedOperationException();
    }

    @Override // com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    @GuardedBy("evictionLock")
    public Node<K, V> getNextInAccessOrder() {
        return null;
    }

    @GuardedBy("evictionLock")
    public void setNextInAccessOrder(Node<K, V> next) {
        throw new UnsupportedOperationException();
    }

    public long getWriteTime() {
        return 0L;
    }

    public void setWriteTime(long time) {
    }

    public boolean casWriteTime(long expect, long update) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public Node<K, V> getPreviousInWriteOrder() {
        return null;
    }

    @GuardedBy("evictionLock")
    public void setPreviousInWriteOrder(Node<K, V> prev) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("evictionLock")
    public Node<K, V> getNextInWriteOrder() {
        return null;
    }

    @GuardedBy("evictionLock")
    public void setNextInWriteOrder(Node<K, V> next) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        Object[] objArr = new Object[12];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = getKey();
        objArr[2] = getValue();
        objArr[3] = Integer.valueOf(getWeight());
        objArr[4] = Integer.valueOf(getQueueType());
        objArr[5] = Long.valueOf(getAccessTime());
        objArr[6] = Long.valueOf(getWriteTime());
        objArr[7] = Long.valueOf(getVariableTime());
        objArr[8] = Boolean.valueOf(getPreviousInAccessOrder() != null);
        objArr[9] = Boolean.valueOf(getNextInAccessOrder() != null);
        objArr[10] = Boolean.valueOf(getPreviousInWriteOrder() != null);
        objArr[11] = Boolean.valueOf(getNextInWriteOrder() != null);
        return String.format("%s=[key=%s, value=%s, weight=%d, queueType=%,d, accessTimeNS=%,d, writeTimeNS=%,d, varTimeNs=%,d, prevInAccess=%s, nextInAccess=%s, prevInWrite=%s, nextInWrite=%s]", objArr);
    }
}
