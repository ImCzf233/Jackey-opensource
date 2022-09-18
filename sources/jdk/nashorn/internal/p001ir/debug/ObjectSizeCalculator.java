package jdk.nashorn.internal.p001ir.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* renamed from: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator.class */
public final class ObjectSizeCalculator {
    private final int arrayHeaderSize;
    private final int objectHeaderSize;
    private final int objectPadding;
    private final int referenceSize;
    private final int superclassFieldPadding;
    private final Map<Class<?>, ClassSizeInfo> classSizeInfos = new IdentityHashMap();
    private final Map<Object, Object> alreadyVisited = new IdentityHashMap();
    private final Map<Class<?>, ClassHistogramElement> histogram = new IdentityHashMap();
    private final Deque<Object> pending = new ArrayDeque(16384);
    private long size;
    static Class<?> managementFactory;
    static Class<?> memoryPoolMXBean;
    static Class<?> memoryUsage;
    static Method getMemoryPoolMXBeans;
    static Method getUsage;
    static Method getMax;

    /* renamed from: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator$MemoryLayoutSpecification */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$MemoryLayoutSpecification.class */
    public interface MemoryLayoutSpecification {
        int getArrayHeaderSize();

        int getObjectHeaderSize();

        int getObjectPadding();

        int getReferenceSize();

        int getSuperclassFieldPadding();
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator$CurrentLayout */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$CurrentLayout.class */
    private static class CurrentLayout {
        private static final MemoryLayoutSpecification SPEC = ObjectSizeCalculator.getEffectiveMemoryLayoutSpecification();

        private CurrentLayout() {
        }
    }

    public static long getObjectSize(Object obj) throws UnsupportedOperationException {
        if (obj == null) {
            return 0L;
        }
        return new ObjectSizeCalculator(CurrentLayout.SPEC).calculateObjectSize(obj);
    }

    public ObjectSizeCalculator(MemoryLayoutSpecification memoryLayoutSpecification) {
        Objects.requireNonNull(memoryLayoutSpecification);
        this.arrayHeaderSize = memoryLayoutSpecification.getArrayHeaderSize();
        this.objectHeaderSize = memoryLayoutSpecification.getObjectHeaderSize();
        this.objectPadding = memoryLayoutSpecification.getObjectPadding();
        this.referenceSize = memoryLayoutSpecification.getReferenceSize();
        this.superclassFieldPadding = memoryLayoutSpecification.getSuperclassFieldPadding();
    }

    public synchronized long calculateObjectSize(Object obj) {
        this.histogram.clear();
        Object o = obj;
        while (true) {
            try {
                visit(o);
                if (this.pending.isEmpty()) {
                    long j = this.size;
                    this.alreadyVisited.clear();
                    this.pending.clear();
                    this.size = 0L;
                    return j;
                }
                o = this.pending.removeFirst();
            } catch (Throwable th) {
                this.alreadyVisited.clear();
                this.pending.clear();
                this.size = 0L;
                throw th;
            }
        }
    }

    public List<ClassHistogramElement> getClassHistogram() {
        return new ArrayList(this.histogram.values());
    }

    public ClassSizeInfo getClassSizeInfo(Class<?> clazz) {
        ClassSizeInfo csi = this.classSizeInfos.get(clazz);
        if (csi == null) {
            csi = new ClassSizeInfo(clazz);
            this.classSizeInfos.put(clazz, csi);
        }
        return csi;
    }

    public void visit(Object obj) {
        if (this.alreadyVisited.containsKey(obj)) {
            return;
        }
        Class<?> clazz = obj.getClass();
        if (clazz == ArrayElementsVisitor.class) {
            ((ArrayElementsVisitor) obj).visit(this);
            return;
        }
        this.alreadyVisited.put(obj, obj);
        if (clazz.isArray()) {
            visitArray(obj);
        } else {
            getClassSizeInfo(clazz).visit(obj, this);
        }
    }

