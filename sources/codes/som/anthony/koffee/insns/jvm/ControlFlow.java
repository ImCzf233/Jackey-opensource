package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.labels.Labels;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��$\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n��\u001a\u0016\u0010\u001e\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010\"\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010#\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010$\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010%\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010&\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010'\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010(\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010)\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010*\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010+\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010,\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010-\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010.\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u0010/\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u00100\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u00101\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0016\u00102\u001a\u00020\u0001*\u00020\u00032\n\u0010\u001f\u001a\u00060 j\u0002`!\u001a\u0012\u00103\u001a\u00020\u0001*\u00020\u00032\u0006\u00104\u001a\u000205\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005\"\u0019\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005\"\u0019\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005\"\u0019\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005\"\u0019\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005¨\u00066"}, m53d2 = {"_return", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "get_return", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "areturn", "getAreturn", "athrow", "getAthrow", "dcmpg", "getDcmpg", "dcmpl", "getDcmpl", "dreturn", "getDreturn", "fcmpg", "getFcmpg", "fcmpl", "getFcmpl", "freturn", "getFreturn", "ireturn", "getIreturn", "lcmp", "getLcmp", "lreturn", "getLreturn", "return", "getReturn", "goto", "label", "", "Lcodes/som/anthony/koffee/labels/LabelLike;", "if_acmpeq", "if_acmpne", "if_icmpeq", "if_icmpge", "if_icmpgt", "if_icmple", "if_icmplt", "if_icmpne", "ifeq", "ifge", "ifgt", "ifle", "iflt", "ifne", "ifnonnull", "ifnull", "jsr", "ret", "slot", "", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.ControlFlowKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/ControlFlowKt.class */
public final class ControlFlow {
    @NotNull
    public static final Unit getIreturn(@NotNull InstructionAssembly ireturn) {
        Intrinsics.checkParameterIsNotNull(ireturn, "$this$ireturn");
        ireturn.getInstructions().add(new InsnNode(172));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLreturn(@NotNull InstructionAssembly lreturn) {
        Intrinsics.checkParameterIsNotNull(lreturn, "$this$lreturn");
        lreturn.getInstructions().add(new InsnNode(173));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFreturn(@NotNull InstructionAssembly freturn) {
        Intrinsics.checkParameterIsNotNull(freturn, "$this$freturn");
        freturn.getInstructions().add(new InsnNode(174));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDreturn(@NotNull InstructionAssembly dreturn) {
        Intrinsics.checkParameterIsNotNull(dreturn, "$this$dreturn");
        dreturn.getInstructions().add(new InsnNode(175));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAreturn(@NotNull InstructionAssembly areturn) {
        Intrinsics.checkParameterIsNotNull(areturn, "$this$areturn");
        areturn.getInstructions().add(new InsnNode(176));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getReturn(@NotNull InstructionAssembly $this$return) {
        Intrinsics.checkParameterIsNotNull($this$return, "$this$return");
        $this$return.getInstructions().add(new InsnNode(177));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit get_return(@NotNull InstructionAssembly _return) {
        Intrinsics.checkParameterIsNotNull(_return, "$this$_return");
        return getReturn(_return);
    }

    @NotNull
    public static final Unit getLcmp(@NotNull InstructionAssembly lcmp) {
        Intrinsics.checkParameterIsNotNull(lcmp, "$this$lcmp");
        lcmp.getInstructions().add(new InsnNode(148));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFcmpl(@NotNull InstructionAssembly fcmpl) {
        Intrinsics.checkParameterIsNotNull(fcmpl, "$this$fcmpl");
        fcmpl.getInstructions().add(new InsnNode(149));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFcmpg(@NotNull InstructionAssembly fcmpg) {
        Intrinsics.checkParameterIsNotNull(fcmpg, "$this$fcmpg");
        fcmpg.getInstructions().add(new InsnNode(150));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDcmpl(@NotNull InstructionAssembly dcmpl) {
        Intrinsics.checkParameterIsNotNull(dcmpl, "$this$dcmpl");
        dcmpl.getInstructions().add(new InsnNode(151));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDcmpg(@NotNull InstructionAssembly dcmpg) {
        Intrinsics.checkParameterIsNotNull(dcmpg, "$this$dcmpg");
        dcmpg.getInstructions().add(new InsnNode(152));
        return Unit.INSTANCE;
    }

    public static final void ifeq(@NotNull InstructionAssembly ifeq, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifeq, "$this$ifeq");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifeq.getInstructions().add(new JumpInsnNode(153, Labels.coerceLabel(label)));
    }

    public static final void ifne(@NotNull InstructionAssembly ifne, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifne, "$this$ifne");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifne.getInstructions().add(new JumpInsnNode(154, Labels.coerceLabel(label)));
    }

    public static final void iflt(@NotNull InstructionAssembly iflt, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(iflt, "$this$iflt");
        Intrinsics.checkParameterIsNotNull(label, "label");
        iflt.getInstructions().add(new JumpInsnNode(155, Labels.coerceLabel(label)));
    }

    public static final void ifge(@NotNull InstructionAssembly ifge, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifge, "$this$ifge");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifge.getInstructions().add(new JumpInsnNode(156, Labels.coerceLabel(label)));
    }

    public static final void ifgt(@NotNull InstructionAssembly ifgt, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifgt, "$this$ifgt");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifgt.getInstructions().add(new JumpInsnNode(157, Labels.coerceLabel(label)));
    }

    public static final void ifle(@NotNull InstructionAssembly ifle, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifle, "$this$ifle");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifle.getInstructions().add(new JumpInsnNode(158, Labels.coerceLabel(label)));
    }

    public static final void if_icmpeq(@NotNull InstructionAssembly if_icmpeq, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmpeq, "$this$if_icmpeq");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmpeq.getInstructions().add(new JumpInsnNode(159, Labels.coerceLabel(label)));
    }

    public static final void if_icmpne(@NotNull InstructionAssembly if_icmpne, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmpne, "$this$if_icmpne");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmpne.getInstructions().add(new JumpInsnNode(160, Labels.coerceLabel(label)));
    }

    public static final void if_icmplt(@NotNull InstructionAssembly if_icmplt, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmplt, "$this$if_icmplt");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmplt.getInstructions().add(new JumpInsnNode(161, Labels.coerceLabel(label)));
    }

    public static final void if_icmpge(@NotNull InstructionAssembly if_icmpge, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmpge, "$this$if_icmpge");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmpge.getInstructions().add(new JumpInsnNode(162, Labels.coerceLabel(label)));
    }

    public static final void if_icmpgt(@NotNull InstructionAssembly if_icmpgt, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmpgt, "$this$if_icmpgt");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmpgt.getInstructions().add(new JumpInsnNode(163, Labels.coerceLabel(label)));
    }

    public static final void if_icmple(@NotNull InstructionAssembly if_icmple, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_icmple, "$this$if_icmple");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_icmple.getInstructions().add(new JumpInsnNode(164, Labels.coerceLabel(label)));
    }

    public static final void if_acmpeq(@NotNull InstructionAssembly if_acmpeq, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_acmpeq, "$this$if_acmpeq");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_acmpeq.getInstructions().add(new JumpInsnNode(165, Labels.coerceLabel(label)));
    }

    public static final void if_acmpne(@NotNull InstructionAssembly if_acmpne, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(if_acmpne, "$this$if_acmpne");
        Intrinsics.checkParameterIsNotNull(label, "label");
        if_acmpne.getInstructions().add(new JumpInsnNode(166, Labels.coerceLabel(label)));
    }

    /* renamed from: goto */
    public static final void m237goto(@NotNull InstructionAssembly $this$goto, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull($this$goto, "$this$goto");
        Intrinsics.checkParameterIsNotNull(label, "label");
        $this$goto.getInstructions().add(new JumpInsnNode(167, Labels.coerceLabel(label)));
    }

    public static final void ifnull(@NotNull InstructionAssembly ifnull, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifnull, "$this$ifnull");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifnull.getInstructions().add(new JumpInsnNode(198, Labels.coerceLabel(label)));
    }

    public static final void ifnonnull(@NotNull InstructionAssembly ifnonnull, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(ifnonnull, "$this$ifnonnull");
        Intrinsics.checkParameterIsNotNull(label, "label");
        ifnonnull.getInstructions().add(new JumpInsnNode(199, Labels.coerceLabel(label)));
    }

    public static final void jsr(@NotNull InstructionAssembly jsr, @NotNull Object label) {
        Intrinsics.checkParameterIsNotNull(jsr, "$this$jsr");
        Intrinsics.checkParameterIsNotNull(label, "label");
        jsr.getInstructions().add(new JumpInsnNode(168, Labels.coerceLabel(label)));
    }

    public static final void ret(@NotNull InstructionAssembly ret, int slot) {
        Intrinsics.checkParameterIsNotNull(ret, "$this$ret");
        ret.getInstructions().add(new VarInsnNode(169, slot));
    }

    @NotNull
    public static final Unit getAthrow(@NotNull InstructionAssembly athrow) {
        Intrinsics.checkParameterIsNotNull(athrow, "$this$athrow");
        athrow.getInstructions().add(new InsnNode(191));
        return Unit.INSTANCE;
    }
}
