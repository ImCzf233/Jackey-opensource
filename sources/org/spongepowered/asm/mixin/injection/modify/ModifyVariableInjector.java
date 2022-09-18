package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector.class */
public class ModifyVariableInjector extends Injector {
    private final LocalVariableDiscriminator discriminator;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector$Context.class */
    public static class Context extends LocalVariableDiscriminator.Context {
        final InsnList insns = new InsnList();

        public Context(Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
            super(returnType, argsOnly, target, node);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector$ContextualInjectionPoint.class */
    public static abstract class ContextualInjectionPoint extends InjectionPoint {
        protected final IMixinContext context;

        abstract boolean find(Target target, Collection<AbstractInsnNode> collection);

        public ContextualInjectionPoint(IMixinContext context) {
            this.context = context;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
            throw new InvalidInjectionException(this.context, getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
        }
    }

    public ModifyVariableInjector(InjectionInfo info, LocalVariableDiscriminator discriminator) {
        super(info);
        this.discriminator = discriminator;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
        if (injectionPoint instanceof ContextualInjectionPoint) {
            Target target = this.info.getContext().getTargetMethod(into);
            return ((ContextualInjectionPoint) injectionPoint).find(target, nodes);
        }
        return injectionPoint.find(into.desc, insns, nodes);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    public void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
        super.sanityCheck(target, injectionPoints);
        if (target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
        }
        int ordinal = this.discriminator.getOrdinal();
        if (ordinal < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + ordinal + " specified in " + this);
        }
        if (this.discriminator.getIndex() == 0 && !this.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        if (node.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
        }
        Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, node.getCurrentTarget());
        if (this.discriminator.printLVT()) {
            printLocals(context);
        }
        String handlerDesc = Bytecode.getDescriptor(new Type[]{this.returnType}, this.returnType);
        if (!handlerDesc.equals(this.methodNode.desc)) {
            throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + handlerDesc + " but found " + this.methodNode.desc);
        }
        try {
            int local = this.discriminator.findLocal(context);
            if (local > -1) {
                inject(context, local);
            }
            target.insns.insertBefore(context.node, context.insns);
            target.addToStack(this.isStatic ? 1 : 2);
        } catch (InvalidImplicitDiscriminatorException ex) {
            if (this.discriminator.printLVT()) {
                this.info.addCallbackInvocation(this.methodNode);
                return;
            }
            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, ex);
        }
    }

    private void printLocals(Context context) {
        SignaturePrinter handlerSig = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[]{"var"});
        handlerSig.setModifiers(this.methodNode);
        new PrettyPrinter().kvWidth(20).m9kv("Target Class", this.classNode.name.replace('/', '.')).m9kv("Target Method", context.target.method.name).m9kv("Callback Name", this.methodNode.name).m9kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false)).m8kv("Instruction", "%s %s", context.node.getClass().getSimpleName(), Bytecode.getOpcodeName(context.node.getOpcode())).m11hr().m9kv("Match mode", this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)").m9kv("Match ordinal", this.discriminator.getOrdinal() < 0 ? "any" : Integer.valueOf(this.discriminator.getOrdinal())).m9kv("Match index", this.discriminator.getIndex() < context.baseArgIndex ? "any" : Integer.valueOf(this.discriminator.getIndex())).m9kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").m9kv("Args only", Boolean.valueOf(this.discriminator.isArgsOnly())).m11hr().add((PrettyPrinter.IPrettyPrintable) context).print(System.err);
    }

    private void inject(Context context, int local) {
        if (!this.isStatic) {
            context.insns.add(new VarInsnNode(25, 0));
        }
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(21), local));
        invokeHandler(context.insns);
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(54), local));
    }
}
