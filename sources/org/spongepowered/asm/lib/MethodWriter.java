package org.spongepowered.asm.lib;

import com.viaversion.viaversion.libs.javassist.bytecode.AnnotationDefaultAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.AnnotationsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.DeprecatedAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.ExceptionsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.LineNumberAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.LocalVariableAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodParametersAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import com.viaversion.viaversion.libs.javassist.bytecode.ParameterAnnotationsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.SignatureAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.StackMap;
import com.viaversion.viaversion.libs.javassist.bytecode.StackMapTable;
import com.viaversion.viaversion.libs.javassist.bytecode.SyntheticAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.TypeAnnotationsAttribute;
import kotlin.jvm.internal.CharCompanionObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/MethodWriter.class */
public class MethodWriter extends MethodVisitor {
    static final int ACC_CONSTRUCTOR = 524288;
    static final int SAME_FRAME = 0;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
    static final int RESERVED = 128;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
    static final int CHOP_FRAME = 248;
    static final int SAME_FRAME_EXTENDED = 251;
    static final int APPEND_FRAME = 252;
    static final int FULL_FRAME = 255;
    static final int FRAMES = 0;
    static final int INSERTED_FRAMES = 1;
    static final int MAXS = 2;
    static final int NOTHING = 3;

    /* renamed from: cw */
    final ClassWriter f420cw;
    private int access;
    private final int name;
    private final int desc;
    private final String descriptor;
    String signature;
    int classReaderOffset;
    int classReaderLength;
    int exceptionCount;
    int[] exceptions;
    private ByteVector annd;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private AnnotationWriter[] panns;
    private AnnotationWriter[] ipanns;
    private int synthetics;
    private Attribute attrs;
    private ByteVector code = new ByteVector();
    private int maxStack;
    private int maxLocals;
    private int currentLocals;
    private int frameCount;
    private ByteVector stackMap;
    private int previousFrameOffset;
    private int[] previousFrame;
    private int[] frame;
    private int handlerCount;
    private Handler firstHandler;
    private Handler lastHandler;
    private int methodParametersCount;
    private ByteVector methodParameters;
    private int localVarCount;
    private ByteVector localVar;
    private int localVarTypeCount;
    private ByteVector localVarType;
    private int lineNumberCount;
    private ByteVector lineNumber;
    private int lastCodeOffset;
    private AnnotationWriter ctanns;
    private AnnotationWriter ictanns;
    private Attribute cattrs;
    private int subroutines;
    private final int compute;
    private Label labels;
    private Label previousBlock;
    private Label currentBlock;
    private int stackSize;
    private int maxStackSize;

