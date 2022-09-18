package kotlin.reflect;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;

/* compiled from: KTypeProjection.kt */
@SinceKotlin(version = "1.1")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018�� \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020��2\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, m53d2 = {"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/reflect/KTypeProjection.class */
public final class KTypeProjection {
    @Nullable
    private final KVariance variance;
    @Nullable
    private final KType type;
    @NotNull
    public static final Companion Companion = new Companion(null);
    @JvmPlatformAnnotations
    @NotNull
    public static final KTypeProjection star = new KTypeProjection(null, null);

    /* compiled from: KTypeProjection.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:kotlin/reflect/KTypeProjection$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KVariance.values().length];
            iArr[KVariance.INVARIANT.ordinal()] = 1;
            iArr[KVariance.IN.ordinal()] = 2;
            iArr[KVariance.OUT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Nullable
    public final KVariance component1() {
        return this.variance;
    }

    @Nullable
    public final KType component2() {
        return this.type;
    }

    @NotNull
    public final KTypeProjection copy(@Nullable KVariance variance, @Nullable KType type) {
        return new KTypeProjection(variance, type);
    }

    public static /* synthetic */ KTypeProjection copy$default(KTypeProjection kTypeProjection, KVariance kVariance, KType kType, int i, Object obj) {
        if ((i & 1) != 0) {
            kVariance = kTypeProjection.variance;
        }
        if ((i & 2) != 0) {
            kType = kTypeProjection.type;
        }
        return kTypeProjection.copy(kVariance, kType);
    }

    public int hashCode() {
        int result = this.variance == null ? 0 : this.variance.hashCode();
        return (result * 31) + (this.type == null ? 0 : this.type.hashCode());
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KTypeProjection)) {
            return false;
        }
        KTypeProjection kTypeProjection = (KTypeProjection) other;
        return this.variance == kTypeProjection.variance && Intrinsics.areEqual(this.type, kTypeProjection.type);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection invariant(@NotNull KType type) {
        return Companion.invariant(type);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection contravariant(@NotNull KType type) {
        return Companion.contravariant(type);
    }

    @JvmStatic
    @NotNull
    public static final KTypeProjection covariant(@NotNull KType type) {
        return Companion.covariant(type);
    }

    public KTypeProjection(@Nullable KVariance variance, @Nullable KType type) {
        String str;
        this.variance = variance;
        this.type = type;
        if ((this.variance == null) == (this.type == null)) {
            return;
        }
        if (getVariance() == null) {
            str = "Star projection must have no type specified.";
        } else {
            str = "The projection variance " + getVariance() + " requires type to be specified.";
        }
        throw new IllegalArgumentException(str.toString());
    }

    @Nullable
    public final KVariance getVariance() {
        return this.variance;
    }

    @Nullable
    public final KType getType() {
        return this.type;
    }

    @NotNull
    public String toString() {
        KVariance kVariance = this.variance;
        switch (kVariance == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kVariance.ordinal()]) {
            case -1:
                return Marker.ANY_MARKER;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                return String.valueOf(this.type);
            case 2:
                return Intrinsics.stringPlus("in ", this.type);
            case 3:
                return Intrinsics.stringPlus("out ", this.type);
        }
    }

    /* compiled from: KTypeProjection.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u00020\u00048��X\u0081\u0004¢\u0006\b\n��\u0012\u0004\b\b\u0010\u0002¨\u0006\u000e"}, m53d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "star", "getStar$annotations", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/reflect/KTypeProjection$Companion.class */
    public static final class Companion {
        @PublishedApi
        public static /* synthetic */ void getStar$annotations() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final KTypeProjection getSTAR() {
            return KTypeProjection.star;
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection invariant(@NotNull KType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new KTypeProjection(KVariance.INVARIANT, type);
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection contravariant(@NotNull KType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new KTypeProjection(KVariance.IN, type);
        }

        @JvmStatic
        @NotNull
        public final KTypeProjection covariant(@NotNull KType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new KTypeProjection(KVariance.OUT, type);
        }
    }
}
