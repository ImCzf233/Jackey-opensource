package jdk.nashorn.internal.objects;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyListeners;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.log4j.spi.Configurator;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDebug.class */
public final class NativeDebug extends ScriptObject {
    private static PropertyMap $nasgenmap$;
    private static final String EVENT_QUEUE = "__eventQueue__";
    private static final String EVENT_QUEUE_CAPACITY = "__eventQueueCapacity__";

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object getArrayDataClass;
        private Object getArrayData;
        private Object getContext;
        private Object map;
        private Object identical;
        private Object equalWithoutType;
        private Object diffPropertyMaps;
        private Object getClass;
        private Object equals;
        private Object toJavaString;
        private Object toIdentString;
        private Object getListenerCount;
        private Object dumpCounters;
        private Object getEventQueueCapacity;
        private Object setEventQueueCapacity;
        private Object addRuntimeEvent;
        private Object expandEventQueueCapacity;
        private Object clearRuntimeEvents;
        private Object removeRuntimeEvent;
        private Object getRuntimeEvents;
        private Object getLastRuntimeEvent;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$getArrayDataClass() {
            return this.getArrayDataClass;
        }

        public void S$getArrayDataClass(Object obj) {
            this.getArrayDataClass = obj;
        }

        public Object G$getArrayData() {
            return this.getArrayData;
        }

        public void S$getArrayData(Object obj) {
            this.getArrayData = obj;
        }

        public Object G$getContext() {
            return this.getContext;
        }

        public void S$getContext(Object obj) {
            this.getContext = obj;
        }

        public Object G$map() {
            return this.map;
        }

        public void S$map(Object obj) {
            this.map = obj;
        }

        public Object G$identical() {
            return this.identical;
        }

        public void S$identical(Object obj) {
            this.identical = obj;
        }

        public Object G$equalWithoutType() {
            return this.equalWithoutType;
        }

        public void S$equalWithoutType(Object obj) {
            this.equalWithoutType = obj;
        }

        public Object G$diffPropertyMaps() {
            return this.diffPropertyMaps;
        }

        public void S$diffPropertyMaps(Object obj) {
            this.diffPropertyMaps = obj;
        }

        public Object G$getClass() {
            return this.getClass;
        }

        public void S$getClass(Object obj) {
            this.getClass = obj;
        }

        public Object G$equals() {
            return this.equals;
        }

        public void S$equals(Object obj) {
            this.equals = obj;
        }

        public Object G$toJavaString() {
            return this.toJavaString;
        }

        public void S$toJavaString(Object obj) {
            this.toJavaString = obj;
        }

        public Object G$toIdentString() {
            return this.toIdentString;
        }

        public void S$toIdentString(Object obj) {
            this.toIdentString = obj;
        }

        public Object G$getListenerCount() {
            return this.getListenerCount;
        }

        public void S$getListenerCount(Object obj) {
            this.getListenerCount = obj;
        }

        public Object G$dumpCounters() {
            return this.dumpCounters;
        }

        public void S$dumpCounters(Object obj) {
            this.dumpCounters = obj;
        }

        public Object G$getEventQueueCapacity() {
            return this.getEventQueueCapacity;
        }

        public void S$getEventQueueCapacity(Object obj) {
            this.getEventQueueCapacity = obj;
        }

        public Object G$setEventQueueCapacity() {
            return this.setEventQueueCapacity;
        }

        public void S$setEventQueueCapacity(Object obj) {
            this.setEventQueueCapacity = obj;
        }

        public Object G$addRuntimeEvent() {
            return this.addRuntimeEvent;
        }

        public void S$addRuntimeEvent(Object obj) {
            this.addRuntimeEvent = obj;
        }

        public Object G$expandEventQueueCapacity() {
            return this.expandEventQueueCapacity;
        }

        public void S$expandEventQueueCapacity(Object obj) {
            this.expandEventQueueCapacity = obj;
        }

        public Object G$clearRuntimeEvents() {
            return this.clearRuntimeEvents;
        }

        public void S$clearRuntimeEvents(Object obj) {
            this.clearRuntimeEvents = obj;
        }

        public Object G$removeRuntimeEvent() {
            return this.removeRuntimeEvent;
        }

        public void S$removeRuntimeEvent(Object obj) {
            this.removeRuntimeEvent = obj;
        }

        public Object G$getRuntimeEvents() {
            return this.getRuntimeEvents;
        }

        public void S$getRuntimeEvents(Object obj) {
            this.getRuntimeEvents = obj;
        }

        public Object G$getLastRuntimeEvent() {
            return this.getLastRuntimeEvent;
        }

