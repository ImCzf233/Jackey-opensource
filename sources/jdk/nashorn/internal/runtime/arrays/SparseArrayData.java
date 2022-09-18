package jdk.nashorn.internal.runtime.arrays;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/SparseArrayData.class */
public class SparseArrayData extends ArrayData {
    static final int MAX_DENSE_LENGTH = 131072;
    private ArrayData underlying;
    private final long maxDenseLength;
    private TreeMap<Long, Object> sparseMap;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SparseArrayData.class.desiredAssertionStatus();
    }

    public SparseArrayData(ArrayData underlying, long length) {
        this(underlying, length, new TreeMap());
    }

    private SparseArrayData(ArrayData underlying, long length, TreeMap<Long, Object> sparseMap) {
        super(length);
        if ($assertionsDisabled || underlying.length() <= length) {
            this.underlying = underlying;
            this.maxDenseLength = underlying.length();
            this.sparseMap = sparseMap;
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new SparseArrayData(this.underlying.copy(), length(), new TreeMap((SortedMap) this.sparseMap));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        int len = (int) Math.min(length(), 2147483647L);
        int underlyingLength = (int) Math.min(len, this.underlying.length());
        Object[] objArray = new Object[len];
        for (int i = 0; i < underlyingLength; i++) {
            objArray[i] = this.underlying.getObject(i);
        }
        Arrays.fill(objArray, underlyingLength, len, ScriptRuntime.UNDEFINED);
        for (Map.Entry<Long, Object> entry : this.sparseMap.entrySet()) {
            long key = entry.getKey().longValue();
            if (key >= 2147483647L) {
                break;
            }
            objArray[(int) key] = entry.getValue();
        }
        return objArray;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        this.underlying = this.underlying.shiftLeft(by);
        TreeMap<Long, Object> newSparseMap = new TreeMap<>();
        for (Map.Entry<Long, Object> entry : this.sparseMap.entrySet()) {
            long newIndex = entry.getKey().longValue() - by;
            if (newIndex >= 0) {
                if (newIndex < this.maxDenseLength) {
                    long oldLength = this.underlying.length();
                    this.underlying = this.underlying.ensure(newIndex).set((int) newIndex, entry.getValue(), false).safeDelete(oldLength, newIndex - 1, false);
                } else {
                    newSparseMap.put(Long.valueOf(newIndex), entry.getValue());
                }
            }
        }
        this.sparseMap = newSparseMap;
        setLength(Math.max(length() - by, 0L));
        return this.sparseMap.isEmpty() ? this.underlying : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        TreeMap<Long, Object> newSparseMap = new TreeMap<>();
        long len = this.underlying.length();
        if (len + by > this.maxDenseLength) {
            long tempLength = Math.max(0L, this.maxDenseLength - by);
            long j = tempLength;
            while (true) {
                long i = j;
                if (i >= len) {
                    break;
                }
                if (this.underlying.has((int) i)) {
                    newSparseMap.put(Long.valueOf(i + by), this.underlying.getObject((int) i));
                }
                j = i + 1;
            }
            this.underlying = this.underlying.shrink((int) tempLength);
            this.underlying.setLength(tempLength);
        }
        this.underlying = this.underlying.shiftRight(by);
        for (Map.Entry<Long, Object> entry : this.sparseMap.entrySet()) {
            long newIndex = entry.getKey().longValue() + by;
            newSparseMap.put(Long.valueOf(newIndex), entry.getValue());
        }
        this.sparseMap = newSparseMap;
        setLength(length() + by);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= length()) {
            setLength(safeIndex + 1);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        if (newLength < this.underlying.length()) {
            this.underlying = this.underlying.shrink(newLength);
            this.underlying.setLength(newLength);
            this.sparseMap.clear();
            setLength(newLength);
        }
        this.sparseMap.subMap(Long.valueOf(newLength), Long.valueOf((long) LongCompanionObject.MAX_VALUE)).clear();
        setLength(newLength);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        if (index >= 0 && index < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index).set(index, value, strict).safeDelete(oldLength, index - 1, strict);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long longIndex = indexToKey(index);
            this.sparseMap.put(longIndex, value);
            setLength(Math.max(longIndex.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        if (index >= 0 && index < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index).set(index, value, strict).safeDelete(oldLength, index - 1, strict);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long longIndex = indexToKey(index);
            this.sparseMap.put(longIndex, Integer.valueOf(value));
            setLength(Math.max(longIndex.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        if (index >= 0 && index < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index).set(index, value, strict).safeDelete(oldLength, index - 1, strict);
            setLength(Math.max(this.underlying.length(), length()));
        } else {
            Long longIndex = indexToKey(index);
            this.sparseMap.put(longIndex, Double.valueOf(value));
            setLength(Math.max(longIndex.longValue() + 1, length()));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(int index) {
        this.underlying.setEmpty(index);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(long lo, long hi) {
        this.underlying.setEmpty(lo, hi);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Type getOptimisticType() {
        return this.underlying.getOptimisticType();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        if (index >= 0 && index < this.maxDenseLength) {
            return this.underlying.getInt(index);
        }
        return JSType.toInt32(this.sparseMap.get(indexToKey(index)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int index, int programPoint) {
        if (index >= 0 && index < this.maxDenseLength) {
            return this.underlying.getIntOptimistic(index, programPoint);
        }
        return JSType.toInt32Optimistic(this.sparseMap.get(indexToKey(index)), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int index) {
        if (index >= 0 && index < this.maxDenseLength) {
            return this.underlying.getDouble(index);
        }
        return JSType.toNumber(this.sparseMap.get(indexToKey(index)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int index, int programPoint) {
        if (index >= 0 && index < this.maxDenseLength) {
            return this.underlying.getDouble(index);
        }
        return JSType.toNumberOptimistic(this.sparseMap.get(indexToKey(index)), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int index) {
        if (index >= 0 && index < this.maxDenseLength) {
            return this.underlying.getObject(index);
        }
        Long key = indexToKey(index);
        if (this.sparseMap.containsKey(key)) {
            return this.sparseMap.get(key);
        }
        return ScriptRuntime.UNDEFINED;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        if (index < 0 || index >= this.maxDenseLength) {
            return this.sparseMap.containsKey(indexToKey(index));
        }
        return ((long) index) < this.underlying.length() && this.underlying.has(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        if (index >= 0 && index < this.maxDenseLength) {
            if (index < this.underlying.length()) {
                this.underlying = this.underlying.delete(index);
            }
        } else {
            this.sparseMap.remove(indexToKey(index));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        if (fromIndex < this.maxDenseLength && fromIndex < this.underlying.length()) {
            this.underlying = this.underlying.delete(fromIndex, Math.min(toIndex, this.underlying.length() - 1));
        }
        if (toIndex >= this.maxDenseLength) {
            this.sparseMap.subMap(Long.valueOf(fromIndex), true, Long.valueOf(toIndex), true).clear();
        }
        return this;
    }

    private static Long indexToKey(int index) {
        return Long.valueOf(ArrayIndex.toLongIndex(index));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class<?> type) {
        this.underlying = this.underlying.convert(type);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        long len = length();
        long underlyingLen = this.underlying.length();
        if (len == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        if (len == underlyingLen) {
            Object result = this.underlying.pop();
            setLength(this.underlying.length());
            return result;
        }
        setLength(len - 1);
        Long key = Long.valueOf(len - 1);
        return this.sparseMap.containsKey(key) ? this.sparseMap.remove(key) : ScriptRuntime.UNDEFINED;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        if ($assertionsDisabled || to <= length()) {
            long start = from < 0 ? from + length() : from;
            long newLength = to - start;
            long underlyingLength = this.underlying.length();
            if (start >= 0 && to <= this.maxDenseLength) {
                if (newLength <= underlyingLength) {
                    return this.underlying.slice(from, to);
                }
                return this.underlying.slice(from, to).ensure(newLength - 1).delete(underlyingLength, newLength);
            }
            ArrayData sliced = EMPTY_ARRAY;
            ArrayData sliced2 = sliced.ensure(newLength - 1);
            long j = start;
            while (true) {
                long i = j;
                if (i >= to) {
                    break;
                }
                if (has((int) i)) {
                    sliced2 = sliced2.set((int) (i - start), getObject((int) i), false);
                }
                j = nextIndex(i);
            }
            if (!$assertionsDisabled && sliced2.length() != newLength) {
                throw new AssertionError();
            }
            return sliced2;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public long nextIndex(long index) {
        if (index < this.underlying.length() - 1) {
            return this.underlying.nextIndex(index);
        }
        Long nextKey = this.sparseMap.higherKey(Long.valueOf(index));
        if (nextKey != null) {
            return nextKey.longValue();
        }
        return length();
    }
}
