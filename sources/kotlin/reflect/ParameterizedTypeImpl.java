package kotlin.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.util.Constants;

/* compiled from: TypesJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n��\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0010��\n\u0002\b\u0005\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018��2\u00020\u00012\u00020\u0002B)\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b¢\u0006\u0002\u0010\tJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\u0013\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016¢\u0006\u0002\u0010\u0011J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\u0013\u001a\u00020\u0006H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0015H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006\u0019"}, m53d2 = {"Lkotlin/reflect/ParameterizedTypeImpl;", "Ljava/lang/reflect/ParameterizedType;", "Lkotlin/reflect/TypeImpl;", "rawType", Constants.CLASS, "ownerType", "Ljava/lang/reflect/Type;", "typeArguments", "", "(Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/List;)V", "", "[Ljava/lang/reflect/Type;", "equals", "", "other", "", "getActualTypeArguments", "()[Ljava/lang/reflect/Type;", "getOwnerType", "getRawType", "getTypeName", "", "hashCode", "", "toString", "kotlin-stdlib"})
@ExperimentalStdlibApi
/* loaded from: Jackey Client b2.jar:kotlin/reflect/ParameterizedTypeImpl.class */
public final class ParameterizedTypeImpl implements ParameterizedType, TypeImpl {
    @NotNull
    private final Class<?> rawType;
    @Nullable
    private final Type ownerType;
    @NotNull
    private final Type[] typeArguments;

    public ParameterizedTypeImpl(@NotNull Class<?> rawType, @Nullable Type ownerType, @NotNull List<? extends Type> typeArguments) {
        Intrinsics.checkNotNullParameter(rawType, "rawType");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        this.rawType = rawType;
        this.ownerType = ownerType;
        List<? extends Type> $this$toTypedArray$iv = typeArguments;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        this.typeArguments = (Type[]) array;
    }

    @Override // java.lang.reflect.ParameterizedType
    @NotNull
    public Type getRawType() {
        return this.rawType;
    }

    @Override // java.lang.reflect.ParameterizedType
    @Nullable
    public Type getOwnerType() {
        return this.ownerType;
    }

    @Override // java.lang.reflect.ParameterizedType
    @NotNull
    public Type[] getActualTypeArguments() {
        return this.typeArguments;
    }

    @Override // java.lang.reflect.Type, kotlin.reflect.TypeImpl
    @NotNull
    public String getTypeName() {
        String typeToString;
        String typeToString2;
        StringBuilder $this$getTypeName_u24lambda_u2d0 = new StringBuilder();
        if (this.ownerType != null) {
            typeToString2 = TypesJVMKt.typeToString(this.ownerType);
            $this$getTypeName_u24lambda_u2d0.append(typeToString2);
            $this$getTypeName_u24lambda_u2d0.append(ArgsClassGenerator.GETTER_PREFIX);
            $this$getTypeName_u24lambda_u2d0.append(this.rawType.getSimpleName());
        } else {
            typeToString = TypesJVMKt.typeToString(this.rawType);
            $this$getTypeName_u24lambda_u2d0.append(typeToString);
        }
        if (!(this.typeArguments.length == 0)) {
            ArraysKt.joinTo$default(this.typeArguments, $this$getTypeName_u24lambda_u2d0, (CharSequence) null, "<", ">", 0, (CharSequence) null, ParameterizedTypeImpl$getTypeName$1$1.INSTANCE, 50, (Object) null);
        }
        String sb = $this$getTypeName_u24lambda_u2d0.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof ParameterizedType) && Intrinsics.areEqual(this.rawType, ((ParameterizedType) other).getRawType()) && Intrinsics.areEqual(this.ownerType, ((ParameterizedType) other).getOwnerType()) && Arrays.equals(getActualTypeArguments(), ((ParameterizedType) other).getActualTypeArguments());
    }

    public int hashCode() {
        int hashCode = this.rawType.hashCode();
        Type type = this.ownerType;
        return (hashCode ^ (type == null ? 0 : type.hashCode())) ^ Arrays.hashCode(getActualTypeArguments());
    }

    @NotNull
    public String toString() {
        return getTypeName();
    }
}
