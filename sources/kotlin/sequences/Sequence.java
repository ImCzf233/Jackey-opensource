package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequence.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n��\n\u0002\u0010(\n��\bf\u0018��*\u0006\b��\u0010\u0001 \u00012\u00020\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028��0\u0004H¦\u0002¨\u0006\u0005"}, m53d2 = {"Lkotlin/sequences/Sequence;", "T", "", "iterator", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/Sequence.class */
public interface Sequence<T> {
    @NotNull
    Iterator<T> iterator();
}
