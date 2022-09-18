package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.types.Types;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.TypeInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0018\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010��\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005\u001a\u0016\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005\u001a\u0016\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005¨\u0006\b"}, m53d2 = {"checkcast", "", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "type", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "instanceof", "new", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.ObjectManagementKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/ObjectManagementKt.class */
public final class ObjectManagement {
    /* renamed from: new */
    public static final void m238new(@NotNull InstructionAssembly $this$new, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull($this$new, "$this$new");
        Intrinsics.checkParameterIsNotNull(type, "type");
        $this$new.getInstructions().add(new TypeInsnNode(187, Types.coerceType(type).getInternalName()));
    }

    public static final void checkcast(@NotNull InstructionAssembly checkcast, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(checkcast, "$this$checkcast");
        Intrinsics.checkParameterIsNotNull(type, "type");
        checkcast.getInstructions().add(new TypeInsnNode(192, Types.coerceType(type).getInternalName()));
    }

    /* renamed from: instanceof */
    public static final void m239instanceof(@NotNull InstructionAssembly $this$instanceof, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull($this$instanceof, "$this$instanceof");
        Intrinsics.checkParameterIsNotNull(type, "type");
        $this$instanceof.getInstructions().add(new TypeInsnNode(193, Types.coerceType(type).getInternalName()));
    }
}
