package jdk.nashorn.internal.objects;

import com.viaversion.viaversion.libs.javassist.compiler.Javac;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRegExp.class */
public final class NativeRegExp extends ScriptObject {
    public Object lastIndex;
    private RegExp regexp;
    private final Global globalObject;
    private static PropertyMap $nasgenmap$;
    private static final Object REPLACE_VALUE = null;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRegExp$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0011: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x001D: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0029: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Constructor() {
            /*
                r12 = this;
                r0 = r12
                java.lang.String r1 = "RegExp"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                long r0 = r0 & r1
                r1 = 3
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                r0 = r-1
                r1 = 1
                jdk.nashorn.internal.runtime.Specialization r2 = new jdk.nashorn.internal.runtime.Specialization
                r3 = r2
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r1.<init>(r2, r3)
                r-2[r-1] = r0
                r-2 = r-3
                r-1 = 2
                jdk.nashorn.internal.runtime.Specialization r0 = new jdk.nashorn.internal.runtime.Specialization
                r1 = r0
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-1.<init>(r0, r1)
                r-4[r-3] = r-2
                r-9.<init>(r-8, r-7, r-6, r-5)
                r-9 = r12
                jdk.nashorn.internal.objects.NativeRegExp$Prototype r-8 = new jdk.nashorn.internal.objects.NativeRegExp$Prototype
                r-7 = r-8
                r-7.<init>()
                r-7 = r-8
                r-6 = r12
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-7, r-6)
                r-9.setPrototype(r-8)
                r-9 = r12
                r-8 = 2
                r-9.setArity(r-8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.Constructor.<init>():void");
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRegExp$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object compile;
        private Object exec;
        private Object test;
        private Object toString;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$compile() {
            return this.compile;
        }

        public void S$compile(Object obj) {
            this.compile = obj;
        }

        public Object G$exec() {
            return this.exec;
        }

        public void S$exec(Object obj) {
            this.exec = obj;
        }

        public Object G$test() {
            return this.test;
        }

        public void S$test(Object obj) {
            this.test = obj;
        }

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x001E: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0028: CONST, method: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
            */
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeRegExp.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "compile"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 - r1
                r-1.compile = r0
                r-1 = r4
                java.lang.String r0 = "exec"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 - r0
                r-2.exec = r-1
                r-2 = r4
                java.lang.String r-1 = "test"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 - r-1
                r-3.test = r-2
                r-3 = r4
                java.lang.String r-2 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 - r-2
                r-4.toString = r-3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "RegExp";
        }
    }

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeRegExp.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRegExp.class
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
        // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeRegExp.$clinit$():void, file: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeRegExp.class
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeRegExp.$clinit$():void");
    }

    public Object G$lastIndex() {
        return this.lastIndex;
    }

    public void S$lastIndex(Object obj) {
        this.lastIndex = obj;
    }

    static {
        $assertionsDisabled = !NativeRegExp.class.desiredAssertionStatus();
        REPLACE_VALUE = new Object();
        $clinit$();
    }

    private NativeRegExp(Global global) {
        super(global.getRegExpPrototype(), $nasgenmap$);
        this.globalObject = global;
    }

    public NativeRegExp(String input, String flagString, Global global, ScriptObject proto) {
        super(proto, $nasgenmap$);
        try {
            this.regexp = RegExpFactory.create(input, flagString);
            this.globalObject = global;
            setLastIndex(0);
        } catch (ParserException e) {
            e.throwAsEcmaException();
            throw new AssertionError();
        }
    }

    NativeRegExp(String input, String flagString, Global global) {
        this(input, flagString, global, global.getRegExpPrototype());
    }

    public NativeRegExp(String input, String flagString) {
        this(input, flagString, Global.instance());
    }

    NativeRegExp(String string, Global global) {
        this(string, "", global);
    }

    public NativeRegExp(String string) {
        this(string, Global.instance());
    }

    public NativeRegExp(NativeRegExp regExp) {
        this(Global.instance());
        this.lastIndex = regExp.getLastIndexObject();
        this.regexp = regExp.getRegExp();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "RegExp";
    }

    public static NativeRegExp constructor(boolean isNew, Object self, Object... args) {
        if (args.length > 1) {
            return newRegExp(args[0], args[1]);
        }
        if (args.length > 0) {
            return newRegExp(args[0], ScriptRuntime.UNDEFINED);
        }
        return newRegExp(ScriptRuntime.UNDEFINED, ScriptRuntime.UNDEFINED);
    }

    public static NativeRegExp constructor(boolean isNew, Object self) {
        return new NativeRegExp("", "");
    }

    public static NativeRegExp constructor(boolean isNew, Object self, Object pattern) {
        return newRegExp(pattern, ScriptRuntime.UNDEFINED);
    }

    public static NativeRegExp constructor(boolean isNew, Object self, Object pattern, Object flags) {
        return newRegExp(pattern, flags);
    }

    public static NativeRegExp newRegExp(Object regexp, Object flags) {
        String patternString = "";
        String flagString = "";
        if (regexp != ScriptRuntime.UNDEFINED) {
            if (regexp instanceof NativeRegExp) {
                if (flags != ScriptRuntime.UNDEFINED) {
                    throw ECMAErrors.typeError("regex.cant.supply.flags", new String[0]);
                }
                return (NativeRegExp) regexp;
            }
            patternString = JSType.toString(regexp);
        }
        if (flags != ScriptRuntime.UNDEFINED) {
            flagString = JSType.toString(flags);
        }
        return new NativeRegExp(patternString, flagString);
    }

    public static NativeRegExp flatRegExp(String string) {
        StringBuilder sb = null;
        int length = string.length();
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            switch (c) {
                case '$':
                case '(':
                case ')':
                case '*':
                case '+':
                case '.':
                case '?':
                case '[':
                case '\\':
                case '^':
                case '{':
                case '|':
                    if (sb == null) {
                        sb = new StringBuilder(length * 2);
                        sb.append((CharSequence) string, 0, i);
                    }
                    sb.append('\\');
                    sb.append(c);
                    break;
                default:
                    if (sb != null) {
                        sb.append(c);
                        break;
                    } else {
                        break;
                    }
            }
        }
        return new NativeRegExp(sb == null ? string : sb.toString(), "");
    }

    private String getFlagString() {
        StringBuilder sb = new StringBuilder(3);
        if (this.regexp.isGlobal()) {
            sb.append('g');
        }
        if (this.regexp.isIgnoreCase()) {
            sb.append('i');
        }
        if (this.regexp.isMultiline()) {
            sb.append('m');
        }
        return sb.toString();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[RegExp " + toString() + "]";
    }

    public String toString() {
        return "/" + this.regexp.getSource() + "/" + getFlagString();
    }

    public static ScriptObject compile(Object self, Object pattern, Object flags) {
        NativeRegExp regExp = checkRegExp(self);
        NativeRegExp compiled = newRegExp(pattern, flags);
        regExp.setRegExp(compiled.getRegExp());
        return regExp;
    }

    public static ScriptObject exec(Object self, Object string) {
        return checkRegExp(self).exec(JSType.toString(string));
    }

    public static boolean test(Object self, Object string) {
        return checkRegExp(self).test(JSType.toString(string));
    }

    public static String toString(Object self) {
        return checkRegExp(self).toString();
    }

    public static Object source(Object self) {
        return checkRegExp(self).getRegExp().getSource();
    }

    public static Object global(Object self) {
        return Boolean.valueOf(checkRegExp(self).getRegExp().isGlobal());
    }

    public static Object ignoreCase(Object self) {
        return Boolean.valueOf(checkRegExp(self).getRegExp().isIgnoreCase());
    }

    public static Object multiline(Object self) {
        return Boolean.valueOf(checkRegExp(self).getRegExp().isMultiline());
    }

    public static Object getLastInput(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getInput();
    }

    public static Object getLastMultiline(Object self) {
        return false;
    }

    public static Object getLastMatch(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(0);
    }

    public static Object getLastParen(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getLastParen();
    }

    public static Object getLeftContext(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getInput().substring(0, match.getIndex());
    }

    public static Object getRightContext(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getInput().substring(match.getIndex() + match.length());
    }

    public static Object getGroup1(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(1);
    }

    public static Object getGroup2(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(2);
    }

    public static Object getGroup3(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(3);
    }

    public static Object getGroup4(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(4);
    }

    public static Object getGroup5(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(5);
    }

    public static Object getGroup6(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(6);
    }

    public static Object getGroup7(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(7);
    }

    public static Object getGroup8(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(8);
    }

    public static Object getGroup9(Object self) {
        RegExpResult match = Global.instance().getLastRegExpResult();
        return match == null ? "" : match.getGroup(9);
    }

    private RegExpResult execInner(String string) {
        boolean isGlobal = this.regexp.isGlobal();
        int start = getLastIndex();
        if (!isGlobal) {
            start = 0;
        }
        if (start < 0 || start > string.length()) {
            if (isGlobal) {
                setLastIndex(0);
                return null;
            }
            return null;
        }
        RegExpMatcher matcher = this.regexp.match(string);
        if (matcher == null || !matcher.search(start)) {
            if (isGlobal) {
                setLastIndex(0);
                return null;
            }
            return null;
        }
        if (isGlobal) {
            setLastIndex(matcher.end());
        }
        RegExpResult match = new RegExpResult(string, matcher.start(), groups(matcher));
        this.globalObject.setLastRegExpResult(match);
        return match;
    }

    private RegExpResult execSplit(String string, int start) {
        RegExpMatcher matcher;
        if (start < 0 || start > string.length() || (matcher = this.regexp.match(string)) == null || !matcher.search(start)) {
            return null;
        }
        RegExpResult match = new RegExpResult(string, matcher.start(), groups(matcher));
        this.globalObject.setLastRegExpResult(match);
        return match;
    }

    private Object[] groups(RegExpMatcher matcher) {
        int groupCount = matcher.groupCount();
        Object[] groups = new Object[groupCount + 1];
        BitVector groupsInNegativeLookahead = this.regexp.getGroupsInNegativeLookahead();
        int lastGroupStart = matcher.start();
        for (int i = 0; i <= groupCount; i++) {
            int groupStart = matcher.start(i);
            if (lastGroupStart > groupStart || (groupsInNegativeLookahead != null && groupsInNegativeLookahead.isSet(i))) {
                groups[i] = ScriptRuntime.UNDEFINED;
            } else {
                String group = matcher.group(i);
                groups[i] = group == null ? ScriptRuntime.UNDEFINED : group;
                lastGroupStart = groupStart;
            }
        }
        return groups;
    }

    public NativeRegExpExecResult exec(String string) {
        RegExpResult match = execInner(string);
        if (match == null) {
            return null;
        }
        return new NativeRegExpExecResult(match, this.globalObject);
    }

    public boolean test(String string) {
        return execInner(string) != null;
    }

    public String replace(String string, String replacement, Object function) throws Throwable {
        RegExpMatcher matcher = this.regexp.match(string);
        if (matcher == null) {
            return string;
        }
        if (!this.regexp.isGlobal()) {
            if (!matcher.search(0)) {
                return string;
            }
            StringBuilder sb = new StringBuilder();
            sb.append((CharSequence) string, 0, matcher.start());
            if (function != null) {
                Object self = Bootstrap.isStrictCallable(function) ? ScriptRuntime.UNDEFINED : Global.instance();
                sb.append(callReplaceValue(getReplaceValueInvoker(), function, self, matcher, string));
            } else {
                appendReplacement(matcher, string, replacement, sb);
            }
            sb.append((CharSequence) string, matcher.end(), string.length());
            return sb.toString();
        }
        setLastIndex(0);
        if (!matcher.search(0)) {
            return string;
        }
        int thisIndex = 0;
        int previousLastIndex = 0;
        StringBuilder sb2 = new StringBuilder();
        MethodHandle invoker = function == null ? null : getReplaceValueInvoker();
        Object self2 = (function == null || Bootstrap.isStrictCallable(function)) ? ScriptRuntime.UNDEFINED : Global.instance();
        do {
            sb2.append((CharSequence) string, thisIndex, matcher.start());
            if (function != null) {
                sb2.append(callReplaceValue(invoker, function, self2, matcher, string));
            } else {
                appendReplacement(matcher, string, replacement, sb2);
            }
            thisIndex = matcher.end();
            if (thisIndex != string.length() || matcher.start() != matcher.end()) {
                if (thisIndex == previousLastIndex) {
                    setLastIndex(thisIndex + 1);
                    previousLastIndex = thisIndex + 1;
                } else {
                    previousLastIndex = thisIndex;
                }
                if (previousLastIndex > string.length()) {
                    break;
                }
            } else {
                break;
            }
        } while (matcher.search(previousLastIndex));
        sb2.append((CharSequence) string, thisIndex, string.length());
        return sb2.toString();
    }

    private void appendReplacement(RegExpMatcher matcher, String text, String replacement, StringBuilder sb) {
        int secondDigit;
        int newRefNum;
        int cursor = 0;
        Object[] groups = null;
        while (cursor < replacement.length()) {
            char nextChar = replacement.charAt(cursor);
            if (nextChar == '$') {
                cursor++;
                if (cursor == replacement.length()) {
                    sb.append('$');
                    return;
                }
                char nextChar2 = replacement.charAt(cursor);
                int firstDigit = nextChar2 - '0';
                if (firstDigit >= 0 && firstDigit <= 9 && firstDigit <= matcher.groupCount()) {
                    int refNum = firstDigit;
                    cursor++;
                    if (cursor < replacement.length() && firstDigit < matcher.groupCount() && (secondDigit = replacement.charAt(cursor) - '0') >= 0 && secondDigit <= 9 && (newRefNum = (firstDigit * 10) + secondDigit) <= matcher.groupCount() && newRefNum > 0) {
                        refNum = newRefNum;
                        cursor++;
                    }
                    if (refNum > 0) {
                        if (groups == null) {
                            groups = groups(matcher);
                        }
                        if (groups[refNum] != ScriptRuntime.UNDEFINED) {
                            sb.append((String) groups[refNum]);
                        }
                    } else if (!$assertionsDisabled && refNum != 0) {
                        throw new AssertionError();
                    } else {
                        sb.append(Javac.param0Name);
                    }
                } else if (nextChar2 == '$') {
                    sb.append('$');
                    cursor++;
                } else if (nextChar2 == '&') {
                    sb.append(matcher.group());
                    cursor++;
                } else if (nextChar2 == '`') {
                    sb.append((CharSequence) text, 0, matcher.start());
                    cursor++;
                } else if (nextChar2 == '\'') {
                    sb.append((CharSequence) text, matcher.end(), text.length());
                    cursor++;
                } else {
                    sb.append('$');
                }
            } else {
                sb.append(nextChar);
                cursor++;
            }
        }
    }

    private static final MethodHandle getReplaceValueInvoker() {
        return Global.instance().getDynamicInvoker(REPLACE_VALUE, new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.objects.NativeRegExp.1
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", String.class, Object.class, Object.class, Object[].class);
            }
        });
    }

    private String callReplaceValue(MethodHandle invoker, Object function, Object self, RegExpMatcher matcher, String string) throws Throwable {
        Object[] groups = groups(matcher);
        Object[] args = Arrays.copyOf(groups, groups.length + 2);
        args[groups.length] = Integer.valueOf(matcher.start());
        args[groups.length + 1] = string;
        return invoker.invokeExact(function, self, args);
    }

    public NativeArray split(String string, long limit) {
        if (limit == 0) {
            return new NativeArray();
        }
        List<Object> matches = new ArrayList<>();
        int inputLength = string.length();
        int splitLastLength = -1;
        int splitLastIndex = 0;
        int splitLastLastIndex = 0;
        while (true) {
            RegExpResult match = execSplit(string, splitLastIndex);
            if (match == null) {
                break;
            }
            splitLastIndex = match.getIndex() + match.length();
            if (splitLastIndex > splitLastLastIndex) {
                matches.add(string.substring(splitLastLastIndex, match.getIndex()));
                Object[] groups = match.getGroups();
                if (groups.length > 1 && match.getIndex() < inputLength) {
                    for (int index = 1; index < groups.length && matches.size() < limit; index++) {
                        matches.add(groups[index]);
                    }
                }
                splitLastLength = match.length();
                if (matches.size() >= limit) {
                    break;
                }
            }
            if (splitLastIndex == splitLastLastIndex) {
                splitLastIndex++;
            } else {
                splitLastLastIndex = splitLastIndex;
            }
        }
        if (matches.size() < limit) {
            if (splitLastLastIndex == string.length()) {
                if (splitLastLength > 0 || execSplit("", 0) == null) {
                    matches.add("");
                }
            } else {
                matches.add(string.substring(splitLastLastIndex, inputLength));
            }
        }
        return new NativeArray(matches.toArray());
    }

    public int search(String string) {
        RegExpResult match = execInner(string);
        if (match == null) {
            return -1;
        }
        return match.getIndex();
    }

    public int getLastIndex() {
        return JSType.toInteger(this.lastIndex);
    }

    public Object getLastIndexObject() {
        return this.lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = JSType.toObject(lastIndex);
    }

    private static NativeRegExp checkRegExp(Object self) {
        if (self instanceof NativeRegExp) {
            return (NativeRegExp) self;
        }
        if (self != null && self == Global.instance().getRegExpPrototype()) {
            return Global.instance().getDefaultRegExp();
        }
        throw ECMAErrors.typeError("not.a.regexp", ScriptRuntime.safeToString(self));
    }

    public boolean getGlobal() {
        return this.regexp.isGlobal();
    }

    private RegExp getRegExp() {
        return this.regexp;
    }

    private void setRegExp(RegExp regexp) {
        this.regexp = regexp;
    }
}
