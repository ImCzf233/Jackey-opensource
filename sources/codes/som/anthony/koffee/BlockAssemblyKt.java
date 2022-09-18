package codes.som.anthony.koffee;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.TryCatchBlockNode;

/* compiled from: BlockAssembly.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��,\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\u001a1\u0010��\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00012\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\t\u001aE\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001*\u00020\u00022\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\t¨\u0006\n"}, m53d2 = {"assembleBlock", "Lkotlin/Pair;", "Lorg/objectweb/asm/tree/InsnList;", "", "Lorg/objectweb/asm/tree/TryCatchBlockNode;", "routine", "Lkotlin/Function1;", "Lcodes/som/anthony/koffee/BlockAssembly;", "", "Lkotlin/ExtensionFunctionType;", "koffee", "tryCatchBlocks", ""})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/BlockAssemblyKt.class */
public final class BlockAssemblyKt {
    @NotNull
    public static final Tuples<InsnList, List<TryCatchBlockNode>> assembleBlock(@NotNull Function1<? super BlockAssembly, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        BlockAssembly blockAssembly = new BlockAssembly(new InsnList(), new ArrayList());
        routine.invoke(blockAssembly);
        return new Tuples<>(blockAssembly.getInstructions(), blockAssembly.getTryCatchBlocks());
    }

    @NotNull
    public static /* synthetic */ Tuples koffee$default(InsnList insnList, List list, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            list = new ArrayList();
        }
        return koffee(insnList, list, function1);
    }

    @NotNull
    public static final Tuples<InsnList, List<TryCatchBlockNode>> koffee(@NotNull InsnList koffee, @NotNull List<TryCatchBlockNode> tryCatchBlocks, @NotNull Function1<? super BlockAssembly, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(koffee, "$this$koffee");
        Intrinsics.checkParameterIsNotNull(tryCatchBlocks, "tryCatchBlocks");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        BlockAssembly blockAssembly = new BlockAssembly(koffee, tryCatchBlocks);
        routine.invoke(blockAssembly);
        return new Tuples<>(blockAssembly.getInstructions(), blockAssembly.getTryCatchBlocks());
    }
}