    private void visitArray(Object array) {
        Class<?> arrayClass = array.getClass();
        Class<?> componentType = arrayClass.getComponentType();
        int length = Array.getLength(array);
        if (componentType.isPrimitive()) {
            increaseByArraySize(arrayClass, length, getPrimitiveFieldSize(componentType));
            return;
        }
        increaseByArraySize(arrayClass, length, this.referenceSize);
        switch (length) {
            case 0:
                return;
            case 1:
                enqueue(Array.get(array, 0));
                return;
            default:
                enqueue(new ArrayElementsVisitor((Object[]) array));
                return;
        }
    }

    private void increaseByArraySize(Class<?> clazz, int length, long elementSize) {
        increaseSize(clazz, roundTo(this.arrayHeaderSize + (length * elementSize), this.objectPadding));
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator$ArrayElementsVisitor */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$ArrayElementsVisitor.class */
    public static class ArrayElementsVisitor {
        private final Object[] array;

        ArrayElementsVisitor(Object[] array) {
            this.array = array;
        }

        public void visit(ObjectSizeCalculator calc) {
            Object[] objArr;
            for (Object elem : this.array) {
                if (elem != null) {
                    calc.visit(elem);
                }
            }
        }
    }

    void enqueue(Object obj) {
        if (obj != null) {
            this.pending.addLast(obj);
        }
    }

    void increaseSize(Class<?> clazz, long objectSize) {
        ClassHistogramElement he = this.histogram.get(clazz);
        if (he == null) {
            he = new ClassHistogramElement(clazz);
            this.histogram.put(clazz, he);
        }
        he.addInstance(objectSize);
        this.size += objectSize;
    }

    static long roundTo(long x, int multiple) {
        return (((x + multiple) - 1) / multiple) * multiple;
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator$ClassSizeInfo */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$ClassSizeInfo.class */
    public class ClassSizeInfo {
        private final long objectSize;
        private final long fieldsSize;
        private final Field[] referenceFields;

        public ClassSizeInfo(Class<?> clazz) {
            Field[] declaredFields;
            ObjectSizeCalculator.this = r7;
            long newFieldsSize = 0;
            List<Field> newReferenceFields = new LinkedList<>();
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    Class<?> type = f.getType();
                    if (type.isPrimitive()) {
                        newFieldsSize += ObjectSizeCalculator.getPrimitiveFieldSize(type);
                    } else {
                        f.setAccessible(true);
                        newReferenceFields.add(f);
                        newFieldsSize += r7.referenceSize;
                    }
                }
            }
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                ClassSizeInfo superClassInfo = r7.getClassSizeInfo(superClass);
                newFieldsSize += ObjectSizeCalculator.roundTo(superClassInfo.fieldsSize, r7.superclassFieldPadding);
                newReferenceFields.addAll(Arrays.asList(superClassInfo.referenceFields));
            }
            this.fieldsSize = newFieldsSize;
            this.objectSize = ObjectSizeCalculator.roundTo(r7.objectHeaderSize + newFieldsSize, r7.objectPadding);
            this.referenceFields = (Field[]) newReferenceFields.toArray(new Field[newReferenceFields.size()]);
        }

        void visit(Object obj, ObjectSizeCalculator calc) {
            calc.increaseSize(obj.getClass(), this.objectSize);
            enqueueReferencedObjects(obj, calc);
        }

        public void enqueueReferencedObjects(Object obj, ObjectSizeCalculator calc) {
            Field[] fieldArr;
            for (Field f : this.referenceFields) {
                try {
                    calc.enqueue(f.get(obj));
                } catch (IllegalAccessException e) {
                    AssertionError ae = new AssertionError("Unexpected denial of access to " + f);
                    ae.initCause(e);
                    throw ae;
                }
            }
        }
    }

