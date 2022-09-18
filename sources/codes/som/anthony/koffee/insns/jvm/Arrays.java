package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.types.Types;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��&\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0016\u0010&\u001a\u00020\u0001*\u00020\u00032\n\u0010'\u001a\u00060(j\u0002`)\u001a\u001e\u0010*\u001a\u00020\u0001*\u00020\u00032\n\u0010'\u001a\u00060(j\u0002`)2\u0006\u0010+\u001a\u00020,\u001a\u0016\u0010-\u001a\u00020\u0001*\u00020\u00032\n\u0010'\u001a\u00060(j\u0002`)\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005\"\u0019\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005\"\u0019\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005\"\u0019\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005\"\u0019\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005\"\u0019\u0010\u001e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005\"\u0019\u0010 \u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010\u0005\"\u0019\u0010\"\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b#\u0010\u0005\"\u0019\u0010$\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b%\u0010\u0005¨\u0006."}, m53d2 = {"aaload", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getAaload", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "aastore", "getAastore", "arraylength", "getArraylength", "baload", "getBaload", "bastore", "getBastore", "caload", "getCaload", "castore", "getCastore", "daload", "getDaload", "dastore", "getDastore", "faload", "getFaload", "fastore", "getFastore", "iaload", "getIaload", "iastore", "getIastore", "laload", "getLaload", "lastore", "getLastore", "saload", "getSaload", "sastore", "getSastore", "anewarray", "type", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "multianewarray", "dimensions", "", "newarray", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.ArraysKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/ArraysKt.class */
public final class Arrays {
    @NotNull
    public static final Unit getIaload(@NotNull InstructionAssembly iaload) {
        Intrinsics.checkParameterIsNotNull(iaload, "$this$iaload");
        iaload.getInstructions().add(new InsnNode(46));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLaload(@NotNull InstructionAssembly laload) {
        Intrinsics.checkParameterIsNotNull(laload, "$this$laload");
        laload.getInstructions().add(new InsnNode(47));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFaload(@NotNull InstructionAssembly faload) {
        Intrinsics.checkParameterIsNotNull(faload, "$this$faload");
        faload.getInstructions().add(new InsnNode(48));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDaload(@NotNull InstructionAssembly daload) {
        Intrinsics.checkParameterIsNotNull(daload, "$this$daload");
        daload.getInstructions().add(new InsnNode(49));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAaload(@NotNull InstructionAssembly aaload) {
        Intrinsics.checkParameterIsNotNull(aaload, "$this$aaload");
        aaload.getInstructions().add(new InsnNode(50));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getBaload(@NotNull InstructionAssembly baload) {
        Intrinsics.checkParameterIsNotNull(baload, "$this$baload");
        baload.getInstructions().add(new InsnNode(51));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getCaload(@NotNull InstructionAssembly caload) {
        Intrinsics.checkParameterIsNotNull(caload, "$this$caload");
        caload.getInstructions().add(new InsnNode(52));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getSaload(@NotNull InstructionAssembly saload) {
        Intrinsics.checkParameterIsNotNull(saload, "$this$saload");
        saload.getInstructions().add(new InsnNode(53));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIastore(@NotNull InstructionAssembly iastore) {
        Intrinsics.checkParameterIsNotNull(iastore, "$this$iastore");
        iastore.getInstructions().add(new InsnNode(79));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLastore(@NotNull InstructionAssembly lastore) {
        Intrinsics.checkParameterIsNotNull(lastore, "$this$lastore");
        lastore.getInstructions().add(new InsnNode(80));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFastore(@NotNull InstructionAssembly fastore) {
        Intrinsics.checkParameterIsNotNull(fastore, "$this$fastore");
        fastore.getInstructions().add(new InsnNode(81));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDastore(@NotNull InstructionAssembly dastore) {
        Intrinsics.checkParameterIsNotNull(dastore, "$this$dastore");
        dastore.getInstructions().add(new InsnNode(82));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getAastore(@NotNull InstructionAssembly aastore) {
        Intrinsics.checkParameterIsNotNull(aastore, "$this$aastore");
        aastore.getInstructions().add(new InsnNode(83));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getBastore(@NotNull InstructionAssembly bastore) {
        Intrinsics.checkParameterIsNotNull(bastore, "$this$bastore");
        bastore.getInstructions().add(new InsnNode(84));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getCastore(@NotNull InstructionAssembly castore) {
        Intrinsics.checkParameterIsNotNull(castore, "$this$castore");
        castore.getInstructions().add(new InsnNode(85));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getSastore(@NotNull InstructionAssembly sastore) {
        Intrinsics.checkParameterIsNotNull(sastore, "$this$sastore");
        sastore.getInstructions().add(new InsnNode(86));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getArraylength(@NotNull InstructionAssembly arraylength) {
        Intrinsics.checkParameterIsNotNull(arraylength, "$this$arraylength");
        arraylength.getInstructions().add(new InsnNode(190));
        return Unit.INSTANCE;
    }

    public static final void newarray(@NotNull InstructionAssembly newarray, @NotNull Object type) {
        int i;
        Intrinsics.checkParameterIsNotNull(newarray, "$this$newarray");
        Intrinsics.checkParameterIsNotNull(type, "type");
        InsnList instructions = newarray.getInstructions();
        switch (Types.coerceType(type).getSort()) {
            case 1:
                i = 4;
                break;
            case 2:
                i = 5;
                break;
            case 3:
                i = 8;
                break;
            case 4:
                i = 9;
                break;
            case 5:
                i = 10;
                break;
            case 6:
                i = 6;
                break;
            case 7:
                i = 11;
                break;
            case 8:
                i = 7;
                break;
            default:
                throw new IllegalStateException("Invalid type for primitive array creation".toString());
        }
        instructions.add(new IntInsnNode(188, i));
    }

    public static final void anewarray(@NotNull InstructionAssembly anewarray, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(anewarray, "$this$anewarray");
        Intrinsics.checkParameterIsNotNull(type, "type");
        anewarray.getInstructions().add(new TypeInsnNode(189, Types.coerceType(type).getInternalName()));
    }

    public static final void multianewarray(@NotNull InstructionAssembly multianewarray, @NotNull Object type, int dimensions) {
        Intrinsics.checkParameterIsNotNull(multianewarray, "$this$multianewarray");
        Intrinsics.checkParameterIsNotNull(type, "type");
        multianewarray.getInstructions().add(new MultiANewArrayInsnNode(Types.coerceType(type).getDescriptor(), dimensions));
    }
}
