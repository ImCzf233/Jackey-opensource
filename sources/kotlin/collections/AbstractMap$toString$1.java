package kotlin.collections;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractMap.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010&\n��\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u0002\"\u0006\b\u0001\u0010\u0003 \u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005H\n¢\u0006\u0002\b\u0006"}, m53d2 = {"<anonymous>", "", "K", "V", "it", "", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/collections/AbstractMap$toString$1.class */
final class AbstractMap$toString$1 extends Lambda implements Function1<Map.Entry<? extends K, ? extends V>, CharSequence> {
    final /* synthetic */ AbstractMap<K, V> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AbstractMap$toString$1(AbstractMap<K, ? extends V> abstractMap) {
        super(1);
        this.this$0 = abstractMap;
    }

    @NotNull
    public final CharSequence invoke(@NotNull Map.Entry<? extends K, ? extends V> it) {
        String abstractMap;
        Intrinsics.checkNotNullParameter(it, "it");
        abstractMap = this.this$0.toString((Map.Entry) it);
        return abstractMap;
    }
}
