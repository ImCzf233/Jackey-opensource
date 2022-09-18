package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;

/* compiled from: UByte.kt */
@SinceKotlin(version = "1.5")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n��\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010��\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018�� t2\b\u0012\u0004\u0012\u00020��0\u0001:\u0001tB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020��H\u0097\nø\u0001��¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b'\u0010\u000fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001��¢\u0006\u0004\b(\u0010\u0012J\u001b\u0010&\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001��¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001��¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b3\u0010\u000fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\b4\u0010\u0012J\u001b\u00102\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b8\u0010\u000bJ\u001b\u00107\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001��¢\u0006\u0004\b9\u0010\u0012J\u001b\u00107\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001��¢\u0006\u0004\b:\u0010\u001fJ\u001b\u00107\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001��¢\u0006\u0004\b;\u0010<J\u001b\u0010=\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b@\u0010\u000fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\bA\u0010\u0012J\u001b\u0010?\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bI\u0010\u000fJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\bJ\u0010\u0012J\u001b\u0010H\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bL\u0010\u0018J\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bN\u0010\u000fJ\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001��¢\u0006\u0004\bO\u0010\u0012J\u001b\u0010M\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001��¢\u0006\u0004\bP\u0010\u001fJ\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001��¢\u0006\u0004\bQ\u0010\u0018J\u0010\u0010R\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bS\u0010\u0005J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u0010\u0010\\\u001a\u00020\rH\u0087\b¢\u0006\u0004\b]\u0010-J\u0010\u0010^\u001a\u00020_H\u0087\b¢\u0006\u0004\b`\u0010aJ\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u000f\u0010f\u001a\u00020gH\u0016¢\u0006\u0004\bh\u0010iJ\u0016\u0010j\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bk\u0010\u0005J\u0016\u0010l\u001a\u00020\u0010H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bm\u0010-J\u0016\u0010n\u001a\u00020\u0013H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bo\u0010aJ\u0016\u0010p\u001a\u00020\u0016H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bq\u0010eJ\u001b\u0010r\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\bs\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006u"}, m53d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(BS)S", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"})
@JvmInline
@WasExperimental(markerClass = {Unsigned.class})
/* loaded from: Jackey Client b2.jar:kotlin/UByte.class */
public final class UByte implements Comparable<UByte> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final byte data;
    public static final byte MIN_VALUE = 0;
    public static final byte MAX_VALUE = -1;
    public static final int SIZE_BYTES = 1;
    public static final int SIZE_BITS = 8;

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m1272hashCodeimpl(byte arg0) {
        return arg0;
    }

    public int hashCode() {
        return m1272hashCodeimpl(this.data);
    }

    /* renamed from: equals-impl */
    public static boolean m1273equalsimpl(byte arg0, Object other) {
        return (other instanceof UByte) && arg0 == ((UByte) other).m1276unboximpl();
    }

    public boolean equals(Object other) {
        return m1273equalsimpl(this.data, other);
    }

    @PublishedApi
    /* renamed from: constructor-impl */
    public static byte m1274constructorimpl(byte data) {
        return data;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ UByte m1275boximpl(byte v) {
        return new UByte(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ byte m1276unboximpl() {
        return this.data;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1277equalsimpl0(byte p1, byte p2) {
        return p1 == p2;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UByte uByte) {
        return Intrinsics.compare(m1276unboximpl() & 255, uByte.m1276unboximpl() & 255);
    }

    @PublishedApi
    private /* synthetic */ UByte(byte data) {
        this.data = data;
    }

    /* compiled from: UByte.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, m53d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/UByte$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU */
    private static int m1221compareTo7apg3OU(byte arg0, byte other) {
        return Intrinsics.compare(arg0 & 255, other & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU */
    private int m1222compareTo7apg3OU(byte other) {
        return Intrinsics.compare(m1276unboximpl() & 255, other & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw */
    private static final int m1223compareToxj2QHRw(byte arg0, short other) {
        return Intrinsics.compare(arg0 & 255, other & 65535);
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns */
    private static final int m1224compareToWZ4Q5Ns(byte arg0, int other) {
        return UnsignedUtils.uintCompare(UInt.m1353constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ */
    private static final int m1225compareToVKZWuLQ(byte arg0, long other) {
        return UnsignedUtils.ulongCompare(ULong.m1432constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU */
    private static final int m1226plus7apg3OU(byte arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) + UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw */
    private static final int m1227plusxj2QHRw(byte arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) + UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns */
    private static final int m1228plusWZ4Q5Ns(byte arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) + other);
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ */
    private static final long m1229plusVKZWuLQ(byte arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 255) + other);
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU */
    private static final int m1230minus7apg3OU(byte arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) - UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw */
    private static final int m1231minusxj2QHRw(byte arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) - UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns */
    private static final int m1232minusWZ4Q5Ns(byte arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) - other);
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ */
    private static final long m1233minusVKZWuLQ(byte arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 255) - other);
    }

    @InlineOnly
    /* renamed from: times-7apg3OU */
    private static final int m1234times7apg3OU(byte arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) * UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw */
    private static final int m1235timesxj2QHRw(byte arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) * UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns */
    private static final int m1236timesWZ4Q5Ns(byte arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 255) * other);
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ */
    private static final long m1237timesVKZWuLQ(byte arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 255) * other);
    }

    @InlineOnly
    /* renamed from: div-7apg3OU */
    private static final int m1238div7apg3OU(byte arg0, byte other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw */
    private static final int m1239divxj2QHRw(byte arg0, short other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns */
    private static final int m1240divWZ4Q5Ns(byte arg0, int other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ */
    private static final long m1241divVKZWuLQ(byte arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(ULong.m1432constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU */
    private static final int m1242rem7apg3OU(byte arg0, byte other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw */
    private static final int m1243remxj2QHRw(byte arg0, short other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns */
    private static final int m1244remWZ4Q5Ns(byte arg0, int other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ */
    private static final long m1245remVKZWuLQ(byte arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(ULong.m1432constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: floorDiv-7apg3OU */
    private static final int m1246floorDiv7apg3OU(byte arg0, byte other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: floorDiv-xj2QHRw */
    private static final int m1247floorDivxj2QHRw(byte arg0, short other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: floorDiv-WZ4Q5Ns */
    private static final int m1248floorDivWZ4Q5Ns(byte arg0, int other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: floorDiv-VKZWuLQ */
    private static final long m1249floorDivVKZWuLQ(byte arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(ULong.m1432constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: mod-7apg3OU */
    private static final byte m1250mod7apg3OU(byte arg0, byte other) {
        return m1274constructorimpl((byte) UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 255)));
    }

    @InlineOnly
    /* renamed from: mod-xj2QHRw */
    private static final short m1251modxj2QHRw(byte arg0, short other) {
        return UShort.m1538constructorimpl((short) UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 65535)));
    }

    @InlineOnly
    /* renamed from: mod-WZ4Q5Ns */
    private static final int m1252modWZ4Q5Ns(byte arg0, int other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: mod-VKZWuLQ */
    private static final long m1253modVKZWuLQ(byte arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(ULong.m1432constructorimpl(arg0 & 255), other);
    }

    @InlineOnly
    /* renamed from: inc-w2LRezQ */
    private static final byte m1254incw2LRezQ(byte arg0) {
        return m1274constructorimpl((byte) (arg0 + 1));
    }

    @InlineOnly
    /* renamed from: dec-w2LRezQ */
    private static final byte m1255decw2LRezQ(byte arg0) {
        return m1274constructorimpl((byte) (arg0 - 1));
    }

    @InlineOnly
    /* renamed from: rangeTo-7apg3OU */
    private static final UIntRange m1256rangeTo7apg3OU(byte arg0, byte other) {
        return new UIntRange(UInt.m1353constructorimpl(arg0 & 255), UInt.m1353constructorimpl(other & 255), null);
    }

    @InlineOnly
    /* renamed from: and-7apg3OU */
    private static final byte m1257and7apg3OU(byte arg0, byte other) {
        return m1274constructorimpl((byte) (arg0 & other));
    }

    @InlineOnly
    /* renamed from: or-7apg3OU */
    private static final byte m1258or7apg3OU(byte arg0, byte other) {
        return m1274constructorimpl((byte) (arg0 | other));
    }

    @InlineOnly
    /* renamed from: xor-7apg3OU */
    private static final byte m1259xor7apg3OU(byte arg0, byte other) {
        return m1274constructorimpl((byte) (arg0 ^ other));
    }

    @InlineOnly
    /* renamed from: inv-w2LRezQ */
    private static final byte m1260invw2LRezQ(byte arg0) {
        return m1274constructorimpl((byte) (arg0 ^ (-1)));
    }

    @InlineOnly
    /* renamed from: toByte-impl */
    private static final byte m1261toByteimpl(byte arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toShort-impl */
    private static final short m1262toShortimpl(byte arg0) {
        return (short) (arg0 & 255);
    }

    @InlineOnly
    /* renamed from: toInt-impl */
    private static final int m1263toIntimpl(byte arg0) {
        return arg0 & 255;
    }

    @InlineOnly
    /* renamed from: toLong-impl */
    private static final long m1264toLongimpl(byte arg0) {
        return arg0 & 255;
    }

    @InlineOnly
    /* renamed from: toUByte-w2LRezQ */
    private static final byte m1265toUBytew2LRezQ(byte arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toUShort-Mh2AYeg */
    private static final short m1266toUShortMh2AYeg(byte arg0) {
        return UShort.m1538constructorimpl((short) (arg0 & 255));
    }

    @InlineOnly
    /* renamed from: toUInt-pVg5ArA */
    private static final int m1267toUIntpVg5ArA(byte arg0) {
        return UInt.m1353constructorimpl(arg0 & 255);
    }

    @InlineOnly
    /* renamed from: toULong-s-VKNKU */
    private static final long m1268toULongsVKNKU(byte arg0) {
        return ULong.m1432constructorimpl(arg0 & 255);
    }

    @InlineOnly
    /* renamed from: toFloat-impl */
    private static final float m1269toFloatimpl(byte arg0) {
        return arg0 & 255;
    }

    @InlineOnly
    /* renamed from: toDouble-impl */
    private static final double m1270toDoubleimpl(byte arg0) {
        return arg0 & 255;
    }

    @NotNull
    /* renamed from: toString-impl */
    public static String m1271toStringimpl(byte arg0) {
        return String.valueOf(arg0 & 255);
    }

    @NotNull
    public String toString() {
        return m1271toStringimpl(this.data);
    }
}
