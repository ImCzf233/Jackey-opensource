package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

/* compiled from: ClassBasedDeclarationContainer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018��2\u00020\u0001R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, m53d2 = {"Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "Lkotlin/reflect/KDeclarationContainer;", "jClass", Constants.CLASS, "getJClass", "()Ljava/lang/Class;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/ClassBasedDeclarationContainer.class */
public interface ClassBasedDeclarationContainer extends KDeclarationContainer {
    @NotNull
    Class<?> getJClass();
}
