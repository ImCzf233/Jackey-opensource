package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/TableSwitchInsnNode.class */
public class TableSwitchInsnNode extends AbstractInsnNode {
    public int min;
    public int max;
    public LabelNode dflt;
    public List<LabelNode> labels = new ArrayList();

    public TableSwitchInsnNode(int min, int max, LabelNode dflt, LabelNode... labels) {
        super(170);
        this.min = min;
        this.max = max;
        this.dflt = dflt;
        if (labels != null) {
            this.labels.addAll(Arrays.asList(labels));
        }
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 11;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        Label[] labels = new Label[this.labels.size()];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = this.labels.get(i).getLabel();
        }
        mv.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), labels);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        return new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, labels), clone(this.labels, labels)).cloneAnnotations(this);
    }
}
