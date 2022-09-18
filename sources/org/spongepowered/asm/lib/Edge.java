package org.spongepowered.asm.lib;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/Edge.class */
public class Edge {
    static final int NORMAL = 0;
    static final int EXCEPTION = Integer.MAX_VALUE;
    int info;
    Label successor;
    Edge next;
}
