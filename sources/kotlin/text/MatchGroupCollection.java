package kotlin.text;

import java.util.Collection;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatchResult.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\bf\u0018��2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001J\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002¨\u0006\u0006"}, m53d2 = {"Lkotlin/text/MatchGroupCollection;", "", "Lkotlin/text/MatchGroup;", PropertyDescriptor.GET, "index", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/text/MatchGroupCollection.class */
public interface MatchGroupCollection extends Collection<MatchGroup>, KMarkers {
    @Nullable
    MatchGroup get(int i);
}
