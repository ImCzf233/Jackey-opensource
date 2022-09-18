package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: CharDirectionality.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n��\u0010��\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "", "", "Lkotlin/text/CharDirectionality;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/CharDirectionality$Companion$directionalityMap$2.class */
final class CharDirectionality$Companion$directionalityMap$2 extends Lambda implements Functions<Map<Integer, ? extends CharDirectionality>> {
    public static final CharDirectionality$Companion$directionalityMap$2 INSTANCE = new CharDirectionality$Companion$directionalityMap$2();

    CharDirectionality$Companion$directionalityMap$2() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    /* renamed from: invoke */
    public final Map<Integer, ? extends CharDirectionality> invoke2() {
        CharDirectionality[] values = CharDirectionality.values();
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(values.length), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        int i = 0;
        int length = values.length;
        while (i < length) {
            CharDirectionality charDirectionality = values[i];
            i++;
            destination$iv$iv.put(Integer.valueOf(charDirectionality.getValue()), charDirectionality);
        }
        return destination$iv$iv;
    }
}
