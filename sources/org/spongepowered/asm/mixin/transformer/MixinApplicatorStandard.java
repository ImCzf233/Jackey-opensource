package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard.class */
public class MixinApplicatorStandard {
    protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
    protected static final int[] INITIALISER_OPCODE_BLACKLIST = {177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
    protected final TargetClassContext context;
    protected final String targetName;
    protected final ClassNode targetClass;
    protected final boolean mergeSignatures;
    protected final Logger logger = LogManager.getLogger("mixin");
    protected final Profiler profiler = MixinEnvironment.getProfiler();

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$ApplicatorPass.class */
    public enum ApplicatorPass {
        MAIN,
        PREINJECT,
        INJECT
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$InitialiserInjectionMode.class */
    public enum InitialiserInjectionMode {
        DEFAULT,
        SAFE
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$Range.class */
    public class Range {
        final int start;
        final int end;
        final int marker;

        Range(int start, int end, int marker) {
            MixinApplicatorStandard.this = this$0;
            this.start = start;
            this.end = end;
            this.marker = marker;
        }

        boolean isValid() {
            return (this.start == 0 || this.end == 0 || this.end < this.start) ? false : true;
        }

        boolean contains(int value) {
            return value >= this.start && value <= this.end;
        }

        boolean excludes(int value) {
            return value < this.start || value > this.end;
        }

        public String toString() {
            return String.format("Range[%d-%d,%d,valid=%s)", Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()));
        }
    }

    public MixinApplicatorStandard(TargetClassContext context) {
        this.context = context;
        this.targetName = context.getClassName();
        this.targetClass = context.getClassNode();
        ExtensionClassExporter exporter = (ExtensionClassExporter) context.getExtensions().getExtension(ExtensionClassExporter.class);
        this.mergeSignatures = exporter.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES);
    }

    public void apply(SortedSet<MixinInfo> mixins) {
        ApplicatorPass[] values;
        List<MixinTargetContext> mixinContexts = new ArrayList<>();
        for (MixinInfo mixin : mixins) {
            this.logger.log(mixin.getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{mixin.getName(), mixin.getParent(), this.targetName});
            mixinContexts.add(mixin.createContextFor(this.context));
        }
        MixinTargetContext current = null;
        try {
            for (MixinTargetContext context : mixinContexts) {
                current = context;
                context.preApply(this.targetName, this.targetClass);
            }
            for (ApplicatorPass pass : ApplicatorPass.values()) {
                Profiler.Section timer = this.profiler.begin("pass", pass.name().toLowerCase());
                for (MixinTargetContext context2 : mixinContexts) {
                    current = context2;
                    applyMixin(context2, pass);
                }
                timer.end();
            }
            for (MixinTargetContext context3 : mixinContexts) {
                current = context3;
                context3.postApply(this.targetName, this.targetClass);
            }
            applySourceMap(this.context);
            this.context.processDebugTasks();
        } catch (InvalidMixinException ex) {
            throw ex;
        } catch (Exception ex2) {
            throw new InvalidMixinException(current, "Unexpecteded " + ex2.getClass().getSimpleName() + " whilst applying the mixin class: " + ex2.getMessage(), ex2);
        }
    }

    protected final void applyMixin(MixinTargetContext mixin, ApplicatorPass pass) {
        switch (pass) {
            case MAIN:
                applySignature(mixin);
                applyInterfaces(mixin);
                applyAttributes(mixin);
                applyAnnotations(mixin);
                applyFields(mixin);
                applyMethods(mixin);
                applyInitialisers(mixin);
                return;
            case PREINJECT:
                prepareInjections(mixin);
                return;
            case INJECT:
                applyAccessors(mixin);
                applyInjections(mixin);
                return;
            default:
                throw new IllegalStateException("Invalid pass specified " + pass);
        }
    }

    protected void applySignature(MixinTargetContext mixin) {
        if (this.mergeSignatures) {
            this.context.mergeSignature(mixin.getSignature());
        }
    }

    protected void applyInterfaces(MixinTargetContext mixin) {
        for (String interfaceName : mixin.getInterfaces()) {
            if (!this.targetClass.interfaces.contains(interfaceName)) {
                this.targetClass.interfaces.add(interfaceName);
                mixin.getTargetClassInfo().addInterface(interfaceName);
            }
        }
    }

    protected void applyAttributes(MixinTargetContext mixin) {
        if (mixin.shouldSetSourceFile()) {
            this.targetClass.sourceFile = mixin.getSourceFile();
        }
        this.targetClass.version = Math.max(this.targetClass.version, mixin.getMinRequiredClassVersion());
    }

    protected void applyAnnotations(MixinTargetContext mixin) {
        ClassNode sourceClass = mixin.getClassNode();
        Bytecode.mergeAnnotations(sourceClass, this.targetClass);
    }

    protected void applyFields(MixinTargetContext mixin) {
        mergeShadowFields(mixin);
        mergeNewFields(mixin);
    }

    protected void mergeShadowFields(MixinTargetContext mixin) {
        for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
            FieldNode shadow = entry.getKey();
            FieldNode target = findTargetField(shadow);
            if (target != null) {
                Bytecode.mergeAnnotations(shadow, target);
                if (entry.getValue().isDecoratedMutable() && !Bytecode.hasFlag(target, 2)) {
                    target.access &= -17;
                }
            }
        }
    }

