package jdk.nashorn.internal.objects;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.GlobalConstants;
import jdk.nashorn.internal.runtime.GlobalFunctions;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.NativeJavaPackage;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Specialization;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;
import jdk.nashorn.internal.scripts.C1653JD;
import jdk.nashorn.internal.scripts.C1654JO;
import jdk.nashorn.tools.ShellFunctions;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/Global.class */
public final class Global extends Scope {
    private static final Object LAZY_SENTINEL;
    private static final Object LOCATION_PLACEHOLDER;
    public Object arguments;
    public Object parseInt;
    public Object parseFloat;
    public Object isNaN;
    public Object isFinite;
    public Object encodeURI;
    public Object encodeURIComponent;
    public Object decodeURI;
    public Object decodeURIComponent;
    public Object escape;
    public Object unescape;
    public Object print;
    public Object load;
    public Object loadWithNewGlobal;
    public Object exit;
    public Object quit;
    public static final double NaN = Double.NaN;
    public static final double Infinity = Double.POSITIVE_INFINITY;
    public static final Object undefined;
    public Object eval;
    public volatile Object object;
    public volatile Object function;
    public volatile Object array;
    public volatile Object string;
    public volatile Object _boolean;
    public volatile Object number;
    public volatile Object math;
    public volatile Object error;
    public volatile Object referenceError;
    public volatile Object syntaxError;
    public volatile Object typeError;
    private volatile Object arrayBuffer;
    private volatile Object dataView;
    private volatile Object int8Array;
    private volatile Object uint8Array;
    private volatile Object uint8ClampedArray;
    private volatile Object int16Array;
    private volatile Object uint16Array;
    private volatile Object int32Array;
    private volatile Object uint32Array;
    private volatile Object float32Array;
    private volatile Object float64Array;
    public volatile Object packages;

    /* renamed from: com */
    public volatile Object f464com;
    public volatile Object edu;
    public volatile Object java;
    public volatile Object javafx;
    public volatile Object javax;

