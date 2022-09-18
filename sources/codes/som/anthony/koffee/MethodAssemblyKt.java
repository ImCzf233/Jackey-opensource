package codes.som.anthony.koffee;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.MethodNode;

/* compiled from: MethodAssembly.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0018\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u001a#\u0010��\u001a\u00020\u0001*\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¨\u0006��"}, m53d2 = {"koffee", "Lorg/objectweb/asm/tree/MethodNode;", "routine", "Lkotlin/Function1;", "Lcodes/som/anthony/koffee/MethodAssembly;", "", "Lkotlin/ExtensionFunctionType;"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/MethodAssemblyKt.class */
public final class MethodAssemblyKt {
    @NotNull
    public static final MethodNode koffee(@NotNull MethodNode koffee, @NotNull Function1<? super MethodAssembly, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(koffee, "$this$koffee");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        MethodAssembly assembly = new MethodAssembly(koffee);
        routine.invoke(assembly);
        return assembly.getNode();
    }
}
