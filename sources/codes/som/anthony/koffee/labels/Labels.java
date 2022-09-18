package codes.som.anthony.koffee.labels;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.LabelNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0014\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010��\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004*\n\u0010\u0005\"\u00020\u00032\u00020\u0003¨\u0006\u0006"}, m53d2 = {"coerceLabel", "Lorg/objectweb/asm/tree/LabelNode;", "value", "", "Lcodes/som/anthony/koffee/labels/LabelLike;", "LabelLike", "koffee"})
/* renamed from: codes.som.anthony.koffee.labels.LabelsKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/labels/LabelsKt.class */
public final class Labels {
    @NotNull
    public static final LabelNode coerceLabel(@NotNull Object value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        if (value instanceof LabelNode) {
            return (LabelNode) value;
        }
        if (value instanceof Label) {
            return new LabelNode((Label) value);
        }
        if (!(value instanceof KoffeeLabel)) {
            throw new IllegalStateException("Non label-like object passed to coerceLabel()".toString());
        }
        return ((KoffeeLabel) value).getLabel$koffee();
    }
}
