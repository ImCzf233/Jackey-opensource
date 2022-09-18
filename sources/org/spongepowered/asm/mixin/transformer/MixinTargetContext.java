package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.Constants;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinTargetContext.class */
public class MixinTargetContext extends ClassContext implements IMixinContext {
    private static final Logger logger = LogManager.getLogger("mixin");
    private final MixinInfo mixin;
    private final ClassNode classNode;
    private final TargetClassContext targetClass;
    private final String sessionId;
    private final boolean inheritsFromMixin;
    private final boolean detachedSuper;
    private final SourceMap.File stratum;
    private final BiMap<String, String> innerClasses = HashBiMap.create();
    private final List<MethodNode> shadowMethods = new ArrayList();
    private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap();
    private final List<MethodNode> mergedMethods = new ArrayList();
    private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
    private final List<InjectionInfo> injectors = new ArrayList();
    private final List<AccessorInfo> accessors = new ArrayList();
    private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
    private final ClassInfo targetClassInfo = ClassInfo.forName(getTarget().getClassRef());

    public MixinTargetContext(MixinInfo mixin, ClassNode classNode, TargetClassContext context) {
        this.mixin = mixin;
        this.classNode = classNode;
        this.targetClass = context;
        this.stratum = context.getSourceMap().addFile(this.classNode);
        this.inheritsFromMixin = mixin.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy();
        this.detachedSuper = !this.classNode.superName.equals(getTarget().getClassNode().superName);
        this.sessionId = context.getSessionId();
        requireVersion(classNode.version);
        InnerClassGenerator icg = (InnerClassGenerator) context.getExtensions().getGenerator(InnerClassGenerator.class);
        for (String innerClass : this.mixin.getInnerClasses()) {
            this.innerClasses.put(innerClass, icg.registerInnerClass(this.mixin, innerClass, this));
        }
    }

    public void addShadowMethod(MethodNode method) {
        this.shadowMethods.add(method);
    }

    public void addShadowField(FieldNode fieldNode, ClassInfo.Field fieldInfo) {
        this.shadowFields.put(fieldNode, fieldInfo);
    }

    public void addAccessorMethod(MethodNode method, Class<? extends Annotation> type) {
        this.accessors.add(AccessorInfo.m26of(this, method, type));
    }

    public void addMixinMethod(MethodNode method) {
        Annotations.setVisible(method, MixinMerged.class, "mixin", getClassName());
        getTarget().addMixinMethod(method);
    }

    public void methodMerged(MethodNode method) {
        this.mergedMethods.add(method);
        this.targetClassInfo.addMethod(method);
        getTarget().methodMerged(method);
        Annotations.setVisible(method, MixinMerged.class, "mixin", getClassName(), "priority", Integer.valueOf(getPriority()), "sessionId", this.sessionId);
    }

    public String toString() {
        return this.mixin.toString();
    }

