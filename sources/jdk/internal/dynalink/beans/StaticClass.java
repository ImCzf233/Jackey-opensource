package jdk.internal.dynalink.beans;

import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/StaticClass.class */
public class StaticClass implements Serializable {
    private static final ClassValue<StaticClass> staticClasses = new ClassValue<StaticClass>() { // from class: jdk.internal.dynalink.beans.StaticClass.1
        protected StaticClass computeValue(Class<?> type) {
            return new StaticClass(type);
        }
    };
    private static final long serialVersionUID = 1;
    private final Class<?> clazz;

    StaticClass(Class<?> clazz) {
        this.clazz = (Class) Objects.requireNonNull(clazz);
    }

    public static StaticClass forClass(Class<?> clazz) {
        return (StaticClass) staticClasses.get(clazz);
    }

    public Class<?> getRepresentedClass() {
        return this.clazz;
    }

    public String toString() {
        return "JavaClassStatics[" + this.clazz.getName() + "]";
    }

    private Object readResolve() {
        return forClass(this.clazz);
    }
}
