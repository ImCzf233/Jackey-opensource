package kotlin.reflect;

import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypesJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
/* loaded from: Jackey Client b2.jar:kotlin/reflect/ParameterizedTypeImpl$getTypeName$1$1.class */
/* synthetic */ class ParameterizedTypeImpl$getTypeName$1$1 extends FunctionReferenceImpl implements Function1<Type, String> {
    public static final ParameterizedTypeImpl$getTypeName$1$1 INSTANCE = new ParameterizedTypeImpl$getTypeName$1$1();

    ParameterizedTypeImpl$getTypeName$1$1() {
        super(1, TypesJVMKt.class, "typeToString", "typeToString(Ljava/lang/reflect/Type;)Ljava/lang/String;", 1);
    }

    @NotNull
    public final String invoke(@NotNull Type p0) {
        String typeToString;
        Intrinsics.checkNotNullParameter(p0, "p0");
        typeToString = TypesJVMKt.typeToString(p0);
        return typeToString;
    }
}
