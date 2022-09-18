package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeNumber.class */
public final class NativeNumber extends ScriptObject {
    static final MethodHandle WRAPFILTER = null;
    private static final MethodHandle PROTOFILTER = null;
    public static final double MAX_VALUE = Double.MAX_VALUE;
    public static final double MIN_VALUE = Double.MIN_VALUE;
    public static final double NaN = Double.NaN;
    public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
    private final double value;
    private static PropertyMap $nasgenmap$;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeNumber$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        public double G$MAX_VALUE() {
            return NativeNumber.MAX_VALUE;
        }

        public double G$MIN_VALUE() {
            return NativeNumber.MIN_VALUE;
        }

        public double G$NaN() {
            return NativeNumber.NaN;
        }

        public double G$NEGATIVE_INFINITY() {
            return NativeNumber.NEGATIVE_INFINITY;
        }

        public double G$POSITIVE_INFINITY() {
            return NativeNumber.POSITIVE_INFINITY;
        }

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Constructor.<init>():void
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
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Number"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r9 = r1
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                jdk.nashorn.internal.objects.NativeNumber$Prototype r-2 = new jdk.nashorn.internal.objects.NativeNumber$Prototype
                r-1 = r-2
                r-1.<init>()
                r-1 = r-2
                r0 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-1, r0)
                r-3.setPrototype(r-2)
                r-3 = r6
                r-2 = 1
                r-3.setArity(r-2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeNumber.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeNumber$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toFixed;
        private Object toExponential;
        private Object toPrecision;
        private Object toString;
        private Object toLocaleString;
        private Object valueOf;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toFixed() {
            return this.toFixed;
        }

        public void S$toFixed(Object obj) {
            this.toFixed = obj;
        }

        public Object G$toExponential() {
            return this.toExponential;
        }

        public void S$toExponential(Object obj) {
            this.toExponential = obj;
        }

        public Object G$toPrecision() {
            return this.toPrecision;
        }

        public void S$toPrecision(Object obj) {
            this.toPrecision = obj;
        }

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0015: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0024: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x002E: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0039: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0048: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0052: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x005C: CONST, method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void
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
        Prototype() {
            /*
                r11 = this;
                r0 = r11
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeNumber.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r11
                java.lang.String r1 = "toFixed"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                jdk.nashorn.internal.runtime.ScriptFunction r-3 = jdk.nashorn.internal.runtime.ScriptFunction.createBuiltin(r-3, r-2, r-1)
                r-4.toFixed = r-3
                r-4 = r11
                java.lang.String r-3 = "toExponential"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = (int) r-3
                r-4.toExponential = r-3
                r-4 = r11
                java.lang.String r-3 = "toPrecision"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                jdk.nashorn.internal.runtime.Specialization[] r-3 = new jdk.nashorn.internal.runtime.Specialization[r-3]
                r-2 = r-3
                r-1 = 0
                jdk.nashorn.internal.runtime.Specialization r0 = new jdk.nashorn.internal.runtime.Specialization
                r1 = r0
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-1.<init>(r0, r1)
                r-4[r-3] = r-2
                jdk.nashorn.internal.runtime.ScriptFunction r-7 = jdk.nashorn.internal.runtime.ScriptFunction.createBuiltin(r-7, r-6, r-5)
                r-8.toPrecision = r-7
                r-8 = r11
                java.lang.String r-7 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-7 = (int) r-7
                r-8.toString = r-7
                r-8 = r11
                java.lang.String r-7 = "toLocaleString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-7 = (int) r-7
                r-8.toLocaleString = r-7
                r-8 = r11
                java.lang.String r-7 = "valueOf"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-7 = (int) r-7
                r-8.valueOf = r-7
                return
                r-8 = 0
                if (r-8 > 0) goto L109
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Number";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeNumber.class.desiredAssertionStatus();
        WRAPFILTER = findOwnMH("wrapFilter", Lookup.f248MH.type(NativeNumber.class, Object.class));
        PROTOFILTER = findOwnMH("protoFilter", Lookup.f248MH.type(Object.class, Object.class));
        $clinit$();
    }

    private NativeNumber(double value, ScriptObject proto, PropertyMap map) {
        super(proto, map);
        this.value = value;
    }

    public NativeNumber(double value, Global global) {
        this(value, global.getNumberPrototype(), $nasgenmap$);
    }

    private NativeNumber(double value) {
        this(value, Global.instance());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[Number " + toString() + "]";
    }

    public String toString() {
        return Double.toString(getValue());
    }

    public double getValue() {
        return doubleValue();
    }

    public double doubleValue() {
        return this.value;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Number";
    }

    public static Object constructor(boolean newObj, Object self, Object... args) {
        double num = args.length > 0 ? JSType.toNumber(args[0]) : 0.0d;
        return newObj ? new NativeNumber(num) : Double.valueOf(num);
    }

    public static String toFixed(Object self, Object fractionDigits) {
        return toFixed(self, JSType.toInteger(fractionDigits));
    }

    public static String toFixed(Object self, int fractionDigits) {
        if (fractionDigits < 0 || fractionDigits > 20) {
            throw ECMAErrors.rangeError("invalid.fraction.digits", "toFixed");
        }
        double x = getNumberValue(self);
        if (Double.isNaN(x)) {
            return "NaN";
        }
        if (Math.abs(x) >= 1.0E21d) {
            return JSType.toString(x);
        }
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        format.setMinimumFractionDigits(fractionDigits);
        format.setMaximumFractionDigits(fractionDigits);
        format.setGroupingUsed(false);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(x);
    }

    public static String toExponential(Object self, Object fractionDigits) {
        double x = getNumberValue(self);
        boolean trimZeros = fractionDigits == ScriptRuntime.UNDEFINED;
        int f = trimZeros ? 16 : JSType.toInteger(fractionDigits);
        if (Double.isNaN(x)) {
            return "NaN";
        }
        if (Double.isInfinite(x)) {
            return x > 0.0d ? "Infinity" : "-Infinity";
        } else if (fractionDigits != ScriptRuntime.UNDEFINED && (f < 0 || f > 20)) {
            throw ECMAErrors.rangeError("invalid.fraction.digits", "toExponential");
        } else {
            String res = String.format(Locale.US, "%1." + f + "e", Double.valueOf(x));
            return fixExponent(res, trimZeros);
        }
    }

    public static String toPrecision(Object self, Object precision) {
        double x = getNumberValue(self);
        if (precision == ScriptRuntime.UNDEFINED) {
            return JSType.toString(x);
        }
        return toPrecision(x, JSType.toInteger(precision));
    }

    public static String toPrecision(Object self, int precision) {
        return toPrecision(getNumberValue(self), precision);
    }

    private static String toPrecision(double x, int p) {
        if (Double.isNaN(x)) {
            return "NaN";
        }
        if (Double.isInfinite(x)) {
            return x > 0.0d ? "Infinity" : "-Infinity";
        } else if (p < 1 || p > 21) {
            throw ECMAErrors.rangeError("invalid.precision", new String[0]);
        } else {
            return (x != 0.0d || p > 1) ? fixExponent(String.format(Locale.US, "%." + p + "g", Double.valueOf(x)), false) : "0";
        }
    }

    public static String toString(Object self, Object radix) {
        int intRadix;
        if (radix != ScriptRuntime.UNDEFINED && (intRadix = JSType.toInteger(radix)) != 10) {
            if (intRadix < 2 || intRadix > 36) {
                throw ECMAErrors.rangeError("invalid.radix", new String[0]);
            }
            return JSType.toString(getNumberValue(self), intRadix);
        }
        return JSType.toString(getNumberValue(self));
    }

    public static String toLocaleString(Object self) {
        return JSType.toString(getNumberValue(self));
    }

    public static double valueOf(Object self) {
        return getNumberValue(self);
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest request, Object receiver) {
        return PrimitiveLookup.lookupPrimitive(request, NashornGuards.getNumberGuard(), new NativeNumber(((Number) receiver).doubleValue()), WRAPFILTER, PROTOFILTER);
    }

    private static NativeNumber wrapFilter(Object receiver) {
        return new NativeNumber(((Number) receiver).doubleValue());
    }

    private static Object protoFilter(Object object) {
        return Global.instance().getNumberPrototype();
    }

    private static double getNumberValue(Object self) {
        if (self instanceof Number) {
            return ((Number) self).doubleValue();
        }
        if (self instanceof NativeNumber) {
            return ((NativeNumber) self).getValue();
        }
        if (self != null && self == Global.instance().getNumberPrototype()) {
            return 0.0d;
        }
        throw ECMAErrors.typeError("not.a.number", ScriptRuntime.safeToString(self));
    }

    private static String fixExponent(String str, boolean trimZeros) {
        int index = str.indexOf(101);
        if (index < 1) {
            return str;
        }
        int expPadding = str.charAt(index + 2) == '0' ? 3 : 2;
        int fractionOffset = index;
        if (trimZeros) {
            if (!$assertionsDisabled && fractionOffset <= 0) {
                throw new AssertionError();
            }
            char charAt = str.charAt(fractionOffset - 1);
            while (true) {
                char c = charAt;
                if (fractionOffset <= 1 || (c != '0' && c != '.')) {
                    break;
                }
                fractionOffset--;
                charAt = str.charAt(fractionOffset - 1);
            }
        }
        if (fractionOffset < index || expPadding == 3) {
            return str.substring(0, fractionOffset) + str.substring(index, index + 2) + str.substring(index + expPadding);
        }
        return str;
    }

    private static MethodHandle findOwnMH(String name, MethodType type) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NativeNumber.class, name, type);
    }
}
