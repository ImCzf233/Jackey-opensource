package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/ObfuscationManager.class */
public class ObfuscationManager implements IObfuscationManager {

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f452ap;
    private final IObfuscationDataProvider obfs;
    private final IReferenceManager refs;
    private boolean initDone;
    private final List<ObfuscationEnvironment> environments = new ArrayList();
    private final List<IMappingConsumer> consumers = new ArrayList();

    public ObfuscationManager(IMixinAnnotationProcessor ap) {
        this.f452ap = ap;
        this.obfs = new ObfuscationDataProvider(ap, this.environments);
        this.refs = new ReferenceManager(ap, this.environments);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void init() {
        if (this.initDone) {
            return;
        }
        this.initDone = true;
        ObfuscationServices.getInstance().initProviders(this.f452ap);
        for (ObfuscationType obfType : ObfuscationType.types()) {
            if (obfType.isSupported()) {
                this.environments.add(obfType.createEnvironment());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IObfuscationDataProvider getDataProvider() {
        return this.obfs;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IReferenceManager getReferenceManager() {
        return this.refs;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IMappingConsumer createMappingConsumer() {
        Mappings mappings = new Mappings();
        this.consumers.add(mappings);
        return mappings;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public List<ObfuscationEnvironment> getEnvironments() {
        return this.environments;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void writeMappings() {
        for (ObfuscationEnvironment env : this.environments) {
            env.writeMappings(this.consumers);
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void writeReferences() {
        this.refs.write();
    }
}
