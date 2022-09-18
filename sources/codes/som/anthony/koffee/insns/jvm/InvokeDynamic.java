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
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��.\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u001aK\u0010��\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\f\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\r\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aK\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u000b\u001aW\u0010\u000f\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\u0004j\u0002`\u00052\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0004j\u0002`\u00050\n\"\u00060\u0004j\u0002`\u00052\u0006\u0010\u0011\u001a\u00020\u00012\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\n¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, m53d2 = {"h_invokeinterface", "Lorg/objectweb/asm/Handle;", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "owner", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "name", "", "returnType", "parameterTypes", "", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)Lorg/objectweb/asm/Handle;", "h_invokespecial", "h_invokestatic", "h_invokevirtual", "invokedynamic", "", "handle", "args", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;Lorg/objectweb/asm/Handle;[Ljava/lang/Object;)V", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.jvm.InvokeDynamicKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/jvm/InvokeDynamicKt.class */
public final class InvokeDynamic {
    public static final void invokedynamic(@NotNull InstructionAssembly invokedynamic, @NotNull String name, @NotNull Object returnType, @NotNull Object[] parameterTypes, @NotNull Handle handle, @NotNull Object[] args) {
        Intrinsics.checkParameterIsNotNull(invokedynamic, "$this$invokedynamic");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(handle, "handle");
        Intrinsics.checkParameterIsNotNull(args, "args");
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
        invokedynamic.getInstructions().add(new InvokeDynamicInsnNode(name, descriptor, handle, Arrays.copyOf(args, args.length)));
    }

    @NotNull
    public static final Handle h_invokestatic(@NotNull InstructionAssembly h_invokestatic, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(h_invokestatic, "$this$h_invokestatic");
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
        return new Handle(6, Types.coerceType(owner).getInternalName(), name, descriptor, false);
    }

    @NotNull
    public static final Handle h_invokevirtual(@NotNull InstructionAssembly h_invokevirtual, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(h_invokevirtual, "$this$h_invokevirtual");
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
        return new Handle(5, Types.coerceType(owner).getInternalName(), name, descriptor, false);
    }

    @NotNull
    public static final Handle h_invokespecial(@NotNull InstructionAssembly h_invokespecial, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(h_invokespecial, "$this$h_invokespecial");
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
        return new Handle(7, Types.coerceType(owner).getInternalName(), name, descriptor, false);
    }

    @NotNull
    public static final Handle h_invokeinterface(@NotNull InstructionAssembly h_invokeinterface, @NotNull Object owner, @NotNull String name, @NotNull Object returnType, @NotNull Object... parameterTypes) {
        Intrinsics.checkParameterIsNotNull(h_invokeinterface, "$this$h_invokeinterface");
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
        return new Handle(9, Types.coerceType(owner).getInternalName(), name, descriptor, true);
    }
}
