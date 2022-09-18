package codes.som.anthony.koffee.labels;

import codes.som.anthony.koffee.insns.InstructionAssembly;
import java.util.LinkedHashMap;
import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.LabelNode;

/* compiled from: LabelRegistry.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020��2\u0006\u0010\u0002\u001a\u00020\u0003J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0086\u0002J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0007H\u0086\u0002J\u000e\u0010\u000f\u001a\u00020��2\u0006\u0010\u0002\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0010"}, m53d2 = {"Lcodes/som/anthony/koffee/labels/LabelRegistry;", "", "insns", "Lcodes/som/anthony/koffee/insns/InstructionAssembly;", "(Lcodes/som/anthony/koffee/insns/InstructionAssembly;)V", "labels", "", "", "Lorg/objectweb/asm/tree/LabelNode;", "copy", PropertyDescriptor.GET, "Lcodes/som/anthony/koffee/labels/KoffeeLabel;", "index", "", "name", "scope", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/labels/LabelRegistry.class */
public final class LabelRegistry {
    private Map<String, LabelNode> labels = new LinkedHashMap();
    private final InstructionAssembly insns;

    public LabelRegistry(@NotNull InstructionAssembly insns) {
        Intrinsics.checkParameterIsNotNull(insns, "insns");
        this.insns = insns;
    }

    @NotNull
    public final LabelRegistry copy(@NotNull InstructionAssembly insns) {
        Intrinsics.checkParameterIsNotNull(insns, "insns");
        LabelRegistry it = new LabelRegistry(insns);
        it.labels = this.labels;
        return it;
    }

    @NotNull
    public final LabelRegistry scope(@NotNull InstructionAssembly insns) {
        Intrinsics.checkParameterIsNotNull(insns, "insns");
        LabelRegistry it = new LabelRegistry(insns);
        it.labels.putAll(this.labels);
        return it;
    }

    @NotNull
    public final KoffeeLabel get(int index) {
        return get("label_" + index);
    }

    @NotNull
    public final KoffeeLabel get(@NotNull String name) {
        LabelNode labelNode;
        Intrinsics.checkParameterIsNotNull(name, "name");
        InstructionAssembly instructionAssembly = this.insns;
        Map $this$getOrPut$iv = this.labels;
        LabelNode labelNode2 = $this$getOrPut$iv.get(name);
        if (labelNode2 == null) {
            LabelNode labelNode3 = new LabelNode();
            $this$getOrPut$iv.put(name, labelNode3);
            labelNode = labelNode3;
        } else {
            labelNode = labelNode2;
        }
        return new KoffeeLabel(instructionAssembly, labelNode);
    }
}
