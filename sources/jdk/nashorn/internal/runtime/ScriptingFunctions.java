package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.NativeArray;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptingFunctions.class */
public final class ScriptingFunctions {
    public static final MethodHandle READLINE;
    public static final MethodHandle READFULLY;
    public static final MethodHandle EXEC;
    public static final String EXEC_NAME = "$EXEC";
    public static final String OUT_NAME = "$OUT";
    public static final String ERR_NAME = "$ERR";
    public static final String EXIT_NAME = "$EXIT";
    public static final String THROW_ON_ERROR_NAME = "throwOnError";
    public static final String ENV_NAME = "$ENV";
    public static final String PWD_NAME = "PWD";
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptingFunctions.class.desiredAssertionStatus();
        READLINE = findOwnMH("readLine", Object.class, Object.class, Object.class);
        READFULLY = findOwnMH("readFully", Object.class, Object.class, Object.class);
        EXEC = findOwnMH("exec", Object.class, Object.class, Object[].class);
    }

    private ScriptingFunctions() {
    }

    public static Object readLine(Object self, Object prompt) throws IOException {
        if (prompt != ScriptRuntime.UNDEFINED) {
            System.out.print(JSType.toString(prompt));
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public static Object readFully(Object self, Object file) throws IOException {
        File f = null;
        if (file instanceof File) {
            f = (File) file;
        } else if (JSType.isString(file)) {
            f = new File(((CharSequence) file).toString());
        }
        if (f == null || !f.isFile()) {
            throw ECMAErrors.typeError("not.a.file", ScriptRuntime.safeToString(file));
        }
        return new String(Source.readFully(f));
    }

    public static Object exec(Object self, Object... args) throws IOException, InterruptedException {
        ScriptObject global = Context.getGlobal();
        Object string = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
        Object input = args.length > 1 ? args[1] : ScriptRuntime.UNDEFINED;
        Object[] argv = args.length > 2 ? Arrays.copyOfRange(args, 2, args.length) : ScriptRuntime.EMPTY_ARRAY;
        List<String> cmdLine = tokenizeString(JSType.toString(string));
        Object[] additionalArgs = (argv.length != 1 || !(argv[0] instanceof NativeArray)) ? argv : ((NativeArray) argv[0]).asObjectArray();
        for (Object arg : additionalArgs) {
            cmdLine.add(JSType.toString(arg));
        }
        ProcessBuilder processBuilder = new ProcessBuilder(cmdLine);
        Object env = global.get(ENV_NAME);
        if (env instanceof ScriptObject) {
            ScriptObject envProperties = (ScriptObject) env;
            Object pwd = envProperties.get(PWD_NAME);
            if (pwd != ScriptRuntime.UNDEFINED) {
                File pwdFile = new File(JSType.toString(pwd));
                if (pwdFile.exists()) {
                    processBuilder.directory(pwdFile);
                }
            }
            Map<String, String> environment = processBuilder.environment();
            environment.clear();
            for (Map.Entry<Object, Object> entry : envProperties.entrySet()) {
                environment.put(JSType.toString(entry.getKey()), JSType.toString(entry.getValue()));
            }
        }
        final Process process = processBuilder.start();
        final IOException[] exception = new IOException[2];
        final StringBuilder outBuffer = new StringBuilder();
        Thread outThread = new Thread(new Runnable() { // from class: jdk.nashorn.internal.runtime.ScriptingFunctions.1
            @Override // java.lang.Runnable
            public void run() {
                char[] buffer = new char[1024];
                try {
                    InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
                    while (true) {
                        int length = inputStream.read(buffer, 0, buffer.length);
                        if (length == -1) {
                            break;
                        }
                        outBuffer.append(buffer, 0, length);
                    }
                    if (inputStream != null) {
                        if (0 != 0) {
                            inputStream.close();
                        } else {
                            inputStream.close();
                        }
                    }
                } catch (IOException ex) {
                    exception[0] = ex;
                }
            }
        }, "$EXEC output");
        final StringBuilder errBuffer = new StringBuilder();
        Thread errThread = new Thread(new Runnable() { // from class: jdk.nashorn.internal.runtime.ScriptingFunctions.2
            @Override // java.lang.Runnable
            public void run() {
                char[] buffer = new char[1024];
                try {
                    InputStreamReader inputStream = new InputStreamReader(process.getErrorStream());
                    while (true) {
                        int length = inputStream.read(buffer, 0, buffer.length);
                        if (length == -1) {
                            break;
                        }
                        errBuffer.append(buffer, 0, length);
                    }
                    if (inputStream != null) {
                        if (0 != 0) {
                            inputStream.close();
                        } else {
                            inputStream.close();
                        }
                    }
                } catch (IOException ex) {
                    exception[1] = ex;
                }
            }
        }, "$EXEC error");
        outThread.start();
        errThread.start();
        if (!JSType.nullOrUndefined(input)) {
            try {
                OutputStreamWriter outputStream = new OutputStreamWriter(process.getOutputStream());
                String in = JSType.toString(input);
                outputStream.write(in, 0, in.length());
                if (outputStream != null) {
                    if (0 != 0) {
                        outputStream.close();
                    } else {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
            }
        }
        int exit = process.waitFor();
        outThread.join();
        errThread.join();
        String out = outBuffer.toString();
        String err = errBuffer.toString();
        global.set(OUT_NAME, out, 0);
        global.set(ERR_NAME, err, 0);
        global.set(EXIT_NAME, exit, 0);
        for (IOException element : exception) {
            if (element != null) {
                throw element;
            }
        }
        if (exit != 0) {
            Object exec = global.get(EXEC_NAME);
            if (!$assertionsDisabled && !(exec instanceof ScriptObject)) {
                throw new AssertionError("$EXEC is not a script object!");
            }
            if (JSType.toBoolean(((ScriptObject) exec).get(THROW_ON_ERROR_NAME))) {
                throw ECMAErrors.rangeError("exec.returned.non.zero", ScriptRuntime.safeToString(Integer.valueOf(exit)));
            }
        }
        return out;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), ScriptingFunctions.class, name, Lookup.f248MH.type(rtype, types));
    }

    public static List<String> tokenizeString(String str) {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(str));
        tokenizer.resetSyntax();
        tokenizer.wordChars(0, 255);
        tokenizer.whitespaceChars(0, 32);
        tokenizer.commentChar(35);
        tokenizer.quoteChar(34);
        tokenizer.quoteChar(39);
        List<String> tokenList = new ArrayList<>();
        StringBuilder toAppend = new StringBuilder();
        while (nextToken(tokenizer) != -1) {
            String s = tokenizer.sval;
            if (s.endsWith("\\")) {
                toAppend.append(s.substring(0, s.length() - 1)).append(' ');
            } else {
                tokenList.add(toAppend.append(s).toString());
                toAppend.setLength(0);
            }
        }
        if (toAppend.length() != 0) {
            tokenList.add(toAppend.toString());
        }
        return tokenList;
    }

    private static int nextToken(StreamTokenizer tokenizer) {
        try {
            return tokenizer.nextToken();
        } catch (IOException e) {
            return -1;
        }
    }
}
