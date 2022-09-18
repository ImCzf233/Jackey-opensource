package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005¨\u0006\u0016"}, m53d2 = {"dup", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getDup", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "dup2", "getDup2", "dup2_x1", "getDup2_x1", "dup2_x2", "getDup2_x2", "dup_x1", "getDup_x1", "dup_x2", "getDup_x2", "pop", "getPop", "pop2", "getPop2", "swap", "getSwap", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.StackManipulationKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/StackManipulationKt.class */
public final class StackManipulation {
    @NotNull
    public static final Unit getPop(@NotNull InstructionAssembly pop) {
        Intrinsics.checkParameterIsNotNull(pop, "$this$pop");
        pop.getInstructions().add(new InsnNode(87));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getPop2(@NotNull InstructionAssembly pop2) {
        Intrinsics.checkParameterIsNotNull(pop2, "$this$pop2");
        pop2.getInstructions().add(new InsnNode(88));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup(@NotNull InstructionAssembly dup) {
        Intrinsics.checkParameterIsNotNull(dup, "$this$dup");
        dup.getInstructions().add(new InsnNode(89));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup_x1(@NotNull InstructionAssembly dup_x1) {
        Intrinsics.checkParameterIsNotNull(dup_x1, "$this$dup_x1");
        dup_x1.getInstructions().add(new InsnNode(90));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup_x2(@NotNull InstructionAssembly dup_x2) {
        Intrinsics.checkParameterIsNotNull(dup_x2, "$this$dup_x2");
        dup_x2.getInstructions().add(new InsnNode(91));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup2(@NotNull InstructionAssembly dup2) {
        Intrinsics.checkParameterIsNotNull(dup2, "$this$dup2");
        dup2.getInstructions().add(new InsnNode(92));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup2_x1(@NotNull InstructionAssembly dup2_x1) {
        Intrinsics.checkParameterIsNotNull(dup2_x1, "$this$dup2_x1");
        dup2_x1.getInstructions().add(new InsnNode(93));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDup2_x2(@NotNull InstructionAssembly dup2_x2) {
        Intrinsics.checkParameterIsNotNull(dup2_x2, "$this$dup2_x2");
        dup2_x2.getInstructions().add(new InsnNode(94));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getSwap(@NotNull InstructionAssembly swap) {
        Intrinsics.checkParameterIsNotNull(swap, "$this$swap");
        swap.getInstructions().add(new InsnNode(95));
        return Unit.INSTANCE;
    }
}