    public static long getPrimitiveFieldSize(Class<?> type) {
        if (type == Boolean.TYPE || type == Byte.TYPE) {
            return 1L;
        }
        if (type == Character.TYPE || type == Short.TYPE) {
            return 2L;
        }
        if (type == Integer.TYPE || type == Float.TYPE) {
            return 4L;
        }
        if (type == Long.TYPE || type == Double.TYPE) {
            return 8L;
        }
        throw new AssertionError("Encountered unexpected primitive type " + type.getName());
    }

    static {
        managementFactory = null;
        memoryPoolMXBean = null;
        memoryUsage = null;
        getMemoryPoolMXBeans = null;
        getUsage = null;
        getMax = null;
        try {
            managementFactory = Class.forName("java.lang.management.ManagementFactory");
            memoryPoolMXBean = Class.forName("java.lang.management.MemoryPoolMXBean");
            memoryUsage = Class.forName("java.lang.management.MemoryUsage");
            getMemoryPoolMXBeans = managementFactory.getMethod("getMemoryPoolMXBeans", new Class[0]);
            getUsage = memoryPoolMXBean.getMethod("getUsage", new Class[0]);
            getMax = memoryUsage.getMethod("getMax", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
        }
    }

    public static MemoryLayoutSpecification getEffectiveMemoryLayoutSpecification() {
        String vmName = System.getProperty("java.vm.name");
        if (vmName == null || !vmName.startsWith("Java HotSpot(TM) ")) {
            throw new UnsupportedOperationException("ObjectSizeCalculator only supported on HotSpot VM");
        }
        String dataModel = System.getProperty("sun.arch.data.model");
        if ("32".equals(dataModel)) {
            return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.1
                @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                public int getArrayHeaderSize() {
                    return 12;
                }

                @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                public int getObjectHeaderSize() {
                    return 8;
                }

                @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                public int getObjectPadding() {
                    return 8;
                }

                @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                public int getReferenceSize() {
                    return 4;
                }

                @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                public int getSuperclassFieldPadding() {
                    return 4;
                }
            };
        }
        if (!"64".equals(dataModel)) {
            throw new UnsupportedOperationException("Unrecognized value '" + dataModel + "' of sun.arch.data.model system property");
        }
        String strVmVersion = System.getProperty("java.vm.version");
        int vmVersion = Integer.parseInt(strVmVersion.substring(0, strVmVersion.indexOf(46)));
        if (vmVersion >= 17) {
            long maxMemory = 0;
            if (getMemoryPoolMXBeans == null) {
                throw new AssertionError("java.lang.management not available in compact 1");
            }
            try {
                List<?> memoryPoolMXBeans = (List) getMemoryPoolMXBeans.invoke(managementFactory, new Object[0]);
                for (Object mp : memoryPoolMXBeans) {
                    Object usage = getUsage.invoke(mp, new Object[0]);
                    Object max = getMax.invoke(usage, new Object[0]);
                    maxMemory += ((Long) max).longValue();
                }
                if (maxMemory < 32212254720L) {
                    return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.2
                        @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                        public int getArrayHeaderSize() {
                            return 16;
                        }

                        @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                        public int getObjectHeaderSize() {
                            return 12;
                        }

                        @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                        public int getObjectPadding() {
                            return 8;
                        }

                        @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                        public int getReferenceSize() {
                            return 4;
                        }

                        @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
                        public int getSuperclassFieldPadding() {
                            return 4;
                        }
                    };
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new AssertionError("java.lang.management not available in compact 1");
            }
        }
        return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.3
            @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
            public int getArrayHeaderSize() {
                return 24;
            }

            @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
            public int getObjectHeaderSize() {
                return 16;
            }

            @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
            public int getObjectPadding() {
                return 8;
            }

            @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
            public int getReferenceSize() {
                return 8;
            }

            @Override // jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator.MemoryLayoutSpecification
            public int getSuperclassFieldPadding() {
                return 8;
            }
        };
    }
}
