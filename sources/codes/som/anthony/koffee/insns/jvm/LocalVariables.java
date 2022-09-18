package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.VarInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u001a\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bR\n\u0002\u0010\b\n\u0002\b\n\u001a\u0012\u0010T\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010W\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010X\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010Y\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010Z\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010[\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010\\\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010]\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010^\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\u001a\u0012\u0010_\u001a\u00020\u0001*\u00020\u00032\u0006\u0010U\u001a\u00020V\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005\"\u0019\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005\"\u0019\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005\"\u0019\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005\"\u0019\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005\"\u0019\u0010\u001e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005\"\u0019\u0010 \u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010\u0005\"\u0019\u0010\"\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b#\u0010\u0005\"\u0019\u0010$\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b%\u0010\u0005\"\u0019\u0010&\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b'\u0010\u0005\"\u0019\u0010(\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b)\u0010\u0005\"\u0019\u0010*\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b+\u0010\u0005\"\u0019\u0010,\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b-\u0010\u0005\"\u0019\u0010.\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b/\u0010\u0005\"\u0019\u00100\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b1\u0010\u0005\"\u0019\u00102\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b3\u0010\u0005\"\u0019\u00104\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b5\u0010\u0005\"\u0019\u00106\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b7\u0010\u0005\"\u0019\u00108\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b9\u0010\u0005\"\u0019\u0010:\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b;\u0010\u0005\"\u0019\u0010<\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b=\u0010\u0005\"\u0019\u0010>\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b?\u0010\u0005\"\u0019\u0010@\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bA\u0010\u0005\"\u0019\u0010B\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bC\u0010\u0005\"\u0019\u0010D\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bE\u0010\u0005\"\u0019\u0010F\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bG\u0010\u0005\"\u0019\u0010H\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bI\u0010\u0005\"\u0019\u0010J\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bK\u0010\u0005\"\u0019\u0010L\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bM\u0010\u0005\"\u0019\u0010N\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bO\u0010\u0005\"\u0019\u0010P\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bQ\u0010\u0005\"\u0019\u0010R\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bS\u0010\u0005¨\u0006`"}, m53d2 = {"aload_0", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getAload_0", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "aload_1", "getAload_1", "aload_2", "getAload_2", "aload_3", "getAload_3", "astore_0", "getAstore_0", "astore_1", "getAstore_1", "astore_2", "getAstore_2", "astore_3", "getAstore_3", "dload_0", "getDload_0", "dload_1", "getDload_1", "dload_2", "getDload_2", "dload_3", "getDload_3", "dstore_0", "getDstore_0", "dstore_1", "getDstore_1", "dstore_2", "getDstore_2", "dstore_3", "getDstore_3", "fload_0", "getFload_0", "fload_1", "getFload_1", "fload_2", "getFload_2", "fload_3", "getFload_3", "fstore_0", "getFstore_0", "fstore_1", "getFstore_1", "fstore_2", "getFstore_2", "fstore_3", "getFstore_3", "iload_0", "getIload_0", "iload_1", "getIload_1", "iload_2", "getIload_2", "iload_3", "getIload_3", "istore_0", "getIstore_0", "istore_1", "getIstore_1", "istore_2", "getIstore_2", "istore_3", "getIstore_3", "lload_0", "getLload_0", "lload_1", "getLload_1", "lload_2", "getLload_2", "lload_3", "getLload_3", "lstore_0", "getLstore_0", "lstore_1", "getLstore_1", "lstore_2", "getLstore_2", "lstore_3", "getLstore_3", "aload", "slot", "", "astore", "dload", "dstore", "fload", "fstore", "iload", "istore", "lload", "lstore", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.LocalVariablesKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/LocalVariablesKt.class */
public final class LocalVariables {
    public static final void iload(@NotNull InstructionAssembly iload, int slot) {
        Intrinsics.checkParameterIsNotNull(iload, "$this$iload");
        iload.getInstructions().add(new VarInsnNode(21, slot));
    }

    public static final void lload(@NotNull InstructionAssembly lload, int slot) {
        Intrinsics.checkParameterIsNotNull(lload, "$this$lload");
        lload.getInstructions().add(new VarInsnNode(22, slot));
    }

    public static final void fload(@NotNull InstructionAssembly fload, int slot) {
        Intrinsics.checkParameterIsNotNull(fload, "$this$fload");
        fload.getInstructions().add(new VarInsnNode(23, slot));
    }

    public static final void dload(@NotNull InstructionAssembly dload, int slot) {
        Intrinsics.checkParameterIsNotNull(dload, "$this$dload");
        dload.getInstructions().add(new VarInsnNode(24, slot));
    }

    public static final void aload(@NotNull InstructionAssembly aload, int slot) {
        Intrinsics.checkParameterIsNotNull(aload, "$this$aload");
        aload.getInstructions().add(new VarInsnNode(25, slot));
    }

    public static final void istore(@NotNull InstructionAssembly istore, int slot) {
        Intrinsics.checkParameterIsNotNull(istore, "$this$istore");
        istore.getInstructions().add(new VarInsnNode(54, slot));
    }

    public static final void lstore(@NotNull InstructionAssembly lstore, int slot) {
        Intrinsics.checkParameterIsNotNull(lstore, "$this$lstore");
        lstore.getInstructions().add(new VarInsnNode(55, slot));
    }

    public static final void fstore(@NotNull InstructionAssembly fstore, int slot) {
        Intrinsics.checkParameterIsNotNull(fstore, "$this$fstore");
        fstore.getInstructions().add(new VarInsnNode(56, slot));
    }

    public static final void dstore(@NotNull InstructionAssembly dstore, int slot) {
        Intrinsics.checkParameterIsNotNull(dstore, "$this$dstore");
        dstore.getInstructions().add(new VarInsnNode(57, slot));
    }

    public static final void astore(@NotNull InstructionAssembly astore, int slot) {
        Intrinsics.checkParameterIsNotNull(astore, "$this$astore");
        astore.getInstructions().add(new VarInsnNode(58, slot));
    }

    @NotNull
    public static final Unit getIload_0(@NotNull InstructionAssembly iload_0) {
        Intrinsics.checkParameterIsNotNull(iload_0, "$this$iload_0");
        iload_0.getInstructions().add(new VarInsnNode(21, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLload_0(@NotNull InstructionAssembly lload_0) {
        Intrinsics.checkParameterIsNotNull(lload_0, "$this$lload_0");
        lload_0.getInstructions().add(new VarInsnNode(22, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFload_0(@NotNull InstructionAssembly fload_0) {
        Intrinsics.checkParameterIsNotNull(fload_0, "$this$fload_0");
        fload_0.getInstructions().add(new VarInsnNode(23, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDload_0(@NotNull InstructionAssembly dload_0) {
        Intrinsics.checkParameterIsNotNull(dload_0, "$this$dload_0");
        dload_0.getInstructions().add(new VarInsnNode(24, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAload_0(@NotNull InstructionAssembly aload_0) {
        Intrinsics.checkParameterIsNotNull(aload_0, "$this$aload_0");
        aload_0.getInstructions().add(new VarInsnNode(25, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIstore_0(@NotNull InstructionAssembly istore_0) {
        Intrinsics.checkParameterIsNotNull(istore_0, "$this$istore_0");
        istore_0.getInstructions().add(new VarInsnNode(54, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLstore_0(@NotNull InstructionAssembly lstore_0) {
        Intrinsics.checkParameterIsNotNull(lstore_0, "$this$lstore_0");
        lstore_0.getInstructions().add(new VarInsnNode(55, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFstore_0(@NotNull InstructionAssembly fstore_0) {
        Intrinsics.checkParameterIsNotNull(fstore_0, "$this$fstore_0");
        fstore_0.getInstructions().add(new VarInsnNode(56, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDstore_0(@NotNull InstructionAssembly dstore_0) {
        Intrinsics.checkParameterIsNotNull(dstore_0, "$this$dstore_0");
        dstore_0.getInstructions().add(new VarInsnNode(57, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAstore_0(@NotNull InstructionAssembly astore_0) {
        Intrinsics.checkParameterIsNotNull(astore_0, "$this$astore_0");
        astore_0.getInstructions().add(new VarInsnNode(58, 0));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIload_1(@NotNull InstructionAssembly iload_1) {
        Intrinsics.checkParameterIsNotNull(iload_1, "$this$iload_1");
        iload_1.getInstructions().add(new VarInsnNode(21, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLload_1(@NotNull InstructionAssembly lload_1) {
        Intrinsics.checkParameterIsNotNull(lload_1, "$this$lload_1");
        lload_1.getInstructions().add(new VarInsnNode(22, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFload_1(@NotNull InstructionAssembly fload_1) {
        Intrinsics.checkParameterIsNotNull(fload_1, "$this$fload_1");
        fload_1.getInstructions().add(new VarInsnNode(23, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDload_1(@NotNull InstructionAssembly dload_1) {
        Intrinsics.checkParameterIsNotNull(dload_1, "$this$dload_1");
        dload_1.getInstructions().add(new VarInsnNode(24, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAload_1(@NotNull InstructionAssembly aload_1) {
        Intrinsics.checkParameterIsNotNull(aload_1, "$this$aload_1");
        aload_1.getInstructions().add(new VarInsnNode(25, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIstore_1(@NotNull InstructionAssembly istore_1) {
        Intrinsics.checkParameterIsNotNull(istore_1, "$this$istore_1");
        istore_1.getInstructions().add(new VarInsnNode(54, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLstore_1(@NotNull InstructionAssembly lstore_1) {
        Intrinsics.checkParameterIsNotNull(lstore_1, "$this$lstore_1");
        lstore_1.getInstructions().add(new VarInsnNode(55, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFstore_1(@NotNull InstructionAssembly fstore_1) {
        Intrinsics.checkParameterIsNotNull(fstore_1, "$this$fstore_1");
        fstore_1.getInstructions().add(new VarInsnNode(56, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDstore_1(@NotNull InstructionAssembly dstore_1) {
        Intrinsics.checkParameterIsNotNull(dstore_1, "$this$dstore_1");
        dstore_1.getInstructions().add(new VarInsnNode(57, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAstore_1(@NotNull InstructionAssembly astore_1) {
        Intrinsics.checkParameterIsNotNull(astore_1, "$this$astore_1");
        astore_1.getInstructions().add(new VarInsnNode(58, 1));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIload_2(@NotNull InstructionAssembly iload_2) {
        Intrinsics.checkParameterIsNotNull(iload_2, "$this$iload_2");
        iload_2.getInstructions().add(new VarInsnNode(21, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLload_2(@NotNull InstructionAssembly lload_2) {
        Intrinsics.checkParameterIsNotNull(lload_2, "$this$lload_2");
        lload_2.getInstructions().add(new VarInsnNode(22, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFload_2(@NotNull InstructionAssembly fload_2) {
        Intrinsics.checkParameterIsNotNull(fload_2, "$this$fload_2");
        fload_2.getInstructions().add(new VarInsnNode(23, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDload_2(@NotNull InstructionAssembly dload_2) {
        Intrinsics.checkParameterIsNotNull(dload_2, "$this$dload_2");
        dload_2.getInstructions().add(new VarInsnNode(24, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAload_2(@NotNull InstructionAssembly aload_2) {
        Intrinsics.checkParameterIsNotNull(aload_2, "$this$aload_2");
        aload_2.getInstructions().add(new VarInsnNode(25, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIstore_2(@NotNull InstructionAssembly istore_2) {
        Intrinsics.checkParameterIsNotNull(istore_2, "$this$istore_2");
        istore_2.getInstructions().add(new VarInsnNode(54, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLstore_2(@NotNull InstructionAssembly lstore_2) {
        Intrinsics.checkParameterIsNotNull(lstore_2, "$this$lstore_2");
        lstore_2.getInstructions().add(new VarInsnNode(55, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFstore_2(@NotNull InstructionAssembly fstore_2) {
        Intrinsics.checkParameterIsNotNull(fstore_2, "$this$fstore_2");
        fstore_2.getInstructions().add(new VarInsnNode(56, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDstore_2(@NotNull InstructionAssembly dstore_2) {
        Intrinsics.checkParameterIsNotNull(dstore_2, "$this$dstore_2");
        dstore_2.getInstructions().add(new VarInsnNode(57, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAstore_2(@NotNull InstructionAssembly astore_2) {
        Intrinsics.checkParameterIsNotNull(astore_2, "$this$astore_2");
        astore_2.getInstructions().add(new VarInsnNode(58, 2));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIload_3(@NotNull InstructionAssembly iload_3) {
        Intrinsics.checkParameterIsNotNull(iload_3, "$this$iload_3");
        iload_3.getInstructions().add(new VarInsnNode(21, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLload_3(@NotNull InstructionAssembly lload_3) {
        Intrinsics.checkParameterIsNotNull(lload_3, "$this$lload_3");
        lload_3.getInstructions().add(new VarInsnNode(22, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFload_3(@NotNull InstructionAssembly fload_3) {
        Intrinsics.checkParameterIsNotNull(fload_3, "$this$fload_3");
        fload_3.getInstructions().add(new VarInsnNode(23, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDload_3(@NotNull InstructionAssembly dload_3) {
        Intrinsics.checkParameterIsNotNull(dload_3, "$this$dload_3");
        dload_3.getInstructions().add(new VarInsnNode(24, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAload_3(@NotNull InstructionAssembly aload_3) {
        Intrinsics.checkParameterIsNotNull(aload_3, "$this$aload_3");
        aload_3.getInstructions().add(new VarInsnNode(25, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIstore_3(@NotNull InstructionAssembly istore_3) {
        Intrinsics.checkParameterIsNotNull(istore_3, "$this$istore_3");
        istore_3.getInstructions().add(new VarInsnNode(54, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLstore_3(@NotNull InstructionAssembly lstore_3) {
        Intrinsics.checkParameterIsNotNull(lstore_3, "$this$lstore_3");
        lstore_3.getInstructions().add(new VarInsnNode(55, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFstore_3(@NotNull InstructionAssembly fstore_3) {
        Intrinsics.checkParameterIsNotNull(fstore_3, "$this$fstore_3");
        fstore_3.getInstructions().add(new VarInsnNode(56, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDstore_3(@NotNull InstructionAssembly dstore_3) {
        Intrinsics.checkParameterIsNotNull(dstore_3, "$this$dstore_3");
        dstore_3.getInstructions().add(new VarInsnNode(57, 3));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAstore_3(@NotNull InstructionAssembly astore_3) {
        Intrinsics.checkParameterIsNotNull(astore_3, "$this$astore_3");
        astore_3.getInstructions().add(new VarInsnNode(58, 3));
        return Unit.INSTANCE;
    }
}
