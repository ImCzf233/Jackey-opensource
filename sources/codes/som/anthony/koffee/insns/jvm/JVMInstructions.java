package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005*\f\b��\u0010\u0006\"\u00020\u00012\u00020\u0001¨\u0006\u0007"}, m53d2 = {"nop", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getNop", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "U", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.JVMInstructionsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/JVMInstructionsKt.class */
public final class JVMInstructions {
    @NotNull
    public static final Unit getNop(@NotNull InstructionAssembly nop) {
        Intrinsics.checkParameterIsNotNull(nop, "$this$nop");
        nop.getInstructions().add(new InsnNode(0));
        return Unit.INSTANCE;
    }
}
