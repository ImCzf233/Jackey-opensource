package org.spongepowered.asm.mixin.transformer;

import java.util.Map;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorInterface.class */
public class MixinApplicatorInterface extends MixinApplicatorStandard {
    public MixinApplicatorInterface(TargetClassContext context) {
        super(context);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyInterfaces(MixinTargetContext mixin) {
        for (String interfaceName : mixin.getInterfaces()) {
            if (!this.targetClass.name.equals(interfaceName) && !this.targetClass.interfaces.contains(interfaceName)) {
                this.targetClass.interfaces.add(interfaceName);
                mixin.getTargetClassInfo().addInterface(interfaceName);
            }
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyFields(MixinTargetContext mixin) {
        for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
            FieldNode shadow = entry.getKey();
            this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[]{shadow.name, shadow.desc, mixin});
        }
        mergeNewFields(mixin);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyInitialisers(MixinTargetContext mixin) {
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void prepareInjections(MixinTargetContext mixin) {
        for (MethodNode method : this.targetClass.methods) {
            try {
                InjectionInfo injectInfo = InjectionInfo.parse(mixin, method);
                if (injectInfo != null) {
                    throw new InvalidInterfaceMixinException(mixin, injectInfo + " is not supported on interface mixin method " + method.name);
                }
            } catch (InvalidInjectionException ex) {
                String description = ex.getInjectionInfo() != null ? ex.getInjectionInfo().toString() : "Injection";
                throw new InvalidInterfaceMixinException(mixin, description + " is not supported in interface mixin");
            }
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyInjections(MixinTargetContext mixin) {
    }
}
