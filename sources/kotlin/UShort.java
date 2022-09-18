package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;

/* compiled from: UShort.kt */
@SinceKotlin(version = "1.5")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n��\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010��\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018�� t2\b\u0012\u0004\u0012\u00020��0\u0001:\u0001tB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020��H\u0097\nø\u0001��¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001��¢\u0006\u0004\b'\u0010\u0010J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001��¢\u0006\u0004\b(\u0010\u0013J\u001b\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001��¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020��H\u0087\nø\u0001��ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b3\u0010\u0010J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\b4\u0010\u0013J\u001b\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001��¢\u0006\u0004\b8\u00109J\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001��¢\u0006\u0004\b:\u0010\u0013J\u001b\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001��¢\u0006\u0004\b;\u0010\u001fJ\u001b\u00107\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\bø\u0001��¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\b@\u0010\u0010J\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bA\u0010\u0013J\u001b\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\bI\u0010\u0010J\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bJ\u0010\u0013J\u001b\u0010H\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bL\u0010\u0018J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001��¢\u0006\u0004\bN\u0010\u0010J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001��¢\u0006\u0004\bO\u0010\u0013J\u001b\u0010M\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001��¢\u0006\u0004\bP\u0010\u001fJ\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020��H\u0087\nø\u0001��¢\u0006\u0004\bQ\u0010\u0018J\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020WH\u0087\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\rH\u0087\b¢\u0006\u0004\b_\u0010-J\u0010\u0010`\u001a\u00020aH\u0087\b¢\u0006\u0004\bb\u0010cJ\u0010\u0010d\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\be\u0010\u0005J\u000f\u0010f\u001a\u00020gH\u0016¢\u0006\u0004\bh\u0010iJ\u0016\u0010j\u001a\u00020\u000eH\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bk\u0010UJ\u0016\u0010l\u001a\u00020\u0011H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bm\u0010-J\u0016\u0010n\u001a\u00020\u0014H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bo\u0010cJ\u0016\u0010p\u001a\u00020��H\u0087\bø\u0001��ø\u0001\u0001¢\u0006\u0004\bq\u0010\u0005J\u001b\u0010r\u001a\u00020��2\u0006\u0010\t\u001a\u00020��H\u0087\fø\u0001��¢\u0006\u0004\bs\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006u"}, m53d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"})
@JvmInline
@WasExperimental(markerClass = {Unsigned.class})
/* loaded from: Jackey Client b2.jar:kotlin/UShort.class */
public final class UShort implements Comparable<UShort> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final short data;
    public static final short MIN_VALUE = 0;
    public static final short MAX_VALUE = -1;
    public static final int SIZE_BYTES = 2;
    public static final int SIZE_BITS = 16;

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m1536hashCodeimpl(short arg0) {
        return arg0;
    }

    public int hashCode() {
        return m1536hashCodeimpl(this.data);
    }

    /* renamed from: equals-impl */
    public static boolean m1537equalsimpl(short arg0, Object other) {
        return (other instanceof UShort) && arg0 == ((UShort) other).m1540unboximpl();
    }

    public boolean equals(Object other) {
        return m1537equalsimpl(this.data, other);
    }

    @PublishedApi
    /* renamed from: constructor-impl */
    public static short m1538constructorimpl(short data) {
        return data;
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ UShort m1539boximpl(short v) {
        return new UShort(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ short m1540unboximpl() {
        return this.data;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m1541equalsimpl0(short p1, short p2) {
        return p1 == p2;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return Intrinsics.compare(m1540unboximpl() & 65535, uShort.m1540unboximpl() & 65535);
    }

    @PublishedApi
    private /* synthetic */ UShort(short data) {
        this.data = data;
    }

    /* compiled from: UShort.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, m53d2 = {"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/UShort$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU */
    private static final int m1485compareTo7apg3OU(short arg0, byte other) {
        return Intrinsics.compare(arg0 & 65535, other & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw */
    private static int m1486compareToxj2QHRw(short arg0, short other) {
        return Intrinsics.compare(arg0 & 65535, other & 65535);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw */
    private int m1487compareToxj2QHRw(short other) {
        return Intrinsics.compare(m1540unboximpl() & 65535, other & 65535);
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns */
    private static final int m1488compareToWZ4Q5Ns(short arg0, int other) {
        return UnsignedUtils.uintCompare(UInt.m1353constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ */
    private static final int m1489compareToVKZWuLQ(short arg0, long other) {
        return UnsignedUtils.ulongCompare(ULong.m1432constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU */
    private static final int m1490plus7apg3OU(short arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) + UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw */
    private static final int m1491plusxj2QHRw(short arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) + UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns */
    private static final int m1492plusWZ4Q5Ns(short arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) + other);
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ */
    private static final long m1493plusVKZWuLQ(short arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 65535) + other);
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU */
    private static final int m1494minus7apg3OU(short arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) - UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw */
    private static final int m1495minusxj2QHRw(short arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) - UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns */
    private static final int m1496minusWZ4Q5Ns(short arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) - other);
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ */
    private static final long m1497minusVKZWuLQ(short arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 65535) - other);
    }

    @InlineOnly
    /* renamed from: times-7apg3OU */
    private static final int m1498times7apg3OU(short arg0, byte other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) * UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw */
    private static final int m1499timesxj2QHRw(short arg0, short other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) * UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns */
    private static final int m1500timesWZ4Q5Ns(short arg0, int other) {
        return UInt.m1353constructorimpl(UInt.m1353constructorimpl(arg0 & 65535) * other);
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ */
    private static final long m1501timesVKZWuLQ(short arg0, long other) {
        return ULong.m1432constructorimpl(ULong.m1432constructorimpl(arg0 & 65535) * other);
    }

    @InlineOnly
    /* renamed from: div-7apg3OU */
    private static final int m1502div7apg3OU(short arg0, byte other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw */
    private static final int m1503divxj2QHRw(short arg0, short other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns */
    private static final int m1504divWZ4Q5Ns(short arg0, int other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ */
    private static final long m1505divVKZWuLQ(short arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(ULong.m1432constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU */
    private static final int m1506rem7apg3OU(short arg0, byte other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw */
    private static final int m1507remxj2QHRw(short arg0, short other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns */
    private static final int m1508remWZ4Q5Ns(short arg0, int other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ */
    private static final long m1509remVKZWuLQ(short arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(ULong.m1432constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: floorDiv-7apg3OU */
    private static final int m1510floorDiv7apg3OU(short arg0, byte other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 255));
    }

    @InlineOnly
    /* renamed from: floorDiv-xj2QHRw */
    private static final int m1511floorDivxj2QHRw(short arg0, short other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 65535));
    }

    @InlineOnly
    /* renamed from: floorDiv-WZ4Q5Ns */
    private static final int m1512floorDivWZ4Q5Ns(short arg0, int other) {
        return UnsignedUtils.m1563uintDivideJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: floorDiv-VKZWuLQ */
    private static final long m1513floorDivVKZWuLQ(short arg0, long other) {
        return UnsignedUtils.m1565ulongDivideeb3DHEI(ULong.m1432constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: mod-7apg3OU */
    private static final byte m1514mod7apg3OU(short arg0, byte other) {
        return UByte.m1274constructorimpl((byte) UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 255)));
    }

    @InlineOnly
    /* renamed from: mod-xj2QHRw */
    private static final short m1515modxj2QHRw(short arg0, short other) {
        return m1538constructorimpl((short) UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 65535)));
    }

    @InlineOnly
    /* renamed from: mod-WZ4Q5Ns */
    private static final int m1516modWZ4Q5Ns(short arg0, int other) {
        return UnsignedUtils.m1564uintRemainderJ1ME1BU(UInt.m1353constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: mod-VKZWuLQ */
    private static final long m1517modVKZWuLQ(short arg0, long other) {
        return UnsignedUtils.m1566ulongRemaindereb3DHEI(ULong.m1432constructorimpl(arg0 & 65535), other);
    }

    @InlineOnly
    /* renamed from: inc-Mh2AYeg */
    private static final short m1518incMh2AYeg(short arg0) {
        return m1538constructorimpl((short) (arg0 + 1));
    }

    @InlineOnly
    /* renamed from: dec-Mh2AYeg */
    private static final short m1519decMh2AYeg(short arg0) {
        return m1538constructorimpl((short) (arg0 - 1));
    }

    @InlineOnly
    /* renamed from: rangeTo-xj2QHRw */
    private static final UIntRange m1520rangeToxj2QHRw(short arg0, short other) {
        return new UIntRange(UInt.m1353constructorimpl(arg0 & 65535), UInt.m1353constructorimpl(other & 65535), null);
    }

    @InlineOnly
    /* renamed from: and-xj2QHRw */
    private static final short m1521andxj2QHRw(short arg0, short other) {
        return m1538constructorimpl((short) (arg0 & other));
    }

    @InlineOnly
    /* renamed from: or-xj2QHRw */
    private static final short m1522orxj2QHRw(short arg0, short other) {
        return m1538constructorimpl((short) (arg0 | other));
    }

    @InlineOnly
    /* renamed from: xor-xj2QHRw */
    private static final short m1523xorxj2QHRw(short arg0, short other) {
        return m1538constructorimpl((short) (arg0 ^ other));
    }

    @InlineOnly
    /* renamed from: inv-Mh2AYeg */
    private static final short m1524invMh2AYeg(short arg0) {
        return m1538constructorimpl((short) (arg0 ^ (-1)));
    }

    @InlineOnly
    /* renamed from: toByte-impl */
    private static final byte m1525toByteimpl(short arg0) {
        return (byte) arg0;
    }

    @InlineOnly
    /* renamed from: toShort-impl */
    private static final short m1526toShortimpl(short arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toInt-impl */
    private static final int m1527toIntimpl(short arg0) {
        return arg0 & 65535;
    }

    @InlineOnly
    /* renamed from: toLong-impl */
    private static final long m1528toLongimpl(short arg0) {
        return arg0 & 65535;
    }

    @InlineOnly
    /* renamed from: toUByte-w2LRezQ */
    private static final byte m1529toUBytew2LRezQ(short arg0) {
        return UByte.m1274constructorimpl((byte) arg0);
    }

    @InlineOnly
    /* renamed from: toUShort-Mh2AYeg */
    private static final short m1530toUShortMh2AYeg(short arg0) {
        return arg0;
    }

    @InlineOnly
    /* renamed from: toUInt-pVg5ArA */
    private static final int m1531toUIntpVg5ArA(short arg0) {
        return UInt.m1353constructorimpl(arg0 & 65535);
    }

    @InlineOnly
    /* renamed from: toULong-s-VKNKU */
    private static final long m1532toULongsVKNKU(short arg0) {
        return ULong.m1432constructorimpl(arg0 & 65535);
    }

    @InlineOnly
    /* renamed from: toFloat-impl */
    private static final float m1533toFloatimpl(short arg0) {
        return arg0 & 65535;
    }

    @InlineOnly
    /* renamed from: toDouble-impl */
    private static final double m1534toDoubleimpl(short arg0) {
        return arg0 & 65535;
    }

    @NotNull
    /* renamed from: toString-impl */
    public static String m1535toStringimpl(short arg0) {
        return String.valueOf(arg0 & 65535);
    }

    @NotNull
    public String toString() {
        return m1535toStringimpl(this.data);
    }
}
