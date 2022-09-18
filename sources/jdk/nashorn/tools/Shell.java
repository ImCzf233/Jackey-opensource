package jdk.nashorn.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Source;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/tools/Shell.class */
public class Shell {
    private static final String MESSAGE_RESOURCE = "jdk.nashorn.tools.resources.Shell";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_RESOURCE, Locale.getDefault());
    public static final int SUCCESS = 0;
    public static final int COMMANDLINE_ERROR = 100;
    public static final int COMPILATION_ERROR = 101;
    public static final int RUNTIME_ERROR = 102;
    public static final int IO_ERROR = 103;
    public static final int INTERNAL_ERROR = 104;

    protected Shell() {
    }

    public static void main(String[] args) {
        try {
            int exitCode = main(System.in, System.out, System.err, args);
            if (exitCode != 0) {
                System.exit(exitCode);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(103);
        }
    }

    public static int main(InputStream in, OutputStream out, OutputStream err, String[] args) throws IOException {
        return new Shell().run(in, out, err, args);
    }

    protected final int run(InputStream in, OutputStream out, OutputStream err, String[] args) throws IOException {
        Context context = makeContext(in, out, err, args);
        if (context == null) {
            return 100;
        }
        Global global = context.createGlobal();
        ScriptEnvironment env = context.getEnv();
        List<String> files = env.getFiles();
        if (files.isEmpty()) {
            return readEvalPrint(context, global);
        }
        if (env._compile_only) {
            return compileScripts(context, global, files);
        }
        if (env._fx) {
            return runFXScripts(context, global, files);
        }
        return runScripts(context, global, files);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e7, code lost:
        r0.set("scripting", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00f1, code lost:
        if (r0 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00f6, code lost:
        if (0 == 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00f9, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x010d, code lost:
        r0.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static jdk.nashorn.internal.runtime.Context makeContext(java.io.InputStream r8, java.io.OutputStream r9, java.io.OutputStream r10, java.lang.String[] r11) {
        /*
            Method dump skipped, instructions count: 394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.tools.Shell.makeContext(java.io.InputStream, java.io.OutputStream, java.io.OutputStream, java.lang.String[]):jdk.nashorn.internal.runtime.Context");
    }

    private static String[] preprocessArgs(String[] args) {
        if (args.length == 0) {
            return args;
        }
        List<String> processedArgs = new ArrayList<>();
        processedArgs.addAll(Arrays.asList(args));
        if (args[0].startsWith("-") && !System.getProperty("os.name", "generic").startsWith("Mac OS X")) {
            processedArgs.addAll(0, ScriptingFunctions.tokenizeString(processedArgs.remove(0)));
        }
        int shebangFilePos = -1;
        int i = 0;
        while (true) {
            if (i >= processedArgs.size()) {
                break;
            }
            String a = processedArgs.get(i);
            if (a.startsWith("-")) {
                i++;
            } else {
                Path p = Paths.get(a, new String[0]);
                String l = "";
                try {
                    BufferedReader r = Files.newBufferedReader(p);
                    l = r.readLine();
                    if (r != null) {
                        if (0 != 0) {
                            r.close();
                        } else {
                            r.close();
                        }
                    }
                } catch (IOException e) {
                }
                if (l.startsWith("#!")) {
                    shebangFilePos = i;
                }
            }
        }
        if (shebangFilePos != -1) {
            processedArgs.add(shebangFilePos + 1, "--");
        }
        return (String[]) processedArgs.toArray(new String[0]);
    }

    private static int compileScripts(Context context, Global global, List<String> files) throws IOException {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != global;
        ScriptEnvironment env = context.getEnv();
        if (globalChanged) {
            try {
                Context.setGlobal(global);
            } finally {
                env.getOut().flush();
                env.getErr().flush();
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
            }
        }
        ErrorManager errors = context.getErrorManager();
        for (String fileName : files) {
            FunctionNode functionNode = new Parser(env, Source.sourceFor(fileName, new File(fileName)), errors, env._strict, 0, context.getLogger(Parser.class)).parse();
            if (errors.getNumberOfErrors() != 0) {
                return 101;
            }
            Compiler.forNoInstallerCompilation(context, functionNode.getSource(), env._strict | functionNode.isStrict()).compile(functionNode, Compiler.CompilationPhases.COMPILE_ALL_NO_INSTALL);
            if (env._print_ast) {
                context.getErr().println(new ASTWriter(functionNode));
            }
            if (env._print_parse) {
                context.getErr().println(new PrintVisitor(functionNode));
            }
            if (errors.getNumberOfErrors() != 0) {
                env.getOut().flush();
                env.getErr().flush();
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                return 101;
            }
        }
        env.getOut().flush();
        env.getErr().flush();
        if (globalChanged) {
            Context.setGlobal(oldGlobal);
            return 0;
        }
        return 0;
    }

    private int runScripts(Context context, Global global, List<String> files) throws IOException {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != global;
        if (globalChanged) {
            try {
                Context.setGlobal(global);
            } catch (Throwable th) {
                context.getOut().flush();
                context.getErr().flush();
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                throw th;
            }
        }
        ErrorManager errors = context.getErrorManager();
        for (String fileName : files) {
            if ("-".equals(fileName)) {
                int res = readEvalPrint(context, global);
                if (res != 0) {
                    context.getOut().flush();
                    context.getErr().flush();
                    if (globalChanged) {
                        Context.setGlobal(oldGlobal);
                    }
                    return res;
                }
            } else {
                File file = new File(fileName);
                ScriptFunction script = context.compileScript(Source.sourceFor(fileName, file), global);
                if (script == null || errors.getNumberOfErrors() != 0) {
                    context.getOut().flush();
                    context.getErr().flush();
                    if (globalChanged) {
                        Context.setGlobal(oldGlobal);
                    }
                    return 101;
                }
                try {
                    apply(script, global);
                } catch (NashornException e) {
                    errors.error(e.toString());
                    if (context.getEnv()._dump_on_error) {
                        e.printStackTrace(context.getErr());
                    }
                    context.getOut().flush();
                    context.getErr().flush();
                    if (globalChanged) {
                        Context.setGlobal(oldGlobal);
                    }
                    return 102;
                }
            }
        }
        context.getOut().flush();
        context.getErr().flush();
        if (globalChanged) {
            Context.setGlobal(oldGlobal);
            return 0;
        }
        return 0;
    }

    private static int runFXScripts(Context context, Global global, List<String> files) throws IOException {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != global;
        if (globalChanged) {
            try {
                try {
                    Context.setGlobal(global);
                } catch (NashornException e) {
                    context.getErrorManager().error(e.toString());
                    if (context.getEnv()._dump_on_error) {
                        e.printStackTrace(context.getErr());
                    }
                    context.getOut().flush();
                    context.getErr().flush();
                    if (globalChanged) {
                        Context.setGlobal(oldGlobal);
                    }
                    return 102;
                }
            } catch (Throwable th) {
                context.getOut().flush();
                context.getErr().flush();
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                throw th;
            }
        }
        global.addOwnProperty("$GLOBAL", 2, global);
        global.addOwnProperty("$SCRIPTS", 2, files);
        context.load(global, "fx:bootstrap.js");
        context.getOut().flush();
        context.getErr().flush();
        if (globalChanged) {
            Context.setGlobal(oldGlobal);
            return 0;
        }
        return 0;
    }

    protected Object apply(ScriptFunction target, Object self) {
        return ScriptRuntime.apply(target, self, new Object[0]);
    }

    private static int readEvalPrint(Context context, Global global) {
        String prompt = bundle.getString("shell.prompt");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter err = context.getErr();
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != global;
        ScriptEnvironment env = context.getEnv();
        if (globalChanged) {
            try {
                Context.setGlobal(global);
            } finally {
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
            }
        }
        global.addShellBuiltins();
        while (true) {
            err.print(prompt);
            err.flush();
            String source = "";
            try {
                source = in.readLine();
            } catch (IOException ioe) {
                err.println(ioe.toString());
            }
            if (source == null) {
                break;
            } else if (!source.isEmpty()) {
                try {
                    Object res = context.eval(global, source, global, "<shell>");
                    if (res != ScriptRuntime.UNDEFINED) {
                        err.println(JSType.toString(res));
                    }
                } catch (Exception e) {
                    err.println(e);
                    if (env._dump_on_error) {
                        e.printStackTrace(err);
                    }
                }
            }
        }
    }
}
