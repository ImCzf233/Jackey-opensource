package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\b"}, m53d2 = {"monitorenter", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getMonitorenter", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "monitorexit", "getMonitorexit", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.SynchronizationKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/SynchronizationKt.class */
public final class Synchronization {
    @NotNull
    public static final Unit getMonitorenter(@NotNull InstructionAssembly monitorenter) {
        Intrinsics.checkParameterIsNotNull(monitorenter, "$this$monitorenter");
        monitorenter.getInstructions().add(new InsnNode(194));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getMonitorexit(@NotNull InstructionAssembly monitorexit) {
        Intrinsics.checkParameterIsNotNull(monitorexit, "$this$monitorexit");
        monitorexit.getInstructions().add(new InsnNode(195));
        return Unit.INSTANCE;
    }
}
