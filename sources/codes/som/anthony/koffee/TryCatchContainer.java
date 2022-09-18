package codes.som.anthony.koffee;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.TryCatchBlockNode;

/* compiled from: TryCatchContainer.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��\u0016\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018��2\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lcodes/som/anthony/koffee/TryCatchContainer;", "", "tryCatchBlocks", "", "Lorg/objectweb/asm/tree/TryCatchBlockNode;", "getTryCatchBlocks", "()Ljava/util/List;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/TryCatchContainer.class */
public interface TryCatchContainer {
    @NotNull
    List<TryCatchBlockNode> getTryCatchBlocks();
}
