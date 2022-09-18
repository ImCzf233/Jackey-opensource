package jdk.nashorn.api.scripting;

import java.nio.ByteBuffer;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.script.Bindings;
import jdk.Exported;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/ScriptObjectMirror.class */
public final class ScriptObjectMirror extends AbstractJSObject implements Bindings {
    private static final AccessControlContext GET_CONTEXT_ACC_CTXT;
    private final ScriptObject sobj;
    private final Global global;
    private final boolean strict;
    private final boolean jsonCompatible;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptObjectMirror.class.desiredAssertionStatus();
        GET_CONTEXT_ACC_CTXT = getContextAccCtxt();
    }

    private static AccessControlContext getContextAccCtxt() {
        Permissions perms = new Permissions();
        perms.add(new RuntimePermission(Context.NASHORN_GET_CONTEXT));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, perms)});
    }

    public boolean equals(Object other) {
        if (other instanceof ScriptObjectMirror) {
            return this.sobj.equals(((ScriptObjectMirror) other).sobj);
        }
        return false;
    }

    public int hashCode() {
        return this.sobj.hashCode();
    }

    public String toString() {
        return (String) inGlobal(new Callable<String>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.1
            @Override // java.util.concurrent.Callable
            public String call() {
                return ScriptRuntime.safeToString(ScriptObjectMirror.this.sobj);
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object call(Object thiz, Object... args) {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != this.global;
        try {
            if (globalChanged) {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (NashornException ne) {
                        throw ne.initEcmaError(this.global);
                    }
                } catch (Error | RuntimeException e) {
                    throw e;
                }
            }
            if (this.sobj instanceof ScriptFunction) {
                Object[] modArgs = globalChanged ? wrapArrayLikeMe(args, oldGlobal) : args;
                Object self = globalChanged ? wrapLikeMe(thiz, oldGlobal) : thiz;
                Object wrapLikeMe = wrapLikeMe(ScriptRuntime.apply((ScriptFunction) this.sobj, unwrap(self, this.global), unwrapArray(modArgs, this.global)));
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                return wrapLikeMe;
            }
            throw new RuntimeException("not a function: " + toString());
        } catch (Throwable th) {
            if (globalChanged) {
                Context.setGlobal(oldGlobal);
            }
            throw th;
        }
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object... args) {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != this.global;
        try {
            if (globalChanged) {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (Error | RuntimeException e) {
                        throw e;
                    }
                } catch (NashornException ne) {
                    throw ne.initEcmaError(this.global);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            if (this.sobj instanceof ScriptFunction) {
                Object[] modArgs = globalChanged ? wrapArrayLikeMe(args, oldGlobal) : args;
                Object wrapLikeMe = wrapLikeMe(ScriptRuntime.construct((ScriptFunction) this.sobj, unwrapArray(modArgs, this.global)));
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                return wrapLikeMe;
            }
            throw new RuntimeException("not a constructor: " + toString());
        } catch (Throwable th) {
            if (globalChanged) {
                Context.setGlobal(oldGlobal);
            }
            throw th;
        }
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object eval(final String s) {
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.2
            @Override // java.util.concurrent.Callable
            public Object call() {
                Context context = (Context) AccessController.doPrivileged(new PrivilegedAction<Context>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.2.1
                    @Override // java.security.PrivilegedAction
                    public Context run() {
                        return Context.getContext();
                    }
                }, ScriptObjectMirror.GET_CONTEXT_ACC_CTXT);
                return ScriptObjectMirror.this.wrapLikeMe(context.eval(ScriptObjectMirror.this.global, s, ScriptObjectMirror.this.sobj, null));
            }
        });
    }

    public Object callMember(String functionName, Object... args) {
        Objects.requireNonNull(functionName);
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != this.global;
        try {
            if (globalChanged) {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (Error | RuntimeException e) {
                        throw e;
                    }
                } catch (NashornException ne) {
                    throw ne.initEcmaError(this.global);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
            Object val = this.sobj.get(functionName);
            if (val instanceof ScriptFunction) {
                Object[] modArgs = globalChanged ? wrapArrayLikeMe(args, oldGlobal) : args;
                Object wrapLikeMe = wrapLikeMe(ScriptRuntime.apply((ScriptFunction) val, this.sobj, unwrapArray(modArgs, this.global)));
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                return wrapLikeMe;
            } else if ((val instanceof JSObject) && ((JSObject) val).isFunction()) {
                Object call = ((JSObject) val).call(this.sobj, args);
                if (globalChanged) {
                    Context.setGlobal(oldGlobal);
                }
                return call;
            } else {
                throw new NoSuchMethodException("No such function " + functionName);
            }
        } catch (Throwable th) {
            if (globalChanged) {
                Context.setGlobal(oldGlobal);
            }
            throw th;
        }
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object getMember(final String name) {
        Objects.requireNonNull(name);
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.3
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(name));
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object getSlot(final int index) {
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.4
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(index));
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(final String name) {
        Objects.requireNonNull(name);
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.5
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.has(name));
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean hasSlot(final int slot) {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.6
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.has(slot));
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public void removeMember(String name) {
        remove(Objects.requireNonNull(name));
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public void setMember(String name, Object value) {
        put((String) Objects.requireNonNull(name), value);
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public void setSlot(final int index, final Object value) {
        inGlobal(new Callable<Void>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.7
            @Override // java.util.concurrent.Callable
            public Void call() {
                ScriptObjectMirror.this.sobj.set(index, ScriptObjectMirror.unwrap(value, ScriptObjectMirror.this.global), ScriptObjectMirror.this.getCallSiteFlags());
                return null;
            }
        });
    }

    public void setIndexedPropertiesToExternalArrayData(final ByteBuffer buf) {
        inGlobal(new Callable<Void>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.8
            @Override // java.util.concurrent.Callable
            public Void call() {
                ScriptObjectMirror.this.sobj.setArray(ArrayData.allocate(buf));
                return null;
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean isInstance(Object instance) {
        if (!(instance instanceof ScriptObjectMirror)) {
            return false;
        }
        final ScriptObjectMirror mirror = (ScriptObjectMirror) instance;
        if (this.global != mirror.global) {
            return false;
        }
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.9
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.isInstance(mirror.sobj));
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        return this.sobj.getClassName();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean isFunction() {
        return this.sobj instanceof ScriptFunction;
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean isStrictFunction() {
        return isFunction() && ((ScriptFunction) this.sobj).isStrict();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean isArray() {
        return this.sobj.isArray();
    }

    public void clear() {
        inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.10
            @Override // java.util.concurrent.Callable
            public Object call() {
                ScriptObjectMirror.this.sobj.clear(ScriptObjectMirror.this.strict);
                return null;
            }
        });
    }

    public boolean containsKey(final Object key) {
        checkKey(key);
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.11
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.containsKey(key));
            }
        })).booleanValue();
    }

    public boolean containsValue(final Object value) {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.12
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.containsValue(ScriptObjectMirror.unwrap(value, ScriptObjectMirror.this.global)));
            }
        })).booleanValue();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return (Set) inGlobal(new Callable<Set<Map.Entry<String, Object>>>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.13
            @Override // java.util.concurrent.Callable
            public Set<Map.Entry<String, Object>> call() {
                Iterator<String> iter = ScriptObjectMirror.this.sobj.propertyIterator();
                Set<Map.Entry<String, Object>> entries = new LinkedHashSet<>();
                while (iter.hasNext()) {
                    String key = iter.next();
                    Object value = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(key)));
                    entries.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
                }
                return Collections.unmodifiableSet(entries);
            }
        });
    }

    public Object get(final Object key) {
        checkKey(key);
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.14
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.get(key)));
            }
        });
    }

    public boolean isEmpty() {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.15
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.isEmpty());
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Set<String> keySet() {
        return (Set) inGlobal(new Callable<Set<String>>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.16
            @Override // java.util.concurrent.Callable
            public Set<String> call() {
                Iterator<String> iter = ScriptObjectMirror.this.sobj.propertyIterator();
                Set<String> keySet = new LinkedHashSet<>();
                while (iter.hasNext()) {
                    keySet.add(iter.next());
                }
                return Collections.unmodifiableSet(keySet);
            }
        });
    }

    public Object put(final String key, final Object value) {
        checkKey(key);
        final ScriptObject oldGlobal = Context.getGlobal();
        final boolean globalChanged = oldGlobal != this.global;
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.17
            @Override // java.util.concurrent.Callable
            public Object call() {
                Object modValue = globalChanged ? ScriptObjectMirror.this.wrapLikeMe(value, oldGlobal) : value;
                return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.put(key, ScriptObjectMirror.unwrap(modValue, ScriptObjectMirror.this.global), ScriptObjectMirror.this.strict)));
            }
        });
    }

    public void putAll(final Map<? extends String, ? extends Object> map) {
        Objects.requireNonNull(map);
        final ScriptObject oldGlobal = Context.getGlobal();
        final boolean globalChanged = oldGlobal != this.global;
        inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.18
            @Override // java.util.concurrent.Callable
            public Object call() {
                for (Map.Entry<? extends String, ? extends Object> entry : map.entrySet()) {
                    Object value = entry.getValue();
                    Object modValue = globalChanged ? ScriptObjectMirror.this.wrapLikeMe(value, oldGlobal) : value;
                    String key = (String) entry.getKey();
                    ScriptObjectMirror.checkKey(key);
                    ScriptObjectMirror.this.sobj.set(key, ScriptObjectMirror.unwrap(modValue, ScriptObjectMirror.this.global), ScriptObjectMirror.this.getCallSiteFlags());
                }
                return null;
            }
        });
    }

    public Object remove(final Object key) {
        checkKey(key);
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.19
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.remove(key, ScriptObjectMirror.this.strict)));
            }
        });
    }

    public boolean delete(final Object key) {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.20
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.delete(ScriptObjectMirror.unwrap(key, ScriptObjectMirror.this.global), ScriptObjectMirror.this.strict));
            }
        })).booleanValue();
    }

    public int size() {
        return ((Integer) inGlobal(new Callable<Integer>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.21
            @Override // java.util.concurrent.Callable
            public Integer call() {
                return Integer.valueOf(ScriptObjectMirror.this.sobj.size());
            }
        })).intValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Collection<Object> values() {
        return (Collection) inGlobal(new Callable<Collection<Object>>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.22
            @Override // java.util.concurrent.Callable
            public Collection<Object> call() {
                List<Object> values = new ArrayList<>(ScriptObjectMirror.this.size());
                Iterator<Object> iter = ScriptObjectMirror.this.sobj.valueIterator();
                while (iter.hasNext()) {
                    values.add(ScriptObjectMirror.translateUndefined(ScriptObjectMirror.this.wrapLikeMe(iter.next())));
                }
                return Collections.unmodifiableList(values);
            }
        });
    }

    public Object getProto() {
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.23
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.getProto());
            }
        });
    }

    public void setProto(final Object proto) {
        inGlobal(new Callable<Void>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.24
            @Override // java.util.concurrent.Callable
            public Void call() {
                ScriptObjectMirror.this.sobj.setPrototypeOf(ScriptObjectMirror.unwrap(proto, ScriptObjectMirror.this.global));
                return null;
            }
        });
    }

    public Object getOwnPropertyDescriptor(final String key) {
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.25
            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.this.wrapLikeMe(ScriptObjectMirror.this.sobj.getOwnPropertyDescriptor(key));
            }
        });
    }

    public String[] getOwnKeys(final boolean all) {
        return (String[]) inGlobal(new Callable<String[]>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.26
            @Override // java.util.concurrent.Callable
            public String[] call() {
                return ScriptObjectMirror.this.sobj.getOwnKeys(all);
            }
        });
    }

    public ScriptObjectMirror preventExtensions() {
        return (ScriptObjectMirror) inGlobal(new Callable<ScriptObjectMirror>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.27
            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                ScriptObjectMirror.this.sobj.preventExtensions();
                return ScriptObjectMirror.this;
            }
        });
    }

    public boolean isExtensible() {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.28
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.isExtensible());
            }
        })).booleanValue();
    }

    public ScriptObjectMirror seal() {
        return (ScriptObjectMirror) inGlobal(new Callable<ScriptObjectMirror>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.29
            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                ScriptObjectMirror.this.sobj.seal();
                return ScriptObjectMirror.this;
            }
        });
    }

    public boolean isSealed() {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.30
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.isSealed());
            }
        })).booleanValue();
    }

    public ScriptObjectMirror freeze() {
        return (ScriptObjectMirror) inGlobal(new Callable<ScriptObjectMirror>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.31
            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                ScriptObjectMirror.this.sobj.freeze();
                return ScriptObjectMirror.this;
            }
        });
    }

    public boolean isFrozen() {
        return ((Boolean) inGlobal(new Callable<Boolean>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.32
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(ScriptObjectMirror.this.sobj.isFrozen());
            }
        })).booleanValue();
    }

    public static boolean isUndefined(Object obj) {
        return obj == ScriptRuntime.UNDEFINED;
    }

    /* renamed from: to */
    public <T> T m77to(final Class<T> type) {
        return (T) inGlobal(new Callable<T>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.33
            /* JADX WARN: Type inference failed for: r0v2, types: [T, java.lang.Object] */
            @Override // java.util.concurrent.Callable
            public T call() {
                return type.cast(ScriptUtils.convert(ScriptObjectMirror.this.sobj, type));
            }
        });
    }

    public static Object wrap(Object obj, Object homeGlobal) {
        return wrap(obj, homeGlobal, false);
    }

    public static Object wrapAsJSONCompatible(Object obj, Object homeGlobal) {
        return wrap(obj, homeGlobal, true);
    }

    private static Object wrap(Object obj, Object homeGlobal, boolean jsonCompatible) {
        if (obj instanceof ScriptObject) {
            if (!(homeGlobal instanceof Global)) {
                return obj;
            }
            ScriptObject sobj = (ScriptObject) obj;
            Global global = (Global) homeGlobal;
            ScriptObjectMirror mirror = new ScriptObjectMirror(sobj, global, jsonCompatible);
            if (jsonCompatible && sobj.isArray()) {
                return new JSONListAdapter(mirror, global);
            }
            return mirror;
        } else if (obj instanceof ConsString) {
            return obj.toString();
        } else {
            if (jsonCompatible && (obj instanceof ScriptObjectMirror)) {
                return ((ScriptObjectMirror) obj).asJSONCompatible();
            }
            return obj;
        }
    }

    public Object wrapLikeMe(Object obj, Object homeGlobal) {
        return wrap(obj, homeGlobal, this.jsonCompatible);
    }

    public Object wrapLikeMe(Object obj) {
        return wrapLikeMe(obj, this.global);
    }

    public static Object unwrap(Object obj, Object homeGlobal) {
        if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) obj;
            return mirror.global == homeGlobal ? mirror.sobj : obj;
        } else if (obj instanceof JSONListAdapter) {
            return ((JSONListAdapter) obj).unwrap(homeGlobal);
        } else {
            return obj;
        }
    }

    public static Object[] wrapArray(Object[] args, Object homeGlobal) {
        return wrapArray(args, homeGlobal, false);
    }

    private static Object[] wrapArray(Object[] args, Object homeGlobal, boolean jsonCompatible) {
        if (args == null || args.length == 0) {
            return args;
        }
        Object[] newArgs = new Object[args.length];
        int index = 0;
        for (Object obj : args) {
            newArgs[index] = wrap(obj, homeGlobal, jsonCompatible);
            index++;
        }
        return newArgs;
    }

    private Object[] wrapArrayLikeMe(Object[] args, Object homeGlobal) {
        return wrapArray(args, homeGlobal, this.jsonCompatible);
    }

    public static Object[] unwrapArray(Object[] args, Object homeGlobal) {
        if (args == null || args.length == 0) {
            return args;
        }
        Object[] newArgs = new Object[args.length];
        int index = 0;
        for (Object obj : args) {
            newArgs[index] = unwrap(obj, homeGlobal);
            index++;
        }
        return newArgs;
    }

    public static boolean identical(Object obj1, Object obj2) {
        Object o1 = obj1 instanceof ScriptObjectMirror ? ((ScriptObjectMirror) obj1).sobj : obj1;
        Object o2 = obj2 instanceof ScriptObjectMirror ? ((ScriptObjectMirror) obj2).sobj : obj2;
        return o1 == o2;
    }

    public ScriptObjectMirror(ScriptObject sobj, Global global) {
        this(sobj, global, false);
    }

    private ScriptObjectMirror(ScriptObject sobj, Global global, boolean jsonCompatible) {
        if ($assertionsDisabled || sobj != null) {
            if (!$assertionsDisabled && global == null) {
                throw new AssertionError("home Global is null");
            }
            this.sobj = sobj;
            this.global = global;
            this.strict = global.isStrictContext();
            this.jsonCompatible = jsonCompatible;
            return;
        }
        throw new AssertionError("ScriptObjectMirror on null!");
    }

    public ScriptObject getScriptObject() {
        return this.sobj;
    }

    public Global getHomeGlobal() {
        return this.global;
    }

    public static Object translateUndefined(Object obj) {
        if (obj == ScriptRuntime.UNDEFINED) {
            return null;
        }
        return obj;
    }

    public int getCallSiteFlags() {
        return this.strict ? 2 : 0;
    }

    private <V> V inGlobal(Callable<V> callable) {
        Global oldGlobal = Context.getGlobal();
        boolean globalChanged = oldGlobal != this.global;
        if (globalChanged) {
            Context.setGlobal(this.global);
        }
        try {
            try {
                try {
                    try {
                        V call = callable.call();
                        if (globalChanged) {
                            Context.setGlobal(oldGlobal);
                        }
                        return call;
                    } catch (NashornException ne) {
                        throw ne.initEcmaError(this.global);
                    }
                } catch (RuntimeException e) {
                    throw e;
                }
            } catch (Exception e2) {
                throw new AssertionError("Cannot happen", e2);
            }
        } catch (Throwable th) {
            if (globalChanged) {
                Context.setGlobal(oldGlobal);
            }
            throw th;
        }
    }

    public static void checkKey(Object key) {
        Objects.requireNonNull(key, "key can not be null");
        if (!(key instanceof String)) {
            throw new ClassCastException("key should be a String. It is " + key.getClass().getName() + " instead.");
        }
        if (((String) key).length() == 0) {
            throw new IllegalArgumentException("key can not be empty");
        }
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return ((Double) inGlobal(new Callable<Double>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.34
            @Override // java.util.concurrent.Callable
            public Double call() {
                return Double.valueOf(JSType.toNumber(ScriptObjectMirror.this.sobj));
            }
        })).doubleValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject
    public Object getDefaultValue(final Class<?> hint) {
        return inGlobal(new Callable<Object>() { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.35
            @Override // java.util.concurrent.Callable
            public Object call() {
                try {
                    return ScriptObjectMirror.this.sobj.getDefaultValue(hint);
                } catch (ECMAException e) {
                    throw new UnsupportedOperationException(e.getMessage(), e);
                }
            }
        });
    }

    private ScriptObjectMirror asJSONCompatible() {
        if (this.jsonCompatible) {
            return this;
        }
        return new ScriptObjectMirror(this.sobj, this.global, true);
    }
}
