package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/ObfuscationData.class */
public class ObfuscationData<T> implements Iterable<ObfuscationType> {
    private final Map<ObfuscationType, T> data;
    private final T defaultValue;

    public ObfuscationData() {
        this(null);
    }

    public ObfuscationData(T defaultValue) {
        this.data = new HashMap();
        this.defaultValue = defaultValue;
    }

    @Deprecated
    public void add(ObfuscationType type, T value) {
        put(type, value);
    }

    public void put(ObfuscationType type, T value) {
        this.data.put(type, value);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public T get(ObfuscationType type) {
        T value = this.data.get(type);
        return value != null ? value : this.defaultValue;
    }

    @Override // java.lang.Iterable
    public Iterator<ObfuscationType> iterator() {
        return this.data.keySet().iterator();
    }

    public String toString() {
        return String.format("ObfuscationData[%s,DEFAULT=%s]", listValues(), this.defaultValue);
    }

    public String values() {
        return "[" + listValues() + "]";
    }

    private String listValues() {
        StringBuilder sb = new StringBuilder();
        boolean delim = false;
        for (ObfuscationType type : this.data.keySet()) {
            if (delim) {
                sb.append(',');
            }
            sb.append(type.getKey()).append('=').append(this.data.get(type));
            delim = true;
        }
        return sb.toString();
    }
}
