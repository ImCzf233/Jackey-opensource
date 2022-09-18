package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: PrimitiveSpreadBuilders.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n��\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018��*\b\b��\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028��¢\u0006\u0002\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0004H\u0004J\u001d\u0010\u0013\u001a\u00028��2\u0006\u0010\u0014\u001a\u00028��2\u0006\u0010\u0015\u001a\u00028��H\u0004¢\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0004*\u00028��H$¢\u0006\u0002\u0010\u0018R\u001a\u0010\u0006\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018��0\u000bX\u0082\u0004¢\u0006\n\n\u0002\u0010\u000e\u0012\u0004\b\f\u0010\r¨\u0006\u0019"}, m53d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "getSpreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/PrimitiveSpreadBuilder.class */
public abstract class PrimitiveSpreadBuilder<T> {
    private final int size;
    private int position;
    @NotNull
    private final T[] spreads;

    protected abstract int getSize(@NotNull T t);

    private static /* synthetic */ void getSpreads$annotations() {
    }

    public PrimitiveSpreadBuilder(int size) {
        this.size = size;
        this.spreads = (T[]) new Object[this.size];
    }

    public final int getPosition() {
        return this.position;
    }

    public final void setPosition(int i) {
        this.position = i;
    }

    public final void addSpread(@NotNull T spreadArgument) {
        Intrinsics.checkNotNullParameter(spreadArgument, "spreadArgument");
        T[] tArr = this.spreads;
        int i = this.position;
        this.position = i + 1;
        tArr[i] = spreadArgument;
    }

    public final int size() {
        int i;
        int totalLength = 0;
        int i2 = 0;
        int i3 = this.size - 1;
        if (0 <= i3) {
            do {
                i = i2;
                i2++;
                int i4 = totalLength;
                T t = this.spreads[i];
                totalLength = i4 + (t == null ? 1 : getSize(t));
            } while (i != i3);
            return totalLength;
        }
        return totalLength;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0081  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final T toArray(@org.jetbrains.annotations.NotNull T r8, @org.jetbrains.annotations.NotNull T r9) {
        /*
            r7 = this;
            r0 = r8
            java.lang.String r1 = "values"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r9
            java.lang.String r1 = "result"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = 0
            r10 = r0
            r0 = 0
            r11 = r0
            r0 = 0
            r12 = r0
            r0 = r7
            int r0 = r0.size
            r1 = 1
            int r0 = r0 - r1
            r13 = r0
            r0 = r12
            r1 = r13
            if (r0 > r1) goto L78
        L23:
            r0 = r12
            r14 = r0
            int r12 = r12 + 1
            r0 = r7
            T[] r0 = r0.spreads
            r1 = r14
            r0 = r0[r1]
            r15 = r0
            r0 = r15
            if (r0 == 0) goto L71
            r0 = r11
            r1 = r14
            if (r0 >= r1) goto L54
            r0 = r8
            r1 = r11
            r2 = r9
            r3 = r10
            r4 = r14
            r5 = r11
            int r4 = r4 - r5
            java.lang.System.arraycopy(r0, r1, r2, r3, r4)
            r0 = r10
            r1 = r14
            r2 = r11
            int r1 = r1 - r2
            int r0 = r0 + r1
            r10 = r0
        L54:
            r0 = r7
            r1 = r15
            int r0 = r0.getSize(r1)
            r16 = r0
            r0 = r15
            r1 = 0
            r2 = r9
            r3 = r10
            r4 = r16
            java.lang.System.arraycopy(r0, r1, r2, r3, r4)
            r0 = r10
            r1 = r16
            int r0 = r0 + r1
            r10 = r0
            r0 = r14
            r1 = 1
            int r0 = r0 + r1
            r11 = r0
        L71:
            r0 = r14
            r1 = r13
            if (r0 != r1) goto L23
        L78:
            r0 = r11
            r1 = r7
            int r1 = r1.size
            if (r0 >= r1) goto L90
            r0 = r8
            r1 = r11
            r2 = r9
            r3 = r10
            r4 = r7
            int r4 = r4.size
            r5 = r11
            int r4 = r4 - r5
            java.lang.System.arraycopy(r0, r1, r2, r3, r4)
        L90:
            r0 = r9
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.internal.PrimitiveSpreadBuilder.toArray(java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
