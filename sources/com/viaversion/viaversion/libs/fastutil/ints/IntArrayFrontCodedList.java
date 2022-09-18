package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongArrays;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayFrontCodedList.class */
public class IntArrayFrontCodedList extends AbstractObjectList<int[]> implements Serializable, Cloneable, RandomAccess {
    private static final long serialVersionUID = 1;

    /* renamed from: n */
    protected final int f88n;
    protected final int ratio;
    protected final int[][] array;

    /* renamed from: p */
    protected transient long[] f89p;

    /* JADX WARN: Multi-variable type inference failed */
    public IntArrayFrontCodedList(Iterator<int[]> arrays, int ratio) {
        long curSize;
        long curSize2;
        if (ratio < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + ratio + ")");
        }
        int[][] array = IntBigArrays.EMPTY_BIG_ARRAY;
        long[] p = LongArrays.EMPTY_ARRAY;
        int[] iArr = new int[2];
        long curSize3 = 0;
        int n = 0;
        int b = 0;
        while (arrays.hasNext()) {
            iArr[b] = arrays.next();
            int length = iArr[b].length;
            if (n % ratio == 0) {
                p = LongArrays.grow(p, (n / ratio) + 1);
                p[n / ratio] = curSize3;
                array = BigArrays.grow(array, curSize3 + count(length) + length, curSize3);
                curSize = curSize3 + writeInt(array, length, curSize3);
                BigArrays.copyToBig(iArr[b], 0, array, curSize, length);
            } else {
                int minLength = iArr[1 - b].length;
                minLength = length < minLength ? length : minLength;
                int common = 0;
                while (common < minLength && iArr[0][common] == iArr[1][common]) {
                    common++;
                }
                length -= common;
                array = BigArrays.grow(array, curSize3 + count(length) + count(common) + length, curSize3);
                curSize = curSize3 + writeInt(array, length, curSize3) + writeInt(array, common, curSize2);
                BigArrays.copyToBig(iArr[b], common, array, curSize, length);
            }
            curSize3 = curSize + length;
            b = 1 - b;
            n++;
        }
        this.f88n = n;
        this.ratio = ratio;
        this.array = BigArrays.trim(array, curSize3);
        this.f89p = LongArrays.trim(p, ((n + ratio) - 1) / ratio);
    }

    public IntArrayFrontCodedList(Collection<int[]> c, int ratio) {
        this(c.iterator(), ratio);
    }

    static int readInt(int[][] a, long pos) {
        return BigArrays.get(a, pos);
    }

    static int count(int length) {
        return 1;
    }

    static int writeInt(int[][] a, int length, long pos) {
        BigArrays.set(a, pos, length);
        return 1;
    }

    public int ratio() {
        return this.ratio;
    }

    public int length(int index) {
        int[][] array = this.array;
        int delta = index % this.ratio;
        long pos = this.f89p[index / this.ratio];
        int length = readInt(array, pos);
        if (delta == 0) {
            return length;
        }
        long pos2 = pos + count(length) + length;
        int length2 = readInt(array, pos2);
        int common = readInt(array, pos2 + count(length2));
        for (int i = 0; i < delta - 1; i++) {
            pos2 += count(length2) + count(common) + length2;
            length2 = readInt(array, pos2);
            common = readInt(array, pos2 + count(length2));
        }
        return length2 + common;
    }

    public int arrayLength(int index) {
        ensureRestrictedIndex(index);
        return length(index);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [long, int[][]] */
    public int extract(int index, int[] a, int offset, int length) {
        int delta = index % this.ratio;
        long startPos = this.f89p[index / this.ratio];
        ?? r0 = this.array;
        long pos = startPos;
        int arrayLength = readInt(r0, r0);
        int currLen = 0;
        if (delta == 0) {
            long pos2 = this.f89p[index / this.ratio] + count(arrayLength);
            BigArrays.copyFromBig(this.array, pos2, a, offset, Math.min(length, arrayLength));
            return arrayLength;
        }
        int common = 0;
        int i = 0;
        while (i < delta) {
            long prevArrayPos = pos + count(arrayLength) + (i != 0 ? count(common) : 0);
            pos = prevArrayPos + arrayLength;
            arrayLength = readInt(this.array, pos);
            common = readInt(this.array, pos + count(arrayLength));
            int actualCommon = Math.min(common, length);
            if (actualCommon > currLen) {
                BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
            }
            currLen = actualCommon;
            i++;
        }
        if (currLen < length) {
            BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, Math.min(arrayLength, length - currLen));
        }
        return arrayLength + common;
    }

    @Override // java.util.List
    public int[] get(int index) {
        return getArray(index);
    }

    public int[] getArray(int index) {
        ensureRestrictedIndex(index);
        int length = length(index);
        int[] a = new int[length];
        extract(index, a, 0, length);
        return a;
    }

    public int get(int index, int[] a, int offset, int length) {
        ensureRestrictedIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        int arrayLength = extract(index, a, offset, length);
        if (length >= arrayLength) {
            return arrayLength;
        }
        return length - arrayLength;
    }

    public int get(int index, int[] a) {
        return get(index, a, 0, a.length);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.f88n;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
    public ObjectListIterator<int[]> listIterator(final int start) {
        ensureIndex(start);
        return new ObjectListIterator<int[]>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntArrayFrontCodedList.1

            /* renamed from: s */
            int[] f90s = IntArrays.EMPTY_ARRAY;

            /* renamed from: i */
            int f91i;
            long pos;
            boolean inSync;

            {
                IntArrayFrontCodedList.this = this;
                this.f91i = 0;
                this.pos = 0L;
                if (start != 0) {
                    if (start == IntArrayFrontCodedList.this.f88n) {
                        this.f91i = start;
                        return;
                    }
                    this.pos = IntArrayFrontCodedList.this.f89p[start / IntArrayFrontCodedList.this.ratio];
                    int j = start % IntArrayFrontCodedList.this.ratio;
                    this.f91i = start - j;
                    while (true) {
                        int i = j;
                        j--;
                        if (i != 0) {
                            next();
                        } else {
                            return;
                        }
                    }
                }
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.f91i < IntArrayFrontCodedList.this.f88n;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.f91i > 0;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.f91i - 1;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.f91i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public int[] next() {
                int length;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.f91i % IntArrayFrontCodedList.this.ratio == 0) {
                    this.pos = IntArrayFrontCodedList.this.f89p[this.f91i / IntArrayFrontCodedList.this.ratio];
                    length = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
                    this.f90s = IntArrays.ensureCapacity(this.f90s, length, 0);
                    BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length), this.f90s, 0, length);
                    this.pos += length + IntArrayFrontCodedList.count(length);
                    this.inSync = true;
                } else if (!this.inSync) {
                    int[] iArr = this.f90s;
                    int length2 = IntArrayFrontCodedList.this.length(this.f91i);
                    length = length2;
                    this.f90s = IntArrays.ensureCapacity(iArr, length2, 0);
                    IntArrayFrontCodedList.this.extract(this.f91i, this.f90s, 0, length);
                } else {
                    int length3 = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
                    int common = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length3));
                    this.f90s = IntArrays.ensureCapacity(this.f90s, length3 + common, common);
                    BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length3) + IntArrayFrontCodedList.count(common), this.f90s, common, length3);
                    this.pos += IntArrayFrontCodedList.count(length3) + IntArrayFrontCodedList.count(common) + length3;
                    length = length3 + common;
                }
                this.f91i++;
                return IntArrays.copy(this.f90s, 0, length);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
            public int[] previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.inSync = false;
                IntArrayFrontCodedList intArrayFrontCodedList = IntArrayFrontCodedList.this;
                int i = this.f91i - 1;
                this.f91i = i;
                return intArrayFrontCodedList.getArray(i);
            }
        };
    }

    public IntArrayFrontCodedList clone() {
        return this;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("[");
        for (int i = 0; i < this.f88n; i++) {
            if (i != 0) {
                s.append(", ");
            }
            s.append(IntArrayList.wrap(getArray(i)).toString());
        }
        s.append("]");
        return s.toString();
    }

    protected long[] rebuildPointerArray() {
        int i;
        long j;
        long[] p = new long[((this.f88n + this.ratio) - 1) / this.ratio];
        int[][] a = this.array;
        long pos = 0;
        int j2 = 0;
        int skip = this.ratio - 1;
        for (int i2 = 0; i2 < this.f88n; i2++) {
            int length = readInt(a, pos);
            int count = count(length);
            skip++;
            if (skip == this.ratio) {
                skip = 0;
                int i3 = j2;
                j2++;
                p[i3] = pos;
                j = pos;
                i = count;
            } else {
                j = pos;
                i = count + count(readInt(a, pos + count));
            }
            pos = j + i + length;
        }
        return p;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.f89p = rebuildPointerArray();
    }
}
