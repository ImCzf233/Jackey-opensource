package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.types.Types;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u001e\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0005\u001a*\u0010��\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u0005\u001a*\u0010\t\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u0005\u001a*\u0010\n\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u0005\u001a*\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u0005¨\u0006\f"}, m53d2 = {"getfield", "", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "owner", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "name", "", "type", "getstatic", "putfield", "putstatic", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.FieldsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/FieldsKt.class */
public final class Fields {
    public static final void getstatic(@NotNull InstructionAssembly getstatic, @NotNull Object owner, @NotNull String name, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(getstatic, "$this$getstatic");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        getstatic.getInstructions().add(new FieldInsnNode(178, Types.coerceType(owner).getInternalName(), name, Types.coerceType(type).getDescriptor()));
    }

    public static final void getfield(@NotNull InstructionAssembly getfield, @NotNull Object owner, @NotNull String name, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(getfield, "$this$getfield");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        getfield.getInstructions().add(new FieldInsnNode(180, Types.coerceType(owner).getInternalName(), name, Types.coerceType(type).getDescriptor()));
    }

    public static final void putstatic(@NotNull InstructionAssembly putstatic, @NotNull Object owner, @NotNull String name, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(putstatic, "$this$putstatic");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        putstatic.getInstructions().add(new FieldInsnNode(179, Types.coerceType(owner).getInternalName(), name, Types.coerceType(type).getDescriptor()));
    }

    public static final void putfield(@NotNull InstructionAssembly putfield, @NotNull Object owner, @NotNull String name, @NotNull Object type) {
        Intrinsics.checkParameterIsNotNull(putfield, "$this$putfield");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        putfield.getInstructions().add(new FieldInsnNode(181, Types.coerceType(owner).getInternalName(), name, Types.coerceType(type).getDescriptor()));
    }
}
