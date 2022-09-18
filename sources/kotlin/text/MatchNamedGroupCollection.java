package kotlin.text;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatchResult.kt */
@SinceKotlin(version = "1.1")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\bg\u0018��2\u00020\u0001J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002¨\u0006\u0006"}, m53d2 = {"Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/text/MatchGroupCollection;", PropertyDescriptor.GET, "Lkotlin/text/MatchGroup;", "name", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/text/MatchNamedGroupCollection.class */
public interface MatchNamedGroupCollection extends MatchGroupCollection {
    @Nullable
    MatchGroup get(@NotNull String str);
}
