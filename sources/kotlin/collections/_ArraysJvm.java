package kotlin.collections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Annotations;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��¬\u0001\n��\n\u0002\u0010 \n��\n\u0002\u0010\u0011\n��\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u001a#\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010\u0004\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0006\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00020\b\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\u00020\n\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\f\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\r0\u0001*\u00020\u000e\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u0010\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00110\u0001*\u00020\u0012\u001a\u0010\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00130\u0001*\u00020\u0014\u001aU\u0010\u0015\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b��\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b��\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001c\u001a9\u0010\u0015\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001d\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a2\u0010\u001e\u001a\u00020\u0005\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f¢\u0006\u0004\b \u0010!\u001a6\u0010\u001e\u001a\u00020\u0005\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\f¢\u0006\u0004\b\"\u0010!\u001a\"\u0010#\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0004\b$\u0010%\u001a$\u0010#\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\b¢\u0006\u0004\b&\u0010%\u001a\"\u0010'\u001a\u00020(\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0004\b)\u0010*\u001a$\u0010'\u001a\u00020(\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\b¢\u0006\u0004\b+\u0010*\u001a0\u0010,\u001a\u00020\u0005\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f¢\u0006\u0002\u0010!\u001a6\u0010,\u001a\u00020\u0005\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\f¢\u0006\u0004\b-\u0010!\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\b2\b\u0010\u001f\u001a\u0004\u0018\u00010\bH\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\n2\b\u0010\u001f\u001a\u0004\u0018\u00010\nH\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u001f\u001a\u00020\fH\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\fH\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\u000e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000eH\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\u00102\b\u0010\u001f\u001a\u0004\u0018\u00010\u0010H\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\u00122\b\u0010\u001f\u001a\u0004\u0018\u00010\u0012H\u0087\f¢\u0006\u0002\b-\u001a\u0015\u0010,\u001a\u00020\u0005*\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\u0087\f\u001a\u001e\u0010,\u001a\u00020\u0005*\u0004\u0018\u00010\u00142\b\u0010\u001f\u001a\u0004\u0018\u00010\u0014H\u0087\f¢\u0006\u0002\b-\u001a \u0010.\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010%\u001a$\u0010.\u001a\u00020\u000f\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\b¢\u0006\u0004\b/\u0010%\u001a\r\u0010.\u001a\u00020\u000f*\u00020\u0006H\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\u0006H\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\bH\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\bH\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\nH\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\nH\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\fH\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\fH\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\u000eH\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\u000eH\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\u0010H\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\u0010H\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\u0012H\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\u0012H\u0087\b¢\u0006\u0002\b/\u001a\r\u0010.\u001a\u00020\u000f*\u00020\u0014H\u0087\b\u001a\u0014\u0010.\u001a\u00020\u000f*\u0004\u0018\u00010\u0014H\u0087\b¢\u0006\u0002\b/\u001a \u00100\u001a\u00020(\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010*\u001a$\u00100\u001a\u00020(\"\u0004\b��\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0087\b¢\u0006\u0004\b1\u0010*\u001a\r\u00100\u001a\u00020(*\u00020\u0006H\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\u0006H\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\bH\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\bH\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\nH\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\nH\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\fH\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\fH\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\u000eH\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\u000eH\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\u0010H\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\u0010H\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\u0012H\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\u0012H\u0087\b¢\u0006\u0002\b1\u001a\r\u00100\u001a\u00020(*\u00020\u0014H\u0087\b\u001a\u0014\u00100\u001a\u00020(*\u0004\u0018\u00010\u0014H\u0087\b¢\u0006\u0002\b1\u001aQ\u00102\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007¢\u0006\u0002\u00107\u001a2\u00102\u001a\u00020\u0006*\u00020\u00062\u0006\u00103\u001a\u00020\u00062\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\b*\u00020\b2\u0006\u00103\u001a\u00020\b2\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\n*\u00020\n2\u0006\u00103\u001a\u00020\n2\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\f*\u00020\f2\u0006\u00103\u001a\u00020\f2\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\u000e*\u00020\u000e2\u0006\u00103\u001a\u00020\u000e2\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\u0010*\u00020\u00102\u0006\u00103\u001a\u00020\u00102\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\u0012*\u00020\u00122\u0006\u00103\u001a\u00020\u00122\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a2\u00102\u001a\u00020\u0014*\u00020\u00142\u0006\u00103\u001a\u00020\u00142\b\b\u0002\u00104\u001a\u00020\u000f2\b\b\u0002\u00105\u001a\u00020\u000f2\b\b\u0002\u00106\u001a\u00020\u000fH\u0007\u001a$\u00108\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u00109\u001a.\u00108\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010:\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\u0010;\u001a\r\u00108\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u00108\u001a\u00020\u0006*\u00020\u00062\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\b*\u00020\bH\u0087\b\u001a\u0015\u00108\u001a\u00020\b*\u00020\b2\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\n*\u00020\nH\u0087\b\u001a\u0015\u00108\u001a\u00020\n*\u00020\n2\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\f*\u00020\fH\u0087\b\u001a\u0015\u00108\u001a\u00020\f*\u00020\f2\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\u000e*\u00020\u000eH\u0087\b\u001a\u0015\u00108\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\u0010*\u00020\u0010H\u0087\b\u001a\u0015\u00108\u001a\u00020\u0010*\u00020\u00102\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\u0012*\u00020\u0012H\u0087\b\u001a\u0015\u00108\u001a\u00020\u0012*\u00020\u00122\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a\r\u00108\u001a\u00020\u0014*\u00020\u0014H\u0087\b\u001a\u0015\u00108\u001a\u00020\u0014*\u00020\u00142\u0006\u0010:\u001a\u00020\u000fH\u0087\b\u001a6\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0004\b=\u0010>\u001a\"\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a\"\u0010<\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\b=\u001a5\u0010?\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0004\b<\u0010>\u001a!\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a!\u0010?\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b<\u001a(\u0010@\u001a\u0002H\u0002\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010A\u001a\u00020\u000fH\u0087\b¢\u0006\u0002\u0010B\u001a\u0015\u0010@\u001a\u00020\u0005*\u00020\u00062\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0007*\u00020\b2\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\t*\u00020\n2\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u000b*\u00020\f2\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\r*\u00020\u000e2\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u000f*\u00020\u00102\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0011*\u00020\u00122\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0013*\u00020\u00142\u0006\u0010A\u001a\u00020\u000fH\u0087\b\u001a7\u0010C\u001a\u00020D\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010E\u001a&\u0010C\u001a\u00020D*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010C\u001a\u00020D*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a-\u0010F\u001a\b\u0012\u0004\u0012\u0002HG0\u0001\"\u0004\b��\u0010G*\u0006\u0012\u0002\b\u00030\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002HG0I¢\u0006\u0002\u0010J\u001aA\u0010K\u001a\u0002HL\"\u0010\b��\u0010L*\n\u0012\u0006\b��\u0012\u0002HG0M\"\u0004\b\u0001\u0010G*\u0006\u0012\u0002\b\u00030\u00032\u0006\u00103\u001a\u0002HL2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002HG0I¢\u0006\u0002\u0010N\u001a,\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010P\u001a4\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010Q\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0086\u0002¢\u0006\u0002\u0010R\u001a2\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H\u00020SH\u0086\u0002¢\u0006\u0002\u0010T\u001a\u0015\u0010O\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0005H\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0006*\u00020\u00062\u0006\u0010Q\u001a\u00020\u0006H\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\u0006*\u00020\u00062\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00050SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\b*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0007H\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\b*\u00020\b2\u0006\u0010Q\u001a\u00020\bH\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\b*\u00020\b2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00070SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010\u0016\u001a\u00020\tH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010Q\u001a\u00020\nH\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\n*\u00020\n2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\t0SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000bH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\f*\u00020\f2\u0006\u0010Q\u001a\u00020\fH\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\f*\u00020\f2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000b0SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010Q\u001a\u00020\u000eH\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\u000e*\u00020\u000e2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\r0SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000fH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0010*\u00020\u00102\u0006\u0010Q\u001a\u00020\u0010H\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\u0010*\u00020\u00102\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000f0SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0011H\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0012*\u00020\u00122\u0006\u0010Q\u001a\u00020\u0012H\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\u0012*\u00020\u00122\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00110SH\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0013H\u0086\u0002\u001a\u0015\u0010O\u001a\u00020\u0014*\u00020\u00142\u0006\u0010Q\u001a\u00020\u0014H\u0086\u0002\u001a\u001b\u0010O\u001a\u00020\u0014*\u00020\u00142\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00130SH\u0086\u0002\u001a,\u0010U\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010P\u001a\u001d\u0010V\u001a\u00020D\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010W\u001a*\u0010V\u001a\u00020D\"\u000e\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020X*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b¢\u0006\u0002\u0010Y\u001a1\u0010V\u001a\u00020D\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010Z\u001a=\u0010V\u001a\u00020D\"\u000e\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020X*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000fH\u0007¢\u0006\u0002\u0010[\u001a\n\u0010V\u001a\u00020D*\u00020\b\u001a\u001e\u0010V\u001a\u00020D*\u00020\b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\n\u001a\u001e\u0010V\u001a\u00020D*\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\f\u001a\u001e\u0010V\u001a\u00020D*\u00020\f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\u000e\u001a\u001e\u0010V\u001a\u00020D*\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\u0010\u001a\u001e\u0010V\u001a\u00020D*\u00020\u00102\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\u0012\u001a\u001e\u0010V\u001a\u00020D*\u00020\u00122\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010V\u001a\u00020D*\u00020\u0014\u001a\u001e\u0010V\u001a\u00020D*\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a9\u0010\\\u001a\u00020D\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b��\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b��\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010]\u001aM\u0010\\\u001a\u00020D\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b��\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b��\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010^\u001a9\u0010_\u001a\u00020`\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0004\bc\u0010d\u001a9\u0010_\u001a\u00020e\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0004\bf\u0010g\u001a)\u0010_\u001a\u00020`*\u00020\u00062\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\u00062\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\b2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\b2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\n2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\n2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\f2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\f2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\u000e2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\u000e2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\u00102\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\u00102\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\u00122\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\u00122\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a)\u0010_\u001a\u00020`*\u00020\u00142\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020`0bH\u0087\bø\u0001��¢\u0006\u0002\bc\u001a)\u0010_\u001a\u00020e*\u00020\u00142\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020e0bH\u0087\bø\u0001��¢\u0006\u0002\bf\u001a-\u0010h\u001a\b\u0012\u0004\u0012\u0002H\u00020i\"\u000e\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020X*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010j\u001a?\u0010h\u001a\b\u0012\u0004\u0012\u0002H\u00020i\"\u0004\b��\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b��\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b��\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010k\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00050i*\u00020\u0006\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00070i*\u00020\b\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\t0i*\u00020\n\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u000b0i*\u00020\f\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\r0i*\u00020\u000e\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u000f0i*\u00020\u0010\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00110i*\u00020\u0012\u001a\u0010\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00130i*\u00020\u0014\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003*\u00020\u0006¢\u0006\u0002\u0010m\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003*\u00020\b¢\u0006\u0002\u0010n\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\t0\u0003*\u00020\n¢\u0006\u0002\u0010o\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003*\u00020\f¢\u0006\u0002\u0010p\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\r0\u0003*\u00020\u000e¢\u0006\u0002\u0010q\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003*\u00020\u0010¢\u0006\u0002\u0010r\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003*\u00020\u0012¢\u0006\u0002\u0010s\u001a\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003*\u00020\u0014¢\u0006\u0002\u0010t\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006u"}, m53d2 = {"asList", "", "T", "", "([Ljava/lang/Object;)Ljava/util/List;", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "binarySearch", "element", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;II)I", "([Ljava/lang/Object;Ljava/lang/Object;II)I", "contentDeepEquals", "other", "contentDeepEqualsInline", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepEqualsNullable", "contentDeepHashCode", "contentDeepHashCodeInline", "([Ljava/lang/Object;)I", "contentDeepHashCodeNullable", "contentDeepToString", "", "contentDeepToStringInline", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringNullable", "contentEquals", "contentEqualsNullable", "contentHashCode", "contentHashCodeNullable", "contentToString", "contentToStringNullable", "copyInto", "destination", "destinationOffset", "startIndex", "endIndex", "([Ljava/lang/Object;[Ljava/lang/Object;III)[Ljava/lang/Object;", "copyOf", "([Ljava/lang/Object;)[Ljava/lang/Object;", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRange", "copyOfRangeInline", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "copyOfRangeImpl", "elementAt", "index", "([Ljava/lang/Object;I)Ljava/lang/Object;", "fill", "", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "filterIsInstance", "R", "klass", Constants.CLASS, "([Ljava/lang/Object;Ljava/lang/Class;)Ljava/util/List;", "filterIsInstanceTo", "C", "", "([Ljava/lang/Object;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "plus", "([Ljava/lang/Object;Ljava/lang/Object;)[Ljava/lang/Object;", "elements", "([Ljava/lang/Object;[Ljava/lang/Object;)[Ljava/lang/Object;", "", "([Ljava/lang/Object;Ljava/util/Collection;)[Ljava/lang/Object;", "plusElement", "sort", "([Ljava/lang/Object;)V", "", "([Ljava/lang/Comparable;)V", "([Ljava/lang/Object;II)V", "([Ljava/lang/Comparable;II)V", "sortWith", "([Ljava/lang/Object;Ljava/util/Comparator;)V", "([Ljava/lang/Object;Ljava/util/Comparator;II)V", "sumOf", "Ljava/math/BigDecimal;", "selector", "Lkotlin/Function1;", "sumOfBigDecimal", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/math/BigDecimal;", "Ljava/math/BigInteger;", "sumOfBigInteger", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/math/BigInteger;", "toSortedSet", "Ljava/util/SortedSet;", "([Ljava/lang/Comparable;)Ljava/util/SortedSet;", "([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/SortedSet;", "toTypedArray", "([Z)[Ljava/lang/Boolean;", "([B)[Ljava/lang/Byte;", "([C)[Ljava/lang/Character;", "([D)[Ljava/lang/Double;", "([F)[Ljava/lang/Float;", "([I)[Ljava/lang/Integer;", "([J)[Ljava/lang/Long;", "([S)[Ljava/lang/Short;", "kotlin-stdlib"}, m48xs = "kotlin/collections/ArraysKt")
/* renamed from: kotlin.collections.ArraysKt___ArraysJvmKt */
/* loaded from: Jackey Client b2.jar:kotlin/collections/ArraysKt___ArraysJvmKt.class */
public class _ArraysJvm extends ArraysKt__ArraysKt {
    @InlineOnly
    private static final <T> T elementAt(T[] tArr, int index) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return tArr[index];
    }

    @InlineOnly
    private static final byte elementAt(byte[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final short elementAt(short[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final int elementAt(int[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final long elementAt(long[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final float elementAt(float[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final double elementAt(double[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final boolean elementAt(boolean[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @InlineOnly
    private static final char elementAt(char[] $this$elementAt, int index) {
        Intrinsics.checkNotNullParameter($this$elementAt, "<this>");
        return $this$elementAt[index];
    }

    @NotNull
    public static final <R> List<R> filterIsInstance(@NotNull Object[] $this$filterIsInstance, @NotNull Class<R> klass) {
        Intrinsics.checkNotNullParameter($this$filterIsInstance, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (List) ArraysKt.filterIsInstanceTo($this$filterIsInstance, new ArrayList(), klass);
    }

    @NotNull
    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(@NotNull Object[] $this$filterIsInstanceTo, @NotNull C destination, @NotNull Class<R> klass) {
        Intrinsics.checkNotNullParameter($this$filterIsInstanceTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(klass, "klass");
        int i = 0;
        int length = $this$filterIsInstanceTo.length;
        while (i < length) {
            Object element = $this$filterIsInstanceTo[i];
            i++;
            if (klass.isInstance(element)) {
                destination.add(element);
            }
        }
        return destination;
    }

    @NotNull
    public static final <T> List<T> asList(@NotNull T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        List<T> asList = ArraysUtilJVM.asList(tArr);
        Intrinsics.checkNotNullExpressionValue(asList, "asList(this)");
        return asList;
    }

    @NotNull
    public static final List<Byte> asList(@NotNull byte[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$1($this$asList);
    }

    @NotNull
    public static final List<Short> asList(@NotNull short[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$2($this$asList);
    }

    @NotNull
    public static final List<Integer> asList(@NotNull int[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$3($this$asList);
    }

    @NotNull
    public static final List<Long> asList(@NotNull long[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$4($this$asList);
    }

    @NotNull
    public static final List<Float> asList(@NotNull float[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$5($this$asList);
    }

    @NotNull
    public static final List<Double> asList(@NotNull double[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$6($this$asList);
    }

    @NotNull
    public static final List<Boolean> asList(@NotNull boolean[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$7($this$asList);
    }

    @NotNull
    public static final List<Character> asList(@NotNull char[] $this$asList) {
        Intrinsics.checkNotNullParameter($this$asList, "<this>");
        return new ArraysKt___ArraysJvmKt$asList$8($this$asList);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, Comparator comparator, int i, int i2, int i3, Object obj2) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch(objArr, obj, comparator, i, i2);
    }

    public static final <T> int binarySearch(@NotNull T[] tArr, T t, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return Arrays.binarySearch(tArr, fromIndex, toIndex, t, comparator);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch(objArr, obj, i, i2);
    }

    public static final <T> int binarySearch(@NotNull T[] tArr, T t, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return Arrays.binarySearch(tArr, fromIndex, toIndex, t);
    }

    public static /* synthetic */ int binarySearch$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return ArraysKt.binarySearch(bArr, b, i, i2);
    }

    public static final int binarySearch(@NotNull byte[] $this$binarySearch, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        return ArraysKt.binarySearch(sArr, s, i, i2);
    }

    public static final int binarySearch(@NotNull short[] $this$binarySearch, short element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.binarySearch(iArr, i, i2, i3);
    }

    public static final int binarySearch(@NotNull int[] $this$binarySearch, int element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        return ArraysKt.binarySearch(jArr, j, i, i2);
    }

    public static final int binarySearch(@NotNull long[] $this$binarySearch, long element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        return ArraysKt.binarySearch(fArr, f, i, i2);
    }

    public static final int binarySearch(@NotNull float[] $this$binarySearch, float element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        return ArraysKt.binarySearch(dArr, d, i, i2);
    }

    public static final int binarySearch(@NotNull double[] $this$binarySearch, double element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        return ArraysKt.binarySearch(cArr, c, i, i2);
    }

    public static final int binarySearch(@NotNull char[] $this$binarySearch, char element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    @SinceKotlin(version = "1.1")
    @JvmName(name = "contentDeepEqualsInline")
    @InlineOnly
    @LowPriorityInOverloadResolution
    private static final <T> boolean contentDeepEqualsInline(T[] tArr, T[] other) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return ArraysKt.contentDeepEquals(tArr, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentDeepEqualsNullable")
    @InlineOnly
    private static final <T> boolean contentDeepEqualsNullable(T[] tArr, T[] tArr2) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepEquals(tArr, tArr2);
        }
        return Arrays.deepEquals(tArr, tArr2);
    }

    @SinceKotlin(version = "1.1")
    @JvmName(name = "contentDeepHashCodeInline")
    @InlineOnly
    @LowPriorityInOverloadResolution
    private static final <T> int contentDeepHashCodeInline(T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return ArraysKt.contentDeepHashCode(tArr);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentDeepHashCodeNullable")
    @InlineOnly
    private static final <T> int contentDeepHashCodeNullable(T[] tArr) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepHashCode(tArr);
        }
        return Arrays.deepHashCode(tArr);
    }

    @SinceKotlin(version = "1.1")
    @JvmName(name = "contentDeepToStringInline")
    @InlineOnly
    @LowPriorityInOverloadResolution
    private static final <T> String contentDeepToStringInline(T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return ArraysKt.contentDeepToString(tArr);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentDeepToStringNullable")
    @InlineOnly
    private static final <T> String contentDeepToStringNullable(T[] tArr) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepToString(tArr);
        }
        String deepToString = Arrays.deepToString(tArr);
        Intrinsics.checkNotNullExpressionValue(deepToString, "deepToString(this)");
        return deepToString;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ <T> boolean contentEquals(T[] tArr, T[] other) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals(tArr, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(byte[] $this$contentEquals, byte[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(short[] $this$contentEquals, short[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(int[] $this$contentEquals, int[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(long[] $this$contentEquals, long[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(float[] $this$contentEquals, float[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(double[] $this$contentEquals, double[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(boolean[] $this$contentEquals, boolean[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ boolean contentEquals(char[] $this$contentEquals, char[] other) {
        Intrinsics.checkNotNullParameter($this$contentEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final <T> boolean contentEqualsNullable(T[] tArr, T[] tArr2) {
        return Arrays.equals(tArr, tArr2);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(byte[] $this$contentEquals, byte[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(short[] $this$contentEquals, short[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(int[] $this$contentEquals, int[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(long[] $this$contentEquals, long[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(float[] $this$contentEquals, float[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(double[] $this$contentEquals, double[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(boolean[] $this$contentEquals, boolean[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentEqualsNullable")
    @InlineOnly
    private static final boolean contentEqualsNullable(char[] $this$contentEquals, char[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final <T> int contentHashCodeNullable(T[] tArr) {
        return Arrays.hashCode(tArr);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(byte[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(short[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(int[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(long[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(float[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(double[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(boolean[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentHashCodeNullable")
    @InlineOnly
    private static final int contentHashCodeNullable(char[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ <T> String contentToString(T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        String arrays = Arrays.toString(tArr);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(byte[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(short[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(int[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(long[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(float[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(double[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(boolean[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    @Annotations(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    private static final /* synthetic */ String contentToString(char[] $this$contentToString) {
        Intrinsics.checkNotNullParameter($this$contentToString, "<this>");
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final <T> String contentToStringNullable(T[] tArr) {
        String arrays = Arrays.toString(tArr);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(byte[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(short[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(int[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(long[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(float[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(double[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(boolean[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "contentToStringNullable")
    @InlineOnly
    private static final String contentToStringNullable(char[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
        return arrays;
    }

    public static /* synthetic */ Object[] copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        return ArraysKt.copyInto(objArr, objArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> T[] copyInto(@NotNull T[] tArr, @NotNull T[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(tArr, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ byte[] copyInto$default(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = bArr.length;
        }
        return ArraysKt.copyInto(bArr, bArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final byte[] copyInto(@NotNull byte[] $this$copyInto, @NotNull byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ short[] copyInto$default(short[] sArr, short[] sArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = sArr.length;
        }
        return ArraysKt.copyInto(sArr, sArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final short[] copyInto(@NotNull short[] $this$copyInto, @NotNull short[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ int[] copyInto$default(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.copyInto(iArr, iArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final int[] copyInto(@NotNull int[] $this$copyInto, @NotNull int[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ long[] copyInto$default(long[] jArr, long[] jArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = jArr.length;
        }
        return ArraysKt.copyInto(jArr, jArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final long[] copyInto(@NotNull long[] $this$copyInto, @NotNull long[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ float[] copyInto$default(float[] fArr, float[] fArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = fArr.length;
        }
        return ArraysKt.copyInto(fArr, fArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final float[] copyInto(@NotNull float[] $this$copyInto, @NotNull float[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ double[] copyInto$default(double[] dArr, double[] dArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = dArr.length;
        }
        return ArraysKt.copyInto(dArr, dArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final double[] copyInto(@NotNull double[] $this$copyInto, @NotNull double[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ boolean[] copyInto$default(boolean[] zArr, boolean[] zArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = zArr.length;
        }
        return ArraysKt.copyInto(zArr, zArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final boolean[] copyInto(@NotNull boolean[] $this$copyInto, @NotNull boolean[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ char[] copyInto$default(char[] cArr, char[] cArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = cArr.length;
        }
        return ArraysKt.copyInto(cArr, cArr2, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final char[] copyInto(@NotNull char[] $this$copyInto, @NotNull char[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$copyInto, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    @InlineOnly
    private static final <T> T[] copyOf(T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, tArr.length);
        Intrinsics.checkNotNullExpressionValue(tArr2, "copyOf(this, size)");
        return tArr2;
    }

    @InlineOnly
    private static final byte[] copyOf(byte[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        byte[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final short[] copyOf(short[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        short[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final int[] copyOf(int[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        int[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final long[] copyOf(long[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        long[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final float[] copyOf(float[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        float[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final double[] copyOf(double[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        double[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final boolean[] copyOf(boolean[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        boolean[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final char[] copyOf(char[] $this$copyOf) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        char[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    @InlineOnly
    private static final byte[] copyOf(byte[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        byte[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final short[] copyOf(short[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        short[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final int[] copyOf(int[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        int[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final long[] copyOf(long[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        long[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final float[] copyOf(float[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        float[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final double[] copyOf(double[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        double[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final boolean[] copyOf(boolean[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        boolean[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final char[] copyOf(char[] $this$copyOf, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOf, "<this>");
        char[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    @InlineOnly
    private static final <T> T[] copyOf(T[] tArr, int newSize) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, newSize);
        Intrinsics.checkNotNullExpressionValue(tArr2, "copyOf(this, newSize)");
        return tArr2;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final <T> T[] copyOfRangeInline(T[] tArr, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return (T[]) ArraysKt.copyOfRange(tArr, fromIndex, toIndex);
        }
        if (toIndex > tArr.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + tArr.length);
        }
        T[] tArr2 = (T[]) Arrays.copyOfRange(tArr, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(tArr2, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return tArr2;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final byte[] copyOfRangeInline(byte[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        byte[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final short[] copyOfRangeInline(short[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        short[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final int[] copyOfRangeInline(int[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        int[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final long[] copyOfRangeInline(long[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        long[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final float[] copyOfRangeInline(float[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        float[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final double[] copyOfRangeInline(double[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        double[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final boolean[] copyOfRangeInline(boolean[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        boolean[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @JvmName(name = "copyOfRangeInline")
    @InlineOnly
    private static final char[] copyOfRangeInline(char[] $this$copyOfRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRange, "<this>");
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex > $this$copyOfRange.length) {
            throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $this$copyOfRange.length);
        }
        char[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "{\n        if (toIndex > …fromIndex, toIndex)\n    }");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final <T> T[] copyOfRange(@NotNull T[] tArr, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, tArr.length);
        T[] tArr2 = (T[]) Arrays.copyOfRange(tArr, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(tArr2, "copyOfRange(this, fromIndex, toIndex)");
        return tArr2;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final byte[] copyOfRange(@NotNull byte[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        byte[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final short[] copyOfRange(@NotNull short[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        short[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final int[] copyOfRange(@NotNull int[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        int[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final long[] copyOfRange(@NotNull long[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        long[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final float[] copyOfRange(@NotNull float[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        float[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final double[] copyOfRange(@NotNull double[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        double[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final boolean[] copyOfRange(@NotNull boolean[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        boolean[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "copyOfRange")
    @NotNull
    @PublishedApi
    public static final char[] copyOfRange(@NotNull char[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$copyOfRangeImpl, "<this>");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        char[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static /* synthetic */ void fill$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.fill(objArr, obj, i, i2);
    }

    public static final <T> void fill(@NotNull T[] tArr, T t, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Arrays.fill(tArr, fromIndex, toIndex, t);
    }

    public static /* synthetic */ void fill$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.fill(bArr, b, i, i2);
    }

    public static final void fill(@NotNull byte[] $this$fill, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.fill(sArr, s, i, i2);
    }

    public static final void fill(@NotNull short[] $this$fill, short element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        ArraysKt.fill(iArr, i, i2, i3);
    }

    public static final void fill(@NotNull int[] $this$fill, int element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.fill(jArr, j, i, i2);
    }

    public static final void fill(@NotNull long[] $this$fill, long element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.fill(fArr, f, i, i2);
    }

    public static final void fill(@NotNull float[] $this$fill, float element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.fill(dArr, d, i, i2);
    }

    public static final void fill(@NotNull double[] $this$fill, double element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(boolean[] zArr, boolean z, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = zArr.length;
        }
        ArraysKt.fill(zArr, z, i, i2);
    }

    public static final void fill(@NotNull boolean[] $this$fill, boolean element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.fill(cArr, c, i, i2);
    }

    public static final void fill(@NotNull char[] $this$fill, char element, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$fill, "<this>");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] tArr, T t) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        int index = tArr.length;
        T[] result = (T[]) Arrays.copyOf(tArr, index + 1);
        result[index] = t;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $this$plus, byte element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        byte[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $this$plus, short element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        short[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $this$plus, int element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        int[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $this$plus, long element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        long[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $this$plus, float element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        float[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $this$plus, double element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        double[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $this$plus, boolean element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        boolean[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $this$plus, char element) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        int index = $this$plus.length;
        char[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] tArr, @NotNull Collection<? extends T> elements) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = tArr.length;
        T[] result = (T[]) Arrays.copyOf(tArr, index + elements.size());
        for (Object element : elements) {
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $this$plus, @NotNull Collection<Byte> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        byte[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Byte b : elements) {
            byte element = b.byteValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $this$plus, @NotNull Collection<Short> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        short[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Short sh : elements) {
            short element = sh.shortValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $this$plus, @NotNull Collection<Integer> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        int[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Integer num : elements) {
            int element = num.intValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $this$plus, @NotNull Collection<Long> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        long[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Long l : elements) {
            long element = l.longValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $this$plus, @NotNull Collection<Float> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        float[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Float f : elements) {
            float element = f.floatValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $this$plus, @NotNull Collection<Double> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        double[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Double d : elements) {
            double element = d.doubleValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $this$plus, @NotNull Collection<Boolean> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        boolean[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Boolean bool : elements) {
            boolean element = bool.booleanValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $this$plus, @NotNull Collection<Character> elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int index = $this$plus.length;
        char[] result = Arrays.copyOf($this$plus, index + elements.size());
        for (Character ch : elements) {
            char element = ch.charValue();
            int i = index;
            index = i + 1;
            result[i] = element;
        }
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] tArr, @NotNull T[] elements) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = tArr.length;
        int arraySize = elements.length;
        T[] result = (T[]) Arrays.copyOf(tArr, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $this$plus, @NotNull byte[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        byte[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $this$plus, @NotNull short[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        short[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $this$plus, @NotNull int[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        int[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $this$plus, @NotNull long[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        long[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $this$plus, @NotNull float[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        float[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $this$plus, @NotNull double[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        double[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $this$plus, @NotNull boolean[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        boolean[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $this$plus, @NotNull char[] elements) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        char[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkNotNullExpressionValue(result, "result");
        return result;
    }

    @InlineOnly
    private static final <T> T[] plusElement(T[] tArr, T t) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return (T[]) ArraysKt.plus(tArr, t);
    }

    public static final void sort(@NotNull int[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull long[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull byte[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull short[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull double[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull float[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(@NotNull char[] $this$sort) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    @InlineOnly
    private static final <T extends Comparable<? super T>> void sort(T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        ArraysKt.sort((Object[]) tArr);
    }

    public static final <T> void sort(@NotNull T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        if (tArr.length > 1) {
            Arrays.sort(tArr);
        }
    }

    public static /* synthetic */ void sort$default(Comparable[] comparableArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = comparableArr.length;
        }
        ArraysKt.sort(comparableArr, i, i2);
    }

    @SinceKotlin(version = "1.4")
    public static final <T extends Comparable<? super T>> void sort(@NotNull T[] tArr, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Arrays.sort(tArr, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.sort(bArr, i, i2);
    }

    public static final void sort(@NotNull byte[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(short[] sArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.sort(sArr, i, i2);
    }

    public static final void sort(@NotNull short[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(int[] iArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = iArr.length;
        }
        ArraysKt.sort(iArr, i, i2);
    }

    public static final void sort(@NotNull int[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(long[] jArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.sort(jArr, i, i2);
    }

    public static final void sort(@NotNull long[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(float[] fArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.sort(fArr, i, i2);
    }

    public static final void sort(@NotNull float[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(double[] dArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.sort(dArr, i, i2);
    }

    public static final void sort(@NotNull double[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(char[] cArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.sort(cArr, i, i2);
    }

    public static final void sort(@NotNull char[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$sort, "<this>");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(Object[] objArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sort(objArr, i, i2);
    }

    public static final <T> void sort(@NotNull T[] tArr, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Arrays.sort(tArr, fromIndex, toIndex);
    }

    public static final <T> void sortWith(@NotNull T[] tArr, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        if (tArr.length > 1) {
            Arrays.sort(tArr, comparator);
        }
    }

    public static /* synthetic */ void sortWith$default(Object[] objArr, Comparator comparator, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sortWith(objArr, comparator, i, i2);
    }

    public static final <T> void sortWith(@NotNull T[] tArr, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Arrays.sort(tArr, fromIndex, toIndex, comparator);
    }

    @NotNull
    public static final Byte[] toTypedArray(@NotNull byte[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Byte[] result = new Byte[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Byte.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Short[] toTypedArray(@NotNull short[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Short[] result = new Short[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Short.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Integer[] toTypedArray(@NotNull int[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Integer[] result = new Integer[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Integer.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Long[] toTypedArray(@NotNull long[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Long[] result = new Long[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Long.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Float[] toTypedArray(@NotNull float[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Float[] result = new Float[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Float.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Double[] toTypedArray(@NotNull double[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Double[] result = new Double[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Double.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Boolean[] toTypedArray(@NotNull boolean[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Boolean[] result = new Boolean[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Boolean.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final Character[] toTypedArray(@NotNull char[] $this$toTypedArray) {
        Intrinsics.checkNotNullParameter($this$toTypedArray, "<this>");
        Character[] result = new Character[$this$toTypedArray.length];
        int i = 0;
        int length = $this$toTypedArray.length;
        while (i < length) {
            int index = i;
            i++;
            result[index] = Character.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(@NotNull T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return (SortedSet) ArraysKt.toCollection(tArr, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Byte> toSortedSet(@NotNull byte[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Short> toSortedSet(@NotNull short[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Integer> toSortedSet(@NotNull int[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Long> toSortedSet(@NotNull long[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Float> toSortedSet(@NotNull float[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Double> toSortedSet(@NotNull double[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Boolean> toSortedSet(@NotNull boolean[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final SortedSet<Character> toSortedSet(@NotNull char[] $this$toSortedSet) {
        Intrinsics.checkNotNullParameter($this$toSortedSet, "<this>");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    @NotNull
    public static final <T> SortedSet<T> toSortedSet(@NotNull T[] tArr, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (SortedSet) ArraysKt.toCollection(tArr, new TreeSet(comparator));
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final <T> BigDecimal sumOfBigDecimal(T[] tArr, Function1<? super T, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = tArr.length;
        while (i < length) {
            i++;
            BigDecimal add = sum.add(selector.invoke(tArr[i]));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(byte[] $this$sumOf, Function1<? super Byte, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            byte element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Byte.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(short[] $this$sumOf, Function1<? super Short, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            short element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Short.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(int[] $this$sumOf, Function1<? super Integer, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            int element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Integer.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(long[] $this$sumOf, Function1<? super Long, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            long element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Long.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(float[] $this$sumOf, Function1<? super Float, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            float element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Float.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(double[] $this$sumOf, Function1<? super Double, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            double element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Double.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(boolean[] $this$sumOf, Function1<? super Boolean, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            boolean element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Boolean.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigDecimal")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigDecimal sumOfBigDecimal(char[] $this$sumOf, Function1<? super Character, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal valueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigDecimal sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            char element = $this$sumOf[i];
            i++;
            BigDecimal add = sum.add(selector.invoke(Character.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final <T> BigInteger sumOfBigInteger(T[] tArr, Function1<? super T, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = tArr.length;
        while (i < length) {
            i++;
            BigInteger add = sum.add(selector.invoke(tArr[i]));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(byte[] $this$sumOf, Function1<? super Byte, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            byte element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Byte.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(short[] $this$sumOf, Function1<? super Short, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            short element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Short.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(int[] $this$sumOf, Function1<? super Integer, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            int element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Integer.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(long[] $this$sumOf, Function1<? super Long, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            long element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Long.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(float[] $this$sumOf, Function1<? super Float, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            float element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Float.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(double[] $this$sumOf, Function1<? super Double, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            double element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Double.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(boolean[] $this$sumOf, Function1<? super Boolean, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            boolean element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Boolean.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }

    @SinceKotlin(version = "1.4")
    @JvmName(name = "sumOfBigInteger")
    @InlineOnly
    @OverloadResolutionByLambdaReturnType
    private static final BigInteger sumOfBigInteger(char[] $this$sumOf, Function1<? super Character, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter($this$sumOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger valueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
        BigInteger sum = valueOf;
        int i = 0;
        int length = $this$sumOf.length;
        while (i < length) {
            char element = $this$sumOf[i];
            i++;
            BigInteger add = sum.add(selector.invoke(Character.valueOf(element)));
            Intrinsics.checkNotNullExpressionValue(add, "this.add(other)");
            sum = add;
        }
        return sum;
    }
}
