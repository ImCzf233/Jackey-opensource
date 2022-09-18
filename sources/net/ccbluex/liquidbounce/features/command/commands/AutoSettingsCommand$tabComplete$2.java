package net.ccbluex.liquidbounce.features.command.commands;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoSettingsCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010 \n\u0002\u0010\u000e\n��\u0010��\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "", "it", "", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand$tabComplete$2.class */
final class AutoSettingsCommand$tabComplete$2 extends Lambda implements Function1<List<? extends String>, Unit> {
    public static final AutoSettingsCommand$tabComplete$2 INSTANCE = new AutoSettingsCommand$tabComplete$2();

    AutoSettingsCommand$tabComplete$2() {
        super(1);
    }

    /* renamed from: invoke */
    public final void invoke2(@NotNull List<String> it) {
        Intrinsics.checkNotNullParameter(it, "it");
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(List<? extends String> list) {
        invoke2((List<String>) list);
        return Unit.INSTANCE;
    }
}
