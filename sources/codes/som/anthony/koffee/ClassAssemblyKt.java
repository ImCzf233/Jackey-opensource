package codes.som.anthony.koffee;

import codes.som.anthony.koffee.modifiers.Modifiers;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

/* compiled from: ClassAssembly.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��<\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\u001aW\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\u0012\b\u0002\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\n2\u0017\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\u0002\b\u0011\u001a#\u0010\u0012\u001a\u00020\u0001*\u00020\u00012\u0017\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\u0002\b\u0011¨\u0006\u0012"}, m53d2 = {"assembleClass", "Lorg/objectweb/asm/tree/ClassNode;", "access", "Lcodes/som/anthony/koffee/modifiers/Modifiers;", "name", "", "version", "", "superName", "interfaces", "", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "routine", "Lkotlin/Function1;", "Lcodes/som/anthony/koffee/ClassAssembly;", "", "Lkotlin/ExtensionFunctionType;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/ClassAssemblyKt.class */
public final class ClassAssemblyKt {
    @NotNull
    public static /* synthetic */ ClassNode assembleClass$default(Modifiers modifiers, String str, int i, String str2, List list, Function1 function1, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 49;
        }
        if ((i2 & 8) != 0) {
            str2 = "java/lang/Object";
        }
        if ((i2 & 16) != 0) {
            list = CollectionsKt.emptyList();
        }
        return assembleClass(modifiers, str, i, str2, list, function1);
    }

    @NotNull
    public static final ClassNode assembleClass(@NotNull Modifiers access, @NotNull String name, int version, @NotNull String superName, @NotNull List<? extends Object> interfaces, @NotNull Function1<? super ClassAssembly, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(access, "access");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(superName, "superName");
        Intrinsics.checkParameterIsNotNull(interfaces, "interfaces");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        ClassAssembly assembly = new ClassAssembly(access, name, version, superName, interfaces);
        routine.invoke(assembly);
        return assembly.getNode();
    }

    @NotNull
    public static final ClassNode koffee(@NotNull ClassNode koffee, @NotNull Function1<? super ClassAssembly, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(koffee, "$this$koffee");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        ClassAssembly assembly = new ClassAssembly(koffee);
        routine.invoke(assembly);
        return assembly.getNode();
    }
}
