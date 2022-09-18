package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntComparators.class */
public final class IntComparators {
    public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private IntComparators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntComparators$NaturalImplicitComparator.class */
    protected static class NaturalImplicitComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator
        public final int compare(int a, int b) {
            return Integer.compare(a, b);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator, java.util.Comparator
        /* renamed from: reversed */
        public Comparator<Integer> reversed2() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return IntComparators.NATURAL_COMPARATOR;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntComparators$OppositeImplicitComparator.class */
    protected static class OppositeImplicitComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator
        public final int compare(int a, int b) {
            return -Integer.compare(a, b);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator, java.util.Comparator
        /* renamed from: reversed */
        public Comparator<Integer> reversed2() {
            return IntComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntComparators$OppositeComparator.class */
    public static class OppositeComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;
        final IntComparator comparator;

        protected OppositeComparator(IntComparator c) {
            this.comparator = c;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator
        public final int compare(int a, int b) {
            return this.comparator.compare(b, a);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator, java.util.Comparator
        /* renamed from: reversed */
        public final Comparator<Integer> reversed2() {
            return this.comparator;
        }
    }

    public static IntComparator oppositeComparator(IntComparator c) {
        if (c instanceof OppositeComparator) {
            return ((OppositeComparator) c).comparator;
        }
        return new OppositeComparator(c);
    }

    public static IntComparator asIntComparator(final Comparator<? super Integer> c) {
        if (c == null || (c instanceof IntComparator)) {
            return (IntComparator) c;
        }
        return new IntComparator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntComparators.1
            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator
            public int compare(int x, int y) {
                return c.compare(Integer.valueOf(x), Integer.valueOf(y));
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntComparator
            public int compare(Integer x, Integer y) {
                return c.compare(x, y);
            }
        };
    }
}
