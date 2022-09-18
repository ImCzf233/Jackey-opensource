package net.ccbluex.liquidbounce.script.api;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScriptModule.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 3, m54d1 = {"��\u0014\n��\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\u0010��\u001a&\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001j\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0003`\u0004H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "Ljava/util/LinkedHashMap;", "", "Lnet/ccbluex/liquidbounce/value/Value;", "Lkotlin/collections/LinkedHashMap;", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/ScriptModule$settings$2.class */
public final class ScriptModule$settings$2 extends Lambda implements Functions<LinkedHashMap<String, Value<?>>> {
    final /* synthetic */ ScriptModule this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptModule$settings$2(ScriptModule scriptModule) {
        super(0);
        this.this$0 = scriptModule;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final LinkedHashMap<String, Value<?>> invoke() {
        LinkedHashMap<String, Value<?>> linkedHashMap;
        linkedHashMap = this.this$0._values;
        return linkedHashMap;
    }
}
