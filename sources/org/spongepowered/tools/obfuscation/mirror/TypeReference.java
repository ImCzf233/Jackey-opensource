package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/TypeReference.class */
public class TypeReference implements Serializable, Comparable<TypeReference> {
    private static final long serialVersionUID = 1;
    private final String name;
    private transient TypeHandle handle;

    public TypeReference(TypeHandle handle) {
        this.name = handle.getName();
        this.handle = handle;
    }

    public TypeReference(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.name.replace('/', '.');
    }

    public TypeHandle getHandle(ProcessingEnvironment processingEnv) {
        if (this.handle == null) {
            TypeElement element = processingEnv.getElementUtils().getTypeElement(getClassName());
            try {
                this.handle = new TypeHandle(element);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.handle;
    }

    public String toString() {
        return String.format("TypeReference[%s]", this.name);
    }

    public int compareTo(TypeReference other) {
        if (other == null) {
            return -1;
        }
        return this.name.compareTo(other.name);
    }

    public boolean equals(Object other) {
        return (other instanceof TypeReference) && compareTo((TypeReference) other) == 0;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
