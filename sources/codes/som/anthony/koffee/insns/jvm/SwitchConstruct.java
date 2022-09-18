package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.labels.Labels;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��&\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u001aO\u0010��\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u000522\u0010\u0006\u001a\u001a\u0012\u0016\b\u0001\u0012\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u00060\u0004j\u0002`\u00050\b0\u0007\"\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u00060\u0004j\u0002`\u00050\b¢\u0006\u0002\u0010\n\u001aG\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\u000e\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\u0007\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, m53d2 = {"lookupswitch", "", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "defaultLabel", "", "Lcodes/som/anthony/koffee/labels/LabelLike;", "branches", "", "Lkotlin/Pair;", "", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Ljava/lang/Object;[Lkotlin/Pair;)V", "tableswitch", "min", "max", "labels", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;IILjava/lang/Object;[Ljava/lang/Object;)V", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.SwitchConstructKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/SwitchConstructKt.class */
public final class SwitchConstruct {
    public static final void tableswitch(@NotNull InstructionAssembly tableswitch, int min, int max, @NotNull Object defaultLabel, @NotNull Object... labels) {
        Intrinsics.checkParameterIsNotNull(tableswitch, "$this$tableswitch");
        Intrinsics.checkParameterIsNotNull(defaultLabel, "defaultLabel");
        Intrinsics.checkParameterIsNotNull(labels, "labels");
        int size$iv = labels.length;
        Object[] result$iv = new LabelNode[size$iv];
        int length = result$iv.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int it = i$iv;
            result$iv[i$iv] = Labels.coerceLabel(labels[it]);
        }
        tableswitch.getInstructions().add(new TableSwitchInsnNode(min, max, Labels.coerceLabel(defaultLabel), (LabelNode[]) Arrays.copyOf(result$iv, result$iv.length)));
    }

    public static final void lookupswitch(@NotNull InstructionAssembly lookupswitch, @NotNull Object defaultLabel, @NotNull Tuples<Integer, ? extends Object>... branches) {
        Intrinsics.checkParameterIsNotNull(lookupswitch, "$this$lookupswitch");
        Intrinsics.checkParameterIsNotNull(defaultLabel, "defaultLabel");
        Intrinsics.checkParameterIsNotNull(branches, "branches");
        int size$iv = branches.length;
        int[] result$iv = new int[size$iv];
        int length = result$iv.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int it = i$iv;
            result$iv[i$iv] = branches[it].getFirst().intValue();
        }
        int size$iv2 = branches.length;
        LabelNode[] labelNodeArr = new LabelNode[size$iv2];
        int length2 = labelNodeArr.length;
        for (int i$iv2 = 0; i$iv2 < length2; i$iv2++) {
            int it2 = i$iv2;
            labelNodeArr[i$iv2] = Labels.coerceLabel(branches[it2].getSecond());
        }
        lookupswitch.getInstructions().add(new LookupSwitchInsnNode(Labels.coerceLabel(defaultLabel), result$iv, labelNodeArr));
    }
}
