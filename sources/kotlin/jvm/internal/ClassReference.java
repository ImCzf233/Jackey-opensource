package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ExceptionsH;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Tuples;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMapping;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.functions.Functions;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.util.Constants;

/* compiled from: ClassReference.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"??????p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010??????\n\u0002\u0018\u0002\n??????\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n??????\n\u0002\u0010\b\n\u0002\b\u0005\u0018?????? O2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001OB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005??\u0006\u0002\u0010\u0006J\u0013\u0010F\u001a\u00020\u00122\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010H\u001a\u00020IH\u0002J\b\u0010J\u001a\u00020KH\u0016J\u0012\u0010L\u001a\u00020\u00122\b\u0010M\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010N\u001a\u000201H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004??\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0096\u0004??\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u001a\u0010\"\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b#\u0010\u0014\u001a\u0004\b\"\u0010\u0015R\u001a\u0010$\u001a\u00020\u00128VX\u0097\u0004??\u0006\f\u0012\u0004\b%\u0010\u0014\u001a\u0004\b$\u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004??\u0006\b\n??????\u001a\u0004\b&\u0010'R\u001e\u0010(\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030)0\r8VX\u0096\u0004??\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u001e\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0096\u0004??\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0016\u0010-\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004??\u0006\u0006\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0096\u0004??\u0006\u0006\u001a\u0004\b2\u00103R(\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0097\u0004??\u0006\f\u0012\u0004\b5\u0010\u0014\u001a\u0004\b6\u0010\u000bR\u0016\u00107\u001a\u0004\u0018\u0001018VX\u0096\u0004??\u0006\u0006\u001a\u0004\b8\u00103R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0097\u0004??\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR \u0010=\u001a\b\u0012\u0004\u0012\u00020>0\b8VX\u0097\u0004??\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010\u000bR\u001c\u0010A\u001a\u0004\u0018\u00010B8VX\u0097\u0004??\u0006\f\u0012\u0004\bC\u0010\u0014\u001a\u0004\bD\u0010E??\u0006P"}, m53d2 = {"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", Constants.CLASS, "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/ClassReference.class */
public final class ClassReference implements KClass<Object>, ClassBasedDeclarationContainer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Class<?> jClass;
    @NotNull
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    @NotNull
    private static final HashMap<String, String> primitiveFqNames;
    @NotNull
    private static final HashMap<String, String> primitiveWrapperFqNames;
    @NotNull
    private static final HashMap<String, String> classFqNames;
    @NotNull
    private static final Map<String, String> simpleNames;

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void getTypeParameters$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void getSupertypes$annotations() {
    }

    @SinceKotlin(version = "1.3")
    public static /* synthetic */ void getSealedSubclasses$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void getVisibility$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isFinal$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isOpen$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isAbstract$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isSealed$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isData$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isInner$annotations() {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void isCompanion$annotations() {
    }

    @SinceKotlin(version = "1.4")
    public static /* synthetic */ void isFun$annotations() {
    }

    @SinceKotlin(version = "1.5")
    public static /* synthetic */ void isValue$annotations() {
    }

    public ClassReference(@NotNull Class<?> jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        this.jClass = jClass;
    }

    @Override // kotlin.jvm.internal.ClassBasedDeclarationContainer
    @NotNull
    public Class<?> getJClass() {
        return this.jClass;
    }

    @Override // kotlin.reflect.KClass
    @Nullable
    public String getSimpleName() {
        return Companion.getClassSimpleName(getJClass());
    }

    @Override // kotlin.reflect.KClass
    @Nullable
    public String getQualifiedName() {
        return Companion.getClassQualifiedName(getJClass());
    }

    @Override // kotlin.reflect.KClass, kotlin.reflect.KDeclarationContainer
    @NotNull
    public Collection<KCallable<?>> getMembers() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @NotNull
    public Collection<KFunction<Object>> getConstructors() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @NotNull
    public Collection<KClass<?>> getNestedClasses() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KAnnotatedElement
    @NotNull
    public List<Annotation> getAnnotations() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @Nullable
    public Object getObjectInstance() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @SinceKotlin(version = "1.1")
    public boolean isInstance(@Nullable Object value) {
        return Companion.isInstance(value, getJClass());
    }

    @Override // kotlin.reflect.KClass
    @NotNull
    public List<KTypeParameter> getTypeParameters() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @NotNull
    public List<KType> getSupertypes() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @NotNull
    public List<KClass<? extends Object>> getSealedSubclasses() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    @Nullable
    public KVisibility getVisibility() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isFinal() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isOpen() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isAbstract() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isSealed() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isData() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isInner() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isCompanion() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isFun() {
        error();
        throw new ExceptionsH();
    }

    @Override // kotlin.reflect.KClass
    public boolean isValue() {
        error();
        throw new ExceptionsH();
    }

    private final Void error() {
        throw new KotlinReflectionNotSupportedError();
    }

    @Override // kotlin.reflect.KClass
    public boolean equals(@Nullable Object other) {
        return (other instanceof ClassReference) && Intrinsics.areEqual(JvmClassMapping.getJavaObjectType(this), JvmClassMapping.getJavaObjectType((KClass) other));
    }

    @Override // kotlin.reflect.KClass
    public int hashCode() {
        return JvmClassMapping.getJavaObjectType(this).hashCode();
    }

    @NotNull
    public String toString() {
        return Intrinsics.stringPlus(getJClass().toString(), " (Kotlin reflection is not available)");
    }

    /* compiled from: ClassReference.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"??????6\n\u0002\u0018\u0002\n\u0002\u0010??????\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n??????\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018??????2\u00020\u0001B\u0007\b\u0002??\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004??\u0006\u0002\n??????R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004??\u0006\u0002\n??????R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004??\u0006\u0002\n??????R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004??\u0006\u0002\n??????R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004??\u0006\u0002\n????????\u0006\u0015"}, m53d2 = {"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", Constants.CLASS, "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/ClassReference$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @Nullable
        public final String getClassSimpleName(@NotNull Class<?> jClass) {
            String str;
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            if (jClass.isAnonymousClass()) {
                return null;
            }
            if (!jClass.isLocalClass()) {
                if (!jClass.isArray()) {
                    String str2 = (String) ClassReference.simpleNames.get(jClass.getName());
                    return str2 == null ? jClass.getSimpleName() : str2;
                }
                Class componentType = jClass.getComponentType();
                if (componentType.isPrimitive()) {
                    String str3 = (String) ClassReference.simpleNames.get(componentType.getName());
                    str = str3 == null ? null : Intrinsics.stringPlus(str3, "Array");
                } else {
                    str = null;
                }
                String str4 = str;
                return str4 == null ? "Array" : str4;
            }
            String name = jClass.getSimpleName();
            Method method = jClass.getEnclosingMethod();
            if (method != null) {
                Intrinsics.checkNotNullExpressionValue(name, "name");
                return StringsKt.substringAfter$default(name, Intrinsics.stringPlus(method.getName(), ArgsClassGenerator.GETTER_PREFIX), (String) null, 2, (Object) null);
            }
            Constructor constructor = jClass.getEnclosingConstructor();
            if (constructor == null) {
                Intrinsics.checkNotNullExpressionValue(name, "name");
                return StringsKt.substringAfter$default(name, '$', (String) null, 2, (Object) null);
            }
            Intrinsics.checkNotNullExpressionValue(name, "name");
            return StringsKt.substringAfter$default(name, Intrinsics.stringPlus(constructor.getName(), ArgsClassGenerator.GETTER_PREFIX), (String) null, 2, (Object) null);
        }

        @Nullable
        public final String getClassQualifiedName(@NotNull Class<?> jClass) {
            String str;
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            if (!jClass.isAnonymousClass() && !jClass.isLocalClass()) {
                if (!jClass.isArray()) {
                    String str2 = (String) ClassReference.classFqNames.get(jClass.getName());
                    return str2 == null ? jClass.getCanonicalName() : str2;
                }
                Class componentType = jClass.getComponentType();
                if (componentType.isPrimitive()) {
                    String str3 = (String) ClassReference.classFqNames.get(componentType.getName());
                    str = str3 == null ? null : Intrinsics.stringPlus(str3, "Array");
                } else {
                    str = null;
                }
                String str4 = str;
                if (str4 != null) {
                    return str4;
                }
                return "kotlin.Array";
            }
            return null;
        }

        public final boolean isInstance(@Nullable Object value, @NotNull Class<?> jClass) {
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            Integer num = (Integer) ClassReference.FUNCTION_CLASSES.get(jClass);
            if (num != null) {
                int arity = num.intValue();
                return TypeIntrinsics.isFunctionOfArity(value, arity);
            }
            Class objectType = jClass.isPrimitive() ? JvmClassMapping.getJavaObjectType(JvmClassMapping.getKotlinClass(jClass)) : jClass;
            return objectType.isInstance(value);
        }
    }

    static {
        Iterable $this$mapIndexed$iv = CollectionsKt.listOf((Object[]) new Class[]{Functions.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int i = index$iv$iv;
            index$iv$iv = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Class clazz = (Class) item$iv$iv;
            destination$iv$iv.add(TuplesKt.m46to(clazz, Integer.valueOf(i)));
        }
        FUNCTION_CLASSES = MapsKt.toMap((List) destination$iv$iv);
        HashMap $this$primitiveFqNames_u24lambda_u2d1 = new HashMap();
        $this$primitiveFqNames_u24lambda_u2d1.put("boolean", "kotlin.Boolean");
        $this$primitiveFqNames_u24lambda_u2d1.put("char", "kotlin.Char");
        $this$primitiveFqNames_u24lambda_u2d1.put("byte", "kotlin.Byte");
        $this$primitiveFqNames_u24lambda_u2d1.put("short", "kotlin.Short");
        $this$primitiveFqNames_u24lambda_u2d1.put("int", "kotlin.Int");
        $this$primitiveFqNames_u24lambda_u2d1.put("float", "kotlin.Float");
        $this$primitiveFqNames_u24lambda_u2d1.put("long", "kotlin.Long");
        $this$primitiveFqNames_u24lambda_u2d1.put("double", "kotlin.Double");
        primitiveFqNames = $this$primitiveFqNames_u24lambda_u2d1;
        HashMap $this$primitiveWrapperFqNames_u24lambda_u2d2 = new HashMap();
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Boolean", "kotlin.Boolean");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Character", "kotlin.Char");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Byte", "kotlin.Byte");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Short", "kotlin.Short");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Integer", "kotlin.Int");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Float", "kotlin.Float");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Long", "kotlin.Long");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Double", "kotlin.Double");
        primitiveWrapperFqNames = $this$primitiveWrapperFqNames_u24lambda_u2d2;
        HashMap $this$classFqNames_u24lambda_u2d4 = new HashMap();
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Object", "kotlin.Any");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.String", "kotlin.String");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.CharSequence", "kotlin.CharSequence");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Throwable", "kotlin.Throwable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Cloneable", "kotlin.Cloneable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Number", "kotlin.Number");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Comparable", "kotlin.Comparable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Enum", "kotlin.Enum");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Iterable", "kotlin.collections.Iterable");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Iterator", "kotlin.collections.Iterator");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Collection", "kotlin.collections.Collection");
        $this$classFqNames_u24lambda_u2d4.put("java.util.List", "kotlin.collections.List");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Set", "kotlin.collections.Set");
        $this$classFqNames_u24lambda_u2d4.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Map", "kotlin.collections.Map");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        $this$classFqNames_u24lambda_u2d4.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        $this$classFqNames_u24lambda_u2d4.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        $this$classFqNames_u24lambda_u2d4.putAll(primitiveFqNames);
        $this$classFqNames_u24lambda_u2d4.putAll(primitiveWrapperFqNames);
        Iterable values = primitiveFqNames.values();
        Intrinsics.checkNotNullExpressionValue(values, "primitiveFqNames.values");
        Iterable $this$associateTo$iv = values;
        for (Object element$iv : $this$associateTo$iv) {
            HashMap hashMap = $this$classFqNames_u24lambda_u2d4;
            String kotlinName = (String) element$iv;
            StringBuilder append = new StringBuilder().append("kotlin.jvm.internal.");
            Intrinsics.checkNotNullExpressionValue(kotlinName, "kotlinName");
            Tuples m46to = TuplesKt.m46to(append.append(StringsKt.substringAfterLast$default(kotlinName, '.', (String) null, 2, (Object) null)).append("CompanionObject").toString(), Intrinsics.stringPlus(kotlinName, ".Companion"));
            hashMap.put(m46to.getFirst(), m46to.getSecond());
        }
        for (Map.Entry<Class<? extends Function<?>>, Integer> entry : FUNCTION_CLASSES.entrySet()) {
            Class klass = entry.getKey();
            int arity = entry.getValue().intValue();
            $this$classFqNames_u24lambda_u2d4.put(klass.getName(), Intrinsics.stringPlus("kotlin.Function", Integer.valueOf(arity)));
        }
        classFqNames = $this$classFqNames_u24lambda_u2d4;
        Map $this$mapValues$iv = classFqNames;
        Map destination$iv$iv2 = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
        Iterable $this$associateByTo$iv$iv$iv = $this$mapValues$iv.entrySet();
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            Map.Entry it$iv$iv = (Map.Entry) element$iv$iv$iv;
            Object key = it$iv$iv.getKey();
            Map.Entry $dstr$_u24__u24$fqName = (Map.Entry) element$iv$iv$iv;
            String fqName = (String) $dstr$_u24__u24$fqName.getValue();
            destination$iv$iv2.put(key, StringsKt.substringAfterLast$default(fqName, '.', (String) null, 2, (Object) null));
        }
        simpleNames = destination$iv$iv2;
    }
}