    public void mergeNewFields(MixinTargetContext mixin) {
        for (FieldNode field : mixin.getFields()) {
            FieldNode target = findTargetField(field);
            if (target == null) {
                this.targetClass.fields.add(field);
                if (field.signature != null) {
                    if (this.mergeSignatures) {
                        SignatureVisitor sv = mixin.getSignature().getRemapper();
                        new SignatureReader(field.signature).accept(sv);
                        field.signature = sv.toString();
                    } else {
                        field.signature = null;
                    }
                }
            }
        }
    }

    protected void applyMethods(MixinTargetContext mixin) {
        for (MethodNode shadow : mixin.getShadowMethods()) {
            applyShadowMethod(mixin, shadow);
        }
        for (MethodNode mixinMethod : mixin.getMethods()) {
            applyNormalMethod(mixin, mixinMethod);
        }
    }

    protected void applyShadowMethod(MixinTargetContext mixin, MethodNode shadow) {
        MethodNode target = findTargetMethod(shadow);
        if (target != null) {
            Bytecode.mergeAnnotations(shadow, target);
        }
    }

    protected void applyNormalMethod(MixinTargetContext mixin, MethodNode mixinMethod) {
        mixin.transformMethod(mixinMethod);
        if (!mixinMethod.name.startsWith("<")) {
            checkMethodVisibility(mixin, mixinMethod);
            checkMethodConstraints(mixin, mixinMethod);
            mergeMethod(mixin, mixinMethod);
        } else if ("<clinit>".equals(mixinMethod.name)) {
            appendInsns(mixin, mixinMethod);
        }
    }

