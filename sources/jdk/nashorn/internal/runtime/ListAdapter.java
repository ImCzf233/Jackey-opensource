package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.util.AbstractList;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ListAdapter.class */
public class ListAdapter extends AbstractList<Object> implements RandomAccess, Deque<Object> {
    private static final Callable<MethodHandle> ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Object.class);
    private static final Object PUSH = new Object();
    private static final Object UNSHIFT = new Object();
    private static final Callable<MethodHandle> REMOVE_INVOKER_CREATOR = invokerCreator(Object.class, Object.class, JSObject.class);
    private static final Object POP = new Object();
    private static final Object SHIFT = new Object();
    private static final Object SPLICE_ADD = new Object();
    private static final Callable<MethodHandle> SPLICE_ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Integer.TYPE, Integer.TYPE, Object.class);
    private static final Object SPLICE_REMOVE = new Object();
    private static final Callable<MethodHandle> SPLICE_REMOVE_INVOKER_CREATOR = invokerCreator(Void.TYPE, Object.class, JSObject.class, Integer.TYPE, Integer.TYPE);
    final JSObject obj;
    private final Global global;

    public ListAdapter(JSObject obj, Global global) {
        if (global == null) {
            throw new IllegalStateException(ECMAErrors.getMessage("list.adapter.null.global", new String[0]));
        }
        this.obj = obj;
        this.global = global;
    }

    public static ListAdapter create(Object obj) {
        Global global = Context.getGlobal();
        return new ListAdapter(getJSObject(obj, global), global);
    }

    private static JSObject getJSObject(Object obj, Global global) {
        if (obj instanceof ScriptObject) {
            return (JSObject) ScriptObjectMirror.wrap(obj, global);
        }
        if (obj instanceof JSObject) {
            return (JSObject) obj;
        }
        throw new IllegalArgumentException("ScriptObject or JSObject expected");
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int index) {
        checkRange(index);
        return getAt(index);
    }

    private Object getAt(int index) {
        return this.obj.getSlot(index);
    }

    @Override // java.util.AbstractList, java.util.List
    public Object set(int index, Object element) {
        checkRange(index);
        Object prevValue = getAt(index);
        this.obj.setSlot(index, element);
        return prevValue;
    }

    private void checkRange(int index) {
        if (index < 0 || index >= size()) {
            throw invalidIndex(index);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public int size() {
        return JSType.toInt32(this.obj.getMember("length"));
    }

    @Override // java.util.Deque
    public final void push(Object e) {
        addFirst(e);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public final boolean add(Object e) {
        addLast(e);
        return true;
    }

    @Override // java.util.Deque
    public final void addFirst(Object e) {
        try {
            getDynamicInvoker(UNSHIFT, ADD_INVOKER_CREATOR).invokeExact(getFunction("unshift"), this.obj, e);
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override // java.util.Deque
    public final void addLast(Object e) {
        try {
            getDynamicInvoker(PUSH, ADD_INVOKER_CREATOR).invokeExact(getFunction("push"), this.obj, e);
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int index, Object e) {
        try {
            if (index < 0) {
                throw invalidIndex(index);
            }
            if (index == 0) {
                addFirst(e);
            } else {
                int size = size();
                if (index < size) {
                    getDynamicInvoker(SPLICE_ADD, SPLICE_ADD_INVOKER_CREATOR).invokeExact(this.obj.getMember("splice"), this.obj, index, 0, e);
                } else if (index == size) {
                    addLast(e);
                } else {
                    throw invalidIndex(index);
                }
            }
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Object getFunction(String name) {
        Object fn = this.obj.getMember(name);
        if (!Bootstrap.isCallable(fn)) {
            throw new UnsupportedOperationException("The script object doesn't have a function named " + name);
        }
        return fn;
    }

    private static IndexOutOfBoundsException invalidIndex(int index) {
        return new IndexOutOfBoundsException(String.valueOf(index));
    }

    @Override // java.util.Deque, java.util.Queue
    public final boolean offer(Object e) {
        return offerLast(e);
    }

    @Override // java.util.Deque
    public final boolean offerFirst(Object e) {
        addFirst(e);
        return true;
    }

    @Override // java.util.Deque
    public final boolean offerLast(Object e) {
        addLast(e);
        return true;
    }

    @Override // java.util.Deque
    public final Object pop() {
        return removeFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object remove() {
        return removeFirst();
    }

    @Override // java.util.Deque
    public final Object removeFirst() {
        checkNonEmpty();
        return invokeShift();
    }

    @Override // java.util.Deque
    public final Object removeLast() {
        checkNonEmpty();
        return invokePop();
    }

    private void checkNonEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int index) {
        if (index < 0) {
            throw invalidIndex(index);
        }
        if (index == 0) {
            return invokeShift();
        }
        int maxIndex = size() - 1;
        if (index < maxIndex) {
            Object prevValue = get(index);
            invokeSpliceRemove(index, 1);
            return prevValue;
        } else if (index == maxIndex) {
            return invokePop();
        } else {
            throw invalidIndex(index);
        }
    }

    private Object invokeShift() {
        try {
            return getDynamicInvoker(SHIFT, REMOVE_INVOKER_CREATOR).invokeExact(getFunction("shift"), this.obj);
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Object invokePop() {
        try {
            return getDynamicInvoker(POP, REMOVE_INVOKER_CREATOR).invokeExact(getFunction("pop"), this.obj);
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int fromIndex, int toIndex) {
        invokeSpliceRemove(fromIndex, toIndex - fromIndex);
    }

    private void invokeSpliceRemove(int fromIndex, int count) {
        try {
            getDynamicInvoker(SPLICE_REMOVE, SPLICE_REMOVE_INVOKER_CREATOR).invokeExact(getFunction("splice"), this.obj, fromIndex, count);
        } catch (Error | RuntimeException ex) {
            throw ex;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object poll() {
        return pollFirst();
    }

    @Override // java.util.Deque
    public final Object pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return invokeShift();
    }

    @Override // java.util.Deque
    public final Object pollLast() {
        if (isEmpty()) {
            return null;
        }
        return invokePop();
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object peek() {
        return peekFirst();
    }

    @Override // java.util.Deque
    public final Object peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return get(0);
    }

    @Override // java.util.Deque
    public final Object peekLast() {
        if (isEmpty()) {
            return null;
        }
        return get(size() - 1);
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object element() {
        return getFirst();
    }

    @Override // java.util.Deque
    public final Object getFirst() {
        checkNonEmpty();
        return get(0);
    }

    @Override // java.util.Deque
    public final Object getLast() {
        checkNonEmpty();
        return get(size() - 1);
    }

    @Override // java.util.Deque
    public final Iterator<Object> descendingIterator() {
        final ListIterator<Object> it = listIterator(size());
        return new Iterator<Object>() { // from class: jdk.nashorn.internal.runtime.ListAdapter.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasPrevious();
            }

            @Override // java.util.Iterator
            public Object next() {
                return it.previous();
            }

            @Override // java.util.Iterator
            public void remove() {
                it.remove();
            }
        };
    }

    @Override // java.util.Deque
    public final boolean removeFirstOccurrence(Object o) {
        return removeOccurrence(o, iterator());
    }

    @Override // java.util.Deque
    public final boolean removeLastOccurrence(Object o) {
        return removeOccurrence(o, descendingIterator());
    }

    private static boolean removeOccurrence(Object o, Iterator<Object> it) {
        while (it.hasNext()) {
            if (Objects.equals(o, it.next())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    private static Callable<MethodHandle> invokerCreator(final Class<?> rtype, final Class<?>... ptypes) {
        return new Callable<MethodHandle>() { // from class: jdk.nashorn.internal.runtime.ListAdapter.2
            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", rtype, ptypes);
            }
        };
    }

    private MethodHandle getDynamicInvoker(Object key, Callable<MethodHandle> creator) {
        return this.global.getDynamicInvoker(key, creator);
    }
}
