package kotlin.collections;

import java.util.Iterator;
import kotlin.Annotations;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: UIterators.kt */
@Annotations(message = "This class is not going to be stabilized and is to be removed soon.", level = DeprecationLevel.ERROR)
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0010\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0007\b'\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u0002H\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u0002H&ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\b\u0010\u0006ø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\t"}, m53d2 = {"Lkotlin/collections/ULongIterator;", "", "Lkotlin/ULong;", "()V", "next", "next-s-VKNKU", "()J", "nextULong", "nextULong-s-VKNKU", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/ULongIterator.class */
public abstract class ULongIterator implements Iterator<ULong>, KMarkers {
    /* renamed from: nextULong-s-VKNKU */
    public abstract long mo1454nextULongsVKNKU();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public /* bridge */ /* synthetic */ ULong next() {
        return ULong.m1433boximpl(m1687nextsVKNKU());
    }

    /* renamed from: next-s-VKNKU */
    public final long m1687nextsVKNKU() {
        return mo1454nextULongsVKNKU();
    }
}
