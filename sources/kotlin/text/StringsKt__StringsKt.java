package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Annotations;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.Tuples;
import kotlin.TuplesKt;
import kotlin.WasExperimental;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.HTTP;

/* compiled from: Strings.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u0084\u0001\n��\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n��\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b!\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H��\u001a\u001c\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0011\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u001f\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0086\u0002\u001a\u0015\u0010\u0012\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\n\u001a\u0018\u0010\u0017\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H��\u001a\u0018\u0010\u0018\u001a\u00020\u0010*\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H��\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a:\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001aE\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0010H\u0002¢\u0006\u0002\b!\u001a:\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\u001c*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010#\u001a\u00020\u0010*\u00020\u00022\u0006\u0010$\u001a\u00020\u0006\u001a7\u0010%\u001a\u0002H&\"\f\b��\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\bø\u0001��¢\u0006\u0002\u0010*\u001a7\u0010+\u001a\u0002H&\"\f\b��\u0010'*\u00020\u0002*\u0002H&\"\u0004\b\u0001\u0010&*\u0002H'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0)H\u0087\bø\u0001��¢\u0006\u0002\u0010*\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a;\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010 \u001a\u00020\u0010H\u0002¢\u0006\u0002\b.\u001a&\u0010,\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u00100\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u00100\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\r\u00103\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a\r\u00105\u001a\u00020\u0010*\u00020\u0002H\u0087\b\u001a \u00106\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b��\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010��\u001a \u00107\u001a\u00020\u0010*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b��\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010��\u001a\r\u00108\u001a\u000209*\u00020\u0002H\u0086\u0002\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010:\u001a\u00020\u0006*\u00020\u00022\u0006\u0010/\u001a\u00020\r2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a&\u0010;\u001a\u00020\u0006*\u00020\u00022\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a,\u0010;\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010<\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u0002\u001a\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u0002\u001a\u0015\u0010@\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0087\f\u001a\u000f\u0010A\u001a\u00020\r*\u0004\u0018\u00010\rH\u0087\b\u001a\u001c\u0010B\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010B\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\u0002*\u00020\u00022\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001a\u001c\u0010E\u001a\u00020\r*\u00020\r2\u0006\u0010C\u001a\u00020\u00062\b\b\u0002\u0010D\u001a\u00020\u0014\u001aG\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u000e\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0004\bI\u0010J\u001a=\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00010=*\u00020\u00022\u0006\u0010G\u001a\u0002022\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0002\bI\u001a4\u0010K\u001a\u00020\u0010*\u00020\u00022\u0006\u0010L\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H��\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u0002\u001a\u001a\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006\u001a\u0012\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010P\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010R\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010R\u001a\u00020\r*\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u0012\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u0002\u001a\u001a\u0010S\u001a\u00020\r*\u00020\r2\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a.\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0014\b\b\u0010V\u001a\u000e\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020\u00020WH\u0087\bø\u0001��\u001a\u001d\u0010U\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010Z\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010\\\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010]\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a$\u0010^\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010Y\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001d\u0010_\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010Y\u001a\u00020\rH\u0087\b\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140WH\u0087\bø\u0001��¢\u0006\u0002\ba\u001a)\u0010`\u001a\u00020\r*\u00020\r2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00020WH\u0087\bø\u0001��¢\u0006\u0002\bb\u001a\"\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002\u001a\u001a\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002\u001a%\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010c\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u0002H\u0087\b\u001a=\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010e\u001a0\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a/\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010T\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0006H\u0002¢\u0006\u0002\bf\u001a%\u0010d\u001a\b\u0012\u0004\u0012\u00020\r0?*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a=\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0012\u0010G\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0H\"\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010h\u001a0\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\n\u0010G\u001a\u000202\"\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u001a%\u0010g\u001a\b\u0012\u0004\u0012\u00020\r0=*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a$\u0010i\u001a\u00020\u0010*\u00020\u00022\u0006\u0010O\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0012\u0010j\u001a\u00020\u0002*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u001d\u0010j\u001a\u00020\u0002*\u00020\r2\u0006\u0010k\u001a\u00020\u00062\u0006\u0010l\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010-\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010m\u001a\u00020\r*\u00020\u00022\u0006\u0010Q\u001a\u00020\u0001\u001a\u0012\u0010m\u001a\u00020\r*\u00020\r2\u0006\u0010Q\u001a\u00020\u0001\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010n\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010o\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010p\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\u00142\b\b\u0002\u0010[\u001a\u00020\r\u001a\u001c\u0010q\u001a\u00020\r*\u00020\r2\u0006\u0010T\u001a\u00020\r2\b\b\u0002\u0010[\u001a\u00020\r\u001a\f\u0010r\u001a\u00020\u0010*\u00020\rH\u0007\u001a\u0013\u0010s\u001a\u0004\u0018\u00010\u0010*\u00020\rH\u0007¢\u0006\u0002\u0010t\u001a\n\u0010u\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010u\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010u\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010u\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010u\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010u\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010w\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010w\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010w\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010w\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010w\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010w\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\u001a\n\u0010x\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010x\u001a\u00020\u0002*\u00020\u00022\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010x\u001a\u00020\u0002*\u00020\u00022\n\u00101\u001a\u000202\"\u00020\u0014\u001a\r\u0010x\u001a\u00020\r*\u00020\rH\u0087\b\u001a$\u0010x\u001a\u00020\r*\u00020\r2\u0012\u0010v\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100WH\u0086\bø\u0001��\u001a\u0016\u0010x\u001a\u00020\r*\u00020\r2\n\u00101\u001a\u000202\"\u00020\u0014\"\u0015\u0010��\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006y"}, m53d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "requireNonNegativeLimit", "", "limit", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "contentEqualsIgnoreCaseImpl", "contentEqualsImpl", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", AsmConstants.CODERANGE, "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceFirstChar", "replaceFirstCharWithChar", "replaceFirstCharWithCharSequence", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", AsmConstants.END, "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "toBooleanStrict", "toBooleanStrictOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"}, m48xs = "kotlin/text/StringsKt")
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__StringsKt.class */
public class StringsKt__StringsKt extends StringsJVM {
    @NotNull
    public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int startIndex = 0;
        int endIndex = $this$trim.length() - 1;
        boolean startFound = false;
        while (startIndex <= endIndex) {
            int index = !startFound ? startIndex : endIndex;
            boolean match = predicate.invoke(Character.valueOf($this$trim.charAt(index))).booleanValue();
            if (!startFound) {
                if (!match) {
                    startFound = true;
                } else {
                    startIndex++;
                }
            } else if (!match) {
                break;
            } else {
                endIndex--;
            }
        }
        return $this$trim.subSequence(startIndex, endIndex + 1);
    }

    @NotNull
    public static final String trim(@NotNull String $this$trim, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String $this$trim$iv = $this$trim;
        int startIndex$iv = 0;
        int endIndex$iv = $this$trim$iv.length() - 1;
        boolean startFound$iv = false;
        while (startIndex$iv <= endIndex$iv) {
            int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
            boolean match$iv = predicate.invoke(Character.valueOf($this$trim$iv.charAt(index$iv))).booleanValue();
            if (!startFound$iv) {
                if (!match$iv) {
                    startFound$iv = true;
                } else {
                    startIndex$iv++;
                }
            } else if (!match$iv) {
                break;
            } else {
                endIndex$iv--;
            }
        }
        return $this$trim$iv.subSequence(startIndex$iv, endIndex$iv + 1).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int i = 0;
        int length = $this$trimStart.length();
        while (i < length) {
            int index = i;
            i++;
            if (!predicate.invoke(Character.valueOf($this$trimStart.charAt(index))).booleanValue()) {
                return $this$trimStart.subSequence(index, $this$trimStart.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String trimStart(@NotNull String $this$trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
        String str;
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String $this$trimStart$iv = $this$trimStart;
        int i = 0;
        int length = $this$trimStart$iv.length();
        while (true) {
            if (i < length) {
                int index$iv = i;
                i++;
                if (!predicate.invoke(Character.valueOf($this$trimStart$iv.charAt(index$iv))).booleanValue()) {
                    str = $this$trimStart$iv.subSequence(index$iv, $this$trimStart$iv.length());
                    break;
                }
            } else {
                break;
            }
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int length = $this$trimEnd.length() - 1;
        if (0 <= length) {
            do {
                int index = length;
                length--;
                if (!predicate.invoke(Character.valueOf($this$trimEnd.charAt(index))).booleanValue()) {
                    return $this$trimEnd.subSequence(0, index + 1);
                }
            } while (0 <= length);
            return "";
        }
        return "";
    }

    @NotNull
    public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
        String str;
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String $this$trimEnd$iv = $this$trimEnd;
        int length = $this$trimEnd$iv.length() - 1;
        if (0 <= length) {
            do {
                int index$iv = length;
                length--;
                if (!predicate.invoke(Character.valueOf($this$trimEnd$iv.charAt(index$iv))).booleanValue()) {
                    str = $this$trimEnd$iv.subSequence(0, index$iv + 1);
                    break;
                }
            } while (0 <= length);
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence $this$trim, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int startIndex$iv = 0;
        int endIndex$iv = $this$trim.length() - 1;
        boolean startFound$iv = false;
        while (startIndex$iv <= endIndex$iv) {
            int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
            char it = $this$trim.charAt(index$iv);
            boolean match$iv = ArraysKt.contains(chars, it);
            if (!startFound$iv) {
                if (!match$iv) {
                    startFound$iv = true;
                } else {
                    startIndex$iv++;
                }
            } else if (!match$iv) {
                break;
            } else {
                endIndex$iv--;
            }
        }
        return $this$trim.subSequence(startIndex$iv, endIndex$iv + 1);
    }

    @NotNull
    public static final String trim(@NotNull String $this$trim, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String $this$trim$iv$iv = $this$trim;
        int startIndex$iv$iv = 0;
        int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
        boolean startFound$iv$iv = false;
        while (startIndex$iv$iv <= endIndex$iv$iv) {
            int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
            char it = $this$trim$iv$iv.charAt(index$iv$iv);
            boolean match$iv$iv = ArraysKt.contains(chars, it);
            if (!startFound$iv$iv) {
                if (!match$iv$iv) {
                    startFound$iv$iv = true;
                } else {
                    startIndex$iv$iv++;
                }
            } else if (!match$iv$iv) {
                break;
            } else {
                endIndex$iv$iv--;
            }
        }
        return $this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int i = 0;
        int length = $this$trimStart.length();
        while (i < length) {
            int index$iv = i;
            i++;
            char it = $this$trimStart.charAt(index$iv);
            if (!ArraysKt.contains(chars, it)) {
                return $this$trimStart.subSequence(index$iv, $this$trimStart.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String trimStart(@NotNull String $this$trimStart, @NotNull char... chars) {
        String str;
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String $this$trimStart$iv$iv = $this$trimStart;
        int i = 0;
        int length = $this$trimStart$iv$iv.length();
        while (true) {
            if (i < length) {
                int index$iv$iv = i;
                i++;
                char it = $this$trimStart$iv$iv.charAt(index$iv$iv);
                if (!ArraysKt.contains(chars, it)) {
                    str = $this$trimStart$iv$iv.subSequence(index$iv$iv, $this$trimStart$iv$iv.length());
                    break;
                }
            } else {
                break;
            }
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int length = $this$trimEnd.length() - 1;
        if (0 <= length) {
            do {
                int index$iv = length;
                length--;
                char it = $this$trimEnd.charAt(index$iv);
                if (!ArraysKt.contains(chars, it)) {
                    return $this$trimEnd.subSequence(0, index$iv + 1);
                }
            } while (0 <= length);
            return "";
        }
        return "";
    }

    @NotNull
    public static final String trimEnd(@NotNull String $this$trimEnd, @NotNull char... chars) {
        String str;
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String $this$trimEnd$iv$iv = $this$trimEnd;
        int length = $this$trimEnd$iv$iv.length() - 1;
        if (0 <= length) {
            do {
                int index$iv$iv = length;
                length--;
                char it = $this$trimEnd$iv$iv.charAt(index$iv$iv);
                if (!ArraysKt.contains(chars, it)) {
                    str = $this$trimEnd$iv$iv.subSequence(0, index$iv$iv + 1);
                    break;
                }
            } while (0 <= length);
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence $this$trim) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        int startIndex$iv = 0;
        int endIndex$iv = $this$trim.length() - 1;
        boolean startFound$iv = false;
        while (startIndex$iv <= endIndex$iv) {
            int index$iv = !startFound$iv ? startIndex$iv : endIndex$iv;
            char p0 = $this$trim.charAt(index$iv);
            boolean match$iv = CharsKt.isWhitespace(p0);
            if (!startFound$iv) {
                if (!match$iv) {
                    startFound$iv = true;
                } else {
                    startIndex$iv++;
                }
            } else if (!match$iv) {
                break;
            } else {
                endIndex$iv--;
            }
        }
        return $this$trim.subSequence(startIndex$iv, endIndex$iv + 1);
    }

    @InlineOnly
    private static final String trim(String $this$trim) {
        Intrinsics.checkNotNullParameter($this$trim, "<this>");
        return StringsKt.trim((CharSequence) $this$trim).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence $this$trimStart) {
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        int i = 0;
        int length = $this$trimStart.length();
        while (i < length) {
            int index$iv = i;
            i++;
            char p0 = $this$trimStart.charAt(index$iv);
            if (!CharsKt.isWhitespace(p0)) {
                return $this$trimStart.subSequence(index$iv, $this$trimStart.length());
            }
        }
        return "";
    }

    @InlineOnly
    private static final String trimStart(String $this$trimStart) {
        Intrinsics.checkNotNullParameter($this$trimStart, "<this>");
        return StringsKt.trimStart((CharSequence) $this$trimStart).toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence $this$trimEnd) {
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        int length = $this$trimEnd.length() - 1;
        if (0 <= length) {
            do {
                int index$iv = length;
                length--;
                char p0 = $this$trimEnd.charAt(index$iv);
                if (!CharsKt.isWhitespace(p0)) {
                    return $this$trimEnd.subSequence(0, index$iv + 1);
                }
            } while (0 <= length);
            return "";
        }
        return "";
    }

    @InlineOnly
    private static final String trimEnd(String $this$trimEnd) {
        Intrinsics.checkNotNullParameter($this$trimEnd, "<this>");
        return StringsKt.trimEnd((CharSequence) $this$trimEnd).toString();
    }

    public static /* synthetic */ CharSequence padStart$default(CharSequence charSequence, int i, char c, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c = ' ';
        }
        return StringsKt.padStart(charSequence, i, c);
    }

    @NotNull
    public static final CharSequence padStart(@NotNull CharSequence $this$padStart, int length, char padChar) {
        int i;
        Intrinsics.checkNotNullParameter($this$padStart, "<this>");
        if (length < 0) {
            throw new IllegalArgumentException("Desired length " + length + " is less than zero.");
        }
        if (length <= $this$padStart.length()) {
            return $this$padStart.subSequence(0, $this$padStart.length());
        }
        StringBuilder sb = new StringBuilder(length);
        int i2 = 1;
        int length2 = length - $this$padStart.length();
        if (1 <= length2) {
            do {
                i = i2;
                i2++;
                sb.append(padChar);
            } while (i != length2);
            sb.append($this$padStart);
            return sb;
        }
        sb.append($this$padStart);
        return sb;
    }

    public static /* synthetic */ String padStart$default(String str, int i, char c, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c = ' ';
        }
        return StringsKt.padStart(str, i, c);
    }

    @NotNull
    public static final String padStart(@NotNull String $this$padStart, int length, char padChar) {
        Intrinsics.checkNotNullParameter($this$padStart, "<this>");
        return StringsKt.padStart((CharSequence) $this$padStart, length, padChar).toString();
    }

    public static /* synthetic */ CharSequence padEnd$default(CharSequence charSequence, int i, char c, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c = ' ';
        }
        return StringsKt.padEnd(charSequence, i, c);
    }

    @NotNull
    public static final CharSequence padEnd(@NotNull CharSequence $this$padEnd, int length, char padChar) {
        int i;
        Intrinsics.checkNotNullParameter($this$padEnd, "<this>");
        if (length < 0) {
            throw new IllegalArgumentException("Desired length " + length + " is less than zero.");
        }
        if (length <= $this$padEnd.length()) {
            return $this$padEnd.subSequence(0, $this$padEnd.length());
        }
        StringBuilder sb = new StringBuilder(length);
        sb.append($this$padEnd);
        int i2 = 1;
        int length2 = length - $this$padEnd.length();
        if (1 <= length2) {
            do {
                i = i2;
                i2++;
                sb.append(padChar);
            } while (i != length2);
            return sb;
        }
        return sb;
    }

    public static /* synthetic */ String padEnd$default(String str, int i, char c, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c = ' ';
        }
        return StringsKt.padEnd(str, i, c);
    }

    @NotNull
    public static final String padEnd(@NotNull String $this$padEnd, int length, char padChar) {
        Intrinsics.checkNotNullParameter($this$padEnd, "<this>");
        return StringsKt.padEnd((CharSequence) $this$padEnd, length, padChar).toString();
    }

    @InlineOnly
    private static final boolean isNullOrEmpty(CharSequence $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || $this$isNullOrEmpty.length() == 0;
    }

    @InlineOnly
    private static final boolean isEmpty(CharSequence $this$isEmpty) {
        Intrinsics.checkNotNullParameter($this$isEmpty, "<this>");
        return $this$isEmpty.length() == 0;
    }

    @InlineOnly
    private static final boolean isNotEmpty(CharSequence $this$isNotEmpty) {
        Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.length() > 0;
    }

    @InlineOnly
    private static final boolean isNotBlank(CharSequence $this$isNotBlank) {
        Intrinsics.checkNotNullParameter($this$isNotBlank, "<this>");
        return !StringsKt.isBlank($this$isNotBlank);
    }

    @InlineOnly
    private static final boolean isNullOrBlank(CharSequence $this$isNullOrBlank) {
        return $this$isNullOrBlank == null || StringsKt.isBlank($this$isNullOrBlank);
    }

    @NotNull
    public static final CharIterator iterator(@NotNull final CharSequence $this$iterator) {
        Intrinsics.checkNotNullParameter($this$iterator, "<this>");
        return new CharIterator() { // from class: kotlin.text.StringsKt__StringsKt$iterator$1
            private int index;

            @Override // kotlin.collections.CharIterator
            public char nextChar() {
                CharSequence charSequence = $this$iterator;
                int i = this.index;
                this.index = i + 1;
                return charSequence.charAt(i);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < $this$iterator.length();
            }
        };
    }

    @InlineOnly
    private static final String orEmpty(String $this$orEmpty) {
        return $this$orEmpty == null ? "" : $this$orEmpty;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final <C extends CharSequence & R, R> R ifEmpty(C c, Functions<? extends R> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return c.length() == 0 ? defaultValue.invoke() : c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final <C extends CharSequence & R, R> R ifBlank(C c, Functions<? extends R> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return StringsKt.isBlank(c) ? defaultValue.invoke() : c;
    }

    @NotNull
    public static final IntRange getIndices(@NotNull CharSequence $this$indices) {
        Intrinsics.checkNotNullParameter($this$indices, "<this>");
        return new IntRange(0, $this$indices.length() - 1);
    }

    public static final int getLastIndex(@NotNull CharSequence $this$lastIndex) {
        Intrinsics.checkNotNullParameter($this$lastIndex, "<this>");
        return $this$lastIndex.length() - 1;
    }

    public static final boolean hasSurrogatePairAt(@NotNull CharSequence $this$hasSurrogatePairAt, int index) {
        Intrinsics.checkNotNullParameter($this$hasSurrogatePairAt, "<this>");
        return (0 <= index ? index <= $this$hasSurrogatePairAt.length() - 2 : false) && Character.isHighSurrogate($this$hasSurrogatePairAt.charAt(index)) && Character.isLowSurrogate($this$hasSurrogatePairAt.charAt(index + 1));
    }

    @NotNull
    public static final String substring(@NotNull String $this$substring, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter($this$substring, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        String substring = $this$substring.substring(range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence subSequence(@NotNull CharSequence $this$subSequence, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter($this$subSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        return $this$subSequence.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
    }

    @Annotations(message = "Use parameters named startIndex and endIndex.", replaceWith = @ReplaceWith(expression = "subSequence(startIndex = start, endIndex = end)", imports = {}))
    @InlineOnly
    private static final CharSequence subSequence(String $this$subSequence, int start, int end) {
        Intrinsics.checkNotNullParameter($this$subSequence, "<this>");
        return $this$subSequence.subSequence(start, end);
    }

    @InlineOnly
    private static final String substring(CharSequence $this$substring, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$substring, "<this>");
        return $this$substring.subSequence(startIndex, endIndex).toString();
    }

    static /* synthetic */ String substring$default(CharSequence $this$substring_u24default, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 2) != 0) {
            endIndex = $this$substring_u24default.length();
        }
        Intrinsics.checkNotNullParameter($this$substring_u24default, "<this>");
        return $this$substring_u24default.subSequence(startIndex, endIndex).toString();
    }

    @NotNull
    public static final String substring(@NotNull CharSequence $this$substring, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter($this$substring, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        return $this$substring.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1).toString();
    }

    public static /* synthetic */ String substringBefore$default(String str, char c, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringBefore(str, c, str2);
    }

    @NotNull
    public static final String substringBefore(@NotNull String $this$substringBefore, char delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringBefore, "<this>");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$substringBefore, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBefore.substring(0, index);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBefore$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringBefore(str, str2, str3);
    }

    @NotNull
    public static final String substringBefore(@NotNull String $this$substringBefore, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringBefore, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$substringBefore, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBefore.substring(0, index);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfter$default(String str, char c, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringAfter(str, c, str2);
    }

    @NotNull
    public static final String substringAfter(@NotNull String $this$substringAfter, char delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringAfter, "<this>");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$substringAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfter.substring(index + 1, $this$substringAfter.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfter$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringAfter(str, str2, str3);
    }

    @NotNull
    public static final String substringAfter(@NotNull String $this$substringAfter, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringAfter, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$substringAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfter.substring(index + delimiter.length(), $this$substringAfter.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBeforeLast$default(String str, char c, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringBeforeLast(str, c, str2);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, char delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringBeforeLast, "<this>");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$substringBeforeLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBeforeLast.substring(0, index);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBeforeLast$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringBeforeLast(str, str2, str3);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String $this$substringBeforeLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringBeforeLast, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$substringBeforeLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringBeforeLast.substring(0, index);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfterLast$default(String str, char c, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringAfterLast(str, c, str2);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String $this$substringAfterLast, char delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringAfterLast, "<this>");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$substringAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfterLast.substring(index + 1, $this$substringAfterLast.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfterLast$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringAfterLast(str, str2, str3);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String $this$substringAfterLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$substringAfterLast, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$substringAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        String substring = $this$substringAfterLast.substring(index + delimiter.length(), $this$substringAfterLast.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, int startIndex, int endIndex, @NotNull CharSequence replacement) {
        Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (endIndex < startIndex) {
            throw new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ").");
        }
        StringBuilder sb = new StringBuilder();
        Intrinsics.checkNotNullExpressionValue(sb.append($this$replaceRange, 0, startIndex), "this.append(value, startIndex, endIndex)");
        sb.append(replacement);
        Intrinsics.checkNotNullExpressionValue(sb.append($this$replaceRange, endIndex, $this$replaceRange.length()), "this.append(value, startIndex, endIndex)");
        return sb;
    }

    @InlineOnly
    private static final String replaceRange(String $this$replaceRange, int startIndex, int endIndex, CharSequence replacement) {
        Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return StringsKt.replaceRange((CharSequence) $this$replaceRange, startIndex, endIndex, replacement).toString();
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence $this$replaceRange, @NotNull IntRange range, @NotNull CharSequence replacement) {
        Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return StringsKt.replaceRange($this$replaceRange, range.getStart().intValue(), range.getEndInclusive().intValue() + 1, replacement);
    }

    @InlineOnly
    private static final String replaceRange(String $this$replaceRange, IntRange range, CharSequence replacement) {
        Intrinsics.checkNotNullParameter($this$replaceRange, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return StringsKt.replaceRange((CharSequence) $this$replaceRange, range, replacement).toString();
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
        if (endIndex < startIndex) {
            throw new IndexOutOfBoundsException("End index (" + endIndex + ") is less than start index (" + startIndex + ").");
        }
        if (endIndex == startIndex) {
            return $this$removeRange.subSequence(0, $this$removeRange.length());
        }
        StringBuilder sb = new StringBuilder($this$removeRange.length() - (endIndex - startIndex));
        Intrinsics.checkNotNullExpressionValue(sb.append($this$removeRange, 0, startIndex), "this.append(value, startIndex, endIndex)");
        Intrinsics.checkNotNullExpressionValue(sb.append($this$removeRange, endIndex, $this$removeRange.length()), "this.append(value, startIndex, endIndex)");
        return sb;
    }

    @InlineOnly
    private static final String removeRange(String $this$removeRange, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
        return StringsKt.removeRange((CharSequence) $this$removeRange, startIndex, endIndex).toString();
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence $this$removeRange, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        return StringsKt.removeRange($this$removeRange, range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
    }

    @InlineOnly
    private static final String removeRange(String $this$removeRange, IntRange range) {
        Intrinsics.checkNotNullParameter($this$removeRange, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        return StringsKt.removeRange((CharSequence) $this$removeRange, range).toString();
    }

    @NotNull
    public static final CharSequence removePrefix(@NotNull CharSequence $this$removePrefix, @NotNull CharSequence prefix) {
        Intrinsics.checkNotNullParameter($this$removePrefix, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (StringsKt.startsWith$default($this$removePrefix, prefix, false, 2, (Object) null)) {
            return $this$removePrefix.subSequence(prefix.length(), $this$removePrefix.length());
        }
        return $this$removePrefix.subSequence(0, $this$removePrefix.length());
    }

    @NotNull
    public static final String removePrefix(@NotNull String $this$removePrefix, @NotNull CharSequence prefix) {
        Intrinsics.checkNotNullParameter($this$removePrefix, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (StringsKt.startsWith$default((CharSequence) $this$removePrefix, prefix, false, 2, (Object) null)) {
            String substring = $this$removePrefix.substring(prefix.length());
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            return substring;
        }
        return $this$removePrefix;
    }

    @NotNull
    public static final CharSequence removeSuffix(@NotNull CharSequence $this$removeSuffix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter($this$removeSuffix, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (StringsKt.endsWith$default($this$removeSuffix, suffix, false, 2, (Object) null)) {
            return $this$removeSuffix.subSequence(0, $this$removeSuffix.length() - suffix.length());
        }
        return $this$removeSuffix.subSequence(0, $this$removeSuffix.length());
    }

    @NotNull
    public static final String removeSuffix(@NotNull String $this$removeSuffix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter($this$removeSuffix, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (StringsKt.endsWith$default((CharSequence) $this$removeSuffix, suffix, false, 2, (Object) null)) {
            String substring = $this$removeSuffix.substring(0, $this$removeSuffix.length() - suffix.length());
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
            return substring;
        }
        return $this$removeSuffix;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if ($this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default($this$removeSurrounding, prefix, false, 2, (Object) null) && StringsKt.endsWith$default($this$removeSurrounding, suffix, false, 2, (Object) null)) {
            return $this$removeSurrounding.subSequence(prefix.length(), $this$removeSurrounding.length() - suffix.length());
        }
        return $this$removeSurrounding.subSequence(0, $this$removeSurrounding.length());
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if ($this$removeSurrounding.length() >= prefix.length() + suffix.length() && StringsKt.startsWith$default((CharSequence) $this$removeSurrounding, prefix, false, 2, (Object) null) && StringsKt.endsWith$default((CharSequence) $this$removeSurrounding, suffix, false, 2, (Object) null)) {
            String substring = $this$removeSurrounding.substring(prefix.length(), $this$removeSurrounding.length() - suffix.length());
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
            return substring;
        }
        return $this$removeSurrounding;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence $this$removeSurrounding, @NotNull CharSequence delimiter) {
        Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String $this$removeSurrounding, @NotNull CharSequence delimiter) {
        Intrinsics.checkNotNullParameter($this$removeSurrounding, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        return StringsKt.removeSurrounding($this$removeSurrounding, delimiter, delimiter);
    }

    public static /* synthetic */ String replaceBefore$default(String str, char c, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceBefore(str, c, str2, str3);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String $this$replaceBefore, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceBefore, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$replaceBefore, delimiter, 0, false, 6, (Object) null);
        return index == -1 ? missingDelimiterValue : StringsKt.replaceRange((CharSequence) $this$replaceBefore, 0, index, (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceBefore$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceBefore(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String $this$replaceBefore, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceBefore, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$replaceBefore, delimiter, 0, false, 6, (Object) null);
        return index == -1 ? missingDelimiterValue : StringsKt.replaceRange((CharSequence) $this$replaceBefore, 0, index, (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceAfter$default(String str, char c, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceAfter(str, c, str2, str3);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String $this$replaceAfter, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceAfter, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$replaceAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        return StringsKt.replaceRange((CharSequence) $this$replaceAfter, index + 1, $this$replaceAfter.length(), (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceAfter$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceAfter(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String $this$replaceAfter, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceAfter, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.indexOf$default((CharSequence) $this$replaceAfter, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        return StringsKt.replaceRange((CharSequence) $this$replaceAfter, index + delimiter.length(), $this$replaceAfter.length(), (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceAfterLast$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceAfterLast(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceAfterLast, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$replaceAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        return StringsKt.replaceRange((CharSequence) $this$replaceAfterLast, index + delimiter.length(), $this$replaceAfterLast.length(), (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceAfterLast$default(String str, char c, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceAfterLast(str, c, str2, str3);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String $this$replaceAfterLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceAfterLast, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$replaceAfterLast, delimiter, 0, false, 6, (Object) null);
        if (index == -1) {
            return missingDelimiterValue;
        }
        return StringsKt.replaceRange((CharSequence) $this$replaceAfterLast, index + 1, $this$replaceAfterLast.length(), (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceBeforeLast$default(String str, char c, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceBeforeLast(str, c, str2, str3);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, char delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceBeforeLast, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$replaceBeforeLast, delimiter, 0, false, 6, (Object) null);
        return index == -1 ? missingDelimiterValue : StringsKt.replaceRange((CharSequence) $this$replaceBeforeLast, 0, index, (CharSequence) replacement).toString();
    }

    public static /* synthetic */ String replaceBeforeLast$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceBeforeLast(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String $this$replaceBeforeLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter($this$replaceBeforeLast, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int index = StringsKt.lastIndexOf$default((CharSequence) $this$replaceBeforeLast, delimiter, 0, false, 6, (Object) null);
        return index == -1 ? missingDelimiterValue : StringsKt.replaceRange((CharSequence) $this$replaceBeforeLast, 0, index, (CharSequence) replacement).toString();
    }

    @InlineOnly
    private static final String replace(CharSequence $this$replace, Regex regex, String replacement) {
        Intrinsics.checkNotNullParameter($this$replace, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return regex.replace($this$replace, replacement);
    }

    @InlineOnly
    private static final String replace(CharSequence $this$replace, Regex regex, Function1<? super MatchResult, ? extends CharSequence> transform) {
        Intrinsics.checkNotNullParameter($this$replace, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(transform, "transform");
        return regex.replace($this$replace, transform);
    }

    @InlineOnly
    private static final String replaceFirst(CharSequence $this$replaceFirst, Regex regex, String replacement) {
        Intrinsics.checkNotNullParameter($this$replaceFirst, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return regex.replaceFirst($this$replaceFirst, replacement);
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "replaceFirstCharWithChar")
    @InlineOnly
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @OverloadResolutionByLambdaReturnType
    private static final String replaceFirstCharWithChar(String $this$replaceFirstChar, Function1<? super Character, Character> transform) {
        Intrinsics.checkNotNullParameter($this$replaceFirstChar, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if ($this$replaceFirstChar.length() > 0) {
            char charValue = transform.invoke(Character.valueOf($this$replaceFirstChar.charAt(0))).charValue();
            String substring = $this$replaceFirstChar.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            return charValue + substring;
        }
        return $this$replaceFirstChar;
    }

    @SinceKotlin(version = "1.5")
    @JvmName(name = "replaceFirstCharWithCharSequence")
    @InlineOnly
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @OverloadResolutionByLambdaReturnType
    private static final String replaceFirstCharWithCharSequence(String $this$replaceFirstChar, Function1<? super Character, ? extends CharSequence> transform) {
        Intrinsics.checkNotNullParameter($this$replaceFirstChar, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if ($this$replaceFirstChar.length() > 0) {
            StringBuilder append = new StringBuilder().append((Object) transform.invoke(Character.valueOf($this$replaceFirstChar.charAt(0))));
            String substring = $this$replaceFirstChar.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            return append.append(substring).toString();
        }
        return $this$replaceFirstChar;
    }

    @InlineOnly
    private static final boolean matches(CharSequence $this$matches, Regex regex) {
        Intrinsics.checkNotNullParameter($this$matches, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.matches($this$matches);
    }

    public static final boolean regionMatchesImpl(@NotNull CharSequence $this$regionMatchesImpl, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$regionMatchesImpl, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (otherOffset < 0 || thisOffset < 0 || thisOffset > $this$regionMatchesImpl.length() - length || otherOffset > other.length() - length) {
            return false;
        }
        int i = 0;
        while (i < length) {
            int index = i;
            i++;
            if (!CharsKt.equals($this$regionMatchesImpl.charAt(thisOffset + index), other.charAt(otherOffset + index), ignoreCase)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, char c, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, c, z);
    }

    public static final boolean startsWith(@NotNull CharSequence $this$startsWith, char c, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        return $this$startsWith.length() > 0 && CharsKt.equals($this$startsWith.charAt(0), c, ignoreCase);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, char c, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.endsWith(charSequence, c, z);
    }

    public static final boolean endsWith(@NotNull CharSequence $this$endsWith, char c, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
        return $this$endsWith.length() > 0 && CharsKt.equals($this$endsWith.charAt(StringsKt.getLastIndex($this$endsWith)), c, ignoreCase);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, z);
    }

    public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!ignoreCase && ($this$startsWith instanceof String) && (prefix instanceof String)) {
            return StringsKt.startsWith$default((String) $this$startsWith, (String) prefix, false, 2, (Object) null);
        }
        return StringsKt.regionMatchesImpl($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, i, z);
    }

    public static final boolean startsWith(@NotNull CharSequence $this$startsWith, @NotNull CharSequence prefix, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!ignoreCase && ($this$startsWith instanceof String) && (prefix instanceof String)) {
            return StringsKt.startsWith$default((String) $this$startsWith, (String) prefix, startIndex, false, 4, (Object) null);
        }
        return StringsKt.regionMatchesImpl($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.endsWith(charSequence, charSequence2, z);
    }

    public static final boolean endsWith(@NotNull CharSequence $this$endsWith, @NotNull CharSequence suffix, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (!ignoreCase && ($this$endsWith instanceof String) && (suffix instanceof String)) {
            return StringsKt.endsWith$default((String) $this$endsWith, (String) suffix, false, 2, (Object) null);
        }
        return StringsKt.regionMatchesImpl($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
    }

    public static /* synthetic */ String commonPrefixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.commonPrefixWith(charSequence, charSequence2, z);
    }

    @NotNull
    public static final String commonPrefixWith(@NotNull CharSequence $this$commonPrefixWith, @NotNull CharSequence other, boolean ignoreCase) {
        int i;
        Intrinsics.checkNotNullParameter($this$commonPrefixWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int shortestLength = Math.min($this$commonPrefixWith.length(), other.length());
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= shortestLength || !CharsKt.equals($this$commonPrefixWith.charAt(i), other.charAt(i), ignoreCase)) {
                break;
            }
            i2 = i + 1;
        }
        if (StringsKt.hasSurrogatePairAt($this$commonPrefixWith, i - 1) || StringsKt.hasSurrogatePairAt(other, i - 1)) {
            i--;
        }
        return $this$commonPrefixWith.subSequence(0, i).toString();
    }

    public static /* synthetic */ String commonSuffixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.commonSuffixWith(charSequence, charSequence2, z);
    }

    @NotNull
    public static final String commonSuffixWith(@NotNull CharSequence $this$commonSuffixWith, @NotNull CharSequence other, boolean ignoreCase) {
        int i;
        Intrinsics.checkNotNullParameter($this$commonSuffixWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int thisLength = $this$commonSuffixWith.length();
        int otherLength = other.length();
        int shortestLength = Math.min(thisLength, otherLength);
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= shortestLength || !CharsKt.equals($this$commonSuffixWith.charAt((thisLength - i) - 1), other.charAt((otherLength - i) - 1), ignoreCase)) {
                break;
            }
            i2 = i + 1;
        }
        if (StringsKt.hasSurrogatePairAt($this$commonSuffixWith, (thisLength - i) - 1) || StringsKt.hasSurrogatePairAt(other, (otherLength - i) - 1)) {
            i--;
        }
        return $this$commonSuffixWith.subSequence(thisLength - i, thisLength).toString();
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOfAny(charSequence, cArr, i, z);
    }

    public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
        int index;
        boolean z;
        Intrinsics.checkNotNullParameter($this$indexOfAny, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        if (!ignoreCase && chars.length == 1 && ($this$indexOfAny instanceof String)) {
            return ((String) $this$indexOfAny).indexOf(ArraysKt.single(chars), startIndex);
        }
        int coerceAtLeast = RangesKt.coerceAtLeast(startIndex, 0);
        int lastIndex = StringsKt.getLastIndex($this$indexOfAny);
        if (coerceAtLeast <= lastIndex) {
            do {
                index = coerceAtLeast;
                coerceAtLeast++;
                char charAtIndex = $this$indexOfAny.charAt(index);
                int i = 0;
                int length = chars.length;
                while (true) {
                    if (i < length) {
                        char element$iv = chars[i];
                        i++;
                        if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return index;
                }
            } while (index != lastIndex);
            return -1;
        }
        return -1;
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, cArr, i, z);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull char[] chars, int startIndex, boolean ignoreCase) {
        boolean z;
        Intrinsics.checkNotNullParameter($this$lastIndexOfAny, "<this>");
        Intrinsics.checkNotNullParameter(chars, "chars");
        if (!ignoreCase && chars.length == 1 && ($this$lastIndexOfAny instanceof String)) {
            return ((String) $this$lastIndexOfAny).lastIndexOf(ArraysKt.single(chars), startIndex);
        }
        int coerceAtMost = RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$lastIndexOfAny));
        if (0 <= coerceAtMost) {
            do {
                int index = coerceAtMost;
                coerceAtMost--;
                char charAtIndex = $this$lastIndexOfAny.charAt(index);
                int i = 0;
                int length = chars.length;
                while (true) {
                    if (i < length) {
                        char element$iv = chars[i];
                        i++;
                        if (CharsKt.equals(element$iv, charAtIndex, ignoreCase)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return index;
                }
            } while (0 <= coerceAtMost);
            return -1;
        }
        return -1;
    }

    static /* synthetic */ int indexOf$StringsKt__StringsKt$default(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            z2 = false;
        }
        return indexOf$StringsKt__StringsKt(charSequence, charSequence2, i, i2, z, z2);
    }

    private static final int indexOf$StringsKt__StringsKt(CharSequence $this$indexOf, CharSequence other, int startIndex, int endIndex, boolean ignoreCase, boolean last) {
        IntRange intRange;
        int index;
        int index2;
        if (!last) {
            intRange = new IntRange(RangesKt.coerceAtLeast(startIndex, 0), RangesKt.coerceAtMost(endIndex, $this$indexOf.length()));
        } else {
            intRange = RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$indexOf)), RangesKt.coerceAtLeast(endIndex, 0));
        }
        IntProgression indices = intRange;
        if (($this$indexOf instanceof String) && (other instanceof String)) {
            int first = indices.getFirst();
            int last2 = indices.getLast();
            int step = indices.getStep();
            if ((step > 0 && first <= last2) || (step < 0 && last2 <= first)) {
                do {
                    index2 = first;
                    first += step;
                    if (StringsKt.regionMatches((String) other, 0, (String) $this$indexOf, index2, other.length(), ignoreCase)) {
                        return index2;
                    }
                } while (index2 != last2);
                return -1;
            }
            return -1;
        }
        int first2 = indices.getFirst();
        int last3 = indices.getLast();
        int step2 = indices.getStep();
        if ((step2 > 0 && first2 <= last3) || (step2 < 0 && last3 <= first2)) {
            do {
                index = first2;
                first2 += step2;
                if (StringsKt.regionMatchesImpl(other, 0, $this$indexOf, index, other.length(), ignoreCase)) {
                    return index;
                }
            } while (index != last3);
            return -1;
        }
        return -1;
    }

    public static final Tuples<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence $this$findAnyOf, Collection<String> collection, int startIndex, boolean ignoreCase, boolean last) {
        int index;
        Object obj;
        int index2;
        Object obj2;
        if (!ignoreCase && collection.size() == 1) {
            String string = (String) CollectionsKt.single(collection);
            int index3 = !last ? StringsKt.indexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object) null) : StringsKt.lastIndexOf$default($this$findAnyOf, string, startIndex, false, 4, (Object) null);
            if (index3 >= 0) {
                return TuplesKt.m46to(Integer.valueOf(index3), string);
            }
            return null;
        }
        IntProgression indices = !last ? new IntRange(RangesKt.coerceAtLeast(startIndex, 0), $this$findAnyOf.length()) : RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($this$findAnyOf)), 0);
        if ($this$findAnyOf instanceof String) {
            int first = indices.getFirst();
            int last2 = indices.getLast();
            int step = indices.getStep();
            if ((step > 0 && first <= last2) || (step < 0 && last2 <= first)) {
                do {
                    index2 = first;
                    first += step;
                    Collection<String> $this$firstOrNull$iv = collection;
                    Iterator<T> it = $this$firstOrNull$iv.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object element$iv = it.next();
                            String it2 = (String) element$iv;
                            if (StringsKt.regionMatches(it2, 0, (String) $this$findAnyOf, index2, it2.length(), ignoreCase)) {
                                obj2 = element$iv;
                                break;
                            }
                        } else {
                            obj2 = null;
                            break;
                        }
                    }
                    String matchingString = (String) obj2;
                    if (matchingString != null) {
                        return TuplesKt.m46to(Integer.valueOf(index2), matchingString);
                    }
                } while (index2 != last2);
                return null;
            }
            return null;
        }
        int first2 = indices.getFirst();
        int last3 = indices.getLast();
        int step2 = indices.getStep();
        if ((step2 > 0 && first2 <= last3) || (step2 < 0 && last3 <= first2)) {
            do {
                index = first2;
                first2 += step2;
                Collection<String> $this$firstOrNull$iv2 = collection;
                Iterator<T> it3 = $this$firstOrNull$iv2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object element$iv2 = it3.next();
                        String it4 = (String) element$iv2;
                        if (StringsKt.regionMatchesImpl(it4, 0, $this$findAnyOf, index, it4.length(), ignoreCase)) {
                            obj = element$iv2;
                            break;
                        }
                    } else {
                        obj = null;
                        break;
                    }
                }
                String matchingString2 = (String) obj;
                if (matchingString2 != null) {
                    return TuplesKt.m46to(Integer.valueOf(index), matchingString2);
                }
            } while (index != last3);
            return null;
        }
        return null;
    }

    public static /* synthetic */ Tuples findAnyOf$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.findAnyOf(charSequence, collection, i, z);
    }

    @Nullable
    public static final Tuples<Integer, String> findAnyOf(@NotNull CharSequence $this$findAnyOf, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$findAnyOf, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        return findAnyOf$StringsKt__StringsKt($this$findAnyOf, strings, startIndex, ignoreCase, false);
    }

    public static /* synthetic */ Tuples findLastAnyOf$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.findLastAnyOf(charSequence, collection, i, z);
    }

    @Nullable
    public static final Tuples<Integer, String> findLastAnyOf(@NotNull CharSequence $this$findLastAnyOf, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$findLastAnyOf, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        return findAnyOf$StringsKt__StringsKt($this$findLastAnyOf, strings, startIndex, ignoreCase, true);
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOfAny(charSequence, collection, i, z);
    }

    public static final int indexOfAny(@NotNull CharSequence $this$indexOfAny, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$indexOfAny, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Tuples<Integer, String> findAnyOf$StringsKt__StringsKt = findAnyOf$StringsKt__StringsKt($this$indexOfAny, strings, startIndex, ignoreCase, false);
        if (findAnyOf$StringsKt__StringsKt == null) {
            return -1;
        }
        return findAnyOf$StringsKt__StringsKt.getFirst().intValue();
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, collection, i, z);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence $this$lastIndexOfAny, @NotNull Collection<String> strings, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$lastIndexOfAny, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Tuples<Integer, String> findAnyOf$StringsKt__StringsKt = findAnyOf$StringsKt__StringsKt($this$lastIndexOfAny, strings, startIndex, ignoreCase, true);
        if (findAnyOf$StringsKt__StringsKt == null) {
            return -1;
        }
        return findAnyOf$StringsKt__StringsKt.getFirst().intValue();
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, char c, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOf(charSequence, c, i, z);
    }

    public static final int indexOf(@NotNull CharSequence $this$indexOf, char c, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$indexOf, "<this>");
        if (ignoreCase || !($this$indexOf instanceof String)) {
            return StringsKt.indexOfAny($this$indexOf, new char[]{c}, startIndex, ignoreCase);
        }
        return ((String) $this$indexOf).indexOf(c, startIndex);
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOf(charSequence, str, i, z);
    }

    public static final int indexOf(@NotNull CharSequence $this$indexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$indexOf, "<this>");
        Intrinsics.checkNotNullParameter(string, "string");
        if (ignoreCase || !($this$indexOf instanceof String)) {
            return indexOf$StringsKt__StringsKt$default($this$indexOf, string, startIndex, $this$indexOf.length(), ignoreCase, false, 16, null);
        }
        return ((String) $this$indexOf).indexOf(string, startIndex);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, char c, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOf(charSequence, c, i, z);
    }

    public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, char c, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$lastIndexOf, "<this>");
        if (ignoreCase || !($this$lastIndexOf instanceof String)) {
            return StringsKt.lastIndexOfAny($this$lastIndexOf, new char[]{c}, startIndex, ignoreCase);
        }
        return ((String) $this$lastIndexOf).lastIndexOf(c, startIndex);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOf(charSequence, str, i, z);
    }

    public static final int lastIndexOf(@NotNull CharSequence $this$lastIndexOf, @NotNull String string, int startIndex, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$lastIndexOf, "<this>");
        Intrinsics.checkNotNullParameter(string, "string");
        if (ignoreCase || !($this$lastIndexOf instanceof String)) {
            return indexOf$StringsKt__StringsKt($this$lastIndexOf, string, startIndex, 0, ignoreCase, true);
        }
        return ((String) $this$lastIndexOf).lastIndexOf(string, startIndex);
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.contains(charSequence, charSequence2, z);
    }

    public static final boolean contains(@NotNull CharSequence $this$contains, @NotNull CharSequence other, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return other instanceof String ? StringsKt.indexOf$default($this$contains, (String) other, 0, ignoreCase, 2, (Object) null) >= 0 : indexOf$StringsKt__StringsKt$default($this$contains, other, 0, $this$contains.length(), ignoreCase, false, 16, null) >= 0;
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, char c, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.contains(charSequence, c, z);
    }

    public static final boolean contains(@NotNull CharSequence $this$contains, char c, boolean ignoreCase) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        return StringsKt.indexOf$default($this$contains, c, 0, ignoreCase, 2, (Object) null) >= 0;
    }

    @InlineOnly
    private static final boolean contains(CharSequence $this$contains, Regex regex) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.containsMatchIn($this$contains);
    }

    static /* synthetic */ Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return rangesDelimitedBy$StringsKt__StringsKt(charSequence, cArr, i, z, i2);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence $this$rangesDelimitedBy, char[] delimiters, int startIndex, boolean ignoreCase, int limit) {
        StringsKt.requireNonNegativeLimit(limit);
        return new Strings($this$rangesDelimitedBy, startIndex, limit, new StringsKt__StringsKt$rangesDelimitedBy$1(delimiters, ignoreCase));
    }

    static /* synthetic */ Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, String[] strArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return rangesDelimitedBy$StringsKt__StringsKt(charSequence, strArr, i, z, i2);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence $this$rangesDelimitedBy, String[] delimiters, int startIndex, boolean ignoreCase, int limit) {
        StringsKt.requireNonNegativeLimit(limit);
        List delimitersList = ArraysKt.asList(delimiters);
        return new Strings($this$rangesDelimitedBy, startIndex, limit, new StringsKt__StringsKt$rangesDelimitedBy$2(delimitersList, ignoreCase));
    }

    public static final void requireNonNegativeLimit(int limit) {
        if (!(limit >= 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Limit must be non-negative, but was ", Integer.valueOf(limit)).toString());
        }
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.splitToSequence(charSequence, strArr, z, i);
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence $this$splitToSequence, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
        Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, delimiters, 0, ignoreCase, limit, 2, (Object) null), new StringsKt__StringsKt$splitToSequence$1($this$splitToSequence));
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.split(charSequence, strArr, z, i);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence $this$split, @NotNull String[] delimiters, boolean ignoreCase, int limit) {
        Intrinsics.checkNotNullParameter($this$split, "<this>");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        if (delimiters.length == 1) {
            String delimiter = delimiters[0];
            if (!(delimiter.length() == 0)) {
                return split$StringsKt__StringsKt($this$split, delimiter, ignoreCase, limit);
            }
        }
        Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, delimiters, 0, ignoreCase, limit, 2, (Object) null));
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            IntRange it = (IntRange) item$iv$iv;
            destination$iv$iv.add(StringsKt.substring($this$split, it));
        }
        return (List) destination$iv$iv;
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, char[] cArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.splitToSequence(charSequence, cArr, z, i);
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence $this$splitToSequence, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
        Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($this$splitToSequence, delimiters, 0, ignoreCase, limit, 2, (Object) null), new StringsKt__StringsKt$splitToSequence$2($this$splitToSequence));
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, char[] cArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.split(charSequence, cArr, z, i);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence $this$split, @NotNull char[] delimiters, boolean ignoreCase, int limit) {
        Intrinsics.checkNotNullParameter($this$split, "<this>");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        if (delimiters.length == 1) {
            return split$StringsKt__StringsKt($this$split, String.valueOf(delimiters[0]), ignoreCase, limit);
        }
        Iterable $this$map$iv = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default($this$split, delimiters, 0, ignoreCase, limit, 2, (Object) null));
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            IntRange it = (IntRange) item$iv$iv;
            destination$iv$iv.add(StringsKt.substring($this$split, it));
        }
        return (List) destination$iv$iv;
    }

    private static final List<String> split$StringsKt__StringsKt(CharSequence $this$split, String delimiter, boolean ignoreCase, int limit) {
        StringsKt.requireNonNegativeLimit(limit);
        int currentOffset = 0;
        int nextIndex = StringsKt.indexOf($this$split, delimiter, 0, ignoreCase);
        if (nextIndex == -1 || limit == 1) {
            return CollectionsKt.listOf($this$split.toString());
        }
        boolean isLimited = limit > 0;
        ArrayList result = new ArrayList(isLimited ? RangesKt.coerceAtMost(limit, 10) : 10);
        do {
            result.add($this$split.subSequence(currentOffset, nextIndex).toString());
            currentOffset = nextIndex + delimiter.length();
            if (isLimited && result.size() == limit - 1) {
                break;
            }
            nextIndex = StringsKt.indexOf($this$split, delimiter, currentOffset, ignoreCase);
        } while (nextIndex != -1);
        result.add($this$split.subSequence(currentOffset, $this$split.length()).toString());
        return result;
    }

    @InlineOnly
    private static final List<String> split(CharSequence $this$split, Regex regex, int limit) {
        Intrinsics.checkNotNullParameter($this$split, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.split($this$split, limit);
    }

    static /* synthetic */ List split$default(CharSequence $this$split_u24default, Regex regex, int limit, int i, Object obj) {
        if ((i & 2) != 0) {
            limit = 0;
        }
        Intrinsics.checkNotNullParameter($this$split_u24default, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.split($this$split_u24default, limit);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final Sequence<String> splitToSequence(CharSequence $this$splitToSequence, Regex regex, int limit) {
        Intrinsics.checkNotNullParameter($this$splitToSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.splitToSequence($this$splitToSequence, limit);
    }

    static /* synthetic */ Sequence splitToSequence$default(CharSequence $this$splitToSequence_u24default, Regex regex, int limit, int i, Object obj) {
        if ((i & 2) != 0) {
            limit = 0;
        }
        Intrinsics.checkNotNullParameter($this$splitToSequence_u24default, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        return regex.splitToSequence($this$splitToSequence_u24default, limit);
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull CharSequence $this$lineSequence) {
        Intrinsics.checkNotNullParameter($this$lineSequence, "<this>");
        return StringsKt.splitToSequence$default($this$lineSequence, new String[]{HTTP.CRLF, "\n", "\r"}, false, 0, 6, (Object) null);
    }

    @NotNull
    public static final List<String> lines(@NotNull CharSequence $this$lines) {
        Intrinsics.checkNotNullParameter($this$lines, "<this>");
        return SequencesKt.toList(StringsKt.lineSequence($this$lines));
    }

    public static final boolean contentEqualsIgnoreCaseImpl(@Nullable CharSequence $this$contentEqualsIgnoreCaseImpl, @Nullable CharSequence other) {
        if (($this$contentEqualsIgnoreCaseImpl instanceof String) && (other instanceof String)) {
            return StringsKt.equals((String) $this$contentEqualsIgnoreCaseImpl, (String) other, true);
        }
        if ($this$contentEqualsIgnoreCaseImpl == other) {
            return true;
        }
        if ($this$contentEqualsIgnoreCaseImpl == null || other == null || $this$contentEqualsIgnoreCaseImpl.length() != other.length()) {
            return false;
        }
        int i = 0;
        int length = $this$contentEqualsIgnoreCaseImpl.length();
        while (i < length) {
            int i2 = i;
            i++;
            if (!CharsKt.equals($this$contentEqualsIgnoreCaseImpl.charAt(i2), other.charAt(i2), true)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean contentEqualsImpl(@Nullable CharSequence $this$contentEqualsImpl, @Nullable CharSequence other) {
        if (($this$contentEqualsImpl instanceof String) && (other instanceof String)) {
            return Intrinsics.areEqual($this$contentEqualsImpl, other);
        }
        if ($this$contentEqualsImpl == other) {
            return true;
        }
        if ($this$contentEqualsImpl == null || other == null || $this$contentEqualsImpl.length() != other.length()) {
            return false;
        }
        int i = 0;
        int length = $this$contentEqualsImpl.length();
        while (i < length) {
            int i2 = i;
            i++;
            if ($this$contentEqualsImpl.charAt(i2) != other.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.5")
    public static final boolean toBooleanStrict(@NotNull String $this$toBooleanStrict) {
        Intrinsics.checkNotNullParameter($this$toBooleanStrict, "<this>");
        if (Intrinsics.areEqual($this$toBooleanStrict, "true")) {
            return true;
        }
        if (!Intrinsics.areEqual($this$toBooleanStrict, "false")) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("The string doesn't represent a boolean value: ", $this$toBooleanStrict));
        }
        return false;
    }

    @SinceKotlin(version = "1.5")
    @Nullable
    public static final Boolean toBooleanStrictOrNull(@NotNull String $this$toBooleanStrictOrNull) {
        Intrinsics.checkNotNullParameter($this$toBooleanStrictOrNull, "<this>");
        if (Intrinsics.areEqual($this$toBooleanStrictOrNull, "true")) {
            return true;
        }
        return Intrinsics.areEqual($this$toBooleanStrictOrNull, "false") ? false : null;
    }
}