    /* renamed from: org */
    public volatile Object f465org;
    private volatile Object javaImporter;
    private volatile Object javaApi;
    public static final Object __FILE__;
    public static final Object __DIR__;
    public static final Object __LINE__;
    private volatile NativeDate DEFAULT_DATE;
    private volatile NativeRegExp DEFAULT_REGEXP;
    private ScriptFunction builtinFunction;
    private ScriptFunction builtinObject;
    private ScriptFunction builtinArray;
    private ScriptFunction builtinBoolean;
    private ScriptFunction builtinDate;
    private ScriptObject builtinJSON;
    private ScriptFunction builtinJSAdapter;
    private ScriptObject builtinMath;
    private ScriptFunction builtinNumber;
    private ScriptFunction builtinRegExp;
    private ScriptFunction builtinString;
    private ScriptFunction builtinError;
    private ScriptFunction builtinEval;
    private ScriptFunction builtinEvalError;
    private ScriptFunction builtinRangeError;
    private ScriptFunction builtinReferenceError;
    private ScriptFunction builtinSyntaxError;
    private ScriptFunction builtinTypeError;
    private ScriptFunction builtinURIError;
    private ScriptObject builtinPackages;
    private ScriptObject builtinCom;
    private ScriptObject builtinEdu;
    private ScriptObject builtinJava;
    private ScriptObject builtinJavafx;
    private ScriptObject builtinJavax;
    private ScriptObject builtinOrg;
    private ScriptFunction builtinJavaImporter;
    private ScriptObject builtinJavaApi;
    private ScriptFunction builtinArrayBuffer;
    private ScriptFunction builtinDataView;
    private ScriptFunction builtinInt8Array;
    private ScriptFunction builtinUint8Array;
    private ScriptFunction builtinUint8ClampedArray;
    private ScriptFunction builtinInt16Array;
    private ScriptFunction builtinUint16Array;
    private ScriptFunction builtinInt32Array;
    private ScriptFunction builtinUint32Array;
    private ScriptFunction builtinFloat32Array;
    private ScriptFunction builtinFloat64Array;
    private ScriptFunction typeErrorThrower;
    private RegExpResult lastRegExpResult;
    private static final MethodHandle EVAL;
    private static final MethodHandle NO_SUCH_PROPERTY;
    private static final MethodHandle PRINT;
    private static final MethodHandle PRINTLN;
    private static final MethodHandle LOAD;
    private static final MethodHandle LOAD_WITH_NEW_GLOBAL;
    private static final MethodHandle EXIT;
    private static final MethodHandle LEXICAL_SCOPE_FILTER;
    private static PropertyMap $nasgenmap$;
    private final Context context;
    private ThreadLocal<ScriptContext> scontext;
    private ScriptEngine engine;
    private volatile ScriptContext initscontext;
    private final LexicalScope lexicalScope;
    private SwitchPoint lexicalScopeSwitchPoint;
    static final /* synthetic */ boolean $assertionsDisabled;
    private final InvokeByName TO_STRING = new InvokeByName("toString", ScriptObject.class);
    private final InvokeByName VALUE_OF = new InvokeByName("valueOf", ScriptObject.class);
    private volatile Object date = LAZY_SENTINEL;
    private volatile Object regexp = LAZY_SENTINEL;
    private volatile Object json = LAZY_SENTINEL;
    private volatile Object jsadapter = LAZY_SENTINEL;
    private volatile Object evalError = LAZY_SENTINEL;
    private volatile Object rangeError = LAZY_SENTINEL;
    private volatile Object uriError = LAZY_SENTINEL;
    private final Map<Object, InvokeByName> namedInvokers = new ConcurrentHashMap();
    private final Map<Object, MethodHandle> dynamicInvokers = new ConcurrentHashMap();

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.Global.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/Global.class
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        Caused by: java.lang.ArrayIndexOutOfBoundsException
        */
    public static void $clinit$() {
        /*
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.Global.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/Global.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.Global.$clinit$():void");
    }

    public Object G$arguments() {
        return this.arguments;
    }

    public void S$arguments(Object obj) {
        this.arguments = obj;
    }

    public Object G$parseInt() {
        return this.parseInt;
    }

    public void S$parseInt(Object obj) {
        this.parseInt = obj;
    }

    public Object G$parseFloat() {
        return this.parseFloat;
    }

    public void S$parseFloat(Object obj) {
        this.parseFloat = obj;
    }

    public Object G$isNaN() {
        return this.isNaN;
    }

    public void S$isNaN(Object obj) {
        this.isNaN = obj;
    }

    public Object G$isFinite() {
        return this.isFinite;
    }

    public void S$isFinite(Object obj) {
        this.isFinite = obj;
    }

    public Object G$encodeURI() {
        return this.encodeURI;
    }

    public void S$encodeURI(Object obj) {
        this.encodeURI = obj;
    }

    public Object G$encodeURIComponent() {
        return this.encodeURIComponent;
    }

    public void S$encodeURIComponent(Object obj) {
        this.encodeURIComponent = obj;
    }

    public Object G$decodeURI() {
        return this.decodeURI;
    }

    public void S$decodeURI(Object obj) {
        this.decodeURI = obj;
    }

    public Object G$decodeURIComponent() {
        return this.decodeURIComponent;
    }

    public void S$decodeURIComponent(Object obj) {
        this.decodeURIComponent = obj;
    }

    public Object G$escape() {
        return this.escape;
    }

    public void S$escape(Object obj) {
        this.escape = obj;
    }

    public Object G$unescape() {
        return this.unescape;
    }

    public void S$unescape(Object obj) {
        this.unescape = obj;
    }

    public Object G$print() {
        return this.print;
    }

    public void S$print(Object obj) {
        this.print = obj;
    }

    public Object G$load() {
        return this.load;
    }

    public void S$load(Object obj) {
        this.load = obj;
    }

    public Object G$loadWithNewGlobal() {
        return this.loadWithNewGlobal;
    }

    public void S$loadWithNewGlobal(Object obj) {
        this.loadWithNewGlobal = obj;
    }

    public Object G$exit() {
        return this.exit;
    }

    public void S$exit(Object obj) {
        this.exit = obj;
    }

    public Object G$quit() {
        return this.quit;
    }

    public void S$quit(Object obj) {
        this.quit = obj;
    }

    public double G$NaN() {
        return NaN;
    }

    public double G$Infinity() {
        return Infinity;
    }

    public Object G$undefined() {
        return undefined;
    }

    public Object G$eval() {
        return this.eval;
    }

    public void S$eval(Object obj) {
        this.eval = obj;
    }

    public Object G$object() {
        return this.object;
    }

    public void S$object(Object obj) {
        this.object = obj;
    }

    public Object G$function() {
        return this.function;
    }

    public void S$function(Object obj) {
        this.function = obj;
    }

    public Object G$array() {
        return this.array;
    }

    public void S$array(Object obj) {
        this.array = obj;
    }

    public Object G$string() {
        return this.string;
    }

    public void S$string(Object obj) {
        this.string = obj;
    }

    public Object G$_boolean() {
        return this._boolean;
    }

    public void S$_boolean(Object obj) {
        this._boolean = obj;
    }

    public Object G$number() {
        return this.number;
    }

    public void S$number(Object obj) {
        this.number = obj;
    }

    public Object G$math() {
        return this.math;
    }

    public void S$math(Object obj) {
        this.math = obj;
    }

    public Object G$error() {
        return this.error;
    }

    public void S$error(Object obj) {
        this.error = obj;
    }

    public Object G$referenceError() {
        return this.referenceError;
    }

    public void S$referenceError(Object obj) {
        this.referenceError = obj;
    }

    public Object G$syntaxError() {
        return this.syntaxError;
    }

    public void S$syntaxError(Object obj) {
        this.syntaxError = obj;
    }

    public Object G$typeError() {
        return this.typeError;
    }

    public void S$typeError(Object obj) {
        this.typeError = obj;
    }

    public Object G$packages() {
        return this.packages;
    }

    public void S$packages(Object obj) {
        this.packages = obj;
    }

    public Object G$com() {
        return this.f464com;
    }

    public void S$com(Object obj) {
        this.f464com = obj;
    }

    public Object G$edu() {
        return this.edu;
    }

    public void S$edu(Object obj) {
        this.edu = obj;
    }

    public Object G$java() {
        return this.java;
    }

    public void S$java(Object obj) {
        this.java = obj;
    }

    public Object G$javafx() {
        return this.javafx;
    }

    public void S$javafx(Object obj) {
        this.javafx = obj;
    }

    public Object G$javax() {
        return this.javax;
    }

    public void S$javax(Object obj) {
        this.javax = obj;
    }

    public Object G$org() {
        return this.f465org;
    }

    public void S$org(Object obj) {
        this.f465org = obj;
    }

    public Object G$__FILE__() {
        return __FILE__;
    }

    public Object G$__DIR__() {
        return __DIR__;
    }

    public Object G$__LINE__() {
        return __LINE__;
    }

    static {
        $assertionsDisabled = !Global.class.desiredAssertionStatus();
        LAZY_SENTINEL = new Object();
        LOCATION_PLACEHOLDER = new Object();
        undefined = ScriptRuntime.UNDEFINED;
        __FILE__ = LOCATION_PLACEHOLDER;
        __DIR__ = LOCATION_PLACEHOLDER;
        __LINE__ = LOCATION_PLACEHOLDER;
        EVAL = findOwnMH_S("eval", Object.class, Object.class, Object.class);
        NO_SUCH_PROPERTY = findOwnMH_S(ScriptObject.NO_SUCH_PROPERTY_NAME, Object.class, Object.class, Object.class);
        PRINT = findOwnMH_S("print", Object.class, Object.class, Object[].class);
        PRINTLN = findOwnMH_S("println", Object.class, Object.class, Object[].class);
        LOAD = findOwnMH_S("load", Object.class, Object.class, Object.class);
        LOAD_WITH_NEW_GLOBAL = findOwnMH_S("loadWithNewGlobal", Object.class, Object.class, Object[].class);
        EXIT = findOwnMH_S("exit", Object.class, Object.class, Object.class);
        LEXICAL_SCOPE_FILTER = findOwnMH_S("lexicalScopeFilter", Object.class, Object.class);
        $clinit$();
    }

    public static Object getDate(Object self) {
        Global global = instanceFrom(self);
        if (global.date == LAZY_SENTINEL) {
            global.date = global.getBuiltinDate();
        }
        return global.date;
    }

    public static void setDate(Object self, Object value) {
        Global global = instanceFrom(self);
        global.date = value;
    }

    public static Object getRegExp(Object self) {
        Global global = instanceFrom(self);
        if (global.regexp == LAZY_SENTINEL) {
            global.regexp = global.getBuiltinRegExp();
        }
        return global.regexp;
    }

    public static void setRegExp(Object self, Object value) {
        Global global = instanceFrom(self);
        global.regexp = value;
    }

    public static Object getJSON(Object self) {
        Global global = instanceFrom(self);
        if (global.json == LAZY_SENTINEL) {
            global.json = global.getBuiltinJSON();
        }
        return global.json;
    }

    public static void setJSON(Object self, Object value) {
        Global global = instanceFrom(self);
        global.json = value;
    }

    public static Object getJSAdapter(Object self) {
        Global global = instanceFrom(self);
        if (global.jsadapter == LAZY_SENTINEL) {
            global.jsadapter = global.getBuiltinJSAdapter();
        }
        return global.jsadapter;
    }

    public static void setJSAdapter(Object self, Object value) {
        Global global = instanceFrom(self);
        global.jsadapter = value;
    }

    public static Object getEvalError(Object self) {
        Global global = instanceFrom(self);
        if (global.evalError == LAZY_SENTINEL) {
            global.evalError = global.getBuiltinEvalError();
        }
        return global.evalError;
    }

    public static void setEvalError(Object self, Object value) {
        Global global = instanceFrom(self);
        global.evalError = value;
    }

    public static Object getRangeError(Object self) {
        Global global = instanceFrom(self);
        if (global.rangeError == LAZY_SENTINEL) {
            global.rangeError = global.getBuiltinRangeError();
        }
        return global.rangeError;
    }

    public static void setRangeError(Object self, Object value) {
        Global global = instanceFrom(self);
        global.rangeError = value;
    }

    public static Object getURIError(Object self) {
        Global global = instanceFrom(self);
        if (global.uriError == LAZY_SENTINEL) {
            global.uriError = global.getBuiltinURIError();
        }
        return global.uriError;
    }

    public static void setURIError(Object self, Object value) {
        Global global = instanceFrom(self);
        global.uriError = value;
    }

    public static Object getArrayBuffer(Object self) {
        Global global = instanceFrom(self);
        if (global.arrayBuffer == LAZY_SENTINEL) {
            global.arrayBuffer = global.getBuiltinArrayBuffer();
        }
        return global.arrayBuffer;
    }

    public static void setArrayBuffer(Object self, Object value) {
        Global global = instanceFrom(self);
        global.arrayBuffer = value;
    }

    public static Object getDataView(Object self) {
        Global global = instanceFrom(self);
        if (global.dataView == LAZY_SENTINEL) {
            global.dataView = global.getBuiltinDataView();
        }
        return global.dataView;
    }

    public static void setDataView(Object self, Object value) {
        Global global = instanceFrom(self);
        global.dataView = value;
    }

    public static Object getInt8Array(Object self) {
        Global global = instanceFrom(self);
        if (global.int8Array == LAZY_SENTINEL) {
            global.int8Array = global.getBuiltinInt8Array();
        }
        return global.int8Array;
    }

    public static void setInt8Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.int8Array = value;
    }

    public static Object getUint8Array(Object self) {
        Global global = instanceFrom(self);
        if (global.uint8Array == LAZY_SENTINEL) {
            global.uint8Array = global.getBuiltinUint8Array();
        }
        return global.uint8Array;
    }

    public static void setUint8Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.uint8Array = value;
    }

    public static Object getUint8ClampedArray(Object self) {
        Global global = instanceFrom(self);
        if (global.uint8ClampedArray == LAZY_SENTINEL) {
            global.uint8ClampedArray = global.getBuiltinUint8ClampedArray();
        }
        return global.uint8ClampedArray;
    }

    public static void setUint8ClampedArray(Object self, Object value) {
        Global global = instanceFrom(self);
        global.uint8ClampedArray = value;
    }

    public static Object getInt16Array(Object self) {
        Global global = instanceFrom(self);
        if (global.int16Array == LAZY_SENTINEL) {
            global.int16Array = global.getBuiltinInt16Array();
        }
        return global.int16Array;
    }

    public static void setInt16Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.int16Array = value;
    }

    public static Object getUint16Array(Object self) {
        Global global = instanceFrom(self);
        if (global.uint16Array == LAZY_SENTINEL) {
            global.uint16Array = global.getBuiltinUint16Array();
        }
        return global.uint16Array;
    }

    public static void setUint16Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.uint16Array = value;
    }

    public static Object getInt32Array(Object self) {
        Global global = instanceFrom(self);
        if (global.int32Array == LAZY_SENTINEL) {
            global.int32Array = global.getBuiltinInt32Array();
        }
        return global.int32Array;
    }

    public static void setInt32Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.int32Array = value;
    }

    public static Object getUint32Array(Object self) {
        Global global = instanceFrom(self);
        if (global.uint32Array == LAZY_SENTINEL) {
            global.uint32Array = global.getBuiltinUint32Array();
        }
        return global.uint32Array;
    }

    public static void setUint32Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.uint32Array = value;
    }

    public static Object getFloat32Array(Object self) {
        Global global = instanceFrom(self);
        if (global.float32Array == LAZY_SENTINEL) {
            global.float32Array = global.getBuiltinFloat32Array();
        }
        return global.float32Array;
    }

    public static void setFloat32Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.float32Array = value;
    }

    public static Object getFloat64Array(Object self) {
        Global global = instanceFrom(self);
        if (global.float64Array == LAZY_SENTINEL) {
            global.float64Array = global.getBuiltinFloat64Array();
        }
        return global.float64Array;
    }

    public static void setFloat64Array(Object self, Object value) {
        Global global = instanceFrom(self);
        global.float64Array = value;
    }

    public static Object getJavaImporter(Object self) {
        Global global = instanceFrom(self);
        if (global.javaImporter == LAZY_SENTINEL) {
            global.javaImporter = global.getBuiltinJavaImporter();
        }
        return global.javaImporter;
    }

    public static void setJavaImporter(Object self, Object value) {
        Global global = instanceFrom(self);
        global.javaImporter = value;
    }

    public static Object getJavaApi(Object self) {
        Global global = instanceFrom(self);
        if (global.javaApi == LAZY_SENTINEL) {
            global.javaApi = global.getBuiltinJavaApi();
        }
        return global.javaApi;
    }

    public static void setJavaApi(Object self, Object value) {
        Global global = instanceFrom(self);
        global.javaApi = value;
    }

    public NativeDate getDefaultDate() {
        return this.DEFAULT_DATE;
    }

    public NativeRegExp getDefaultRegExp() {
        return this.DEFAULT_REGEXP;
    }

    public void setScriptContext(ScriptContext ctxt) {
        if ($assertionsDisabled || this.scontext != null) {
            this.scontext.set(ctxt);
            return;
        }
        throw new AssertionError();
    }

    public ScriptContext getScriptContext() {
        if ($assertionsDisabled || this.scontext != null) {
            return this.scontext.get();
        }
        throw new AssertionError();
    }

    public void setInitScriptContext(ScriptContext ctxt) {
        this.initscontext = ctxt;
    }

    private ScriptContext currentContext() {
        ScriptContext sc = this.scontext != null ? this.scontext.get() : null;
        if (sc != null) {
            return sc;
        }
        if (this.initscontext != null) {
            return this.initscontext;
        }
        if (this.engine == null) {
            return null;
        }
        return this.engine.getContext();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Context getContext() {
        return this.context;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean useDualFields() {
        return this.context.useDualFields();
    }

    private static PropertyMap checkAndGetMap(Context context) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(Context.NASHORN_CREATE_GLOBAL));
        }
        Objects.requireNonNull(context);
        return $nasgenmap$;
    }

    public Global(Context context) {
        super(checkAndGetMap(context));
        this.context = context;
        this.lexicalScope = context.getEnv()._es6 ? new LexicalScope(this) : null;
    }

    public static Global instance() {
        return (Global) Objects.requireNonNull(Context.getGlobal());
    }

    private static Global instanceFrom(Object self) {
        return self instanceof Global ? (Global) self : instance();
    }

    public static boolean hasInstance() {
        return Context.getGlobal() != null;
    }

    public static ScriptEnvironment getEnv() {
        return instance().getContext().getEnv();
    }

    public static Context getThisContext() {
        return instance().getContext();
    }

    public ClassFilter getClassFilter() {
        return this.context.getClassFilter();
    }

    public boolean isOfContext(Context ctxt) {
        return this.context == ctxt;
    }

    public boolean isStrictContext() {
        return this.context.getEnv()._strict;
    }

    public void initBuiltinObjects(ScriptEngine eng) {
        if (this.builtinObject != null) {
            return;
        }
        this.engine = eng;
        if (this.engine != null) {
            this.scontext = new ThreadLocal<>();
        }
        init(eng);
    }

    public Object wrapAsObject(Object obj) {
        if (obj instanceof Boolean) {
            return new NativeBoolean(((Boolean) obj).booleanValue(), this);
        }
        if (obj instanceof Number) {
            return new NativeNumber(((Number) obj).doubleValue(), this);
        }
        if (JSType.isString(obj)) {
            return new NativeString((CharSequence) obj, this);
        }
        if (obj instanceof Object[]) {
            return new NativeArray(ArrayData.allocate((Object[]) obj), this);
        }
        if (obj instanceof double[]) {
            return new NativeArray(ArrayData.allocate((double[]) obj), this);
        }
        if (obj instanceof int[]) {
            return new NativeArray(ArrayData.allocate((int[]) obj), this);
        }
        if (obj instanceof ArrayData) {
            return new NativeArray((ArrayData) obj, this);
        }
        return obj;
    }

    public static GuardedInvocation primitiveLookup(LinkRequest request, Object self) {
        if (JSType.isString(self)) {
            return NativeString.lookupPrimitive(request, self);
        }
        if (self instanceof Number) {
            return NativeNumber.lookupPrimitive(request, self);
        }
        if (self instanceof Boolean) {
            return NativeBoolean.lookupPrimitive(request, self);
        }
        throw new IllegalArgumentException("Unsupported primitive: " + self);
    }

    public static MethodHandle getPrimitiveWrapFilter(Object self) {
        if (JSType.isString(self)) {
            return NativeString.WRAPFILTER;
        }
        if (self instanceof Number) {
            return NativeNumber.WRAPFILTER;
        }
        if (self instanceof Boolean) {
            return NativeBoolean.WRAPFILTER;
        }
        throw new IllegalArgumentException("Unsupported primitive: " + self);
    }

    public ScriptObject newObject() {
        return useDualFields() ? new C1653JD(getObjectPrototype()) : new C1654JO(getObjectPrototype());
    }

    public Object getDefaultValue(ScriptObject sobj, Class<?> typeHint) {
        Class<?> hint = typeHint;
        if (hint == null) {
            hint = Number.class;
        }
        try {
            if (hint == String.class) {
                Object toString = this.TO_STRING.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(toString)) {
                    Object value = this.TO_STRING.getInvoker().invokeExact(toString, sobj);
                    if (JSType.isPrimitive(value)) {
                        return value;
                    }
                }
                Object valueOf = this.VALUE_OF.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(valueOf)) {
                    Object value2 = this.VALUE_OF.getInvoker().invokeExact(valueOf, sobj);
                    if (JSType.isPrimitive(value2)) {
                        return value2;
                    }
                }
                throw ECMAErrors.typeError(this, "cannot.get.default.string", new String[0]);
            } else if (hint == Number.class) {
                Object valueOf2 = this.VALUE_OF.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(valueOf2)) {
                    Object value3 = this.VALUE_OF.getInvoker().invokeExact(valueOf2, sobj);
                    if (JSType.isPrimitive(value3)) {
                        return value3;
                    }
                }
                Object toString2 = this.TO_STRING.getGetter().invokeExact(sobj);
                if (Bootstrap.isCallable(toString2)) {
                    Object value4 = this.TO_STRING.getInvoker().invokeExact(toString2, sobj);
                    if (JSType.isPrimitive(value4)) {
                        return value4;
                    }
                }
                throw ECMAErrors.typeError(this, "cannot.get.default.number", new String[0]);
            } else {
                return ScriptRuntime.UNDEFINED;
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public boolean isError(ScriptObject sobj) {
        ScriptObject errorProto = getErrorPrototype();
        ScriptObject proto = sobj.getProto();
        while (true) {
            ScriptObject proto2 = proto;
            if (proto2 != null) {
                if (proto2 == errorProto) {
                    return true;
                }
                proto = proto2.getProto();
            } else {
                return false;
            }
        }
    }

    public ScriptObject newError(String msg) {
        return new NativeError(msg, this);
    }

    public ScriptObject newEvalError(String msg) {
        return new NativeEvalError(msg, this);
    }

    public ScriptObject newRangeError(String msg) {
        return new NativeRangeError(msg, this);
    }

    public ScriptObject newReferenceError(String msg) {
        return new NativeReferenceError(msg, this);
    }

    public ScriptObject newSyntaxError(String msg) {
        return new NativeSyntaxError(msg, this);
    }

    public ScriptObject newTypeError(String msg) {
        return new NativeTypeError(msg, this);
    }

    public ScriptObject newURIError(String msg) {
        return new NativeURIError(msg, this);
    }

    public PropertyDescriptor newGenericDescriptor(boolean configurable, boolean enumerable) {
        return new GenericPropertyDescriptor(configurable, enumerable, this);
    }

    public PropertyDescriptor newDataDescriptor(Object value, boolean configurable, boolean enumerable, boolean writable) {
        return new DataPropertyDescriptor(configurable, enumerable, writable, value, this);
    }

    public PropertyDescriptor newAccessorDescriptor(Object get, Object set, boolean configurable, boolean enumerable) {
        AccessorPropertyDescriptor desc = new AccessorPropertyDescriptor(configurable, enumerable, get == null ? ScriptRuntime.UNDEFINED : get, set == null ? ScriptRuntime.UNDEFINED : set, this);
        if (get == null) {
            desc.delete((Object) PropertyDescriptor.GET, false);
        }
        if (set == null) {
            desc.delete((Object) PropertyDescriptor.SET, false);
        }
        return desc;
    }

    private static <T> T getLazilyCreatedValue(Object key, Callable<T> creator, Map<Object, T> map) {
        T obj = map.get(key);
        if (obj != null) {
            return obj;
        }
        try {
            T newObj = creator.call();
            T existingObj = map.putIfAbsent(key, newObj);
            return existingObj != null ? existingObj : newObj;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    public InvokeByName getInvokeByName(Object key, Callable<InvokeByName> creator) {
        return (InvokeByName) getLazilyCreatedValue(key, creator, this.namedInvokers);
    }

    public MethodHandle getDynamicInvoker(Object key, Callable<MethodHandle> creator) {
        return (MethodHandle) getLazilyCreatedValue(key, creator, this.dynamicInvokers);
    }

    public static Object __noSuchProperty__(Object self, Object name) {
        int scope;
        Global global = instance();
        ScriptContext sctxt = global.currentContext();
        String nameStr = name.toString();
        if (sctxt != null && (scope = sctxt.getAttributesScope(nameStr)) != -1) {
            return ScriptObjectMirror.unwrap(sctxt.getAttribute(nameStr, scope), global);
        }
        if ("context".equals(nameStr)) {
            return sctxt;
        }
        if ("engine".equals(nameStr) && (System.getSecurityManager() == null || global.getClassFilter() == null)) {
            return global.engine;
        }
        if (self == ScriptRuntime.UNDEFINED) {
            throw ECMAErrors.referenceError(global, "not.defined", nameStr);
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object eval(Object self, Object str) {
        return directEval(self, str, instanceFrom(self), ScriptRuntime.UNDEFINED, false);
    }

    public static Object directEval(Object self, Object str, Object callThis, Object location, boolean strict) {
        if (!JSType.isString(str)) {
            return str;
        }
        Global global = instanceFrom(self);
        ScriptObject scope = (!(self instanceof ScriptObject) || !((ScriptObject) self).isScope()) ? global : (ScriptObject) self;
        return global.getContext().eval(scope, str.toString(), callThis, location, strict, true);
    }

    public static Object print(Object self, Object... objects) {
        return instanceFrom(self).printImpl(false, objects);
    }

    public static Object println(Object self, Object... objects) {
        return instanceFrom(self).printImpl(true, objects);
    }

    public static Object load(Object self, Object source) throws IOException {
        Global global = instanceFrom(self);
        return global.getContext().load(self, source);
    }

    public static Object loadWithNewGlobal(Object self, Object... args) throws IOException {
        Global global = instanceFrom(self);
        int length = args.length;
        boolean hasArgs = 0 < length;
        Object from = hasArgs ? args[0] : ScriptRuntime.UNDEFINED;
        Object[] arguments = hasArgs ? Arrays.copyOfRange(args, 1, length) : args;
        return global.getContext().loadWithNewGlobal(from, arguments);
    }

    public static Object exit(Object self, Object code) {
        System.exit(JSType.toInt32(code));
        return ScriptRuntime.UNDEFINED;
    }

    public ScriptObject getObjectPrototype() {
        return ScriptFunction.getPrototype(this.builtinObject);
    }

    public ScriptObject getFunctionPrototype() {
        return ScriptFunction.getPrototype(this.builtinFunction);
    }

    public ScriptObject getArrayPrototype() {
        return ScriptFunction.getPrototype(this.builtinArray);
    }

    public ScriptObject getBooleanPrototype() {
        return ScriptFunction.getPrototype(this.builtinBoolean);
    }

    public ScriptObject getNumberPrototype() {
        return ScriptFunction.getPrototype(this.builtinNumber);
    }

    public ScriptObject getDatePrototype() {
        return ScriptFunction.getPrototype(getBuiltinDate());
    }

    public ScriptObject getRegExpPrototype() {
        return ScriptFunction.getPrototype(getBuiltinRegExp());
    }

    public ScriptObject getStringPrototype() {
        return ScriptFunction.getPrototype(this.builtinString);
    }

    public ScriptObject getErrorPrototype() {
        return ScriptFunction.getPrototype(this.builtinError);
    }

    public ScriptObject getEvalErrorPrototype() {
        return ScriptFunction.getPrototype(getBuiltinEvalError());
    }

    public ScriptObject getRangeErrorPrototype() {
        return ScriptFunction.getPrototype(getBuiltinRangeError());
    }

    public ScriptObject getReferenceErrorPrototype() {
        return ScriptFunction.getPrototype(this.builtinReferenceError);
    }

    public ScriptObject getSyntaxErrorPrototype() {
        return ScriptFunction.getPrototype(this.builtinSyntaxError);
    }

    public ScriptObject getTypeErrorPrototype() {
        return ScriptFunction.getPrototype(this.builtinTypeError);
    }

    public ScriptObject getURIErrorPrototype() {
        return ScriptFunction.getPrototype(getBuiltinURIError());
    }

    public ScriptObject getJavaImporterPrototype() {
        return ScriptFunction.getPrototype(getBuiltinJavaImporter());
    }

    public ScriptObject getJSAdapterPrototype() {
        return ScriptFunction.getPrototype(getBuiltinJSAdapter());
    }

    private synchronized ScriptFunction getBuiltinArrayBuffer() {
        if (this.builtinArrayBuffer == null) {
            this.builtinArrayBuffer = (ScriptFunction) initConstructorAndSwitchPoint("ArrayBuffer", ScriptFunction.class);
        }
        return this.builtinArrayBuffer;
    }

    public ScriptObject getArrayBufferPrototype() {
        return ScriptFunction.getPrototype(getBuiltinArrayBuffer());
    }

    private synchronized ScriptFunction getBuiltinDataView() {
        if (this.builtinDataView == null) {
            this.builtinDataView = (ScriptFunction) initConstructorAndSwitchPoint("DataView", ScriptFunction.class);
        }
        return this.builtinDataView;
    }

    public ScriptObject getDataViewPrototype() {
        return ScriptFunction.getPrototype(getBuiltinDataView());
    }

    private synchronized ScriptFunction getBuiltinInt8Array() {
        if (this.builtinInt8Array == null) {
            this.builtinInt8Array = (ScriptFunction) initConstructorAndSwitchPoint("Int8Array", ScriptFunction.class);
        }
        return this.builtinInt8Array;
    }

    public ScriptObject getInt8ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinInt8Array());
    }

    private synchronized ScriptFunction getBuiltinUint8Array() {
        if (this.builtinUint8Array == null) {
            this.builtinUint8Array = (ScriptFunction) initConstructorAndSwitchPoint("Uint8Array", ScriptFunction.class);
        }
        return this.builtinUint8Array;
    }

    public ScriptObject getUint8ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinUint8Array());
    }

    private synchronized ScriptFunction getBuiltinUint8ClampedArray() {
        if (this.builtinUint8ClampedArray == null) {
            this.builtinUint8ClampedArray = (ScriptFunction) initConstructorAndSwitchPoint("Uint8ClampedArray", ScriptFunction.class);
        }
        return this.builtinUint8ClampedArray;
    }

    public ScriptObject getUint8ClampedArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinUint8ClampedArray());
    }

    private synchronized ScriptFunction getBuiltinInt16Array() {
        if (this.builtinInt16Array == null) {
            this.builtinInt16Array = (ScriptFunction) initConstructorAndSwitchPoint("Int16Array", ScriptFunction.class);
        }
        return this.builtinInt16Array;
    }

    public ScriptObject getInt16ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinInt16Array());
    }

    private synchronized ScriptFunction getBuiltinUint16Array() {
        if (this.builtinUint16Array == null) {
            this.builtinUint16Array = (ScriptFunction) initConstructorAndSwitchPoint("Uint16Array", ScriptFunction.class);
        }
        return this.builtinUint16Array;
    }

    public ScriptObject getUint16ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinUint16Array());
    }

    private synchronized ScriptFunction getBuiltinInt32Array() {
        if (this.builtinInt32Array == null) {
            this.builtinInt32Array = (ScriptFunction) initConstructorAndSwitchPoint("Int32Array", ScriptFunction.class);
        }
        return this.builtinInt32Array;
    }

    public ScriptObject getInt32ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinInt32Array());
    }

    private synchronized ScriptFunction getBuiltinUint32Array() {
        if (this.builtinUint32Array == null) {
            this.builtinUint32Array = (ScriptFunction) initConstructorAndSwitchPoint("Uint32Array", ScriptFunction.class);
        }
        return this.builtinUint32Array;
    }

    public ScriptObject getUint32ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinUint32Array());
    }

    private synchronized ScriptFunction getBuiltinFloat32Array() {
        if (this.builtinFloat32Array == null) {
            this.builtinFloat32Array = (ScriptFunction) initConstructorAndSwitchPoint("Float32Array", ScriptFunction.class);
        }
        return this.builtinFloat32Array;
    }

    public ScriptObject getFloat32ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinFloat32Array());
    }

    private synchronized ScriptFunction getBuiltinFloat64Array() {
        if (this.builtinFloat64Array == null) {
            this.builtinFloat64Array = (ScriptFunction) initConstructorAndSwitchPoint("Float64Array", ScriptFunction.class);
        }
        return this.builtinFloat64Array;
    }

    public ScriptObject getFloat64ArrayPrototype() {
        return ScriptFunction.getPrototype(getBuiltinFloat64Array());
    }

    public ScriptFunction getTypeErrorThrower() {
        return this.typeErrorThrower;
    }

    private synchronized ScriptFunction getBuiltinDate() {
        if (this.builtinDate == null) {
            this.builtinDate = (ScriptFunction) initConstructorAndSwitchPoint("Date", ScriptFunction.class);
            ScriptObject dateProto = ScriptFunction.getPrototype(this.builtinDate);
            this.DEFAULT_DATE = new NativeDate(Double.NaN, dateProto);
        }
        return this.builtinDate;
    }

    private synchronized ScriptFunction getBuiltinEvalError() {
        if (this.builtinEvalError == null) {
            this.builtinEvalError = initErrorSubtype("EvalError", getErrorPrototype());
        }
        return this.builtinEvalError;
    }

    private ScriptFunction getBuiltinFunction() {
        return this.builtinFunction;
    }

    public static SwitchPoint getBuiltinFunctionApplySwitchPoint() {
        return ScriptFunction.getPrototype(instance().getBuiltinFunction()).getProperty("apply").getBuiltinSwitchPoint();
    }

    private static boolean isBuiltinFunctionProperty(String name) {
        Global instance = instance();
        ScriptFunction builtinFunction = instance.getBuiltinFunction();
        if (builtinFunction == null) {
            return false;
        }
        boolean isBuiltinFunction = instance.function == builtinFunction;
        return isBuiltinFunction && ScriptFunction.getPrototype(builtinFunction).getProperty(name).isBuiltin();
    }

    public static boolean isBuiltinFunctionPrototypeApply() {
        return isBuiltinFunctionProperty("apply");
    }

    public static boolean isBuiltinFunctionPrototypeCall() {
        return isBuiltinFunctionProperty("call");
    }

    private synchronized ScriptFunction getBuiltinJSAdapter() {
        if (this.builtinJSAdapter == null) {
            this.builtinJSAdapter = (ScriptFunction) initConstructorAndSwitchPoint("JSAdapter", ScriptFunction.class);
        }
        return this.builtinJSAdapter;
    }

    private synchronized ScriptObject getBuiltinJSON() {
        if (this.builtinJSON == null) {
            this.builtinJSON = initConstructorAndSwitchPoint("JSON", ScriptObject.class);
        }
        return this.builtinJSON;
    }

    private synchronized ScriptFunction getBuiltinJavaImporter() {
        if (getContext().getEnv()._no_java) {
            throw new IllegalStateException();
        }
        if (this.builtinJavaImporter == null) {
            this.builtinJavaImporter = (ScriptFunction) initConstructor("JavaImporter", ScriptFunction.class);
        }
        return this.builtinJavaImporter;
    }

    private synchronized ScriptObject getBuiltinJavaApi() {
        if (getContext().getEnv()._no_java) {
            throw new IllegalStateException();
        }
        if (this.builtinJavaApi == null) {
            this.builtinJavaApi = initConstructor("Java", ScriptObject.class);
        }
        return this.builtinJavaApi;
    }

    private synchronized ScriptFunction getBuiltinRangeError() {
        if (this.builtinRangeError == null) {
            this.builtinRangeError = initErrorSubtype("RangeError", getErrorPrototype());
        }
        return this.builtinRangeError;
    }

    private synchronized ScriptFunction getBuiltinRegExp() {
        if (this.builtinRegExp == null) {
            this.builtinRegExp = (ScriptFunction) initConstructorAndSwitchPoint("RegExp", ScriptFunction.class);
            ScriptObject regExpProto = ScriptFunction.getPrototype(this.builtinRegExp);
            this.DEFAULT_REGEXP = new NativeRegExp("(?:)", "", this, regExpProto);
            regExpProto.addBoundProperties(this.DEFAULT_REGEXP);
        }
        return this.builtinRegExp;
    }

    private synchronized ScriptFunction getBuiltinURIError() {
        if (this.builtinURIError == null) {
            this.builtinURIError = initErrorSubtype("URIError", getErrorPrototype());
        }
        return this.builtinURIError;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "global";
    }

    public static Object regExpCopy(Object regexp) {
        return new NativeRegExp((NativeRegExp) regexp);
    }

    public static NativeRegExp toRegExp(Object obj) {
        if (obj instanceof NativeRegExp) {
            return (NativeRegExp) obj;
        }
        return new NativeRegExp(JSType.toString(obj));
    }

    public static Object toObject(Object obj) {
        if (obj == null || obj == ScriptRuntime.UNDEFINED) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
        }
        if (obj instanceof ScriptObject) {
            return obj;
        }
        return instance().wrapAsObject(obj);
    }

    public static NativeArray allocate(Object[] initial) {
        ArrayData arrayData = ArrayData.allocate(initial);
        for (int index = 0; index < initial.length; index++) {
            Object value = initial[index];
            if (value == ScriptRuntime.EMPTY) {
                arrayData = arrayData.delete(index);
            }
        }
        return new NativeArray(arrayData);
    }

    public static NativeArray allocate(double[] initial) {
        return new NativeArray(ArrayData.allocate(initial));
    }

    public static NativeArray allocate(int[] initial) {
        return new NativeArray(ArrayData.allocate(initial));
    }

    public static ScriptObject allocateArguments(Object[] arguments, Object callee, int numParams) {
        return NativeArguments.allocate(arguments, (ScriptFunction) callee, numParams);
    }

    public static boolean isEval(Object fn) {
        return fn == instance().builtinEval;
    }

    public static Object replaceLocationPropertyPlaceholder(Object placeholder, Object locationProperty) {
        return isLocationPropertyPlaceholder(placeholder) ? locationProperty : placeholder;
    }

    public static boolean isLocationPropertyPlaceholder(Object placeholder) {
        return placeholder == LOCATION_PLACEHOLDER;
    }

    public static Object newRegExp(String expression, String options) {
        if (options == null) {
            return new NativeRegExp(expression);
        }
        return new NativeRegExp(expression, options);
    }

    public static ScriptObject objectPrototype() {
        return instance().getObjectPrototype();
    }

    public static ScriptObject newEmptyInstance() {
        return instance().newObject();
    }

    public static ScriptObject checkObject(Object obj) {
        if (!(obj instanceof ScriptObject)) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
        }
        return (ScriptObject) obj;
    }

    public static void checkObjectCoercible(Object obj) {
        if (obj == null || obj == ScriptRuntime.UNDEFINED) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
        }
    }

    public final ScriptObject getLexicalScope() {
        if ($assertionsDisabled || this.context.getEnv()._es6) {
            return this.lexicalScope;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void addBoundProperties(ScriptObject source, Property[] properties) {
        PropertyMap ownMap = getMap();
        LexicalScope lexScope = null;
        PropertyMap lexicalMap = null;
        boolean hasLexicalDefinitions = false;
        if (this.context.getEnv()._es6) {
            lexScope = (LexicalScope) getLexicalScope();
            lexicalMap = lexScope.getMap();
            for (Property property : properties) {
                if (property.isLexicalBinding()) {
                    hasLexicalDefinitions = true;
                }
                Property globalProperty = ownMap.findProperty(property.getKey());
                if (globalProperty != null && !globalProperty.isConfigurable() && property.isLexicalBinding()) {
                    throw ECMAErrors.syntaxError("redeclare.variable", property.getKey());
                }
                Property lexicalProperty = lexicalMap.findProperty(property.getKey());
                if (lexicalProperty != null && !property.isConfigurable()) {
                    throw ECMAErrors.syntaxError("redeclare.variable", property.getKey());
                }
            }
        }
        boolean extensible = isExtensible();
        for (Property property2 : properties) {
            if (property2.isLexicalBinding()) {
                if (!$assertionsDisabled && lexScope == null) {
                    throw new AssertionError();
                }
                lexicalMap = lexScope.addBoundProperty(lexicalMap, source, property2, true);
                if (ownMap.findProperty(property2.getKey()) != null) {
                    invalidateGlobalConstant(property2.getKey());
                }
            } else {
                ownMap = addBoundProperty(ownMap, source, property2, extensible);
            }
        }
        setMap(ownMap);
        if (hasLexicalDefinitions) {
            if (!$assertionsDisabled && lexScope == null) {
                throw new AssertionError();
            }
            lexScope.setMap(lexicalMap);
            invalidateLexicalSwitchPoint();
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
        String name = desc.getNameToken(2);
        boolean isScope = NashornCallSiteDescriptor.isScope(desc);
        if (this.lexicalScope != null && isScope && !NashornCallSiteDescriptor.isApplyToCall(desc) && this.lexicalScope.hasOwnProperty(name)) {
            return this.lexicalScope.findGetMethod(desc, request, operator);
        }
        GuardedInvocation invocation = super.findGetMethod(desc, request, operator);
        if (isScope && this.context.getEnv()._es6 && (invocation.getSwitchPoints() == null || !hasOwnProperty(name))) {
            return invocation.addSwitchPoint(getLexicalScopeSwitchPoint());
        }
        return invocation;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public FindProperty findProperty(String key, boolean deep, ScriptObject start) {
        FindProperty find;
        if (this.lexicalScope != null && start != this && start.isScope() && (find = this.lexicalScope.findProperty(key, false)) != null) {
            return find;
        }
        return super.findProperty(key, deep, start);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
        boolean isScope = NashornCallSiteDescriptor.isScope(desc);
        if (this.lexicalScope != null && isScope) {
            String name = desc.getNameToken(2);
            if (this.lexicalScope.hasOwnProperty(name)) {
                return this.lexicalScope.findSetMethod(desc, request);
            }
        }
        GuardedInvocation invocation = super.findSetMethod(desc, request);
        if (isScope && this.context.getEnv()._es6) {
            return invocation.addSwitchPoint(getLexicalScopeSwitchPoint());
        }
        return invocation;
    }

    public void addShellBuiltins() {
        Object value = ScriptFunction.createBuiltin("input", ShellFunctions.INPUT);
        addOwnProperty("input", 2, value);
        Object value2 = ScriptFunction.createBuiltin("evalinput", ShellFunctions.EVALINPUT);
        addOwnProperty("evalinput", 2, value2);
    }

    private synchronized SwitchPoint getLexicalScopeSwitchPoint() {
        SwitchPoint switchPoint = this.lexicalScopeSwitchPoint;
        if (switchPoint == null || switchPoint.hasBeenInvalidated()) {
            SwitchPoint switchPoint2 = new SwitchPoint();
            this.lexicalScopeSwitchPoint = switchPoint2;
            switchPoint = switchPoint2;
        }
        return switchPoint;
    }

    private synchronized void invalidateLexicalSwitchPoint() {
        if (this.lexicalScopeSwitchPoint != null) {
            this.context.getLogger(GlobalConstants.class).info("Invalidating non-constant globals on lexical scope update");
            SwitchPoint.invalidateAll(new SwitchPoint[]{this.lexicalScopeSwitchPoint});
        }
    }

    private static Object lexicalScopeFilter(Object self) {
        if (self instanceof Global) {
            return ((Global) self).getLexicalScope();
        }
        return self;
    }

    private <T extends ScriptObject> T initConstructorAndSwitchPoint(String name, Class<T> clazz) {
        T func = (T) initConstructor(name, clazz);
        tagBuiltinProperties(name, func);
        return func;
    }

    private void init(ScriptEngine eng) {
        boolean debugOkay;
        if ($assertionsDisabled || Context.getGlobal() == this) {
            ScriptEnvironment env = getContext().getEnv();
            initFunctionAndObject();
            setInitialProto(getObjectPrototype());
            ScriptFunction createBuiltin = ScriptFunction.createBuiltin("eval", EVAL);
            this.builtinEval = createBuiltin;
            this.eval = createBuiltin;
            this.parseInt = ScriptFunction.createBuiltin("parseInt", GlobalFunctions.PARSEINT, new Specialization[]{new Specialization(GlobalFunctions.PARSEINT_Z), new Specialization(GlobalFunctions.PARSEINT_I), new Specialization(GlobalFunctions.PARSEINT_OI), new Specialization(GlobalFunctions.PARSEINT_O)});
            this.parseFloat = ScriptFunction.createBuiltin("parseFloat", GlobalFunctions.PARSEFLOAT);
            this.isNaN = ScriptFunction.createBuiltin("isNaN", GlobalFunctions.IS_NAN, new Specialization[]{new Specialization(GlobalFunctions.IS_NAN_I), new Specialization(GlobalFunctions.IS_NAN_J), new Specialization(GlobalFunctions.IS_NAN_D)});
            this.parseFloat = ScriptFunction.createBuiltin("parseFloat", GlobalFunctions.PARSEFLOAT);
            this.isNaN = ScriptFunction.createBuiltin("isNaN", GlobalFunctions.IS_NAN);
            this.isFinite = ScriptFunction.createBuiltin("isFinite", GlobalFunctions.IS_FINITE);
            this.encodeURI = ScriptFunction.createBuiltin("encodeURI", GlobalFunctions.ENCODE_URI);
            this.encodeURIComponent = ScriptFunction.createBuiltin("encodeURIComponent", GlobalFunctions.ENCODE_URICOMPONENT);
            this.decodeURI = ScriptFunction.createBuiltin("decodeURI", GlobalFunctions.DECODE_URI);
            this.decodeURIComponent = ScriptFunction.createBuiltin("decodeURIComponent", GlobalFunctions.DECODE_URICOMPONENT);
            this.escape = ScriptFunction.createBuiltin("escape", GlobalFunctions.ESCAPE);
            this.unescape = ScriptFunction.createBuiltin("unescape", GlobalFunctions.UNESCAPE);
            this.print = ScriptFunction.createBuiltin("print", env._print_no_newline ? PRINT : PRINTLN);
            this.load = ScriptFunction.createBuiltin("load", LOAD);
            this.loadWithNewGlobal = ScriptFunction.createBuiltin("loadWithNewGlobal", LOAD_WITH_NEW_GLOBAL);
            this.exit = ScriptFunction.createBuiltin("exit", EXIT);
            this.quit = ScriptFunction.createBuiltin("quit", EXIT);
            this.builtinArray = (ScriptFunction) initConstructorAndSwitchPoint("Array", ScriptFunction.class);
            this.builtinBoolean = (ScriptFunction) initConstructorAndSwitchPoint("Boolean", ScriptFunction.class);
            this.builtinNumber = (ScriptFunction) initConstructorAndSwitchPoint("Number", ScriptFunction.class);
            this.builtinString = (ScriptFunction) initConstructorAndSwitchPoint("String", ScriptFunction.class);
            this.builtinMath = initConstructorAndSwitchPoint("Math", ScriptObject.class);
            ScriptObject stringPrototype = getStringPrototype();
            stringPrototype.addOwnProperty("length", 7, Double.valueOf(0.0d));
            ScriptObject arrayPrototype = getArrayPrototype();
            arrayPrototype.setIsArray();
            initErrorObjects();
            if (!env._no_java) {
                this.javaApi = LAZY_SENTINEL;
                this.javaImporter = LAZY_SENTINEL;
                initJavaAccess();
            } else {
                delete("Java", false);
                delete("JavaImporter", false);
                delete("Packages", false);
                delete("com", false);
                delete("edu", false);
                delete("java", false);
                delete("javafx", false);
                delete("javax", false);
                delete("org", false);
            }
            if (!env._no_typed_arrays) {
                this.arrayBuffer = LAZY_SENTINEL;
                this.dataView = LAZY_SENTINEL;
                this.int8Array = LAZY_SENTINEL;
                this.uint8Array = LAZY_SENTINEL;
                this.uint8ClampedArray = LAZY_SENTINEL;
                this.int16Array = LAZY_SENTINEL;
                this.uint16Array = LAZY_SENTINEL;
                this.int32Array = LAZY_SENTINEL;
                this.uint32Array = LAZY_SENTINEL;
                this.float32Array = LAZY_SENTINEL;
                this.float64Array = LAZY_SENTINEL;
            }
            if (env._scripting) {
                initScripting(env);
            }
            if (Context.DEBUG) {
                SecurityManager sm = System.getSecurityManager();
                if (sm != null) {
                    try {
                        sm.checkPermission(new RuntimePermission(Context.NASHORN_DEBUG_MODE));
                        debugOkay = true;
                    } catch (SecurityException e) {
                        debugOkay = false;
                    }
                } else {
                    debugOkay = true;
                }
                if (debugOkay) {
                    initDebug();
                }
            }
            copyBuiltins();
            this.arguments = wrapAsObject(env.getArguments().toArray());
            if (env._scripting) {
                addOwnProperty("$ARG", 2, this.arguments);
            }
            if (eng != null) {
                addOwnProperty("javax.script.filename", 2, null);
                ScriptFunction noSuchProp = ScriptFunction.createStrictBuiltin(ScriptObject.NO_SUCH_PROPERTY_NAME, NO_SUCH_PROPERTY);
                addOwnProperty(ScriptObject.NO_SUCH_PROPERTY_NAME, 2, noSuchProp);
                return;
            }
            return;
        }
        throw new AssertionError("this global is not set as current");
    }

    private void initErrorObjects() {
        this.builtinError = (ScriptFunction) initConstructor("Error", ScriptFunction.class);
        ScriptObject errorProto = getErrorPrototype();
        ScriptFunction getStack = ScriptFunction.createBuiltin("getStack", NativeError.GET_STACK);
        ScriptFunction setStack = ScriptFunction.createBuiltin("setStack", NativeError.SET_STACK);
        errorProto.addOwnProperty("stack", 2, getStack, setStack);
        ScriptFunction getLineNumber = ScriptFunction.createBuiltin("getLineNumber", NativeError.GET_LINENUMBER);
        ScriptFunction setLineNumber = ScriptFunction.createBuiltin("setLineNumber", NativeError.SET_LINENUMBER);
        errorProto.addOwnProperty("lineNumber", 2, getLineNumber, setLineNumber);
        ScriptFunction getColumnNumber = ScriptFunction.createBuiltin("getColumnNumber", NativeError.GET_COLUMNNUMBER);
        ScriptFunction setColumnNumber = ScriptFunction.createBuiltin("setColumnNumber", NativeError.SET_COLUMNNUMBER);
        errorProto.addOwnProperty("columnNumber", 2, getColumnNumber, setColumnNumber);
        ScriptFunction getFileName = ScriptFunction.createBuiltin("getFileName", NativeError.GET_FILENAME);
        ScriptFunction setFileName = ScriptFunction.createBuiltin("setFileName", NativeError.SET_FILENAME);
        errorProto.addOwnProperty("fileName", 2, getFileName, setFileName);
        errorProto.set("name", "Error", 0);
        errorProto.set("message", "", 0);
        tagBuiltinProperties("Error", this.builtinError);
        this.builtinReferenceError = initErrorSubtype("ReferenceError", errorProto);
        this.builtinSyntaxError = initErrorSubtype("SyntaxError", errorProto);
        this.builtinTypeError = initErrorSubtype("TypeError", errorProto);
    }

    private ScriptFunction initErrorSubtype(String name, ScriptObject errorProto) {
        ScriptFunction cons = (ScriptFunction) initConstructor(name, ScriptFunction.class);
        ScriptObject prototype = ScriptFunction.getPrototype(cons);
        prototype.set("name", name, 0);
        prototype.set("message", "", 0);
        prototype.setInitialProto(errorProto);
        tagBuiltinProperties(name, cons);
        return cons;
    }

    private void initJavaAccess() {
        ScriptObject objectProto = getObjectPrototype();
        this.builtinPackages = new NativeJavaPackage("", objectProto);
        this.builtinCom = new NativeJavaPackage("com", objectProto);
        this.builtinEdu = new NativeJavaPackage("edu", objectProto);
        this.builtinJava = new NativeJavaPackage("java", objectProto);
        this.builtinJavafx = new NativeJavaPackage("javafx", objectProto);
        this.builtinJavax = new NativeJavaPackage("javax", objectProto);
        this.builtinOrg = new NativeJavaPackage("org", objectProto);
    }

    private void initScripting(ScriptEnvironment scriptEnv) {
        ScriptObject value = ScriptFunction.createBuiltin("readLine", ScriptingFunctions.READLINE);
        addOwnProperty("readLine", 2, value);
        ScriptObject value2 = ScriptFunction.createBuiltin("readFully", ScriptingFunctions.READFULLY);
        addOwnProperty("readFully", 2, value2);
        ScriptObject value3 = ScriptFunction.createBuiltin(ScriptingFunctions.EXEC_NAME, ScriptingFunctions.EXEC);
        value3.addOwnProperty(ScriptingFunctions.THROW_ON_ERROR_NAME, 2, false);
        addOwnProperty(ScriptingFunctions.EXEC_NAME, 2, value3);
        ScriptObject value4 = (ScriptObject) get("print");
        addOwnProperty("echo", 2, value4);
        ScriptObject options = newObject();
        copyOptions(options, scriptEnv);
        addOwnProperty("$OPTIONS", 2, options);
        if (System.getSecurityManager() == null) {
            ScriptObject env = newObject();
            env.putAll(System.getenv(), scriptEnv._strict);
            if (!env.containsKey(ScriptingFunctions.PWD_NAME)) {
                env.put(ScriptingFunctions.PWD_NAME, System.getProperty("user.dir"), scriptEnv._strict);
            }
            addOwnProperty(ScriptingFunctions.ENV_NAME, 2, env);
        } else {
            addOwnProperty(ScriptingFunctions.ENV_NAME, 2, ScriptRuntime.UNDEFINED);
        }
        addOwnProperty(ScriptingFunctions.OUT_NAME, 2, ScriptRuntime.UNDEFINED);
        addOwnProperty(ScriptingFunctions.ERR_NAME, 2, ScriptRuntime.UNDEFINED);
        addOwnProperty(ScriptingFunctions.EXIT_NAME, 2, ScriptRuntime.UNDEFINED);
    }

    private static void copyOptions(ScriptObject options, ScriptEnvironment scriptEnv) {
        Field[] fields;
        for (Field f : scriptEnv.getClass().getFields()) {
            try {
                options.set(f.getName(), f.get(scriptEnv), 0);
            } catch (IllegalAccessException | IllegalArgumentException exp) {
                throw new RuntimeException(exp);
            }
        }
    }

    private void copyBuiltins() {
        this.array = this.builtinArray;
        this._boolean = this.builtinBoolean;
        this.error = this.builtinError;
        this.function = this.builtinFunction;
        this.f464com = this.builtinCom;
        this.edu = this.builtinEdu;
        this.java = this.builtinJava;
        this.javafx = this.builtinJavafx;
        this.javax = this.builtinJavax;
        this.f465org = this.builtinOrg;
        this.math = this.builtinMath;
        this.number = this.builtinNumber;
        this.object = this.builtinObject;
        this.packages = this.builtinPackages;
        this.referenceError = this.builtinReferenceError;
        this.string = this.builtinString;
        this.syntaxError = this.builtinSyntaxError;
        this.typeError = this.builtinTypeError;
    }

    private void initDebug() {
        addOwnProperty("Debug", 2, initConstructor("Debug", ScriptObject.class));
    }

    private Object printImpl(boolean newLine, Object... objects) {
        ScriptContext sc = currentContext();
        PrintWriter out = sc != null ? new PrintWriter(sc.getWriter()) : getContext().getEnv().getOut();
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append(JSType.toString(obj));
        }
        if (newLine) {
            out.println(sb.toString());
        } else {
            out.print(sb.toString());
        }
        out.flush();
        return ScriptRuntime.UNDEFINED;
    }

    private <T extends ScriptObject> T initConstructor(String name, Class<T> clazz) {
        try {
            Class<?> funcClass = Class.forName("jdk.nashorn.internal.objects.Native" + name + "$Constructor");
            T res = clazz.cast(funcClass.newInstance());
            if (res instanceof ScriptFunction) {
                ScriptFunction func = (ScriptFunction) res;
                func.modifyOwnProperty(func.getProperty("prototype"), 7);
            }
            if (res.getProto() == null) {
                res.setInitialProto(getObjectPrototype());
            }
            res.setIsBuiltin();
            return res;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Property> extractBuiltinProperties(String name, ScriptObject func) {
        ScriptObject proto;
        List<Property> list = new ArrayList<>();
        list.addAll(Arrays.asList(func.getMap().getProperties()));
        if ((func instanceof ScriptFunction) && (proto = ScriptFunction.getPrototype((ScriptFunction) func)) != null) {
            list.addAll(Arrays.asList(proto.getMap().getProperties()));
        }
        Property prop = getProperty(name);
        if (prop != null) {
            list.add(prop);
        }
        return list;
    }

    private void tagBuiltinProperties(String name, ScriptObject func) {
        SwitchPoint sp = this.context.getBuiltinSwitchPoint(name);
        if (sp == null) {
            sp = this.context.newBuiltinSwitchPoint(name);
        }
        for (Property prop : extractBuiltinProperties(name, func)) {
            prop.setBuiltinSwitchPoint(sp);
        }
    }

    private void initFunctionAndObject() {
        Property[] properties;
        this.builtinFunction = (ScriptFunction) initConstructor("Function", ScriptFunction.class);
        ScriptFunction anon = ScriptFunction.createAnonymous();
        anon.addBoundProperties(getFunctionPrototype());
        this.builtinFunction.setInitialProto(anon);
        this.builtinFunction.setPrototype(anon);
        anon.set("constructor", this.builtinFunction, 0);
        anon.deleteOwnProperty(anon.getMap().findProperty("prototype"));
        this.typeErrorThrower = ScriptFunction.createBuiltin("TypeErrorThrower", Lookup.TYPE_ERROR_THROWER_GETTER);
        this.typeErrorThrower.preventExtensions();
        this.builtinObject = (ScriptFunction) initConstructor("Object", ScriptFunction.class);
        ScriptObject ObjectPrototype = getObjectPrototype();
        anon.setInitialProto(ObjectPrototype);
        ScriptFunction getProto = ScriptFunction.createBuiltin("getProto", NativeObject.GET__PROTO__);
        ScriptFunction setProto = ScriptFunction.createBuiltin("setProto", NativeObject.SET__PROTO__);
        ObjectPrototype.addOwnProperty(ScriptObject.PROTO_PROPERTY_NAME, 2, getProto, setProto);
        Property[] properties2 = getFunctionPrototype().getMap().getProperties();
        for (Property property : properties2) {
            Object value = this.builtinFunction.get(property.getKey());
            if ((value instanceof ScriptFunction) && value != anon) {
                ScriptFunction func = (ScriptFunction) value;
                func.setInitialProto(getFunctionPrototype());
                ScriptObject prototype = ScriptFunction.getPrototype(func);
                if (prototype != null) {
                    prototype.setInitialProto(ObjectPrototype);
                }
            }
        }
        for (Property property2 : this.builtinObject.getMap().getProperties()) {
            Object value2 = this.builtinObject.get(property2.getKey());
            if (value2 instanceof ScriptFunction) {
                ScriptFunction func2 = (ScriptFunction) value2;
                ScriptObject prototype2 = ScriptFunction.getPrototype(func2);
                if (prototype2 != null) {
                    prototype2.setInitialProto(ObjectPrototype);
                }
            }
        }
        Property[] properties3 = getObjectPrototype().getMap().getProperties();
        for (Property property3 : properties3) {
            Object key = property3.getKey();
            if (!key.equals("constructor")) {
                Object value3 = ObjectPrototype.get(key);
                if (value3 instanceof ScriptFunction) {
                    ScriptFunction func3 = (ScriptFunction) value3;
                    ScriptObject prototype3 = ScriptFunction.getPrototype(func3);
                    if (prototype3 != null) {
                        prototype3.setInitialProto(ObjectPrototype);
                    }
                }
            }
        }
        tagBuiltinProperties("Object", this.builtinObject);
        tagBuiltinProperties("Function", this.builtinFunction);
        tagBuiltinProperties("Function", anon);
    }

    private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), Global.class, name, Lookup.f248MH.type(rtype, types));
    }

    public RegExpResult getLastRegExpResult() {
        return this.lastRegExpResult;
    }

    public void setLastRegExpResult(RegExpResult regExpResult) {
        this.lastRegExpResult = regExpResult;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isGlobal() {
        return true;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/Global$LexicalScope.class */
    public static class LexicalScope extends ScriptObject {
        LexicalScope(Global global) {
            super(global, PropertyMap.newMap());
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
            return filterInvocation(super.findGetMethod(desc, request, operator));
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
            return filterInvocation(super.findSetMethod(desc, request));
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public PropertyMap addBoundProperty(PropertyMap propMap, ScriptObject source, Property property, boolean extensible) {
            return super.addBoundProperty(propMap, source, property, extensible);
        }

        private static GuardedInvocation filterInvocation(GuardedInvocation invocation) {
            MethodType type = invocation.getInvocation().type();
            return invocation.asType(type.changeParameterType(0, Object.class)).filterArguments(0, Global.LEXICAL_SCOPE_FILTER);
        }
    }
}
