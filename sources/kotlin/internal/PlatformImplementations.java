package kotlin.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PlatformImplementations.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010 \n\u0002\b\u0002\b\u0010\u0018��2\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\u0013"}, m53d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "getSuppressed", "", "ReflectThrowable", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/internal/PlatformImplementations.class */
public class PlatformImplementations {

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: PlatformImplementations.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��¨\u0006\u0006"}, m53d2 = {"Lkotlin/internal/PlatformImplementations$ReflectThrowable;", "", "()V", "addSuppressed", "Ljava/lang/reflect/Method;", "getSuppressed", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/internal/PlatformImplementations$ReflectThrowable.class */
    public static final class ReflectThrowable {
        @NotNull
        public static final ReflectThrowable INSTANCE = new ReflectThrowable();
        @JvmPlatformAnnotations
        @Nullable
        public static final Method addSuppressed;
        @JvmPlatformAnnotations
        @Nullable
        public static final Method getSuppressed;

        private ReflectThrowable() {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0071 A[SYNTHETIC] */
        static {
            /*
                kotlin.internal.PlatformImplementations$ReflectThrowable r0 = new kotlin.internal.PlatformImplementations$ReflectThrowable
                r1 = r0
                r1.<init>()
                kotlin.internal.PlatformImplementations.ReflectThrowable.INSTANCE = r0
                java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
                r3 = r0
                r0 = r3
                java.lang.reflect.Method[] r0 = r0.getMethods()
                r4 = r0
                r0 = r4
                java.lang.String r1 = "throwableMethods"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
                r0 = r4
                r5 = r0
                r0 = r5
                r6 = r0
                r0 = r6
                r7 = r0
                r0 = 0
                r8 = r0
                r0 = r7
                int r0 = r0.length
                r9 = r0
            L27:
                r0 = r8
                r1 = r9
                if (r0 >= r1) goto L76
                r0 = r7
                r1 = r8
                r0 = r0[r1]
                r10 = r0
                int r8 = r8 + 1
                r0 = r10
                r11 = r0
                r0 = 0
                r12 = r0
                r0 = r11
                java.lang.String r0 = r0.getName()
                java.lang.String r1 = "addSuppressed"
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
                if (r0 == 0) goto L6d
                r0 = r11
                java.lang.Class[] r0 = r0.getParameterTypes()
                r13 = r0
                r0 = r13
                java.lang.String r1 = "it.parameterTypes"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
                r0 = r13
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r0 = kotlin.collections.ArraysKt.singleOrNull(r0)
                r1 = r3
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
                if (r0 == 0) goto L6d
                r0 = 1
                goto L6e
            L6d:
                r0 = 0
            L6e:
                if (r0 == 0) goto L27
                r0 = r10
                goto L77
            L76:
                r0 = 0
            L77:
                kotlin.internal.PlatformImplementations.ReflectThrowable.addSuppressed = r0
                r0 = r4
                r5 = r0
                r0 = r5
                r6 = r0
                r0 = r6
                r7 = r0
                r0 = 0
                r8 = r0
                r0 = r7
                int r0 = r0.length
                r9 = r0
            L89:
                r0 = r8
                r1 = r9
                if (r0 >= r1) goto Lb3
                r0 = r7
                r1 = r8
                r0 = r0[r1]
                r10 = r0
                int r8 = r8 + 1
                r0 = r10
                r11 = r0
                r0 = 0
                r12 = r0
                r0 = r11
                java.lang.String r0 = r0.getName()
                java.lang.String r1 = "getSuppressed"
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
                if (r0 == 0) goto L89
                r0 = r10
                goto Lb4
            Lb3:
                r0 = 0
            Lb4:
                kotlin.internal.PlatformImplementations.ReflectThrowable.getSuppressed = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementations.ReflectThrowable.m2430clinit():void");
        }
    }

    public void addSuppressed(@NotNull Throwable cause, @NotNull Throwable exception) {
        Intrinsics.checkNotNullParameter(cause, "cause");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Method method = ReflectThrowable.addSuppressed;
        if (method == null) {
            return;
        }
        method.invoke(cause, exception);
    }

    @NotNull
    public List<Throwable> getSuppressed(@NotNull Throwable exception) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        Method method = ReflectThrowable.getSuppressed;
        Object it = method == null ? null : method.invoke(exception, new Object[0]);
        return it == null ? CollectionsKt.emptyList() : ArraysKt.asList((Throwable[]) it);
    }

    @Nullable
    public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
        Intrinsics.checkNotNullParameter(matchResult, "matchResult");
        Intrinsics.checkNotNullParameter(name, "name");
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    @NotNull
    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }
}
