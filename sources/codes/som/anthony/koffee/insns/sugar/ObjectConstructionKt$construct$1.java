package codes.som.anthony.koffee.insns.sugar;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: ObjectConstruction.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 3, m54d1 = {"��\u0010\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010��\u001a\u00020\u0001\"\b\b��\u0010\u0002*\u00020\u0003*\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "", "S", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "invoke", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)V"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/sugar/ObjectConstructionKt$construct$1.class */
final class ObjectConstructionKt$construct$1 extends Lambda implements Function1<S, Unit> {
    public static final ObjectConstructionKt$construct$1 INSTANCE = new ObjectConstructionKt$construct$1();

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((InstructionAssembly) obj);
        return Unit.INSTANCE;
    }

    ObjectConstructionKt$construct$1() {
        super(1);
    }

    public final void invoke(@NotNull InstructionAssembly receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
    }
}
