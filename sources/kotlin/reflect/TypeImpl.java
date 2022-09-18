package kotlin.reflect;

import java.lang.reflect.Type;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypesJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\bc\u0018��2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, m53d2 = {"Lkotlin/reflect/TypeImpl;", "Ljava/lang/reflect/Type;", "getTypeName", "", "kotlin-stdlib"})
@ExperimentalStdlibApi
/* loaded from: Jackey Client b2.jar:kotlin/reflect/TypeImpl.class */
interface TypeImpl extends Type {
    @Override // kotlin.reflect.TypeImpl
    @NotNull
    String getTypeName();
}