    protected void mergeMethod(MixinTargetContext mixin, MethodNode method) {
        boolean isOverwrite = Annotations.getVisible(method, Overwrite.class) != null;
        MethodNode target = findTargetMethod(method);
        if (target != null) {
            if (isAlreadyMerged(mixin, method, isOverwrite, target)) {
                return;
            }
            AnnotationNode intrinsic = Annotations.getInvisible(method, Intrinsic.class);
            if (intrinsic != null) {
                if (mergeIntrinsic(mixin, method, isOverwrite, target, intrinsic)) {
                    mixin.getTarget().methodMerged(method);
                    return;
                }
            } else if (mixin.requireOverwriteAnnotations() && !isOverwrite) {
                throw new InvalidMixinException(mixin, String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", method.name, method.desc, mixin, mixin.getTarget().getClassName()));
            } else {
                this.targetClass.methods.remove(target);
            }
        } else if (isOverwrite) {
            throw new InvalidMixinException(mixin, String.format("Overwrite target \"%s\" was not located in target class %s", method.name, mixin.getTargetClassRef()));
        }
        this.targetClass.methods.add(method);
        mixin.methodMerged(method);
        if (method.signature != null) {
            if (this.mergeSignatures) {
                SignatureVisitor sv = mixin.getSignature().getRemapper();
                new SignatureReader(method.signature).accept(sv);
                method.signature = sv.toString();
                return;
            }
            method.signature = null;
        }
    }

    protected boolean isAlreadyMerged(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target) {
        AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
        if (merged == null) {
            if (Annotations.getVisible(target, Final.class) != null) {
                this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[]{method.name, mixin});
                return true;
            }
            return false;
        }
        String sessionId = (String) Annotations.getValue(merged, "sessionId");
        if (!this.context.getSessionId().equals(sessionId)) {
            throw new ClassFormatError("Invalid @MixinMerged annotation found in" + mixin + " at " + method.name + " in " + this.targetClass.name);
        }
        if (Bytecode.hasFlag(target, 4160) && Bytecode.hasFlag(method, 4160)) {
            if (mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[]{method.name, mixin});
                return true;
            }
            return true;
        }
        String owner = (String) Annotations.getValue(merged, "mixin");
        int priority = ((Integer) Annotations.getValue(merged, "priority")).intValue();
        if (priority >= mixin.getPriority() && !owner.equals(mixin.getClassName())) {
            this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[]{method.name, mixin, owner});
            return true;
        } else if (Annotations.getVisible(target, Final.class) != null) {
            this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[]{method.name, mixin, owner});
            return true;
        } else {
            return false;
        }
    }

    protected boolean mergeIntrinsic(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target, AnnotationNode intrinsic) {
        AnnotationNode renamed;
        if (isOverwrite) {
            throw new InvalidMixinException(mixin, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + method.name + " in " + mixin);
        }
        String methodName = method.name + method.desc;
        if (Bytecode.hasFlag(method, 8)) {
            throw new InvalidMixinException(mixin, "@Intrinsic method cannot be static, found " + methodName + " in " + mixin);
        }
        if (!Bytecode.hasFlag(method, 4096) && ((renamed = Annotations.getVisible(method, MixinRenamed.class)) == null || !((Boolean) Annotations.getValue(renamed, "isInterfaceMember", Boolean.FALSE)).booleanValue())) {
            throw new InvalidMixinException(mixin, "@Intrinsic method must be prefixed interface method, no rename encountered on " + methodName + " in " + mixin);
        }
        if (!((Boolean) Annotations.getValue(intrinsic, "displace", Boolean.FALSE)).booleanValue()) {
            this.logger.log(mixin.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[]{methodName, mixin.getTargetClassRef()});
            return true;
        }
        displaceIntrinsic(mixin, method, target);
        return false;
    }

    protected void displaceIntrinsic(MixinTargetContext mixin, MethodNode method, MethodNode target) {
        String proxyName = "proxy+" + target.name;
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if ((insn instanceof MethodInsnNode) && insn.getOpcode() != 184) {
                MethodInsnNode methodNode = (MethodInsnNode) insn;
                if (methodNode.owner.equals(this.targetClass.name) && methodNode.name.equals(target.name) && methodNode.desc.equals(target.desc)) {
                    methodNode.name = proxyName;
                }
            }
        }
        target.name = proxyName;
    }

    protected final void appendInsns(MixinTargetContext mixin, MethodNode method) {
        if (Type.getReturnType(method.desc) != Type.VOID_TYPE) {
            throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
        }
        MethodNode target = findTargetMethod(method);
        if (target != null) {
            AbstractInsnNode returnNode = Bytecode.findInsn(target, 177);
            if (returnNode != null) {
                Iterator<AbstractInsnNode> injectIter = method.instructions.iterator();
                while (injectIter.hasNext()) {
                    AbstractInsnNode insn = injectIter.next();
                    if (!(insn instanceof LineNumberNode) && insn.getOpcode() != 177) {
                        target.instructions.insertBefore(returnNode, insn);
                    }
                }
                target.maxLocals = Math.max(target.maxLocals, method.maxLocals);
                target.maxStack = Math.max(target.maxStack, method.maxStack);
                return;
            }
            return;
        }
        this.targetClass.methods.add(method);
    }

    protected void applyInitialisers(MixinTargetContext mixin) {
        Deque<AbstractInsnNode> initialiser;
        MethodNode ctor = getConstructor(mixin);
        if (ctor == null || (initialiser = getInitialiser(mixin, ctor)) == null || initialiser.size() == 0) {
            return;
        }
        for (MethodNode method : this.targetClass.methods) {
            if ("<init>".equals(method.name)) {
                method.maxStack = Math.max(method.maxStack, ctor.maxStack);
                injectInitialiser(mixin, method, initialiser);
            }
        }
    }

    protected MethodNode getConstructor(MixinTargetContext mixin) {
        MethodNode ctor = null;
        for (MethodNode mixinMethod : mixin.getMethods()) {
            if ("<init>".equals(mixinMethod.name) && Bytecode.methodHasLineNumbers(mixinMethod)) {
                if (ctor == null) {
                    ctor = mixinMethod;
                } else {
                    this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", mixin, ctor.desc));
                }
            }
        }
        return ctor;
    }

    private Range getConstructorRange(MethodNode ctor) {
        boolean lineNumberIsValid = false;
        AbstractInsnNode endReturn = null;
        int line = 0;
        int start = 0;
        int end = 0;
        int superIndex = -1;
        Iterator<AbstractInsnNode> iter = ctor.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn instanceof LineNumberNode) {
                line = ((LineNumberNode) insn).line;
                lineNumberIsValid = true;
            } else if (insn instanceof MethodInsnNode) {
                if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode) insn).name) && superIndex == -1) {
                    superIndex = ctor.instructions.indexOf(insn);
                    start = line;
                }
            } else if (insn.getOpcode() == 181) {
                lineNumberIsValid = false;
            } else if (insn.getOpcode() == 177) {
                if (lineNumberIsValid) {
                    end = line;
                } else {
                    end = start;
                    endReturn = insn;
                }
            }
        }
        if (endReturn != null) {
            LabelNode label = new LabelNode(new Label());
            ctor.instructions.insertBefore(endReturn, label);
            ctor.instructions.insertBefore(endReturn, new LineNumberNode(start, label));
        }
        return new Range(start, end, superIndex);
    }

    protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext mixin, MethodNode ctor) {
        int[] iArr;
        Range init = getConstructorRange(ctor);
        if (!init.isValid()) {
            return null;
        }
        Deque<AbstractInsnNode> initialiser = new ArrayDeque<>();
        boolean gatherNodes = false;
        int trimAtOpcode = -1;
        LabelNode optionalInsn = null;
        Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(init.marker);
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn instanceof LineNumberNode) {
                int line = ((LineNumberNode) insn).line;
                AbstractInsnNode next = ctor.instructions.get(ctor.instructions.indexOf(insn) + 1);
                if (line == init.end && next.getOpcode() != 177) {
                    gatherNodes = true;
                    trimAtOpcode = 177;
                } else {
                    gatherNodes = init.excludes(line);
                    trimAtOpcode = -1;
                }
            } else if (!gatherNodes) {
                continue;
            } else {
                if (optionalInsn != null) {
                    initialiser.add(optionalInsn);
                    optionalInsn = null;
                }
                if (insn instanceof LabelNode) {
                    optionalInsn = (LabelNode) insn;
                } else {
                    int opcode = insn.getOpcode();
                    if (opcode == trimAtOpcode) {
                        trimAtOpcode = -1;
                    } else {
                        for (int ivalidOp : INITIALISER_OPCODE_BLACKLIST) {
                            if (opcode == ivalidOp) {
                                throw new InvalidMixinException(mixin, "Cannot handle " + Bytecode.getOpcodeName(opcode) + " opcode (0x" + Integer.toHexString(opcode).toUpperCase() + ") in class initialiser");
                            }
                        }
                        initialiser.add(insn);
                    }
                }
            }
        }
        AbstractInsnNode last = initialiser.peekLast();
        if (last != null && last.getOpcode() != 181) {
            throw new InvalidMixinException(mixin, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(last.getOpcode()) + " in " + mixin);
        }
        return initialiser;
    }

    protected final void injectInitialiser(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
        Map<LabelNode, LabelNode> labels = Bytecode.cloneLabels(ctor.instructions);
        AbstractInsnNode insn = findInitialiserInjectionPoint(mixin, ctor, initialiser);
        if (insn == null) {
            this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{ctor.desc});
            return;
        }
        for (AbstractInsnNode node : initialiser) {
            if (!(node instanceof LabelNode)) {
                if (node instanceof JumpInsnNode) {
                    throw new InvalidMixinException(mixin, "Unsupported JUMP opcode in initialiser in " + mixin);
                }
                AbstractInsnNode imACloneNow = node.clone(labels);
                ctor.instructions.insert(insn, imACloneNow);
                insn = imACloneNow;
            }
        }
    }

    protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
        Set<String> initialisedFields = new HashSet<>();
        for (AbstractInsnNode initialiserInsn : initialiser) {
            if (initialiserInsn.getOpcode() == 181) {
                initialisedFields.add(fieldKey((FieldInsnNode) initialiserInsn));
            }
        }
        InitialiserInjectionMode mode = getInitialiserInjectionMode(mixin.getEnvironment());
        String targetName = mixin.getTargetClassInfo().getName();
        String targetSuperName = mixin.getTargetClassInfo().getSuperName();
        AbstractInsnNode targetInsn = null;
        Iterator<AbstractInsnNode> iter = ctor.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode) insn).name)) {
                String owner = ((MethodInsnNode) insn).owner;
                if (owner.equals(targetName) || owner.equals(targetSuperName)) {
                    targetInsn = insn;
                    if (mode == InitialiserInjectionMode.SAFE) {
                        break;
                    }
                }
            } else if (insn.getOpcode() == 181 && mode == InitialiserInjectionMode.DEFAULT) {
                String key = fieldKey((FieldInsnNode) insn);
                if (initialisedFields.contains(key)) {
                    targetInsn = insn;
                }
            }
        }
        return targetInsn;
    }

    private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment environment) {
        String strMode = environment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
        if (strMode == null) {
            return InitialiserInjectionMode.DEFAULT;
        }
        try {
            return InitialiserInjectionMode.valueOf(strMode.toUpperCase());
        } catch (Exception e) {
            this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{strMode});
            return InitialiserInjectionMode.DEFAULT;
        }
    }

    private static String fieldKey(FieldInsnNode fieldNode) {
        return String.format("%s:%s", fieldNode.desc, fieldNode.name);
    }

    protected void prepareInjections(MixinTargetContext mixin) {
        mixin.prepareInjections();
    }

    protected void applyInjections(MixinTargetContext mixin) {
        mixin.applyInjections();
    }

    protected void applyAccessors(MixinTargetContext mixin) {
        List<MethodNode> accessorMethods = mixin.generateAccessors();
        for (MethodNode method : accessorMethods) {
            if (!method.name.startsWith("<")) {
                mergeMethod(mixin, method);
            }
        }
    }

    protected void checkMethodVisibility(MixinTargetContext mixin, MethodNode mixinMethod) {
        if (Bytecode.hasFlag(mixinMethod, 8) && !Bytecode.hasFlag(mixinMethod, 2) && !Bytecode.hasFlag(mixinMethod, 4096) && Annotations.getVisible(mixinMethod, Overwrite.class) == null) {
            throw new InvalidMixinException(mixin, String.format("Mixin %s contains non-private static method %s", mixin, mixinMethod));
        }
    }

    protected void applySourceMap(TargetClassContext context) {
        this.targetClass.sourceDebug = context.getSourceMap().toString();
    }

    protected void checkMethodConstraints(MixinTargetContext mixin, MethodNode method) {
        for (Class<? extends Annotation> annotationType : CONSTRAINED_ANNOTATIONS) {
            AnnotationNode annotation = Annotations.getVisible(method, annotationType);
            if (annotation != null) {
                checkConstraints(mixin, method, annotation);
            }
        }
    }

    protected final void checkConstraints(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        try {
            ConstraintParser.Constraint constraint = ConstraintParser.parse(annotation);
            try {
                constraint.check(mixin.getEnvironment());
            } catch (ConstraintViolationException ex) {
                String message = String.format("Constraint violation: %s on %s in %s", ex.getMessage(), method, mixin);
                this.logger.warn(message);
                if (!mixin.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
                    throw new InvalidMixinException(mixin, message, ex);
                }
            }
        } catch (InvalidConstraintException ex2) {
            throw new InvalidMixinException(mixin, ex2.getMessage());
        }
    }

    protected final MethodNode findTargetMethod(MethodNode searchFor) {
        for (MethodNode target : this.targetClass.methods) {
            if (target.name.equals(searchFor.name) && target.desc.equals(searchFor.desc)) {
                return target;
            }
        }
        return null;
    }

    protected final FieldNode findTargetField(FieldNode searchFor) {
        for (FieldNode target : this.targetClass.fields) {
            if (target.name.equals(searchFor.name)) {
                return target;
            }
        }
        return null;
    }
}
