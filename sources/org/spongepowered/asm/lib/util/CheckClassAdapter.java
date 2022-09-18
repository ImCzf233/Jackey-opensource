package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/CheckClassAdapter.class */
public class CheckClassAdapter extends ClassVisitor {
    private int version;
    private boolean start;
    private boolean source;
    private boolean outer;
    private boolean end;
    private Map<Label, Integer> labels;
    private boolean checkDataFlow;

    public static void main(String[] args) throws Exception {
        ClassReader cr;
        if (args.length != 1) {
            System.err.println("Verifies the given class.");
            System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
            return;
        }
        if (args[0].endsWith(".class")) {
            cr = new ClassReader(new FileInputStream(args[0]));
        } else {
            cr = new ClassReader(args[0]);
        }
        verify(cr, false, new PrintWriter(System.err));
    }

    public static void verify(ClassReader cr, ClassLoader loader, boolean dump, PrintWriter pw) {
        ClassNode cn = new ClassNode();
        cr.accept(new CheckClassAdapter(cn, false), 2);
        Type syperType = cn.superName == null ? null : Type.getObjectType(cn.superName);
        List<MethodNode> methods = cn.methods;
        List<Type> interfaces = new ArrayList<>();
        for (String str : cn.interfaces) {
            interfaces.add(Type.getObjectType(str));
        }
        for (int i = 0; i < methods.size(); i++) {
            MethodNode method = methods.get(i);
            SimpleVerifier verifier = new SimpleVerifier(Type.getObjectType(cn.name), syperType, interfaces, (cn.access & 512) != 0);
            Analyzer<BasicValue> a = new Analyzer<>(verifier);
            if (loader != null) {
                verifier.setClassLoader(loader);
            }
            try {
                a.analyze(cn.name, method);
            } catch (Exception e) {
                e.printStackTrace(pw);
            }
            if (!dump) {
            }
            printAnalyzerResult(method, a, pw);
        }
        pw.flush();
    }

    public static void verify(ClassReader cr, boolean dump, PrintWriter pw) {
        verify(cr, null, dump, pw);
    }

    public static void printAnalyzerResult(MethodNode method, Analyzer<BasicValue> a, PrintWriter pw) {
        Frame<BasicValue>[] frames = a.getFrames();
        Textifier t = new Textifier();
        TraceMethodVisitor mv = new TraceMethodVisitor(t);
        pw.println(method.name + method.desc);
        for (int j = 0; j < method.instructions.size(); j++) {
            method.instructions.get(j).accept(mv);
            StringBuilder sb = new StringBuilder();
            Frame<BasicValue> f = frames[j];
            if (f == null) {
                sb.append('?');
            } else {
                for (int k = 0; k < f.getLocals(); k++) {
                    sb.append(getShortName(f.getLocal(k).toString())).append(' ');
                }
                sb.append(" : ");
                for (int k2 = 0; k2 < f.getStackSize(); k2++) {
                    sb.append(getShortName(f.getStack(k2).toString())).append(' ');
                }
            }
            while (sb.length() < method.maxStack + method.maxLocals + 1) {
                sb.append(' ');
            }
            pw.print(Integer.toString(j + Config.MAX_REPEAT_NUM).substring(1));
            pw.print(" " + ((Object) sb) + " : " + t.text.get(t.text.size() - 1));
        }
        for (int j2 = 0; j2 < method.tryCatchBlocks.size(); j2++) {
            method.tryCatchBlocks.get(j2).accept(mv);
            pw.print(" " + t.text.get(t.text.size() - 1));
        }
        pw.println();
    }

    private static String getShortName(String name) {
        int n = name.lastIndexOf(47);
        int k = name.length();
        if (name.charAt(k - 1) == ';') {
            k--;
        }
        return n == -1 ? name : name.substring(n + 1, k);
    }

    public CheckClassAdapter(ClassVisitor cv) {
        this(cv, true);
    }

