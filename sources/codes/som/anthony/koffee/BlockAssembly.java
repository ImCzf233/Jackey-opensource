package codes.som.anthony.koffee;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.labels.LabelRegistry;
import codes.som.anthony.koffee.labels.LabelScope;
import codes.som.anthony.koffee.modifiers.C0000annotation;
import codes.som.anthony.koffee.modifiers.C0001bridge;
import codes.som.anthony.koffee.modifiers.C0002module;
import codes.som.anthony.koffee.modifiers.Cabstract;
import codes.som.anthony.koffee.modifiers.Cenum;
import codes.som.anthony.koffee.modifiers.Cfinal;
import codes.som.anthony.koffee.modifiers.Cinterface;
import codes.som.anthony.koffee.modifiers.Cnative;
import codes.som.anthony.koffee.modifiers.Cprivate;
import codes.som.anthony.koffee.modifiers.Cprotected;
import codes.som.anthony.koffee.modifiers.Cpublic;
import codes.som.anthony.koffee.modifiers.Cstatic;
import codes.som.anthony.koffee.modifiers.Csuper;
import codes.som.anthony.koffee.modifiers.Csynchronized;
import codes.som.anthony.koffee.modifiers.Ctransient;
import codes.som.anthony.koffee.modifiers.Cvolatile;
import codes.som.anthony.koffee.modifiers.deprecated;
import codes.som.anthony.koffee.modifiers.mandated;
import codes.som.anthony.koffee.modifiers.open;
import codes.som.anthony.koffee.modifiers.package_private;
import codes.som.anthony.koffee.modifiers.static_phase;
import codes.som.anthony.koffee.modifiers.strict;
import codes.som.anthony.koffee.modifiers.synthetic;
import codes.som.anthony.koffee.modifiers.transitive;
import codes.som.anthony.koffee.modifiers.varargs;
import codes.som.anthony.koffee.sugar.ModifiersAccess;
import codes.som.anthony.koffee.sugar.TypesAccess;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.TryCatchBlockNode;

/* compiled from: BlockAssembly.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018��2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\u001b\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n��\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, m53d2 = {"Lcodes/som/anthony/koffee/BlockAssembly;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "Lcodes/som/anthony/koffee/TryCatchContainer;", "Lcodes/som/anthony/koffee/labels/LabelScope;", "Lcodes/som/anthony/koffee/sugar/ModifiersAccess;", "Lcodes/som/anthony/koffee/sugar/TypesAccess;", "instructions", "Lorg/objectweb/asm/tree/InsnList;", "tryCatchBlocks", "", "Lorg/objectweb/asm/tree/TryCatchBlockNode;", "(Lorg/objectweb/asm/tree/InsnList;Ljava/util/List;)V", "L", "Lcodes/som/anthony/koffee/labels/LabelRegistry;", "getL", "()Lcodes/som/anthony/koffee/labels/LabelRegistry;", "getInstructions", "()Lorg/objectweb/asm/tree/InsnList;", "getTryCatchBlocks", "()Ljava/util/List;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/BlockAssembly.class */
public final class BlockAssembly implements InstructionAssembly, TryCatchContainer, LabelScope, ModifiersAccess, TypesAccess {
    @NotNull

    /* renamed from: L */
    private final LabelRegistry f0L = new LabelRegistry(this);
    @NotNull
    private final InsnList instructions;
    @NotNull
    private final List<TryCatchBlockNode> tryCatchBlocks;

    @Override // codes.som.anthony.koffee.insns.InstructionAssembly
    @NotNull
    public InsnList getInstructions() {
        return this.instructions;
    }

    @Override // codes.som.anthony.koffee.TryCatchContainer
    @NotNull
    public List<TryCatchBlockNode> getTryCatchBlocks() {
        return this.tryCatchBlocks;
    }

    public BlockAssembly(@NotNull InsnList instructions, @NotNull List<TryCatchBlockNode> tryCatchBlocks) {
        Intrinsics.checkParameterIsNotNull(instructions, "instructions");
        Intrinsics.checkParameterIsNotNull(tryCatchBlocks, "tryCatchBlocks");
        this.instructions = instructions;
        this.tryCatchBlocks = tryCatchBlocks;
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public package_private getPackage_private() {
        return ModifiersAccess.DefaultImpls.getPackage_private(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cpublic getPublic() {
        return ModifiersAccess.DefaultImpls.getPublic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cprivate getPrivate() {
        return ModifiersAccess.DefaultImpls.getPrivate(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cprotected getProtected() {
        return ModifiersAccess.DefaultImpls.getProtected(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cstatic getStatic() {
        return ModifiersAccess.DefaultImpls.getStatic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cfinal getFinal() {
        return ModifiersAccess.DefaultImpls.getFinal(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csuper getSuper() {
        return ModifiersAccess.DefaultImpls.getSuper(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csuper get_super() {
        return ModifiersAccess.DefaultImpls.get_super(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csynchronized getSynchronized() {
        return ModifiersAccess.DefaultImpls.getSynchronized(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public open getOpen() {
        return ModifiersAccess.DefaultImpls.getOpen(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public transitive getTransitive() {
        return ModifiersAccess.DefaultImpls.getTransitive(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cvolatile getVolatile() {
        return ModifiersAccess.DefaultImpls.getVolatile(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0001bridge getBridge() {
        return ModifiersAccess.DefaultImpls.getBridge(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public static_phase getStatic_phase() {
        return ModifiersAccess.DefaultImpls.getStatic_phase(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public varargs getVarargs() {
        return ModifiersAccess.DefaultImpls.getVarargs(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Ctransient getTransient() {
        return ModifiersAccess.DefaultImpls.getTransient(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cnative getNative() {
        return ModifiersAccess.DefaultImpls.getNative(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cinterface getInterface() {
        return ModifiersAccess.DefaultImpls.getInterface(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cinterface get_interface() {
        return ModifiersAccess.DefaultImpls.get_interface(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cabstract getAbstract() {
        return ModifiersAccess.DefaultImpls.getAbstract(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public strict getStrict() {
        return ModifiersAccess.DefaultImpls.getStrict(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public synthetic getSynthetic() {
        return ModifiersAccess.DefaultImpls.getSynthetic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0000annotation getAnnotation() {
        return ModifiersAccess.DefaultImpls.getAnnotation(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cenum getEnum() {
        return ModifiersAccess.DefaultImpls.getEnum(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public mandated getMandated() {
        return ModifiersAccess.DefaultImpls.getMandated(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0002module getModule() {
        return ModifiersAccess.DefaultImpls.getModule(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public deprecated getDeprecated() {
        return ModifiersAccess.DefaultImpls.getDeprecated(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getVoid() {
        return TypesAccess.DefaultImpls.getVoid(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getChar() {
        return TypesAccess.DefaultImpls.getChar(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getByte() {
        return TypesAccess.DefaultImpls.getByte(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getShort() {
        return TypesAccess.DefaultImpls.getShort(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getInt() {
        return TypesAccess.DefaultImpls.getInt(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getFloat() {
        return TypesAccess.DefaultImpls.getFloat(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getLong() {
        return TypesAccess.DefaultImpls.getLong(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getDouble() {
        return TypesAccess.DefaultImpls.getDouble(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getBoolean() {
        return TypesAccess.DefaultImpls.getBoolean(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    @NotNull
    public Type coerceType(@NotNull Object value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return TypesAccess.DefaultImpls.coerceType(this, value);
    }

    @Override // codes.som.anthony.koffee.labels.LabelScope
    @NotNull
    public LabelRegistry getL() {
        return this.f0L;
    }
}
