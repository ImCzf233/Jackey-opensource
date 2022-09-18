package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSONFunctions;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import org.apache.log4j.spi.Configurator;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSON.class */
public final class NativeJSON extends ScriptObject {
    private static final Object TO_JSON = null;
    private static final Object JSOBJECT_INVOKER = null;
    private static final Object REPLACER_INVOKER = null;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSON$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object parse;
        private Object stringify;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$parse() {
            return this.parse;
        }

        public void S$parse(Object obj) {
            this.parse = obj;
        }

        public Object G$stringify() {
            return this.stringify;
        }

        public void S$stringify(Object obj) {
            this.stringify = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeJSON.Constructor.<init>():void
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:49)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:110)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeJSON.Constructor.<init>():void
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:49)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:110)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Constructor() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeJSON.Constructor.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "parse"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.parse = r0
                r-1 = r4
                java.lang.String r0 = "stringify"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.stringify = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJSON.Constructor.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "JSON";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeJSON.class.desiredAssertionStatus();
        TO_JSON = new Object();
        JSOBJECT_INVOKER = new Object();
        REPLACER_INVOKER = new Object();
        $clinit$();
    }

    private static InvokeByName getTO_JSON() {
        return Global.instance().getInvokeByName(TO_JSON, new Callable<InvokeByName>() { // from class: jdk.nashorn.internal.objects.NativeJSON.1
            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toJSON", ScriptObject.class, Object.class, Object.class);
            }
        });
    }

    private static MethodHandle getJSOBJECT_INVOKER() {
        return Global.instance().getDynamicInvoker(JSOBJECT_INVOKER, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeJSON.2
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Object.class);
            }
        });
    }

    private static MethodHandle getREPLACER_INVOKER() {
        return Global.instance().getDynamicInvoker(REPLACER_INVOKER, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeJSON.3
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Object.class, Object.class, Object.class);
            }
        });
    }

    private NativeJSON() {
        throw new UnsupportedOperationException();
    }

    public static Object parse(Object self, Object text, Object reviver) {
        return JSONFunctions.parse(text, reviver);
    }

    public static Object stringify(Object self, Object value, Object replacer, Object space) {
        String gap;
        StringifyState state = new StringifyState();
        if (Bootstrap.isCallable(replacer)) {
            state.replacerFunction = replacer;
        } else if (isArray(replacer) || isJSObjectArray(replacer) || (replacer instanceof Iterable) || (replacer != null && replacer.getClass().isArray())) {
            state.propertyList = new ArrayList();
            Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(replacer);
            while (iter.hasNext()) {
                String item = null;
                Object v = iter.next();
                if (v instanceof String) {
                    item = (String) v;
                } else if (v instanceof ConsString) {
                    item = v.toString();
                } else if ((v instanceof Number) || (v instanceof NativeNumber) || (v instanceof NativeString)) {
                    item = JSType.toString(v);
                }
                if (item != null) {
                    state.propertyList.add(item);
                }
            }
        }
        Object modSpace = space;
        if (modSpace instanceof NativeNumber) {
            modSpace = Double.valueOf(JSType.toNumber(JSType.toPrimitive(modSpace, Number.class)));
        } else if (modSpace instanceof NativeString) {
            modSpace = JSType.toString(JSType.toPrimitive(modSpace, String.class));
        }
        if (modSpace instanceof Number) {
            int indent = Math.min(10, JSType.toInteger(modSpace));
            if (indent < 1) {
                gap = "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < indent; i++) {
                    sb.append(' ');
                }
                gap = sb.toString();
            }
        } else if (JSType.isString(modSpace)) {
            String str = modSpace.toString();
            gap = str.substring(0, Math.min(10, str.length()));
        } else {
            gap = "";
        }
        state.gap = gap;
        ScriptObject wrapper = Global.newEmptyInstance();
        wrapper.set("", value, 0);
        return str("", wrapper, state);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeJSON$StringifyState.class */
    public static class StringifyState {
        final Map<Object, Object> stack;
        StringBuilder indent;
        String gap;
        List<String> propertyList;
        Object replacerFunction;

        private StringifyState() {
            this.stack = new IdentityHashMap();
            this.indent = new StringBuilder();
            this.gap = "";
            this.propertyList = null;
            this.replacerFunction = null;
        }
    }

    private static Object str(Object key, Object holder, StringifyState state) {
        if ($assertionsDisabled || (holder instanceof ScriptObject) || (holder instanceof JSObject)) {
            Object value = getProperty(holder, key);
            try {
                if (value instanceof ScriptObject) {
                    InvokeByName toJSONInvoker = getTO_JSON();
                    ScriptObject svalue = (ScriptObject) value;
                    Object toJSON = toJSONInvoker.getGetter().invokeExact(svalue);
                    if (Bootstrap.isCallable(toJSON)) {
                        value = toJSONInvoker.getInvoker().invokeExact(toJSON, svalue, key);
                    }
                } else if (value instanceof JSObject) {
                    JSObject jsObj = (JSObject) value;
                    Object toJSON2 = jsObj.getMember("toJSON");
                    if (Bootstrap.isCallable(toJSON2)) {
                        value = getJSOBJECT_INVOKER().invokeExact(toJSON2, value);
                    }
                }
                if (state.replacerFunction != null) {
                    value = getREPLACER_INVOKER().invokeExact(state.replacerFunction, holder, key, value);
                }
                boolean isObj = value instanceof ScriptObject;
                if (isObj) {
                    if (value instanceof NativeNumber) {
                        value = Double.valueOf(JSType.toNumber(value));
                    } else if (value instanceof NativeString) {
                        value = JSType.toString(value);
                    } else if (value instanceof NativeBoolean) {
                        value = Boolean.valueOf(((NativeBoolean) value).booleanValue());
                    }
                }
                if (value == null) {
                    return Configurator.NULL;
                }
                if (Boolean.TRUE.equals(value)) {
                    return "true";
                }
                if (Boolean.FALSE.equals(value)) {
                    return "false";
                }
                if (value instanceof String) {
                    return JSONFunctions.quote((String) value);
                }
                if (value instanceof ConsString) {
                    return JSONFunctions.quote(value.toString());
                }
                if (value instanceof Number) {
                    return JSType.isFinite(((Number) value).doubleValue()) ? JSType.toString(value) : Configurator.NULL;
                }
                JSType type = JSType.m66of(value);
                if (type == JSType.OBJECT) {
                    if (isArray(value) || isJSObjectArray(value)) {
                        return m71JA(value, state);
                    }
                    if ((value instanceof ScriptObject) || (value instanceof JSObject)) {
                        return m70JO(value, state);
                    }
                }
                return ScriptRuntime.UNDEFINED;
            } catch (Error | RuntimeException t) {
                throw t;
            } catch (Throwable t2) {
                throw new RuntimeException(t2);
            }
        }
        throw new AssertionError();
    }

    /* renamed from: JO */
    private static String m70JO(Object value, StringifyState state) {
        if ($assertionsDisabled || (value instanceof ScriptObject) || (value instanceof JSObject)) {
            if (state.stack.containsKey(value)) {
                throw ECMAErrors.typeError("JSON.stringify.cyclic", new String[0]);
            }
            state.stack.put(value, value);
            StringBuilder stepback = new StringBuilder(state.indent.toString());
            state.indent.append(state.gap);
            StringBuilder finalStr = new StringBuilder();
            List<Object> partial = new ArrayList<>();
            List<String> k = state.propertyList == null ? Arrays.asList(getOwnKeys(value)) : state.propertyList;
            for (Object p : k) {
                Object strP = str(p, value, state);
                if (strP != ScriptRuntime.UNDEFINED) {
                    StringBuilder member = new StringBuilder();
                    member.append(JSONFunctions.quote(p.toString())).append(':');
                    if (!state.gap.isEmpty()) {
                        member.append(' ');
                    }
                    member.append(strP);
                    partial.add(member);
                }
            }
            if (partial.isEmpty()) {
                finalStr.append("{}");
            } else if (state.gap.isEmpty()) {
                int size = partial.size();
                int index = 0;
                finalStr.append('{');
                for (Object str : partial) {
                    finalStr.append(str);
                    if (index < size - 1) {
                        finalStr.append(',');
                    }
                    index++;
                }
                finalStr.append('}');
            } else {
                int size2 = partial.size();
                int index2 = 0;
                finalStr.append("{\n");
                finalStr.append((CharSequence) state.indent);
                for (Object str2 : partial) {
                    finalStr.append(str2);
                    if (index2 < size2 - 1) {
                        finalStr.append(",\n");
                        finalStr.append((CharSequence) state.indent);
                    }
                    index2++;
                }
                finalStr.append('\n');
                finalStr.append((CharSequence) stepback);
                finalStr.append('}');
            }
            state.stack.remove(value);
            state.indent = stepback;
            return finalStr.toString();
        }
        throw new AssertionError();
    }

    /* renamed from: JA */
    private static Object m71JA(Object value, StringifyState state) {
        if ($assertionsDisabled || (value instanceof ScriptObject) || (value instanceof JSObject)) {
            if (state.stack.containsKey(value)) {
                throw ECMAErrors.typeError("JSON.stringify.cyclic", new String[0]);
            }
            state.stack.put(value, value);
            StringBuilder stepback = new StringBuilder(state.indent.toString());
            state.indent.append(state.gap);
            List<Object> partial = new ArrayList<>();
            int length = JSType.toInteger(getLength(value));
            for (int index = 0; index < length; index++) {
                Object strP = str(Integer.valueOf(index), value, state);
                if (strP == ScriptRuntime.UNDEFINED) {
                    strP = Configurator.NULL;
                }
                partial.add(strP);
            }
            StringBuilder finalStr = new StringBuilder();
            if (partial.isEmpty()) {
                finalStr.append("[]");
            } else if (state.gap.isEmpty()) {
                int size = partial.size();
                int index2 = 0;
                finalStr.append('[');
                for (Object str : partial) {
                    finalStr.append(str);
                    if (index2 < size - 1) {
                        finalStr.append(',');
                    }
                    index2++;
                }
                finalStr.append(']');
            } else {
                int size2 = partial.size();
                int index3 = 0;
                finalStr.append("[\n");
                finalStr.append((CharSequence) state.indent);
                for (Object str2 : partial) {
                    finalStr.append(str2);
                    if (index3 < size2 - 1) {
                        finalStr.append(",\n");
                        finalStr.append((CharSequence) state.indent);
                    }
                    index3++;
                }
                finalStr.append('\n');
                finalStr.append((CharSequence) stepback);
                finalStr.append(']');
            }
            state.stack.remove(value);
            state.indent = stepback;
            return finalStr.toString();
        }
        throw new AssertionError();
    }

    private static String[] getOwnKeys(Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getOwnKeys(false);
        }
        if (obj instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj).getOwnKeys(false);
        }
        if (obj instanceof JSObject) {
            return (String[]) ((JSObject) obj).keySet().toArray(new String[0]);
        }
        throw new AssertionError("should not reach here");
    }

    private static Object getLength(Object obj) {
        if (obj instanceof ScriptObject) {
            return ((ScriptObject) obj).getLength();
        }
        if (obj instanceof JSObject) {
            return ((JSObject) obj).getMember("length");
        }
        throw new AssertionError("should not reach here");
    }

    private static boolean isJSObjectArray(Object obj) {
        return (obj instanceof JSObject) && ((JSObject) obj).isArray();
    }

    private static Object getProperty(Object holder, Object key) {
        if (holder instanceof ScriptObject) {
            return ((ScriptObject) holder).get(key);
        }
        if (holder instanceof JSObject) {
            JSObject jsObj = (JSObject) holder;
            if (key instanceof Integer) {
                return jsObj.getSlot(((Integer) key).intValue());
            }
            return jsObj.getMember(Objects.toString(key));
        }
        return new AssertionError("should not reach here");
    }
}