    public MixinEnvironment getEnvironment() {
        return this.mixin.getParent().getEnvironment();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public boolean getOption(MixinEnvironment.Option option) {
        return getEnvironment().getOption(option);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    public ClassNode getClassNode() {
        return this.classNode;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getClassName() {
        return this.mixin.getClassName();
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext, org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getClassRef() {
        return this.mixin.getClassRef();
    }

    public TargetClassContext getTarget() {
        return this.targetClass;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getTargetClassRef() {
        return getTarget().getClassRef();
    }

    public ClassNode getTargetClassNode() {
        return getTarget().getClassNode();
    }

    public ClassInfo getTargetClassInfo() {
        return this.targetClassInfo;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    protected ClassInfo getClassInfo() {
        return this.mixin.getClassInfo();
    }

    public ClassSignature getSignature() {
        return getClassInfo().getSignature();
    }

    public SourceMap.File getStratum() {
        return this.stratum;
    }

    public int getMinRequiredClassVersion() {
        return this.minRequiredClassVersion;
    }

    public int getDefaultRequiredInjections() {
        return this.mixin.getParent().getDefaultRequiredInjections();
    }

    public String getDefaultInjectorGroup() {
        return this.mixin.getParent().getDefaultInjectorGroup();
    }

    public int getMaxShiftByValue() {
        return this.mixin.getParent().getMaxShiftByValue();
    }

    public InjectorGroupInfo.Map getInjectorGroups() {
        return this.injectorGroups;
    }

    public boolean requireOverwriteAnnotations() {
        return this.mixin.getParent().requireOverwriteAnnotations();
    }

    public ClassInfo findRealType(ClassInfo mixin) {
        if (mixin == getClassInfo()) {
            return this.targetClassInfo;
        }
        ClassInfo type = this.targetClassInfo.findCorrespondingType(mixin);
        if (type == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + mixin + " in hierarchy of " + this.targetClassInfo);
        }
        return type;
    }

    public void transformMethod(MethodNode method) {
        validateMethod(method);
        transformDescriptor(method);
        transformLVT(method);
        this.stratum.applyOffset(method);
        AbstractInsnNode lastInsn = null;
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn instanceof MethodInsnNode) {
                transformMethodRef(method, iter, new MemberRef.Method((MethodInsnNode) insn));
            } else if (insn instanceof FieldInsnNode) {
                transformFieldRef(method, iter, new MemberRef.Field((FieldInsnNode) insn));
                checkFinal(method, iter, (FieldInsnNode) insn);
            } else if (insn instanceof TypeInsnNode) {
                transformTypeNode(method, iter, (TypeInsnNode) insn, lastInsn);
            } else if (insn instanceof LdcInsnNode) {
                transformConstantNode(method, iter, (LdcInsnNode) insn);
            } else if (insn instanceof InvokeDynamicInsnNode) {
                transformInvokeDynamicNode(method, iter, (InvokeDynamicInsnNode) insn);
            }
            lastInsn = insn;
        }
    }

    private void validateMethod(MethodNode method) {
        if (Annotations.getInvisible(method, SoftOverride.class) != null) {
            ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(method.name, method.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
            if (superMethod == null || !superMethod.isInjected()) {
                throw new InvalidMixinException(this, "Mixin method " + method.name + method.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + getTarget().getClassName());
            }
        }
    }

    private void transformLVT(MethodNode method) {
        if (method.localVariables == null) {
            return;
        }
        for (LocalVariableNode local : method.localVariables) {
            if (local != null && local.desc != null) {
                local.desc = transformSingleDescriptor(Type.getType(local.desc));
            }
        }
    }

    private void transformMethodRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef methodRef) {
        transformDescriptor(methodRef);
        if (methodRef.getOwner().equals(getClassRef())) {
            methodRef.setOwner(getTarget().getClassRef());
            ClassInfo.Method md = getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
            if (md != null && md.isRenamed() && md.getOriginalName().equals(methodRef.getName()) && md.isSynthetic()) {
                methodRef.setName(md.getName());
            }
            upgradeMethodRef(method, methodRef, md);
        } else if (this.innerClasses.containsKey(methodRef.getOwner())) {
            methodRef.setOwner((String) this.innerClasses.get(methodRef.getOwner()));
            methodRef.setDesc(transformMethodDescriptor(methodRef.getDesc()));
        } else if (this.detachedSuper || this.inheritsFromMixin) {
            if (methodRef.getOpcode() == 183) {
                updateStaticBinding(method, methodRef);
            } else if (methodRef.getOpcode() == 182 && ClassInfo.forName(methodRef.getOwner()).isMixin()) {
                updateDynamicBinding(method, methodRef);
            }
        }
    }

    private void transformFieldRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef fieldRef) {
        if (Constants.IMAGINARY_SUPER.equals(fieldRef.getName())) {
            if (fieldRef instanceof MemberRef.Field) {
                processImaginarySuper(method, ((MemberRef.Field) fieldRef).insn);
                iter.remove();
            } else {
                throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
            }
        }
        transformDescriptor(fieldRef);
        if (fieldRef.getOwner().equals(getClassRef())) {
            fieldRef.setOwner(getTarget().getClassRef());
            ClassInfo.Field field = getClassInfo().findField(fieldRef.getName(), fieldRef.getDesc(), 10);
            if (field != null && field.isRenamed() && field.getOriginalName().equals(fieldRef.getName()) && field.isStatic()) {
                fieldRef.setName(field.getName());
                return;
            }
            return;
        }
        ClassInfo fieldOwner = ClassInfo.forName(fieldRef.getOwner());
        if (fieldOwner.isMixin()) {
            ClassInfo actualOwner = this.targetClassInfo.findCorrespondingType(fieldOwner);
            fieldRef.setOwner(actualOwner != null ? actualOwner.getName() : getTarget().getClassRef());
        }
    }

    private void checkFinal(MethodNode method, Iterator<AbstractInsnNode> iter, FieldInsnNode fieldNode) {
        int opcode;
        if (!fieldNode.owner.equals(getTarget().getClassRef()) || (opcode = fieldNode.getOpcode()) == 180 || opcode == 178) {
            return;
        }
        for (Map.Entry<FieldNode, ClassInfo.Field> shadow : this.shadowFields.entrySet()) {
            FieldNode shadowFieldNode = shadow.getKey();
            if (shadowFieldNode.desc.equals(fieldNode.desc) && shadowFieldNode.name.equals(fieldNode.name)) {
                ClassInfo.Field shadowField = shadow.getValue();
                if (shadowField.isDecoratedFinal()) {
                    if (shadowField.isDecoratedMutable()) {
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                            logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[]{shadowField, this.mixin, method.name});
                            return;
                        }
                        return;
                    } else if ("<init>".equals(method.name) || "<clinit>".equals(method.name)) {
                        logger.warn("@Final field {} in {} should be final", new Object[]{shadowField, this.mixin});
                        return;
                    } else {
                        logger.error("Write access detected to @Final field {} in {}::{}", new Object[]{shadowField, this.mixin, method.name});
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                            throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + shadowField + " in " + this.mixin + "::" + method.name);
                        }
                        return;
                    }
                }
                return;
            }
        }
    }

    private void transformTypeNode(MethodNode method, Iterator<AbstractInsnNode> iter, TypeInsnNode typeInsn, AbstractInsnNode lastNode) {
        if (typeInsn.getOpcode() == 192 && typeInsn.desc.equals(getTarget().getClassRef()) && lastNode.getOpcode() == 25 && ((VarInsnNode) lastNode).var == 0) {
            iter.remove();
            return;
        }
        if (typeInsn.desc.equals(getClassRef())) {
            typeInsn.desc = getTarget().getClassRef();
        } else {
            String newName = (String) this.innerClasses.get(typeInsn.desc);
            if (newName != null) {
                typeInsn.desc = newName;
            }
        }
        transformDescriptor(typeInsn);
    }

    private void transformConstantNode(MethodNode method, Iterator<AbstractInsnNode> iter, LdcInsnNode ldcInsn) {
        ldcInsn.cst = transformConstant(method, iter, ldcInsn.cst);
    }

    private void transformInvokeDynamicNode(MethodNode method, Iterator<AbstractInsnNode> iter, InvokeDynamicInsnNode dynInsn) {
        requireVersion(51);
        dynInsn.desc = transformMethodDescriptor(dynInsn.desc);
        dynInsn.bsm = transformHandle(method, iter, dynInsn.bsm);
        for (int i = 0; i < dynInsn.bsmArgs.length; i++) {
            dynInsn.bsmArgs[i] = transformConstant(method, iter, dynInsn.bsmArgs[i]);
        }
    }

    private Object transformConstant(MethodNode method, Iterator<AbstractInsnNode> iter, Object constant) {
        if (constant instanceof Type) {
            Type type = (Type) constant;
            String desc = transformDescriptor(type);
            if (!type.toString().equals(desc)) {
                return Type.getType(desc);
            }
            return constant;
        } else if (constant instanceof Handle) {
            return transformHandle(method, iter, (Handle) constant);
        } else {
            return constant;
        }
    }

    private Handle transformHandle(MethodNode method, Iterator<AbstractInsnNode> iter, Handle handle) {
        MemberRef.Handle memberRef = new MemberRef.Handle(handle);
        if (memberRef.isField()) {
            transformFieldRef(method, iter, memberRef);
        } else {
            transformMethodRef(method, iter, memberRef);
        }
        return memberRef.getMethodHandle();
    }

    private void processImaginarySuper(MethodNode method, FieldInsnNode fieldInsn) {
        if (fieldInsn.getOpcode() != 180) {
            if ("<init>".equals(method.name)) {
                throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsn.name + " must not specify an initialiser");
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsn.getOpcode()) + " opcode in " + method.name + method.desc);
        } else if ((method.access & 2) != 0 || (method.access & 8) != 0) {
            throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is private or static");
        } else {
            if (Annotations.getInvisible(method, SoftOverride.class) == null) {
                throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is not decorated with @SoftOverride");
            }
            Iterator<AbstractInsnNode> methodIter = method.instructions.iterator(method.instructions.indexOf(fieldInsn));
            while (methodIter.hasNext()) {
                AbstractInsnNode insn = methodIter.next();
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode methodNode = (MethodInsnNode) insn;
                    if (methodNode.owner.equals(getClassRef()) && methodNode.name.equals(method.name) && methodNode.desc.equals(method.desc)) {
                        methodNode.setOpcode(183);
                        updateStaticBinding(method, new MemberRef.Method(methodNode));
                        return;
                    }
                }
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + method.name + method.desc);
        }
    }

    private void updateStaticBinding(MethodNode method, MemberRef methodRef) {
        updateBinding(method, methodRef, ClassInfo.Traversal.SUPER);
    }

    private void updateDynamicBinding(MethodNode method, MemberRef methodRef) {
        updateBinding(method, methodRef, ClassInfo.Traversal.ALL);
    }

    private void updateBinding(MethodNode method, MemberRef methodRef, ClassInfo.Traversal traversal) {
        if ("<init>".equals(method.name) || methodRef.getOwner().equals(getTarget().getClassRef()) || getTarget().getClassRef().startsWith("<")) {
            return;
        }
        ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(methodRef.getName(), methodRef.getDesc(), traversal.getSearchType(), traversal);
        if (superMethod != null) {
            if (superMethod.getOwner().isMixin()) {
                throw new InvalidMixinException(this, "Invalid " + methodRef + " in " + this + " resolved " + superMethod.getOwner() + " but is mixin.");
            }
            methodRef.setOwner(superMethod.getImplementor().getName());
        } else if (ClassInfo.forName(methodRef.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + methodRef + " in " + this);
        }
    }

    public void transformDescriptor(FieldNode field) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        field.desc = transformSingleDescriptor(field.desc, false);
    }

    public void transformDescriptor(MethodNode method) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        method.desc = transformMethodDescriptor(method.desc);
    }

    public void transformDescriptor(MemberRef member) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        if (member.isField()) {
            member.setDesc(transformSingleDescriptor(member.getDesc(), false));
        } else {
            member.setDesc(transformMethodDescriptor(member.getDesc()));
        }
    }

    public void transformDescriptor(TypeInsnNode typeInsn) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        typeInsn.desc = transformSingleDescriptor(typeInsn.desc, true);
    }

    private String transformDescriptor(Type type) {
        if (type.getSort() == 11) {
            return transformMethodDescriptor(type.getDescriptor());
        }
        return transformSingleDescriptor(type);
    }

    private String transformSingleDescriptor(Type type) {
        if (type.getSort() < 9) {
            return type.toString();
        }
        return transformSingleDescriptor(type.toString(), false);
    }

    private String transformSingleDescriptor(String desc, boolean isObject) {
        String type = desc;
        while (true) {
            if (!type.startsWith("[") && !type.startsWith("L")) {
                break;
            } else if (type.startsWith("[")) {
                type = type.substring(1);
            } else {
                type = type.substring(1, type.indexOf(";"));
                isObject = true;
            }
        }
        if (!isObject) {
            return desc;
        }
        String innerClassName = (String) this.innerClasses.get(type);
        if (innerClassName != null) {
            return desc.replace(type, innerClassName);
        }
        if (this.innerClasses.inverse().containsKey(type)) {
            return desc;
        }
        ClassInfo typeInfo = ClassInfo.forName(type);
        if (!typeInfo.isMixin()) {
            return desc;
        }
        return desc.replace(type, findRealType(typeInfo).toString());
    }

    private String transformMethodDescriptor(String desc) {
        Type[] argumentTypes;
        StringBuilder newDesc = new StringBuilder();
        newDesc.append('(');
        for (Type arg : Type.getArgumentTypes(desc)) {
            newDesc.append(transformSingleDescriptor(arg));
        }
        return newDesc.append(')').append(transformSingleDescriptor(Type.getReturnType(desc))).toString();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public Target getTargetMethod(MethodNode method) {
        return getTarget().getTargetMethod(method);
    }

    public MethodNode findMethod(MethodNode method, AnnotationNode annotation) {
        List<String> aka;
        Deque<String> aliases = new LinkedList<>();
        aliases.add(method.name);
        if (annotation != null && (aka = (List) Annotations.getValue(annotation, "aliases")) != null) {
            aliases.addAll(aka);
        }
        return getTarget().findMethod(aliases, method.desc);
    }

    public MethodNode findRemappedMethod(MethodNode method) {
        RemapperChain remapperChain = getEnvironment().getRemappers();
        String remappedName = remapperChain.mapMethodName(getTarget().getClassRef(), method.name, method.desc);
        if (remappedName.equals(method.name)) {
            return null;
        }
        Deque<String> aliases = new LinkedList<>();
        aliases.add(remappedName);
        return getTarget().findAliasedMethod(aliases, method.desc);
    }

    public FieldNode findField(FieldNode field, AnnotationNode shadow) {
        List<String> aka;
        Deque<String> aliases = new LinkedList<>();
        aliases.add(field.name);
        if (shadow != null && (aka = (List) Annotations.getValue(shadow, "aliases")) != null) {
            aliases.addAll(aka);
        }
        return getTarget().findAliasedField(aliases, field.desc);
    }

    public FieldNode findRemappedField(FieldNode field) {
        RemapperChain remapperChain = getEnvironment().getRemappers();
        String remappedName = remapperChain.mapFieldName(getTarget().getClassRef(), field.name, field.desc);
        if (remappedName.equals(field.name)) {
            return null;
        }
        Deque<String> aliases = new LinkedList<>();
        aliases.add(remappedName);
        return getTarget().findAliasedField(aliases, field.desc);
    }

    protected void requireVersion(int version) {
        this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, version);
        if (version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            throw new InvalidMixinException(this, "Unsupported mixin class version " + version);
        }
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public Extensions getExtensions() {
        return this.targetClass.getExtensions();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public IMixinInfo getMixin() {
        return this.mixin;
    }

    MixinInfo getInfo() {
        return this.mixin;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public int getPriority() {
        return this.mixin.getPriority();
    }

    public Set<String> getInterfaces() {
        return this.mixin.getInterfaces();
    }

    public Collection<MethodNode> getShadowMethods() {
        return this.shadowMethods;
    }

    public List<MethodNode> getMethods() {
        return this.classNode.methods;
    }

    public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
        return this.shadowFields.entrySet();
    }

    public List<FieldNode> getFields() {
        return this.classNode.fields;
    }

    public Level getLoggingLevel() {
        return this.mixin.getLoggingLevel();
    }

    public boolean shouldSetSourceFile() {
        return this.mixin.getParent().shouldSetSourceFile();
    }

    public String getSourceFile() {
        return this.classNode.sourceFile;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public IReferenceMapper getReferenceMapper() {
        return this.mixin.getParent().getReferenceMapper();
    }

    public void preApply(String transformedName, ClassNode targetClass) {
        this.mixin.preApply(transformedName, targetClass);
    }

    public void postApply(String transformedName, ClassNode targetClass) {
        try {
            this.injectorGroups.validateAll();
            this.mixin.postApply(transformedName, targetClass);
        } catch (InjectionValidationException ex) {
            InjectorGroupInfo group = ex.getGroup();
            throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", group, this.mixin, ex.getMessage()));
        }
    }

    public String getUniqueName(MethodNode method, boolean preservePrefix) {
        return getTarget().getUniqueName(method, preservePrefix);
    }

    public String getUniqueName(FieldNode field) {
        return getTarget().getUniqueName(field);
    }

    public void prepareInjections() {
        this.injectors.clear();
        for (MethodNode method : this.mergedMethods) {
            InjectionInfo injectInfo = InjectionInfo.parse(this, method);
            if (injectInfo != null) {
                if (injectInfo.isValid()) {
                    injectInfo.prepare();
                    this.injectors.add(injectInfo);
                }
                method.visibleAnnotations.remove(injectInfo.getAnnotation());
            }
        }
    }

    public void applyInjections() {
        for (InjectionInfo injectInfo : this.injectors) {
            injectInfo.inject();
        }
        for (InjectionInfo injectInfo2 : this.injectors) {
            injectInfo2.postInject();
        }
        this.injectors.clear();
    }

    public List<MethodNode> generateAccessors() {
        for (AccessorInfo accessor : this.accessors) {
            accessor.locate();
        }
        List<MethodNode> methods = new ArrayList<>();
        for (AccessorInfo accessor2 : this.accessors) {
            MethodNode generated = accessor2.generate();
            getTarget().addMixinMethod(generated);
            methods.add(generated);
        }
        return methods;
    }
}
