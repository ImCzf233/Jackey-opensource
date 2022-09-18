package jdk.nashorn.internal.lookup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/lookup/MethodHandleFactory.class */
public final class MethodHandleFactory {
    private static final MethodHandles.Lookup PUBLIC_LOOKUP;
    private static final MethodHandles.Lookup LOOKUP;
    private static final Level TRACE_LEVEL;
    private static final MethodHandleFunctionality FUNC;
    private static final boolean PRINT_STACKTRACE;
    private static final MethodHandle TRACE;
    private static final MethodHandle TRACE_RETURN;
    private static final MethodHandle TRACE_RETURN_VOID;
    private static final String VOID_TAG = "[VOID]";
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !MethodHandleFactory.class.desiredAssertionStatus();
        PUBLIC_LOOKUP = MethodHandles.publicLookup();
        LOOKUP = MethodHandles.lookup();
        TRACE_LEVEL = Level.INFO;
        FUNC = new StandardMethodHandleFunctionality();
        PRINT_STACKTRACE = Options.getBooleanProperty("nashorn.methodhandles.debug.stacktrace");
        TRACE = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceArgs", MethodType.methodType(Void.TYPE, DebugLogger.class, String.class, Integer.TYPE, Object[].class));
        TRACE_RETURN = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturn", MethodType.methodType(Object.class, DebugLogger.class, Object.class));
        TRACE_RETURN_VOID = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturnVoid", MethodType.methodType(Void.TYPE, DebugLogger.class));
    }

    private MethodHandleFactory() {
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/lookup/MethodHandleFactory$LookupException.class */
    public static class LookupException extends RuntimeException {
        public LookupException(Exception e) {
            super(e);
        }
    }

    public static String stripName(Object obj) {
        if (obj == null) {
            return Configurator.NULL;
        }
        if (obj instanceof Class) {
            return ((Class) obj).getSimpleName();
        }
        return obj.toString();
    }

    public static MethodHandleFunctionality getFunctionality() {
        return FUNC;
    }

    private static void err(String str) {
        Context.getContext().getErr().println(str);
    }

    static Object traceReturn(DebugLogger logger, Object value) {
        String str;
        StringBuilder append = new StringBuilder().append("    return");
        if (VOID_TAG.equals(value)) {
            str = ";";
        } else {
            str = " " + stripName(value) + "; // [type=" + (value == null ? "null]" : stripName(value.getClass()) + ']');
        }
        String str2 = append.append(str).toString();
        if (logger == null) {
            err(str2);
        } else if (logger.isEnabled()) {
            logger.log(TRACE_LEVEL, str2);
        }
        return value;
    }

    static void traceReturnVoid(DebugLogger logger) {
        traceReturn(logger, VOID_TAG);
    }

    static void traceArgs(DebugLogger logger, String tag, int paramStart, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(tag);
        for (int i = paramStart; i < args.length; i++) {
            if (i == paramStart) {
                sb.append(" => args: ");
            }
            sb.append('\'').append(stripName(argString(args[i]))).append('\'').append(' ').append('[').append("type=").append(args[i] == null ? Configurator.NULL : stripName(args[i].getClass())).append(']');
            if (i + 1 < args.length) {
                sb.append(", ");
            }
        }
        if (logger == null) {
            err(sb.toString());
        } else {
            logger.log(TRACE_LEVEL, sb);
        }
        stacktrace(logger);
    }

    public static void stacktrace(DebugLogger logger) {
        if (!PRINT_STACKTRACE) {
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        new Throwable().printStackTrace(ps);
        String st = baos.toString();
        if (logger == null) {
            err(st);
        } else {
            logger.log(TRACE_LEVEL, st);
        }
    }

    private static String argString(Object arg) {
        Object[] objArr;
        if (arg == null) {
            return Configurator.NULL;
        }
        if (arg.getClass().isArray()) {
            List<Object> list = new ArrayList<>();
            for (Object elem : (Object[]) arg) {
                list.add('\'' + argString(elem) + '\'');
            }
            return list.toString();
        } else if (arg instanceof ScriptObject) {
            return arg.toString() + " (map=" + Debug.m67id(((ScriptObject) arg).getMap()) + ')';
        } else {
            return arg.toString();
        }
    }

    public static MethodHandle addDebugPrintout(MethodHandle mh, Object tag) {
        return addDebugPrintout(null, Level.OFF, mh, 0, true, tag);
    }

    public static MethodHandle addDebugPrintout(DebugLogger logger, Level level, MethodHandle mh, Object tag) {
        return addDebugPrintout(logger, level, mh, 0, true, tag);
    }

    public static MethodHandle addDebugPrintout(MethodHandle mh, int paramStart, boolean printReturnValue, Object tag) {
        return addDebugPrintout(null, Level.OFF, mh, paramStart, printReturnValue, tag);
    }

    public static MethodHandle addDebugPrintout(DebugLogger logger, Level level, MethodHandle mh, int paramStart, boolean printReturnValue, Object tag) {
        MethodType type = mh.type();
        if (logger == null || !logger.isLoggable(level)) {
            return mh;
        }
        if (!$assertionsDisabled && TRACE == null) {
            throw new AssertionError();
        }
        MethodHandle trace = MethodHandles.foldArguments(mh, MethodHandles.insertArguments(TRACE, 0, logger, tag, Integer.valueOf(paramStart)).asCollector(Object[].class, type.parameterCount()).asType(type.changeReturnType(Void.TYPE)));
        Class<?> retType = type.returnType();
        if (printReturnValue) {
            if (retType != Void.TYPE) {
                MethodHandle traceReturn = MethodHandles.insertArguments(TRACE_RETURN, 0, logger);
                trace = MethodHandles.filterReturnValue(trace, traceReturn.asType(traceReturn.type().changeParameterType(0, retType).changeReturnType(retType)));
            } else {
                trace = MethodHandles.filterReturnValue(trace, MethodHandles.insertArguments(TRACE_RETURN_VOID, 0, logger));
            }
        }
        return trace;
    }

    @Logger(name = "methodhandles")
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/lookup/MethodHandleFactory$StandardMethodHandleFunctionality.class */
    private static class StandardMethodHandleFunctionality implements MethodHandleFunctionality, Loggable {
        private DebugLogger log = DebugLogger.DISABLED_LOGGER;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // jdk.nashorn.internal.runtime.logging.Loggable
        public DebugLogger initLogger(Context context) {
            DebugLogger logger = context.getLogger(getClass());
            this.log = logger;
            return logger;
        }

        @Override // jdk.nashorn.internal.runtime.logging.Loggable
        public DebugLogger getLogger() {
            return this.log;
        }

        protected static String describe(Object... data) {
            Object[] objArr;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                Object d = data[i];
                if (d == null) {
                    sb.append("<null> ");
                } else if (JSType.isString(d)) {
                    sb.append(d.toString());
                    sb.append(' ');
                } else if (d.getClass().isArray()) {
                    sb.append("[ ");
                    for (Object da : (Object[]) d) {
                        sb.append(describe(da)).append(' ');
                    }
                    sb.append("] ");
                } else {
                    sb.append(d).append('{').append(Integer.toHexString(System.identityHashCode(d))).append('}');
                }
                if (i + 1 < data.length) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }

        public MethodHandle debug(MethodHandle master, String str, Object... args) {
            if (this.log.isEnabled()) {
                if (MethodHandleFactory.PRINT_STACKTRACE) {
                    MethodHandleFactory.stacktrace(this.log);
                }
                return MethodHandleFactory.addDebugPrintout(this.log, Level.INFO, master, Integer.MAX_VALUE, false, str + ' ' + describe(args));
            }
            return master;
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle filterArguments(MethodHandle target, int pos, MethodHandle... filters) {
            MethodHandle mh = MethodHandles.filterArguments(target, pos, filters);
            return debug(mh, "filterArguments", target, Integer.valueOf(pos), filters);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle filterReturnValue(MethodHandle target, MethodHandle filter) {
            MethodHandle mh = MethodHandles.filterReturnValue(target, filter);
            return debug(mh, "filterReturnValue", target, filter);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle guardWithTest(MethodHandle test, MethodHandle target, MethodHandle fallback) {
            MethodHandle mh = MethodHandles.guardWithTest(test, target, fallback);
            return debug(mh, "guardWithTest", test, target, fallback);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle insertArguments(MethodHandle target, int pos, Object... values) {
            MethodHandle mh = MethodHandles.insertArguments(target, pos, values);
            return debug(mh, "insertArguments", target, Integer.valueOf(pos), values);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle dropArguments(MethodHandle target, int pos, Class<?>... values) {
            MethodHandle mh = MethodHandles.dropArguments(target, pos, values);
            return debug(mh, "dropArguments", target, Integer.valueOf(pos), values);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle dropArguments(MethodHandle target, int pos, List<Class<?>> values) {
            MethodHandle mh = MethodHandles.dropArguments(target, pos, values);
            return debug(mh, "dropArguments", target, Integer.valueOf(pos), values);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asType(MethodHandle handle, MethodType type) {
            MethodHandle mh = handle.asType(type);
            return debug(mh, "asType", handle, type);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle bindTo(MethodHandle handle, Object x) {
            MethodHandle mh = handle.bindTo(x);
            return debug(mh, "bindTo", handle, x);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle foldArguments(MethodHandle target, MethodHandle combiner) {
            MethodHandle mh = MethodHandles.foldArguments(target, combiner);
            return debug(mh, "foldArguments", target, combiner);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle explicitCastArguments(MethodHandle target, MethodType type) {
            MethodHandle mh = MethodHandles.explicitCastArguments(target, type);
            return debug(mh, "explicitCastArguments", target, type);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle arrayElementGetter(Class<?> type) {
            MethodHandle mh = MethodHandles.arrayElementGetter(type);
            return debug(mh, "arrayElementGetter", type);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle arrayElementSetter(Class<?> type) {
            MethodHandle mh = MethodHandles.arrayElementSetter(type);
            return debug(mh, "arrayElementSetter", type);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle throwException(Class<?> returnType, Class<? extends Throwable> exType) {
            MethodHandle mh = MethodHandles.throwException(returnType, exType);
            return debug(mh, "throwException", returnType, exType);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle catchException(MethodHandle target, Class<? extends Throwable> exType, MethodHandle handler) {
            MethodHandle mh = MethodHandles.catchException(target, exType, handler);
            return debug(mh, "catchException", exType);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle constant(Class<?> type, Object value) {
            MethodHandle mh = MethodHandles.constant(type, value);
            return debug(mh, "constant", type, value);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle identity(Class<?> type) {
            MethodHandle mh = MethodHandles.identity(type);
            return debug(mh, "identity", type);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asCollector(MethodHandle handle, Class<?> arrayType, int arrayLength) {
            MethodHandle mh = handle.asCollector(arrayType, arrayLength);
            return debug(mh, "asCollector", handle, arrayType, Integer.valueOf(arrayLength));
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asSpreader(MethodHandle handle, Class<?> arrayType, int arrayLength) {
            MethodHandle mh = handle.asSpreader(arrayType, arrayLength);
            return debug(mh, "asSpreader", handle, arrayType, Integer.valueOf(arrayLength));
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle getter(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
            try {
                MethodHandle mh = explicitLookup.findGetter(clazz, name, type);
                return debug(mh, "getter", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle staticGetter(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
            try {
                MethodHandle mh = explicitLookup.findStaticGetter(clazz, name, type);
                return debug(mh, "static getter", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle setter(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
            try {
                MethodHandle mh = explicitLookup.findSetter(clazz, name, type);
                return debug(mh, "setter", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle staticSetter(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
            try {
                MethodHandle mh = explicitLookup.findStaticSetter(clazz, name, type);
                return debug(mh, "static setter", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle find(Method method) {
            try {
                MethodHandle mh = MethodHandleFactory.PUBLIC_LOOKUP.unreflect(method);
                return debug(mh, "find", method);
            } catch (IllegalAccessException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findStatic(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type) {
            try {
                MethodHandle mh = explicitLookup.findStatic(clazz, name, type);
                return debug(mh, "findStatic", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findSpecial(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type, Class<?> thisClass) {
            try {
                MethodHandle mh = explicitLookup.findSpecial(clazz, name, type, thisClass);
                return debug(mh, "findSpecial", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findVirtual(MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type) {
            try {
                MethodHandle mh = explicitLookup.findVirtual(clazz, name, type);
                return debug(mh, "findVirtual", explicitLookup, clazz, name, type);
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public SwitchPoint createSwitchPoint() {
            SwitchPoint sp = new SwitchPoint();
            this.log.log(MethodHandleFactory.TRACE_LEVEL, "createSwitchPoint ", sp);
            return sp;
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle guardWithTest(SwitchPoint sp, MethodHandle before, MethodHandle after) {
            MethodHandle mh = sp.guardWithTest(before, after);
            return debug(mh, "guardWithTest", sp, before, after);
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodType type(Class<?> returnType, Class<?>... paramTypes) {
            MethodType mt = MethodType.methodType(returnType, paramTypes);
            this.log.log(MethodHandleFactory.TRACE_LEVEL, "methodType ", returnType, " ", Arrays.toString(paramTypes), " ", mt);
            return mt;
        }
    }
}
