package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/*  JADX ERROR: NullPointerException in pass: ExtractFieldInit
    java.lang.NullPointerException
    */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeFunction.class */
public final class NativeFunction {
    public static final MethodHandle TO_APPLY_ARGS = null;
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeFunction$Constructor.class */
    final class Constructor extends ScriptFunction {
        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Constructor.<init>():void
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
                r5 = this;
                r0 = r5
                java.lang.String r1 = "Function"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeFunction$Prototype r-1 = new jdk.nashorn.internal.objects.NativeFunction$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                r-2 = r5
                r-1 = 1
                r-2.setArity(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFunction.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/NativeFunction$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object apply;
        private Object call;
        private Object bind;
        private Object toSource;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$apply() {
            return this.apply;
        }

        public void S$apply(Object obj) {
            this.apply = obj;
        }

        public Object G$call() {
            return this.call;
        }

        public void S$call(Object obj) {
            this.call = obj;
        }

        public Object G$bind() {
            return this.bind;
        }

        public void S$bind(Object obj) {
            this.bind = obj;
        }

        public Object G$toSource() {
            return this.toSource;
        }

        public void S$toSource(Object obj) {
            this.toSource = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x001E: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x002D: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void
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
        /*  JADX ERROR: Failed to decode insn: 0x003C: CONST, method: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void
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
                r5 = this;
                r0 = r5
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeFunction.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r5
                java.lang.String r1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 % r1
                r-1.toString = r0
                r-1 = r5
                java.lang.String r0 = "apply"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 % r0
                r-2.apply = r-1
                r-2 = r5
                java.lang.String r-1 = "call"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 % r-1
                r-1 = r-2
                r0 = 1
                r-1.setArity(r0)
                r-3.call = r-2
                r-3 = r5
                java.lang.String r-2 = "bind"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 % r-2
                r-2 = r-3
                r-1 = 1
                r-2.setArity(r-1)
                r-4.bind = r-3
                r-4 = r5
                java.lang.String r-3 = "toSource"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-4 = r-4 % r-3
                r-5.toSource = r-4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFunction.Prototype.<init>():void");
        }

        @Override // jdk.nashorn.internal.runtime.ScriptObject
        public String getClassName() {
            return "Function";
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        TO_APPLY_ARGS = Lookup.findOwnStatic(MethodHandles.lookup(), "toApplyArgs", Object[].class, Object.class);
        $clinit$();
    }

    private NativeFunction() {
        throw new UnsupportedOperationException();
    }

    public static String toString(Object self) {
        if (!(self instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
        }
        return ((ScriptFunction) self).toSource();
    }

    public static Object apply(Object self, Object thiz, Object array) {
        checkCallable(self);
        Object[] args = toApplyArgs(array);
        if (self instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) self, thiz, args);
        }
        if (self instanceof JSObject) {
            return ((JSObject) self).call(thiz, args);
        }
        throw new AssertionError("Should not reach here");
    }

    public static Object[] toApplyArgs(Object array) {
        if (array instanceof NativeArguments) {
            return ((NativeArguments) array).getArray().asObjectArray();
        }
        if (array instanceof ScriptObject) {
            ScriptObject sobj = (ScriptObject) array;
            int n = lengthToInt(sobj.getLength());
            Object[] args = new Object[n];
            for (int i = 0; i < args.length; i++) {
                args[i] = sobj.get(i);
            }
            return args;
        } else if (array instanceof Object[]) {
            return (Object[]) array;
        } else {
            if (array instanceof List) {
                List<?> list = (List) array;
                return list.toArray(new Object[list.size()]);
            } else if (array == null || array == ScriptRuntime.UNDEFINED) {
                return ScriptRuntime.EMPTY_ARRAY;
            } else {
                if (array instanceof JSObject) {
                    JSObject jsObj = (JSObject) array;
                    Object len = jsObj.hasMember("length") ? jsObj.getMember("length") : 0;
                    int n2 = lengthToInt(len);
                    Object[] args2 = new Object[n2];
                    for (int i2 = 0; i2 < args2.length; i2++) {
                        args2[i2] = jsObj.hasSlot(i2) ? jsObj.getSlot(i2) : ScriptRuntime.UNDEFINED;
                    }
                    return args2;
                }
                throw ECMAErrors.typeError("function.apply.expects.array", new String[0]);
            }
        }
    }

    private static int lengthToInt(Object len) {
        long ln = JSType.toUint32(len);
        if (ln > 2147483647L) {
            throw ECMAErrors.rangeError("range.error.inappropriate.array.length", JSType.toString(len));
        }
        return (int) ln;
    }

    private static void checkCallable(Object self) {
        if (!(self instanceof ScriptFunction)) {
            if (!(self instanceof JSObject) || !((JSObject) self).isFunction()) {
                throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
            }
        }
    }

    public static Object call(Object self, Object... args) {
        Object[] arguments;
        checkCallable(self);
        Object thiz = args.length == 0 ? ScriptRuntime.UNDEFINED : args[0];
        if (args.length > 1) {
            arguments = new Object[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);
        } else {
            arguments = ScriptRuntime.EMPTY_ARRAY;
        }
        if (self instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) self, thiz, arguments);
        }
        if (self instanceof JSObject) {
            return ((JSObject) self).call(thiz, arguments);
        }
        throw new AssertionError("should not reach here");
    }

    public static Object bind(Object self, Object... args) {
        Object[] arguments;
        Object thiz = args.length == 0 ? ScriptRuntime.UNDEFINED : args[0];
        if (args.length > 1) {
            arguments = new Object[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);
        } else {
            arguments = ScriptRuntime.EMPTY_ARRAY;
        }
        return Bootstrap.bindCallable(self, thiz, arguments);
    }

    public static String toSource(Object self) {
        if (!(self instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
        }
        return ((ScriptFunction) self).toSource();
    }

    public static ScriptFunction function(boolean newObj, Object self, Object... args) {
        String funcBody;
        StringBuilder sb = new StringBuilder();
        sb.append("(function (");
        if (args.length > 0) {
            StringBuilder paramListBuf = new StringBuilder();
            for (int i = 0; i < args.length - 1; i++) {
                paramListBuf.append(JSType.toString(args[i]));
                if (i < args.length - 2) {
                    paramListBuf.append(",");
                }
            }
            funcBody = JSType.toString(args[args.length - 1]);
            String paramList = paramListBuf.toString();
            if (!paramList.isEmpty()) {
                checkFunctionParameters(paramList);
                sb.append(paramList);
            }
        } else {
            funcBody = null;
        }
        sb.append(") {\n");
        if (args.length > 0) {
            checkFunctionBody(funcBody);
            sb.append(funcBody);
            sb.append('\n');
        }
        sb.append("})");
        Global global = Global.instance();
        Context context = global.getContext();
        return (ScriptFunction) context.eval(global, sb.toString(), global, "<function>");
    }

    private static void checkFunctionParameters(String params) {
        Parser parser = getParser(params);
        try {
            parser.parseFormalParameterList();
        } catch (ParserException pe) {
            pe.throwAsEcmaException();
        }
    }

    private static void checkFunctionBody(String funcBody) {
        Parser parser = getParser(funcBody);
        try {
            parser.parseFunctionBody();
        } catch (ParserException pe) {
            pe.throwAsEcmaException();
        }
    }

    private static Parser getParser(String sourceText) {
        ScriptEnvironment env = Global.getEnv();
        return new Parser(env, Source.sourceFor("<function>", sourceText), new Context.ThrowErrorManager(), env._strict, null);
    }
}
