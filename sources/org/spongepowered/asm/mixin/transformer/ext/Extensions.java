package org.spongepowered.asm.mixin.transformer.ext;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ext/Extensions.class */
public final class Extensions {
    private final MixinTransformer transformer;
    private final List<IExtension> extensions = new ArrayList();
    private final Map<Class<? extends IExtension>, IExtension> extensionMap = new HashMap();
    private final List<IClassGenerator> generators = new ArrayList();
    private final List<IClassGenerator> generatorsView = Collections.unmodifiableList(this.generators);
    private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap = new HashMap();
    private List<IExtension> activeExtensions = Collections.emptyList();

    public Extensions(MixinTransformer transformer) {
        this.transformer = transformer;
    }

    public MixinTransformer getTransformer() {
        return this.transformer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void add(IExtension extension) {
        this.extensions.add(extension);
        this.extensionMap.put(extension.getClass(), extension);
    }

    public List<IExtension> getExtensions() {
        return Collections.unmodifiableList(this.extensions);
    }

    public List<IExtension> getActiveExtensions() {
        return this.activeExtensions;
    }

    public <T extends IExtension> T getExtension(Class<? extends IExtension> extensionClass) {
        return (T) lookup(extensionClass, this.extensionMap, this.extensions);
    }

    public void select(MixinEnvironment environment) {
        ImmutableList.Builder<IExtension> activeExtensions = ImmutableList.builder();
        for (IExtension extension : this.extensions) {
            if (extension.checkActive(environment)) {
                activeExtensions.add(extension);
            }
        }
        this.activeExtensions = activeExtensions.build();
    }

    public void preApply(ITargetClassContext context) {
        for (IExtension extension : this.activeExtensions) {
            extension.preApply(context);
        }
    }

    public void postApply(ITargetClassContext context) {
        for (IExtension extension : this.activeExtensions) {
            extension.postApply(context);
        }
    }

    public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
        for (IExtension extension : this.activeExtensions) {
            extension.export(env, name, force, bytes);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void add(IClassGenerator generator) {
        this.generators.add(generator);
        this.generatorMap.put(generator.getClass(), generator);
    }

    public List<IClassGenerator> getGenerators() {
        return this.generatorsView;
    }

    public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> generatorClass) {
        return (T) lookup(generatorClass, this.generatorMap, this.generators);
    }

    private static <T> T lookup(Class<? extends T> extensionClass, Map<Class<? extends T>, T> map, List<T> list) {
        T extension = map.get(extensionClass);
        if (extension == null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                T classGenerator = it.next();
                if (extensionClass.isAssignableFrom(classGenerator.getClass())) {
                    extension = classGenerator;
                    break;
                }
            }
            if (extension == null) {
                throw new IllegalArgumentException("Extension for <" + extensionClass.getName() + "> could not be found");
            }
            map.put(extensionClass, extension);
        }
        return extension;
    }
}
