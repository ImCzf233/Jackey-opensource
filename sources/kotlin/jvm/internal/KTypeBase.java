package kotlin.jvm.internal;

import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.reflect.KType;
import org.jetbrains.annotations.Nullable;

/* compiled from: KTypeBase.kt */
@SinceKotlin(version = "1.4")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018��2\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, m53d2 = {"Lkotlin/jvm/internal/KTypeBase;", "Lkotlin/reflect/KType;", "javaType", "Ljava/lang/reflect/Type;", "getJavaType", "()Ljava/lang/reflect/Type;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/KTypeBase.class */
public interface KTypeBase extends KType {
    @Nullable
    Type getJavaType();
}
