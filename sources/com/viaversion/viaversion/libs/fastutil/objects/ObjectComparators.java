package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectComparators.class */
public final class ObjectComparators {
    public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ObjectComparators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectComparators$NaturalImplicitComparator.class */
    protected static class NaturalImplicitComparator implements Comparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // java.util.Comparator
        public final int compare(Object a, Object b) {
            return ((Comparable) a).compareTo(b);
        }

        @Override // java.util.Comparator
        public Comparator reversed() {
            return ObjectComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return ObjectComparators.NATURAL_COMPARATOR;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectComparators$OppositeImplicitComparator.class */
    protected static class OppositeImplicitComparator implements Comparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // java.util.Comparator
        public final int compare(Object a, Object b) {
            return ((Comparable) b).compareTo(a);
        }

        @Override // java.util.Comparator
        public Comparator reversed() {
            return ObjectComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return ObjectComparators.OPPOSITE_COMPARATOR;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectComparators$OppositeComparator.class */
    protected static class OppositeComparator<K> implements Comparator<K>, Serializable {
        private static final long serialVersionUID = 1;
        final Comparator<K> comparator;

        protected OppositeComparator(Comparator<K> c) {
            this.comparator = c;
        }

        @Override // java.util.Comparator
        public final int compare(K a, K b) {
            return this.comparator.compare(b, a);
        }

        @Override // java.util.Comparator
        public final Comparator<K> reversed() {
            return this.comparator;
        }
    }

    public static <K> Comparator<K> oppositeComparator(Comparator<K> c) {
        if (c instanceof OppositeComparator) {
            return ((OppositeComparator) c).comparator;
        }
        return new OppositeComparator(c);
    }

    public static <K> Comparator<K> asObjectComparator(Comparator<K> c) {
        return c;
    }
}
