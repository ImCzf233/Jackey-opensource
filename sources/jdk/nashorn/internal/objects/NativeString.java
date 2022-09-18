package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;
import kotlin.jvm.internal.CharCompanionObject;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString.class */
public final class NativeString extends ScriptObject implements OptimisticBuiltins {
    private final CharSequence value;
    static final MethodHandle WRAPFILTER = null;
    private static final MethodHandle PROTOFILTER = null;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object fromCharCode;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$fromCharCode() {
            return this.fromCharCode;
        }

        public void S$fromCharCode(Object obj) {
            this.fromCharCode = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object valueOf;
        private Object charAt;
        private Object charCodeAt;
        private Object concat;
        private Object indexOf;
        private Object lastIndexOf;
        private Object localeCompare;
        private Object match;
        private Object replace;
        private Object search;
        private Object slice;
        private Object split;
        private Object substr;
        private Object substring;
        private Object toLowerCase;
        private Object toLocaleLowerCase;
        private Object toUpperCase;
        private Object toLocaleUpperCase;
        private Object trim;
        private Object trimLeft;
        private Object trimRight;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        public Object G$charAt() {
            return this.charAt;
        }

        public void S$charAt(Object obj) {
            this.charAt = obj;
        }

        public Object G$charCodeAt() {
            return this.charCodeAt;
        }

        public void S$charCodeAt(Object obj) {
            this.charCodeAt = obj;
        }

        public Object G$concat() {
            return this.concat;
        }

        public void S$concat(Object obj) {
            this.concat = obj;
        }

        public Object G$indexOf() {
            return this.indexOf;
        }

        public void S$indexOf(Object obj) {
            this.indexOf = obj;
        }

        public Object G$lastIndexOf() {
            return this.lastIndexOf;
        }

        public void S$lastIndexOf(Object obj) {
            this.lastIndexOf = obj;
        }

        public Object G$localeCompare() {
            return this.localeCompare;
        }

        public void S$localeCompare(Object obj) {
            this.localeCompare = obj;
        }

        public Object G$match() {
            return this.match;
        }

        public void S$match(Object obj) {
            this.match = obj;
        }

        public Object G$replace() {
            return this.replace;
        }

        public void S$replace(Object obj) {
            this.replace = obj;
        }

        public Object G$search() {
            return this.search;
        }

        public void S$search(Object obj) {
            this.search = obj;
        }

        public Object G$slice() {
            return this.slice;
        }

        public void S$slice(Object obj) {
            this.slice = obj;
        }

        public Object G$split() {
            return this.split;
        }

        public void S$split(Object obj) {
            this.split = obj;
        }

        public Object G$substr() {
            return this.substr;
        }

        public void S$substr(Object obj) {
            this.substr = obj;
        }

        public Object G$substring() {
            return this.substring;
        }

        public void S$substring(Object obj) {
            this.substring = obj;
        }

        public Object G$toLowerCase() {
            return this.toLowerCase;
        }

        public void S$toLowerCase(Object obj) {
            this.toLowerCase = obj;
        }

        public Object G$toLocaleLowerCase() {
            return this.toLocaleLowerCase;
        }

        public void S$toLocaleLowerCase(Object obj) {
            this.toLocaleLowerCase = obj;
        }

        public Object G$toUpperCase() {
            return this.toUpperCase;
        }

        public void S$toUpperCase(Object obj) {
            this.toUpperCase = obj;
        }

        public Object G$toLocaleUpperCase() {
            return this.toLocaleUpperCase;
        }

        public void S$toLocaleUpperCase(Object obj) {
            this.toLocaleUpperCase = obj;
        }

        public Object G$trim() {
            return this.trim;
        }

        public void S$trim(Object obj) {
            this.trim = obj;
        }

        public Object G$trimLeft() {
            return this.trimLeft;
        }

        public void S$trimLeft(Object obj) {
            this.trimLeft = obj;
        }

        public Object G$trimRight() {
            return this.trimRight;
        }

        public void S$trimRight(Object obj) {
            this.trimRight = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:409)
            	at jadx.core.ProcessClass.process(ProcessClass.java:67)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Prototype() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "String";
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000D: CONST, method: jdk.nashorn.internal.objects.NativeString.$clinit$():void
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:49)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        */
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 1
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "length"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = 0
            r5 = 1
            boolean r4 = r4.add(r5)
            jdk.nashorn.internal.runtime.PropertyMap r3 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r3)
            jdk.nashorn.internal.objects.NativeString.$nasgenmap$ = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.$clinit$():void");
    }

    static {
        $assertionsDisabled = !NativeString.class.desiredAssertionStatus();
        WRAPFILTER = findOwnMH("wrapFilter", Lookup.f248MH.type(NativeString.class, Object.class));
        PROTOFILTER = findOwnMH("protoFilter", Lookup.f248MH.type(Object.class, Object.class));
        $clinit$();
    }

    private NativeString(CharSequence value) {
        this(value, Global.instance());
    }

    public NativeString(CharSequence value, Global global) {
        this(value, global.getStringPrototype(), $nasgenmap$);
    }

    private NativeString(CharSequence value, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        if ($assertionsDisabled || JSType.isString(value)) {
            this.value = value;
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[String " + toString() + "]";
    }

    public String toString() {
        return getStringValue();
    }

    public boolean equals(Object other) {
        if (other instanceof NativeString) {
            return getStringValue().equals(((NativeString) other).getStringValue());
        }
        return false;
    }

    public int hashCode() {
        return getStringValue().hashCode();
    }

    private String getStringValue() {
        return this.value instanceof String ? (String) this.value : this.value.toString();
    }

    private CharSequence getValue() {
        return this.value;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "String";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getLength() {
        return Integer.valueOf(this.value.length());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
        String name = desc.getNameToken(2);
        if ("length".equals(name) && "getMethod".equals(operator)) {
            return null;
        }
        return super.findGetMethod(desc, request, operator);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
        Object self = request.getReceiver();
        Class<?> returnType = desc.getMethodType().returnType();
        if (returnType == Object.class && JSType.isString(self)) {
            try {
                return new GuardedInvocation(Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeString.class, PropertyDescriptor.GET, desc.getMethodType()), NashornGuards.getStringGuard());
            } catch (MethodHandleFactory.LookupException e) {
            }
        }
        return super.findGetIndexMethod(desc, request);
    }

    private static Object get(Object self, Object key) {
        CharSequence cs = JSType.toCharSequence(self);
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (index >= 0 && index < cs.length()) {
            return String.valueOf(cs.charAt(index));
        }
        return ((ScriptObject) Global.toObject(self)).get(primitiveKey);
    }

    private static Object get(Object self, double key) {
        if (JSType.isRepresentableAsInt(key)) {
            return get(self, (int) key);
        }
        return ((ScriptObject) Global.toObject(self)).get(key);
    }

    private static Object get(Object self, long key) {
        CharSequence cs = JSType.toCharSequence(self);
        if (key >= 0 && key < cs.length()) {
            return String.valueOf(cs.charAt((int) key));
        }
        return ((ScriptObject) Global.toObject(self)).get(key);
    }

    private static Object get(Object self, int key) {
        CharSequence cs = JSType.toCharSequence(self);
        if (key >= 0 && key < cs.length()) {
            return String.valueOf(cs.charAt(key));
        }
        return ((ScriptObject) Global.toObject(self)).get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object key) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (index >= 0 && index < this.value.length()) {
            return String.valueOf(this.value.charAt(index));
        }
        return super.get(primitiveKey);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double key) {
        if (JSType.isRepresentableAsInt(key)) {
            return get((int) key);
        }
        return super.get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int key) {
        if (key >= 0 && key < this.value.length()) {
            return String.valueOf(this.value.charAt(key));
        }
        return super.get(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object key, int programPoint) {
        return JSType.toInt32MaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double key, int programPoint) {
        return JSType.toInt32MaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int key, int programPoint) {
        return JSType.toInt32MaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object key, int programPoint) {
        return JSType.toNumberMaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double key, int programPoint) {
        return JSType.toNumberMaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int key, int programPoint) {
        return JSType.toNumberMaybeOptimistic(get(key), programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(Object key) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        return isValidStringIndex(index) || super.has(primitiveKey);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int key) {
        return isValidStringIndex(key) || super.has(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double key) {
        int index = ArrayIndex.getArrayIndex(key);
        return isValidStringIndex(index) || super.has(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(Object key) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        return isValidStringIndex(index) || super.hasOwnProperty(primitiveKey);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(int key) {
        return isValidStringIndex(key) || super.hasOwnProperty(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(double key) {
        int index = ArrayIndex.getArrayIndex(key);
        return isValidStringIndex(index) || super.hasOwnProperty(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int key, boolean strict) {
        if (checkDeleteIndex(key, strict)) {
            return false;
        }
        return super.delete(key, strict);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double key, boolean strict) {
        int index = ArrayIndex.getArrayIndex(key);
        if (checkDeleteIndex(index, strict)) {
            return false;
        }
        return super.delete(key, strict);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object key, boolean strict) {
        Object primitiveKey = JSType.toPrimitive(key, String.class);
        int index = ArrayIndex.getArrayIndex(primitiveKey);
        if (checkDeleteIndex(index, strict)) {
            return false;
        }
        return super.delete(primitiveKey, strict);
    }

    private boolean checkDeleteIndex(int index, boolean strict) {
        if (isValidStringIndex(index)) {
            if (strict) {
                throw ECMAErrors.typeError("cant.delete.property", Integer.toString(index), ScriptRuntime.safeToString(this));
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getOwnPropertyDescriptor(String key) {
        int index = ArrayIndex.getArrayIndex(key);
        if (index >= 0 && index < this.value.length()) {
            Global global = Global.instance();
            return global.newDataDescriptor(String.valueOf(this.value.charAt(index)), false, true, false);
        }
        return super.getOwnPropertyDescriptor(key);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String[] getOwnKeys(boolean all, Set<String> nonEnumerable) {
        List<Object> keys = new ArrayList<>();
        for (int i = 0; i < this.value.length(); i++) {
            keys.add(JSType.toString(i));
        }
        keys.addAll(Arrays.asList(super.getOwnKeys(all, nonEnumerable)));
        return (String[]) keys.toArray(new String[keys.size()]);
    }

    public static Object length(Object self) {
        return Integer.valueOf(getCharSequence(self).length());
    }

    public static String fromCharCode(Object self, Object... args) {
        char[] buf = new char[args.length];
        int index = 0;
        for (Object arg : args) {
            int i = index;
            index++;
            buf[i] = (char) JSType.toUint16(arg);
        }
        return new String(buf);
    }

    public static Object fromCharCode(Object self, Object value) {
        if (value instanceof Integer) {
            return fromCharCode(self, ((Integer) value).intValue());
        }
        return Character.toString((char) JSType.toUint16(value));
    }

    public static String fromCharCode(Object self, int value) {
        return Character.toString((char) (value & CharCompanionObject.MAX_VALUE));
    }

    public static Object fromCharCode(Object self, int ch1, int ch2) {
        return Character.toString((char) (ch1 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch2 & CharCompanionObject.MAX_VALUE));
    }

    public static Object fromCharCode(Object self, int ch1, int ch2, int ch3) {
        return Character.toString((char) (ch1 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch2 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch3 & CharCompanionObject.MAX_VALUE));
    }

    public static String fromCharCode(Object self, int ch1, int ch2, int ch3, int ch4) {
        return Character.toString((char) (ch1 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch2 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch3 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (ch4 & CharCompanionObject.MAX_VALUE));
    }

    public static String fromCharCode(Object self, double value) {
        return Character.toString((char) JSType.toUint16(value));
    }

    public static String toString(Object self) {
        return getString(self);
    }

    public static String valueOf(Object self) {
        return getString(self);
    }

    public static String charAt(Object self, Object pos) {
        return charAtImpl(checkObjectToString(self), JSType.toInteger(pos));
    }

    public static String charAt(Object self, double pos) {
        return charAt(self, (int) pos);
    }

    public static String charAt(Object self, int pos) {
        return charAtImpl(checkObjectToString(self), pos);
    }

    private static String charAtImpl(String str, int pos) {
        return (pos < 0 || pos >= str.length()) ? "" : String.valueOf(str.charAt(pos));
    }

    private static int getValidChar(Object self, int pos) {
        try {
            return ((CharSequence) self).charAt(pos);
        } catch (IndexOutOfBoundsException e) {
            throw new ClassCastException();
        }
    }

    public static double charCodeAt(Object self, Object pos) {
        String str = checkObjectToString(self);
        int idx = JSType.toInteger(pos);
        if (idx < 0 || idx >= str.length()) {
            return Double.NaN;
        }
        return str.charAt(idx);
    }

    public static int charCodeAt(Object self, double pos) {
        return charCodeAt(self, (int) pos);
    }

    public static int charCodeAt(Object self, long pos) {
        return charCodeAt(self, (int) pos);
    }

    public static int charCodeAt(Object self, int pos) {
        return getValidChar(self, pos);
    }

    public static Object concat(Object self, Object... args) {
        CharSequence cs = checkObjectToString(self);
        if (args != null) {
            for (Object obj : args) {
                cs = new ConsString(cs, JSType.toCharSequence(obj));
            }
        }
        return cs;
    }

    public static int indexOf(Object self, Object search, Object pos) {
        String str = checkObjectToString(self);
        return str.indexOf(JSType.toString(search), JSType.toInteger(pos));
    }

    public static int indexOf(Object self, Object search) {
        return indexOf(self, search, 0);
    }

    public static int indexOf(Object self, Object search, double pos) {
        return indexOf(self, search, (int) pos);
    }

    public static int indexOf(Object self, Object search, int pos) {
        return checkObjectToString(self).indexOf(JSType.toString(search), pos);
    }

    public static int lastIndexOf(Object self, Object search, Object pos) {
        int end;
        String str = checkObjectToString(self);
        String searchStr = JSType.toString(search);
        int length = str.length();
        if (pos == ScriptRuntime.UNDEFINED) {
            end = length;
        } else {
            double numPos = JSType.toNumber(pos);
            end = Double.isNaN(numPos) ? length : (int) numPos;
            if (end < 0) {
                end = 0;
            } else if (end > length) {
                end = length;
            }
        }
        return str.lastIndexOf(searchStr, end);
    }

    public static double localeCompare(Object self, Object that) {
        String str = checkObjectToString(self);
        Collator collator = Collator.getInstance(Global.getEnv()._locale);
        collator.setStrength(3);
        collator.setDecomposition(1);
        return collator.compare(str, JSType.toString(that));
    }

    public static ScriptObject match(Object self, Object regexp) {
        NativeRegExp nativeRegExp;
        int i;
        String str = checkObjectToString(self);
        if (regexp == ScriptRuntime.UNDEFINED) {
            nativeRegExp = new NativeRegExp("");
        } else {
            nativeRegExp = Global.toRegExp(regexp);
        }
        if (!nativeRegExp.getGlobal()) {
            return nativeRegExp.exec(str);
        }
        nativeRegExp.setLastIndex(0);
        int previousLastIndex = 0;
        List<Object> matches = new ArrayList<>();
        while (true) {
            Object result = nativeRegExp.exec(str);
            if (result == null) {
                break;
            }
            int thisIndex = nativeRegExp.getLastIndex();
            if (thisIndex == previousLastIndex) {
                nativeRegExp.setLastIndex(thisIndex + 1);
                i = thisIndex + 1;
            } else {
                i = thisIndex;
            }
            previousLastIndex = i;
            matches.add(((ScriptObject) result).get(0));
        }
        if (matches.isEmpty()) {
            return null;
        }
        return new NativeArray(matches.toArray());
    }

    public static String replace(Object self, Object string, Object replacement) throws Throwable {
        NativeRegExp nativeRegExp;
        String str = checkObjectToString(self);
        if (string instanceof NativeRegExp) {
            nativeRegExp = (NativeRegExp) string;
        } else {
            nativeRegExp = NativeRegExp.flatRegExp(JSType.toString(string));
        }
        if (Bootstrap.isCallable(replacement)) {
            return nativeRegExp.replace(str, "", replacement);
        }
        return nativeRegExp.replace(str, JSType.toString(replacement), null);
    }

    public static int search(Object self, Object string) {
        String str = checkObjectToString(self);
        NativeRegExp nativeRegExp = Global.toRegExp(string == ScriptRuntime.UNDEFINED ? "" : string);
        return nativeRegExp.search(str);
    }

    public static String slice(Object self, Object start, Object end) {
        String str = checkObjectToString(self);
        if (end == ScriptRuntime.UNDEFINED) {
            return slice((Object) str, JSType.toInteger(start));
        }
        return slice((Object) str, JSType.toInteger(start), JSType.toInteger(end));
    }

    public static String slice(Object self, int start) {
        String str = checkObjectToString(self);
        int from = start < 0 ? Math.max(str.length() + start, 0) : Math.min(start, str.length());
        return str.substring(from);
    }

    public static String slice(Object self, double start) {
        return slice(self, (int) start);
    }

    public static String slice(Object self, int start, int end) {
        String str = checkObjectToString(self);
        int len = str.length();
        int from = start < 0 ? Math.max(len + start, 0) : Math.min(start, len);
        int to = end < 0 ? Math.max(len + end, 0) : Math.min(end, len);
        return str.substring(Math.min(from, to), to);
    }

    public static String slice(Object self, double start, double end) {
        return slice(self, (int) start, (int) end);
    }

    public static ScriptObject split(Object self, Object separator, Object limit) {
        String str = checkObjectToString(self);
        long lim = limit == ScriptRuntime.UNDEFINED ? JSType.MAX_UINT : JSType.toUint32(limit);
        if (separator == ScriptRuntime.UNDEFINED) {
            return lim == 0 ? new NativeArray() : new NativeArray(new Object[]{str});
        } else if (separator instanceof NativeRegExp) {
            return ((NativeRegExp) separator).split(str, lim);
        } else {
            return splitString(str, JSType.toString(separator), lim);
        }
    }

    private static ScriptObject splitString(String str, String separator, long limit) {
        int found;
        if (separator.isEmpty()) {
            int length = (int) Math.min(str.length(), limit);
            Object[] array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = String.valueOf(str.charAt(i));
            }
            return new NativeArray(array);
        }
        List<String> elements = new LinkedList<>();
        int strLength = str.length();
        int sepLength = separator.length();
        int pos = 0;
        int n = 0;
        while (pos < strLength && n < limit && (found = str.indexOf(separator, pos)) != -1) {
            elements.add(str.substring(pos, found));
            n++;
            pos = found + sepLength;
        }
        if (pos <= strLength && n < limit) {
            elements.add(str.substring(pos));
        }
        return new NativeArray(elements.toArray());
    }

    public static String substr(Object self, Object start, Object length) {
        String str = JSType.toString(self);
        int strLength = str.length();
        int intStart = JSType.toInteger(start);
        if (intStart < 0) {
            intStart = Math.max(intStart + strLength, 0);
        }
        int intLen = Math.min(Math.max(length == ScriptRuntime.UNDEFINED ? Integer.MAX_VALUE : JSType.toInteger(length), 0), strLength - intStart);
        return intLen <= 0 ? "" : str.substring(intStart, intStart + intLen);
    }

    public static String substring(Object self, Object start, Object end) {
        String str = checkObjectToString(self);
        if (end == ScriptRuntime.UNDEFINED) {
            return substring((Object) str, JSType.toInteger(start));
        }
        return substring((Object) str, JSType.toInteger(start), JSType.toInteger(end));
    }

    public static String substring(Object self, int start) {
        String str = checkObjectToString(self);
        if (start < 0) {
            return str;
        }
        if (start >= str.length()) {
            return "";
        }
        return str.substring(start);
    }

    public static String substring(Object self, double start) {
        return substring(self, (int) start);
    }

    public static String substring(Object self, int start, int end) {
        String str = checkObjectToString(self);
        int len = str.length();
        int validStart = start < 0 ? 0 : start > len ? len : start;
        int validEnd = end < 0 ? 0 : end > len ? len : end;
        if (validStart < validEnd) {
            return str.substring(validStart, validEnd);
        }
        return str.substring(validEnd, validStart);
    }

    public static String substring(Object self, double start, double end) {
        return substring(self, (int) start, (int) end);
    }

    public static String toLowerCase(Object self) {
        return checkObjectToString(self).toLowerCase(Locale.ROOT);
    }

    public static String toLocaleLowerCase(Object self) {
        return checkObjectToString(self).toLowerCase(Global.getEnv()._locale);
    }

    public static String toUpperCase(Object self) {
        return checkObjectToString(self).toUpperCase(Locale.ROOT);
    }

    public static String toLocaleUpperCase(Object self) {
        return checkObjectToString(self).toUpperCase(Global.getEnv()._locale);
    }

    public static String trim(Object self) {
        String str = checkObjectToString(self);
        int start = 0;
        int end = str.length() - 1;
        while (start <= end && ScriptRuntime.isJSWhitespace(str.charAt(start))) {
            start++;
        }
        while (end > start && ScriptRuntime.isJSWhitespace(str.charAt(end))) {
            end--;
        }
        return str.substring(start, end + 1);
    }

    public static String trimLeft(Object self) {
        String str = checkObjectToString(self);
        int start = 0;
        int end = str.length() - 1;
        while (start <= end && ScriptRuntime.isJSWhitespace(str.charAt(start))) {
            start++;
        }
        return str.substring(start, end + 1);
    }

    public static String trimRight(Object self) {
        String str = checkObjectToString(self);
        int end = str.length() - 1;
        while (end >= 0 && ScriptRuntime.isJSWhitespace(str.charAt(end))) {
            end--;
        }
        return str.substring(0, end + 1);
    }

    private static ScriptObject newObj(CharSequence str) {
        return new NativeString(str);
    }

    public static Object constructor(boolean newObj, Object self, Object... args) {
        CharSequence str = args.length > 0 ? JSType.toCharSequence(args[0]) : "";
        return newObj ? newObj(str) : str.toString();
    }

    public static Object constructor(boolean newObj, Object self) {
        return newObj ? newObj("") : "";
    }

    public static Object constructor(boolean newObj, Object self, Object arg) {
        CharSequence str = JSType.toCharSequence(arg);
        return newObj ? newObj(str) : str.toString();
    }

    public static Object constructor(boolean newObj, Object self, int arg) {
        String str = Integer.toString(arg);
        return newObj ? newObj(str) : str;
    }

    public static Object constructor(boolean newObj, Object self, long arg) {
        String str = Long.toString(arg);
        return newObj ? newObj(str) : str;
    }

    public static Object constructor(boolean newObj, Object self, double arg) {
        String str = JSType.toString(arg);
        return newObj ? newObj(str) : str;
    }

    public static Object constructor(boolean newObj, Object self, boolean arg) {
        String str = Boolean.toString(arg);
        return newObj ? newObj(str) : str;
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest request, Object receiver) {
        return PrimitiveLookup.lookupPrimitive(request, NashornGuards.getStringGuard(), new NativeString((CharSequence) receiver), WRAPFILTER, PROTOFILTER);
    }

    private static NativeString wrapFilter(Object receiver) {
        return new NativeString((CharSequence) receiver);
    }

    private static Object protoFilter(Object object) {
        return Global.instance().getStringPrototype();
    }

    private static CharSequence getCharSequence(Object self) {
        if (JSType.isString(self)) {
            return (CharSequence) self;
        }
        if (self instanceof NativeString) {
            return ((NativeString) self).getValue();
        }
        if (self != null && self == Global.instance().getStringPrototype()) {
            return "";
        }
        throw ECMAErrors.typeError("not.a.string", ScriptRuntime.safeToString(self));
    }

    private static String getString(Object self) {
        if (self instanceof String) {
            return (String) self;
        }
        if (self instanceof ConsString) {
            return self.toString();
        }
        if (self instanceof NativeString) {
            return ((NativeString) self).getStringValue();
        }
        if (self != null && self == Global.instance().getStringPrototype()) {
            return "";
        }
        throw ECMAErrors.typeError("not.a.string", ScriptRuntime.safeToString(self));
    }

    private static String checkObjectToString(Object self) {
        if (self instanceof String) {
            return (String) self;
        }
        if (self instanceof ConsString) {
            return self.toString();
        }
        Global.checkObjectCoercible(self);
        return JSType.toString(self);
    }

    private boolean isValidStringIndex(int key) {
        return key >= 0 && key < this.value.length();
    }

    private static MethodHandle findOwnMH(String name, MethodType type) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeString.class, name, type);
    }

    @Override // jdk.nashorn.internal.runtime.OptimisticBuiltins
    public SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> clazz) {
        if (clazz != CharCodeAtLinkLogic.class) {
            return null;
        }
        return CharCodeAtLinkLogic.INSTANCE;
    }

    @Override // jdk.nashorn.internal.runtime.OptimisticBuiltins
    public boolean hasPerInstanceAssumptions() {
        return false;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeString$CharCodeAtLinkLogic.class */
    private static final class CharCodeAtLinkLogic extends SpecializedFunction.LinkLogic {
        private static final CharCodeAtLinkLogic INSTANCE = new CharCodeAtLinkLogic();

        private CharCodeAtLinkLogic() {
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
            try {
                CharSequence cs = (CharSequence) self;
                int intIndex = JSType.toInteger(request.getArguments()[2]);
                if (intIndex >= 0) {
                    if (intIndex < cs.length()) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | IndexOutOfBoundsException e) {
                return false;
            }
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public Class<? extends Throwable> getRelinkException() {
            return ClassCastException.class;
        }
    }
}
