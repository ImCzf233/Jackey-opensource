package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ArrayLikeIterator.class */
public abstract class ArrayLikeIterator<T> implements Iterator<T> {
    protected long index = 0;
    protected final boolean includeUndefined;

    public abstract long getLength();

    public ArrayLikeIterator(boolean includeUndefined) {
        this.includeUndefined = includeUndefined;
    }

    public boolean isReverse() {
        return false;
    }

    public long bumpIndex() {
        long j = this.index;
        this.index = j + 1;
        return j;
    }

    public long nextIndex() {
        return this.index;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    public static ArrayLikeIterator<Object> arrayLikeIterator(Object object) {
        return arrayLikeIterator(object, false);
    }

    public static ArrayLikeIterator<Object> reverseArrayLikeIterator(Object object) {
        return reverseArrayLikeIterator(object, false);
    }

    public static ArrayLikeIterator<Object> arrayLikeIterator(Object object, boolean includeUndefined) {
        if (ScriptObject.isArray(object)) {
            return new ScriptArrayIterator((ScriptObject) object, includeUndefined);
        }
        Object obj = JSType.toScriptObject(object);
        if (obj instanceof ScriptObject) {
            return new ScriptObjectIterator((ScriptObject) obj, includeUndefined);
        }
        if (obj instanceof JSObject) {
            return new JSObjectIterator((JSObject) obj, includeUndefined);
        }
        if (obj instanceof List) {
            return new JavaListIterator((List) obj, includeUndefined);
        }
        if (obj != null && obj.getClass().isArray()) {
            return new JavaArrayIterator(obj, includeUndefined);
        }
        return new EmptyArrayLikeIterator();
    }

    public static ArrayLikeIterator<Object> reverseArrayLikeIterator(Object object, boolean includeUndefined) {
        if (ScriptObject.isArray(object)) {
            return new ReverseScriptArrayIterator((ScriptObject) object, includeUndefined);
        }
        Object obj = JSType.toScriptObject(object);
        if (obj instanceof ScriptObject) {
            return new ReverseScriptObjectIterator((ScriptObject) obj, includeUndefined);
        }
        if (obj instanceof JSObject) {
            return new ReverseJSObjectIterator((JSObject) obj, includeUndefined);
        }
        if (obj instanceof List) {
            return new ReverseJavaListIterator((List) obj, includeUndefined);
        }
        if (obj != null && obj.getClass().isArray()) {
            return new ReverseJavaArrayIterator(obj, includeUndefined);
        }
        return new EmptyArrayLikeIterator();
    }
}
