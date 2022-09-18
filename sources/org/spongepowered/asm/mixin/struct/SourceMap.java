package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/SourceMap.class */
public class SourceMap {
    private static final String DEFAULT_STRATUM = "Mixin";
    private static final String NEWLINE = "\n";
    private final String sourceFile;
    private final Map<String, Stratum> strata = new LinkedHashMap();
    private int nextLineOffset = 1;
    private String defaultStratum = DEFAULT_STRATUM;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/SourceMap$File.class */
    public static class File {

        /* renamed from: id */
        public final int f442id;
        public final int lineOffset;
        public final int size;
        public final String sourceFileName;
        public final String sourceFilePath;

        public File(int id, int lineOffset, int size, String sourceFileName) {
            this(id, lineOffset, size, sourceFileName, null);
        }

        public File(int id, int lineOffset, int size, String sourceFileName, String sourceFilePath) {
            this.f442id = id;
            this.lineOffset = lineOffset;
            this.size = size;
            this.sourceFileName = sourceFileName;
            this.sourceFilePath = sourceFilePath;
        }

        public void applyOffset(ClassNode classNode) {
            for (MethodNode method : classNode.methods) {
                applyOffset(method);
            }
        }

        public void applyOffset(MethodNode method) {
            Iterator<AbstractInsnNode> iter = method.instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode node = iter.next();
                if (node instanceof LineNumberNode) {
                    ((LineNumberNode) node).line += this.lineOffset - 1;
                }
            }
        }

        void appendFile(StringBuilder sb) {
            if (this.sourceFilePath != null) {
                sb.append("+ ").append(this.f442id).append(" ").append(this.sourceFileName).append(SourceMap.NEWLINE);
                sb.append(this.sourceFilePath).append(SourceMap.NEWLINE);
                return;
            }
            sb.append(this.f442id).append(" ").append(this.sourceFileName).append(SourceMap.NEWLINE);
        }

        public void appendLines(StringBuilder sb) {
            sb.append("1#").append(this.f442id).append(",").append(this.size).append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.lineOffset).append(SourceMap.NEWLINE);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/SourceMap$Stratum.class */
    public static class Stratum {
        private static final String STRATUM_MARK = "*S";
        private static final String FILE_MARK = "*F";
        private static final String LINES_MARK = "*L";
        public final String name;
        private final Map<String, File> files = new LinkedHashMap();

        public Stratum(String name) {
            this.name = name;
        }

        public File addFile(int lineOffset, int size, String sourceFileName, String sourceFilePath) {
            File file = this.files.get(sourceFilePath);
            if (file == null) {
                file = new File(this.files.size() + 1, lineOffset, size, sourceFileName, sourceFilePath);
                this.files.put(sourceFilePath, file);
            }
            return file;
        }

        void appendTo(StringBuilder sb) {
            sb.append(STRATUM_MARK).append(" ").append(this.name).append(SourceMap.NEWLINE);
            sb.append(FILE_MARK).append(SourceMap.NEWLINE);
            for (File file : this.files.values()) {
                file.appendFile(sb);
            }
            sb.append(LINES_MARK).append(SourceMap.NEWLINE);
            for (File file2 : this.files.values()) {
                file2.appendLines(sb);
            }
        }
    }

    public SourceMap(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getSourceFile() {
        return this.sourceFile;
    }

    public String getPseudoGeneratedSourceFile() {
        return this.sourceFile.replace(".java", "$mixin.java");
    }

    public File addFile(ClassNode classNode) {
        return addFile(this.defaultStratum, classNode);
    }

    public File addFile(String stratumName, ClassNode classNode) {
        return addFile(stratumName, classNode.sourceFile, classNode.name + ".java", Bytecode.getMaxLineNumber(classNode, 500, 50));
    }

    public File addFile(String sourceFileName, String sourceFilePath, int size) {
        return addFile(this.defaultStratum, sourceFileName, sourceFilePath, size);
    }

    public File addFile(String stratumName, String sourceFileName, String sourceFilePath, int size) {
        Stratum stratum = this.strata.get(stratumName);
        if (stratum == null) {
            stratum = new Stratum(stratumName);
            this.strata.put(stratumName, stratum);
        }
        File file = stratum.addFile(this.nextLineOffset, size, sourceFileName, sourceFilePath);
        this.nextLineOffset += size;
        return file;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    private void appendTo(StringBuilder sb) {
        sb.append("SMAP").append(NEWLINE);
        sb.append(getSourceFile()).append(NEWLINE);
        sb.append(this.defaultStratum).append(NEWLINE);
        for (Stratum stratum : this.strata.values()) {
            stratum.appendTo(sb);
        }
        sb.append("*E").append(NEWLINE);
    }
}
