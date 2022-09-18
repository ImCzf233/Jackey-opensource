package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"�� \n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n��\n\u0002\u0010��\n\u0002\b\u0002\u001a\u0012\u0010\"\u001a\u00020\u0001*\u00020\u00032\u0006\u0010#\u001a\u00020$\u001a\u0012\u0010%\u001a\u00020\u0001*\u00020\u00032\u0006\u0010#\u001a\u00020&\u001a\u0012\u0010'\u001a\u00020\u0001*\u00020\u00032\u0006\u0010#\u001a\u00020$\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005\"\u0019\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005\"\u0019\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005\"\u0019\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005\"\u0019\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005\"\u0019\u0010\u001e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005\"\u0019\u0010 \u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010\u0005¨\u0006("}, m53d2 = {"aconst_null", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getAconst_null", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "dconst_0", "getDconst_0", "dconst_1", "getDconst_1", "fconst_0", "getFconst_0", "fconst_1", "getFconst_1", "fconst_2", "getFconst_2", "iconst_0", "getIconst_0", "iconst_1", "getIconst_1", "iconst_2", "getIconst_2", "iconst_3", "getIconst_3", "iconst_4", "getIconst_4", "iconst_5", "getIconst_5", "iconst_m1", "getIconst_m1", "lconst_0", "getLconst_0", "lconst_1", "getLconst_1", "bipush", "v", "", "ldc", "", "sipush", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.ConstantsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/ConstantsKt.class */
public final class Constants {
    @NotNull
    public static final Unit getAconst_null(@NotNull InstructionAssembly aconst_null) {
        Intrinsics.checkParameterIsNotNull(aconst_null, "$this$aconst_null");
        aconst_null.getInstructions().add(new InsnNode(1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_m1(@NotNull InstructionAssembly iconst_m1) {
        Intrinsics.checkParameterIsNotNull(iconst_m1, "$this$iconst_m1");
        iconst_m1.getInstructions().add(new InsnNode(2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_0(@NotNull InstructionAssembly iconst_0) {
        Intrinsics.checkParameterIsNotNull(iconst_0, "$this$iconst_0");
        iconst_0.getInstructions().add(new InsnNode(3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_1(@NotNull InstructionAssembly iconst_1) {
        Intrinsics.checkParameterIsNotNull(iconst_1, "$this$iconst_1");
        iconst_1.getInstructions().add(new InsnNode(4));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_2(@NotNull InstructionAssembly iconst_2) {
        Intrinsics.checkParameterIsNotNull(iconst_2, "$this$iconst_2");
        iconst_2.getInstructions().add(new InsnNode(5));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_3(@NotNull InstructionAssembly iconst_3) {
        Intrinsics.checkParameterIsNotNull(iconst_3, "$this$iconst_3");
        iconst_3.getInstructions().add(new InsnNode(6));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_4(@NotNull InstructionAssembly iconst_4) {
        Intrinsics.checkParameterIsNotNull(iconst_4, "$this$iconst_4");
        iconst_4.getInstructions().add(new InsnNode(7));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIconst_5(@NotNull InstructionAssembly iconst_5) {
        Intrinsics.checkParameterIsNotNull(iconst_5, "$this$iconst_5");
        iconst_5.getInstructions().add(new InsnNode(8));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLconst_0(@NotNull InstructionAssembly lconst_0) {
        Intrinsics.checkParameterIsNotNull(lconst_0, "$this$lconst_0");
        lconst_0.getInstructions().add(new InsnNode(9));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLconst_1(@NotNull InstructionAssembly lconst_1) {
        Intrinsics.checkParameterIsNotNull(lconst_1, "$this$lconst_1");
        lconst_1.getInstructions().add(new InsnNode(10));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFconst_0(@NotNull InstructionAssembly fconst_0) {
        Intrinsics.checkParameterIsNotNull(fconst_0, "$this$fconst_0");
        fconst_0.getInstructions().add(new InsnNode(11));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFconst_1(@NotNull InstructionAssembly fconst_1) {
        Intrinsics.checkParameterIsNotNull(fconst_1, "$this$fconst_1");
        fconst_1.getInstructions().add(new InsnNode(12));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFconst_2(@NotNull InstructionAssembly fconst_2) {
        Intrinsics.checkParameterIsNotNull(fconst_2, "$this$fconst_2");
        fconst_2.getInstructions().add(new InsnNode(13));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDconst_0(@NotNull InstructionAssembly dconst_0) {
        Intrinsics.checkParameterIsNotNull(dconst_0, "$this$dconst_0");
        dconst_0.getInstructions().add(new InsnNode(14));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDconst_1(@NotNull InstructionAssembly dconst_1) {
        Intrinsics.checkParameterIsNotNull(dconst_1, "$this$dconst_1");
        dconst_1.getInstructions().add(new InsnNode(15));
        return Unit.INSTANCE;
    }

    public static final void bipush(@NotNull InstructionAssembly bipush, int v) {
        Intrinsics.checkParameterIsNotNull(bipush, "$this$bipush");
        bipush.getInstructions().add(new IntInsnNode(16, v));
    }

    public static final void sipush(@NotNull InstructionAssembly sipush, int v) {
        Intrinsics.checkParameterIsNotNull(sipush, "$this$sipush");
        sipush.getInstructions().add(new IntInsnNode(17, v));
    }

    public static final void ldc(@NotNull InstructionAssembly ldc, @NotNull Object v) {
        Intrinsics.checkParameterIsNotNull(ldc, "$this$ldc");
        Intrinsics.checkParameterIsNotNull(v, "v");
        ldc.getInstructions().add(new LdcInsnNode(v));
    }
}
