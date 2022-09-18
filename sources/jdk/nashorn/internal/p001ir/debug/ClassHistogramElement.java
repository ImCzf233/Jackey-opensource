package jdk.nashorn.internal.p001ir.debug;

import java.util.Comparator;

/* renamed from: jdk.nashorn.internal.ir.debug.ClassHistogramElement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ClassHistogramElement.class */
public final class ClassHistogramElement {
    public static final Comparator<ClassHistogramElement> COMPARE_INSTANCES = new Comparator<ClassHistogramElement>() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.1
        public int compare(ClassHistogramElement o1, ClassHistogramElement o2) {
            return (int) Math.abs(o1.instances - o2.instances);
        }
    };
    public static final Comparator<ClassHistogramElement> COMPARE_BYTES = new Comparator<ClassHistogramElement>() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.2
        public int compare(ClassHistogramElement o1, ClassHistogramElement o2) {
            return (int) Math.abs(o1.bytes - o2.bytes);
        }
    };
    public static final Comparator<ClassHistogramElement> COMPARE_CLASSNAMES = new Comparator<ClassHistogramElement>() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.3
        public int compare(ClassHistogramElement o1, ClassHistogramElement o2) {
            return o1.clazz.getCanonicalName().compareTo(o2.clazz.getCanonicalName());
        }
    };
    private final Class<?> clazz;
    private long instances;
    private long bytes;

    public ClassHistogramElement(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void addInstance(long sizeInBytes) {
        this.instances++;
        this.bytes += sizeInBytes;
    }

    public long getBytes() {
        return this.bytes;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public long getInstances() {
        return this.instances;
    }

    public String toString() {
        return "ClassHistogramElement[class=" + this.clazz.getCanonicalName() + ", instances=" + this.instances + ", bytes=" + this.bytes + "]";
    }
}
