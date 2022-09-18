package kotlin;

import kotlin.internal.InlineOnly;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010\b\n\u0002\u0010��\n��\u001a\u000f\u0010��\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"}, m53d2 = {"hashCode", "", "", "kotlin-stdlib"})
/* renamed from: kotlin.HashCodeKt */
/* loaded from: Jackey Client b2.jar:kotlin/HashCodeKt.class */
public final class HashCode {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final int hashCode(Object $this$hashCode) {
        if ($this$hashCode == null) {
            return 0;
        }
        return $this$hashCode.hashCode();
    }
}
