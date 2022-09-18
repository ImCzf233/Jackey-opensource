package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.SpillProperty;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/MapCreator.class */
public class MapCreator<T> {
    private final Class<?> structure;
    private final List<MapTuple<T>> tuples;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !MapCreator.class.desiredAssertionStatus();
    }

    public MapCreator(Class<? extends ScriptObject> structure, List<MapTuple<T>> tuples) {
        this.structure = structure;
        this.tuples = tuples;
    }

    public PropertyMap makeFieldMap(boolean hasArguments, boolean dualFields, int fieldCount, int fieldMaximum, boolean evalCode) {
        List<Property> properties = new ArrayList<>();
        if ($assertionsDisabled || this.tuples != null) {
            for (MapTuple<T> tuple : this.tuples) {
                String key = tuple.key;
                Symbol symbol = tuple.symbol;
                Class<?> initialType = dualFields ? tuple.getValueType() : Object.class;
                if (symbol != null && !ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(key))) {
                    int flags = getPropertyFlags(symbol, hasArguments, evalCode, dualFields);
                    Property property = new AccessorProperty(key, flags, this.structure, symbol.getFieldIndex(), initialType);
                    properties.add(property);
                }
            }
            return PropertyMap.newMap(properties, this.structure.getName(), fieldCount, fieldMaximum, 0);
        }
        throw new AssertionError();
    }

    public PropertyMap makeSpillMap(boolean hasArguments, boolean dualFields) {
        List<Property> properties = new ArrayList<>();
        int spillIndex = 0;
        if ($assertionsDisabled || this.tuples != null) {
            for (MapTuple<T> tuple : this.tuples) {
                String key = tuple.key;
                Symbol symbol = tuple.symbol;
                Class<?> initialType = dualFields ? tuple.getValueType() : Object.class;
                if (symbol != null && !ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(key))) {
                    int flags = getPropertyFlags(symbol, hasArguments, false, dualFields);
                    int i = spillIndex;
                    spillIndex++;
                    properties.add(new SpillProperty(key, flags, i, initialType));
                }
            }
            return PropertyMap.newMap(properties, this.structure.getName(), 0, 0, spillIndex);
        }
        throw new AssertionError();
    }

    static int getPropertyFlags(Symbol symbol, boolean hasArguments, boolean evalCode, boolean dualFields) {
        int flags = 0;
        if (symbol.isParam()) {
            flags = 0 | 8;
        }
        if (hasArguments) {
            flags |= 16;
        }
        if (symbol.isScope() && !evalCode) {
            flags |= 4;
        }
        if (symbol.isFunctionDeclaration()) {
            flags |= 32;
        }
        if (symbol.isConst()) {
            flags |= 1;
        }
        if (symbol.isBlockScoped()) {
            flags |= 1024;
        }
        if (symbol.isBlockScoped() && symbol.isScope()) {
            flags |= 512;
        }
        if (dualFields) {
            flags |= 2048;
        }
        return flags;
    }
}
