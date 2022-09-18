package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u001a\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bh\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0012\u0010j\u001a\u00020\u0001*\u00020\u00032\u0006\u0010k\u001a\u00020l\u001a\u001a\u0010j\u001a\u00020\u0001*\u00020\u00032\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020l\"\u0019\u0010��\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0019\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0019\u0010\b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005\"\u0019\u0010\n\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0019\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005\"\u0019\u0010\u000e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005\"\u0019\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005\"\u0019\u0010\u0012\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005\"\u0019\u0010\u0014\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005\"\u0019\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005\"\u0019\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005\"\u0019\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005\"\u0019\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005\"\u0019\u0010\u001e\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005\"\u0019\u0010 \u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b!\u0010\u0005\"\u0019\u0010\"\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b#\u0010\u0005\"\u0019\u0010$\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b%\u0010\u0005\"\u0019\u0010&\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b'\u0010\u0005\"\u0019\u0010(\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b)\u0010\u0005\"\u0019\u0010*\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b+\u0010\u0005\"\u0019\u0010,\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b-\u0010\u0005\"\u0019\u0010.\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b/\u0010\u0005\"\u0019\u00100\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b1\u0010\u0005\"\u0019\u00102\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b3\u0010\u0005\"\u0019\u00104\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b5\u0010\u0005\"\u0019\u00106\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b7\u0010\u0005\"\u0019\u00108\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b9\u0010\u0005\"\u0019\u0010:\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b;\u0010\u0005\"\u0019\u0010<\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b=\u0010\u0005\"\u0019\u0010>\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b?\u0010\u0005\"\u0019\u0010@\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bA\u0010\u0005\"\u0019\u0010B\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bC\u0010\u0005\"\u0019\u0010D\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bE\u0010\u0005\"\u0019\u0010F\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bG\u0010\u0005\"\u0019\u0010H\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bI\u0010\u0005\"\u0019\u0010J\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bK\u0010\u0005\"\u0019\u0010L\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bM\u0010\u0005\"\u0019\u0010N\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bO\u0010\u0005\"\u0019\u0010P\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bQ\u0010\u0005\"\u0019\u0010R\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bS\u0010\u0005\"\u0019\u0010T\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bU\u0010\u0005\"\u0019\u0010V\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bW\u0010\u0005\"\u0019\u0010X\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bY\u0010\u0005\"\u0019\u0010Z\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b[\u0010\u0005\"\u0019\u0010\\\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b]\u0010\u0005\"\u0019\u0010^\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b_\u0010\u0005\"\u0019\u0010`\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\ba\u0010\u0005\"\u0019\u0010b\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bc\u0010\u0005\"\u0019\u0010d\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\be\u0010\u0005\"\u0019\u0010f\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bg\u0010\u0005\"\u0019\u0010h\u001a\u00060\u0001j\u0002`\u0002*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bi\u0010\u0005¨\u0006n"}, m53d2 = {"d2f", "", "Lcodes/som/anthony/koffee/insns/jvm/U;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "getD2f", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)Lkotlin/Unit;", "d2i", "getD2i", "d2l", "getD2l", "dadd", "getDadd", "ddiv", "getDdiv", "dmul", "getDmul", "dneg", "getDneg", "drem", "getDrem", "dsub", "getDsub", "f2d", "getF2d", "f2i", "getF2i", "f2l", "getF2l", "fadd", "getFadd", "fdiv", "getFdiv", "fmul", "getFmul", "fneg", "getFneg", "frem", "getFrem", "fsub", "getFsub", "i2b", "getI2b", "i2c", "getI2c", "i2d", "getI2d", "i2f", "getI2f", "i2l", "getI2l", "i2s", "getI2s", "iadd", "getIadd", "iand", "getIand", "idiv", "getIdiv", "imul", "getImul", "ineg", "getIneg", "ior", "getIor", "irem", "getIrem", "ishl", "getIshl", "ishr", "getIshr", "isub", "getIsub", "iushr", "getIushr", "ixor", "getIxor", "l2d", "getL2d", "l2f", "getL2f", "l2i", "getL2i", "ladd", "getLadd", "land", "getLand", "ldiv", "getLdiv", "lmul", "getLmul", "lneg", "getLneg", "lor", "getLor", "lrem", "getLrem", "lshl", "getLshl", "lshr", "getLshr", "lsub", "getLsub", "lushr", "getLushr", "lxor", "getLxor", "iinc", "slot", "", "amount", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.ArithmeticKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/ArithmeticKt.class */
public final class Arithmetic {
    @NotNull
    public static final Unit getIadd(@NotNull InstructionAssembly iadd) {
        Intrinsics.checkParameterIsNotNull(iadd, "$this$iadd");
        iadd.getInstructions().add(new InsnNode(96));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLadd(@NotNull InstructionAssembly ladd) {
        Intrinsics.checkParameterIsNotNull(ladd, "$this$ladd");
        ladd.getInstructions().add(new InsnNode(97));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFadd(@NotNull InstructionAssembly fadd) {
        Intrinsics.checkParameterIsNotNull(fadd, "$this$fadd");
        fadd.getInstructions().add(new InsnNode(98));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDadd(@NotNull InstructionAssembly dadd) {
        Intrinsics.checkParameterIsNotNull(dadd, "$this$dadd");
        dadd.getInstructions().add(new InsnNode(99));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIsub(@NotNull InstructionAssembly isub) {
        Intrinsics.checkParameterIsNotNull(isub, "$this$isub");
        isub.getInstructions().add(new InsnNode(100));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLsub(@NotNull InstructionAssembly lsub) {
        Intrinsics.checkParameterIsNotNull(lsub, "$this$lsub");
        lsub.getInstructions().add(new InsnNode(101));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFsub(@NotNull InstructionAssembly fsub) {
        Intrinsics.checkParameterIsNotNull(fsub, "$this$fsub");
        fsub.getInstructions().add(new InsnNode(102));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDsub(@NotNull InstructionAssembly dsub) {
        Intrinsics.checkParameterIsNotNull(dsub, "$this$dsub");
        dsub.getInstructions().add(new InsnNode(103));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getImul(@NotNull InstructionAssembly imul) {
        Intrinsics.checkParameterIsNotNull(imul, "$this$imul");
        imul.getInstructions().add(new InsnNode(104));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLmul(@NotNull InstructionAssembly lmul) {
        Intrinsics.checkParameterIsNotNull(lmul, "$this$lmul");
        lmul.getInstructions().add(new InsnNode(105));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFmul(@NotNull InstructionAssembly fmul) {
        Intrinsics.checkParameterIsNotNull(fmul, "$this$fmul");
        fmul.getInstructions().add(new InsnNode(106));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDmul(@NotNull InstructionAssembly dmul) {
        Intrinsics.checkParameterIsNotNull(dmul, "$this$dmul");
        dmul.getInstructions().add(new InsnNode(107));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIdiv(@NotNull InstructionAssembly idiv) {
        Intrinsics.checkParameterIsNotNull(idiv, "$this$idiv");
        idiv.getInstructions().add(new InsnNode(108));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLdiv(@NotNull InstructionAssembly ldiv) {
        Intrinsics.checkParameterIsNotNull(ldiv, "$this$ldiv");
        ldiv.getInstructions().add(new InsnNode(109));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFdiv(@NotNull InstructionAssembly fdiv) {
        Intrinsics.checkParameterIsNotNull(fdiv, "$this$fdiv");
        fdiv.getInstructions().add(new InsnNode(110));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDdiv(@NotNull InstructionAssembly ddiv) {
        Intrinsics.checkParameterIsNotNull(ddiv, "$this$ddiv");
        ddiv.getInstructions().add(new InsnNode(111));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIrem(@NotNull InstructionAssembly irem) {
        Intrinsics.checkParameterIsNotNull(irem, "$this$irem");
        irem.getInstructions().add(new InsnNode(112));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLrem(@NotNull InstructionAssembly lrem) {
        Intrinsics.checkParameterIsNotNull(lrem, "$this$lrem");
        lrem.getInstructions().add(new InsnNode(113));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFrem(@NotNull InstructionAssembly frem) {
        Intrinsics.checkParameterIsNotNull(frem, "$this$frem");
        frem.getInstructions().add(new InsnNode(114));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDrem(@NotNull InstructionAssembly drem) {
        Intrinsics.checkParameterIsNotNull(drem, "$this$drem");
        drem.getInstructions().add(new InsnNode(115));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIneg(@NotNull InstructionAssembly ineg) {
        Intrinsics.checkParameterIsNotNull(ineg, "$this$ineg");
        ineg.getInstructions().add(new InsnNode(116));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLneg(@NotNull InstructionAssembly lneg) {
        Intrinsics.checkParameterIsNotNull(lneg, "$this$lneg");
        lneg.getInstructions().add(new InsnNode(117));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getFneg(@NotNull InstructionAssembly fneg) {
        Intrinsics.checkParameterIsNotNull(fneg, "$this$fneg");
        fneg.getInstructions().add(new InsnNode(118));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getDneg(@NotNull InstructionAssembly dneg) {
        Intrinsics.checkParameterIsNotNull(dneg, "$this$dneg");
        dneg.getInstructions().add(new InsnNode(119));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIshl(@NotNull InstructionAssembly ishl) {
        Intrinsics.checkParameterIsNotNull(ishl, "$this$ishl");
        ishl.getInstructions().add(new InsnNode(120));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLshl(@NotNull InstructionAssembly lshl) {
        Intrinsics.checkParameterIsNotNull(lshl, "$this$lshl");
        lshl.getInstructions().add(new InsnNode(121));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIshr(@NotNull InstructionAssembly ishr) {
        Intrinsics.checkParameterIsNotNull(ishr, "$this$ishr");
        ishr.getInstructions().add(new InsnNode(122));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLshr(@NotNull InstructionAssembly lshr) {
        Intrinsics.checkParameterIsNotNull(lshr, "$this$lshr");
        lshr.getInstructions().add(new InsnNode(123));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIushr(@NotNull InstructionAssembly iushr) {
        Intrinsics.checkParameterIsNotNull(iushr, "$this$iushr");
        iushr.getInstructions().add(new InsnNode(124));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLushr(@NotNull InstructionAssembly lushr) {
        Intrinsics.checkParameterIsNotNull(lushr, "$this$lushr");
        lushr.getInstructions().add(new InsnNode(125));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIand(@NotNull InstructionAssembly iand) {
        Intrinsics.checkParameterIsNotNull(iand, "$this$iand");
        iand.getInstructions().add(new InsnNode(126));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLand(@NotNull InstructionAssembly land) {
        Intrinsics.checkParameterIsNotNull(land, "$this$land");
        land.getInstructions().add(new InsnNode(127));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIor(@NotNull InstructionAssembly ior) {
        Intrinsics.checkParameterIsNotNull(ior, "$this$ior");
        ior.getInstructions().add(new InsnNode(128));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLor(@NotNull InstructionAssembly lor) {
        Intrinsics.checkParameterIsNotNull(lor, "$this$lor");
        lor.getInstructions().add(new InsnNode(129));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getIxor(@NotNull InstructionAssembly ixor) {
        Intrinsics.checkParameterIsNotNull(ixor, "$this$ixor");
        ixor.getInstructions().add(new InsnNode(130));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getLxor(@NotNull InstructionAssembly lxor) {
        Intrinsics.checkParameterIsNotNull(lxor, "$this$lxor");
        lxor.getInstructions().add(new InsnNode(131));
        return Unit.INSTANCE;
    }

    public static final void iinc(@NotNull InstructionAssembly iinc, int slot) {
        Intrinsics.checkParameterIsNotNull(iinc, "$this$iinc");
        iinc.getInstructions().add(new IincInsnNode(slot, 1));
    }

    public static final void iinc(@NotNull InstructionAssembly iinc, int slot, int amount) {
        Intrinsics.checkParameterIsNotNull(iinc, "$this$iinc");
        iinc.getInstructions().add(new IincInsnNode(slot, amount));
    }

    @NotNull
    public static final Unit getI2l(@NotNull InstructionAssembly i2l) {
        Intrinsics.checkParameterIsNotNull(i2l, "$this$i2l");
        i2l.getInstructions().add(new InsnNode(133));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getI2f(@NotNull InstructionAssembly i2f) {
        Intrinsics.checkParameterIsNotNull(i2f, "$this$i2f");
        i2f.getInstructions().add(new InsnNode(134));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getI2d(@NotNull InstructionAssembly i2d) {
        Intrinsics.checkParameterIsNotNull(i2d, "$this$i2d");
        i2d.getInstructions().add(new InsnNode(135));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getL2i(@NotNull InstructionAssembly l2i) {
        Intrinsics.checkParameterIsNotNull(l2i, "$this$l2i");
        l2i.getInstructions().add(new InsnNode(136));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getL2f(@NotNull InstructionAssembly l2f) {
        Intrinsics.checkParameterIsNotNull(l2f, "$this$l2f");
        l2f.getInstructions().add(new InsnNode(137));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getL2d(@NotNull InstructionAssembly l2d) {
        Intrinsics.checkParameterIsNotNull(l2d, "$this$l2d");
        l2d.getInstructions().add(new InsnNode(138));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getF2i(@NotNull InstructionAssembly f2i) {
        Intrinsics.checkParameterIsNotNull(f2i, "$this$f2i");
        f2i.getInstructions().add(new InsnNode(139));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getF2l(@NotNull InstructionAssembly f2l) {
        Intrinsics.checkParameterIsNotNull(f2l, "$this$f2l");
        f2l.getInstructions().add(new InsnNode(140));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getF2d(@NotNull InstructionAssembly f2d) {
        Intrinsics.checkParameterIsNotNull(f2d, "$this$f2d");
        f2d.getInstructions().add(new InsnNode(141));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getD2i(@NotNull InstructionAssembly d2i) {
        Intrinsics.checkParameterIsNotNull(d2i, "$this$d2i");
        d2i.getInstructions().add(new InsnNode(142));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getD2l(@NotNull InstructionAssembly d2l) {
        Intrinsics.checkParameterIsNotNull(d2l, "$this$d2l");
        d2l.getInstructions().add(new InsnNode(143));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getD2f(@NotNull InstructionAssembly d2f) {
        Intrinsics.checkParameterIsNotNull(d2f, "$this$d2f");
        d2f.getInstructions().add(new InsnNode(144));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getI2b(@NotNull InstructionAssembly i2b) {
        Intrinsics.checkParameterIsNotNull(i2b, "$this$i2b");
        i2b.getInstructions().add(new InsnNode(145));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getI2c(@NotNull InstructionAssembly i2c) {
        Intrinsics.checkParameterIsNotNull(i2c, "$this$i2c");
        i2c.getInstructions().add(new InsnNode(146));
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Unit getI2s(@NotNull InstructionAssembly i2s) {
        Intrinsics.checkParameterIsNotNull(i2s, "$this$i2s");
        i2s.getInstructions().add(new InsnNode(147));
        return Unit.INSTANCE;
    }
}
