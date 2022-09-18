package jdk.nashorn.internal.p001ir.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import kotlin.jvm.internal.CharCompanionObject;
import org.spongepowered.asm.lib.Opcodes;

/* renamed from: jdk.nashorn.internal.ir.debug.NashornTextifier */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornTextifier.class */
public final class NashornTextifier extends Printer {
    private String currentClassName;
    private Iterator<Label> labelIter;
    private Graph graph;
    private String currentBlock;
    private boolean lastWasNop;
    private boolean lastWasEllipse;
    private static final int INTERNAL_NAME = 0;
    private static final int FIELD_DESCRIPTOR = 1;
    private static final int FIELD_SIGNATURE = 2;
    private static final int METHOD_DESCRIPTOR = 3;
    private static final int METHOD_SIGNATURE = 4;
    private static final int CLASS_SIGNATURE = 5;
    private final String tab = "  ";
    private final String tab2 = "    ";
    private final String tab3 = "      ";
    private Map<Label, String> labelNames;
    private boolean localVarsStarted;

    /* renamed from: cr */
    private NashornClassReader f245cr;
    private ScriptEnvironment env;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornTextifier.class.desiredAssertionStatus();
    }

    public NashornTextifier(ScriptEnvironment env, NashornClassReader cr) {
        this(Opcodes.ASM5);
        this.env = env;
        this.f245cr = cr;
    }

    private NashornTextifier(ScriptEnvironment env, NashornClassReader cr, Iterator<Label> labelIter, Graph graph) {
        this(env, cr);
        this.labelIter = labelIter;
        this.graph = graph;
    }

    protected NashornTextifier(int api) {
        super(api);
        this.lastWasNop = false;
        this.lastWasEllipse = false;
        this.tab = "  ";
        this.tab2 = "    ";
        this.tab3 = "      ";
        this.localVarsStarted = false;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        int major = version & CharCompanionObject.MAX_VALUE;
        int minor = version >>> 16;
        this.currentClassName = name;
        StringBuilder sb = new StringBuilder();
        sb.append("// class version ").append(major).append('.').append(minor).append(" (").append(version).append(")\n");
        if ((access & 131072) != 0) {
            sb.append("// DEPRECATED\n");
        }
        sb.append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        appendDescriptor(sb, 5, signature);
        if (signature != null) {
            TraceSignatureVisitor sv = new TraceSignatureVisitor(access);
            SignatureReader r = new SignatureReader(signature);
            r.accept(sv);
            sb.append("// declaration: ").append(name).append(sv.getDeclaration()).append('\n');
        }
        appendAccess(sb, access & (-33));
        if ((access & 8192) != 0) {
            sb.append("@interface ");
        } else if ((access & 512) != 0) {
            sb.append("interface ");
        } else if ((access & 16384) == 0) {
            sb.append("class ");
        }
        appendDescriptor(sb, 0, name);
        if (superName != null && !"java/lang/Object".equals(superName)) {
            sb.append(" extends ");
            appendDescriptor(sb, 0, superName);
            sb.append(' ');
        }
        if (interfaces != null && interfaces.length > 0) {
            sb.append(" implements ");
            for (String interface1 : interfaces) {
                appendDescriptor(sb, 0, interface1);
                sb.append(' ');
            }
        }
        sb.append(" {\n");
        addText(sb);
    }

    public void visitSource(String file, String debug) {
        StringBuilder sb = new StringBuilder();
        if (file != null) {
            sb.append("  ").append("// compiled from: ").append(file).append('\n');
        }
        if (debug != null) {
            sb.append("  ").append("// debug info: ").append(debug).append('\n');
        }
        if (sb.length() > 0) {
            addText(sb);
        }
    }

    public void visitOuterClass(String owner, String name, String desc) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append("outer class ");
        appendDescriptor(sb, 0, owner);
        sb.append(' ');
        if (name != null) {
            sb.append(name).append(' ');
        }
        appendDescriptor(sb, 3, desc);
        sb.append('\n');
        addText(sb);
    }

    public NashornTextifier visitField(int access, String name, String desc, String signature, Object value) {
        StringBuilder sb = new StringBuilder();
        if ((access & 131072) != 0) {
            sb.append("  ").append("// DEPRECATED\n");
        }
        if (signature != null) {
            sb.append("  ");
            appendDescriptor(sb, 2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            sb.append("  ").append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        sb.append("  ");
        appendAccess(sb, access);
        String prunedDesc = desc.endsWith(";") ? desc.substring(0, desc.length() - 1) : desc;
        appendDescriptor(sb, 1, prunedDesc);
        sb.append(' ').append(name);
        if (value != null) {
            sb.append(" = ");
            if (value instanceof String) {
                sb.append('\"').append(value).append('\"');
            } else {
                sb.append(value);
            }
        }
        sb.append(";\n");
        addText(sb);
        NashornTextifier t = createNashornTextifier();
        addText(t.getText());
        return t;
    }

    public NashornTextifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        this.graph = new Graph(name);
        List<Label> extraLabels = this.f245cr.getExtraLabels(this.currentClassName, name, desc);
        this.labelIter = extraLabels == null ? null : extraLabels.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        if ((access & 131072) != 0) {
            sb.append("  ").append("// DEPRECATED\n");
        }
        sb.append("  ").append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        if (signature != null) {
            sb.append("  ");
            appendDescriptor(sb, 4, signature);
            TraceSignatureVisitor v = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.accept(v);
            String genericDecl = v.getDeclaration();
            String genericReturn = v.getReturnType();
            String genericExceptions = v.getExceptions();
            sb.append("  ").append("// declaration: ").append(genericReturn).append(' ').append(name).append(genericDecl);
            if (genericExceptions != null) {
                sb.append(" throws ").append(genericExceptions);
            }
            sb.append('\n');
        }
        sb.append("  ");
        appendAccess(sb, access);
        if ((access & 256) != 0) {
            sb.append("native ");
        }
        if ((access & 128) != 0) {
            sb.append("varargs ");
        }
        if ((access & 64) != 0) {
            sb.append("bridge ");
        }
        sb.append(name);
        appendDescriptor(sb, 3, desc);
        if (exceptions != null && exceptions.length > 0) {
            sb.append(" throws ");
            for (String exception : exceptions) {
                appendDescriptor(sb, 0, exception);
                sb.append(' ');
            }
        }
        sb.append('\n');
        addText(sb);
        NashornTextifier t = createNashornTextifier();
        addText(t.getText());
        return t;
    }

    public void visitClassEnd() {
        addText("}\n");
    }

    public void visitFieldEnd() {
    }

    public void visitParameter(String name, int access) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append("// parameter ");
        appendAccess(sb, access);
        sb.append(' ').append(name == null ? "<no name>" : name).append('\n');
        addText(sb);
    }

    public void visitCode() {
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        StringBuilder sb = new StringBuilder();
        sb.append("frame ");
        switch (type) {
            case -1:
            case 0:
                sb.append("full [");
                appendFrameTypes(sb, nLocal, local);
                sb.append("] [");
                appendFrameTypes(sb, nStack, stack);
                sb.append(']');
                break;
            case 1:
                sb.append("append [");
                appendFrameTypes(sb, nLocal, local);
                sb.append(']');
                break;
            case 2:
                sb.append("chop ").append(nLocal);
                break;
            case 3:
                sb.append("same");
                break;
            case 4:
                sb.append("same1 ");
                appendFrameTypes(sb, 1, stack);
                break;
            default:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
        }
        sb.append('\n');
        sb.append('\n');
        addText(sb);
    }

    private StringBuilder appendOpcode(StringBuilder sb, int opcode) {
        Label next = getNextLabel();
        if (next instanceof NashornLabel) {
            int bci = next.getOffset();
            if (bci != -1) {
                String bcis = "" + bci;
                for (int i = 0; i < 5 - bcis.length(); i++) {
                    sb.append(' ');
                }
                sb.append(bcis);
                sb.append(' ');
            } else {
                sb.append("       ");
            }
        }
        return sb.append("    ").append(OPCODES[opcode].toLowerCase());
    }

    private Label getNextLabel() {
        if (this.labelIter == null) {
            return null;
        }
        return this.labelIter.next();
    }

    public void visitInsn(int opcode) {
        if (opcode == 0) {
            if (this.lastWasEllipse) {
                getNextLabel();
                return;
            } else if (this.lastWasNop) {
                getNextLabel();
                addText("          ...\n");
                this.lastWasEllipse = true;
                return;
            } else {
                this.lastWasNop = true;
            }
        } else {
            this.lastWasEllipse = false;
            this.lastWasNop = false;
        }
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append('\n');
        addText(sb);
        checkNoFallThru(opcode, null);
    }

    public void visitIntInsn(int opcode, int operand) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ').append(opcode == 188 ? TYPES[operand] : Integer.toString(operand)).append('\n');
        addText(sb);
    }

    public void visitVarInsn(int opcode, int var) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ').append(var).append('\n');
        addText(sb);
    }

    public void visitTypeInsn(int opcode, String type) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ');
        appendDescriptor(sb, 0, type);
        sb.append('\n');
        addText(sb);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ');
        appendDescriptor(sb, 0, owner);
        sb.append('.').append(name).append(" : ");
        appendDescriptor(sb, 1, desc);
        sb.append('\n');
        addText(sb);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ');
        appendDescriptor(sb, 0, owner);
        sb.append('.').append(name);
        appendDescriptor(sb, 3, desc);
        sb.append('\n');
        addText(sb);
    }

    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 186).append(' ');
        sb.append(name);
        appendDescriptor(sb, 3, desc);
        int len = sb.length();
        for (int i = 0; i < 80 - len; i++) {
            sb.append(' ');
        }
        sb.append(" [");
        appendHandle(sb, bsm);
        if (bsmArgs.length == 0) {
            sb.append("none");
        } else {
            for (Object cst : bsmArgs) {
                if (cst instanceof String) {
                    appendStr(sb, (String) cst);
                } else if (cst instanceof Type) {
                    sb.append(((Type) cst).getDescriptor()).append(".class");
                } else if (cst instanceof Handle) {
                    appendHandle(sb, (Handle) cst);
                } else if (cst instanceof Integer) {
                    int c = ((Integer) cst).intValue();
                    int pp = c >> 11;
                    if (pp != 0) {
                        sb.append(" pp=").append(pp);
                    }
                    sb.append(NashornCallSiteDescriptor.toString(c & NashornCallSiteDescriptor.FLAGS_MASK));
                } else {
                    sb.append(cst);
                }
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("]\n");
        addText(sb);
    }

    private static final boolean noFallThru(int opcode) {
        switch (opcode) {
            case 167:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 191:
                return true;
            case 168:
            case 169:
            case 170:
            case 171:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            default:
                return false;
        }
    }

    private void checkNoFallThru(int opcode, String to) {
        if (noFallThru(opcode)) {
            this.graph.setNoFallThru(this.currentBlock);
        }
        if (this.currentBlock != null && to != null) {
            this.graph.addEdge(this.currentBlock, to);
        }
    }

    public void visitJumpInsn(int opcode, Label label) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, opcode).append(' ');
        String to = appendLabel(sb, label);
        sb.append('\n');
        addText(sb);
        checkNoFallThru(opcode, to);
    }

    private void addText(Object t) {
        this.text.add(t);
        if (this.currentBlock != null) {
            this.graph.addText(this.currentBlock, t.toString());
        }
    }

    public void visitLabel(Label label) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        String name = appendLabel(sb, label);
        sb.append(" [bci=");
        sb.append(label.info);
        sb.append("]");
        sb.append("\n");
        this.graph.addNode(name);
        if (this.currentBlock != null && !this.graph.isNoFallThru(this.currentBlock)) {
            this.graph.addEdge(this.currentBlock, name);
        }
        this.currentBlock = name;
        addText(sb);
    }

    public void visitLdcInsn(Object cst) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 18).append(' ');
        if (cst instanceof String) {
            appendStr(sb, (String) cst);
        } else if (cst instanceof Type) {
            sb.append(((Type) cst).getDescriptor()).append(".class");
        } else {
            sb.append(cst);
        }
        sb.append('\n');
        addText(sb);
    }

    public void visitIincInsn(int var, int increment) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 132).append(' ');
        sb.append(var).append(' ').append(increment).append('\n');
        addText(sb);
    }

    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 170).append(' ');
        for (int i = 0; i < labels.length; i++) {
            sb.append("      ").append(min + i).append(": ");
            String to = appendLabel(sb, labels[i]);
            this.graph.addEdge(this.currentBlock, to);
            sb.append('\n');
        }
        sb.append("      ").append("default: ");
        appendLabel(sb, dflt);
        sb.append('\n');
        addText(sb);
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 171).append(' ');
        for (int i = 0; i < labels.length; i++) {
            sb.append("      ").append(keys[i]).append(": ");
            String to = appendLabel(sb, labels[i]);
            this.graph.addEdge(this.currentBlock, to);
            sb.append('\n');
        }
        sb.append("      ").append("default: ");
        String to2 = appendLabel(sb, dflt);
        this.graph.addEdge(this.currentBlock, to2);
        sb.append('\n');
        addText(sb.toString());
    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 197).append(' ');
        appendDescriptor(sb, 1, desc);
        sb.append(' ').append(dims).append('\n');
        addText(sb);
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append("try ");
        String from = appendLabel(sb, start);
        sb.append(' ');
        appendLabel(sb, end);
        sb.append(' ');
        String to = appendLabel(sb, handler);
        sb.append(' ');
        appendDescriptor(sb, 0, type);
        sb.append('\n');
        addText(sb);
        this.graph.setIsCatch(to, type);
        this.graph.addTryCatch(from, to);
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        StringBuilder sb = new StringBuilder();
        if (!this.localVarsStarted) {
            this.text.add("\n");
            this.localVarsStarted = true;
            this.graph.addNode("vars");
            this.currentBlock = "vars";
        }
        sb.append("    ").append("local ").append(name).append(' ');
        int len = sb.length();
        for (int i = 0; i < 25 - len; i++) {
            sb.append(' ');
        }
        String label = appendLabel(sb, start);
        for (int i2 = 0; i2 < 5 - label.length(); i2++) {
            sb.append(' ');
        }
        String label2 = appendLabel(sb, end);
        for (int i3 = 0; i3 < 5 - label2.length(); i3++) {
            sb.append(' ');
        }
        sb.append(index).append("    ");
        appendDescriptor(sb, 1, desc);
        sb.append('\n');
        if (signature != null) {
            sb.append("    ");
            appendDescriptor(sb, 2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            sb.append("    ").append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        addText(sb.toString());
    }

    public void visitLineNumber(int line, Label start) {
        addText("<line " + line + ">\n");
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append("    ").append("max stack  = ").append(maxStack);
        sb.append(", max locals = ").append(maxLocals).append('\n');
        addText(sb.toString());
    }

    private void printToDir(Graph g) {
        File file;
        if (this.env._print_code_dir != null) {
            File dir = new File(this.env._print_code_dir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException(dir.toString());
            }
            int uniqueId = 0;
            do {
                String fileName = g.getName() + (uniqueId == 0 ? "" : "_" + uniqueId) + ".dot";
                file = new File(dir, fileName);
                uniqueId++;
            } while (file.exists());
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(file));
                pw.println(g);
                if (pw != null) {
                    if (0 != 0) {
                        pw.close();
                    } else {
                        pw.close();
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void visitMethodEnd() {
        if ((this.env._print_code_func == null || this.env._print_code_func.equals(this.graph.getName())) && this.env._print_code_dir != null) {
            printToDir(this.graph);
        }
    }

    protected NashornTextifier createNashornTextifier() {
        return new NashornTextifier(this.env, this.f245cr, this.labelIter, this.graph);
    }

    private static void appendDescriptor(StringBuilder sb, int type, String desc) {
        if (desc != null) {
            if (type == 5 || type == 2 || type == 4) {
                sb.append("// signature ").append(desc).append('\n');
            } else {
                appendShortDescriptor(sb, desc);
            }
        }
    }

    private String appendLabel(StringBuilder sb, Label l) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        String name = this.labelNames.get(l);
        if (name == null) {
            name = "L" + this.labelNames.size();
            this.labelNames.put(l, name);
        }
        sb.append(name);
        return name;
    }

    private static void appendHandle(StringBuilder sb, Handle h) {
        switch (h.getTag()) {
            case 1:
                sb.append("getfield");
                break;
            case 2:
                sb.append("getstatic");
                break;
            case 3:
                sb.append("putfield");
                break;
            case 4:
                sb.append("putstatic");
                break;
            case 5:
                sb.append("virtual");
                break;
            case 6:
                sb.append("static");
                break;
            case 7:
                sb.append("special");
                break;
            case 8:
                sb.append("new_special");
                break;
            case 9:
                sb.append("interface");
                break;
            default:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
        }
        sb.append(" '");
        sb.append(h.getName());
        sb.append("'");
    }

    private static void appendAccess(StringBuilder sb, int access) {
        if ((access & 1) != 0) {
            sb.append("public ");
        }
        if ((access & 2) != 0) {
            sb.append("private ");
        }
        if ((access & 4) != 0) {
            sb.append("protected ");
        }
        if ((access & 16) != 0) {
            sb.append("final ");
        }
        if ((access & 8) != 0) {
            sb.append("static ");
        }
        if ((access & 32) != 0) {
            sb.append("synchronized ");
        }
        if ((access & 64) != 0) {
            sb.append("volatile ");
        }
        if ((access & 128) != 0) {
            sb.append("transient ");
        }
        if ((access & 1024) != 0) {
            sb.append("abstract ");
        }
        if ((access & 2048) != 0) {
            sb.append("strictfp ");
        }
        if ((access & 4096) != 0) {
            sb.append("synthetic ");
        }
        if ((access & 32768) != 0) {
            sb.append("mandated ");
        }
        if ((access & 16384) != 0) {
            sb.append("enum ");
        }
    }

    private void appendFrameTypes(StringBuilder sb, int n, Object[] o) {
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            if (o[i] instanceof String) {
                String desc = (String) o[i];
                if (desc.startsWith("[")) {
                    appendDescriptor(sb, 1, desc);
                } else {
                    appendDescriptor(sb, 0, desc);
                }
            } else if (o[i] instanceof Integer) {
                switch (((Integer) o[i]).intValue()) {
                    case 0:
                        appendDescriptor(sb, 1, "T");
                        continue;
                    case 1:
                        appendDescriptor(sb, 1, "I");
                        continue;
                    case 2:
                        appendDescriptor(sb, 1, "F");
                        continue;
                    case 3:
                        appendDescriptor(sb, 1, "D");
                        continue;
                    case 4:
                        appendDescriptor(sb, 1, "J");
                        continue;
                    case 5:
                        appendDescriptor(sb, 1, "N");
                        continue;
                    case 6:
                        appendDescriptor(sb, 1, "U");
                        continue;
                    default:
                        if (!$assertionsDisabled) {
                            throw new AssertionError();
                        }
                        continue;
                }
            } else {
                appendLabel(sb, (Label) o[i]);
            }
        }
    }

    private static void appendShortDescriptor(StringBuilder sb, String desc) {
        if (desc.charAt(0) == '(') {
            int i = 0;
            while (i < desc.length()) {
                if (desc.charAt(i) == 'L') {
                    int slash = i;
                    while (desc.charAt(i) != ';') {
                        i++;
                        if (desc.charAt(i) == '/') {
                            slash = i;
                        }
                    }
                    sb.append(desc.substring(slash + 1, i)).append(';');
                } else {
                    sb.append(desc.charAt(i));
                }
                i++;
            }
            return;
        }
        int lastSlash = desc.lastIndexOf(47);
        int lastBracket = desc.lastIndexOf(91);
        if (lastBracket != -1) {
            sb.append((CharSequence) desc, 0, lastBracket + 1);
        }
        sb.append(lastSlash == -1 ? desc : desc.substring(lastSlash + 1));
    }

    private static void appendStr(StringBuilder sb, String s) {
        sb.append('\"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\\') {
                sb.append("\\\\");
            } else if (c == '\"') {
                sb.append("\\\"");
            } else if (c < ' ' || c > 127) {
                sb.append("\\u");
                if (c < 16) {
                    sb.append("000");
                } else if (c < 256) {
                    sb.append("00");
                } else if (c < 4096) {
                    sb.append('0');
                }
                sb.append(Integer.toString(c, 16));
            } else {
                sb.append(c);
            }
        }
        sb.append('\"');
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornTextifier$Graph */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornTextifier$Graph.class */
    public static class Graph {
        private final String name;
        private static final String LEFT_ALIGN = "\\l";
        private static final String COLOR_CATCH = "\"#ee9999\"";
        private static final String COLOR_ORPHAN = "\"#9999bb\"";
        private static final String COLOR_DEFAULT = "\"#99bb99\"";
        private static final String COLOR_LOCALVARS = "\"#999999\"";
        static final /* synthetic */ boolean $assertionsDisabled;
        private final LinkedHashSet<String> nodes = new LinkedHashSet<>();
        private final Map<String, StringBuilder> contents = new HashMap();
        private final Map<String, Set<String>> edges = new HashMap();
        private final Set<String> hasPreds = new HashSet();
        private final Map<String, String> catches = new HashMap();
        private final Set<String> noFallThru = new HashSet();
        private final Map<String, Set<String>> exceptionMap = new HashMap();

        static {
            $assertionsDisabled = !NashornTextifier.class.desiredAssertionStatus();
        }

        Graph(String name) {
            this.name = name;
        }

        void addEdge(String from, String to) {
            Set<String> edgeSet = this.edges.get(from);
            if (edgeSet == null) {
                edgeSet = new LinkedHashSet<>();
                this.edges.put(from, edgeSet);
            }
            edgeSet.add(to);
            this.hasPreds.add(to);
        }

        void addTryCatch(String tryNode, String catchNode) {
            Set<String> tryNodes = this.exceptionMap.get(catchNode);
            if (tryNodes == null) {
                tryNodes = new HashSet<>();
                this.exceptionMap.put(catchNode, tryNodes);
            }
            if (!tryNodes.contains(tryNode)) {
                addEdge(tryNode, catchNode);
            }
            tryNodes.add(tryNode);
        }

        void addNode(String node) {
            if ($assertionsDisabled || !this.nodes.contains(node)) {
                this.nodes.add(node);
                return;
            }
            throw new AssertionError();
        }

        void setNoFallThru(String node) {
            this.noFallThru.add(node);
        }

        boolean isNoFallThru(String node) {
            return this.noFallThru.contains(node);
        }

        void setIsCatch(String node, String exception) {
            this.catches.put(node, exception);
        }

        String getName() {
            return this.name;
        }

        void addText(String node, String text) {
            StringBuilder sb = this.contents.get(node);
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (int i = 0; i < text.length(); i++) {
                switch (text.charAt(i)) {
                    case '\n':
                        sb.append(LEFT_ALIGN);
                        break;
                    case '\"':
                        sb.append("'");
                        break;
                    default:
                        sb.append(text.charAt(i));
                        break;
                }
            }
            this.contents.put(node, sb);
        }

        private static String dottyFriendly(String name) {
            return name.replace(':', '_');
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("digraph " + dottyFriendly(this.name) + " {");
            sb.append("\n");
            sb.append("\tgraph [fontname=courier]\n");
            sb.append("\tnode [style=filled,color=\"#99bb99\",fontname=courier]\n");
            sb.append("\tedge [fontname=courier]\n\n");
            Iterator<String> it = this.nodes.iterator();
            while (it.hasNext()) {
                String node = it.next();
                sb.append("\t");
                sb.append(node);
                sb.append(" [");
                sb.append("id=");
                sb.append(node);
                sb.append(", label=\"");
                String c = this.contents.get(node).toString();
                if (c.startsWith(LEFT_ALIGN)) {
                    c = c.substring(LEFT_ALIGN.length());
                }
                String ex = this.catches.get(node);
                if (ex != null) {
                    sb.append("*** CATCH: ").append(ex).append(" ***\\l");
                }
                sb.append(c);
                sb.append("\"]\n");
            }
            for (String from : this.edges.keySet()) {
                for (String to : this.edges.get(from)) {
                    sb.append("\t");
                    sb.append(from);
                    sb.append(" -> ");
                    sb.append(to);
                    sb.append("[label=\"");
                    sb.append(to);
                    sb.append("\"");
                    if (this.catches.get(to) != null) {
                        sb.append(", color=red, style=dashed");
                    }
                    sb.append(']');
                    sb.append(";\n");
                }
            }
            sb.append("\n");
            Iterator<String> it2 = this.nodes.iterator();
            while (it2.hasNext()) {
                String node2 = it2.next();
                sb.append("\t");
                sb.append(node2);
                sb.append(" [shape=box");
                if (this.catches.get(node2) != null) {
                    sb.append(", color=\"#ee9999\"");
                } else if ("vars".equals(node2)) {
                    sb.append(", shape=hexagon, color=\"#999999\"");
                } else if (!this.hasPreds.contains(node2)) {
                    sb.append(", color=\"#9999bb\"");
                }
                sb.append("]\n");
            }
            sb.append("}\n");
            return sb.toString();
        }
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornTextifier$NashornLabel */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornTextifier$NashornLabel.class */
    public static class NashornLabel extends Label {
        final Label label;
        final int bci;
        final int opcode;

        NashornLabel(Label label, int bci) {
            this.label = label;
            this.bci = bci;
            this.opcode = -1;
        }

        public NashornLabel(int opcode, int bci) {
            this.opcode = opcode;
            this.bci = bci;
            this.label = null;
        }

        Label getLabel() {
            return this.label;
        }

        public int getOffset() {
            return this.bci;
        }

        public String toString() {
            return "label " + this.bci;
        }
    }

    public Printer visitAnnotationDefault() {
        throw new AssertionError();
    }

    public Printer visitClassAnnotation(String arg0, boolean arg1) {
        return this;
    }

    public void visitClassAttribute(Attribute arg0) {
        throw new AssertionError();
    }

    public Printer visitFieldAnnotation(String arg0, boolean arg1) {
        throw new AssertionError();
    }

    public void visitFieldAttribute(Attribute arg0) {
        throw new AssertionError();
    }

    public Printer visitMethodAnnotation(String arg0, boolean arg1) {
        return this;
    }

    public void visitMethodAttribute(Attribute arg0) {
        throw new AssertionError();
    }

    public Printer visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
        throw new AssertionError();
    }

    public void visit(String arg0, Object arg1) {
        throw new AssertionError();
    }

    public Printer visitAnnotation(String arg0, String arg1) {
        throw new AssertionError();
    }

    public void visitAnnotationEnd() {
    }

    public Printer visitArray(String arg0) {
        throw new AssertionError();
    }

    public void visitEnum(String arg0, String arg1, String arg2) {
        throw new AssertionError();
    }

    public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
        throw new AssertionError();
    }
}