    public MethodWriter(ClassWriter cw, int access, String name, String desc, String signature, String[] exceptions, int compute) {
        super(Opcodes.ASM5);
        if (cw.firstMethod == null) {
            cw.firstMethod = this;
        } else {
            cw.lastMethod.f419mv = this;
        }
        cw.lastMethod = this;
        this.f420cw = cw;
        this.access = access;
        if ("<init>".equals(name)) {
            this.access |= 524288;
        }
        this.name = cw.newUTF8(name);
        this.desc = cw.newUTF8(desc);
        this.descriptor = desc;
        this.signature = signature;
        if (exceptions != null && exceptions.length > 0) {
            this.exceptionCount = exceptions.length;
            this.exceptions = new int[this.exceptionCount];
            for (int i = 0; i < this.exceptionCount; i++) {
                this.exceptions[i] = cw.newClass(exceptions[i]);
            }
        }
        this.compute = compute;
        if (compute != 3) {
            int size = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
            size = (access & 8) != 0 ? size - 1 : size;
            this.maxLocals = size;
            this.currentLocals = size;
            this.labels = new Label();
            this.labels.status |= 8;
            visitLabel(this.labels);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitParameter(String name, int access) {
        if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
        }
        this.methodParametersCount++;
        this.methodParameters.putShort(name == null ? 0 : this.f420cw.newUTF8(name)).putShort(access);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        this.annd = new ByteVector();
        return new AnnotationWriter(this.f420cw, false, this.annd, null, 0);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, 2);
        if (visible) {
            aw.next = this.anns;
            this.anns = aw;
        } else {
            aw.next = this.ianns;
            this.ianns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.tanns;
            this.tanns = aw;
        } else {
            aw.next = this.itanns;
            this.itanns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(desc)) {
            this.synthetics = Math.max(this.synthetics, parameter + 1);
            return new AnnotationWriter(this.f420cw, false, bv, null, 0);
        }
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, 2);
        if (visible) {
            if (this.panns == null) {
                this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            aw.next = this.panns[parameter];
            this.panns[parameter] = aw;
        } else {
            if (this.ipanns == null) {
                this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            aw.next = this.ipanns[parameter];
            this.ipanns[parameter] = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitAttribute(Attribute attr) {
        if (attr.isCodeAttribute()) {
            attr.next = this.cattrs;
            this.cattrs = attr;
            return;
        }
        attr.next = this.attrs;
        this.attrs = attr;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitCode() {
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        int delta;
        if (this.compute == 0) {
            return;
        }
        if (this.compute == 1) {
            if (this.currentBlock.frame == null) {
                this.currentBlock.frame = new CurrentFrame();
                this.currentBlock.frame.owner = this.currentBlock;
                this.currentBlock.frame.initInputFrame(this.f420cw, this.access, Type.getArgumentTypes(this.descriptor), nLocal);
                visitImplicitFirstFrame();
            } else {
                if (type == -1) {
                    this.currentBlock.frame.set(this.f420cw, nLocal, local, nStack, stack);
                }
                visitFrame(this.currentBlock.frame);
            }
        } else if (type == -1) {
            if (this.previousFrame == null) {
                visitImplicitFirstFrame();
            }
            this.currentLocals = nLocal;
            int frameIndex = startFrame(this.code.length, nLocal, nStack);
            for (int i = 0; i < nLocal; i++) {
                if (local[i] instanceof String) {
                    int i2 = frameIndex;
                    frameIndex++;
                    this.frame[i2] = 24117248 | this.f420cw.addType((String) local[i]);
                } else if (local[i] instanceof Integer) {
                    int i3 = frameIndex;
                    frameIndex++;
                    this.frame[i3] = ((Integer) local[i]).intValue();
                } else {
                    int i4 = frameIndex;
                    frameIndex++;
                    this.frame[i4] = 25165824 | this.f420cw.addUninitializedType("", ((Label) local[i]).position);
                }
            }
            for (int i5 = 0; i5 < nStack; i5++) {
                if (stack[i5] instanceof String) {
                    int i6 = frameIndex;
                    frameIndex++;
                    this.frame[i6] = 24117248 | this.f420cw.addType((String) stack[i5]);
                } else if (stack[i5] instanceof Integer) {
                    int i7 = frameIndex;
                    frameIndex++;
                    this.frame[i7] = ((Integer) stack[i5]).intValue();
                } else {
                    int i8 = frameIndex;
                    frameIndex++;
                    this.frame[i8] = 25165824 | this.f420cw.addUninitializedType("", ((Label) stack[i5]).position);
                }
            }
            endFrame();
        } else {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
                delta = this.code.length;
            } else {
                delta = (this.code.length - this.previousFrameOffset) - 1;
                if (delta < 0) {
                    if (type == 3) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            }
            switch (type) {
                case 0:
                    this.currentLocals = nLocal;
                    this.stackMap.putByte(255).putShort(delta).putShort(nLocal);
                    for (int i9 = 0; i9 < nLocal; i9++) {
                        writeFrameType(local[i9]);
                    }
                    this.stackMap.putShort(nStack);
                    for (int i10 = 0; i10 < nStack; i10++) {
                        writeFrameType(stack[i10]);
                    }
                    break;
                case 1:
                    this.currentLocals += nLocal;
                    this.stackMap.putByte(SAME_FRAME_EXTENDED + nLocal).putShort(delta);
                    for (int i11 = 0; i11 < nLocal; i11++) {
                        writeFrameType(local[i11]);
                    }
                    break;
                case 2:
                    this.currentLocals -= nLocal;
                    this.stackMap.putByte(SAME_FRAME_EXTENDED - nLocal).putShort(delta);
                    break;
                case 3:
                    if (delta < 64) {
                        this.stackMap.putByte(delta);
                        break;
                    } else {
                        this.stackMap.putByte(SAME_FRAME_EXTENDED).putShort(delta);
                        break;
                    }
                case 4:
                    if (delta < 64) {
                        this.stackMap.putByte(64 + delta);
                    } else {
                        this.stackMap.putByte(SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED).putShort(delta);
                    }
                    writeFrameType(stack[0]);
                    break;
            }
            this.previousFrameOffset = this.code.length;
            this.frameCount++;
        }
        this.maxStack = Math.max(this.maxStack, nStack);
        this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInsn(int opcode) {
        this.lastCodeOffset = this.code.length;
        this.code.putByte(opcode);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, 0, null, null);
            } else {
                int size = this.stackSize + Frame.SIZE[opcode];
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
            if ((opcode >= 172 && opcode <= 177) || opcode == 191) {
                noSuccessor();
            }
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIntInsn(int opcode, int operand) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, operand, null, null);
            } else if (opcode != 188) {
                int size = this.stackSize + 1;
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        if (opcode == 17) {
            this.code.put12(opcode, operand);
        } else {
            this.code.put11(opcode, operand);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitVarInsn(int opcode, int var) {
        int opt;
        int n;
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, var, null, null);
            } else if (opcode == 169) {
                this.currentBlock.status |= 256;
                this.currentBlock.inputStackTop = this.stackSize;
                noSuccessor();
            } else {
                int size = this.stackSize + Frame.SIZE[opcode];
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        if (this.compute != 3) {
            if (opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57) {
                n = var + 2;
            } else {
                n = var + 1;
            }
            if (n > this.maxLocals) {
                this.maxLocals = n;
            }
        }
        if (var < 4 && opcode != 169) {
            if (opcode < 54) {
                opt = 26 + ((opcode - 21) << 2) + var;
            } else {
                opt = 59 + ((opcode - 54) << 2) + var;
            }
            this.code.putByte(opt);
        } else if (var >= 256) {
            this.code.putByte(Opcode.WIDE).put12(opcode, var);
        } else {
            this.code.put11(opcode, var);
        }
        if (opcode >= 54 && this.compute == 0 && this.handlerCount > 0) {
            visitLabel(new Label());
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTypeInsn(int opcode, String type) {
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newClassItem(type);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, this.code.length, this.f420cw, i);
            } else if (opcode == 187) {
                int size = this.stackSize + 1;
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        this.code.put12(opcode, i.index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        int size;
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newFieldItem(owner, name, desc);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, 0, this.f420cw, i);
            } else {
                char c = desc.charAt(0);
                switch (opcode) {
                    case 178:
                        size = this.stackSize + ((c == 'D' || c == 'J') ? 2 : 1);
                        break;
                    case 179:
                        size = this.stackSize + ((c == 'D' || c == 'J') ? -2 : -1);
                        break;
                    case 180:
                        size = this.stackSize + ((c == 'D' || c == 'J') ? 1 : 0);
                        break;
                    default:
                        size = this.stackSize + ((c == 'D' || c == 'J') ? -3 : -2);
                        break;
                }
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        this.code.put12(opcode, i.index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        int size;
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newMethodItem(owner, name, desc, itf);
        int argSize = i.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(opcode, 0, this.f420cw, i);
            } else {
                if (argSize == 0) {
                    argSize = Type.getArgumentsAndReturnSizes(desc);
                    i.intVal = argSize;
                }
                if (opcode == 184) {
                    size = (this.stackSize - (argSize >> 2)) + (argSize & 3) + 1;
                } else {
                    size = (this.stackSize - (argSize >> 2)) + (argSize & 3);
                }
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        if (opcode == 185) {
            if (argSize == 0) {
                argSize = Type.getArgumentsAndReturnSizes(desc);
                i.intVal = argSize;
            }
            this.code.put12(185, i.index).put11(argSize >> 2, 0);
            return;
        }
        this.code.put12(opcode, i.index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newInvokeDynamicItem(name, desc, bsm, bsmArgs);
        int argSize = i.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(186, 0, this.f420cw, i);
            } else {
                if (argSize == 0) {
                    argSize = Type.getArgumentsAndReturnSizes(desc);
                    i.intVal = argSize;
                }
                int size = (this.stackSize - (argSize >> 2)) + (argSize & 3) + 1;
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        this.code.put12(186, i.index);
        this.code.putShort(0);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitJumpInsn(int opcode, Label label) {
        boolean isWide = opcode >= 200;
        int opcode2 = isWide ? opcode - 33 : opcode;
        this.lastCodeOffset = this.code.length;
        Label nextInsn = null;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(opcode2, 0, null, null);
                label.getFirst().status |= 16;
                addSuccessor(0, label);
                if (opcode2 != 167) {
                    nextInsn = new Label();
                }
            } else if (this.compute == 1) {
                this.currentBlock.frame.execute(opcode2, 0, null, null);
            } else if (opcode2 == 168) {
                if ((label.status & 512) == 0) {
                    label.status |= 512;
                    this.subroutines++;
                }
                this.currentBlock.status |= 128;
                addSuccessor(this.stackSize + 1, label);
                nextInsn = new Label();
            } else {
                this.stackSize += Frame.SIZE[opcode2];
                addSuccessor(this.stackSize, label);
            }
        }
        if ((label.status & 2) != 0 && label.position - this.code.length < -32768) {
            if (opcode2 == 167) {
                this.code.putByte(200);
            } else if (opcode2 == 168) {
                this.code.putByte(Opcode.JSR_W);
            } else {
                if (nextInsn != null) {
                    nextInsn.status |= 16;
                }
                this.code.putByte(opcode2 <= 166 ? ((opcode2 + 1) ^ 1) - 1 : opcode2 ^ 1);
                this.code.putShort(8);
                this.code.putByte(200);
            }
            label.put(this, this.code, this.code.length - 1, true);
        } else if (isWide) {
            this.code.putByte(opcode2 + 33);
            label.put(this, this.code, this.code.length - 1, true);
        } else {
            this.code.putByte(opcode2);
            label.put(this, this.code, this.code.length - 1, false);
        }
        if (this.currentBlock != null) {
            if (nextInsn != null) {
                visitLabel(nextInsn);
            }
            if (opcode2 == 167) {
                noSuccessor();
            }
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLabel(Label label) {
        this.f420cw.hasAsmInsns |= label.resolve(this, this.code.length, this.code.data);
        if ((label.status & 1) != 0) {
            return;
        }
        if (this.compute == 0) {
            if (this.currentBlock != null) {
                if (label.position == this.currentBlock.position) {
                    this.currentBlock.status |= label.status & 16;
                    label.frame = this.currentBlock.frame;
                    return;
                }
                addSuccessor(0, label);
            }
            this.currentBlock = label;
            if (label.frame == null) {
                label.frame = new Frame();
                label.frame.owner = label;
            }
            if (this.previousBlock != null) {
                if (label.position == this.previousBlock.position) {
                    this.previousBlock.status |= label.status & 16;
                    label.frame = this.previousBlock.frame;
                    this.currentBlock = this.previousBlock;
                    return;
                }
                this.previousBlock.successor = label;
            }
            this.previousBlock = label;
        } else if (this.compute == 1) {
            if (this.currentBlock == null) {
                this.currentBlock = label;
            } else {
                this.currentBlock.frame.owner = label;
            }
        } else if (this.compute == 2) {
            if (this.currentBlock != null) {
                this.currentBlock.outputStackMax = this.maxStackSize;
                addSuccessor(this.stackSize, label);
            }
            this.currentBlock = label;
            this.stackSize = 0;
            this.maxStackSize = 0;
            if (this.previousBlock != null) {
                this.previousBlock.successor = label;
            }
            this.previousBlock = label;
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLdcInsn(Object cst) {
        int size;
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newConstItem(cst);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(18, 0, this.f420cw, i);
            } else {
                if (i.type == 5 || i.type == 6) {
                    size = this.stackSize + 2;
                } else {
                    size = this.stackSize + 1;
                }
                if (size > this.maxStackSize) {
                    this.maxStackSize = size;
                }
                this.stackSize = size;
            }
        }
        int index = i.index;
        if (i.type == 5 || i.type == 6) {
            this.code.put12(20, index);
        } else if (index >= 256) {
            this.code.put12(19, index);
        } else {
            this.code.put11(18, index);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIincInsn(int var, int increment) {
        int n;
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null && (this.compute == 0 || this.compute == 1)) {
            this.currentBlock.frame.execute(132, var, null, null);
        }
        if (this.compute != 3 && (n = var + 1) > this.maxLocals) {
            this.maxLocals = n;
        }
        if (var > 255 || increment > 127 || increment < -128) {
            this.code.putByte(Opcode.WIDE).put12(132, var).putShort(increment);
        } else {
            this.code.putByte(132).put11(var, increment);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        this.lastCodeOffset = this.code.length;
        int source = this.code.length;
        this.code.putByte(170);
        this.code.putByteArray(null, 0, (4 - (this.code.length % 4)) % 4);
        dflt.put(this, this.code, source, true);
        this.code.putInt(min).putInt(max);
        for (Label label : labels) {
            label.put(this, this.code, source, true);
        }
        visitSwitchInsn(dflt, labels);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.lastCodeOffset = this.code.length;
        int source = this.code.length;
        this.code.putByte(171);
        this.code.putByteArray(null, 0, (4 - (this.code.length % 4)) % 4);
        dflt.put(this, this.code, source, true);
        this.code.putInt(labels.length);
        for (int i = 0; i < labels.length; i++) {
            this.code.putInt(keys[i]);
            labels[i].put(this, this.code, source, true);
        }
        visitSwitchInsn(dflt, labels);
    }

    private void visitSwitchInsn(Label dflt, Label[] labels) {
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(171, 0, null, null);
                addSuccessor(0, dflt);
                dflt.getFirst().status |= 16;
                for (int i = 0; i < labels.length; i++) {
                    addSuccessor(0, labels[i]);
                    labels[i].getFirst().status |= 16;
                }
            } else {
                this.stackSize--;
                addSuccessor(this.stackSize, dflt);
                for (Label label : labels) {
                    addSuccessor(this.stackSize, label);
                }
            }
            noSuccessor();
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.lastCodeOffset = this.code.length;
        Item i = this.f420cw.newClassItem(desc);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(197, dims, this.f420cw, i);
            } else {
                this.stackSize += 1 - dims;
            }
        }
        this.code.put12(197, i.index).putByte(dims);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget((typeRef & (-16776961)) | (this.lastCodeOffset << 8), typePath, bv);
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.ctanns;
            this.ctanns = aw;
        } else {
            aw.next = this.ictanns;
            this.ictanns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.handlerCount++;
        Handler h = new Handler();
        h.start = start;
        h.end = end;
        h.handler = handler;
        h.desc = type;
        h.type = type != null ? this.f420cw.newClass(type) : 0;
        if (this.lastHandler == null) {
            this.firstHandler = h;
        } else {
            this.lastHandler.next = h;
        }
        this.lastHandler = h;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.ctanns;
            this.ctanns = aw;
        } else {
            aw.next = this.ictanns;
            this.ictanns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if (signature != null) {
            if (this.localVarType == null) {
                this.localVarType = new ByteVector();
            }
            this.localVarTypeCount++;
            this.localVarType.putShort(start.position).putShort(end.position - start.position).putShort(this.f420cw.newUTF8(name)).putShort(this.f420cw.newUTF8(signature)).putShort(index);
        }
        if (this.localVar == null) {
            this.localVar = new ByteVector();
        }
        this.localVarCount++;
        this.localVar.putShort(start.position).putShort(end.position - start.position).putShort(this.f420cw.newUTF8(name)).putShort(this.f420cw.newUTF8(desc)).putShort(index);
        if (this.compute != 3) {
            char c = desc.charAt(0);
            int n = index + ((c == 'J' || c == 'D') ? 2 : 1);
            if (n > this.maxLocals) {
                this.maxLocals = n;
            }
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        bv.putByte(typeRef >>> 24).putShort(start.length);
        for (int i = 0; i < start.length; i++) {
            bv.putShort(start[i].position).putShort(end[i].position - start[i].position).putShort(index[i]);
        }
        if (typePath == null) {
            bv.putByte(0);
        } else {
            int length = (typePath.f421b[typePath.offset] * 2) + 1;
            bv.putByteArray(typePath.f421b, typePath.offset, length);
        }
        bv.putShort(this.f420cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f420cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.ctanns;
            this.ctanns = aw;
        } else {
            aw.next = this.ictanns;
            this.ictanns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLineNumber(int line, Label start) {
        if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
        }
        this.lineNumberCount++;
        this.lineNumber.putShort(start.position);
        this.lineNumber.putShort(line);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMaxs(int maxStack, int maxLocals) {
        if (this.compute != 0) {
            if (this.compute == 2) {
                Handler handler = this.firstHandler;
                while (true) {
                    Handler handler2 = handler;
                    if (handler2 == null) {
                        break;
                    }
                    Label h = handler2.handler;
                    Label e = handler2.end;
                    for (Label l = handler2.start; l != e; l = l.successor) {
                        Edge b = new Edge();
                        b.info = Integer.MAX_VALUE;
                        b.successor = h;
                        if ((l.status & 128) == 0) {
                            b.next = l.successors;
                            l.successors = b;
                        } else {
                            b.next = l.successors.next.next;
                            l.successors.next.next = b;
                        }
                    }
                    handler = handler2.next;
                }
                if (this.subroutines > 0) {
                    int id = 0;
                    this.labels.visitSubroutine(null, 1L, this.subroutines);
                    Label label = this.labels;
                    while (true) {
                        Label l2 = label;
                        if (l2 == null) {
                            break;
                        }
                        if ((l2.status & 128) != 0) {
                            Label subroutine = l2.successors.next.successor;
                            if ((subroutine.status & 1024) == 0) {
                                id++;
                                subroutine.visitSubroutine(null, ((id / 32) << 32) | (1 << (id % 32)), this.subroutines);
                            }
                        }
                        label = l2.successor;
                    }
                    Label label2 = this.labels;
                    while (true) {
                        Label l3 = label2;
                        if (l3 == null) {
                            break;
                        }
                        if ((l3.status & 128) != 0) {
                            Label label3 = this.labels;
                            while (true) {
                                Label L = label3;
                                if (L == null) {
                                    break;
                                }
                                L.status &= -2049;
                                label3 = L.successor;
                            }
                            l3.successors.next.successor.visitSubroutine(l3, 0L, this.subroutines);
                        }
                        label2 = l3.successor;
                    }
                }
                int max = 0;
                Label stack = this.labels;
                while (stack != null) {
                    Label l4 = stack;
                    stack = stack.next;
                    int start = l4.inputStackTop;
                    int blockMax = start + l4.outputStackMax;
                    if (blockMax > max) {
                        max = blockMax;
                    }
                    Edge b2 = l4.successors;
                    if ((l4.status & 128) != 0) {
                        b2 = b2.next;
                    }
                    while (b2 != null) {
                        Label l5 = b2.successor;
                        if ((l5.status & 8) == 0) {
                            l5.inputStackTop = b2.info == Integer.MAX_VALUE ? 1 : start + b2.info;
                            l5.status |= 8;
                            l5.next = stack;
                            stack = l5;
                        }
                        b2 = b2.next;
                    }
                }
                this.maxStack = Math.max(maxStack, max);
                return;
            }
            this.maxStack = maxStack;
            this.maxLocals = maxLocals;
            return;
        }
        Handler handler3 = this.firstHandler;
        while (true) {
            Handler handler4 = handler3;
            if (handler4 == null) {
                break;
            }
            Label h2 = handler4.handler.getFirst();
            Label e2 = handler4.end.getFirst();
            String t = handler4.desc == null ? "java/lang/Throwable" : handler4.desc;
            int kind = 24117248 | this.f420cw.addType(t);
            h2.status |= 16;
            for (Label l6 = handler4.start.getFirst(); l6 != e2; l6 = l6.successor) {
                Edge b3 = new Edge();
                b3.info = kind;
                b3.successor = h2;
                b3.next = l6.successors;
                l6.successors = b3;
            }
            handler3 = handler4.next;
        }
        Frame f = this.labels.frame;
        f.initInputFrame(this.f420cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
        visitFrame(f);
        int max2 = 0;
        Label changed = this.labels;
        while (changed != null) {
            Label l7 = changed;
            changed = changed.next;
            l7.next = null;
            Frame f2 = l7.frame;
            if ((l7.status & 16) != 0) {
                l7.status |= 32;
            }
            l7.status |= 64;
            int blockMax2 = f2.inputStack.length + l7.outputStackMax;
            if (blockMax2 > max2) {
                max2 = blockMax2;
            }
            Edge edge = l7.successors;
            while (true) {
                Edge e3 = edge;
                if (e3 != null) {
                    Label n = e3.successor.getFirst();
                    boolean change = f2.merge(this.f420cw, n.frame, e3.info);
                    if (change && n.next == null) {
                        n.next = changed;
                        changed = n;
                    }
                    edge = e3.next;
                }
            }
        }
        Label label4 = this.labels;
        while (true) {
            Label l8 = label4;
            if (l8 == null) {
                break;
            }
            Frame f3 = l8.frame;
            if ((l8.status & 32) != 0) {
                visitFrame(f3);
            }
            if ((l8.status & 64) == 0) {
                Label k = l8.successor;
                int start2 = l8.position;
                int end = (k == null ? this.code.length : k.position) - 1;
                if (end >= start2) {
                    max2 = Math.max(max2, 1);
                    for (int i = start2; i < end; i++) {
                        this.code.data[i] = 0;
                    }
                    this.code.data[end] = -65;
                    int frameIndex = startFrame(start2, 0, 1);
                    this.frame[frameIndex] = 24117248 | this.f420cw.addType("java/lang/Throwable");
                    endFrame();
                    this.firstHandler = Handler.remove(this.firstHandler, l8, k);
                }
            }
            label4 = l8.successor;
        }
        this.handlerCount = 0;
        for (Handler handler5 = this.firstHandler; handler5 != null; handler5 = handler5.next) {
            this.handlerCount++;
        }
        this.maxStack = max2;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitEnd() {
    }

    private void addSuccessor(int info, Label successor) {
        Edge b = new Edge();
        b.info = info;
        b.successor = successor;
        b.next = this.currentBlock.successors;
        this.currentBlock.successors = b;
    }

    private void noSuccessor() {
        if (this.compute == 0) {
            Label l = new Label();
            l.frame = new Frame();
            l.frame.owner = l;
            l.resolve(this, this.code.length, this.code.data);
            this.previousBlock.successor = l;
            this.previousBlock = l;
        } else {
            this.currentBlock.outputStackMax = this.maxStackSize;
        }
        if (this.compute != 1) {
            this.currentBlock = null;
        }
    }

    private void visitFrame(Frame f) {
        int nTop = 0;
        int nLocal = 0;
        int nStack = 0;
        int[] locals = f.inputLocals;
        int[] stacks = f.inputStack;
        int i = 0;
        while (i < locals.length) {
            int t = locals[i];
            if (t == 16777216) {
                nTop++;
            } else {
                nLocal += nTop + 1;
                nTop = 0;
            }
            if (t == 16777220 || t == 16777219) {
                i++;
            }
            i++;
        }
        int i2 = 0;
        while (i2 < stacks.length) {
            int t2 = stacks[i2];
            nStack++;
            if (t2 == 16777220 || t2 == 16777219) {
                i2++;
            }
            i2++;
        }
        int frameIndex = startFrame(f.owner.position, nLocal, nStack);
        int i3 = 0;
        while (nLocal > 0) {
            int t3 = locals[i3];
            int i4 = frameIndex;
            frameIndex++;
            this.frame[i4] = t3;
            if (t3 == 16777220 || t3 == 16777219) {
                i3++;
            }
            i3++;
            nLocal--;
        }
        int i5 = 0;
        while (i5 < stacks.length) {
            int t4 = stacks[i5];
            int i6 = frameIndex;
            frameIndex++;
            this.frame[i6] = t4;
            if (t4 == 16777220 || t4 == 16777219) {
                i5++;
            }
            i5++;
        }
        endFrame();
    }

    private void visitImplicitFirstFrame() {
        int frameIndex = startFrame(0, this.descriptor.length() + 1, 0);
        if ((this.access & 8) == 0) {
            if ((this.access & 524288) == 0) {
                frameIndex++;
                this.frame[frameIndex] = 24117248 | this.f420cw.addType(this.f420cw.thisName);
            } else {
                frameIndex++;
                this.frame[frameIndex] = 6;
            }
        }
        int i = 1;
        while (true) {
            int j = i;
            int i2 = i;
            i++;
            switch (this.descriptor.charAt(i2)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    int i3 = frameIndex;
                    frameIndex++;
                    this.frame[i3] = 1;
                    break;
                case 'D':
                    int i4 = frameIndex;
                    frameIndex++;
                    this.frame[i4] = 3;
                    break;
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
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                default:
                    this.frame[1] = frameIndex - 3;
                    endFrame();
                    return;
                case 'F':
                    int i5 = frameIndex;
                    frameIndex++;
                    this.frame[i5] = 2;
                    break;
                case 'J':
                    int i6 = frameIndex;
                    frameIndex++;
                    this.frame[i6] = 4;
                    break;
                case 'L':
                    while (this.descriptor.charAt(i) != ';') {
                        i++;
                    }
                    int i7 = frameIndex;
                    frameIndex++;
                    int i8 = i;
                    i++;
                    this.frame[i7] = 24117248 | this.f420cw.addType(this.descriptor.substring(j + 1, i8));
                    break;
                case '[':
                    while (this.descriptor.charAt(i) == '[') {
                        i++;
                    }
                    if (this.descriptor.charAt(i) == 'L') {
                        while (true) {
                            i++;
                            if (this.descriptor.charAt(i) != ';') {
                            }
                        }
                    }
                    int i9 = frameIndex;
                    frameIndex++;
                    i++;
                    this.frame[i9] = 24117248 | this.f420cw.addType(this.descriptor.substring(j, i));
                    break;
            }
        }
    }

    private int startFrame(int offset, int nLocal, int nStack) {
        int n = 3 + nLocal + nStack;
        if (this.frame == null || this.frame.length < n) {
            this.frame = new int[n];
        }
        this.frame[0] = offset;
        this.frame[1] = nLocal;
        this.frame[2] = nStack;
        return 3;
    }

    private void endFrame() {
        if (this.previousFrame != null) {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
            }
            writeFrame();
            this.frameCount++;
        }
        this.previousFrame = this.frame;
        this.frame = null;
    }

    private void writeFrame() {
        int delta;
        int clocalsSize = this.frame[1];
        int cstackSize = this.frame[2];
        if ((this.f420cw.version & CharCompanionObject.MAX_VALUE) < 50) {
            this.stackMap.putShort(this.frame[0]).putShort(clocalsSize);
            writeFrameTypes(3, 3 + clocalsSize);
            this.stackMap.putShort(cstackSize);
            writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
            return;
        }
        int localsSize = this.previousFrame[1];
        int type = 255;
        int k = 0;
        if (this.frameCount == 0) {
            delta = this.frame[0];
        } else {
            delta = (this.frame[0] - this.previousFrame[0]) - 1;
        }
        if (cstackSize == 0) {
            k = clocalsSize - localsSize;
            switch (k) {
                case -3:
                case -2:
                case -1:
                    type = CHOP_FRAME;
                    localsSize = clocalsSize;
                    break;
                case 0:
                    type = delta < 64 ? 0 : SAME_FRAME_EXTENDED;
                    break;
                case 1:
                case 2:
                case 3:
                    type = APPEND_FRAME;
                    break;
            }
        } else if (clocalsSize == localsSize && cstackSize == 1) {
            type = delta < 63 ? 64 : SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED;
        }
        if (type != 255) {
            int l = 3;
            int j = 0;
            while (true) {
                if (j < localsSize) {
                    if (this.frame[l] != this.previousFrame[l]) {
                        type = 255;
                    } else {
                        l++;
                        j++;
                    }
                }
            }
        }
        switch (type) {
            case 0:
                this.stackMap.putByte(delta);
                return;
            case 64:
                this.stackMap.putByte(64 + delta);
                writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
                return;
            case SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED /* 247 */:
                this.stackMap.putByte(SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED).putShort(delta);
                writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
                return;
            case CHOP_FRAME /* 248 */:
                this.stackMap.putByte(SAME_FRAME_EXTENDED + k).putShort(delta);
                return;
            case SAME_FRAME_EXTENDED /* 251 */:
                this.stackMap.putByte(SAME_FRAME_EXTENDED).putShort(delta);
                return;
            case APPEND_FRAME /* 252 */:
                this.stackMap.putByte(SAME_FRAME_EXTENDED + k).putShort(delta);
                writeFrameTypes(3 + localsSize, 3 + clocalsSize);
                return;
            default:
                this.stackMap.putByte(255).putShort(delta).putShort(clocalsSize);
                writeFrameTypes(3, 3 + clocalsSize);
                this.stackMap.putShort(cstackSize);
                writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
                return;
        }
    }

    private void writeFrameTypes(int start, int end) {
        for (int i = start; i < end; i++) {
            int t = this.frame[i];
            int d = t & (-268435456);
            if (d == 0) {
                int v = t & 1048575;
                switch (t & 267386880) {
                    case 24117248:
                        this.stackMap.putByte(7).putShort(this.f420cw.newClass(this.f420cw.typeTable[v].strVal1));
                        continue;
                    case 25165824:
                        this.stackMap.putByte(8).putShort(this.f420cw.typeTable[v].intVal);
                        continue;
                    default:
                        this.stackMap.putByte(v);
                        continue;
                }
            } else {
                StringBuilder sb = new StringBuilder();
                int d2 = d >> 28;
                while (true) {
                    int i2 = d2;
                    d2--;
                    if (i2 > 0) {
                        sb.append('[');
                    } else {
                        if ((t & 267386880) == 24117248) {
                            sb.append('L');
                            sb.append(this.f420cw.typeTable[t & 1048575].strVal1);
                            sb.append(';');
                        } else {
                            switch (t & 15) {
                                case 1:
                                    sb.append('I');
                                    break;
                                case 2:
                                    sb.append('F');
                                    break;
                                case 3:
                                    sb.append('D');
                                    break;
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                default:
                                    sb.append('J');
                                    break;
                                case 9:
                                    sb.append('Z');
                                    break;
                                case 10:
                                    sb.append('B');
                                    break;
                                case 11:
                                    sb.append('C');
                                    break;
                                case 12:
                                    sb.append('S');
                                    break;
                            }
                        }
                        this.stackMap.putByte(7).putShort(this.f420cw.newClass(sb.toString()));
                    }
                }
            }
        }
    }

    private void writeFrameType(Object type) {
        if (type instanceof String) {
            this.stackMap.putByte(7).putShort(this.f420cw.newClass((String) type));
        } else if (type instanceof Integer) {
            this.stackMap.putByte(((Integer) type).intValue());
        } else {
            this.stackMap.putByte(8).putShort(((Label) type).position);
        }
    }

    public final int getSize() {
        if (this.classReaderOffset != 0) {
            return 6 + this.classReaderLength;
        }
        int size = 8;
        if (this.code.length > 0) {
            if (this.code.length > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.f420cw.newUTF8(CodeAttribute.tag);
            size = 8 + 18 + this.code.length + (8 * this.handlerCount);
            if (this.localVar != null) {
                this.f420cw.newUTF8(LocalVariableAttribute.tag);
                size += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                this.f420cw.newUTF8("LocalVariableTypeTable");
                size += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                this.f420cw.newUTF8(LineNumberAttribute.tag);
                size += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                boolean zip = (this.f420cw.version & CharCompanionObject.MAX_VALUE) >= 50;
                this.f420cw.newUTF8(zip ? StackMapTable.tag : StackMap.tag);
                size += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                this.f420cw.newUTF8(TypeAnnotationsAttribute.visibleTag);
                size += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                this.f420cw.newUTF8(TypeAnnotationsAttribute.invisibleTag);
                size += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                size += this.cattrs.getSize(this.f420cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
        }
        if (this.exceptionCount > 0) {
            this.f420cw.newUTF8(ExceptionsAttribute.tag);
            size += 8 + (2 * this.exceptionCount);
        }
        if ((this.access & 4096) != 0 && ((this.f420cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            this.f420cw.newUTF8(SyntheticAttribute.tag);
            size += 6;
        }
        if ((this.access & 131072) != 0) {
            this.f420cw.newUTF8(DeprecatedAttribute.tag);
            size += 6;
        }
        if (this.signature != null) {
            this.f420cw.newUTF8(SignatureAttribute.tag);
            this.f420cw.newUTF8(this.signature);
            size += 8;
        }
        if (this.methodParameters != null) {
            this.f420cw.newUTF8(MethodParametersAttribute.tag);
            size += 7 + this.methodParameters.length;
        }
        if (this.annd != null) {
            this.f420cw.newUTF8(AnnotationDefaultAttribute.tag);
            size += 6 + this.annd.length;
        }
        if (this.anns != null) {
            this.f420cw.newUTF8(AnnotationsAttribute.visibleTag);
            size += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.f420cw.newUTF8(AnnotationsAttribute.invisibleTag);
            size += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.f420cw.newUTF8(TypeAnnotationsAttribute.visibleTag);
            size += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.f420cw.newUTF8(TypeAnnotationsAttribute.invisibleTag);
            size += 8 + this.itanns.getSize();
        }
        if (this.panns != null) {
            this.f420cw.newUTF8(ParameterAnnotationsAttribute.visibleTag);
            size += 7 + (2 * (this.panns.length - this.synthetics));
            for (int i = this.panns.length - 1; i >= this.synthetics; i--) {
                size += this.panns[i] == null ? 0 : this.panns[i].getSize();
            }
        }
        if (this.ipanns != null) {
            this.f420cw.newUTF8(ParameterAnnotationsAttribute.invisibleTag);
            size += 7 + (2 * (this.ipanns.length - this.synthetics));
            for (int i2 = this.ipanns.length - 1; i2 >= this.synthetics; i2--) {
                size += this.ipanns[i2] == null ? 0 : this.ipanns[i2].getSize();
            }
        }
        if (this.attrs != null) {
            size += this.attrs.getSize(this.f420cw, null, 0, -1, -1);
        }
        return size;
    }

    public final void put(ByteVector out) {
        int mask = 917504 | ((this.access & 262144) / 64);
        out.putShort(this.access & (mask ^ (-1))).putShort(this.name).putShort(this.desc);
        if (this.classReaderOffset != 0) {
            out.putByteArray(this.f420cw.f416cr.f414b, this.classReaderOffset, this.classReaderLength);
            return;
        }
        int attributeCount = 0;
        if (this.code.length > 0) {
            attributeCount = 0 + 1;
        }
        if (this.exceptionCount > 0) {
            attributeCount++;
        }
        if ((this.access & 4096) != 0 && ((this.f420cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            attributeCount++;
        }
        if ((this.access & 131072) != 0) {
            attributeCount++;
        }
        if (this.signature != null) {
            attributeCount++;
        }
        if (this.methodParameters != null) {
            attributeCount++;
        }
        if (this.annd != null) {
            attributeCount++;
        }
        if (this.anns != null) {
            attributeCount++;
        }
        if (this.ianns != null) {
            attributeCount++;
        }
        if (this.tanns != null) {
            attributeCount++;
        }
        if (this.itanns != null) {
            attributeCount++;
        }
        if (this.panns != null) {
            attributeCount++;
        }
        if (this.ipanns != null) {
            attributeCount++;
        }
        if (this.attrs != null) {
            attributeCount += this.attrs.getCount();
        }
        out.putShort(attributeCount);
        if (this.code.length > 0) {
            int size = 12 + this.code.length + (8 * this.handlerCount);
            if (this.localVar != null) {
                size += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                size += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                size += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                size += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                size += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                size += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                size += this.cattrs.getSize(this.f420cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
            out.putShort(this.f420cw.newUTF8(CodeAttribute.tag)).putInt(size);
            out.putShort(this.maxStack).putShort(this.maxLocals);
            out.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            out.putShort(this.handlerCount);
            if (this.handlerCount > 0) {
                Handler handler = this.firstHandler;
                while (true) {
                    Handler h = handler;
                    if (h == null) {
                        break;
                    }
                    out.putShort(h.start.position).putShort(h.end.position).putShort(h.handler.position).putShort(h.type);
                    handler = h.next;
                }
            }
            int attributeCount2 = 0;
            if (this.localVar != null) {
                attributeCount2 = 0 + 1;
            }
            if (this.localVarType != null) {
                attributeCount2++;
            }
            if (this.lineNumber != null) {
                attributeCount2++;
            }
            if (this.stackMap != null) {
                attributeCount2++;
            }
            if (this.ctanns != null) {
                attributeCount2++;
            }
            if (this.ictanns != null) {
                attributeCount2++;
            }
            if (this.cattrs != null) {
                attributeCount2 += this.cattrs.getCount();
            }
            out.putShort(attributeCount2);
            if (this.localVar != null) {
                out.putShort(this.f420cw.newUTF8(LocalVariableAttribute.tag));
                out.putInt(this.localVar.length + 2).putShort(this.localVarCount);
                out.putByteArray(this.localVar.data, 0, this.localVar.length);
            }
            if (this.localVarType != null) {
                out.putShort(this.f420cw.newUTF8("LocalVariableTypeTable"));
                out.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
                out.putByteArray(this.localVarType.data, 0, this.localVarType.length);
            }
            if (this.lineNumber != null) {
                out.putShort(this.f420cw.newUTF8(LineNumberAttribute.tag));
                out.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
                out.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
            }
            if (this.stackMap != null) {
                boolean zip = (this.f420cw.version & CharCompanionObject.MAX_VALUE) >= 50;
                out.putShort(this.f420cw.newUTF8(zip ? StackMapTable.tag : StackMap.tag));
                out.putInt(this.stackMap.length + 2).putShort(this.frameCount);
                out.putByteArray(this.stackMap.data, 0, this.stackMap.length);
            }
            if (this.ctanns != null) {
                out.putShort(this.f420cw.newUTF8(TypeAnnotationsAttribute.visibleTag));
                this.ctanns.put(out);
            }
            if (this.ictanns != null) {
                out.putShort(this.f420cw.newUTF8(TypeAnnotationsAttribute.invisibleTag));
                this.ictanns.put(out);
            }
            if (this.cattrs != null) {
                this.cattrs.put(this.f420cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, out);
            }
        }
        if (this.exceptionCount > 0) {
            out.putShort(this.f420cw.newUTF8(ExceptionsAttribute.tag)).putInt((2 * this.exceptionCount) + 2);
            out.putShort(this.exceptionCount);
            for (int i = 0; i < this.exceptionCount; i++) {
                out.putShort(this.exceptions[i]);
            }
        }
        if ((this.access & 4096) != 0 && ((this.f420cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            out.putShort(this.f420cw.newUTF8(SyntheticAttribute.tag)).putInt(0);
        }
        if ((this.access & 131072) != 0) {
            out.putShort(this.f420cw.newUTF8(DeprecatedAttribute.tag)).putInt(0);
        }
        if (this.signature != null) {
            out.putShort(this.f420cw.newUTF8(SignatureAttribute.tag)).putInt(2).putShort(this.f420cw.newUTF8(this.signature));
        }
        if (this.methodParameters != null) {
            out.putShort(this.f420cw.newUTF8(MethodParametersAttribute.tag));
            out.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
            out.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
        }
        if (this.annd != null) {
            out.putShort(this.f420cw.newUTF8(AnnotationDefaultAttribute.tag));
            out.putInt(this.annd.length);
            out.putByteArray(this.annd.data, 0, this.annd.length);
        }
        if (this.anns != null) {
            out.putShort(this.f420cw.newUTF8(AnnotationsAttribute.visibleTag));
            this.anns.put(out);
        }
        if (this.ianns != null) {
            out.putShort(this.f420cw.newUTF8(AnnotationsAttribute.invisibleTag));
            this.ianns.put(out);
        }
        if (this.tanns != null) {
            out.putShort(this.f420cw.newUTF8(TypeAnnotationsAttribute.visibleTag));
            this.tanns.put(out);
        }
        if (this.itanns != null) {
            out.putShort(this.f420cw.newUTF8(TypeAnnotationsAttribute.invisibleTag));
            this.itanns.put(out);
        }
        if (this.panns != null) {
            out.putShort(this.f420cw.newUTF8(ParameterAnnotationsAttribute.visibleTag));
            AnnotationWriter.put(this.panns, this.synthetics, out);
        }
        if (this.ipanns != null) {
            out.putShort(this.f420cw.newUTF8(ParameterAnnotationsAttribute.invisibleTag));
            AnnotationWriter.put(this.ipanns, this.synthetics, out);
        }
        if (this.attrs != null) {
            this.attrs.put(this.f420cw, null, 0, -1, -1, out);
        }
    }
}
