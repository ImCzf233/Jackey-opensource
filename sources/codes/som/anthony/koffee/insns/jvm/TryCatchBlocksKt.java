package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.TryCatchContainer;
import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.labels.LabelScope;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

/* compiled from: TryCatchBlocks.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��&\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a@\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0010\b��\u0010\u0002*\u00020\u0003*\u00020\u0004*\u00020\u0005*\u0002H\u00022\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\b\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, m53d2 = {"guard", "Lcodes/som/anthony/koffee/insns/jvm/GuardAssembly;", "T", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "Lcodes/som/anthony/koffee/TryCatchContainer;", "Lcodes/som/anthony/koffee/labels/LabelScope;", "routine", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Lkotlin/jvm/functions/Function1;)Lcodes/som/anthony/koffee/insns/jvm/GuardAssembly;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/TryCatchBlocksKt.class */
public final class TryCatchBlocksKt {
    @NotNull
    public static final <T extends InstructionAssembly & TryCatchContainer & LabelScope> TryCatchBlocks<T> guard(@NotNull T guard, @NotNull Function1<? super T, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(guard, "$this$guard");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        AbstractInsnNode labelNode = new LabelNode();
        AbstractInsnNode labelNode2 = new LabelNode();
        AbstractInsnNode labelNode3 = new LabelNode();
        guard.getInstructions().add(labelNode);
        routine.invoke(guard);
        guard.getInstructions().add(new JumpInsnNode(167, labelNode3));
        guard.getInstructions().add(labelNode2);
        guard.getInstructions().add(labelNode3);
        return new TryCatchBlocks<>(guard, labelNode, labelNode2, labelNode3);
    }
}