    public CheckClassAdapter(ClassVisitor cv, boolean checkDataFlow) {
        this(Opcodes.ASM5, cv, checkDataFlow);
        if (getClass() != CheckClassAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckClassAdapter(int api, ClassVisitor cv, boolean checkDataFlow) {
        super(api, cv);
        this.labels = new HashMap();
        this.checkDataFlow = checkDataFlow;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (this.start) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.start = true;
        checkState();
        checkAccess(access, 423473);
        if (name == null || !name.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(name, "class name");
        }
        if ("java/lang/Object".equals(name)) {
            if (superName != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        } else {
            CheckMethodAdapter.checkInternalName(superName, "super class name");
        }
        if (signature != null) {
            checkClassSignature(signature);
        }
        if ((access & 512) != 0 && !"java/lang/Object".equals(superName)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        if (interfaces != null) {
            for (int i = 0; i < interfaces.length; i++) {
                CheckMethodAdapter.checkInternalName(interfaces[i], "interface name at index " + i);
            }
        }
        this.version = version;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitSource(String file, String debug) {
        checkState();
        if (this.source) {
            throw new IllegalStateException("visitSource can be called only once.");
        }
        this.source = true;
        super.visitSource(file, debug);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitOuterClass(String owner, String name, String desc) {
        checkState();
        if (this.outer) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
        }
        this.outer = true;
        if (owner == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        if (desc != null) {
            CheckMethodAdapter.checkMethodDesc(desc);
        }
        super.visitOuterClass(owner, name, desc);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        checkState();
        CheckMethodAdapter.checkInternalName(name, "class name");
        if (outerName != null) {
            CheckMethodAdapter.checkInternalName(outerName, "outer class name");
        }
        if (innerName != null) {
            int start = 0;
            while (start < innerName.length() && Character.isDigit(innerName.charAt(start))) {
                start++;
            }
            if (start == 0 || start < innerName.length()) {
                CheckMethodAdapter.checkIdentifier(innerName, start, -1, "inner class name");
            }
        }
        checkAccess(access, 30239);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        checkState();
        checkAccess(access, 413919);
        CheckMethodAdapter.checkUnqualifiedName(this.version, name, "field name");
        CheckMethodAdapter.checkDesc(desc, false);
        if (signature != null) {
            checkFieldSignature(signature);
        }
        if (value != null) {
            CheckMethodAdapter.checkConstant(value);
        }
        FieldVisitor av = super.visitField(access, name, desc, signature, value);
        return new CheckFieldAdapter(av);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        CheckMethodAdapter cma;
        checkState();
        checkAccess(access, 400895);
        if (!"<init>".equals(name) && !"<clinit>".equals(name)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, name, "method name");
        }
        CheckMethodAdapter.checkMethodDesc(desc);
        if (signature != null) {
            checkMethodSignature(signature);
        }
        if (exceptions != null) {
            for (int i = 0; i < exceptions.length; i++) {
                CheckMethodAdapter.checkInternalName(exceptions[i], "exception name at index " + i);
            }
        }
        if (this.checkDataFlow) {
            cma = new CheckMethodAdapter(access, name, desc, super.visitMethod(access, name, desc, signature, exceptions), this.labels);
        } else {
            cma = new CheckMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions), this.labels);
        }
        cma.version = this.version;
        return cma;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        checkState();
        CheckMethodAdapter.checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        checkState();
        int sort = typeRef >>> 24;
        if (sort != 0 && sort != 17 && sort != 16) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        checkTypeRefAndPath(typeRef, typePath);
        CheckMethodAdapter.checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitAttribute(Attribute attr) {
        checkState();
        if (attr == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitEnd() {
        checkState();
        this.end = true;
        super.visitEnd();
    }

    private void checkState() {
        if (!this.start) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
        }
        if (this.end) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
        }
    }

    public static void checkAccess(int access, int possibleAccess) {
        if ((access & (possibleAccess ^ (-1))) != 0) {
            throw new IllegalArgumentException("Invalid access flags: " + access);
        }
        int pub = (access & 1) == 0 ? 0 : 1;
        int pri = (access & 2) == 0 ? 0 : 1;
        int pro = (access & 4) == 0 ? 0 : 1;
        if (pub + pri + pro > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + access);
        }
        int fin = (access & 16) == 0 ? 0 : 1;
        int abs = (access & 1024) == 0 ? 0 : 1;
        if (fin + abs > 1) {
            throw new IllegalArgumentException("final and abstract are mutually exclusive: " + access);
        }
    }

    public static void checkClassSignature(String signature) {
        int pos;
        int pos2 = 0;
        if (getChar(signature, 0) == '<') {
            pos2 = checkFormalTypeParameters(signature, 0);
        }
        int checkClassTypeSignature = checkClassTypeSignature(signature, pos2);
        while (true) {
            pos = checkClassTypeSignature;
            if (getChar(signature, pos) != 'L') {
                break;
            }
            checkClassTypeSignature = checkClassTypeSignature(signature, pos);
        }
        if (pos != signature.length()) {
            throw new IllegalArgumentException(signature + ": error at index " + pos);
        }
    }

    public static void checkMethodSignature(String signature) {
        int pos;
        int pos2;
        int pos3 = 0;
        if (getChar(signature, 0) == '<') {
            pos3 = checkFormalTypeParameters(signature, 0);
        }
        int checkChar = checkChar('(', signature, pos3);
        while (true) {
            pos = checkChar;
            if ("ZCBSIFJDL[T".indexOf(getChar(signature, pos)) == -1) {
                break;
            }
            checkChar = checkTypeSignature(signature, pos);
        }
        int pos4 = checkChar(')', signature, pos);
        if (getChar(signature, pos4) == 'V') {
            pos2 = pos4 + 1;
        } else {
            pos2 = checkTypeSignature(signature, pos4);
        }
        while (getChar(signature, pos2) == '^') {
            int pos5 = pos2 + 1;
            if (getChar(signature, pos5) == 'L') {
                pos2 = checkClassTypeSignature(signature, pos5);
            } else {
                pos2 = checkTypeVariableSignature(signature, pos5);
            }
        }
        if (pos2 != signature.length()) {
            throw new IllegalArgumentException(signature + ": error at index " + pos2);
        }
    }

    public static void checkFieldSignature(String signature) {
        int pos = checkFieldTypeSignature(signature, 0);
        if (pos != signature.length()) {
            throw new IllegalArgumentException(signature + ": error at index " + pos);
        }
    }

    public static void checkTypeRefAndPath(int typeRef, TypePath typePath) {
        int mask;
        switch (typeRef >>> 24) {
            case 0:
            case 1:
            case 22:
                mask = -65536;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
                throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(typeRef >>> 24));
            case 16:
            case 17:
            case 18:
            case 23:
            case 66:
                mask = -256;
                break;
            case 19:
            case 20:
            case 21:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 70:
                mask = -16777216;
                break;
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
                mask = -16776961;
                break;
        }
        if ((typeRef & (mask ^ (-1))) != 0) {
            throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(typeRef));
        }
        if (typePath != null) {
            for (int i = 0; i < typePath.getLength(); i++) {
                int step = typePath.getStep(i);
                if (step != 0 && step != 1 && step != 3 && step != 2) {
                    throw new IllegalArgumentException("Invalid type path step " + i + " in " + typePath);
                }
                if (step != 3 && typePath.getStepArgument(i) != 0) {
                    throw new IllegalArgumentException("Invalid type path step argument for step " + i + " in " + typePath);
                }
            }
        }
    }

    private static int checkFormalTypeParameters(String signature, int pos) {
        int checkFormalTypeParameter = checkFormalTypeParameter(signature, checkChar('<', signature, pos));
        while (true) {
            int pos2 = checkFormalTypeParameter;
            if (getChar(signature, pos2) != '>') {
                checkFormalTypeParameter = checkFormalTypeParameter(signature, pos2);
            } else {
                return pos2 + 1;
            }
        }
    }

    private static int checkFormalTypeParameter(String signature, int pos) {
        int pos2 = checkChar(':', signature, checkIdentifier(signature, pos));
        if ("L[T".indexOf(getChar(signature, pos2)) != -1) {
            pos2 = checkFieldTypeSignature(signature, pos2);
        }
        while (getChar(signature, pos2) == ':') {
            pos2 = checkFieldTypeSignature(signature, pos2 + 1);
        }
        return pos2;
    }

    private static int checkFieldTypeSignature(String signature, int pos) {
        switch (getChar(signature, pos)) {
            case 'L':
                return checkClassTypeSignature(signature, pos);
            case '[':
                return checkTypeSignature(signature, pos + 1);
            default:
                return checkTypeVariableSignature(signature, pos);
        }
    }

    private static int checkClassTypeSignature(String signature, int pos) {
        int pos2;
        int checkIdentifier = checkIdentifier(signature, checkChar('L', signature, pos));
        while (true) {
            pos2 = checkIdentifier;
            if (getChar(signature, pos2) != '/') {
                break;
            }
            checkIdentifier = checkIdentifier(signature, pos2 + 1);
        }
        if (getChar(signature, pos2) == '<') {
            pos2 = checkTypeArguments(signature, pos2);
        }
        while (getChar(signature, pos2) == '.') {
            pos2 = checkIdentifier(signature, pos2 + 1);
            if (getChar(signature, pos2) == '<') {
                pos2 = checkTypeArguments(signature, pos2);
            }
        }
        return checkChar(';', signature, pos2);
    }

    private static int checkTypeArguments(String signature, int pos) {
        int checkTypeArgument = checkTypeArgument(signature, checkChar('<', signature, pos));
        while (true) {
            int pos2 = checkTypeArgument;
            if (getChar(signature, pos2) != '>') {
                checkTypeArgument = checkTypeArgument(signature, pos2);
            } else {
                return pos2 + 1;
            }
        }
    }

    private static int checkTypeArgument(String signature, int pos) {
        char c = getChar(signature, pos);
        if (c == '*') {
            return pos + 1;
        }
        if (c == '+' || c == '-') {
            pos++;
        }
        return checkFieldTypeSignature(signature, pos);
    }

    private static int checkTypeVariableSignature(String signature, int pos) {
        return checkChar(';', signature, checkIdentifier(signature, checkChar('T', signature, pos)));
    }

    private static int checkTypeSignature(String signature, int pos) {
        switch (getChar(signature, pos)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
                return pos + 1;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
                return checkFieldTypeSignature(signature, pos);
        }
    }

    private static int checkIdentifier(String signature, int pos) {
        if (!Character.isJavaIdentifierStart(getChar(signature, pos))) {
            throw new IllegalArgumentException(signature + ": identifier expected at index " + pos);
        }
        while (true) {
            pos++;
            if (!Character.isJavaIdentifierPart(getChar(signature, pos))) {
                return pos;
            }
        }
    }

    private static int checkChar(char c, String signature, int pos) {
        if (getChar(signature, pos) == c) {
            return pos + 1;
        }
        throw new IllegalArgumentException(signature + ": '" + c + "' expected at index " + pos);
    }

    private static char getChar(String signature, int pos) {
        if (pos < signature.length()) {
            return signature.charAt(pos);
        }
        return (char) 0;
    }
}
