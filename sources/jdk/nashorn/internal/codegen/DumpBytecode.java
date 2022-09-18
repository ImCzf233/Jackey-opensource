package jdk.nashorn.internal.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/DumpBytecode.class */
public final class DumpBytecode {
    public static void dumpBytecode(ScriptEnvironment env, DebugLogger logger, byte[] bytecode, String className) {
        File dir;
        File file;
        File dir2 = null;
        try {
            if (env._print_code) {
                StringBuilder sb = new StringBuilder();
                sb.append("class: " + className).append('\n').append(ClassEmitter.disassemble(bytecode)).append("=====");
                if (env._print_code_dir != null) {
                    String name = className;
                    int dollar = name.lastIndexOf(36);
                    if (dollar != -1) {
                        name = name.substring(dollar + 1);
                    }
                    File dir3 = new File(env._print_code_dir);
                    if (!dir3.exists() && !dir3.mkdirs()) {
                        throw new IOException(dir3.toString());
                    }
                    int uniqueId = 0;
                    do {
                        file = new File(env._print_code_dir, name + (uniqueId == 0 ? "" : "_" + uniqueId) + ".bytecode");
                        uniqueId++;
                    } while (file.exists());
                    PrintWriter pw = new PrintWriter(new FileOutputStream(file));
                    pw.print(sb.toString());
                    pw.flush();
                    if (pw != null) {
                        if (0 != 0) {
                            pw.close();
                        } else {
                            pw.close();
                        }
                    }
                } else {
                    env.getErr().println(sb);
                }
            }
            if (env._dest_dir != null) {
                String fileName = className.replace('.', File.separatorChar) + ".class";
                int index = fileName.lastIndexOf(File.separatorChar);
                if (index != -1) {
                    dir = new File(env._dest_dir, fileName.substring(0, index));
                } else {
                    dir = new File(env._dest_dir);
                }
                if (!dir.exists() && !dir.mkdirs()) {
                    throw new IOException(dir.toString());
                }
                File file2 = new File(env._dest_dir, fileName);
                FileOutputStream fos = new FileOutputStream(file2);
                fos.write(bytecode);
                if (fos != null) {
                    if (0 != 0) {
                        fos.close();
                    } else {
                        fos.close();
                    }
                }
                logger.info("Wrote class to '" + file2.getAbsolutePath() + '\'');
            }
        } catch (IOException e) {
            logger.warning("Skipping class dump for ", className, ": ", ECMAErrors.getMessage("io.error.cant.write", dir2.toString()));
        }
    }
}