        public void S$getLastRuntimeEvent(Object obj) {
            this.getLastRuntimeEvent = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:110)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Debug";
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeDebug() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Debug";
    }

    public static Object getArrayDataClass(Object self, Object obj) {
        try {
            return ((ScriptObject) obj).getArray().getClass();
        } catch (ClassCastException e) {
            return ScriptRuntime.UNDEFINED;
        }
    }

    public static Object getArrayData(Object self, Object obj) {
        try {
            return ((ScriptObject) obj).getArray();
        } catch (ClassCastException e) {
            return ScriptRuntime.UNDEFINED;
        }
    }

    public static Object getContext(Object self) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(Context.NASHORN_GET_CONTEXT));
        }
        return Global.getThisContext();
    }

    public static Object map(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getMap();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static boolean identical(Object self, Object obj1, Object obj2) {
        return obj1 == obj2;
    }

    public static Object equalWithoutType(Object self, Object m1, Object m2) {
        return Boolean.valueOf(((PropertyMap) m1).equalsWithoutType((PropertyMap) m2));
    }

    public static Object diffPropertyMaps(Object self, Object m1, Object m2) {
        return PropertyMap.diff((PropertyMap) m1, (PropertyMap) m2);
    }

    public static Object getClass(Object self, Object obj) {
        if (obj != null) {
            return obj.getClass();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static boolean equals(Object self, Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    public static String toJavaString(Object self, Object obj) {
        return Objects.toString(obj);
    }

    public static String toIdentString(Object self, Object obj) {
        if (obj == null) {
            return Configurator.NULL;
        }
        int hash = System.identityHashCode(obj);
        return obj.getClass() + "@" + Integer.toHexString(hash);
    }

    public static int getListenerCount(Object self, Object obj) {
        if (obj instanceof ScriptObject) {
            return PropertyListeners.getListenerCount((ScriptObject) obj);
        }
        return 0;
    }

    public static Object dumpCounters(Object self) {
        PrintWriter out = Context.getCurrentErr();
        out.println("ScriptObject count " + ScriptObject.getCount());
        out.println("Scope count " + Scope.getScopeCount());
        out.println("ScriptObject listeners added " + PropertyListeners.getListenersAdded());
        out.println("ScriptObject listeners removed " + PropertyListeners.getListenersRemoved());
        out.println("ScriptFunction constructor calls " + ScriptFunction.getConstructorCount());
        out.println("ScriptFunction invokes " + ScriptFunction.getInvokes());
        out.println("ScriptFunction allocations " + ScriptFunction.getAllocations());
        out.println("PropertyMap count " + PropertyMap.getCount());
        out.println("PropertyMap cloned " + PropertyMap.getClonedCount());
        out.println("PropertyMap history hit " + PropertyMap.getHistoryHit());
        out.println("PropertyMap proto invalidations " + PropertyMap.getProtoInvalidations());
        out.println("PropertyMap proto history hit " + PropertyMap.getProtoHistoryHit());
        out.println("PropertyMap setProtoNewMapCount " + PropertyMap.getSetProtoNewMapCount());
        out.println("Callsite count " + LinkerCallSite.getCount());
        out.println("Callsite misses " + LinkerCallSite.getMissCount());
        out.println("Callsite misses by site at " + LinkerCallSite.getMissSamplingPercentage() + "%");
        LinkerCallSite.getMissCounts(out);
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getEventQueueCapacity(Object self) {
        Integer cap;
        ScriptObject sobj = (ScriptObject) self;
        if (sobj.has(EVENT_QUEUE_CAPACITY)) {
            cap = Integer.valueOf(JSType.toInt32(sobj.get(EVENT_QUEUE_CAPACITY)));
        } else {
            Integer valueOf = Integer.valueOf(RuntimeEvent.RUNTIME_EVENT_QUEUE_SIZE);
            cap = valueOf;
            setEventQueueCapacity(self, valueOf);
        }
        return cap;
    }

    public static void setEventQueueCapacity(Object self, Object newCapacity) {
        ((ScriptObject) self).set(EVENT_QUEUE_CAPACITY, newCapacity, 2);
    }

    public static void addRuntimeEvent(Object self, Object event) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        int cap = ((Integer) getEventQueueCapacity(self)).intValue();
        while (q.size() >= cap) {
            q.removeFirst();
        }
        q.addLast(getEvent(event));
    }

    public static void expandEventQueueCapacity(Object self, Object newCapacity) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        int nc = JSType.toInt32(newCapacity);
        while (q.size() > nc) {
            q.removeFirst();
        }
        setEventQueueCapacity(self, Integer.valueOf(nc));
    }

    public static void clearRuntimeEvents(Object self) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        q.clear();
    }

    public static void removeRuntimeEvent(Object self, Object event) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        RuntimeEvent<?> re = getEvent(event);
        if (!q.remove(re)) {
            throw new IllegalStateException("runtime event " + re + " was not in event queue");
        }
    }

    public static Object getRuntimeEvents(Object self) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        return q.toArray(new RuntimeEvent[q.size()]);
    }

    public static Object getLastRuntimeEvent(Object self) {
        LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
        if (q.isEmpty()) {
            return null;
        }
        return q.getLast();
    }

    private static LinkedList<RuntimeEvent<?>> getEventQueue(Object self) {
        LinkedList<RuntimeEvent<?>> q;
        ScriptObject sobj = (ScriptObject) self;
        if (sobj.has(EVENT_QUEUE)) {
            q = (LinkedList) ((ScriptObject) self).get(EVENT_QUEUE);
        } else {
            LinkedList<RuntimeEvent<?>> linkedList = new LinkedList<>();
            q = linkedList;
            ((ScriptObject) self).set(EVENT_QUEUE, linkedList, 2);
        }
        return q;
    }

    private static RuntimeEvent<?> getEvent(Object event) {
        return (RuntimeEvent) event;
    }
}
