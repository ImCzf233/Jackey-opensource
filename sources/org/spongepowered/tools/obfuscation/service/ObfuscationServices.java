package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/service/ObfuscationServices.class */
public final class ObfuscationServices {
    private static ObfuscationServices instance;
    private final Set<IObfuscationService> services = new HashSet();
    private final ServiceLoader<IObfuscationService> serviceLoader = ServiceLoader.load(IObfuscationService.class, getClass().getClassLoader());

    private ObfuscationServices() {
    }

    public static ObfuscationServices getInstance() {
        if (instance == null) {
            instance = new ObfuscationServices();
        }
        return instance;
    }

    public void initProviders(IMixinAnnotationProcessor ap) {
        try {
            Iterator<IObfuscationService> it = this.serviceLoader.iterator();
            while (it.hasNext()) {
                IObfuscationService service = it.next();
                if (!this.services.contains(service)) {
                    this.services.add(service);
                    String serviceName = service.getClass().getSimpleName();
                    Collection<ObfuscationTypeDescriptor> obfTypes = service.getObfuscationTypes();
                    if (obfTypes != null) {
                        for (ObfuscationTypeDescriptor obfType : obfTypes) {
                            try {
                                ObfuscationType type = ObfuscationType.create(obfType, ap);
                                ap.printMessage(Diagnostic.Kind.NOTE, serviceName + " supports type: \"" + type + "\"");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (ServiceConfigurationError serviceError) {
            ap.printMessage(Diagnostic.Kind.ERROR, serviceError.getClass().getSimpleName() + ": " + serviceError.getMessage());
            serviceError.printStackTrace();
        }
    }

    public Set<String> getSupportedOptions() {
        Set<String> supportedOptions = new HashSet<>();
        Iterator<IObfuscationService> it = this.serviceLoader.iterator();
        while (it.hasNext()) {
            IObfuscationService provider = it.next();
            Set<String> options = provider.getSupportedOptions();
            if (options != null) {
                supportedOptions.addAll(options);
            }
        }
        return supportedOptions;
    }

    public IObfuscationService getService(Class<? extends IObfuscationService> serviceClass) {
        Iterator<IObfuscationService> it = this.serviceLoader.iterator();
        while (it.hasNext()) {
            IObfuscationService service = it.next();
            if (serviceClass.getName().equals(service.getClass().getName())) {
                return service;
            }
        }
        return null;
    }
}
