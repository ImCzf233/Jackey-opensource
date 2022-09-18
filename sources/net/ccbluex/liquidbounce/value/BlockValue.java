package net.ccbluex.liquidbounce.value;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/BlockValue;", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "name", "", "value", "", "(Ljava/lang/String;I)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;ILkotlin/jvm/functions/Function0;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/BlockValue.class */
public final class BlockValue extends IntegerValue {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.BlockValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/BlockValue$1.class */
    public static final class C16941 extends Lambda implements Functions<Boolean> {
        public static final C16941 INSTANCE = new C16941();

        C16941() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlockValue(@NotNull String name, int value, @NotNull Functions<Boolean> displayable) {
        super(name, value, 1, 197, displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BlockValue(@NotNull String name, int value) {
        this(name, value, C16941.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
    }
}
