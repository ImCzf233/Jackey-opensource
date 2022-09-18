package codes.som.anthony.koffee.insns.sugar;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import codes.som.anthony.koffee.insns.jvm.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\u001a\u0012\u0010��\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, m53d2 = {"push_int", "", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "i", "", "koffee"})
/* renamed from: codes.som.anthony.koffee.insns.sugar.IntConstantsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/insns/sugar/IntConstantsKt.class */
public final class IntConstants {
    public static final void push_int(@NotNull InstructionAssembly push_int, int i) {
        Intrinsics.checkParameterIsNotNull(push_int, "$this$push_int");
        if (i != -1) {
            if (i == 0) {
                Constants.getIconst_0(push_int);
                return;
            } else if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 5) {
                                if (-128 > i || 127 < i) {
                                    if (-32768 > i || 32767 < i) {
                                        Constants.ldc(push_int, Integer.valueOf(i));
                                        return;
                                    } else {
                                        Constants.sipush(push_int, i);
                                        return;
                                    }
                                }
                                Constants.bipush(push_int, i);
                                return;
                            }
                            Constants.getIconst_5(push_int);
                            return;
                        }
                        Constants.getIconst_4(push_int);
                        return;
                    }
                    Constants.getIconst_3(push_int);
                    return;
                }
                Constants.getIconst_2(push_int);
                return;
            } else {
                Constants.getIconst_1(push_int);
                return;
            }
        }
        Constants.getIconst_m1(push_int);
    }
}
