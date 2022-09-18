package codes.som.anthony.koffee.insns.sugar;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.insns.jvm.Methods;
import codes.som.anthony.koffee.insns.jvm.ObjectManagement;
import codes.som.anthony.koffee.insns.jvm.StackManipulation;
import codes.som.anthony.koffee.types.Types;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��0\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0011\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001af\u0010��\u001a\u00020\u0001\"\b\b��\u0010\u0002*\u00020\u0003*\u0002H\u00022\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u00062\u001a\u0010\u0007\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0005j\u0002`\u00060\b\"\u00060\u0005j\u0002`\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\u0019\b\u0002\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f¢\u0006\u0002\b\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, m53d2 = {"construct", "", "S", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "type", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "constructorTypes", "", "initializerName", "", "initializerBlock", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.sugar.ObjectConstructionKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/sugar/ObjectConstructionKt.class */
public final class ObjectConstruction {
    public static /* synthetic */ void construct$default(InstructionAssembly instructionAssembly, Object obj, Object[] objArr, String str, Function1 function1, int i, Object obj2) {
        if ((i & 4) != 0) {
            str = "<init>";
        }
        if ((i & 8) != 0) {
            function1 = ObjectConstructionKt$construct$1.INSTANCE;
        }
        construct(instructionAssembly, obj, objArr, str, function1);
    }

    public static final <S extends InstructionAssembly> void construct(@NotNull S construct, @NotNull Object type, @NotNull Object[] constructorTypes, @NotNull String initializerName, @NotNull Function1<? super S, Unit> initializerBlock) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(construct, "$this$construct");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(constructorTypes, "constructorTypes");
        Intrinsics.checkParameterIsNotNull(initializerName, "initializerName");
        Intrinsics.checkParameterIsNotNull(initializerBlock, "initializerBlock");
        if (0 <= ArraysKt.getLastIndex(constructorTypes)) {
            obj = constructorTypes[0];
        } else {
            obj = Types.getVoid();
            Intrinsics.checkExpressionValueIsNotNull(obj, "void");
        }
        Object returnType = obj;
        Collection $this$toTypedArray$iv = ArraysKt.drop(constructorTypes, 1);
        if ($this$toTypedArray$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
        }
        Object[] parameterTypes = $this$toTypedArray$iv.toArray(new Object[0]);
        if (parameterTypes == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        ObjectManagement.m238new(construct, type);
        StackManipulation.getDup(construct);
        initializerBlock.invoke(construct);
        Intrinsics.checkExpressionValueIsNotNull(returnType, "returnType");
        Methods.invokespecial(construct, type, initializerName, returnType, Arrays.copyOf(parameterTypes, parameterTypes.length));
    }
}
