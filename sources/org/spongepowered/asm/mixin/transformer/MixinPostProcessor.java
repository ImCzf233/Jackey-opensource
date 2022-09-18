package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinPostProcessor.class */
public class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener {
    private final Set<String> syntheticInnerClasses = new HashSet();
    private final Map<String, MixinInfo> accessorMixins = new HashMap();
    private final Set<String> loadable = new HashSet();

    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
    public void onInit(MixinInfo mixin) {
        for (String innerClass : mixin.getSyntheticInnerClasses()) {
            registerSyntheticInner(innerClass.replace('/', '.'));
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
    public void onPrepare(MixinInfo mixin) {
        String className = mixin.getClassName();
        if (mixin.isLoadable()) {
            registerLoadable(className);
        }
        if (mixin.isAccessor()) {
            registerAccessor(mixin);
        }
    }

    void registerSyntheticInner(String className) {
        this.syntheticInnerClasses.add(className);
    }

    void registerLoadable(String className) {
        this.loadable.add(className);
    }

    void registerAccessor(MixinInfo mixin) {
        registerLoadable(mixin.getClassName());
        this.accessorMixins.put(mixin.getClassName(), mixin);
    }

    public boolean canTransform(String className) {
        return this.syntheticInnerClasses.contains(className) || this.loadable.contains(className);
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public String getName() {
        return getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public boolean isDelegationExcluded() {
        return true;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public byte[] transformClassBytes(String name, String transformedName, byte[] bytes) {
        if (this.syntheticInnerClasses.contains(transformedName)) {
            return processSyntheticInner(bytes);
        }
        if (this.accessorMixins.containsKey(transformedName)) {
            MixinInfo mixin = this.accessorMixins.get(transformedName);
            return processAccessor(bytes, mixin);
        }
        return bytes;
    }

    private byte[] processSyntheticInner(byte[] bytes) {
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new MixinClassWriter(cr, 0);
        ClassVisitor visibilityVisitor = new ClassVisitor(Opcodes.ASM5, cw) { // from class: org.spongepowered.asm.mixin.transformer.MixinPostProcessor.1
            @Override // org.spongepowered.asm.lib.ClassVisitor
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access | 1, name, signature, superName, interfaces);
            }

            @Override // org.spongepowered.asm.lib.ClassVisitor
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                if ((access & 6) == 0) {
                    access |= 1;
                }
                return super.visitField(access, name, desc, signature, value);
            }

            @Override // org.spongepowered.asm.lib.ClassVisitor
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if ((access & 6) == 0) {
                    access |= 1;
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        cr.accept(visibilityVisitor, 8);
        return cw.toByteArray();
    }

    private byte[] processAccessor(byte[] bytes, MixinInfo mixin) {
        if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
            return bytes;
        }
        boolean transformed = false;
        MixinInfo.MixinClassNode classNode = mixin.getClassNode(0);
        ClassInfo targetClass = mixin.getTargets().get(0);
        for (MixinInfo.MixinMethodNode methodNode : classNode.mixinMethods) {
            if (Bytecode.hasFlag(methodNode, 8)) {
                AnnotationNode accessor = methodNode.getVisibleAnnotation(Accessor.class);
                AnnotationNode invoker = methodNode.getVisibleAnnotation(Invoker.class);
                if (accessor != null || invoker != null) {
                    ClassInfo.Method method = getAccessorMethod(mixin, methodNode, targetClass);
                    createProxy(methodNode, targetClass, method);
                    transformed = true;
                }
            }
        }
        if (transformed) {
            return writeClass(classNode);
        }
        return bytes;
    }

    private static ClassInfo.Method getAccessorMethod(MixinInfo mixin, MethodNode methodNode, ClassInfo targetClass) throws MixinTransformerError {
        ClassInfo.Method method = mixin.getClassInfo().findMethod(methodNode, 10);
        if (!method.isRenamed()) {
            throw new MixinTransformerError("Unexpected state: " + mixin + " loaded before " + targetClass + " was conformed");
        }
        return method;
    }

    private static void createProxy(MethodNode methodNode, ClassInfo targetClass, ClassInfo.Method method) {
        methodNode.instructions.clear();
        Type[] args = Type.getArgumentTypes(methodNode.desc);
        Type returnType = Type.getReturnType(methodNode.desc);
        Bytecode.loadArgs(args, methodNode.instructions, 0);
        methodNode.instructions.add(new MethodInsnNode(184, targetClass.getName(), method.getName(), methodNode.desc, false));
        methodNode.instructions.add(new InsnNode(returnType.getOpcode(172)));
        methodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(args, false);
        methodNode.maxLocals = 0;
    }
}
