package kotlin;

import jdk.nashorn.internal.runtime.JSType;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

/* compiled from: ULong.kt */
@SinceKotlin(version = "1.5")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n��\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010��\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018�� |2\b\u0012\u0004\u0012\u00020��0\u0001:\u0001|B\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020��H\u0097\nø\u0001��¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b!\u0010\"J\u001a\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%HÖ\u0003¢\u0006\u0004\b&\u0010'J\u001b\u0010(\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001��¢\u0006\u0004\b)\u0010\u001dJ\u001b\u0010(\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001��¢\u0006\u0004\b*\u0010\u001fJ\u001b\u0010(\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b+\u0010\u000bJ\u001b\u0010(\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001��¢\u0006\u0004\b,\u0010\"J\u0010\u0010-\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b.\u0010/J\u0016\u00100\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u0016\u00102\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\b3\u0010\u0005J\u001b\u00104\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b5\u0010\u001dJ\u001b\u00104\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b6\u0010\u001fJ\u001b\u00104\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b7\u0010\u000bJ\u001b\u00104\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b8\u0010\"J\u001b\u00109\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001��¢\u0006\u0004\b:\u0010;J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001��¢\u0006\u0004\b<\u0010\u0013J\u001b\u00109\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b=\u0010\u000bJ\u001b\u00109\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001��¢\u0006\u0004\b>\u0010?J\u001b\u0010@\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\bA\u0010\u000bJ\u001b\u0010B\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\bC\u0010\u001dJ\u001b\u0010B\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bD\u0010\u001fJ\u001b\u0010B\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bE\u0010\u000bJ\u001b\u0010B\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bF\u0010\"J\u001b\u0010G\u001a\u00020H2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bI\u0010JJ\u001b\u0010K\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\bL\u0010\u001dJ\u001b\u0010K\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bM\u0010\u001fJ\u001b\u0010K\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bN\u0010\u000bJ\u001b\u0010K\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bO\u0010\"J\u001e\u0010P\u001a\u00020��2\u0006\u0010Q\u001a\u00020\rH\u0087\fø\u0001��ø\u0001\u0001¢\u0006\u0004\bR\u0010\u001fJ\u001e\u0010S\u001a\u00020��2\u0006\u0010Q\u001a\u00020\rH\u0087\fø\u0001��ø\u0001\u0001¢\u0006\u0004\bT\u0010\u001fJ\u001b\u0010U\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\bV\u0010\u001dJ\u001b\u0010U\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bW\u0010\u001fJ\u001b\u0010U\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bX\u0010\u000bJ\u001b\u0010U\u001a\u00020��2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bY\u0010\"J\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020_H\u0087\b¢\u0006\u0004\b`\u0010aJ\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\rH\u0087\b¢\u0006\u0004\bg\u0010/J\u0010\u0010h\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bi\u0010\u0005J\u0010\u0010j\u001a\u00020kH\u0087\b¢\u0006\u0004\bl\u0010mJ\u000f\u0010n\u001a\u00020oH\u0016¢\u0006\u0004\bp\u0010qJ\u0016\u0010r\u001a\u00020\u000eH\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bs\u0010]J\u0016\u0010t\u001a\u00020\u0011H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bu\u0010/J\u0016\u0010v\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bw\u0010\u0005J\u0016\u0010x\u001a\u00020\u0016H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\by\u0010mJ\u001b\u0010z\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b{\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006}"}, m53d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "getData$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-s-VKNKU", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(J)I", "inc", "inc-s-VKNKU", "inv", "inv-s-VKNKU", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(JB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(JS)S", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"})
@JvmInline
@WasExperimental(markerClass = {Unsigned.class})
/* loaded from: Jackey Client b2.jar:kotlin/ULong.class */
public final class ULong implements Comparable<ULong> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final long data;
    public static final long MIN_VALUE = 0;
    public static final long MAX_VALUE = -1;
    public static final int SIZE_BYTES = 8;
    public static final int SIZE_BITS = 64;

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m1430hashCodeimpl(long arg0) {
        return (int) (arg0 ^ (arg0 >>> 32));
    }

    public int hashCode() {
        return m1430hashCodeimpl(this.data);
    }

    /* renamed from: equals-impl */
    public static boolean m1431equalsimpl(long arg0, Object other) {
        return (other instanceof ULong) && arg0 == ((ULong) other).m1434unboximpl();
    }

    public boolean equals(Object other) {
        return m1431equalsimpl(this.data, other);
    }

    @PublishedApi
    /* renamed from: constructor-impl */
    public static long m1432constructorimpl(long data) {
        return data;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ ULong m1433boximpl(long v) {
        return new ULong(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ long m1434unboximpl() {
        return this.data;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1435equalsimpl0(long p1, long p2) {
        return p1 == p2;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(ULong uLong) {
        return UnsignedUtils.ulongCompare(m1434unboximpl(), uLong.m1434unboximpl());
    }

    @PublishedApi
    private /* synthetic */ ULong(long data) {
        this.data = data;
    }

    /* compiled from: ULong.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, m53d2 = {"Lkotlin/ULong$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/ULong;", "J", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/ULong$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU */
    private static final int m1377compareTo7apg3OU(long arg0, byte other) {
        return UnsignedUtils.ulongCompare(arg0, m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw */
    private static final int m1378compareToxj2QHRw(long arg0, short other) {
        return UnsignedUtils.ulongCompare(arg0, m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns */
    private static final int m1379compareToWZ4Q5Ns(long arg0, int other) {
        return UnsignedUtils.ulongCompare(arg0, m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ */
    private static int m1380compareToVKZWuLQ(long arg0, long other) {
        return UnsignedUtils.ulongCompare(arg0, other);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ */
    private int m1381compareToVKZWuLQ(long other) {
        return UnsignedUtils.ulongCompare(m1434unboximpl(), other);
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU */
    private static final long m1382plus7apg3OU(long arg0, byte other) {
        return m1432constructorimpl(arg0 + m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw */
    private static final long m1383plusxj2QHRw(long arg0, short other) {
        return m1432constructorimpl(arg0 + m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns */
    private static final long m1384plusWZ4Q5Ns(long arg0, int other) {
        return m1432constructorimpl(arg0 + m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ */
    private static final long m1385plusVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 + other);
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU */
    private static final long m1386minus7apg3OU(long arg0, byte other) {
        return m1432constructorimpl(arg0 - m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw */
    private static final long m1387minusxj2QHRw(long arg0, short other) {
        return m1432constructorimpl(arg0 - m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns */
    private static final long m1388minusWZ4Q5Ns(long arg0, int other) {
        return m1432constructorimpl(arg0 - m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ */
    private static final long m1389minusVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 - other);
    }

    @InlineOnly
    /* renamed from: times-7apg3OU */
    private static final long m1390times7apg3OU(long arg0, byte other) {
        return m1432constructorimpl(arg0 * m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw */
    private static final long m1391timesxj2QHRw(long arg0, short other) {
        return m1432constructorimpl(arg0 * m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns */
    private static final long m1392timesWZ4Q5Ns(long arg0, int other) {
        return m1432constructorimpl(arg0 * m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ */
    private static final long m1393timesVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 * other);
    }

    @InlineOnly
    /* renamed from: div-7apg3OU */
    private static final long m1394div7apg3OU(long arg0, byte other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw */
    private static final long m1395divxj2QHRw(long arg0, short other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns */
    private static final long m1396divWZ4Q5Ns(long arg0, int other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ */
    private static final long m1397divVKZWuLQ(long arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, other);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU */
    private static final long m1398rem7apg3OU(long arg0, byte other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw */
    private static final long m1399remxj2QHRw(long arg0, short other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns */
    private static final long m1400remWZ4Q5Ns(long arg0, int other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ */
    private static final long m1401remVKZWuLQ(long arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, other);
    }

    @InlineOnly
    /* renamed from: floorDiv-7apg3OU */
    private static final long m1402floorDiv7apg3OU(long arg0, byte other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: floorDiv-xj2QHRw */
    private static final long m1403floorDivxj2QHRw(long arg0, short other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: floorDiv-WZ4Q5Ns */
    private static final long m1404floorDivWZ4Q5Ns(long arg0, int other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, m1432constructorimpl(other & JSType.MAX_UINT));
    }

    @InlineOnly
    /* renamed from: floorDiv-VKZWuLQ */
    private static final long m1405floorDivVKZWuLQ(long arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(arg0, other);
    }

    @InlineOnly
    /* renamed from: mod-7apg3OU */
    private static final byte m1406mod7apg3OU(long arg0, byte other) {
        return UByte.m1274constructorimpl((byte) UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & 255)));
    }

    @InlineOnly
    /* renamed from: mod-xj2QHRw */
    private static final short m1407modxj2QHRw(long arg0, short other) {
        return UShort.m1538constructorimpl((short) UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & 65535)));
    }

    @InlineOnly
    /* renamed from: mod-WZ4Q5Ns */
    private static final int m1408modWZ4Q5Ns(long arg0, int other) {
        return UInt.m1353constructorimpl((int) UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, m1432constructorimpl(other & JSType.MAX_UINT)));
    }

    @InlineOnly
    /* renamed from: mod-VKZWuLQ */
    private static final long m1409modVKZWuLQ(long arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(arg0, other);
    }

    @InlineOnly
    /* renamed from: inc-s-VKNKU */
    private static final long m1410incsVKNKU(long arg0) {
        return m1432constructorimpl(arg0 + 1);
    }

    @InlineOnly
    /* renamed from: dec-s-VKNKU */
    private static final long m1411decsVKNKU(long arg0) {
        return m1432constructorimpl(arg0 - 1);
    }

    @InlineOnly
    /* renamed from: rangeTo-VKZWuLQ */
    private static final ULongRange m1412rangeToVKZWuLQ(long arg0, long other) {
        return new ULongRange(arg0, other, null);
    }

    @InlineOnly
    /* renamed from: shl-s-VKNKU */
    private static final long m1413shlsVKNKU(long arg0, int bitCount) {
        return m1432constructorimpl(arg0 << bitCount);
    }

    @InlineOnly
    /* renamed from: shr-s-VKNKU */
    private static final long m1414shrsVKNKU(long arg0, int bitCount) {
        return m1432constructorimpl(arg0 >>> bitCount);
    }

    @InlineOnly
    /* renamed from: and-VKZWuLQ */
    private static final long m1415andVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 & other);
    }

    @InlineOnly
    /* renamed from: or-VKZWuLQ */
    private static final long m1416orVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 | other);
    }

    @InlineOnly
    /* renamed from: xor-VKZWuLQ */
    private static final long m1417xorVKZWuLQ(long arg0, long other) {
        return m1432constructorimpl(arg0 ^ other);
    }

    @InlineOnly
    /* renamed from: inv-s-VKNKU */
    private static final long m1418invsVKNKU(long arg0) {
        return m1432constructorimpl(arg0 ^ (-1));
    }

    @InlineOnly
    /* renamed from: toByte-impl */
    private static final byte m1419toByteimpl(long arg0) {
        return (byte) arg0;
    }

    @InlineOnly
    /* renamed from: toShort-impl */
    private static final short m1420toShortimpl(long arg0) {
        return (short) arg0;
    }

    @InlineOnly
    /* renamed from: toInt-impl */
    private static final int m1421toIntimpl(long arg0) {
        return (int) arg0;
    }

    @InlineOnly
    /* renamed from: toLong-impl */
    private static final long m1422toLongimpl(long arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toUByte-w2LRezQ */
    private static final byte m1423toUBytew2LRezQ(long arg0) {
        return UByte.m1274constructorimpl((byte) arg0);
    }

    @InlineOnly
    /* renamed from: toUShort-Mh2AYeg */
    private static final short m1424toUShortMh2AYeg(long arg0) {
        return UShort.m1538constructorimpl((short) arg0);
    }

    @InlineOnly
    /* renamed from: toUInt-pVg5ArA */
    private static final int m1425toUIntpVg5ArA(long arg0) {
        return UInt.m1353constructorimpl((int) arg0);
    }

    @InlineOnly
    /* renamed from: toULong-s-VKNKU */
    private static final long m1426toULongsVKNKU(long arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toFloat-impl */
    private static final float m1427toFloatimpl(long arg0) {
        return (float) UnsignedUtils.ulongToDouble(arg0);
    }

    @InlineOnly
    /* renamed from: toDouble-impl */
    private static final double m1428toDoubleimpl(long arg0) {
        return UnsignedUtils.ulongToDouble(arg0);
    }

    @NotNull
    /* renamed from: toString-impl */
    public static String m1429toStringimpl(long arg0) {
        return UnsignedUtils.ulongToString(arg0);
    }

    @NotNull
    public String toString() {
        return m1429toStringimpl(this.data);
    }
}
