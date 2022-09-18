package jdk.nashorn.internal.p001ir;

import java.io.Serializable;
import java.util.List;
import jdk.nashorn.internal.codegen.CompileUnit;

/* renamed from: jdk.nashorn.internal.ir.Splittable */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Splittable.class */
public interface Splittable {
    List<SplitRange> getSplitRanges();

    /* renamed from: jdk.nashorn.internal.ir.Splittable$SplitRange */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Splittable$SplitRange.class */
    public static final class SplitRange implements CompileUnitHolder, Serializable {
        private static final long serialVersionUID = 1;
        private final CompileUnit compileUnit;
        private final int low;
        private final int high;

        public SplitRange(CompileUnit compileUnit, int low, int high) {
            this.compileUnit = compileUnit;
            this.low = low;
            this.high = high;
        }

        public int getHigh() {
            return this.high;
        }

        public int getLow() {
            return this.low;
        }

        @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
        public CompileUnit getCompileUnit() {
            return this.compileUnit;
        }
    }
}
