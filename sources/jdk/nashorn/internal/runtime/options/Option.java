package jdk.nashorn.internal.runtime.options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/Option.class */
public class Option<T> {
    protected T value;

    public Option(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public String toString() {
        return getValue() + " [" + getValue().getClass() + "]";
    }
}
