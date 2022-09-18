package codes.som.anthony.koffee.insns.jvm;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.types.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��&\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\u001aK\u0010��\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\f\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\r\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b¨\u0006\u000f"}, m53d2 = {"invokeinterface", "", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "owner", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "name", "", "returnType", "parameterTypes", "", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V", "invokespecial", "invokestatic", "invokevirtual", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.MethodsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/MethodsKt.class */
public final class Methods {
    public static final void invokevirtual(@NotNull InstructionAssembly invokevirtual, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(invokevirtual, "$this$invokevirtual");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Type coerceType = Types.coerceType(returnType);
        Collection destination$iv$iv = new ArrayList(parameterTypes.length);
        for (Object item$iv$iv : parameterTypes) {
            destination$iv$iv.add(Types.coerceType(item$iv$iv));
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Type[] typeArr = (Type[]) array;
        String descriptor = Type.getMethodDescriptor(coerceType, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        invokevirtual.getInstructions().add(new MethodInsnNode(182, Types.coerceType(owner).getInternalName(), name, descriptor));
    }

    public static final void invokespecial(@NotNull InstructionAssembly invokespecial, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(invokespecial, "$this$invokespecial");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Type coerceType = Types.coerceType(returnType);
        Collection destination$iv$iv = new ArrayList(parameterTypes.length);
        for (Object item$iv$iv : parameterTypes) {
            destination$iv$iv.add(Types.coerceType(item$iv$iv));
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Type[] typeArr = (Type[]) array;
        String descriptor = Type.getMethodDescriptor(coerceType, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        invokespecial.getInstructions().add(new MethodInsnNode(183, Types.coerceType(owner).getInternalName(), name, descriptor));
    }

    public static final void invokestatic(@NotNull InstructionAssembly invokestatic, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(invokestatic, "$this$invokestatic");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Type coerceType = Types.coerceType(returnType);
        Collection destination$iv$iv = new ArrayList(parameterTypes.length);
        for (Object item$iv$iv : parameterTypes) {
            destination$iv$iv.add(Types.coerceType(item$iv$iv));
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Type[] typeArr = (Type[]) array;
        String descriptor = Type.getMethodDescriptor(coerceType, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        invokestatic.getInstructions().add(new MethodInsnNode(184, Types.coerceType(owner).getInternalName(), name, descriptor));
    }

    public static final void invokeinterface(@NotNull InstructionAssembly invokeinterface, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(invokeinterface, "$this$invokeinterface");
        Intrinsics.checkParameterIsNotNull(owner, "owner");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Type coerceType = Types.coerceType(returnType);
        Collection destination$iv$iv = new ArrayList(parameterTypes.length);
        for (Object item$iv$iv : parameterTypes) {
            destination$iv$iv.add(Types.coerceType(item$iv$iv));
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Type[] typeArr = (Type[]) array;
        String descriptor = Type.getMethodDescriptor(coerceType, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        invokeinterface.getInstructions().add(new MethodInsnNode(185, Types.coerceType(owner).getInternalName(), name, descriptor));
    }
}
