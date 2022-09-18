package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/SpillProperty.class */
public class SpillProperty extends AccessorProperty {
    private static final long serialVersionUID = 3028496245198669460L;
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final MethodHandle PARRAY_GETTER = Lookup.f248MH.asType(Lookup.f248MH.getter(LOOKUP, ScriptObject.class, "primitiveSpill", long[].class), Lookup.f248MH.type(long[].class, Object.class));
    private static final MethodHandle OARRAY_GETTER = Lookup.f248MH.asType(Lookup.f248MH.getter(LOOKUP, ScriptObject.class, "objectSpill", Object[].class), Lookup.f248MH.type(Object[].class, Object.class));
    private static final MethodHandle OBJECT_GETTER = Lookup.f248MH.filterArguments(Lookup.f248MH.arrayElementGetter(Object[].class), 0, OARRAY_GETTER);
    private static final MethodHandle PRIMITIVE_GETTER = Lookup.f248MH.filterArguments(Lookup.f248MH.arrayElementGetter(long[].class), 0, PARRAY_GETTER);
    private static final MethodHandle OBJECT_SETTER = Lookup.f248MH.filterArguments(Lookup.f248MH.arrayElementSetter(Object[].class), 0, OARRAY_GETTER);
    private static final MethodHandle PRIMITIVE_SETTER = Lookup.f248MH.filterArguments(Lookup.f248MH.arrayElementSetter(long[].class), 0, PARRAY_GETTER);

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/SpillProperty$Accessors.class */
    public static class Accessors {
        private MethodHandle objectGetter;
        private MethodHandle objectSetter;
        private MethodHandle primitiveGetter;
        private MethodHandle primitiveSetter;
        private final int slot;
        private final MethodHandle ensureSpillSize;
        private static Accessors[] ACCESSOR_CACHE;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !SpillProperty.class.desiredAssertionStatus();
            ACCESSOR_CACHE = new Accessors[512];
        }

        Accessors(int slot) {
            if ($assertionsDisabled || slot >= 0) {
                this.slot = slot;
                this.ensureSpillSize = Lookup.f248MH.asType(Lookup.f248MH.insertArguments(ScriptObject.ENSURE_SPILL_SIZE, 1, Integer.valueOf(slot)), Lookup.f248MH.type(Object.class, Object.class));
                return;
            }
            throw new AssertionError();
        }

        private static void ensure(int slot) {
            int len = ACCESSOR_CACHE.length;
            if (slot >= len) {
                do {
                    len *= 2;
                } while (slot >= len);
                Accessors[] newCache = new Accessors[len];
                System.arraycopy(ACCESSOR_CACHE, 0, newCache, 0, ACCESSOR_CACHE.length);
                ACCESSOR_CACHE = newCache;
            }
        }

        static MethodHandle getCached(int slot, boolean isPrimitive, boolean isGetter) {
            ensure(slot);
            Accessors acc = ACCESSOR_CACHE[slot];
            if (acc == null) {
                acc = new Accessors(slot);
                ACCESSOR_CACHE[slot] = acc;
            }
            return acc.getOrCreate(isPrimitive, isGetter);
        }

        private static MethodHandle primordial(boolean isPrimitive, boolean isGetter) {
            return isPrimitive ? isGetter ? SpillProperty.PRIMITIVE_GETTER : SpillProperty.PRIMITIVE_SETTER : isGetter ? SpillProperty.OBJECT_GETTER : SpillProperty.OBJECT_SETTER;
        }

        MethodHandle getOrCreate(boolean isPrimitive, boolean isGetter) {
            MethodHandle accessor = getInner(isPrimitive, isGetter);
            if (accessor != null) {
                return accessor;
            }
            MethodHandle accessor2 = Lookup.f248MH.insertArguments(primordial(isPrimitive, isGetter), 1, Integer.valueOf(this.slot));
            if (!isGetter) {
                accessor2 = Lookup.f248MH.filterArguments(accessor2, 0, this.ensureSpillSize);
            }
            setInner(isPrimitive, isGetter, accessor2);
            return accessor2;
        }

        void setInner(boolean isPrimitive, boolean isGetter, MethodHandle mh) {
            if (isPrimitive) {
                if (isGetter) {
                    this.primitiveGetter = mh;
                } else {
                    this.primitiveSetter = mh;
                }
            } else if (isGetter) {
                this.objectGetter = mh;
            } else {
                this.objectSetter = mh;
            }
        }

        MethodHandle getInner(boolean isPrimitive, boolean isGetter) {
            return isPrimitive ? isGetter ? this.primitiveGetter : this.primitiveSetter : isGetter ? this.objectGetter : this.objectSetter;
        }
    }

    private static MethodHandle primitiveGetter(int slot, int flags) {
        if ((flags & 2048) == 2048) {
            return Accessors.getCached(slot, true, true);
        }
        return null;
    }

    private static MethodHandle primitiveSetter(int slot, int flags) {
        if ((flags & 2048) == 2048) {
            return Accessors.getCached(slot, true, false);
        }
        return null;
    }

    private static MethodHandle objectGetter(int slot) {
        return Accessors.getCached(slot, false, true);
    }

    private static MethodHandle objectSetter(int slot) {
        return Accessors.getCached(slot, false, false);
    }

    public SpillProperty(String key, int flags, int slot) {
        super(key, flags, slot, primitiveGetter(slot, flags), primitiveSetter(slot, flags), objectGetter(slot), objectSetter(slot));
    }

    public SpillProperty(String key, int flags, int slot, Class<?> initialType) {
        this(key, flags, slot);
        setType(hasDualFields() ? initialType : Object.class);
    }

    public SpillProperty(String key, int flags, int slot, ScriptObject owner, Object initialValue) {
        this(key, flags, slot);
        setInitialValue(owner, initialValue);
    }

    public SpillProperty(SpillProperty property) {
        super(property);
    }

    public SpillProperty(SpillProperty property, Class<?> newType) {
        super((AccessorProperty) property, newType);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy() {
        return new SpillProperty(this);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy(Class<?> newType) {
        return new SpillProperty(this, newType);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public boolean isSpill() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public void initMethodHandles(Class<?> structure) {
        int slot = getSlot();
        this.primitiveGetter = primitiveGetter(slot, getFlags());
        this.primitiveSetter = primitiveSetter(slot, getFlags());
        this.objectGetter = objectGetter(slot);
        this.objectSetter = objectSetter(slot);
    }
}
