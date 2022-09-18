package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/Mappings.class */
class Mappings implements IMappingConsumer {
    private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings = new HashMap();
    private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings = new HashMap();
    private UniqueMappings unique;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/Mappings$MappingConflictException.class */
    public static class MappingConflictException extends RuntimeException {
        private static final long serialVersionUID = 1;
        private final IMapping<?> oldMapping;
        private final IMapping<?> newMapping;

        public MappingConflictException(IMapping<?> oldMapping, IMapping<?> newMapping) {
            this.oldMapping = oldMapping;
            this.newMapping = newMapping;
        }

        public IMapping<?> getOld() {
            return this.oldMapping;
        }

        public IMapping<?> getNew() {
            return this.newMapping;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/Mappings$UniqueMappings.class */
    public static class UniqueMappings implements IMappingConsumer {
        private final IMappingConsumer mappings;
        private final Map<ObfuscationType, Map<MappingField, MappingField>> fields = new HashMap();
        private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods = new HashMap();

        public UniqueMappings(IMappingConsumer mappings) {
            this.mappings = mappings;
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void clear() {
            clearMaps();
            this.mappings.clear();
        }

        protected void clearMaps() {
            this.fields.clear();
            this.methods.clear();
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
            if (!checkForExistingMapping(type, from, to, this.fields)) {
                this.mappings.addFieldMapping(type, from, to);
            }
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
            if (!checkForExistingMapping(type, from, to, this.methods)) {
                this.mappings.addMethodMapping(type, from, to);
            }
        }

        private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType type, TMapping from, TMapping to, Map<ObfuscationType, Map<TMapping, TMapping>> mappings) throws MappingConflictException {
            Map<TMapping, TMapping> existingMappings = mappings.get(type);
            if (existingMappings == null) {
                existingMappings = new HashMap<>();
                mappings.put(type, existingMappings);
            }
            TMapping existing = existingMappings.get(from);
            if (existing != null) {
                if (existing.equals(to)) {
                    return true;
                }
                throw new MappingConflictException(existing, to);
            }
            existingMappings.put(from, to);
            return false;
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) {
            return this.mappings.getFieldMappings(type);
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) {
            return this.mappings.getMethodMappings(type);
        }
    }

    public Mappings() {
        init();
    }

    private void init() {
        for (ObfuscationType obfType : ObfuscationType.types()) {
            this.fieldMappings.put(obfType, new IMappingConsumer.MappingSet<>());
            this.methodMappings.put(obfType, new IMappingConsumer.MappingSet<>());
        }
    }

    public IMappingConsumer asUnique() {
        if (this.unique == null) {
            this.unique = new UniqueMappings(this);
        }
        return this.unique;
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) {
        IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
        return mappings != null ? mappings : new IMappingConsumer.MappingSet<>();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) {
        IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
        return mappings != null ? mappings : new IMappingConsumer.MappingSet<>();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void clear() {
        this.fieldMappings.clear();
        this.methodMappings.clear();
        if (this.unique != null) {
            this.unique.clearMaps();
        }
        init();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
        IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
        if (mappings == null) {
            mappings = new IMappingConsumer.MappingSet<>();
            this.fieldMappings.put(type, mappings);
        }
        mappings.add(new IMappingConsumer.MappingSet.Pair(from, to));
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
        IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
        if (mappings == null) {
            mappings = new IMappingConsumer.MappingSet<>();
            this.methodMappings.put(type, mappings);
        }
        mappings.add(new IMappingConsumer.MappingSet.Pair(from, to));
    }
}
