package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.collections.ArraysKt;
import kotlin.collections.UIterators;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;

/* compiled from: UByteArray.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010��\n\u0002\b\f\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u00012B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001��¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001��¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001��¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016J\u001a\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003¢\u0006\u0004\b\u001a\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0004H\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\u0004HÖ\u0001¢\u0006\u0004\b!\u0010\u000bJ\u000f\u0010\"\u001a\u00020\u000fH\u0016¢\u0006\u0004\b#\u0010$J\u0019\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020&H\u0096\u0002ø\u0001��¢\u0006\u0004\b'\u0010(J#\u0010)\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0002H\u0086\u0002ø\u0001��¢\u0006\u0004\b,\u0010-J\u0010\u0010.\u001a\u00020/HÖ\u0001¢\u0006\u0004\b0\u00101R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\f\u0010\r\u0088\u0001\u0007\u0092\u0001\u00020\bø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u00063"}, m53d2 = {"Lkotlin/UByteArray;", "", "Lkotlin/UByte;", "size", "", "constructor-impl", "(I)[B", "storage", "", "([B)[B", "getSize-impl", "([B)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-7apg3OU", "([BB)Z", "containsAll", "elements", "containsAll-impl", "([BLjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([BLjava/lang/Object;)Z", PropertyDescriptor.GET, "index", "get-w2LRezQ", "([BI)B", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([B)Z", "iterator", "", "iterator-impl", "([B)Ljava/util/Iterator;", PropertyDescriptor.SET, "", "value", "set-VurrAj0", "([BIB)V", "toString", "", "toString-impl", "([B)Ljava/lang/String;", "Iterator", "kotlin-stdlib"})
@Unsigned
@JvmInline
/* loaded from: Jackey Client b2.jar:kotlin/UByteArray.class */
public final class UByteArray implements Collection<UByte>, KMarkers {
    @NotNull
    private final byte[] storage;

    @PublishedApi
    public static /* synthetic */ void getStorage$annotations() {
    }

    /* renamed from: toString-impl */
    public static String m1288toStringimpl(byte[] arg0) {
        return "UByteArray(storage=" + Arrays.toString(arg0) + ')';
    }

    public String toString() {
        return m1288toStringimpl(this.storage);
    }

    /* renamed from: hashCode-impl */
    public static int m1289hashCodeimpl(byte[] arg0) {
        return Arrays.hashCode(arg0);
    }

    @Override // java.util.Collection
    public int hashCode() {
        return m1289hashCodeimpl(this.storage);
    }

    /* renamed from: equals-impl */
    public static boolean m1290equalsimpl(byte[] arg0, Object other) {
        return (other instanceof UByteArray) && Intrinsics.areEqual(arg0, ((UByteArray) other).m1294unboximpl());
    }

    @Override // java.util.Collection
    public boolean equals(Object other) {
        return m1290equalsimpl(this.storage, other);
    }

    /* renamed from: add-7apg3OU */
    public boolean m1291add7apg3OU(byte element) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends UByte> collection) {
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
    public static byte[] m1292constructorimpl(@NotNull byte[] storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return storage;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ UByteArray m1293boximpl(byte[] v) {
        return new UByteArray(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ byte[] m1294unboximpl() {
        return this.storage;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1295equalsimpl0(byte[] p1, byte[] p2) {
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
        if (!(element instanceof UByte)) {
            return false;
        }
        return m1285contains7apg3OU(((UByte) element).m1276unboximpl());
    }

    @Override // java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(UByte uByte) {
        return m1291add7apg3OU(uByte.m1276unboximpl());
    }

    @PublishedApi
    private /* synthetic */ UByteArray(byte[] storage) {
        this.storage = storage;
    }

    @NotNull
    /* renamed from: constructor-impl */
    public static byte[] m1279constructorimpl(int size) {
        return m1292constructorimpl(new byte[size]);
    }

    /* renamed from: get-w2LRezQ */
    public static final byte m1280getw2LRezQ(byte[] arg0, int index) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return UByte.m1274constructorimpl(arg0[index]);
    }

    /* renamed from: set-VurrAj0 */
    public static final void m1281setVurrAj0(byte[] arg0, int index, byte value) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        arg0[index] = value;
    }

    /* renamed from: getSize-impl */
    public static int m1282getSizeimpl(byte[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return arg0.length;
    }

    /* renamed from: getSize */
    public int size() {
        return m1282getSizeimpl(this.storage);
    }

    @NotNull
    /* renamed from: iterator-impl */
    public static java.util.Iterator<UByte> m1283iteratorimpl(byte[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return new Iterator(arg0);
    }

    @Override // java.util.Collection, java.lang.Iterable
    @NotNull
    public java.util.Iterator<UByte> iterator() {
        return m1283iteratorimpl(this.storage);
    }

    /* compiled from: UByteArray.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0015\u0010\t\u001a\u00020\nH\u0016ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\r"}, m53d2 = {"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "nextUByte-w2LRezQ", "()B", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/UByteArray$Iterator.class */
    public static final class Iterator extends UIterators {
        @NotNull
        private final byte[] array;
        private int index;

        public Iterator(@NotNull byte[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            this.array = array;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override // kotlin.collections.UIterators
        /* renamed from: nextUByte-w2LRezQ */
        public byte mo1296nextUBytew2LRezQ() {
            if (this.index < this.array.length) {
                byte[] bArr = this.array;
                int i = this.index;
                this.index = i + 1;
                return UByte.m1274constructorimpl(bArr[i]);
            }
            throw new NoSuchElementException(String.valueOf(this.index));
        }
    }

    /* renamed from: contains-7apg3OU */
    public boolean m1285contains7apg3OU(byte element) {
        return m1284contains7apg3OU(this.storage, element);
    }

    /* renamed from: contains-7apg3OU */
    public static boolean m1284contains7apg3OU(byte[] arg0, byte element) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return ArraysKt.contains(arg0, element);
    }

    @Override // java.util.Collection
    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return m1286containsAllimpl(this.storage, elements);
    }

    /* renamed from: containsAll-impl */
    public static boolean m1286containsAllimpl(byte[] arg0, @NotNull Collection<UByte> elements) {
        boolean z;
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Collection<UByte> $this$all$iv = elements;
        if ($this$all$iv.isEmpty()) {
            return true;
        }
        for (Object element$iv : $this$all$iv) {
            if (!(element$iv instanceof UByte) || !ArraysKt.contains(arg0, ((UByte) element$iv).m1276unboximpl())) {
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
    public static boolean m1287isEmptyimpl(byte[] arg0) {
        Intrinsics.checkNotNullParameter(arg0, "arg0");
        return arg0.length == 0;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return m1287isEmptyimpl(this.storage);
    }
}
