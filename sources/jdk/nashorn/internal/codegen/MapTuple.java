package jdk.nashorn.internal.codegen;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Symbol;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/MapTuple.class */
class MapTuple<T> {
    final String key;
    final Symbol symbol;
    final Type type;
    final T value;

    public MapTuple(String key, Symbol symbol, Type type) {
        this(key, symbol, type, null);
    }

    public MapTuple(String key, Symbol symbol, Type type, T value) {
        this.key = key;
        this.symbol = symbol;
        this.type = type;
        this.value = value;
    }

    public Class<?> getValueType() {
        return null;
    }

    public boolean isPrimitive() {
        return (getValueType() == null || !getValueType().isPrimitive() || getValueType() == Boolean.TYPE) ? false : true;
    }

    public String toString() {
        return "[key=" + this.key + ", symbol=" + this.symbol + ", value=" + this.value + " (" + (this.value == null ? Configurator.NULL : this.value.getClass().getSimpleName()) + ")]";
    }
}
