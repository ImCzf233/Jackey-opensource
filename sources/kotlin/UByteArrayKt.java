package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UByteArray.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u001a\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a0\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\u0087\bø\u0001��¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u009920\u0001¨\u0006\f"}, m53d2 = {"UByteArray", "Lkotlin/UByteArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UByte;", "(ILkotlin/jvm/functions/Function1;)[B", "ubyteArrayOf", "elements", "ubyteArrayOf-GBYM_sE", "([B)[B", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/UByteArrayKt.class */
public final class UByteArrayKt {
    @SinceKotlin(version = "1.3")
    @Unsigned
    @InlineOnly
    private static final byte[] UByteArray(int size, Function1<? super Integer, UByte> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            int i2 = i;
            bArr[i2] = init.invoke(Integer.valueOf(i2)).m1276unboximpl();
        }
        return UByteArray.m1292constructorimpl(bArr);
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @InlineOnly
    /* renamed from: ubyteArrayOf-GBYM_sE */
    private static final byte[] m1297ubyteArrayOfGBYM_sE(byte... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements;
    }
}
