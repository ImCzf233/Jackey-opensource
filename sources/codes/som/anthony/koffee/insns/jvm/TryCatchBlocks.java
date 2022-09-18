package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.TryCatchContainer;
import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.labels.LabelRegistry;
import codes.som.anthony.koffee.labels.LabelScope;
import codes.som.anthony.koffee.types.Types;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��B\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��*\u0010\b��\u0010\u0001*\u00020\u0002*\u00020\u0003*\u00020\u00042\u00020\u0005:\u0001\u001bB%\u0012\u0006\u0010\u0006\u001a\u00028��\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b¢\u0006\u0002\u0010\u000bJA\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028��0��2\n\u0010\u0012\u001a\u00060\u0005j\u0002`\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u001d\u0010\u0016\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00028��0\u0018\u0012\u0004\u0012\u00020\u00190\u0017¢\u0006\u0002\b\u001aR\u0010\u0010\u0006\u001a\u00028��X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\n\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u001c"}, m53d2 = {"Lcodes/som/anthony/koffee/insns/jvm/GuardAssembly;", "T", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "Lcodes/som/anthony/koffee/TryCatchContainer;", "Lcodes/som/anthony/koffee/labels/LabelScope;", "", "assembly", "startNode", "Lorg/objectweb/asm/tree/LabelNode;", "endNode", "exitNode", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Lorg/objectweb/asm/tree/LabelNode;Lorg/objectweb/asm/tree/LabelNode;Lorg/objectweb/asm/tree/LabelNode;)V", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getEndNode", "()Lorg/objectweb/asm/tree/LabelNode;", "getExitNode", "getStartNode", "handle", "exceptionType", "Lcodes/som/anthony/koffee/types/TypeLike;", "fallthrough", "", "routine", "Lkotlin/Function1;", "Lcodes/som/anthony/koffee/insns/jvm/GuardAssembly$GuardHandlerAssemblyContext;", "", "Lkotlin/ExtensionFunctionType;", "GuardHandlerAssemblyContext", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.GuardAssembly */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/GuardAssembly.class */
public final class TryCatchBlocks<T extends InstructionAssembly & TryCatchContainer & LabelScope> {
    private final T assembly;
    @NotNull
    private final LabelNode startNode;
    @NotNull
    private final LabelNode endNode;
    @NotNull
    private final LabelNode exitNode;

    public TryCatchBlocks(@NotNull T assembly, @NotNull LabelNode startNode, @NotNull LabelNode endNode, @NotNull LabelNode exitNode) {
        Intrinsics.checkParameterIsNotNull(assembly, "assembly");
        Intrinsics.checkParameterIsNotNull(startNode, "startNode");
        Intrinsics.checkParameterIsNotNull(endNode, "endNode");
        Intrinsics.checkParameterIsNotNull(exitNode, "exitNode");
        this.assembly = assembly;
        this.startNode = startNode;
        this.endNode = endNode;
        this.exitNode = exitNode;
    }

    @NotNull
    public final LabelNode getStartNode() {
        return this.startNode;
    }

    @NotNull
    public final LabelNode getEndNode() {
        return this.endNode;
    }

    @NotNull
    public final LabelNode getExitNode() {
        return this.exitNode;
    }

    /* compiled from: TryCatchBlocks.kt */
    @Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��0\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��*\u0010\b\u0001\u0010\u0001*\u00020\u0002*\u00020\u0003*\u00020\u00042\u00020\u00022\u00020\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0007\u001a\u00028\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, m53d2 = {"Lcodes/som/anthony/koffee/insns/jvm/GuardAssembly$GuardHandlerAssemblyContext;", "T", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "Lcodes/som/anthony/koffee/TryCatchContainer;", "Lcodes/som/anthony/koffee/labels/LabelScope;", "instructions", "Lorg/objectweb/asm/tree/InsnList;", "assembly", "(Lorg/objectweb/asm/tree/InsnList;Lcodes/som/anthony/koffee/insns/InstructionAssembly;)V", "L", "Lcodes/som/anthony/koffee/labels/LabelRegistry;", "getL", "()Lcodes/som/anthony/koffee/labels/LabelRegistry;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getInstructions", "()Lorg/objectweb/asm/tree/InsnList;", "tryCatchBlocks", "", "Lorg/objectweb/asm/tree/TryCatchBlockNode;", "getTryCatchBlocks", "()Ljava/util/List;", "koffee"})
    /* renamed from: codes.som.anthony.koffee.insns.jvm.GuardAssembly$GuardHandlerAssemblyContext */
    /* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/GuardAssembly$GuardHandlerAssemblyContext.class */
    public static final class GuardHandlerAssemblyContext<T extends InstructionAssembly & TryCatchContainer & LabelScope> implements InstructionAssembly, TryCatchContainer, LabelScope {
        @NotNull
        private final List<TryCatchBlockNode> tryCatchBlocks;
        @NotNull

        /* renamed from: L */
        private final LabelRegistry f2L;
        @NotNull
        private final InsnList instructions;
        private final T assembly;

        @Override // codes.som.anthony.koffee.insns.InstructionAssembly
        @NotNull
        public InsnList getInstructions() {
            return this.instructions;
        }

        public GuardHandlerAssemblyContext(@NotNull InsnList instructions, @NotNull T assembly) {
            Intrinsics.checkParameterIsNotNull(instructions, "instructions");
            Intrinsics.checkParameterIsNotNull(assembly, "assembly");
            this.instructions = instructions;
            this.assembly = assembly;
            this.tryCatchBlocks = this.assembly.getTryCatchBlocks();
            this.f2L = this.assembly.getL().copy(this.assembly);
        }

        @Override // codes.som.anthony.koffee.TryCatchContainer
        @NotNull
        public List<TryCatchBlockNode> getTryCatchBlocks() {
            return this.tryCatchBlocks;
        }

        @Override // codes.som.anthony.koffee.labels.LabelScope
        @NotNull
        public LabelRegistry getL() {
            return this.f2L;
        }
    }

    @NotNull
    public static /* synthetic */ TryCatchBlocks handle$default(TryCatchBlocks tryCatchBlocks, Object obj, boolean z, Function1 function1, int i, Object obj2) {
        if ((i & 2) != 0) {
            z = false;
        }
        return tryCatchBlocks.handle(obj, z, function1);
    }

    @NotNull
    public final TryCatchBlocks<T> handle(@NotNull Object exceptionType, boolean fallthrough, @NotNull Function1<? super GuardHandlerAssemblyContext<T>, Unit> routine) {
        Intrinsics.checkParameterIsNotNull(exceptionType, "exceptionType");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        InsnList instructions = new InsnList();
        AbstractInsnNode labelNode = new LabelNode();
        instructions.add(labelNode);
        GuardHandlerAssemblyContext embeddedASM = new GuardHandlerAssemblyContext(instructions, this.assembly);
        routine.invoke(embeddedASM);
        if (!fallthrough) {
            instructions.add(new JumpInsnNode(167, this.exitNode));
        }
        this.assembly.getInstructions().insertBefore(this.exitNode, instructions);
        this.assembly.getTryCatchBlocks().add(new TryCatchBlockNode(this.startNode, this.endNode, labelNode, Types.coerceType(exceptionType).getInternalName()));
        return this;
    }
}
