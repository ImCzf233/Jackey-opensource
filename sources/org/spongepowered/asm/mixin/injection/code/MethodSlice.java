package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.Deque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice.class */
public final class MethodSlice {
    private static final Logger logger = LogManager.getLogger("mixin");
    private final ISliceContext owner;

    /* renamed from: id */
    private final String f437id;
    private final InjectionPoint from;

    /* renamed from: to */
    private final InjectionPoint f438to;
    private final String name;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice$InsnListSlice.class */
    public static final class InsnListSlice extends ReadOnlyInsnList {
        private final int start;
        private final int end;

        /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice$InsnListSlice$SliceIterator.class */
        public static class SliceIterator implements ListIterator<AbstractInsnNode> {
            private final ListIterator<AbstractInsnNode> iter;
            private int start;
            private int end;
            private int index;

            public SliceIterator(ListIterator<AbstractInsnNode> iter, int start, int end, int index) {
                this.iter = iter;
                this.start = start;
                this.end = end;
                this.index = index;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public boolean hasNext() {
                return this.index <= this.end && this.iter.hasNext();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public AbstractInsnNode next() {
                if (this.index > this.end) {
                    throw new NoSuchElementException();
                }
                this.index++;
                return this.iter.next();
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return this.index > this.start;
            }

            @Override // java.util.ListIterator
            public AbstractInsnNode previous() {
                if (this.index <= this.start) {
                    throw new NoSuchElementException();
                }
                this.index--;
                return this.iter.previous();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.index - this.start;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return (this.index - this.start) - 1;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove insn from slice");
            }

            public void set(AbstractInsnNode e) {
                throw new UnsupportedOperationException("Cannot set insn using slice");
            }

            public void add(AbstractInsnNode e) {
                throw new UnsupportedOperationException("Cannot add insn using slice");
            }
        }

        protected InsnListSlice(InsnList inner, int start, int end) {
            super(inner);
            this.start = start;
            this.end = end;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public ListIterator<AbstractInsnNode> iterator() {
            return iterator(0);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public ListIterator<AbstractInsnNode> iterator(int index) {
            return new SliceIterator(super.iterator(this.start + index), this.start, this.end, this.start + index);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public AbstractInsnNode[] toArray() {
            AbstractInsnNode[] all = super.toArray();
            AbstractInsnNode[] subset = new AbstractInsnNode[size()];
            System.arraycopy(all, this.start, subset, 0, subset.length);
            return subset;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public int size() {
            return (this.end - this.start) + 1;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public AbstractInsnNode getFirst() {
            return super.get(this.start);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public AbstractInsnNode getLast() {
            return super.get(this.end);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public AbstractInsnNode get(int index) {
            return super.get(this.start + index);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public boolean contains(AbstractInsnNode insn) {
            AbstractInsnNode[] array;
            for (AbstractInsnNode node : toArray()) {
                if (node == insn) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList, org.spongepowered.asm.lib.tree.InsnList
        public int indexOf(AbstractInsnNode insn) {
            int index = super.indexOf(insn);
            if (index < this.start || index > this.end) {
                return -1;
            }
            return index - this.start;
        }

        public int realIndexOf(AbstractInsnNode insn) {
            return super.indexOf(insn);
        }
    }

    private MethodSlice(ISliceContext owner, String id, InjectionPoint from, InjectionPoint to) {
        if (from == null && to == null) {
            throw new InvalidSliceException(owner, String.format("%s is redundant. No 'from' or 'to' value specified", this));
        }
        this.owner = owner;
        this.f437id = Strings.nullToEmpty(id);
        this.from = from;
        this.f438to = to;
        this.name = getSliceName(id);
    }

    public String getId() {
        return this.f437id;
    }

    public ReadOnlyInsnList getSlice(MethodNode method) {
        int max = method.instructions.size() - 1;
        int start = find(method, this.from, 0, 0, this.name + "(from)");
        int end = find(method, this.f438to, max, start, this.name + "(to)");
        if (start > end) {
            throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", describe(), Integer.valueOf(start), Integer.valueOf(end)));
        }
        if (start < 0 || end < 0 || start > max || end > max) {
            throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + start + " end=" + end + " lim=" + max);
        }
        if (start == 0 && end == max) {
            return new ReadOnlyInsnList(method.instructions);
        }
        return new InsnListSlice(method.instructions, start, end);
    }

    private int find(MethodNode method, InjectionPoint injectionPoint, int defaultValue, int failValue, String description) {
        if (injectionPoint == null) {
            return defaultValue;
        }
        Deque<AbstractInsnNode> nodes = new LinkedList<>();
        ReadOnlyInsnList insns = new ReadOnlyInsnList(method.instructions);
        boolean result = injectionPoint.find(method.desc, insns, nodes);
        InjectionPoint.Selector select = injectionPoint.getSelector();
        if (nodes.size() != 1 && select == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", describe(description), Integer.valueOf(nodes.size())));
        }
        if (result) {
            return method.instructions.indexOf(select == InjectionPoint.Selector.FIRST ? nodes.getFirst() : nodes.getLast());
        }
        if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            logger.warn("{} did not match any instructions", new Object[]{describe(description)});
        }
        return failValue;
    }

    public String toString() {
        return describe();
    }

    private String describe() {
        return describe(this.name);
    }

    private String describe(String description) {
        return describeSlice(description, this.owner);
    }

    private static String describeSlice(String description, ISliceContext owner) {
        String annotation = Bytecode.getSimpleName(owner.getAnnotation());
        MethodNode method = owner.getMethod();
        return String.format("%s->%s(%s)::%s%s", owner.getContext(), annotation, description, method.name, method.desc);
    }

    private static String getSliceName(String id) {
        return String.format("@Slice[%s]", Strings.nullToEmpty(id));
    }

    public static MethodSlice parse(ISliceContext owner, Slice slice) {
        String id = slice.m16id();
        AbstractC1790At from = slice.from();
        AbstractC1790At to = slice.m15to();
        InjectionPoint fromPoint = from != null ? InjectionPoint.parse(owner, from) : null;
        InjectionPoint toPoint = to != null ? InjectionPoint.parse(owner, to) : null;
        return new MethodSlice(owner, id, fromPoint, toPoint);
    }

    public static MethodSlice parse(ISliceContext info, AnnotationNode node) {
        String id = (String) Annotations.getValue(node, "id");
        AnnotationNode from = (AnnotationNode) Annotations.getValue(node, "from");
        AnnotationNode to = (AnnotationNode) Annotations.getValue(node, "to");
        InjectionPoint fromPoint = from != null ? InjectionPoint.parse(info, from) : null;
        InjectionPoint toPoint = to != null ? InjectionPoint.parse(info, to) : null;
        return new MethodSlice(info, id, fromPoint, toPoint);
    }
}
