package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.collections.ArraysKt;
import kotlin.collections.UIntIterator;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: UIntArray.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010��\n\u0002\b\f\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u00012B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001��¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001��¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001��¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016J\u001a\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003¢\u0006\u0004\b\u001a\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0004H\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\u0004HÖ\u0001¢\u0006\u0004\b!\u0010\u000bJ\u000f\u0010\"\u001a\u00020\u000fH\u0016¢\u0006\u0004\b#\u0010$J\u0019\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020&H\u0096\u0002ø\u0001��¢\u0006\u0004\b'\u0010(J#\u0010)\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0002H\u0086\u0002ø\u0001��¢\u0006\u0004\b,\u0010-J\u0010\u0010.\u001a\u00020/HÖ\u0001¢\u0006\u0004\b0\u00101R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\f\u0010\r\u0088\u0001\u0007\u0092\u0001\u00020\bø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u00063"}, m53d2 = {"Lkotlin/UIntArray;", "", "Lkotlin/UInt;", "size", "", "constructor-impl", "(I)[I", "storage", "", "([I)[I", "getSize-impl", "([I)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-WZ4Q5Ns", "([II)Z", "containsAll", "elements", "containsAll-impl", "([ILjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([ILjava/lang/Object;)Z", PropertyDescriptor.GET, "index", "get-pVg5ArA", "([II)I", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([I)Z", "iterator", "", "iterator-impl", "([I)Ljava/util/Iterator;", PropertyDescriptor.SET, "", "value", "set-VXSXFK8", "([III)V", "toString", "", "toString-impl", "([I)Ljava/lang/String;", "Iterator", "kotlin-stdlib"})
@Unsigned
@JvmInline
/* loaded from: Jackey Client b2.jar:kotlin/UIntArray.class */
public final class UIntArray implements Collection<UInt>, KMarkers {
    @NotNull
    private final int[] storage;

    @PublishedApi
    public static /* synthetic */ void getStorage$annotations() {
    }

    /* renamed from: toString-impl */
    public static String m1367toStringimpl(int[] arg0) {
        return "UIntArray(storage=" + Arrays.toString(arg0) + ')';
    }

    public String toString() {
        return m1367toStringimpl(this.storage);
    }

    /* renamed from: hashCode-impl */
    public static int m1368hashCodeimpl(int[] arg0) {
        return Arrays.hashCode(arg0);
    }

    @Override // java.util.Collection
    public int hashCode() {
        return m1368hashCodeimpl(this.storage);
    }

    /* renamed from: equals-impl */
    public static boolean m1369equalsimpl(int[] arg0, Object other) {
        return (other instanceof UIntArray) && Intrinsics.areEqual(arg0, ((UIntArray) other).m1373unboximpl());
    }

    @Override // java.util.Collection
    public boolean equals(Object other) {
        return m1369equalsimpl(this.storage, other);
    }

    /* renamed from: add-WZ4Q5Ns */
    public boolean m1370addWZ4Q5Ns(int element) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends UInt> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean remove(Object element) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @PublishedApi
    @NotNull
    /* renamed from: constructor-impl */
    public static int[] m1371constructorimpl(@NotNull int[] storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return storage;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ UIntArray m1372boximpl(int[] v) {
        return new UIntArray(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ int[] m1373unboximpl() {
        return this.storage;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1374equalsimpl0(int[] p1, int[] p2) {
        return Intrinsics.areEqual(p1, p2);
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof UInt)) {
            return false;
        }
        return m1364containsWZ4Q5Ns(((UInt) element).m1355unboximpl());
    }

    @Override // java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(UInt uInt) {
        return m1370addWZ4Q5Ns(uInt.m1355unboximpl());
    }

    @PublishedApi
    private /* synthetic */ UIntArray(int[] storage) {
        this.storage = storage;
    }

    @NotNull
    /* renamed from: constructor-impl */
    public static int[] m1358constructorimpl(int size) {
        return m1371constructorimpl(new int[size]);
    }

    /* renamed from: get-pVg5ArA */
    public static final int m1359getpVg5ArA(int[] arg0, int index) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return UInt.m1353constructorimpl(arg0[index]);
    }

    /* renamed from: set-VXSXFK8 */
    public static final void m1360setVXSXFK8(int[] arg0, int index, int value) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        arg0[index] = value;
    }

    /* renamed from: getSize-impl */
    public static int m1361getSizeimpl(int[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return arg0.length;
    }

    /* renamed from: getSize */
    public int size() {
        return m1361getSizeimpl(this.storage);
    }

    @NotNull
    /* renamed from: iterator-impl */
    public static java.util.Iterator<UInt> m1362iteratorimpl(int[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return new Iterator(arg0);
    }

    @Override // java.util.Collection, java.lang.Iterable
    @NotNull
    public java.util.Iterator<UInt> iterator() {
        return m1362iteratorimpl(this.storage);
    }

    /* compiled from: UIntArray.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0015\u0010\t\u001a\u00020\nH\u0016ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\r"}, m53d2 = {"Lkotlin/UIntArray$Iterator;", "Lkotlin/collections/UIntIterator;", "array", "", "([I)V", "index", "", "hasNext", "", "nextUInt", "Lkotlin/UInt;", "nextUInt-pVg5ArA", "()I", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/UIntArray$Iterator.class */
    public static final class Iterator extends UIntIterator {
        @NotNull
        private final int[] array;
        private int index;

        public Iterator(@NotNull int[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            this.array = array;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override // kotlin.collections.UIntIterator
        /* renamed from: nextUInt-pVg5ArA */
        public int mo1375nextUIntpVg5ArA() {
            if (this.index < this.array.length) {
                int[] iArr = this.array;
                int i = this.index;
                this.index = i + 1;
                return UInt.m1353constructorimpl(iArr[i]);
            }
            throw new NoSuchElementException(String.valueOf(this.index));
        }
    }

    /* renamed from: contains-WZ4Q5Ns */
    public boolean m1364containsWZ4Q5Ns(int element) {
        return m1363containsWZ4Q5Ns(this.storage, element);
    }

    /* renamed from: contains-WZ4Q5Ns */
    public static boolean m1363containsWZ4Q5Ns(int[] arg0, int element) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return ArraysKt.contains(arg0, element);
    }

    @Override // java.util.Collection
    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return m1365containsAllimpl(this.storage, elements);
    }

    /* renamed from: containsAll-impl */
    public static boolean m1365containsAllimpl(int[] arg0, @NotNull Collection<UInt> elements) {
        boolean z;
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection<UInt> $this$all$iv = elements;
        if ($this$all$iv.isEmpty()) {
            return true;
        }
        for (Object element$iv : $this$all$iv) {
            if (!(element$iv instanceof UInt) || !ArraysKt.contains(arg0, ((UInt) element$iv).m1355unboximpl())) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: isEmpty-impl */
    public static boolean m1366isEmptyimpl(int[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return arg0.length == 0;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return m1366isEmptyimpl(this.storage);
    }
}
