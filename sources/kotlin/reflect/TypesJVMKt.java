package kotlin.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmClassMapping;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

/* compiled from: TypesJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��0\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\u001a\"\u0010\n\u001a\u00020\u00012\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0001H\u0002\u001a\u0016\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0003\"\u001e\u0010��\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001e\u0010��\u001a\u00020\u0001*\u00020\u00078BX\u0083\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\b\u001a\u0004\b\u0005\u0010\t¨\u0006\u0015"}, m53d2 = {"javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType$annotations", "(Lkotlin/reflect/KType;)V", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "Lkotlin/reflect/KTypeProjection;", "(Lkotlin/reflect/KTypeProjection;)V", "(Lkotlin/reflect/KTypeProjection;)Ljava/lang/reflect/Type;", "createPossiblyInnerType", "jClass", Constants.CLASS, "arguments", "", "typeToString", "", "type", "computeJavaType", "forceWrapper", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/reflect/TypesJVMKt.class */
public final class TypesJVMKt {

    /* compiled from: TypesJVM.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:kotlin/reflect/TypesJVMKt$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KVariance.values().length];
            iArr[KVariance.IN.ordinal()] = 1;
            iArr[KVariance.INVARIANT.ordinal()] = 2;
            iArr[KVariance.OUT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @LowPriorityInOverloadResolution
    @SinceKotlin(version = "1.4")
    @ExperimentalStdlibApi
    public static /* synthetic */ void getJavaType$annotations(KType kType) {
    }

    @ExperimentalStdlibApi
    private static /* synthetic */ void getJavaType$annotations(KTypeProjection kTypeProjection) {
    }

    @NotNull
    public static final Type getJavaType(@NotNull KType $this$javaType) {
        Type it;
        Intrinsics.checkNotNullParameter($this$javaType, "<this>");
        if (($this$javaType instanceof KTypeBase) && (it = ((KTypeBase) $this$javaType).getJavaType()) != null) {
            return it;
        }
        return computeJavaType$default($this$javaType, false, 1, null);
    }

    static /* synthetic */ Type computeJavaType$default(KType kType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return computeJavaType(kType, z);
    }

    @ExperimentalStdlibApi
    public static final Type computeJavaType(KType $this$computeJavaType, boolean forceWrapper) {
        KClassifier classifier = $this$computeJavaType.getClassifier();
        if (classifier instanceof KTypeParameter) {
            return new TypeVariableImpl((KTypeParameter) classifier);
        }
        if (classifier instanceof KClass) {
            Class jClass = forceWrapper ? JvmClassMapping.getJavaObjectType((KClass) classifier) : JvmClassMapping.getJavaClass((KClass) classifier);
            List arguments = $this$computeJavaType.getArguments();
            if (arguments.isEmpty()) {
                return jClass;
            }
            if (jClass.isArray()) {
                if (jClass.getComponentType().isPrimitive()) {
                    return jClass;
                }
                KTypeProjection kTypeProjection = (KTypeProjection) CollectionsKt.singleOrNull((List<? extends Object>) arguments);
                if (kTypeProjection != null) {
                    KVariance variance = kTypeProjection.component1();
                    KType elementType = kTypeProjection.component2();
                    switch (variance == null ? -1 : WhenMappings.$EnumSwitchMapping$0[variance.ordinal()]) {
                        case -1:
                        case 1:
                            return jClass;
                        case 0:
                        default:
                            throw new NoWhenBranchMatchedException();
                        case 2:
                        case 3:
                            Intrinsics.checkNotNull(elementType);
                            Type javaElementType = computeJavaType$default(elementType, false, 1, null);
                            return javaElementType instanceof Class ? jClass : new TypesJVM(javaElementType);
                    }
                }
                throw new IllegalArgumentException(Intrinsics.stringPlus("kotlin.Array must have exactly one type argument: ", $this$computeJavaType));
            }
            return createPossiblyInnerType(jClass, arguments);
        }
        throw new UnsupportedOperationException(Intrinsics.stringPlus("Unsupported type classifier: ", $this$computeJavaType));
    }

    @ExperimentalStdlibApi
    private static final Type createPossiblyInnerType(Class<?> cls, List<KTypeProjection> list) {
        Class ownerClass = cls.getDeclaringClass();
        if (ownerClass != null) {
            if (Modifier.isStatic(cls.getModifiers())) {
                Class cls2 = ownerClass;
                List<KTypeProjection> $this$map$iv = list;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    KTypeProjection p0 = (KTypeProjection) item$iv$iv;
                    destination$iv$iv.add(getJavaType(p0));
                }
                return new ParameterizedTypeImpl(cls, cls2, (List) destination$iv$iv);
            }
            int n = cls.getTypeParameters().length;
            Type createPossiblyInnerType = createPossiblyInnerType(ownerClass, list.subList(n, list.size()));
            Iterable $this$map$iv2 = list.subList(0, n);
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv2 : $this$map$iv2) {
                KTypeProjection p02 = (KTypeProjection) item$iv$iv2;
                destination$iv$iv2.add(getJavaType(p02));
            }
            return new ParameterizedTypeImpl(cls, createPossiblyInnerType, (List) destination$iv$iv2);
        }
        List<KTypeProjection> $this$map$iv3 = list;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        for (Object item$iv$iv3 : $this$map$iv3) {
            KTypeProjection p03 = (KTypeProjection) item$iv$iv3;
            destination$iv$iv3.add(getJavaType(p03));
        }
        return new ParameterizedTypeImpl(cls, null, (List) destination$iv$iv3);
    }

    private static final Type getJavaType(KTypeProjection $this$javaType) {
        KVariance variance = $this$javaType.getVariance();
        if (variance == null) {
            return WildcardTypeImpl.Companion.getSTAR();
        }
        KType type = $this$javaType.getType();
        Intrinsics.checkNotNull(type);
        switch (WhenMappings.$EnumSwitchMapping$0[variance.ordinal()]) {
            case 1:
                return new WildcardTypeImpl(null, computeJavaType(type, true));
            case 2:
                return computeJavaType(type, true);
            case 3:
                return new WildcardTypeImpl(computeJavaType(type, true), null);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final String typeToString(Type type) {
        String str;
        if (type instanceof Class) {
            if (((Class) type).isArray()) {
                Sequence unwrap = SequencesKt.generateSequence(type, TypesJVMKt$typeToString$unwrap$1.INSTANCE);
                str = Intrinsics.stringPlus(((Class) SequencesKt.last(unwrap)).getName(), StringsKt.repeat("[]", SequencesKt.count(unwrap)));
            } else {
                str = ((Class) type).getName();
            }
            String str2 = str;
            Intrinsics.checkNotNullExpressionValue(str2, "{\n        if (type.isArr…   } else type.name\n    }");
            return str2;
        }
        return type.toString();
    }
}
