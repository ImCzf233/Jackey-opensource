package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.CharCompanionObject;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.BasicVerifier;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/CheckMethodAdapter.class */
public class CheckMethodAdapter extends MethodVisitor {
    public int version;
    private int access;
    private boolean startCode;
    private boolean endCode;
    private boolean endMethod;
    private int insnCount;
    private final Map<Label, Integer> labels;
    private Set<Label> usedLabels;
    private int expandedFrames;
    private int compressedFrames;
    private int lastFrame;
    private List<Label> handlers;
    private static final int[] TYPE = new int["BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA".length()];
    private static Field labelStatusField;

    static {
        for (int i = 0; i < TYPE.length; i++) {
            TYPE[i] = ("BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA".charAt(i) - 'A') - 1;
        }
    }

    public CheckMethodAdapter(MethodVisitor mv) {
        this(mv, new HashMap());
    }

    public CheckMethodAdapter(MethodVisitor mv, Map<Label, Integer> labels) {
        this(Opcodes.ASM5, mv, labels);
        if (getClass() != CheckMethodAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckMethodAdapter(int api, MethodVisitor mv, Map<Label, Integer> labels) {
        super(api, mv);
        this.lastFrame = -1;
        this.labels = labels;
        this.usedLabels = new HashSet();
        this.handlers = new ArrayList();
    }

    public CheckMethodAdapter(int access, String name, String desc, final MethodVisitor cmv, Map<Label, Integer> labels) {
        this(new MethodNode(Opcodes.ASM5, access, name, desc, null, null) { // from class: org.spongepowered.asm.lib.util.CheckMethodAdapter.1
            @Override // org.spongepowered.asm.lib.tree.MethodNode, org.spongepowered.asm.lib.MethodVisitor
            public void visitEnd() {
                Analyzer<BasicValue> a = new Analyzer<>(new BasicVerifier());
                try {
                    a.analyze("dummy", this);
                    accept(cmv);
                } catch (Exception e) {
                    if ((e instanceof IndexOutOfBoundsException) && this.maxLocals == 0 && this.maxStack == 0) {
                        throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
                    }
                    e.printStackTrace();
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter((Writer) sw, true);
                    CheckClassAdapter.printAnalyzerResult(this, a, pw);
                    pw.close();
                    throw new RuntimeException(e.getMessage() + ' ' + sw.toString());
                }
            }
        }, labels);
        this.access = access;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitParameter(String name, int access) {
        if (name != null) {
            checkUnqualifiedName(this.version, name, "name");
        }
        CheckClassAdapter.checkAccess(access, 36880);
        super.visitParameter(name, access);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        checkEndMethod();
        checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        checkEndMethod();
        int sort = typeRef >>> 24;
        if (sort != 1 && sort != 18 && sort != 20 && sort != 21 && sort != 22 && sort != 23) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
        checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        checkEndMethod();
        return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        checkEndMethod();
        checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitParameterAnnotation(parameter, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitAttribute(Attribute attr) {
        checkEndMethod();
        if (attr == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitCode() {
        if ((this.access & 1024) != 0) {
            throw new RuntimeException("Abstract methods cannot have code");
        }
        this.startCode = true;
        super.visitCode();
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        int mStack;
        int mLocal;
        if (this.insnCount == this.lastFrame) {
            throw new IllegalStateException("At most one frame can be visited at a given code location.");
        }
        this.lastFrame = this.insnCount;
        switch (type) {
            case -1:
            case 0:
                mLocal = Integer.MAX_VALUE;
                mStack = Integer.MAX_VALUE;
                break;
            case 1:
            case 2:
                mLocal = 3;
                mStack = 0;
                break;
            case 3:
                mLocal = 0;
                mStack = 0;
                break;
            case 4:
                mLocal = 0;
                mStack = 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid frame type " + type);
        }
        if (nLocal > mLocal) {
            throw new IllegalArgumentException("Invalid nLocal=" + nLocal + " for frame type " + type);
        }
        if (nStack > mStack) {
            throw new IllegalArgumentException("Invalid nStack=" + nStack + " for frame type " + type);
        }
        if (type != 2) {
            if (nLocal > 0 && (local == null || local.length < nLocal)) {
                throw new IllegalArgumentException("Array local[] is shorter than nLocal");
            }
            for (int i = 0; i < nLocal; i++) {
                checkFrameValue(local[i]);
            }
        }
        if (nStack > 0 && (stack == null || stack.length < nStack)) {
            throw new IllegalArgumentException("Array stack[] is shorter than nStack");
        }
        for (int i2 = 0; i2 < nStack; i2++) {
            checkFrameValue(stack[i2]);
        }
        if (type == -1) {
            this.expandedFrames++;
        } else {
            this.compressedFrames++;
        }
        if (this.expandedFrames > 0 && this.compressedFrames > 0) {
            throw new RuntimeException("Expanded and compressed frames must not be mixed.");
        }
        super.visitFrame(type, nLocal, local, nStack, stack);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInsn(int opcode) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 0);
        super.visitInsn(opcode);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIntInsn(int opcode, int operand) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 1);
        switch (opcode) {
            case 16:
                checkSignedByte(operand, "Invalid operand");
                break;
            case 17:
                checkSignedShort(operand, "Invalid operand");
                break;
            default:
                if (operand < 4 || operand > 11) {
                    throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + operand);
                }
                break;
        }
        super.visitIntInsn(opcode, operand);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitVarInsn(int opcode, int var) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 2);
        checkUnsignedShort(var, "Invalid variable index");
        super.visitVarInsn(opcode, var);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTypeInsn(int opcode, String type) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 3);
        checkInternalName(type, "type");
        if (opcode == 187 && type.charAt(0) == '[') {
            throw new IllegalArgumentException("NEW cannot be used to create arrays: " + type);
        }
        super.visitTypeInsn(opcode, type);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 4);
        checkInternalName(owner, "owner");
        checkUnqualifiedName(this.version, name, "name");
        checkDesc(desc, false);
        super.visitFieldInsn(opcode, owner, name, desc);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, desc);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, opcode == 185);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 5);
        if (opcode != 183 || !"<init>".equals(name)) {
            checkMethodIdentifier(this.version, name, "name");
        }
        checkInternalName(owner, "owner");
        checkMethodDesc(desc);
        if (opcode == 182 && itf) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (opcode == 185 && !itf) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (opcode == 183 && itf && (this.version & CharCompanionObject.MAX_VALUE) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
        }
        if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        checkStartCode();
        checkEndCode();
        checkMethodIdentifier(this.version, name, "name");
        checkMethodDesc(desc);
        if (bsm.getTag() != 6 && bsm.getTag() != 8) {
            throw new IllegalArgumentException("invalid handle tag " + bsm.getTag());
        }
        for (Object obj : bsmArgs) {
            checkLDCConstant(obj);
        }
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitJumpInsn(int opcode, Label label) {
        checkStartCode();
        checkEndCode();
        checkOpcode(opcode, 6);
        checkLabel(label, false, "label");
        checkNonDebugLabel(label);
        super.visitJumpInsn(opcode, label);
        this.usedLabels.add(label);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLabel(Label label) {
        checkStartCode();
        checkEndCode();
        checkLabel(label, false, "label");
        if (this.labels.get(label) != null) {
            throw new IllegalArgumentException("Already visited label");
        }
        this.labels.put(label, Integer.valueOf(this.insnCount));
        super.visitLabel(label);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLdcInsn(Object cst) {
        checkStartCode();
        checkEndCode();
        checkLDCConstant(cst);
        super.visitLdcInsn(cst);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIincInsn(int var, int increment) {
        checkStartCode();
        checkEndCode();
        checkUnsignedShort(var, "Invalid variable index");
        checkSignedShort(increment, "Invalid increment");
        super.visitIincInsn(var, increment);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        checkStartCode();
        checkEndCode();
        if (max < min) {
            throw new IllegalArgumentException("Max = " + max + " must be greater than or equal to min = " + min);
        }
        checkLabel(dflt, false, "default label");
        checkNonDebugLabel(dflt);
        if (labels == null || labels.length != (max - min) + 1) {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        for (int i = 0; i < labels.length; i++) {
            checkLabel(labels[i], false, "label at index " + i);
            checkNonDebugLabel(labels[i]);
        }
        super.visitTableSwitchInsn(min, max, dflt, labels);
        for (Label label : labels) {
            this.usedLabels.add(label);
        }
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        checkEndCode();
        checkStartCode();
        checkLabel(dflt, false, "default label");
        checkNonDebugLabel(dflt);
        if (keys == null || labels == null || keys.length != labels.length) {
            throw new IllegalArgumentException("There must be the same number of keys and labels");
        }
        for (int i = 0; i < labels.length; i++) {
            checkLabel(labels[i], false, "label at index " + i);
            checkNonDebugLabel(labels[i]);
        }
        super.visitLookupSwitchInsn(dflt, keys, labels);
        this.usedLabels.add(dflt);
        for (Label label : labels) {
            this.usedLabels.add(label);
        }
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMultiANewArrayInsn(String desc, int dims) {
        checkStartCode();
        checkEndCode();
        checkDesc(desc, false);
        if (desc.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + desc);
        }
        if (dims < 1) {
            throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + dims);
        }
        if (dims > desc.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + dims);
        }
        super.visitMultiANewArrayInsn(desc, dims);
        this.insnCount++;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        checkStartCode();
        checkEndCode();
        int sort = typeRef >>> 24;
        if (sort != 67 && sort != 68 && sort != 69 && sort != 70 && sort != 71 && sort != 72 && sort != 73 && sort != 74 && sort != 75) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
        checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitInsnAnnotation(typeRef, typePath, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        checkStartCode();
        checkEndCode();
        checkLabel(start, false, "start label");
        checkLabel(end, false, "end label");
        checkLabel(handler, false, "handler label");
        checkNonDebugLabel(start);
        checkNonDebugLabel(end);
        checkNonDebugLabel(handler);
        if (this.labels.get(start) != null || this.labels.get(end) != null || this.labels.get(handler) != null) {
            throw new IllegalStateException("Try catch blocks must be visited before their labels");
        }
        if (type != null) {
            checkInternalName(type, "type");
        }
        super.visitTryCatchBlock(start, end, handler, type);
        this.handlers.add(start);
        this.handlers.add(end);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        checkStartCode();
        checkEndCode();
        int sort = typeRef >>> 24;
        if (sort != 66) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
        checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(typeRef, typePath, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        checkStartCode();
        checkEndCode();
        checkUnqualifiedName(this.version, name, "name");
        checkDesc(desc, false);
        checkLabel(start, true, "start label");
        checkLabel(end, true, "end label");
        checkUnsignedShort(index, "Invalid variable index");
        int s = this.labels.get(start).intValue();
        int e = this.labels.get(end).intValue();
        if (e < s) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        checkStartCode();
        checkEndCode();
        int sort = typeRef >>> 24;
        if (sort != 64 && sort != 65) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
        checkDesc(desc, false);
        if (start == null || end == null || index == null || end.length != start.length || index.length != start.length) {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
        }
        for (int i = 0; i < start.length; i++) {
            checkLabel(start[i], true, "start label");
            checkLabel(end[i], true, "end label");
            checkUnsignedShort(index[i], "Invalid variable index");
            int s = this.labels.get(start[i]).intValue();
            int e = this.labels.get(end[i]).intValue();
            if (e < s) {
                throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
            }
        }
        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLineNumber(int line, Label start) {
        checkStartCode();
        checkEndCode();
        checkUnsignedShort(line, "Invalid line number");
        checkLabel(start, true, "start label");
        super.visitLineNumber(line, start);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMaxs(int maxStack, int maxLocals) {
        checkStartCode();
        checkEndCode();
        this.endCode = true;
        for (Label l : this.usedLabels) {
            if (this.labels.get(l) == null) {
                throw new IllegalStateException("Undefined label used");
            }
        }
        int i = 0;
        while (i < this.handlers.size()) {
            int i2 = i;
            int i3 = i + 1;
            Integer start = this.labels.get(this.handlers.get(i2));
            i = i3 + 1;
            Integer end = this.labels.get(this.handlers.get(i3));
            if (start == null || end == null) {
                throw new IllegalStateException("Undefined try catch block labels");
            }
            if (end.intValue() <= start.intValue()) {
                throw new IllegalStateException("Emty try catch block handler range");
            }
        }
        checkUnsignedShort(maxStack, "Invalid max stack");
        checkUnsignedShort(maxLocals, "Invalid max locals");
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitEnd() {
        checkEndMethod();
        this.endMethod = true;
        super.visitEnd();
    }

    void checkStartCode() {
        if (!this.startCode) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
        }
    }

    void checkEndCode() {
        if (this.endCode) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
        }
    }

    void checkEndMethod() {
        if (this.endMethod) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
        }
    }

    void checkFrameValue(Object value) {
        if (value == Opcodes.TOP || value == Opcodes.INTEGER || value == Opcodes.FLOAT || value == Opcodes.LONG || value == Opcodes.DOUBLE || value == Opcodes.NULL || value == Opcodes.UNINITIALIZED_THIS) {
            return;
        }
        if (value instanceof String) {
            checkInternalName((String) value, "Invalid stack frame value");
        } else if (!(value instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + value);
        } else {
            this.usedLabels.add((Label) value);
        }
    }

    static void checkOpcode(int opcode, int type) {
        if (opcode < 0 || opcode > 199 || TYPE[opcode] != type) {
            throw new IllegalArgumentException("Invalid opcode: " + opcode);
        }
    }

    static void checkSignedByte(int value, String msg) {
        if (value < -128 || value > 127) {
            throw new IllegalArgumentException(msg + " (must be a signed byte): " + value);
        }
    }

    static void checkSignedShort(int value, String msg) {
        if (value < -32768 || value > 32767) {
            throw new IllegalArgumentException(msg + " (must be a signed short): " + value);
        }
    }

    static void checkUnsignedShort(int value, String msg) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException(msg + " (must be an unsigned short): " + value);
        }
    }

    public static void checkConstant(Object cst) {
        if (!(cst instanceof Integer) && !(cst instanceof Float) && !(cst instanceof Long) && !(cst instanceof Double) && !(cst instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + cst);
        }
    }

    void checkLDCConstant(Object cst) {
        if (!(cst instanceof Type)) {
            if (cst instanceof Handle) {
                if ((this.version & CharCompanionObject.MAX_VALUE) < 51) {
                    throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
                }
                int tag = ((Handle) cst).getTag();
                if (tag < 1 || tag > 9) {
                    throw new IllegalArgumentException("invalid handle tag " + tag);
                }
                return;
            }
            checkConstant(cst);
            return;
        }
        int s = ((Type) cst).getSort();
        if (s != 10 && s != 9 && s != 11) {
            throw new IllegalArgumentException("Illegal LDC constant value");
        }
        if (s != 11 && (this.version & CharCompanionObject.MAX_VALUE) < 49) {
            throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
        }
        if (s == 11 && (this.version & CharCompanionObject.MAX_VALUE) < 51) {
            throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
        }
    }

    public static void checkUnqualifiedName(int version, String name, String msg) {
        if ((version & CharCompanionObject.MAX_VALUE) < 49) {
            checkIdentifier(name, msg);
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (".;[/".indexOf(name.charAt(i)) != -1) {
                throw new IllegalArgumentException("Invalid " + msg + " (must be a valid unqualified name): " + name);
            }
        }
    }

    public static void checkIdentifier(String name, String msg) {
        checkIdentifier(name, 0, -1, msg);
    }

    public static void checkIdentifier(String name, int start, int end, String msg) {
        if (name == null || (end != -1 ? end <= start : name.length() <= start)) {
            throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
        }
        if (!Character.isJavaIdentifierStart(name.charAt(start))) {
            throw new IllegalArgumentException("Invalid " + msg + " (must be a valid Java identifier): " + name);
        }
        int max = end == -1 ? name.length() : end;
        for (int i = start + 1; i < max; i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                throw new IllegalArgumentException("Invalid " + msg + " (must be a valid Java identifier): " + name);
            }
        }
    }

    public static void checkMethodIdentifier(int version, String name, String msg) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
        }
        if ((version & CharCompanionObject.MAX_VALUE) >= 49) {
            for (int i = 0; i < name.length(); i++) {
                if (".;[/<>".indexOf(name.charAt(i)) != -1) {
                    throw new IllegalArgumentException("Invalid " + msg + " (must be a valid unqualified name): " + name);
                }
            }
        } else if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + msg + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + name);
        } else {
            for (int i2 = 1; i2 < name.length(); i2++) {
                if (!Character.isJavaIdentifierPart(name.charAt(i2))) {
                    throw new IllegalArgumentException("Invalid " + msg + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + name);
                }
            }
        }
    }

    public static void checkInternalName(String name, String msg) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
        }
        if (name.charAt(0) == '[') {
            checkDesc(name, false);
        } else {
            checkInternalName(name, 0, -1, msg);
        }
    }

    static void checkInternalName(String name, int start, int end, String msg) {
        int slash;
        int max = end == -1 ? name.length() : end;
        int begin = start;
        do {
            try {
                slash = name.indexOf(47, begin + 1);
                if (slash == -1 || slash > max) {
                    slash = max;
                }
                checkIdentifier(name, begin, slash, null);
                begin = slash + 1;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid " + msg + " (must be a fully qualified class name in internal form): " + name);
            }
        } while (slash != max);
    }

    public static void checkDesc(String desc, boolean canBeVoid) {
        int end = checkDesc(desc, 0, canBeVoid);
        if (end != desc.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
        }
    }

    static int checkDesc(String desc, int start, boolean canBeVoid) {
        if (desc == null || start >= desc.length()) {
            throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
        }
        switch (desc.charAt(start)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
                return start + 1;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
                throw new IllegalArgumentException("Invalid descriptor: " + desc);
            case 'L':
                int index = desc.indexOf(59, start);
                if (index == -1 || index - start < 2) {
                    throw new IllegalArgumentException("Invalid descriptor: " + desc);
                }
                try {
                    checkInternalName(desc, start + 1, index, null);
                    return index + 1;
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid descriptor: " + desc);
                }
            case 'V':
                if (canBeVoid) {
                    return start + 1;
                }
                throw new IllegalArgumentException("Invalid descriptor: " + desc);
            case '[':
                int index2 = start + 1;
                while (index2 < desc.length() && desc.charAt(index2) == '[') {
                    index2++;
                }
                if (index2 < desc.length()) {
                    return checkDesc(desc, index2, false);
                }
                throw new IllegalArgumentException("Invalid descriptor: " + desc);
        }
    }

    public static void checkMethodDesc(String desc) {
        if (desc == null || desc.length() == 0) {
            throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
        }
        if (desc.charAt(0) != '(' || desc.length() < 3) {
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
        }
        int start = 1;
        if (desc.charAt(1) != ')') {
            while (desc.charAt(start) != 'V') {
                start = checkDesc(desc, start, false);
                if (start < desc.length()) {
                    if (desc.charAt(start) == ')') {
                    }
                }
            }
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
        }
        if (checkDesc(desc, start + 1, true) != desc.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + desc);
        }
    }

    void checkLabel(Label label, boolean checkVisited, String msg) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid " + msg + " (must not be null)");
        }
        if (checkVisited && this.labels.get(label) == null) {
            throw new IllegalArgumentException("Invalid " + msg + " (must be visited first)");
        }
    }

    private static void checkNonDebugLabel(Label label) {
        int intValue;
        Field f = getLabelStatusField();
        if (f == null) {
            intValue = 0;
        } else {
            try {
                intValue = ((Integer) f.get(label)).intValue();
            } catch (IllegalAccessException e) {
                throw new Error("Internal error");
            }
        }
        int status = intValue;
        if ((status & 1) != 0) {
            throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
        }
    }

    private static Field getLabelStatusField() {
        if (labelStatusField == null) {
            labelStatusField = getLabelField("a");
            if (labelStatusField == null) {
                labelStatusField = getLabelField("status");
            }
        }
        return labelStatusField;
    }

    private static Field getLabelField(String name) {
        try {
            Field f = Label.class.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
